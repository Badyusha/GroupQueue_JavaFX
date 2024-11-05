package by.bsuir.services;

import by.bsuir.enums.entityAttributes.RequestType;
import by.bsuir.models.dto.Request;
import by.bsuir.models.entities.RequestEntity;
import by.bsuir.repo.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
	private final RequestRepository requestRepository;
	private final StudentService studentService;

	public List<Request> getRequests() {
		return requestRepository.getRequests();
	}

	public void sendBecomeGroupAdmin(long studentId) {
		requestRepository.save(new RequestEntity(RequestType.BECOME_GROUP_ADMIN, studentId));
	}

	public void acceptBecomeGroupAdminRequest(Request requestDto) {
		long studentId = requestDto.getStudentId();
		studentService.updateStudentRoleType(studentId);

		long requestId = requestRepository.getRequestIdByRequestTypeStudentId(requestDto.getRequestType(), studentId);
		requestRepository.deleteById(requestId);
	}

	public void declineBecomeGroupAdminRequest(Request requestDto) {
		long requestId = requestRepository.getRequestIdByRequestTypeStudentId(requestDto.getRequestType(),
																				requestDto.getStudentId());
		requestRepository.deleteById(requestId);
	}
}
