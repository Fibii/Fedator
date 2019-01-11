package gui.Mediator;

import EditLogic.Connector;
import gui.Controller;
import javafx.scene.control.TextArea;

public interface IMediator {
    void setTextAreaController(Controller controller);
    void setLineCounterController(Controller controller);
    void setMenuBarController(Controller controller);
    void setConnector(Connector connector);

    /*
    void setTextAreaFromTextAreaController(Controller controller);
    TextArea getTextAreaFromLineCounterController(Controller controller);
    */
}
