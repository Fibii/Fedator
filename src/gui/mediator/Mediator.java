package gui.mediator;

import gui.MainController;
import gui.components.MainMenuBar;
import gui.components.TextSpace;
import smallUndoEngine.Connector;

public class Mediator implements IMediator{
    private MainController mainController;
    private MainMenuBar mainMenuBar;
    private TextSpace textSpace;
    private Connector connector;
    private boolean isSaved;

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

    public boolean getIsSaved(){
        return isSaved;
    }

    //TODO: Finish the switch cases and implement their methods
    public void sendEvent(Events event){
        switch (event){
            case NEW_TAB:
                mainController.createNewTab();
                break;
            case UNDO_TEXT:
                textSpace.undo();
                break;
            case OPEN_MENU:
                textSpace.setText(mainMenuBar.getText());
                break;

            case REDO_TEXT:
                textSpace.redo();
                break;

            case SAVE_MENU:
                isSaved = true;
                break;

            case ABOUT_MENU:

                break;

            case CLOSE_MENU:
                System.exit(0);
                break;
            case AUTO_SAVE:
                //TODO: find a good way to share the path of the file
                break;
            case SAVE_FILE:
                mainMenuBar.showSaveWindow();
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

