package gui.components;
import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import org.fxmisc.richtext.*;

import java.io.IOException;
import java.nio.file.Path;

public class TextSpace extends HBox {
    private int textSpaceNumber = 0;
    private Mediator mediator = Mediator.getInstance();
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
        mediator.setTextSpace(this);
        textArea.setParagraphGraphicFactory(LineNumberFactory.get(textArea));
    }

    /**
     * sends TEXT_CHANGED event to the mediator
     * updates the redo/undo stack
     *
     * @see Mediator
     * @see smallUndoEngine.Connector
     */
    private void textAreaChangeListener() {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            mediator.sendEvent(Events.TEXT_CHANGED);
            mediator.getCurrentConnector().update(textArea.getText());
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
     * @see smallUndoEngine.Connector
     */
    public void undo() {
        boolean undoIsNotEmpty = mediator.getCurrentConnector().undo();
        if(undoIsNotEmpty) {
            textArea.replaceText(mediator.getCurrentConnector().getText());
        }
        mediator.sendEvent(Events.TEXT_CHANGED);
    }

    /**
     * adds the last word from redo stack to the text
     * sends TEXT_CHANGED event to the mediator
     *
     * @see Mediator
     * @see smallUndoEngine.Connector
     */
    public void redo() {
        boolean redoIsNotEmpty = mediator.getCurrentConnector().redo();
        if(redoIsNotEmpty){
            textArea.replaceText(mediator.getCurrentConnector().getText());
        }
        mediator.sendEvent(Events.TEXT_CHANGED);
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
     *             setns currentPath to path
     */
    public void setCurrentPath(Path path) {
        currentPath = path;
    }

    /**
     * @return the current number of lines
     * used to get the number of lines when the user pastes a text from the clipboard
     */
    public long numberOfLines() {
        return textArea.getText().chars().parallel().filter(c -> c == '\n').count();
    }

}
