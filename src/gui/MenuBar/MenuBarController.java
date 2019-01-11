package gui.MenuBar;

import EditLogic.Connector;
import gui.Controller;
import gui.Mediator.Event;
import gui.TextArea.TextAreaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;


public class MenuBarController implements Controller {
    private String text;
    private boolean undoRedo;
    private Connector connector;

    @FXML
    private MenuBar menubar;

    @FXML
    private MenuItem undo;

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem about;

    @FXML
    private MenuItem redo;

    @FXML
    private Pane menubarPane;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem open;

    @FXML
    void openMenuItemClick(ActionEvent event) {

    }

    @FXML
    void saveMenuItemClick(ActionEvent event) {

    }

    @FXML
    void closeMenuItemClick(ActionEvent event) {

    }

    @FXML
    void undoMenuItemClick(ActionEvent event) {

    }

    @FXML
    void redoMenuItemClick(ActionEvent event) {

    }

    @FXML
    void aboutMenuItemClick(ActionEvent event) {

    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setUndoRedo(boolean undoRedo) {
        this.undoRedo = undoRedo;
    }

    @Override
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    @Override
    public TextArea getTextArea() {
        return null;
    }

    @Override
    public boolean getUndoRedo() {
        return undoRedo;
    }

    @Override
    public boolean changed(){
        return false;
    }

    @Override
    public void eventListener(Event event) {

    }

    @FXML
    public void initialize() {

    }
}
