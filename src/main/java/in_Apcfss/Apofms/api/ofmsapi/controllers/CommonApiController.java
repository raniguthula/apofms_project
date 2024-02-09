package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in_Apcfss.Apofms.api.ofmsapi.services.CommonApiService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommonApiController {
	@Autowired 
	CommonApiService commonApiService;
	// ------http://172.17.205.53:9082/ofmsapi/District_Name
	// To Get the District Name:

	@GetMapping("District_Names")
	public List<Map<String, String>> getDistrict_Names() {

		return commonApiService.getDistrict_Names();
	}

	@GetMapping("District_Name")
	public List<Map<String, String>> getDistrict_Name() {

		return commonApiService.getDistrict_Name();
	}

	@GetMapping("getLoggedUser")
	public List<Map<String, String>> getLoggedUser() {

		return commonApiService.getLoggedUser();
	}


//-----http://172.17.205.53:9082/ofmsapi/Name_Of_The_Bank_DropDown
	// To Get Name of the Bank:
	@GetMapping("Name_Of_The_Bank_DropDown")
	public List<Map<String, String>> getName_Of_The_Bank_DropDown() {
		return commonApiService.getName_Of_The_Bank_DropDown();

	}
	@GetMapping("Bank_Name_And_Account_No_DropDown")
	public List<Map<String, String>> Bank_Name_And_Account_No_DropDown() {

		return commonApiService.Bank_Name_And_Account_No_DropDown();
	}
	
//	http://172.17.205.53:9082/ofmsapi/HeadNames
	@GetMapping("HeadNames")//heeads for receiptss
	public List<Map<String, String>> getHeadNames() {
		return commonApiService.getHeadNames();
	}

	@GetMapping("HeadNamesPayments")
	public List<Map<String, String>> HeadNamesPayments() {
		return commonApiService.HeadNamesPayments();
	}
	
	@GetMapping("ClassificationBudget")
	public List<Map<String, String>> ClassificationBudget() {
		return commonApiService.ClassificationBudget();
	}

//	http://172.17.205.53:9082/ofmsapi/SubHeadNames
	@GetMapping("SubHeadNames/{headid}")
	public List<Map<String, String>> getSubHeadNames(@PathVariable String headid) {
		return commonApiService.getSubHeadNames(headid);
	}
	@GetMapping("CGG_District")
	public List<Map<String, String>> CGG_District() {

		return commonApiService.CGG_District();
	}

}
