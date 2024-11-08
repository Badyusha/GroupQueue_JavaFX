package by.bsuir.controllers;

import by.bsuir.enums.RegistrationState;
import by.bsuir.exceptions.AuthorizationException;
import by.bsuir.exceptions.EmptyObjectException;
import by.bsuir.exceptions.GroupNotExistsException;
import by.bsuir.exceptions.PasswordsNotMatchesException;
import by.bsuir.models.dto.Pair;
import by.bsuir.models.dto.Student;
import by.bsuir.services.SignUpService;
import by.bsuir.utils.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Getter
public class SignUpController {

    @FXML
    private Button exitButton;

    @FXML
    private TextField firstName;

    @FXML
    private TextField groupNumber;

    @FXML
    private TextField lastName;

    @FXML
    private TextField password;

    @FXML
    private Button registerButton;

    @FXML
    private TextField repeatedPassword;

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
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void register(MouseEvent event) {
        RegistrationState registrationState = null;
        try {
            registrationState = SignUpService.registerStudent(this.toStudent());
            if(!registrationState.equals(RegistrationState.OK)) {
                SignUpService.setUpErrorLabel(registrationState, statusLabel);
                return;
            }
        } catch (Exception e) {
            statusLabel.setText("Ошибка чтения данных сервера");
            return;
        }
        WindowManager.generateWindow("/views/schedulePage.fxml",
                "Schedule",
                false,
                true,
                new Pair<>(650, 400),
                event);
    }

    @FXML
    void signIn(MouseEvent event) {
        WindowManager.generateWindow("/views/signIn.fxml",
                                    "Authorization",
                                    false,
                                    false,
                                    new Pair<>(600, 400),
                                    event);
    }

    @FXML
    void signUp(MouseEvent event) {
        // empty
    }

    @FXML
    void initialize() {
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'Untitled'.";
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file 'Untitled'.";
        assert groupNumber != null : "fx:id=\"groupNumber\" was not injected: check your FXML file 'Untitled'.";
        assert lastName != null : "fx:id=\"lastName\" was not injected: check your FXML file 'Untitled'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'Untitled'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'Untitled'.";
        assert repeatedPassword != null : "fx:id=\"repeatedPassword\" was not injected: check your FXML file 'Untitled'.";
        assert signInButton != null : "fx:id=\"signInButton\" was not injected: check your FXML file 'Untitled'.";
        assert signUpButton != null : "fx:id=\"signUpButton\" was not injected: check your FXML file 'Untitled'.";
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'Untitled'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'Untitled'.";
    }

    public Student toStudent() throws EmptyObjectException {
        int groupNumber = 0;
        try {
            groupNumber = Integer.parseInt(this.groupNumber.getText());
        } catch(NumberFormatException e) {
            //
        }

        return new Student(this.firstName.getText(),
                            this.lastName.getText(),
                            this.username.getText(),
                            this.password.getText(),
                            this.repeatedPassword.getText(),
                            groupNumber);
    }
}
