package by.bsuir.utils;

import by.bsuir.models.dto.Pair;
import by.bsuir.MainClient;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowManager {
    public static Stage getStage(Event event) {
        return (Stage)((Node) event.getSource()).getScene().getWindow();
    }

    public static void closeWindow(Event event) {
        getStage(event).close();
    }

    public static void generateWindow(String fxmlPath,
                                      String windowTitle,
                                      boolean isResizable,
                                      boolean isMaximized,
                                      Pair<Integer, Integer> widthAndHeight,
                                      Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainClient.class.getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), widthAndHeight.getFirst(), widthAndHeight.getSecond());
            Stage stage = getStage(event);
            stage.setScene(scene);
            stage.setTitle(windowTitle);
            stage.setResizable(isResizable);
            stage.setMaximized(isMaximized);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Cannot load FXML Scene:", e);
        }
    }
}
