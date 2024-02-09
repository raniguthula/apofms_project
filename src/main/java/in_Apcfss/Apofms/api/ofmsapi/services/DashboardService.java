package in_Apcfss.Apofms.api.ofmsapi.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface DashboardService {

	List<Map<String, String>> DashBoardBankDetails();

}
