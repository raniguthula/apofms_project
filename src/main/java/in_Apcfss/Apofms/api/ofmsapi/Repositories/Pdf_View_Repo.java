package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in_Apcfss.Apofms.api.ofmsapi.models.BankDetails_Model;

@Repository
public interface Pdf_View_Repo extends JpaRepository<BankDetails_Model, Integer> {

	/*--------------REPORTS--------------*/

	@Transactional
	@Modifying
	@Query(value = "select payment_receipt_id,to_char(date,''),receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,\r\n"
			+ "banknameaccountno,receiptamount,paymentamount,balance_in_account,transaction_type from payments_receipts where date between \r\n"
			+ "to_date('20/6/2014','dd/mm/yyyy') and to_date('21/6/2014','dd/mm/yyyy') \r\n"
			+ "and cash_bank='Bank' \r\n"
			+ "and user_id='DM03' and isdelete='f' order by date,receiptamount,paymentamount", nativeQuery = true)
	List<Map<String, String>> getBankBookReportData();

	@Query(value = "SELECT payment_receipt_id,receiptno,date,cash_bank,banknameaccountno,receiptamount,balance_in_account,name,mode,security_id,"
			+ "transaction_type,no_of_subheads,isdelete,timestamp,user_id FROM payments_receipts", nativeQuery = true)
	List<Map<String, String>> getGenrationOfRecieptData();

	@Query(value = "select jh.payment_receipt_id, receiptno,cash_bank, banknameaccountno, paymentamount, name,  mode, cheque_dd_receipt_no, receipt_description, security_id, "
			+ "no_of_subheads, to_char(date,'yyyy-mm-dd') \r\n"
			+ "from payments_receipts jh where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd') "
			+ "and security_id='02' and user_id='DM02' and isdelete='f'  order by date", nativeQuery = true)
	List<Map<String, String>> getReceiptReport(String fromdate, String todate);

	@Query(value = "select jh.payment_receipt_id, to_char(date,'yyyy-mm-dd'), receiptno, name, mode, cheque_dd_receipt_no, receipt_description, no_of_subheads, "
			+ "banknameaccountno, receiptamount, balance_in_account from payments_receipts jh\r\n"
			+ "where date between to_date(cast(:fromdate as text),'yyyy-mm-dd') and to_date(cast(:todate as text),'yyyy-mm-dd')\r\n"
			+ " and security_id='02' and user_id='DM02' order by date", nativeQuery = true)
	List<Map<String, String>> GetPaymentReport(String fromdate, String todate);

	// 142710100034244
	@Query(value = "SELECT  remaining_balance FROM bankdetails WHERE accountno=:accountno", nativeQuery = true)
	List<Map<String, String>> getTotalReciept(String accountno);

	@Query(value = "select sum(balance_in_account+receiptamount) from payments_receipts where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> getTotalByAddingReceiptAmt(String payment_receipt_id);

	@Query(value = "select sum(balance_in_account-paymentamount) from payments_receipts where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	List<Map<String, String>> getTotalBySubtractingPaymentAmt(String payment_receipt_id);

	// InterBank Transfer

	@Query(value = "select balance_in_account from payments_receipts WHERE banknameaccountno=:banknameaccountno", nativeQuery = true)
	List<Map<String, String>> getinterbankscreen(String banknameaccountno);

	// -----BankNames

	@Query(value = " select banknameaccountno||'-'||branchname as bankName,banknameaccountno,remaining_balance "
			+ "from bankdetails WHERE userid=:createdBy", nativeQuery = true)
	List<Map<String, String>> getBanknames(String createdBy);

	@Query(value = "select sum(paymentamount) from payments_receipts where date between to_date('20/6/2014','dd/mm/yyyy') and to_date('21/6/2015','dd/mm/yyyy') ", nativeQuery = true)
	List<Map<String, String>> getTotalPayment();

	/*------------- BankDetailsEntryFormServiceImpl -------------*/

	@Query(value = "SELECT payment_receipt_id,receiptno,date,cash_bank,banknameaccountno,paymentamount,balance_in_account,name,mode,security_id,"
			+ "transaction_type,no_of_subheads,isdelete,timestamp,user_id FROM payments_receipts", nativeQuery = true)
	List<Map<String, String>> getGenrationOfPaymentData();

	// Bank Subsidary Report

	@Query(value = "SELECT row_number() over(order by date) as id,payment_receipt_id,to_char(date,'yyyy/mm/dd') as date,receiptno,name,mode,case when cheque_dd_receipt_no='null' then ''\r\n"
			+ " else cheque_dd_receipt_no end,receipt_description,no_of_subheads,receiptamount,paymentamount,balance_in_account,transaction_type \r\n"
			+ "from((select payment_receipt_id,date,'00' as receiptno,'' \r\n"
			+ "as name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,balance_in_account,transaction_type\r\n"
			+ " from manager_payments_receipts m where date between to_date(cast(:fromdate as text),'yyyy/mm/dd') and\r\n"
			+ " to_date(cast(:todate as text),'yyyy/mm/dd') and banknameaccountno=:banknameaccountno and\r\n"
			+ " security_id=:security_id and isdelete='f' order by date) union all \r\n"
			+ "(select payment_receipt_id,date,receiptno,name,mode,cheque_dd_receipt_no,receipt_description,no_of_subheads,receiptamount,paymentamount,\r\n"
			+ "balance_in_account,transaction_type from payments_receipts m where date between to_date(cast(:fromdate as text),'yyyy/mm/dd')\r\n"
			+ " and to_date(cast(:todate as text),'yyyy/mm/dd') and banknameaccountno=:banknameaccountno\r\n"
			+ "  and isdelete='f' order by date))as s  order by date,transaction_type desc", nativeQuery = true)
	List<Map<String, String>> getBankSubsidiartyList(String fromdate, String todate, String security_id,
			String banknameaccountno);

	@Query(value = "select banknameaccountno||'-'||branchname as bankname,banknameaccountno from bankdetails where security_id=:security_id", nativeQuery = true)
	List<Map<String, String>> getBankSubsidiartyNames(String security_id);

//P032006141 
	@Query(value = "select payment_receipt_id,receiptno,to_char(date,'dd-mm-yyyy') as date,cash_bank,cast(banknameaccountno as varchar),cast(balance_in_account as varchar),cast(paymentamount as varchar),cast(receiptamount as varchar),\r\n"
			+ "name,mode,cheque_dd_receipt_no,receipt_description,timestamp from payments_receipts where payment_receipt_id=:payment_receipt_id", nativeQuery = true)
	Map<String, Object> getdata_by_transection_id(String payment_receipt_id);

	@Query(value = "SELECT (d.headid  || '-' || m.headname) as heads, (d.subheadseqid || '-'||\r\n"
			+ "case  when  d.subheadseqid='No' then 'Subheads' when d.subheadseqid='more' or d.subheadseqid='More' then 'More' else (select subheadname from mstsubheads ms where ms.subheadseqid=d.subheadseqid) end) as subheads,\r\n"
			+ "d.amount,sum(pr.receiptamount) as receiptsum,sum(pr.paymentamount) as paymentsum FROM payments_receipts_subheads d \r\n"
			+ "left join mstheads m on m.headid=d.headid\r\n"
			+ "left join mstsubheads ms on ms.subheadid=d.subheadseqid and ms.headid=d.headid\r\n"
			+ "left join payments_receipts pr on pr.payment_receipt_id=d.payment_receipt_id\r\n"
			+ "where pr.payment_receipt_id=:payment_receipt_id  group by 1,2,3", nativeQuery = true)
	List<Map<String, Object>> getheads(String payment_receipt_id);
//	@Query(value = "SELECT (d.headid  || '-' || m.headname) as heads, (d.subheadseqid || '-'||\r\n"
//			+ "case  when  d.subheadseqid='No' then 'No Subheads' when d.subheadseqid='more' or d.subheadseqid='More' then 'More' else (select subheadname from mstsubheads ms where ms.subheadseqid=d.subheadseqid) end) as subheads,\r\n"
//			+ "d.amount,sum(pr.receiptamount) as receiptsum,sum(pr.paymentamount) as paymentsum FROM payments_receipts_subheads d \r\n"
//			+ "left join mstheads m on m.headid=d.headid\r\n"
//			+ "left join mstsubheads ms on ms.subheadid=d.subheadseqid and ms.headid=d.headid\r\n"
//			+ "left join payments_receipts pr on pr.payment_receipt_id=d.payment_receipt_id\r\n"
//			+ "where pr.payment_receipt_id=:payment_receipt_id  group by 1,2,3", nativeQuery = true)
//	List<Map<String, Object>> getheads(String payment_receipt_id);

	@Query(value = "select 'P'||replace(to_char(nextval(cast('hibernate_sequence' as regclass)), '000000000'),' ','') transection_id", nativeQuery = true)
	String get_auto_payment_trans();

	@Query(value = "select 'R'||replace(to_char(nextval(cast('hibernate_sequence' as regclass)), '000000000'),' ','') transection_id", nativeQuery = true)
	String get_auto_receipt_trans();

	@Query(value = "select 'J'||replace(to_char(nextval(cast('hibernate_sequence' as regclass)), '000000000'),' ','') transection_id", nativeQuery = true)
	String get_auto_journal_trans();

	@Query(value="select c.name as display_name,security_id  from users u left join cgg_master_districts c on c.code=u.security_id  where  security_id=:security_id and user_id=:userid",nativeQuery = true)
	Map<String, String>  getdist_data(String security_id,String userid);
	@Query(value = "SELECT distinct u.display_name FROM payments_receipts p left join users u on u.user_id=p.user_id where payment_receipt_id=:object", nativeQuery = true)
	Map<String, String> getReceiptdist_data(Object object);
	@Query(value = "SELECT distinct u.display_name FROM payments_receipts p left join users u on u.user_id=p.user_id where payment_receipt_id=:object", nativeQuery = true)
	Map<String, String> getPaymentdist_data(Object object);
	@Query(value = "SELECT distinct u.display_name FROM journal_voucher j left join users u on u.security_id=j.security_id and u.user_id=j.dh_id  where payment_receipt_id=:object", nativeQuery = true)
	Map<String, String> getJournaldist_data(Object object);
	
	@Query(value = "SELECT distinct u.display_name FROM payments_receipts p left join users u on u.user_id=p.user_id where payment_receipt_id=:object", nativeQuery = true)
	Map<String, String> getInterBankdist_data(Object object);
	@Query(value = "SELECT payment_receipt_id,voucher_id,date,description,timestamp FROM journal_voucher where payment_receipt_id=:paymentreceiptid", nativeQuery = true)
	Map<String, Object> getdata_by_transection_id_journal(String paymentreceiptid);

	@Query(value = "SELECT  jv.payment_receipt_id, jv.voucher_id, jv.date, jv.description,(d.headid  || '-' || m.headname) as heads, (d.subheadid || '-'||\r\n"
			+ "case  when  d.subheadid='No' then 'Subheads' when d.subheadid='more' or d.subheadid='More' then 'More' else (select subheadname from mstsubheads ms where ms.subheadseqid=d.subheadid) end) as subheads,\r\n"
			+ "d.credit,d.debit,sum(jv.credit) as creditsum,sum(jv.debit)as debitsum FROM journal_voucher_heads d \r\n"
			+ "left join mstheads m on m.headid=d.headid\r\n"
			+ "left join mstsubheads ms on ms.subheadid=d.subheadid and ms.headid=d.headid\r\n"
			+ "left join journal_voucher jv on jv.payment_receipt_id=d.payment_receipt_id\r\n"
			+ "where jv.payment_receipt_id=:paymentreceiptid  group by 1,2,3,4,5,6,7,8 ORDER BY D.DEBIT DESC", nativeQuery = true) 
	List<Map<String, Object>> getheads_journal(String paymentreceiptid);

//	@Query(value = "select pr.payment_receipt_id,pr.banknameaccountno,receiptamount,balance_in_account FROM payments_receipts pr\r\n" + 
//			"left join interbankingmapping ibm on ibm.payment_receipt_id=pr.payment_receipt_id  where pr.payment_receipt_id=:receipt_id", nativeQuery = true)
@Query(value="select ibm.payment_receipt_id,ibm.receipt_id,pr.banknameaccountno,receiptamount,paymentamount,balance_in_account FROM payments_receipts pr\r\n" + 
		"left join interbankingmapping ibm on ibm.payment_receipt_id=pr.payment_receipt_id or ibm.receipt_id=pr.payment_receipt_id \r\n" + 
		"where pr.payment_receipt_id=:receipt_id ",nativeQuery = true)
	List<Map<String, Object>> getbanks(String receipt_id);

	@Query(value = "SELECT a.payment_receipt_id,to_char(b.date,'dd/mm/yyyy'),receipt_description,payment_receipt as debit,cast(0 as NUMERIC) AS credit,b.receiptno as receiptno,b.date,'P' AS transaction_type \r\n"
			+ " FROM (SELECT (SELECT DATE FROM payments_receipts pr WHERE pr.payment_receipt_id = p.payment_receipt_id ),payment_receipt_id,COALESCE(amount,0.00) AS payment_receipt \r\n"
			+ " FROM mstheads M LEFT JOIN payments_receipts_subheads p ON p.headid=M.headid AND M.headid=:headid and security_id=:security_id AND payment_receipt_id IN (	SELECT payment_receipt_id \r\n"
			+ " FROM payments_receipts WHERE transaction_type='P' ANd DATE BETWEEN to_date(:fromDate,'dd/mm/yyyy')  ANd to_date(:toDate,'dd/mm/yyyy')) \r\n"
			+ " WHERE M.headid=:headid ORDER BY M.headid)a,payments_receipts b WHERE a.payment_receipt_id=b.payment_receipt_id  UNION ALL (SELECT a.payment_receipt_id,to_char(b.date,'dd/mm/yyyy'),receipt_description,cast(0 as NUMERIC) AS c,payment_receipt,b.receiptno,b.date,'R' AS transaction_type  \r\n"
			+ " FROM (SELECT (SELECT DATE FROM payments_receipts pr WHERE pr.payment_receipt_id = p.payment_receipt_id ),payment_receipt_id,COALESCE(amount,0.00) AS payment_receipt  \r\n"
			+ " FROM mstheads M LEFT JOIN payments_receipts_subheads p ON p.headid=M.headid AND M.headid=:headid and security_id=:security_id AND \r\n"
			+ " payment_receipt_id IN (	SELECT payment_receipt_id \r\n" + " FROM payments_receipts  \r\n"
			+ " WHERE transaction_type='R'  \r\n" + " AND \r\n" + " DATE BETWEEN to_date(:fromDate,'dd/mm/yyyy') \r\n"
			+ " AND \r\n" + " to_date(:toDate,'dd/mm/yyyy'))\r\n" + " WHERE M.headid=:headid \r\n"
			+ " ORDER BY M.headid)a,\r\n" + " payments_receipts b \r\n"
			+ " WHERE a.payment_receipt_id=b.payment_receipt_id\r\n" + ") UNION ALL (SELECT payment_receipt_id,\r\n"
			+ " to_char(DATE,'dd/mm/yyyy'),\r\n" + " receipt_description,\r\n"
			+ " paymentamount,receiptamount,'00' as rep,DATE,transaction_type FROM manager_payments_receipts \r\n"
			+ " WHERE DATE BETWEEN to_date(:fromDate,'dd/mm/yyyy') AND to_date(:toDate,'dd/mm/yyyy') and headid=:headid)\r\n"
			+ " UNION ALL (SELECT a.payment_receipt_id,to_char(b.date,'dd/mm/yyyy'),description,a.debit,a.credit,b.voucher_id ,\r\n"
			+ "b.date,'J' AS transaction_type FROM (SELECT (SELECT DATE FROM journal_voucher jv WHERE jv.payment_receipt_id = p.payment_receipt_id ),\r\n"
			+ " payment_receipt_id,COALESCE(debit,0.00)  AS debit,COALESCE(credit,0.00) AS credit FROM mstheads M  \r\n"
			+ "LEFT JOIN journal_voucher_heads p ON p.headid=M.headid AND M.headid=:headid and  security_id=:security_id\r\n"
			+ " AND payment_receipt_id IN (SELECT payment_receipt_id FROM journal_voucher WHERE DATE BETWEEN to_date(:fromDate,'dd/mm/yyyy')\r\n"
			+ " AND to_date(:toDate,'dd/mm/yyyy'))WHERE M.headid=:headid ORDER BY M.headid)a,journal_voucher b \r\n"
			+ " WHERE a.payment_receipt_id=b.payment_receipt_id)ORDER BY DATE,transaction_type", nativeQuery = true)
	List<Map<String, Object>> get_Cash_Bank_Heads(String headid, String fromDate, String toDate, String security_id);

	@Query(value="SELECT COALESCE(no_of_subheads,'-') FROM payments_receipts where payment_receipt_id=:payment_receipt_id",nativeQuery=true)
	String getInterBankSubhead(String payment_receipt_id);

	@Query(value="SELECT receipt_id FROM interbankingmapping where payment_receipt_id=:payment_receipt_id",nativeQuery = true)
	String getReceipt_id(String payment_receipt_id); 
	@Query(value="SELECT payment_receipt_id FROM interbankingmapping where receipt_id=:payment_receipt_id",nativeQuery = true)
	String getPayment_Receipt_id(String payment_receipt_id); 

}
