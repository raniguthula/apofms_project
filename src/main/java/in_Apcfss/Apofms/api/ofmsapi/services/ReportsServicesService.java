package in_Apcfss.Apofms.api.ofmsapi.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.models.LoginRequest;

@Service
public interface ReportsServicesService {

	String getRoleNames();

	Map<String, Object> getServices();

	Map<String, Object> getReports();

	Map<String, Object> getPayrollReports();

	Map<String, Object> getBRSForms();

	Map<String, Object> getManagerApproveForms();

	Map<String, Object> getHousingReports();

	Map<String, Object> getGeneralServices();
	Map<String, Object> getAdminServices();

	List<Map<String, Object>> changeUsername(LoginRequest loginRequest, HttpServletRequest request);
	//ADDED PARENT IDS
	Map<String, Object> getParentReports();

	Map<String, Object> getParentServices(); 

	Map<String, Object> getParentBrs();
	
	Map<String, Object> getParentPayrollReports();
	Map<String, Object> getParentApprovals();
	List<Map<String, Object>> getParentIDsList();

	List<Map<String, Object>> getServicesList();

	List<Map<String, Object>> getEditServiceData(int service_id);

	List<Map<String, Object>> updateServices(int parent_id, int service_id, int display_id, String target,
			String service_name);

	List<Map<String, Object>> deleteService(int service_id);

	List<Map<String, Object>> getRolesList();

	List<Map<String, Object>> getServicesListWithRoleID(int role_id);

	List<Map<String, Object>> insertRolesServices(LoginRequest loginRequest);

}
