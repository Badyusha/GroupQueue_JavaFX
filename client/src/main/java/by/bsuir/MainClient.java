package by.bsuir;

import by.bsuir.tcp_ip.ServerSocketInfo;
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
    public static Socket clientSocket;
    public static ObjectOutputStream output;
    public static ObjectInputStream input;


    public static void main(String[] args) {
        connect();
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

    private static void connect() {
        try {
            clientSocket = new Socket(ServerSocketInfo.HOST, ServerSocketInfo.PORT);
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Не удалось подключиться к серверу",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Connected to server");
    }
}
