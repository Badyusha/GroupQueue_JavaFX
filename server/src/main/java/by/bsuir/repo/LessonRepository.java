package by.bsuir.repo;

import by.bsuir.models.dto.Lesson;
import by.bsuir.models.entities.LessonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface LessonRepository extends CrudRepository<LessonEntity, Long> {
	@Query(value = """
					SELECT lesson
					FROM LessonEntity lesson
					WHERE lesson.id = ?1
			""")
	LessonEntity getLessonEntityById(long scheduleId);

	@Query("""
			SELECT new by.bsuir.models.dto.Lesson(
			lesson.id,
			null,
			null,
			schedule.subjectName,
			schedule.subjectFullName,
			schedule.subgroupType,
			schedule.startTime,
			CASE
				WHEN preQueue.id IS NOT NULL
					THEN TRUE
					ELSE FALSE
			END,
			queue.order,
			queue.id,
			schedule.dayOfWeek,
			lesson.date,
			schedule.weekType
			)
			FROM LessonEntity lesson
			INNER JOIN ScheduleEntity schedule
				ON schedule.id = lesson.scheduleId
			LEFT JOIN PreQueueEntity preQueue
				ON preQueue.lessonId = lesson.id AND preQueue.studentId = ?1
			LEFT JOIN QueueEntity queue
				ON queue.lessonId = lesson.id AND queue.studentId = ?1
			WHERE schedule.groupId = ?2
			ORDER BY schedule.startTime ASC
			""")
	List<Lesson> getScheduleInfoByStudentIdGroupId(long studentId, long groupId);

	@Query("""
			SELECT l
			FROM LessonEntity l
			WHERE l.date = ?1 AND l.scheduleEntity.startTime = ?2
			""")
	List<LessonEntity> findLessonsStartingAt(LocalDate date, LocalTime time);
}

