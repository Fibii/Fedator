package gui.components;

import gui.TabSpace;
import gui.mediator.Events;
import gui.mediator.IMediator;
import gui.mediator.Mediator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
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

    @FXML
    private FindReplaceToolBar findReplaceToolBar;

    private EditorTextHistory editorTextHistory = new EditorTextHistory();

    private IMediator mediator = Mediator.getInstance();

    private ArrayList<TabSpace> tabSpaces = new ArrayList<>();
    private int textSpacesCount = 0;

    @FXML
    public void initialize() {
        createNewTab(false);
        mediator.setMainController(this);
        mediator.setTabSpaces(tabSpaces);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    private void tabPaneListener() {
        tabPane.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            if (tabPane.getTabs().size() == 0) {
                return;
            }
            System.out.println("sending TAB_CHANGED, #of tabs" + tabPane.getTabs().size());
            mediator.getEventBuilder().withEvent(Events.TAB_CHANGED).build();
        });
    }


    /**
     * @param isSaved: true if a new tab is created as a result of OPEN_MENU event, false otherwise
     * @see Mediator
     * creates new tab and a new editorTextHistory if the array of tabs is not full
     */
    public void createNewTab(boolean isSaved) {
        Tab tab = new Tab("untitled tab " + textSpacesCount);
        TextSpace textSpace = new TextSpace();

        FindReplaceToolBar findReplaceToolBar = new FindReplaceToolBar();
        findReplaceToolBar.setManaged(false);

        EditorTextHistory editorTextHistory = new EditorTextHistory();
        textSpace.setNumber(textSpacesCount);
        System.out.println("textspace " + textSpacesCount + " is created");

        //VBox used for tab content that hold textspace and toolbar because we can't add many nodes to Tab
        VBox vBox = new VBox();
        vBox.getChildren().addAll(textSpace, findReplaceToolBar);
        tab.setContent(vBox);

        TabSpace tabSpace = addTabSpace(textSpace, editorTextHistory, findReplaceToolBar, isSaved);
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
            }
        });

        tabPane.getTabs().add(tab);
        textSpacesCount++;

        tabPaneListener();
    }

    /**
     * adds a new tabspace the the list of tabspaces
     *
     * @param textSpace         the current TextSpace of TabSpace
     * @param editorTextHistory the current EditorTextHistory of TabSpace
     * @param isSaved           specifies if the file is saved in the system
     * @return the created tabSpace
     */
    private TabSpace addTabSpace(TextSpace textSpace, EditorTextHistory editorTextHistory, FindReplaceToolBar findReplaceToolBar, boolean isSaved) {
        TabSpace current = new TabSpace(textSpace, editorTextHistory, findReplaceToolBar);
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
    public TabPane getTabPane() {
        return tabPane;
    }


    public void updateIsSaved(boolean isSaved) {
        tabSpaces.get(getCurrentTabIndex()).setIsSaved(isSaved);
    }

    /**
     * @return the index of the selected tab
     */
    public int getCurrentTabIndex() {
        return tabPane.getSelectionModel().getSelectedIndex();
    }


}
