package com.locurasoft.samplemanager;

import com.locurasoft.samplemanager.controller.MainController;
import com.locurasoft.samplemanager.util.SpringFxmlLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 400;
    private MainController controller;

    @Override
    public void start(Stage primaryStage) {
        SpringFxmlLoader loader = new SpringFxmlLoader();
        Parent root = (Parent) loader.load("/main.fxml", "application");
        controller = loader.getController();
        primaryStage.setTitle("Sample Manager");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        controller.onApplicationExit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
