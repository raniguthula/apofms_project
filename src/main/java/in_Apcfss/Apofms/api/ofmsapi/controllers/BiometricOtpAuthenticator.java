package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.BiometricOtpAuthenticatorRepo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.request.BioOtpAuthenticatorRequest;
import in_Apcfss.Apofms.api.ofmsapi.servicesimpl.CommonServiceImpl;
import java.util.Base64;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class BiometricOtpAuthenticator {

	@Autowired
	BiometricOtpAuthenticatorRepo biometricOtpAuthenticatorRepo;
	@Autowired
	UsersSecurityRepo usersSecurityRepo;
	private final static RestTemplate restTemplate = new RestTemplate();

//	@PostMapping(value = "/uidencrpt", consumes = "application/json", produces = "application/json")
//	public List<Map<String, Object>> BioUidEncrpt(@RequestBody BioOtpAuthenticatorMode bioOtpAuthenticatorModel) {
//		
//		final String user_id = CommonServiceImpl.getLoggedInUserId();
//		System.out.println("Logged User Id is :::" + user_id);
//
//		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		String uid = (String) bioOtpAuthenticatorModel.getUid();
//		String outer = "";
//		String unique_key = "e0b421bfff38418170fa78e5750d5841";
//		String code_append_to_uid = "sS00Fc7";
//
//		if (uid != null && !uid.equals(""))
//
//			outer = uid + "#" + unique_key;
//		// outer=new Base64.Encoder;
//		System.out.println("outer------------>" + outer);
//
//		byte[] encode = Base64.getEncoder().encode(outer.getBytes());
//		System.out.println("encode------------>" + encode);
//
//		String result = new String(encode);
//		System.out.println("result------------>" + result);
//
//		String encodeToString = Base64.getEncoder().encodeToString(outer.getBytes());
//		System.out.println("encodeToString------------>" + encodeToString);
//
//		outer = encodeToString + code_append_to_uid;
//		System.out.println("outer------------>" + outer);
//
//		if (outer.length() > 0) {
//			map.put("responseToken", 1);
//			map.put("responseMsg", "Aadhaar founded");
//			map.put("respose", outer);
//		} else {
//
//			map.put("responseToken", 0);
//			map.put("responseMsg", "Aadhaar not found");
//
//		}
//		respose.add(map);
//
//		return respose;
//	}

	@SuppressWarnings("unused")
	@PostMapping(value = "/sendOTP", consumes = "application/json", produces = "application/json")
	public String SendOtpforAuth(@RequestBody BioOtpAuthenticatorRequest bioOtpAuthenticatorRequest) {

		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);

		String auth_uid = (String) bioOtpAuthenticatorRequest.getAuth_uid();
		String outer = "";
		String unique_key = "e0b421bfff38418170fa78e5750d5841";
		String code_append_to_uid = "sS00Fc7";

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("aadharno", auth_uid);
		System.out.println("aadharno------>" + auth_uid);

		jsonObj.put("unique_key", unique_key);
		System.out.println("unique_key------>" + unique_key);

		String url = "http://cfssutilities.ap.gov.in/cfssutilsws/cfss/aadharservices/otpGeneration";

		String aadhaarResponse = apiCallingusing(jsonObj.toString(), url);
		System.out.println("Aadhaar Response---------->" + aadhaarResponse);
		String auth_date = "";
		String auth_err_code = "";
		String auth_reason = "";
		String auth_transaction_code = "";
		String auth_status = "";

		JSONObject jObj = new JSONObject(aadhaarResponse);
		String json = jObj.toString();

		System.out.println("jObj------>" + jObj);

//			if (jObj != null) {
//				
//				System.out.println("jObj IF Loop------>"+jObj);
//				
//				auth_date = jObj.getString("ts");
//				auth_err_code = jObj.getString("err");
//				auth_reason = jObj.getString("errdesc");
//				auth_transaction_code = jObj.getString("txn");
//
//				auth_status = jObj.getString("ret");
//			}
		if (aadhaarResponse != null) {

			if (auth_status.equalsIgnoreCase("y")) {
				outer = auth_status + "@" + auth_transaction_code + "@" + auth_uid;
			}
		}
		return json;
	}

	@SuppressWarnings("unused")
	@PostMapping(value = "/checkOTP", consumes = "application/json", produces = "application/json")
	public String CheckOtpforAuth(@RequestBody BioOtpAuthenticatorRequest bioOtpAuthenticatorRequest) {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String auth_uid = (String) bioOtpAuthenticatorRequest.getAuth_uid();
		String otp_ent = (String) bioOtpAuthenticatorRequest.getOtp_ent();
		String txnOtpresp = (String) bioOtpAuthenticatorRequest.getTxnOtpresp();
		String outer = "";
		String unique_key = "e0b421bfff38418170fa78e5750d5841";
		String code_append_to_uid = "sS00Fc7";

		JSONObject jObj = null;

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("aadharno", auth_uid);
		System.out.println("aadharno------>" + auth_uid);

		jsonObj.put("unique_key", unique_key);
		System.out.println("unique_key------>" + unique_key);

		jsonObj.put("txnNo", txnOtpresp);
		System.out.println("txnOtpresp------>" + txnOtpresp);

		jsonObj.put("otp", otp_ent);
		System.out.println("otp_ent------>" + otp_ent);

		String url = "http://cfssutilities.ap.gov.in/cfssutilsws/cfss/aadharservices/OtpReponse";

		String aadhaarResponse = apiCallingusing(jsonObj.toString(), url);

		System.out.println("aadhaarResponse------>" + aadhaarResponse);

		String auth_date = "";
		String auth_err_code = "";
		String auth_reason = "";
		String auth_transaction_code = "";
		String auth_status = "";

		jObj = new JSONObject(aadhaarResponse);
		String json = jObj.toString();
		if (aadhaarResponse != null) {

			if (auth_status.equalsIgnoreCase("y")) {
				outer = "Authentication Success";
			}
		}
		return json;
	}

	public static String apiCalling(String url, String queryParams) {

		String result = "";
		URL url1 = null;
		HttpURLConnection connection = null;
		String auth_user = "utilities";
		String auth_pwd = "swte@m482";
		try {
			url1 = new URL(url);
			String auth = auth_user + ":" + auth_pwd;

//	         String encoding = new BASE64Encoder().encode(auth.getBytes());

			byte[] encode = Base64.getEncoder().encode(auth.getBytes());
			result = new String(encode);
			System.out.println(result);

			String encodeToString = Base64.getEncoder().encodeToString(auth.getBytes());
			System.out.println(encodeToString);

			connection = (HttpURLConnection) url1.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + encode);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			StringBuffer queryParam = new StringBuffer();
			queryParam.append(queryParams);
			final OutputStream os = connection.getOutputStream();
			os.write(queryParam.toString().getBytes());
			os.flush();
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			System.out.println("in:::" + in);
			String line;
			while ((line = in.readLine()) != null) {
				result = line;
			}
			System.out.println("json response--->" + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
			System.out.println("AADHAR Connection Closed");
		}
		return result;
	}

	@SuppressWarnings("unused")
	public static String apiCallingusing(String jsonObject, String url) {

		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);

		String output = "";
		String auth_user = "utilities";
		String auth_pwd = "swte@m482";
		ResponseEntity<String> response = null;

		try {

			String authString = auth_user + ":" + auth_pwd;

//		     String authStringEnc = new BASE64Encoder().encode(authString.getBytes());

			byte[] encode = Base64.getEncoder().encode(authString.getBytes());

			String result = new String(encode);
			System.out.println("result----------->" + result);

			String encodeToString = Base64.getEncoder().encodeToString(authString.getBytes());
			System.out.println("encodeToString--------->" + encodeToString);

			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBasicAuth(auth_user, auth_pwd);
			System.out.println("headers------>" + headers);

			HttpEntity<String> request = null;

			request = new HttpEntity<String>(jsonObject, headers);
			System.out.println("request------>" + request);

			response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			System.out.println("HttpMethod.POST------>" + HttpMethod.POST);
			System.out.println("response------>" + response);

			response.getBody();
			System.out.println("response get Body------>" + response.getBody());

			if (response != null && response.getBody() != null) {
				System.out.println("YES");
				System.out.println("respponse-------->" + response.getBody().toString());
			} else {
				System.out.println("NONES");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return response.getBody().toString();
	}

	@GetMapping("/getBiometricList")
	public List<Map<String, String>> getBiometricList() {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		return biometricOtpAuthenticatorRepo.getBiometricList();
	}

	@GetMapping("/getAadhaarNum")
	public List<Map<String, String>> getAadhaarNum() {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		List<Map<String, String>> aadhar = null;
		 
		String role_id = usersSecurityRepo.getRoleId(user_id);
		System.out.println("Role_ID ::::" + role_id);
		 
		/*
		 * if(user_id.startsWith("AE") || user_id.startsWith("DEE") ||
		 * user_id.startsWith("DVH") || user_id.startsWith("DH") ||
		 * user_id.startsWith("CBSUSERA"))
		 */ 
//		if(role_id.equals("18") || role_id.equals("5") || role_id.equals("13") || role_id.equals("2") || role_id.equals("15") || 
//				role_id.equals("50") || role_id.equals("64") || ) {
			
			System.out.println("user_id---->"+user_id);
			aadhar = biometricOtpAuthenticatorRepo.getAadhaarNum(user_id); 
//		}
		return aadhar;
	}
}
