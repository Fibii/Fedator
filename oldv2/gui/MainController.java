package oldv2.gui;

import oldv2.EditLogic.Connector;
import oldv2.gui.LineCounter.LineCounterController;
import oldv2.gui.Mediator.Mediator;
import oldv2.gui.MenuBar.MenuBarController;
import oldv2.gui.TabPane.TabPaneController;
import oldv2.gui.TabPane.TextAreaController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    MenuBarController menubarController;

    @FXML
    TabPaneController tabPaneController;

    @FXML
    private VBox vbox;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private HBox hbox;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tab1;

    @FXML
    public void initialize(){
        //textAreaListener();
        mediator.setTextAreaController(textAreaController);
        mediator.setLineCounterController(lineCounterController);
        mediator.setMenuBarController(menubarController);
        mediator.setTabPaneController(tabPaneController);
        mediator.setMainController(this);
        mediator.setConnector(connector);
    }

    @FXML
    public void tab1OnClose(ActionEvent event){

    }

    @FXML
    public void tab1OnRequest(ActionEvent event){

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

    public void createNewTab() throws Exception{
        System.out.println("create new tab from main controller was called");
        //TODO: https://stackoverflow.com/questions/37439213/load-new-tab-dynamically-with-own-fxmls
        //TODO: migh go for new dependent fxml tab: tab --> hbox --> linecouter & textarea.
        Tab tab2 = new Tab();
        /*
        HBox hbox2 = new HBox();
        hbox2.setId("hbox2");
        TextArea textArea2 = new TextArea();
        textArea2.setId("textArea2");
        TextArea lineCounter = new TextArea();
        lineCounter.setId("lineCounter2");
        hbox2.getChildren().addAll(lineCounter,textArea2);
        */
        Parent root = FXMLLoader.load(this.getClass().getResource("/old/gui/res/gui.fxml"));
        tab2.setContent(root);
        tabPane.getTabs().add(tab2);

    }


}
