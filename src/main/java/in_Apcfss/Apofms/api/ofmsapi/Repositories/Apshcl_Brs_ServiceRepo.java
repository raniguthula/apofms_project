package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.BankDetails_Model;

@Repository
public interface Apshcl_Brs_ServiceRepo extends JpaRepository<BankDetails_Model, Integer> {

	@Query(value = "select banknameaccountno from bankdetails where userid=:user_id and is_approved_by_gm =true", nativeQuery = true)
	List<Map<String, String>> getBanks_Brs(String user_id);

	@Query(value = "select payment_receipt_id,to_char(date,'dd-mm-yyyy') as date,banknameaccountno,cheque_dd_receipt_no,transaction_type,case when transaction_type='P' then  coalesce(paymentamount,0) end as debit ,\r\n"
			+ "case when transaction_type='R' then  coalesce(receiptamount,0) end as credit   from payments_receipts where\r\n"
			+ "to_char(date,'yyyy') like :year and to_char(date,'mm') like :month  and  mode!='Cash' and banknameaccountno=:banknameaccountno", nativeQuery = true)
	List<Map<String, String>> getBRS_BBU(String year, String month, String banknameaccountno);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO brs(payment_receipt_id,cheque_dd_receipt_no,bank_date,amount,transaction_type,userid,ip,banknameaccountno,security_id) \r\n"
			+ "values(:payment_receipt_id,:cheque_dd_receipt_no,:bank_date,cast(:amount as numeric),:transaction_type,:user_id,cast(:ip_address as inet),:banknameaccountno,:security_id)", nativeQuery = true)
	int CheckInsert_Brs(String payment_receipt_id, String cheque_dd_receipt_no, Date bank_date, long amount,
			String transaction_type, String user_id,  String ip_address,String banknameaccountno, String security_id);

//2nd
	@Query(value = "select payment_receipt_id,to_char(date,'dd-mm-yyyy') as date,banknameaccountno,cheque_dd_receipt_no,transaction_type, coalesce(paymentamount,0)  as amount   from payments_receipts\r\n"
			+ " where  to_char(date,'yyyy-mm-dd') <:monthfirst and banknameaccountno=:banknameaccountno and transaction_type='P' and mode!='Cash' and payment_receipt_id NOT IN \r\n"
			+ "(select payment_receipt_id from brs) order by date", nativeQuery = true)
	List<Map<String, String>> getBRS_OutstandingPayments(String monthfirst, String banknameaccountno);

//	@Transactional
//	@Modifying
//	@Query(value ="INSERT INTO brs(payment_receipt_id,cheque_dd_receipt_no,bank_date,amount,transaction_type,userid,banknameaccountno,ip,security_id) \r\n" + 
//			"values(:payment_receipt_id,:cheque_dd_receipt_no,to_date(:bank_date,'dd/mm/yyyy'),:amount,:transaction_type,:user_id,:banknameaccountno,:ip_address,:security_id)",nativeQuery = true)
//	int getBRS_OutstandingPayments_CheckInsert(String payment_receipt_id, String cheque_dd_receipt_no, Date bank_date,
//			Integer amount, String transaction_type, String banknameaccountno, String user_id, String security_id,
//			String ip_address);
//	
//	3rd
	@Query(value = "select payment_receipt_id,to_char(date,'dd-mm-yyyy') as date,banknameaccountno,cheque_dd_receipt_no,transaction_type, coalesce(paymentamount,0)  as amount   from payments_receipts\r\n"
			+ " where to_char(date,'yyyy-mm-dd') <:monthfirst  and  banknameaccountno=:banknameaccountno and transaction_type='R' and mode!='Cash' and payment_receipt_id NOT IN \r\n"
			+ "(select payment_receipt_id from brs) order by date", nativeQuery = true)
	List<Map<String, String>> getBRS_OutstandingReceipts(String monthfirst, String banknameaccountno);

	@Transactional
	@Modifying
	@Query(value = "INSERT into brs(payment_receipt_id,bank_date ,amount,transaction_type,userid,ip,banknameaccountno,remarks,security_id)\r\n"
			+ "values((SELECT  'D'||security_code||to_char(current_date,'ddmmyy')||  count(payment_receipt_id)+1  as payment_receipt_id from \r\n"
			+ "security_code_master \r\n"
			+ "left join ( select * from payments_receipts where date =current_date and payment_receipt_id like 'D%') p  on security_code =security_id\r\n"
			+ "where security_code=:security_id   group by security_code,current_date),:cheque_dd_receipt_no,:bank_date,cast(:amount as numeric),:transaction_type,:user_id,:banknameaccountno,:ip_address,:security_id)", nativeQuery = true)
	List<Map<String, String>> getBRS_Direct_CD(String cheque_dd_receipt_no, Date bank_date, long amount,
			String transaction_type, String user_id, String banknameaccountno, String ip_address, String security_id);

//5th
	@Query(value = "select date,remarks,coalesce(a.debit,0) as debit,coalesce(a.credit,0) as credit,a.banknameaccountno from (select to_char(bank_date,'dd/mm/yyyy') as date,case when remarks is null then p.receipt_description else remarks end as remarks,\r\n"
			+ "case when b.transaction_type='debit' then  coalesce(amount,0) end as debit ,case when b.transaction_type='credit' then  coalesce(amount,0) end as credit,p.banknameaccountno from brs b left join payments_receipts p on \r\n"
			+ "( p.payment_receipt_id=b.payment_receipt_id) where 1=1\r\n"
			+ "and  date(bank_date) between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') \r\n"
			+ "order by date) a  order by to_date(date,'DD/MM/YYYY')", nativeQuery = true)
	List<Map<String, String>> BRS_BankAcc_AsPerEntries(String fromDate, String toDate);

	@Query(value = "select date,remarks,coalesce(a.debit,0) as debit,coalesce(a.credit,0) as credit,a.banknameaccountno from (select to_char(bank_date,'dd/mm/yyyy') as date,case when remarks is null then p.receipt_description else remarks end as remarks,\r\n"
			+ "case when b.transaction_type='debit' then  coalesce(amount,0) end as debit ,case when b.transaction_type='credit' then  coalesce(amount,0) end as credit,p.banknameaccountno from brs b left join payments_receipts p on \r\n"
			+ "( p.payment_receipt_id=b.payment_receipt_id) where 1=1\r\n"
			+ "and  date(bank_date) between to_date(:fromDate,'dd/mm/yyyy') and to_date(:toDate,'dd/mm/yyyy') and banknameaccountno=:banknameaccountno \r\n"
			+ "order by date) a  order by to_date(date,'DD/MM/YYYY')", nativeQuery = true)
	List<Map<String, String>> BRS_BankAcc_AsPerEntries_bank(String fromDate, String toDate, String banknameaccountno);

//6th
	@Query(value = "select coalesce( b.balance+receiptamount-paymentamount,0) as balance from ((select coalesce(sum(receiptamount),0) as  receiptamount,coalesce(sum(paymentamount),0) as paymentamount ,user_id from  payments_receipts b  \r\n"
			+ "where date <= to_date(:maxdate,'dd/mm/yyyy') and security_id =:security_id and banknameaccountno=:banknameaccountno group by user_id) a \r\n"
			+ "right join (select coalesce(sum(balance),0) as balance ,userid from bankdetails where security_id =:security_id  and banknameaccountno=:banknameaccountno  group by userid) b  on  a.user_id=b.userid)", nativeQuery = true)
	int getbalance(String maxdate, String security_id, String banknameaccountno);

	@Query(value = "select coalesce(sum(receiptamount),0) as  receiptamount   from payments_receipts p left join brs b on ( b.payment_receipt_id=p.payment_receipt_id) \r\n"
			+ "where date between to_date(:mindate,'dd/mm/yyyy')   and to_date(:maxdate,'dd/mm/yyyy') and p.security_id =:security_id  and  mode!='Cash' and p.transaction_type='R' \r\n"
			+ "and p.banknameaccountno=:banknameaccountno   and bank_date is null", nativeQuery = true)
	int getreceiptamount(String mindate, String maxdate, String security_id, String banknameaccountno);

	@Query(value = "select coalesce(sum(paymentamount),0) as  paymentamount   from payments_receipts p left join brs b on ( b.payment_receipt_id=p.payment_receipt_id) where date between to_date(:maxdate,'dd/mm/yyyy')  \r\n"
			+ " and to_date(:mindate,'dd/mm/yyyy') and p.security_id =:security_id and  mode!='Cash' and p.transaction_type='P' and p.banknameaccountno=:banknameaccountno and bank_date is null ", nativeQuery = true)
	int getpaymentamount(String mindate, String maxdate, String security_id, String banknameaccountno);

	@Query(value = "select coalesce(sum(amount),0) as directdebit from brs where bank_date between to_date(:mindate,'dd/mm/yyyy')   and to_date(:maxdate,'dd/mm/yyyy') and\r\n"
			+ " payment_receipt_id like 'D%' and  security_id =:security_id and transaction_type='debit' and banknameaccountno=:banknameaccountno ", nativeQuery = true)
	int getdirectdebit(String mindate, String maxdate, String security_id, String banknameaccountno);

	@Query(value = "select coalesce(sum(amount),0) as directcredit from brs where bank_date between to_date(:mindate,'dd/mm/yyyy')   and to_date(:maxdate,'dd/mm/yyyy') and\r\n"
			+ " payment_receipt_id like 'D%' and  security_id =:security_id and transaction_type='credit' and banknameaccountno=:banknameaccountno ", nativeQuery = true)
	int getdirectcredit(String mindate, String maxdate, String security_id, String banknameaccountno);

	@Query(value = "select coalesce(sum(receiptamount),0) as  receiptamount   from payments_receipts p left join brs b on ( b.payment_receipt_id=p.payment_receipt_id) where date <   to_date(:maxdate,'dd/mm/yyyy')  \r\n"
			+ "and p.security_id =:security_id and  mode!='Cash' and p.transaction_type='R' and p.banknameaccountno=:banknameaccountno  and bank_date is null", nativeQuery = true)
	int getoutreceiptamount(String maxdate, String security_id, String banknameaccountno);

	@Query(value = "select coalesce(sum(paymentamount),0) as  paymentamount   from payments_receipts p left join brs b on ( b.payment_receipt_id=p.payment_receipt_id) where date <   to_date(:maxdate,'dd/mm/yyyy')  \r\n"
			+ "  and p.security_id =:security_id  and  mode!='Cash' and p.transaction_type='P' and p.banknameaccountno=:banknameaccountno  and bank_date is null", nativeQuery = true)
	int getoutpaymentamount(String maxdate, String security_id, String banknameaccountno);

	/// BRS OLD**********
	@Query(value = "select banknameaccountno from bankdetails_old where userid=:user_id and  mandal_account=false", nativeQuery = true)
	List<Map<String, String>> getBanks_Brs_Old(String user_id);

	@Query(value = "select payment_receipt_id,to_char(date,'dd-mm-yyyy') as date,banknameaccountno,cheque_dd_receipt_no,transaction_type,case when transaction_type='P' then  coalesce(paymentamount,0) end as debit ,\r\n"
			+ "case when transaction_type='R' then  coalesce(receiptamount,0) end as credit   from payments_receipts_old1 where to_char(date,'yyyy') like :year and to_char(date,'mm') like :month  and  banknameaccountno=:banknameaccountno\r\n"
			+ " and mode!='Cash' and payment_receipt_id NOT IN (select payment_receipt_id from brs_old) order by date", nativeQuery = true)
	List<Map<String, String>> getBRS_BBU_Old(String year, String month, String banknameaccountno);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO brs_old(payment_receipt_id,cheque_dd_receipt_no,bank_date,amount,transaction_type,userid,banknameaccountno,ip,security_id)"
			+ "values(:payment_receipt_id,:cheque_dd_receipt_no,:bank_date,cast(:amount as numeric),:transaction_type,:user_id,:banknameaccountno,:ip_address,:security_id)", nativeQuery = true)
	int CheckInsert_Brs_Old(String payment_receipt_id, String cheque_dd_receipt_no, Date bank_date, long amount,
			String transaction_type, String banknameaccountno, String user_id, String security_id, String ip_address);

	@Query(value = "select payment_receipt_id,to_char(date,'dd-mm-yyyy') as date,banknameaccountno,cheque_dd_receipt_no,transaction_type, coalesce(paymentamount,0)  as amount   from payments_receipts_old1\r\n"
			+ " where to_char(date,'yyyy-mm-dd') <:monthfirst  and  \r\n"
			+ "banknameaccountno=:banknameaccountno and transaction_type='P' and mode!='Cash' and payment_receipt_id NOT IN\r\n"
			+ " (select payment_receipt_id from brs_old) order by date", nativeQuery = true)
	List<Map<String, String>> getBRS_OutstandingPayments_Old(String monthfirst, String banknameaccountno);

	@Query(value = "select payment_receipt_id,to_char(date,'dd-mm-yyyy') as date,banknameaccountno,cheque_dd_receipt_no,transaction_type, coalesce(paymentamount,0)  as amount   from payments_receipts_old1\r\n"
			+ " where to_char(date,'yyyy-mm-dd') <:monthfirst  and  \r\n"
			+ "banknameaccountno=:banknameaccountno and transaction_type='R' and mode!='Cash' and payment_receipt_id NOT IN\r\n"
			+ " (select payment_receipt_id from brs_old) order by date", nativeQuery = true)
	List<Map<String, String>> getBRS_OutstandingReceipts_Old(String monthfirst, String banknameaccountno);

	// old brs passbook report
	@Query(value = " select date,remarks,coalesce(a.debit,0) as debit,coalesce(a.credit,0) as credit from (select to_char(bank_date,'dd/mm/yyyy') as date,case when remarks is null then p.receipt_description \r\n"
			+ "else remarks end as remarks,case when b.transaction_type='debit' then  coalesce(amount,0) end as debit ,case when b.transaction_type='credit' then  coalesce(amount,0) end as credit from brs_old b \r\n"
			+ "left join payments_receipts_old1 p on ( p.payment_receipt_id=b.payment_receipt_id) where 1=1 \r\n"
			+ "and  date(bank_date) between to_date(:fromDate,'dd/mm/yyyy')  and to_date(:toDate,'dd/mm/yyyy')\r\n"
			+ "order by date) a  order by to_date(date,'DD/MM/YYYY')", nativeQuery = true)
	List<Map<String, String>> BRS_PassBookReport_Old(String fromDate, String toDate);

	@Query(value = " select date,remarks,coalesce(a.debit,0) as debit,coalesce(a.credit,0) as credit from (select to_char(bank_date,'dd/mm/yyyy') as date,case when remarks is null then p.receipt_description \r\n"
			+ "else remarks end as remarks,case when b.transaction_type='debit' then  coalesce(amount,0) end as debit ,case when b.transaction_type='credit' then  coalesce(amount,0) end as credit from brs_old b \r\n"
			+ "left join payments_receipts_old1 p on ( p.payment_receipt_id=b.payment_receipt_id) where 1=1 \r\n"
			+ "and  date(bank_date) between to_date(:fromDate,'dd/mm/yyyy')  and to_date(:toDate,'dd/mm/yyyy')\r\n"
			+ "and b.banknameaccountno=:banknameaccountno\r\n"
			+ "order by date) a  order by to_date(date,'DD/MM/YYYY')", nativeQuery = true)
	List<Map<String, String>> BRS_PassBookReport_Old_bank(String fromDate, String toDate, String banknameaccountno);

	// old direct cd
	@Transactional
	@Modifying
	@Query(value = "INSERT into brs(payment_receipt_id,bank_date ,amount,transaction_type,userid,ip,banknameaccountno,remarks,security_id)\r\n"
			+ "values((SELECT  'D'||security_code||to_char(current_date,'ddmmyy')||  count(payment_receipt_id)+1  as payment_receipt_id from \r\n"
			+ "security_code_master \r\n"
			+ "left join ( select * from payments_receipts where date =current_date and payment_receipt_id like 'D%') p  on security_code =security_id\r\n"
			+ "where security_code=:security_id   group by security_code,current_date),:cheque_dd_receipt_no,:bank_date,cast(:amount as numeric),:transaction_type,:user_id,:banknameaccountno,:ip_address,:security_id)", nativeQuery = true)

	List<Map<String, String>> getBRS_Direct_CD_Old(String cheque_dd_receipt_no, Date bank_date, long amount,
			String transaction_type, String user_id, String banknameaccountno, String ip_address, String security_id);

}
