package gui;

import gui.util.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainController {
    @FXML
    private TextArea textArea;

    @FXML
    private TextArea lineCounter;

    @FXML
    private Button saveBtn;

    @FXML
    private ChoiceBox<Integer> choiceBox;

    @FXML
    private ScrollBar scrollBar;

    @FXML
    private HBox hboxTxt;

    @FXML
    private MenuBar menuBar;

    private Runnable r1;
    private String currentFileName;
    private boolean fileOpened;

    @FXML
    void saveUsingKeyboard(KeyEvent event) {
        if (event.getCode() == KeyCode.S && event.isControlDown()) {
            System.out.println(textArea.getText());
            /*
            for(int i = 0; i < textArea.getParagraphs().size(); i++){
                System.out.println(textArea.getParagraphs().get(i));

            }*/
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
    public void choiceBoxListener() {
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) ->
                        setFontSize(choiceBox.getValue()));
    }

    public void textAreaListener() {

        //update the getTextArea method whenever the text is changed
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {


            }
        });
    }

    /**
     * sets the font size
     */
    public void setFontSize(int size) {
        textArea.setFont(new Font(size));
        //lineCounter.setFont(new Font(size));
    }

    /**
     * shows the number of lines on the side..
     */
    private int i = 0;

    public void setLineCount(int length) {
        //lineCounter.setText("1\n2\n3\n");
        if (lineCounter.getOpacity() != 0) {
            StringBuilder a = new StringBuilder("");
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

    public void menuBarInit() {
        //setup the File Menu
        Menu fileMenu = new Menu("File");

        MenuItem save = new MenuItem("save (ctrl + s)");
        MenuItem open = new MenuItem("open");
        MenuItem exit = new MenuItem("exit");


        exit.setOnAction(event -> System.exit(0));
        save.setOnAction(event -> saveMenuClick());
        open.setOnAction(event -> openMenuClick());

        //add the menu items to the file menu
        fileMenu.getItems().addAll(open, save, exit);


        /*
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
        menuBar.getMenus().addAll(fileMenu);

    }

    private void saveMenuClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));

        //automaticall save the file if it was opened from the editor
        if(fileOpened) {
            Util.saveFile(textArea.getText(),currentFileName);
            return;
        }

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
                Util.saveFile(textArea.getText(), file.getName() + ".txt");
            }

        }

    private  void openMenuClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            //read the text file ?
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(file.getName()))) {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    String everything = sb.toString();
                    System.out.println(everything);
                    textArea.setText(everything);
                    currentFileName = file.getName();
                    fileOpened = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}







