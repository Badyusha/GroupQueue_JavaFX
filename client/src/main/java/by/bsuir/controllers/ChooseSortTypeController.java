package by.bsuir.controllers;

import by.bsuir.enums.ServerResponseType;
import by.bsuir.enums.entityAttributes.SortType;
import by.bsuir.models.dto.GroupSchedule;
import by.bsuir.services.ControllerRequestsService;
import by.bsuir.utils.StudentSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ChooseSortTypeController {

    @FXML
    public Button refreshPage;
    @FXML
    private TableView<GroupSchedule> lessonsTable;
    @FXML
    private TableColumn<GroupSchedule, String> subjectNameColumn;
    @FXML
    private TableColumn<GroupSchedule, String> subgroupColumn;
    @FXML
    private TableColumn<GroupSchedule, LocalDate> dateColumn;
    @FXML
    private TableColumn<GroupSchedule, LocalTime> startTimeColumn;
    @FXML
    private TableColumn<GroupSchedule, SortType> sortTypeColumn;

    public void initialize() {
        lessonsTable.getItems().clear();

        // Configure columns with PropertyValueFactories
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        subgroupColumn.setCellValueFactory(new PropertyValueFactory<>("subgroupType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        // Set up the sortTypeColumn to display a ComboBox with all SortType values
        sortTypeColumn.setCellFactory(column -> new TableCell<GroupSchedule, SortType>() {
            private final ComboBox<SortType> comboBox = new ComboBox<>(FXCollections.observableArrayList(SortType.values()));

            @Override
            protected void updateItem(SortType item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    GroupSchedule groupSchedule = getTableRow().getItem();

                    // Remove any existing listener before setting a new one to avoid multiple handlers
                    comboBox.setOnAction(null);

                    // Set the ComboBox to the current SortType in GroupSchedule
                    comboBox.setValue(groupSchedule.getSortType() != null ? groupSchedule.getSortType() : SortType.SIMPLE);

                    // Add a new listener to update the GroupSchedule object when ComboBox value changes
                    comboBox.setOnAction(event -> {
                        SortType newSortType = comboBox.getValue();
                        groupSchedule.setSortType(newSortType);
                        changeSortType(getTableRow().getItem().getLessonId(), newSortType);
                    });

                    // Display the ComboBox in the cell
                    setGraphic(comboBox);
                }
            }
        });

        double columnWidth = 1.0 / 5; // Assuming 5 columns, adjust if column count changes
        subjectNameColumn.prefWidthProperty().bind(lessonsTable.widthProperty().multiply(columnWidth));
        subgroupColumn.prefWidthProperty().bind(lessonsTable.widthProperty().multiply(columnWidth));
        dateColumn.prefWidthProperty().bind(lessonsTable.widthProperty().multiply(columnWidth));
        startTimeColumn.prefWidthProperty().bind(lessonsTable.widthProperty().multiply(columnWidth));
        sortTypeColumn.prefWidthProperty().bind(lessonsTable.widthProperty().multiply(columnWidth));

        lessonsTable.setItems(loadLessonsData());
    }

    private ObservableList<GroupSchedule> loadLessonsData() {
        try {
            List<GroupSchedule> groupSchedules = ControllerRequestsService.
                                                getGroupSchedules(StudentSession.getInstance().getGroupId());
            return FXCollections.observableArrayList(groupSchedules);
        } catch (Exception e) {
            showAlert("Ошибка сервера", "Не удалось получить данные сервера", Alert.AlertType.ERROR);
        }
        return null;
    }

    private void changeSortType(long lessonId, SortType newSortType) {
        GroupSchedule groupSchedule = new GroupSchedule(lessonId, newSortType);

        ServerResponseType serverResponseType;
        try {
            serverResponseType = ControllerRequestsService.changeSortType(groupSchedule);
        } catch(Exception e) {
            showAlert("Ошибка", "Не удалось обновить тип сортировки", Alert.AlertType.ERROR);
            return;
        }

        if(serverResponseType.equals(ServerResponseType.ERROR)) {
            showAlert("Ошибка", "Не удалось обновить тип сортировки", Alert.AlertType.ERROR);
            return;
        }
        showAlert("Подтверждение", "Тип сортировки успешно изменен", Alert.AlertType.CONFIRMATION);

        initialize();
    }

    private void showAlert(String title, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
