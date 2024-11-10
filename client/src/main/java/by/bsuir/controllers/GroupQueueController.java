package by.bsuir.controllers;

import by.bsuir.models.dto.GroupQueue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;
import java.util.Arrays;

public class GroupQueueController {

    @FXML
    private TableView<GroupQueue> groupQueueTable;
    @FXML
    private TableColumn<GroupQueue, Integer> numberInQueueColumn;
    @FXML
    private TableColumn<GroupQueue, String> lastNameColumn;
    @FXML
    private TableColumn<GroupQueue, String> firstNameColumn;
    @FXML
    private TableColumn<GroupQueue, String> usernameColumn;
    @FXML
    private TableColumn<GroupQueue, String> passingLabsColumn;

    @FXML
    public void initialize() {
        numberInQueueColumn.setCellValueFactory(new PropertyValueFactory<>("numberInQueue"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Convert byte[] passingLabs to a displayable string
        passingLabsColumn.setCellValueFactory(cellData -> {
            byte[] labs = cellData.getValue().getPassingLabs();
            String labsText = labs != null ? Arrays.toString(labs).replaceAll("[\\[\\]]", "") : "";
            return new SimpleStringProperty(labsText);
        });
    }

    public void setGroupQueueData(List<GroupQueue> groupQueueList) {
        groupQueueTable.getItems().setAll(groupQueueList);
    }
}
