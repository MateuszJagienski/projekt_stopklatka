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
    private Button signupSubmitButton, signupCancelButton;
    @FXML
    private TextField signupFirstNameField, signupLastNameField, signupEmailField, signupPhoneFiled,
            signupUsernameField;
    @FXML
    private PasswordField signupPasswordField, signupConfirmPasswordField;

    private MainApp mainApp;

    private Connection connection;
    private DatabaseConnection databaseConnection;
    private PreparedStatement pst;

    @FXML
    public void setSignupCancelButton(MouseEvent mouseEvent) {
        Stage stage = (Stage) signupCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void setSignupSubmitButton(MouseEvent mouseEvent) {
        if (validateUser()) {
            System.out.println("Gituwa");
            User newUser = new User(signupFirstNameField.getText(), signupLastNameField.getText(),
                            signupEmailField.getText(), signupPhoneFiled.getText(),
                            signupUsernameField.getText(), signupPasswordField.getText(), "Customer");


            String insertUser = "INSERT INTO `projekt_stopklatka`.`user`\n" +
                    "(`first_name`,\n" +
                    "`last_name`,\n" +
                    "`email`,\n" +
                    "`phone_number`,\n" +
                    "`role`,\n" +
                    "`password`,\n" +
                    "`username`)\n" +
                    "VALUES (?,?,?,?,?,?,?)";

            System.out.println(insertUser);

            connection = databaseConnection.getConnection();
            try {
                pst = connection.prepareStatement(insertUser);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            try {
                pst.setString(1, signupFirstNameField.getText());
                pst.setString(2, signupLastNameField.getText());
                pst.setString(3, signupEmailField.getText());
                pst.setString(4, signupPhoneFiled.getText());
                pst.setString(5, "Customer");
                pst.setString(6, signupPasswordField.getText());
                pst.setString(7, signupUsernameField.getText());

                pst.executeUpdate();


            } catch (SQLException e1) {

                e1.printStackTrace();
            }


        } else {
            System.out.println("nie gituwa");
        }
    }

    @FXML
    public boolean validateUser() {
        Pattern name = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
        Pattern password = Pattern.compile("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$");
        //password must contain 1 number (0-9)
//        password must contain 1 uppercase letters
//        password must contain 1 lowercase letters
//        password must contain 1 non-alpha numeric number
//        password is 8-16 characters with no space
        Pattern phoneNumber = Pattern.compile("^\\d{9}$");
        Pattern email = Pattern.compile(".+@.+\\..+");
        Pattern username = Pattern.compile("\\w+");


        System.out.println(name.matcher(signupFirstNameField.getText()).find() + " " +
                name.matcher(signupLastNameField.getText()).find() + " " +
                password.matcher(signupPasswordField.getText()).find() + " " +
                phoneNumber.matcher(signupPhoneFiled.getText()).find() + " " +
                email.matcher(signupEmailField.getText()).find() + " " +
                username.matcher(signupUsernameField.getText()).find());

        return name.matcher(signupFirstNameField.getText()).find() &&
                name.matcher(signupLastNameField.getText()).find() &&
                password.matcher(signupPasswordField.getText()).find() &&
                phoneNumber.matcher(signupPhoneFiled.getText()).find() &&
                email.matcher(signupEmailField.getText()).find() &&
                username.matcher(signupUsernameField.getText()).find() &&
                password.matcher(signupConfirmPasswordField.getText()).find() &&
                signupPasswordField.getText().equals(signupConfirmPasswordField.getText());


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection = new DatabaseConnection();
    }

    public static void main(String[] args) {
    }
}
