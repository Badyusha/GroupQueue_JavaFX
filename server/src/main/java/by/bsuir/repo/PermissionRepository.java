package by.bsuir.repo;

import by.bsuir.models.entities.PermissionEntity;
import by.bsuir.enums.entityAttributes.PermissionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {
	@Query("""
				SELECT count(1)
				FROM PermissionRoleEntity pr
				INNER JOIN PermissionEntity p
				ON p.id = pr.permissionId
				WHERE p.permissionType = ?1 and pr.roleId = ?2
			""")
	int getPermissionCountByPermissionNameRoleId(PermissionType permission, long roleId);

	default boolean isActionAllowed(PermissionType permission, long roleId) {
		int res = getPermissionCountByPermissionNameRoleId(permission, roleId);
		return getPermissionCountByPermissionNameRoleId(permission, roleId) == 1;
	}
}
