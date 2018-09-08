package gui.util;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;

public class Util {

    public static void closeStage(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static void saveFile(String text, String path) {
        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.println(text);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


