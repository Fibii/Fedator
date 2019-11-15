package lib;

import gui.components.MainMenuBar;
import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditorUtils {

    public static boolean writeToFile(String text, Path path) {
        if (path == null || text == null) {
            return false;
        }
        try {
            Files.write(path.toAbsolutePath(), text.getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void onCloseExitConfirmation() {
        if (Mediator.getInstance().shouldExit()) {
            Alert alert = createConfirmationAlert("Do you want to save your changes before quitting?", "Yes", "No");
            Optional<ButtonType> btnClicked = alert.showAndWait();
            if (btnClicked.get().getText().equals("Yes")) {
                if (Mediator.getInstance().isFileSaved()) { // auto save the changes without opening the save window
                    Mediator.getInstance().getEventBuilder().withEvent(Events.EXIT_EVENT).build();
                } else {
                    Mediator.getInstance().getEventBuilder().withEvent(Events.SAVE_REQUEST).build();
                }
            } else if (btnClicked.get().getText().equals("No")) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    /**
     * creates an alert confirmation window with 2/3 buttons, 2 custom buttons, 1 cancel button
     *
     * @param alertText:   the content of the alert
     * @param buttonText1: the text value of the first button
     * @param buttonText2: the text value of the second button
     *                     <p>
     *                     usage:
     *                     Alert alert = EditorUtils.createConfirmationAlert("Do you want to save your changes before quitting?", "Yes", "No");
     *                     Optional<ButtonType> btnClicked = alert.showAndWait();
     *                     if(btnClicked.get().getText().equals("type"){
     *                     // do something
     *                     } ...
     *                     <p>
     *                     you can create one custom button only, just pass "" as an argument for buttonText2
     *                     <p>
     *
     */
    public static Alert createConfirmationAlert(String alertText, String buttonText1, String buttonText2) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure you want to exit?");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText(alertText);
        ButtonType btn1 = new ButtonType(buttonText1);
        ButtonType btn2 = new ButtonType(buttonText2);
        ButtonType cnlBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        if (buttonText2.isEmpty()) {
            alert.getButtonTypes().setAll(btn1, cnlBtn);
            return alert;
        }
        alert.getButtonTypes().setAll(btn1, btn2, cnlBtn);

        return alert;
    }

    /**
     * updates the stage title whenever the selected tab changes
     *
     * @param tabPane:    the TabPane of the main controller
     * @param filePath: the path of the opened file in the selected tab
     * @param tabNumber:  the index of the selected tab
     *                    <p>
     *                    Getting the tab using the index of the selected tab is used because tabPane...getSelectedTab somehow returns the previous selected tab,
     */
    public static void setCurrentEditorTitle(TabPane tabPane, Path filePath, int tabNumber) {
        Stage stage = (Stage) tabPane.getParent().getScene().getWindow();

        if (filePath == null) {
            stage.setTitle("Untitled tab " + tabNumber);
            return;
        }

        stage.setTitle(filePath.toString());
        tabPane.getTabs().get(tabNumber).setText(filePath.getFileName().toString());
    }

    /**
     * updates the stage title
     * used whenever the user opens a new file, or save a new file
     *
     * @param filePath: the path of the saved file
     * @param tabPane:  the current tabPane, used to get the stage
     */
    public static void setStageTitle(TabPane tabPane, Path filePath) {
        Stage stage = (Stage) tabPane.getParent().getScene().getWindow();
        stage.setTitle(filePath.toString());
    }

    /**
     * updates the stage title
     * used whenever the user opens a new file, or save a new file
     *
     * @param filePath: the path of the saved file
     * @param tabPane:  the current tabPane, used to get the stage, and the selected tab
     * @param tabNumber: the index of the selected tab
     */
    public static void setTabTitle(TabPane tabPane, Path filePath, int tabNumber) {
        tabPane.getTabs().get(tabNumber).setText(filePath.getFileName().toString());
    }

    public static List<String> readFromFile(File file) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * opens a fileChooser save windows so the user can save a new file as .txt
     * does nothing if the file in null
     * creates a new text file with the current text in the specified path
     * sends SAVE_MENU event to the mediator
     *
     * @see Mediator
     * @see EditorUtils#writeToFile(String, Path)
     */
    public static Path showSaveWindow(Window window) {
        Mediator mediator = Mediator.getInstance();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(window);
        if (file == null) { //check if the user clicked the cancel button
            return null;
        }
        file = new File(file.getPath() + ".txt"); //might be only in linux that the file is not saved as title.txt
        EditorUtils.writeToFile(mediator.getText(), file.toPath());
        Path filePath = file.toPath();
        return filePath;
    }

    /**
     * shows the version information of the app
     * */
    public static void showAboutWindow(MainMenuBar mainMenuBar){
        FXMLLoader fxmlLoader = new FXMLLoader(mainMenuBar.getClass().getResource("/about.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            Scene scene = mainMenuBar.getScene();
            scene.getStylesheets().add(mainMenuBar.getClass().getResource("/style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("About");
            stage.setScene(new Scene(root, 290, 130));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
