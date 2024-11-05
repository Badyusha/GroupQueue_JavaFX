package by.bsuir;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientRequestHandler implements Runnable{
    Socket clientSocket = null;
    ObjectInputStream input;
    ObjectOutputStream output;

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
    }
}
