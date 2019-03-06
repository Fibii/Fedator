package gui.components;

import javafx.concurrent.Task;
import lib.EditorUtils;
import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenuBar extends MenuBar {

    @FXML
    private MenuItem undo;

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem about;

    @FXML
    private MenuItem redo;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem newTab;

    @FXML
    private MenuItem open;

    private Mediator mediator = Mediator.getInstance();
    private FileChooser fileChooser = new FileChooser();
    private String text;
    private Path filePath;
    private int numberOfLines;

    public MainMenuBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/res/fxml/menubar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * @param event javafx event..
     *              sends an event to the mediator when a new tab is created
     * @see Mediator
     **/
    @FXML
    void onNewTabClick(ActionEvent event) {
        mediator.sendEvent(Events.NEW_TAB);
    }

    /**
     * @param event javafx event..
     *              opens a new window and prompt the user to choose a text file
     *              file must not be null
     *              updates filepath to the current path of the file
     */
    @FXML
    void openMenuItemClick(ActionEvent event) {
        fileChooser.setTitle("title");
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(open.getParentPopup().getScene().getWindow());
        if (file != null) {
            readFile(file);
            filePath = file.toPath();
        }
    }

    /**
     * @param event javafx event..
     *              sends AUTO_SAVE event to mediator if the file is saved to save the file without opening the fileChooser save windows
     *              otherwise shows the fileChooser save windows and sends a SAVE_MENU to save the file
     * @see Mediator
     */
    @FXML
    void saveMenuItemClick(ActionEvent event) {
        if (mediator.getFileIsSaved()) {
            mediator.sendEvent(Events.AUTO_SAVE);
        } else {
            showSaveWindow();
            mediator.sendEvent(Events.SAVE_MENU);
        }
    }

    /**
     * @param event javafx event..
     *              sends a CLOSE_MENU event to the mediator
     *              pops the alert confirmation window
     * @see EditorUtils
     * @see Mediator
     */
    @FXML
    void closeMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.CLOSE_MENU);
        EditorUtils.onCloseExitConfirmation();
    }

    /**
     * @param event javafx event..
     *              sends a UNDO_TEXT event to the mediator to undo the current text
     * @see Mediator
     * @see smallUndoEngine.Connector
     */
    @FXML
    void undoMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.UNDO_TEXT);
    }

    /**
     * @param event javafx event..
     *              sends a REDO_TEXT event to the mediator to undo the current text
     * @see Mediator
     * @see smallUndoEngine.Connector
     */
    @FXML
    void redoMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.REDO_TEXT);
    }

    /**
     * @deprecated shows the version information of the app
     */
    @FXML
    void aboutMenuItemClick(ActionEvent event) {
        mediator.sendEvent(Events.ABOUT_MENU);
    }

    /**
     * sets the current menubar in mediator to this instance
     *
     * @see Mediator
     */
    @FXML
    public void initialize() {
        mediator.setMenuBar(this);
    }

    /**
     * @return the text in textArea
     */
    public String getText() {
        return text;
    }

    /**
     * opens a fileChooser save windows so the user can save a new file as .txt
     * does nothing if the file in null
     * creates a new text file with the current text in the specified path
     * sends SAVE_MENU event to the mediator
     *
     * @see Mediator
     * @see EditorUtils
     */
    public void showSaveWindow() {
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(save.getParentPopup().getScene().getWindow());
        if (file == null) { //check if the user clicked the cancel button
            return;
        }
        file = new File(file.getPath() + ".txt"); //might be only in linux that the file is not saved as title.txt
        EditorUtils.writeToFile(mediator.getCurrentText(), file.toPath());
        mediator.sendEvent(Events.SAVE_MENU);
    }

    /**
     * @return the path of the file
     */
    public Path getSavedFilePath() {
        return filePath;
    }

    /**
     * @return the number of lines of the current text
     */
    public int getNumberOfLines() {
        return numberOfLines;
    }

    /**
     * @param file the text file to read from
     *             a task to read the text file in another thread
     *             waits for the thread to finish, then sends OPEN_MENU event to the mediator to update the textArea text
     *             updates numberOfLines
     *             if the file reading fails, it sets the current text to "FAILED"
     * @see Mediator
     * @see EditorUtils
     */
    private void readFile(File file) {
        Task<String> task = new Task<String>() {
            @Override
            protected String call() {
                List<String> list = EditorUtils.readFromFile(file);
                String str = list.stream().collect(Collectors.joining("\n"));
                setCurrentText(str);
                numberOfLines = list.size();
                return str;
            }
        };

        task.setOnSucceeded(t -> mediator.sendEvent(Events.OPEN_MENU));
        task.setOnFailed(e -> setCurrentText("FAILED"));
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     * @param text the text that will be displayed in textArea
     *             used to update textArea's text when reading a file
     **/
    private void setCurrentText(String text) {
        this.text = text;
    }
}
