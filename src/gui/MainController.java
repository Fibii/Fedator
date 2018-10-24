package gui;

import EditLogic.Connector;
import EditLogic.IEdit;
import gui.util.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainController {
    private Runnable r1;
    private String currentFileName;
    private boolean fileOpened;
    private Connector connector = new Connector(); //for the undo-redo

    @FXML
    private TextArea textArea;
    @FXML
    private TextArea lineCounter; //for later use
    @FXML
    private ChoiceBox<Integer> choiceBox;

    @FXML
    private MenuBar menuBar;
    @FXML

    private void saveUsingKeyboard(KeyEvent event) {
        if(fileOpened) {
            System.out.println(textArea.getText());
            Util.saveFile(textArea.getText(),currentFileName);
        }
    }

    public void initialize() {
        //add the font sizes to the choiceBox
        choiceBox.getItems().addAll(12, 14, 16, 18, 20, 22, 24, 26, 32, 48, 56);

        //select 18 as the default font size
        choiceBox.getSelectionModel().select(3); //3 is the index
        setFontSize(18);

        choiceBoxListener();
        textAreaListener();

        //update the line count  every 200ms
     //   r1 = () -> setLineCount(numberOfLines());


        //update the size of the line counter using a  SES, so each 200 ms, the font is set to a new value
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);

        //change this later
       // lineCounter.setOpacity(0);

        menuBarInit();

      //  scheduledExecutorService.scheduleAtFixedRate(r1, 0, 200, TimeUnit.MILLISECONDS);

    }

    /**
     * changes the font size whenever the user chooses a new size from the choicebox
     */
    private void choiceBoxListener() {
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) ->
                        setFontSize(choiceBox.getValue()));
    }

    private void textAreaListener() {

        //update the getTextArea method whenever the text is changed
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                connector.update(textArea.getText());
            }
        });
    }

    /**
     * sets the font size
     */
    private void setFontSize(int size) {
        textArea.setFont(new Font(size));
        //lineCounter.setFont(new Font(size));
    }

    /**
     * shows the number of lines on the side..
     */
    public void setLineCount(int length) {
        //lineCounter.setText("1\n2\n3\n");
        if (lineCounter.getOpacity() != 0) {
            StringBuilder a = new StringBuilder();
            for (int i = 0; i <= length; i++) {
                a.append(i + 1 + "\n");
                lineCounter.setText(a.toString());
                //scroll down whenever the number of lines increases
                lineCounter.setScrollTop(textArea.getHeight());
            }
        }
    }

    /**
     * returns the number of lines
     */
    public int numberOfLines() {
        return textArea.getText().split("\n").length;
    }

    private void menuBarInit() {
        //setup the File Menu
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");

        //setup the menu items
        MenuItem save = new MenuItem("save (ctrl + s)");
        MenuItem open = new MenuItem("open (ctrl + o)");
        MenuItem exit = new MenuItem("exit");

        MenuItem undo = new MenuItem("undo (ctr + z)");
        MenuItem redo = new MenuItem("redo (ctr + shift + z)");

        //set on action
        exit.setOnAction(event -> System.exit(0));
        save.setOnAction(event -> saveMenuClick());
        open.setOnAction(event -> openMenuClick());

        undo.setOnAction(event -> undoMenuClick());
        redo.setOnAction(event -> redoMenuClick());
        //add the menu items to the file menu
        fileMenu.getItems().addAll(open, save, exit);
        editMenu.getItems().addAll(undo,redo);

        /*
        This was supposed to be a view menu with a toggleLineNumber menu item
        in it, it caused thread errors due to bad planning, will keep it for
        future work maybe.

        Menu view = new Menu("View");

        MenuItem toggleLineCounter = new Menu("show line numbers");

        //the if and else should be a method
        toggleLineCounter.setOnAction(event -> {
            if(toggleLineCounter.getText().equals("show line numbers")){
                lineCounter.setOpacity(100);
                toggleLineCounter.setText("hide line numbers");
            } else {
                lineCounter.setOpacity(0);
                toggleLineCounter.setText("show line numbers");
            }
        });

        view.getItems().addAll(toggleLineCounter);

    */
        //add everything to the menu bar
        menuBar.getMenus().addAll(fileMenu,editMenu);

    }

    private void saveMenuClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));

        //automatically save the file if it was opened from the editor
        if(fileOpened) {
            Util.saveFile(textArea.getText(),currentFileName);
            return;
        }

        //show the file chooser
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            Util.saveFile(textArea.getText(), file.getPath() + ".txt");
        }
        }

    private  void openMenuClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    String everything = sb.toString();
                    textArea.setText(everything);
                    currentFileName = file.getPath();
                    fileOpened = true;

                    //set the title of the stage to the current file name
                    Stage stage = (Stage) textArea.getScene().getWindow();
                    stage.setTitle(file.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void undoMenuClick(){
        connector.undo();
        textArea.setText(connector.getText());
    }

    private void redoMenuClick(){
        connector.redo();
        connector.redo(); //find out why this needs to be executed 2 times to work
        textArea.setText(connector.getText());
    }
}