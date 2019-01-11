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
    boolean connectorIsSetup;

    private Mediator() {

    }

    @Override
    public void setTextAreaController(Controller controller) {
        textAreaController = controller;
    }

    @Override
    public void setLineCounterController(Controller controller) {
        lineCounterController = controller;
    }

    @Override
    public void setMenuBarController(Controller controller) {
        menuBarController = controller;
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

    /** passes the new text from textArea to lineCounter, might not be needed */
    public void changed(boolean changed){
        if(changed){
            lineCounterController.setText(textAreaController.getTextArea().getText());
        }
    }

    public void eventListener(Event event){
        if(!connectorIsSetup){
            setConnectors();
        }

        switch (event){
            case ENTER:
                lineCounterController.eventListener(event);
                break;
            case PASTE:
                lineCounterController.eventListener(event);
                break;
            case TEXTCHANGED:
                //TODO: update the connector
                System.out.println("text changed event, connector should be updated");
                break;
        }
    }

    public void setUndoRedo(boolean bool){
        textAreaController.setUndoRedo(bool);
        lineCounterController.setUndoRedo(bool);
        //menuBarController.setUndoRedo(bool); //TODO: find a workaround this npe
    }

    public void setConnectors(){
        lineCounterController.setConnector(connector);
        //menuBarController.setConnector(connector); //TODO: find a workaround this npe
        textAreaController.setConnector(connector);
    }

    public void setTheCaretToTheLastChar(){
        textAreaController.getTextArea().positionCaret(textAreaController.getTextArea().getText().length()-1);
    }

}

