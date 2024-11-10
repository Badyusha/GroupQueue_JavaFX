package by.bsuir.utils;

import by.bsuir.enums.ClientRequestType;
import by.bsuir.enums.entityAttributes.RoleType;
import by.bsuir.models.dto.Student;
import by.bsuir.services.ScheduleService;
import by.bsuir.tcp.ClientRequest;
import by.bsuir.tcp.ClientRequestHandler;
import lombok.Data;

import java.io.IOException;

@Data
public final class StudentSession {
    private static StudentSession instance = null;

    private long studentId;
    private long groupId;
    private String firstName;
    private String lastName;
    private String username;
    private RoleType roleType;
    private int groupNumber;

    private StudentSession() {
        //
    }

    public static StudentSession getInstance() {
        if(instance == null) {
            instance = new StudentSession();
        }
        return instance;
    }

    public void logout() {
        this.studentId = 0;
        this.groupId = 0;
    }

    public void setUpFields(long studentId, long groupId) throws IOException {
        Student student = ScheduleService.getStudentInfo(studentId);
        if(student == null) {
            throw new IOException("Student is null");
        }
        this.studentId = studentId;
        this.groupId = groupId;
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.username = student.getUsername();
        this.roleType = student.getRoleType();
        this.groupNumber = student.getGroupNumber();
    }
}
