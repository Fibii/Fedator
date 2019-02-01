package gui.components;

import gui.mediator.Mediator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
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

    @FXML
    void inputChanged(ActionEvent event) {
        System.out.println("hi, input is changed");
    }

    @FXML public void initialize(){
        textAreaListener();
        mediator.setTextSpace(this);
    }

    private void textAreaListener() {
        //update the getTextArea method whenever the text is changed
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //System.out.println("textSpace " + textSpaceNumber +  " : changed");
                mediator.getCurrentConnector().update(textArea.getText());
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
    }
}
