package by.bsuir.utils.tcp;

import by.bsuir.Main;
import by.bsuir.enums.ClientRequestType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientRequestHandler implements Runnable {
    private final ServerResponse serverResponse = Main.springContext.getBean(ServerResponse.class);
    public static ObjectInputStream input;
    public static ObjectOutputStream output;

    public ClientRequestHandler(Socket clientSocket) {
        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
        }
        catch(Exception e) {
            System.err.println("Unable to get IO streams");
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
                    case GET_STUDENT_INFO: {
                        serverResponse.getStudentInfo();
                        break;
                    }
                    case EDIT_STUDENT_PROFILE: {
                        serverResponse.editProfile();
                        break;
                    }
                    case GET_QUEUE_INFO: {
                        serverResponse.getQueueInfo();
                        break;
                    }
                    case GET_GROUP_QUEUE: {
                        serverResponse.getGroupQueue();
                        break;
                    }
                    case BECOME_GROUP_ADMIN: {
                        serverResponse.becomeGroupAdmin();
                        break;
                    }
                    case GET_GROUP_SCHEDULES: {
                        serverResponse.getGroupSchedules();
                        break;
                    }
                    case CHANGE_SORT_TYPE: {
                        serverResponse.changeSortType();
                        break;
                    }
                    case GET_REQUESTS: {
                        serverResponse.getRequests();
                        break;
                    }
                    case ACCEPT_REQUEST: {
                        serverResponse.acceptRequest();
                        break;
                    }
                    case DECLINE_REQUEST: {
                        serverResponse.declineRequest();
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
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
