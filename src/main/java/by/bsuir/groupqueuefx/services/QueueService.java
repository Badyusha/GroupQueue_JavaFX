package by.bsuir.groupqueuefx.services;

import by.bsuir.groupqueuefx.enums.entityAttributes.SortType;
import by.bsuir.groupqueuefx.models.dto.EncryptedLesson;
import by.bsuir.groupqueuefx.models.dto.GroupQueue;
import by.bsuir.groupqueuefx.models.dto.Pair;
import by.bsuir.groupqueuefx.models.dto.QueueInfo;
import by.bsuir.groupqueuefx.models.entities.LessonEntity;
import by.bsuir.groupqueuefx.models.entities.PreQueueEntity;
import by.bsuir.groupqueuefx.models.entities.QueueEntity;
import by.bsuir.groupqueuefx.repo.LessonRepository;
import by.bsuir.groupqueuefx.repo.PreQueueRepository;
import by.bsuir.groupqueuefx.repo.QueueRepository;
import by.bsuir.groupqueuefx.utils.EncryptionUtil;
import by.bsuir.groupqueuefx.utils.GenerateQueueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueService {
	private final QueueRepository queueRepository;
	private final LessonRepository lessonRepository;
	private final PreQueueRepository preQueueRepository;

	public List<QueueInfo> getQueueInfoByStudentIdGroupId(long studentId) {
		List<QueueInfo> queueResults = queueRepository.findQueueResultsByStudentId(studentId);
		List<QueueInfo> preQueueResults = queueRepository.findPreQueueResults(studentId);

		List<QueueInfo> combinedResults = new ArrayList<>();
		combinedResults.addAll(queueResults);
		combinedResults.addAll(preQueueResults);

		encryptQueueIds(combinedResults);
		return combinedResults;
	}

	public List<GroupQueue> getGroupQueueByLessonId(EncryptedLesson encryptedLesson) {
		String encryptedLessonId = encryptedLesson.getEncryptedLessonId();
		String encryptedLessonIdSeed = encryptedLesson.getEncryptedLessonIdSeed();
		long lessonId = Long.parseLong(EncryptionUtil.decrypt(encryptedLessonId, encryptedLessonIdSeed));

		return queueRepository.getGroupQueueByLessonId(lessonId);
	}

	/**
	 * This method generate queues
	 * and run at the time in each @Scheduled annotation
	 */
	@Schedules({
			@Scheduled(cron = "0 0 8 * * *", zone = "Europe/Minsk"),
			@Scheduled(cron = "0 35 9 * * *", zone = "Europe/Minsk"),
			@Scheduled(cron = "0 25 11 * * *", zone = "Europe/Minsk"),
			@Scheduled(cron = "0 0 13 * * *", zone = "Europe/Minsk"),
			@Scheduled(cron = "0 50 14 * * *", zone = "Europe/Minsk"),
			@Scheduled(cron = "0 25 16 * * *", zone = "Europe/Minsk"),
			@Scheduled(cron = "0 0 18 * * *", zone = "Europe/Minsk"),
			@Scheduled(cron = "0 40 19 * * *", zone = "Europe/Minsk")
	})
	public void generateQueueForUpcomingLessons() {
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTimeOneHourLater = LocalTime.now().plusHours(1);

		List<LessonEntity> upcomingLessons = lessonRepository.findLessonsStartingAt(currentDate, currentTimeOneHourLater);
		for (LessonEntity lesson : upcomingLessons) {
			List<PreQueueEntity> preQueueEntities = preQueueRepository.getPreQueueEntityListByLessonId(lesson.getId());
			List<QueueEntity> queueEntities = generateQueueBasedOnSortType(preQueueEntities, lesson.getSortType());
			queueRepository.saveAll(queueEntities);
		}
	}

	private List<QueueEntity> generateQueueBasedOnSortType(List<PreQueueEntity> preQueueEntities, SortType sortType) {
		return switch (sortType) {
			case SIMPLE -> GenerateQueueUtil.simple(preQueueEntities);
			case RANDOM -> GenerateQueueUtil.random(preQueueEntities);
			case HIGHEST_LAB_COUNT -> GenerateQueueUtil.highestLabCount(preQueueEntities);
			case LOWEST_LAB_COUNT -> GenerateQueueUtil.lowestLabCount(preQueueEntities);
			case HIGHEST_LAB_SUM -> GenerateQueueUtil.highestLabSum(preQueueEntities);
			case LOWEST_LAB_SUM -> GenerateQueueUtil.lowestLabSum(preQueueEntities);
		};
	}

	private void encryptQueueIds(List<QueueInfo> queues) {
		for(QueueInfo queue : queues) {
			Long lessonId = queue.getLessonId();
			if(lessonId == null) {
				continue;
			}
			Pair<String, String> encryptedIdAndSeed = EncryptionUtil.encrypt(lessonId);
			queue.setEncryptedLessonId(encryptedIdAndSeed.getFirst());
			queue.setEncryptedLessonIdSeed(encryptedIdAndSeed.getSecond());
		}
	}
}
