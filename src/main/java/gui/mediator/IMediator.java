package gui.mediator;

import gui.components.MainController;
import gui.TabSpace;
import gui.components.FindReplaceToolBar;
import gui.components.MainMenuBar;

import java.nio.file.Path;
import java.util.List;

public interface IMediator {

    void setMenuBar(MainMenuBar mainMenuBar);
    void setTabSpaces(List<TabSpace> tabSpaces);
    void setMainController(MainController mainController);
    void setFindReplaceToolBar(FindReplaceToolBar findReplaceToolBar);

    String getText();
    Path getFilePath();
    boolean isFileSaved();
    boolean shouldExit();
    boolean isMatchCase();
    Mediator.EventBuilder getEventBuilder();

    String getMediatorText();
    Path getMediatorFilePath();

}
