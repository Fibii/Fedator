package gui;

import gui.FileSaver.FileSaver;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MenuBarController extends MainController {

    private FileSaver fileSaver = new FileSaver();

    protected static void showMenuBar(MenuBar menuBar, TextArea textArea, TextArea lineCounter){

        //add everything to the menu bar
        menuBar.getMenus().addAll(fileMenu(menuBar,textArea),view(lineCounter));
    }

    private static Menu fileMenu(MenuBar menuBar, TextArea textArea){
        //setup the File Menu
        Menu file = new Menu("File");

        MenuItem save = new MenuItem("save (ctrl + s)");
        MenuItem exit = new MenuItem("exit");

        exit.setOnAction(event -> exit(menuBar));
        save.setOnAction(event -> printAll(textArea));

        //add the menu items to the file menu
        file.getItems().addAll(save,exit);

        return file;

    }

    private static Menu view(TextArea lineCounter){
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

    return view;
    }

    /** closes the stage thus the program */
    private static void exit(MenuBar menuBar){
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    private  static void printAll(TextArea textArea){

    }

}
