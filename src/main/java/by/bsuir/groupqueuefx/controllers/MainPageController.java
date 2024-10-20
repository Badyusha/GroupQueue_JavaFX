package by.bsuir.groupqueuefx.controllers;

import by.bsuir.groupqueuefx.utils.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

@Component
public class MainPageController {
    @FXML
    private Button exitButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void exit(MouseEvent event) {
        WindowManager.closeWindow(event);
    }

    @FXML
    void signIn(MouseEvent event) {
        WindowManager.generateWindow("signIn.fxml", "Authorization", event);
    }

    @FXML
    void signUp(MouseEvent event) {
        WindowManager.generateWindow("signUp.fxml", "Registration", event);
    }

    @FXML
    void initialize() {
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'Untitled'.";
        assert signInButton != null : "fx:id=\"signInButton\" was not injected: check your FXML file 'Untitled'.";
        assert signUpButton != null : "fx:id=\"signUpButton\" was not injected: check your FXML file 'Untitled'.";

    }
}
