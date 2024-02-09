package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.BankDetails_Model;

@Repository
public interface Reports_Repo extends JpaRepository<BankDetails_Model, Integer> {

	@Query(value = "select banknameaccountno,payment_receipt_id,case  when no_of_subheads='IBT' then 'IBT'  when headidcount=1 then headid else headid||'- more' end head_of_account,\r\n"
			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,coalesce(receiptamount,0)as receiptamount,coalesce(paymentamount,0)as paymentamount,\r\n"
			+ "balance_in_account,transaction_type,upload_copy from (\r\n"
			+ "select banknameaccountno,payment_receipt_id,\r\n"
			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,\r\n"
			+ "balance_in_account,transaction_type,upload_copy,(select count(headid) from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id) headidcount,\r\n"
			+ "(select headid from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id limit 1 ) headid\r\n"
			+ "from payments_receipts m where date between to_date(cast(:fromDate as text),'dd/mm/yyyy')\r\n"
			+ "and to_date(cast(:toDate as text),'dd/mm/yyyy') and user_id=:user_id\r\n"
			+ "and isdelete='f'  order by timestamp)a", nativeQuery = true)
	List<Map<String, String>> GetBankBook_Report(String fromDate, String toDate, String user_id);

	@Query(value = "select designation_name, designation_id from designation order by designation_name", nativeQuery = true)
	List<Map<String, String>> DesignationDrop_Down();

	@Query(value = "select empname, empid, de.designation_name, empfather , cr.caste_name as caste, dob, doj, dor,\r\n"
			+ "COALESCE(ap1.dist_name, 'prdist_other') AS prdist,\r\n"
			+ "COALESCE((SELECT div_name FROM ap_master WHERE dist_code = prdist AND div_code = prdiv LIMIT 1),prdiv_other ) AS prdiv,\r\n"
			+ "COALESCE(ap1.sdiv_name, prsubdiv_other) AS prsdiv,COALESCE(ap1.mandal_name, prmandal_other) AS prmandal,\r\n"
			+ "COALESCE((SELECT md.name FROM cgg_master_districts md WHERE code = LPAD(cast(natdist as text), 2, '0') LIMIT 1), natdist_other ) AS natdist,\r\n"
			+ "COALESCE( (SELECT div_name FROM ap_master WHERE dist_code = natdist AND div_code = natdiv LIMIT 1), natdiv_other ) AS natdiv,\r\n"
			+ "COALESCE(ap2.sdiv_name, natsdiv_other) AS natsdiv, COALESCE(ap2.mandal_name, natmandal_other) AS natmandal,et.emptype_name AS prwork,\r\n"
			+ "otherwork AS otherdatabasic,othercase AS disciplinary,CASE WHEN otherworkarea = '0' OR otherworkarea IS NULL THEN '-'\r\n"
			+ "ELSE cast(otherworkarea as text) END AS otherdatabasics,dowpc,qms.qualificationname AS eduappoint, \r\n"
			+ "qmst.qualificationname || ', Other Qualifications:' || COALESCE(other_qualifications, '') AS eduafterappoint,\r\n"
			+ "cadreappoint,cadrerefno, cadrerefdate, empaadhar, emppan, emppfuno, prdiv AS prdivcode, prsubdiv AS prsubdivcode, prsubmandal AS prsubmancode\r\n"
			+ "FROM empdetails_new em\r\n"
			+ "LEFT JOIN (SELECT dist_code, dist_name, div_code, div_name, sdiv_code, sdiv_name, mandal_code, mandal_name FROM ap_master\r\n"
			+ "GROUP BY dist_code,dist_name,div_code,div_name,sdiv_code,sdiv_name,mandal_code,mandal_name) ap1 ON (ap1.dist_code = em.prdist AND ap1.mandal_code = em.prsubmandal)\r\n"
			+ "LEFT JOIN ( SELECT dist_code,dist_name,div_code,div_name,cont_code AS sdiv_code,cont_name AS sdiv_name,mandal_code,mandal_name FROM ap_master\r\n"
			+ "group by dist_code,dist_name,div_code,div_name,cont_code,cont_name,mandal_code,mandal_name) ap2 on (ap2.dist_code = em.natdist \r\n"
			+ "and ap2.mandal_code=em.natmandal) left join caste_master cr on cr.caste_id=em.caste left join designation de on de.designation_id=em.designation\r\n"
			+ "left join employee_type_inception et on et.emptype_id=cast(em.prwork as int4)\r\n"
			+ "left join qualificationmaster qms on qms.qualificationid=cast(em.eduappoint as int4)\r\n"
			+ "left join qualificationmaster qmst on qmst.qualificationid=cast(em.eduafterappoint as int4 )\r\n"
			+ "where 1=1 and emp_status_id=1 and prdist=:dist_code ", nativeQuery = true)
	List<Map<String, String>> GetSearchEmployee_Report_dist(int dist_code);

	@Query(value = "select empname, empid, de.designation_name, empfather , cr.caste_name as caste, dob, doj, dor,\r\n"
			+ "COALESCE(ap1.dist_name, 'prdist_other') AS prdist,\r\n"
			+ "COALESCE((SELECT div_name FROM ap_master WHERE dist_code = prdist AND div_code = prdiv LIMIT 1),prdiv_other ) AS prdiv,\r\n"
			+ "COALESCE(ap1.sdiv_name, prsubdiv_other) AS prsdiv,COALESCE(ap1.mandal_name, prmandal_other) AS prmandal,\r\n"
			+ "COALESCE((SELECT md.name FROM cgg_master_districts md WHERE code = LPAD(cast(natdist as text), 2, '0') LIMIT 1), natdist_other ) AS natdist,\r\n"
			+ "COALESCE( (SELECT div_name FROM ap_master WHERE dist_code = natdist AND div_code = natdiv LIMIT 1), natdiv_other ) AS natdiv,\r\n"
			+ "COALESCE(ap2.sdiv_name, natsdiv_other) AS natsdiv, COALESCE(ap2.mandal_name, natmandal_other) AS natmandal,et.emptype_name AS prwork,\r\n"
			+ "otherwork AS otherdatabasic,othercase AS disciplinary,CASE WHEN otherworkarea = '0' OR otherworkarea IS NULL THEN '-'\r\n"
			+ "ELSE cast(otherworkarea as text) END AS otherdatabasics,dowpc,qms.qualificationname AS eduappoint, \r\n"
			+ "qmst.qualificationname || ', Other Qualifications:' || COALESCE(other_qualifications, '') AS eduafterappoint,\r\n"
			+ "cadreappoint,cadrerefno, cadrerefdate, empaadhar, emppan, emppfuno, prdiv AS prdivcode, prsubdiv AS prsubdivcode, prsubmandal AS prsubmancode\r\n"
			+ "FROM empdetails_new em\r\n"
			+ "LEFT JOIN (SELECT dist_code, dist_name, div_code, div_name, sdiv_code, sdiv_name, mandal_code, mandal_name FROM ap_master\r\n"
			+ "GROUP BY dist_code,dist_name,div_code,div_name,sdiv_code,sdiv_name,mandal_code,mandal_name) ap1 ON (ap1.dist_code = em.prdist AND ap1.mandal_code = em.prsubmandal)\r\n"
			+ "LEFT JOIN ( SELECT dist_code,dist_name,div_code,div_name,cont_code AS sdiv_code,cont_name AS sdiv_name,mandal_code,mandal_name FROM ap_master\r\n"
			+ "group by dist_code,dist_name,div_code,div_name,cont_code,cont_name,mandal_code,mandal_name) ap2 on (ap2.dist_code = em.natdist \r\n"
			+ "and ap2.mandal_code=em.natmandal) left join caste_master cr on cr.caste_id=em.caste left join designation de on de.designation_id=em.designation\r\n"
			+ "left join employee_type_inception et on et.emptype_id=cast(em.prwork as int4)\r\n"
			+ "left join qualificationmaster qms on qms.qualificationid=cast(em.eduappoint as int4)\r\n"
			+ "left join qualificationmaster qmst on qmst.qualificationid=cast(em.eduafterappoint as int4 )\r\n"
			+ "where 1=1 and emp_status_id=1 and prdist=:dist_code and empname=:empname", nativeQuery = true)
	List<Map<String, String>> GetSearchEmployee_Report_dist_empname(int dist_code, String empname);

	@Query(value = "select empname, empid, de.designation_name, empfather , cr.caste_name as caste, dob, doj, dor,\r\n"
			+ "COALESCE(ap1.dist_name, 'prdist_other') AS prdist,\r\n"
			+ "COALESCE((SELECT div_name FROM ap_master WHERE dist_code = prdist AND div_code = prdiv LIMIT 1),prdiv_other ) AS prdiv,\r\n"
			+ "COALESCE(ap1.sdiv_name, prsubdiv_other) AS prsdiv,COALESCE(ap1.mandal_name, prmandal_other) AS prmandal,\r\n"
			+ "COALESCE((SELECT md.name FROM cgg_master_districts md WHERE code = LPAD(cast(natdist as text), 2, '0') LIMIT 1), natdist_other ) AS natdist,\r\n"
			+ "COALESCE( (SELECT div_name FROM ap_master WHERE dist_code = natdist AND div_code = natdiv LIMIT 1), natdiv_other ) AS natdiv,\r\n"
			+ "COALESCE(ap2.sdiv_name, natsdiv_other) AS natsdiv, COALESCE(ap2.mandal_name, natmandal_other) AS natmandal,et.emptype_name AS prwork,\r\n"
			+ "otherwork AS otherdatabasic,othercase AS disciplinary,CASE WHEN otherworkarea = '0' OR otherworkarea IS NULL THEN '-'\r\n"
			+ "ELSE cast(otherworkarea as text) END AS otherdatabasics,dowpc,qms.qualificationname AS eduappoint, \r\n"
			+ "qmst.qualificationname || ', Other Qualifications:' || COALESCE(other_qualifications, '') AS eduafterappoint,\r\n"
			+ "cadreappoint,cadrerefno, cadrerefdate, empaadhar, emppan, emppfuno, prdiv AS prdivcode, prsubdiv AS prsubdivcode, prsubmandal AS prsubmancode\r\n"
			+ "FROM empdetails_new em\r\n"
			+ "LEFT JOIN (SELECT dist_code, dist_name, div_code, div_name, sdiv_code, sdiv_name, mandal_code, mandal_name FROM ap_master\r\n"
			+ "GROUP BY dist_code,dist_name,div_code,div_name,sdiv_code,sdiv_name,mandal_code,mandal_name) ap1 ON (ap1.dist_code = em.prdist AND ap1.mandal_code = em.prsubmandal)\r\n"
			+ "LEFT JOIN ( SELECT dist_code,dist_name,div_code,div_name,cont_code AS sdiv_code,cont_name AS sdiv_name,mandal_code,mandal_name FROM ap_master\r\n"
			+ "group by dist_code,dist_name,div_code,div_name,cont_code,cont_name,mandal_code,mandal_name) ap2 on (ap2.dist_code = em.natdist \r\n"
			+ "and ap2.mandal_code=em.natmandal) left join caste_master cr on cr.caste_id=em.caste left join designation de on de.designation_id=em.designation\r\n"
			+ "left join employee_type_inception et on et.emptype_id=cast(em.prwork as int4)\r\n"
			+ "left join qualificationmaster qms on qms.qualificationid=cast(em.eduappoint as int4)\r\n"
			+ "left join qualificationmaster qmst on qmst.qualificationid=cast(em.eduafterappoint as int4 )\r\n"
			+ "where 1=1 and emp_status_id=1 and prdist=:dist_code and  designation =:designation_id ", nativeQuery = true)
	List<Map<String, String>> GetSearchEmployee_Report_design(int dist_code, int designation_id);

	@Query(value = "select empname, empid, de.designation_name, empfather , cr.caste_name as caste, dob, doj, dor,\r\n"
			+ "COALESCE(ap1.dist_name, 'prdist_other') AS prdist,\r\n"
			+ "COALESCE((SELECT div_name FROM ap_master WHERE dist_code = prdist AND div_code = prdiv LIMIT 1),prdiv_other ) AS prdiv,\r\n"
			+ "COALESCE(ap1.sdiv_name, prsubdiv_other) AS prsdiv,COALESCE(ap1.mandal_name, prmandal_other) AS prmandal,\r\n"
			+ "COALESCE((SELECT md.name FROM cgg_master_districts md WHERE code = LPAD(cast(natdist as text), 2, '0') LIMIT 1), natdist_other ) AS natdist,\r\n"
			+ "COALESCE( (SELECT div_name FROM ap_master WHERE dist_code = natdist AND div_code = natdiv LIMIT 1), natdiv_other ) AS natdiv,\r\n"
			+ "COALESCE(ap2.sdiv_name, natsdiv_other) AS natsdiv, COALESCE(ap2.mandal_name, natmandal_other) AS natmandal,et.emptype_name AS prwork,\r\n"
			+ "otherwork AS otherdatabasic,othercase AS disciplinary,CASE WHEN otherworkarea = '0' OR otherworkarea IS NULL THEN '-'\r\n"
			+ "ELSE cast(otherworkarea as text) END AS otherdatabasics,dowpc,qms.qualificationname AS eduappoint, \r\n"
			+ "qmst.qualificationname || ', Other Qualifications:' || COALESCE(other_qualifications, '') AS eduafterappoint,\r\n"
			+ "cadreappoint,cadrerefno, cadrerefdate, empaadhar, emppan, emppfuno, prdiv AS prdivcode, prsubdiv AS prsubdivcode, prsubmandal AS prsubmancode\r\n"
			+ "FROM empdetails_new em\r\n"
			+ "LEFT JOIN (SELECT dist_code, dist_name, div_code, div_name, sdiv_code, sdiv_name, mandal_code, mandal_name FROM ap_master\r\n"
			+ "GROUP BY dist_code,dist_name,div_code,div_name,sdiv_code,sdiv_name,mandal_code,mandal_name) ap1 ON (ap1.dist_code = em.prdist AND ap1.mandal_code = em.prsubmandal)\r\n"
			+ "LEFT JOIN ( SELECT dist_code,dist_name,div_code,div_name,cont_code AS sdiv_code,cont_name AS sdiv_name,mandal_code,mandal_name FROM ap_master\r\n"
			+ "group by dist_code,dist_name,div_code,div_name,cont_code,cont_name,mandal_code,mandal_name) ap2 on (ap2.dist_code = em.natdist \r\n"
			+ "and ap2.mandal_code=em.natmandal) left join caste_master cr on cr.caste_id=em.caste left join designation de on de.designation_id=em.designation\r\n"
			+ "left join employee_type_inception et on et.emptype_id=cast(em.prwork as int4)\r\n"
			+ "left join qualificationmaster qms on qms.qualificationid=cast(em.eduappoint as int4)\r\n"
			+ "left join qualificationmaster qmst on qmst.qualificationid=cast(em.eduafterappoint as int4 )\r\n"
			+ "where 1=1 and emp_status_id=1 and prdist=:dist_code and  designation =:designation_id and empname ilike cast(:empname as varchar)", nativeQuery = true)
	List<Map<String, String>> GetSearchEmployee_Report_empname(int dist_code, int designation_id, String empname);

//Bank Subsidiary Report

//	@Query(value = "select payment_receipt_id,date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,\r\n"
//			+ "balance_in_account,transaction_type,upload_copy from payments_receipts m where date between to_date(cast(:fromDate as text),'dd/mm/yyyy')\r\n"
//			+ "and to_date(cast(:toDate as text),'dd/mm/yyyy') and banknameaccountno=:banknameaccountno and security_id=:security_id \r\n"
//			+ "and isdelete='f' order by timestamp", nativeQuery = true)
//	@Query(value = "select case when transaction_type='P' then (balance_in_account+paymentamount) else (balance_in_account-receiptamount) end openingbalance,"
//			+ "banknameaccountno,payment_receipt_id,case  when no_of_subheads='IBT' then 'IBT'  when headidcount=1 then headid else headid||'- more' end head_of_account,\r\n"
//			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,coalesce(no_of_subheads,'0') no_of_subheads,"
//			+ "cast(coalesce(receiptamount,0) as text)as receiptamount,cast(coalesce(paymentamount,0) as text) as paymentamount,\r\n"
//			+ "balance_in_account,transaction_type,upload_copy from (\r\n"
//			+ "select banknameaccountno,payment_receipt_id,\r\n"
//			+ "to_char(date,'dd/mm/yyyy') as date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,\r\n"
//			+ "balance_in_account,transaction_type,upload_copy,(select count(headid) from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id) headidcount,\r\n"
//			+ "(select headid from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id limit 1 ) headid\r\n"
//			+ "from payments_receipts m where date between to_date(cast(:fromDate as text),'dd/mm/yyyy')\r\n"
//			+ "and to_date(cast(:toDate as text),'dd/mm/yyyy') and banknameaccountno=:banknameaccountno and security_id=:security_id\r\n"
//			+ "and isdelete='f'  order by date)a", nativeQuery = true) 
	@Query(value = "select sequence_order,banknameaccountno,payment_receipt_id,head_of_account,date,\r\n"
			+ "receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,\r\n"
			+ "DEBIT,CREDIT,transaction_type,dummy,abs(sum(credit-debit-dummy) over(order by sequence_order,payment_receipt_id)) as balance,\r\n"
			+ "upload_copy from (\r\n"
			+ "SELECT  row_number() over () as sequence_order,banknameaccountno,payment_receipt_id,head_of_account,\r\n"
			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,DEBIT,CREDIT,transaction_type,dummy,upload_copy from (\r\n"
			+ "( SELECT a.banknameaccountno,'null' payment_receipt_id ,'null' head_of_account,TO_DATE(cast(:fromDate as text),'dd/mm/yyyy') date ,'null' receiptno,'null' as name,\r\n"
			+ "'null' as mode,'null' as cheque_dd_receipt_no,'null' receipt_description,'null'as no_of_subheads,0 DEBIT,\r\n"
			+ "SUM(CURRENT)+sum(coalesce(CREDIT,0.00))-sum(coalesce(debit,0.00)) CREDIT,'null' transaction_type,\r\n"
			+ "cast(0 as numeric) as dummy,'null' upload_copy FROM bankdetails B left  JOIN\r\n"
			+ "bank_opening_balance OB ON OB.banknameaccountno=B.banknameaccountno  left JOIN\r\n"
			+ "(SELECT banknameaccountno,sum(paymentamount) debit,sum(receiptamount) credit   from payments_receipts \r\n"
			+ "where banknameaccountno!='' and date  >=TO_DATE('01/04/2022','dd/mm/yyyy') and date <TO_DATE(cast(:fromDate as text),'dd/mm/yyyy')  and security_id=:security_id and banknameaccountno=:banknameaccountno\r\n"
			+ "group by banknameaccountno) A ON  a.banknameaccountno =B.banknameaccountnO  where security_id=:security_id \r\n"
			+ "and a.banknameaccountno!='' group by a.banknameaccountno)\r\n" + "union all\r\n"
			+ "select A.banknameaccountno,payment_receipt_id,\r\n"
			+ "case  when no_of_subheads='IBT' then 'IBT'  when headidcount=1 then headid else headid||'- more' end head_of_account,\r\n"
			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,coalesce(paymentamount,0)as debit,coalesce(receiptamount,0)as credit,\r\n"
			+ "transaction_type,cast(0 as numeric) as dummy,upload_copy from (\r\n"
			+ "select banknameaccountno,payment_receipt_id,\r\n"
			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,transaction_type,upload_copy,(select count(headid) from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id) headidcount,\r\n"
			+ "(select headid from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id limit 1 ) headid\r\n"
			+ "from payments_receipts m where date between to_date(cast(:fromDate as text),'dd/mm/yyyy')\r\n"
			+ "and to_date(cast(:toDate as text),'dd/mm/yyyy') and banknameaccountno=:banknameaccountno and security_id=:security_id\r\n"
			+ "and isdelete='f'  order by date) a) a order by date) a", nativeQuery = true)
	List<Map<String, String>> GetSubsidiary_Report(String fromDate, String toDate, String banknameaccountno,
			String security_id);

	@Query(value = "select payment_receipt_id,banknameaccountno,case  when no_of_subheads='IBT' then 'IBT'  when headidcount=1 then headid else headid||'- more' end head_of_account,\r\n"
			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,coalesce(receiptamount,0)as receiptamount,coalesce(paymentamount,0)as paymentamount,\r\n"
			+ "balance_in_account,transaction_type,upload_copy from (\r\n"
			+ "select banknameaccountno,payment_receipt_id,\r\n"
			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,\r\n"
			+ "balance_in_account,transaction_type,upload_copy,(select count(headid) from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id) headidcount,\r\n"
			+ "(select headid from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id limit 1 ) headid\r\n"
			+ "from payments_receipts m where date between to_date(cast(:fromDate as text),'dd/mm/yyyy')\r\n"
			+ "and to_date(cast(:toDate as text),'dd/mm/yyyy')  and transaction_type=:transaction_type and security_id=:security_id\r\n"
			+ "and isdelete='f'  order by timestamp)a", nativeQuery = true)
	List<Map<String, String>> GetCashBank_All_Receipts_Report(String fromDate, String toDate, String transaction_type,
			String security_id);

	@Query(value = "select payment_receipt_id,banknameaccountno,case  when no_of_subheads='IBT' then 'IBT'  when headidcount=1 then headid else headid||'- more' end head_of_account,\r\n"
			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,coalesce(receiptamount,0)as receiptamount,coalesce(paymentamount,0)as paymentamount,\r\n"
			+ "balance_in_account,transaction_type,upload_copy from (\r\n"
			+ "select banknameaccountno,payment_receipt_id,\r\n"
			+ "date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,\r\n"
			+ "balance_in_account,transaction_type,upload_copy,(select count(headid) from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id) headidcount,\r\n"
			+ "(select headid from payments_receipts_subheads pr where pr.payment_receipt_id=m.payment_receipt_id limit 1 ) headid\r\n"
			+ "from payments_receipts m where date between to_date(cast(:fromDate as text),'dd/mm/yyyy')\r\n"
			+ "and to_date(cast(:toDate as text),'dd/mm/yyyy')  and cash_bank=:cash_bank and transaction_type=:transaction_type  and security_id=:security_id\r\n"
			+ "and isdelete='f'  order by timestamp)a", nativeQuery = true)
	List<Map<String, String>> GetCashBank_Receipts_Report(String fromDate, String toDate, String cash_bank,
			String transaction_type, String security_id);
//
//	
//	@Query(value = "select j.payment_receipt_id,cast(voucher_id as int),to_char(date,'dd/mm/yyyy'),description,coalesce(jv.debit,0.00) as debit,coalesce(jv.credit,0.00) as credit from journal_voucher j \r\n"
//			+ " left join journal_voucher_heads jv on jv.payment_receipt_id=j.payment_receipt_id\r\n"
//			+ " where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')\r\n"
//			+ " j.security_id=:security_id and j.isdelete='f'  order by voucher_id", nativeQuery = true)
//	List<Map<String, String>> GetJournalVochersReport(String fromDate, String toDate, String security_id);

	@Query(value = "select payment_receipt_id,cast(voucher_id as int),to_char(date,'dd/mm/yyyy'),description,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit,upload_copy from journal_voucher \r\n"
			+ "where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and\r\n"
			+ "security_id=:security_id and isdelete='f'  order by voucher_id", nativeQuery = true)
	List<Map<String, String>> GetJournalVochersReport(String fromDate, String toDate, String security_id);

	@Query(value = "select payment_receipt_id,voucher_id,to_char(date,'dd/mm/yyyy'),description,credit,debit,upload_copy from journal_voucher j where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> GetJournalVochersReport_ById(String payment_receipt_id);

	@Query(value = "select headid,case  when  subheadid='No' then 'No Subheads' when subheadid='more' or subheadid='More' then 'More' else (select subheadname from mstsubheads ms where ms.subheadseqid=j.subheadid) end,\r\n"
			+ "debit,credit,jvslno from journal_voucher_heads j where payment_receipt_id=:payment_receipt_id  order by debit desc,credit desc", nativeQuery = true)
	List<Map<String, String>> GetJournalVochersReport_HeadsById(String payment_receipt_id);

	@Query(value = "SELECT (SELECT name FROM cgg_master_districts WHERE code =:security_id) AS district, \r\n"
			+ "(SELECT COUNT(transaction_type) FROM payments_receipts WHERE date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') AND transaction_type = 'R' AND security_id = :security_id) AS receipt_count, \r\n"
			+ "(SELECT COALESCE(SUM(receiptamount), 0.00) FROM payments_receipts  WHERE date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') AND transaction_type = 'R' AND security_id = :security_id) AS receipt_total, \r\n"
			+ "(SELECT COUNT(transaction_type) FROM payments_receipts WHERE date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') AND transaction_type = 'P' AND security_id = :security_id) AS payment_count, \r\n"
			+ "(SELECT COALESCE(SUM(paymentamount), 0.00) FROM payments_receipts  WHERE date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') AND transaction_type = 'P' AND security_id = :security_id) AS payment_total, \r\n"
			+ "(SELECT COUNT(DISTINCT payment_receipt_id) FROM journal_voucher_heads j WHERE j.payment_receipt_id IN \r\n"
			+ "(SELECT payment_receipt_id FROM journal_voucher WHERE date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') AND security_id = :security_id)) AS jv_count, \r\n"
			+ "(SELECT COALESCE(SUM(debit), 0.00) FROM journal_voucher_heads j WHERE j.payment_receipt_id IN \r\n"
			+ "(SELECT payment_receipt_id FROM journal_voucher  WHERE date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') AND security_id = :security_id)) AS jv_debit, \r\n"
			+ "(SELECT COALESCE(SUM(credit), 0.00) FROM journal_voucher_heads j WHERE j.payment_receipt_id IN \r\n"
			+ "(SELECT payment_receipt_id FROM journal_voucher  WHERE date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') AND security_id = :security_id)) AS jv_credit", nativeQuery = true)
	List<Map<String, String>> GetHeadWiseAll_Details_Report(String fromDate, String toDate, String security_id);

	@Query(value = "SELECT (SELECT name FROM cgg_master_districts WHERE code = :security_id) AS district,t1.amount_receipts,t1.count_receipts,t2.amount_payments, t2.count_payments ,t3.jv_debit, t3.jv_credit,t3.jv_count\r\n"
			+ "FROM(SELECT COALESCE(SUM(amount), 0.00) AS amount_receipts, COALESCE(COUNT(DISTINCT(payment_receipt_id)), 0) AS count_receipts FROM payments_receipts_subheads\r\n"
			+ "WHERE payment_receipt_id IN (SELECT payment_receipt_id FROM payments_receipts WHERE transaction_type = 'R'   and security_id=:security_id AND  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy'))\r\n"
			+ "AND headid =:headid) t1,\r\n"
			+ "(SELECT COALESCE(SUM(amount), 0.00) AS amount_payments, COALESCE(COUNT(DISTINCT(payment_receipt_id)), 0) AS count_payments\r\n"
			+ "FROM payments_receipts_subheads\r\n"
			+ "WHERE payment_receipt_id IN (SELECT payment_receipt_id FROM payments_receipts WHERE transaction_type = 'P'  and security_id=:security_id AND date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy'))\r\n"
			+ "AND headid =:headid) t2,\r\n"
			+ "(SELECT CASE WHEN COALESCE(SUM(debit), 0.00) IS NULL THEN 0 ELSE COALESCE(SUM(debit), 0.00) END AS jv_debit,\r\n"
			+ "CASE WHEN COALESCE(SUM(credit), 0.00) IS NULL THEN 0 ELSE COALESCE(SUM(credit), 0.00) END AS jv_credit,\r\n"
			+ "COALESCE(COUNT(DISTINCT(payment_receipt_id)), 0) AS jv_count   FROM journal_voucher_heads j\r\n"
			+ "  WHERE j.payment_receipt_id IN (SELECT payment_receipt_id FROM journal_voucher WHERE  security_id=:security_id and date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy'))\r\n"
			+ "  AND headid =:headid) t3", nativeQuery = true)

	List<Map<String, String>> GetHeadWise_Details_Report(String fromDate, String toDate, String headid,
			String security_id);

	// SubLedger Report
	@Query(value = "select row_number() over(order by payment_receipt_id) as sno,payment_receipt_id,description,debit,credit,to_char(date,'dd/mm/yyyy') from ((SELECT payment_receipt_id,(select receipt_description from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 \r\n"
			+ "end as debit,case when payment_receipt_id like 'R%' or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as date from payments_receipts_subheads p where  payment_receipt_id not in (select payment_receipt_id \r\n"
			+ "from payments_receipts_incomplete where security_id=:security_id) and  payment_receipt_id in (select payment_receipt_id from payments_receipts where \r\n"
			+ "date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy'))  and security_id =:security_id\r\n"
			+ "group by payment_receipt_id) \r\n" + "union all \r\n"
			+ "(SELECT payment_receipt_id,(select receipt_description from payments_receipts where payment_receipt_id =p.payment_receipt_id) \r\n"
			+ "as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 end as debit,case when payment_receipt_id like 'R%' \r\n"
			+ "or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts where payment_receipt_id =p.payment_receipt_id) as date \r\n"
			+ "from payments_receipts_incomplete p where  payment_receipt_id in (select payment_receipt_id from payments_receipts where  \r\n"
			+ "date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy'))  and security_id =:security_id\r\n"
			+ "group by payment_receipt_id) \r\n" + "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id),\r\n"
			+ "sum(debit) as debit,sum(credit) as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from journal_voucher_heads j \r\n"
			+ "where payment_receipt_id in (select payment_receipt_id from journal_voucher where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) \r\n"
			+ " and security_id =:security_id group by payment_receipt_id) \r\n" + "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id) as description,sum(debit) as debit,sum(credit) \r\n"
			+ "as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from jv_incomplete j where   payment_receipt_id in \r\n"
			+ "(select payment_receipt_id from journal_voucher where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) \r\n"
			+ " and security_id =:security_id\r\n"
			+ "group by payment_receipt_id) )abcd order by date,payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> getSubLedgerReportAll(String fromDate, String toDate, String security_id);

	@Query(value = "select row_number() over(order by payment_receipt_id) as sno,payment_receipt_id,description,debit,credit,to_char(date,'dd/mm/yyyy') from ((SELECT payment_receipt_id,(select receipt_description from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 \r\n"
			+ "end as debit,case when payment_receipt_id like 'R%' or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as date from payments_receipts_subheads p where  payment_receipt_id not in (select payment_receipt_id \r\n"
			+ "from payments_receipts_incomplete where security_id=:security_id) and  payment_receipt_id in (select payment_receipt_id from payments_receipts where \r\n"
			+ "date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) and headid=:headid and security_id =:security_id \r\n"
			+ "group by payment_receipt_id) \r\n" + "union all \r\n"
			+ "(SELECT payment_receipt_id,(select receipt_description from payments_receipts where payment_receipt_id =p.payment_receipt_id) \r\n"
			+ "as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 end as debit,case when payment_receipt_id like 'R%' \r\n"
			+ "or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts where payment_receipt_id =p.payment_receipt_id) as date \r\n"
			+ "from payments_receipts_incomplete p where  payment_receipt_id in (select payment_receipt_id from payments_receipts where date \r\n"
			+ "between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) and headid=:headid and security_id =:security_id \r\n"
			+ "group by payment_receipt_id) \r\n" + "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id),\r\n"
			+ "sum(debit) as debit,sum(credit) as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from journal_voucher_heads j \r\n"
			+ "where payment_receipt_id in (select payment_receipt_id from journal_voucher where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) "
			+ "and headid=:headid and security_id =:security_id group by payment_receipt_id) \r\n" + "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id) as description,sum(debit) as debit,sum(credit) \r\n"
			+ "as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from jv_incomplete j where   payment_receipt_id in \r\n"
			+ "(select payment_receipt_id from journal_voucher where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) and headid=:headid \r\n"
			+ " and security_id =:security_id group by payment_receipt_id) )abcd order by date,payment_receipt_id ", nativeQuery = true)
	List<Map<String, String>> getSubLedgerReportByheadids(String fromDate, String toDate, String security_id,
			String headid);

	@Query(value = "select row_number() over(order by payment_receipt_id) as sno,payment_receipt_id,description,debit,credit,to_char(date,'dd/mm/yyyy') from ((SELECT payment_receipt_id,(select receipt_description from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 \r\n"
			+ "end as debit,case when payment_receipt_id like 'R%' or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts \r\n"
			+ "where payment_receipt_id =p.payment_receipt_id) as date from payments_receipts_subheads p where  payment_receipt_id not in (select payment_receipt_id \r\n"
			+ "from payments_receipts_incomplete where security_id=:security_id) and  payment_receipt_id in (select payment_receipt_id from payments_receipts where \r\n"
			+ "date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) and headid=:headid and p.subheadseqid=:subheadseqid and security_id =:security_id \r\n"
			+ "group by payment_receipt_id) \r\n" + "union all \r\n"
			+ "(SELECT payment_receipt_id,(select receipt_description from payments_receipts where payment_receipt_id =p.payment_receipt_id) \r\n"
			+ "as description,case when payment_receipt_id like 'P%' or payment_receipt_id like '2%' then sum(amount) else 0.00 end as debit,case when payment_receipt_id like 'R%' \r\n"
			+ "or payment_receipt_id like '1%' then sum(amount) else 0.00 end as credit,(select date from payments_receipts where payment_receipt_id =p.payment_receipt_id) as date \r\n"
			+ "from payments_receipts_incomplete p where  payment_receipt_id in (select payment_receipt_id from payments_receipts where date \r\n"
			+ "between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) and headid=:headid and p.subheadseqid=:subheadseqid and security_id =:security_id \r\n"
			+ "group by payment_receipt_id) \r\n" + "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id),\r\n"
			+ "sum(debit) as debit,sum(credit) as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from journal_voucher_heads j \r\n"
			+ "where payment_receipt_id in (select payment_receipt_id from journal_voucher where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) "
			+ "and headid=:headid and j.subheadid=:subheadseqid and security_id =:security_id group by payment_receipt_id) \r\n"
			+ "union all \r\n"
			+ "( SELECT payment_receipt_id,(select description from journal_voucher where payment_receipt_id =j.payment_receipt_id) as description,sum(debit) as debit,sum(credit) \r\n"
			+ "as credit,(select date  from journal_voucher where payment_receipt_id =j.payment_receipt_id) as date from jv_incomplete j where   payment_receipt_id in \r\n"
			+ "(select payment_receipt_id from journal_voucher where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) and headid=:headid \r\n"
			+ "and j.subheadseqid=:subheadseqid and security_id =:security_id group by payment_receipt_id) )abcd order by date,payment_receipt_id ", nativeQuery = true)
	List<Map<String, String>> getSubLedgerReportByIDs(String fromDate, String toDate, String security_id, String headid,
			String subheadseqid);

//Journal Register

//	@Query(value = "select payment_receipt_id,cast(voucher_id as int) as vid,to_char(date,'dd/mm/yyyy'),description,debit,credit from journal_voucher j\r\n" + 
//			"where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')  and security_id=:security_id and isdelete='f'  order by vid,date", nativeQuery = true)
	@Query(value = "select j.payment_receipt_id,cast(voucher_id as int) as vid,to_char(date,'dd/mm/yyyy'),description,j.debit,j.credit,j.upload_copy,\r\n"
			+ "jh.headid||'-'||(select headname from mstheads where headid=jh.headid)as heads,\r\n"
			+ "case subheadid  when 'No' then 'No Subheads' else (select subheadname from mstsubheads ms where ms.subheadseqid=jh.subheadid) end,jh.debit as headsdebit,jh.credit as headscredit\r\n"
			+ " from journal_voucher j\r\n"
			+ "left join journal_voucher_heads jh on jh.payment_receipt_id=j.payment_receipt_id\r\n"
			+ "where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')  and j.security_id=:security_id and j.isdelete='f'  order by vid,date", nativeQuery = true)
	List<Map<String, String>> GetJournalRegister_Report(String fromDate, String toDate, String security_id);

	@Query(value = "select payment_receipt_id,headid||'-'||(select headname from mstheads where headid=j.headid)headname,case subheadid  when 'No' then 'No Subheads' else (select subheadname from mstsubheads ms where ms.subheadseqid=j.subheadid) end,debit,credit\r\n"
			+ " from journal_voucher_heads j\r\n"
			+ " where payment_receipt_id=:payment_reciept_id order by debit desc,headid,credit desc", nativeQuery = true)
	List<Map<String, String>> getPaymentDataForReceiptHeads(String payment_reciept_id);

//General Leadger
	@Query(value = "select row_number() over(order by ps.payment_receipt_id) as sno,m.headid,m.headname,ps.payment_receipt_id as payment_receipt_id ,to_char(date,'dd/mm/yyyy') as date,\r\n"
			+ "receiptno,receipt_description as description,p.balance_in_account,\r\n"
			+ "case when ps.payment_receipt_id like 'P%' or ps.payment_receipt_id like '2%' then amount else '0.00' end as debit,\r\n"
			+ "case when ps.payment_receipt_id like 'R%' or ps.payment_receipt_id like '1%' then amount else '0.00' end as credit,user_id\r\n"
			+ "from mstheads m \r\n" + "inner join payments_receipts_subheads ps on m.headid=ps.headid\r\n"
			+ "inner join payments_receipts p on p.payment_receipt_id=ps.payment_receipt_id\r\n"
			+ "where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and p.security_id=:security_id  \r\n"
			+ "union all\r\n"
			+ "SELECT row_number() over(order by a.payment_receipt_id) as sno,headid,headname,a.payment_receipt_id as payment_receipt_id,to_char(b.date,'dd/mm/yyyy') as date,\r\n"
			+ "b.voucher_id,description,pr.balance_in_account,a.debit as debit,a.credit as credit,'00' as userid from (SELECT m.headid,m.headname ,p.payment_receipt_id,\r\n"
			+ "coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit from mstheads m \r\n"
			+ "left join journal_voucher_heads p on p.headid=m.headid and p.security_id=:security_id and p.payment_receipt_id in \r\n"
			+ "(select payment_receipt_id from journal_voucher where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) \r\n"
			+ "order by m.headid)a,journal_voucher b,payments_receipts pr where a.payment_receipt_id=b.payment_receipt_id and pr.payment_receipt_id=b.payment_receipt_id \r\n"
			+ "order by headid,date", nativeQuery = true)
	List<Map<String, String>> getGeneralLedgerReportAll(String fromDate, String toDate, String security_id);

	@Query(value = "select row_number() over(order by ps.payment_receipt_id) as sno,m.headid,m.headname,\r\n"
			+ "ps.payment_receipt_id as payment_receipt_id ,to_char(date,'dd/mm/yyyy') as date,receiptno,\r\n"
			+ "receipt_description as description,p.balance_in_account,\r\n"
			+ "case when ps.payment_receipt_id like 'P%' or ps.payment_receipt_id like '2%' then amount else '0.00' end as debit,\r\n"
			+ "case when ps.payment_receipt_id like 'R%' or ps.payment_receipt_id like '1%' then amount else '0.00' end as credit,user_id from mstheads m \r\n"
			+ "inner join payments_receipts_subheads ps on m.headid=ps.headid\r\n"
			+ "inner join payments_receipts p on p.payment_receipt_id=ps.payment_receipt_id\r\n"
			+ "where m.headid=:headid and date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and p.security_id=:security_id \r\n"
			+ "union all\r\n"
			+ "SELECT row_number() over(order by a.payment_receipt_id) as sno,headid,headname,a.payment_receipt_id as payment_receipt_id,to_char(b.date,'dd/mm/yyyy') as date,\r\n"
			+ "b.voucher_id,description,pr.balance_in_account,\r\n"
			+ "a.debit as debit,a.credit as credit,'00' as userid from (SELECT m.headid,m.headname ,p.payment_receipt_id,coalesce(debit,0.00) as debit,\r\n"
			+ "coalesce(credit,0.00) as credit from mstheads m \r\n"
			+ "left join journal_voucher_heads p on p.headid=m.headid and p.security_id=:security_id and p.payment_receipt_id in \r\n"
			+ "(select payment_receipt_id from journal_voucher where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')) \r\n"
			+ "and m.headid=:headid order by m.headid)a,journal_voucher b\r\n"
			+ "left join payments_receipts pr on pr.payment_receipt_id=b.payment_receipt_id\r\n"
			+ "where a.payment_receipt_id=b.payment_receipt_id  order by headid,date", nativeQuery = true)
	List<Map<String, String>> getGeneralLedgerReportByHeadID(String fromDate, String toDate, String security_id,
			String headid);

//EXPENDITURE ANAYSIS Districts
	@Query(value = "select code,name,coalesce(regular, round((regular_pay+regular_debit)/100000.00,1),'0') as regular,\r\n"
			+ "coalesce(regular_pay,0.00)+coalesce(regular_debit,0.00) as reg_exp, \r\n"
			+ "coalesce(conveyance,round((conveyance_pay+conveyance_debit)/100000.00,1),0.0) as conveyance, coalesce(conveyance_pay,0.00)+coalesce(conveyance_debit,0.00) as conveyance_exp, \r\n"
			+ "coalesce( administative,round((administrative_pay+administrative_debit)/100000.00,1),0.0) as administative , \r\n"
			+ "coalesce(administrative_pay,0.00)+coalesce(administrative_debit,0.00) as administative_exp, \r\n"
			+ "coalesce( others,round((others_pay+Others_debit)/100000.00,1),0.0) as others, \r\n"
			+ "coalesce(others_pay,0.00)+coalesce(Others_debit,0.00) as others_exp, \r\n"
			+ "coalesce(regular, round((regular_pay+regular_debit)/100000.00,1),0.0)+\r\n"
			+ "coalesce(conveyance,round((conveyance_pay+conveyance_debit)/100000.00,1),0.0 )+coalesce(administative,round((administrative_pay+administrative_debit)/100000.00,1),0.0) +coalesce(others,round((others_pay+Others_debit)/100000.00,1),0.0)  as tot, \r\n"
			+ "coalesce((regular_pay+regular_debit+conveyance_pay+conveyance_debit+administrative_pay+administrative_debit+others_pay+Others_debit),0.00) as tot_exp, \r\n"
			+ "coalesce( capital_exp,round((capital_pay+capital_debit)/100000.00,1),0.0 ) as capital_exp,\r\n"
			+ "coalesce((capital_pay+capital_debit),0.00) as capital_expenditure, \r\n"
			+ "coalesce( adv_staff,round((advance_pay+advance__debit)/100000.00,1),0.0)as adv_staff,\r\n"
			+ "coalesce((advance_pay+advance__debit),0.00) as advance_exp , +\r\n"
			+ "coalesce( liabilities,round((liabilities_pay+liabilities_debit)/100000.00,1),0.0)as liabilities, \r\n"
			+ "coalesce((liabilities_pay+liabilities_debit),0.0)as liabilities_exp,\r\n"
			+ "coalesce(((liabilities_pay+liabilities_debit)-liabilities_rec),0.00) as liabilities_amount  \r\n"
			+ "from cgg_master_districts   join ( \r\n"
			+ "select regular,conveyance,administative, others, capital_exp, adv_staff,liabilities,code ,timestamp from ( \r\n"
			+ "select coalesce(regular,0)+coalesce(out_sourcing,0 ) as regular ,conveyance,administative,others,capital_exp,adv_staff,liabilities,code,timestamp from update_budget_limits \r\n"
			+ "	where  code=:security_id order by timestamp ) bu  \r\n"
			+ "  join   (select code,max(timestamp) as timestamp from \r\n"
			+ "(select code, timestamp from budget_limits b   \r\n"
			+ "union select code, timestamp from update_budget_limits where code=:security_id order by timestamp ) t\r\n"
			+ "where  date(timestamp) between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and code=:security_id group by code ) t using (code,timestamp)  )\r\n"
			+ "a using (code)  \r\n" + "left join( \r\n"
			+ "select a.security_id as code,sum(case when classification ='01' then amount else 0.00 end )as regular_pay , \r\n"
			+ "sum(case when classification ='02' then amount else 0.00 end )as conveyance_pay , \r\n"
			+ "sum(case when classification ='03' then amount else 0.00 end )as administrative_pay , \r\n"
			+ "sum(case when classification ='04' then amount else 0.00 end )as capital_pay , \r\n"
			+ "sum(case when classification ='05' then amount else 0.00 end )as others_pay , \r\n"
			+ "sum(case when classification ='07' then amount else 0.00 end )as advance_pay , \r\n"
			+ "sum(case when classification ='08' then amount else 0.00 end )as liabilities_pay \r\n"
			+ "from payments_receipts_subheads a join payments_receipts b using (payment_receipt_id ) join mstheads using(headid) where transaction_type='P' \r\n"
			+ "and date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')  and a.security_id=:security_id  group by a.security_id) b using (code) \r\n"
			+ "left join ( select j.security_id as code,sum(case when classification ='01' then  h.debit - h.credit else 0.00 end )as regular_debit , \r\n"
			+ "sum(case when classification ='02' then h.debit - h.credit else 0.00 end )as conveyance_debit , \r\n"
			+ "sum(case when classification ='03' then h.debit - h.credit else 0.00 end )as administrative_debit ,\r\n"
			+ "sum(case when classification ='04' then h.debit - h.credit else 0.00 end )as capital_debit , \r\n"
			+ "sum(case when classification ='05' then h.debit - h.credit else 0.00 end )as  others_debit ,\r\n"
			+ "sum(case when classification ='07' then h.debit - h.credit else 0.00 end )as advance__debit ,\r\n"
			+ "sum(case when classification ='08' then h.debit - h.credit else 0.00 end )as liabilities_debit \r\n"
			+ "from journal_voucher j join journal_voucher_heads h using(payment_receipt_id,security_id) join mstheads using(headid) where \r\n"
			+ "date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')  and j.security_id=:security_id   group by j.security_id ) c using (code) \r\n"
			+ "left join\r\n"
			+ "(select a.security_id as code,sum(case when classification ='08' then amount else 0.00 end )as liabilities_rec from payments_receipts_subheads a \r\n"
			+ "join payments_receipts b using (payment_receipt_id ) join mstheads using(headid) where transaction_type='R' and \r\n"
			+ "date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')  and a.security_id=:security_id   group by a.security_id) rec using (code) order by code", nativeQuery = true)
	List<Map<String, String>> GetExpenditureAnalaysis_Report(String fromDate, String toDate, String security_id);

	@Query(value = "select * ,(CASE WHEN liabilities_amount>=0 THEN 'DR' ELSE 'CR' END) DR_CR from (select code,name,coalesce(regular, round((regular_pay+regular_debit)/100000.00,1),'0') as regular,\r\n"
			+ "coalesce(regular_pay,0.00)+coalesce(regular_debit,0.00) as reg_exp, \r\n"
			+ "coalesce(conveyance,round((conveyance_pay+conveyance_debit)/100000.00,1),0.0) as conveyance, coalesce(conveyance_pay,0.00)+coalesce(conveyance_debit,0.00) as conveyance_exp, \r\n"
			+ "coalesce( administative,round((administrative_pay+administrative_debit)/100000.00,1),0.0) as administative , \r\n"
			+ "coalesce(administrative_pay,0.00)+coalesce(administrative_debit,0.00) as administative_exp, \r\n"
			+ "coalesce( others,round((others_pay+Others_debit)/100000.00,1),0.0) as others, \r\n"
			+ "coalesce(others_pay,0.00)+coalesce(Others_debit,0.00) as others_exp, \r\n"
			+ " coalesce(regular, round((regular_pay+regular_debit)/100000.00,1),0.0)+\r\n"
			+ " coalesce(conveyance,round((conveyance_pay+conveyance_debit)/100000.00,1),0.0 )+coalesce(administative,round((administrative_pay+administrative_debit)/100000.00,1),0.0) +coalesce(others,round((others_pay+Others_debit)/100000.00,1),0.0)  as tot, \r\n"
			+ " coalesce((regular_pay+regular_debit+conveyance_pay+conveyance_debit+administrative_pay+administrative_debit+others_pay+Others_debit),0.00) as tot_exp, \r\n"
			+ "coalesce( capital_exp,round((capital_pay+capital_debit)/100000.00,1),0.0 ) as capital_exp,\r\n"
			+ "coalesce((capital_pay+capital_debit),0.00) as capital_expenditure, \r\n"
			+ "coalesce( adv_staff,round((advance_pay+advance__debit)/100000.00,1),0.0)as adv_staff,\r\n"
			+ "coalesce((advance_pay+advance__debit),0.00) as advance_exp , +\r\n"
			+ "coalesce( liabilities,round((liabilities_pay+liabilities_debit)/100000.00,1),0.0)as liabilities, \r\n"
			+ "coalesce((liabilities_pay+liabilities_debit),0.0)as liabilities_exp,\r\n"
			+ "coalesce(((liabilities_pay+liabilities_debit)-liabilities_rec),0.00) as liabilities_amount  \r\n"
			+ "from cgg_master_districts left join ( \r\n"
			+ "select regular,conveyance,administative, others, capital_exp, adv_staff,liabilities,code ,timestamp from ( \r\n"
			+ "select coalesce(regular,0)+coalesce(out_sourcing,0 ) as regular ,conveyance,administative,others,capital_exp,adv_staff,liabilities,code,timestamp from update_budget_limits ) bu  \r\n"
			+ "join  \r\n" + "(select code,max(timestamp) as timestamp from \r\n"
			+ "(select code, timestamp from budget_limits b union select code, timestamp from update_budget_limits ) t\r\n"
			+ " where  date(timestamp) between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') group by code ) t using (code,timestamp) ) a using (code)  \r\n"
			+ "left join( \r\n"
			+ "select a.security_id as code,sum(case when classification ='01' then amount else 0.00 end )as regular_pay , \r\n"
			+ " sum(case when classification ='02' then amount else 0.00 end )as conveyance_pay , \r\n"
			+ " sum(case when classification ='03' then amount else 0.00 end )as administrative_pay , \r\n"
			+ " sum(case when classification ='04' then amount else 0.00 end )as capital_pay , \r\n"
			+ "sum(case when classification ='05' then amount else 0.00 end )as others_pay , \r\n"
			+ " sum(case when classification ='07' then amount else 0.00 end )as advance_pay , \r\n"
			+ "sum(case when classification ='08' then amount else 0.00 end )as liabilities_pay \r\n"
			+ "from payments_receipts_subheads a join payments_receipts b using (payment_receipt_id ) join mstheads using(headid) where transaction_type='P' \r\n"
			+ "and date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')  group by a.security_id) b using (code) \r\n"
			+ " left join ( select j.security_id as code,sum(case when classification ='01' then  h.debit - h.credit else 0.00 end )as regular_debit , \r\n"
			+ "sum(case when classification ='02' then h.debit - h.credit else 0.00 end )as conveyance_debit , \r\n"
			+ "sum(case when classification ='03' then h.debit - h.credit else 0.00 end )as administrative_debit ,\r\n"
			+ "sum(case when classification ='04' then h.debit - h.credit else 0.00 end )as capital_debit , \r\n"
			+ "sum(case when classification ='05' then h.debit - h.credit else 0.00 end )as  others_debit ,\r\n"
			+ "sum(case when classification ='07' then h.debit - h.credit else 0.00 end )as advance__debit ,\r\n"
			+ "sum(case when classification ='08' then h.debit - h.credit else 0.00 end )as liabilities_debit \r\n"
			+ "from journal_voucher j join journal_voucher_heads h using(payment_receipt_id,security_id) join mstheads using(headid) where \r\n"
			+ " date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')  group by j.security_id ) c using (code) \r\n"
			+ " left join\r\n"
			+ "(select a.security_id as code,sum(case when classification ='08' then amount else 0.00 end )as liabilities_rec from payments_receipts_subheads a \r\n"
			+ " join payments_receipts b using (payment_receipt_id ) join mstheads using(headid) where transaction_type='R' and \r\n"
			+ " date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')  group by a.security_id) rec using (code) order by code) a", nativeQuery = true)
	List<Map<String, String>> GetExpenditureAnalaysis_Report_Apshcl(String fromDate, String toDate);

	// expenditure analysisonclick
	@Query(value = "SELECT * ,(CASE WHEN amount>=0 THEN 'DR' ELSE 'CR' END) DR_CR  FROM (\r\n"
			+ "select classification,headid,headname ,sum(debit) as debit ,sum(credit) as cr_amount,abs(sum(amount)) as amount from (  \r\n"
			+ "select   classification,headid  ,headname ,coalesce(sum(amount),0.00) as debit,0 as credit,coalesce(sum(amount),0.00) as amount  from mstheads  \r\n"
			+ "left join payments_receipts_subheads p  using(headid)  join payments_receipts using(payment_receipt_id)  \r\n"
			+ "where  transaction_type='P' and  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and classification=:classification\r\n"
			+ "and p.security_id=:security_id   group by   headid,headname,classification  \r\n"
			+ "union all select  classification, headid  ,headname, coalesce(sum(p.debit ),0.00) as debit ,coalesce(sum(  p.credit),0.00) as credit, coalesce(sum(p.debit - p.credit),0.00) as amount  from mstheads   \r\n"
			+ "left join journal_voucher_heads p  using(headid)  join journal_voucher using(payment_receipt_id)   where   date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')\r\n"
			+ "and p.security_id=:security_id and classification=:classification group by headid ,headname,classification ) am \r\n"
			+ "group by  headid,headname, classification  order by headid) a", nativeQuery = true)
	List<Map<String, String>> GetExpenditureAnalaysis_ReportDistrictOnclick(String fromDate, String toDate,
			String classification, String security_id);

	@Query(value = "SELECT * ,(CASE WHEN amount>=0 THEN 'DR' ELSE 'CR' END) DR_CR  FROM (select classification,headid,headname ,sum(debit) as debit ,sum(credit) as cr_amount,abs(sum(amount)) as amount from (  \r\n"
			+ "select   classification,headid  ,headname ,coalesce(sum(amount),0.00) as debit,0 as credit,coalesce(sum(amount),0.00) as amount  from mstheads  \r\n"
			+ "left join payments_receipts_subheads p  using(headid)  join payments_receipts using(payment_receipt_id)  \r\n"
			+ "where  transaction_type='P' and  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and classification='08' \r\n"
			+ "and p.security_id=:security_id   group by   headid,headname,classification  \r\n"
			+ "union all select  classification, headid  ,headname, coalesce(sum(p.debit ),0.00) as debit ,coalesce(sum(  p.credit),0.00) as credit, coalesce(sum(p.debit - p.credit),0.00) as amount  from mstheads   \r\n"
			+ "left join journal_voucher_heads p  using(headid)  join journal_voucher using(payment_receipt_id)   where   date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') \r\n"
			+ "and p.security_id=:security_id and classification='08' group by headid ,headname,classification ) am \r\n"
			+ "group by  headid,headname, classification  order by headid) a", nativeQuery = true)
	List<Map<String, String>> GetExpenditureAnalaysis_ReportDistrictOnclick08(String fromDate, String toDate,
			String security_id); 

	@Query(value = "SELECT * ,(CASE WHEN amount>=0 THEN 'DR' ELSE 'CR' END) DR_CR  FROM (select classification,headid,headname ,sum(debit) as debit ,sum(credit) as cr_amount,abs(sum(amount)) as amount from (  \r\n"
			+ "select classification,  headid  ,headname ,coalesce(sum(amount),0.00) as debit,0 as credit,coalesce(sum(amount),0.00) as amount  from mstheads   left join payments_receipts_subheads p  using(headid)  join payments_receipts using(payment_receipt_id)  \r\n"
			+ " where  transaction_type='P' and  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and classification=:classification   and p.security_id=:security_id   group by   headid,headname ,classification \r\n"
			+ "union all select  classification, headid  ,headname, coalesce(sum(p.debit ),0.00) as debit ,coalesce(sum(  p.credit),0.00) as credit, coalesce(sum(p.debit - p.credit),0.00) as amount  from mstheads   \r\n"
			+ "left join journal_voucher_heads p  using(headid)  join journal_voucher using(payment_receipt_id)   where   date between to_date(cast(:fromDate as text),'dd/mm/yyyy')  and to_date(cast(:toDate as text),'dd/mm/yyyy')  \r\n"
			+ "and p.security_id=:security_id and classification=:classification group by headid ,headname,classification ) am group by  headid,headname,am.amount,classification  order by headid) a", nativeQuery = true)
	List<Map<String, String>> GetExpenditureAnalaysis_Report_ApshclOnclick(String fromDate, String toDate,
			String classification, String security_id);

	@Query(value = "SELECT * ,(CASE WHEN amount>=0 THEN 'DR' ELSE 'CR' END) DR_CR  FROM (select classification,headid,headname ,sum(debit) as debit ,sum(credit) as cr_amount,abs(sum(amount)) as amount from (  \r\n"
			+ "select  classification, headid  ,headname ,coalesce(sum(amount),0.00) as debit,0 as credit,coalesce(sum(amount),0.00) as amount  from mstheads   left join payments_receipts_subheads p  using(headid)  join payments_receipts using(payment_receipt_id)  \r\n"
			+ " where  transaction_type='P' and  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and classification='08'   and p.security_id=:security_id   group by   headid,headname,classification  \r\n"
			+ "union all select classification,  headid  ,headname, coalesce(sum(p.debit ),0.00) as debit ,coalesce(sum(  p.credit),0.00) as credit, coalesce(sum(p.debit - p.credit),0.00) as amount  from mstheads   \r\n"
			+ "left join journal_voucher_heads p  using(headid)  join journal_voucher using(payment_receipt_id)   where   date between to_date(cast(:fromDate as text),'dd/mm/yyyy')  and to_date(cast(:toDate as text),'dd/mm/yyyy')  \r\n"
			+ "and p.security_id=:security_id and classification='08' group by headid ,headname,classification ) am group by  headid,headname,am.amount,classification  order by headid) a", nativeQuery = true)
	List<Map<String, String>> GetExpenditureAnalaysis_Report_ApshclOnclick08(String fromDate, String toDate,
			String security_id);

	@Query(value = "select payment_receipt_id,date,receipt_description,headid,headname ,sum(debit) as debit ,sum(credit) as credit,sum(amount) as amount,(CASE WHEN amount>=0 THEN 'DR' ELSE 'CR' END) DR_CR  from (   \r\n"
			+ "select  payment_receipt_id,date,receipt_description,headid  ,headname ,coalesce(sum(amount),0.00) as debit,0 as credit,coalesce(sum(amount),0.00) as amount  from mstheads   left join payments_receipts_subheads p \r\n"
			+ " using(headid)  join payments_receipts using(payment_receipt_id)   \r\n"
			+ " where  transaction_type='P' and  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and classification=:classification  and p.security_id=:security_id and headid=:headid  group by   headid,headname, payment_receipt_id,date,receipt_description\r\n"
			+ "union all \r\n"
			+ "select  payment_receipt_id,date,description, headid  ,headname, coalesce(sum(p.debit ),0.00) as debit ,coalesce(sum(  p.credit),0.00) as credit, coalesce(sum(p.debit - p.credit),0.00) as amount  from mstheads    \r\n"
			+ "left join journal_voucher_heads p  using(headid)  join journal_voucher using(payment_receipt_id)   where   date between to_date(cast(:fromDate as text),'dd/mm/yyyy')  and to_date(cast(:toDate as text),'dd/mm/yyyy')   \r\n"
			+ "and p.security_id=:security_id and classification=:classification and headid=:headid group by headid ,headname,payment_receipt_id,date,description) am group by  headid,headname,am.amount,payment_receipt_id,date,receipt_description  order by headid", nativeQuery = true)
	List<Map<String, String>> GetExpenditureAnalaysis_OnClick_Headid(String fromDate, String toDate,
			String classification, String security_id, String headid);

	// Review Reports
	@Query(value = "select district_name,coalesce(openingbalance,0) as openingbalance,coalesce(debit,0) as debit,coalesce(credit,0) as credit,coalesce(openingbalance+(coalesce(debit,0)-coalesce(credit,0)),0) as current from (\r\n"
			+ "SELECT  district_name,(j.opening_balance+(coalesce(k.receiptamount,0.00)-coalesce(k.paymentamount,0.00))) as openingbalance,j.receipt debit,j.payment credit from \r\n"
			+ "(SELECT b.code,b.district_name,b.opening_balance,pr.payment,pr.receipt,(b.opening_balance+(pr.receipt-pr.payment))as current from \r\n"
			+ "(select d.code,d.name as District_Name,sum(b.balance) as Opening_Balance,sum(b.remaining_balance) as Current_Balance \r\n"
			+ "from cgg_master_districts d left join  bankdetails b on d.code=b.security_id group by b.security_id,d.name,d.code order by d.code)b left join \r\n"
			+ "(SELECT security_id,sum(paymentamount) as payment,sum(receiptamount) as receipt from payments_receipts where banknameaccountno!='' and \r\n"
			+ "date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') group by security_id order by security_id)pr on b.code=pr.security_id)j left join \r\n"
			+ "(SELECT security_id,sum(receiptamount) as receiptamount,sum(paymentamount) as paymentamount from payments_receipts \r\n"
			+ "where date < to_date(cast(:fromDate as text),'dd/mm/yyyy') group by security_id order by security_id)k on j.code=k.security_id	)a", nativeQuery = true)
	List<Map<String, String>> getstatewisedata(String fromDate, String toDate);

	@Query(value = "SELECT b.banknameaccountno,sum(current) opening_balance,sum(coalesce(debit,0.00)) debit,sum(coalesce(credit,0.00)) credit,\r\n"
			+ "SUM((CURRENT)+coalesce(DEBIT,0.00))-sum(coalesce(CREDIT,0.00)) closingbalnce FROM bankdetails B LEFT JOIN\r\n"
			+ "bank_opening_balance OB ON OB.banknameaccountno=B.banknameaccountno LEFT JOIN \r\n"
			+ "(SELECT banknameaccountno,sum(paymentamount) CREDIT ,sum(receiptamount) DEBIT  from payments_receipts \r\n"
			+ "where banknameaccountno!='' and date >=to_date(cast(:fromDate as text),'dd/mm/yyyy') AND DATE <=to_date(cast(:toDate as text),'dd/mm/yyyy') and security_id=:security_id\r\n"
			+ "group by banknameaccountno) A ON  a.banknameaccountno =B.banknameaccountno where  security_id=:security_id group by 1", nativeQuery = true)

	List<Map<String, String>> getDistBankWise(String fromDate, String toDate, String security_id);

//TO_CHAR(DATE cast(:fromDate as text), 'DD-MM-YYYY')
	// to_char(to_date(:fromDate,'dd/mm/yyyy'),'dd-mm-yyyy')
	@Query(value = "( SELECT '34451' headid,'OPENING BALANCE AS ON '||TO_DATE(cast(:fromDate as text),'dd/mm/yyyy')  HEADname,0 DEBIT,\r\n"
			+ "SUM(CURRENT)+sum(coalesce(DEBIT,0.00))-sum(coalesce(CREDIT,0.00)) CREDIT,0 netdebit,\r\n"
			+ "SUM(CURRENT)+sum(coalesce(DEBIT,0.00))-sum(coalesce(CREDIT,0.00))  netcredit FROM bankdetails B left  JOIN\r\n"
			+ "bank_opening_balance OB ON OB.banknameaccountno=B.banknameaccountno  left JOIN \r\n"
			+ "(SELECT banknameaccountno,sum(paymentamount) CREDIT ,sum(receiptamount) DEBIT  from payments_receipts \r\n"
			+ "where banknameaccountno!='' and date  >=TO_DATE('01/04/2022','dd/mm/yyyy') and date <TO_DATE(cast(:fromDate as text),'dd/mm/yyyy')  and  security_id not ilike 'EE%'\r\n"
			+ "group by banknameaccountno) A ON  a.banknameaccountno =B.banknameaccountnO where security_id not ilike 'EE%')\r\n"
			+ " union all\r\n" + "(select mh.HEADID  , HEADNAME,sum(DEBIT) DEBIT,sum(CREDIT) CREDIT ,\r\n"
			+ "coalesce(case when sum(debit)>sum(credit) then sum(debit)-sum(credit) end,0) netdebit,\r\n"
			+ "coalesce(case when sum(credit)>sum(debit) then sum(credit)-sum(debit) end,0) netcredit\r\n"
			+ "from MSTHEADS mh join (\r\n"
			+ "(SELECT HEADID , SUM(jvh.DEBIT) AS DEBIT , SUM(jvh.CREDIT) AS CREDIT \r\n"
			+ " FROM JOURNAL_VOUCHER_HEADS jvh\r\n"
			+ "join JOURNAL_VOUCHER jv on jvh.PAYMENT_RECEIPT_ID=jv.PAYMENT_RECEIPT_ID\r\n"
			+ " WHERE DATE>=TO_DATE(cast(:fromDate as text),'dd/mm/yyyy') AND DATE<=TO_DATE(cast(:toDate as text),'dd/mm/yyyy') and jv.security_id not ilike 'EE%'\r\n"
			+ " GROUP BY HEADID ORDER BY HEADID)  \r\n" + " union all\r\n"
			+ "(SELECT prs.HEADID ,SUM( CASE WHEN SUBSTR(prs.PAYMENT_RECEIPT_ID,1,1)='P' OR SUBSTR(prs.PAYMENT_RECEIPT_ID,1,1)='2' THEN AMOUNT ELSE 0 END) AS debit , \r\n"
			+ "SUM( CASE WHEN SUBSTR(prs.PAYMENT_RECEIPT_ID,1,1)='R' OR SUBSTR(prs.PAYMENT_RECEIPT_ID,1,1)='1' THEN AMOUNT ELSE 0 END) AS credit  \r\n"
			+ " FROM PAYMENTS_RECEIPTS_SUBHEADS prs\r\n"
			+ " join PAYMENTS_RECEIPTS pr on prs.PAYMENT_RECEIPT_ID=pr.PAYMENT_RECEIPT_ID\r\n"
			+ " WHERE pr.DATE>=TO_DATE(cast(:fromDate as text),'dd/mm/yyyy') AND pr.DATE<=TO_DATE(cast(:toDate as text),'dd/mm/yyyy')  and pr.security_id not ilike 'EE%'\r\n"
			+ " GROUP BY prs.HEADID  ORDER BY prs.HEADID) )p on p.HEADID=mh.HEADID group by mh.HEADID , HEADNAME order by mh.headid)\r\n"
			+ "   union all\r\n"
			+ "(  SELECT '40800' headid,'CASH AT BANK ON '||TO_DATE(cast(:toDate as text),'dd/mm/yyyy')   HEADNAME,\r\n"
			+ "SUM(CURRENT)+sum(coalesce(DEBIT,0.00))-sum(coalesce(CREDIT,0.00)) debit,0 credit,SUM(CURRENT)+sum(coalesce(DEBIT,0.00))-sum(coalesce(CREDIT,0.00))  netdebit,\r\n"
			+ "0 netcredit FROM bankdetails B left  JOIN\r\n"
			+ "bank_opening_balance OB ON OB.banknameaccountno=B.banknameaccountno  left JOIN \r\n"
			+ "(SELECT banknameaccountno,sum(paymentamount) CREDIT ,sum(receiptamount) DEBIT  from payments_receipts \r\n"
			+ "where banknameaccountno!='' and date  >=TO_DATE('01/04/2022','dd/mm/yyyy') and date <=TO_DATE(cast(:toDate as text),'dd/mm/yyyy')  and security_id not ilike 'EE%'\r\n"
			+ "   group by banknameaccountno) A ON  a.banknameaccountno =B.banknameaccountnO where security_id not ilike 'EE%')", nativeQuery = true)
	List<Map<String, String>> getTrailBlance_state(String fromDate, String toDate);

	// Based On Prakasham District changes
	@Query(value = "( SELECT '34451' headid,'OPENING BALANCE AS ON '||TO_DATE(cast(:fromDate as text),'dd/mm/yyyy')  HEADname,0 DEBIT,\r\n"
			+ "SUM(CURRENT)+sum(coalesce(DEBIT,0.00))-sum(coalesce(CREDIT,0.00)) CREDIT,0 netdebit,\r\n"
			+ "SUM(CURRENT)+sum(coalesce(DEBIT,0.00))-sum(coalesce(CREDIT,0.00))  netcredit FROM bankdetails B left  JOIN\r\n"
			+ "bank_opening_balance OB ON OB.banknameaccountno=B.banknameaccountno  left JOIN \r\n"
			+ "(SELECT banknameaccountno,sum(paymentamount) CREDIT ,sum(receiptamount) DEBIT  from payments_receipts \r\n"
			+ "where banknameaccountno!='' and date  >=TO_DATE('01/04/2022','dd/mm/yyyy') and date <TO_DATE(cast(:fromDate as text),'dd/mm/yyyy')  and security_id=:security_id \r\n"
			+ "group by banknameaccountno) A ON  a.banknameaccountno =B.banknameaccountnO where security_id=:security_id)\r\n"
			+ "union all\r\n" + "(select mh.HEADID  , HEADNAME,sum(DEBIT) DEBIT,sum(CREDIT) CREDIT ,\r\n"
			+ "coalesce(case when sum(debit)>sum(credit) then sum(debit)-sum(credit) end,0) netdebit,\r\n"
			+ "coalesce(case when sum(credit)>sum(debit) then sum(credit)-sum(debit) end,0) netcredit\r\n"
			+ "from MSTHEADS mh join (\r\n"
			+ "(SELECT HEADID , SUM(jvh.DEBIT) AS DEBIT , SUM(jvh.CREDIT) AS CREDIT \r\n"
			+ " FROM JOURNAL_VOUCHER_HEADS jvh\r\n"
			+ "join JOURNAL_VOUCHER jv on jvh.PAYMENT_RECEIPT_ID=jv.PAYMENT_RECEIPT_ID\r\n"
			+ " WHERE DATE>=TO_DATE(cast(:fromDate as text),'dd/mm/yyyy') AND DATE<=TO_DATE(cast(:toDate as text),'dd/mm/yyyy') and jv.security_id =:security_id\r\n"
			+ " GROUP BY HEADID ORDER BY HEADID)  \r\n" + " union all\r\n"
			+ " (SELECT prs.HEADID ,SUM( CASE WHEN SUBSTR(prs.PAYMENT_RECEIPT_ID,1,1)='P' OR SUBSTR(prs.PAYMENT_RECEIPT_ID,1,1)='2' THEN AMOUNT ELSE 0 END) AS debit , \r\n"
			+ "SUM( CASE WHEN SUBSTR(prs.PAYMENT_RECEIPT_ID,1,1)='R' OR SUBSTR(prs.PAYMENT_RECEIPT_ID,1,1)='1' THEN AMOUNT ELSE 0 END) AS credit  \r\n"
			+ " FROM PAYMENTS_RECEIPTS_SUBHEADS prs\r\n"
			+ " join PAYMENTS_RECEIPTS pr on prs.PAYMENT_RECEIPT_ID=pr.PAYMENT_RECEIPT_ID\r\n"
			+ " WHERE pr.DATE>=TO_DATE(cast(:fromDate as text),'dd/mm/yyyy') AND pr.DATE<=TO_DATE(cast(:toDate as text),'dd/mm/yyyy')  and pr.security_id =:security_id \r\n"
			+ " GROUP BY prs.HEADID  ORDER BY prs.HEADID) )p on p.HEADID=mh.HEADID group by mh.HEADID , HEADNAME order by mh.headid)\r\n"
			+ " union all\r\n"
			+ "(  SELECT '40800' headid,'CASH AT BANK ON '||TO_DATE(cast(:toDate as text),'dd/mm/yyyy')  HEADname,\r\n"
			+ "SUM(CURRENT)+sum(coalesce(DEBIT,0.00))-sum(coalesce(CREDIT,0.00)) debit,0 credit,SUM(CURRENT)+sum(coalesce(DEBIT,0.00))-sum(coalesce(CREDIT,0.00))  netdebit,\r\n"
			+ "0 netcredit FROM bankdetails B left  JOIN\r\n"
			+ "bank_opening_balance OB ON OB.banknameaccountno=B.banknameaccountno  left JOIN \r\n"
			+ "(SELECT banknameaccountno,sum(paymentamount) CREDIT ,sum(receiptamount) DEBIT  from payments_receipts \r\n"
			+ "where banknameaccountno!='' and date  >=TO_DATE('01/04/2022','dd/mm/yyyy') and date <=TO_DATE(cast(:toDate as text),'dd/mm/yyyy')  and security_id=:security_id\r\n"
			+ "group by banknameaccountno) A ON  a.banknameaccountno =B.banknameaccountnO where security_id=:security_id)", nativeQuery = true)

	List<Map<String, String>> getTrailBlance(String fromDate, String toDate, String security_id);

	@Query(value = "select sequence_order,payment_receipt_id,date_char,receipt_description,payment_receipt,dummy,DR_CR,abs(sum(payment_receipt-dummy) over(order by sequence_order,payment_receipt_id)) as balance,receiptno,date,transaction_type from (\r\n"
			+ "SELECT  row_number() over () as sequence_order,payment_receipt_id,date_char,receipt_description,payment_receipt,dummy, DR_CR,receiptno,date,transaction_type FROM (\r\n"
			+ "(SELECT a.payment_receipt_id,to_char(A.date,'dd/mm/yyyy') date_char,receipt_description,payment_receipt,cast(0 as numeric) as dummy,(CASE WHEN PAYMENT_RECEIPT=0 THEN 'CR' ELSE 'DR' END) DR_CR,\r\n"
			+ "b.receiptno,A.date,'P' as transaction_type from (SELECT (select date from payments_receipts pr where \r\n"
			+ "pr.payment_receipt_id = p.payment_receipt_id ),payment_receipt_id,coalesce(amount,0.00) as payment_receipt \r\n"
			+ "from mstheads m left join payments_receipts_subheads p on p.headid=m.headid and m.headid=:headid and 1=1  \r\n"
			+ "and payment_receipt_id in (select payment_receipt_id from payments_receipts where transaction_type='P' and  \r\n"
			+ "date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy'))  \r\n"
			+ "where m.headid=:headid  order by m.headid)a,payments_receipts b where a.payment_receipt_id=b.payment_receipt_id) \r\n"
			+ "union all \r\n"
			+ "( SELECT a.payment_receipt_id,to_char(A.date,'dd/mm/yyyy') date_char,receipt_description,cast(0 as numeric) as dummy,payment_receipt,(CASE WHEN PAYMENT_RECEIPT=0 THEN 'DR' ELSE 'CR' END) DR_CR,\r\n"
			+ "b.receiptno,A.date,'R' as transaction_type from (SELECT (select date from payments_receipts pr where pr.payment_receipt_id = p.payment_receipt_id ),\r\n"
			+ "payment_receipt_id,coalesce(amount,0.00) as payment_receipt from mstheads m left join payments_receipts_subheads p on p.headid=m.headid and m.headid=:headid   and 1=1 and\r\n"
			+ " payment_receipt_id in (select payment_receipt_id from payments_receipts where transaction_type='R' and  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') \r\n"
			+ " and to_date(cast(:toDate as text),'dd/mm/yyyy'))  where m.headid=:headid and 1=1 order by m.headid)a,payments_receipts b where \r\n"
			+ " a.payment_receipt_id=b.payment_receipt_id)\r\n" + " union all \r\n"
			+ " (SELECT a.payment_receipt_id,to_char(A.date,'dd/mm/yyyy') date_char,description,a.debit,a.credit,(CASE WHEN a.debit=0 THEN 'CR' ELSE 'DR' END) DR_CR,b.voucher_id,A.date,'J' as transaction_type from \r\n"
			+ " (SELECT (select date from journal_voucher jv where jv.payment_receipt_id = p.payment_receipt_id ),payment_receipt_id,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit \r\n"
			+ " from mstheads m left join journal_voucher_heads p on p.headid=m.headid and m.headid=:headid and  payment_receipt_id in \r\n"
			+ " (select payment_receipt_id from journal_voucher where  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') \r\n"
			+ " and to_date(cast(:toDate as text),'dd/mm/yyyy'))  where m.headid=:headid  order by m.headid)a,journal_voucher b \r\n"
			+ "where a.payment_receipt_id=b.payment_receipt_id) order by date,transaction_type) A order by date,transaction_type ) b", nativeQuery = true)
	List<Map<String, String>> getTrail_statewiseonclick(String fromDate, String toDate, String headid);

	@Query(value = "select sequence_order,payment_receipt_id,date_char,receipt_description,payment_receipt,dummy,DR_CR,abs(sum(payment_receipt-dummy) over(order by sequence_order,payment_receipt_id)) as balance,receiptno,date,transaction_type from (\r\n"
			+ "SELECT  row_number() over () as sequence_order,payment_receipt_id,date_char,receipt_description,payment_receipt,dummy, DR_CR,receiptno,date,transaction_type \r\n"
			+ "		FROM (\r\n"
			+ "(SELECT a.payment_receipt_id,to_char(A.date,'dd/mm/yyyy') date_char,receipt_description,payment_receipt,cast(0 as numeric) as dummy,(CASE WHEN PAYMENT_RECEIPT=0 THEN 'CR' ELSE 'DR' END) DR_CR,\r\n"
			+ "b.receiptno,A.date,'P' as transaction_type from (SELECT (select date from payments_receipts pr where \r\n"
			+ "pr.payment_receipt_id = p.payment_receipt_id ),payment_receipt_id,coalesce(amount,0.00) as payment_receipt \r\n"
			+ "from mstheads m left join payments_receipts_subheads p on p.headid=m.headid and m.headid=:headid and security_id=:security_id \r\n"
			+ "and payment_receipt_id in (select payment_receipt_id from payments_receipts where transaction_type='P' and  \r\n"
			+ "date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy'))  \r\n"
			+ "where m.headid=:headid and security_id=:security_id order by m.headid)a,payments_receipts b where a.payment_receipt_id=b.payment_receipt_id) \r\n"
			+ "union all \r\n"
			+ "( SELECT a.payment_receipt_id,to_char(A.date,'dd/mm/yyyy') date_char,receipt_description,cast(0 as numeric)as dummy,payment_receipt,(CASE WHEN PAYMENT_RECEIPT=0 THEN 'DR' ELSE 'CR' END) DR_CR,\r\n"
			+ "b.receiptno,A.date,'R' as transaction_type from (SELECT (select date from payments_receipts pr where pr.payment_receipt_id = p.payment_receipt_id ),\r\n"
			+ "payment_receipt_id,coalesce(amount,0.00) as payment_receipt from mstheads m left join payments_receipts_subheads p on p.headid=m.headid and m.headid=:headid   and security_id=:security_id and\r\n"
			+ " payment_receipt_id in (select payment_receipt_id from payments_receipts where transaction_type='R' and  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') \r\n"
			+ " and to_date(cast(:toDate as text),'dd/mm/yyyy'))  where m.headid=:headid  and security_id=:security_id order by m.headid)a,payments_receipts b where \r\n"
			+ " a.payment_receipt_id=b.payment_receipt_id) \r\n" + " union all \r\n"
			+ " (SELECT a.payment_receipt_id,to_char(A.date,'dd/mm/yyyy') date_char,description,a.debit,a.credit,(CASE WHEN a.debit=0 THEN 'CR' ELSE 'DR' END) DR_CR,b.voucher_id,A.date,'J' as transaction_type from \r\n"
			+ " (SELECT (select date from journal_voucher jv where jv.payment_receipt_id = p.payment_receipt_id ),payment_receipt_id,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit \r\n"
			+ " from mstheads m left join journal_voucher_heads p on p.headid=m.headid and m.headid=:headid  and  security_id=:security_id and payment_receipt_id in \r\n"
			+ " (select payment_receipt_id from journal_voucher where  date between to_date(cast(:fromDate as text),'dd/mm/yyyy') \r\n"
			+ " and to_date(cast(:toDate as text),'dd/mm/yyyy'))  where m.headid=:headid  and security_id=:security_id order by m.headid)a,journal_voucher b \r\n"
			+ "where a.payment_receipt_id=b.payment_receipt_id) order by date,transaction_type) A order by date,transaction_type ) b", nativeQuery = true)
	List<Map<String, String>> getTrail_DistrictWiseonclick(String fromDate, String toDate, String headid,
			String security_id);
//
//	// Review Income/Exp
//	// Review Income/Exp
//	@Query(value = "SELECT am.name,sum(payment+debit) as state_in_debit,sum(receipt+credit)as state_in_credit,am.code from (select m.name,code,coalesce(payment,0.00) as payment,\r\n"
//			+ "coalesce(receipt,0.00) as receipt from cgg_master_districts m left join(select coalesce(sum( case when transaction_type='R' then amount else 0 end),0.00) as receipt,\r\n"
//			+ "coalesce(sum( case when transaction_type='P' then amount else 0 end),0.00) as payment ,B.security_id from  payments_receipts_subheads a left join \r\n"
//			+ "payments_receipts b on a.payment_receipt_id=b.payment_receipt_id where  a.headid like '1%' and a.headid!='10105' and  \r\n"
//			+ "b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') \r\n"
//			+ "group by B.security_id order by security_id) c  on m.code=c.security_id )am right join\r\n"
//			+ "(select m.name,code,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit  from cgg_master_districts m \r\n"
//			+ "left join(select sum(coalesce(a.debit,0.00)) as debit,sum(coalesce(a.credit,0.00)) as credit,a.security_id from  \r\n"
//			+ "journal_voucher_heads a left join journal_voucher b on a.payment_receipt_id=b.payment_receipt_id \r\n"
//			+ "where  a.headid like '1%'  and a.headid!='10105' and b.date between to_date(:fromDate,'dd/mm/yyyy') \r\n"
//			+ "and to_date(:toDate,'dd/mm/yyyy') group by a.security_id order by a.security_id) c on m.code=c.security_id)as \r\n"
//			+ "db on am.code=db.code group by am.name,am.code order by am.code", nativeQuery = true)
//	List<Map<String, String>> getstatewisedata_receipts(String fromDate, String toDate);
//
//	@Query(value = "SELECT am.name,sum(payment+debit) as state_exp_debit,sum(receipt+credit)as state_exp_credit,am.code from  \r\n"
//			+ "(select m.name,code,coalesce(payment,0.00) as payment,coalesce(receipt,0.00) as receipt from \r\n"
//			+ "cgg_master_districts m left join (select coalesce(sum( case when transaction_type='R' then amount else 0 end),0.00) as receipt, \r\n"
//			+ "coalesce(sum( case when transaction_type='P' then amount else 0 end),0.00) as payment ,B.security_id from  \r\n"
//			+ "payments_receipts_subheads a left join payments_receipts b on a.payment_receipt_id=b.payment_receipt_id \r\n"
//			+ "where  a.headid like '2%' and  b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy')\r\n"
//			+ " group by B.security_id order by B.security_id) c  \r\n"
//			+ "on m.code=c.security_id )am right join(select m.name,code,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit  from \r\n"
//			+ "cgg_master_districts m left join (select sum(coalesce(a.debit,0.00)) as debit,sum(coalesce(a.credit,0.00)) as credit,a.security_id from \r\n"
//			+ "journal_voucher_heads a left join journal_voucher b on a.payment_receipt_id=b.payment_receipt_id \r\n"
//			+ "where  a.headid like '2%' and  b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') \r\n"
//			+ "group by a.security_id order by a.security_id) c on m.code=c.security_id)as db\r\n"
//			+ "on am.code=db.code group by am.name,am.code order by am.code", nativeQuery = true)
//	List<Map<String, String>> getstatewisedata_payments(String fromDate, String toDate);

	@Query(value = "SELECT A.NAME,A.DEBIT INCOME_DEBIT,A.CREDIT INCOME_CREDIT,B.DEBIT EXPENDITURE_DEBIT,B.CREDIT EXPENDITURE_CREDIT,A.CODE\r\n"
			+ "FROM cgg_master_districts c left join\r\n"
			+ "(SELECT am.name,sum(payment+debit) as debit,sum(receipt+credit)as credit,am.code from (select m.name,code,coalesce(payment,0.00) as payment,\r\n"
			+ "coalesce(receipt,0.00) as receipt from cgg_master_districts m left join(select coalesce(sum( case when transaction_type='R' then amount else 0 end),0.00) as receipt,\r\n"
			+ "coalesce(sum( case when transaction_type='P' then amount else 0 end),0.00) as payment ,B.security_id from  payments_receipts_subheads a left join \r\n"
			+ "payments_receipts b on a.payment_receipt_id=b.payment_receipt_id where  a.headid like '1%' and a.headid!='10105' and  \r\n"
			+ "b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') \r\n"
			+ "group by B.security_id order by security_id) c  on m.code=c.security_id )am right join\r\n"
			+ "(select m.name,code,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit  from cgg_master_districts m \r\n"
			+ "left join(select sum(coalesce(a.debit,0.00)) as debit,sum(coalesce(a.credit,0.00)) as credit,a.security_id from  \r\n"
			+ "journal_voucher_heads a left join journal_voucher b on a.payment_receipt_id=b.payment_receipt_id \r\n"
			+ "where  a.headid like '1%'  and a.headid!='10105' and b.date between to_date(:fromDate,'dd/mm/yyyy') \r\n"
			+ "and to_date(:toDate,'dd/mm/yyyy') group by a.security_id order by a.security_id) c on m.code=c.security_id)as \r\n"
			+ "db on am.code=db.code group by am.name,am.code order by am.code) A  on a.code=c.code left JOIN\r\n"
			+ "(SELECT am.name,sum(payment+debit) as debit,sum(receipt+credit)as credit,am.code from  \r\n"
			+ "(select m.name,code,coalesce(payment,0.00) as payment,coalesce(receipt,0.00) as receipt from \r\n"
			+ "cgg_master_districts m left join (select coalesce(sum( case when transaction_type='R' then amount else 0 end),0.00) as receipt, \r\n"
			+ "coalesce(sum( case when transaction_type='P' then amount else 0 end),0.00) as payment ,B.security_id from  \r\n"
			+ "payments_receipts_subheads a left join payments_receipts b on a.payment_receipt_id=b.payment_receipt_id \r\n"
			+ "where  a.headid like '2%' and  b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy')\r\n"
			+ "group by B.security_id order by B.security_id) c  \r\n"
			+ "on m.code=c.security_id )am right join(select m.name,code,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit  from \r\n"
			+ "cgg_master_districts m left join (select sum(coalesce(a.debit,0.00)) as debit,sum(coalesce(a.credit,0.00)) as credit,a.security_id from \r\n"
			+ "journal_voucher_heads a left join journal_voucher b on a.payment_receipt_id=b.payment_receipt_id \r\n"
			+ "where  a.headid like '2%' and  b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') \r\n"
			+ "group by a.security_id order by a.security_id) c on m.code=c.security_id)as db\r\n"
			+ "on am.code=db.code group by am.name,am.code order by am.code) B on c.code=b.code", nativeQuery = true)
	List<Map<String, String>> getstatewisedata_inexp(String fromDate, String toDate);

	@Query(value = "SELECT headid,headname,sum(debit) as dis_in_debit,sum(credit)as dist_in_credit,code from (\r\n"
			+ "(select mh.headid,headname,code,coalesce(payment,0.00) as debit,\r\n"
			+ "coalesce(receipt,0.00) as credit\r\n"
			+ "from MSTHEADS mh join ( cgg_master_districts m left join(select headid,\r\n"
			+ "coalesce(sum( case when transaction_type='R' then amount else 0 end),0.00) as receipt,\r\n"
			+ "coalesce(sum( case when transaction_type='P' then amount else 0 end),0.00) as payment ,\r\n"
			+ "B.security_id from  payments_receipts_subheads a left join \r\n"
			+ "payments_receipts b on a.payment_receipt_id=b.payment_receipt_id where  a.headid like '1%' and a.headid!='10105' and  \r\n"
			+ "b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') and b.security_id=:security_id\r\n"
			+ "group by B.security_id,headid order by security_id) c  on m.code=c.security_id ) m on m.headid=mh.headid)\r\n"
			+ "union all\r\n"
			+ "(select mh.headid,headname,code,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit  \r\n"
			+ "from MSTHEADS mh join ( cgg_master_districts m \r\n"
			+ "left join(select headid,sum(coalesce(a.debit,0.00)) as debit,sum(coalesce(a.credit,0.00)) as credit,a.security_id from  \r\n"
			+ "journal_voucher_heads a left join journal_voucher b on a.payment_receipt_id=b.payment_receipt_id \r\n"
			+ "where  a.headid like '1%'  and a.headid!='10105' and "
			+ "b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') and a.security_id=:security_id \r\n"
			+ "group by a.security_id,headid order by a.security_id)  c on m.code=c.security_id) m on m.headid=mh.headid)) a group by headid,headname,code", nativeQuery = true)
	List<Map<String, String>> getstatewisedata_receipts_dist(String fromDate, String toDate, String security_id);

	@Query(value = "SELECT headid,headname,sum(debit) as dist_exp_debit,sum(credit)as dist_exp_credit,code from ( \r\n"
			+ "(select mh.headid,headname,code,coalesce(payment,0.00) as debit,coalesce(receipt,0.00) as credit  from MSTHEADS mh join (\r\n"
			+ "cgg_master_districts m left join (select headid,coalesce(sum( case when transaction_type='R' then amount else 0 end),0.00) as receipt, \r\n"
			+ "coalesce(sum( case when transaction_type='P' then amount else 0 end),0.00) as payment ,B.security_id from  \r\n"
			+ "payments_receipts_subheads a left join payments_receipts b on a.payment_receipt_id=b.payment_receipt_id \r\n"
			+ "where  a.headid like '2%' and  b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') \r\n"
			+ "and b.security_id=:security_id group by b.security_id,headid order by headid) c on m.code=c.security_id  ) m on m.headid=mh.headid)\r\n"
			+ "union all\r\n"
			+ "(select mh.headid,headname,code,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit  from MSTHEADS mh join (\r\n"
			+ "cgg_master_districts m left join (select headid,sum(coalesce(a.debit,0.00)) as debit,sum(coalesce(a.credit,0.00)) as credit,\r\n"
			+ "a.security_id from journal_voucher_heads a left join journal_voucher b on a.payment_receipt_id=b.payment_receipt_id \r\n"
			+ "where  a.headid like '2%' and  b.date between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') and a.security_id=':security_id' \r\n"
			+ "group by a.security_id,headid order by a.security_id) c on m.code=c.security_id   ) m  on m.headid=mh.headid ) )a   group by headid,headname,code", nativeQuery = true)
	List<Map<String, String>> getstatewisedata_payments_dist(String fromDate, String toDate, String security_id);

	@Query(value = "select distinct(m.headid) as headid,headname,coalesce(sum(amount),0.00) as amount from mstheads m  \r\n"
			+ "left join payments_receipts_subheads p on m.headid=p.headid and payment_receipt_id in (select payment_receipt_id from payments_receipts\r\n"
			+ " where transaction_type=:transaction_type and  date between \r\n"
			+ "to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and cash_bank=:cash_bank and security_id=:security_id) where security_id=:security_id  group by m.headid,headname  order by m.headid", nativeQuery = true)
	List<Map<String, String>> GetPayments_Receips_CashBank_Report(String fromDate, String toDate, String cash_bank,
			String transaction_type, String security_id);

	@Query(value = "select distinct(m.headid) as headid,headname,coalesce(sum(amount),0.00) as amount from mstheads m  \r\n"
			+ "left join payments_receipts_subheads p on m.headid=p.headid and payment_receipt_id in (select payment_receipt_id from payments_receipts\r\n"
			+ " where transaction_type=:transaction_type and  date between \r\n"
			+ "to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy')  and security_id=:security_id) where security_id=:security_id  group by m.headid,headname  order by m.headid", nativeQuery = true)
	List<Map<String, String>> GetPayments_Receips_CashBank_Report_All(String fromDate, String toDate,
			String transaction_type, String security_id);

//Salary_OnlineJV_Report

	@Query(value = "SELECT month_id,month_name FROM mstmonth", nativeQuery = true)
	List<Map<String, String>> Months_Drop_Down();

	@Query(value = "SELECT distinct year  FROM emp_details_confirm order by year", nativeQuery = true)
	List<Map<String, String>> years_Drop_Down();

//
//	@Query(value="SELECT vl.voucher_id,vl.vouchershortname FROM voucher_list vl LEFT JOIN journal_voucher jv ON cast(vl.voucher_id AS varchar) = cast(jv.voucher_id as varchar) AND to_char(date, 'dd/mm/yyyy')\r\n"
//			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%' and security_id =:security_id\r\n"
//			+ " WHERE vl.voucher_id <= 16 OR (vl.voucher_id >= 31 AND vl.voucher_id < 100)\r\n"
//			+ "GROUP BY vl.voucher_id, vouchershortname ORDER BY vl.voucher_id",nativeQuery = true)
	@Query(value = "SELECT cast(array_to_string(ARRAY(SELECT vouchershortname || '(' || vl.voucher_id || ')' FROM voucher_list vl \r\n"
			+ "LEFT JOIN journal_voucher jv ON cast(vl.voucher_id AS varchar) = cast(jv.voucher_id as varchar) AND to_char(date, 'dd/mm/yyyy') \r\n"
			+ "LIKE cast(:year as varchar) || '-' || cast(:month as varchar) || '%' WHERE vl.voucher_id <= 16 OR (vl.voucher_id >= 31 AND vl.voucher_id < 100)\r\n"
			+ "GROUP BY vl.voucher_id, vouchershortname ORDER BY vl.voucher_id), ',') AS text) column_names", nativeQuery = true)
	List<Map<String, String>> GetSalary_OnlineJV_Report_Columns_ojv(String year, String month);
//	@Query(value="SELECT vl.voucher_id,vl.vouchershortname FROM voucher_list vl LEFT JOIN journal_voucher jv ON cast(vl.voucher_id AS varchar) = cast(jv.voucher_id as varchar) AND to_char(date, 'dd/mm/yyyy')\r\n"
//			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%' and security_id =:security_id\r\n"
//			+ " WHERE vl.voucher_id <= 16 OR (vl.voucher_id >= 31 AND vl.voucher_id < 100)\r\n"
//			+ "GROUP BY vl.voucher_id, vouchershortname ORDER BY vl.voucher_id",nativeQuery = true)
	@Query(value = "SELECT array_to_string(ARRAY(SELECT vouchershortname||'('||vl.voucher_id||')' from voucher_list vl \r\n"
			+ "left  join journal_voucher jv on vl.voucher_id=int4(jv.voucher_id) and to_char(date,'dd/mm/yyyy') \r\n"
			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%' and security_id =:security_id where vl.voucher_id>16 and vl.voucher_id<=26\r\n"
			+ " group by vl.voucher_id,vouchershortname order by vl.voucher_id),',')column_names", nativeQuery = true)
	List<Map<String, String>> GetSalary_OnlineJV_Report_Columns_sjv(String year, String month, String security_id);
	@Query(value = "SELECT name,(SELECT array_to_string(ARRAY(SELECT sum(coalesce(jv.debit,0)) from voucher_list vl left  join journal_voucher jv on vl.voucher_id=int4(jv.voucher_id) and to_char(date,'dd/mm/yyyy') \r\n"
			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%'  where vl.voucher_id<=16 or(vl.voucher_id>=31 and vl.voucher_id<100) group by vl.voucher_id \r\n"
			+ "order by vl.voucher_id),','))as values,code,array_to_string(ARRAY(SELECT vl.voucher_id from voucher_list vl \r\n"
			+ "left  join journal_voucher jv on vl.voucher_id=int4(jv.voucher_id) \r\n"
			+ "and to_char(date,'dd/mm/yyyy')\r\n"
			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%'  where vl.voucher_id<=16 or(vl.voucher_id>=31 and vl.voucher_id<100) group by vl.voucher_id,vouchershortname order by vl.voucher_id),',')as voucher_list \r\n"
			+ "from cgg_master_districts d order by d.code", nativeQuery = true)
	
	List<Map<String, String>> GetSalary_OnlineJV_Report_Values_ojvApshcl(String year, String month);
	
	@Query(value = "SELECT name,(SELECT array_to_string(ARRAY(SELECT sum(coalesce(jv.debit,0)) from voucher_list vl left  join journal_voucher jv on vl.voucher_id=int4(jv.voucher_id) and to_char(date,'dd/mm/yyyy') \r\n"
			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%' and security_id =:security_id where vl.voucher_id<=16 or(vl.voucher_id>=31 and vl.voucher_id<100) group by vl.voucher_id \r\n"
			+ "order by vl.voucher_id),','))as values,code,array_to_string(ARRAY(SELECT vl.voucher_id from voucher_list vl \r\n"
			+ "left  join journal_voucher jv on vl.voucher_id=int4(jv.voucher_id) \r\n"
			+ "and to_char(date,'dd/mm/yyyy')\r\n"
			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%' and security_id =:security_id where vl.voucher_id<=16 or(vl.voucher_id>=31 and vl.voucher_id<100) group by vl.voucher_id,vouchershortname order by vl.voucher_id),',')as voucher_list \r\n"
			+ "from cgg_master_districts d where d.code=:security_id order by d.code", nativeQuery = true)
	List<Map<String, String>> GetSalary_OnlineJV_Report_Values_ojv(String year, String month, String security_id);

	@Query(value = "SELECT name,(SELECT array_to_string(ARRAY(SELECT sum(coalesce(jv.debit,0)) from voucher_list vl left  join journal_voucher jv on vl.voucher_id=int4(jv.voucher_id) and to_char(date,'dd/mm/yyyy') \r\n"
			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%'  where vl.voucher_id>16 and vl.voucher_id<=26 group by vl.voucher_id \r\n"
			+ "order by vl.voucher_id),','))as values,code,array_to_string(ARRAY(SELECT vl.voucher_id from voucher_list vl \r\n"
			+ "left  join journal_voucher jv on vl.voucher_id=int4(jv.voucher_id) \r\n"
			+ "and to_char(date,'dd/mm/yyyy') \r\n"
			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%' where vl.voucher_id>16 and vl.voucher_id<=26 group by vl.voucher_id,vouchershortname order by vl.voucher_id),',')as voucher_list \r\n"
			+ "from cgg_master_districts d  order by d.code", nativeQuery = true)
	List<Map<String, String>> GetSalary_OnlineJV_Report_Values_sjvApshcl(String year, String month); 
	
	@Query(value = "SELECT name,(SELECT array_to_string(ARRAY(SELECT sum(coalesce(jv.debit,0)) from voucher_list vl left  join journal_voucher jv on vl.voucher_id=int4(jv.voucher_id) and to_char(date,'dd/mm/yyyy') \r\n"
			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%' and security_id =:security_id where vl.voucher_id>16 and vl.voucher_id<=26 group by vl.voucher_id \r\n"
			+ "order by vl.voucher_id),','))as values,code,array_to_string(ARRAY(SELECT vl.voucher_id from voucher_list vl \r\n"
			+ "left  join journal_voucher jv on vl.voucher_id=int4(jv.voucher_id) \r\n"
			+ "and to_char(date,'dd/mm/yyyy') \r\n"
			+ "like cast(:year as varchar)||'-'||cast(:month as varchar)||'%' and security_id =:security_id where vl.voucher_id>16 and vl.voucher_id<=26 group by vl.voucher_id,vouchershortname order by vl.voucher_id),',')as voucher_list \r\n"
			+ "from cgg_master_districts d where d.code=:security_id order by d.code", nativeQuery = true)
	List<Map<String, String>> GetSalary_OnlineJV_Report_Values_sjv(String year, String month, String security_id);

	// Bank Balances Report************8APSHCL LOGIN**************
	@Query(value = "select bankname,branchname,accountno,b.banknameaccountno,balance,balance+(coalesce(sum(receiptamount),0.00)-coalesce(sum(paymentamount),0.00)) AS current_balance, case when mandal_account is true  then 'No' else 'Yes'end  \r\n"
			+ " from bankdetails b left join (SELECT p.banknameaccountno,\r\n"
			+ "sum(coalesce(m.paymentamount,0)+coalesce(p.paymentamount,0)) as paymentamount,sum(coalesce(m.receiptamount,0)+coalesce(p.receiptamount,0)) as receiptamount from payments_receipts p \r\n"
			+ "left join manager_payments_receipts m on m.banknameaccountno=p.banknameaccountno\r\n"
			+ "group by p.banknameaccountno) as p on b.banknameaccountno=p.banknameaccountno where security_id =:security_id and b.mandal_account=false group by bankname,branchname,accountno,b.banknameaccountno,\r\n"
			+ "b.mandal_account,balance order by b.banknameaccountno ", nativeQuery = true)
	List<Map<String, String>> BankBalancesReport(String security_id);

	@Transactional
	@Modifying
	@Query(value = "update bankdetails set mandal_account='t' where banknameaccountno=:banknameaccountno and  security_id=:security_id ", nativeQuery = true)
	int updatemandal_account(String banknameaccountno, String security_id);

	// Bank Book Report ****Apshcl Login ******************
	@Query(value = "select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,banknameaccountno,receiptamount,paymentamount,balance_in_account,transaction_type,cash_bank,security_id from payments_receipts\r\n"
			+ " where date between to_date(cast(:fromDate as varchar),'dd/mm/yyyy') and to_date(cast(:toDate as varchar),'dd/mm/yyyy') and cash_bank='Bank' and isdelete='f'  \r\n"
			+ "order by date,receiptamount,paymentamount", nativeQuery = true)
	List<Map<String, String>> bankbook_fromdate_todate(String fromDate, String toDate);

	@Query(value = "select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,banknameaccountno,receiptamount,paymentamount,balance_in_account,transaction_type,cash_bank,security_id from payments_receipts\r\n"
			+ " where date between to_date(cast(:fromDate as varchar),'dd/mm/yyyy') and to_date(cast(:toDate as varchar),'dd/mm/yyyy') and cash_bank='Bank' and isdelete='f' and security_id=:security_id \r\n"
			+ "order by date,receiptamount,paymentamount", nativeQuery = true)

	List<Map<String, String>> bankbook_fromdate_todate_ByDistrict(String fromDate, String toDate, String security_id);

	@Query(value = "select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,banknameaccountno,receiptamount,paymentamount,balance_in_account,transaction_type,cash_bank,security_id from payments_receipts\r\n"
			+ " where date between to_date(cast(:fromDate as varchar),'dd/mm/yyyy') and to_date(cast(:toDate as varchar),'dd/mm/yyyy') and cash_bank='Bank' and isdelete='f' "
			+ "and security_id=:security_id and  banknameaccountno like :banks || '%' \r\n"
			+ "order by date,receiptamount,paymentamount", nativeQuery = true)
	List<Map<String, String>> bankbook_fromdate_todate_ByBanks(String fromDate, String toDate, String security_id,
			String banks);

	@Query(value = "select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,banknameaccountno,receiptamount,paymentamount,balance_in_account,transaction_type,cash_bank,security_id from payments_receipts\r\n"
			+ " where date between to_date(cast(:fromDate as varchar),'dd/mm/yyyy') and to_date(cast(:toDate as varchar),'dd/mm/yyyy') and cash_bank='Bank' and isdelete='f' "
			+ "and security_id=:security_id and banknameaccountno like :banks || '%' and banknameaccountno =:banknameaccountno \r\n"
			+ "order by date,receiptamount,paymentamount", nativeQuery = true)

	List<Map<String, String>> bankbook_fromdate_todate_ByDistrict_account(String fromDate, String toDate,
			String security_id, String banks, String banknameaccountno);

	@Query(value = "select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,banknameaccountno,receiptamount,paymentamount,balance_in_account,transaction_type,cash_bank,security_id from payments_receipts\r\n"
			+ " where date between to_date(cast(:fromDate as varchar),'dd/mm/yyyy') and to_date(cast(:toDate as varchar),'dd/mm/yyyy') and cash_bank='Bank' and isdelete='f' "
			+ "and security_id=:security_id and banknameaccountno like :banks || '%' and banknameaccountno =:banknameaccountno  and transaction_type=:transaction_type \r\n"
			+ "order by date,receiptamount,paymentamount", nativeQuery = true)

	List<Map<String, String>> bankbook_fromdate_todate_ByDistrict_account_Mode(String fromDate, String toDate,
			String security_id, String banks, String banknameaccountno, String transaction_type);

	@Query(value = "SELECT banknameaccountno FROM bankdetails  bd\r\n"
			+ "left join bankmaster bm on bm.bankshortname=bd.bankname\r\n"
			+ "where bm.bankshortname=:bankshortname", nativeQuery = true)
	List<Map<String, String>> AccountNoDropDown(String bankshortname);

	@Query(value = "SELECT cast (current+BAL as text)   OB FROM (SELECT   M.banknameaccountno ,current,SUM(receiptamount)- +SUM(paymentamount)  BAL from \r\n"
			+ "payments_receipts m LEFT JOIN bank_opening_balance BB ON BB.banknameaccountno =M.banknameaccountno  where   DATE >='2022-04-01' AND  date < to_date(cast(:fromDate as varchar),'dd/mm/yyyy') AND\r\n"
			+ "  M.banknameaccountno=:banknameaccountno and security_id=:security_id and isdelete='f'   GROUP BY 1,2) A", nativeQuery = true)
	String getOpeningBal(String fromDate, String banknameaccountno, String security_id);
	//DeletePRJ report from GMF1 USER
	@Query(value="select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description as description,no_of_subheads,banknameaccountno,receiptamount,paymentamount as amount ,balance_in_account,transaction_type,\r\n"
			+ "cash_bank,security_id from payments_receipts where date between to_date(cast(:fromDate as varchar),'dd/mm/yyyy') and to_date(cast(:toDate as varchar),'dd/mm/yyyy') and cash_bank='Bank' and isdelete='f' and transaction_type='P' and security_id=:district\r\n"
			+ "order by date,receiptamount,paymentamount",nativeQuery = true)
	List<Map<String, String>> GetPgetfordelete(String fromDate, String toDate,String district);
	@Query(value="select payment_receipt_id,to_char(date,'dd/mm/yyyy'),receiptno,name,mode,cheque_dd_receipt_no,receipt_description as description,no_of_subheads,banknameaccountno,receiptamount as amount,paymentamount,balance_in_account,transaction_type,\r\n"
			+ "cash_bank,security_id from payments_receipts where date between to_date(cast(:fromDate as varchar),'dd/mm/yyyy') and to_date(cast(:toDate as varchar),'dd/mm/yyyy') and cash_bank='Bank' and isdelete='f' and transaction_type='R' and security_id=:district\r\n"
			+ "order by date,receiptamount,paymentamount",nativeQuery = true)
	List<Map<String, String>> GetRgetfordelete(String fromDate, String toDate,String district);
	@Query(value = "select payment_receipt_id,cast(voucher_id as int)as receiptno,to_char(date,'dd/mm/yyyy'),description,coalesce(debit,0.00) as debit,coalesce(credit,0.00) as credit,upload_copy from journal_voucher \r\n"
			+ "where date between to_date(cast(:fromDate as text),'dd/mm/yyyy') and to_date(cast(:toDate as text),'dd/mm/yyyy') and\r\n"
			+ "isdelete='f' and security_id=:district order by voucher_id", nativeQuery = true)
	List<Map<String, String>> GetJvgetfordelete(String fromDate, String toDate,String district);

	

}
