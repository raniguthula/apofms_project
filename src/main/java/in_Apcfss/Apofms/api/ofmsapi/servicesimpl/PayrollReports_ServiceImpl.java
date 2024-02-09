package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.PayrollReports_Repo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.Response.VariationInCal_PaybillResponse;
import in_Apcfss.Apofms.api.ofmsapi.request.EntryForms_GMF_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.Generate_Confirm_Updatelist;
import in_Apcfss.Apofms.api.ofmsapi.request.Generate_paybill_Updatelist;
import in_Apcfss.Apofms.api.ofmsapi.request.List_PayrollReports_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.MonthlyPaybillRequest;
import in_Apcfss.Apofms.api.ofmsapi.request.PayrollReports_Request;
import in_Apcfss.Apofms.api.ofmsapi.services.PayrollReports_Service;

@Service
public class PayrollReports_ServiceImpl implements PayrollReports_Service {

	@Autowired
	PayrollReports_Repo payrollReports_Repo;

	@Autowired
	UsersSecurityRepo usersSecurityRepo;

	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	public EntityManager entityManager;
	@Autowired
	CommonServiceImpl commonServiceImpl;

//1st payrollreport
	@Override
	public List<Map<String, String>> EmployeeTypeDropDown() {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		return payrollReports_Repo.EmployeeTypeDropDown();
	}

	@Override
	public List<Map<String, String>> ConfirmEmp_OnRolls_Report(int emptype_id, String district) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);

		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		List<Map<String, String>> confirmEmp = null;

		if (district.equals("state")) {
			System.out.println("called if");
			confirmEmp = payrollReports_Repo.ConfirmEmp_OnRolls_Report_state(emptype_id);
		}
		// DH User
		else if (district.equals("0")) {
			System.out.println("called else if");
			confirmEmp = payrollReports_Repo.ConfirmEmp_OnRolls_Report(emptype_id, security_id);

		}
//		// APSHCL DISTRICT
		else {
			security_id = district;
			confirmEmp = payrollReports_Repo.ConfirmEmp_OnRolls_Report(emptype_id, security_id);
		}

		return confirmEmp;

	}

//	@Override
//	public Map<String, Object> ConfirmEmp_WorkStatus_Report(String emp_id, String working_status) {
//		String user_id = CommonServiceImpl.getLoggedInUserId();
//		System.out.println("------Userid" + user_id);
//		Map<String, Object> responseMap = new LinkedHashMap<>();
//		int update_working_status = payrollReports_Repo.ConfirmEmp_WorkStatus_Report(emp_id, working_status);
//		if (update_working_status != 0) {
//			responseMap.put("SCODE", "01");
//			responseMap.put("SDESC", "Data Updated Sucessfully");
//		} else {
//			responseMap.put("SCODE", "02");
//			responseMap.put("SDESC", "Having as Error");
//		}
//
//		return responseMap;
//	}

	public Map<String, Object> ConfirmEmp_WorkStatus_Report(List<List_PayrollReports_Request> employeeStatusList) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int update_working_status = 0;

		for (List_PayrollReports_Request employeeStatus : employeeStatusList) {
			String emp_id = employeeStatus.getEmp_id();
			String working_status = employeeStatus.getWorking_status();
			update_working_status = payrollReports_Repo.ConfirmEmp_WorkStatus_Report(emp_id, working_status);
		}
		if (update_working_status != 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Data Updated Successfully");
		} else {

			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an Error");
		}
		return responseMap;

	}

	@Override
	public List<Map<String, String>> ConfirmEmp_EmpName_Report(int emptype_id, String emp_id, String year,
			String confirm_month, String payment_type) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		List<Map<String, String>> EmpNameData = null;
		if (emptype_id == 1 || emptype_id == 2 || emptype_id == 5) {
			System.out.println("hiiiiii");
			EmpNameData = payrollReports_Repo.ConfirmEmp_EmpName_Report125(emp_id, year, confirm_month, payment_type,
					security_id);

		} else if (emptype_id == 3) {
			EmpNameData = payrollReports_Repo.ConfirmEmp_EmpName_Report3(emp_id);
		} else if (emptype_id == 4) {
			EmpNameData = payrollReports_Repo.ConfirmEmp_EmpName_Report4(emp_id);
		}
		return EmpNameData;
	}

	@Override
	public Map<String, Object> ConfirmEmp_Update_Report(PayrollReports_Request payrollReports_Request) {

		int emptype_id = payrollReports_Request.getEmptype_id();
		String emp_id = payrollReports_Request.getEmp_id();
		String confirm_month = payrollReports_Request.getConfirm_month();
		String year = payrollReports_Request.getYear();
		String payment_type = payrollReports_Request.getPayment_type();
		String prefix = payrollReports_Request.getPrefix();
		String firstname = payrollReports_Request.getFirstname();
		String lastname = payrollReports_Request.getLastname();
		String surname = payrollReports_Request.getSurname();
		String fathername = payrollReports_Request.getFathername();
		String sex = payrollReports_Request.getSex();
		int designation = payrollReports_Request.getDesignation();
		String account_no = payrollReports_Request.getAccount_no();
		String others = payrollReports_Request.getOthers();
		String bank_name = payrollReports_Request.getBank_name();
		String branch_code = payrollReports_Request.getBranch_code();
		String ifsc = payrollReports_Request.getIfsc();
		// Earning Details
		double basic_pay_earnings = payrollReports_Request.getBasic_pay_earnings();
		double per_pay_earnings = payrollReports_Request.getPer_pay_earnings();
		double spl_pay_earnings = payrollReports_Request.getSpl_pay_earnings();
		double da_earnings = payrollReports_Request.getDa_earnings();
		double hra_earnings = payrollReports_Request.getHra_earnings();
		double cca_earnings = payrollReports_Request.getCca_earnings();
		double gp_earnings = payrollReports_Request.getGp_earnings();
		double ir_earnings = payrollReports_Request.getIr_earnings();
		double medical_earnings = payrollReports_Request.getMedical_earnings();
		double ca_earnings = payrollReports_Request.getCa_earnings();
		double spl_all = payrollReports_Request.getSpl_all();
		double misc_h_c = payrollReports_Request.getMisc_h_c();
		double addl_hra = payrollReports_Request.getAddl_hra();
		double sca = payrollReports_Request.getSca();
		// Deduction Details
		double gpfs_deductions = payrollReports_Request.getGpfs_deductions();
		double gpfl_deductions = payrollReports_Request.getGpfl_deductions();
		double gpfsa_deductions = payrollReports_Request.getGpfsa_deductions();
		double house_rent_deductions = payrollReports_Request.getHouse_rent_deductions();
		double gis_deductions = payrollReports_Request.getGis_deductions();
//		int pt_deductions = payrollReports_Request.getPt_deductions();
		double pt_deductions = payrollReports_Request.getPt_deductions();
		double it_deductions = payrollReports_Request.getIt_deductions();
		double cca_deductions = payrollReports_Request.getCca_deductions();
		double license_deductions = payrollReports_Request.getLicense_deductions();
		double con_decd_deductions = payrollReports_Request.getCon_decd_deductions();
		double lic_deductions = payrollReports_Request.getLic_deductions();
		double rcs_cont_deductions = payrollReports_Request.getRcs_cont_deductions();
		double sal_rec_deductions = payrollReports_Request.getSal_rec_deductions();
		double cmrf_deductions = payrollReports_Request.getCmrf_deductions();
		double fcf_deductions = payrollReports_Request.getFcf_deductions();
		double epf_l_deductions = payrollReports_Request.getEpf_l_deductions();
		double vpf_deductions = payrollReports_Request.getVpf_deductions();
		double apglis_deductions = payrollReports_Request.getApglis_deductions();
		double apglil_deductions = payrollReports_Request.getApglil_deductions();
		double epf_deductions = payrollReports_Request.getEpf_deductions();
		double ppf_deductions = payrollReports_Request.getPpf_deductions();
		double other_deductions = payrollReports_Request.getOther_deductions();
		// Loan Details

		double car_i_loan = payrollReports_Request.getCar_i_loan();
		double car_a_loan = payrollReports_Request.getCar_a_loan();
		double car_i_loanpi = payrollReports_Request.getCar_i_loanpi();
		double car_a_loanpi = payrollReports_Request.getCar_a_loanpi();
		double car_i_loanti = payrollReports_Request.getCar_i_loanti();
		double car_a_loanti = payrollReports_Request.getCar_a_loanti();

		double cyc_i_loan = payrollReports_Request.getCyc_i_loan();
		double cyc_a_loan = payrollReports_Request.getCyc_a_loan();
		double cyc_i_loanpi = payrollReports_Request.getCyc_i_loanpi();
		double cyc_a_loanpi = payrollReports_Request.getCyc_a_loanpi();
		double cyc_i_loanti = payrollReports_Request.getCyc_i_loanti();
		double cyc_a_loanti = payrollReports_Request.getCyc_a_loanti();

		double mca_i_loan = payrollReports_Request.getMca_i_loan();
		double mca_a_loan = payrollReports_Request.getMca_a_loan();
		double mca_i_loanpi = payrollReports_Request.getMca_i_loanpi();
		double mca_a_loanpi = payrollReports_Request.getMca_a_loanpi();
		double mca_i_loanti = payrollReports_Request.getMca_i_loanti();
		double mca_a_loanti = payrollReports_Request.getMca_a_loanti();

		double mar_a_loan = payrollReports_Request.getMar_a_loan();
		double mar_a_loanpi = payrollReports_Request.getMar_a_loanpi();
		double mar_i_loan = payrollReports_Request.getMar_i_loan();
		double mar_a_loanti = payrollReports_Request.getMar_a_loanti();
		double mar_i_loanti = payrollReports_Request.getMar_a_loanti();
		double mar_i_loanpi = payrollReports_Request.getMar_i_loanpi();

		double med_a_loan = payrollReports_Request.getMed_a_loan();
		double med_a_i_loan = payrollReports_Request.getMed_a_i_loan();
		double med_a_loanpi = payrollReports_Request.getMed_a_loanpi();
		double med_a_i_loanpi = payrollReports_Request.getMed_a_i_loanpi();
		double med_a_loanti = payrollReports_Request.getMed_a_i_loanti();
		double med_a_i_loanti = payrollReports_Request.getMed_a_i_loanti();

		double hba_loan = payrollReports_Request.getHba_loan();
		double hba_loanpi = payrollReports_Request.getHba_loanpi();
		double hba_loanti = payrollReports_Request.getHba_loanti();
		double hba1_loanti = payrollReports_Request.getHba1_loanti();
		double hba1_loan = payrollReports_Request.getHba1_loan();
		double hba1_loanpi = payrollReports_Request.getHba1_loanpi();

		double add_hba_loanpi = payrollReports_Request.getAdd_hba_loanpi();
		double add_hba1_loanpi = payrollReports_Request.getAdd_hba1_loanpi();
		double add_hba_loan = payrollReports_Request.getAdd_hba1_loan();
		double add_hba1_loan = payrollReports_Request.getAdd_hba1_loan();
		double add_hba_loanti = payrollReports_Request.getAdd_hba_loanti();
		double add_hba1_loanti = payrollReports_Request.getAdd_hba1_loanti();

		double comp_loanpi = payrollReports_Request.getComp_loanpi();
		double comp_loanti = payrollReports_Request.getComp_loanti();
		double comp1_loan = payrollReports_Request.getComp1_loan();
		double comp_loan = payrollReports_Request.getComp_loan();
		double comp1_loanti = payrollReports_Request.getComp1_loanti();
		double comp1_loanpi = payrollReports_Request.getComp1_loanpi();

		double hcesa_loan = payrollReports_Request.getHcesa_i_loan();
		double hcesa_I_loan = payrollReports_Request.getHcesa_i_loan();
		double hcesa_loanpi = payrollReports_Request.getHcesa_loanpi();
		double hcesa_i_loanpi = payrollReports_Request.getHcesa_i_loanpi();
		double hcesa_loanti = payrollReports_Request.getHcesa_i_loanti();
		double hcesa_i_loanti = payrollReports_Request.getHcesa_i_loanti();

		double hbao_loanti = payrollReports_Request.getHbao_loanti();
		double hbao_loanpi = payrollReports_Request.getHbao_loanpi();
		double hbao_loan = payrollReports_Request.getHbao_loan();
		double vij_bank_loan = payrollReports_Request.getVij_bank_loan();
		double vij_bank_loanti = payrollReports_Request.getVij_bank_loanti();
		double vij_bank_loanpi = payrollReports_Request.getVij_bank_loanpi();

		double ea_loanti = payrollReports_Request.getEa_loanti();
		double ea_loan = payrollReports_Request.getEa_loan();
		double ea_loanpi = payrollReports_Request.getEa_loanpi();
		double fa_loan = payrollReports_Request.getFa_loan();
		double fa_loanpi = payrollReports_Request.getFa_loanpi();
		double fa_loanti = payrollReports_Request.getFa_loanti();

		double sfa_loan = payrollReports_Request.getSfa_loan();
		double sfa_loanti = payrollReports_Request.getSfa_loanti();
		double sfa_loanpi = payrollReports_Request.getSfa_loanpi();
		double sal_adv_loanpi = payrollReports_Request.getSal_adv_loanpi();
		double sal_adv_loan = payrollReports_Request.getSal_adv_loan();
		double sal_adv_loanti = payrollReports_Request.getSal_adv_loanti();

		double hr_arrear_loan = payrollReports_Request.getHr_arrear_loan();
		double hr_arrear_loanpi = payrollReports_Request.getHr_arrear_loanpi();
		double hr_arrear_loanti = payrollReports_Request.getHr_arrear_loanti();
		double cell_loan = payrollReports_Request.getCell_loan();
		double cell_loanpi = payrollReports_Request.getCell_loanpi();
		double cell_loanti = payrollReports_Request.getCell_loanti();

		double staff_pl_loan = payrollReports_Request.getStaff_pl_loan();
		double staff_pl_loanti = payrollReports_Request.getStaff_pl_loanti();
		double staff_pl_loanpi = payrollReports_Request.getStaff_pl_loanpi();
		double court_loan = payrollReports_Request.getCourt_loan();
		double court_loanti = payrollReports_Request.getCourt_loanti();
		double court_loanpi = payrollReports_Request.getCourt_loanpi();

		// Os Earn Ded Details
		double os_basic_pay_earnings = payrollReports_Request.getOs_basic_pay_earnings();
		double os_hra_earnings = payrollReports_Request.getOs_hra_earnings();
		double os_medical_earnings = payrollReports_Request.getOs_medical_earnings();
		double os_ca_earnings = payrollReports_Request.getOs_ca_earnings();
		double os_performance_earnings = payrollReports_Request.getOs_ec_epf();
		double os_ec_epf = payrollReports_Request.getOs_ec_epf();
		double os_ees_epf_deductions = payrollReports_Request.getOs_ees_epf_deductions();
		double os_ers_epf_deductions = payrollReports_Request.getOs_ers_epf_deductions();
		double os_prof_tax_deductions = payrollReports_Request.getOs_prof_tax_deductions();
		double os_other_deductions = payrollReports_Request.getOs_other_deductions();
		String os_work_place = payrollReports_Request.getOs_work_place();
		String os_location = payrollReports_Request.getOs_location();
		// Nmr Earn Ded Details
		double nmr_gross_earnings = payrollReports_Request.getNmr_gross_earnings();
		double nmr_postalrd_deductions = payrollReports_Request.getNmr_postalrd_deductions();
		double nmr_tds_deductions = payrollReports_Request.getNmr_tds_deductions();
		double nmr_fa_deductions = payrollReports_Request.getNmr_fa_deductions();
		double nmr_ea_deductions = payrollReports_Request.getNmr_ea_deductions();
		double nmr_ma_deductions = payrollReports_Request.getNmr_ma_deductions();
		double nmr_lic_deductions = payrollReports_Request.getNmr_lic_deductions();
		double nmr_otherliab_deductions = payrollReports_Request.getNmr_otherliab_deductions();
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);

		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		List<Map<String, String>> EmpNameData = null;
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int Insert_Emp_Details = 0;
		int Insert_Earning_Details = 0;
		int Insert_Deduction_Details = 0;
		int Insert_Loan_Details = 0;
		int Insert_OsEarnDed_Details = 0;
		int Insert_NmrEarnDed_Details = 0;

		int Delete_Emp_Details = 0;
		int Delete_Earning_Details = 0;
		int Delete_Deduction_Details = 0;
		int Delete_Loan_Details = 0;
		int Delete_OsEarnDed_Details = 0;
		int Delete_NmrEarnDed_Details = 0;
		Insert_Emp_Details = payrollReports_Repo.ConfirmEmp_Insert_Report_Emp_125(security_id, emp_id, prefix,
				firstname, lastname, surname, fathername, emptype_id, sex, designation, account_no, others, bank_name,
				branch_code, ifsc, confirm_month, payment_type, year);
		System.out.println(ifsc + "...ifsc");
		if (emptype_id == 1 || emptype_id == 2 || emptype_id == 5) {

			System.out.println("Insert_Emp_Details:::" + Insert_Emp_Details);

			Insert_Earning_Details = payrollReports_Repo.ConfirmEmp_Insert_Report_Earning_125(security_id, emp_id,
					basic_pay_earnings, per_pay_earnings, spl_pay_earnings, da_earnings, hra_earnings, cca_earnings,
					gp_earnings, ir_earnings, medical_earnings, ca_earnings, spl_all, misc_h_c, addl_hra, sca,
					confirm_month, payment_type, year);
			System.out.println("Insert_Earning_Details:::" + Insert_Earning_Details);

			Insert_Deduction_Details = payrollReports_Repo.ConfirmEmp_Insert_Report_Deductions_125(security_id, emp_id,
					gpfs_deductions, gpfl_deductions, gpfsa_deductions, house_rent_deductions, gis_deductions,
					pt_deductions, it_deductions, cca_deductions, license_deductions, con_decd_deductions,
					lic_deductions, rcs_cont_deductions, sal_rec_deductions, cmrf_deductions, fcf_deductions,
					epf_l_deductions, vpf_deductions, apglis_deductions, apglil_deductions, epf_deductions,
					ppf_deductions, other_deductions, confirm_month, payment_type, year);
			System.out.println("Insert_Deduction_Details:::" + Insert_Deduction_Details);

			Insert_Loan_Details = payrollReports_Repo.ConfirmEmp_Insert_Report_Loan_125(security_id, emp_id, car_i_loan,
					car_a_loan, cyc_i_loan, cyc_a_loan, mca_i_loan, mca_a_loan, mar_a_loan, med_a_loan, hba_loan,
					hba1_loan, comp_loan, fa_loan, ea_loan, cell_loan, add_hba_loan, add_hba1_loan, sal_adv_loan,
					sfa_loan, med_a_i_loan, hcesa_loan, hcesa_I_loan, staff_pl_loan, court_loan, vij_bank_loan,
					mar_i_loan, hr_arrear_loan, hbao_loan, comp1_loan, car_i_loanpi, car_a_loanpi, cyc_i_loanpi,
					cyc_a_loanpi, mca_i_loanpi, mca_a_loanpi, mar_a_loanpi, med_a_loanpi, hba_loanpi, hba1_loanpi,
					comp_loanpi, fa_loanpi, ea_loanpi, cell_loanpi, add_hba_loanpi, add_hba1_loanpi, sal_adv_loanpi,
					sfa_loanpi, med_a_i_loanpi, hcesa_loanpi, hcesa_i_loanpi, staff_pl_loanpi, hr_arrear_loanpi,
					hbao_loanpi, comp1_loanpi, car_i_loanti, car_a_loanti, cyc_i_loanti, cyc_a_loanti, mca_i_loanti,
					mca_a_loanti, mar_a_loanti, med_a_loanti, hba_loanti, hba1_loanti, comp_loanti, fa_loanti,
					ea_loanti, cell_loanti, add_hba_loanti, add_hba1_loanti, sal_adv_loanti, sfa_loanti, med_a_i_loanti,
					hcesa_loanti, hcesa_i_loanti, staff_pl_loanti, court_loanti, vij_bank_loanti, mar_i_loanti,
					hr_arrear_loanti, hbao_loanti, comp1_loanti, court_loanpi, vij_bank_loanpi, mar_i_loanpi,
					confirm_month, payment_type, year);

//			Delete_Emp_Details = payrollReports_Repo.ConfirmEmp_Delete_Report_Emp_125(emp_id);
//			System.out.println("Insert_Loan_Details:::" + Insert_Loan_Details);
//			Delete_Earning_Details = payrollReports_Repo.ConfirmEmp_Delete_Report_Earning_125(emp_id);
//			System.out.println("Delete__Earning_Details:::" + Delete_Earning_Details);
//			Delete_Deduction_Details = payrollReports_Repo.ConfirmEmp_Delete_Report_Deduction_125(emp_id);
//			System.out.println("Delete__Deduction_Details:::" + Delete_Deduction_Details);
//			Delete_Loan_Details = payrollReports_Repo.ConfirmEmp_Delete_Report_Loan_125(emp_id);
//			System.out.println("Delete__Loan_Details:::" + Delete_Loan_Details);
			if (Insert_Emp_Details != 0 && Insert_Earning_Details != 0 && Insert_Deduction_Details != 0
					&& Insert_Loan_Details != 0) {

				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Updated Successfully");
			} else {

				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having Error");
			}

		} else if (emptype_id == 3) {
			System.out.println("os_work_place" + os_work_place);
			System.out.println("os_location" + os_location);
			Insert_OsEarnDed_Details = payrollReports_Repo.ConfirmEmp_Insert_Os_Report3(security_id, emp_id,
					os_basic_pay_earnings, os_hra_earnings, os_medical_earnings, os_ca_earnings,
					os_performance_earnings, os_ec_epf, os_ees_epf_deductions, os_ers_epf_deductions,
					os_prof_tax_deductions, os_other_deductions, os_work_place, os_location, confirm_month,
					payment_type, year);
			System.out.println("Insert_OsEarnDed_Details:::" + Insert_OsEarnDed_Details);
//			Delete_OsEarnDed_Details = payrollReports_Repo.ConfirmEmp_Delete_Os_Report3(emp_id);
			if (Insert_Emp_Details != 0 && Insert_OsEarnDed_Details != 0) { 
				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Updated Successfully");
			} else {

				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having Error");
			}

		} else if (emptype_id == 4) {
			Insert_NmrEarnDed_Details = payrollReports_Repo.ConfirmEmp_Insert_Nmr_Report4(security_id, emp_id,
					nmr_gross_earnings, os_ees_epf_deductions, nmr_postalrd_deductions, os_prof_tax_deductions,
					os_work_place, os_location, nmr_tds_deductions, nmr_fa_deductions, nmr_ea_deductions,
					nmr_ma_deductions, nmr_lic_deductions, nmr_otherliab_deductions, confirm_month, payment_type, year);

			System.out.println("Insert_NmrEarnDed_Details:::" + Insert_NmrEarnDed_Details);

//			Delete_NmrEarnDed_Details = payrollReports_Repo.ConfirmEmp_Delete_Nmr_Report4(emp_id);
			if (Insert_Emp_Details != 0 && Insert_NmrEarnDed_Details != 0) {
				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Updated Successfully");
			} else {

				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having Error");
			}
		}

		return responseMap;

	}

//2nd Payroll report
	public List<Map<String, String>> DesignationDropDown() {
		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);

		return payrollReports_Repo.DesignationDropDown();
	}

	@Override
	public List<Map<String, String>> MonthsDropDown() {
		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);
		return payrollReports_Repo.getMonthsDropDown();
	}

	public List<Map<String, String>> YearsDropDown() {
		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);
		return payrollReports_Repo.YearsDropDown();
	}

	@Override
	public List<Map<String, String>> GetMonthlyPaybillConfirmationReport(String emp_type, String payment_type,
			String confirm_month, String year) {
		System.out.println("service");
		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		System.out.println("security_id" + security_id);
		System.out.println("GetMonthlyPaybillConfirmationReport");
		return payrollReports_Repo.GetMonthlyPaybillConfirmationReport(emp_type, payment_type, confirm_month, year,
				security_id);
	}

	@Override
	public List<Map<String, String>> GetMonthlyPaybillConfirmationReport_AfterConfirm(String emp_type,
			String payment_type, String confirm_month, String year) {
		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		System.out.println("security_id" + security_id);
		System.out.println("GetMonthlyPaybillConfirmationReport_AfterConfirm");
		return payrollReports_Repo.GetMonthlyPaybillConfirmationReport_AfterConfirm(emp_type, payment_type,
				confirm_month, year, security_id);
	}

	public List<Map<String, String>> GetMonthlyPaybillConfirmationReport_beforeConfirm(String emp_type,
			String payment_type, String confirm_month, String year) {
		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		System.out.println("security_id" + security_id);
		System.out.println("GetMonthlyPaybillConfirmationReport_beforeConfirm");
		return payrollReports_Repo.GetMonthlyPaybillConfirmationReport_beforeConfirm(emp_type, payment_type,
				confirm_month, year, security_id);
	}

	@Override
	public Map<String, Object> paybillconfirm_UpdatList(List<Generate_paybill_Updatelist> genertedPaybillList) {

		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		for (Generate_paybill_Updatelist generetedstatus : genertedPaybillList) {
			String emp_id = generetedstatus.getEmp_id();
			String emp_type = generetedstatus.getEmp_type();
			String payment_type = generetedstatus.getPayment_type();
			String confirm_month = generetedstatus.getConfirm_month();
			String year = generetedstatus.getYear();
			System.out.println("payment_type" + payment_type);
			System.out.println("year" + year);
			System.out.println("confirm_month" + confirm_month);
			System.out.println("emp_id" + emp_id);
			System.out.println("emp_type" + emp_type);

			int generted_status = payrollReports_Repo.paybillconfirm_PaybillUpdatList(emp_id, emp_type, confirm_month,
					year, payment_type);

			System.out.println("generted_status" + generted_status);
			if (generted_status != 0) {
				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Updated Successfully");
			} else {
				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having an Error");
			}

		}
		return responseMap;

	}

	@Override
	public Map<String, Object> confirm_UpdatList(MonthlyPaybillRequest monthlyPaybillRequest) {
		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		String emp_type = monthlyPaybillRequest.getEmp_type();

//		TransactionDefinition def = new DefaultTransactionDefinition();
//        TransactionStatus status = transactionManager.getTransaction(def);

		List<Generate_Confirm_Updatelist> GenertedConfirmList = monthlyPaybillRequest.getGenerateConfirmlist();
		for (Generate_Confirm_Updatelist generetedstatus : GenertedConfirmList) {
			String emp_id = generetedstatus.getEmp_id();
			String payment_type = generetedstatus.getPayment_type();
			String confirm_month = generetedstatus.getConfirm_month();
			String year = generetedstatus.getYear();
			int confirmupdate_empdetails = payrollReports_Repo
					.paybillconfirm_UpdatList_empdetails_Confirm(confirm_month, payment_type, year, emp_id);
			System.out.println("confirmupdate_empdetails" + confirmupdate_empdetails);
			if (emp_type.equals("1") || emp_type.equals("2") || emp_type.equals("5")) {

				int confirmupdate_earningdetails = payrollReports_Repo
						.paybillconfirm_UpdatList_earningdetails_Confirm(confirm_month, payment_type, year, emp_id);
				System.out.println("confirmupdate_earningdetails" + confirmupdate_earningdetails);
				int confirmupdate_deductiondetails = payrollReports_Repo
						.paybillconfirm_UpdatList_deductiondetails_Confirm(confirm_month, payment_type, year, emp_id);
				System.out.println("confirmupdate_deductiondetails" + confirmupdate_deductiondetails);
				int confirmupdate_loandetails = payrollReports_Repo
						.paybillconfirm_UpdatList_loandetails_Confirm(confirm_month, payment_type, year, emp_id);
				System.out.println("confirmupdate_loandetails" + confirmupdate_loandetails);
				if (confirmupdate_earningdetails != 0 && confirmupdate_deductiondetails != 0
						&& confirmupdate_loandetails != 0) {
					responseMap.put("emp_id", emp_id);
					responseMap.put("SCODE", "01");
					responseMap.put("SDESC", "Data Updated Successfully");
//					transactionManager.commit(status);

				} else {
					responseMap.put("emp_id", emp_id);
					responseMap.put("SCODE", "02");
					responseMap.put("SDESC", "Having Error at emp_type 1,2,5");
//					transactionManager.rollback(status);
				}

			} else if (emp_type.equals("3")) {
				int confirmupdate_os_earn_ded_details_confirm = payrollReports_Repo
						.paybillconfirm_UpdatList_os_earn_ded_Confirm(confirm_month, payment_type, year, emp_id);
				if (confirmupdate_os_earn_ded_details_confirm != 0) {
					responseMap.put("emp_id", emp_id);
					responseMap.put("SCODE", "01");
					responseMap.put("SDESC", "Data Updated Successfully");

				} else {
					responseMap.put("emp_id", emp_id);
					responseMap.put("SCODE", "02");
					responseMap.put("SDESC", "Having Error at emp_type 3");
				}
			} else if (emp_type.equals("4")) {
				int confirmupdate_nmr_earn_ded_details_confirm = payrollReports_Repo
						.paybillconfirm_UpdatList_nmr_earn_ded_Confirm(confirm_month, payment_type, year, emp_id);
				if (confirmupdate_nmr_earn_ded_details_confirm != 0) {
					responseMap.put("emp_id", emp_id);
					responseMap.put("SCODE", "01");
					responseMap.put("SDESC", "Data Updated Successfully");

				} else {
					responseMap.put("emp_id", emp_id);
					responseMap.put("SCODE", "02");
					responseMap.put("SDESC", "Having Error at emp_type 4");
				}

			}
			if (confirmupdate_empdetails != 0) {
				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Updated Successfully");

			} else {
				responseMap.put("emp_id", emp_id);
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having Error");
			}

		}

		return responseMap;

	}

//3rd PayrollReport
	@Override
	public List<Map<String, String>> GenertedMonthlyPaybill_Report(String emp_type, String payment_type,
			String confirm_month, String year) {

		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		List<Map<String, String>> GenertedMonthly = null;

//		if (emp_type.equals("1") || emp_type.equals("2") || emp_type.equals("5") && (payment_type=="All")) {
//			
//			System.out.println("1,2,3,All");
//			GenertedMonthly = payrollReports_Repo.GenertedMonthlyPaybill_Report125_All(security_id, confirm_month,
//					year);
//
//		} else 
		if (emp_type.equals("1") || emp_type.equals("2") || emp_type.equals("5")) {
			System.out.println("1,2,3");
			GenertedMonthly = payrollReports_Repo.GenertedMonthlyPaybill_Report125(security_id, emp_type, payment_type,
					confirm_month, year);

		} else if (emp_type.equals("3")) {
			GenertedMonthly = payrollReports_Repo.GenertedMonthlyPaybill_Report3(security_id, payment_type,
					confirm_month, year);
		} else if (emp_type.equals("4")) {
			GenertedMonthly = payrollReports_Repo.GenertedMonthlyPaybill_Report4(security_id, payment_type,
					confirm_month, year);
		}
		return GenertedMonthly;
	}

	// 4th payroll report
	@Override
	public List<Map<String, String>> getEmpNamesbyType(String emp_type) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		List<Map<String, String>> empNames = null;
		if (security_id.equals("00")) {
			empNames = payrollReports_Repo.getEmpNamesbyType_Apshcl(emp_type);
		} else {
			empNames = payrollReports_Repo.getEmpNamesbyType(security_id, emp_type);
		}

		return empNames;
	}

	public List<Map<String, String>> getEmpNamesbyTypeandDistrict(String emp_type, String district) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		System.out.println("district" + district);
		security_id = district;
		return payrollReports_Repo.getEmpNamesbyTypeandDistrict(security_id, emp_type);
	}

	public List<Map<String, String>> empPayParticluarsReport(String emp_id, String emp_type, String payment_type,
			String fromMonth, String toMonth, String fromYear, String toYear) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		List<Map<String, String>> EmpPayParticluars = null;
		if ((emp_type.equals("1") || emp_type.equals("2") || emp_type.equals("5")) && (payment_type.equals("All"))) {
			EmpPayParticluars = payrollReports_Repo.empPayParticularsReport125_All(emp_id, fromMonth, toMonth, fromYear,
					toYear, emp_type);

		} else if ((emp_type.equals("1") || emp_type.equals("2") || emp_type.equals("5"))
				&& (payment_type.equals("reg"))) {

			EmpPayParticluars = payrollReports_Repo.empPayParticularsReport125_reg(emp_id, fromMonth, toMonth, fromYear,
					toYear, emp_type);

		} else if ((emp_type.equals("1") || emp_type.equals("2") || emp_type.equals("5"))
				&& (payment_type.equals("supp"))) {

			EmpPayParticluars = payrollReports_Repo.empPayParticularsReport125_supp(emp_id, fromMonth, toMonth,
					fromYear, toYear, emp_type);

		} else if (emp_type.equals("3") && payment_type.equals("All")) {
			EmpPayParticluars = payrollReports_Repo.empPayParticluarsReport3_All(emp_id, emp_type, fromMonth, toMonth,
					fromYear, toYear);
		} else if (emp_type.equals("3") && payment_type.equals("reg")) {
			EmpPayParticluars = payrollReports_Repo.empPayParticluarsReport3_reg(emp_id, emp_type, fromMonth, toMonth,
					fromYear, toYear);
		} else if (emp_type.equals("3") && payment_type.equals("supp")) {
			EmpPayParticluars = payrollReports_Repo.empPayParticluarsReport3_supp(emp_id, emp_type, fromMonth, toMonth,
					fromYear, toYear);

		} else if (emp_type.equals("4") && payment_type.equals("All")) {
			EmpPayParticluars = payrollReports_Repo.empPayParticluarsReport4_All(emp_id, emp_type, fromMonth, toMonth,
					fromYear, toYear);
		} else if (emp_type.equals("4") && payment_type.equals("reg")) {
			EmpPayParticluars = payrollReports_Repo.empPayParticluarsReport4_reg(emp_id, emp_type, fromMonth, toMonth,
					fromYear, toYear);
		} else if (emp_type.equals("4") && payment_type.equals("supp")) {
			EmpPayParticluars = payrollReports_Repo.empPayParticluarsReport4_supp(emp_id, emp_type, fromMonth, toMonth,
					fromYear, toYear);
		}
		return EmpPayParticluars;
	}

	@Override
	public List<Map<String, String>> Emp_pay_values(String emp_id, String emp_type, String fromMonth, String toMonth,
			String fromYear, String toYear, String payment_type) {
		List<Map<String, String>> Emppay = null;
		if (payment_type.equals("reg") || payment_type.equals("supp")) {
			Emppay = payrollReports_Repo.Emp_pay_values(emp_id, emp_type, fromMonth, toMonth, fromYear, toYear,
					payment_type);
		} else {
			Emppay = payrollReports_Repo.Emp_pay_values_All(emp_id, emp_type, fromMonth, toMonth, fromYear, toYear);
		}
		return Emppay;
	}

	// --
	public List<Map<String, Object>> ViewDetailedPayParticular(String emp_id, String emp_type, String payment_type,
			String fromMonth, String toMonth, String fromYear, String toYear) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> data125 = null;
		List<Map<String, Object>> sumdata125 = null;
		List<Map<String, Object>> heading = null;
		List<Map<String, Object>> totalsum125 = null;
		List<Map<String, Object>> headingList = new ArrayList<>();
		if ((emp_type.equals("1") || emp_type.equals("2") || emp_type.equals("5"))) {
			if (payment_type.equals("reg") || payment_type.equals("supp")) {
				System.out.println("called if");
				data125 = payrollReports_Repo.ViewDetailedPayParticulardata125(emp_id, fromMonth, toMonth, fromYear,
						toYear, emp_type, payment_type);

				sumdata125 = payrollReports_Repo.ViewDetailedPayParticularsumdata125(emp_id, fromMonth, toMonth,
						fromYear, toYear, emp_type, payment_type);
				heading = payrollReports_Repo.ViewDetailedPayParticularheading125();

				for (Map<String, Object> item : heading) {
					for (Map.Entry<String, Object> entry : item.entrySet()) {
						Map<String, Object> individualMap = new HashMap<>();
						individualMap.put(entry.getKey(), entry.getValue());
						headingList.add(individualMap);
					}
				}

				totalsum125 = payrollReports_Repo.ViewDetailedPayParticulartotalsum125(emp_id, fromMonth, toMonth,
						fromYear, toYear, emp_type, payment_type);

			} else {
				System.out.println("called else");
				data125 = payrollReports_Repo.ViewDetailedPayParticulardata125All(emp_id, fromMonth, toMonth, fromYear,
						toYear, emp_type);
				System.out.println("payment_type" + payment_type);
				sumdata125 = payrollReports_Repo.ViewDetailedPayParticularsumdata125All(emp_id, fromMonth, toMonth,
						fromYear, toYear, emp_type);
				heading = payrollReports_Repo.ViewDetailedPayParticularheading125();

				for (Map<String, Object> item : heading) {
					for (Map.Entry<String, Object> entry : item.entrySet()) {
						Map<String, Object> individualMap = new HashMap<>();
						individualMap.put(entry.getKey(), entry.getValue());
						headingList.add(individualMap);
					}
				}

				totalsum125 = payrollReports_Repo.ViewDetailedPayParticulartotalsum125All(emp_id, fromMonth, toMonth,
						fromYear, toYear, emp_type);
			}

		}

		else if (emp_type.equals("3")) {
			System.out.println("called 3");
			if (payment_type.equals("reg") || payment_type.equals("supp")) {
				System.out.println("called if");
				data125 = payrollReports_Repo.ViewDetailedPayParticulardata3(emp_id, fromMonth, toMonth, fromYear,
						toYear, emp_type, payment_type);
				sumdata125 = payrollReports_Repo.ViewDetailedPayParticularsumdata3(emp_id, fromMonth, toMonth, fromYear,
						toYear, payment_type);
				heading = payrollReports_Repo.ViewDetailedPayParticularheading3();

				for (Map<String, Object> item : heading) {
					for (Map.Entry<String, Object> entry : item.entrySet()) {
						Map<String, Object> individualMap = new HashMap<>();
						individualMap.put(entry.getKey(), entry.getValue());
						headingList.add(individualMap);
					}
				}

				totalsum125 = payrollReports_Repo.ViewDetailedPayParticulartotalsum3(emp_id, fromMonth, toMonth,
						fromYear, toYear, payment_type);
			} else {
				System.out.println("called else");
				data125 = payrollReports_Repo.ViewDetailedPayParticulardata3All(emp_id, fromMonth, toMonth, fromYear,
						toYear, emp_type);
				System.out.println("payment_type" + payment_type);
				sumdata125 = payrollReports_Repo.ViewDetailedPayParticularsumdata3All(emp_id, fromMonth, toMonth,
						fromYear, toYear);
				heading = payrollReports_Repo.ViewDetailedPayParticularheading3();

				for (Map<String, Object> item : heading) {
					for (Map.Entry<String, Object> entry : item.entrySet()) {
						Map<String, Object> individualMap = new HashMap<>();
						individualMap.put(entry.getKey(), entry.getValue());
						headingList.add(individualMap);
					}
				}

				totalsum125 = payrollReports_Repo.ViewDetailedPayParticulartotalsum3All(emp_id, fromMonth, toMonth,
						fromYear, toYear);
			}
		}

		else {
			System.out.println("called 4");
			if (payment_type.equals("reg") || payment_type.equals("supp")) {
				System.out.println("called if");
				data125 = payrollReports_Repo.ViewDetailedPayParticulardata4(emp_id, fromMonth, toMonth, fromYear,
						toYear, emp_type, payment_type);
				sumdata125 = payrollReports_Repo.ViewDetailedPayParticularsumdata4(emp_id, fromMonth, toMonth, fromYear,
						toYear, payment_type);
				heading = payrollReports_Repo.ViewDetailedPayParticularheading4();

				for (Map<String, Object> item : heading) {
					for (Map.Entry<String, Object> entry : item.entrySet()) {
						Map<String, Object> individualMap = new HashMap<>();
						individualMap.put(entry.getKey(), entry.getValue());
						headingList.add(individualMap);
					}
				}

				totalsum125 = payrollReports_Repo.ViewDetailedPayParticulartotalsum4(emp_id, fromMonth, toMonth,
						fromYear, toYear, emp_type, payment_type);
			} else {
				System.out.println("called else");
				data125 = payrollReports_Repo.ViewDetailedPayParticulardata4All(emp_id, fromMonth, toMonth, fromYear,
						toYear, emp_type);
				System.out.println("payment_type" + payment_type);
				sumdata125 = payrollReports_Repo.ViewDetailedPayParticularsumdata4All(emp_id, fromMonth, toMonth,
						fromYear, toYear);
				heading = payrollReports_Repo.ViewDetailedPayParticularheading4();

				for (Map<String, Object> item : heading) {
					for (Map.Entry<String, Object> entry : item.entrySet()) {
						Map<String, Object> individualMap = new HashMap<>();
						individualMap.put(entry.getKey(), entry.getValue());
						headingList.add(individualMap);
					}
				}

				totalsum125 = payrollReports_Repo.ViewDetailedPayParticulartotalsum4All(emp_id, fromMonth, toMonth,
						fromYear, toYear, emp_type);
			}
		}
		if (data125.size() > 0)

		{
			map.put("responseToken", 01);
			map.put("responseMsg", "Data fetched Successfully");
			map.put("data125", data125);
			map.put("sumdata125", sumdata125);
			map.put("heaidng125", headingList);
			map.put("totalsum125", totalsum125);
		} else {
			map.put("responseToken", 02);
			map.put("responseMsg", "Data not fetched");
		}
		respose.add(map);

		return respose;

	}

	@Override
	public List<VariationInCal_PaybillResponse> VariationCalPaybill(String confirm_month, String year, String category,
			String district, String hra_percent) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		String role_id = usersSecurityRepo.GetRole_Id(user_id);
		String cond = "";
		String sql = "";
		String security_idearnings = "";
		String percentage = "";
		String getdistrict_name = "a.security_id,(select name from cgg_master_districts where code=a.security_id)";
		String category_col = "";
		if (role_id.equals("2") || role_id.equals("4") || role_id.equals("5")) {
			System.out.println("district" + district);
			if (district.equals("all")) {
				System.out.println("called all");
				security_id = " and edc.security_id='" + security_id + "'";
//			security_idearnings = " and a.security_id='" + security_id + "'";

			} else if (!district.equals("all")) {

				security_id = " and edc.security_id='" + security_id + "'";
				security_idearnings = " and a.security_id='" + district + "'";
			}

		}
		if (category.equals("DA")) {
			category_col = "da_earnings";
			percentage = "51.81";
		} else if (category.equals("IR")) {
			category_col = "ir_earnings";
			percentage = "22";
		} else if (category.equals("HRA")) {
			category_col = "hra_earnings";
			percentage = hra_percent;
		} else if (category.equals("EPF")) {
			category_col = "epf_deductions";
			percentage = "12";
		}
		if (!category.equals("EPF")) {
			System.out.println("category" + category);
			System.out.println("security_id" + security_id);
			sql = "SELECT a.emp_id,prefix||' '||firstname||' '||lastname||' '||surname as emp_name,basic_pay_earnings,a.confirm_month,year,payment_type,round("
					+ category_col + "),actual,emp_type," + getdistrict_name + " "
					+ "from ( SELECT security_id,confirm_month,emp_id,basic_pay_earnings," + category_col
					+ ",round((basic_pay_earnings*" + percentage + ")/100) as actual from "
					+ "earnings_details_confirm a where payment_type='reg' and emp_id in (select emp_id from emp_details_confirm edc where generate_paybill='t'"
					+ " " + security_id + " and year='" + year + "' and confirm_month='" + confirm_month
					+ "'))a  join emp_details_confirm edc on a.emp_id=edc.emp_id and " + "edc.confirm_month='"
					+ confirm_month + "' and year='" + year + "' " + security_id + " where a." + category_col
					+ "!=a.actual and" + " a.confirm_month='" + confirm_month + "' " + security_idearnings
					+ " and edc.payment_type='reg' order by a.security_id,a.emp_id,a.confirm_month";
			System.out.println("sql in not epf" + sql);

		} else {
			System.out.println("called else");
			System.out.println("security_id" + security_id);
			sql = "SELECT a.emp_id,prefix||' '||firstname||' '||lastname||' '||surname as emp_name,round(basic_pay_da)basic_pay_da,round(epf_deductions)epf_deductions,actual,emp_type,a.security_id,payment_type,"
					+ "(select name from cgg_master_districts where code=a.security_id) from emp_details_confirm a join (SELECT bp.emp_id,basic_pay_da,epf_deductions,"
					+ "round(((basic_pay_da*" + percentage
					+ ")/100)) as actual,security_id  from (SELECT emp_id,epf_deductions,security_id from deductions_details_confirm a"
					+ " where confirm_month ='" + confirm_month + "' and year='" + year + "'  " + security_idearnings
					+ " "
					+ "and payment_type='reg' and emp_id in (select emp_id from emp_details_confirm where confirm_month ='"
					+ confirm_month + "' " + "and year='" + year + "' " + security_idearnings
					+ " and payment_type='reg' and generate_paybill='t'))epfd join"
					+ " (SELECT emp_id,basic_pay_earnings+da_earnings as basic_pay_da from earnings_details_confirm edc where confirm_month ='"
					+ confirm_month + "'  " + security_id + " "
					+ "and payment_type='reg' and emp_id in (select emp_id from emp_details_confirm edc where confirm_month ='"
					+ confirm_month + "' " + "and year='" + year + "' " + security_id
					+ " and payment_type='reg' and generate_paybill='t'))bp  on epfd.emp_id=bp.emp_id)total on a.confirm_month ='"
					+ confirm_month + "' and year='" + year + "' " + "" + security_idearnings
					+ "  and a.payment_type='reg' and a.generate_paybill='t' and a.emp_id=total.emp_id where a.emp_id=total.emp_id and  epf_deductions!=actual order by a.security_id";
			System.out.println("sql in  epf" + sql);
		}
		List<VariationInCal_PaybillResponse> resultList = new ArrayList<>();

		Query jpaQuery = entityManager.createNativeQuery(sql, Tuple.class);

		@SuppressWarnings("unchecked")
		List<Tuple> tupleList = jpaQuery.getResultList();

		List<Map<String, Object>> mapList = commonServiceImpl.getMap(tupleList);

		if (!mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {

				VariationInCal_PaybillResponse variation_paybill = new VariationInCal_PaybillResponse();
				variation_paybill.setEmp_id(map.get("emp_id") + "");
				variation_paybill.setEmp_name(map.get("emp_name") + "");
				variation_paybill.setBasic_pay_earnings(map.get("basic_pay_earnings") + "");
				variation_paybill.setRound(map.get("round") + "");
				variation_paybill.setActual(map.get("actual") + "");
				variation_paybill.setName(map.get("name") + "");
				variation_paybill.setEmp_type(map.get("emp_type") + "");
				variation_paybill.setEpf_deductions(map.get("epf_deductions") + "");
				variation_paybill.setBasic_pay_da(map.get("basic_pay_da") + "");
				variation_paybill.setPayment_type(map.get("payment_type") + "");
				variation_paybill.setConfirm_month(map.get("confirm_month") + "");
				variation_paybill.setYear(map.get("year") + "");
				resultList.add(variation_paybill);

			}
		}

		System.out.println("resultList" + resultList);
		return resultList;
	}

	// DistrictwiseDesignationCount
	@Override
	public List<Map<String, String>> DistrictwiseDesignationCount(String emp_type) {
		List<Map<String, String>> DesignationCount = null;
		if (emp_type.equals("All")) {
			DesignationCount = payrollReports_Repo.DistrictwiseDesignationCountAll();
		} else {
			DesignationCount = payrollReports_Repo.DistrictwiseDesignationCount(emp_type);
		}
		return DesignationCount;
	}

	// PAYBILL REPORT IN GMADM LOGIN
	@Override
	public List<Map<String, String>> PaybillReport(String emp_type) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		List<Map<String, String>> PAYBILL = null;

		if (emp_type.equals("1") || emp_type.equals("2") || emp_type.equals("5")) {
			System.out.println("1,2,3");
			PAYBILL = payrollReports_Repo.Paybill_Report125(security_id, emp_type);

		} else if (emp_type.equals("3")) {
			PAYBILL = payrollReports_Repo.Paybill_Report3(security_id);
		} else if (emp_type.equals("4")) {
			PAYBILL = payrollReports_Repo.Paybill_Report4(security_id);
		}
		return PAYBILL;
	}

	// EMPLOYEE DATA COUNT REPORT
	@Override
	public List<Map<String, Object>> DistrictsPayBill() {
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> Actual = payrollReports_Repo.DistrictsPayBill_Actual();
		List<Map<String, Object>> Entered = payrollReports_Repo.DistrictsPayBill_Entered();

		if (Actual.size() > 0 || Entered.size() > 0) {
			map.put("responseToken", 01);
			map.put("responseMsg", "Data fetched Successfully");

			map.put("Actual", Actual);
			map.put("Entered", Entered);

		} else {
			map.put("responseToken", 02);
			map.put("responseMsg", "Data not fetched");
		}
		respose.add(map);
		return respose;
	}

	public Map<String, Object> UpdateDistrictsPayBillCount(List<PayrollReports_Request> payrollReports_Request) {
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		System.out.println("security_id" + security_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int update_employeedatacount = 0;
		int employeedatacount = 0;

		try {
			for (PayrollReports_Request employeeCount : payrollReports_Request) {
				String code = employeeCount.getCode();
				update_employeedatacount = payrollReports_Repo.Insert_update_employeedatacount(code);
				System.out.println("update_employeedatacount" + update_employeedatacount);

				float reg_apshcl = employeeCount.getReg_apshcl();
				float reg_deputation = employeeCount.getReg_deputation();
				float outsourcing = employeeCount.getOutsourcing();
				float nmr = employeeCount.getNmr();

				System.out.println("Regular: " + reg_apshcl);
				System.out.println("Conveyance: " + reg_deputation);

				employeedatacount = payrollReports_Repo.update_employeedatacount(reg_apshcl, reg_deputation,
						outsourcing, nmr, code);
				System.out.println("employeedatacount" + employeedatacount);
			}

			if (update_employeedatacount != 0 && employeedatacount != 0) {
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Updated Successfully");
			} else {
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Error Updating Data");
			}
		} catch (Exception e) {
			responseMap.put("SCODE", "03");
			responseMap.put("SDESC", "Error: " + e.getMessage());
			e.printStackTrace();
		}

		return responseMap;
	}

}
