package gui.LineCounter;

import EditLogic.Connector;
import gui.Controller;
import gui.Mediator.Event;
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
    private boolean changed = false;
    private StringBuilder lineCount = new StringBuilder();

    @FXML
    private TextArea lineCounter;

    @Override
    public void setText(String text) {
        this.text = text;
        changed();
        System.out.println("hi from line counter");
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

        return true;
    }

    @Override
    public void eventListener(Event event) {
        switch (event){
            case ENTER:
                //TODO: line++
                System.out.println("hi from lineCounter, enter key was pressed");
                break;
            case PASTE:
                //TODO:loop n stuff
                break;
        }
    }

    @FXML
    public void initialize() {

    }




}
