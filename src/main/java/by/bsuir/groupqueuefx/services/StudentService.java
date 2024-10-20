package by.bsuir.groupqueuefx.services;


import by.bsuir.groupqueuefx.enums.entityAttributes.RoleType;
import by.bsuir.groupqueuefx.models.dto.Student;
import by.bsuir.groupqueuefx.models.entities.PersonEntity;
import by.bsuir.groupqueuefx.models.entities.StudentEntity;
import by.bsuir.groupqueuefx.repo.PersonRepository;
import by.bsuir.groupqueuefx.repo.StudentRepository;
import by.bsuir.groupqueuefx.utils.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
	private final PersonRepository personRepository;
	private final StudentRepository studentRepository;
	private final RoleService roleService;

	public long getRoleIdByStudentId(long studentId) {
		return studentRepository.getRoleIdByStudentId(studentId);
	}

	public void updateStudentRoleType(long studentId) {
		StudentEntity student = studentRepository.getStudentEntityByStudentId(studentId);
		long roleId = roleService.getRoleIdByType(RoleType.GROUP_ADMIN);
		student.setRoleId(roleId);
		studentRepository.save(student);
	}

	public boolean isItStudentRole(long studentId, String roleType) {
		return studentRepository.isRoleMatchByStudentIdRoleName(studentId, roleType);
	}

	public void saveStudent(Student student) {
		long roleId = roleService.getRoleIdByType(RoleType.USER);
		student.setRoleId(roleId);
		Long personId = personRepository.save(new PersonEntity(student)).getId();
		studentRepository.save(student.toStudentEntity(personId));
	}

	public boolean isUsernameExist(String username) {
		return studentRepository.isUsernameExist(username);
	}

	public boolean isPasswordMatches(long studentId, String password) {
		String hashedPassword = EncryptionUtil.hashData(password);
		String studentPassword = studentRepository.getPasswordByStudentId(studentId);
		return hashedPassword.equals(studentPassword);
	}

	public Long getStudentIdByStudent(Student student) {
		return studentRepository.getIdByUsername(student.getUsername());
	}

	public boolean isStudentExistByUsernamePassword(Student student) {
		return studentRepository.isStudentExistByUsernamePassword(student.getUsername(),
															EncryptionUtil.hashData(student.getPassword()));
	}

	public void fillInStudent(Student student) {
		StudentEntity studentEntity = studentRepository.getStudentByUsername(student.getUsername());
		student.copyFromStudentEntity(studentEntity);
	}

	public Student getStudentInfo(long studentId) {
		return studentRepository.getStudentDtoByStudentId(studentId);
	}

	public void editProfile(long studentId, Student student) {
		PersonEntity personEntity = personRepository.getPersonByStudentId(studentId);

		fillInPerson(personEntity, student);

		if(!student.getPassword().isEmpty()) {
			personEntity.setPassword(EncryptionUtil.hashData(student.getPassword()));
		}

		personRepository.save(personEntity);
	}

	public void deleteStudentByStudentId(long studentId) {
		studentRepository.deleteById(studentId);
	}

	public String getStudentRoleByStudentId(long studentId) {
		return studentRepository.getStudentRoleByStudentId(studentId);
	}

	private void fillInPerson(PersonEntity personEntity, Student student) {
		personEntity.setUsername(student.getUsername());
		personEntity.setFirstName(student.getFirstName());
		personEntity.setLastName(student.getLastName());
	}

}
