package by.bsuir.models.dto;

import by.bsuir.enums.entityAttributes.DayOfWeek;
import by.bsuir.enums.entityAttributes.SubgroupType;
import by.bsuir.enums.entityAttributes.WeekType;
import by.bsuir.utils.EncryptionUtil;
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

	public Lesson(long lessonId,
				  String encryptedLessonId,
				  String encryptedLessonIdSeed,
				  String subjectName,
				  String subjectFullName,
				  SubgroupType subgroupType,
				  LocalTime startTime,
				  boolean isRegisteredInQueue,
				  Integer numberInQueue,
				  Long queueId,
				  DayOfWeek dayOfWeek,
				  LocalDate date,
				  WeekType weekType) {
		this.lessonId = lessonId;
		this.encryptedLessonId = encryptedLessonId;
		this.encryptedLessonIdSeed = encryptedLessonIdSeed;
		this.subjectName = subjectName;
		this.subjectFullName = subjectFullName;
		this.subgroupType = subgroupType;
		this.startTime = startTime;
		this.isRegisteredInQueue = isRegisteredInQueue;
		this.numberInQueue = numberInQueue;
		this.queueId = queueId;
		this.dayOfWeek = dayOfWeek;
		this.date = date;
		this.weekType = weekType;
	}

	public void encryptId() {
		Pair<String, String> encryptedIdAndSeed = EncryptionUtil.encrypt(this.lessonId);
		this.encryptedLessonId = encryptedIdAndSeed.getFirst();
		this.encryptedLessonIdSeed = encryptedIdAndSeed.getSecond();
	}
}
