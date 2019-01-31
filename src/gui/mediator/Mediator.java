package gui.mediator;

import gui.MainController;
import gui.components.MainMenuBar;
import gui.components.TextSpace;

public class Mediator implements IMediator{
    private MainController mainController;
    private MainMenuBar mainMenuBar;
    private TextSpace textSpace;

    @Override
    public void setMenuBar(MainMenuBar mainMenuBar) {
        this.mainMenuBar = mainMenuBar;
    }

    @Override
    public void setTextSpace(TextSpace textSpace) {

    }

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void sendEvent(Events event){
        switch (event){
            case NEW_TAB_CLICK:
                mainController.createNewTab();
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

