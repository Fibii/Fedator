package gui.components;

import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TextSpace extends HBox {
    //TODO: Finish undo/redo
    private int textSpaceNumber = 0;
    private Mediator mediator = Mediator.getInstance();

    public TextSpace() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/res/textspace.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private TextArea lineCounter;

    @FXML
    private TextArea textArea;


    @FXML public void initialize(){
        textAreaChangeListener();
        textAreaKeyboardListener();
        mediator.setTextSpace(this);
    }

    private void textAreaChangeListener() {
        //update the getTextArea method whenever the text is changed
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //System.out.println("textSpace " + textSpaceNumber +  " : changed");
                mediator.sendEvent(Events.TEXT_CHANGED);
                mediator.getCurrentConnector().update(textArea.getText());
                //TODO: fix redo/undo so it catches text change (select all text then backspace = no change)
            }
        });
    }

    private void textAreaKeyboardListener(){
        KeyCodeCombination ctrlV = new KeyCodeCombination(KeyCode.V,KeyCodeCombination.CONTROL_DOWN);
        KeyCodeCombination ctrlZ = new KeyCodeCombination(KeyCode.Z,KeyCodeCombination.CONTROL_DOWN);
        KeyCodeCombination ctrlShiftZ = new KeyCodeCombination(KeyCode.Z,KeyCodeCombination.CONTROL_DOWN,KeyCodeCombination.SHIFT_DOWN);


        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //handle pasting using keyboard
                if(ctrlV.match(event)){
                    mediator.sendEvent(Events.TEXT_CHANGED);
                    //TODO: increment the number of lines here.
                    setTheCaretToTheLastChar();
                }

                if(ctrlZ.match(event)){
                    mediator.sendEvent(Events.UNDO_TEXT);
                    event.consume();
                }

                if(ctrlShiftZ.match(event)){
                    mediator.sendEvent(Events.REDO_TEXT);
                    event.consume();
                }
            }
        });
    }
    /** sets the number of the textspace (used when dealing with multiple tabs) */
    public void setNumber(int n){
        textSpaceNumber = n;
    }

    public void undo(){
        //System.out.println("undo called on textSpace " + textSpaceNumber);
        mediator.getCurrentConnector().undo();
        textArea.setText(mediator.getCurrentConnector().getText());
        setTheCaretToTheLastChar();
        mediator.sendEvent(Events.TEXT_CHANGED);
    }

    public void redo(){
        mediator.getCurrentConnector().redo();
        textArea.setText(mediator.getCurrentConnector().getText());
        setTheCaretToTheLastChar();
        mediator.sendEvent(Events.TEXT_CHANGED);
    }

    public void setText(String text){
        textArea.setText(text);
    }

    public String getText(){
        return textArea.getText();
    }

    private void setTheCaretToTheLastChar(){
        textArea.positionCaret(textArea.getText().length()-1);
    }
}
