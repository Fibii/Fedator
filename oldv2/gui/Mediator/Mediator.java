package oldv2.gui.Mediator;

import oldv2.EditLogic.Connector;
import oldv2.gui.Controller;
import oldv2.gui.MainController;

public class Mediator implements IMediator {
    Controller textAreaController;
    Controller lineCounterController;
    Controller menuBarController;
    Controller tabPaneController;
    Connector connector;
    MainController mainController;
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
    public void setTabPaneController(Controller controller){
        tabPaneController = controller;
    }

    @Override
    public void setMainController(MainController controller){
        this.mainController = controller;
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
            connectorIsSetup = true;
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
            case TABCREATED:
                System.out.println("new tab is created event");
                try {
                    mainController.createNewTab();
                } catch (Exception e){}

                break;

        }
    }

    public void setUndoRedo(boolean bool){
        textAreaController.setUndoRedo(bool);
        lineCounterController.setUndoRedo(bool);
        //menuBarController.setUndoRedo(bool); //TODO: find a workaround this npe
    }

    public void setConnectors(){
//        lineCounterController.setConnector(connector);
        menuBarController.setConnector(connector); //TODO: find a workaround this npe
        textAreaController.setConnector(connector);
    }

    public void setTheCaretToTheLastChar(){
        textAreaController.getTextArea().positionCaret(textAreaController.getTextArea().getText().length()-1);
    }

}

