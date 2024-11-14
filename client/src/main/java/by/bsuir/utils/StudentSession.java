package by.bsuir.utils;

import by.bsuir.enums.entityAttributes.RoleType;
import by.bsuir.models.dto.Student;
import by.bsuir.services.ControllerRequestsService;
import lombok.Data;

import java.io.IOException;

@Data
public final class StudentSession {
    private static StudentSession instance = null;

    private long studentId;
    private long groupId;
    private long roleId;
    private int groupNumber;
    private String firstName;
    private String lastName;
    private String username;
    private RoleType roleType;

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
        this.roleId = 0;
        this.groupNumber = 0;
        this.firstName = "";
        this.lastName = "";
        this.username = "";
        this.roleType = null;
    }

    public void setUpFields() throws IOException {
        Student student = ControllerRequestsService.getStudentInfo(this.studentId);
        if(student == null) {
            throw new IOException("Student is null");
        }
        this.roleId = student.getRoleId();
        this.groupNumber = student.getGroupNumber();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.username = student.getUsername();
        this.roleType = student.getRoleType();
    }
}
