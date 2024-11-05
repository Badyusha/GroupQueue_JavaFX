package by.bsuir.models.dto;

import by.bsuir.enums.entityAttributes.DayOfWeek;
import by.bsuir.enums.entityAttributes.SubgroupType;
import by.bsuir.enums.entityAttributes.WeekType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Lesson {
	private long lessonId;
	private String encryptedLessonId;
	private String encryptedLessonIdSeed;
	private String subjectName;
	private String subjectFullName;
	private SubgroupType subgroupType;
	private LocalTime startTime;
	private boolean isRegisteredInQueue;
	private Integer numberInQueue;
	private Long queueId;

	private DayOfWeek dayOfWeek;
	private LocalDate date;
	private WeekType weekType;
	private boolean isRegistrationOpen;
}
