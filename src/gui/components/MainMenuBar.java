package gui.components;

import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;

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
        mediator.sendEvent(Events.OPEN_MENU);
    }

    @FXML
    void saveMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.SAVE_MENU);
    }

    @FXML
    void closeMenuItemClick(ActionEvent event) {
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
}
