package in_Apcfss.Apofms.api.ofmsapi.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import in_Apcfss.Apofms.api.ofmsapi.Execptions.JwtAuthenticationException;
import in_Apcfss.Apofms.api.ofmsapi.servicesimpl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public String generateToken(String username) {

		Claims claims = Jwts.claims().setSubject(username);

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + 3600000); // 1 hour
//		Date expiryDate = new Date(now.getTime() + 60000); 
		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	public boolean validateJwtToken(String authToken) throws JwtAuthenticationException {

	    try {

	        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

	        return true;

	    } catch (ExpiredJwtException e) {

	        throw new JwtAuthenticationException("JWT token is expired");

	    } catch (SignatureException e) {

			logger.error("Invalid JWT signature: {}", e.getMessage());

		} catch (MalformedJwtException e) {

			logger.error("Invalid JWT token: {}", e.getMessage());

		} catch (UnsupportedJwtException e) {

			logger.error("JWT token is unsupported: {}", e.getMessage());

		} catch (IllegalArgumentException e) {

			logger.error("JWT claims string is empty: {}", e.getMessage());

		}

	    return false;

	}
//	public boolean validateJwtToken(String authToken) {
//		try {
//			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//			return true;
//		} catch (SignatureException e) {
//			logger.error("Invalid JWT signature: {}", e.getMessage());
//		} catch (MalformedJwtException e) {
//			logger.error("Invalid JWT token: {}", e.getMessage());
//		} catch (ExpiredJwtException e) {
//			logger.error("JWT token is expired: {}", e.getMessage());
//		} catch (UnsupportedJwtException e) {
//			logger.error("JWT token is unsupported: {}", e.getMessage());
//		} catch (IllegalArgumentException e) {
//			logger.error("JWT claims string is empty: {}", e.getMessage());
//		}
//
//		return false;
//	}
}
