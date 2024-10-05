package by.bsuir.groupqueuefx.models.dto;

import by.bsuir.groupqueuefx.models.entities.PreQueueEntity;
import lombok.Data;

@Data
public class PreQueue {
	long id;
	long lessonId;
	long studentId;
	String dayOfWeek;
	String startTime;
	byte[] passingLabs;

	public PreQueueEntity toPreQueueEntity(long studentId) {
		return new PreQueueEntity(lessonId, studentId, passingLabs);
	}
}
