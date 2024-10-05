package by.bsuir.groupqueuefx.repo_Del;

import by.bsuir.groupqueuefx.models.dto.QueueInfo;
import by.bsuir.groupqueuefx.models.entities.QueueEntity;
import by.bsuir.groupqueuefx.models.dto.GroupQueue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QueueRepository extends CrudRepository<QueueEntity, Long> {

	@Query(value = """
				SELECT new by.bsuir.groupqueuefx.models.dto.QueueInfo(
				s.subjectName,
				s.subjectFullName,
				l.date,
				s.startTime,
				l.sortType,
				s.subgroupType,
				pq.passingLabs,
				q.order,
				l.id)
				FROM QueueEntity q
				JOIN q.lessonEntity l
				JOIN l.scheduleEntity s
				LEFT JOIN PreQueueEntity pq ON q.lessonId = pq.lessonId AND q.studentId = pq.studentId
				WHERE q.studentId = ?1
			""")
	List<QueueInfo> findQueueResultsByStudentId(long studentId);

	@Query(value = """
				SELECT new by.bsuir.groupqueuefx.models.dto.QueueInfo(
				s.subjectName,
				s.subjectFullName,
				l.date,
				s.startTime,
				l.sortType,
				s.subgroupType,
				pq.passingLabs,
				0,
				l.id)
				FROM PreQueueEntity pq
				JOIN pq.lessonEntity l
				JOIN l.scheduleEntity s
				LEFT JOIN QueueEntity q ON pq.lessonId = q.lessonId AND pq.studentId = q.studentId
				WHERE pq.studentId = ?1 AND q.id IS NULL
			""")
	List<QueueInfo> findPreQueueResults(long studentId);

	@Query(value = """
					SELECT new by.bsuir.groupqueuefx.models.dto.GroupQueue(
						q.order, p.username,
						p.lastName, p.firstName,
						pq.passingLabs
					)
					FROM QueueEntity q
					INNER JOIN PersonEntity p
						ON p.id = q.studentEntity.personId
					INNER JOIN PreQueueEntity pq
						ON pq.studentId = q.studentId AND pq.lessonId = ?1
					WHERE q.lessonId = ?1
					ORDER BY q.order ASC
					""")
	List<GroupQueue> getGroupQueueByLessonId(long lessonId);
}
