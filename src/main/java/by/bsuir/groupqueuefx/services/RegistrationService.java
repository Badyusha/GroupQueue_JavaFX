package by.bsuir.groupqueuefx.services;

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

	public void registerUser(TextField firstNameTextField,
							 TextField lastNameTextField,
							 TextField groupNumberTextField,
							 TextField usernameTextField,
							 TextField passwordTextField,
							 TextField repeatedPasswordTextField) throws EmptyObjectException,
			GroupNotExistsException,
			AuthorizationException {
		List<TextField> textFields = List.of(firstNameTextField,
				lastNameTextField,
				groupNumberTextField,
				usernameTextField,
				passwordTextField,
				repeatedPasswordTextField);
		if(isTextFieldEmpty(textFields)) {
			throw new EmptyObjectException("Some fields on Registration page are empty");
		}

		int groupNumber = 0;
		try {
			groupNumber = Integer.parseInt(groupNumberTextField.getText());
			if(!BsuirAPI.isGroupExist(groupNumber)) {
				throw new GroupNotExistsException("Group " + groupNumber + " does not exist");
			}
		} catch (NumberFormatException e) {
			throw new GroupNotExistsException("Group number is invalid");
		}

		if(studentRepository.isUsernameExist(usernameTextField.getText())) {
			throw new AuthorizationException("Username is already in use");
		}

		if(!passwordTextField.getText().equals(repeatedPasswordTextField.getText())) {
			throw new PasswordsNotMatchesException("Passwords do not match");
		}

		registerGroup(groupNumber);

		Student student = new Student(firstNameTextField.getText(),
									lastNameTextField.getText(),
									usernameTextField.getText(),
									passwordTextField.getText(),
									groupNumber);
		registerStudent(student);
	}

	private boolean isTextFieldEmpty(List<TextField> textFields) {
		return textFields.stream().anyMatch(textField -> textField.getText().isEmpty());
	}

	public void registerStudent(Student student) {
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
