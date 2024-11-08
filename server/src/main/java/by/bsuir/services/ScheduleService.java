package by.bsuir.services;

import by.bsuir.api.BsuirAPI;
//import by.bsuir.controllers.ScheduleController;
import by.bsuir.enums.entityAttributes.DayOfWeek;
import by.bsuir.enums.entityAttributes.WeekType;
import by.bsuir.exceptions.entityExceptions.ScheduleException;
import by.bsuir.models.dto.*;
import by.bsuir.models.entities.ScheduleEntity;
import by.bsuir.repo.GroupRepository;
import by.bsuir.repo.ScheduleRepository;
import by.bsuir.utils.GenerateQueueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;
	private final GroupRepository groupRepository;
	private final LessonService lessonService;
	private final StudentService studentService;

	public List<GroupSchedule> getGroupSchedulesByGroupId(long groupId) {
		return scheduleRepository.getGroupSchedulesByGroupId(groupId);
	}

	public Schedule getDayOfWeekSchedule(long studentId, long groupId) {
		List<Lesson> lessonList = lessonService.getScheduleInfoByStudentIdGroupId(studentId, groupId);
		if (lessonList.isEmpty()) {
			throw new ScheduleException("there is no schedule for groupId=" + groupId);
		}

		WeekType weekType = lessonList.get(0).getWeekType();
		return fillInSchedule(lessonList, weekType);
	}

	private Schedule fillInSchedule(List<Lesson> lessonList, WeekType weekType) {
		Schedule schedule = new Schedule(weekType);
		for(Lesson lesson : lessonList) {
			DayOfWeek dayOfWeek = lesson.getDayOfWeek();
			DayOfWeekScheduled dayOfWeekScheduled = switch(dayOfWeek) {
				case MONDAY -> schedule.getMonday();
				case TUESDAY -> schedule.getTuesday();
				case WEDNESDAY -> schedule.getWednesday();
				case THURSDAY -> schedule.getThursday();
				case FRIDAY -> schedule.getFriday();
				case SATURDAY -> schedule.getSaturday();
				case SUNDAY -> schedule.getSunday();
			};
			lesson.setRegistrationOpen(GenerateQueueUtil.isRegistrationOpen(dayOfWeek, lesson.getStartTime()));
			dayOfWeekScheduled.addLesson(lesson);
		}
		return schedule;
	}

	//	add records into schedule and lesson tables ===
	public int getCurrentWeek() {
		return BsuirAPI.getCurrentWeek();
	}

	public void addRecordsForNewGroupByGroupNumber(int groupNumber) {
		int scheduleWeekNumber = getCurrentWeek();
		if (isTimeToGenerateNextWeekSchedule()) {
			scheduleWeekNumber = WeekType.getNextWeekNumber(scheduleWeekNumber);
		}

		Long groupId = groupRepository.getGroupIdByNumber(groupNumber);
		List<ScheduleEntity> scheduleEntities = BsuirAPI.getScheduleEntities(groupId, groupNumber);
		scheduleRepository.saveAll(scheduleEntities);

		WeekType weekType = WeekType.getWeekTypeByNumber(scheduleWeekNumber);
		List<ScheduleEntity> scheduleEntityList =
				getScheduleEntityByGroupNumberWeekType(groupNumber, weekType);
		lessonService.addLessonByScheduleList(weekType, scheduleEntityList);
	}

	public List<ScheduleEntity> getScheduleEntityByGroupNumberWeekType(int groupNumber, WeekType week) {
		return scheduleRepository.getScheduleEntityByGroupNumberWeekType(groupNumber, week);
	}

	private boolean isTimeToGenerateNextWeekSchedule() {
		DayOfWeek dayOfWeek = DayOfWeek.getDayOfWeekFromCalendar();
		if(!dayOfWeek.equals(DayOfWeek.SUNDAY)) {
			return false;
		}

		LocalTime generateNewScheduleTime = DayOfWeek.TIME_TO_GENERATE_NEW_SCHEDULE;
		return !LocalTime.now().isBefore(generateNewScheduleTime);
	}

	// SCHEDULED
	/**
	method calls every Sunday at 22:00
	*/
	@Scheduled(cron = "0 0 22 * * SUN", zone = "Europe/Moscow")
	@Transactional
	public void updateLessonsForNextWeek() {
		lessonService.deleteAll();
		addLessonsForNextWeek();
	}

	private void addLessonsForNextWeek() {
		WeekType weekType = WeekType.getNextWeekType();
		List<ScheduleEntity> scheduleEntityList = scheduleRepository.getScheduleEntityListByWeekType(weekType);
		lessonService.addLessonByScheduleList(weekType, scheduleEntityList);
	}
}
