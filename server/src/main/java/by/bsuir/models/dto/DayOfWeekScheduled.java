package by.bsuir.models.dto;

import by.bsuir.enums.entityAttributes.DayOfWeek;
import by.bsuir.enums.entityAttributes.WeekType;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class DayOfWeekScheduled {
	private LocalDate date;
	private List<Lesson> lessons;

	public DayOfWeekScheduled(WeekType weekType, DayOfWeek dayOfWeek) {
		lessons = new ArrayList<>();
		date = DayOfWeek.getLessonDate(dayOfWeek, weekType);
	}

	public void addLesson(Lesson lesson) {
		lessons.add(lesson);
	}
}
