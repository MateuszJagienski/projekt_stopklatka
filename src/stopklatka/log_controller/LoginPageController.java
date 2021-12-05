package stopklatka.log_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import stopklatka.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    Button loginButton, signupButton;
    @FXML
    TextField loginUsernameField, loginPasswordField;
    @FXML
    ImageView engbutton, polbutton;


    @FXML
    public void signupButtonPressed(MouseEvent mouseEvent) {
        MainApp main = new MainApp();
        main.goToNextPage("/stopklatka/log_view/SignUpPage.fxml", "Sign Up Page");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void setEngbutton(MouseEvent mouseEvent) {
        MainApp main = new MainApp();
        main.goToNextPage("/stopklatka/log_view/SignUpPage.fxml", "Sign Up Page");
    }
}
