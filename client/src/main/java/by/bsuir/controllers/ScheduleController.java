package by.bsuir.controllers;

import by.bsuir.MainClient;
import by.bsuir.models.dto.*;
import by.bsuir.services.ScheduleService;
import by.bsuir.utils.StudentSession;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class ScheduleController {
    private boolean isMenuVisible = false;

    @FXML
    private VBox sideMenu;
    @FXML
    private Button menuButton;
    @FXML
    private HBox weekScheduleHBox;
    @FXML
    private VBox mondayContainer;
    @FXML
    private VBox tuesdayContainer;
    @FXML
    private VBox wednesdayContainer;
    @FXML
    private VBox thursdayContainer;
    @FXML
    private VBox fridayContainer;
    @FXML
    private VBox saturdayContainer;
    @FXML
    private VBox sundayContainer;

    public void initialize() throws IOException, ClassNotFoundException {
        sideMenu.setVisible(false);
        Schedule schedule = ScheduleService.getSchedule();
        if(schedule != null) {
            populateScheduleTable(ScheduleService.getSchedule());
            return;
        }
        JOptionPane.showMessageDialog(null,
                "Проверьте подключение к интернету",
                "Connection error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void populateScheduleTable(Schedule schedule) {
        clearContainers();
        addLessonsToContainer(mondayContainer, schedule.getMonday(), "Monday");
        addLessonsToContainer(tuesdayContainer, schedule.getTuesday(), "Tuesday");
        addLessonsToContainer(wednesdayContainer, schedule.getWednesday(), "Wednesday");
        addLessonsToContainer(thursdayContainer, schedule.getThursday(), "Thursday");
        addLessonsToContainer(fridayContainer, schedule.getFriday(), "Friday");
        addLessonsToContainer(saturdayContainer, schedule.getSaturday(), "Saturday");
        addLessonsToContainer(sundayContainer, schedule.getSunday(), "Sunday");
    }

    private void clearContainers() {
        mondayContainer.getChildren().clear();
        tuesdayContainer.getChildren().clear();
        wednesdayContainer.getChildren().clear();
        thursdayContainer.getChildren().clear();
        fridayContainer.getChildren().clear();
        saturdayContainer.getChildren().clear();
        sundayContainer.getChildren().clear();
    }

    private void addLessonsToContainer(VBox container, DayOfWeekScheduled daySchedule, String dayOfWeek) {
        container.getChildren().add(new Label(dayOfWeek + " (" + daySchedule.getDate().toString() + ")"));
        if (daySchedule.getLessons() == null) {
            return;
        }

        for (Lesson lesson : daySchedule.getLessons()) {
            VBox lessonCard = createLessonCard(lesson);
            container.getChildren().add(lessonCard);
        }
    }

    private VBox createLessonCard(Lesson lesson) {
        VBox lessonCard = new VBox();
        lessonCard.getStyleClass().add("lesson-card");

        Label subjectNameLabel = new Label(lesson.getSubjectName());
        subjectNameLabel.getStyleClass().add("subject-name");

        Label subgroupLabel = new Label("Subgroup: " + lesson.getSubgroupType());
        subgroupLabel.getStyleClass().add("subgroup-info");

        Label startTimeLabel = new Label("Start: " + lesson.getStartTime());
        startTimeLabel.getStyleClass().add("start-time");

        Label dayOfWeekLabel = new Label("Day of week: " + lesson.getDayOfWeek().name());
        dayOfWeekLabel.getStyleClass().add("start-time");

        Button registrationButton = new Button();
        registrationButton.setVisible(false);
        Integer numberInQueue = lesson.getNumberInQueue();

        if(lesson.isRegistrationOpen()) {
            registrationButton = new Button("Register");
            registrationButton.setVisible(true);

            registrationButton.setOnAction(event -> {
                registerStudentToQueue(lesson);
            });
        } if(lesson.isRegisteredInQueue()) {
            registrationButton = new Button("Registered\nLeave?");
            registrationButton.setVisible(true);

            registrationButton.setOnAction(event -> {
                removeStudentFromQueue(lesson.getLessonId());
            });
        } if(numberInQueue != null) {
            registrationButton = new Button("You are " + numberInQueue + " in Q");
            registrationButton.setVisible(true);

            registrationButton.setOnAction(event -> {
                System.out.println(lesson.getSubjectName() + " --- " + lesson.getLessonId());
            });
        }

        lessonCard.getChildren().addAll(subjectNameLabel,
                                        subgroupLabel,
                                        startTimeLabel,
                                        dayOfWeekLabel,
                                        registrationButton);
        return lessonCard;
    }

    @FXML
    public void toggleSideMenu() {
        if (isMenuVisible) {
            hideSideMenu();
        } else {
            showSideMenu();
        }
    }

    public void showSideMenu() {
        sideMenu.setVisible(true);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), sideMenu);
        slideIn.setFromX(sideMenu.getWidth());
        slideIn.setToX(0);
        slideIn.setOnFinished(event -> isMenuVisible = true);
        slideIn.play();
    }

    public void hideSideMenu() {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), sideMenu);
        slideOut.setFromX(0);
        slideOut.setToX(-sideMenu.getWidth());
        slideOut.setOnFinished(event -> {
            sideMenu.setVisible(false);
            isMenuVisible = false;
        });
        slideOut.play();
    }

    public void showDeleteAccountForm(MouseEvent mouseEvent) {
        // implementation ...
    }

    private void removeStudentFromQueue(long lessonId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            long studentId = StudentSession.getInstance().getStudentId();
            ScheduleService.removeStudentFromQueue(studentId, lessonId);
        }
        // TODO
        // update schedule
    }

    private void registerStudentToQueue(Lesson lesson) {
        try {
            FXMLLoader loader = new FXMLLoader(MainClient.class.getResource("/views/registerToQueueConfirmation.fxml"));
            VBox dialogRoot = loader.load();

            ConfirmationDialogController controller = loader.getController();
            controller.setLessonId(lesson.getLessonId());
            controller.setDayOfWeek(lesson.getDayOfWeek());
            controller.getSubjectName().setText(lesson.getSubjectName());
            controller.getSubgroup().setText(lesson.getSubgroupType().name());
            controller.getStartTime().setText(lesson.getStartTime().toString());

            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.setTitle("Register to Queue");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(menuButton.getScene().getWindow());
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
