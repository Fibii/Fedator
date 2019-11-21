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
    void githubLinkOnAction(ActionEvent event) {

        String githubURL = "https://github.com/Fibii";
        try {
            if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + githubURL);
            }
            Runtime.getRuntime().exec("xdg-open " + githubURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}