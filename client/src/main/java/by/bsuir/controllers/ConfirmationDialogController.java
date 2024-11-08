package by.bsuir.controllers;

import by.bsuir.enums.RegistrationState;
import by.bsuir.enums.entityAttributes.DayOfWeek;
import by.bsuir.models.dto.PreQueue;
import by.bsuir.services.ScheduleService;
import by.bsuir.utils.StudentSession;
import by.bsuir.utils.WindowManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConfirmationDialogController {
    @FXML
    @Getter
    private TextField subjectName;
    @FXML
    @Getter
    private TextField startTime;
    @FXML
    @Getter
    private TextField subgroup;
    @FXML
    private TextField passingLabs;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;

    @Setter
    private long lessonId;
    @Setter
    private DayOfWeek dayOfWeek;

    @FXML
    public void initialize() {
        subjectName.editableProperty().set(false);
        startTime.editableProperty().set(false);
        subgroup.editableProperty().set(false);
    }

    @FXML
    private void handleRegister(Event event) throws IOException, ClassNotFoundException {
        String PASSING_LABS_PATTERN = "^\\b(1?\\d|20)\\b(?:[ ,]\\s*\\b(1?\\d|20)\\b)*$";
        String passingLabsStr = passingLabs.getText();
        if (!passingLabsStr.matches(PASSING_LABS_PATTERN)) {
            JOptionPane.showMessageDialog(null,
                    "Неверный формат сдаваемых лаб.\nИспользуйте числа от 1 до 20 и пробелы, запятые или пробелы с запятыми.\nНапример1,2,3 или 1 2 3 или 1, 2, 3",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        long studentId = StudentSession.getInstance().getStudentId();
        PreQueue preQueue = new PreQueue(studentId, lessonId, startTime.getText(), dayOfWeek, passingLabsStr.getBytes());

        RegistrationState registrationState = ScheduleService.registerStudentIntoQueue(preQueue);
        if(registrationState.equals(RegistrationState.OK)) {
            WindowManager.closeWindow(event);
            return;
        }

        JOptionPane.showMessageDialog(null,
                "Не удалось записаться",
                "Registration error",
                JOptionPane.ERROR_MESSAGE);
    }

    @FXML
    private void handleCancel(Event event) {
        WindowManager.closeWindow(event);
    }
}
