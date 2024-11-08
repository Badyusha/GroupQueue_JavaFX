package by.bsuir;

import by.bsuir.tcp.ClientRequestHandler;
import by.bsuir.tcp.ServerSocketInfo;
import by.bsuir.utils.fabrics.SpringBeanControllerFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Main extends Application {
	public static int activeUsers = 0;
	public static ConfigurableApplicationContext springContext;
	private static ServerSocket serverSocket;

	public static void startServer() {
		try {
			Socket clientSocket = null;
			try {
				serverSocket = new ServerSocket(ServerSocketInfo.PORT);
				System.out.println("<=======> SERVER IS RUNNING <=======>");
				while(true) {
					clientSocket = serverSocket.accept();

					Runnable run = new ClientRequestHandler(clientSocket);
					Thread newThread = new Thread(run);
					newThread.start();
					System.out.println("Новое подключение...\nВсего " + (++activeUsers) + " активных пользователей\n");
				}
			}
			finally {
                assert clientSocket != null;
                clientSocket.close();
				serverSocket.close();
			}
		}
		catch(IOException e) {
			//
		}
	}

	@Override
	public void init() {
		springContext = SpringApplication.run(SpringBootApp.class);
		SpringBeanControllerFactory.setSpringContext(springContext);
//		springContext = new SpringApplicationBuilder(SpringBootApplicationJPA.class).run();
	}

	@Override
	public void stop() {
		springContext.close();
	}

	@Override
	public void start(Stage stage) {
		startServer();
	}
}