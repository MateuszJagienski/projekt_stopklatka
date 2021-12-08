package stopklatka.log_controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import stopklatka.MainApp;
import stopklatka.database.DatabaseConnection;
import stopklatka.model.User;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPageController implements Initializable {

    @FXML
    private Button signupSubmit, signupCancel, signupSelectImage;
    @FXML
    private TextField signupUsername, signupEmail;
    @FXML
    private PasswordField signupPassword, signupConfirmPassword;

    private final MainApp mainApp = new MainApp();

    private Connection connection;
    private DatabaseConnection databaseConnection;
    private PreparedStatement pst;

    @FXML
    public void setSignupCancel(MouseEvent mouseEvent) {
        mainApp.goToNextPage("/stopklatka/log_view/LoginPage.fxml", "Login Page");
    }

    @FXML
    public void setSignupSubmit(MouseEvent mouseEvent) {
        if (validateUser()) {


            String insertUser = "INSERT INTO `stopklatka`.`user`\n" +
                    "(`username`,\n" +
                    "`email`,\n" +
                    "`password`) " +
                    "VALUES (?,?,?)";

            System.out.println(insertUser);

            connection = databaseConnection.getConnection();
            try {
                pst = connection.prepareStatement(insertUser);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            try {
                pst.setString(1, signupUsername.getText());
                pst.setString(2, signupEmail.getText());
                pst.setString(3, signupPassword.getText());

                pst.executeUpdate();


            } catch (SQLException e1) {

                e1.printStackTrace();
            }


        } else {
            System.out.println("nie gituwa");
        }

        mainApp.goToNextPage("/stopklatka/log_view/LoginPage.fxml", "Login Page");
    }

    @FXML
    public boolean validateUser() {
        Pattern username = Pattern.compile("^[a-zA-Z]+$");
        Pattern password = Pattern.compile("([a-zA-Z\\d]){3,15}");
        Pattern email = Pattern.compile(".+@.+\\..+");


        System.out.println(username.matcher(signupUsername.getText()).matches() + " " +
                password.matcher(signupPassword.getText()).matches() + " " + signupPassword.getText().equals(signupConfirmPassword.getText()) + " " +
                email.matcher(signupEmail.getText()).matches());


        return username.matcher(signupUsername.getText()).matches() &&
                password.matcher(signupPassword.getText()).matches() &&
                signupPassword.getText().equals(signupConfirmPassword.getText()) &&
                email.matcher(signupEmail.getText()).matches();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection = new DatabaseConnection();
    }

    public static void main(String[] args) {
        Pattern xd = Pattern.compile("([a-zA-Z\\d]){3,6}");
        System.out.println(xd.matcher("123").matches());
    }
}
