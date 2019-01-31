package gui;

import gui.components.MainMenuBar;
import gui.components.TextSpace;
import gui.mediator.Mediator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.event.ActionEvent;


public class MainController {
    //TODO: make an array of tabs and a private number counter for tabs

    @FXML private TabPane tabPane;
    @FXML private Tab tab1;
    @FXML private TextSpace textSpace;
    @FXML private MainMenuBar mainMenuBar;
    private Mediator mediator = Mediator.getInstance();

    @FXML public void initialize(){
        mediator.setMainController(this);
    }

    @FXML private void tabOnClose(ActionEvent event){

    }

    public void createNewTab(){
        Tab tab2 = new Tab("untitled tab2");
        TextSpace textSpace2 = new TextSpace();
        textSpace2.setNumber(2);
        tab2.setContent(textSpace2);
        tabPane.getTabs().add(tab2);
    }
}
