package by.bsuir.groupqueuefx.controllers;

import by.bsuir.groupqueuefx.models.dto.DayOfWeekScheduled;
import by.bsuir.groupqueuefx.models.dto.Lesson;
import by.bsuir.groupqueuefx.models.dto.Schedule;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
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

    public void initialize() {
        sideMenu.setVisible(false);
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

        Label registrationLabel = new Label(
                lesson.isRegisteredInQueue() ? "Registered" : "Not Registered"
        );
        registrationLabel.getStyleClass().add(
                lesson.isRegisteredInQueue() ? "registered" : "not-registered"
        );

        lessonCard.getChildren().addAll(subjectNameLabel, subgroupLabel, startTimeLabel, registrationLabel, dayOfWeekLabel);
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
}
