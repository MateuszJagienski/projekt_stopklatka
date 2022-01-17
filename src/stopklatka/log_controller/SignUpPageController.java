package stopklatka.log_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import stopklatka.MainApp;
import stopklatka.database.DatabaseConnection;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignUpPageController implements Initializable {

    @FXML
    private Button signupSubmit, signupCancel, signupSelectImage;
    @FXML
    private TextField signupUsername, signupEmail;
    @FXML
    private PasswordField signupPassword, signupConfirmPassword;
    @FXML
    private ImageView imageView;
    private String image = "stopklatka/images/User.png";
    private final MainApp mainApp = new MainApp();
    private Connection connection;
    private DatabaseConnection databaseConnection;
    private PreparedStatement pst;

    @FXML
    public void setSignupCancel(MouseEvent mouseEvent) {
        mainApp.goToNextPage("/stopklatka/log_view/LoginPage.fxml", "Login Page");
    }

    @FXML
    public void selectImageButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("stopklatka/images/filmlogo.png");
        File testFile = new File("src/stopklatka/images");
        fileChooser.setInitialDirectory(testFile);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png file", "*.png"));
        File f = fileChooser.showOpenDialog(null);
        if (f != null) {
            image = "stopklatka/images/" + f.getName();
            imageView.setImage(new Image(image));
        }
    }

    @FXML
    public void setSignupSubmit(MouseEvent mouseEvent) {
        if (validateUser()) {
            String insertUser = "INSERT INTO `stopklatka`.`user`\n" +
                    "(`username`,\n" +
                    "`email`,\n" +
                    "`password`," +
                    "`avatar`)" +
                    "VALUES (?,?,?,?)";
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
                pst.setString(4, image);
                pst.executeUpdate();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            mainApp.goToNextPage("/stopklatka/log_view/LoginPage.fxml", "Login Page");
        } else {
        }
    }

    @FXML
    public boolean validateUser() {
        Pattern username = Pattern.compile("^[a-zA-Z]+$");
        Pattern password = Pattern.compile("([a-zA-Z\\d]){3,15}");
        Pattern email = Pattern.compile(".+@.+\\..+");
        return username.matcher(signupUsername.getText()).matches() &&
                password.matcher(signupPassword.getText()).matches() &&
                signupPassword.getText().equals(signupConfirmPassword.getText()) &&
                email.matcher(signupEmail.getText()).matches();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection = new DatabaseConnection();
        signupSelectImage.setOnAction(this::selectImageButton);
        imageView.setFitHeight(111);
        imageView.setFitWidth(111);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(image));
    }
}