package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in_Apcfss.Apofms.api.ofmsapi.models.Role;
import in_Apcfss.Apofms.api.ofmsapi.models.User;
import in_Apcfss.Apofms.api.ofmsapi.models.UsersSecurity;

@Repository
public interface UsersSecurityRepo extends JpaRepository<User, String> {

	@Query(value = " select *  from ofms_users where user_id=:user_id ", nativeQuery = true)
	UsersSecurity getUsereligibility(String user_id);

	// and password=md5('Apshcl@2022')
	// user_id='CON0000005'
//    @Query(value = "SELECT * FROM users WHERE user_id=?1", nativeQuery = true)
	@Query(value = "SELECT u.user_id,u.display_name,u.password,u.security_id,u.flag,u.pwd_change_date,u.is_active,ur.role_id FROM users u\r\n"
			+ "left join user_roles ur on ur.user_id=u.user_id\r\n" + "WHERE u.user_id=:user_id", nativeQuery = true)
	public UsersSecurity getUserByUsersname(@Param("user_id") String username);

	@Query(value = "SELECT u.user_id,u.display_name,u.password,u.security_id,u.flag,u.pwd_change_date,u.is_active,ur.role_id FROM users u\r\n"
			+ "left join user_roles ur on ur.user_id=u.user_id\r\n" + "WHERE u.user_id=:userid", nativeQuery = true)
	UsersSecurity findByUserid(String userid);

	@Transactional
	@Modifying
	@Query(value = "update apofms_login_users set  password=:password where username=:user_id", nativeQuery = true)
	int Change_pass_apofms_login_users(String user_id, String password);

	@Transactional
	@Modifying
	@Query(value = "update ofms_users set passwor:password and pwd_change_ip=cast(:ip_address as inet) and pwd_change_date=:currentTimeStamp where user", nativeQuery = true)
	int change_pass_changePasswordUserRepo(String password, Timestamp currentTimeStamp, String ip_address);

	@Query(value = "SELECT  'P'||security_id||to_char(current_date,'ddmmyy')||(count(*)+1)as payment_receipt_id from payments_receipts \r\n"
			+ "where  security_id=:security_id group by security_id,current_date", nativeQuery = true)
	String getPayment_Receipt_Id(String security_id);

//Services List
	@Query(value = "select target, service_name as service_display_name from services where service_id in (select distinct(service_id) from role_services \r\n"
			+ "where role_id in (select role_id from user_roles where user_id=:user_id)) order by priority", nativeQuery = true)
	List<Map<String, String>> ServicesListBy_UserId(String user_id);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO public.users_log(user_id, ip, timestamp, is_user_loggedin) \r\n"
			+ "    VALUES(:username, cast(:ip_address as inet), now(), true)", nativeQuery = true)
	int insert_User_logined_in(String username, String ip_address);

	@Transactional
	@Modifying
	@Query(value = "update users_log set is_user_loggedin='true', ip=cast(:ip_address as inet),timestamp=now() where user_id=:user_id", nativeQuery = true)
	int update_userslog_login(String user_id, String ip_address);

	@Transactional
	@Modifying
	@Query(value = "UPDATE public.users_log set ip=cast(?2 as inet), timestamp=now(), is_user_loggedin=false WHERE user_id=?1", nativeQuery = true)
	int OfmsLogout(String user_id, String ip_address);

	@Query(value = "SELECT COUNT(*) AS logged_in_users_count from (\r\n"
			+ "select * FROM users_log where user_id =:username and user_id!='ADMIN'\r\n"
			+ "and is_user_loggedin='true' and timestamp >= NOW() - INTERVAL '30 minutes'\r\n"
			+ "GROUP BY timestamp,user_id,ip,is_user_loggedin order by timestamp DESC limit 1) a", nativeQuery = true)
	int getcount_users(String username);

	@Query(value = "SELECT COUNT(*) FROM users_log where user_id=:user_id", nativeQuery = true)
	int getcount_users_log(String user_id);

	@Query(value = "SELECT distinct ur.role_id FROM services s\r\n"
			+ "left join role_services rs on rs.service_id=s.service_id\r\n"
			+ "left join user_roles ur on ur.role_id=rs.role_id where ur.user_id=:user_id", nativeQuery = true)
	String getRoleId(String user_id);

//	@Query(value = "SELECT distinct ur.security_id FROM services s\r\n"
//			+ "left join role_services rs on rs.service_id=s.service_id\r\n"
//			+ "left join user_roles ur on ur.role_id=rs.role_id where ur.user_id=:user_id", nativeQuery = true)
	@Query(value = "SELECT  distinct security_id FROM users  where user_id=:user_id", nativeQuery = true)
	String GetSecurity_Id(String user_id);

	@Query(value = "SELECT distinct role_id FROM  user_roles where user_id=:user_id", nativeQuery = true)
	String GetRole_Id(String user_id);
	@Query(value = "SELECT  s.service_id,s.service_name FROM services s "
			+ "LEFT JOIN role_services rs ON rs.service_id = s.service_id "
			+ "LEFT JOIN user_roles ur ON ur.role_id = rs.role_id "
			+ "LEFT JOIN users u ON ur.user_id = u.user_id WHERE u.user_id = :userId", nativeQuery = true)
	List<Map<String, String>> getServiceIdsByUserId(@Param("userId") String userId);

	@Query(value = "select target, service_name as service_display_name from services where service_id in (select distinct(service_id) from role_services\r\n"
			+ "where role_id in (select role_id from user_roles where user_id=:user_id) ) and service_name like 'REPORTS%' OR service_name like 'PAYROLL%' order by priority", nativeQuery = true)
	List<Map<String, String>> ServicesListBy_DM(String user_id);

	@Query(value = "SELECT distinct cast(r.role_id as int4),r.role_name FROM users u\r\n"
			+ "left join user_roles ur on ur.security_id=u.security_id\r\n"
			+ "left join  roles r on r.role_id=ur.role_id where u.user_id=:user_id", nativeQuery = true)
	List<Map<String, Object>> getRoleIdsByServiceIds(String user_id);

	@Query(value = "SELECT count(*) FROM users where user_id=:user_id and password=:hashedoldpassword ", nativeQuery = true)
	int userCount(String user_id, String hashedoldpassword);

	@Transactional
	@Modifying
	@Query(value = "update users  set password=:hashednewpassword,pwd_change_date=now(),flag=true where  user_id=:user_id", nativeQuery = true)
	int UpdatePassword(String hashednewpassword, String user_id);

	@Query(value="SELECT flag FROM users where user_id=:username",nativeQuery = true)
	String getflag_password_change(String username);

//	@Query(value = "SELECT DISTINCT ur.role_id FROM services s " +
//	        "LEFT JOIN role_services rs ON rs.service_id = s.service_id " +
//	        "LEFT JOIN user_roles ur ON ur.role_id = rs.role_id " +
//	        "LEFT JOIN users u ON ur.user_id = u.user_id WHERE s.service_id IN :serviceIds", nativeQuery = true)
//	List<Role> getRoleIdsByServiceIds(@Param("serviceIds") String user_id);

}
