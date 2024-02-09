package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.BankDetails_Model;

@Repository
public interface EntryForms_Repo extends JpaRepository<BankDetails_Model, Integer> {

	@Transactional
	@Modifying

	// Primaru Key:::accountid
	@Query(value = "insert into bankdetails(bankname,branchname,ifsc,accountno,operationaccount,accounttype,accountholder1, \r\n"
			+ " accountholder2,accountholders,balance,userid,banknameaccountno,security_id,remaining_balance,is_approved_by_gm, \r\n"
			+ " ee_entered_date,mandal_account,passbook_path,distid,dh_status,dh_ip) \r\n"
			+ " values(:bankname,:branchname,:ifsc,:accountno,:operationaccount,:accounttype,:accountholder1, \r\n"
			+ "	 :accountholder2,:accountholders,:balance,:user_id,:banknameaccountno,:security_id,:remaining_balance,false,now(),true,:passbook_path,:dist_id,true,:ip_address) ", nativeQuery = true)
	int Post_DM_insertion(String bankname, String branchname, String ifsc, String accountno, String operationaccount,
			String accounttype, String accountholder1, String accountholder2, String accountholders, double balance,
			String user_id, String banknameaccountno, String security_id, double remaining_balance,
			String passbook_path, String dist_id, String ip_address);

	@Transactional
	@Modifying
	// Primaru Key:::accountid
	@Query(value = " insert into bankdetails(bankname,branchname,ifsc,accountno,operationaccount,accounttype,accountholder1, \r\n"
			+ "  accountholder2,accountholders,balance,userid,banknameaccountno,security_id,remaining_balance,ee_id,div_id,is_approved_by_gm,\r\n"
			+ "  ee_entered_date,mandal_account,passbook_path,distid)\r\n"
			+ "  values(:bankname,:branchname,:ifsc,:accountno,:operationaccount,:accounttype,:accountholder1,\r\n"
			+ "   :accountholder2,:accountholders,:balance,:user_id,:banknameaccountno,:security_id,:remaining_balance,:user_id,cast(:div_id as integer),false,now(),true,:passbook_path,:dist_id) ", nativeQuery = true)
	int Post_EE_insertion(String bankname, String branchname, String ifsc, String accountno, String operationaccount,
			String accounttype, String accountholder1, String accountholder2, String accountholders, double balance,
			String user_id, String banknameaccountno, String security_id, double remaining_balance, String div_id,
			String passbook_path, String dist_id);

	@Transactional
	@Modifying
	// Primaru Key:::accountid
	@Query(value = "insert into bankdetails(bankname,branchname,ifsc,accountno,operationaccount,accounttype,accountholder1, \r\n"
			+ " accountholder2,accountholders,balance,userid,banknameaccountno,security_id,remaining_balance,is_approved_by_gm, \r\n"
			+ " ee_entered_date,mandal_account,passbook_path,distid,dh_status,dh_ip) \r\n"
			+ " values(:bankname,:branchname,:ifsc,:accountno,:operationaccount,:accounttype,:accountholder1, \r\n"
			+ "	 :accountholder2,:accountholders,:balance,:user_id,:banknameaccountno,:security_id,:remaining_balance,false,now(),true,:passbook_path,:dist_id,true,:ip_address) ", nativeQuery = true)
	int Post_Apshcl_insertion(String bankname, String branchname, String ifsc, String accountno,
			String operationaccount, String accounttype, String accountholder1, String accountholder2,
			String accountholders, double balance, String user_id, String banknameaccountno, String security_id,
			double remaining_balance, String passbook_path, String dist_id, String ip_address);

	@Transactional
	@Modifying
	@Query(value = "update bankdetails set accountid=:accountid,bankname=:bankname,branchname=:branchname,ifsc=:ifsc,accountno=:accountno,operationaccount=:operationaccount,accounttype=:accounttype,"
			+ "accountholder1=:accountholder1,accountholder2=:accountholder2,accountholders=:accountholders,balance=:balance,manager_id=:user_id,banknameaccountno=:banknameaccountno,"
			+ "remaining_balance=:remaining_balance,passbook_path=:passbook_path,manager_status=true,manager_ip=:ip_address,manager_approvedtime=now() where accountid=:accountid", nativeQuery = true)
	int updatemanager_status(int accountid, String bankname, String branchname, String ifsc, String accountno,
			String operationaccount, String accounttype, String accountholder1, String accountholder2,
			String accountholders, double balance, String user_id, String banknameaccountno, double remaining_balance,
			String passbook_path, String ip_address);

	@Transactional
	@Modifying
	@Query(value = "delete from  bankdetails where banknameaccountno=:banknameaccountno", nativeQuery = true)
	int BankEntryMangerReject(String banknameaccountno);

//Payment_Receipts

//	@Query(value = "SELECT 'R' || scm.security_code || TO_CHAR(current_date, 'ddmmyy') || COALESCE(count_receipts, 0)+1 AS payment_receipt_id\r\n"
//			+ "FROM security_code_master scm LEFT JOIN (SELECT  security_id, COUNT(payment_receipt_id) AS count_receipts FROM payments_receipts GROUP BY security_id) pr ON scm.security_code = pr.security_id\r\n"
//			+ "WHERE scm.security_code =:security_id", nativeQuery = true)
	@Query(value = "SELECT  'R'||security_code||to_char(current_date,'ddmmyy')||  count(payment_receipt_id)+1  as payment_receipt_id from \r\n"
			+ "security_code_master \r\n"
			+ "left join ( select * from payments_receipts where date(timestamp) =current_date and payment_receipt_id like 'R%') p  on security_code =security_id\r\n"
			+ "where security_code=:security_id    group by security_code,current_date", nativeQuery = true)
	List<Map<String, String>> getReceipt_Id(String security_id);

//	@Query(value = "SELECT 'P' || scm.security_code || TO_CHAR(current_date, 'ddmmyy') || COALESCE(count_receipts, 0)+1 AS payment_receipt_id\r\n"
//			+ "FROM security_code_master scm LEFT JOIN (SELECT  security_id, COUNT(payment_receipt_id) AS count_receipts FROM journal_voucher GROUP BY security_id) pr ON scm.security_code = pr.security_id\r\n"
//			+ "WHERE scm.security_code =:security_id", nativeQuery = true)
	@Query(value = "SELECT  'P'||security_code||to_char(current_date,'ddmmyy')||  count(payment_receipt_id)+1  as payment_receipt_id from \r\n"
			+ "security_code_master \r\n"
			+ "left join ( select * from payments_receipts where date(timestamp) =current_date and payment_receipt_id like 'P%') p  on security_code =security_id\r\n"
			+ "where security_code=:security_id     group by security_code,current_date", nativeQuery = true)
	List<Map<String, String>> getPayment_Id(String security_id);

//
//	@Query(value = "SELECT 'J' || scm.security_code || TO_CHAR(current_date, 'ddmmyy') || COALESCE(count_receipts, 0)+1 AS payment_receipt_id\r\n"
//			+ "FROM security_code_master scm LEFT JOIN (SELECT  security_id, COUNT(payment_receipt_id) AS count_receipts FROM journal_voucher GROUP BY security_id) pr ON scm.security_code = pr.security_id\r\n"
//			+ "WHERE scm.security_code =:security_id", nativeQuery = true)
	@Query(value = "SELECT  'J'||security_code||to_char(current_date,'ddmmyy')||  count(payment_receipt_id)+1  as payment_receipt_id from \r\n"
			+ "security_code_master \r\n"
			+ "left join ( select * from journal_voucher where date(timestamp) =current_date and payment_receipt_id like 'J%') p  on security_code =security_id\r\n"
			+ "where security_code=:security_id     group by security_code,current_date", nativeQuery = true)
	List<Map<String, String>> getJournal_Id(String security_id);

	@Transactional
	@Modifying
	// Primaru Key:::payment_receipt_id
	@Query(value = "INSERT INTO public.payments_receipts(payment_receipt_id, receiptno, date, cash_bank, banknameaccountno, paymentamount, receiptamount, balance_in_account, name, mode, cheque_dd_receipt_no, receipt_description, security_id, transaction_type, no_of_subheads , timestamp, user_id,upload_copy,dh_status,dh_ip) \r\n"
			+ "    VALUES(:payment_receipt_id, :receiptno,:date, :cash_bank, :banknameaccountno, 0, cast(:receiptamount as numeric), cast(:balance_in_account as numeric), :name, :mode, :cheque_dd_receipt_no, :receipt_description, :security_id, 'R', :no_of_subheads, now(),  :user_id,:upload_copy,true,:ip_address)", nativeQuery = true)

	int receipt_entry_insertion(String payment_receipt_id, String receiptno, Date date, String cash_bank,
			String banknameaccountno, double receiptamount, double balance_in_account, String name, String mode,
			String cheque_dd_receipt_no, String receipt_description, String security_id, String no_of_subheads,
			String user_id, String upload_copy, String ip_address);

	@Transactional
	@Modifying
	// Primaru Key:::payment_receipt_id
	@Query(value = "INSERT INTO public.payments_receipts(payment_receipt_id, receiptno, date, cash_bank, banknameaccountno, paymentamount, receiptamount, balance_in_account, name, mode, cheque_dd_receipt_no, receipt_description, security_id, transaction_type, no_of_subheads , timestamp, user_id,upload_copy,dh_status,dh_ip) \r\n"
			+ "    VALUES(:payment_receipt_id, :receiptno,:date, :cash_bank, :banknameaccountno, cast(:paymentamount as numeric), 0, cast(:balance_in_account as numeric), :name, :mode, :cheque_dd_receipt_no, :receipt_description, :security_id, 'P', :no_of_subheads, now(),  :user_id,:upload_copy,true,:ip_address)", nativeQuery = true)
	int payment_entry_insertion(String payment_receipt_id, String receiptno, Date date, String cash_bank,
			String banknameaccountno, double paymentamount, double balance_in_account, String name, String mode,
			String cheque_dd_receipt_no, String receipt_description, String security_id, String no_of_subheads,
			String user_id, String upload_copy, String ip_address);

	@Transactional
	@Modifying
	// Primaru Key:::payment_receipt_id, headid, subheadseqid
	@Query(value = "INSERT INTO public.payments_receipts_subheads(payment_receipt_id, headid, subheadseqid, amount, security_id, timestamp, complete_save) \r\n"
			+ "    VALUES(:payment_receipt_id, :headid, :subheadseqid, :paymentamount, :security_id, now(), true)", nativeQuery = true)

	int payments_receipts_subheads_insertion(String payment_receipt_id, String headid, String subheadseqid,
			long paymentamount, String security_id);

	@Query(value = "SELECT nextval('subheadid_seq') AS subheadseqid", nativeQuery = true)
	String getsubheadseqid();

	@Transactional
	@Modifying
	// Primaru Key:::payment_receipt_id, headid, subheadseqid
	@Query(value = "insert into dummy_journal_voucher_heads(payment_receipt_id,jvslno,headid,subheadid,debit,credit,security_id,timestamp,dh_status,dh_id,dh_ip)\r\n"
			+ "values(:payment_receipt_id,(select max(jvslno)+1 from dummy_journal_voucher_heads), :headid, :subheadid, 0.00, :credit, :security_id,now(),true,:user_id,:ip_address)", nativeQuery = true)
	int dummy_head_entry_insertion_receipt(String payment_receipt_id, String headid, String subheadid, double credit,
			String security_id, String user_id, String ip_address);

	@Transactional
	@Modifying
	// Primaru Key:::payment_receipt_id, headid, subheadseqid
	@Query(value = "insert into dummy_journal_voucher_heads(payment_receipt_id,jvslno,headid,subheadid,debit,credit,security_id,timestamp,dh_status,dh_id,dh_ip)\r\n"
			+ "values(:payment_receipt_id,(select max(jvslno)+1 from dummy_journal_voucher_heads), :headid, :subheadid,  :debit, 0.00,:security_id,now(),true,:user_id,:ip_address)", nativeQuery = true)
	int dummy_head_entry_insertion_payment(String payment_receipt_id, String headid, String subheadid, double debit,
			String security_id, String user_id, String ip_address);

	@Transactional
	@Modifying
	// Primaru Key:::payment_receipt_id, headid, subheadseqid
	@Query(value = "insert into payments_receipts_subheads (payment_receipt_id,headid,subheadseqid,amount,security_id,complete_save,timestamp,dh_status,dh_id,dh_ip)\r\n"
			+ "values(:payment_receipt_id, :headid, :subheadid, :credit, :security_id, true,now(),true,:user_id,:ip_address)", nativeQuery = true)
	int receipts_subheads(String payment_receipt_id, String headid, String subheadid, double credit, String security_id,
			String user_id, String ip_address);

	@Transactional
	@Modifying
	// Primaru Key:::payment_receipt_id, headid, subheadseqid
	@Query(value = "insert into payments_receipts_subheads (payment_receipt_id,headid,subheadseqid,amount,security_id,complete_save,timestamp,dh_status,dh_id,dh_ip)\r\n"
			+ "values(:payment_receipt_id, :headid, :subheadid, :debit, :security_id, true,now(),true,:user_id,:ip_address)", nativeQuery = true)
	int payments_subheads(String payment_receipt_id, String headid, String subheadid, double debit, String security_id,
			String user_id, String ip_address);

	@Transactional
	@Modifying
	// Primaru Key:::payment_receipt_id, headid, subheadseqid
	@Query(value = "INSERT INTO payments_receipts_incomplete(payment_receipt_id,headid,subheadseqid,amount,security_id) \r\n"
			+ "(select payment_receipt_id,headid,subheadid,credit,security_id from dummy_journal_voucher_heads where\r\n"
			+ " payment_receipt_id=:payment_receipt_id and headid=:headid and subheadid=:subheadid)", nativeQuery = true)
	int payments_receipts_incomplete(String payment_receipt_id, String headid, String subheadid);

	@Transactional
	@Modifying
	// //Back Up Table ::::bankdetails_crud(added new columns()
	@Query(value = " update bankdetails set remaining_balance=cast(:remaining_balance as numeric) where banknameaccountno=:banknameaccountno ", nativeQuery = true)
	int updatebankdetails(double remaining_balance, String banknameaccountno);

	@Transactional
	@Modifying
	@Query(value = "UPDATE bankdetails SET remaining_balance = :remaining_balance WHERE banknameaccountno = :banknameaccountno AND security_id = :security_id", nativeQuery = true)
	int updatebankdetailssec(double remaining_balance, String banknameaccountno, String security_id);

	@Transactional
	@Modifying
	// Back Up Table ::: dummy_journal_voucher_heads_crud (added new coumns)
	@Query(value = "delete from dummy_journal_voucher_heads where payment_receipt_id=:payment_receipt_id ", nativeQuery = true)
	int deletedummy(String payment_receipt_id);

	// Journal Vocher
	@Transactional
	@Modifying
	// PrimarY Key:::payment_receipt_id,
	@Query(value = "insert into journal_voucher(payment_receipt_id,date,voucher_id,description,credit,debit,upload_copy,security_id, is_confirm,dh_status,dh_ip,dh_id)"
			+ " values(:payment_receipt_id,:date,:voucher_id,:description,cast(:credit as numeric),cast(:debit as numeric),:upload_copy,:security_id,true,true,:ip_address,:user_id)", nativeQuery = true)
	int journal_entry_insertion(String payment_receipt_id, Date date, String voucher_id, String description,
			double credit, double debit, String upload_copy, String security_id, String ip_address, String user_id);

	@Transactional
	@Modifying
	// PrimarY Key:::payment_receipt_id, headid, subheadseqidpayment_receipt_id,
	// jvslno
	@Query(value = "insert into journal_voucher_heads(payment_receipt_id,jvslno,headid,subheadid,credit,debit,security_id,complete_save,dh_status,dh_ip,dh_id) "
			+ "values(:payment_receipt_id,(select max(jvslno)+1 from journal_voucher_heads),:headid,:subheadid,:credit,:debit,:security_id,true,true,:ip_address,:user_id)", nativeQuery = true)
	int journal_voucher_heads(String payment_receipt_id, String headid, String subheadid, double credit, double debit,
			String security_id, String user_id, String ip_address);

	@Transactional
	@Modifying
	//// Primaru Key::: (payment_receipt_id, jvslno, headid, subheadseqid)
	@Query(value = " insert into jv_incomplete(payment_receipt_id,jvslno,headid,subheadseqid,debit,credit,security_id,timestamp) "
			+ "(select payment_receipt_id,(select max(jvslno)+1 from journal_voucher_heads),headid,subheadid,debit,credit,security_id,timestamp "
			+ "from dummy_journal_voucher_heads where headid=:headid and payment_receipt_id=:payment_receipt_id )", nativeQuery = true)
	int jv_incomplete(String payment_receipt_id, String headid);

	@Transactional
	@Modifying
	// Back Up Table ::: dummy_journal_voucher_heads_crud (added new coumns)
	@Query(value = "delete from dummy_journal_voucher_heads where headid=:headid and payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int dummy_journal_voucher_heads(String payment_receipt_id, String headid);

	// **************************Cadre Wise Employees*************************
	@Query(value = "select  dist_name, empname,empid as empid, to_char(dob,'dd/mm/yyyy') as dob ,dist_name as distname,  \r\n"
			+ "to_char(dor,'dd/mm/yyyy') as dor, (select designation_name from designation where a.designation=designation_id) as des,\r\n"
			+ "case when  empid is null then 'NODATA' else 'DATAFOUND' end as empdatacheck from  empdetails_new a \r\n"
			+ "left join(select dist_code,dist_name from ap_master  group by  dist_code,dist_name ) ap  on(ap.dist_code = prdist  )   \r\n"
			+ "where dist_code =cast(:dist_code as integer) and emp_status_id= 1 order by empid", nativeQuery = true)
	List<Map<String, String>> getCadreWiseData(String dist_code);

	@Query(value = "select  empname, empid, de.designation_name, empfather , cr.caste_name  as caste, dob, doj, dor,\r\n"
			+ "coalesce(ap1.dist_name,'prdist_other') as prdist,coalesce((select distinct div_name from ap_master where dist_code=prdist \r\n"
			+ "and div_code=prdiv),prdiv_other) as prdiv,coalesce(ap1.sdiv_name,prsubdiv_other) as prsdiv,\r\n"
			+ "coalesce(ap1.mandal_name,prmandal_other) as prmandal,\r\n"
			+ "coalesce((select md.name from cgg_master_districts md where code= lpad(cast(natdist as text),2,'0')) ,natdist_other) as natdist,\r\n"
			+ "coalesce((select distinct div_name from ap_master where dist_code=natdist and div_code=natdiv),natdiv_other) as natdiv,\r\n"
			+ "coalesce(ap2.sdiv_name,natsdiv_other) as natsdiv,coalesce(ap2.mandal_name,natmandal_other)  as natmandal ,et.emptype_name as prwork,\r\n"
			+ "otherwork as otherdatabasic, othercase as displinary, case when otherworkarea='0' or otherworkarea is  null  then '-' \r\n"
			+ "else cast(otherworkarea as text) end   as otherdatabasics, \r\n"
			+ "dowpc,qms.qualificationname as eduappoint, qmst.qualificationname||', Other Qualifications:'||coalesce(other_qualifications,'') as \r\n"
			+ "eduafterappoint, cadreappoint, cadrerefno, cadrerefdate, empaadhar, emppan, emppfuno ,prdiv as prdivcode,prsubdiv  as prsubdivcode,\r\n"
			+ "prsubmandal as prsubmandalcode from empdetails_new em \r\n"
			+ "left join (select dist_code,dist_name,div_code,div_name,sdiv_code,sdiv_name,mandal_code,mandal_name from ap_master  \r\n"
			+ "group by  dist_code,dist_name,div_code,div_name,sdiv_code,\r\n"
			+ "sdiv_name,mandal_code,mandal_name) ap1 on (ap1.dist_code = em.prdist  and ap1.mandal_code=em.prsubmandal) \r\n"
			+ "left join (select dist_code,dist_name,div_code,div_name,cont_code as sdiv_code,\r\n"
			+ "cont_name as sdiv_name,mandal_code,mandal_name from ap_master  \r\n"
			+ "group by  dist_code,dist_name,div_code,div_name,cont_code,cont_name,mandal_code,mandal_name) ap2 on (ap2.dist_code = em.natdist \r\n"
			+ "and ap2.mandal_code=em.natmandal) left join caste_master cr on cr.caste_id=em.caste \r\n"
			+ "left join designation de on de.designation_id=em.designation\r\n"
			+ "left join employee_type_inception et on et.emptype_id=cast(em.prwork as integer)  \r\n"
			+ "left join qualificationmaster qms on qms.qualificationid=cast(em.eduappoint as integer)  \r\n"
			+ "left join qualificationmaster qmst on qmst.qualificationid=cast(em.eduafterappoint as integer) \r\n"
			+ "where empid=:empid", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDetailsByEmpID(String empid);

	@Query(value = "select empname, empid , de.designation_name  as fromcadre, fromdate,fromref ,dep.designation_name as tocadre,todate,toref \r\n"
			+ "from empdetails_new_promotions ep  \r\n"
			+ "left join designation de on cast(ep.fromcadre as integer)=de.designation_id  \r\n"
			+ "left join designation dep on  cast(ep.tocadre as integer)=dep.designation_id where empid=:empid order by fromdate desc", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDetailsByEmpID_SubsequentPromotions(String empid);

	@Query(value = "select displanary as dis,discase as discase from empdetails_new_disp where empid=:empid", nativeQuery = true)
	List<Map<String, String>> getDisExp_PresentConditon(String empid);

	@Query(value = "select coalesce(coalesce(ap.dist_name,incdist_other),incdist) as indist,coalesce(coalesce(ap.mandal_name,incmandal_other),incmandal) \r\n"
			+ "as inmandal, incfrom,incto,coalesce(de.designation_name, incdesign) as desname ,et.emptype_name as emptype from empdetails_new_incept em \r\n"
			+ "left join (select dist_code,dist_name,mandal_code,mandal_name from ap_master  group by  dist_code,dist_name,mandal_code,mandal_name) ap on \r\n"
			+ "( ap.dist_code||''=em.incdist    and ap.mandal_code||''=em.incmandal )   left join designation de on de.designation_id||''=em.incdesign   \r\n"
			+ " left join employee_type_inception et on et.emptype_id=cast(em.incemptype as integer) where empid=:empid order by incfrom desc", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDetailsByEmpID_POWI(String empid);

	@Query(value = "select  empname as empname1, empid, designation, empfather as fathername , cr.caste_name  as caste_name, caste, to_char(dob,'dd/mm/yyyy') as dob, to_char(doj,'dd/mm/yyyy') as doj, to_char(dor,'dd/mm/yyyy') as dor,ap1.dist_name as prdist,ap1.div_name as prdiv,\r\n"
			+ "coalesce(prdiv, ap1.div_code) as division2, coalesce(prsubdiv, ap1.sdiv_code) as sdiv2, \r\n"
			+ "coalesce(prsubmandal, ap1.mandal_code) as mandals2, ap1.sdiv_name as prsdiv,\r\n"
			+ "ap1.mandal_name as prmandal,\r\n" + "ap2.dist_name as natdist, ap2.div_name as natdiv,\r\n"
			+ "ap2.sdiv_name as natsdiv,ap2.mandal_name  as natmandal,  coalesce(ap2.dist_code,natdist) as districtss, \r\n"
			+ "coalesce(ap2.div_code,natdiv) as division, \r\n"
			+ "coalesce(ap2.sdiv_code,natsdiv) as sdiv, coalesce(ap2.mandal_code,natmandal) as mandal, \r\n"
			+ "et.emptype_name as prwork1, prwork as workcond, otherwork as otherdatabasic,othercase as displinary,otherworkarea as otherdatabasics, dowpc,qms.qualificationname as eduappoint1, eduappoint, qmst.qualificationname as eduafterappoint1, eduafterappoint, cadreappoint, cadrerefno, \r\n"
			+ "cadrerefdate, empaadhar, emppan, emppfuno, prdist_other , prdiv_other as other_division, prsubdiv_other  as other_sub_division, prmandal_other as other_mandal,natdist_other as other_district2,natdiv_other as other_division2,natsdiv_other as other_sub_division2,natmandal_other as other_mandal2,\r\n"
			+ " prdist as prdistcode,    prdiv as prdivcode,     prsubdiv as prsubdiccode,     prsubmandal as prsubmandalcode,    natdist  as natdistcode,\r\n"
			+ "    natdiv as natdivcode,     natsdiv  as natsdivcode,     natmandal as natmandalcode,other_qualifications as otherqualifications \r\n"
			+ "from empdetails_new em  left join (select dist_code,dist_name,div_code,div_name,sdiv_code,sdiv_name,mandal_code,mandal_name from ap_master \r\n"
			+ " group by  dist_code,dist_name,div_code,div_name,sdiv_code, sdiv_name,mandal_code,mandal_name) ap1 on (ap1.dist_code = em.prdist  and ap1.mandal_code=em.prsubmandal) \r\n"
			+ "left join (select dist_code,dist_name,div_code,div_name,cont_code as sdiv_code, cont_name as sdiv_name,mandal_code,mandal_name from ap_master  group by  dist_code,\r\n"
			+ "dist_name,div_code,div_name,cont_code,cont_name,mandal_code,mandal_name) ap2 on  (ap2.dist_code = em.natdist and ap2.div_code=em.natdiv  and ap2.mandal_code=em.natmandal) left join caste_master cr\r\n"
			+ " on cr.caste_id=em.caste left join designation de on de.designation_id=em.designation left join employee_type_inception et on et.emptype_id=cast(em.prwork as int4) \r\n"
			+ "left join qualificationmaster qms on qms.qualificationid=cast(em.eduappoint as int4)  left join qualificationmaster qmst on qmst.qualificationid=cast(em.eduafterappoint as int4)\r\n"
			+ " where empid=:empid", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID(String empid);

	@Query(value = " select sno, empname, empid , de.designation_name  as fromcadre, fromdate,fromref ,dep.designation_name as tocadre,todate,toref from empdetails_new_promotions ep  \r\n"
			+ "left join designation de on cast(COALESCE(ep.fromcadre,'0') as int4)=de.designation_id  \r\n"
			+ "left join designation dep on  cast(COALESCE(ep.tocadre,'0') as int4)=dep.designation_id where empid=:empid order by fromdate desc", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_SubsequentPromotions(String empid);

	@Query(value = "select sno, empid, case when incdist='999' then incdist_other else coalesce(ap.dist_name,incdist) end as indist,\r\n"
			+ "case when incmandal='999' then incmandal_other else coalesce(ap.mandal_name,incmandal) end as inmandal, \r\n"
			+ "incfrom,incto,coalesce(de.designation_name, incdesign) as desname ,et.emptype_name as empname\r\n"
			+ "from empdetails_new_incept em left join  \r\n"
			+ "(select dist_code,dist_name,mandal_code,mandal_name from ap_master  group by  dist_code,dist_name,mandal_code,mandal_name) ap on \r\n"
			+ "( ap.dist_code||''=em.incdist    and ap.mandal_code||''=em.incmandal )   left join designation de on de.designation_id||''=em.incdesign   \r\n"
			+ "left join employee_type_inception et on et.emptype_id=cast(em.incemptype as int4) where empid=:empid order by sno desc ", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_POWI(String empid);

	@Query(value = "select * from empdetails_new_disp where empid=:empid", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_Disp(String empid);

	@Query(value = "select caste_id, caste_name from caste_master", nativeQuery = true)
	List<Map<String, String>> getCadreWiseCasteDropdown();

	@Query(value = "select distinct dist_code,dist_name from ap_master order by dist_code", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDistDropdown();

	@Query(value = "select distinct div_code,div_name from ap_master where dist_code=:dist_code order by div_code", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDivDropdown(int dist_code);

	@Query(value = "select distinct sdiv_code,sdiv_name from ap_master where dist_code=:dist_code "
			+ "and div_code=:div_code order by sdiv_code", nativeQuery = true)
	List<Map<String, String>> getCadreWiseSubDivDropdown(int dist_code, int div_code);

	@Query(value = "select distinct mandal_code,mandal_name from ap_master where dist_code=:dist_code "
			+ "and div_code=:div_code and sdiv_code=:sdiv_code order by mandal_code", nativeQuery = true)
	List<Map<String, String>> getCadreWiseMandalDropdown(int dist_code, int div_code, int sdiv_code);

	@Query(value = "select designation_id,designation_name from designation where designation_id not in (1,2) "
			+ "order by designation_name", nativeQuery = true)
	List<Map<String, String>> getCadreWiseDesignationDropdown();

	@Query(value = "select  emptype_id,emptype_name from employee_type_inception order by emptype_id", nativeQuery = true)
	List<Map<String, String>> getCadreWiseWorkConditionDropdown();

	@Query(value = "select qualificationid , qualificationname   from qualificationmaster order by qualificationid", nativeQuery = true)
	List<Map<String, String>> getCadreWiseQualificationDropdown();

	@Query(value = "SELECT distinct cadreappoint FROM empdetails_new order by cadreappoint", nativeQuery = true)
	List<Map<String, String>> getCadreWiseAppointmentDropdown();

	@Transactional
	@Modifying
	// Back Up table :::::empdetails_new_crud (by Rani) (Add new colomns)
	@Query(value = "update empdetails_new set empname=:empname, empfather=:empfather,designation=:designation,\r\n"
			+ "caste=:caste, dob=to_date(cast(:dob as text),'dd/mm/yyyy'), doj=to_date(cast(:doj as text),'dd/mm/yyyy'), \r\n"
			+ "dor=to_date(cast(:dor as text),'dd/mm/yyyy'), prdist=:prdist, prdiv=:prdiv, prsubdiv=:prsdiv, \r\n"
			+ "prsubmandal=:prmandal, prwork=:workcond, \r\n"
			+ "natdist=:natdist, natdiv=:natdiv, natsdiv=:natsdiv, natmandal=:natmandal, \r\n"
			+ "otherwork=:otherwork, othercase=:othercase, otherworkarea=:otherworkarea,\r\n"
			+ "dowpc=:dojpresent, eduappoint=:eduappoint, eduafterappoint=:eduafterappoint,\r\n"
			+ "cadreappoint=:cadreappoint, cadrerefno=:cadrerefno, cadrerefdate=:cadrerefdate, \r\n"
			+ "empaadhar=:empaadhar,emppan=:emppan,emppfuno=:emppfuno,\r\n"
			+ " prdiv_other=:prdiv_other,prsubdiv_other=:prsubdiv_other,prmandal_other=:prmandal_other,\r\n"
			+ "natdist_other=:natdist_other,natdiv_other=:natdiv_other,natsdiv_other=:natsdiv_other,natmandal_other=:natmandal_other\r\n"
			+ "  , other_qualifications=:other_qualifications where empid=:empid ", nativeQuery = true)

	int update_empdetails_new(String empid, String empname, String empfather, int designation, int caste, String dob,
			String doj, String dor, int prdist, int prdiv, int prsdiv, int prmandal, String workcond, int natdist,
			int natdiv, int natsdiv, int natmandal, String otherwork, String othercase, String otherworkarea,
			String dojpresent, String eduappoint, String eduafterappoint, String cadreappoint, String cadrerefno,
			String cadrerefdate, String empaadhar, String emppan, String emppfuno, String prdiv_other,
			String prsubdiv_other, String prmandal_other, String natdist_other, String natdiv_other,
			String natsdiv_other, String natmandal_other, String other_qualifications);

	@Transactional
	@Modifying
////Primaru Key:::sno(by Rani)
	// Backup TABle empdetails_new_promotions_crud

	@Query(value = "INSERT INTO public.empdetails_new_promotions( empname, empid, fromcadre, fromdate, fromref, tocadre, todate, toref)  \r\n"
			+ "values(:empname, :empid, :fromcadre, TO_DATE(:fromdate, 'dd-mm-yyyy'), :fromref, :tocadre, TO_DATE(:todate, 'dd-mm-yyyy'), :toref)", nativeQuery = true)
	int Edit_Subsequent_Promotion(String empid, String empname, String fromcadre, String fromdate, String fromref,
			String tocadre, String todate, String toref);

	@Transactional
	@Modifying
////Primaru Key:::sno(by Rani)
	// me add 2 columns (incdiv,incsdiv)s
	@Query(value = "INSERT INTO public.empdetails_new_incept(empname,empid,incdist,incmandal,incdesign,incemptype,incfrom,incto,incdist_other,incmandal_other,incdiv,incsdiv)\r\n"
			+ "values(:empname,:empid,:incdist,:incmandal,:incdesign,:incemptype,:incfrom,:incto,:incdist_other,:incmandal_other,:incdiv,:incsdiv)", nativeQuery = true)
	int Edit_Placeofworkingfrominception_Add(String empid, String empname, String incdist, String incmandal,
			String incdesign, String incemptype, String incfrom, String incto, String incdist_other,
			String incmandal_other, String incdiv, String incsdiv);

	@Transactional
	@Modifying
////Primaru Key:::sno(by me)
	@Query(value = "INSERT INTO public.empdetails_new_disp(empname,empid,displanary,discase)"
			+ "values(:empname,:empid,:displanary,:discase)", nativeQuery = true)
	int Edit_Other_Basic_Data_Add(String empid, String empname, String displanary, String discase);

	@Modifying
	@Transactional
////Primaru Key:::sno(by me)
	@Query(value = "insert into empdetails_new_incept_deleted (deleted_user,deleted_time,deleted_ip,sno,empname,empid,incdist,incmandal,incdesign,incemptype,incfrom,incto,incdist_other,incmandal_other)\r\n"
			+ "select :user_id as deleted_user, now() as deleted_time, :ip_address as deleted_ip, sno,empname,empid,incdist,incmandal,incdesign,incemptype,incfrom,incto,incdist_other,incmandal_other from empdetails_new_incept \r\n"
			+ "where sno=:sno and empid=:empid ", nativeQuery = true)
	int empdetails_new_incept_deleted(String empid, String ip_address, String user_id, int sno);

	@Modifying
	@Transactional
	// Back_UP Table empdetails_new_incept_crud (add new columns)
	@Query(value = "delete from empdetails_new_incept where sno=:sno and empid=:empid ", nativeQuery = true)
	int delete_empdetails_new_incept(int sno, String empid);

	@Modifying
	@Transactional
////Primaru Key:::sno(by me)
	@Query(value = "insert into empdetails_new_promotions_deleted \r\n"
			+ "select :user_id as deleted_user, now() as deleted_time, :ip_address as deleted_ip, \r\n"
			+ " * from empdetails_new_promotions where sno=:sno and empid=:empid ", nativeQuery = true)
	int insert_empdetails_new_promotions_deleted(String empid, String ip_address, String user_id, int sno);

	@Modifying
	@Transactional
	// Back Up Table empdetails_new_promotions_crud (add new coulmsn)
	@Query(value = "delete from empdetails_new_promotions where sno=:sno and empid=:empid ", nativeQuery = true)
	int empdetails_new_promotions(int sno, String empid);

	@Modifying
	@Transactional
////Primaru Key:::sno(by me)
	@Query(value = "insert into empdetails_new_disp_deleted \r\n"
			+ "select :user_id as deleted_user, now() as deleted_time, :ip_address as deleted_ip, \r\n"
			+ " * from empdetails_new_promotions where sno=:sno and empid=:empid", nativeQuery = true)
	int insert_empdetails_new_disp_deleted(String empid, String ip_address, String user_id, int sno);

	@Modifying
	@Transactional
	// Back Up Table empdetails_new_disp_crud (add new coulmsn)
	@Query(value = "delete from empdetails_new_disp where sno=:sno and empid=:empid ", nativeQuery = true)
	int empdetails_new_disp(int sno, String empid);

	@Query(value = "(select max(cast(empid as numeric))+1 as empid from empdetails_new )", nativeQuery = true)
	String get_emp_id();

	@Modifying
	@Transactional
//	no_of_the_bank,ifsc_code,account_no,(ADD THESE THREEE COLUMNS FOR CLIENT REQUIREMENT)
////Primaru Key:::sno(by me)
	@Query(value = "INSERT INTO public.empdetails_new(empname, empid, empfather,designation, caste, dob, doj, dor, \r\n"
			+ "prdist, prdiv, prsubdiv, prsubmandal, prwork, natdist, natdiv, natsdiv, natmandal, otherwork, \r\n"
			+ "othercase, otherworkarea,dowpc, eduappoint, eduafterappoint, cadreappoint, cadrerefno, cadrerefdate,\r\n"
			+ " empaadhar, emppan, emppfuno,prdiv_other,prsubdiv_other,prmandal_other, natdist_other, natdiv_other, \r\n"
			+ "natsdiv_other,natmandal_other,other_qualifications,no_of_the_bank,account_no,ifsc_code)   \r\n"
			+ " VALUES(:empname, :max_emp_id,:empfather,:designation,:caste, to_date(:dob,'dd/mm/yyyy'), to_date(:doj,'dd/mm/yyyy'), \r\n"
			+ "to_date(:dor,'dd/mm/yyyy'), :prdist, :prdiv, :prsdiv, :prmandal, :workcond, :natdist,:natdiv, :natsdiv, :natmandal,\r\n"
			+ " :otherwork, :othercase, :otherworkarea, :dojpresent, :eduappoint, :eduafterappoint, :cadreappoint, :cadrerefno,:cadrerefdate,\r\n"
			+ " :empaadhar, :emppan, :emppfuno, :prdiv_other, :prsubdiv_other, :prmandal_other, :natdist_other, :natdiv_other, :natsdiv_other,\r\n"
			+ " :natmandal_other, :other_qualifications ,:no_of_the_bank,:account_no,:ifsc_code)", nativeQuery = true)
	int Add_button_insert_cadreWise(String max_emp_id, String empname, String empfather, int designation, int caste,
			String dob, String doj, String dor, int prdist, int prdiv, int prsdiv, int prmandal, String workcond,
			int natdist, int natdiv, int natsdiv, int natmandal, String otherwork, String othercase,
			String otherworkarea, String dojpresent, String eduappoint, String eduafterappoint, String cadreappoint,
			String cadrerefno, String cadrerefdate, String empaadhar, String emppan, String emppfuno,
			String prdiv_other, String prsubdiv_other, String prmandal_other, String natdist_other, String natdiv_other,
			String natsdiv_other, String natmandal_other, String other_qualifications, String no_of_the_bank,
			String account_no, String ifsc_code);

	@Query(value = "select max(cast(empid as numeric))+1  as empid from empdetails_new", nativeQuery = true)
	String AddNewEmpid();

//Inter Bank Transfer
//Have to check tables
	@Transactional
	@Modifying
	// Primary key (payment_receipt_id)
	@Query(value = "insert into payments_receipts(payment_receipt_id,receiptno,name,no_of_subheads,cash_bank,date,banknameaccountno,balance_in_account,\r\n"
			+ "	paymentamount,receiptamount,mode,cheque_dd_receipt_no,receipt_description,security_id,transaction_type,user_id,upload_copy) \r\n"
			+ " VALUES(:payment_id, :receiptno, 'InterTransfer','IBT','Bank',:date, :banknameaccountno, "
			+ "cast(:balance_in_account as numeric), cast(:paymentamount as numeric),0, :mode, :cheque_dd_receipt_no, :receipt_description, :security_id, 'P', :user_id,:upload_copy)", nativeQuery = true)
	int InterBankTransferInsertion_Payment(String payment_id, String receiptno, Date date, String banknameaccountno,
			double balance_in_account, double paymentamount, String mode, String cheque_dd_receipt_no,
			String receipt_description, String security_id, String user_id, String upload_copy);

	@Transactional
	@Modifying
	// Primary key (payment_receipt_id)
	@Query(value = "insert into payments_receipts(payment_receipt_id,receiptno,name,no_of_subheads,cash_bank,date,banknameaccountno,balance_in_account,\r\n"
			+ "	paymentamount,receiptamount,mode,cheque_dd_receipt_no,receipt_description,security_id,transaction_type,user_id) \r\n"
			+ " VALUES(:receipt_id, :receiptno, 'InterTransfer','IBT','Bank',:date, :banknameaccountno1, "
			+ "cast(:balance_in_account1 as numeric),0, cast(:receiptamount as numeric), :mode, :cheque_dd_receipt_no, :receipt_description, :security_id, 'R', :user_id)", nativeQuery = true)
	int InterBankTransferInsertion_Receipt(String receipt_id, String receiptno, Date date, String banknameaccountno1,
			double balance_in_account1, double receiptamount, String mode, String cheque_dd_receipt_no,
			String receipt_description, String security_id, String user_id);

	@Transactional
	@Modifying
	// primary key (payment_receipt_id, receipt_id)
	@Query(value = "insert into interbankingmapping (payment_receipt_id,receipt_id) values(:payment_id,:receipt_id)", nativeQuery = true)
	int insertrelation(String payment_id, String receipt_id);

	@Transactional
	@Modifying
	// primary key accountid)
	@Query(value = "update bankdetails set remaining_balance=:frombankbalancedouble where banknameaccountno=:banknameaccountno", nativeQuery = true)
	int InterBankTransferInsertion_bank(double frombankbalancedouble, String banknameaccountno);

	@Transactional
	@Modifying
	// primary key accountid)
	@Query(value = "update bankdetails set remaining_balance=:tobankbalancedouble where banknameaccountno=:banknameaccountno1", nativeQuery = true)
	int InterBankTransferInsertion_bank1(double tobankbalancedouble, String banknameaccountno1);

	@Query(value = "SELECT subheadname,subheadid,subheadseqid,security_id,headid FROM mstsubheads  where headid=:headid and security_id=:security_id  and isdelete='f' ", nativeQuery = true)
	List<Map<String, String>> GetSubHeadsData(String headid, String security_id);

//Sub Head Mater
	@Query(value = "SELECT headid FROM mstsubheads where subheadid=cast(:subheadid as varchar) and subheadname=cast(:subheadname as varchar)", nativeQuery = true)
	String getheadId(String subheadid, String subheadname);

	@Transactional
	@Modifying
//primary key (subheadseqid)
	@Query(value = "insert into mstsubheads (subheadseqid,subheadid,subheadname,headid,security_id,timestamp)\r\n"
			+ "values(:subheadseqid ,:subheadid,:subheadname,:headid,:security_id,now())", nativeQuery = true)
	int insertSubHeads(String subheadseqid, String subheadid, String subheadname, String headid, String security_id);

	@Transactional
	@Modifying
//primary key (subheadseqid)
	@Query(value = "update mstsubheads set subheadname=:subheadname, subheadid=:subheadid  where  subheadseqid=:subheadseqid and security_id=:security_id", nativeQuery = true)
	int updateSubheads(String subheadname, String subheadid, String subheadseqid, String security_id);

	@Transactional
	@Modifying
//primary key (subheadseqid)
	@Query(value = "update mstsubheads set isdelete='t' where subheadseqid=cast(:subheadseqid as varchar) and headid=cast(:headid as varchar) and security_id=:security_id", nativeQuery = true)
	int DeleteSubheads(String subheadseqid, String headid, String security_id);

//Online  Report Journal Vocher
//	--new add (169,170,171,172,173,174,175,176)
	@Query(value = "SELECT voucher_id,voucher_name,description from voucher_list where voucher_id  not in(17,18,19,20,21,22,23,24,25,26) order by voucher_id", nativeQuery = true)
	List<Map<String, String>> getVoucherListDropdown();

	@Query(value = "SELECT hl.headid,hl.headname,class_budget,description,dr_cr,subheadname,subheadid,classification from \r\n"
			+ "(SELECT mh.headid,mh.headid||'-'||mh.headname as headname,dr_cr,subheadid,subheadname,(cm.classification_id||'-'||cm.classification_name)as class_budget,classification from mstheads mh \r\n"
			+ "left join classification_master cm on cm.classification_id=mh.classification\r\n"
			+ "join heads_voucherlist_mapping hvm on  mh.headid = hvm.headid  and hvm.voucher_id in \r\n"
			+ "(select mapping_heads from voucher_list where voucher_id=cast(:voucher_id as numeric))\r\n"
			+ "and hvm.headid in (select headid from heads_voucherlist_mapping where voucher_id in (select mapping_heads \r\n"
			+ "from voucher_list where  voucher_id=cast(:voucher_id as numeric))))hl join voucher_list on \r\n"
			+ "voucher_id=cast(:voucher_id as numeric) order by classification,dr_cr,hl.headid,subheadid ", nativeQuery = true)
	List<Map<String, String>> getDescription_Heads(String voucher_id);

//Slaary Journal Voucher 
//	--new add (169,170,171,172,173,174,175,176)
//	@Query(value = "SELECT voucher_id,voucher_name,description from voucher_list where voucher_id in(17,18,19,20,21,22,23,24,25,26,169,170,171,172,173,174,175,176) order by voucher_id", nativeQuery = true)
//	List<Map<String, String>> getSalaryVoucherListDropdown();

	@Query(value = "select voucher_name,voucher_id,description from voucher_list where  1=1 and voucher_id>16 and voucher_id<27 order by voucher_id", nativeQuery = true)
	List<Map<String, String>> getSalaryVoucherListDropdown_0();

	@Query(value = "select voucher_name,voucher_id,description from voucher_list where  1=1 and voucher_id>21 and voucher_id<27 order by voucher_id", nativeQuery = true)
	List<Map<String, String>> getSalaryVoucherListDropdown_1();

	// ********************Heads Master **************** in APSHCL LOGIN
	@Query(value = "SELECT headname,payments,receipts,headid,classification from mstheads where flag='f' order by  headid", nativeQuery = true)
	List<Map<String, String>> getHeadsDetails();

	@Query(value = "SELECT classification_id,classification_name FROM classification_master", nativeQuery = true)
	List<Map<String, String>> classificationDropdown();

	@Transactional
	@Modifying
	@Query(value = " insert into mstheads (headid,headname,payments,receipts,classification) values(:headid,:headname,:payments,:receipts,:classification)", nativeQuery = true)
	int headsmasterAdd(String headid, String headname, Boolean receipts, Boolean payments, String classification);

	@Transactional
	@Modifying
	@Query(value = "update mstheads set headname=:headname,payments=:payments,receipts=:receipts,classification=:classification where headid=:headid", nativeQuery = true)
	int headsmasterEdit(String headid, String headname, Boolean receipts, Boolean payments, String classification);

	@Transactional
	@Modifying
	@Query(value = "update mstheads set flag='t' where headid=:headid", nativeQuery = true)
	int headsmasterDelete(String headid);

//===========APRROVE RECEIPT MANAGET
	@Transactional
	@Modifying
//	@Query(value = "update payments_receipts set receiptno=:receiptno,date=:date,cash_bank=:cash_bank,banknameaccountno=:banknameaccountno,receiptamount=:receiptamount,balance_in_account:balance_in_account,\r\n"
//			+ "name=:name,mode=:mode,cheque_dd_receipt_no=:cheque_dd_receipt_no,receipt_description=:receipt_description,security_id:security_id,upload_copy:upload_copy,manager_status='true',manager_ip=:ip_address,manager_approvedtime=now()\r\n"
//			+ "where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	@Query(value = "update payments_receipts set receiptno=:receiptno, date=:date, cash_bank=:cash_bank, banknameaccountno=:banknameaccountno, receiptamount=:receiptamount, "
			+ "balance_in_account=:balance_in_account, name=:name, mode=:mode, cheque_dd_receipt_no=:cheque_dd_receipt_no, receipt_description=:receipt_description, security_id=:security_id,"
			+ " upload_copy=:upload_copy, manager_status=true, manager_ip=:ip_address,manager_approvedtime=now(),manager_id=:user_id where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int receiptentryManagerEdit(String payment_receipt_id, String receiptno, Date date, String cash_bank,
			String banknameaccountno, double receiptamount, double balance_in_account, String name, String mode,
			String cheque_dd_receipt_no, String receipt_description, String security_id, String upload_copy,
			String ip_address, String user_id);

	@Transactional
	@Modifying

	@Query(value = "update  dummy_journal_voucher_heads set jvslno=:jvslno,headid=:headid, subheadid=:subheadid,debit=0,credit=:credit,security_id=:security_id,manager_id=:user_id, manager_status='true', manager_ip=:ip_address,manager_approvedtime=now()   where payment_receipt_id=:payment_receipt_id and jvslno=:jvslno", nativeQuery = true)
	int dummy_head_entry_insertion_receipt_Man(String payment_receipt_id, Integer jvslno, String headid,
			String subheadid, double credit, String security_id, String user_id, String ip_address);

	@Transactional
	@Modifying
	@Query(value = "update payments_receipts_subheads set headid=:headid,subheadseqid=:subheadid,amount=:credit,security_id=:security_id ,complete_save=true,manager_id=:user_id, manager_status='true', manager_ip=:ip_address,manager_approvedtime=now()  where payment_receipt_id=:payment_receipt_id and headid=:headid", nativeQuery = true)
	int receipts_subheads_man(String payment_receipt_id, String headid, String subheadid, double credit,
			String security_id, String user_id, String ip_address);

	@Transactional
	@Modifying
	@Query(value = "UPDATE payments_receipts_incomplete\r\n"
			+ "SET headid=(SELECT headid  FROM dummy_journal_voucher_heads WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadid = :subheadid),\r\n"
			+ "subheadseqid=(SELECT subheadid  FROM dummy_journal_voucher_heads WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadid = :subheadid),\r\n"
			+ "amount = (SELECT credit  FROM dummy_journal_voucher_heads WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadid = :subheadid),\r\n"
			+ " security_id = (SELECT security_id FROM dummy_journal_voucher_heads WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadid = :subheadid)\r\n"
			+ "WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadseqid = :subheadid", nativeQuery = true)
	int receipts_incomplete_man(String payment_receipt_id, String headid, String subheadid);

	// ===========APRROVE Payment MANAGET

	@Transactional
	@Modifying
	@Query(value = "update payments_receipts set receiptno=:receiptno,date=:date,cash_bank=:cash_bank,banknameaccountno=:banknameaccountno,paymentamount=cast(:paymentamount as numeric),balance_in_account= cast(:balance_in_account as numeric),\r\n"
			+ "name=:name,mode=:mode,cheque_dd_receipt_no=:cheque_dd_receipt_no,receipt_description=:receipt_description,security_id=:security_id,upload_copy=:upload_copy,manager_status=true,manager_ip=:ip_address,manager_approvedtime=now()\r\n"
			+ "where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int paymententryManagerEdit(String payment_receipt_id, String receiptno, Date date, String cash_bank,
			String banknameaccountno, double paymentamount, double balance_in_account, String name, String mode,
			String cheque_dd_receipt_no, String receipt_description, String security_id, String upload_copy,
			String ip_address);

	@Transactional
	@Modifying

	@Query(value = "update  dummy_journal_voucher_heads set   headid=:headid, subheadid=:subheadid,debit=:debit,credit=0,security_id=:security_id,manager_id=:user_id, manager_status='true', manager_ip=:ip_address,manager_approvedtime=now()   where payment_receipt_id=:payment_receipt_id and jvslno=:jvslno", nativeQuery = true)
	int dummy_head_entry_insertion_payment_Man(String payment_receipt_id, String headid, String subheadid, double debit,
			String security_id, String user_id, String ip_address, int jvslno);

	@Transactional
	@Modifying
	@Query(value = "update payments_receipts_subheads set headid=:headid,subheadseqid=:subheadid,amount=:debit,security_id=:security_id ,complete_save=true,manager_id=:user_id, manager_status='true', manager_ip=:ip_address,manager_approvedtime=now() where payment_receipt_id=:payment_receipt_id and headid=:headid", nativeQuery = true)
	int payments_subheads_man(String payment_receipt_id, String headid, String subheadid, double debit,
			String security_id, String user_id, String ip_address);

	@Transactional
	@Modifying
	@Query(value = "UPDATE payments_receipts_incomplete\r\n"
			+ "SET headid=(SELECT credit  FROM dummy_journal_voucher_heads WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadid = :subheadid),\r\n"
			+ "subheadseqid=(SELECT subheadid  FROM dummy_journal_voucher_heads WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadid = :subheadid),\r\n"
			+ "amount = (SELECT debit  FROM dummy_journal_voucher_heads WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadid = :subheadid),\r\n"
			+ " security_id = (SELECT security_id FROM dummy_journal_voucher_heads WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadid = :subheadid)\r\n"
			+ "WHERE payment_receipt_id = :payment_receipt_id AND headid = :headid AND subheadseqid = :subheadid", nativeQuery = true)
	int payments__incomplete_man(String payment_receipt_id, String headid, String subheadid);

	@Transactional
	@Modifying
	@Query(value = "update journal_voucher set date=:date,voucher_id=:voucher_id,description=:description,debit=cast(:debit as numeric),"
			+ "credit=cast(:credit as numeric),upload_copy=:upload_copy where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int journal_entry_update_apshcl(String payment_receipt_id, Date date, String voucher_id, String description,
			double credit, double debit, String upload_copy);

	@Transactional
	@Modifying
	@Query(value = "update journal_voucher set date=:date,voucher_id=:voucher_id,description=:description,debit=cast(:debit as numeric),"
			+ "credit=cast(:credit as numeric),upload_copy=:upload_copy where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int journal_entry_update_insert(String payment_receipt_id, Date date, String voucher_id, String description,
			double credit, double debit, String upload_copy);

	@Transactional
	@Modifying
	@Query(value = "update journal_voucher set date=:date,voucher_id=:voucher_id,description=:description,debit=cast(:debit as numeric),credit=cast(:credit as numeric),upload_copy=:upload_copy,security_id=:security_id, manager_status='true',manager_ip=:ip_address,manager_approvedtime=now() where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int journal_entry_insertion_manager(String payment_receipt_id, Date date, String voucher_id, String description,
			double credit, double debit, String upload_copy, String security_id, String ip_address);

	@Query(value = "SELECT count(*) FROM journal_voucher_heads  where payment_receipt_id=:payment_receipt_id and jvslno=:jvslno", nativeQuery = true)
	int getcountofjv(String payment_receipt_id, int jvslno);

	@Transactional
	@Modifying
	@Query(value = "insert into journal_voucher_heads(payment_receipt_id,jvslno,headid,subheadid,credit,debit,security_id,dh_ip,timestamp,dh_id) "
			+ "values(:payment_receipt_id,(select max(jvslno)+1 from journal_voucher_heads),:headid,:subheadid,:credit,:debit,:security_id,:ip_address,now(),:user_id)", nativeQuery = true)
	int journal_voucher_heads_insert_heads(String payment_receipt_id, String headid, String subheadid, double credit,
			double debit, String security_id, String ip_address, String user_id);

	@Transactional
	@Modifying
	@Query(value = "insert into journal_voucher_heads(payment_receipt_id,jvslno,headid,subheadid,credit,debit,security_id,complete_save,manager_status,manager_ip,,manager_id) "
			+ "values(:payment_receipt_id,(select max(jvslno)+1 from journal_voucher_heads),:headid,:subheadid,:credit,:debit,:security_id,true,true,:ip_address,now(),:user_id)", nativeQuery = true)
	int journal_voucher_headsmanagetinsert(String payment_receipt_id, String headid, String subheadid, double credit,
			double debit, String security_id, String ip_address, String user_id);

	@Transactional
	@Modifying
	@Query(value = "update journal_voucher_heads set jvslno=:jvslno,headid=:headid,subheadid=:subheadid, credit = CAST(:credit AS numeric), debit = CAST(:debit AS numeric)\r\n"
			+ ",security_id=:security_id,complete_save='true',dh_id=:user_id, dh_status='true', dh_ip=:ip_address, timestamp=now()"
			+ "  where payment_receipt_id=:payment_receipt_id and jvslno=cast(:jvslno as numeric)\r\n"
			+ "", nativeQuery = true)

	int journal_voucher_heads_update_heads(String payment_receipt_id, String headid, String subheadid, double debit,
			double credit, String security_id, int jvslno, String user_id, String ip_address);

	@Transactional
	@Modifying
	@Query(value = "update journal_voucher_heads set jvslno=:jvslno,headid=:headid,subheadid=:subheadid, credit = CAST(:credit AS numeric), debit = CAST(:debit AS numeric)\r\n"
			+ ",security_id=:security_id,complete_save='true', manager_id=:user_id, manager_status='true', manager_ip=:ip_address, manager_approvedtime=now()"
			+ "  where payment_receipt_id=:payment_receipt_id and jvslno=cast(:jvslno as numeric)\r\n"
			+ "", nativeQuery = true)

	int journal_voucher_heads_manager(String payment_receipt_id, String headid, String subheadid, double debit,
			double credit, String security_id, int jvslno, String user_id, String ip_address);
//	int journal_voucher_heads_manager(String payment_receipt_id, String headid, String subheadid, int debit,
//			Integer credit, String security_id,int jvslno,String user_id,String ip_address);

	@Query(value = "SELECT (d.headid  || '-' || m.headname) as heads, (d.subheadseqid || '-'||\r\n"
			+ "case  when  d.subheadseqid='No' then 'No Subheads' when d.subheadseqid='more' or d.subheadseqid='More' then 'More' else (select subheadname from mstsubheads ms where ms.subheadseqid=d.subheadseqid) end) as subheads,\r\n"
			+ "d.amount,d.headid,d.subheadseqid,sum(pr.receiptamount) as receiptsum,sum(pr.paymentamount) as paymentsum FROM payments_receipts_subheads d \r\n"
			+ "left join mstheads m on m.headid=d.headid\r\n"
			+ "left join mstsubheads ms on ms.subheadid=d.subheadseqid and ms.headid=d.headid\r\n"
			+ "left join payments_receipts pr on pr.payment_receipt_id=d.payment_receipt_id\r\n"
			+ "where pr.payment_receipt_id=:payment_receipt_id  group by 1,2,3,4,5", nativeQuery = true)
	List<Map<String, String>> GetHeadbyPayment_receipt_id(String payment_receipt_id);

//GET MANAGET APPROVALS
	@Query(value = "SELECT accountid,bankname,branchname,ifsc,banknameaccountno,balance,remaining_balance,accountno,operationaccount,accounttype, accountholder1 ,  accountholder2  ,passbook_path "
			+ "FROM bankdetails where dh_status=true and manager_status=false ", nativeQuery = true)
	List<Map<String, String>> GetDataforBankEntnry_Manger();

	@Query(value = "select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cash_bank,balance_in_account,cheque_dd_receipt_no,receipt_description,no_of_subheads,\r\n"
			+ "banknameaccountno,receiptamount,paymentamount,transaction_type,upload_copy from payments_receipts \r\n"
			+ "where transaction_type='R'  and dh_status=true and manager_status=false order by timestamp", nativeQuery = true)
	List<Map<String, String>> GetDataforGR_Manger();

	@Query(value = "select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cash_bank,balance_in_account,cheque_dd_receipt_no,receipt_description,no_of_subheads,\r\n"
			+ "banknameaccountno,receiptamount,paymentamount,transaction_type,upload_copy from payments_receipts \r\n"
			+ "where transaction_type='P'  and dh_status=true and manager_status=false order by timestamp", nativeQuery = true)
	List<Map<String, String>> GetDataforGP_Manger();

	@Query(value = "SELECT distinct dj.jvslno,(d.headid  || '-' || m.headname) as heads, (d.subheadseqid || '-'||\r\n"
			+ "case  when  d.subheadseqid='No' then 'No Subheads' when d.subheadseqid='more' or d.subheadseqid='More' then 'More' else (select subheadname from mstsubheads ms where ms.subheadseqid=d.subheadseqid) end) as subheads,\r\n"
			+ "d.amount,d.headid,d.subheadseqid,sum(pr.receiptamount) as receiptsum,sum(pr.paymentamount) as paymentsum FROM payments_receipts_subheads d \r\n"
			+ "left join mstheads m on m.headid=d.headid\r\n"
			+ "left join mstsubheads ms on ms.subheadid=d.subheadseqid and ms.headid=d.headid\r\n"
			+ "left join payments_receipts pr on pr.payment_receipt_id=d.payment_receipt_id\r\n"
			+ "left join dummy_journal_voucher_heads dj on dj.payment_receipt_id=d.payment_receipt_id and d.headid=dj.headid\r\n"
			+ "where pr.payment_receipt_id=:payment_receipt_id  group by 1,2,3,4,5,6", nativeQuery = true)
	List<Map<String, Object>> GetSubHeadsDataforGR_Manger(String payment_receipt_id);

	@Query(value = "SELECT  payment_receipt_id,voucher_id,debit,credit, date,description, security_id ,isdelete,timestamp, is_confirm,upload_copy,dh_status FROM journal_voucher where dh_status=true and manager_status=false", nativeQuery = true)
	List<Map<String, String>> GetDataforGJ_Manger();

	@Query(value = "SELECT jh.jvslno,(jh.headid  || '-' || m.headname) as heads,jv.payment_receipt_id,voucher_id,jh.debit,jh.credit,jh.headid,jh.subheadid,\r\n"
			+ "(jh.subheadid || '-'||case  when  jh.subheadid='No' then 'No Subheads' when jh.subheadid='more' or jh.subheadid='More' then 'More' else (select subheadname from mstsubheads ms where ms.subheadseqid=jh.subheadid) end) as subheads\r\n"
			+ "FROM journal_voucher jv \r\n"
			+ "left join journal_voucher_heads jh on jh.payment_receipt_id=jv.payment_receipt_id \r\n"
			+ "left join mstheads m on m.headid=jh.headid\r\n"
			+ "left join mstsubheads ms on ms.subheadid=jh.subheadid and ms.headid=jh.headid where jv.payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, Object>> GetSubHeadsDataforGJ_Manger(String payment_receipt_id);

	@Query(value = "select balance_in_account from payments_receipts where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int getbalanceinaccount(String payment_receipt_id);

	@Query(value = "select remaining_balance from bankdetails where banknameaccountno=:banknameaccountno ", nativeQuery = true)
	int getremainingbalance(String banknameaccountno);

	@Query(value = "SELECT count(*) FROM dummy_journal_voucher_heads  where payment_receipt_id=:payment_receipt_id and jvslno=:jvslno", nativeQuery = true)
	int getcountof_gr(String payment_receipt_id, int jvslno);

	@Transactional
	@Modifying
	@Query(value = "insert into dummy_journal_voucher_heads(payment_receipt_id,jvslno,headid,subheadid,debit,credit,security_id,timestamp,dh_status,dh_id,dh_ip,manager_status,manager_id,manager_ip,manager_approvedtime)\r\n"
			+ "values(:payment_receipt_id,(select max(jvslno)+1 from dummy_journal_voucher_heads), :headid, :subheadid, 0.00, :credit, :security_id,now(),true,:user_id,:ip_address,true,:user_id,:ip_address,now())", nativeQuery = true)
	int dummy_head_entry_insertion_receipt_Man_insert(String payment_receipt_id, String headid, String subheadid,
			double credit, String security_id, String user_id, String ip_address);

	@Modifying
	@Transactional
	@Query(value = "insert into payments_receipts_subheads (payment_receipt_id,headid,subheadseqid,amount,security_id,complete_save,timestamp,dh_status,dh_id,dh_ip,manager_status,manager_id,manager_ip,manager_approvedtime)\r\n"
			+ "values(:payment_receipt_id, :headid, :subheadid, :credit, :security_id, true,now(),true,:user_id,:ip_address,true,:user_id,:ip_address,now())", nativeQuery = true)
	int receipts_subheads_man_insert(String payment_receipt_id, String headid, String subheadid, double credit,
			String security_id, String user_id, String ip_address);

	@Query(value = "SELECT count(*) FROM dummy_journal_voucher_heads  where payment_receipt_id=:payment_receipt_id and jvslno=:jvslno", nativeQuery = true)
	int getcountof_gp(String payment_receipt_id, int jvslno);

	@Transactional
	@Modifying
	@Query(value = "insert into dummy_journal_voucher_heads(payment_receipt_id,jvslno,headid,subheadid,debit,credit,security_id,timestamp,dh_status,dh_id,dh_ip,manager_status,manager_id,manager_ip,manager_approvedtime)\r\n"
			+ "values(:payment_receipt_id,(select max(jvslno)+1 from dummy_journal_voucher_heads), :headid, :subheadid, 0.00, :credit, :security_id,now(),true,:user_id,:ip_address,true,:user_id,:ip_address,now())", nativeQuery = true)
	int dummy_head_entry_insertion_payment_Man_insert(String payment_receipt_id, String headid, String subheadid,
			double credit, String security_id, String user_id, String ip_address);

	@Modifying
	@Transactional
	@Query(value = "insert into payments_receipts_subheads (payment_receipt_id,headid,subheadseqid,amount,security_id,complete_save,timestamp,dh_status,dh_id,dh_ip,manager_status,manager_id,manager_ip,manager_approvedtime)\r\n"
			+ "values(:payment_receipt_id, :headid, :subheadid, :credit, :security_id, true,now(),true,:user_id,:ip_address,true,:user_id,:ip_address,now())", nativeQuery = true)
	int payments_subheads_man_insert(String payment_receipt_id, String headid, String subheadid, double credit,
			String security_id, String user_id, String ip_address);

	@Modifying
	@Transactional
	@Query(value = "delete  from payments_receipts where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int payment_receipts_reject(String payment_receipt_id);

	@Modifying
	@Transactional
	@Query(value = "delete from payments_receipts_subheads where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int payment_receipts_subheads_reject(String payment_receipt_id);

	@Modifying
	@Transactional
	@Query(value = "delete from dummy_journal_voucher_heads where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int dummyvocuherheads_reject(String payment_receipt_id);

	@Modifying
	@Transactional
	@Query(value = "delete  from journal_voucher where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int journal_vocuher_reject(String payment_receipt_id);

	@Modifying
	@Transactional
	@Query(value = "delete  from journal_voucher_heads where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int journal_voucher_heads_reject(String payment_receipt_id);

	@Modifying
	@Transactional
	@Query(value = "delete  from payments_receipts_subheads where payment_receipt_id=:payment_receipt_id and headid=:headid and subheadseqid=:subheadid", nativeQuery = true)
	int payment_receipts_subheads_rowdelete(String payment_receipt_id, String headid, String subheadid);

	@Modifying
	@Transactional
	@Query(value = "delete  from dummy_journal_voucher_heads where payment_receipt_id=:payment_receipt_id and headid=:headid and subheadid=:subheadid", nativeQuery = true)
	int dummyvocuherheads_subheads_rowdelete(String payment_receipt_id, String headid, String subheadid);

	@Query(value = "SELECT count(*) FROM dummy_journal_voucher_heads where payment_receipt_id=:payment_receipt_id and headid=:headid and subheadid=:subheadid", nativeQuery = true)
	int checkIfRecordExists(String payment_receipt_id, String headid, String subheadid);

	@Query(value = "select case when int4(to_char(current_date,'dd'))>24 then 0 when int4(dl_jvs)=int4(to_char(current_date,'dd'))  then 0 else 1 end from date_limit where code=:security_id", nativeQuery = true)
	String getCase(String security_id);

	@Query(value = "SELECT count(*) FROM  journal_voucher where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	int getcountof_paymentreceipt_id(String payment_receipt_id);

	@Query(value = "SELECT  payment_receipt_id,voucher_id,debit,credit, date,description, security_id ,isdelete,timestamp, is_confirm,upload_copy,dh_status FROM journal_voucher where security_id=:security_id", nativeQuery = true)
	List<Map<String, String>> GetDataforGJ_Edit(String security_id);

	@Modifying
	@Transactional
	@Query(value = "delete from journal_voucher_heads where payment_receipt_id=:payment_receipt_id and headid=:headid and subheadid=:subheadid and security_id=:security_id", nativeQuery = true)
	int Generation_Journal_RowDelete(String payment_receipt_id, String headid, String subheadid, String security_id);

	@Modifying
	@Transactional
	@Query(value = "update journal_voucher set credit=:credit , debit=:debit  where  payment_receipt_id=:payment_receipt_id and security_id=:security_id", nativeQuery = true)
	int journal_vocuherupdateRowDelete(long credit, long debit, String payment_receipt_id, String security_id);

	@Query(value = "SELECT  payment_receipt_id,voucher_id,debit,credit, date,description, security_id ,isdelete,timestamp, is_confirm,upload_copy,dh_status FROM journal_voucher\r\n"
			+ " where date between to_date(cast(:fromDate as text),'dd/mm/yyyy')and to_date(cast(:toDate as text),'dd/mm/yyyy') ", nativeQuery = true)
	List<Map<String, String>> GetJV_Edit_dataByDates_state(String fromDate, String toDate);

	@Query(value = "SELECT  payment_receipt_id,voucher_id,debit,credit, date,description, security_id ,isdelete,timestamp, is_confirm,upload_copy,dh_status FROM journal_voucher\r\n"
			+ " where security_id=:security_id  and date between to_date(cast(:fromDate as text),'dd/mm/yyyy')and to_date(cast(:toDate as text),'dd/mm/yyyy') ", nativeQuery = true)
	List<Map<String, String>> GetJV_Edit_dataByDates(String fromDate, String toDate, String security_id);

	@Query(value = "SELECT  payment_receipt_id,voucher_id,debit,credit, date,description, security_id ,isdelete,timestamp, is_confirm,upload_copy,dh_status FROM journal_voucher\r\n"
			+ " where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> GetJV_EdidataById_state(String payment_receipt_id);

	// GMADM (DISTRICT WISE TRANSFER)
	@Query(value = "select  security_id,emp_type,(prefix ||firstname||'.'||lastname||''||surname) as empname ,coalesce(fathername,'-') as fathername,(select designation_name from designation where  designation_id=designation) as designation,emp_id from  emp_details\r\n"
			+ " where  security_id=cast(:fromDistrict as varchar) and  emp_type=cast(:emp_type as varchar) and   isdelete=false order by designation,firstname", nativeQuery = true)
	List<Map<String, String>> getDistrictwiseTransferData(String emp_type, String fromDistrict);

	@Modifying
	@Transactional
	@Query(value = "update  emp_details set security_id=:toDistrict where  emp_id=:emp_id and  security_id=:fromDistrict  and emp_type=:emp_type", nativeQuery = true)
	int empdetailsupdate(String toDistrict, String emp_id, String fromDistrict, String emp_type);

	@Modifying
	@Transactional
	@Query(value = "update  earnings_details set security_id=:toDistrict where  emp_id=:emp_id  and  security_id=:fromDistrict", nativeQuery = true)
	int earningdetailsupdate(String toDistrict, String emp_id, String fromDistrict);

	@Modifying
	@Transactional
	@Query(value = "update  deductions_details set security_id=:toDistrict where  emp_id=:emp_id  and  security_id=:fromDistrict", nativeQuery = true)
	int deductiondetailsupdate(String toDistrict, String emp_id, String fromDistrict);

	@Modifying
	@Transactional
	@Query(value = "update  loan_details set security_id=:toDistrict where  emp_id=:emp_id  and  security_id=:fromDistrict ", nativeQuery = true)
	int loandetailsupdate(String toDistrict, String emp_id, String fromDistrict);

	@Modifying
	@Transactional
	@Query(value = "insert into  transferdetails (fromdistrict,todistrict,emp_id,reasons,joiningdate,timestamp) values(:fromDistrict,:toDistrict,:emp_id,:remarks,to_date(:tranferDate,'dd/mm/yyyy'),now())", nativeQuery = true)
	int inserttransfetdetails(String toDistrict, String fromDistrict, String emp_id, String remarks,
			String tranferDate);

	@Query(value = "SELECT paymentamount,banknameaccountno,receiptamount FROM payments_receipts where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Object[]> getReceiptAmountbyPayment_Receipt_Id(String payment_receipt_id);

	@Query(value = "select remaining_balance from bankdetails where banknameaccountno = :banknameaccountno", nativeQuery = true)
	BigDecimal getremainingbalanceBAccount(String banknameaccountno);

	@Transactional
	@Modifying

	@Query(value = "update bankdetails set remaining_balance=cast(:remaining_balance_new as numeric) where banknameaccountno=:banknameaccountno and remaining_balance=:remaining_balance", nativeQuery = true)
	int UpdateRemainingBal(String banknameaccountno, BigDecimal remaining_balance, BigDecimal remaining_balance_new);

	// ====================================>New
	// Employee//Registration<=============================================//
	// ====================================>Employee
	// details<=============================================//

	@Query(value = "select 'EMP'||replace(to_char(nextval(cast('empdetails_new_sno_seq' as regclass)), '000000'),' ','')", nativeQuery = true)
	String empIdGeneation();

	@Modifying
	@Transactional
	@Query(value = "insert into emp_details (security_id,emp_id,prefix, firstname, lastname, surname,\r\n"
			+ "				fathername, emp_type, sex, designation, account_no, bank_name, branch_code, ifsc) values(:security_id,:emp_id,:prefix, :firstname, :lastname, :surname,\r\n"
			+ "				:fathername, :emptype_id, :sex, :designation, :account_no, :bank_name, :branch_code, :ifsc)", nativeQuery = true)
	int insert_into_EmployeeDetails(String security_id, String emp_id, String prefix, String firstname, String lastname,
			String surname, String fathername, int emptype_id, String sex, int designation, String account_no,
			String bank_name, String branch_code, String ifsc);

	// ====================================>125
	// Earnings<=============================================//
	@Modifying
	@Transactional
	@Query(value = "insert into earnings_details(security_id,emp_id,basic_pay_earnings, per_pay_earnings,\r\n"
			+ " spl_pay_earnings, da_earnings, hra_earnings, cca_earnings, gp_earnings, ir_earnings,\r\n"
			+ " medical_earnings, ca_earnings, spl_all, misc_h_c, addl_hra, sca) values(:security_id,:emp_id,:basic_pay_earnings, :per_pay_earnings,\r\n"
			+ " :spl_pay_earnings, :da_earnings, :hra_earnings, :cca_earnings, :gp_earnings, :ir_earnings,\r\n"
			+ " :medical_earnings, :ca_earnings, :spl_all, :misc_h_c, :addl_hra, :sca)", nativeQuery = true)
	int insert_into_EmpEarnings125(String security_id, String emp_id, double basic_pay_earnings,
			double per_pay_earnings, double spl_pay_earnings, double da_earnings, double hra_earnings,
			double cca_earnings, double gp_earnings, double ir_earnings, double medical_earnings, double ca_earnings,
			double spl_all, double misc_h_c, double addl_hra, double sca);

	// ====================================>125
	// Deductions<=============================================//
	@Modifying
	@Transactional
	@Query(value = "insert into deductions_details (security_id,emp_id,gpfs_deductions, gpfsa_deductions,\r\n"
			+ " gpfl_deductions, apglis_deductions, apglil_deductions, gis_deductions, lic_deductions,\r\n"
			+ " license_deductions, con_decd_deductions, epf_deductions,epf_l_deductions,\r\n"
			+ " vpf_deductions, ppf_deductions, rcs_cont_deductions, cmrf_deductions, fcf_deductions,\r\n"
			+ " house_rent_deductions, sal_rec_deductions, pt_deductions, it_deductions, other_deductions)"
			+ "values(:security_id,:emp_id,:gpfs_deductions, :gpfsa_deductions,\r\n"
			+ " :gpfl_deductions, :apglis_deductions, :apglil_deductions, :gis_deductions, :lic_deductions,\r\n"
			+ " :license_deductions, :con_decd_deductions, :epf_deductions, :epf_l_deductions,\r\n"
			+ " :vpf_deductions, :ppf_deductions, :rcs_cont_deductions, :cmrf_deductions, :fcf_deductions,\r\n"
			+ " :house_rent_deductions, :sal_rec_deductions, :pt_deductions, :it_deductions, :other_deductions)", nativeQuery = true)
	int insert_into_EmpDeductions125(String security_id, String emp_id, double gpfs_deductions, double gpfsa_deductions,
			double gpfl_deductions, double apglis_deductions, double apglil_deductions, double gis_deductions,
			double lic_deductions, double license_deductions, double con_decd_deductions, double epf_deductions,
			double epf_l_deductions, double vpf_deductions, double ppf_deductions, double rcs_cont_deductions,
			double cmrf_deductions, double fcf_deductions, double house_rent_deductions, double sal_rec_deductions,
			double pt_deductions, double it_deductions, double other_deductions);

	// ====================================>125Loans<=============================================//
	@Modifying
	@Transactional
	@Query(value = "insert into loan_details(security_id,emp_id,car_i_loan, car_a_loan, cyc_i_loan,\r\n"
			+ " cyc_a_loan, mca_i_loan, mca_a_loan, mar_a_loan, med_a_loan, hba_loan, hba1_loan, comp_loan, fa_loan,\r\n"
			+ " ea_loan, cell_loan, add_hba_loan, add_hba1_loan, sal_adv_loan, sfa_loan, med_a_i_loan, hcesa_loan,\r\n"
			+ " hcesa_I_loan, staff_pl_loan, court_loan, vij_bank_loan, mar_i_loan, hr_arrear_loan, hbao_loan,\r\n"
			+ " comp1_loan, car_i_loanpi, car_a_loanpi, cyc_i_loanpi, cyc_a_loanpi, mca_i_loanpi, mca_a_loanpi,\r\n"
			+ " mar_a_loanpi, med_a_loanpi, hba_loanpi, hba1_loanpi, comp_loanpi, fa_loanpi, ea_loanpi, cell_loanpi,\r\n"
			+ " add_hba_loanpi, add_hba1_loanpi, sal_adv_loanpi, sfa_loanpi, med_a_i_loanpi, hcesa_loanpi,\r\n"
			+ " hcesa_i_loanpi, staff_pl_loanpi, hr_arrear_loanpi, hbao_loanpi, comp1_loanpi, car_i_loanti,\r\n"
			+ " car_a_loanti, cyc_i_loanti, cyc_a_loanti, mca_i_loanti, mca_a_loanti, mar_a_loanti, med_a_loanti,\r\n"
			+ " hba_loanti, hba1_loanti, comp_loanti, fa_loanti, ea_loanti, cell_loanti, add_hba_loanti,\r\n"
			+ " add_hba1_loanti, sal_adv_loanti, sfa_loanti, med_a_i_loanti, hcesa_loanti, hcesa_i_loanti,\r\n"
			+ " staff_pl_loanti, court_loanti, vij_bank_loanti, mar_i_loanti, hr_arrear_loanti, hbao_loanti,\r\n"
			+ " comp1_loanti, court_loanpi, vij_bank_loanpi, mar_i_loanpi)values(:security_id,:emp_id,:car_i_loan, :car_a_loan, :cyc_i_loan,\r\n"
			+ " :cyc_a_loan, :mca_i_loan, :mca_a_loan, :mar_a_loan, :med_a_loan, :hba_loan, :hba1_loan, :comp_loan, :fa_loan,\r\n"
			+ " :ea_loan, :cell_loan, :add_hba_loan, :add_hba1_loan, :sal_adv_loan, :sfa_loan, :med_a_i_loan, :hcesa_loan,\r\n"
			+ " :hcesa_I_loan, :staff_pl_loan, :court_loan, :vij_bank_loan, :mar_i_loan, :hr_arrear_loan, :hbao_loan,\r\n"
			+ " :comp1_loan, :car_i_loanpi, :car_a_loanpi, :cyc_i_loanpi, :cyc_a_loanpi, :mca_i_loanpi, :mca_a_loanpi,\r\n"
			+ " :mar_a_loanpi, :med_a_loanpi, :hba_loanpi, :hba1_loanpi, :comp_loanpi, :fa_loanpi, :ea_loanpi, :cell_loanpi,\r\n"
			+ " :add_hba_loanpi, :add_hba1_loanpi, :sal_adv_loanpi, :sfa_loanpi, :med_a_i_loanpi, :hcesa_loanpi,\r\n"
			+ " :hcesa_i_loanpi, :staff_pl_loanpi, :hr_arrear_loanpi, :hbao_loanpi, :comp1_loanpi, :car_i_loanti,\r\n"
			+ " :car_a_loanti, :cyc_i_loanti, :cyc_a_loanti, :mca_i_loanti, :mca_a_loanti, :mar_a_loanti, :med_a_loanti,\r\n"
			+ " :hba_loanti, :hba1_loanti, :comp_loanti, :fa_loanti, :ea_loanti, :cell_loanti, :add_hba_loanti,\r\n"
			+ " :add_hba1_loanti, :sal_adv_loanti, :sfa_loanti, :med_a_i_loanti, :hcesa_loanti, :hcesa_i_loanti,\r\n"
			+ " :staff_pl_loanti, :court_loanti, :vij_bank_loanti, :mar_i_loanti, :hr_arrear_loanti, :hbao_loanti,\r\n"
			+ " :comp1_loanti, :court_loanpi, :vij_bank_loanpi, :mar_i_loanpi)", nativeQuery = true)
	int insert_into_EmpLoans125(String security_id, String emp_id, double car_i_loan, double car_a_loan,
			double cyc_i_loan, double cyc_a_loan, double mca_i_loan, double mca_a_loan, double mar_a_loan,
			double med_a_loan, double hba_loan, double hba1_loan, double comp_loan, double fa_loan, double ea_loan,
			double cell_loan, double add_hba_loan, double add_hba1_loan, double sal_adv_loan, double sfa_loan,
			double med_a_i_loan, double hcesa_loan, double hcesa_I_loan, double staff_pl_loan, double court_loan,
			double vij_bank_loan, double mar_i_loan, double hr_arrear_loan, double hbao_loan, double comp1_loan,
			double car_i_loanpi, double car_a_loanpi, double cyc_i_loanpi, double cyc_a_loanpi, double mca_i_loanpi,
			double mca_a_loanpi, double mar_a_loanpi, double med_a_loanpi, double hba_loanpi, double hba1_loanpi,
			double comp_loanpi, double fa_loanpi, double ea_loanpi, double cell_loanpi, double add_hba_loanpi,
			double add_hba1_loanpi, double sal_adv_loanpi, double sfa_loanpi, double med_a_i_loanpi,
			double hcesa_loanpi, double hcesa_i_loanpi, double staff_pl_loanpi, double hr_arrear_loanpi,
			double hbao_loanpi, double comp1_loanpi, double car_i_loanti, double car_a_loanti, double cyc_i_loanti,
			double cyc_a_loanti, double mca_i_loanti, double mca_a_loanti, double mar_a_loanti, double med_a_loanti,
			double hba_loanti, double hba1_loanti, double comp_loanti, double fa_loanti, double ea_loanti,
			double cell_loanti, double add_hba_loanti, double add_hba1_loanti, double sal_adv_loanti, double sfa_loanti,
			double med_a_i_loanti, double hcesa_loanti, double hcesa_i_loanti, double staff_pl_loanti,
			double court_loanti, double vij_bank_loanti, double mar_i_loanti, double hr_arrear_loanti,
			double hbao_loanti, double comp1_loanti, double court_loanpi, double vij_bank_loanpi, double mar_i_loanpi);

	// ====================================>3 Earnings and
	// Deductions<=============================================//
	@Modifying
	@Transactional
	@Query(value = "insert into  os_earn_ded_details(security_id,emp_id,os_basic_pay_earnings, os_hra_earnings, os_medical_earnings, os_ca_earnings,\r\n"
			+ "	os_performance_earnings, os_ec_epf, os_ees_epf_deductions, os_ers_epf_deductions,os_prof_tax_deductions, os_other_deductions, os_work_place, os_location) "
			+ "values(:security_id,:emp_id,:os_basic_pay_earnings, :os_hra_earnings, :os_medical_earnings, :os_ca_earnings,\r\n"
			+ "	 :os_performance_earnings, :os_ec_epf, :os_ees_epf_deductions, :os_ers_epf_deductions,:os_prof_tax_deductions, :os_other_deductions, :os_work_place, :os_location)", nativeQuery = true)
	int insert_into_earningsDeductions3(String security_id, String emp_id, double os_basic_pay_earnings,
			double os_hra_earnings, double os_medical_earnings, double os_ca_earnings, double os_performance_earnings,
			double os_ec_epf, double os_ees_epf_deductions, double os_ers_epf_deductions, double os_prof_tax_deductions,
			double os_other_deductions, String os_work_place, String os_location);

	// ====================================>4 Earnings and
	// Deductions<=============================================//
	@Modifying
	@Transactional
	@Query(value = "insert into nmr_earn_ded_details(security_id,emp_id,nmr_gross_earnings, os_ees_epf_deductions, nmr_postalrd_deductions, \r\n"
			+ " os_prof_tax_deductions,os_work_place, os_location, nmr_tds_deductions, nmr_fa_deductions, nmr_ea_deductions,nmr_ma_deductions, nmr_lic_deductions, \r\n"
			+ " nmr_otherliab_deductions) values(:security_id,:emp_id,:nmr_gross_earnings, :os_ees_epf_deductions, :nmr_postalrd_deductions, \r\n"
			+ " :os_prof_tax_deductions,:os_work_place, :os_location, :nmr_tds_deductions, :nmr_fa_deductions, :nmr_ea_deductions,:nmr_ma_deductions, :nmr_lic_deductions, \r\n"
			+ " :nmr_otherliab_deductions)", nativeQuery = true)
	int insert_into_earningsDeductions4(String security_id, String emp_id, double nmr_gross_earnings,
			double os_ees_epf_deductions, double nmr_postalrd_deductions, double os_prof_tax_deductions,
			String os_work_place, String os_location, double nmr_tds_deductions, double nmr_fa_deductions,
			double nmr_ea_deductions, double nmr_ma_deductions, double nmr_lic_deductions,
			double nmr_otherliab_deductions);

}
