package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import com.azure.core.annotation.Get;

import in_Apcfss.Apofms.api.ofmsapi.models.BankDetails_Model;

@Repository
public interface EntryForms_GMF_Repo extends JpaRepository<BankDetails_Model, Integer> {

	@Query(value = "SELECT name,regular,conveyance,administative,others,capital_exp,adv_staff,ori_regular_amount,\r\n"
			+ "ori_conveyance_amount,ori_administative_amount,ori_others_amount,ori_capital_exp,ori_adv_staff,b.code,sum(regular+conveyance+administative+others+capital_exp+adv_staff) as total\r\n"
			+ "from cgg_master_districts d join budget_limits b on b.code=d.code and d.code!='00' \r\n"
			+ "group by name,regular,conveyance,administative,others,capital_exp,adv_staff,ori_regular_amount,\r\n"
			+ "ori_conveyance_amount,ori_administative_amount,ori_others_amount,ori_capital_exp,ori_adv_staff,b.code,d.code\r\n"
			+ "order by d.code", nativeQuery = true)
	List<Map<String, String>> DistrictsBudgetLimits();

	@Query(value = "SELECT name,regular,conveyance,administative,others,capital_exp,adv_staff,ori_regular_amount,ori_conveyance_amount,\r\n"
			+ "ori_administative_amount,ori_others_amount,ori_capital_exp,ori_adv_staff,b.code,\r\n"
			+ "sum(regular+conveyance+administative+others+capital_exp+adv_staff) as total from cgg_master_districts d \r\n"
			+ "join budget_limits b on b.code=d.code and d.code='00' \r\n"
			+ "group by name,regular,conveyance,administative,others,capital_exp,adv_staff,ori_regular_amount,\r\n"
			+ "ori_conveyance_amount,ori_administative_amount,ori_others_amount,ori_capital_exp,ori_adv_staff,b.code,d.code\r\n"
			+ "order by d.code", nativeQuery = true)
	List<Map<String, String>> HeadOfficeBudgetLimits();

	@Query(value = "SELECT sum(ori_regular_amount),sum(regular),sum(ori_conveyance_amount),sum(conveyance),sum(ori_administative_amount),\r\n"
			+ "sum(administative),sum(ori_others_amount),sum(others),sum(ori_capital_exp),sum(capital_exp),sum(ori_adv_staff),sum(adv_staff) \r\n"
			+ "from budget_limits where code!='00'", nativeQuery = true)
	List<Map<String, String>> getDistrictTotal();

	@Query(value = "SELECT sum(ori_regular_amount),sum(regular),sum(ori_conveyance_amount),sum(conveyance),sum(ori_administative_amount),\r\n"
			+ "sum(administative),sum(ori_others_amount),sum(others),sum(ori_capital_exp),sum(capital_exp),sum(ori_adv_staff),sum(adv_staff)\r\n"
			+ " from budget_limits", nativeQuery = true)
	List<Map<String, String>> getGrandTotal();

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO  update_budget_limits(code,regular,conveyance,administative,others,timestamp,capital_exp,adv_staff) \r\n"
			+ "(select code,regular,conveyance,administative,others,timestamp,capital_exp,adv_staff from budget_limits where code=:code) ", nativeQuery = true)
	int Insert_update_budget_limits(String code);

	@Transactional
	@Modifying
	@Query(value = "update budget_limits set regular=cast(:regular as numeric) ,conveyance=cast(:conveyance as numeric),administative=cast(:administative as numeric),\r\n"
			+ "others=cast(:others as numeric),timestamp=now(),capital_exp=cast(:capital_exp as numeric),adv_staff=cast(:adv_staff as numeric) \r\n"
			+ " where code=:code ", nativeQuery = true)
	int budget_limits_update(float regular, float conveyance, float administative, float others,
			float capital_exp, float adv_staff, String code);

////*------- Bank Detail Confirmation --------*/
	@Query(value = "SELECT accountid,ap.security_name,bankname,branchname,accountno,ifsc,balance,banknameaccountno,passbook_path FROM bankdetails bd\r\n"
			+ "left join security_code_master ap on cast(ap.security_code as varchar)=bd.distid\r\n"
			+ " where bd.is_approved_by_gm='f' and dh_status=true   order by distid", nativeQuery = true)
	List<Map<String, String>> GetBankDetailsToBeConfirmByGmf();
//and man_status=true 
	@Query(value = "SELECT accountid,ap.security_name,bankname,branchname,accountno,ifsc,balance,banknameaccountno,passbook_path FROM bankdetails bd\r\n"
			+ "left join security_code_master ap on cast(ap.security_code as varchar)=bd.distid\r\n"
			+ " where bd.is_approved_by_gm='f' and dh_status=true and distid=:code   order by distid", nativeQuery = true)
	//and man_status=true
	List<Map<String, String>> GetBankDetailsToBeConfirmByGmf_dist(String code);

//	/*------- District Wise Date Limits --------*/
	@Query(value = "SELECT name,dl_sw,dl_conveyance,dl_reclib,dl_jvs,dl.code from cgg_master_districts d join  date_limit dl on d.code=dl.code order by d.code", nativeQuery = true)
	List<Map<String, String>> DistrictBudgetDateLimits();

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO  update_date_limit  (select code,dl_sw,dl_conveyance,timestamp,dl_reclib,dl_jvs from date_limit where code=:code)", nativeQuery = true)
	int Insert_update_date_limits(String code);

	@Transactional
	@Modifying
	@Query(value = "update date_limit set timestamp=now(),dl_sw=:dl_sw,dl_conveyance=:dl_conveyance,dl_reclib=:dl_reclib,dl_jvs=:dl_jvs where code=:code", nativeQuery = true)
	int date_limits_update(String dl_sw, String dl_conveyance, String dl_reclib, String dl_jvs, String code);

	@Transactional
	@Modifying
	@Query(value = "update bankdetails  set mandal_account=false,is_approved_by_gm=true,gmfinance_aproved_date=now(),gm_id=:user_id where  banknameaccountno =:banknameaccountno", nativeQuery = true)
	int update_mandal_account(String user_id, String banknameaccountno);

	@Transactional
	@Modifying
	// bankdetails_crud back table
	@Query(value = "delete from  bankdetails   where  banknameaccountno=:banknameaccountno ", nativeQuery = true) 
	int delete_mandal_account(String banknameaccountno);

//	EmployeeConfirmation
	@Query(value = "SELECT emp_id,(prefix||''||firstname||' '||lastname||' '||surname) as name,(select designation_name from designation d where d.designation_id=e.designation) as designation_name,sc.security_name,sc.security_code,\r\n"
			+ "case when emp_type='3' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo'\r\n"
			+ " then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place and dist_code='01'||'\"+security_id+\"') end as work_place\r\n"
			+ " from os_earn_ded_details os where os.emp_id=e.emp_id) when  emp_type='4' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo'\r\n"
			+ " then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place) \r\n"
			+ "end as work_place from nmr_earn_ded_details os where os.emp_id=e.emp_id)else '--' end as work_location1,case when emp_type='3' then (select os_location from os_earn_ded_details os where os.emp_id=e.emp_id) when  emp_type='4' then \r\n"
			+ "(select os_location from nmr_earn_ded_details os where os.emp_id=e.emp_id) else '--' end as work_location2,case when emp_type='1' or emp_type='2' or emp_type='5' then (select sum(basic_pay_earnings+per_pay_earnings\r\n"
			+ "+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details ea where ea.emp_id=e.emp_id)\r\n"
			+ " when emp_type='3' then  (select sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf) from os_earn_ded_details os where os.emp_id=e.emp_id)\r\n"
			+ " else  (select sum(nmr_gross_earnings) from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as earnings,case when emp_type='1' or emp_type='2' or emp_type='5' then \r\n"
			+ "(select sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+\r\n"
			+ "lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions)as deductions\r\n"
			+ " from deductions_details d where d.emp_id=e.emp_id) when emp_type='3' then  (select sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions) from os_earn_ded_details os where  os.emp_id=e.emp_id)\r\n"
			+ " else  (select sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions)\r\n"
			+ " from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as deductions,case when emp_type='1' or emp_type='2' or emp_type='5' then (select sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+\r\n"
			+ "med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+\r\n"
			+ "hbao_loan+comp1_loan)as loan from loan_details l where l.emp_id=e.emp_id) else 0.00  end as loan,emp_type,e.designation,working_status,case when flag is not true then 1 else 0 end as flag from emp_details e \r\n"
			+ " Left join confirmedemployees using (emp_id,security_id) left join security_code_master sc on sc.security_code=e.security_id  where confirmed_by_cgm='f' order by  security_id", nativeQuery = true)
	List<Map<String, String>> EmployeeConfirmation_All();

	@Query(value = "SELECT emp_id,(prefix||''||firstname||' '||lastname||' '||surname) as name,(select designation_name from designation d where d.designation_id=e.designation) as designation_name, sc.security_name,sc.security_code,\r\n"
			+ "case when emp_type='3' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo' then 'SE/GM Office' when os_work_place='eeo'\r\n"
			+ " then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place and dist_code='01'||'\"+security_id+\"') end as work_place\r\n"
			+ " from os_earn_ded_details os where os.emp_id=e.emp_id) when  emp_type='4' then (select case when os_work_place='null' then '--' when os_work_place='ho' then 'Head Office' when os_work_place='segmo'\r\n"
			+ " then 'SE/GM Office' when os_work_place='eeo' then 'EE Office' when os_work_place='dyeeo' then 'Dy.EE Office' else (select name from cgg_master_mandals where code=os_work_place) \r\n"
			+ "end as work_place from nmr_earn_ded_details os where os.emp_id=e.emp_id)else '--' end as work_location1,case when emp_type='3' then (select os_location from os_earn_ded_details os where os.emp_id=e.emp_id) when  emp_type='4' then \r\n"
			+ "(select os_location from nmr_earn_ded_details os where os.emp_id=e.emp_id) else '--' end as work_location2,case when emp_type='1' or emp_type='2' or emp_type='5' then (select sum(basic_pay_earnings+per_pay_earnings\r\n"
			+ "+spl_pay_earnings+da_earnings+hra_earnings+cca_earnings+gp_earnings+ir_earnings+medical_earnings+ca_earnings+spl_all+misc_h_c+addl_hra+sca)as earnings from earnings_details ea where ea.emp_id=e.emp_id)\r\n"
			+ " when emp_type='3' then  (select sum(os_basic_pay_earnings+os_hra_earnings+os_medical_earnings+os_ca_earnings+os_performance_earnings+os_ec_epf) from os_earn_ded_details os where os.emp_id=e.emp_id)\r\n"
			+ " else  (select sum(nmr_gross_earnings) from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as earnings,case when emp_type='1' or emp_type='2' or emp_type='5' then \r\n"
			+ "(select sum(gpfs_deductions+gpfl_deductions+gpfsa_deductions+house_rent_deductions+gis_deductions+pt_deductions+it_deductions+cca_deductions+license_deductions+con_decd_deductions+\r\n"
			+ "lic_deductions+rcs_cont_deductions+sal_rec_deductions+cmrf_deductions+fcf_deductions+epf_l_deductions+vpf_deductions+apglis_deductions+apglil_deductions+epf_deductions+ppf_deductions+other_deductions)as deductions\r\n"
			+ " from deductions_details d where d.emp_id=e.emp_id) when emp_type='3' then  (select sum(os_ees_epf_deductions+os_ers_epf_deductions+os_prof_tax_deductions+os_other_deductions) from os_earn_ded_details os where  os.emp_id=e.emp_id)\r\n"
			+ " else  (select sum(os_ees_epf_deductions+os_prof_tax_deductions+nmr_postalrd_deductions+nmr_tds_deductions+nmr_fa_deductions+nmr_ea_deductions+nmr_ma_deductions+nmr_lic_deductions+nmr_otherliab_deductions)\r\n"
			+ " from nmr_earn_ded_details os where os.emp_id=e.emp_id) end as deductions,case when emp_type='1' or emp_type='2' or emp_type='5' then (select sum(car_i_loan+car_a_loan+cyc_i_loan+cyc_a_loan+mca_i_loan+mca_a_loan+mar_a_loan+\r\n"
			+ "med_a_loan+hba_loan+hba1_loan+comp_loan+fa_loan+ea_loan+cell_loan+add_hba_loan+add_hba1_loan+sal_adv_loan+sfa_loan+med_a_i_loan+hcesa_loan+hcesa_i_loan+staff_pl_loan+court_loan+vij_bank_loan+mar_i_loan+hr_arrear_loan+\r\n"
			+ "hbao_loan+comp1_loan)as loan from loan_details l where l.emp_id=e.emp_id) else 0.00  end as loan,emp_type,e.designation,working_status,case when flag is not true then 1 else 0 end as flag from emp_details e \r\n"
			+ " Left join confirmedemployees using (emp_id,security_id) left join security_code_master sc on sc.security_code=e.security_id  where confirmed_by_cgm='f' and security_id=:code order by  security_id", nativeQuery = true)
	List<Map<String, String>> EmployeeConfirmation_dist(String code);

	@Transactional
	@Modifying
	@Query(value = "insert into confirmedemployees (emp_id, security_id,flag,designation_code) values(:emp_id,:code,'t',:designation_code)", nativeQuery = true)
	int confirmedemployees_insert(String emp_id, String code, String designation_code);

	@Transactional
	@Modifying
	@Query(value = "update emp_details  set confirmed_by_cgm='t',timestamp=now(),user_login=:user_id where  emp_id =:emp_id", nativeQuery = true)
	int empdetails_update(String user_id, String emp_id);

	@Transactional
	@Modifying
	@Query(value = "update emp_details set confirmed_by_cgm='t' where  emp_id =:emp_id", nativeQuery = true)
	int deleteemployees(String emp_id);

}
