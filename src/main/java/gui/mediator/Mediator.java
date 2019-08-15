package gui.mediator;

import gui.MainController;
import gui.TabSpace;
import gui.components.MainMenuBar;
import lib.EditorUtils;

import java.nio.file.Path;
import java.util.List;

import static gui.mediator.Events.*;


//todo: define a Component interface, and make mediator use components
/*
* like Component mainMenuBar instead of MainMenuBar mainMenuBar..
* */

public class Mediator implements IMediator {
    private Path filePath;
    private MainController mainController;
    private boolean fileSaved;
    private boolean textChanged;
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

    public boolean getFileSaved() {
        return fileSaved;
    }

    public boolean getTextChanged() {
        return textChanged;
    }

    public String getText() {
        return text;
    }

    public Path getFilePath() {
        return filePath;
    }

    private void notify(Events event) {
        int tabIndex = mainController.getCurrentTabIndex();
        switch (event) {
            case TEXT_CHANGED:
                //textChanged = true;
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
                 * mediator.textChanged(false);
                 * and so on
                 *
                 * actually do this !!!
                 * */
//                fileSaved = true;
//                textChanged = false;
//                filePath = mainMenuBar.getSavedFilePath();
//                text = mainMenuBar.getText();
                tabSpaces.get(tabIndex).sendEvent(OPEN_MENU);
                EditorUtils.setCurrentEditorTitle(mainMenuBar, mainController.getCurrentTab(),tabSpaces.get(tabIndex).getCurrentPath());
                mainController.updateIsSaved(fileSaved);
                break;

            case REDO_TEXT:
                tabSpaces.get(tabIndex).sendEvent(REDO_TEXT);
                break;

            case SAVE_MENU:
                //fileSaved = true;
                break;

            case ABOUT_MENU:
                //todo: implement this
                break;
            case AUTO_SAVE:
                text = tabSpaces.get(tabIndex).getText();
                EditorUtils.writeToFile(text, filePath);
                break;

            case CLOSE_MENU:
                break;
            case SAVE_FILE:
                mainMenuBar.showSaveWindow();
                //fileSaved = true;
                EditorUtils.setCurrentEditorTitle(mainMenuBar, mainController.getCurrentTab(), tabSpaces.get(tabIndex).getCurrentPath());
                break;
            case EXIT_EVENT:
                text = tabSpaces.get(tabIndex).getText();
                EditorUtils.writeToFile(text, filePath);
                System.exit(0);
                break;
        }
    }

    public EventBuilder getEventBuilder(){
        // so the new object has the same values of the mediator
        return new EventBuilder(textChanged, fileSaved, filePath, text);
    }

    private static final class MediatorInstance {
        private static Mediator INSTANCE = new Mediator();
    }

    public static class EventBuilder {

        private boolean textChanged;
        private boolean fileSaved;
        private Path filePath;
        private String text;
        private Events event;

        private EventBuilder(boolean textChanged, boolean fileSaved, Path filePath, String text) {
            this.textChanged = textChanged;
            this.fileSaved = fileSaved;
            this.filePath = filePath;
            this.text = text;
        }

        public EventBuilder textChanged(boolean textChanged) {
            this.textChanged = textChanged;
            return this;
        }

        public EventBuilder fileSaved(boolean fileSaved) {
            this.fileSaved = fileSaved;
            return this;
        }

        public EventBuilder withFilePath(Path filePath) {
            this.filePath = filePath;
            return this;
        }

        public EventBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public EventBuilder withEvent(Events event){
            this.event = event;
            return this;
        }

        public void build() {
            Mediator mediator = Mediator.getInstance();
            mediator.text = this.text;
            mediator.filePath = this.filePath;
            mediator.fileSaved = this.fileSaved;
            mediator.textChanged = this.textChanged;
            mediator.notify(event);
        }


    }

}
