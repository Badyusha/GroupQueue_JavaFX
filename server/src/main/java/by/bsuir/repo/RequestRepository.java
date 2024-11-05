package by.bsuir.repo;

import by.bsuir.models.dto.Request;
import by.bsuir.models.entities.RequestEntity;
import by.bsuir.enums.entityAttributes.RequestType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<RequestEntity, Long> {
	@Query(value = """
					SELECT new by.bsuir.models.dto.Request(
					s.id,
					s.personEntity.lastName,
					s.personEntity.firstName,
					s.personEntity.username,
					role.name,
					g.number,
					r.requestType)
					FROM RequestEntity r
					INNER JOIN StudentEntity s
					ON s.id = r.studentId
					INNER JOIN GroupEntity g
					ON g.id = s.groupId
					INNER JOIN RoleEntity role
					ON s.roleId = role.id
				""")
	List<Request> getRequests();

	@Query(value = """
					SELECT r.id
					FROM RequestEntity r
					WHERE r.requestType = ?1 AND r.studentId = ?2
					""")
	long getRequestIdByRequestTypeStudentId(RequestType requestType, long studentId);
}
