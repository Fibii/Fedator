import gui.mediator.Events;
import gui.mediator.Mediator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application  {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //load the fxml
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/res/main.fxml"));
        Parent root = fxmlLoader.load();

        //setup the scene
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(this.getClass() .getResource("/old/gui/res/css.css").toExternalForm());

        //finalize
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.setTitle("untitled");
        primaryStage.setOnCloseRequest(event -> {
            onCloseExitConfirmation(false); //TODO:update this
            //System.out.println("med:" + Mediator.getInstance().getIsSaved());
            event.consume();
        });
        primaryStage.show();
    }



    private void onCloseExitConfirmation(boolean fileIssaved) {
        if (!fileIssaved) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are you sure you want to exit?");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Do you want to save your changes before quitting?");
            ButtonType yesBtn = new ButtonType("Yes");
            ButtonType noBtn = new ButtonType("No");
            ButtonType cnlBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesBtn, noBtn, cnlBtn);
            Optional<ButtonType> btnClicked = alert.showAndWait();
            if (btnClicked.get() == yesBtn) {
                Mediator.getInstance().sendEvent(Events.SAVE_FILE);
                System.exit(0);
            } else if(btnClicked.get() == noBtn){
                System.exit(0);
            }
        }
    }
}
