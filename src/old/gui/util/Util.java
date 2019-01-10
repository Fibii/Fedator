package old.gui.util;

import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.*;

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


