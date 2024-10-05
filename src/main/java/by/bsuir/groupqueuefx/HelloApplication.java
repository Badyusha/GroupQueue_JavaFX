package by.bsuir.groupqueuefx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class HelloApplication extends Application {
	private ConfigurableApplicationContext springContext;

	@Override
	public void init() {
		springContext = SpringApplication.run(SpringBootApplicationJPA.class);
//		springContext = new SpringApplicationBuilder(SpringBootApplicationJPA.class).run();
	}

	@Override
	public void stop() {
		springContext.close();
	}

	@Override
	public void start(Stage stage) {
		Parent root = SpringFXMLLoader.load(springContext, SpringBootApplicationJPA.class, "hello-view.fxml");
		Scene scene = new Scene(root, 320, 240);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
	}
}