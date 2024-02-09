package in_Apcfss.Apofms.api.ofmsapi.services;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.request.BankDetails_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.Dummy_journal_voucher_heads_Req;
import in_Apcfss.Apofms.api.ofmsapi.request.EmpdetailsNewDisp_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.EmpdetailsNewPromotions_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.Empdetails_NewIncept_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.InterbankReq;
import in_Apcfss.Apofms.api.ofmsapi.request.Payment_receipts_Req;
import in_Apcfss.Apofms.api.ofmsapi.request.PayrollReports_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.SubHeadMaster;
import in_Apcfss.Apofms.api.ofmsapi.request.UpdateDistrictTransferData;

@Service
public interface EntryForms_Service {



	

	Map<String, Object> Bank_Details_Entry_Insertion(int accountid, String bankname, String branchname,
			String accountno, String operationaccount, String accounttype, String accountholder1, String accountholder2,
			double balance, String banknameaccountno, String accountholders, double remaining_balance,
			double available_balance, boolean is_approved_by_gm, String ifsc, String passbook_path, String ip_address);

	

	Map<String, Object> BankEntryMangerApproval(int accountid, String bankname, String branchname, String accountno,
			String operationaccount, String accounttype, String accountholder1, String accountholder2, double balance,
			String banknameaccountno, String accountholders, double remaining_balance, String ifsc,
			String passbook_path, String ip_address);

	Map<String, Object> BankEntryMangerReject(String banknameaccountno);

	List<Map<String, String>> GeneratePayment_Receipt_Id(String save_flag_Payment_Receipts_Journal);

	Map<String, Object> Generation_Receipt_Insertion(String payment_receipt_id, String receiptno, Date date,
			String cash_bank, double remaining_balance, String banknameaccountno, double receiptamount,
			double balance_in_account, String name, String mode, String cheque_dd_receipt_no,
			String receipt_description, String no_of_subheads,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, String upload_copy,
			HttpServletRequest request);

	Map<String, Object> Generation_Receipt_Managerapproval(String payment_receipt_id, String receiptno, Date date,
			String cash_bank, double remaining_balance, String banknameaccountno, double receiptamount,
			double balance_in_account, String name, String mode, String cheque_dd_receipt_no,
			String receipt_description, String no_of_subheads, int olderreceiptamount,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, String upload_copy,
			HttpServletRequest request);

	Map<String, Object> Generation_Receipt_Managerreject(String payment_receipt_id);

	Map<String, Object> Payment_Receipts_ManagerRemoveRow(String payment_receipt_id, String headid, String subheadid);

	Map<String, Object> Generation_Payment_Insertion(String payment_receipt_id, String receiptno, Date date,
			String cash_bank, double remaining_balance, String banknameaccountno, double paymentamount,
			double balance_in_account, String name, String mode, String cheque_dd_receipt_no,
			String receipt_description, String no_of_subheads,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, String upload_copy,
			HttpServletRequest request);

	Map<String, Object> Generation_Payment_Managerapproval(String payment_receipt_id, String receiptno, Date date,
			String cash_bank, double remaining_balance, String banknameaccountno, double paymentamount,
			double balance_in_account, String name, String mode, String cheque_dd_receipt_no,
			String receipt_description, String no_of_subheads, int oldpaymentamount,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, String upload_copy,
			HttpServletRequest request);

	Map<String, Object> Generation_Journal_Insertion(String payment_receipt_id, String voucher_id, Date date,
			String description, double credit, double debit, String upload_copy,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, HttpServletRequest request);
	Map<String, Object> Generation_Journal_Managerapproval(String payment_receipt_id, String voucher_id, Date date,
			String description, double credit, double debit, String upload_copy,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, HttpServletRequest request);
	Map<String, Object> Generation_Journal_reject(String payment_receipt_id);
	
	Map<String, Object> Generation_Journal_RowDelete(String payment_receipt_id ,String headid,String subheadid, long credit, long debit);
	

	List<Map<String, String>> getCadreWiseData();

	List<Map<String, String>> getCadreWiseDetailsByEmpID(String empid);

	List<Map<String, String>> getCadreWiseDetailsByEmpID_SubsequentPromotions(String empid);

	List<Map<String, String>> getDisExp_PresentConditon(String empid);

	List<Map<String, String>> getCadreWiseDetailsByEmpID_POWI(String empid);

	List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID(String empid);

	List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_SubsequentPromotions(String empid);

	List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_POWI(String empid);

	List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_Disp(String empid);

	List<Map<String, String>> getCadreWiseCasteDropdown();

	List<Map<String, String>> getCadreWiseDesignationDropdown();

	List<Map<String, String>> getCadreWiseDistDivManDropdowns(int dist_code, int div_code, int sdiv_code);

	List<Map<String, String>> getCadreWiseWorkConditionDropdown();

	List<Map<String, String>> getCadreWiseQualificationDropdown();

	List<Map<String, String>> getCadreWiseAppointmentDropdown();

	List<Map<String, Object>> Edit_Other_Basic_Data_Add(List<EmpdetailsNewDisp_Request> empdetailsNewDisp);

	List<Map<String, Object>> deleteInceptionDetails(String empid, String ip_address, int sno);

	List<Map<String, Object>> deletePromotionsDetails(String empid, String ip_address, int sno);

	List<Map<String, Object>> deletedisciplinaryDetails(String empid, String ip_address, int sno);

	List<Map<String, Object>> Add_button_insert_cadreWise(String empname, String empfather, int designation, int caste,
			String dob, String doj, String dor, int prdist, int prdiv, int prsdiv, int prmandal, String workcond,
			int natdist, int natdiv, int natsdiv, int natmandal, String otherwork, String othercase,
			String otherworkarea, String dojpresent, String eduappoint, String eduafterappoint, String cadreappoint,
			String cadrerefno, String cadrerefdate, String empaadhar, String emppan, String emppfuno,
			String prdiv_other, String prsubdiv_other, String prmandal_other, String natdist_other, String natdiv_other,
			String natsdiv_other, String natmandal_other, String other_qualifications, String no_of_the_bank,
			String account_no, String ifsc_code);

	String AddNewEmpid();

	List<Map<String, Object>> Edit_Subsequent_Promotion(
			List<EmpdetailsNewPromotions_Request> empdetails_new_promotions);

	List<Map<String, Object>> insertcadre(String empid, String empname, String empfather, int designation, int caste,
			String dob, String doj, String dor, int prdist, int prdiv, int prsdiv, int prmandal, String workcond,
			int natdist, int natdiv, int natsdiv, int natmandal, String otherwork, String othercase,
			String otherworkarea, String dojpresent, String eduappoint, String eduafterappoint, String cadreappoint,
			String cadrerefno, String cadrerefdate, String empaadhar, String emppan, String emppfuno,
			String prdiv_other, String prsubdiv_other, String prmandal_other, String natdist_other, String natdiv_other,
			String natsdiv_other, String natmandal_other, String other_qualifications);

	List<Map<String, Object>> Edit_Placeofworkingfrominception_Add(
			List<Empdetails_NewIncept_Request> empdetails_new_incept);

	Map<String, Object> InterBankTransferInsertion(String payment_id, String receiptno, Date date,
			String banknameaccountno, double balance_in_account, double paymentamount, String mode,
			String cheque_dd_receipt_no, String receipt_description, int no_of_banks, String upload_copy,
			List<InterbankReq> interbankreq);
//	Map<String, Object> InterBankTransferInsertion(String payment_id,String receipt_id, String receiptno, Date date,
//			String banknameaccountno,String banknameaccountno1,long balance_in_account,long balance_in_account1,long paymentamount, long receiptamount,String mode,
//			String cheque_dd_receipt_no, String receipt_description);

	// SUB-HEAD MASTER


	List<Map<String, String>> GetSubHeadsData(String headid);

	List<Map<String, Object>> AddSubheads(String headid, String subheadid, String subheadname);

	Map<String, Object> DeleteSubheads(List<SubHeadMaster> subHeadMaster);

//	List<Map<String, Object>> EditSubheads(List<SubHeadMaster> subHeadMaster);
	List<Map<String, Object>> EditSubheads(String subheadname, String subheadid, String subheadseqid);

	List<Map<String, String>> getVoucherListDropdown();

	List<Map<String, String>> getDescription_Heads(String voucher_id);

	List<Map<String, String>> getSalaryVoucherListDropdown();



	// ********************Heads Master **************** in APSHCL LOGIN
	List<Map<String, String>> getHeadsDetails();

	List<Map<String, String>> classificationDropdown();

	Map<String, Object> HeadMasterActions(Payment_receipts_Req payment_receipts_Req);

	List<Map<String, String>> GetHeadbyPayment_receipt_id(String payment_receipt_id);

	List<Map<String, String>> GetDataforBankEntnry_Manger();

	List<Map<String, String>> GetDataforGR_Manger();

	List<Map<String, String>> GetDataforGP_Manger();

	List<Map<String, Object>> GetSubHeadsDataforGR_Manger(String payment_receipt_id);

	List<Map<String, String>> GetDataforGJ_Manger();

	List<Map<String, Object>> GetSubHeadsDataforGJ_Manger(String payment_receipt_id);

	List<Map<String, String>> GetDataforGJ_Edit();

	List<Map<String, String>> GetJV_Edit_data(String fromDate, String toDate, String payment_receipt_id,String district);


	//GMADM (DISTRICT WISE TRANSFER)
	List<Map<String, String>> getDistrictwiseTransferData(String emp_type, String fromDistrict);



	Map<String, Object>  UpdateDistrictwiseTransferData(
			List<UpdateDistrictTransferData> updatedistricttranferdata);


	//New Employee registration
	List<Map<String, Object>> NewEmpRegistration(PayrollReports_Request payrollReports_Request);



}
