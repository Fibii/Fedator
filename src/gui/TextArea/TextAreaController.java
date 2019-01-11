package gui.TextArea;

import EditLogic.Connector;
import gui.Controller;
import gui.Mediator.Event;
import gui.Mediator.Mediator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

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

    @Override
    public void eventListener(Event event) {

    }

    @FXML
    public void initialize() {
        textAreaKeyListener();
        textAreaListener();
    }

    private void textAreaListener() {
        //update the getTextArea method whenever the text is changed
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Mediator.getInstance().eventListener(Event.TEXTCHANGED);
            }
        });

    }

    private void textAreaKeyListener(){
        KeyCodeCombination ctrlV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
        KeyCodeCombination ctrlS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        KeyCodeCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        KeyCodeCombination ctrlShiftZ = new KeyCodeCombination(KeyCode.Z,KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN);


        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {

            //TODO:Maybe use a switch here
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    Mediator.getInstance().eventListener(Event.ENTER);
                    textArea.setText(textArea.getText() + "\n"); //TODO:perhaps use a stringBuilder here
                    Mediator.getInstance().setTheCaretToTheLastChar();
                    event.consume();
                }

                if(ctrlV.match(event)){
                    textArea.paste();
                    Mediator.getInstance().eventListener(Event.PASTE);
                    Mediator.getInstance().setTheCaretToTheLastChar();
                    event.consume();
                }

                if(ctrlZ.match(event)){
                    Mediator.getInstance().eventListener(Event.UNDO);
                    Mediator.getInstance().setUndoRedo(true);
                    //TODO:Undo stuff here
                    connector.undo(); //testing only
                    event.consume();
                }

                if(ctrlShiftZ.match(event)){
                    Mediator.getInstance().eventListener(Event.REDO);
                    Mediator.getInstance().setUndoRedo(true);
                    //TODO:Redo stuff here
                    connector.redo(); //testing only
                    event.consume();
                }

                if(ctrlS.match(event)){
                    Mediator.getInstance().eventListener(Event.SAVE);
                    //TODO:Save stuff here
                    event.consume();
                }
            }
        });
    }
}
