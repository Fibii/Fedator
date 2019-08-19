package gui;

import gui.components.TextSpace;
import gui.mediator.Events;
import gui.mediator.Mediator;
import smallUndoEngine.Connector;

import java.nio.file.Path;

/** a class that wraps TextSpace and Connector objects together */
public class TabSpace {

    private TextSpace textSpace;
    private Connector connector;
    private boolean fileSaved;
    private boolean textChanged;

    private Mediator mediator = Mediator.getInstance();

    public TabSpace(TextSpace textSpace, Connector connector) {
        this.textSpace = textSpace;
        this.connector = connector;
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
                textSpace.undo(connector);
                textChanged = true;
                break;

            case REDO_TEXT:
                textSpace.redo(connector);
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
                connector.update(textSpace.getText());
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