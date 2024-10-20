package by.bsuir.groupqueuefx.utils.fabrics;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringBeanControllerFactory {
    @Setter
    @Getter
    private static ConfigurableApplicationContext springContext;

    private static <T> T getController() {
//        ControllerClass clazz = springContext.registerShutdownHook(ControllerClass.class);
//        clazz.findAll();
        return null;
    }
}
