package by.bsuir.groupqueuefx.services;

import by.bsuir.groupqueuefx.enums.entityAttributes.DayOfWeek;
import by.bsuir.groupqueuefx.exceptions.entityExceptions.QueueException;
import by.bsuir.groupqueuefx.models.dto.EncryptedLesson;
import by.bsuir.groupqueuefx.models.dto.PreQueue;
import by.bsuir.groupqueuefx.models.entities.PreQueueEntity;
import by.bsuir.groupqueuefx.repo.PreQueueRepository;
import by.bsuir.groupqueuefx.utils.EncryptionUtil;
import by.bsuir.groupqueuefx.utils.GenerateQueueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class PreQueueService {
	private final PreQueueRepository preQueueRepository;

	public void removeStudentFromPreQueueByLessonId(long studentId,
													EncryptedLesson encryptedLesson) {
		long lessonId = Long.parseLong(EncryptionUtil.decrypt(encryptedLesson.getEncryptedLessonId(),
																encryptedLesson.getEncryptedLessonIdSeed()));
		System.err.println(lessonId);
		preQueueRepository.delete(preQueueRepository.getPreQueueEntityByStudentIdLessonId(studentId, lessonId));
	}

	public void addStudentToPreQueue(long studentId, PreQueue preQueue) {
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