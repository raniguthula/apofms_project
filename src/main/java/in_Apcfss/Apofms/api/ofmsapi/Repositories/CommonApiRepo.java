package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.BankDetails_Model;

@Repository
public interface CommonApiRepo extends JpaRepository<BankDetails_Model, Integer> {

	@Query(value = "SELECT distinct dist_name FROM ap_master where dist_code=cast(:dist_code as int)", nativeQuery = true)
	List<Map<String, String>> getDistrict_Name(String dist_code);

	@Query(value = "SELECT display_name,user_id,security_id FROM users where user_id=:user_id", nativeQuery = true)
	List<Map<String, String>> getLoggedUser(String user_id);

	@Query(value = "select bankid,bankfullname,bankshortname from bankmaster where flag='f' order by bankid", nativeQuery = true)
	List<Map<String, String>> getName_Of_The_Bank_DropDown();

	@Query(value = "select (bankname||'-'||accountno)banks,banknameaccountno,remaining_balance,balance from bankdetails  where  is_approved_by_gm='true' order by banknameaccountno", nativeQuery = true)
	List<Map<String, String>> Bank_Name_And_Account_No_DropDown_Man();

	@Query(value = "select (bankname||'-'||accountno)banks,banknameaccountno,remaining_balance,balance from bankdetails  where security_id=:security_id and is_approved_by_gm='true' order by banknameaccountno", nativeQuery = true)
	List<Map<String, String>> Bank_Name_And_Account_No_DropDown(String security_id);

	@Query(value = "select (headid||'-'||headname)as Heads,headid,c.classification_id from mstheads m\r\n"
			+ "left join classification_master c on c.classification_id=m.classification  where payments='t' and flag='f' order by headid", nativeQuery = true)
	List<Map<String, String>> getHeadNames();

	@Query(value = "select (headid||'-'||headname)as Heads,headid,c.classification_id from mstheads m\r\n"
			+ "left join classification_master c on c.classification_id=m.classification  where payments='t' and flag='f' and headid not  like '1%'  order by headid", nativeQuery = true)
	List<Map<String, String>> HeadNamesPayments();

	@Query(value = "select classification,security_id,\r\n"
			+ "case when  classification ='01' then regular+out_sourcing \r\n"
			+ "when classification ='02' then conveyance\r\n" + "when classification ='03' then administative\r\n"
			+ "when classification ='05' then others\r\n" + "when classification ='04' then capital_exp\r\n"
			+ "when classification ='07' then adv_staff\r\n" + "when classification ='08' then liabilities\r\n"
			+ "else 0 \r\n" + "end * 100000 budget\r\n" + ",sum(expen)  expen from budget_limits join (\r\n"
			+ "select classification,pr.security_id security_id ,sum( amount) expen from payments_receipts pr join  payments_receipts_subheads prs   using(payment_receipt_id)\r\n"
			+ "join  mstheads  using(headid)    where   pr.security_id =:security_id and to_char(date,'yyyy-mm') = to_char(current_date,'yyyy-mm')\r\n"
			+ "group by 1,2\r\n" + "union all \r\n"
			+ "select classification, pr.security_id security_id,sum( prs.debit) expen from journal_voucher pr join  journal_voucher_heads prs   using(payment_receipt_id)\r\n"
			+ "join  mstheads  using(headid)    where   pr.security_id =:security_id and to_char(date,'yyyy-mm') = to_char(current_date,'yyyy-mm')\r\n"
			+ "group by 1,2) a  on security_id=code group by 1,2 ,3", nativeQuery = true)
	List<Map<String, String>> ClassificationBudget(String security_id);

//	@Query(value = "select (subheadid||'-'||subheadname)subheads,subheadid,subheadseqid from mstsubheads where headid =:headid and isdelete='f' and security_id=:security_id order by timestamp", nativeQuery = true)
	@Query(value = "select (subheadname||'-'||subheadid)subheads,subheadid,subheadseqid from mstsubheads where headid =:headid and isdelete='f' and security_id=:security_id order by timestamp", nativeQuery = true)
	List<Map<String, String>> getSubHeadNames(String headid, String security_id);

	@Query(value = "select (subheadname||'-'||subheadid)subheads,subheadseqid from mstsubheads where  security_id=:security_id and isdelete='f'", nativeQuery = true)
	List<Map<String, String>> getSubHeadNames_All(String security_id);

	@Query(value = "select (subheadname||'-'||subheadid)subheads,subheadid,subheadseqid from mstsubheads where headid =:headid and isdelete='f' order by timestamp", nativeQuery = true)
	List<Map<String, String>> getSubHeadNames_Apshcl(String headid);

	@Query(value = "select (subheadname||'-'||subheadid)subheads,subheadseqid from mstsubheads where  isdelete='f'", nativeQuery = true)
	List<Map<String, String>> getSubHeadNames_Apshcl_All();
	@Query(value = "select name,code from cgg_master_districts order by code", nativeQuery = true)
	List<Map<String, String>> CGG_DistrictApshcl();

	@Query(value = "select name,code from cgg_master_districts  where code=:security_id order by code", nativeQuery = true)
	List<Map<String, String>> CGG_District(String security_id);

}
