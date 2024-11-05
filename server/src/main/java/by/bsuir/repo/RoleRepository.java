package by.bsuir.repo;

import by.bsuir.models.entities.RoleEntity;
import by.bsuir.enums.entityAttributes.RoleType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
	@Query(value = """
					SELECT role.id
					FROM RoleEntity role
					WHERE role.name = ?1
				""")
	Long getRoleIdByType(RoleType roleType);
}
