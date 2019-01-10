package gui.LineCounter;

import EditLogic.Connector;
import gui.Controller;
import gui.Mediator.Mediator;
import gui.TextArea.TextAreaController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.print.attribute.standard.Media;

public class LineCounterController implements Controller {

    private String text;
    private boolean undoRedo;
    private Connector connector;

    @FXML
    private TextArea lineCounter;

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
        return lineCounter;
    }

    @Override
    public boolean getUndoRedo() {
        return undoRedo;
    }

    @Override
    public boolean changed(){
        return false;
    }

    public void initialize() {
        if(Mediator.getInstance() != null) {
            Mediator.getInstance().changed();
        }
    }




}
