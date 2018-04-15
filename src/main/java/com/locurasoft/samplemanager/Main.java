package com.locurasoft.samplemanager;

import com.locurasoft.samplemanager.util.SpringFxmlLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SpringFxmlLoader loader = new SpringFxmlLoader();
        Parent root = (Parent) loader.load("/main.fxml", "application");
        primaryStage.setTitle("Sample Manager");
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
