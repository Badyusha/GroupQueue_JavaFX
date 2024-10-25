package by.bsuir.groupqueuefx.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import by.bsuir.groupqueuefx.exceptions.AuthorizationException;
import by.bsuir.groupqueuefx.models.dto.Student;
import by.bsuir.groupqueuefx.services.AuthorizationService;
import by.bsuir.groupqueuefx.services.StudentService;
import by.bsuir.groupqueuefx.utils.WindowManager;
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
    void exit(MouseEvent event) {
        WindowManager.closeWindow(event);
    }

    @FXML
    void authorize(MouseEvent event) {
        try {
            authorizationService.authorizeStudent(this.toStudent());
        } catch(AuthorizationException e) {
            statusLabel.setText("Неверное имя пользователя или пароль");
            return;
        }
        System.out.println("congrats!!!");
    }

    @FXML
    void signIn(MouseEvent event) {
        //
    }

    @FXML
    void signUp(MouseEvent event) {
        WindowManager.generateWindow("signUp.fxml", "Registration", event);
    }

    @FXML
    void initialize() {
        assert authorizeButton != null : "fx:id=\"authorizeButton\" was not injected: check your FXML file 'main-page.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'main-page.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'main-page.fxml'.";
        assert signInButton != null : "fx:id=\"signInButton\" was not injected: check your FXML file 'main-page.fxml'.";
        assert signUpButton != null : "fx:id=\"signUpButton\" was not injected: check your FXML file 'main-page.fxml'.";
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'main-page.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'main-page.fxml'.";
    }

    public Student toStudent() {
        return new Student(null, null, username.getText(), password.getText(), null);
    }

}
