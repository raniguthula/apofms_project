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
public interface ReportsServicesRepo extends JpaRepository<BankDetails_Model, Integer> {

	@Query(value = "SELECT role_id FROM user_roles  where user_id=:user_id", nativeQuery = true)
	String getRoleNames(String user_id);

//	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n" + 
//			"left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'ENTRY%' order by priority", nativeQuery = true)
//	List<Map<String, String>> getServiceNames(String element);
//	
//	
//	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n" + 
//			"left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'REPORT%' order by priority", nativeQuery = true)
//	List<Map<String, String>> getReportNames(String element);

	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n"
			+ "left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'PAYROLL-REPORTS%' order by priority", nativeQuery = true)
	List<Map<String, String>> getPayrollReportNames(String element);

	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n"
			+ "left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'BRS%' order by priority", nativeQuery = true)
	List<Map<String, String>> getBRSForms(String element);

	// rani
	@Query(value = "select target, service_name as service_display_name,service_id from services where service_id in (select distinct(service_id) from role_services\r\n"
			+ "where role_id in (select role_id from user_roles where user_id=:user_id)) and  service_name like 'REPORTS::%' order by priority", nativeQuery = true)
	Map<String, Object> getRaniServices(String user_id);

	// Chnage for live
	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n"
			+ "left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'ENTRY%'  order by priority", nativeQuery = true)
	List<Map<String, String>> getServiceNames(String element);

	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n"
			+ "left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'REPORT%'  order by priority", nativeQuery = true)
	List<Map<String, String>> getReportNames(String element);

	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n"
			+ "left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'MANAGER APPROVAL%'  order by priority", nativeQuery = true)
	List<Map<String, String>> getManagerApproveForms(String element);

	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n"
			+ "left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'Housing%'  order by priority", nativeQuery = true)

	List<Map<String, String>> getHousingReports(String element);

	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n"
			+ "left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'GENERAL SERVICES%'  order by priority", nativeQuery = true)
	List<Map<String, String>> getGeneralServices(String element);

	@Query(value = "SELECT s.service_id,s.service_name,target FROM services s\r\n"
			+ "left join role_services rs on rs.service_id=s.service_id where rs.role_id=cast(:element as int) and s.service_name like 'ADMIN SERVICES%'  order by priority", nativeQuery = true)
	List<Map<String, String>> getAdminServices(String element);

	@Query(value = "select user_id from users where user_id=:username", nativeQuery = true)
	String getUser_ID(String username);

	// ADDED PARENT IDS
	@Query(value = "select distinct parent_id service_id from sb_services where service_id in ( select aa.service_id from sb_services aa \r\n"
			+ "left join sb_role_services r on aa.service_id=r.service_id\r\n"
			+ "join (select a.service_id from sb_services  a left join sb_role_services b on a.service_id=b.service_id \r\n"
			+ "where role_id=cast(:element2 as integer) and parent_id=2 group by a.service_id) a on a.service_id =aa.parent_id\r\n"
			+ "where role_id=cast(:element2 as integer)) order by parent_id", nativeQuery = true)
	List<String> getServiceIdsForParentReports(String element2);

	@Query(value = "select a.service_id,service_name from sb_services a left join sb_role_services b on a.service_id=b.service_id \r\n"
			+ "where a.service_id=cast(:element as integer) and role_id=cast(:element2 as integer) order by priority", nativeQuery = true)
	List<Map<String, String>> getServiceNames(String element, String element2);

	@Query(value = "select a.service_id,service_name,target from sb_services a left join sb_role_services b on a.service_id=b.service_id \r\n"
			+ "where parent_id=cast(:element as integer) and role_id=cast(:element2 as integer) order by priority", nativeQuery = true)
	List<Map<String, String>> getServicesList(String element, String element2);

	@Query(value = "select distinct parent_id service_id from sb_services where service_id in ( select aa.service_id from sb_services aa \r\n"
			+ "left join sb_role_services r on aa.service_id=r.service_id\r\n"
			+ "join (select a.service_id from sb_services  a left join sb_role_services b on a.service_id=b.service_id \r\n"
			+ "where role_id=cast(:element2 as integer) and parent_id=1 group by a.service_id) a on a.service_id =aa.parent_id\r\n"
			+ "where role_id=cast(:element2 as integer)) order by parent_id", nativeQuery = true)
	List<String> getServiceIdsForParentServices(String element2);

	@Query(value = "select distinct parent_id service_id from sb_services where service_id in ( select aa.service_id from sb_services aa \r\n"
			+ "left join sb_role_services r on aa.service_id=r.service_id\r\n"
			+ "join (select a.service_id from sb_services  a left join sb_role_services b on a.service_id=b.service_id \r\n"
			+ "where role_id=cast(:element2 as integer) and parent_id=4 group by a.service_id) a on a.service_id =aa.parent_id\r\n"
			+ "where role_id=cast(:element2 as integer)) order by parent_id", nativeQuery = true)

	List<String> getServiceIdsForParentBRS(String element2);

	
	@Query(value = "select distinct parent_id service_id from sb_services where service_id in ( select aa.service_id from sb_services aa \r\n"
			+ "left join sb_role_services r on aa.service_id=r.service_id\r\n"
			+ "join (select a.service_id from sb_services  a left join sb_role_services b on a.service_id=b.service_id \r\n"
			+ "where role_id=cast(:element2 as integer) and parent_id=3 group by a.service_id) a on a.service_id =aa.parent_id\r\n"
			+ "where role_id=cast(:element2 as integer)) order by parent_id", nativeQuery = true)
	List<String> getServiceIdsForParentPayrollReports(String element2);
	
	@Query(value = "select distinct parent_id service_id from sb_services where service_id in ( select aa.service_id from sb_services aa \r\n"
			+ "left join sb_role_services r on aa.service_id=r.service_id\r\n"
			+ "join (select a.service_id from sb_services  a left join sb_role_services b on a.service_id=b.service_id \r\n"
			+ "where role_id=cast(:element2 as integer) and parent_id=7 group by a.service_id) a on a.service_id =aa.parent_id\r\n"
			+ "where role_id=cast(:element2 as integer)) order by parent_id", nativeQuery = true)
	List<String> getParentApprovals(String element2);
	@Query(value = "select role_id from user_roles where user_id=:user_id", nativeQuery = true)
	String getRoleId(String user_id);

	@Query(value = "select service_id,service_name from sb_services where parent_id in (1,2,3,4,5,6) order by service_name", nativeQuery = true)
	List<Map<String, Object>> getParentIDsList();

	@Query(value = "select service_id,(case when parent_id!=0 then (select service_name from sb_services where service_id=s.parent_id) else 'Parent' end) as parent_id,\r\n"
			+ "priority,target,service_name from sb_services s where delete_flag='false' order by parent_id,priority,service_id", nativeQuery = true)
	List<Map<String, Object>> getAddServiceServicesList();

	@Query(value = "select service_id,parent_id,priority,target,service_name from sb_services where service_id=:service_id", nativeQuery = true)
	List<Map<String, Object>> getEditServiceData(int service_id);

	@Modifying
	@Transactional
	@Query(value = "insert into sb_services_bkp(service_id,parent_id,priority,target,service_name) "
			+ "select service_id,parent_id,priority,target,service_name from sb_services \r\n"
			+ "where service_id=:service_id", nativeQuery = true)
	int insert_service_bck(int service_id);

	@Modifying
	@Transactional
	@Query(value = "update sb_services set parent_id=:parent_id,priority=:priority,target=:target,service_name=:service_name "
			+ "where service_id=:service_id", nativeQuery = true)
	int update_services(int service_id, int parent_id, int priority, String target, String service_name);

	@Query(value = "UPDATE sb_services SET delete_flag='true' WHERE service_id=:service_id", nativeQuery = true)
	int update_Service_false(int service_id);

	/*---------------------------------------------------- ROLE SERVICE MAPPING ----------------------------------------------------*/

	@Query(value = "select role_id,role_name from roles order by role_id", nativeQuery = true)
	List<Map<String, Object>> getRolesList();

	@Query(value = "select service_id,service_name,(case when a.service_id is null then '' else 'checked' end) as checked from sb_services \r\n"
			+ "left join (select service_id from sb_role_services where role_id=:role_id) a using(service_id) order by service_id", nativeQuery = true)
	List<Map<String, Object>> getServicesListWithRoleID(int role_id);

	@Modifying
	@Transactional
	@Query(value = "delete from sb_role_services where role_id=:role_id", nativeQuery = true)
	int delete_roles(int role_id);

	@Modifying
	@Transactional
	// @Query(value = "insert into role_services(role_id,service_id)
	// values(:role_id,any(string_to_array(:serviceIds,','))", nativeQuery = true)
	@Query(value = "insert into sb_role_services(role_id,service_id) values(:role_id,:serviceIds)", nativeQuery = true)
	int insert_roles(int role_id, int serviceIds);

}
