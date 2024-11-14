package by.bsuir.controllers;

import by.bsuir.enums.ClientRequestType;
import by.bsuir.enums.ServerResponseType;
import by.bsuir.models.dto.Student;
import by.bsuir.utils.tcp.ClientRequest;
import by.bsuir.utils.StudentSession;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import lombok.Getter;

import java.io.IOException;

public class EditProfileController {
    @FXML
    @Getter
    private TextField firstNameField;
    @FXML
    @Getter
    private TextField lastNameField;
    @FXML
    @Getter
    private TextField usernameField;
    @FXML
    @Getter
    private Label groupNumberLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;

    public void initialize() {
        //
    }

    @FXML
    private void handleSaveChanges() throws IOException, ClassNotFoundException {
        String password = passwordField.getText();
        if (!password.equals(repeatPasswordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Ошибка",
                    "Пароли не совпадают");
            return;
        }
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();

        Student student = new Student(StudentSession.getInstance().getStudentId(), firstName, lastName, username, password);
        ClientRequest.sendRequestType(ClientRequestType.EDIT_STUDENT_PROFILE);
        ClientRequest.output.writeObject(student);

        ServerResponseType serverResponseType = (ServerResponseType) ClientRequest.input.readObject();
        if(serverResponseType.equals(ServerResponseType.OK)) {
            showAlert(Alert.AlertType.CONFIRMATION, "Успешно",
                    "Профиль успешно обновлен");
            return;
        }
        showAlert(Alert.AlertType.ERROR, "Ошибка",
                "Не удалось обновить профиль.\nПопробуйте другое имя пользователя");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
