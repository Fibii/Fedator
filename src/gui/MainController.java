package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

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

    @FXML
    void saveBtnClick(ActionEvent event) {

        //togle the view of the line number
        if(lineCounter.getOpacity() == 100) {
            lineCounter.setOpacity(0);
        } else {
            lineCounter.setOpacity(100);
        }

    }

    @FXML
    void saveUsingKeyboard(KeyEvent event) {
        if (event.getCode() == KeyCode.S && event.isControlDown()){
            System.out.println(lineCounter.getScrollTop());
            /*
            for(int i = 0; i < textArea.getParagraphs().size(); i++){
                System.out.println(textArea.getParagraphs().get(i));

            }*/
        }
    }

    public void initialize(){
        //add the font sizes to the choiceBox
        choiceBox.getItems().addAll(12,14,16,18,20,22,24,26,32,48,56);

        //select 18 as the default font size
        choiceBox.getSelectionModel().select(3); //3 is the index
        setFontSize(18);

        choiceBoxListener();


        //update the line count for every 200ms
        r1 = () -> setLineCount(numberOfLines());

        //update the size of the line counter using a  SES, so each 200 ms, the font is set to a new value
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);

        //change this later
        lineCounter.setOpacity(0);

        MenuBarController.showMenuBar(menuBar,textArea,lineCounter);



            scheduledExecutorService.scheduleAtFixedRate(r1,0,200, TimeUnit.MILLISECONDS);


    }

    /** changes the font size whenever the user chooses a new size from the choicebox */
    public void choiceBoxListener(){
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v,oldValue,newValue) ->
                        setFontSize(choiceBox.getValue()) );
    }

    /** sets the font size */
    public void setFontSize(int size){
        textArea.setFont(new Font(size));
        lineCounter.setFont(new Font(size));
    }



    /** shows the number of lines on the side.. */
    private int i  = 0 ;
    public void setLineCount(int length){
        //lineCounter.setText("1\n2\n3\n");
        if(lineCounter.getOpacity() != 0){
            StringBuilder a = new StringBuilder("");
            for(int i = 0; i <= length; i++){
                a.append(i+1 + "\n");
                lineCounter.setText(a.toString());

                //scroll down whenever the number of lines increases
                lineCounter.setScrollTop(textArea.getHeight());
        }

        }



    }

    public TextArea getTextArea(){
        return textArea;
    }

    /** returns the number of lines */
    public int numberOfLines(){
        return textArea.getText().split("\n").length;
    }



    }







