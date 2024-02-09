
package in_Apcfss.Apofms.api.ofmsapi.Repositories;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	@Transactional
	@Modifying
	@Query(value="UPDATE users set password=:bcryptHash where user_id=:user_id",nativeQuery = true)
	int updatePassword(String user_id, String bcryptHash);






}
