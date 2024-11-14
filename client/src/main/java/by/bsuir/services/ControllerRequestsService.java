package by.bsuir.services;

import by.bsuir.enums.ClientRequestType;
import by.bsuir.enums.RegistrationState;
import by.bsuir.enums.ServerResponseType;
import by.bsuir.models.dto.*;
import by.bsuir.utils.StudentSession;
import by.bsuir.utils.tcp.ClientRequest;
import by.bsuir.utils.tcp.ClientRequestHandler;

import java.io.IOException;
import java.util.List;

public class ControllerRequestsService {
    public static Schedule getSchedule() throws IOException, ClassNotFoundException {
        ClientRequest.sendRequestType(ClientRequestType.GET_SCHEDULE);

        StudentSession studentSession = StudentSession.getInstance();
        Pair<Long, Long> studentIdAndGroupId = new Pair<>(studentSession.getStudentId(), studentSession.getGroupId());
        ClientRequest.output.writeObject(studentIdAndGroupId);

        return (Schedule) ClientRequest.input.readObject();
    }

    public static RegistrationState registerStudentIntoQueue(PreQueue preQueue) throws IOException, ClassNotFoundException {
        ClientRequest.sendRequestType(ClientRequestType.REGISTER_STUDENT_TO_QUEUE);
        ClientRequest.output.writeObject(preQueue);

        return (RegistrationState) ClientRequest.input.readObject();
    }

    public static ServerResponseType removeStudentFromQueue(long studentId, long lessonId) {
        try {
            ClientRequest.sendRequestType(ClientRequestType.REMOVE_STUDENT_FROM_QUEUE);
            ClientRequest.output.writeObject(new PreQueue(studentId, lessonId));
        } catch (IOException e) {
            return ServerResponseType.ERROR;
        }
        return ServerResponseType.OK;
    }

    public static Student getStudentInfo(long studentId) {
        try {
            ClientRequest.sendRequestType(ClientRequestType.GET_STUDENT_INFO);
            ClientRequest.output.writeObject(studentId);

            return (Student) ClientRequest.input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static List<QueueInfo> getQueueInfo() throws IOException, ClassNotFoundException {
        long studentId = StudentSession.getInstance().getStudentId();

        ClientRequest.sendRequestType(ClientRequestType.GET_QUEUE_INFO);
        ClientRequest.output.writeObject(studentId);

        return (List<QueueInfo>) ClientRequest.input.readObject();
    }

    public static List<GroupQueue> getGroupQueue(long lessonId) throws IOException, ClassNotFoundException {
        ClientRequest.sendRequestType(ClientRequestType.GET_GROUP_QUEUE);
        ClientRequest.output.writeObject(lessonId);

        return (List<GroupQueue>) ClientRequest.input.readObject();
    }

    public static ServerResponseType becomeGroupAdmin() throws IOException, ClassNotFoundException {
        ClientRequest.sendRequestType(ClientRequestType.BECOME_GROUP_ADMIN);
        ClientRequest.output.writeObject(new Student(StudentSession.getInstance().getStudentId(),
                                        null,
                                        StudentSession.getInstance().getRoleId()));
        return (ServerResponseType) ClientRequest.input.readObject();
    }

    public static List<GroupSchedule> getGroupSchedules(long groupId) throws IOException, ClassNotFoundException {
        ClientRequest.sendRequestType(ClientRequestType.GET_GROUP_SCHEDULES);
        ClientRequest.output.writeObject(groupId);

        return (List<GroupSchedule>) ClientRequest.input.readObject();
    }

    public static ServerResponseType changeSortType(GroupSchedule groupSchedule) throws IOException, ClassNotFoundException {
        ClientRequest.sendRequestType(ClientRequestType.CHANGE_SORT_TYPE);

        ClientRequest.output.writeObject(StudentSession.getInstance().getRoleId());

        ServerResponseType serverResponseType = (ServerResponseType) ClientRequest.input.readObject();
        if(serverResponseType.equals(ServerResponseType.ERROR)) {
            return ServerResponseType.ERROR;
        }

        ClientRequest.output.writeObject(groupSchedule);

        return (ServerResponseType) ClientRequest.input.readObject();
    }

    public static List<Request> getRequests() throws IOException, ClassNotFoundException {
        ClientRequest.sendRequestType(ClientRequestType.GET_REQUESTS);
        return (List<Request>) ClientRequest.input.readObject();
    }

    public static ServerResponseType acceptRequest(Request request) {
        try {
            ClientRequest.sendRequestType(ClientRequestType.ACCEPT_REQUEST);
            ClientRequest.output.writeObject(request);
        } catch (IOException e) {
            return ServerResponseType.ERROR;
        }
        return ServerResponseType.OK;
    }

    public static ServerResponseType declineRequest(Request request) {
        try {
            ClientRequest.sendRequestType(ClientRequestType.DECLINE_REQUEST);
            ClientRequest.output.writeObject(request);
        } catch (IOException e) {
            return ServerResponseType.ERROR;
        }
        return ServerResponseType.OK;
    }
}
