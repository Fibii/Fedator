package gui;

import gui.components.MainMenuBar;
import gui.components.TextSpace;
import gui.mediator.Mediator;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import smallUndoEngine.Connector;

public class MainController {

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tab1;
    @FXML
    private TextSpace textSpace;
    @FXML
    private MainMenuBar mainMenuBar;

    private Connector connector = new Connector();

    //todo: wrap this in its own class
    private TextSpace textspaces[] = new TextSpace[20];
    private Connector connectors[] = new Connector[20];
    int textSpacesCount = 0;

    private Mediator mediator = Mediator.getInstance();

    @FXML
    public void initialize() {
        mediator.setMainController(this);
        mediator.setConnector(connector);
        textspaces[0] = textSpace;
        connectors[0] = connector;
        textSpacesCount++;
        tabPaneListener();
    }

    private void tabPaneListener() {
        tabPane.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            //set the textSpace to the current one on the selected tab
            int currentTab = tabPane.getSelectionModel().getSelectedIndex();
            mediator.setTextSpace(textspaces[currentTab]);
            mediator.setConnector(connectors[currentTab]);
            updateCurrentStageTitle();
            System.out.println(tabPane.getSelectionModel().getSelectedItem().getText());
        });
    }

    private void updateCurrentStageTitle() {
        Stage stage = (Stage) tabPane.getParent().getScene().getWindow();
        if (getCurrentTextSpace().getText().isEmpty()) {
            stage.setTitle("Untitled");
        } else {
            stage.setTitle(getCurrentTextSpace().getCurrentPath().toString());
        }

    }

    /**
     * creates new tab and a new connector if the array of tabs is not full
     */
    public void createNewTab() {
        if (textSpacesCount >= textspaces.length) {
            System.out.println("the array of tabs is full");
            return;
        }
        Tab tab = new Tab("untitled tab");
        TextSpace textSpace = new TextSpace();
        Connector connector = new Connector();
        textSpace.setNumber(textSpacesCount);
        System.out.println("textspace " + textSpacesCount + " is created");
        tab.setContent(textSpace);
        textspaces[textSpacesCount] = textSpace;
        connectors[textSpacesCount] = connector;
        textSpacesCount++;
        tabPane.getTabs().add(tab);
    }

    //todo: add a way to close tabs

    /**
     * @return the currect selected tab
     */
    public Tab getCurrentTab() {
        return tabPane.getSelectionModel().getSelectedItem();
    }

    /**
     * @return the current textspace
     */
    public TextSpace getCurrentTextSpace() {
        return textspaces[tabPane.getSelectionModel().getSelectedIndex()];
    }

}
