package gui.components;

import gui.mediator.Events;
import gui.mediator.IMediator;
import gui.mediator.Mediator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;
import lib.EditorUtils;
import org.fxmisc.richtext.*;
import smallUndoEngine.EditorTextHistory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class TextSpace extends HBox {
    private int textSpaceNumber = 0;
    private IMediator mediator = Mediator.getInstance();
    private Path currentPath;
    private Selection<Collection<String>, String, Collection<String>> extraSelection;
    private List<Integer> startIndices;
    private int startIndicesTracker = 0;

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

        // add highlighter
        extraSelection = new SelectionImpl<>("another selection", textArea,
                path -> {
                    // make rendered selection path look like a yellow highlighter
                    path.setStrokeWidth(0);
                    path.setFill(Color.YELLOW);
                }
        );
        if (!textArea.addSelection(extraSelection)) {
            throw new IllegalStateException("selection was not added to area");
        }
    }

    /**
     * sends TEXT_CHANGED event to the mediator
     * updates the redo/undo stack
     *
     * @see Mediator
     * @see EditorTextHistory
     */
    private void textAreaChangeListener() {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> mediator.getEventBuilder().withEvent(Events.TEXT_CHANGED).build());
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
        editorTextHistory.undo();
        textArea.replaceText(editorTextHistory.getText());
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
        editorTextHistory.redo();
        textArea.replaceText(editorTextHistory.getText());
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

    /**
     * @return the selected text in codeArea
     */
    public String getSelectedText() {
        return textArea.getSelectedText();
    }

    /**
     * replaces the selected text with empty string
     */
    public void removeSelectedText() {
        textArea.replaceSelection("");
    }


    private void clearHighlighting() {
        extraSelection.selectRange(0, 0);
    }

    /**
     * highlights str
     */
    public void selectText(String str) {

        System.out.println("called with; " + str + " tracker: " + startIndicesTracker);
        String text = getText();

        startIndices = EditorUtils.getIndexStartsOfSubstring(text, str, mediator.isMatchCase());

        if (str == null || str.isEmpty() || startIndices.size() == 0) {
            extraSelection.selectRange(0, 0);
            return;
        }

        extraSelection.selectRange(startIndices.get(startIndicesTracker), startIndices.get(startIndicesTracker) + str.length());

    }

    public void increaseIndicesTracker() {

        if (startIndices == null) {
            return;
        }

        if (startIndicesTracker < startIndices.size() - 1) {
            startIndicesTracker++;
        }
    }

    public void decreaseIndicesTracker() {
        if (startIndicesTracker > 0) {
            startIndicesTracker--;
        }
    }

    public void resetIndicesTracker() {
        startIndicesTracker = 0;
    }

    public void replaceCurrent(String oldString, String newStr) {

        // called with textfield empty
        if (oldString == null || newStr == null) {
            return;
        }

        try {
            setText(EditorUtils.replaceSpecificString(getText(), oldString, newStr, startIndicesTracker, mediator.isMatchCase()));
            clearHighlighting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceAll(String oldString, String newStr) {

        // called with textfield empty
        if (oldString == null || newStr == null) {
            return;
        }

        String newText = getText().replaceAll(oldString, newStr);
        setText(newText);
        clearHighlighting();
    }

}
