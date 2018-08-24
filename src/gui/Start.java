package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Start extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        File gui = new File("src/gui/gui.fxml");
        Parent root = FXMLLoader.load(gui.toURL());

        Scene scene = new Scene(root);


        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
