package by.bsuir.groupqueuefx.services;

import by.bsuir.groupqueuefx.controllers.SignUpController;
import by.bsuir.groupqueuefx.exceptions.AuthorizationException;
import by.bsuir.groupqueuefx.exceptions.EmptyObjectException;
import by.bsuir.groupqueuefx.exceptions.GroupNotExistsException;
import by.bsuir.groupqueuefx.exceptions.PasswordsNotMatchesException;
import by.bsuir.groupqueuefx.external.api.BsuirAPI;
import by.bsuir.groupqueuefx.models.dto.Student;
import by.bsuir.groupqueuefx.models.entities.GroupEntity;
import by.bsuir.groupqueuefx.repo.GroupRepository;
import by.bsuir.groupqueuefx.repo.StudentRepository;
import javafx.scene.control.TextField;
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

	public void registerStudent(Student student, String repeatedPassword) throws EmptyObjectException,
															GroupNotExistsException,
															AuthorizationException {
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
			throw new EmptyObjectException("Some fields on Registration page are empty");
		}

		try {
			if(!BsuirAPI.isGroupExist(groupNumber)) {
				throw new GroupNotExistsException("Group " + groupNumber + " does not exist");
			}
		} catch (NumberFormatException e) {
			throw new GroupNotExistsException("Group number is invalid");
		}

		if(studentRepository.isUsernameExist(username)) {
			throw new AuthorizationException("Username is already in use");
		}

		if(!password.equals(repeatedPassword)) {
			throw new PasswordsNotMatchesException("Passwords do not match");
		}

		registerGroup(groupNumber);
		register(student);
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
