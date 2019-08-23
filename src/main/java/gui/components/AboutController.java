package gui.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;



public class AboutController {

    @FXML
    private Hyperlink githubLink;

    @FXML
    private Button okButton;

    @FXML
    private Text aboutText;

    @FXML
    void okButtonClicked(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void githubLinkOnAction(ActionEvent event){
        // works only on linux, requires python..
        try {
            Runtime.getRuntime().exec("python -mwebbrowser https://github.com/Fibii");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}