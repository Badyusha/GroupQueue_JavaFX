package by.bsuir.repo;

import by.bsuir.models.dto.GroupSchedule;
import by.bsuir.models.entities.ScheduleEntity;
import by.bsuir.enums.entityAttributes.WeekType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<ScheduleEntity, Long> {
	@Query(value = """
					SELECT schedule
					FROM ScheduleEntity schedule
					INNER JOIN GroupEntity group
					ON group.id = schedule.groupId
					WHERE group.number = ?1 AND schedule.weekType = ?2
				""")
	List<ScheduleEntity> getScheduleEntityByGroupNumberWeekType(long groupId, WeekType week);

	@Query(value = """
				SELECT new by.bsuir.models.dto.GroupSchedule(
				l.id,
				s.subjectName,
				s.subjectFullName,
				s.subgroupType,
				l.date,
				s.startTime,
				l.sortType)
				FROM ScheduleEntity s
				INNER JOIN LessonEntity l
				ON l.scheduleId = s.id
				WHERE s.groupId = ?1
	""")
	List<GroupSchedule> getGroupSchedulesByGroupId(long groupId);

	@Query(value = """
					SELECT s
					FROM ScheduleEntity s
					WHERE s.weekType = ?1
					""")
	List<ScheduleEntity> getScheduleEntityListByWeekType(WeekType weekType);
}