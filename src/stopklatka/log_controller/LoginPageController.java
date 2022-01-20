package stopklatka.log_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import stopklatka.MainApp;
import stopklatka.database.DatabaseConnection;
import stopklatka.model.User;
import stopklatka.model.UserAccount;

import java.io.IOException;
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
    ImageView engbutton, polbutton, mainLogo;

    private final MainApp mainApp = new MainApp();
    private Connection connection;
    private DatabaseConnection databaseConnection;
    private PreparedStatement pst;
    private Statement statement;
    private ResultSet rs;
    private User user;

    @FXML
    public void signupButtonPressed(MouseEvent mouseEvent) {
        MainApp main = new MainApp();
        main.goToNextPage("/stopklatka/log_view/SignUpPage.fxml", "Sign Up Page");
    }

    @FXML
    public void setLoginButton(MouseEvent mouseEvent) {
        if (validateUser()) {
                // Step 2
                Node node = (Node) mouseEvent.getSource();
                // Step 3
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                try {
                    // Step 4
                    Parent root = FXMLLoader.load(getClass().getResource("/stopklatka/customer_view/CustomerHomeView.fxml"));
                    // Step 5
                    stage.setUserData(user);
                    // Step 6
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    // Step 7
                    stage.show();
                } catch (IOException e) {
                    System.err.println(String.format("Error: %s", e.getMessage()));
                }
        }
    }

    @FXML
    public boolean validateUser() {
        String validUsername = "SELECT * FROM `stopklatka`.`user` where USERNAME=(?) and password=(?) ;";
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
                String count = rs.getString("username");
                if (count.equals(loginUsername.getText())) {
                    user = new User(rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("avatar"), new UserAccount(10000.0));
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
    int logo=1;
    public void changeLogo(){
        if(logo==6) logo=1;
        Image myLogo = new Image(getClass().getResourceAsStream("/stopklatka/images/logo"+logo+".png"));
        mainLogo.setImage(myLogo);
        logo++;
    }
}
