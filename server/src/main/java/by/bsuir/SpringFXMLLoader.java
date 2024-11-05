package by.bsuir;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class SpringFXMLLoader {

	public static Parent load(ApplicationContext context, Class<?> clazz, String fxmlPath) {
		FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlPath));
		loader.setControllerFactory(context::getBean);
		try {
			loader.load();
		} catch (IOException ex) {
			System.err.println("Cannot load resource " + fxmlPath);
		}

		return loader.getRoot();
	}
}

