package in_Apcfss.Apofms.api.ofmsapi.request;

import java.sql.Timestamp;
import java.util.List;

public class PayrollReports_Request {
	int emptype_id;
	String emp_id;
	String working_status;
	List<List_PayrollReports_Request> employeeStatusList;
	// Emp_Details
	private String security_id;
	private String prefix;
	private String firstname;
	private String lastname;
	private String surname;
	private String emp_type;
	private String sex;
	private int designation;
	private String account_no;
	private boolean isdelete;
	private Timestamp timestamp;
	private String others;
	private String fathername;
	private String bank_name;
	private String branch_code;
	private String confirm_month;
	private String payment_type;
	private String year;
	private String bank_ifsc;
	String district;
	private String ifsc;
//Earning Deatils

	private double basic_pay_earnings;
	private double per_pay_earnings;
	private double spl_pay_earnings;
	private double da_earnings;
	private double hra_earnings;
	private double cca_earnings;
	private double ir_earnings;
	private double gp_earnings;

	private double medical_earnings;
	private double ca_earnings;
	private double spl_all;
	private double misc_h_c;
	private double addl_hra;
	private double sca;

	// Deduction Details

	private double gpfs_deductions;
	private double gpfl_deductions;
	private double gpfsa_deductions;
	private double house_rent_deductions;
	private double gis_deductions;
	private double pt_deductions;
	private double it_deductions;
	private double cca_deductions;
	private double license_deductions;
	private double con_decd_deductions;
	private double lic_deductions;
	private double rcs_cont_deductions;
	private double sal_rec_deductions;
	private double cmrf_deductions;
	private double fcf_deductions;
	private double epf_l_deductions;
	private double vpf_deductions;
	private double apglis_deductions;
	private double apglil_deductions;
	private double epf_deductions;
	private double ppf_deductions;
	private double other_deductions;

	// Loan Details Request

	private double car_i_loan;
	private double car_a_loan;
	private double cyc_i_loan;
	private double cyc_a_loan;
	private double mca_i_loan;
	private double mca_a_loan;
	private double mar_a_loan;
	private double med_a_loan;
	private double hba_loan;
	private double hba1_loan;
	private double comp_loan;
	private double fa_loan;
	private double ea_loan;
	private double cell_loan;
	private double add_hba_loan;
	private double add_hba1_loan;
	private double sal_adv_loan;
	private double sfa_loan;
	private double med_a_i_loan;
	private double hcesa_loan;
	private double hcesa_i_loan;
	private double staff_pl_loan;
	private double court_loan;
	private double vij_bank_loan;
	private double mar_i_loan;
	private double hr_arrear_loan;
	private double hbao_loan;
	private double comp1_loan;

	private double car_i_loanpi;
	private double car_a_loanpi;
	private double cyc_i_loanpi;
	private double cyc_a_loanpi;
	private double mca_i_loanpi;
	private double mca_a_loanpi;
	private double mar_a_loanpi;
	private double med_a_loanpi;
	private double hba_loanpi;
	private double hba1_loanpi;
	private double comp_loanpi;
	private double fa_loanpi;
	private double ea_loanpi;
	private double cell_loanpi;
	private double add_hba_loanpi;
	private double add_hba1_loanpi;
	private double sal_adv_loanpi;
	private double sfa_loanpi;
	private double med_a_i_loanpi;
	private double hcesa_loanpi;
	private double hcesa_i_loanpi;
	private double staff_pl_loanpi;
	private double court_loanpi;
	private double vij_bank_loanpi;
	private double mar_i_loanpi;
	private double hr_arrear_loanpi;
	private double hbao_loanpi;
	private double comp1_loanpi;
	private double car_i_loanti;
	private double car_a_loanti;
	private double cyc_i_loanti;
	private double cyc_a_loanti;
	private double mca_i_loanti;
	private double mca_a_loanti;
	private double mar_a_loanti;
	private double med_a_loanti;
	private double hba_loanti;
	private double hba1_loanti;
	private double comp_loanti;
	private double fa_loanti;
	private double ea_loanti;
	private double cell_loanti;
	private double add_hba_loanti;
	private double add_hba1_loanti;
	private double sal_adv_loanti;
	private double sfa_loanti;
	private double med_a_i_loanti;
	private double hcesa_loanti;
	private double hcesa_i_loanti;
	private double staff_pl_loanti;
	private double court_loanti;
	private double vij_bank_loanti;
	private double mar_i_loanti;
	private double hr_arrear_loanti;
	private double hbao_loanti;
	private double comp1_loanti;

	// OS Earn Ded Details

	private double os_basic_pay_earnings;
	private double os_hra_earnings;
	private double os_medical_earnings;
	private double os_ca_earnings;
	private double os_performance_earnings;
	private double os_ec_epf;
	private double os_ees_epf_deductions;
	private double os_ers_epf_deductions;
	private double os_prof_tax_deductions;
	private String os_work_place;
	private String os_location;

	private double os_other_deductions;

	// Nmr Earn Ded Details
	private double nmr_gross_earnings;
	private double nmr_postalrd_deductions;
	private double nmr_tds_deductions;
	private double nmr_fa_deductions;
	private double nmr_ea_deductions;
	private double nmr_ma_deductions;
	private double nmr_lic_deductions;
	private double nmr_otherliab_deductions;

	// variation
	private String category;
	private String hra_percent;
	private String emp_name;
//EMPLOYEE DATA COUNT REPORT
	private String code;
	private float reg_apshcl;
	private float reg_deputation;
	private float outsourcing;
	private float nmr;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public float getReg_apshcl() {
		return reg_apshcl;
	}

	public void setReg_apshcl(float reg_apshcl) {
		this.reg_apshcl = reg_apshcl;
	}

	public float getReg_deputation() {
		return reg_deputation;
	}

	public void setReg_deputation(float reg_deputation) {
		this.reg_deputation = reg_deputation;
	}

	public float getOutsourcing() {
		return outsourcing;
	}

	public void setOutsourcing(float outsourcing) {
		this.outsourcing = outsourcing;
	}

	public float getNmr() {
		return nmr;
	}

	public void setNmr(float nmr) {
		this.nmr = nmr;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getHra_percent() {
		return hra_percent;
	}

	public void setHra_percent(String hra_percent) {
		this.hra_percent = hra_percent;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getEmptype_id() {
		return emptype_id;
	}

	public void setEmptype_id(int emptype_id) {
		this.emptype_id = emptype_id;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getWorking_status() {
		return working_status;
	}

	public void setWorking_status(String working_status) {
		this.working_status = working_status;
	}

	public String getSecurity_id() {
		return security_id;
	}

	public void setSecurity_id(String security_id) {
		this.security_id = security_id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmp_type() {
		return emp_type;
	}

	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getDesignation() {
		return designation;
	}

	public void setDesignation(int designation) {
		this.designation = designation;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public boolean isIsdelete() {
		return isdelete;
	}

	public void setIsdelete(boolean isdelete) {
		this.isdelete = isdelete;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getFathername() {
		return fathername;
	}

	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}

	public String getConfirm_month() {
		return confirm_month;
	}

	public void setConfirm_month(String confirm_month) {
		this.confirm_month = confirm_month;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBank_ifsc() {
		return bank_ifsc;
	}

	public void setBank_ifsc(String bank_ifsc) {
		this.bank_ifsc = bank_ifsc;
	}

	public double getBasic_pay_earnings() {
		return basic_pay_earnings;
	}

	public void setBasic_pay_earnings(double basic_pay_earnings) {
		this.basic_pay_earnings = basic_pay_earnings;
	}

	public double getPer_pay_earnings() {
		return per_pay_earnings;
	}

	public void setPer_pay_earnings(double per_pay_earnings) {
		this.per_pay_earnings = per_pay_earnings;
	}

	public double getSpl_pay_earnings() {
		return spl_pay_earnings;
	}

	public void setSpl_pay_earnings(double spl_pay_earnings) {
		this.spl_pay_earnings = spl_pay_earnings;
	}

	public double getDa_earnings() {
		return da_earnings;
	}

	public void setDa_earnings(double da_earnings) {
		this.da_earnings = da_earnings;
	}

	public double getHra_earnings() {
		return hra_earnings;
	}

	public void setHra_earnings(double hra_earnings) {
		this.hra_earnings = hra_earnings;
	}

	public double getCca_earnings() {
		return cca_earnings;
	}

	public void setCca_earnings(double cca_earnings) {
		this.cca_earnings = cca_earnings;
	}

	public double getIr_earnings() {
		return ir_earnings;
	}

	public void setIr_earnings(double ir_earnings) {
		this.ir_earnings = ir_earnings;
	}

	public double getGp_earnings() {
		return gp_earnings;
	}

	public void setGp_earnings(double gp_earnings) {
		this.gp_earnings = gp_earnings;
	}

	public double getMedical_earnings() {
		return medical_earnings;
	}

	public void setMedical_earnings(double medical_earnings) {
		this.medical_earnings = medical_earnings;
	}

	public double getCa_earnings() {
		return ca_earnings;
	}

	public void setCa_earnings(double ca_earnings) {
		this.ca_earnings = ca_earnings;
	}

	public double getSpl_all() {
		return spl_all;
	}

	public void setSpl_all(double spl_all) {
		this.spl_all = spl_all;
	}

	public double getMisc_h_c() {
		return misc_h_c;
	}

	public void setMisc_h_c(double misc_h_c) {
		this.misc_h_c = misc_h_c;
	}

	public double getAddl_hra() {
		return addl_hra;
	}

	public void setAddl_hra(double addl_hra) {
		this.addl_hra = addl_hra;
	}

	public double getSca() {
		return sca;
	}

	public void setSca(double sca) {
		this.sca = sca;
	}

	public double getGpfs_deductions() {
		return gpfs_deductions;
	}

	public void setGpfs_deductions(double gpfs_deductions) {
		this.gpfs_deductions = gpfs_deductions;
	}

	public double getGpfl_deductions() {
		return gpfl_deductions;
	}

	public void setGpfl_deductions(double gpfl_deductions) {
		this.gpfl_deductions = gpfl_deductions;
	}

	public double getGpfsa_deductions() {
		return gpfsa_deductions;
	}

	public void setGpfsa_deductions(double gpfsa_deductions) {
		this.gpfsa_deductions = gpfsa_deductions;
	}

	public double getHouse_rent_deductions() {
		return house_rent_deductions;
	}

	public void setHouse_rent_deductions(double house_rent_deductions) {
		this.house_rent_deductions = house_rent_deductions;
	}

	public double getGis_deductions() {
		return gis_deductions;
	}

	public void setGis_deductions(double gis_deductions) {
		this.gis_deductions = gis_deductions;
	}

	
	public double getPt_deductions() {
		return pt_deductions;
	}

	public void setPt_deductions(double pt_deductions) {
		this.pt_deductions = pt_deductions;
	}

	

	public double getIt_deductions() {
		return it_deductions;
	}

	public void setIt_deductions(double it_deductions) {
		this.it_deductions = it_deductions;
	}

	public double getCca_deductions() {
		return cca_deductions;
	}

	public void setCca_deductions(double cca_deductions) {
		this.cca_deductions = cca_deductions;
	}

	public double getLicense_deductions() {
		return license_deductions;
	}

	public void setLicense_deductions(double license_deductions) {
		this.license_deductions = license_deductions;
	}

	public double getCon_decd_deductions() {
		return con_decd_deductions;
	}

	public void setCon_decd_deductions(double con_decd_deductions) {
		this.con_decd_deductions = con_decd_deductions;
	}

	public double getLic_deductions() {
		return lic_deductions;
	}

	public void setLic_deductions(double lic_deductions) {
		this.lic_deductions = lic_deductions;
	}

	public double getRcs_cont_deductions() {
		return rcs_cont_deductions;
	}

	public void setRcs_cont_deductions(double rcs_cont_deductions) {
		this.rcs_cont_deductions = rcs_cont_deductions;
	}

	public double getSal_rec_deductions() {
		return sal_rec_deductions;
	}

	public void setSal_rec_deductions(double sal_rec_deductions) {
		this.sal_rec_deductions = sal_rec_deductions;
	}

	public double getCmrf_deductions() {
		return cmrf_deductions;
	}

	public void setCmrf_deductions(double cmrf_deductions) {
		this.cmrf_deductions = cmrf_deductions;
	}

	public double getFcf_deductions() {
		return fcf_deductions;
	}

	public void setFcf_deductions(double fcf_deductions) {
		this.fcf_deductions = fcf_deductions;
	}

	public double getEpf_l_deductions() {
		return epf_l_deductions;
	}

	public void setEpf_l_deductions(double epf_l_deductions) {
		this.epf_l_deductions = epf_l_deductions;
	}

	public double getVpf_deductions() {
		return vpf_deductions;
	}

	public void setVpf_deductions(double vpf_deductions) {
		this.vpf_deductions = vpf_deductions;
	}

	public double getApglis_deductions() {
		return apglis_deductions;
	}

	public void setApglis_deductions(double apglis_deductions) {
		this.apglis_deductions = apglis_deductions;
	}

	public double getApglil_deductions() {
		return apglil_deductions;
	}

	public void setApglil_deductions(double apglil_deductions) {
		this.apglil_deductions = apglil_deductions;
	}

	public double getEpf_deductions() {
		return epf_deductions;
	}

	public void setEpf_deductions(double epf_deductions) {
		this.epf_deductions = epf_deductions;
	}

	public double getPpf_deductions() {
		return ppf_deductions;
	}

	public void setPpf_deductions(double ppf_deductions) {
		this.ppf_deductions = ppf_deductions;
	}



	public double getOther_deductions() {
		return other_deductions;
	}

	public void setOther_deductions(double other_deductions) {
		this.other_deductions = other_deductions;
	}

	

	public double getCar_i_loan() {
		return car_i_loan;
	}

	public void setCar_i_loan(double car_i_loan) {
		this.car_i_loan = car_i_loan;
	}

	public double getCar_a_loan() {
		return car_a_loan;
	}

	public void setCar_a_loan(double car_a_loan) {
		this.car_a_loan = car_a_loan;
	}

	public double getCyc_i_loan() {
		return cyc_i_loan;
	}

	public void setCyc_i_loan(double cyc_i_loan) {
		this.cyc_i_loan = cyc_i_loan;
	}

	public double getCyc_a_loan() {
		return cyc_a_loan;
	}

	public void setCyc_a_loan(double cyc_a_loan) {
		this.cyc_a_loan = cyc_a_loan;
	}

	public double getMca_i_loan() {
		return mca_i_loan;
	}

	public void setMca_i_loan(double mca_i_loan) {
		this.mca_i_loan = mca_i_loan;
	}

	public double getMca_a_loan() {
		return mca_a_loan;
	}

	public void setMca_a_loan(double mca_a_loan) {
		this.mca_a_loan = mca_a_loan;
	}

	public double getMar_a_loan() {
		return mar_a_loan;
	}

	public void setMar_a_loan(double mar_a_loan) {
		this.mar_a_loan = mar_a_loan;
	}

	public double getMed_a_loan() {
		return med_a_loan;
	}

	public void setMed_a_loan(double med_a_loan) {
		this.med_a_loan = med_a_loan;
	}

	public double getHba_loan() {
		return hba_loan;
	}

	public void setHba_loan(double hba_loan) {
		this.hba_loan = hba_loan;
	}

	public double getHba1_loan() {
		return hba1_loan;
	}

	public void setHba1_loan(double hba1_loan) {
		this.hba1_loan = hba1_loan;
	}

	public double getComp_loan() {
		return comp_loan;
	}

	public void setComp_loan(double comp_loan) {
		this.comp_loan = comp_loan;
	}

	public double getFa_loan() {
		return fa_loan;
	}

	public void setFa_loan(double fa_loan) {
		this.fa_loan = fa_loan;
	}

	public double getEa_loan() {
		return ea_loan;
	}

	public void setEa_loan(double ea_loan) {
		this.ea_loan = ea_loan;
	}

	public double getCell_loan() {
		return cell_loan;
	}

	public void setCell_loan(double cell_loan) {
		this.cell_loan = cell_loan;
	}

	public double getAdd_hba_loan() {
		return add_hba_loan;
	}

	public void setAdd_hba_loan(double add_hba_loan) {
		this.add_hba_loan = add_hba_loan;
	}

	public double getAdd_hba1_loan() {
		return add_hba1_loan;
	}

	public void setAdd_hba1_loan(double add_hba1_loan) {
		this.add_hba1_loan = add_hba1_loan;
	}

	public double getSal_adv_loan() {
		return sal_adv_loan;
	}

	public void setSal_adv_loan(double sal_adv_loan) {
		this.sal_adv_loan = sal_adv_loan;
	}

	public double getSfa_loan() {
		return sfa_loan;
	}

	public void setSfa_loan(double sfa_loan) {
		this.sfa_loan = sfa_loan;
	}

	public double getMed_a_i_loan() {
		return med_a_i_loan;
	}

	public void setMed_a_i_loan(double med_a_i_loan) {
		this.med_a_i_loan = med_a_i_loan;
	}

	public double getHcesa_loan() {
		return hcesa_loan;
	}

	public void setHcesa_loan(double hcesa_loan) {
		this.hcesa_loan = hcesa_loan;
	}

	public double getHcesa_i_loan() {
		return hcesa_i_loan;
	}

	public void setHcesa_i_loan(double hcesa_i_loan) {
		this.hcesa_i_loan = hcesa_i_loan;
	}

	public double getStaff_pl_loan() {
		return staff_pl_loan;
	}

	public void setStaff_pl_loan(double staff_pl_loan) {
		this.staff_pl_loan = staff_pl_loan;
	}

	public double getCourt_loan() {
		return court_loan;
	}

	public void setCourt_loan(double court_loan) {
		this.court_loan = court_loan;
	}

	public double getVij_bank_loan() {
		return vij_bank_loan;
	}

	public void setVij_bank_loan(double vij_bank_loan) {
		this.vij_bank_loan = vij_bank_loan;
	}

	public double getMar_i_loan() {
		return mar_i_loan;
	}

	public void setMar_i_loan(double mar_i_loan) {
		this.mar_i_loan = mar_i_loan;
	}

	public double getHr_arrear_loan() {
		return hr_arrear_loan;
	}

	public void setHr_arrear_loan(double hr_arrear_loan) {
		this.hr_arrear_loan = hr_arrear_loan;
	}

	public double getHbao_loan() {
		return hbao_loan;
	}

	public void setHbao_loan(double hbao_loan) {
		this.hbao_loan = hbao_loan;
	}

	public double getComp1_loan() {
		return comp1_loan;
	}

	public void setComp1_loan(double comp1_loan) {
		this.comp1_loan = comp1_loan;
	}

	public double getCar_i_loanpi() {
		return car_i_loanpi;
	}

	public void setCar_i_loanpi(double car_i_loanpi) {
		this.car_i_loanpi = car_i_loanpi;
	}

	public double getCar_a_loanpi() {
		return car_a_loanpi;
	}

	public void setCar_a_loanpi(double car_a_loanpi) {
		this.car_a_loanpi = car_a_loanpi;
	}

	public double getCyc_i_loanpi() {
		return cyc_i_loanpi;
	}

	public void setCyc_i_loanpi(double cyc_i_loanpi) {
		this.cyc_i_loanpi = cyc_i_loanpi;
	}

	public double getCyc_a_loanpi() {
		return cyc_a_loanpi;
	}

	public void setCyc_a_loanpi(double cyc_a_loanpi) {
		this.cyc_a_loanpi = cyc_a_loanpi;
	}

	public double getMca_i_loanpi() {
		return mca_i_loanpi;
	}

	public void setMca_i_loanpi(double mca_i_loanpi) {
		this.mca_i_loanpi = mca_i_loanpi;
	}

	public double getMca_a_loanpi() {
		return mca_a_loanpi;
	}

	public void setMca_a_loanpi(double mca_a_loanpi) {
		this.mca_a_loanpi = mca_a_loanpi;
	}

	public double getMar_a_loanpi() {
		return mar_a_loanpi;
	}

	public void setMar_a_loanpi(double mar_a_loanpi) {
		this.mar_a_loanpi = mar_a_loanpi;
	}

	public double getMed_a_loanpi() {
		return med_a_loanpi;
	}

	public void setMed_a_loanpi(double med_a_loanpi) {
		this.med_a_loanpi = med_a_loanpi;
	}

	public double getHba_loanpi() {
		return hba_loanpi;
	}

	public void setHba_loanpi(double hba_loanpi) {
		this.hba_loanpi = hba_loanpi;
	}

	public double getHba1_loanpi() {
		return hba1_loanpi;
	}

	public void setHba1_loanpi(double hba1_loanpi) {
		this.hba1_loanpi = hba1_loanpi;
	}

	public double getComp_loanpi() {
		return comp_loanpi;
	}

	public void setComp_loanpi(double comp_loanpi) {
		this.comp_loanpi = comp_loanpi;
	}

	public double getFa_loanpi() {
		return fa_loanpi;
	}

	public void setFa_loanpi(double fa_loanpi) {
		this.fa_loanpi = fa_loanpi;
	}

	public double getEa_loanpi() {
		return ea_loanpi;
	}

	public void setEa_loanpi(double ea_loanpi) {
		this.ea_loanpi = ea_loanpi;
	}

	public double getCell_loanpi() {
		return cell_loanpi;
	}

	public void setCell_loanpi(double cell_loanpi) {
		this.cell_loanpi = cell_loanpi;
	}

	public double getAdd_hba_loanpi() {
		return add_hba_loanpi;
	}

	public void setAdd_hba_loanpi(double add_hba_loanpi) {
		this.add_hba_loanpi = add_hba_loanpi;
	}

	public double getAdd_hba1_loanpi() {
		return add_hba1_loanpi;
	}

	public void setAdd_hba1_loanpi(double add_hba1_loanpi) {
		this.add_hba1_loanpi = add_hba1_loanpi;
	}

	public double getSal_adv_loanpi() {
		return sal_adv_loanpi;
	}

	public void setSal_adv_loanpi(double sal_adv_loanpi) {
		this.sal_adv_loanpi = sal_adv_loanpi;
	}

	public double getSfa_loanpi() {
		return sfa_loanpi;
	}

	public void setSfa_loanpi(double sfa_loanpi) {
		this.sfa_loanpi = sfa_loanpi;
	}

	public double getMed_a_i_loanpi() {
		return med_a_i_loanpi;
	}

	public void setMed_a_i_loanpi(double med_a_i_loanpi) {
		this.med_a_i_loanpi = med_a_i_loanpi;
	}

	public double getHcesa_loanpi() {
		return hcesa_loanpi;
	}

	public void setHcesa_loanpi(double hcesa_loanpi) {
		this.hcesa_loanpi = hcesa_loanpi;
	}

	public double getHcesa_i_loanpi() {
		return hcesa_i_loanpi;
	}

	public void setHcesa_i_loanpi(double hcesa_i_loanpi) {
		this.hcesa_i_loanpi = hcesa_i_loanpi;
	}

	public double getStaff_pl_loanpi() {
		return staff_pl_loanpi;
	}

	public void setStaff_pl_loanpi(double staff_pl_loanpi) {
		this.staff_pl_loanpi = staff_pl_loanpi;
	}

	public double getCourt_loanpi() {
		return court_loanpi;
	}

	public void setCourt_loanpi(double court_loanpi) {
		this.court_loanpi = court_loanpi;
	}

	public double getVij_bank_loanpi() {
		return vij_bank_loanpi;
	}

	public void setVij_bank_loanpi(double vij_bank_loanpi) {
		this.vij_bank_loanpi = vij_bank_loanpi;
	}

	public double getMar_i_loanpi() {
		return mar_i_loanpi;
	}

	public void setMar_i_loanpi(double mar_i_loanpi) {
		this.mar_i_loanpi = mar_i_loanpi;
	}

	public double getHr_arrear_loanpi() {
		return hr_arrear_loanpi;
	}

	public void setHr_arrear_loanpi(double hr_arrear_loanpi) {
		this.hr_arrear_loanpi = hr_arrear_loanpi;
	}

	public double getHbao_loanpi() {
		return hbao_loanpi;
	}

	public void setHbao_loanpi(double hbao_loanpi) {
		this.hbao_loanpi = hbao_loanpi;
	}

	public double getComp1_loanpi() {
		return comp1_loanpi;
	}

	public void setComp1_loanpi(double comp1_loanpi) {
		this.comp1_loanpi = comp1_loanpi;
	}

	public double getCar_i_loanti() {
		return car_i_loanti;
	}

	public void setCar_i_loanti(double car_i_loanti) {
		this.car_i_loanti = car_i_loanti;
	}

	public double getCar_a_loanti() {
		return car_a_loanti;
	}

	public void setCar_a_loanti(double car_a_loanti) {
		this.car_a_loanti = car_a_loanti;
	}

	public double getCyc_i_loanti() {
		return cyc_i_loanti;
	}

	public void setCyc_i_loanti(double cyc_i_loanti) {
		this.cyc_i_loanti = cyc_i_loanti;
	}

	public double getCyc_a_loanti() {
		return cyc_a_loanti;
	}

	public void setCyc_a_loanti(double cyc_a_loanti) {
		this.cyc_a_loanti = cyc_a_loanti;
	}

	public double getMca_i_loanti() {
		return mca_i_loanti;
	}

	public void setMca_i_loanti(double mca_i_loanti) {
		this.mca_i_loanti = mca_i_loanti;
	}

	public double getMca_a_loanti() {
		return mca_a_loanti;
	}

	public void setMca_a_loanti(double mca_a_loanti) {
		this.mca_a_loanti = mca_a_loanti;
	}

	public double getMar_a_loanti() {
		return mar_a_loanti;
	}

	public void setMar_a_loanti(double mar_a_loanti) {
		this.mar_a_loanti = mar_a_loanti;
	}

	public double getMed_a_loanti() {
		return med_a_loanti;
	}

	public void setMed_a_loanti(double med_a_loanti) {
		this.med_a_loanti = med_a_loanti;
	}

	public double getHba_loanti() {
		return hba_loanti;
	}

	public void setHba_loanti(double hba_loanti) {
		this.hba_loanti = hba_loanti;
	}

	public double getHba1_loanti() {
		return hba1_loanti;
	}

	public void setHba1_loanti(double hba1_loanti) {
		this.hba1_loanti = hba1_loanti;
	}

	public double getComp_loanti() {
		return comp_loanti;
	}

	public void setComp_loanti(double comp_loanti) {
		this.comp_loanti = comp_loanti;
	}

	public double getFa_loanti() {
		return fa_loanti;
	}

	public void setFa_loanti(double fa_loanti) {
		this.fa_loanti = fa_loanti;
	}

	public double getEa_loanti() {
		return ea_loanti;
	}

	public void setEa_loanti(double ea_loanti) {
		this.ea_loanti = ea_loanti;
	}

	public double getCell_loanti() {
		return cell_loanti;
	}

	public void setCell_loanti(double cell_loanti) {
		this.cell_loanti = cell_loanti;
	}

	public double getAdd_hba_loanti() {
		return add_hba_loanti;
	}

	public void setAdd_hba_loanti(double add_hba_loanti) {
		this.add_hba_loanti = add_hba_loanti;
	}

	public double getAdd_hba1_loanti() {
		return add_hba1_loanti;
	}

	public void setAdd_hba1_loanti(double add_hba1_loanti) {
		this.add_hba1_loanti = add_hba1_loanti;
	}

	public double getSal_adv_loanti() {
		return sal_adv_loanti;
	}

	public void setSal_adv_loanti(double sal_adv_loanti) {
		this.sal_adv_loanti = sal_adv_loanti;
	}

	public double getSfa_loanti() {
		return sfa_loanti;
	}

	public void setSfa_loanti(double sfa_loanti) {
		this.sfa_loanti = sfa_loanti;
	}

	public double getMed_a_i_loanti() {
		return med_a_i_loanti;
	}

	public void setMed_a_i_loanti(double med_a_i_loanti) {
		this.med_a_i_loanti = med_a_i_loanti;
	}

	public double getHcesa_loanti() {
		return hcesa_loanti;
	}

	public void setHcesa_loanti(double hcesa_loanti) {
		this.hcesa_loanti = hcesa_loanti;
	}

	public double getHcesa_i_loanti() {
		return hcesa_i_loanti;
	}

	public void setHcesa_i_loanti(double hcesa_i_loanti) {
		this.hcesa_i_loanti = hcesa_i_loanti;
	}

	public double getStaff_pl_loanti() {
		return staff_pl_loanti;
	}

	public void setStaff_pl_loanti(double staff_pl_loanti) {
		this.staff_pl_loanti = staff_pl_loanti;
	}

	public double getCourt_loanti() {
		return court_loanti;
	}

	public void setCourt_loanti(double court_loanti) {
		this.court_loanti = court_loanti;
	}

	public double getVij_bank_loanti() {
		return vij_bank_loanti;
	}

	public void setVij_bank_loanti(double vij_bank_loanti) {
		this.vij_bank_loanti = vij_bank_loanti;
	}

	public double getMar_i_loanti() {
		return mar_i_loanti;
	}

	public void setMar_i_loanti(double mar_i_loanti) {
		this.mar_i_loanti = mar_i_loanti;
	}

	public double getHr_arrear_loanti() {
		return hr_arrear_loanti;
	}

	public void setHr_arrear_loanti(double hr_arrear_loanti) {
		this.hr_arrear_loanti = hr_arrear_loanti;
	}

	public double getHbao_loanti() {
		return hbao_loanti;
	}

	public void setHbao_loanti(double hbao_loanti) {
		this.hbao_loanti = hbao_loanti;
	}

	public double getComp1_loanti() {
		return comp1_loanti;
	}

	public void setComp1_loanti(double comp1_loanti) {
		this.comp1_loanti = comp1_loanti;
	}

	
	public double getOs_basic_pay_earnings() {
		return os_basic_pay_earnings;
	}

	public void setOs_basic_pay_earnings(double os_basic_pay_earnings) {
		this.os_basic_pay_earnings = os_basic_pay_earnings;
	}

	public double getOs_hra_earnings() {
		return os_hra_earnings;
	}

	public void setOs_hra_earnings(double os_hra_earnings) {
		this.os_hra_earnings = os_hra_earnings;
	}

	public double getOs_medical_earnings() {
		return os_medical_earnings;
	}

	public void setOs_medical_earnings(double os_medical_earnings) {
		this.os_medical_earnings = os_medical_earnings;
	}

	public double getOs_ca_earnings() {
		return os_ca_earnings;
	}

	public void setOs_ca_earnings(double os_ca_earnings) {
		this.os_ca_earnings = os_ca_earnings;
	}

	public double getOs_performance_earnings() {
		return os_performance_earnings;
	}

	public void setOs_performance_earnings(double os_performance_earnings) {
		this.os_performance_earnings = os_performance_earnings;
	}

	public double getOs_ec_epf() {
		return os_ec_epf;
	}

	public void setOs_ec_epf(double os_ec_epf) {
		this.os_ec_epf = os_ec_epf;
	}

	public double getOs_ees_epf_deductions() {
		return os_ees_epf_deductions;
	}

	public void setOs_ees_epf_deductions(double os_ees_epf_deductions) {
		this.os_ees_epf_deductions = os_ees_epf_deductions;
	}

	public double getOs_ers_epf_deductions() {
		return os_ers_epf_deductions;
	}

	public void setOs_ers_epf_deductions(double os_ers_epf_deductions) {
		this.os_ers_epf_deductions = os_ers_epf_deductions;
	}

	public double getOs_prof_tax_deductions() {
		return os_prof_tax_deductions;
	}

	public void setOs_prof_tax_deductions(double os_prof_tax_deductions) {
		this.os_prof_tax_deductions = os_prof_tax_deductions;
	}

	public String getOs_work_place() {
		return os_work_place;
	}

	public void setOs_work_place(String os_work_place) {
		this.os_work_place = os_work_place;
	}

	public String getOs_location() {
		return os_location;
	}

	public void setOs_location(String os_location) {
		this.os_location = os_location;
	}

	public double getNmr_gross_earnings() {
		return nmr_gross_earnings;
	}

	public void setNmr_gross_earnings(double nmr_gross_earnings) {
		this.nmr_gross_earnings = nmr_gross_earnings;
	}

	public double getNmr_postalrd_deductions() {
		return nmr_postalrd_deductions;
	}

	public void setNmr_postalrd_deductions(double nmr_postalrd_deductions) {
		this.nmr_postalrd_deductions = nmr_postalrd_deductions;
	}

	public double getNmr_tds_deductions() {
		return nmr_tds_deductions;
	}

	public void setNmr_tds_deductions(double nmr_tds_deductions) {
		this.nmr_tds_deductions = nmr_tds_deductions;
	}

	public double getNmr_fa_deductions() {
		return nmr_fa_deductions;
	}

	public void setNmr_fa_deductions(double nmr_fa_deductions) {
		this.nmr_fa_deductions = nmr_fa_deductions;
	}

	public double getNmr_ea_deductions() {
		return nmr_ea_deductions;
	}

	public void setNmr_ea_deductions(double nmr_ea_deductions) {
		this.nmr_ea_deductions = nmr_ea_deductions;
	}

	public double getNmr_ma_deductions() {
		return nmr_ma_deductions;
	}

	public void setNmr_ma_deductions(double nmr_ma_deductions) {
		this.nmr_ma_deductions = nmr_ma_deductions;
	}

	public double getNmr_lic_deductions() {
		return nmr_lic_deductions;
	}

	public void setNmr_lic_deductions(double nmr_lic_deductions) {
		this.nmr_lic_deductions = nmr_lic_deductions;
	}

	public double getNmr_otherliab_deductions() {
		return nmr_otherliab_deductions;
	}

	public void setNmr_otherliab_deductions(double nmr_otherliab_deductions) {
		this.nmr_otherliab_deductions = nmr_otherliab_deductions;
	}

	

	public List<List_PayrollReports_Request> getEmployeeStatusList() {
		return employeeStatusList;
	}

	public void setEmployeeStatusList(List<List_PayrollReports_Request> employeeStatusList) {
		this.employeeStatusList = employeeStatusList;
	}

	public double getOs_other_deductions() {
		return os_other_deductions;
	}

	public void setOs_other_deductions(double os_other_deductions) {
		this.os_other_deductions = os_other_deductions;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	
	
}
