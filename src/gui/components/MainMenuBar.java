package gui.components;

import lib.EditorUtils;
import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenuBar extends MenuBar {

    @FXML
    private MenuItem undo;

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem about;

    @FXML
    private MenuItem redo;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem newTab;

    @FXML
    private MenuItem open;

    private Mediator mediator = Mediator.getInstance();
    private FileChooser fileChooser = new FileChooser();
    private String text;
    private Path filePath;
    private List<String> lines;

    public MainMenuBar(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/res/menubar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void onNewTabClick(ActionEvent event) {
        mediator.sendEvent(Events.NEW_TAB);
    }

    @FXML
    void openMenuItemClick(ActionEvent event) {
        fileChooser.setTitle("title");
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(open.getParentPopup().getScene().getWindow());
        if (file != null){
                lines = EditorUtils.readFromFile(file);
                text = lines.stream().collect(Collectors.joining("\n"));
                filePath = file.toPath();
                mediator.sendEvent(Events.OPEN_MENU);
        }
    }

    @FXML
    void saveMenuItemClick(ActionEvent event) {
        if(mediator.getFileIsSaved()){
            mediator.sendEvent(Events.AUTO_SAVE);
        } else {
            showSaveWindow();
            mediator.sendEvent(Events.SAVE_MENU);
        }
    }

    @FXML
    void closeMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.CLOSE_MENU);
        EditorUtils.onCloseExitConfirmation();
    }

    @FXML
    void undoMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.UNDO_TEXT);
    }

    @FXML
    void redoMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.REDO_TEXT);
    }

    @FXML
    void aboutMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.ABOUT_MENU);
    }

    @FXML public void initialize(){
        mediator.setMenuBar(this);
    }

    //returns the text of the opened file
    public String getText(){
        return text;
    }

    public void showSaveWindow(){
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(save.getParentPopup().getScene().getWindow());
        if (file == null){ //check if the user clicked the cancel button
            return;
        }
        file = new File(file.getPath() + ".txt"); //might be only in linux that the file is not saved as title.txt
        EditorUtils.writeToFile(mediator.getCurrentText(),file.toPath());
        mediator.sendEvent(Events.SAVE_MENU);
    }

    public Path getSavedFilePath(){
        return filePath;
    }

    public int getNumberOfLines(){
        return lines.size();
    }
}
