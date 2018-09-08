package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        //load the fxml
        Parent root = FXMLLoader.load(this.getClass().getResource("/gui/res/gui.fxml"));

        //setup the scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass() .getResource("/gui/res/css.css").toExternalForm());

        //finalize
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
        primaryStage.setTitle("untitled");
    }
}
