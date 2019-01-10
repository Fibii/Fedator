import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application  {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //load the fxml
        Parent root = FXMLLoader.load(this.getClass().getResource("/gui/res/main.fxml"));

        //setup the scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass() .getResource("/old/gui/res/css.css").toExternalForm());

        //finalize
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(800);
        primaryStage.setTitle("untitled");
    }
}
