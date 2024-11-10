package by.bsuir.services;

import by.bsuir.enums.ClientRequestType;
import by.bsuir.exceptions.AuthorizationException;
import by.bsuir.models.dto.Student;
import by.bsuir.utils.StudentSession;
import by.bsuir.tcp.ClientRequest;

import java.io.IOException;

public class SignInService {

    public static void authorizeStudent(Student student) throws IOException,
                                                        ClassNotFoundException,
                                                        AuthorizationException {
        ClientRequest.sendRequestType(ClientRequestType.AUTHORIZE_STUDENT);
        ClientRequest.output.writeObject(student);

        Student authorizedStudent = (Student) ClientRequest.input.readObject();
        if(authorizedStudent == null) {
            throw new AuthorizationException("Invalid username or password");
        }

        StudentSession.getInstance().setStudentId(authorizedStudent.getStudentId());
        StudentSession.getInstance().setGroupId(authorizedStudent.getGroupId());
    }
}
