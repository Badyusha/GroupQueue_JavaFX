package by.bsuir.models.dto;

import by.bsuir.enums.entityAttributes.RoleType;
import lombok.Data;

@Data
public class Student {
	private Long studentId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private Long groupId;
	private Long personId;
	private Integer groupNumber;
	private RoleType roleType;
	private Long roleId;

	public Student(String firstName,
				   String lastName,
				   String username,
				   String password,
				   Integer groupNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.groupNumber = groupNumber;
	}

	public Student(Long studentId,
				   String firstName,
				   String lastName,
				   String username,
				   String password,
				   Long groupId,
				   Integer groupNumber,
				   RoleType roleType) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.groupId = groupId;
		this.groupNumber = groupNumber;
		this.roleType = roleType;
	}
}
