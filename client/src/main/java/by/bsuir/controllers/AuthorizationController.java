package by.bsuir.controllers;

import by.bsuir.models.dto.Pair;
import by.bsuir.utils.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class AuthorizationController {
    @FXML
    private Button exitButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void exit(MouseEvent event) throws IOException {
        WindowManager.closeWindow(event);
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
        WindowManager.generateWindow("/views/signUp.fxml",
                                    "Registration",
                                    false,
                                    false,
                                    new Pair<>(600, 400),
                                    event);
    }

    @FXML
    void initialize() {
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'Untitled'.";
        assert signInButton != null : "fx:id=\"signInButton\" was not injected: check your FXML file 'Untitled'.";
        assert signUpButton != null : "fx:id=\"signUpButton\" was not injected: check your FXML file 'Untitled'.";
    }
}
