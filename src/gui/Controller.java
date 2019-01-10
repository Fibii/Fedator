package gui;

import EditLogic.Connector;
import javafx.event.Event;
import javafx.scene.control.TextArea;

public interface Controller {
    void setText(String text);
    void setUndoRedo(boolean undoRedo);
    void setConnector(Connector connector);
    TextArea getTextArea();
    boolean changed();
    boolean getUndoRedo();
}
