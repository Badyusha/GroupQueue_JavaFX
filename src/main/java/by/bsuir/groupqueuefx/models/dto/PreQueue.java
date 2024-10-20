package by.bsuir.groupqueuefx.models.dto;

import by.bsuir.groupqueuefx.models.entities.PreQueueEntity;
import by.bsuir.groupqueuefx.utils.EncryptionUtil;
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

	public PreQueueEntity toPreQueueEntity(long studentId) {
		long lessonId = getDecryptedLessonId();
		return new PreQueueEntity(lessonId, studentId, passingLabs);
	}

	public long getDecryptedLessonId() {
		return Long.parseLong(EncryptionUtil.decrypt(this.encryptedLessonId, this.encryptedLessonIdSeed));
	}
}
