package by.bsuir.services;

import by.bsuir.api.BsuirAPI;
import by.bsuir.enums.RegistrationState;
import by.bsuir.exceptions.PasswordsNotMatchesException;
import by.bsuir.models.dto.Student;
import by.bsuir.models.entities.GroupEntity;
import by.bsuir.repo.GroupRepository;
import by.bsuir.repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {
	private final StudentService studentService;
	private final GroupRepository groupRepository;
	private final ScheduleService scheduleService;
	private final StudentRepository studentRepository;

	public RegistrationState registerStudent(Student student, String repeatedPassword) {
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		Integer groupNumber = student.getGroupNumber();
		String username = student.getUsername();
		String password = student.getPassword();

		List<String> textFields = List.of(firstName,
											lastName,
											username,
											password,
											repeatedPassword);
		if(isTextFieldEmpty(textFields)) {
			return RegistrationState.EMPTY_FIELDS;
		}

		if(!groupRepository.isGroupExist(groupNumber) &&
			!BsuirAPI.isGroupExist(groupNumber)) {
			return RegistrationState.GROUP_NOT_EXISTS;
		}

		if(studentRepository.isUsernameExist(username)) {
			return RegistrationState.USERNAME_EXISTS;
		}

		if(!password.equals(repeatedPassword)) {
			return RegistrationState.PASSWORDS_NOT_MATCHES;
		}

		registerGroup(groupNumber);
		register(student);

		return RegistrationState.OK;
	}

	private boolean isTextFieldEmpty(List<String> textFields) {
		return textFields.stream().anyMatch(textField -> textField.isEmpty());
	}

	public void register(Student student) {
		long groupId = groupRepository.getGroupIdByNumber(student.getGroupNumber());
		student.setGroupId(groupId);
		studentService.saveStudent(student);

		long studentId = studentService.getStudentIdByStudent(student);
		student.setStudentId(studentId);
	}

	public void registerGroup(int groupNumber) {
		if(!groupRepository.isGroupExist(groupNumber) && BsuirAPI.isGroupExist(groupNumber)) {
			groupRepository.save(new GroupEntity(groupNumber));
			scheduleService.addRecordsForNewGroupByGroupNumber(groupNumber);
		}
	}
}
