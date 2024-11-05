package by.bsuir.models.dto;

import lombok.Data;

@Data
public class PreQueue {
	long id;
	long lessonId;
	String encryptedLessonId;
	String encryptedLessonIdSeed;
	long studentId;
	String dayOfWeek;
	String startTime;
	byte[] passingLabs;
}
