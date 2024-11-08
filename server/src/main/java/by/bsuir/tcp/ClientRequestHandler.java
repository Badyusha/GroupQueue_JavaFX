package by.bsuir.tcp;

import by.bsuir.Main;
import by.bsuir.enums.ClientRequestType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientRequestHandler implements Runnable {
    private final ServerResponse serverResponse = Main.springContext.getBean(ServerResponse.class);
    private Socket clientSocket = null;
    public static ObjectInputStream input;
    public static ObjectOutputStream output;

    public ClientRequestHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
        }
        catch(Exception e) {
            //
        }
    }

    @Override
    public void run() {
        try {
            ClientRequestType command;
            while (true) {
                command = (ClientRequestType) input.readObject();
                switch (command) {
                    case AUTHORIZE_STUDENT: {
                        serverResponse.authorizeStudent();
                        break;
                    }
                    case REGISTER_STUDENT: {
                        serverResponse.registerStudent();
                        break;
                    }
                    case GET_SCHEDULE: {
                        serverResponse.getSchedule();
                        break;
                    }
                    case REGISTER_STUDENT_TO_QUEUE: {
                        serverResponse.registerStudentIntoQueue();
                        break;
                    }
                    case REMOVE_STUDENT_FROM_QUEUE: {
                        serverResponse.removeStudentFromQueue();
                        break;
                    }
                    case EXIT: {
                        break;
                    }
                }
            }
        } catch(SocketException e) {
            System.out.println("Пользователь вышел...\nВсего " + (--Main.activeUsers) + " активных пользователей\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
