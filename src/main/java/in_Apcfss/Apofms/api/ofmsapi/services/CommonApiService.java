package in_Apcfss.Apofms.api.ofmsapi.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CommonApiService {
	List<Map<String, String>> getDistrict_Names();
	List<Map<String, String>> getDistrict_Name();

	List<Map<String, String>> getLoggedUser();

	List<Map<String, String>> getName_Of_The_Bank_DropDown();

	List<Map<String, String>> Bank_Name_And_Account_No_DropDown();
	List<Map<String, String>> getHeadNames();

	List<Map<String, String>> HeadNamesPayments();

	List<Map<String, String>> ClassificationBudget();

	List<Map<String, String>> getSubHeadNames(String headid);
	List<Map<String, String>> CGG_District();
}
