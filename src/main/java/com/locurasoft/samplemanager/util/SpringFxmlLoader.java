package com.locurasoft.samplemanager.util;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ResourceBundle;

public class SpringFxmlLoader {
    private static final ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("applicationContext.xml");

    public Object load(String url, String resources) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);
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
