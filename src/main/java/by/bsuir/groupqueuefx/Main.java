package by.bsuir.groupqueuefx;

import by.bsuir.groupqueuefx.utils.fabrics.SpringBeanControllerFactory;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Main extends Application {
	private ConfigurableApplicationContext springContext;

	@Override
	public void init() {
		springContext = SpringApplication.run(SpringBootApplication.class);
		SpringBeanControllerFactory.setSpringContext(springContext);
//		springContext = new SpringApplicationBuilder(SpringBootApplicationJPA.class).run();
	}

	@Override
	public void stop() {
		springContext.close();
	}

	@Override
	public void start(Stage stage) {
		Parent root = SpringFXMLLoader.load(springContext, SpringBootApplication.class, "views/authorizationPage.fxml");
		stage.setScene(new Scene(root, 600, 400));
		stage.setTitle("GroupQueue");
		stage.setResizable(false);
		stage.show();
	}
}