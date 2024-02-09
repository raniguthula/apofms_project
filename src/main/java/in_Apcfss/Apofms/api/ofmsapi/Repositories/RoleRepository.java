package in_Apcfss.Apofms.api.ofmsapi.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in_Apcfss.Apofms.api.ofmsapi.models.ERole;
import in_Apcfss.Apofms.api.ofmsapi.models.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
