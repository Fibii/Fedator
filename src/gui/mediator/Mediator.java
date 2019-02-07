package gui.mediator;

import gui.MainController;
import gui.components.MainMenuBar;
import gui.components.TextSpace;
import lib.EditorUtils;
import smallUndoEngine.Connector;

import java.nio.file.Path;

public class Mediator implements IMediator{
    private MainController mainController;
    private MainMenuBar mainMenuBar;
    private TextSpace textSpace;
    private Connector connector;
    private boolean fileIsSaved;
    private boolean textIsChaned;
    Path filePath;

    @Override
    public void setMenuBar(MainMenuBar mainMenuBar) {
        this.mainMenuBar = mainMenuBar;
    }

    @Override
    public void setTextSpace(TextSpace textSpace) {
        this.textSpace = textSpace;
    }

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void setConnector(Connector connector){
        this.connector = connector;
    }

    public Connector getCurrentConnector(){
        return connector;
    }

    public String getCurrentText(){
        return textSpace.getText();
    }

    public boolean getFileIsSaved(){
        return fileIsSaved;
    }

    public boolean getTextIsChanged(){
        return textIsChaned;
    }

    //TODO: Finish the switch cases and implement their methods
    public void sendEvent(Events event){
        switch (event){
            case TEXT_CHANGED:
                textIsChaned = true;
                break;
            case NEW_TAB:
                mainController.createNewTab();
                break;
            case UNDO_TEXT:
                textSpace.undo();
                break;
            case OPEN_MENU:
                textSpace.setText(mainMenuBar.getText());
                fileIsSaved = true;
                textIsChaned = false;
                filePath = mainMenuBar.getSavedFilePath();
                break;

            case REDO_TEXT:
                textSpace.redo();
                break;

            case SAVE_MENU:
                fileIsSaved = true;
                break;

            case ABOUT_MENU:

                break;
            case AUTO_SAVE:
                EditorUtils.writeToFile(getCurrentText(),filePath);
                break;

            case CLOSE_MENU:
                break;
            case SAVE_FILE:
                mainMenuBar.showSaveWindow();
                fileIsSaved = true;
                break;
            case EXIT_EVENT:
                EditorUtils.writeToFile(getCurrentText(),filePath);
                textIsChaned = false;
                System.exit(0);
                break;
        }
    }


    public static Mediator getInstance() {
        return MediatorInstace.INSTANCE;
    }

    private static final class MediatorInstace {
        private static Mediator INSTANCE = new Mediator();
    }

}

