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
public interface PayrollReports_Repo extends JpaRepository<BankDetails_Model, Integer> {

	@Query(value = "SELECT emptype_id,emptype_name FROM employee_type order by emptype_id", nativeQuery = true)
	List<Map<String, String>> EmployeeTypeDropDown();

	@Query(value = "SELECT emp_id,(prefix||''||firstname||' '||lastname||' '||surname) as name,(select designation_name from designation d where d.designation_id=e.designation) as \r\n"
			+ "designation,case when emp_type='3' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place=\r\n"
			+ "'dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place and dist_code='01') end as work_place from os_earn_ded_details os where os.emp_id=e.emp_id) when \r\n"
			+ " emp_type='4' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' \r\n"
			+ "when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place and dist_code='01') end as work_place from nmr_earn_ded_details os where os.emp_id=e.emp_id)else \r\n"
			+ "'--' end as work_location1,case when emp_type='3' then (select os_location from os_earn_ded_details os where os.emp_id=e.emp_id) when  emp_type='4' then (select os_location from nmr_earn_ded_details os \r\n"
			+ "where os.emp_id=e.emp_id) else '--' end as work_location2,case when emp_type=cast(:emptype_id as varchar) or emp_type='2' or emp_type='5' then (select sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+\r\n"
			+ "medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details ea where ea.emp_id=e.emp_id) when emp_type='3' then  (select sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+\r\n"
			+ "os_ca_earnings+os_performance_earnings+os_ec_epf) from os_earn_ded_details os where os.emp_id=e.emp_id) else  (select sum(nmr_gross_earnings) from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as earnings,\r\n"
			+ "case when emp_type=cast(:emptype_id as varchar) or emp_type='2' or emp_type='5' then (select sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+\r\n"
			+ "con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions)\r\n"
			+ "as deductions from deductions_details d where d.emp_id=e.emp_id) when emp_type='3' then  (select sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions) from os_earn_ded_details os \r\n"
			+ "where  os.emp_id=e.emp_id) else  (select sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions) \r\n"
			+ "from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as deductions,case when emp_type=cast(:emptype_id as varchar) or emp_type='2'or emp_type='5' then (select sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+\r\n"
			+ "ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan+\r\n"
			+ "car_i_loanpi+car_a_loanpi+cyc_i_loanpi+cyc_a_loanpi+mca_i_loanpi+mca_a_loanpi+mar_a_loanpi+med_a_loanpi+hba_loanpi+hba1_loanpi+comp_loanpi+fa_loanpi+ea_loanpi+\r\n"
			+ "cell_loanpi+add_hba_loanpi+add_hba1_loanpi+sal_adv_loanpi+sfa_loanpi+med_a_i_loanpi+hcesa_loanpi+hcesa_i_loanpi+staff_pl_loanpi+court_loanpi+vij_bank_loanpi+mar_i_loanpi+hr_arrear_loanpi+\r\n"
			+ "hbao_loanpi+comp1_loanpi+car_i_loanti+car_a_loanti+cyc_i_loanti+cyc_a_loanti+mca_i_loanti+mca_a_loanti+mar_a_loanti+med_a_loanti+hba_loanti+hba1_loanti+comp_loanti+fa_loanti+ea_loanti+cell_loanti+\r\n"
			+ "add_hba_loanti+add_hba1_loanti+sal_adv_loanti+sfa_loanti+med_a_i_loanti+hcesa_loanti+hcesa_i_loanti+staff_pl_loanti+court_loanti+vij_bank_loanti+mar_i_loanti+hr_arrear_loanti+hbao_loanti+\r\n"
			+ "comp1_loanti)as loan \r\n"
			+ "from loan_details l where l.emp_id=e.emp_id)else 0.00  end as loan,emp_type,e.designation as designation_id ,working_status from emp_details e  where  emp_type=cast(:emptype_id as varchar)  and confirmed_by_cgm='t' order by working_status desc,e.designation,firstname", nativeQuery = true)
	List<Map<String, String>> ConfirmEmp_OnRolls_Report_state(int emptype_id);

	@Query(value = "SELECT emp_id,(prefix||''||firstname||' '||lastname||' '||surname) as name,(select designation_name from designation d where d.designation_id=e.designation) as \r\n"
			+ "designation,case when emp_type='3' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place=\r\n"
			+ "'dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place and dist_code='01'||:security_id) end as work_place from os_earn_ded_details os where os.emp_id=e.emp_id) when \r\n"
			+ " emp_type='4' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' \r\n"
			+ "when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place and dist_code='01'||:security_id) end as work_place from nmr_earn_ded_details os where os.emp_id=e.emp_id)else \r\n"
			+ "'--' end as work_location1,case when emp_type='3' then (select os_location from os_earn_ded_details os where os.emp_id=e.emp_id) when  emp_type='4' then (select os_location from nmr_earn_ded_details os \r\n"
			+ "where os.emp_id=e.emp_id) else '--' end as work_location2,case when emp_type=cast(:emptype_id as varchar) or emp_type='2' or emp_type='5' then (select sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+\r\n"
			+ "medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details ea where ea.emp_id=e.emp_id) when emp_type='3' then  (select sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+\r\n"
			+ "os_ca_earnings+os_performance_earnings+os_ec_epf) from os_earn_ded_details os where os.emp_id=e.emp_id) else  (select sum(nmr_gross_earnings) from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as earnings,\r\n"
			+ "case when emp_type=cast(:emptype_id as varchar) or emp_type='2' or emp_type='5' then (select sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+\r\n"
			+ "con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions)\r\n"
			+ "as deductions from deductions_details d where d.emp_id=e.emp_id) when emp_type='3' then  (select sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions) from os_earn_ded_details os \r\n"
			+ "where  os.emp_id=e.emp_id) else  (select sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions) \r\n"
			+ "from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as deductions,case when emp_type=cast(:emptype_id as varchar) or emp_type='2'or emp_type='5' then (select sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+\r\n"
			+ "ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan+\r\n"
			+ "car_i_loanpi+car_a_loanpi+cyc_i_loanpi+cyc_a_loanpi+mca_i_loanpi+mca_a_loanpi+mar_a_loanpi+med_a_loanpi+hba_loanpi+hba1_loanpi+comp_loanpi+fa_loanpi+ea_loanpi+\r\n"
			+ "cell_loanpi+add_hba_loanpi+add_hba1_loanpi+sal_adv_loanpi+sfa_loanpi+med_a_i_loanpi+hcesa_loanpi+hcesa_i_loanpi+staff_pl_loanpi+court_loanpi+vij_bank_loanpi+mar_i_loanpi+hr_arrear_loanpi+\r\n"
			+ "hbao_loanpi+comp1_loanpi+car_i_loanti+car_a_loanti+cyc_i_loanti+cyc_a_loanti+mca_i_loanti+mca_a_loanti+mar_a_loanti+med_a_loanti+hba_loanti+hba1_loanti+comp_loanti+fa_loanti+ea_loanti+cell_loanti+\r\n"
			+ "add_hba_loanti+add_hba1_loanti+sal_adv_loanti+sfa_loanti+med_a_i_loanti+hcesa_loanti+hcesa_i_loanti+staff_pl_loanti+court_loanti+vij_bank_loanti+mar_i_loanti+hr_arrear_loanti+hbao_loanti+\r\n"
			+ "comp1_loanti)as loan \r\n"
			+ "from loan_details l where l.emp_id=e.emp_id)else 0.00  end as loan,emp_type,e.designation as designation_id ,working_status from emp_details e  where security_id=:security_id and cast(emp_type as int)=:emptype_id  and confirmed_by_cgm='t' order by working_status desc,e.designation,firstname", nativeQuery = true)
	List<Map<String, String>> ConfirmEmp_OnRolls_Report(int emptype_id, String security_id);

//Back UP Table emp_details_crud 
	@Transactional
	@Modifying
	@Query(value = "update emp_details set confirmed_by_cgm='t',working_status=:working_status where emp_id=:emp_id", nativeQuery = true)
	int ConfirmEmp_WorkStatus_Report(String emp_id, String working_status);

	@Query(value = "select e.security_id,e.emp_id, e.prefix,e.firstname,e.lastname,e.surname,e.fathername,e.emp_type,sex,e.designation,\r\n"
			+ "e.account_no,e.others,e.bank_name,e.branch_code,e.ifsc,e.timestamp,d.gpfs_deductions,d.gpfl_deductions,d.gpfsa_deductions,\r\n"
			+ "d.house_rent_deductions,d.gis_deductions,d.pt_deductions,d.it_deductions,d.cca_deductions,d.license_deductions,d.con_decd_deductions,\r\n"
			+ "d.lic_deductions,d.rcs_cont_deductions,d.sal_rec_deductions,d.cmrf_deductions,d.fcf_deductions,d.epf_l_deductions,d.vpf_deductions,\r\n"
			+ "d.apgliS_deductions,d.apgliL_deductions,d.epf_deductions,d.ppf_deductions,d.other_deductions,er.basic_pay_earnings,er.per_pay_earnings,\r\n"
			+ "er.spl_pay_earnings,er.da_earnings,er.hra_earnings,er.cca_earnings,er.gp_earnings,er.ir_earnings,er.medical_earnings,er.ca_earnings,\r\n"
			+ "er.spl_all,er.misc_h_c,er.addl_hra,er.sca,l.car_i_loan,l.car_a_loan,l.cyc_i_loan,l.cyc_a_loan,l.mca_i_loan,l.mca_a_loan,l.mar_a_loan,\r\n"
			+ "l.med_a_loan,l.hba_loan,l.hba1_loan,l.comp_loan,l.fa_loan,l.ea_loan,l.cell_loan,l.add_hba_loan,l.add_hba1_loan,l.sal_adv_loan,l.sfa_loan,\r\n"
			+ "l.med_a_i_loan,l.hcesa_loan,l.hcesa_i_loan,l.staff_pl_loan,l.court_loan,l.vij_bank_loan,l.mar_i_loan,l.hr_arrear_loan,l.hbao_loan,l.comp1_loan,\r\n"
			+ "car_i_loanpi,l.car_a_loanpi,l.cyc_i_loanpi,l.cyc_a_loanpi,l.mca_i_loanpi,l.mca_a_loanpi,l.mar_a_loanpi,l.med_a_loanpi,l.hba_loanpi,l.hba1_loanpi,l.comp_loanpi,l.fa_loanpi,\r\n"
			+ "l.ea_loanpi,l.cell_loanpi,l.add_hba_loanpi,l.add_hba1_loanpi,l.sal_adv_loanpi,l.sfa_loanpi,l.med_a_i_loanpi,l.hcesa_loanpi,l.hcesa_i_loanpi,l.staff_pl_loanpi,\r\n"
			+ "l.hr_arrear_loanpi,l.hbao_loanpi,l.comp1_loanpi,l.car_i_loanti,l.car_a_loanti,l.cyc_i_loanti,l.cyc_a_loanti,l.mca_i_loanti,l.mca_a_loanti,l.mar_a_loanti,l.med_a_loanti,l.hba_loanti,\r\n"
			+ "l.hba1_loanti,l.comp_loanti,l.fa_loanti,l.ea_loanti,l.cell_loanti,l.add_hba_loanti,l.add_hba1_loanti,l.sal_adv_loanti,l.sfa_loanti,l.med_a_i_loanti,l.hcesa_loanti,\r\n"
			+ "l.hcesa_i_loanti,l.staff_pl_loanti,l.court_loanti,l.vij_bank_loanti,l.mar_i_loanti,l.hr_arrear_loanti,l.hbao_loanti,l.comp1_loanti,\r\n"
			+ "l.court_loanpi,l.vij_bank_loanpi,l.mar_i_loanpi from emp_details_confirm e\r\n"
			+ "left join deductions_details_confirm d on d.emp_id=e.emp_id and d.year=e.year and d.confirm_month=e.confirm_month and d.payment_type=e.payment_type and d.security_id=e.security_id\r\n"
			+ "left join earnings_details_confirm er on er.emp_id=e.emp_id and er.year=e.year and er.confirm_month=e.confirm_month and er.payment_type=e.payment_type and er.security_id=e.security_id\r\n"
			+ "left join loan_details_confirm l on l.emp_id=e.emp_id  and l.year=e.year and l.confirm_month=e.confirm_month and l.payment_type=e.payment_type and l.security_id=e.security_id\r\n"
			+ "where e.emp_id=cast(:emp_id as varchar) and e.year=cast(:year as varchar) and e.confirm_month=cast(:confirm_month as varchar) and e.payment_type=cast(:payment_type as varchar) and e.security_id=cast(:security_id as varchar) ", nativeQuery = true)
	List<Map<String, String>> ConfirmEmp_EmpName_Report125(String emp_id, String year, String confirm_month,
			String payment_type, String security_id);

	@Query(value = "select e.security_id,e.emp_id, e.prefix,e.firstname,e.lastname,e.surname,e.fathername,e.emp_type,sex,e.designation,\r\n"
			+ "e.account_no,e.others,e.bank_name,e.branch_code,e.ifsc,e.timestamp,os_basic_pay_earnings,os_hra_earnings,os_medical_earnings, \r\n"
			+ " os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions, \r\n"
			+ " os_prof_tax_deductions,os_other_deductions,os_work_place,os_location,'t' from os_earn_ded_details_confirm os left join\r\n"
			+ "emp_details_confirm e on e.emp_id=os.emp_id  where os.emp_id=:emp_id", nativeQuery = true)
	List<Map<String, String>> ConfirmEmp_EmpName_Report3(String emp_id);

	@Query(value = "select e.security_id,e.emp_id, e.prefix,e.firstname,e.lastname,e.surname,e.fathername,e.emp_type,sex,e.designation,\r\n"
			+ "e.account_no,e.others,e.bank_name,e.branch_code,e.ifsc,e.timestamp,nmr_gross_earnings,os_ees_epf_deductions, \r\n"
			+ " nmr_postalrd_deductions,os_prof_tax_deductions,os_work_place,os_location,nmr_tds_deductions, \r\n"
			+ " nmr_fa_deductions,nmr_ea_deductions,nmr_ma_deductions,nmr_lic_deductions,nmr_otherliab_deductions,'t' \r\n"
			+ "  from nmr_earn_ded_details_confirm ne left join emp_details_confirm e on e.emp_id=ne.emp_id  where  ne.emp_id=:emp_id", nativeQuery = true)
	List<Map<String, String>> ConfirmEmp_EmpName_Report4(String emp_id);

	@Transactional
	@Modifying

	@Query(value = "update emp_details_confirm set security_id=:security_id,prefix=:prefix,firstname=:firstname,lastname=:lastname,surname=:surname,"
			+ "fathername=:fathername,emp_type=cast(:emptype_id as varchar),sex=:sex,designation=cast(:designation as numeric),account_no=cast(:account_no as varchar),others=cast(:others as varchar),bank_name=:bank_name,"
			+ "branch_code=:branch_code,ifsc=cast(:ifsc as varchar), timestamp=now() where emp_id=cast(:emp_id as varchar) and confirm_month=cast(:confirm_month as varchar) and payment_type=cast(:payment_type as varchar) and year=cast(:year as varchar)", nativeQuery = true)

//	@Query(value = "update emp_details set security_id=:security_id,prefix=:prefix,firstname=:firstname,lastname=:lastname,surname=:surname,"
//			+ "fathername=:fathername,emp_type=:emptype_id,sex=:sex,designation=:designation,account_no=:account_no,others=:others,bank_name=:bank_name,"
//			+ "branch_code=:branch_code,ifsc=:ifsc, timestamp=now() where emp_id=:emp_id", nativeQuery = true)
	int ConfirmEmp_Insert_Report_Emp_125(String security_id, String emp_id, String prefix, String firstname,
			String lastname, String surname, String fathername, int emptype_id, String sex, int designation,
			String account_no, String others, String bank_name, String branch_code, String ifsc, String confirm_month,
			String payment_type, String year);

	@Transactional
	@Modifying

	@Query(value = "update earnings_details_confirm set  security_id=:security_id,basic_pay_earnings=:basic_pay_earnings,per_pay_earnings=:per_pay_earnings,spl_pay_earnings=:spl_pay_earnings,"
			+ "da_earnings=:da_earnings,hra_earnings=:hra_earnings,cca_earnings=:cca_earnings,gp_earnings=:gp_earnings,ir_earnings=:ir_earnings,medical_earnings=:medical_earnings,"
			+ "ca_earnings=:ca_earnings,spl_all=:spl_all,misc_h_c=:misc_h_c,addl_hra=:addl_hra,sca=:sca,timestamp=now() where emp_id=:emp_id and confirm_month=:confirm_month and payment_type=:payment_type and year=:year", nativeQuery = true)
//	@Query(value = "update earnings_details set  security_id=:security_id,basic_pay_earnings=:basic_pay_earnings,per_pay_earnings=:per_pay_earnings,spl_pay_earnings=:spl_pay_earnings,"
//			+ "da_earnings=:da_earnings,hra_earnings=:hra_earnings,cca_earnings=:cca_earnings,gp_earnings=:gp_earnings,ir_earnings=:ir_earnings,medical_earnings=:medical_earnings,"
//			+ "ca_earnings=:ca_earnings,spl_all=:spl_all,misc_h_c=:misc_h_c,addl_hra=:addl_hra,sca=:sca,timestamp=now() where emp_id=:emp_id", nativeQuery = true)
	int ConfirmEmp_Insert_Report_Earning_125(String security_id, String emp_id, double basic_pay_earnings,
			double per_pay_earnings, double spl_pay_earnings, double da_earnings, double hra_earnings,
			double cca_earnings, double gp_earnings, double ir_earnings, double medical_earnings, double ca_earnings,
			double spl_all, double misc_h_c, double addl_hra, double sca, String confirm_month, String payment_type,
			String year);

	@Transactional
	@Modifying
	@Query(value = "update deductions_details_confirm set security_id=:security_id,gpfs_deductions=:gpfs_deductions,gpfl_deductions=:gpfl_deductions,gpfsa_deductions=:gpfsa_deductions,"
			+ "house_rent_deductions=:house_rent_deductions,gis_deductions=:gis_deductions,pt_deductions=:pt_deductions,it_deductions=:it_deductions,cca_deductions=:cca_deductions,"
			+ "license_deductions=:license_deductions,con_decd_deductions=:con_decd_deductions,lic_deductions=:lic_deductions,rcs_cont_deductions=:rcs_cont_deductions,"
			+ "sal_rec_deductions=:sal_rec_deductions,cmrf_deductions=:cmrf_deductions,fcf_deductions=:fcf_deductions,epf_l_deductions=:epf_l_deductions,vpf_deductions=:vpf_deductions,"
			+ "apgliS_deductions=:apglis_deductions,apgliL_deductions=:apglil_deductions,epf_deductions=:epf_deductions,ppf_deductions=:ppf_deductions,other_deductions=:other_deductions,"
			+ "timestamp=now() where emp_id=:emp_id and confirm_month=:confirm_month and payment_type=:payment_type and year=:year", nativeQuery = true)
//	@Query(value = "update deductions_details set security_id=:security_id,gpfs_deductions=:gpfs_deductions,gpfl_deductions=:gpfl_deductions,gpfsa_deductions=:gpfsa_deductions,"
//			+ "house_rent_deductions=:house_rent_deductions,gis_deductions=:gis_deductions,pt_deductions=:pt_deductions,it_deductions=:it_deductions,cca_deductions=:cca_deductions,"
//			+ "license_deductions=:license_deductions,con_decd_deductions=:con_decd_deductions,lic_deductions=:lic_deductions,rcs_cont_deductions=:rcs_cont_deductions,"
//			+ "sal_rec_deductions=:sal_rec_deductions,cmrf_deductions=:cmrf_deductions,fcf_deductions=:fcf_deductions,epf_l_deductions=:epf_l_deductions,vpf_deductions=:vpf_deductions,"
//			+ "apgliS_deductions=:apglis_deductions,apgliL_deductions=:apglil_deductions,epf_deductions=:epf_deductions,ppf_deductions=:ppf_deductions,other_deductions=:other_deductions,"
//			+ "timestamp=now() where emp_id=:emp_id", nativeQuery = true)
	int ConfirmEmp_Insert_Report_Deductions_125(String security_id, String emp_id, double gpfs_deductions,
			double gpfl_deductions, double gpfsa_deductions, double house_rent_deductions, double gis_deductions,
			double pt_deductions, double it_deductions, double cca_deductions, double license_deductions,
			double con_decd_deductions, double lic_deductions, double rcs_cont_deductions, double sal_rec_deductions,
			double cmrf_deductions, double fcf_deductions, double epf_l_deductions, double vpf_deductions,
			double apglis_deductions, double apglil_deductions, double epf_deductions, double ppf_deductions,
			double other_deductions, String confirm_month, String payment_type, String year);

	@Transactional
	@Modifying
	@Query(value = "update loan_details_confirm set security_id=:security_id,car_i_loan=:car_i_loan,car_a_loan=:car_a_loan,cyc_i_loan=:cyc_i_loan,cyc_a_loan=:cyc_a_loan,"
			+ "mca_i_loan=:mca_i_loan,mca_a_loan=:mca_a_loan,mar_a_loan=:mar_a_loan,med_a_loan=:med_a_loan,hba_loan=:hba_loan,hba1_loan=:hba1_loan,comp_loan=:comp_loan,"
			+ "fa_loan=:fa_loan,ea_loan=:ea_loan,cell_loan=:cell_loan,add_hba_loan=:add_hba_loan, add_hba1_loan=:add_hba1_loan,sal_adv_loan=:sal_adv_loan,sfa_loan=:sfa_loan,"
			+ "med_a_i_loan=:med_a_i_loan,hcesa_loan=:hcesa_loan,hcesa_I_loan=:hcesa_I_loan,staff_pl_loan=:staff_pl_loan,court_loan=:court_loan,vij_bank_loan=:vij_bank_loan,"
			+ "mar_i_loan=:mar_i_loan,hr_arrear_loan=:hr_arrear_loan,hbao_loan=:hbao_loan,comp1_loan=:comp1_loan,car_i_loanpi=:car_i_loanpi, car_a_loanpi=:car_a_loanpi, cyc_i_loanpi=:cyc_i_loanpi, cyc_a_loanpi=:cyc_a_loanpi, mca_i_loanpi=:mca_i_loanpi,"
			+ "mca_a_loanpi=:mca_a_loanpi, mar_a_loanpi=:mar_a_loanpi, med_a_loanpi=:med_a_loanpi, hba_loanpi=:hba_loanpi, hba1_loanpi=:hba1_loanpi,"
			+ "comp_loanpi=:comp_loanpi, fa_loanpi=:fa_loanpi, ea_loanpi=:ea_loanpi, cell_loanpi=:cell_loanpi, add_hba_loanpi=:add_hba_loanpi,"
			+ "add_hba1_loanpi=:add_hba1_loanpi, sal_adv_loanpi=:sal_adv_loanpi, sfa_loanpi=:sfa_loanpi, med_a_i_loanpi=:med_a_i_loanpi,"
			+ "hcesa_loanpi=:hcesa_loanpi, hcesa_i_loanpi=:hcesa_i_loanpi, staff_pl_loanpi=:staff_pl_loanpi, hr_arrear_loanpi=:hr_arrear_loanpi,"
			+ "hbao_loanpi=:hbao_loanpi,comp1_loanpi=:comp1_loanpi,car_i_loanti=:car_i_loanti,car_a_loanti=:car_a_loanti,cyc_i_loanti=:cyc_i_loanti,cyc_a_loanti=:cyc_a_loanti,mca_i_loanti=:mca_i_loanti,mca_a_loanti=:mca_a_loanti,mar_a_loanti=:mar_a_loanti,med_a_loanti=:med_a_loanti,hba_loanti=:hba_loanti,"
			+ "hba1_loanti=:hba1_loanti,comp_loanti=:comp_loanti,fa_loanti=:fa_loanti,ea_loanti=:ea_loanti,cell_loanti=:cell_loanti,add_hba_loanti=:add_hba_loanti,add_hba1_loanti=:add_hba1_loanti,"
			+ "sal_adv_loanti=:sal_adv_loanti,sfa_loanti=:sfa_loanti,med_a_i_loanti=:med_a_i_loanti,hcesa_loanti=:hcesa_loanti,"
			+ "hcesa_i_loanti=:hcesa_i_loanti,staff_pl_loanti=:staff_pl_loanti,court_loanti=:court_loanti,vij_bank_loanti=:vij_bank_loanti,mar_i_loanti=:mar_i_loanti,hr_arrear_loanti=:hr_arrear_loanti,"
			+ "hbao_loanti=:hbao_loanti,comp1_loanti=:comp1_loanti,"
			+ "court_loanpi=:court_loanpi,vij_bank_loanpi=:vij_bank_loanpi,mar_i_loanpi=:mar_i_loanpi,timestamp=now() where emp_id=:emp_id and confirm_month=:confirm_month and payment_type=:payment_type and year=:year", nativeQuery = true)

//	@Query(value = "update loan_details set security_id=:security_id,car_i_loan=:car_i_loan,car_a_loan=:car_a_loan,cyc_i_loan=:cyc_i_loan,cyc_a_loan=:cyc_a_loan,"
//			+ "mca_i_loan=:mca_i_loan,mca_a_loan=:mca_a_loan,mar_a_loan=:mar_a_loan,med_a_loan=:med_a_loan,hba_loan=:hba_loan,hba1_loan=:hba1_loan,comp_loan=:comp_loan,"
//			+ "fa_loan=:fa_loan,ea_loan=:ea_loan,cell_loan=:cell_loan,add_hba_loan=:add_hba_loan, add_hba1_loan=:add_hba1_loan,sal_adv_loan=:sal_adv_loan,sfa_loan=:sfa_loan,"
//			+ "med_a_i_loan=:med_a_i_loan,hcesa_loan=:hcesa_loan,hcesa_I_loan=:hcesa_I_loan,staff_pl_loan=:staff_pl_loan,court_loan=:court_loan,vij_bank_loan=:vij_bank_loan,"
//			+ "mar_i_loan=:mar_i_loan,hr_arrear_loan=:hr_arrear_loan,hbao_loan=:hbao_loan,comp1_loan=:comp1_loan,car_i_loanpi=:car_i_loanpi, car_a_loanpi=:car_a_loanpi, cyc_i_loanpi=:cyc_i_loanpi, cyc_a_loanpi=:cyc_a_loanpi, mca_i_loanpi=:mca_i_loanpi,"
//			+ "mca_a_loanpi=:mca_a_loanpi, mar_a_loanpi=:mar_a_loanpi, med_a_loanpi=:med_a_loanpi, hba_loanpi=:hba_loanpi, hba1_loanpi=:hba1_loanpi,"
//			+ "comp_loanpi=:comp_loanpi, fa_loanpi=:fa_loanpi, ea_loanpi=:ea_loanpi, cell_loanpi=:cell_loanpi, add_hba_loanpi=:add_hba_loanpi,"
//			+ "add_hba1_loanpi=:add_hba1_loanpi, sal_adv_loanpi=:sal_adv_loanpi, sfa_loanpi=:sfa_loanpi, med_a_i_loanpi=:med_a_i_loanpi,"
//			+ "hcesa_loanpi=:hcesa_loanpi, hcesa_i_loanpi=:hcesa_i_loanpi, staff_pl_loanpi=:staff_pl_loanpi, hr_arrear_loanpi=:hr_arrear_loanpi,"
//			+ "hbao_loanpi=:hbao_loanpi,comp1_loanpi=:comp1_loanpi,car_i_loanti=:car_i_loanti,car_a_loanti=:car_a_loanti,cyc_i_loanti=:cyc_i_loanti,cyc_a_loanti=:cyc_a_loanti,mca_i_loanti=:mca_i_loanti,mca_a_loanti=:mca_a_loanti,mar_a_loanti=:mar_a_loanti,med_a_loanti=:med_a_loanti,hba_loanti=:hba_loanti,"
//			+ "hba1_loanti=:hba1_loanti,comp_loanti=:comp_loanti,fa_loanti=:fa_loanti,ea_loanti=:ea_loanti,cell_loanti=:cell_loanti,add_hba_loanti=:add_hba_loanti,add_hba1_loanti=:add_hba1_loanti,"
//			+ "sal_adv_loanti=:sal_adv_loanti,sfa_loanti=:sfa_loanti,med_a_i_loanti=:med_a_i_loanti,hcesa_loanti=:hcesa_loanti,"
//			+ "hcesa_i_loanti=:hcesa_i_loanti,staff_pl_loanti=:staff_pl_loanti,court_loanti=:court_loanti,vij_bank_loanti=:vij_bank_loanti,mar_i_loanti=:mar_i_loanti,hr_arrear_loanti=:hr_arrear_loanti,"
//			+ "hbao_loanti=:hbao_loanti,comp1_loanti=:comp1_loanti,"
//			+ "court_loanpi=:court_loanpi,vij_bank_loanpi=:vij_bank_loanpi,mar_i_loanpi=:mar_i_loanpi,timestamp=now() where emp_id=:emp_id", nativeQuery = true)
	int ConfirmEmp_Insert_Report_Loan_125(String security_id, String emp_id, double car_i_loan, double car_a_loan,
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
			double hbao_loanti, double comp1_loanti, double court_loanpi, double vij_bank_loanpi, double mar_i_loanpi,
			String confirm_month, String payment_type, String year);

	@Transactional
	@Modifying
	@Query(value = "update  os_earn_ded_details_confirm  set security_id=:security_id,os_basic_pay_earnings=:os_basic_pay_earnings,os_hra_earnings=:os_hra_earnings,"
			+ " os_medical_earnings=:os_medical_earnings,os_ca_earnings=:os_ca_earnings,os_performance_earnings=:os_performance_earnings,os_ec_epf=:os_ec_epf,os_ees_epf_deductions=:os_ees_epf_deductions,"
			+ "os_ers_epf_deductions=:os_ers_epf_deductions,os_prof_tax_deductions=:os_prof_tax_deductions,os_other_deductions=:os_other_deductions,os_work_place=:os_work_place,os_location=:os_location,"
			+ "timestamp=now() where emp_id=:emp_id and confirm_month=:confirm_month and payment_type=:payment_type and year=:year", nativeQuery = true)
//	@Query(value = "update  os_earn_ded_details  set security_id=:security_id,os_basic_pay_earnings=:os_basic_pay_earnings,os_hra_earnings=:os_hra_earnings,"
//			+ " os_medical_earnings=:os_medical_earnings,os_ca_earnings=:os_ca_earnings,os_performance_earnings=:os_performance_earnings,os_ec_epf=:os_ec_epf,os_ees_epf_deductions=:os_ees_epf_deductions,"
//			+ "os_ers_epf_deductions=:os_ers_epf_deductions,os_prof_tax_deductions=:os_prof_tax_deductions,os_other_deductions=:os_other_deductions,os_work_place=:os_work_place,os_location=:os_location,"
//			+ "timestamp=now() where emp_id=:emp_id", nativeQuery = true)
	int ConfirmEmp_Insert_Os_Report3(String security_id, String emp_id, double os_basic_pay_earnings,
			double os_hra_earnings, double os_medical_earnings, double os_ca_earnings, double os_performance_earnings,
			double os_ec_epf, double os_ees_epf_deductions, double os_ers_epf_deductions, double os_prof_tax_deductions,
			double os_other_deductions, String os_work_place, String os_location, String confirm_month,
			String payment_type, String year);

	@Transactional
	@Modifying

	@Query(value = "update nmr_earn_ded_details_confirm set security_id=:security_id,nmr_gross_earnings=:nmr_gross_earnings,os_ees_epf_deductions=:os_ees_epf_deductions,"
			+ " nmr_postalrd_deductions=:nmr_postalrd_deductions,os_prof_tax_deductions=:os_prof_tax_deductions,os_work_place=:os_work_place,os_location=:os_location,"
			+ "nmr_tds_deductions=:nmr_tds_deductions,"
			+ "  nmr_fa_deductions=:nmr_fa_deductions,nmr_ea_deductions=:nmr_ea_deductions,nmr_ma_deductions=:nmr_ma_deductions,nmr_lic_deductions=:nmr_lic_deductions,"
			+ "nmr_otherliab_deductions=:nmr_otherliab_deductions, timestamp=now() where emp_id=cast(:emp_id as varchar)and confirm_month=cast(:confirm_month as varchar) and payment_type=cast(:payment_type as varchar) and year=cast(:year as varchar)", nativeQuery = true)
//	@Query(value = "update nmr_earn_ded_details set security_id=:security_id,nmr_gross_earnings=:nmr_gross_earnings,os_ees_epf_deductions=:os_ees_epf_deductions,"
//			+ " nmr_postalrd_deductions=:nmr_postalrd_deductions,os_prof_tax_deductions=:os_prof_tax_deductions,os_work_place=:os_work_place,os_location=:os_location,"
//			+ "nmr_tds_deductions=:nmr_tds_deductions,"
//			+ "  nmr_fa_deductions=:nmr_fa_deductions,nmr_ea_deductions=:nmr_ea_deductions,nmr_ma_deductions=:nmr_ma_deductions,nmr_lic_deductions=:nmr_lic_deductions,"
//			+ "nmr_otherliab_deductions=:nmr_otherliab_deductions, timestamp=now() where emp_id=:emp_id ", nativeQuery = true)
	int ConfirmEmp_Insert_Nmr_Report4(String security_id, String emp_id, double nmr_gross_earnings,
			double os_ees_epf_deductions, double nmr_postalrd_deductions, double os_prof_tax_deductions,
			String os_work_place, String os_location, double nmr_tds_deductions, double nmr_fa_deductions,
			double nmr_ea_deductions, double nmr_ma_deductions, double nmr_lic_deductions,
			double nmr_otherliab_deductions, String confirm_month, String payment_type, String year);

//2nd Payroll Report

	@Query(value = "SELECT designation_id,designation_name FROM designation", nativeQuery = true)
	List<Map<String, String>> DesignationDropDown();

	@Query(value = "select month_id,month_name from mstmonth", nativeQuery = true)
	List<Map<String, String>> getMonthsDropDown();
//SELECT distinct year  FROM emp_details_confirm order by year desc
	@Query(value = "SELECT year FROM  year_mst ", nativeQuery = true)
	List<Map<String, String>> YearsDropDown();

	@Query(value = "SELECT  emp_id,(prefix||''||firstname||' '||lastname||' '||surname) as name,(select designation_name from designation d where d.designation_id=e.designation) as designation,case when emp_type='3'  then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place\r\n"
			+ " and dist_code='01'||:security_id) end as work_place from os_earn_ded_details_confirm os where os.emp_id=e.emp_id\r\n"
			+ " and confirm_month=:confirm_month  and year=:year and payment_type=:payment_type) when  emp_type='4' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place\r\n"
			+ " and dist_code='01'||:security_id) end as work_place from nmr_earn_ded_details_confirm nmr where nmr.emp_id=e.emp_id\r\n"
			+ " and confirm_month=:confirm_month  and year=:year and payment_type=:payment_type) else '--'  end as work_location1,case when emp_type='3'  then (select os_location from os_earn_ded_details os where os.emp_id=e.emp_id) when emp_type='4' then (select os_location from nmr_earn_ded_details os where os.emp_id=e.emp_id) else '--'  end as work_location2,case when emp_type='1' or emp_type='2' or emp_type='5' then (select sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details_confirm ea where ea.emp_id=e.emp_id and\r\n"
			+ "confirm_month=:confirm_month and year=:year and payment_type=:payment_type) when emp_type='3' then (select sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf) from os_earn_ded_details_confirm os where os.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) else  (select sum(nmr_gross_earnings) from nmr_earn_ded_details_confirm os where os.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) end as earnings,case when emp_type='1' or emp_type='2'or emp_type='5' then (select sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions )as deductions from deductions_details_confirm d where d.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) when emp_type='3' then  (select sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions)\" + \" from os_earn_ded_details_confirm os where  os.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) else  (select sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions) from nmr_earn_ded_details_confirm os where os.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) end as deductions,case when emp_type='1' or emp_type='2'or emp_type='5' then (select sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+\r\n"
			+ "ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan+\r\n"
			+ "car_i_loanpi+car_a_loanpi+cyc_i_loanpi+cyc_a_loanpi+mca_i_loanpi+mca_a_loanpi+mar_a_loanpi+med_a_loanpi+hba_loanpi+hba1_loanpi+comp_loanpi+fa_loanpi+ea_loanpi+\r\n"
			+ "cell_loanpi+add_hba_loanpi+add_hba1_loanpi+sal_adv_loanpi+sfa_loanpi+med_a_i_loanpi+hcesa_loanpi+hcesa_i_loanpi+staff_pl_loanpi+court_loanpi+vij_bank_loanpi+mar_i_loanpi+hr_arrear_loanpi+\r\n"
			+ "hbao_loanpi+comp1_loanpi+car_i_loanti+car_a_loanti+cyc_i_loanti+cyc_a_loanti+mca_i_loanti+mca_a_loanti+mar_a_loanti+med_a_loanti+hba_loanti+hba1_loanti+comp_loanti+fa_loanti+ea_loanti+cell_loanti+\r\n"
			+ "add_hba_loanti+add_hba1_loanti+sal_adv_loanti+sfa_loanti+med_a_i_loanti+hcesa_loanti+hcesa_i_loanti+staff_pl_loanti+court_loanti+vij_bank_loanti+mar_i_loanti+hr_arrear_loanti+hbao_loanti+\r\n"
			+ "comp1_loanti)as loan from loan_details_confirm l  where l.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) else 0.00  end as loan,emp_type,e.designation as designation_id ,confirm_month,payment_type,year  from emp_details_confirm e \r\n"
			+ " where security_id=:security_id and emp_type=:emp_type and  confirm_month=:confirm_month and year=:year and payment_type=:payment_type and generate_paybill='t'  order by e.designation,firstname", nativeQuery = true)
	List<Map<String, String>> GetMonthlyPaybillConfirmationReport(String emp_type, String payment_type,
			String confirm_month, String year, String security_id);

	@Query(value = "SELECT  emp_id,(prefix||''||firstname||' '||lastname||' '||surname) as name,(select designation_name from designation d where d.designation_id=e.designation) as designation,case when emp_type='3'  then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place\r\n"
			+ " and dist_code='01'||:security_id) end as work_place from os_earn_ded_details_confirm os where os.emp_id=e.emp_id\r\n"
			+ " and confirm_month=:confirm_month  and year=:year and payment_type=:payment_type) when  emp_type='4' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place\r\n"
			+ " and dist_code='01'||:security_id) end as work_place from nmr_earn_ded_details_confirm nmr where nmr.emp_id=e.emp_id\r\n"
			+ " and confirm_month=:confirm_month  and year=:year and payment_type=:payment_type) else '--'  end as work_location1,case when emp_type='3'  then (select os_location from os_earn_ded_details os where os.emp_id=e.emp_id) when emp_type='4' then (select os_location from nmr_earn_ded_details os where os.emp_id=e.emp_id) else '--'  end as work_location2,case when emp_type='1' or emp_type='2' or emp_type='5' then (select sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details_confirm ea where ea.emp_id=e.emp_id and\r\n"
			+ "confirm_month=:confirm_month and year=:year and payment_type=:payment_type) when emp_type='3' then (select sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf) from os_earn_ded_details_confirm os where os.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) else  (select sum(nmr_gross_earnings) from nmr_earn_ded_details_confirm os where os.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) end as earnings,case when emp_type='1' or emp_type='2'or emp_type='5' then (select sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions )as deductions from deductions_details_confirm d where d.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) when emp_type='3' then  (select sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions)\" + \" from os_earn_ded_details_confirm os where  os.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) else  (select sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions) from nmr_earn_ded_details_confirm os where os.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) end as deductions,case when emp_type='1' or emp_type='2'or emp_type='5' then (select sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+\r\n"
			+ "ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan+\r\n"
			+ "car_i_loanpi+car_a_loanpi+cyc_i_loanpi+cyc_a_loanpi+mca_i_loanpi+mca_a_loanpi+mar_a_loanpi+med_a_loanpi+hba_loanpi+hba1_loanpi+comp_loanpi+fa_loanpi+ea_loanpi+\r\n"
			+ "cell_loanpi+add_hba_loanpi+add_hba1_loanpi+sal_adv_loanpi+sfa_loanpi+med_a_i_loanpi+hcesa_loanpi+hcesa_i_loanpi+staff_pl_loanpi+court_loanpi+vij_bank_loanpi+mar_i_loanpi+hr_arrear_loanpi+\r\n"
			+ "hbao_loanpi+comp1_loanpi+car_i_loanti+car_a_loanti+cyc_i_loanti+cyc_a_loanti+mca_i_loanti+mca_a_loanti+mar_a_loanti+med_a_loanti+hba_loanti+hba1_loanti+comp_loanti+fa_loanti+ea_loanti+cell_loanti+\r\n"
			+ "add_hba_loanti+add_hba1_loanti+sal_adv_loanti+sfa_loanti+med_a_i_loanti+hcesa_loanti+hcesa_i_loanti+staff_pl_loanti+court_loanti+vij_bank_loanti+mar_i_loanti+hr_arrear_loanti+hbao_loanti+\r\n"
			+ "comp1_loanti)as loan from loan_details_confirm l  where l.emp_id=e.emp_id and\r\n"
			+ "  confirm_month=:confirm_month and year=:year and payment_type=:payment_type) else 0.00  end as loan,emp_type,e.designation as designation_id ,confirm_month,payment_type,year  from emp_details_confirm e \r\n"
			+ " where security_id=:security_id and emp_type=:emp_type and  confirm_month=:confirm_month and year=:year and payment_type=:payment_type and generate_paybill='f'  order by e.designation,firstname", nativeQuery = true)
	List<Map<String, String>> GetMonthlyPaybillConfirmationReport_AfterConfirm(String emp_type, String payment_type,
			String confirm_month, String year, String security_id);

	@Query(value = "select emp_id,(prefix||''||firstname||' '||lastname||' '||surname) as name,(select designation_name from designation d where d.designation_id=e.designation) as designation,case when emp_type='3'  then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place \r\n"
			+ " and dist_code='01'||:security_id) end as work_place from os_earn_ded_details os where os.emp_id=e.emp_id) when  emp_type='4' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place \r\n"
			+ " and dist_code='01'||:security_id) end as work_place from nmr_earn_ded_details os where os.emp_id=e.emp_id) else '--' end as work_location1,case when emp_type='3' then (select os_location from os_earn_ded_details os where os.emp_id=e.emp_id) \r\n"
			+ "when emp_type='4'  then (select os_location from nmr_earn_ded_details os where os.emp_id=e.emp_id)  else '--' end as work_location2,case when emp_type='1' or emp_type='2' or emp_type='5' then \r\n"
			+ "(select sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details ea \r\n"
			+ "where ea.emp_id=e.emp_id) when emp_type='3' then  (select sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf) from os_earn_ded_details os \r\n"
			+ "where os.emp_id=e.emp_id) else  (select sum(nmr_gross_earnings) from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as earnings,case when emp_type='1' or emp_type='2' or emp_type='5' then \r\n"
			+ "(select sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions )as deductions\r\n"
			+ " from deductions_details d where d.emp_id=e.emp_id) when emp_type='3' then  (select sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions) from os_earn_ded_details os \r\n"
			+ "where  os.emp_id=e.emp_id) else  (select sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions) \r\n"
			+ "from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as deductions,case when emp_type='1' or emp_type='2' or emp_type='5' then (select sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+\r\n"
			+ "ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan+\r\n"
			+ "car_i_loanpi+car_a_loanpi+cyc_i_loanpi+cyc_a_loanpi+mca_i_loanpi+mca_a_loanpi+mar_a_loanpi+med_a_loanpi+hba_loanpi+hba1_loanpi+comp_loanpi+fa_loanpi+ea_loanpi+\r\n"
			+ "cell_loanpi+add_hba_loanpi+add_hba1_loanpi+sal_adv_loanpi+sfa_loanpi+med_a_i_loanpi+hcesa_loanpi+hcesa_i_loanpi+staff_pl_loanpi+court_loanpi+vij_bank_loanpi+mar_i_loanpi+hr_arrear_loanpi+\r\n"
			+ "hbao_loanpi+comp1_loanpi+car_i_loanti+car_a_loanti+cyc_i_loanti+cyc_a_loanti+mca_i_loanti+mca_a_loanti+mar_a_loanti+med_a_loanti+hba_loanti+hba1_loanti+comp_loanti+fa_loanti+ea_loanti+cell_loanti+\r\n"
			+ "add_hba_loanti+add_hba1_loanti+sal_adv_loanti+sfa_loanti+med_a_i_loanti+hcesa_loanti+hcesa_i_loanti+staff_pl_loanti+court_loanti+vij_bank_loanti+mar_i_loanti+hr_arrear_loanti+hbao_loanti+\r\n"
			+ "comp1_loanti)as loan from loan_details l \r\n"
			+ "where l.emp_id=e.emp_id) else 0.00  end as loan,emp_type,e.designation as designation_id from emp_details e \r\n"
			+ " where security_id=:security_id and emp_type=:emp_type and  working_status in('working','suspended','longleave','resigned','retired',null) and  emp_id not in (select emp_id from emp_details_confirm \r\n"
			+ " where confirm_month=:confirm_month  and year=:year and payment_type=:payment_type) order by e.designation,firstname", nativeQuery = true)
	List<Map<String, String>> GetMonthlyPaybillConfirmationReport_beforeConfirm(String emp_type, String payment_type,
			String confirm_month, String year, String security_id);

	@Transactional
	@Modifying
	@Query(value = "update emp_details_confirm set generate_paybill='t' where emp_id=:emp_id \r\n"
			+ "and emp_type=CAST(:emp_type AS VARCHAR) and confirm_month=:confirm_month and year=:year and payment_type=:payment_type", nativeQuery = true)
	int paybillconfirm_PaybillUpdatList(String emp_id, String emp_type, String confirm_month, String year,
			String payment_type);

	@Transactional
	@Modifying
	@Query(value = " insert into emp_details_confirm(security_id,emp_id,prefix,firstname,lastname,surname,fathername, \r\n"
			+ " emp_type,sex,designation,account_no,others,bank_name,branch_code,generate_paybill,timestamp,confirm_month, \r\n"
			+ " payment_type,year) (select security_id,emp_id,prefix,firstname,lastname,surname,fathername,emp_type, \r\n"
			+ " sex,designation,account_no,others,bank_name,branch_code,'f',now(),:confirm_month,:payment_type,:year\r\n"
			+ " from emp_details where emp_id=:emp_id )", nativeQuery = true)
	int paybillconfirm_UpdatList_empdetails_Confirm(String confirm_month, String payment_type, String year,
			String emp_id);

	@Transactional
	@Modifying
	@Query(value = "insert into earnings_details_confirm(security_id,emp_id,basic_pay_earnings,per_pay_earnings, \r\n"
			+ " spl_pay_earnings,da_earnings,hra_earnings,cca_earnings,gp_earnings,ir_earnings,medical_earnings, \r\n"
			+ " ca_earnings,spl_all,misc_h_c,addl_hra,sca,isdelete,timestamp,confirm_month,payment_type,year) \r\n"
			+ " (select security_id,emp_id,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,da_earnings, \r\n"
			+ " hra_earnings,cca_earnings,gp_earnings,ir_earnings,medical_earnings,ca_earnings,spl_all,misc_h_c, \r\n"
			+ " addl_hra,sca,'t',now(),:confirm_month,:payment_type,:year from earnings_details where emp_id=:emp_id )", nativeQuery = true)
	int paybillconfirm_UpdatList_earningdetails_Confirm(String confirm_month, String payment_type, String year,
			String emp_id);
//	@Transactional
//	@Modifying
//	@Query(value = "insert into earnings_details_confirm(security_id,emp_id,basic_pay_earnings,per_pay_earnings, \r\n"
//			+ " spl_pay_earnings,da_earnings,hra_earnings,cca_earnings,gp_earnings,ir_earnings,medical_earnings, \r\n"
//			+ " ca_earnings,spl_all,misc_h_c,addl_hra,sca,isdelete,timestamp,confirm_month,payment_type,year) \r\n"
//			+ " (select security_id,emp_id,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,da_earnings, \r\n"
//			+ " hra_earnings,cca_earnings,gp_earnings,ir_earnings,medical_earnings,ca_earnings,spl_all,misc_h_c, \r\n"
//			+ " addl_hra,sca,'t',now(),confirm_month,payment_type,year from earnings_details where emp_id=:emp_id )", nativeQuery = true)
//	int paybillconfirm_UpdatList_earningdetails_Confirm(String emp_id);

	@Transactional
	@Modifying
	@Query(value = " insert into deductions_details_confirm(security_id,emp_id,gpfs_deductions,gpfl_deductions, \r\n"
			+ " gpfsa_deductions,house_rent_deductions,gis_deductions,pt_deductions,it_deductions,cca_deductions, \r\n"
			+ " license_deductions,con_decd_deductions,lic_deductions,rcs_cont_deductions,sal_rec_deductions, \r\n"
			+ " cmrf_deductions,fcf_deductions,epf_l_deductions,vpf_deductions,apgliS_deductions,apgliL_deductions, \r\n"
			+ " epf_deductions,ppf_deductions,other_deductions,isdelete,timestamp,confirm_month,payment_type,year) \r\n"
			+ " (select security_id,emp_id,gpfs_deductions,gpfl_deductions,gpfsa_deductions,house_rent_deductions,gis_deductions,pt_deductions, \r\n"
			+ " it_deductions,cca_deductions,license_deductions,con_decd_deductions,lic_deductions,rcs_cont_deductions,sal_rec_deductions, \r\n"
			+ " cmrf_deductions,fcf_deductions,epf_l_deductions,vpf_deductions,apgliS_deductions,apgliL_deductions,epf_deductions,ppf_deductions, \r\n"
			+ " other_deductions,'t',now(),:confirm_month,:payment_type,:year from deductions_details where emp_id=:emp_id) ", nativeQuery = true)
	int paybillconfirm_UpdatList_deductiondetails_Confirm(String confirm_month, String payment_type, String year,
			String emp_id);
//	@Transactional
//	@Modifying
//	@Query(value = " insert into deductions_details_confirm(security_id,emp_id,gpfs_deductions,gpfl_deductions, \r\n"
//			+ " gpfsa_deductions,house_rent_deductions,gis_deductions,pt_deductions,it_deductions,cca_deductions, \r\n"
//			+ " license_deductions,con_decd_deductions,lic_deductions,rcs_cont_deductions,sal_rec_deductions, \r\n"
//			+ " cmrf_deductions,fcf_deductions,epf_l_deductions,vpf_deductions,apgliS_deductions,apgliL_deductions, \r\n"
//			+ " epf_deductions,ppf_deductions,other_deductions,isdelete,timestamp,confirm_month,payment_type,year) \r\n"
//			+ " (select security_id,emp_id,gpfs_deductions,gpfl_deductions,gpfsa_deductions,house_rent_deductions,gis_deductions,pt_deductions, \r\n"
//			+ " it_deductions,cca_deductions,license_deductions,con_decd_deductions,lic_deductions,rcs_cont_deductions,sal_rec_deductions, \r\n"
//			+ " cmrf_deductions,fcf_deductions,epf_l_deductions,vpf_deductions,apgliS_deductions,apgliL_deductions,epf_deductions,ppf_deductions, \r\n"
//			+ " other_deductions,'t',now(),confirm_month,payment_type,year from deductions_details where emp_id=:emp_id) ", nativeQuery = true)
//	int paybillconfirm_UpdatList_deductiondetails_Confirm(String emp_id);

	@Transactional
	@Modifying
	@Query(value = " insert into loan_details_confirm(security_id,emp_id,car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan, \r\n"
			+ " add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,med_a_i_loan,hcesa_loan,hcesa_I_loan,staff_pl_loan,court_loan,vij_bank_loan,mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,car_i_loanti,car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,med_a_i_loanti,hcesa_loanti,hcesa_I_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi, \r\n"
			+ " hba1_loanpi,comp_loanpi,fa_loanpi,ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_I_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,isdelete, \r\n"
			+ " timestamp,confirm_month,payment_type,year) (select security_id,emp_id,car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan,add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,med_a_i_loan,hcesa_loan,hcesa_I_loan,staff_pl_loan,court_loan,vij_bank_loan,mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,car_i_loanti,car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,med_a_i_loanti,hcesa_loanti,hcesa_I_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi,hba1_loanpi,comp_loanpi,fa_loanpi,ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_I_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,'t', \r\n"
			+ " now(),:confirm_month,:payment_type,:year from loan_details where emp_id=:emp_id )", nativeQuery = true)
	int paybillconfirm_UpdatList_loandetails_Confirm(String confirm_month, String payment_type, String year,
			String emp_id);
//	@Transactional
//	@Modifying
//	@Query(value = " insert into loan_details_confirm(security_id,emp_id,car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan, \r\n"
//			+ " add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,med_a_i_loan,hcesa_loan,hcesa_I_loan,staff_pl_loan,court_loan,vij_bank_loan,mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,car_i_loanti,car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,med_a_i_loanti,hcesa_loanti,hcesa_I_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi, \r\n"
//			+ " hba1_loanpi,comp_loanpi,fa_loanpi,ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_I_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,isdelete, \r\n"
//			+ " timestamp,confirm_month,payment_type,year) (select security_id,emp_id,car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan,add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,med_a_i_loan,hcesa_loan,hcesa_I_loan,staff_pl_loan,court_loan,vij_bank_loan,mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,car_i_loanti,car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,med_a_i_loanti,hcesa_loanti,hcesa_I_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi,hba1_loanpi,comp_loanpi,fa_loanpi,ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_I_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,'t', \r\n"
//			+ " now(),confirm_month,payment_type,year from loan_details where emp_id=:emp_id )", nativeQuery = true)
//	int paybillconfirm_UpdatList_loandetails_Confirm(String emp_id);

	@Transactional
	@Modifying
	@Query(value = "insert into os_earn_ded_details_confirm(security_id,emp_id,os_basic_pay_earnings,os_hra_earnings, \r\n"
			+ " os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions, \r\n"
			+ " os_ers_epf_deductions,os_prof_tax_deductions,os_other_deductions,os_work_place,os_location, \r\n"
			+ " isdelete,timestamp,confirm_month,payment_type,year)(select security_id,emp_id,os_basic_pay_earnings,\r\n"
			+ "os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,\r\n"
			+ "os_ers_epf_deductions,os_prof_tax_deductions,os_other_deductions,os_work_place,os_location,'t',now(), \r\n"
			+ ":confirm_month,:payment_type,:year from os_earn_ded_details where emp_id=:emp_id )", nativeQuery = true)
	int paybillconfirm_UpdatList_os_earn_ded_Confirm(String confirm_month, String payment_type, String year,
			String emp_id);

	@Transactional
	@Modifying
	@Query(value = "insert into nmr_earn_ded_details_confirm(security_id,emp_id,nmr_gross_earnings,os_ees_epf_deductions,nmr_postalrd_deductions,os_prof_tax_deductions,os_work_place,os_location,nmr_tds_deductions,\r\n"
			+ "nmr_fa_deductions,nmr_ea_deductions,nmr_ma_deductions,nmr_lic_deductions,nmr_otherliab_deductions,isdelete,timestamp,confirm_month,payment_type,year)(select security_id,emp_id,nmr_gross_earnings,\r\n"
			+ "os_ees_epf_deductions,nmr_postalrd_deductions,os_prof_tax_deductions,os_work_place,os_location,nmr_tds_deductions,nmr_fa_deductions,nmr_ea_deductions,nmr_ma_deductions,nmr_lic_deductions,nmr_otherliab_deductions,\r\n"
			+ "'t',timestamp,:confirm_month,:payment_type,:year from nmr_earn_ded_details where  emp_id=:emp_id )", nativeQuery = true)
	int paybillconfirm_UpdatList_nmr_earn_ded_Confirm(String confirm_month, String payment_type, String year,
			String emp_id);

//3rd payroll report
	@Query(value = "SELECT emp.emp_id,name,designation,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ca_earnings,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca,apglis_deductions,apglil_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,\r\n"
			+ "epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,\r\n"
			+ "mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan,ea_loan,sfa_loan,pt_deductions,mar_i_loan,it_deductions,hr_arrear_loan,med_a_i_loan,vij_bank_loan,cca_deductions,rcs_cont_deductions,cmrf_deductions,\r\n"
			+ "fcf_deductions,vpf_deductions,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,other_deductions,emp_type,confirm_month,payment_type  from (SELECT emp.emp_id,name,designation,emp_type,basic_pay_earnings,\r\n"
			+ "per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,ca_earnings,addl_hra,sca,apglis_deductions,apglil_deductions,house_rent_deductions,\r\n"
			+ "lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,pt_deductions,it_deductions,\r\n"
			+ "cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions,confirm_month,payment_type  from (SELECT emp.emp_id,name,emp_type,designation,confirm_month,payment_type,basic_pay_earnings,\r\n"
			+ "per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,ca_earnings,addl_hra,sca from (SELECT emp_id,\r\n"
			+ "prefix||''||firstname||' '||lastname||' '||surname as name,emp_type,case when designation='100' then (select designation_name from designation where designation_id=e.designation)||'-'||others else \r\n"
			+ "(select designation_name from designation where designation_id=e.designation and \r\n"
			+ "security_id=:security_id) end as designation,confirm_month,payment_type  from emp_details_confirm e where  emp_type=:emp_type and  payment_type in ('reg','supp') \r\n"
			+ "and confirm_month=:confirm_month and year=:year and generate_paybill='t'  order by e.designation )as emp join (SELECT emp_id,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,\r\n"
			+ "cca_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,ca_earnings,addl_hra,sca from earnings_details_confirm where \r\n"
			+ "security_id=:security_id and  payment_type in ('reg','supp') and year=:year and confirm_month=:confirm_month)as ear on ear.emp_id=emp.emp_id) as emp join (select emp_id,apglis_deductions,apglil_deductions,house_rent_deductions,lic_deductions,\r\n"
			+ "gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,pt_deductions,it_deductions,\r\n"
			+ "cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions  from deductions_details_confirm where \r\n"
			+ "security_id=:security_id and  payment_type in ('reg','supp') and year=:year and confirm_month=:confirm_month) ded on emp.emp_id=ded.emp_id) as emp join (select emp_id,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,\r\n"
			+ "car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,\r\n"
			+ "court_loan\r\n"
			+ "from loan_details_confirm where security_id=:security_id and payment_type in ('reg','supp') and year=:year and confirm_month=:confirm_month) as loan on emp.emp_id=loan.emp_id", nativeQuery = true)
	List<Map<String, String>> GenertedMonthlyPaybill_Report125_All(String security_id, String confirm_month,
			String year);

	@Query(value = "SELECT emp.emp_id,name,designation,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ca_earnings,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca,apglis_deductions,apglil_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,\r\n"
			+ "epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,\r\n"
			+ "mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan,ea_loan,sfa_loan,pt_deductions,mar_i_loan,it_deductions,hr_arrear_loan,med_a_i_loan,vij_bank_loan,cca_deductions,rcs_cont_deductions,cmrf_deductions,\r\n"
			+ "fcf_deductions,vpf_deductions,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,other_deductions,emp_type,confirm_month,payment_type,year  from (SELECT emp.emp_id,name,designation,emp_type,basic_pay_earnings,\r\n"
			+ "per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,ca_earnings,addl_hra,sca,apglis_deductions,apglil_deductions,house_rent_deductions,\r\n"
			+ "lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,pt_deductions,it_deductions,\r\n"
			+ "cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions,confirm_month,payment_type,year  from (SELECT emp.emp_id,name,emp_type,designation,confirm_month,year,payment_type,basic_pay_earnings,\r\n"
			+ "per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,ca_earnings,addl_hra,sca from (SELECT emp_id,\r\n"
			+ "prefix||''||firstname||' '||lastname||' '||surname as name,emp_type,case when designation='100' then (select designation_name from designation where designation_id=e.designation)||'-'||others else \r\n"
			+ "(select designation_name from designation where designation_id=e.designation and \r\n"
			+ "security_id=:security_id) end as designation,confirm_month,payment_type,year  from emp_details_confirm e where  emp_type=:emp_type and payment_type=:payment_type \r\n"
			+ "and confirm_month=:confirm_month and year=:year and generate_paybill='t'  order by e.designation )as emp join (SELECT emp_id,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,\r\n"
			+ "cca_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,ca_earnings,addl_hra,sca from earnings_details_confirm where \r\n"
			+ "security_id=:security_id and payment_type=:payment_type and year=:year and confirm_month=:confirm_month)as ear on ear.emp_id=emp.emp_id) as emp join (select emp_id,apglis_deductions,apglil_deductions,house_rent_deductions,lic_deductions,\r\n"
			+ "gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,pt_deductions,it_deductions,\r\n"
			+ "cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions  from deductions_details_confirm where \r\n"
			+ "security_id=:security_id and payment_type=:payment_type and year=:year and confirm_month=:confirm_month) ded on emp.emp_id=ded.emp_id) as emp join (select emp_id,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,\r\n"
			+ "car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,\r\n"
			+ "court_loan\r\n"
			+ "from loan_details_confirm where security_id=:security_id and payment_type in (:payment_type) and year=:year and confirm_month=:confirm_month) as loan on emp.emp_id=loan.emp_id", nativeQuery = true)
	List<Map<String, String>> GenertedMonthlyPaybill_Report125(String security_id, String emp_type, String payment_type,
			String confirm_month, String year);

	@Query(value = "SELECT emp.emp_id,emp_type,name,designation,work_place,os_location,os_basic_pay_earnings,os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions,os_prof_tax_deductions,os_other_deductions,confirm_month,payment_type from \r\n"
			+ "(SELECT emp_id,prefix||''||firstname||' '||lastname||' '||surname as name,emp_type,case when designation='100' then (select designation_name from designation where designation_id=e.designation)||'-'||others else (select designation_name from designation where designation_id=e.designation) end as designation,confirm_month,payment_type \r\n"
			+ " from emp_details_confirm e where security_id=:security_id and emp_type='3'  and payment_type=:payment_type and confirm_month=:confirm_month and year='3' and generate_paybill='t' order by designation,name)emp join(SELECT emp_id,case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else \r\n"
			+ "(select name from cgg_master_mandals where code=os_work_place \r\n"
			+ "and dist_code='01'||:security_id) end as work_place,os_location,os_basic_pay_earnings,os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions,os_prof_tax_deductions,os_other_deductions from os_earn_ded_details_confirm where \r\n"
			+ "security_id=:security_id and  payment_type=:payment_type and year=:year and confirm_month=:confirm_month) os  on os.emp_id=emp.emp_id", nativeQuery = true)
	List<Map<String, String>> GenertedMonthlyPaybill_Report3(String security_id, String payment_type,
			String confirm_month, String year);

	@Query(value = "SELECT emp.emp_id,emp_type,name,designation,work_place,os_location,nmr_gross_earnings,os_ees_epf_deductions,os_prof_tax_deductions,nmr_postalrd_deductions,nmr_tds_deductions,nmr_fa_deductions,nmr_ea_deductions,nmr_ma_deductions,nmr_lic_deductions,nmr_otherliab_deductions,confirm_month,payment_type from (SELECT emp_id,prefix||''||firstname||' '||lastname||' '||surname as name,emp_type,case when designation='100' then (select designation_name from designation where designation_id=e.designation)||'-'||others else (select designation_name from designation where designation_id=e.designation) end as designation,confirm_month,payment_type  from emp_details_confirm e where\r\n"
			+ "security_id=:security_id and emp_type='4'  and payment_type=:payment_type and confirm_month=:confirm_month and year=:year and generate_paybill='t')emp join(SELECT emp_id,case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place and dist_code='01'||:security_id) end as work_place,os_location,nmr_gross_earnings,os_ees_epf_deductions,os_prof_tax_deductions,nmr_postalrd_deductions,nmr_tds_deductions,nmr_fa_deductions,nmr_ea_deductions,nmr_ma_deductions,nmr_lic_deductions,nmr_otherliab_deductions from nmr_earn_ded_details_confirm where\r\n"
			+ "security_id=:security_id and  payment_type=:payment_type and year=:year and confirm_month=:confirm_month) os  on os.emp_id=emp.emp_id order by designation,name", nativeQuery = true)
	List<Map<String, String>> GenertedMonthlyPaybill_Report4(String security_id, String payment_type,
			String confirm_month, String year);

	// 4th payroll report
	@Query(value = "SELECT emp_id,prefix||' '||firstname||' '||lastname||' '||surname as emp_name from emp_details  where  emp_type=:emp_type order by firstname ", nativeQuery = true)
	List<Map<String, String>> getEmpNamesbyType_Apshcl(String emp_type);

	@Query(value = "SELECT emp_id,prefix||' '||firstname||' '||lastname||' '||surname as emp_name from emp_details  where  security_id=:security_id and  emp_type=:emp_type order by firstname ", nativeQuery = true)
	List<Map<String, String>> getEmpNamesbyType(String security_id, String emp_type);

	@Query(value = "SELECT emp_id,prefix||' '||firstname||' '||lastname||' '||surname as emp_name from emp_details  where  security_id=:security_id and  emp_type=cast(:emp_type as text) order by firstname ", nativeQuery = true)
	List<Map<String, String>> getEmpNamesbyTypeandDistrict(String security_id, String emp_type);

	@Query(value = "SELECT emp_id,emp.year,(select month_name from mstmonth where month_id=emp.confirm_month),earnings,deductions,loan,case when generate_paybill='t' then 'Generated' else 'Confirmed' end as status,emp.payment_type,emp.confirm_month,emp.emp_type,case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end,case when emp_type=:emp_type then'APSHCL Regular' when emp_type='2' then 'APSHCL Deputation' when emp_type='5' then 'Special Officers' end as type   from emp_details_confirm emp left join (select year,payment_type,confirm_month,sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details_confirm ea  where\r\n"
			+ "ea.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month  and ea.year=emp.year and emp.payment_type=ea.payment_type left join (select year,payment_type,confirm_month,sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions )as deductions from deductions_details_confirm d where\r\n"
			+ "d.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded\r\n"
			+ "on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and ded.year=emp.year and emp.payment_type=ded.payment_type left join (select year,payment_type,confirm_month, sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan)as loan from loan_details_confirm l where  \r\n"
			+ "year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and \r\n"
			+ "l.emp_id=:emp_id and payment_type in ('reg','supp') group by year,confirm_month,payment_type order by confirm_month)as ln on ln.confirm_month=emp.confirm_month  and emp.payment_type=ln.payment_type and ln.year=emp.year where \r\n"
			+ "emp.emp_id=:emp_id and  emp.year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and emp.year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('reg','supp') order by emp.year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> empPayParticularsReport125_All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type);

	@Query(value = "SELECT emp_id,emp.year,(select month_name from mstmonth where month_id=emp.confirm_month),earnings,deductions,loan,case when generate_paybill='t' then 'Generated' else 'Confirmed' end as status,emp.payment_type,emp.confirm_month,emp.emp_type,case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end,case when emp_type=:emp_type then'APSHCL Regular' when emp_type='2' then 'APSHCL Deputation' when emp_type='5' then 'Special Officers' end as type   from emp_details_confirm emp left join (select year,payment_type,confirm_month,sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details_confirm ea  where\r\n"
			+ "ea.emp_id=:emp_id and payment_type in ('reg') and  year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month  and ea.year=emp.year and emp.payment_type=ea.payment_type left join (select year,payment_type,confirm_month,sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions )as deductions from deductions_details_confirm d where\r\n"
			+ "d.emp_id=:emp_id and payment_type in ('reg') and  year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded\r\n"
			+ "on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and ded.year=emp.year and emp.payment_type=ded.payment_type left join (select year,payment_type,confirm_month, sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan)as loan from loan_details_confirm l where  \r\n"
			+ "year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and \r\n"
			+ "l.emp_id=:emp_id and payment_type in ('reg') group by year,confirm_month,payment_type order by confirm_month)as ln on ln.confirm_month=emp.confirm_month  and emp.payment_type=ln.payment_type and ln.year=emp.year where \r\n"
			+ "emp.emp_id=:emp_id and  emp.year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and emp.year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('reg') order by emp.year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> empPayParticularsReport125_reg(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type);

	@Query(value = "SELECT emp_id,emp.year,(select month_name from mstmonth where month_id=emp.confirm_month),earnings,deductions,loan,case when generate_paybill='t' then 'Generated' else 'Confirmed' end as status,emp.payment_type,emp.confirm_month,emp.emp_type,case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end,case when emp_type=:emp_type then'APSHCL Regular' when emp_type='2' then 'APSHCL Deputation' when emp_type='5' then 'Special Officers' end as type   from emp_details_confirm emp left join (select year,payment_type,confirm_month,sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details_confirm ea  where\r\n"
			+ "ea.emp_id=:emp_id and payment_type in ('supp') and  year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month  and ea.year=emp.year and emp.payment_type=ea.payment_type left join (select year,payment_type,confirm_month,sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions )as deductions from deductions_details_confirm d where\r\n"
			+ "d.emp_id=:emp_id and payment_type in ('supp') and  year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded\r\n"
			+ "on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and ded.year=emp.year and emp.payment_type=ded.payment_type left join (select year,payment_type,confirm_month, sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan)as loan from loan_details_confirm l where  \r\n"
			+ "year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and \r\n"
			+ "l.emp_id=:emp_id and payment_type in ('supp') group by year,confirm_month,payment_type order by confirm_month)as ln on ln.confirm_month=emp.confirm_month  and emp.payment_type=ln.payment_type and ln.year=emp.year where \r\n"
			+ "emp.emp_id=:emp_id and  emp.year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and emp.year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('supp') order by emp.year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> empPayParticularsReport125_supp(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type);

	@Query(value = "SELECT emp_id,year,(select month_name from mstmonth where month_id=emp.confirm_month),earnings,deductions,case when generate_paybill='t' then 'Generated' else 'Confirmed' end as status,emp.payment_type,emp.confirm_month,emp.emp_type,case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end,case when emp_type='3' then 'OutSourcing' end as type  from emp_details_confirm emp left join (select payment_type,confirm_month,sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf)as earnings from os_earn_ded_details_confirm os where \r\n"
			+ "os.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||os.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||os.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on  \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type left join (select payment_type,confirm_month,sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions)as deductions from os_earn_ded_details_confirm d where \r\n"
			+ "d.emp_id=:emp_id and payment_type in ('reg','supp')  and  year||'-'||d.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and  year||'-'||ded.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ded.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type   where  \r\n"
			+ "emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('reg','supp')  order by year,emp.confirm_month,generate_paybill", nativeQuery = true)

	List<Map<String, String>> empPayParticluarsReport3_All(String emp_id, String emp_type, String fromMonth,
			String toMonth, String fromYear, String toYear);

	@Query(value = "SELECT emp_id,year,(select month_name from mstmonth where month_id=emp.confirm_month),earnings,deductions,case when generate_paybill='t' then 'Generated' else 'Confirmed' end as status,emp.payment_type,emp.confirm_month,emp.emp_type,case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end,case when emp_type='3' then 'OutSourcing' end as type  from emp_details_confirm emp left join (select payment_type,confirm_month,sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf)as earnings from os_earn_ded_details_confirm os where \r\n"
			+ "os.emp_id=:emp_id and payment_type in ('reg') and  year||'-'||os.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||os.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on  \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type left join (select payment_type,confirm_month,sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions)as deductions from os_earn_ded_details_confirm d where \r\n"
			+ "d.emp_id=:emp_id and payment_type in ('reg')  and  year||'-'||d.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and  year||'-'||ded.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ded.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type   where  \r\n"
			+ "emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('reg')  order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> empPayParticluarsReport3_reg(String emp_id, String emp_type, String fromMonth,
			String toMonth, String fromYear, String toYear);

	@Query(value = "SELECT emp_id,year,(select month_name from mstmonth where month_id=emp.confirm_month),earnings,deductions,case when generate_paybill='t' then 'Generated' else 'Confirmed' end as status,emp.payment_type,emp.confirm_month,emp.emp_type,case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end,case when emp_type='3' then 'OutSourcing' end as type  from emp_details_confirm emp left join (select payment_type,confirm_month,sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf)as earnings from os_earn_ded_details_confirm os where \r\n"
			+ "os.emp_id=:emp_id and payment_type in ('supp') and  year||'-'||os.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||os.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on  \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type left join (select payment_type,confirm_month,sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions)as deductions from os_earn_ded_details_confirm d where \r\n"
			+ "d.emp_id=:emp_id and payment_type in ('supp')  and  year||'-'||d.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and  year||'-'||ded.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ded.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type   where  \r\n"
			+ "emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('supp')  order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> empPayParticluarsReport3_supp(String emp_id, String emp_type, String fromMonth,
			String toMonth, String fromYear, String toYear);

	@Query(value = "SELECT emp_id,year,(select month_name from mstmonth where month_id=emp.confirm_month),earnings,deductions,case when generate_paybill='t' then 'Generated' else 'Confirmed' end as status,emp.payment_type,emp.confirm_month,emp.emp_type,case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end,case when emp_type='4' then 'NMR/Min. Emoluments' end as type from emp_details_confirm emp left join (select payment_type,confirm_month,sum(nmr_gross_earnings)as earnings from nmr_earn_ded_details_confirm nmr where\r\n"
			+ "nmr.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||nmr.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||nmr.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type  left join (select payment_type,confirm_month,sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions)as deductions from nmr_earn_ded_details_confirm d where\r\n"
			+ "d.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||d.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on\r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and  year||'-'||ded.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ded.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type where \r\n"
			+ "emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') and\r\n"
			+ "emp.emp_type=:emp_type and emp.payment_type in ('reg','supp') order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> empPayParticluarsReport4_All(String emp_id, String emp_type, String fromMonth,
			String toMonth, String fromYear, String toYear);

	@Query(value = "SELECT emp_id,year,(select month_name from mstmonth where month_id=emp.confirm_month),earnings,deductions,case when generate_paybill='t' then 'Generated' else 'Confirmed' end as status,emp.payment_type,emp.confirm_month,emp.emp_type,case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end,case when emp_type='4' then 'NMR/Min. Emoluments' end as type  from emp_details_confirm emp left join (select payment_type,confirm_month,sum(nmr_gross_earnings)as earnings from nmr_earn_ded_details_confirm nmr where\r\n"
			+ "nmr.emp_id=:emp_id and payment_type in ('reg') and  year||'-'||nmr.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||nmr.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type  left join (select payment_type,confirm_month,sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions)as deductions from nmr_earn_ded_details_confirm d where\r\n"
			+ "d.emp_id=:emp_id and payment_type in ('reg') and  year||'-'||d.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on\r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and  year||'-'||ded.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ded.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type where \r\n"
			+ "emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') and\r\n"
			+ "emp.emp_type=:emp_type and emp.payment_type in ('reg') order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> empPayParticluarsReport4_reg(String emp_id, String emp_type, String fromMonth,
			String toMonth, String fromYear, String toYear);

	@Query(value = "SELECT emp_id,year,(select month_name from mstmonth where month_id=emp.confirm_month),earnings,deductions,case when generate_paybill='t' then 'Generated' else 'Confirmed' end as status,emp.payment_type,emp.confirm_month,emp.emp_type,case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end,case when emp_type='4' then 'NMR/Min. Emoluments' end as type  from emp_details_confirm emp left join (select payment_type,confirm_month,sum(nmr_gross_earnings)as earnings from nmr_earn_ded_details_confirm nmr where\r\n"
			+ "nmr.emp_id=:emp_id and payment_type in ('supp') and  year||'-'||nmr.confirm_month||'%' >=(:fromYear||'-'||:fromMonth||'%') and year||'-'||nmr.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type  left join (select payment_type,confirm_month,sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions)as deductions from nmr_earn_ded_details_confirm d where\r\n"
			+ "d.emp_id=:emp_id and payment_type in ('supp') and  year||'-'||d.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on\r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and  year||'-'||ded.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||ded.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type where \r\n"
			+ "emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%'>=(:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%'>=(:toYear||'-'||:toMonth||'%') and\r\n"
			+ "emp.emp_type=:emp_type and emp.payment_type in ('supp') order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> empPayParticluarsReport4_supp(String emp_id, String emp_type, String fromMonth,
			String toMonth, String fromYear, String toYear);

	@Query(value = "SELECT case when emp.payment_type=:payment_type then 'Regular' else 'Supplementary' end as payment_type,basic_pay_earnings,ea.per_pay_earnings,ea.da_earnings,ea.hra_earnings,ea.cca_earnings,ca_earnings,spl_pay_earnings,gp_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca,\r\n"
			+ "it_deductions,pt_deductions,lic_deductions,epf_deductions,apglis_deductions,apglil_deductions,house_rent_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,ppf_deductions,\r\n"
			+ "epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions,\r\n"
			+ "car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan,add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,\r\n"
			+ "med_a_i_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,vij_bank_loan,\r\n"
			+ "mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,isdelete,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi,hba1_loanpi,comp_loanpi,fa_loanpi,\r\n"
			+ "ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_i_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,car_i_loanti,\r\n"
			+ "car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,\r\n"
			+ "med_a_i_loanti,hcesa_loanti,hcesa_i_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti,emp_id,emp.year,\r\n"
			+ "(select month_name from mstmonth where month_id=emp.confirm_month),total_earnings,total_deductions,total_loan from emp_details_confirm emp \r\n"
			+ "left join (select year,payment_type,confirm_month,basic_pay_earnings,per_pay_earnings,da_earnings,hra_earnings,cca_earnings,ca_earnings,spl_pay_earnings,\r\n"
			+ "gp_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca,sum(basic_pay_earnings+ per_pay_earnings+da_earnings+hra_earnings+cca_earnings+ca_earnings+\r\n"
			+ "spl_pay_earnings+gp_earnings+spl_all+ir_earnings+medical_earnings+misc_h_c+addl_hra+sca)as total_earnings from earnings_details_confirm ea  where \r\n"
			+ "ea.emp_id=:emp_id and payment_type in (:payment_type) and  year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type,\r\n"
			+ "basic_pay_earnings,ea.per_pay_earnings,ea.da_earnings,ea.hra_earnings,ea.cca_earnings,ca_earnings,spl_pay_earnings,gp_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca order by confirm_month)ea on \r\n"
			+ "emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month  and ea.year=emp.year and emp.payment_type=ea.payment_type\r\n"
			+ "left join (select year,payment_type,confirm_month,it_deductions,pt_deductions,lic_deductions,epf_deductions,apglis_deductions,apglil_deductions,house_rent_deductions,gpfs_deductions,\r\n"
			+ "gpfl_deductions,gpfsa_deductions,con_decd_deductions,ppf_deductions,\r\n"
			+ "epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions,\r\n"
			+ "sum(it_deductions+pt_deductions+lic_deductions+epf_deductions+apglis_deductions+apglil_deductions+house_rent_deductions+gpfs_deductions+\r\n"
			+ "gpfl_deductions+gpfsa_deductions+con_decd_deductions+ppf_deductions+epf_l_deductions+license_deductions+\r\n"
			+ "gis_deductions+sal_rec_deductions+cca_deductions+rcs_cont_deductions+cmrf_deductions+fcf_deductions+vpf_deductions+other_deductions )as total_deductions from deductions_details_confirm d where \r\n"
			+ "d.emp_id=:emp_id and payment_type in (:payment_type) and  year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') \r\n"
			+ "group by year,confirm_month,payment_type,it_deductions,pt_deductions,lic_deductions,epf_deductions,apglis_deductions,apglil_deductions,house_rent_deductions,gpfs_deductions,\r\n"
			+ "gpfl_deductions,gpfsa_deductions,con_decd_deductions,ppf_deductions,\r\n"
			+ "epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions\r\n"
			+ "order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and ded.year=emp.year and emp.payment_type=ded.payment_type \r\n"
			+ "left join (select year,payment_type,confirm_month,car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan,add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,\r\n"
			+ "med_a_i_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,vij_bank_loan,\r\n"
			+ "mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi,hba1_loanpi,comp_loanpi,fa_loanpi,\r\n"
			+ "ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_i_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,car_i_loanti,\r\n"
			+ "car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,\r\n"
			+ "med_a_i_loanti,hcesa_loanti,hcesa_i_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti, sum( car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+\r\n"
			+ "hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan+car_i_loanpi+car_a_loanpi+cyc_i_loanpi+cyc_a_loanpi+mca_i_loanpi+mca_a_loanpi+mar_a_loanpi+med_a_loanpi+\r\n"
			+ "hba_loanpi+hba1_loanpi+comp_loanpi+fa_loanpi+ea_loanpi+cell_loanpi+add_hba_loanpi+add_hba1_loanpi+sal_adv_loanpi+sfa_loanpi+med_a_i_loanpi+hcesa_loanpi+hcesa_i_loanpi+staff_pl_loanpi+court_loanpi+vij_bank_loanpi+\r\n"
			+ "mar_i_loanpi+hr_arrear_loanpi+hbao_loanpi+comp1_loanpi+car_i_loanti+car_a_loanti+cyc_i_loanti+cyc_a_loanti+mca_i_loanti+mca_a_loanti+mar_a_loanti+med_a_loanti+hba_loanti+hba1_loanti+comp_loanti+fa_loanti+ea_loanti+\r\n"
			+ "cell_loanti+add_hba_loanti+add_hba1_loanti+sal_adv_loanti+sfa_loanti+med_a_i_loanti+hcesa_loanti+hcesa_i_loanti+staff_pl_loanti+court_loanti+vij_bank_loanti+mar_i_loanti+hr_arrear_loanti+hbao_loanti+comp1_loanti)as total_loan from loan_details_confirm l where  \r\n"
			+ "year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and \r\n"
			+ "l.emp_id=:emp_id and payment_type in (:payment_type) group by year,confirm_month,payment_type,car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan,add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,\r\n"
			+ "med_a_i_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,vij_bank_loan,\r\n"
			+ "mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,isdelete,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi,hba1_loanpi,comp_loanpi,fa_loanpi,\r\n"
			+ "ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_i_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,car_i_loanti,\r\n"
			+ "car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,\r\n"
			+ "med_a_i_loanti,hcesa_loanti,hcesa_i_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti\r\n"
			+ "order by confirm_month)as ln on ln.confirm_month=emp.confirm_month  and emp.payment_type=ln.payment_type and ln.year=emp.year where \r\n"
			+ "emp.emp_id=:emp_id and  emp.year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and emp.year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in (:payment_type)  \r\n"
			+ "order by emp.year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> Emp_pay_values(String emp_id, String emp_type, String fromMonth, String toMonth,
			String fromYear, String toYear, String payment_type);

	@Query(value = "SELECT case when emp.payment_type='reg' then 'Regular' else 'Supplementary' end as payment_type,basic_pay_earnings,ea.per_pay_earnings,ea.da_earnings,ea.hra_earnings,ea.cca_earnings,ca_earnings,spl_pay_earnings,gp_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca,\r\n"
			+ "it_deductions,pt_deductions,lic_deductions,epf_deductions,apglis_deductions,apglil_deductions,house_rent_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,ppf_deductions,\r\n"
			+ "epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions,\r\n"
			+ "car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan,add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,\r\n"
			+ "med_a_i_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,vij_bank_loan,\r\n"
			+ "mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi,hba1_loanpi,comp_loanpi,fa_loanpi,\r\n"
			+ "ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_i_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,car_i_loanti,\r\n"
			+ "car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,\r\n"
			+ "med_a_i_loanti,hcesa_loanti,hcesa_i_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti,emp_id,emp.year,\r\n"
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
			+ "left join (select year,payment_type,confirm_month,car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan,add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,\r\n"
			+ "med_a_i_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,vij_bank_loan,\r\n"
			+ "mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi,hba1_loanpi,comp_loanpi,fa_loanpi,\r\n"
			+ "ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_i_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,car_i_loanti,\r\n"
			+ "car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,\r\n"
			+ "med_a_i_loanti,hcesa_loanti,hcesa_i_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti,sum( car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+\r\n"
			+ "hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+hbao_loan+comp1_loan+car_i_loanpi+car_a_loanpi+cyc_i_loanpi+cyc_a_loanpi+mca_i_loanpi+mca_a_loanpi+mar_a_loanpi+med_a_loanpi+\r\n"
			+ "hba_loanpi+hba1_loanpi+comp_loanpi+fa_loanpi+ea_loanpi+cell_loanpi+add_hba_loanpi+add_hba1_loanpi+sal_adv_loanpi+sfa_loanpi+med_a_i_loanpi+hcesa_loanpi+hcesa_i_loanpi+staff_pl_loanpi+court_loanpi+vij_bank_loanpi+\r\n"
			+ "mar_i_loanpi+hr_arrear_loanpi+hbao_loanpi+comp1_loanpi+car_i_loanti+car_a_loanti+cyc_i_loanti+cyc_a_loanti+mca_i_loanti+mca_a_loanti+mar_a_loanti+med_a_loanti+hba_loanti+hba1_loanti+comp_loanti+fa_loanti+ea_loanti+\r\n"
			+ "cell_loanti+add_hba_loanti+add_hba1_loanti+sal_adv_loanti+sfa_loanti+med_a_i_loanti+hcesa_loanti+hcesa_i_loanti+staff_pl_loanti+court_loanti+vij_bank_loanti+mar_i_loanti+hr_arrear_loanti+hbao_loanti+comp1_loanti)as total_loan from loan_details_confirm l where  \r\n"
			+ "year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and \r\n"
			+ "l.emp_id=:emp_id and payment_type in ('reg','supp') group by year,confirm_month,payment_type,car_i_loan,car_a_loan,cyc_i_loan,cyc_a_loan,mca_i_loan,mca_a_loan,mar_a_loan,med_a_loan,hba_loan,hba1_loan,comp_loan,fa_loan,ea_loan,cell_loan,add_hba_loan,add_hba1_loan,sal_adv_loan,sfa_loan,\r\n"
			+ "med_a_i_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,vij_bank_loan,\r\n"
			+ "mar_i_loan,hr_arrear_loan,hbao_loan,comp1_loan,car_i_loanpi,car_a_loanpi,cyc_i_loanpi,cyc_a_loanpi,mca_i_loanpi,mca_a_loanpi,mar_a_loanpi,med_a_loanpi,hba_loanpi,hba1_loanpi,comp_loanpi,fa_loanpi,\r\n"
			+ "ea_loanpi,cell_loanpi,add_hba_loanpi,add_hba1_loanpi,sal_adv_loanpi,sfa_loanpi,med_a_i_loanpi,hcesa_loanpi,hcesa_i_loanpi,staff_pl_loanpi,court_loanpi,vij_bank_loanpi,mar_i_loanpi,hr_arrear_loanpi,hbao_loanpi,comp1_loanpi,car_i_loanti,\r\n"
			+ "car_a_loanti,cyc_i_loanti,cyc_a_loanti,mca_i_loanti,mca_a_loanti,mar_a_loanti,med_a_loanti,hba_loanti,hba1_loanti,comp_loanti,fa_loanti,ea_loanti,cell_loanti,add_hba_loanti,add_hba1_loanti,sal_adv_loanti,sfa_loanti,\r\n"
			+ "med_a_i_loanti,hcesa_loanti,hcesa_i_loanti,staff_pl_loanti,court_loanti,vij_bank_loanti,mar_i_loanti,hr_arrear_loanti,hbao_loanti,comp1_loanti \r\n"
			+ "order by confirm_month)as ln on ln.confirm_month=emp.confirm_month  and emp.payment_type=ln.payment_type and ln.year=emp.year where \r\n"
			+ "emp.emp_id=:emp_id and  emp.year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and emp.year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('reg','supp')  \r\n"
			+ "order by emp.year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, String>> Emp_pay_values_All(String emp_id, String emp_type, String fromMonth, String toMonth,
			String fromYear, String toYear);
	// ----ViewDetailedPayParticular

	@Query(value = "SELECT emp_id,(Select prefix||' '||firstname||' '||lastname||' '||surname as emp_name from emp_details_confirm where emp_id=emp.emp_id limit 1),year,(select month_name from mstmonth where month_id=emp.confirm_month),emp.payment_type, basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ca_earnings,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca,apglis_deductions,apglil_deductions,it_deductions, pt_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions, cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan, car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,emp.emp_type,emp.confirm_month from emp_details_confirm emp left join (select payment_type,confirm_month,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ca_earnings,ir_earnings,medical_earnings, misc_h_c,addl_hra,sca from earnings_details_confirm ea where ea.emp_id=:emp_id and payment_type in (:payment_type) and year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') order by confirm_month)ea on emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type left join (select payment_type,confirm_month,apglis_deductions,apglil_deductions,it_deductions,pt_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions, ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions from deductions_details_confirm d where d.emp_id=:emp_id and payment_type in (:payment_type) and year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type left join (select payment_type,confirm_month,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan, ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan from loan_details_confirm l where year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and l.emp_id=:emp_id and payment_type in (:payment_type) order by confirm_month)as ln on ln.confirm_month=emp.confirm_month and emp.payment_type=ln.payment_type where emp.emp_id=:emp_id and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in (:payment_type) order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulardata125(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type, String payment_type);

	@Query(value = "SELECT sum(basic_pay_earnings) AS sum_basic_pay_earnings,sum(per_pay_earnings) AS sum_per_pay_earnings,sum(spl_pay_earnings) AS sum_spl_pay_earnings,sum(gp_earnings) AS sum_gp_earnings, sum(da_earnings) AS sum_da_earnings,sum(hra_earnings) AS sum_hra_earnings,sum(cca_earnings) AS sum_cca_earnings,sum(spl_all) AS sum_spl_all,sum(ca_earnings) AS sum_ca_earnings,sum(ir_earnings) AS sum_ir_earnings, sum(medical_earnings) AS sum_medical_earnings,sum(misc_h_c) AS sum_misc_h_c,sum(addl_hra) AS sum_addl_hra,sum(sca) AS sum_sca,sum(basic_pay_earnings + per_pay_earnings + spl_pay_earnings + da_earnings + hra_earnings + cca_earnings + gp_earnings + ir_earnings + medical_earnings + ca_earnings + spl_all + misc_h_c + addl_hra + sca) AS sum_total_earnings, sum(apglis_deductions) AS sum_apglis_deductions,sum(apglil_deductions) AS sum_apglil_deductions,sum(it_deductions) AS sum_it_deductions,sum(pt_deductions) AS sum_pt_deductions,sum(house_rent_deductions) AS sum_house_rent_deductions, sum(lic_deductions) AS sum_lic_deductions,sum(gpfs_deductions) AS sum_gpfs_deductions,sum(gpfl_deductions) AS sum_gpfl_deductions,sum(gpfsa_deductions) AS sum_gpfsa_deductions, sum(con_decd_deductions) AS sum_con_decd_deductions,sum(epf_deductions) AS sum_epf_deductions,sum(ppf_deductions) AS sum_ppf_deductions,sum(epf_l_deductions) AS sum_epf_l_deductions,sum(license_deductions) AS sum_license_deductions, sum(gis_deductions) AS sum_gis_deductions, sum(sal_rec_deductions) AS sum_sal_rec_deductions, sum(cca_deductions) AS sum_cca_deductions, sum(rcs_cont_deductions) AS sum_rcs_cont_deductions, sum(cmrf_deductions) AS sum_cmrf_deductions, sum(fcf_deductions) AS sum_fcf_deductions, sum(vpf_deductions) AS sum_vpf_deductions, sum(other_deductions) AS sum_other_deductions, sum(apglis_deductions + apglil_deductions + it_deductions + pt_deductions + house_rent_deductions + lic_deductions + gpfs_deductions + gpfl_deductions + gpfsa_deductions + con_decd_deductions + epf_deductions + ppf_deductions + epf_l_deductions + license_deductions + gis_deductions + sal_rec_deductions + cca_deductions + rcs_cont_deductions + cmrf_deductions + fcf_deductions + vpf_deductions + other_deductions) AS sum_total_deductions, sum(cell_loan) AS sum_cell_loan, sum(comp_loan) AS sum_comp_loan, sum(hba_loan) AS sum_hba_loan, sum(add_hba_loan) AS sum_add_hba_loan, sum(hbao_loan) AS sum_hbao_loan, sum(comp1_loan) AS sum_comp1_loan, sum(hba1_loan) AS sum_hba1_loan, sum(add_hba1_loan) AS sum_add_hba1_loan, sum(car_a_loan) AS sum_car_a_loan, sum(cyc_a_loan) AS sum_cyc_a_loan, sum(mca_a_loan) AS sum_mca_a_loan, sum(mar_a_loan) AS sum_mar_a_loan, sum(car_i_loan) AS sum_car_i_loan, sum(cyc_i_loan) AS sum_cyc_i_loan, sum(mca_i_loan) AS sum_mca_i_loan, sum(med_a_loan) AS sum_med_a_loan, sum(fa_loan) AS sum_fa_loan, sum(ea_loan) AS sum_ea_loan, sum(sfa_loan) AS sum_sfa_loan, sum(mar_i_loan) AS sum_mar_i_loan, sum(hr_arrear_loan) AS sum_hr_arrear_loan, sum(med_a_i_loan) AS sum_med_a_i_loan, sum(vij_bank_loan) AS sum_vij_bank_loan, sum(sal_adv_loan) AS sum_sal_adv_loan, sum(hcesa_loan) AS sum_hcesa_loan, sum(hcesa_i_loan) AS sum_hcesa_i_loan, sum(staff_pl_loan) AS sum_staff_pl_loan, sum(court_loan) AS sum_court_loan, ((sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+ misc_h_c+addl_hra+sca))-(sum(apglis_deductions+apglil_deductions+it_deductions+pt_deductions+house_rent_deductions+lic_deductions+gpfs_deductions+gpfl_deductions+gpfsa_deductions+con_decd_deductions+epf_deductions+ppf_deductions+ epf_l_deductions+license_deductions+gis_deductions+sal_rec_deductions+cca_deductions+rcs_cont_deductions+cmrf_deductions+fcf_deductions+vpf_deductions+other_deductions)+sum(cell_loan+comp_loan+hba_loan+add_hba_loan+hbao_loan+ comp1_loan+hba1_loan+add_hba1_loan+car_a_loan+cyc_a_loan+mca_a_loan+mar_a_loan+car_i_loan+cyc_i_loan+mca_i_loan+med_a_loan+fa_loan+ea_loan+sfa_loan+mar_i_loan+hr_arrear_loan+med_a_i_loan+vij_bank_loan+sal_adv_loan+hcesa_loan+ hcesa_i_loan+staff_pl_loan+court_loan))) from emp_details_confirm emp left join (select payment_type,confirm_month,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all, ca_earnings,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca from earnings_details_confirm ea where ea.emp_id=:emp_id and payment_type in (:payment_type) and year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') order by confirm_month)ea on emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type left join (select payment_type,confirm_month,apglis_deductions,apglil_deductions,it_deductions,pt_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions, ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions from deductions_details_confirm d where d.emp_id=:emp_id and payment_type in (:payment_type) and year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type left join (select payment_type,confirm_month,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan, fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan from loan_details_confirm l where year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and l.emp_id=:emp_id and payment_type in (:payment_type) order by confirm_month)as ln on ln.confirm_month=emp.confirm_month and emp.payment_type=ln.payment_type where emp.emp_id=:emp_id and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in (:payment_type)", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticularsumdata125(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type, String payment_type);

	@Query(value = "SELECT 'Basic pay' as BasicPay, 'Personal Pay' as PersonalPay, 'Special Pay' as SpecialPay, 'GP' as GP, 'DA' as DA, 'HRA' as HRA, 'CCA' as CCA,'Special Allowance' as SpecialAllowance, 'CA' as CA,'IR' as IR,'Medical' as Medical,'Misc(H.C)' as MiscHC,'Addl.HRA' as AddlHRA, 'SCA' as SCA,'Earnings Total' as EarningsTotal, 'APGLI(S)' as APGLIS,'APGLI(L)' as APGIL,'IT' as IT,'PT' as PT,'House Rent' as HouseRent,'LIC' as LIC,'GPF(S)' as GPFS,'GPF(L)' as GPFL,'GPF(Addl.S)' as GPFAddlS,'Con.Ded.' as ConDed, 'EPF' as EPF,'PPF' as PPF,'EPF(L)' as EPFL,'License' as License,'GIS' as GIS,'Sal.Ded.' as SalDed,'CCA' as CCADed, 'RCS Cont' as RCSCont,'CMRF' as CMRF,'FCF' as FCF,'VPF' as VPF,'Other Deductions' as OtherDeductions,'Deduction Total' as DeductionTotal,'Phone' as Phone,'Comp. Loan' as CompLoan,'HBA' as HBA,'Addl. HBA' as AddlHBA, 'HBA(Other Dept.)' as HBAOtherDept,'Computer(I)' as ComputerI, 'HBA(I)' as HBAI,'Addl. HBA(I)' as AddlHBAI,'Car' as Car,'Cycle' as Cycle, 'MCA' as MCA,'MAR' as MAR,'Car(I)' as CarI,'Cycle(I)' as CycleI, 'MCA(I)' as MCAI,'MED(P)' as MEDP,'FA' as FA,'EA' as EA,'Spl.FA' as SplFA,'MAR(I)' as MARI,'HR Arrear' as HRArrear,'MED(I)' as MEDI, 'Bank Personal Loan' as BankPersonalLoan,'Sal Adv.' as SalAdv,'HCESA' as HCESA,'HCESA(I)' as HCESAI,'Staff PL' as StaffPL,'Court Loan' as CourtLoan,'Loan Total' as LoanTotal;", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticularheading125();

	@Query(value = "SELECT earnings,deductions,loan,(earnings)-(deductions+loan) netsalary from emp_details_confirm emp left join (select payment_type,confirm_month,sum(basic_pay_earnings+per_pay_earnings+ spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+ gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details_confirm ea where ea.emp_id=:emp_id and payment_type in (:payment_type) and year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type left join (select payment_type,confirm_month,sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+ house_rent_deductions+ gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+ epf_l_deductions+vpf_deductions+apglis_deductions+ apglil_deductions+epf_deductions+ppf_deductions+other_deductions )as deductions from deductions_details_confirm d where d.emp_id=:emp_id and payment_type in (:payment_type) and year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type left join (select payment_type,confirm_month,sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+ mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+ea_loan+cell_loan+add_hba_loan+add_hba1_loan+ sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+ hbao_loan+comp1_loan)as loan from loan_details_confirm l where year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and l.emp_id=:emp_id and payment_type in (:payment_type) group by year,confirm_month, payment_type order by confirm_month)as ln on ln.confirm_month=emp.confirm_month and emp.payment_type=ln.payment_type where emp.emp_id=:emp_id and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in (:payment_type) order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulartotalsum125(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type, String payment_type);
	// All

	@Query(value = "SELECT emp_id,(Select prefix||' '||firstname||' '||lastname||' '||surname from emp_details_confirm where emp_id=emp.emp_id limit 1),year,(select month_name from mstmonth where month_id=emp.confirm_month),emp.payment_type, basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ca_earnings,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca,apglis_deductions,apglil_deductions,it_deductions, pt_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions, cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan, car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,emp.emp_type,emp.confirm_month from emp_details_confirm emp left join (select payment_type,confirm_month,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ca_earnings,ir_earnings,medical_earnings, misc_h_c,addl_hra,sca from earnings_details_confirm ea where ea.emp_id=:emp_id and payment_type in ('reg','supp') and year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') order by confirm_month)ea on emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type left join (select payment_type,confirm_month,apglis_deductions,apglil_deductions,it_deductions,pt_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions, ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions from deductions_details_confirm d where d.emp_id=:emp_id and payment_type in ('reg','supp') and year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type left join (select payment_type,confirm_month,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan, ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan from loan_details_confirm l where year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and l.emp_id=:emp_id and payment_type in ('reg','supp') order by confirm_month)as ln on ln.confirm_month=emp.confirm_month and emp.payment_type=ln.payment_type where emp.emp_id=:emp_id and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('reg','supp') order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulardata125All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type);

	@Query(value = "SELECT sum(basic_pay_earnings) AS sum_basic_pay_earnings,sum(per_pay_earnings) AS sum_per_pay_earnings,sum(spl_pay_earnings) AS sum_spl_pay_earnings,sum(gp_earnings) AS sum_gp_earnings, sum(da_earnings) AS sum_da_earnings,sum(hra_earnings) AS sum_hra_earnings,sum(cca_earnings) AS sum_cca_earnings,sum(spl_all) AS sum_spl_all,sum(ca_earnings) AS sum_ca_earnings,sum(ir_earnings) AS sum_ir_earnings, sum(medical_earnings) AS sum_medical_earnings,sum(misc_h_c) AS sum_misc_h_c,sum(addl_hra) AS sum_addl_hra,sum(sca) AS sum_sca,sum(basic_pay_earnings + per_pay_earnings + spl_pay_earnings + da_earnings + hra_earnings + cca_earnings + gp_earnings + ir_earnings + medical_earnings + ca_earnings + spl_all + misc_h_c + addl_hra + sca) AS sum_total_earnings, sum(apglis_deductions) AS sum_apglis_deductions,sum(apglil_deductions) AS sum_apglil_deductions,sum(it_deductions) AS sum_it_deductions,sum(pt_deductions) AS sum_pt_deductions,sum(house_rent_deductions) AS sum_house_rent_deductions, sum(lic_deductions) AS sum_lic_deductions,sum(gpfs_deductions) AS sum_gpfs_deductions,sum(gpfl_deductions) AS sum_gpfl_deductions,sum(gpfsa_deductions) AS sum_gpfsa_deductions, sum(con_decd_deductions) AS sum_con_decd_deductions,sum(epf_deductions) AS sum_epf_deductions,sum(ppf_deductions) AS sum_ppf_deductions,sum(epf_l_deductions) AS sum_epf_l_deductions,sum(license_deductions) AS sum_license_deductions, sum(gis_deductions) AS sum_gis_deductions, sum(sal_rec_deductions) AS sum_sal_rec_deductions, sum(cca_deductions) AS sum_cca_deductions, sum(rcs_cont_deductions) AS sum_rcs_cont_deductions, sum(cmrf_deductions) AS sum_cmrf_deductions, sum(fcf_deductions) AS sum_fcf_deductions, sum(vpf_deductions) AS sum_vpf_deductions, sum(other_deductions) AS sum_other_deductions, sum(apglis_deductions + apglil_deductions + it_deductions + pt_deductions + house_rent_deductions + lic_deductions + gpfs_deductions + gpfl_deductions + gpfsa_deductions + con_decd_deductions + epf_deductions + ppf_deductions + epf_l_deductions + license_deductions + gis_deductions + sal_rec_deductions + cca_deductions + rcs_cont_deductions + cmrf_deductions + fcf_deductions + vpf_deductions + other_deductions) AS sum_total_deductions, sum(cell_loan) AS sum_cell_loan, sum(comp_loan) AS sum_comp_loan, sum(hba_loan) AS sum_hba_loan, sum(add_hba_loan) AS sum_add_hba_loan, sum(hbao_loan) AS sum_hbao_loan, sum(comp1_loan) AS sum_comp1_loan, sum(hba1_loan) AS sum_hba1_loan, sum(add_hba1_loan) AS sum_add_hba1_loan, sum(car_a_loan) AS sum_car_a_loan, sum(cyc_a_loan) AS sum_cyc_a_loan, sum(mca_a_loan) AS sum_mca_a_loan, sum(mar_a_loan) AS sum_mar_a_loan, sum(car_i_loan) AS sum_car_i_loan, sum(cyc_i_loan) AS sum_cyc_i_loan, sum(mca_i_loan) AS sum_mca_i_loan, sum(med_a_loan) AS sum_med_a_loan, sum(fa_loan) AS sum_fa_loan, sum(ea_loan) AS sum_ea_loan, sum(sfa_loan) AS sum_sfa_loan, sum(mar_i_loan) AS sum_mar_i_loan, sum(hr_arrear_loan) AS sum_hr_arrear_loan, sum(med_a_i_loan) AS sum_med_a_i_loan, sum(vij_bank_loan) AS sum_vij_bank_loan, sum(sal_adv_loan) AS sum_sal_adv_loan, sum(hcesa_loan) AS sum_hcesa_loan, sum(hcesa_i_loan) AS sum_hcesa_i_loan, sum(staff_pl_loan) AS sum_staff_pl_loan, sum(court_loan) AS sum_court_loan, ((sum(basic_pay_earnings+per_pay_earnings+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+ misc_h_c+addl_hra+sca))-(sum(apglis_deductions+apglil_deductions+it_deductions+pt_deductions+house_rent_deductions+lic_deductions+gpfs_deductions+gpfl_deductions+gpfsa_deductions+con_decd_deductions+epf_deductions+ppf_deductions+ epf_l_deductions+license_deductions+gis_deductions+sal_rec_deductions+cca_deductions+rcs_cont_deductions+cmrf_deductions+fcf_deductions+vpf_deductions+other_deductions)+sum(cell_loan+comp_loan+hba_loan+add_hba_loan+hbao_loan+ comp1_loan+hba1_loan+add_hba1_loan+car_a_loan+cyc_a_loan+mca_a_loan+mar_a_loan+car_i_loan+cyc_i_loan+mca_i_loan+med_a_loan+fa_loan+ea_loan+sfa_loan+mar_i_loan+hr_arrear_loan+med_a_i_loan+vij_bank_loan+sal_adv_loan+hcesa_loan+ hcesa_i_loan+staff_pl_loan+court_loan))) from emp_details_confirm emp left join (select payment_type,confirm_month,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all, ca_earnings,ir_earnings,medical_earnings,misc_h_c,addl_hra,sca from earnings_details_confirm ea where ea.emp_id=:emp_id and payment_type in ('reg','supp') and year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') order by confirm_month)ea on emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type left join (select payment_type,confirm_month,apglis_deductions,apglil_deductions,it_deductions,pt_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions, ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,other_deductions from deductions_details_confirm d where d.emp_id=:emp_id and payment_type in ('reg','supp') and year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type left join (select payment_type,confirm_month,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan, fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan from loan_details_confirm l where year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and l.emp_id=:emp_id and payment_type in ('reg','supp') order by confirm_month)as ln on ln.confirm_month=emp.confirm_month and emp.payment_type=ln.payment_type where emp.emp_id=:emp_id and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('reg','supp')", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticularsumdata125All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type);

	@Query(value = "SELECT earnings,deductions,loan,(earnings)-(deductions+loan) netsalary from emp_details_confirm emp left join (select payment_type,confirm_month,sum(basic_pay_earnings+per_pay_earnings+ spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+ gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details_confirm ea where ea.emp_id=:emp_id and payment_type in ('reg','supp') and year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ea on emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ea.payment_type left join (select payment_type,confirm_month,sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+ house_rent_deductions+ gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+ epf_l_deductions+vpf_deductions+apglis_deductions+ apglil_deductions+epf_deductions+ppf_deductions+other_deductions )as deductions from deductions_details_confirm d where d.emp_id=:emp_id and payment_type in ('reg','supp') and year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=ded.payment_type left join (select payment_type,confirm_month,sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+ mar_a_loan+med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+ea_loan+cell_loan+add_hba_loan+add_hba1_loan+ sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+ hbao_loan+comp1_loan)as loan from loan_details_confirm l where year||'-'||l.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||l.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and l.emp_id=:emp_id and payment_type in ('reg','supp') group by year,confirm_month, payment_type order by confirm_month)as ln on ln.confirm_month=emp.confirm_month and emp.payment_type=ln.payment_type where emp.emp_id=:emp_id and year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type and emp.payment_type in ('reg','supp') order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulartotalsum125All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type);

	@Query(value = "SELECT emp_id,(Select prefix||' '||firstname||' '||lastname||' '||surname from emp_details_confirm where emp_id=emp.emp_id limit 1),year,(select month_name from mstmonth where month_id=emp.confirm_month),emp.payment_type,\r\n"
			+ "os_basic_pay_earnings,os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions,os_prof_tax_deductions,os_other_deductions,emp.emp_type,emp.confirm_month  \r\n"
			+ "from emp_details_confirm emp left join (select payment_type,confirm_month,os_basic_pay_earnings,os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions,\r\n"
			+ "os_prof_tax_deductions,os_other_deductions from os_earn_ded_details_confirm os where os.emp_id=:emp_id and payment_type in (:payment_type) and  year||'-'||os.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and \r\n"
			+ "year||'-'||os.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  order by confirm_month)ea on  emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and\r\n"
			+ " year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  and emp.payment_type=ea.payment_type  and  emp.payment_type in (:payment_type) where emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and \r\n"
			+ "year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type  and emp.payment_type in (:payment_type) order by year,emp.confirm_month,generate_paybill ", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulardata3(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type, String payment_type);

	@Query(value = "SELECT sum(os.os_basic_pay_earnings) as total_basic_pay_earnings, sum(os.os_hra_earnings) as total_hra_earnings, sum(os.os_medical_earnings) as total_medical_earnings, sum(os.os_ca_earnings) as total_ca_earnings,\r\n"
			+ " sum(os.os_performance_earnings) as total_performance_earnings, sum(os.os_ec_epf) as total_ec_epf, sum(os.os_basic_pay_earnings + os.os_hra_earnings + os.os_medical_earnings + os.os_ca_earnings + os.os_performance_earnings +\r\n"
			+ " os.os_ec_epf) as total_earnings, sum(os.os_ees_epf_deductions) as total_ees_epf_deductions, sum(os.os_ers_epf_deductions) as total_ers_epf_deductions, sum(os.os_prof_tax_deductions) as total_prof_tax_deductions, \r\n"
			+ "sum(os.os_other_deductions) as total_other_deductions, sum(os.os_ees_epf_deductions + os.os_ers_epf_deductions + os.os_prof_tax_deductions + os.os_other_deductions) as total_deductions, \r\n"
			+ "(sum(os.os_basic_pay_earnings + os.os_hra_earnings + os.os_medical_earnings + os.os_ca_earnings + os.os_performance_earnings + os.os_ec_epf) - sum(os.os_ees_epf_deductions + os.os_ers_epf_deductions + os.os_prof_tax_deductions \r\n"
			+ "+ os.os_other_deductions)) as net_pay FROM emp_details_confirm emp LEFT JOIN (SELECT payment_type, confirm_month, os_basic_pay_earnings, os_hra_earnings, os_medical_earnings, os_ca_earnings, os_performance_earnings, os_ec_epf, \r\n"
			+ "os_ees_epf_deductions, os_ers_epf_deductions, os_prof_tax_deductions, os_other_deductions FROM os_earn_ded_details_confirm os WHERE os.emp_id = :emp_id AND payment_type IN (:payment_type) AND \r\n"
			+ "year || '-' || os.confirm_month || '%' >= (:fromYear||'-'||:fromMonth||'%' AND year || '-' || os.confirm_month || '%' <= (:toYear||'-'||:toMonth||'%') ORDER BY confirm_month) os ON emp.emp_id = :emp_id AND \r\n"
			+ "emp.confirm_month = os.confirm_month AND year || '-' || emp.confirm_month || '%' >= (:fromYear||'-'||:fromMonth||'%' AND year || '-' || emp.confirm_month || '%' <= (:toYear||'-'||:toMonth||'%') AND emp.payment_type = os.payment_type \r\n"
			+ "AND emp.payment_type IN (:payment_type)", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticularsumdata3(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String payment_type);

	@Query(value = "SELECT 'Basic Pay' AS BasicPay, 'HRA' AS HRA, 'Medical' AS Medical, 'CA' AS CA, 'Performance' AS Performance, 'Employers contribution to EPF' AS EmployersContributionEPF, 'Earnings Total' AS EarningsTotal, 'Employees Share(EPF)' AS EmployeesShareEPF, 'Employers Share(EPF)' AS EmployersShareEPF, 'Professional Tax' AS ProfessionalTax, 'Other Liabilities' AS OtherLiabilities, 'Deductions Total' AS DeductionsTotal;", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticularheading3();

	@Query(value = "SELECT sum(os_basic_pay_earnings), sum(os_hra_earnings),sum(os_medical_earnings),sum(os_ca_earnings),sum(os_performance_earnings),sum(os_ec_epf),sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+\r\n"
			+ "os_ca_earnings+os_performance_earnings+os_ec_epf),sum(os_ees_epf_deductions),sum(os_ers_epf_deductions),sum(os_prof_tax_deductions),sum(os_other_deductions),sum(os_ees_epf_deductions+os_ers_epf_deductions+\r\n"
			+ "os_prof_tax_deductions+os_other_deductions),(sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf)-sum(os_ees_epf_deductions+os_ers_epf_deductions+\r\n"
			+ "os_prof_tax_deductions+os_other_deductions)) from emp_details_confirm emp left join (select payment_type,confirm_month,os_basic_pay_earnings,os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,\r\n"
			+ "os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions,os_prof_tax_deductions ,os_other_deductions from os_earn_ded_details_confirm os where os.emp_id=:emp_id and payment_type in (:payment_type) and  \r\n"
			+ "year||'-'||os.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and year||'-'||os.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  order by confirm_month)os on  emp.emp_id=:emp_id and emp.confirm_month=os.confirm_month and  \r\n"
			+ "year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=os.payment_type and  emp.payment_type in (:payment_type) ", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulartotalsum3(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String payment_type);

	// All
	@Query(value = "SELECT emp_id,(Select prefix||' '||firstname||' '||lastname||' '||surname from emp_details_confirm where emp_id=emp.emp_id limit 1),year,(select month_name from mstmonth where month_id=emp.confirm_month),emp.payment_type,\r\n"
			+ "os_basic_pay_earnings,os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions,os_prof_tax_deductions,os_other_deductions,emp.emp_type,emp.confirm_month  \r\n"
			+ "from emp_details_confirm emp left join (select payment_type,confirm_month,os_basic_pay_earnings,os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions,\r\n"
			+ "os_prof_tax_deductions,os_other_deductions from os_earn_ded_details_confirm os where os.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||os.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and \r\n"
			+ "year||'-'||os.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  order by confirm_month)ea on  emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and\r\n"
			+ " year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  and emp.payment_type=ea.payment_type  and  emp.payment_type in ('reg','supp') where emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and \r\n"
			+ "year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.emp_type=:emp_type  and emp.payment_type in ('reg','supp') order by year,emp.confirm_month,generate_paybill ", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulardata3All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type);

	@Query(value = "SELECT sum(os.os_basic_pay_earnings) as total_basic_pay_earnings, sum(os.os_hra_earnings) as total_hra_earnings, sum(os.os_medical_earnings) as total_medical_earnings, sum(os.os_ca_earnings) as total_ca_earnings,\r\n"
			+ " sum(os.os_performance_earnings) as total_performance_earnings, sum(os.os_ec_epf) as total_ec_epf, sum(os.os_basic_pay_earnings + os.os_hra_earnings + os.os_medical_earnings + os.os_ca_earnings + os.os_performance_earnings +\r\n"
			+ " os.os_ec_epf) as total_earnings, sum(os.os_ees_epf_deductions) as total_ees_epf_deductions, sum(os.os_ers_epf_deductions) as total_ers_epf_deductions, sum(os.os_prof_tax_deductions) as total_prof_tax_deductions, \r\n"
			+ "sum(os.os_other_deductions) as total_other_deductions, sum(os.os_ees_epf_deductions + os.os_ers_epf_deductions + os.os_prof_tax_deductions + os.os_other_deductions) as total_deductions, \r\n"
			+ "(sum(os.os_basic_pay_earnings + os.os_hra_earnings + os.os_medical_earnings + os.os_ca_earnings + os.os_performance_earnings + os.os_ec_epf) - sum(os.os_ees_epf_deductions + os.os_ers_epf_deductions + os.os_prof_tax_deductions \r\n"
			+ "+ os.os_other_deductions)) as net_pay FROM emp_details_confirm emp LEFT JOIN (SELECT payment_type, confirm_month, os_basic_pay_earnings, os_hra_earnings, os_medical_earnings, os_ca_earnings, os_performance_earnings, os_ec_epf, \r\n"
			+ "os_ees_epf_deductions, os_ers_epf_deductions, os_prof_tax_deductions, os_other_deductions FROM os_earn_ded_details_confirm os WHERE os.emp_id = :emp_id AND payment_type IN ('reg','supp') AND \r\n"
			+ "year || '-' || os.confirm_month || '%' >= (:fromYear||'-'||:fromMonth||'%' AND year || '-' || os.confirm_month || '%' <= (:toYear||'-'||:toMonth||'%') ORDER BY confirm_month) os ON emp.emp_id = :emp_id AND \r\n"
			+ "emp.confirm_month = os.confirm_month AND year || '-' || emp.confirm_month || '%' >= (:fromYear||'-'||:fromMonth||'%' AND year || '-' || emp.confirm_month || '%' <= (:toYear||'-'||:toMonth||'%') AND emp.payment_type = os.payment_type \r\n"
			+ "AND emp.payment_type IN ('reg','supp')", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticularsumdata3All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear);

	@Query(value = "SELECT sum(os_basic_pay_earnings), sum(os_hra_earnings),sum(os_medical_earnings),sum(os_ca_earnings),sum(os_performance_earnings),sum(os_ec_epf),sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+\r\n"
			+ "os_ca_earnings+os_performance_earnings+os_ec_epf),sum(os_ees_epf_deductions),sum(os_ers_epf_deductions),sum(os_prof_tax_deductions),sum(os_other_deductions),sum(os_ees_epf_deductions+os_ers_epf_deductions+\r\n"
			+ "os_prof_tax_deductions+os_other_deductions),(sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf)-sum(os_ees_epf_deductions+os_ers_epf_deductions+\r\n"
			+ "os_prof_tax_deductions+os_other_deductions)) from emp_details_confirm emp left join (select payment_type,confirm_month,os_basic_pay_earnings,os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,\r\n"
			+ "os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions,os_prof_tax_deductions ,os_other_deductions from os_earn_ded_details_confirm os where os.emp_id=:emp_id and payment_type in ('reg','supp') and  \r\n"
			+ "year||'-'||os.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and year||'-'||os.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  order by confirm_month)os on  emp.emp_id=:emp_id and emp.confirm_month=os.confirm_month and  \r\n"
			+ "year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%' and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and emp.payment_type=os.payment_type and  emp.payment_type in ('reg','supp') ", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulartotalsum3All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear);

	@Query(value = "SELECT emp_id, ( SELECT prefix || ' ' || firstname || ' ' || lastname || ' ' || surname FROM emp_details_confirm WHERE emp_id = emp.emp_id LIMIT 1 ) AS full_name, year, ( SELECT month_name FROM mstmonth WHERE month_id = emp.confirm_month ) AS month_name, emp.payment_type, nmr_gross_earnings, os_ees_epf_deductions, nmr_postalrd_deductions, os_prof_tax_deductions, nmr_tds_deductions, nmr_fa_deductions, nmr_ea_deductions, nmr_ma_deductions, nmr_lic_deductions, nmr_otherliab_deductions, emp.emp_type, emp.confirm_month FROM emp_details_confirm emp LEFT JOIN ( SELECT payment_type, confirm_month, nmr_gross_earnings, os_ees_epf_deductions, nmr_postalrd_deductions, os_prof_tax_deductions, nmr_tds_deductions, nmr_fa_deductions, nmr_ea_deductions, nmr_ma_deductions, nmr_lic_deductions, nmr_otherliab_deductions FROM nmr_earn_ded_details_confirm os WHERE os.emp_id = :emp_id AND payment_type IN (:payment_type) AND year || '-' || os.confirm_month || '%' >= (:fromYear || '-' || :fromMonth || '%') AND year || '-' || os.confirm_month || '%' <= (:toYear || '-' || :toMonth || '%') ORDER BY confirm_month ) ea ON emp.emp_id = :emp_id AND emp.confirm_month = ea.confirm_month AND year || '-' || ea.confirm_month || '%' >= (:fromYear || '-' || :fromMonth || '%') AND year || '-' || ea.confirm_month || '%' <= (:toYear || '-' || :toMonth || '%') AND emp.payment_type = ea.payment_type AND emp.payment_type IN (:payment_type) WHERE emp.emp_id = :emp_id AND year || '-' || emp.confirm_month || '%' >= (:fromYear || '-' || :fromMonth || '%') AND year || '-' || emp.confirm_month || '%' <= (:toYear || '-' || :toMonth || '%') AND emp.emp_type = :emp_type AND emp.payment_type IN (:payment_type) ORDER BY year, emp.confirm_month, generate_paybill", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulardata4(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type, String payment_type);

	@Query(value = "SELECT sum(nmr_gross_earnings) AS total_gross_earnings, sum(os_ees_epf_deductions) AS total_os_ees_epf_deductions, sum(nmr_postalrd_deductions) AS total_nmr_postalrd_deductions, sum(os_prof_tax_deductions) AS total_os_prof_tax_deductions, sum(nmr_tds_deductions) AS total_nmr_tds_deductions, sum(nmr_fa_deductions) AS total_nmr_fa_deductions, sum(nmr_ea_deductions) AS total_nmr_ea_deductions, sum(nmr_ma_deductions) AS total_nmr_ma_deductions, sum(nmr_lic_deductions) AS total_nmr_lic_deductions, sum(nmr_otherliab_deductions) AS total_nmr_otherliab_deductions, sum(os_ees_epf_deductions + nmr_postalrd_deductions + os_prof_tax_deductions + nmr_tds_deductions + nmr_fa_deductions + nmr_ea_deductions + nmr_ma_deductions + nmr_lic_deductions + nmr_otherliab_deductions) AS total_deductions, (sum(nmr_gross_earnings) - sum(os_ees_epf_deductions + nmr_postalrd_deductions + os_prof_tax_deductions + nmr_tds_deductions + nmr_fa_deductions + nmr_ea_deductions + nmr_ma_deductions + nmr_lic_deductions + nmr_otherliab_deductions)) AS net_earnings FROM emp_details_confirm emp LEFT JOIN ( SELECT payment_type, confirm_month, nmr_gross_earnings, os_ees_epf_deductions, nmr_postalrd_deductions, os_prof_tax_deductions, nmr_tds_deductions, nmr_fa_deductions, nmr_ea_deductions, nmr_ma_deductions, nmr_lic_deductions, nmr_otherliab_deductions FROM nmr_earn_ded_details_confirm os WHERE os.emp_id = :emp_id AND payment_type IN (:payment_type) AND year || '-' || os.confirm_month || '%' >= (:fromYear||'-'||:fromMonth||'%') AND year || '-' || os.confirm_month || '%' <= (:toYear||'-'||:toMonth||'%') ) os ON emp.emp_id = :emp_id AND emp.confirm_month = os.confirm_month AND year || '-' || emp.confirm_month || '%' >= (:fromYear||'-'||:fromMonth||'%') AND year || '-' || emp.confirm_month || '%' <= (:toYear||'-'||:toMonth||'%') AND emp.payment_type = os.payment_type AND emp.payment_type IN (:payment_type)", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticularsumdata4(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String payment_type);

	@Query(value = "SELECT 'Gross Pay' AS Gross_Pay, 'EPF' AS EPF, 'Postal RD' AS Postal_RD, 'Professional Tax' AS Professional_Tax, 'TDS' AS TDS, 'FA' AS FA, 'EA' AS EA, 'MA' AS MA, 'LIC' AS LIC, 'Other Liabilities' AS Other_Liabilities, 'Deductions Total' AS Deductions_Total;", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticularheading4();

	@Query(value = "SELECT earnings,deductions,earnings-deductions  from emp_details_confirm emp left join (select payment_type,confirm_month,sum(nmr_gross_earnings)as earnings \r\n"
			+ "from nmr_earn_ded_details_confirm nmr where nmr.emp_id=:emp_id and payment_type in (:payment_type) and  year||'-'||nmr.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||nmr.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')\r\n"
			+ " group by year,confirm_month,payment_type order by confirm_month)ea on  emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  \r\n"
			+ "and emp.payment_type=ea.payment_type  left join (select payment_type,confirm_month,sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+\r\n"
			+ "nmr_lic_deductions+nmr_otherliab_deductions)as deductions from nmr_earn_ded_details_confirm d where d.emp_id=:emp_id and payment_type in (:payment_type) and  year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and \r\n"
			+ "year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and  year||'-'||ded.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') \r\n"
			+ "and year||'-'||ded.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  and emp.payment_type=ded.payment_type    where  emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and \r\n"
			+ "emp.emp_type=:emp_type  and emp.payment_type in (:payment_type) order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulartotalsum4(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type, String payment_type);

//All
	@Query(value = "SELECT emp_id, ( SELECT prefix || ' ' || firstname || ' ' || lastname || ' ' || surname FROM emp_details_confirm WHERE emp_id = emp.emp_id LIMIT 1 ) AS full_name, year, ( SELECT month_name FROM mstmonth WHERE month_id = emp.confirm_month ) AS month_name, emp.payment_type, nmr_gross_earnings, os_ees_epf_deductions, nmr_postalrd_deductions, os_prof_tax_deductions, nmr_tds_deductions, nmr_fa_deductions, nmr_ea_deductions, nmr_ma_deductions, nmr_lic_deductions, nmr_otherliab_deductions, emp.emp_type, emp.confirm_month FROM emp_details_confirm emp LEFT JOIN ( SELECT payment_type, confirm_month, nmr_gross_earnings, os_ees_epf_deductions, nmr_postalrd_deductions, os_prof_tax_deductions, nmr_tds_deductions, nmr_fa_deductions, nmr_ea_deductions, nmr_ma_deductions, nmr_lic_deductions, nmr_otherliab_deductions FROM nmr_earn_ded_details_confirm os WHERE os.emp_id = :emp_id AND payment_type IN ('reg', 'supp') AND year || '-' || os.confirm_month || '%' >= (:fromYear || '-' || :fromMonth || '%') AND year || '-' || os.confirm_month || '%' <= (:toYear || '-' || :toMonth || '%') ORDER BY confirm_month ) ea ON emp.emp_id = :emp_id AND emp.confirm_month = ea.confirm_month AND year || '-' || ea.confirm_month || '%' >= (:fromYear || '-' || :fromMonth || '%') AND year || '-' || ea.confirm_month || '%' <= (:toYear || '-' || :toMonth || '%') AND emp.payment_type = ea.payment_type AND emp.payment_type IN ('reg', 'supp') WHERE emp.emp_id = :emp_id AND year || '-' || emp.confirm_month || '%' >= (:fromYear || '-' || :fromMonth || '%') AND year || '-' || emp.confirm_month || '%' <= (:toYear || '-' || :toMonth || '%') AND emp.emp_type = :emp_type AND emp.payment_type IN ('reg', 'supp') ORDER BY year, emp.confirm_month, generate_paybill", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulardata4All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear, String emp_type);

	@Query(value = "SELECT sum(nmr_gross_earnings) AS total_gross_earnings, sum(os_ees_epf_deductions) AS total_os_ees_epf_deductions, sum(nmr_postalrd_deductions) AS total_nmr_postalrd_deductions, sum(os_prof_tax_deductions) AS total_os_prof_tax_deductions, sum(nmr_tds_deductions) AS total_nmr_tds_deductions, sum(nmr_fa_deductions) AS total_nmr_fa_deductions, sum(nmr_ea_deductions) AS total_nmr_ea_deductions, sum(nmr_ma_deductions) AS total_nmr_ma_deductions, sum(nmr_lic_deductions) AS total_nmr_lic_deductions, sum(nmr_otherliab_deductions) AS total_nmr_otherliab_deductions, sum(os_ees_epf_deductions + nmr_postalrd_deductions + os_prof_tax_deductions + nmr_tds_deductions + nmr_fa_deductions + nmr_ea_deductions + nmr_ma_deductions + nmr_lic_deductions + nmr_otherliab_deductions) AS total_deductions, (sum(nmr_gross_earnings) - sum(os_ees_epf_deductions + nmr_postalrd_deductions + os_prof_tax_deductions + nmr_tds_deductions + nmr_fa_deductions + nmr_ea_deductions + nmr_ma_deductions + nmr_lic_deductions + nmr_otherliab_deductions)) AS net_earnings FROM emp_details_confirm emp LEFT JOIN ( SELECT payment_type, confirm_month, nmr_gross_earnings, os_ees_epf_deductions, nmr_postalrd_deductions, os_prof_tax_deductions, nmr_tds_deductions, nmr_fa_deductions, nmr_ea_deductions, nmr_ma_deductions, nmr_lic_deductions, nmr_otherliab_deductions FROM nmr_earn_ded_details_confirm os WHERE os.emp_id = :emp_id AND payment_type IN ('reg','supp') AND year || '-' || os.confirm_month || '%' >= (:fromYear||'-'||:fromMonth||'%') AND year || '-' || os.confirm_month || '%' <= (:toYear||'-'||:toMonth||'%') ) os ON emp.emp_id = :emp_id AND emp.confirm_month = os.confirm_month AND year || '-' || emp.confirm_month || '%' >= (:fromYear||'-'||:fromMonth||'%') AND year || '-' || emp.confirm_month || '%' <= (:toYear||'-'||:toMonth||'%') AND emp.payment_type = os.payment_type AND emp.payment_type IN ('reg','supp')", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticularsumdata4All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String toYear);

	@Query(value = "SELECT earnings,deductions,earnings-deductions  from emp_details_confirm emp left join (select payment_type,confirm_month,sum(nmr_gross_earnings)as earnings \r\n"
			+ "from nmr_earn_ded_details_confirm nmr where nmr.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||nmr.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||nmr.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')\r\n"
			+ " group by year,confirm_month,payment_type order by confirm_month)ea on  emp.emp_id=:emp_id and emp.confirm_month=ea.confirm_month and  year||'-'||ea.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||ea.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  \r\n"
			+ "and emp.payment_type=ea.payment_type  left join (select payment_type,confirm_month,sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+\r\n"
			+ "nmr_lic_deductions+nmr_otherliab_deductions)as deductions from nmr_earn_ded_details_confirm d where d.emp_id=:emp_id and payment_type in ('reg','supp') and  year||'-'||d.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and \r\n"
			+ "year||'-'||d.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') group by year,confirm_month,payment_type order by confirm_month)ded on emp.emp_id=:emp_id and emp.confirm_month=ded.confirm_month and  year||'-'||ded.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') \r\n"
			+ "and year||'-'||ded.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%')  and emp.payment_type=ded.payment_type    where  emp.emp_id=:emp_id and  year||'-'||emp.confirm_month||'%' >= (:fromYear||'-'||:fromMonth||'%') and year||'-'||emp.confirm_month||'%' <=(:toYear||'-'||:toMonth||'%') and \r\n"
			+ "emp.emp_type=:emp_type  and emp.payment_type in ('reg','supp') order by year,emp.confirm_month,generate_paybill", nativeQuery = true)
	List<Map<String, Object>> ViewDetailedPayParticulartotalsum4All(String emp_id, String fromMonth, String toMonth,
			String fromYear, String emp_type, String toYear);

//DistrictwiseDesignationCount
	@Query(value = "SELECT designation_name,COUNT(*) FILTER (WHERE code = '00') AS Head_Office,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '502') AS ANANTAPUR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '503') AS CHITTOOR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '504') AS YSR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '505') AS EAST_GODAVARI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '506') AS GUNTUR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '510') AS KRISHNA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '511') AS KURNOOL,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '515') AS SPSR_NELLORE,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '517') AS PRAKASAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '519') AS SRIKAKULAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '520') AS VISAKHAPATANAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '521') AS VIZIANAGARAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '523') AS WEST_GODAVARI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '743') AS PARVATHIPURAM_MANYAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '744') AS ANAKAPALLI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '745') AS ALLURI_SITHA_RAMA_RAJU,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '746') AS KAKINADA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '747') AS KONASEEMA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '748') AS ELURU,\r\n" + "COUNT(*) FILTER (WHERE code = '749') AS NTR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '750') AS BAPATLA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '751') AS PALNADU,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '752') AS TIRUPATI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '753') AS ANNAMAYYA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '754') AS SRI_SATHYA_SAI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '755') AS NANDYAL\r\n" + "FROM emp_details e\r\n"
			+ "JOIN designation d ON e.designation = d.designation_id\r\n"
			+ "JOIN cgg_master_districts c ON e.security_id = c.code\r\n"
			+ "GROUP BY designation_id order by designation_id", nativeQuery = true)
	List<Map<String, String>> DistrictwiseDesignationCountAll();

	@Query(value = "SELECT designation_name,COUNT(*) FILTER (WHERE code = '00') AS Head_Office,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '502') AS ANANTAPUR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '503') AS CHITTOOR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '504') AS YSR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '505') AS EAST_GODAVARI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '506') AS GUNTUR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '510') AS KRISHNA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '511') AS KURNOOL,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '515') AS SPSR_NELLORE,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '517') AS PRAKASAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '519') AS SRIKAKULAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '520') AS VISAKHAPATANAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '521') AS VIZIANAGARAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '523') AS WEST_GODAVARI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '743') AS PARVATHIPURAM_MANYAM,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '744') AS ANAKAPALLI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '745') AS ALLURI_SITHA_RAMA_RAJU,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '746') AS KAKINADA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '747') AS KONASEEMA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '748') AS ELURU,\r\n" + "COUNT(*) FILTER (WHERE code = '749') AS NTR,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '750') AS BAPATLA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '751') AS PALNADU,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '752') AS TIRUPATI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '753') AS ANNAMAYYA,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '754') AS SRI_SATHYA_SAI,\r\n"
			+ "COUNT(*) FILTER (WHERE code = '755') AS NANDYAL\r\n" + "FROM emp_details e\r\n"
			+ "JOIN designation d ON e.designation = d.designation_id\r\n"
			+ "JOIN cgg_master_districts c ON e.security_id = c.code where emp_type=:emp_type\r\n"
			+ "GROUP BY designation_id order by designation_id", nativeQuery = true)
	List<Map<String, String>> DistrictwiseDesignationCount(String emp_type);

	// PAYBILL REPORT IN GMADM LOGIN
	@Query(value = "SELECT emp.emp_id,name,designation,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ca_earnings,ir_earnings,medical_earnings,misc_h_c,apglis_deductions,\r\n"
			+ "apglil_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,"
			+ "cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,\r\n"
			+ "fa_loan,ea_loan,sfa_loan,pt_deductions,mar_i_loan,it_deductions,hr_arrear_loan,med_a_i_loan,vij_bank_loan,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions,sal_adv_loan,\r\n"
			+ "hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan,emp_type  from (SELECT emp.emp_id,name,designation,emp_type,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,\r\n"
			+ "cca_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,ca_earnings,apglis_deductions,apglil_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,con_decd_deductions,\r\n"
			+ "epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,pt_deductions,it_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions  from \r\n"
			+ "(SELECT emp.emp_id,name,emp_type,designation,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,ca_earnings from \r\n"
			+ "(SELECT emp_id,prefix||' '||firstname||' '||lastname||' '||surname as name,emp_type,case when designation='100' then (select designation_name from designation where designation_id=e.designation)||'-'||others else \r\n"
			+ "(select designation_name from designation where designation_id=e.designation and security_id=:security_id) end as designation  from emp_details e where  emp_type=:emp_type and isdelete='f' order by e.designation )\r\n"
			+ "as emp join (SELECT emp_id,basic_pay_earnings,per_pay_earnings,spl_pay_earnings,gp_earnings,da_earnings,hra_earnings,cca_earnings,spl_all,ir_earnings,medical_earnings,misc_h_c,ca_earnings from earnings_details where isdelete='f' \r\n"
			+ " and security_id=:security_id)as ear on ear.emp_id=emp.emp_id) as emp join (select emp_id,apglis_deductions,apglil_deductions,house_rent_deductions,lic_deductions,gpfs_deductions,gpfl_deductions,gpfsa_deductions,\r\n"
			+ "con_decd_deductions,epf_deductions,ppf_deductions,epf_l_deductions,license_deductions,gis_deductions,sal_rec_deductions,pt_deductions,it_deductions,cca_deductions,rcs_cont_deductions,cmrf_deductions,fcf_deductions,vpf_deductions \r\n"
			+ " from deductions_details where isdelete ='f' and security_id=:security_id)ded on emp.emp_id=ded.emp_id) as emp join (select emp_id,cell_loan,comp_loan,hba_loan,add_hba_loan,hbao_loan,comp1_loan,hba1_loan,add_hba1_loan,\r\n"
			+ "car_a_loan,cyc_a_loan,mca_a_loan,mar_a_loan,car_i_loan,cyc_i_loan,mca_i_loan,med_a_loan,fa_loan,ea_loan,sfa_loan,mar_i_loan,hr_arrear_loan,med_a_i_loan,vij_bank_loan,sal_adv_loan,hcesa_loan,hcesa_i_loan,staff_pl_loan,court_loan\r\n"
			+ " from loan_details where  isdelete='f' and security_id=:security_id) as loan on emp.emp_id=loan.emp_id ", nativeQuery = true)
	List<Map<String, String>> Paybill_Report125(String security_id, String emp_type);

	@Query(value = "SELECT emp.emp_id,emp_type,name,designation,work_place,os_location,os_basic_pay_earnings,os_hra_earnings,os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,\r\n"
			+ "os_ers_epf_deductions,os_prof_tax_deductions from (SELECT emp_id,prefix||' '||firstname||' '||lastname||' '||surname as name,emp_type,case when designation='100' then (select designation_name from designation \r\n"
			+ "where designation_id=e.designation)||'-'||others else (select designation_name from designation where designation_id=e.designation) end as designation  from emp_details e where security_id=:security_id and emp_type='3' and  \r\n"
			+ "isdelete='f' order by designation,name)emp join(SELECT emp_id,case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office'\r\n"
			+ " when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place and dist_code='01'||:security_id) end as work_place,os_location,os_basic_pay_earnings,os_hra_earnings,\r\n"
			+ "os_medical_earnings,os_ca_earnings,os_performance_earnings,os_ec_epf,os_ees_epf_deductions,os_ers_epf_deductions,os_prof_tax_deductions from os_earn_ded_details) os  on os.emp_id=emp.emp_id", nativeQuery = true)
	List<Map<String, String>> Paybill_Report3(String security_id);

	@Query(value = "SELECT emp.emp_id,emp_type,name,designation,work_place,os_location,nmr_gross_earnings,os_ees_epf_deductions,os_prof_tax_deductions,nmr_postalrd_deductions,nmr_tds_deductions,nmr_fa_deductions,nmr_ea_deductions,\r\n"
			+ "nmr_ma_deductions,nmr_lic_deductions,nmr_otherliab_deductions from (SELECT emp_id,prefix||' '||firstname||' '||lastname||' '||surname as name,emp_type,case when designation='100' then (select designation_name from designation \r\n"
			+ "where designation_id=e.designation)||'-'||others else (select designation_name from designation where designation_id=e.designation) end as designation  from emp_details e where security_id=:security_id and emp_type='4' and \r\n"
			+ " isdelete='f')emp join(SELECT emp_id,case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' \r\n"
			+ "when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place and dist_code='01'||:security_id) end as work_place,os_location,nmr_gross_earnings,\r\n"
			+ "os_ees_epf_deductions,os_prof_tax_deductions,nmr_postalrd_deductions,nmr_tds_deductions,nmr_fa_deductions,nmr_ea_deductions,nmr_ma_deductions,nmr_lic_deductions,nmr_otherliab_deductions from nmr_earn_ded_details)\r\n"
			+ " os  on os.emp_id=emp.emp_id order by designation,name", nativeQuery = true)
	List<Map<String, String>> Paybill_Report4(String security_id);

	// EMPLOYEE DATA COUNT REPORT
	@Query(value = "select code,coalesce(reg_apshcl,0)AS reg_apshcl,coalesce(reg_deputation,0)AS reg_deputation,coalesce(outsourcing,0)ASoutsourcing ,coalesce(nmr,0)AS NMR,(COALESCE(reg_apshcl, 0) + COALESCE(reg_deputation, 0) + COALESCE(outsourcing, 0) + COALESCE(nmr, 0)) AS total from cgg_master_districts left join\r\n"
			+ " (select security_id,reg_apshcl,reg_deputation,outsourcing,nmr from employeedatacount)edata on code=security_id order by code", nativeQuery = true)
	List<Map<String, Object>> DistrictsPayBill_Actual();

	@Query(value = "SELECT  rgosnmr.code,(select name from cgg_master_districts where code=rgosnmr.code),regular,deputation,os,nmr,sum( regular+deputation+os+nmr)as total from (SELECT rgos.code as code,regular,os,nmr from (SELECT reg.code,regular,os from \r\n"
			+ "(SELECT code,coalesce(regular,0) as regular from cgg_master_districts left join (SELECT security_id,count(*) as regular from emp_details where emp_type ='1'  group by security_id,emp_type order by security_id)reg on \r\n"
			+ "code=security_id)reg left join (SELECT code,coalesce(os,0) as os from cgg_master_districts left join (SELECT security_id,count(*) as os from emp_details where emp_type ='3'  group by security_id,emp_type\r\n"
			+ "order by security_id)reg on code=security_id) as outsourcing on reg.code=outsourcing.code)as rgos left join ( SELECT code,coalesce(nmr,0) as nmr from cgg_master_districts left join (SELECT security_id,\r\n"
			+ "count(*) as nmr from emp_details where emp_type ='4'  group by security_id,emp_type order by security_id)reg on code=security_id)nm  on nm.code=rgos.code order by rgos.code)rgosnmr\r\n"
			+ " left join (SELECT code,coalesce(deputation,0) as deputation from cgg_master_districts left join (SELECT security_id,count(*) as deputation from emp_details where emp_type ='2'  group by security_id,emp_type\r\n"
			+ "order by security_id)reg on code=security_id) AS dep ON rgosnmr.code = dep.code\r\n"
			+ "GROUP BY rgosnmr.code, (SELECT name FROM cgg_master_districts WHERE code = rgosnmr.code), rgosnmr.regular,dep.deputation,rgosnmr.os,rgosnmr.nmr ORDER BY rgosnmr.code", nativeQuery = true)
	List<Map<String, Object>> DistrictsPayBill_Entered();

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO  update_employeedatacount (security_id,reg_apshcl,reg_deputation,outsourcing,nmr,timestamp)  (select security_id,reg_apshcl,reg_deputation,outsourcing,nmr,timestamp from employeedatacount where security_id=:code ) ", nativeQuery = true)
	int Insert_update_employeedatacount(String code);

	@Transactional
	@Modifying
	@Query(value = "update employeedatacount set reg_apshcl=:reg_apshcl ,reg_deputation=:reg_deputation,outsourcing=:outsourcing,nmr=:nmr,timestamp=now() where security_id=:code", nativeQuery = true)
	int update_employeedatacount(float reg_apshcl, float reg_deputation, float outsourcing, float nmr, String code);

}
