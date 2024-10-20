package by.bsuir.groupqueuefx.services;


import by.bsuir.groupqueuefx.exceptions.AuthorizationException;
import by.bsuir.groupqueuefx.models.dto.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
	private final StudentService studentService;

	public void isStudentExist(Student student) throws AuthorizationException {
		boolean isStudentExist = studentService.isStudentExistByUsernamePassword(student);
		if(!isStudentExist) {
			throw new AuthorizationException("Incorrect username or password\nFor user: " + student);
		}
	}
}
