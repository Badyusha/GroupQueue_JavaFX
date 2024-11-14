package by.bsuir.models.dto;

import by.bsuir.enums.entityAttributes.SortType;
import by.bsuir.enums.entityAttributes.SubgroupType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class GroupSchedule implements Serializable {
	private long lessonId;
	private String subjectName;
	private String subjectFullName;
	private SubgroupType subgroupType;
	private LocalDate date;
	private LocalTime startTime;
	private SortType sortType;

	public GroupSchedule(long lessonId, SortType sortType) {
		this.lessonId = lessonId;
		this.sortType = sortType;
	}
}
