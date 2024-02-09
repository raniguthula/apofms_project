package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.BankDetails_Model;

@Repository
public interface DashboardRepo extends JpaRepository<BankDetails_Model, Integer> {

	@Query(value = "SELECT bankname,branchname,ifsc,banknameaccountno,balance,remaining_balance,accountno,operationaccount,accounttype, accountholder1 ,  accountholder2  ,passbook_path FROM bankdetails where security_id=:security_id", nativeQuery = true)
	List<Map<String, String>> DashBoardBankDetails(String security_id);

}
