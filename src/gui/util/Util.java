package gui.util;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;

public class Util {


    public static void closeStage(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }


    public static void saveFile(ObservableList<CharSequence> textlist, File fileName){

        Charset charset = Charset.forName("US-ASCII");
        ObservableList<CharSequence> list = textlist;
        Path file = Paths.get(fileName.toURI());
        for (int i = 0 ; i < list.size(); i++){
            try (BufferedWriter writer = Files.newBufferedWriter(file,charset)) {
                writer.write(list.get(i).toString(), 0, list.get(i).length());
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
    }

    /*
    public static void saveFile(ObservableList<CharSequence> textlist){
        Charset charset = Charset.forName("US-ASCII");
        ObservableList<CharSequence> list = textlist;
        for (int i = 0 ; i < list.size(); i++){
            try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
                writer.write(s, 0, s.length());
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
    } */
}
