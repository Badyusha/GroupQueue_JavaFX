package by.bsuir;

import by.bsuir.tcp_ip.ServerSocketInfo;
import by.bsuir.utils.fabrics.SpringBeanControllerFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Application {
	private ConfigurableApplicationContext springContext;
	private static ServerSocket serverSocket;

	public static void startServer() {
		try {
			Socket clientSocket = null;
			try {
				serverSocket = new ServerSocket(ServerSocketInfo.PORT);
				System.out.println("-------SERVER IS RUNNING-------");
				while(true){
					clientSocket = serverSocket.accept();
					Runnable run = new ClientRequestHandler(clientSocket);
					Thread newThread = new Thread(run);
					newThread.start();
					System.out.println("Новое подключение...\n");
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