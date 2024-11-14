package by.bsuir.controllers;

import by.bsuir.enums.ServerResponseType;
import by.bsuir.enums.entityAttributes.RequestType;
import by.bsuir.models.dto.Request;
import by.bsuir.services.ControllerRequestsService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;

public class RequestsController {

    @FXML
    public Button refreshPage;
    @FXML
    private TableView<Request> requestsTable;
    @FXML
    private TableColumn<Request, String> lastNameColumn;
    @FXML
    private TableColumn<Request, String> firstNameColumn;
    @FXML
    private TableColumn<Request, String> usernameColumn;
    @FXML
    private TableColumn<Request, String> roleTypeColumn;
    @FXML
    private TableColumn<Request, String> requestTypeColumn;
    @FXML
    private TableColumn<Request, Integer> groupNumberColumn;
    @FXML
    private TableColumn<Request, Void> actionColumn;

    @FXML
    public void initialize() {
        // Fetch requests from the service when the controller is initialized
        fetchRequestsAndPopulateTable();

        // Table column widths and bindings
        double columnCount = 7;
        double columnWidth = 1.0 / columnCount;

        lastNameColumn.prefWidthProperty().bind(requestsTable.widthProperty().multiply(columnWidth));
        firstNameColumn.prefWidthProperty().bind(requestsTable.widthProperty().multiply(columnWidth));
        usernameColumn.prefWidthProperty().bind(requestsTable.widthProperty().multiply(columnWidth));
        roleTypeColumn.prefWidthProperty().bind(requestsTable.widthProperty().multiply(columnWidth));
        groupNumberColumn.prefWidthProperty().bind(requestsTable.widthProperty().multiply(columnWidth));
        requestTypeColumn.prefWidthProperty().bind(requestsTable.widthProperty().multiply(columnWidth));
        actionColumn.prefWidthProperty().bind(requestsTable.widthProperty().multiply(columnWidth));

        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        roleTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoleType().name()));
        groupNumberColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGroupNumber()));
        requestTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRequestType().name()));

        actionColumn.setCellFactory(col -> new TableCell<Request, Void>() {
            private final Button acceptButton = new Button("Accept");
            private final Button declineButton = new Button("Decline");

            {
                // Set up action for accept and decline buttons
                acceptButton.setOnAction(event -> {
                    Request request = getTableView().getItems().get(getIndex());
                    acceptRequest(new Request(request.getRequestType(), request.getStudentId()));
                });
                declineButton.setOnAction(event -> {
                    Request request = getTableView().getItems().get(getIndex());
                    declineRequest(new Request(request.getRequestType(), request.getStudentId()));
                });

                // Create HBox for action buttons
                HBox buttons = new HBox(5, acceptButton, declineButton);
                setGraphic(buttons);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null); // Hide the action buttons if the row is empty or contains no data
                } else {
                    setGraphic(new HBox(5, acceptButton, declineButton)); // Show action buttons when there is data
                }
            }

            private void acceptRequest(Request request) {
                ServerResponseType serverResponseType = ControllerRequestsService.acceptRequest(request);
                if (serverResponseType.equals(ServerResponseType.ERROR)) {
                    showAlert("Error", "Failed to accept the request", Alert.AlertType.ERROR);
                    return;
                }
                showAlert("Confirmation", "Request successfully accepted", Alert.AlertType.CONFIRMATION);
                initialize();
            }

            private void declineRequest(Request request) {
                ServerResponseType serverResponseType = ControllerRequestsService.declineRequest(request);
                if (serverResponseType.equals(ServerResponseType.ERROR)) {
                    showAlert("Error", "Failed to decline the request", Alert.AlertType.ERROR);
                    return;
                }
                showAlert("Confirmation", "Request successfully declined", Alert.AlertType.CONFIRMATION);
                initialize();
            }
        });

    }

    private void fetchRequestsAndPopulateTable() {
        try {
            List<Request> requests = ControllerRequestsService.getRequests();
            populateRequestsTable(requests);
        } catch (Exception e) {
            showAlert("Error", "Failed to fetch requests", Alert.AlertType.ERROR);
        }
    }

    public void populateRequestsTable(List<Request> requests) {
        requestsTable.getItems().setAll(requests);
    }

    private void showAlert(String title, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}
