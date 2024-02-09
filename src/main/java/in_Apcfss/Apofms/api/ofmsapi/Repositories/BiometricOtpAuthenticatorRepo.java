package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.BankDetails_Model;

@Repository
public interface BiometricOtpAuthenticatorRepo extends JpaRepository<BankDetails_Model, Integer> {

	@Query(value = "select display_name, device_name from biometric_devices_list", nativeQuery = true)
	List<Map<String, String>> getBiometricList();

	@Query(value = "select coalesce(emp_name,'Not Available') emp_name,b.uid from employee_user_mapping a \r\n"
			+ "left join employee_registration b on a.uid=b.uid where user_id=:user_id", nativeQuery = true)
	List<Map<String, String>> getAadhaarNum(String user_id);

}
