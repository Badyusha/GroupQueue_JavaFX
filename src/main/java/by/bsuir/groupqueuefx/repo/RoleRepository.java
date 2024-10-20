package by.bsuir.groupqueuefx.repo;

import by.bsuir.groupqueuefx.models.entities.RoleEntity;
import by.bsuir.groupqueuefx.enums.entityAttributes.RoleType;
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
