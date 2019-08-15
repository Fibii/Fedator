package gui;

import gui.components.MainMenuBar;
import gui.components.TextSpace;
import gui.mediator.Mediator;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import smallUndoEngine.Connector;

import java.util.ArrayList;

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

    private ArrayList<TabSpace> tabSpaces = new ArrayList<>();
    private int textSpacesCount = 0;

    private Mediator mediator = Mediator.getInstance();

    @FXML
    public void initialize() {
        mediator.setMainController(this);
        mediator.setTabSpaces(tabSpaces);
        TabSpace current = new TabSpace(textSpace, connector);
        tabSpaces.add(current);
        tabPaneListener();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    private void tabPaneListener() {
        tabPane.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            //set the textSpace to the current one on the selected tab
            int currentTab = tabPane.getSelectionModel().getSelectedIndex();
            System.out.println("current tab is " + currentTab + "ts count is " + textSpacesCount);
            updateCurrentStageTitle(currentTab);
            System.out.println(tabPane.getSelectionModel().getSelectedItem().getText());
        });
    }

    //todo: add * to a file that is opened and edited, but not saved
    private void updateCurrentStageTitle(int currentTab) {
        Stage stage = (Stage) tabPane.getParent().getScene().getWindow();
        if (tabSpaces.get(currentTab).isSaved()) {
            System.out.println("current tab is saved: " + currentTab);
            stage.setTitle(tabSpaces.get(currentTab).getCurrentPath().toString());
        } else {
            stage.setTitle("Untitled " + currentTab);
        }

    }

    /**
     * creates new tab and a new connector if the array of tabs is not full
     */
    public void createNewTab() {
        textSpacesCount++;
        Tab tab = new Tab("untitled tab " + textSpacesCount);
        TextSpace textSpace = new TextSpace();
        Connector connector = new Connector();
        textSpace.setNumber(textSpacesCount);
        System.out.println("textspace " + textSpacesCount + " is created");
        tab.setContent(textSpace);
        addTabSpace(textSpace, connector, false);
        tabPane.getTabs().add(tab);
    }

    /**
     * adds a new tabspace the the list of tabspaces
     * @param textSpace the current TextSpace of TabSpace
     * @param connector the current Connector of TabSpace
     * @param isSaved specifies if the file is saved in the system
     * */
    private void addTabSpace(TextSpace textSpace, Connector connector, boolean isSaved){
        TabSpace current = new TabSpace(textSpace, connector);
        current.setIsSaved(isSaved);
        tabSpaces.add(current);
    }

    //todo: add a listener to tab#onClose so it wont close the tab when the text is edited
    /* and also remove the closed tab from the tablist... */

    /**
     * @return the current selected tab
     */
    public Tab getCurrentTab() {
        return tabPane.getSelectionModel().getSelectedItem();
    }


    public void updateIsSaved(boolean isSaved){
        tabSpaces.get(textSpacesCount).setIsSaved(true);
    }

    /**
     * @return the index of the selected tab
     * */
    public int getCurrentTabIndex(){
        return tabPane.getSelectionModel().getSelectedIndex();
    }
}
