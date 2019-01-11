package gui;

import EditLogic.Connector;
import gui.LineCounter.LineCounterController;
import gui.Mediator.Mediator;
import gui.MenuBar.MenuBarController;
import gui.TextArea.TextAreaController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class MainController {
    private Connector connector = new Connector();
    private Mediator mediator = Mediator.getInstance();

    @FXML
    LineCounterController lineCounterController;

    @FXML
    TextAreaController textAreaController;

    @FXML
    MenuBarController menuBarController;

    @FXML
    private VBox vbox;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private HBox hbox;

    @FXML
    public void initialize(){
        //textAreaListener();
        mediator.setTextAreaController(textAreaController);
        mediator.setLineCounterController(lineCounterController);
        mediator.setMenuBarController(menuBarController);
        mediator.setConnector(connector);
    }

    private void textAreaListener() {

        //KeyCodeCombination ctrlV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

        //update the getTextArea method whenever the text is changed
        textAreaController.getTextArea().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lineCounterController.setText(textAreaController.getTextArea().getText());
            }
        });

    }


}
