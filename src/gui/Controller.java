package gui;

import EditLogic.Connector;
import gui.Mediator.Event;
import javafx.scene.control.TextArea;

public interface Controller {
    //TODO:Better structure can be done here, like maybe have a method on Mediator like setController(cont,value){ cont.setValue(value) }
    void setText(String text);
    void setUndoRedo(boolean undoRedo);
    void setConnector(Connector connector);
    TextArea getTextArea();
    boolean changed();
    boolean getUndoRedo();
    void eventListener(Event event);
}
