package gui.Mediator;

import EditLogic.Connector;
import gui.Controller;
import javafx.scene.control.TextArea;

public interface IMediator {
    void setController1(Controller controller);
    void setController2(Controller controller);
    void setController3(Controller controller);
    void setConnector(Connector connector);

    /*
    void setTextAreaFromTextAreaController(Controller controller);
    TextArea getTextAreaFromLineCounterController(Controller controller);
    */
}
