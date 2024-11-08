package by.bsuir.models.dto;

import by.bsuir.enums.entityAttributes.DayOfWeek;
import by.bsuir.models.entities.PreQueueEntity;
import by.bsuir.utils.EncryptionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PreQueue implements Serializable {
	long id;
	long lessonId;
	String encryptedLessonId;
	String encryptedLessonIdSeed;
	long studentId;
	String dayOfWeek;
	String startTime;
	byte[] passingLabs;

	public PreQueue(long studentId, long lessonId) {
		this.studentId = studentId;
		this.lessonId = lessonId;
	}

	public PreQueue(long studentId, long lessonId, String startTime, DayOfWeek dayOfWeek, byte[] passingLabs) {
		this.studentId = studentId;
		this.lessonId = lessonId;
		this.passingLabs = passingLabs;
		this.dayOfWeek = dayOfWeek.toString().toLowerCase();
		this.startTime = startTime;
	}

	public PreQueueEntity toPreQueueEntity(long studentId) {
		return new PreQueueEntity(lessonId, studentId, passingLabs);
	}

	public long getDecryptedLessonId() {
		return Long.parseLong(EncryptionUtil.decrypt(this.encryptedLessonId, this.encryptedLessonIdSeed));
	}
}
