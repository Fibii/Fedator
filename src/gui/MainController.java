package gui;

import gui.components.MainMenuBar;
import gui.components.TextSpace;
import gui.mediator.Mediator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lib.EditorUtils;
import smallUndoEngine.Connector;

import java.nio.file.Path;
import java.util.Optional;


public class MainController {

    @FXML private TabPane tabPane;
    @FXML private Tab tab1;
    @FXML private TextSpace textSpace;
    @FXML private MainMenuBar mainMenuBar;
    private Connector connector = new Connector();

    private TextSpace textspaces[] = new TextSpace[20];
    private Connector connectors[] = new Connector[20];
    int textSpacesCount = 0;

    private Mediator mediator = Mediator.getInstance();

    @FXML public void initialize(){
        mediator.setMainController(this);
        mediator.setConnector(connector);
        textspaces[0] = textSpace;
        connectors[0] = connector;
        textSpacesCount++;
        tabPaneListener();
    }

    @FXML private void tabOnClose(ActionEvent event){

    }

    private void tabPaneListener(){
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                //set the textSpace to the current one on the selected tab
                int currentTab = tabPane.getSelectionModel().getSelectedIndex();
                mediator.setTextSpace(textspaces[currentTab]);
                mediator.setConnector(connectors[currentTab]);
                updateCurrentStageTitle();
                System.out.println(tabPane.getSelectionModel().getSelectedItem().getText());
            }
        });
    }

    private void updateCurrentStageTitle(){
        Stage stage = (Stage) tabPane.getParent().getScene().getWindow();
        if(getCurrentTextSpace().getText().isEmpty()){
            stage.setTitle("Untitled");
        } else {
            stage.setTitle(getCurrentTextSpace().getCurrentPath().toString());
        }

    }

    public void createNewTab(){
        if(textSpacesCount >= textspaces.length){
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

    public Tab getCurrentTab(){
        return tabPane.getSelectionModel().getSelectedItem();
    }

    public TextSpace getCurrentTextSpace(){
        return textspaces[tabPane.getSelectionModel().getSelectedIndex()];
    }

}
