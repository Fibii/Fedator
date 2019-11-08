package gui;

import gui.components.TextSpace;
import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.scene.input.Clipboard;
import smallUndoEngine.EditorTextHistory;

import java.nio.file.Path;

/** a class that wraps TextSpace and EditorTextHistory objects together */
public class TabSpace {

    private TextSpace textSpace;
    private EditorTextHistory editorTextHistory;
    private boolean fileSaved;
    private boolean textChanged;

    private Mediator mediator = Mediator.getInstance();

    public TabSpace(TextSpace textSpace, EditorTextHistory editorTextHistory) {
        this.textSpace = textSpace;
        this.editorTextHistory = editorTextHistory;
    }

    /** updates fileSaved
     * @param bool the value isSaved will be set to
     * */
    public void setIsSaved(boolean bool){
        fileSaved = bool;
    }

    /**
     * replaces textArea's text with itself appended with string content in the clipboards to textArea by
     * */
    private void pasteToTextArea(){
        StringBuilder sb = new StringBuilder();
        String clipboardString = Clipboard.getSystemClipboard().getString();
        String finalText = sb.append(getText()).append(" ").append(clipboardString).toString();
        textSpace.setText(finalText);
    }

    /**
     * removes the selected text in textArea
     * */
    private void cutFromTextAreA(){
        textSpace.removeSelectedText();
    }


    public void sendEvent(Events event){
        switch (event) {
            case UNDO_TEXT:
                textSpace.undo(editorTextHistory);
                textChanged = true;
                break;

            case REDO_TEXT:
                textSpace.redo(editorTextHistory);
                textChanged = true;
                break;

            case OPEN_MENU:
                textSpace.setCurrentPath(mediator.getMediatorFilePath());
                textSpace.setText(mediator.getMediatorText());
                fileSaved = true;

                break;

            case SAVE_MENU:
                textSpace.setCurrentPath(mediator.getMediatorFilePath());
                fileSaved = true;
                textChanged = false;
                break;
            case TEXT_CHANGED:
                editorTextHistory.update(textSpace.getText());
                textChanged = true;
                break;

                //todo: undo-redo engine needs to be updated to work with cut/paste events
            case COPY_MENU:
                mediator.setText(textSpace.getSelectedText());
                break;
            case CUT_MENU:
                mediator.setText(textSpace.getSelectedText());
                cutFromTextAreA();
                break;
            case PASTE_MENU:
                pasteToTextArea();
                break;
        }

    }

    public String getText(){
        return textSpace.getText();
    }

    public Path getCurrentPath(){
        return textSpace.getCurrentPath();
    }

    public boolean isFileSaved() {
        return fileSaved;
    }

    public boolean isTextChanged() {
        return textChanged;
    }
}