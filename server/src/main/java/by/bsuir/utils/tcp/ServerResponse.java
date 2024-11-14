package by.bsuir.utils.tcp;

import by.bsuir.enums.ClientRequestType;
import by.bsuir.enums.RegistrationState;
import by.bsuir.enums.ServerResponseType;
import by.bsuir.enums.entityAttributes.PermissionType;
import by.bsuir.exceptions.AuthorizationException;
import by.bsuir.exceptions.entityExceptions.QueueException;
import by.bsuir.exceptions.entityExceptions.ScheduleException;
import by.bsuir.models.dto.*;
import by.bsuir.repo.PermissionRepository;
import by.bsuir.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ServerResponse {
    private final AuthorizationService authorizationService;
    private final GroupService groupService;
    private final LessonService lessonService;
    private final PreQueueService preQueueService;
    private final QueueService queueService;
    private final RegistrationService registrationService;
    private final RequestService requestService;
    private final RoleService roleService;
    private final ScheduleService scheduleService;
    private final SignInService signInService;
    private final StudentService studentService;

    private final PermissionRepository permissionRepository;

    public void authorizeStudent() throws IOException, ClassNotFoundException {
        Student student = (Student) ClientRequestHandler.input.readObject();
        try {
            authorizationService.authorizeStudent(student);
        } catch(AuthorizationException e) {
            ClientRequestHandler.output.writeObject(null);
            return;
        }
        ClientRequestHandler.output.writeObject(student);
    }

    public void registerStudent() throws IOException, ClassNotFoundException {
        Student student = (Student) ClientRequestHandler.input.readObject();
        RegistrationState registrationState = registrationService.
                                                registerStudent(student, student.getRepeatedPassword());
        if(registrationState.equals(RegistrationState.OK)) {
            ClientRequestHandler.output.writeObject(student);
        } else {
            ClientRequestHandler.output.writeObject(registrationState);
        }
    }

    public void getSchedule() throws IOException, ClassNotFoundException {
        Pair<Long, Long> studentIdAndGroupId = (Pair<Long, Long>) ClientRequestHandler.input.readObject();
        Schedule schedule = null;
        try {
            schedule = scheduleService.getDayOfWeekSchedule(studentIdAndGroupId.getFirst(),
                    studentIdAndGroupId.getSecond());
        } catch(ScheduleException e) {
            //
        }
        ClientRequestHandler.output.writeObject(schedule);
    }

    public void registerStudentIntoQueue() throws IOException, ClassNotFoundException {
        PreQueue preQueue = (PreQueue) ClientRequestHandler.input.readObject();
        try {
            preQueueService.addStudentToPreQueue(preQueue.getStudentId(), preQueue);
        } catch (QueueException e) {
            ClientRequestHandler.output.writeObject(RegistrationState.FAILED_TO_REGISTER_STUDENT_INTO_PRE_QUEUE);
            return;
        }
        ClientRequestHandler.output.writeObject(RegistrationState.OK);
    }

    public void removeStudentFromQueue() throws IOException, ClassNotFoundException {
        PreQueue preQueue = (PreQueue) ClientRequestHandler.input.readObject();
        preQueueService.removeStudentFromPreQueueByLessonId(preQueue.getStudentId(), preQueue.getLessonId());
    }

    public void getStudentInfo() throws IOException, ClassNotFoundException {
        long studentId = (long) ClientRequestHandler.input.readObject();
        Student studentInfo = studentService.getStudentInfo(studentId);
        ClientRequestHandler.output.writeObject(studentInfo);
    }

    public void editProfile() throws IOException {
        try {
            Student student = (Student) ClientRequestHandler.input.readObject();
            studentService.editProfile(student.getStudentId(), student);
            ClientRequestHandler.output.writeObject(ServerResponseType.OK);
        } catch(IOException | ClassNotFoundException e) {
            ClientRequestHandler.output.writeObject(ServerResponseType.ERROR);
        }
    }

    public void getQueueInfo() throws IOException, ClassNotFoundException {
        long studentId = (long) ClientRequestHandler.input.readObject();
        List<QueueInfo> queueInfos = queueService.getQueueInfoByStudentIdGroupId(studentId);
        ClientRequestHandler.output.writeObject(queueInfos);
    }

    public void getGroupQueue() throws IOException, ClassNotFoundException {
        long lessonId = (long) ClientRequestHandler.input.readObject();
        List<GroupQueue> groupQueues = queueService.getGroupQueueByLessonId(lessonId);
        ClientRequestHandler.output.writeObject(groupQueues);
    }

    public void becomeGroupAdmin() throws IOException, ClassNotFoundException {
        Student student = (Student) ClientRequestHandler.input.readObject();
        if(permissionRepository.isActionAllowed(PermissionType.BECOME_GROUP_ADMIN, student.getRoleId())) {
            ClientRequestHandler.output.writeObject(ServerResponseType.ERROR);
            return;
        }
        requestService.sendBecomeGroupAdmin(student.getStudentId());
        ClientRequestHandler.output.writeObject(ServerResponseType.OK);
    }

    public void getGroupSchedules() throws IOException, ClassNotFoundException {
        long groupId = (long) ClientRequestHandler.input.readObject();
        List<GroupSchedule> groupSchedules = scheduleService.getGroupSchedulesByGroupId(groupId);
        ClientRequestHandler.output.writeObject(groupSchedules);
    }

    public void changeSortType() throws IOException {
        try {
            long roleId = (long) ClientRequestHandler.input.readObject();
            if(permissionRepository.isActionAllowed(PermissionType.SHOW_BECOME_GROUP_ADMIN_REQUESTS, roleId)) {
                ClientRequestHandler.output.writeObject(ServerResponseType.ERROR);
                return;
            }
            ClientRequestHandler.output.writeObject(ServerResponseType.OK);

            GroupSchedule groupSchedule = (GroupSchedule) ClientRequestHandler.input.readObject();
            lessonService.changeSortType(groupSchedule);
            ClientRequestHandler.output.writeObject(ServerResponseType.OK);
        } catch(Exception e) {
            ClientRequestHandler.output.writeObject(ServerResponseType.ERROR);
        }
    }

    public void getRequests() throws IOException {
        ClientRequestHandler.output.writeObject(requestService.getRequests());
    }

    public void acceptRequest() throws IOException, ClassNotFoundException {
        Request request = (Request) ClientRequestHandler.input.readObject();
        requestService.acceptBecomeGroupAdminRequest(request);
    }

    public void declineRequest() throws IOException, ClassNotFoundException {
        Request request = (Request) ClientRequestHandler.input.readObject();
        requestService.declineBecomeGroupAdminRequest(request);
    }
}
