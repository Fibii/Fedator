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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lib.EditorUtils;


public class FindReplaceToolBar extends VBox {

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
    private Text findReplaceHighlightedCount;


    @FXML
    private CheckBox replaceAllCheckbox;

    @FXML
    private CheckBox caseSensetiveCheckBox;

    @FXML
    private HBox findHbox;

    @FXML
    private HBox replaceHbox;

    @FXML
    private Button hideFindReplaceToolBarButton;

    @FXML
    private ToolBar findToolBar;

    @FXML
    private ToolBar replaceToolBar;


    private Mediator mediator = Mediator.getInstance();

    private int currentSelectedMatch = 0;
    private int matchedCount;


    public FindReplaceToolBar() {
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

        mediator.setFindReplaceToolBar(this);

        findTextField.textProperty().addListener((observable, oldValue, newValue) -> findReplaceTextFieldChangeListener()
        );

        caseSensetiveCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> findReplaceTextFieldChangeListener()
        );
    }

    @FXML
    public void replaceButtonPressed(ActionEvent event) {
        if (replaceAllCheckbox.isSelected()) {
            mediator.getEventBuilder().withText(replaceTextField.getText()).withEvent(Events.REPLACE_ALL).build();
        } else {
            mediator.getEventBuilder().withText(replaceTextField.getText()).withEvent(Events.REPLACE_CURRENT).build();
        }

    }

    @FXML
    public void hideFindReplaceToolBarButtonPressed(ActionEvent event) {
        mediator.getEventBuilder().withEvent(Events.HIDE_REPLACE).build();
    }

    @FXML
    public void nextFindButtonPressed() {

        if (currentSelectedMatch + 1 <= matchedCount) {
            currentSelectedMatch++;
        }

        if (matchedCount > 0) {
            findReplaceHighlightedCount.setText(currentSelectedMatch + " of ");
        }

        mediator.getEventBuilder().withEvent(Events.FIND_NEXT).build();

    }

    @FXML
    public void previousFindButtonPressed() {

        if (currentSelectedMatch - 1 > 0) {
            currentSelectedMatch--;
        }

        if (matchedCount > 0) {
            findReplaceHighlightedCount.setText(currentSelectedMatch + " of ");
        }
        mediator.getEventBuilder().withEvent(Events.FIND_PREVIOUS).build();

    }

    private void findReplaceTextFieldChangeListener() {

        currentSelectedMatch = 0;
        findReplaceHighlightedCount.setText("");
        String text = mediator.getText();
        String substring = findTextField.getText();

        System.out.println("selected: " + caseSensetiveCheckBox.isSelected());
        System.out.println("text: " + text);
        System.out.println("subs: " + substring);
        matchedCount = EditorUtils.getSubstringMatchedCount(substring, text, caseSensetiveCheckBox.isSelected());
        findReplaceWordCount.setText(matchedCount + "\nmatches");

        if (matchedCount > 0) {
            currentSelectedMatch++;
            findReplaceHighlightedCount.setText(currentSelectedMatch + " of ");
        }

        mediator.getEventBuilder().withEvent(Events.FIND_SELECT).withText(substring).build();
    }

    private void setReplaceToolbarVisibility(boolean visibility) {
        replaceToolBar.setVisible(visibility);
        replaceToolBar.setManaged(visibility);
    }


    /**
     * Shows replaceToolBar
     */
    public void showFindReplace() {
        setReplaceToolbarVisibility(true);
        this.setVisible(true);
        this.setManaged(true);
    }

    /**
     * Hides FindAndReplaceToolbar by hiding Vbox
     */
    public void hideFindReplace() {
        this.setVisible(false);
        this.setManaged(false);
    }

    /**
     * Shows findToolBar
     */
    public void showFindToolbar() {
        setReplaceToolbarVisibility(false);
        this.setVisible(true);
        this.setManaged(true);
    }


    public boolean isMatchCase() {
        return caseSensetiveCheckBox.isSelected();
    }

}
