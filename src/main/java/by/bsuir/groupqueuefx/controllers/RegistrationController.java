package by.bsuir.groupqueuefx.controllers;

import by.bsuir.groupqueuefx.exceptions.AuthorizationException;
import by.bsuir.groupqueuefx.exceptions.EmptyObjectException;
import by.bsuir.groupqueuefx.exceptions.GroupNotExistsException;
import by.bsuir.groupqueuefx.exceptions.PasswordsNotMatchesException;
import by.bsuir.groupqueuefx.services.RegistrationService;
import by.bsuir.groupqueuefx.utils.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

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
        try {
            registrationService.registerUser(firstName,
                                            lastName,
                                            groupNumber,
                                            username,
                                            password,
                                            repeatedPassword);
        } catch(EmptyObjectException e) {
            statusLabel.setText("Some fields are empty");
        } catch (GroupNotExistsException e) {
            statusLabel.setText("Group does not exist");
        } catch(PasswordsNotMatchesException e) {
            statusLabel.setText("Passwords does not match");
        } catch(AuthorizationException e) {
            statusLabel.setText("Username is already in use");
        }
    }

    @FXML
    void signIn(MouseEvent event) {
        WindowManager.generateWindow("signIn.fxml", "Authorization", event);
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
}
