package by.bsuir;

import by.bsuir.utils.tcp.ServerSocketInfo;
import by.bsuir.utils.tcp.ClientRequest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainClient extends Application {
    public static void main(String[] args) {
        try {
            connect();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Не удалось подключиться к серверу",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClient.class.getResource("/views/authorizationPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("GroupQueue");
        stage.setResizable(false);
        stage.show();
    }

    private static void connect() throws IOException {
        ClientRequest.clientSocket = new Socket(ServerSocketInfo.HOST, ServerSocketInfo.PORT);
        ClientRequest.output = new ObjectOutputStream(ClientRequest.clientSocket.getOutputStream());
        ClientRequest.input = new ObjectInputStream(ClientRequest.clientSocket.getInputStream());
    }
}
