package gui;

import gui.components.TextSpace;
import gui.mediator.Events;
import gui.mediator.Mediator;
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