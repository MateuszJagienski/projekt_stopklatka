package stopklatka.log_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import stopklatka.MainApp;
import stopklatka.database.DatabaseConnection;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    Button loginButton, signupButton;
    @FXML
    TextField loginUsername;
    @FXML
    PasswordField loginPassword;
    @FXML
    ImageView engbutton, polbutton;

    private final MainApp mainApp = new MainApp();

    private Connection connection;
    private DatabaseConnection databaseConnection;
    private PreparedStatement pst;
    private Statement statement;
    private ResultSet rs;


    @FXML
    public void signupButtonPressed(MouseEvent mouseEvent) {
        MainApp main = new MainApp();
        main.goToNextPage("/stopklatka/log_view/SignUpPage.fxml", "Sign Up Page");
    }

    @FXML
    public void setLoginButton(MouseEvent mouseEvent) {
        if (validateUser()) {
            mainApp.goToNextPage("/stopklatka/customer_view/CustomerHomeView.fxml", "home");
        }
    }

    @FXML
    public boolean validateUser() {

        String validUsername = "SELECT count(*) FROM `stopklatka`.`user` where USERNAME=(?) and password=(?) ;";


        connection = databaseConnection.getConnection();

        try {
            pst = connection.prepareStatement(validUsername);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        try {
            //pst.setString(1, loginUsername.getText());
            pst.setString(1, loginUsername.getText());
            pst.setString(2, loginPassword.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                String count = rs.getString("count(*)");
                if (count.equals("1")) {
                    System.out.println("xd");
                    return true;
                }
            }



        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        System.out.println("zły login lub haslo");
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection = new DatabaseConnection();
    }
    @FXML
    public void setEngbutton(MouseEvent mouseEvent) {
        MainApp main = new MainApp();
        main.goToNextPage("/stopklatka/log_view/SignUpPage.fxml", "Sign Up Page");
    }
}
