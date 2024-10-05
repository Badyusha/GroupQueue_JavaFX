package by.bsuir.groupqueuefx.models.entities;

import by.bsuir.groupqueuefx.enums.entityAttributes.DayOfWeek;
import by.bsuir.groupqueuefx.enums.entityAttributes.SubgroupType;
import by.bsuir.groupqueuefx.enums.entityAttributes.WeekType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "`schedule`")
public class ScheduleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "subject_name", columnDefinition = "VARCHAR(20)")
	private String subjectName;

	@Column(name = "subject_full_name", columnDefinition = "VARCHAR(100)")
	private String subjectFullName;

	@Enumerated(EnumType.STRING)
	@Column(name = "subgroup_type", columnDefinition = "ENUM('FIRST','SECOND','ALL')")
	private SubgroupType subgroupType;

	@Column(name = "start_time", columnDefinition = "DATETIME")
	private LocalTime startTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id", insertable = false, updatable = false, nullable = false)
	private GroupEntity groupEntity;

	@Column(name = "group_id", columnDefinition = "BIGINT")
	private Long groupId;

	@Enumerated(EnumType.STRING)
	@Column(name = "week_type", columnDefinition = "ENUM('FIRST','SECOND','THIRD','FOURTH')")
	private WeekType weekType;

	@Enumerated(EnumType.STRING)
	@Column(name = "day_of_week", columnDefinition = "ENUM('MONDAY','TUESDAY','WEDNESDAY'," +
													"'THURSDAY','FRIDAY','SATURDAY','SUNDAY')")
	private DayOfWeek dayOfWeek;


	public ScheduleEntity(String subjectName,
						  String subjectFullName,
						  SubgroupType subgroupType,
						  LocalTime startTime,
						  Long groupId,
						  WeekType weekType,
						  DayOfWeek dayOfWeek) {
		this.subjectName = subjectName;
		this.subjectFullName = subjectFullName;
		this.subgroupType = subgroupType;
		this.startTime = startTime;
		this.groupId = groupId;
		this.weekType = weekType;
		this.dayOfWeek = dayOfWeek;
	}
}
