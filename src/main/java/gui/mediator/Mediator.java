package gui.mediator;

import gui.MainController;
import gui.components.MainMenuBar;
import gui.components.TextSpace;
import lib.EditorUtils;
import smallUndoEngine.Connector;

import java.nio.file.Path;

public class Mediator implements IMediator {
    Path filePath;
    private MainController mainController;
    private MainMenuBar mainMenuBar;
    private TextSpace textSpace;
    private Connector connector;
    private boolean fileIsSaved;
    private boolean textIsChanged;

    public static Mediator getInstance() {
        return MediatorInstance.INSTANCE;
    }

    @Override
    public void setMenuBar(MainMenuBar mainMenuBar) {
        this.mainMenuBar = mainMenuBar;
    }

    @Override
    public void setTextSpace(TextSpace textSpace) {
        this.textSpace = textSpace;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public Connector getCurrentConnector() {
        return connector;
    }

    public String getCurrentText() {
        return textSpace.getText();
    }

    public boolean getFileIsSaved() {
        return fileIsSaved;
    }

    public boolean getTextIsChanged() {
        return textIsChanged;
    }

    public int getNumberOfLines() {
        return mainMenuBar.getNumberOfLines();
    }

    public void sendEvent(Events event) {
        switch (event) {
            case TEXT_CHANGED:
                textIsChanged = true;
                break;
            case NEW_TAB:
                mainController.createNewTab();
                break;
            case UNDO_TEXT:
                textSpace.undo();
                break;
            case OPEN_MENU:
                fileIsSaved = true;
                textIsChanged = false;
                filePath = mainMenuBar.getSavedFilePath();
                textSpace.setCurrentPath(filePath);
                EditorUtils.setCurrentEditorTitle(mainMenuBar, mainController.getCurrentTab(), mainController.getCurrentTextSpace());
                textSpace.setText(mainMenuBar.getText());
                break;

            case REDO_TEXT:
                textSpace.redo();
                break;

            case SAVE_MENU:
                fileIsSaved = true;
                textSpace.setCurrentPath(filePath);
                break;

            case ABOUT_MENU:

                break;
            case AUTO_SAVE:
                EditorUtils.writeToFile(getCurrentText(), filePath);
                break;

            case CLOSE_MENU:
                break;
            case SAVE_FILE:
                mainMenuBar.showSaveWindow();
                fileIsSaved = true;
                EditorUtils.setCurrentEditorTitle(mainMenuBar, mainController.getCurrentTab(), mainController.getCurrentTextSpace());
                break;
            case EXIT_EVENT:
                EditorUtils.writeToFile(getCurrentText(), filePath);
                textIsChanged = false;
                System.exit(0);
                break;
        }
    }

    private static final class MediatorInstance {
        private static Mediator INSTANCE = new Mediator();
    }

}

