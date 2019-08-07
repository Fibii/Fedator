package gui.mediator;

import gui.TabSpace;
import gui.components.MainMenuBar;
import gui.components.TextSpace;

import java.util.List;

public interface IMediator {
    void setMenuBar(MainMenuBar mainMenuBar);
    void setTabSpaces(List<TabSpace> tabSpaces);
}
