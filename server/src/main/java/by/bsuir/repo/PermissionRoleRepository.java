package by.bsuir.repo;

import by.bsuir.models.entities.PermissionEntity;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRoleRepository extends CrudRepository<PermissionEntity, Long> { }
