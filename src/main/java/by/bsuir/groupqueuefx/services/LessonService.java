package by.bsuir.groupqueuefx.services;

import by.bsuir.groupqueuefx.enums.entityAttributes.DayOfWeek;
import by.bsuir.groupqueuefx.enums.entityAttributes.SortType;
import by.bsuir.groupqueuefx.enums.entityAttributes.WeekType;
import by.bsuir.groupqueuefx.exceptions.entityExceptions.ScheduleException;
import by.bsuir.groupqueuefx.models.dto.GroupSchedule;
import by.bsuir.groupqueuefx.models.dto.Lesson;
import by.bsuir.groupqueuefx.models.entities.LessonEntity;
import by.bsuir.groupqueuefx.models.entities.ScheduleEntity;
import by.bsuir.groupqueuefx.repo.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
	private final LessonRepository lessonRepository;

	public void deleteAll() {
		lessonRepository.deleteAll();
	}

	public void changeSortType(GroupSchedule groupSchedule) {
		long lessonId = groupSchedule.getLessonId();
		SortType sortType = groupSchedule.getSortType();

		LessonEntity lessonEntity = lessonRepository.getLessonEntityById(lessonId);
		lessonEntity.setSortType(sortType);

		lessonRepository.save(lessonEntity);
	}

	public List<Lesson> getScheduleInfoByStudentIdGroupId(long studentId, long groupId) {
		return lessonRepository.getScheduleInfoByStudentIdGroupId(studentId, groupId);
	}

	public void addLessonByScheduleList(WeekType weekType, List<ScheduleEntity> scheduleEntityList) {
		if(scheduleEntityList == null || scheduleEntityList.isEmpty()) {
			throw new ScheduleException("scheduleEntityList is null or empty. Probably there is no lessons on the week");
		}

		for(ScheduleEntity scheduleEntity : scheduleEntityList) {
			DayOfWeek dayOfWeek = scheduleEntity.getDayOfWeek();
			lessonRepository.save(new LessonEntity(
					scheduleEntity.getId(),
					SortType.SIMPLE,
					DayOfWeek.getLessonDate(dayOfWeek, weekType)
			));
		}
	}
}
