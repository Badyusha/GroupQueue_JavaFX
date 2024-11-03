package by.bsuir.groupqueuefx.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import by.bsuir.groupqueuefx.exceptions.AuthorizationException;
import by.bsuir.groupqueuefx.models.dto.Pair;
import by.bsuir.groupqueuefx.models.dto.Schedule;
import by.bsuir.groupqueuefx.models.dto.Student;
import by.bsuir.groupqueuefx.models.entities.StudentEntity;
import by.bsuir.groupqueuefx.services.AuthorizationService;
import by.bsuir.groupqueuefx.services.GroupService;
import by.bsuir.groupqueuefx.services.ScheduleService;
import by.bsuir.groupqueuefx.services.StudentService;
import by.bsuir.groupqueuefx.utils.WindowManager;
import by.bsuir.groupqueuefx.utils.fabrics.SpringBeanControllerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignInController {
    private final AuthorizationService authorizationService;
    private final ScheduleService scheduleService;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authorizeButton;

    @FXML
    private Button exitButton;

    @FXML
    private PasswordField password;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField username;

    @FXML
    private void exit(MouseEvent event) {
        WindowManager.closeWindow(event);
    }

    @FXML
    private void authorize(MouseEvent event) {
//        try {
//            authorizationService.authorizeStudent(this.toStudent());
//        } catch(AuthorizationException e) {
//            statusLabel.setText("Неверное имя пользователя или пароль");
//            return;
//        }
        WindowManager.generateWindow("views/schedulePage.fxml",
                                    "Schedule",
                                    false,
                                    true,
                                    new Pair<>(650, 400),
                                    event);
        scheduleService.populateScheduleTable("el_Pablo");
    }

    @FXML
    private void signIn(MouseEvent event) {
        //
    }

    @FXML
    private void signUp(MouseEvent event) {
        WindowManager.generateWindow("views/signUp.fxml",
                                    "Registration",
                                    false,
                                    false,
                                    new Pair<>(600, 400),
                                    event);
    }

    @FXML
    private void initialize() {
        assert authorizeButton != null : "fx:id=\"authorizeButton\" was not injected: check your FXML file 'authorizationPage.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'authorizationPage.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'authorizationPage.fxml'.";
        assert signInButton != null : "fx:id=\"signInButton\" was not injected: check your FXML file 'authorizationPage.fxml'.";
        assert signUpButton != null : "fx:id=\"signUpButton\" was not injected: check your FXML file 'authorizationPage.fxml'.";
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'authorizationPage.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'authorizationPage.fxml'.";
    }

    private Student toStudent() {
        return new Student(null, null, username.getText(), password.getText(), null);
    }

}
