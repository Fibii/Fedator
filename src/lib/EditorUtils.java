package lib;

import gui.components.TextSpace;
import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class EditorUtils {

    public static boolean writeToFile(String text, Path path){
        if (path == null || text == null){
            return false;
        }
        //Files.write(path,text);
        try {
            Files.write(path.toAbsolutePath(), text.getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void onCloseExitConfirmation() {
        if(Mediator.getInstance().getTextIsChanged()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are you sure you want to exit?");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Do you want to save your changes before quitting?");
            ButtonType yesBtn = new ButtonType("Yes");
            ButtonType noBtn = new ButtonType("No");
            ButtonType cnlBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesBtn, noBtn, cnlBtn);
            Optional<ButtonType> btnClicked = alert.showAndWait();
            if (btnClicked.get() == yesBtn) {
                if(Mediator.getInstance().getFileIsSaved()){ //we already have the path, so auto save the changes with opening the save window
                    Mediator.getInstance().sendEvent(Events.EXIT_EVENT);
                } else {
                    Mediator.getInstance().sendEvent(Events.SAVE_FILE);
                }
            } else if(btnClicked.get() == noBtn){
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public static void setCurrentEditorTitle(Node node, Tab tab, TextSpace textSpace){
        Stage stage = (Stage) node.getParent().getScene().getWindow();
        stage.setTitle(textSpace.getCurrentPath().toString());
        tab.setText(textSpace.getCurrentPath().getFileName().toString());
    }

    public static String readFromFile(File file){
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                sb.append(line + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
