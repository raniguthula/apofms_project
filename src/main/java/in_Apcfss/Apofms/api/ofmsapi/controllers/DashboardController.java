package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in_Apcfss.Apofms.api.ofmsapi.services.DashboardService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {
	@Autowired
	DashboardService dashboardService;
	
	@GetMapping("DashBoardBankDetails")
	public List<Map<String, String>>  DashBoardBankDetails() {

		return dashboardService.DashBoardBankDetails();
	}
}
