package in_Apcfss.Apofms.api.ofmsapi.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import in_Apcfss.Apofms.api.ofmsapi.Execptions.JwtAuthenticationException;
import in_Apcfss.Apofms.api.ofmsapi.servicesimpl.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		System.out.println("AuthTokenFilter");
//		try {
//			String jwt = parseJwt(request);
//			System.out.println("AuthTokenFilter"+jwt);
//			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//				String username = jwtUtils.getUserNameFromJwtToken(jwt);
//				System.out.println("username"+username);
//				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//			}
//		} catch (Exception e) {
//			logger.error("Cannot set user authentication: {}", e);
//		}
//
//		filterChain.doFilter(request, response);
//	}
	@Override

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)

			throws ServletException, IOException {

		try {

			final String requestTokenHeader = request.getHeader("Authorization");

			String jwt = parseJwt(request);

			if (request.getRequestURL().toString().contains("/ofmsapi/") ||

					request.getRequestURL().toString().contains("/ofmsapi/OfmsLogin") ||

//					request.getRequestURL().toString().contains("/YJCAPI/MobileLogin") ||
//
//					request.getRequestURL().toString().contains("/YJCAPI/login") ||

					

					jwt != null) {

				jwtUtils.validateJwtToken(jwt);

				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				System.out.println(username + "authontication usernameusernameusername");

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(

						userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);

			}

			else if (!jwtUtils.validateJwtToken(requestTokenHeader.substring(7, requestTokenHeader.length()))) {

				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>[[TOKEN IS EXPIRED]]<<\n");

				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Is Expired");


			}



			else {

				throw new JwtAuthenticationException("JWT token is missing");

//	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: JWT token is missing");

			}

		} catch (JwtAuthenticationException e) {

			SecurityContextHolder.clearContext(); // Clear the security context

			response.setContentType("application/json");

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");

			return;

		} catch (Exception e) {

			logger.error("Cannot set user authentication: {}", e);

		}

		filterChain.doFilter(request, response);

	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
