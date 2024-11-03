package by.bsuir.groupqueuefx.utils;

import by.bsuir.groupqueuefx.SpringBootApplication;
import by.bsuir.groupqueuefx.SpringFXMLLoader;
import by.bsuir.groupqueuefx.models.dto.Pair;
import by.bsuir.groupqueuefx.utils.fabrics.SpringBeanControllerFactory;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        Parent root = SpringFXMLLoader.load(SpringBeanControllerFactory.getSpringContext(),
                                            SpringBootApplication.class,
                                            fxmlPath);
        Stage stage = getStage(event);
        stage.setScene(new Scene(root, widthAndHeight.getFirst(), widthAndHeight.getSecond()));
        stage.setTitle(windowTitle);
        stage.setResizable(isResizable);
        stage.setMaximized(isMaximized);
        stage.show();
    }
}
