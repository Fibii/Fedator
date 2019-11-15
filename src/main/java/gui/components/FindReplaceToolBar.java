package gui.components;

import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class FindReplaceToolBar extends ToolBar {

    @FXML
    private TextField findReplaceTextField;

    @FXML
    private Button findReplaceButton;

    @FXML
    private Text findReplaceWordCount;

    @FXML
    private CheckBox findReplaceCheckBox;

    @FXML
    private HBox hbox;

    @FXML
    private Button hideFindReplaceToolBarButton;

    private Mediator mediator = Mediator.getInstance();

    public FindReplaceToolBar(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/findAndReplace.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initalize(){

    }

    @FXML
    public void replaceButtonPressed(ActionEvent event){

    }

    @FXML
    public void hideFindReplaceToolBarButtonPressed(ActionEvent event){
        mediator.getEventBuilder().withEvent(Events.FIND_REPLACE).build();
    }
}
