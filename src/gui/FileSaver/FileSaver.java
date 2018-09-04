package gui.FileSaver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class FileSaver  extends Application {

    @Override public void start(Stage stage) throws IOException {
        File gui = new File("src/gui/FileSaver/fileSaver.fxml");
        Parent root = FXMLLoader.load(gui.toURL());

        Scene scene = new Scene(root);


        stage.setTitle("Save as");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
    }
}
