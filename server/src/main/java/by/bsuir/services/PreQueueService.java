package by.bsuir.services;

import by.bsuir.enums.entityAttributes.DayOfWeek;
import by.bsuir.exceptions.entityExceptions.QueueException;
import by.bsuir.models.dto.PreQueue;
import by.bsuir.models.entities.PreQueueEntity;
import by.bsuir.repo.PreQueueRepository;
import by.bsuir.utils.GenerateQueueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class PreQueueService {
	private final PreQueueRepository preQueueRepository;

	public void removeStudentFromPreQueueByLessonId(long studentId,
													long lessonId) {
		preQueueRepository.delete(preQueueRepository.getPreQueueEntityByStudentIdLessonId(studentId, lessonId));
	}

	public void addStudentToPreQueue(long studentId, PreQueue preQueue) throws QueueException {
		LocalTime startTime = LocalTime.parse(preQueue.getStartTime());
		DayOfWeek dayOfWeek = DayOfWeek.getDayOfWeekByName(preQueue.getDayOfWeek());

		if(GenerateQueueUtil.isRegistrationOpen(dayOfWeek, startTime)) {
			preQueueRepository.save(preQueue.toPreQueueEntity(studentId));
			return;
		}
		throw new QueueException("cannot register to pre_queue because of current day of week or current time");
	}

	public void changePassingLabs(long studentId, PreQueue preQueue) {
		long lessonId = preQueue.getDecryptedLessonId();
		PreQueueEntity preQueueEntity = preQueueRepository.getPreQueueEntityByStudentIdLessonId(studentId, lessonId);

		preQueueEntity.setPassingLabs(preQueue.getPassingLabs());
		preQueueRepository.save(preQueueEntity);
	}
}