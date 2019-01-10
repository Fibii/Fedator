package gui.TextArea;

import EditLogic.Connector;
import gui.Controller;
import gui.LineCounter.LineCounterController;
import gui.Mediator.Mediator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TextAreaController implements Controller {
    private String text;
    private boolean undoRedo;
    private Connector connector;
    private boolean changed = true;

    @FXML
    private TextArea textArea;

    @FXML
    void onChange(ActionEvent event) {

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
        return textArea;
    }

    @Override
    public boolean getUndoRedo() {
        return undoRedo;
    }

    @Override
    public boolean changed(){
        return changed;
    }

    public void initialize(){

    }

    private void textAreaListener() {

        //KeyCodeCombination ctrlV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

        //update the getTextArea method whenever the text is changed
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changed = true;
            }
        });

    }


}
