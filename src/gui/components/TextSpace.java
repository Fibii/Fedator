package gui.components;

import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.nio.file.Path;

public class TextSpace extends HBox {
    private int textSpaceNumber = 0;
    private Mediator mediator = Mediator.getInstance();
    private Path currentPath;
    private int lineNumber = 3;
    private StringBuilder lineCountBuilder = new StringBuilder("1\n2\n3\n");
    @FXML
    private TextArea lineCounter;
    @FXML
    private TextArea textArea;

    /**
     * loads textspace.fxml and specifies the controller and the root
     */
    public TextSpace() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/res/fxml/textspace.fxml"));
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
     * starts {@link TextSpace#textAreaKeyboardListener()}
     * sets the lineCounter text to "1 2 3" as an initial value
     * sets the current TextSpace in the mediator to the current instance
     * binds the scrolling of textArea and lineCounter
     */
    @FXML
    public void initialize() {
        textAreaChangeListener();
        textAreaKeyboardListener();
        lineCounter.setText(lineCountBuilder.toString());
        mediator.setTextSpace(this);
        //scroll down whenever the number of lines increases
        textArea.scrollTopProperty().bindBidirectional(lineCounter.scrollTopProperty());

    }

    /**
     * sends TEXT_CHANEGED event to the mediator
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
     *
     */
    private void textAreaKeyboardListener() {
        KeyCodeCombination ctrlV = new KeyCodeCombination(KeyCode.V, KeyCodeCombination.CONTROL_DOWN);
        KeyCodeCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN);
        KeyCodeCombination ctrlShiftZ = new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHIFT_DOWN);

        textArea.setOnKeyPressed(event -> {
            //handle pasting using keyboard
            if (ctrlV.match(event)) {
                mediator.sendEvent(Events.TEXT_CHANGED);
                event.consume();
                textArea.paste();
                updateLineCount(false, true, false);
                setTheCaretToTheLastChar();
            }

            if (ctrlZ.match(event)) {
                mediator.sendEvent(Events.UNDO_TEXT);
                event.consume();
            }

            if (ctrlShiftZ.match(event)) {
                mediator.sendEvent(Events.REDO_TEXT);
                event.consume();
            }

            if (event.getCode() == KeyCode.ENTER) {
                updateLineCount(true, false, false);
            }
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
        mediator.getCurrentConnector().undo();
        textArea.setText(mediator.getCurrentConnector().getText());
        setTheCaretToTheLastChar();
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
        mediator.getCurrentConnector().redo();
        textArea.setText(mediator.getCurrentConnector().getText());
        setTheCaretToTheLastChar();
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
        textArea.setText(text);
    }

    /**
     * sets the caret to the last char of the text, since using {@link TextSpace#undo()}
     * or {@link TextSpace#redo()} messes it up
     */
    private void setTheCaretToTheLastChar() {
        textArea.positionCaret(textArea.getText().length());
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

    /**
     * helper method for updateLineCount that adds the number of lines to lineCountBuilder
     */
    private void updateLines(long number) {
        while (lineNumber < number) {
            lineNumber++;
            lineCountBuilder.append(lineNumber + "\n");
        }
        lineCounter.setText(lineCountBuilder.toString());
    }

    /**
     * @param isEnterKey did the user click the enter key ?
     * @param isPaste    did the user paste a text from the clipboards ?
     * @param isRead     is the user opening a file ?
     *                   <p>
     *                   updates the current number of lines by calling {@link TextSpace#updateLines(long)}
     *                   *
     *                   since the native TextArea line count won't be updated when there's no text but there are
     *                   multiple new lines, the update must be done by listening to the keyboard
     *
     *                   if the user pastes something from the clipboard, or opens a file,  the number of lines is counted by finding the occurrence of '\n'
     *
     *
     */
    public void updateLineCount(boolean isEnterKey, boolean isPaste, boolean isRead) {

        if (isEnterKey) {
            updateLines(lineNumber + 1);
        }

        if (isPaste) {
            updateLines(numberOfLines());
        }

        if (isRead) {
            updateLines(mediator.getNumberOfLines());
        }
    }
}
