package gui.Mediator;

import EditLogic.Connector;
import gui.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;

public class Mediator implements IMediator {
    Controller textAreaController;
    Controller lineCounterController;
    Controller menuBarController;
    Connector connector;

    private Mediator() {
    }

    @Override
    public void setController1(Controller controller) {
        textAreaController = controller;
        setControllerVariablesValues(controller);
    }

    @Override
    public void setController2(Controller controller) {
        lineCounterController = controller;
        setControllerVariablesValues(controller);
    }

    @Override
    public void setController3(Controller controller) {
        menuBarController = controller;
        setControllerVariablesValues(controller);
    }

    @Override
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public static Mediator getInstance() {
        return MediatorInstace.INSTANCE;
    }

    private static final class MediatorInstace {
        private static Mediator INSTANCE = new Mediator();
    }

    /**
     * helper method to set the values to a Controllre object
     */
    private void setControllerVariablesValues(Controller controller) {
        if (textAreaController != null && controller != null) {
            controller.setText(textAreaController.getTextArea().getText());
        }
        if (connector != null && controller != null) {
            controller.setConnector(this.connector);
        }

        if (menuBarController != null && controller != null) {
            controller.setUndoRedo(menuBarController.getUndoRedo() || textAreaController.getUndoRedo()); //maybe even the pane
        }
    }

    public boolean changed(){
        return textAreaController.changed();
    }

}

