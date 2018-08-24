package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.sound.sampled.Line;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    private TextArea textArea;

    @FXML
    private ListView<String> LineCounter;


    @FXML
    private Button saveBtn;

    @FXML
    private ChoiceBox<Integer> choiceBox;

    @FXML
    private ScrollBar scrollBar;

    @FXML
    private HBox hboxTxt;

    private Runnable r1;

    @FXML
    void saveBtnClick(ActionEvent event) {
        //iterate over list

    }

    @FXML
    void saveUsingKeyboard(KeyEvent event) {
        if (event.getCode() == KeyCode.S && event.isControlDown()){
            for(int i = 0; i < textArea.getParagraphs().size(); i++){
                System.out.println(textArea.getParagraphs().get(i));
            }
        }
    }

    public void initialize(){
        //add the font sizes to the choiceBox
        choiceBox.getItems().addAll(12,14,16,18,20,22,24,26,32,48,56);

        //select 18 as the default font size
        choiceBox.getSelectionModel().select(3); //3 is the index
       // setFontSize(18);

        choiceBoxListener();


        //set the line counter
        LineCounter.getItems().addAll("1\n","2\n","3\n","4\n","5\n","6\n");

        r1 = () -> setLineCounterFont();

        //update the size of the line counter using a  SES, so each 200 ms, the font is set to a new value
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleAtFixedRate(r1,0,200, TimeUnit.MILLISECONDS);
    }

    public void choiceBoxListener(){
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v,oldValue,newValue) ->
                        setFontSize(choiceBox.getValue()) );
    }

    public void setFontSize(int size){
        textArea.setFont(new Font(size));
    }

    public void setLineCounterFont(){
        LineCounter.setCellFactory(cell -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);

                        // decide to add a new styleClass
                        // getStyleClass().add("costume style");
                        // decide the new font size
                        setFont(Font.font(choiceBox.getValue()));
                        setPadding(new Insets(0.2)); //to match the text area
                    }
                }
            };
        });

    }


}
