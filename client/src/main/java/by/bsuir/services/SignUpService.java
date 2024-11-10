package by.bsuir.services;

import by.bsuir.enums.ClientRequestType;
import by.bsuir.enums.RegistrationState;
import by.bsuir.models.dto.Student;
import by.bsuir.utils.StudentSession;
import by.bsuir.tcp.ClientRequest;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

public class SignUpService {
    public static RegistrationState registerStudent(Student student) throws IOException,
                                                                    ClassNotFoundException {
        if(student.getGroupNumber().equals(0)) {
            return RegistrationState.GROUP_NOT_EXISTS;
        }
        ClientRequest.sendRequestType(ClientRequestType.REGISTER_STUDENT);
        ClientRequest.output.writeObject(student);

        Object object = ClientRequest.input.readObject();
        if(object instanceof Student) {
            Student registeredStudent = (Student) object;
            StudentSession.getInstance().setUpFields(registeredStudent.getStudentId(), registeredStudent.getGroupId());
            return RegistrationState.OK;
        }
        return (RegistrationState) object;
    }

    public static void setUpErrorLabel(RegistrationState state, Label statusLabel) {
        switch(state) {
            case EMPTY_FIELDS: {
                statusLabel.setText("Некоторые поля не заполнены");
                break;
            }
            case GROUP_NOT_EXISTS: {
                statusLabel.setText("Группа с таким номером не существует");
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Ошибка подключения");
                error.setHeaderText("Проверьте подключение к интернету");
                error.showAndWait();
                break;
            }
            case USERNAME_EXISTS: {
                statusLabel.setText("Имя пользователя занято");
                break;
            }
            case PASSWORDS_NOT_MATCHES: {
                statusLabel.setText("Пароли не совпадают");
                break;
            }
        }
    }
}
