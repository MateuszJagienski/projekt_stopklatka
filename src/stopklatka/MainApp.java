package stopklatka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static Stage theStage;


    public void goToNextPage(String pathToFXML, String pageTitle) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(pathToFXML));

            Scene scene = new Scene(root, 1350, 850);
            this.theStage.setTitle(pageTitle);
            this.theStage.setScene(scene);
            this.theStage.show();
        } catch (Exception e) {
            System.out.println("Couldn't load page " + pathToFXML);
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.theStage = stage;

        Parent root = FXMLLoader.load(getClass().getResource("/stopklatka/log_view/LoginPage.fxml"));
        Scene scene = new Scene(root,1350, 850);  //SETS A NEW SCENE WITH THE LOADED FXML AND DEFINES THE WDITH/HEIGHT
        this.theStage.setScene(scene);   //SETS THE SCENE TO THE STAGE
        this.theStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
