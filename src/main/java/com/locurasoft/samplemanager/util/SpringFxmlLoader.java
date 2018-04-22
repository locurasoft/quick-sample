package com.locurasoft.samplemanager.util;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ResourceBundle;

public class SpringFxmlLoader {
    private static final ApplicationContext APPLICATION_CONTEXT =
            new ClassPathXmlApplicationContext("applicationContext.xml");

    private final FXMLLoader loader;

    public SpringFxmlLoader() {
        loader = new FXMLLoader();
        loader.setControllerFactory(APPLICATION_CONTEXT::getBean);
    }

    public <T> T getController() {
        return loader.getController();
    }

    public Object load(String url, String resources) {
        loader.setLocation(getClass().getResource(url));
        loader.setResources(ResourceBundle.getBundle(resources));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
