package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Start extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        File gui = new File("src/gui/gui.fxml");
        Parent root = FXMLLoader.load(gui.toURL());

        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass() .getResource("/gui/css.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
