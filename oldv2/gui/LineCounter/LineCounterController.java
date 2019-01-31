package oldv2.gui.LineCounter;

import oldv2.EditLogic.Connector;
import oldv2.gui.Controller;
import oldv2.gui.Mediator.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

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

    @Override
    public TextArea getLineCounter(){
        return getTextArea();
    }

}
