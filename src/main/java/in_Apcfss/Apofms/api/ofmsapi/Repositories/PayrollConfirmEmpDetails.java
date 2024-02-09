package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.UpdateEmpDetails;

@Repository
public interface PayrollConfirmEmpDetails extends JpaRepository<UpdateEmpDetails, String> {

	@Query(value = "select emp_id,prefix||''||firstname||' '||lastname||' '||surname as emp_name,prefix||''||firstname||' '||lastname||' '||surname||','||(select designation_name from \r\n"
			+ "designation where designation_id=designation) as emp_name_and_deg,(select designation_name from designation where designation_id=designation)as deg_name,et.emptype_name,mm.month_name,year from emp_details_confirm ed\r\n"
			+ "left join  employee_type et on cast(et.emptype_id as varchar)=ed.emp_type\r\n"
			+ "left join mstmonth mm on mm.month_id=ed.confirm_month where "
			+ "emp_id=:emp_id and payment_type=:payment_type and confirm_month=:confirm_month  and year=:year", nativeQuery = true)
	Map<String, String> FormEmpnameByName(String emp_id, String payment_type, String confirm_month, String year);

//case when basic_pay_earnings!=0 then 'Basic Pay'||' '||basic_pay_earnings else 'No' end as
	@Query(value = "select  case when basic_pay_earnings!=0 then ''||basic_pay_earnings else 'No' end as basic_pay,case when per_pay_earnings!=0 then ''||per_pay_earnings else 'No' end as per_pay,"
			+ "case when spl_pay_earnings!=0 then ''||spl_pay_earnings else 'No' end as spl_pay,case when da_earnings!=0 then ''||da_earnings else 'No' end as da,case when hra_earnings!=0 then ''||hra_earnings else 'No' end as hra,"
			+ "case when cca_earnings!=0 then ''||cca_earnings else 'No' end as cca,case when gp_earnings!=0 then ''||gp_earnings else 'No' end as gp,case when ir_earnings!=0 then ''||ir_earnings else 'No' end as ir,"
			+ "case when medical_earnings!=0 then ''||medical_earnings else 'No' end as medical,case when ca_earnings!=0 then ''||ca_earnings else 'No' end as ca,case when spl_all!=0 then ''||spl_all else 'No' end as spl_all,"
			+ "case when misc_h_c!=0 then ''||misc_h_c else 'No' end as misc_h_c,case when spl_all!=0 then ''||addl_hra else 'No' end as addl_hra,case when sca!=0 then ''||sca else 'No' end as sca from earnings_details_confirm \r\n"
			+ "where emp_id=:emp_id and payment_type=:payment_type and confirm_month=:confirm_month  and year=:year", nativeQuery = true)
	List<Map<String, Object>> FormEarningDetailsByName(String emp_id, String payment_type, String confirm_month,
			String year);

	@Query(value = "select case when gpfs_deductions!=0 then ''||gpfs_deductions else 'No' end as GPFS,case when gpfsa_deductions!=0 then ''||gpfsa_deductions else 'No' end as GPFSA,"
			+ "case when gpfl_deductions!=0 then ''||gpfl_deductions else 'No' end as gpfl,case when apglis_deductions!=0 then ''||apglis_deductions else 'No' end as apglis,"
			+ "case when apglil_deductions!=0 then ''||apglil_deductions else 'No' end as apglil,case when gis_deductions!=0 then ''||gis_deductions else 'No' end as GIS,"
			+ "case when lic_deductions!=0 then ''||lic_deductions else 'No' end as lic,case when license_deductions!=0 then ''||license_deductions else 'No' end as license_ded,"
			+ "case when con_decd_deductions!=0 then ''||con_decd_deductions else 'No' end as con_decd,case when epf_deductions!=0 then ''||epf_deductions else 'No' end as epf,"
			+ "case when epf_l_deductions!=0 then ''||epf_l_deductions else 'No' end as epf_l,case when vpf_deductions!=0 then ''||vpf_deductions else 'No' end as vpf,"
			+ "case when ppf_deductions!=0 then ''||ppf_deductions else 'No' end as ppf,case when rcs_cont_deductions!=0 then ''||rcs_cont_deductions else 'No' end as rcs_cont,"
			+ "case when cmrf_deductions!=0 then ''||cmrf_deductions else 'No' end as cmrf,case when fcf_deductions!=0 then ''||fcf_deductions else 'No' end as fcf,"
			+ "case when house_rent_deductions!=0 then 'House Rent'||'   '||house_rent_deductions else 'No' end as house_rent,case when sal_rec_deductions!=0 then ''||sal_rec_deductions else 'No' end as sal_rec,"
			+ "case when pt_deductions!=0 then ''||pt_deductions else 'No' end as pt,case when it_deductions!=0 then ''||it_deductions else 'No' end as it,"
			+ "case when cca_deductions!=0 then ''||cca_deductions else 'No' end as postal_rd,case when other_deductions!=0 then ''||other_deductions else 'No' end as other_deductions "
			+ "from deductions_details_confirm \r\n"
			+ "where emp_id=:emp_id and payment_type=:payment_type and confirm_month=:confirm_month  and year=:year", nativeQuery = true)
	List<Map<String, Object>> FormDeductionDetailsByName(String emp_id, String payment_type, String confirm_month,
			String year);

	@Query(value = " select  case when car_a_loan!=0 then ''||car_a_loan else 'No' end as car_a,case when car_i_loan!=0 then ''||car_i_loan else 'No' end as car_i,"
			+ "case when cyc_a_loan!=0 then ''||cyc_a_loan else 'No' end as cyc_a,case when cyc_i_loan!=0 then ''||cyc_i_loan else 'No' end as cyc_i,"
			+ "case when mca_a_loan!=0 then ''||mca_a_loan else 'No' end as mca_a,case when mca_i_loan!=0 then ''||mca_i_loan else 'No' end as mca_i,"
			+ "case when mar_a_loan!=0 then ''||mar_a_loan else 'No' end as mar_a,case when med_a_loan!=0 then ''||med_a_loan else 'No' end as med_a,"
			+ "case when hba_loan!=0 then ''||hba_loan else 'No' end as hba,case when hba1_loan!=0 then ''||hba1_loan else 'No' end as hbal,"
			+ "case when comp_loan!=0 then ''||comp_loan else 'No' end as comp_a,case when fa_loan!=0 then ''||fa_loan else 'No' end as fa,"
			+ "case when ea_loan!=0 then ''||ea_loan else 'No' end as ea,case when cell_loan!=0 then ''||cell_loan else 'No' end as cell,"
			+ "case when add_hba_loan!=0 then ''||add_hba_loan else 'No' end as add_hba,case when add_hba1_loan!=0 then ''||add_hba1_loan else 'No' end as add_hba1,"
			+ "case when sal_adv_loan!=0 then ''||sal_adv_loan else 'No' end as sal_adv,case when sfa_loan!=0 then ''||sfa_loan else 'No' end as sfa,"
			+ "case when med_a_i_loan!=0 then ''||med_a_i_loan else 'No' end as med_a_i,case when hcesa_loan!=0 then ''||hcesa_loan else 'No' end as hcesa,"
			+ "case when hcesa_i_loan!=0 then ''||hcesa_i_loan else 'No' end as hcesa_i,case when staff_pl_loan!=0 then ''||staff_pl_loan else 'No' end as staff_pl,"
			+ "case when court_loan!=0 then ''||court_loan else 'No' end as court,case when vij_bank_loan!=0 then ''||vij_bank_loan else 'No' end as personal_bank,"
			+ "case when mar_i_loan!=0 then ''||mar_i_loan else 'No' end as mar_i,case when hr_arrear_loan!=0 then ''||hr_arrear_loan else 'No' end as hr_arrear,"
			+ "case when hbao_loan!=0 then ''||hbao_loan else 'No' end as hbao,case when comp1_loan!=0 then ''||comp1_loan else 'No' end as comp1,"
			+ "case when car_i_loanpi!=0 then ''||car_i_loanpi else 'No' end as car_ipi,case when car_a_loanpi!=0 then ''||car_a_loanpi else 'No' end as car_api,\r\n"
			+ "case when cyc_i_loanpi!=0 then ''||cyc_i_loanpi else 'No' end as cyc_ipi,case when cyc_a_loanpi!=0 then ''||cyc_a_loanpi else 'No' end as cyc_api,\r\n"
			+ "case when mca_i_loanpi!=0 then ''||mca_i_loanpi else 'No' end as mca_ipi,case when mca_a_loanpi!=0 then ''||mca_a_loanpi else 'No' end as mca_api,\r\n"
			+ "case when mar_a_loanpi!=0 then ''||mar_a_loanpi else 'No' end as mar_api,case when med_a_loanpi!=0 then ''||med_a_loanpi else 'No' end as med_api,\r\n"
			+ "case when hba_loanpi!=0 then ''||hba_loanpi else 'No' end as hbapi,case when hba1_loanpi!=0 then ''||hba1_loanpi else 'No' end as hba1pi, "
			+ "case when comp_loanpi!=0 then ''||comp_loanpi else 'No' end as comppi,case when fa_loanpi!=0 then ''||fa_loanpi else 'No' end as fapi,\r\n"
			+ "case when ea_loanpi!=0 then ''||ea_loanpi else 'No' end as eapi,case when cell_loanpi!=0 then ''||cell_loanpi else 'No' end as cellpi,case when add_hba_loanpi!=0 then ''||add_hba_loanpi else 'No' end as add_hbapi,\r\n"
			+ "case when add_hba1_loanpi!=0 then ''||add_hba1_loanpi else 'No' end as add_hba1pi,case when sal_adv_loanpi!=0 then ''||sal_adv_loanpi else 'No' end as sal_adv_pi,\r\n"
			+ "case when sfa_loanpi!=0 then ''||sfa_loanpi else 'No' end as sfapi,case when med_a_i_loanpi!=0 then ''||med_a_i_loanpi else 'No' end as med_a_ipi,\r\n"
			+ "case when hcesa_loanpi!=0 then ''||hcesa_loanpi else 'No' end as hcesapi,case when hcesa_i_loanpi!=0 then ''||hcesa_i_loanpi else 'No' end as hcesa_ipi,\r\n"
			+ "case when staff_pl_loanpi!=0 then ''||staff_pl_loanpi else 'No' end as staff_plpi,case when court_loanpi!=0 then ''||court_loanpi else 'No' end as courtpi, "
			+ "case when vij_bank_loanpi!=0 then ''||vij_bank_loanpi else 'No' end as vij_bankpi,\r\n"
			+ "case when mar_i_loanpi!=0 then ''||mar_i_loanpi else 'No' end as mar_ipi,case when hr_arrear_loanpi!=0 then ''||hr_arrear_loanpi else 'No' end as hr_arrearpi,\r\n"
			+ "case when hbao_loanpi!=0 then ''||hbao_loanpi else 'No' end as hbaopi,case when comp1_loanpi!=0 then ''||comp1_loanpi else 'No' end as comp1pi,\r\n"
			+ "case when car_i_loanti!=0 then ''||car_i_loanti else 'No' end as car_iti,case when car_a_loanti!=0 then ''||car_a_loanti else 'No' end as car_ati,\r\n"
			+ "case when cyc_i_loanti!=0 then ''||cyc_i_loanti else 'No' end as cyc_iti,case when cyc_a_loanti!=0 then ''||cyc_a_loanti else 'No' end as cyc_ati,\r\n"
			+ "case when mca_i_loanti!=0 then ''||mca_i_loanti else 'No' end as mca_iti,case when mca_a_loanti!=0 then ''||mca_a_loanti else 'No' end as mca_ati,\r\n"
			+ "case when mar_a_loanti!=0 then ''||mar_a_loanti else 'No' end as mar_ati,case when med_a_loanti!=0 then ''||med_a_loanti else 'No' end as med_ati,\r\n"
			+ "case when hba_loanti!=0 then ''||hba_loanti else 'No' end as hbati,case when hba1_loanti!=0 then ''||hba1_loanti else 'No' end as hba1ti,\r\n"
			+ "case when comp_loanti!=0 then ''||comp_loanti else 'No' end as compti,case when fa_loanti!=0 then ''||fa_loanti else 'No' end as fati,\r\n"
			+ "case when ea_loanti!=0 then ''||ea_loanti else 'No' end as eati,case when cell_loanti!=0 then ''||cell_loanti else 'No' end as cellti,\r\n"
			+ "case when add_hba_loanti!=0 then ''||add_hba_loanti else 'No' end as add_hbati,case when add_hba1_loanti!=0 then ''||add_hba1_loanti else 'No' end as add_hba1ti,\r\n"
			+ "case when sal_adv_loanti!=0 then ''||sal_adv_loanti else 'No' end as sal_advti,case when sfa_loanti!=0 then ''||sfa_loanti else 'No' end as sfati,"
			+ "case when med_a_i_loanti!=0 then ''||med_a_i_loanti else 'No' end as med_a_iti,case when hcesa_loanti!=0 then ''||hcesa_loanti else 'No' end as hcesati,case when hcesa_i_loanti!=0 then ''||hcesa_i_loanti else 'No' end as hcesaiti,\r\n"
			+ "case when staff_pl_loanti!=0 then ''||staff_pl_loanti else 'No' end as staff_plti,case when court_loanti!=0 then ''||court_loanti else 'No' end as courtti,\r\n"
			+ "case when vij_bank_loanti!=0 then ''||vij_bank_loanti else 'No' end as vij_bankti,case when mar_i_loanti!=0 then ''||mar_i_loanti else 'No' end as mar_iti,\r\n"
			+ "case when hr_arrear_loanti!=0 then ''||hr_arrear_loanti else 'No' end as hr_arrearti,case when hbao_loanti!=0 then ''||hbao_loanti else 'No' end as hbaoti,\r\n"
			+ "case when comp1_loanti!=0 then ''||comp1_loanti else 'No' end as comp1ti "
			+ "from loan_details_confirm\r\n"
			+ "where emp_id=:emp_id and payment_type=:payment_type and confirm_month=:confirm_month  and year=:year", nativeQuery = true)
	List<Map<String, Object>> FormLoanDetailsByName(String emp_id, String payment_type, String confirm_month,
			String year);

	@Query(value = " SELECT coalesce(cast(SUM(CASE WHEN basic_pay_earnings != 0 THEN basic_pay_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN per_pay_earnings != 0 THEN per_pay_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN spl_pay_earnings != 0 THEN spl_pay_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN da_earnings != 0 THEN da_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN hra_earnings != 0 THEN hra_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN cca_earnings != 0 THEN cca_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN gp_earnings != 0 THEN gp_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN ir_earnings != 0 THEN ir_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN medical_earnings != 0 THEN medical_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN ca_earnings != 0 THEN ca_earnings ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN spl_all != 0 THEN spl_all ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN misc_h_c != 0 THEN misc_h_c ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN addl_hra != 0 THEN addl_hra ELSE 0 END) +\r\n"
			+ "SUM(CASE WHEN sca != 0 THEN sca ELSE 0 END) as varchar),'0')as basic_earnings_sum\r\n"
			+ "FROM earnings_details_confirm\r\n"
			+ "where emp_id=:emp_id and payment_type=:payment_type and confirm_month=:confirm_month  and year=:year", nativeQuery = true)
	String basic_earnings_sum(String emp_id, String payment_type, String confirm_month, String year);

	@Query(value = "SELECT coalesce(cast(sum(CASE WHEN gpfs_deductions != 0 THEN  gpfs_deductions ELSE 0 END )+\r\n"
			+ "sum(CASE WHEN gpfsa_deductions != 0 THEN gpfsa_deductions ELSE 0 END)+ \r\n"
			+ "sum(CASE WHEN gpfl_deductions != 0 THEN  gpfl_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN apglis_deductions != 0 THEN  apglis_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN apglil_deductions != 0 THEN  apglil_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN gis_deductions != 0 THEN  gis_deductions  ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN lic_deductions != 0 THEN  lic_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN license_deductions != 0 THEN  license_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN con_decd_deductions != 0 THEN  con_decd_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN epf_deductions != 0 THEN epf_deductions ELSE 0 END)+\r\n"
			+ "sum( CASE WHEN epf_l_deductions != 0 THEN epf_l_deductions ELSE 0 END)+\r\n"
			+ "sum( CASE WHEN vpf_deductions != 0 THEN  vpf_deductions ELSE 0 END)+\r\n"
			+ "sum( CASE WHEN ppf_deductions != 0 THEN  ppf_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN rcs_cont_deductions != 0 THEN  rcs_cont_deductions ELSE 0 END)+\r\n"
			+ "sum( CASE WHEN cmrf_deductions != 0 THEN  cmrf_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN fcf_deductions != 0 THEN fcf_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN house_rent_deductions != 0 THEN house_rent_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN sal_rec_deductions != 0 THEN  sal_rec_deductions ELSE 0 END)+\r\n"
			+ "sum(CASE WHEN pt_deductions != 0 THEN  pt_deductions ELSE 0 END)+\r\n"
			+ "sum( CASE WHEN it_deductions != 0 THEN  it_deductions ELSE 0 END)+\r\n"
			+ "sum( CASE WHEN cca_deductions != 0 THEN  cca_deductions ELSE 0 END)+\r\n"
			+ "sum( CASE WHEN other_deductions != 0 THEN  other_deductions ELSE 0 END) as varchar),'0')  as deductions_sum\r\n"
			+ " from deductions_details_confirm \r\n"
			+ "where emp_id=:emp_id and payment_type=:payment_type and confirm_month=:confirm_month  and year=:year", nativeQuery = true)
	String deductions_sum(String emp_id, String payment_type, String confirm_month, String year);

	@Query(value = "select coalesce(cast( sum(case when car_a_loan!=0 then car_a_loan ELSE 0 END)+\r\n"
			+ "sum(case when car_i_loan!=0 then car_i_loan ELSE 0 END)+\r\n"
			+ "sum(case when cyc_a_loan!=0 then cyc_a_loan ELSE 0 END)+\r\n"
			+ "sum(case when cyc_i_loan!=0 then cyc_i_loan ELSE 0 END)+\r\n"
			+ "sum(case when mca_a_loan!=0 then mca_a_loan ELSE 0 END)+\r\n"
			+ "sum(case when mca_i_loan!=0 then mca_i_loan ELSE 0 END)+\r\n"
			+ "sum(case when mar_a_loan!=0 then mar_a_loan ELSE 0 END)+\r\n"
			+ "sum(case when med_a_loan!=0 then med_a_loan ELSE 0 END)+\r\n"
			+ "sum(case when hba_loan!=0 then hba_loan ELSE 0 END)+\r\n"
			+ "sum(case when hba1_loan!=0 then hba1_loan ELSE 0 END)+\r\n"
			+ "sum(case when comp_loan!=0 then comp_loan ELSE 0 END)+\r\n"
			+ "sum(case when fa_loan!=0 then fa_loan  ELSE 0 END)+\r\n"
			+ "sum(case when ea_loan!=0 then  ea_loan ELSE 0 END)+\r\n"
			+ "sum(case when cell_loan!=0 then cell_loan ELSE 0 END)+\r\n"
			+ "sum(case when add_hba_loan!=0 then add_hba_loan ELSE 0 END)+\r\n"
			+ "sum(case when add_hba1_loan!=0 then add_hba1_loan ELSE 0 END)+\r\n"
			+ "sum(case when sal_adv_loan!=0 then sal_adv_loan ELSE 0 END)+\r\n"
			+ "sum(case when sfa_loan!=0 then sfa_loan ELSE 0 END)+\r\n"
			+ "sum(case when med_a_i_loan!=0 then med_a_i_loan ELSE 0 END)+\r\n"
			+ "sum(case when hcesa_loan!=0 then hcesa_loan ELSE 0 END)+\r\n"
			+ "sum(case when hcesa_i_loan!=0 then hcesa_i_loan ELSE 0 END)+\r\n"
			+ "sum(case when staff_pl_loan!=0 then staff_pl_loan ELSE 0 END)+\r\n"
			+ "sum(case when court_loan!=0 then court_loan ELSE 0 END)+\r\n"
			+ "sum(case when vij_bank_loan!=0 then vij_bank_loan ELSE 0 END)+\r\n"
			+ "sum(case when mar_i_loan!=0 then mar_i_loan ELSE 0 END)+\r\n"
			+ "sum(case when hr_arrear_loan!=0 then hr_arrear_loan ELSE 0 END)+\r\n"
			+ "sum(case when hbao_loan!=0 then hbao_loan ELSE 0 END)+\r\n"
			+ "sum(case when comp1_loan!=0 then comp1_loan ELSE 0 END) as varchar),'0')as  loan_sum from loan_details_confirm\r\n"
			+ "where emp_id=:emp_id and payment_type=:payment_type and confirm_month=:confirm_month  and year=:year", nativeQuery = true)
	String loan_sum(String emp_id, String payment_type, String confirm_month, String year);

	@Query(value = "SELECT prefix||' '||firstname||' '||lastname||' '||surname as employeename FROM  emp_details where emp_id=:emp_id", nativeQuery = true)
	Map<String, String> Emp_pay_Heading(String emp_id);


	@Query(value="SELECT case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end as payment_type,basic_pay_earnings,ea.per_pay_earnings,ea.da_earnings,ea.hra_earnings,ea.cca_earnings,ca_earnings,spl_pay_earnings,gp_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca,\r\n"
			+ "it_deductions,pt_deductions,lic_deductions,epf_deductions,apglis_deductions,apglil_deductions,house_rent_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,ppf_deductions,\r\n"
			+ "epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions,\r\n"
			+ "cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,\r\n"
			+ "fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,emp_id,emp.year,\r\n"
			+ "(select month_name from mstmonth where month_id=emp.confirm_month),total_earnings,total_deductions,total_loan from emp_details_confirm emp \r\n"
			+ "left join (select year,payment_type,confirm_month,basic_pay_earnings,per_pay_earnings,da_earnings,hra_earnings,cca_earnings,ca_earnings,spl_pay_earnings,\r\n"
			+ "gp_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca,sum(basic_pay_earnings+ per_pay_earnings+da_earnings+hra_earnings+cca_earnings+ca_earnings+\r\n"
			+ "spl_pay_earnings+gp_earnings+spl_all+ir_earnings+medical_earnings+misc_h_c+addl_hra+sca)as total_earnings from earnings_details_confirm ea  where \r\n"
			+ "ea.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type,\r\n"
			+ "basic_pay_earnings,ea.per_pay_earnings,ea.da_earnings,ea.hra_earnings,ea.cca_earnings,ca_earnings,spl_pay_earnings,gp_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca order by confirm_month)ea on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month  and ea.year=emp.year and emp.payment_type=ea.payment_type\r\n"
			+ "left join (select year,payment_type,confirm_month,it_deductions,pt_deductions,lic_deductions,epf_deductions,apglis_deductions,apglil_deductions,house_rent_deductions,gpfs_deductions,\r\n"
			+ "gpfl_deductions,gpfsa_deductions,con_decd_deductions,ppf_deductions,\r\n"
			+ "epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions,\r\n"
			+ "sum(it_deductions+pt_deductions+lic_deductions+epf_deductions+apglis_deductions+apglil_deductions+house_rent_deductions+gpfs_deductions+\r\n"
			+ "gpfl_deductions+gpfsa_deductions+con_decd_deductions+ppf_deductions+epf_l_deductions+license_deductions+\r\n"
			+ "gis_deductions+sal_rec_deductions+cca_deductions+rcs_cont_deductions+cmrf_deductions+fcf_deductions+vpf_deductions+other_deductions )as total_deductions from deductions_details_confirm d where \r\n"
			+ "d.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') \r\n"
			+ "group by year,confirm_month,payment_type,it_deductions,pt_deductions,lic_deductions,epf_deductions,apglis_deductions,apglil_deductions,house_rent_deductions,gpfs_deductions,\r\n"
			+ "gpfl_deductions,gpfsa_deductions,con_decd_deductions,ppf_deductions,\r\n"
			+ "epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions\r\n"
			+ "order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and ded.year=emp.year and emp.payment_type=ded.payment_type \r\n"
			+ "left join (select year,payment_type,confirm_month,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,\r\n"
			+ "med_a_loan,fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,\r\n"
			+ "vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,\r\n"
			+ "court_loan, sum(cell_loan+comp_loan+hba_loan+add_hba_loan+hbao_loan+comp1_loan+hba1_loan+\r\n"
			+ "add_hba1_loan+car_a_loan+cyc_a_loan+mca_a_loan+mar_a_loan+car_i_loan+cyc_i_loan+mca_i_loan+med_a_loan+fa_loan+ea_loan+sfa_loan+mar_i_loan+hr_arrear_loan+med_a_i_loan+vij_bank_loan+sal_adv_loan+hcesa_loan+\r\n"
			+ "court_loan)as total_loan from loan_details_confirm l where  \r\n"
			+ "year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and \r\n"
			+ "l.emp_id=:emp_id and payment_type in ('reg','supp') group by year,confirm_month,payment_type,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,\r\n"
			+ "vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan\r\n"
			+ "order by confirm_month)as ln on ln.confirm_month=emp.confirm_month  and emp.payment_type=ln.payment_type and ln.year=emp.year where \r\n"
			+ "emp.emp_id=:emp_id and  emp.year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and emp.year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('reg','supp')  \r\n"
			+ "order by emp.year,emp.confirm_month,generate_paybill",nativeQuery = true)
	List<Map<String, String>> Emp_pay_values(String emp_id, String emp_type, String fromMonth, String toMonth,
			String fromYear, String toYear);

}
