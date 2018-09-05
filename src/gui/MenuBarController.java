package gui;

import gui.util.Util;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MenuBarController extends MainController {
    private static String text;

    protected static void showMenuBar(MenuBar menuBar, TextArea textArea, TextArea lineCounter){

        //add everything to the menu bar
        menuBar.getMenus().addAll(fileMenu(menuBar,textArea),view(lineCounter));
    }

    private static Menu fileMenu(MenuBar menuBar, TextArea textArea){
        //setup the File Menu
        Menu fileMenu = new Menu("File");

        MenuItem save = new MenuItem("save (ctrl + s)");
        MenuItem open = new MenuItem("open");
        MenuItem exit = new MenuItem("exit");

        exit.setOnAction(event -> exit(menuBar));
        save.setOnAction(event -> saveMenuClick());
        open.setOnAction(event -> openMenuClick());

        //add the menu items to the file menu
        fileMenu.getItems().addAll(save,exit,open);

        return fileMenu;

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

    private static void saveMenuClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));

        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            Util.saveFile(text,file.getName());
        }
    }

    private static void openMenuClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));

        File file = fileChooser.showOpenDialog(null);

        if(file != null){
           //read the text file ?
            try {
                try(BufferedReader br = new BufferedReader(new FileReader(file.getName()))) {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    String everything = sb.toString();


                }            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public static void setText(String t) {
        text = t;

    }

}
