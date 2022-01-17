package stopklatka.customer_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stopklatka.database.DatabaseConnection;
import stopklatka.model.Film;
import stopklatka.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerHomeController implements Initializable{

    private Connection connection;
    private DatabaseConnection databaseConnection;
    private PreparedStatement pst;
    private ResultSet rs;
    private ArrayList<Film> filmArrayList = new ArrayList<>();
    private ArrayList<Film> cartArrayList = new ArrayList<>();
    private User user;
    @FXML
    private Button button;
    @FXML
    private Button secretButtton;
    @FXML
    private TilePane tilePane;
    @FXML
    private ScrollPane scrollPane;
    private String PATH = "src/stopklatka/images";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection = new DatabaseConnection();
        connection = databaseConnection.getConnection();
        selectFilmQuery();
        loadFilmList();
        button.setOnAction(e -> buttonClick());
//        secretButtton.setOnMouseClicked(e -> receiveData(e));
//        Button but = new Button("ads");
//        but.setOnMouseClicked(e -> receiveData(e));
        //secretButtton.fire();
    }

    @FXML
    private void receiveData() {
        // Step 1
        Stage stage = (Stage) button.getScene().getWindow();
        // Step 2
        user = (User) stage.getUserData();
        // Step 3
        System.out.println(user);
    }

    private void buttonClick() {
        receiveData();
        try {
            CustomerStage.finalizePayment(this.cartArrayList, user);
            System.out.println(1);
            this.cartArrayList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectFilmQuery() {
        String selectFilm = "SELECT * FROM `stopklatka`.`film`;";
        try {
            pst = connection.prepareStatement(selectFilm);
            rs = pst.executeQuery();
            while (rs.next()) {
                Film film = new Film(rs.getString("title"), rs.getString("description"), rs.getString("image"), rs.getDouble("price"));
                film.setId(rs.getInt("id"));
                filmArrayList.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFilmList() {
        for (Film f : filmArrayList) {
            loadFilm(f);
        }
        scrollPane.setContent(tilePane);
    }

    private void loadFilm(Film f) {
        ImageView imageView = new ImageView(f.getImage());
        imageView.setFitHeight(111);
        imageView.setFitWidth(162);
        Region spacer = new Region();
        Label titleLabel = new Label();
        titleLabel.setText(f.getTitle());
        titleLabel.setFont(new Font("Bold", 16));
        titleLabel.setPrefHeight(40);
        VBox vBox1 = new VBox(imageView, titleLabel);
        vBox1.setAlignment(Pos.TOP_CENTER);
        Text dTxt = new Text(f.getDescription());
        dTxt.setWrappingWidth(320);
        Button cartButton = new Button(f.getPrice().toString() + " PLN");
        cartButton.setOnAction(e -> cartButtonClick(f));
        HBox hBox = new HBox(10, vBox1, spacer, dTxt, cartButton);
        VBox vBox = new VBox(10, hBox);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        vBox.setAlignment(Pos.CENTER);
        tilePane.getChildren().add(vBox);
    }


    public void cartButtonClick(Film f) {
        this.cartArrayList.add(f);
        button.setText("Buy" + "(" + this.cartArrayList.size() + ")");
    }
}