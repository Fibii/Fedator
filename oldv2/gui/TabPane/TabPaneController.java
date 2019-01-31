package oldv2.gui.TabPane;

import oldv2.EditLogic.Connector;
import oldv2.gui.Controller;
import oldv2.gui.Mediator.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

public class TabPaneController implements Controller{

    @FXML
    private TextArea lineCounter;

    @FXML
    private HBox hbox;

    @FXML
    private Tab tab1;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextArea textArea;


    @FXML
    void tab1OnClose(ActionEvent event) {

    }

    @FXML
    void tab1OnRequest(ActionEvent event) {

    }

    @FXML
    void onChange(ActionEvent event) {

    }

    @Override
    public void setText(String text) {

    }

    @Override
    public void setUndoRedo(boolean undoRedo) {

    }

    @Override
    public void setConnector(Connector connector) {

    }

    @Override
    public TextArea getTextArea() {
        return textArea;
    }

    @Override
    public TextArea getLineCounter() {
        return lineCounter;
    }

    @Override
    public boolean changed() {
        return false;
    }

    @Override
    public boolean getUndoRedo() {
        return false;
    }

    @Override
    public void eventListener(Event event) {

    }

    @FXML public void initialize(){
    }
}
