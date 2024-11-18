package by.bsuir.controllers;

import by.bsuir.enums.entityAttributes.SortType;
import by.bsuir.models.dto.GroupQueue;
import by.bsuir.models.dto.QueueInfo;
import by.bsuir.services.ControllerRequestsService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class MyQueuesController {

    @FXML
    private TableView<QueueInfo> queueTable;
    @FXML
    private TableColumn<QueueInfo, String> subjectNameColumn;
    @FXML
    private TableColumn<QueueInfo, LocalDate> dateColumn;
    @FXML
    private TableColumn<QueueInfo, LocalTime> startTimeColumn;
    @FXML
    private TableColumn<QueueInfo, String> subgroupColumn;
    @FXML
    private TableColumn<QueueInfo, String> passingLabsColumn;
    @FXML
    private TableColumn<QueueInfo, String> registrationStatusColumn;
    @FXML
    private TableColumn<QueueInfo, String> numberInQueueColumn;
    @FXML
    private TableColumn<QueueInfo, SortType> sortTypeColumn;

    @FXML
    public void initialize() {
        double columnCount = 8;
        double columnWidth = 1.0 / columnCount;

        subjectNameColumn.prefWidthProperty().bind(queueTable.widthProperty().multiply(columnWidth));
        dateColumn.prefWidthProperty().bind(queueTable.widthProperty().multiply(columnWidth));
        startTimeColumn.prefWidthProperty().bind(queueTable.widthProperty().multiply(columnWidth));
        subgroupColumn.prefWidthProperty().bind(queueTable.widthProperty().multiply(columnWidth));
        passingLabsColumn.prefWidthProperty().bind(queueTable.widthProperty().multiply(columnWidth));
        registrationStatusColumn.prefWidthProperty().bind(queueTable.widthProperty().multiply(columnWidth));
        numberInQueueColumn.prefWidthProperty().bind(queueTable.widthProperty().multiply(columnWidth));
        sortTypeColumn.prefWidthProperty().bind(queueTable.widthProperty().multiply(columnWidth));

        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        subjectNameColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<QueueInfo, String> call(TableColumn<QueueInfo, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setOnMouseClicked(null);
                        } else {
                            setText(item);
                            setOnMouseClicked(event -> {
                                QueueInfo queueInfo = getTableView().getItems().get(getIndex());
                                int numberInQueue = queueInfo.getNumberInQueue();
                                if(numberInQueue == 0) {
                                    return;
                                }
                                showGroupQueueDetails(queueInfo.getLessonId()); // Show modal with GroupQueue data
                            });
                        }
                    }
                };
            }
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        subgroupColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getSubgroupType().toString());
        });
        passingLabsColumn.setCellValueFactory(cellData -> {
            byte[] labs = cellData.getValue().getPassingLabs();
            String labsText = labs != null ? Arrays.toString(labs).replaceAll("[\\[\\]]", "") : "";
            return new SimpleStringProperty(labsText);
        });
        registrationStatusColumn.setCellValueFactory(cellData -> {
            Integer numberInQueue = cellData.getValue().getNumberInQueue();
            return new SimpleStringProperty( (numberInQueue != null && numberInQueue != 0) ? "Finished" : "Not finished");
        });
        numberInQueueColumn.setCellValueFactory(cellData -> {
            Integer numberInQueue = cellData.getValue().getNumberInQueue();
            String displayValue = (numberInQueue != null && numberInQueue != 0) ? numberInQueue.toString() : "-";
            return new SimpleStringProperty(displayValue);
        });
        sortTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sortType"));
    }

    public static void showGroupQueueDetails(long lessonId) {
        try {
            List<GroupQueue> groupQueues = ControllerRequestsService.getGroupQueue(lessonId);
            FXMLLoader loader = new FXMLLoader(MyQueuesController.class.getResource("/views/groupQueueTable.fxml"));
            Parent root = loader.load();

            GroupQueueController controller = loader.getController();
            controller.setGroupQueueData(groupQueues);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Group Queue Details");
            stage.setScene(new Scene(root, 600, 400)); // Set appropriate dimensions
            stage.initModality(Modality.APPLICATION_MODAL); // Make modal
            stage.setResizable(false); // Disable resizing
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("не удалось получить данные с сервера");
            alert.showAndWait();
        }
    }

    public void setQueueData(List<QueueInfo> queueInfos) {
        queueTable.getItems().setAll(queueInfos);
    }
}
