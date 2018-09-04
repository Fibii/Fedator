package gui.FileSaver;

import gui.MainController;
import gui.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class FileSaverController {

    private FileChooser fileChooser = new FileChooser();
    private MainController mainController = new MainController();

    @FXML
    private VBox vbox;

    @FXML
    private Button closeButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField fileName;

    @FXML
    void saveButtonClick(ActionEvent event) {
        File file = new File(fileName.getText());
        Util.saveFile(mainController.getTextArea().getParagraphs(),file);

    }

    @FXML
    void closeButtonClick(ActionEvent event) {
        Util.closeStage(closeButton);
    }

    public void initialize(){
       // vbox.getChildren().addAll(fileChooser);
    }


}
