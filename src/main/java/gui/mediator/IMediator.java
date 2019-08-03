package gui.mediator;

import gui.components.MainMenuBar;
import gui.components.TextSpace;

public interface IMediator {
    void setMenuBar(MainMenuBar mainMenuBar);
    void setTextSpace(TextSpace textSpace);
}
