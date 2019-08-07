package gui.mediator;

import gui.MainController;
import gui.TabSpace;
import gui.components.MainMenuBar;
import lib.EditorUtils;

import java.nio.file.Path;
import java.util.List;

import static gui.mediator.Events.*;


//todo: refactor mediator to store and use tabSpace arraylist

public class Mediator implements IMediator {
    private Path filePath;
    private MainController mainController;
    private boolean fileIsSaved;
    private boolean textIsChanged;
    private String text;
    private MainMenuBar mainMenuBar;
    private List<TabSpace> tabSpaces;


    public static Mediator getInstance() {
        return MediatorInstance.INSTANCE;
    }

    @Override
    public void setMenuBar(MainMenuBar mainMenuBar) {
        this.mainMenuBar = mainMenuBar;
    }

    @Override
    public void setTabSpaces(List<TabSpace> tabSpaces) {
        this.tabSpaces = tabSpaces;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public boolean getFileIsSaved() {
        return fileIsSaved;
    }

    public boolean getTextIsChanged() {
        return textIsChanged;
    }

    public String getText() {
        return text;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void sendEvent(Events event) {
        int tabIndex = mainController.getCurrentTabIndex();
        text = tabSpaces.get(tabIndex).getText();
        switch (event) {
            case TEXT_CHANGED:
                textIsChanged = true;
                break;
            case NEW_TAB:
                mainController.createNewTab();
                break;
            case UNDO_TEXT:
                tabSpaces.get(tabIndex).sendEvent(UNDO_TEXT);
                break;
            case OPEN_MENU:
                //todo: maybe change the desgin to:
                /*
                 * let the MainMenuBar class handles these variables, like when open is clicked
                 * mediator.setFileIsSaved(true);
                 * mediator.textIsChanged(false);
                 * and so on
                 *
                 * actually do this !!!
                 * */
                fileIsSaved = true;
                textIsChanged = false;
                filePath = mainMenuBar.getSavedFilePath();
                text = mainMenuBar.getText();

                tabSpaces.get(tabIndex).sendEvent(OPEN_MENU);
                EditorUtils.setCurrentEditorTitle(mainMenuBar, mainController.getCurrentTab(),tabSpaces.get(tabIndex).getCurrentPath());
                mainController.updateIsSaved(fileIsSaved);
                break;

            case REDO_TEXT:
                tabSpaces.get(tabIndex).sendEvent(REDO_TEXT);
                break;

            case SAVE_MENU:
                fileIsSaved = true;
                break;

            case ABOUT_MENU:
                //todo: implement this
                break;
            case AUTO_SAVE:
                EditorUtils.writeToFile(text, filePath);
                break;

            case CLOSE_MENU:
                break;
            case SAVE_FILE:
                mainMenuBar.showSaveWindow();
                fileIsSaved = true;
                EditorUtils.setCurrentEditorTitle(mainMenuBar, mainController.getCurrentTab(), tabSpaces.get(tabIndex).getCurrentPath());
                break;
            case EXIT_EVENT:
                EditorUtils.writeToFile(text, filePath);
                textIsChanged = false;
                System.exit(0);
                break;
        }
    }

    private static final class MediatorInstance {
        private static Mediator INSTANCE = new Mediator();
    }

}