package gui.components;

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
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
    //TODO:add a var to check if the file is new or, opened, saved or whatever
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
            try {
                text = new String(Files.readAllBytes(Paths.get(file.getPath())), StandardCharsets.UTF_8);
                mediator.sendEvent(Events.OPEN_MENU);
            } catch (IOException e){
                System.out.println("file was not found");
            }
        }
    }

    @FXML
    void saveMenuItemClick(ActionEvent event) {
        fileChooser.setTitle("title"); //TODO: get a title variable..
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(save.getParentPopup().getScene().getWindow());
        file = new File(file.getPath() + ".txt"); //might be only in linux that the file is not saved as title.txt
        if(file != null){
            try {
                Files.write(Paths.get(file.toURI()), mediator.getCurrentText().getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediator.sendEvent(Events.SAVE_MENU);
    }

    @FXML
    void closeMenuItemClick(ActionEvent event) {
        //TODO:check if the file is saved or not, show an alert, then send the event
        mediator.sendEvent(Events.CLOSE_MENU);
    }

    @FXML
    void undoMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.UNDO_MENU);
    }

    @FXML
    void redoMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.REDO_MENU);
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
}
