package oldv2.gui.Mediator;

import oldv2.EditLogic.Connector;
import oldv2.gui.Controller;
import oldv2.gui.MainController;

public interface IMediator {
    void setTextAreaController(Controller controller);
    void setLineCounterController(Controller controller);
    void setMenuBarController(Controller controller);
    void setTabPaneController(Controller controller);
    void setMainController(MainController controller);
    void setConnector(Connector connector);

    /*
    void setTextAreaFromTextAreaController(Controller controller);
    TextArea getTextAreaFromLineCounterController(Controller controller);
    */
}
