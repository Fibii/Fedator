package gui;

import gui.components.TextSpace;
import smallUndoEngine.Connector;

/** a class that wraps TextSpace and Connector objects together */
public class TabSpace {

    private TextSpace textSpace;
    private Connector connector;
    private boolean isSaved = false;

    public TabSpace(TextSpace textSpace, Connector connector) {
        this.textSpace = textSpace;
        this.connector = connector;
    }

    public TextSpace getTextSpace() {
        return textSpace;
    }

    public Connector getConnector() {
        return connector;
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
}