package lib;

import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

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

}
