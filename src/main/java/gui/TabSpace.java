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
    private boolean isSaved = false;
    private Mediator mediator = Mediator.getInstance();

    public TabSpace(TextSpace textSpace, Connector connector) {
        this.textSpace = textSpace;
        this.connector = connector;
    }

    /** updates isSaved
     * @param bool the value isSaved will be set to
     * */
    public void setIsSaved(boolean bool){
        isSaved = bool;
    }

    public boolean isSaved(){
        return isSaved;
    }

    public void sendEvent(Events event){
        switch (event) {
            case UNDO_TEXT:
                textSpace.undo(connector);
                break;

            case REDO_TEXT:
                textSpace.redo(connector);
                break;

            case OPEN_MENU:
                textSpace.setCurrentPath(mediator.getFilePath());
                textSpace.setText(mediator.getText());
                break;

            case SAVE_MENU:
                textSpace.setCurrentPath(mediator.getFilePath());
                break;
        }

    }

    public String getText(){
        return textSpace.getText();
    }

    public Path getCurrentPath(){
        return textSpace.getCurrentPath();
    }
}