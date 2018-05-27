package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("PAM");
        primaryStage.setScene(new Scene(root, 670, 550));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Run the program
    public static void main(String[] args) {
        launch(args);
    }
}
