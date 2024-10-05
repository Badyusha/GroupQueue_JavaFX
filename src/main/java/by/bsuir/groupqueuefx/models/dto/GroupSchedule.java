package by.bsuir.groupqueuefx.models.dto;

import by.bsuir.groupqueuefx.enums.entityAttributes.SortType;
import by.bsuir.groupqueuefx.enums.entityAttributes.SubgroupType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class GroupSchedule {
	private long lessonId;
	private String subjectName;
	private String subjectFullName;
	private SubgroupType subgroupType;
	private LocalDate date;
	private LocalTime startTime;
	private SortType sortType;
}
