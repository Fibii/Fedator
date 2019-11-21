package gui.components;

import gui.mediator.IMediator;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import lib.EditorUtils;
import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import smallUndoEngine.EditorTextHistory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @FXML
    private MenuItem copy;

    @FXML
    private MenuItem cut;

    @FXML
    private MenuItem paste;

    @FXML
    private MenuItem findAndReplace;

    @FXML
    private MenuItem find;

    private IMediator mediator = Mediator.getInstance();
    private FileChooser fileChooser = new FileChooser();
    private String text;
    private Path filePath;

    public MainMenuBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/menubar.fxml"));
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
        mediator.getEventBuilder().withEvent(Events.NEW_TAB).build();
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
        if (mediator.isFileSaved()) {
            mediator.getEventBuilder().withEvent(Events.AUTO_SAVE).build();
        } else {

            filePath = EditorUtils.showSaveWindow(save.getParentPopup().getScene().getWindow());

            if(filePath == null){
                return;
            }

            mediator.getEventBuilder()
                    .withEvent(Events.SAVE_MENU)
                    .fileSaved(true)
                    .textChanged(false)
                    .withFilePath(filePath)
                    .build();
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
        mediator.getEventBuilder().withEvent(Events.CLOSE_MENU).build();
        EditorUtils.onCloseExitConfirmation();
    }

    /**
     * @param event javafx event..
     *              sends a UNDO_TEXT event to the mediator to undo the current text
     * @see Mediator
     * @see EditorTextHistory
     */
    @FXML
    void undoMenuItemClick(ActionEvent event) {
        mediator.getEventBuilder().withEvent(Events.UNDO_TEXT).build();
    }

    /**
     * @param event javafx event..
     *              sends a REDO_TEXT event to the mediator to undo the current text
     * @see Mediator
     * @see EditorTextHistory
     */
    @FXML
    void redoMenuItemClick(ActionEvent event) {
        mediator.getEventBuilder().withEvent(Events.REDO_TEXT).build();
    }

    /**
     * @param event javafx event..
     *              sends a COPY_MENU event to mediator to copy the selected text to clipboards
     * @see Mediator
     */
    @FXML
    void copyMenuItemClick(ActionEvent event) {
        mediator.getEventBuilder().withEvent(Events.COPY_MENU).build();
        Map<DataFormat, Object> hashMap = new HashMap<>();
        hashMap.put(DataFormat.PLAIN_TEXT, mediator.getMediatorText());
        Clipboard.getSystemClipboard().setContent(hashMap);
    }

    /**
     * @param event javafx event..
     *              sends a CUT_MENU event to mediator and copies the selected text to clipboards then
     *              deletes it from textArea
     * @see Mediator
     */
    @FXML
    void cutMenuItemClick(ActionEvent event) {
        mediator.getEventBuilder().withEvent(Events.CUT_MENU).build();
        Map<DataFormat, Object> hashMap = new HashMap<>();
        hashMap.put(DataFormat.PLAIN_TEXT, mediator.getMediatorText());
        Clipboard.getSystemClipboard().setContent(hashMap);
    }

    /**
     * @param event javafx event..
     *              sends a PASTE_MENU event to mediator and adds the text in clipboards to textArea
     *              starting from the position of the carret
     * @see Mediator
     */
    @FXML
    void pasteMenuItemClick(ActionEvent event) {
        mediator.getEventBuilder().withEvent(Events.PASTE_MENU).build();
    }


    @FXML
    void findAndReplaceMenuItemClick(ActionEvent event){
        mediator.getEventBuilder().withEvent(Events.SHOW_FIND_REPLACE).build();
    }

    @FXML
    void findMenuItemClick(ActionEvent event){
        mediator.getEventBuilder().withEvent(Events.SHOW_FIND).build();
    }


    /**
     * shows the version information of the app
     *
     * @see EditorUtils#showAboutWindow(MainMenuBar)
     */
    @FXML
    void aboutMenuItemClick(ActionEvent event) {
        EditorUtils.showAboutWindow(this);
        mediator.getEventBuilder().withEvent(Events.ABOUT_MENU).build();
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
     * @param file the text file to read from
     *             a task to read the text file in another thread
     *             waits for the thread to finish, then sends OPEN_MENU event to the mediator to update the textArea text
     *             if the file reading fails, it sets the current text to "FAILED"
     * @see Mediator
     * @see EditorUtils
     */
    private void readFile(File file) {
        List<String> list = EditorUtils.readFromFile(file);
        String str = list.stream().collect(Collectors.joining("\n"));
        setCurrentText(str);
        mediator.getEventBuilder()
                .withEvent(Events.OPEN_MENU)
                .withFilePath(file.toPath())
                .withText(text)
                .textChanged(false)
                .fileSaved(true)
                .build();
    }

    /**
     * @param text the text that will be displayed in textArea
     *             used to update textArea's text when reading a file
     **/
    private void setCurrentText(String text) {
        this.text = text;
    }
}
