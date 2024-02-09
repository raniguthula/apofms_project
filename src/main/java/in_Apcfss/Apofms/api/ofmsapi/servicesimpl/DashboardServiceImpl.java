package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.DashboardRepo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.services.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {
	@Autowired
	DashboardRepo dashboardRepo;
	@Autowired
	UsersSecurityRepo usersSecurityRepo;

	@Override
	public List<Map<String, String>> DashBoardBankDetails() {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		return dashboardRepo.DashBoardBankDetails(security_id);
	}

}
