package gui.components;
import gui.mediator.Events;
import gui.mediator.IMediator;
import gui.mediator.Mediator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import org.fxmisc.richtext.*;
import smallUndoEngine.EditorTextHistory;

import java.io.IOException;
import java.nio.file.Path;

public class TextSpace extends HBox {
    private int textSpaceNumber = 0;
    private IMediator mediator = Mediator.getInstance();
    private Path currentPath;
    @FXML
    private CodeArea textArea;



    /**
     * loads textspace.fxml and specifies the controller and the root
     */
    public TextSpace() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/textspace.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * starts {@link TextSpace#textAreaChangeListener}
     * sets the current TextSpace in the mediator to the current instance
     * adds line number to textArea
     */
    @FXML
    public void initialize() {
        textAreaChangeListener();
        textArea.setParagraphGraphicFactory(LineNumberFactory.get(textArea));
    }

    /**
     * sends TEXT_CHANGED event to the mediator
     * updates the redo/undo stack
     *
     * @see Mediator
     * @see EditorTextHistory
     */
    private void textAreaChangeListener() {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            mediator.getEventBuilder().withEvent(Events.TEXT_CHANGED).build();
        });
    }

    /**
     * sets the number of the textspace (used when dealing with multiple tabs)
     */
    public void setNumber(int n) {
        textSpaceNumber = n;
    }

    /**
     * removes the last word from the text
     * sends TEXT_CHANGED event to the mediator
     *
     * @see Mediator
     * @see EditorTextHistory
     */
    public void undo(EditorTextHistory editorTextHistory) {
        boolean undoIsNotEmpty = editorTextHistory.undo();
        if(undoIsNotEmpty) {
            textArea.replaceText(editorTextHistory.getText());
        }
        //mediator.notify(Events.TEXT_CHANGED);
        mediator.getEventBuilder().withEvent(Events.TEXT_CHANGED).build();
    }

    /**
     * adds the last word from redo stack to the text
     * sends TEXT_CHANGED event to the mediator
     *
     * @see Mediator
     * @see EditorTextHistory
     */
    public void redo(EditorTextHistory editorTextHistory) {
        boolean redoIsNotEmpty = editorTextHistory.redo();
        if(redoIsNotEmpty){
            textArea.replaceText(editorTextHistory.getText());
        }
        //mediator.notify(Events.TEXT_CHANGED);
        mediator.getEventBuilder().withEvent(Events.TEXT_CHANGED).build();

    }

    /**
     * @return the text of textArea
     */
    public String getText() {
        return textArea.getText();
    }

    /**
     * @param text the text to be set
     *             sets the textArea text
     */
    public void setText(String text) {
        textArea.replaceText(text);
    }


    /**
     * @return the current path of the file
     */
    public Path getCurrentPath() {
        return currentPath;
    }

    /**
     * @param path the path of the current file
     *             sets currentPath to path
     */
    public void setCurrentPath(Path path) {
        currentPath = path;
    }

}
