package by.bsuir.models.dto;

import by.bsuir.models.entities.StudentEntity;
import by.bsuir.enums.entityAttributes.RoleType;
import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
	private Long studentId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String repeatedPassword;
	private Long groupId;
	private Long personId;
	private Integer groupNumber;
	private RoleType roleType;
	private Long roleId;

	public Student(long studentId, Long groupId, Long roleId) {
		this.studentId = studentId;
		this.groupId = groupId;
		this.roleId = roleId;
	}

	public Student(long studentId, String firstName, String lastName, String username, String password) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	public Student(String firstName,
				   String lastName,
				   String username,
				   String password,
				   String repeatedPassword,
				   Integer groupNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.repeatedPassword = repeatedPassword;
		this.groupNumber = groupNumber;
	}

	public Student(Long studentId,
				   String firstName,
				   String lastName,
				   String username,
				   String password,
				   Long groupId,
				   Integer groupNumber,
				   RoleType roleType,
				   Long roleId) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.groupId = groupId;
		this.groupNumber = groupNumber;
		this.roleType = roleType;
		this.roleId = roleId;
	}

	public StudentEntity toStudentEntity(Long personId) {
		return new StudentEntity(groupId, roleId, personId);
	}

	public void copyFromStudentEntity(StudentEntity studentEntity) {
		this.studentId = studentEntity.getId();
		this.firstName = studentEntity.getPersonEntity().getFirstName();
		this.lastName = studentEntity.getPersonEntity().getLastName();
		this.username = studentEntity.getPersonEntity().getUsername();
		this.password = studentEntity.getPersonEntity().getPassword();
		this.groupId = studentEntity.getGroupId();
		this.groupNumber = studentEntity.getGroupEntity().getNumber();
	}
}
