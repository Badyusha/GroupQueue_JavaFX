package by.bsuir.groupqueuefx.repo;

import by.bsuir.groupqueuefx.models.entities.PersonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
	@Query(value = """
					SELECT s.personEntity
					FROM StudentEntity s
					WHERE s.id = ?1
					""")
	PersonEntity getPersonByStudentId(long studentId);
}
