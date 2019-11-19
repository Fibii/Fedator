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
import lib.EditorUtils;

// todo: fix toolbar components width to be synced with the stage's width
public class FindReplaceToolBar extends ToolBar {

    @FXML
    private TextField findTextField;

    @FXML
    private TextField replaceTextField;

    @FXML
    private Button findReplaceButton;

    @FXML
    private Button nextFindButton;

    @FXML
    private Button previousFindButton;

    @FXML
    private Text findReplaceWordCount;

    @FXML
    private CheckBox replaceAllCheckbox;

    @FXML
    private CheckBox caseSensetiveCheckBox;

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
    public void initialize() {
        findTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    findReplaceTextFieldChangeListener();
                }
        );
    }

    @FXML
    public void replaceButtonPressed(ActionEvent event){
//        mediator.setText(replaceTextField.getText());
        mediator.getEventBuilder().withText(replaceTextField.getText()).withEvent(Events.REPLACE_CURRENT).build();
    }

    @FXML
    public void hideFindReplaceToolBarButtonPressed(ActionEvent event){
        mediator.getEventBuilder().withEvent(Events.FIND_REPLACE).build();
    }

    @FXML
    public void nextFindButtonPressed(){
        mediator.getEventBuilder().withEvent(Events.FIND_NEXT).build();
    }

    @FXML
    public void previousFindButtonPressed(){
        mediator.getEventBuilder().withEvent(Events.FIND_PREVIOUS).build();
    }

    public void findReplaceTextFieldChangeListener(){
        String text = caseSensetiveCheckBox.isSelected() ? mediator.getText().toLowerCase() : mediator.getText();
        String substring = caseSensetiveCheckBox.isSelected() ? findTextField.getText().toLowerCase() : findTextField.getText().toLowerCase();
        int count = EditorUtils.getSubstringMatchedCount(substring, text);
        findReplaceWordCount.setText(count + "\nmatches");


        mediator.getEventBuilder().withEvent(Events.FIND_SELECT).withText(substring).build();
    }
}
