package by.bsuir.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class StudentSession {
    private static StudentSession instance = null;

    private long studentId;
    private long groupId;

    public static StudentSession getInstance() {
        if(instance == null) {
            instance = new StudentSession();
        }
        return instance;
    }

    public void setUpFields(long studentId, long groupId) {
        this.studentId = studentId;
        this.groupId = groupId;
    }

    public void cleanUserSession() {
        studentId = 0;
        groupId = 0;
    }
}
