package by.bsuir.services;

import by.bsuir.enums.ClientRequestType;
import by.bsuir.enums.RegistrationState;
import by.bsuir.models.dto.Pair;
import by.bsuir.models.dto.PreQueue;
import by.bsuir.models.dto.Schedule;
import by.bsuir.tcp.ClientRequestHandler;
import by.bsuir.utils.StudentSession;
import by.bsuir.tcp.ClientRequest;

import java.io.IOException;

public class ScheduleService {
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

    public static RegistrationState removeStudentFromQueue(long studentId, long lessonId) {
        try {
            ClientRequest.sendRequestType(ClientRequestType.REMOVE_STUDENT_FROM_QUEUE);
            ClientRequest.output.writeObject(new PreQueue(studentId, lessonId));
        } catch (IOException e) {
            return RegistrationState.ERROR;
        }
        return RegistrationState.OK;
    }
}
