package gui;

import gui.components.MainMenuBar;
import gui.components.TextSpace;
import gui.mediator.Events;
import gui.mediator.IMediator;
import gui.mediator.Mediator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import lib.EditorUtils;
import smallUndoEngine.EditorTextHistory;

import java.util.ArrayList;
import java.util.Optional;

public class MainController {

    @FXML
    private TabPane tabPane;

    @FXML
    private TextSpace textSpace;
    @FXML
    private MainMenuBar mainMenuBar;

    private EditorTextHistory editorTextHistory = new EditorTextHistory();

    private ArrayList<TabSpace> tabSpaces = new ArrayList<>();
    private int textSpacesCount = 0;
    private IMediator mediator = Mediator.getInstance();

    @FXML
    public void initialize() {
        createNewTab();
        mediator.setMainController(this);
        mediator.setTabSpaces(tabSpaces);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    private void tabPaneListener() {
        tabPane.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            if(tabPane.getTabs().size() == 0){
                return;
            }
            System.out.println("sending TAB_CHANGED, #of tabs" + tabPane.getTabs().size());
            mediator.getEventBuilder().withEvent(Events.TAB_CHANGED).build();
        });
    }

    private void updateCurrentStageTitle(int currentTab) {
        Stage stage = (Stage) tabPane.getParent().getScene().getWindow();
        if (mediator.isFileSaved()) {
            System.out.println("current tab is saved: " + currentTab);
            stage.setTitle(tabSpaces.get(currentTab).getCurrentPath().toString());
        } else {
            stage.setTitle("Untitled " + currentTab);
        }

    }

    /**
     * creates new tab and a new editorTextHistory if the array of tabs is not full
     */
    public void createNewTab() {
        Tab tab = new Tab("untitled tab " + textSpacesCount);
        TextSpace textSpace = new TextSpace();
        EditorTextHistory editorTextHistory = new EditorTextHistory();
        textSpace.setNumber(textSpacesCount);
        System.out.println("textspace " + textSpacesCount + " is created");
        tab.setContent(textSpace);
        TabSpace tabSpace = addTabSpace(textSpace, editorTextHistory, false);
        tab.setOnCloseRequest(event -> {
            Alert alert = EditorUtils.createConfirmationAlert("Are you sure you want to close this tab?", "yes", "");
            boolean close = true;
            if (tabSpace.isTextChanged()) {
                Optional<ButtonType> btnClicked = alert.showAndWait();
                if (!btnClicked.get().getText().equals("yes")) {
                    close = false;
                    event.consume();
                }
            }
            if (close) {
                System.out.println(getCurrentTabIndex());
                tabSpaces.remove(getCurrentTabIndex());
                textSpacesCount--;
            }
        });

        tabPane.getTabs().add(tab);
        textSpacesCount++;

        tabPaneListener();
    }

    /**
     * adds a new tabspace the the list of tabspaces
     *
     * @param textSpace the current TextSpace of TabSpace
     * @param editorTextHistory the current EditorTextHistory of TabSpace
     * @param isSaved   specifies if the file is saved in the system
     * @return: the created tabSpace
     */
    private TabSpace addTabSpace(TextSpace textSpace, EditorTextHistory editorTextHistory, boolean isSaved) {
        TabSpace current = new TabSpace(textSpace, editorTextHistory);
        current.setIsSaved(isSaved);
        tabSpaces.add(current);
        return current;
    }

    /**
     * @return the current selected tab
     */
    public Tab getCurrentTab() {
        return tabPane.getSelectionModel().getSelectedItem();
    }

    /**
     * @return tabPane
     */
    public TabPane getTabPane(){
        return tabPane;
    }


    public void updateIsSaved(boolean isSaved) {
        tabSpaces.get(getCurrentTabIndex()).setIsSaved(true);
    }

    /**
     * @return the index of the selected tab
     */
    public int getCurrentTabIndex() {
        return tabPane.getSelectionModel().getSelectedIndex();
    }


}
