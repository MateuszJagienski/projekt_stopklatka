package stopklatka.customer_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerHomeController implements Initializable {

    private Connection connection;
    private DatabaseConnection databaseConnection;
    private PreparedStatement pst;
    private ResultSet rs;
    private ArrayList<Film> filmArrayList = new ArrayList<>();
    private ArrayList<Film> cartArrayList = new ArrayList<>();
    @FXML
    private Button testbtn;

    @FXML
    private TilePane tilePane;
    @FXML
    private ScrollPane scrollPane;

    private String PATH = "src/stopklatka/images";

    public CustomerHomeController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection = new DatabaseConnection();
        String selectFilm = "SELECT * FROM `stopklatka`.`film`;";

        connection = databaseConnection.getConnection();

        try {
            pst = connection.prepareStatement(selectFilm);
            rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println(2);
                System.out.println(rs.getString("title"));
                filmArrayList.add(new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("image"), rs.getDouble("price")));
            }

            for (Film f: filmArrayList) {
                System.out.println(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(filmArrayList.get(0));



//        File dir = new File(PATH);
//        File[] files = dir.listFiles();
        for (Film f : filmArrayList) {
//            Image img = new Image(f.getImage(), 200, 200, true, true);
            System.out.println(f.getImage());
            ImageView imageView = new ImageView(f.getImage());
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);




            Region spacer = new Region();



            Label titleLabel = new Label();
            titleLabel.setText(f.getTitle());
            titleLabel.setFont(new Font("Bold", 16));


            titleLabel.setPrefHeight(40);
            VBox vBox1 = new VBox(imageView, titleLabel);
            vBox1.setAlignment(Pos.TOP_CENTER);

            Text dTxt = new Text(f.getDescription());
            dTxt.setWrappingWidth(320);

            Button cartButton = new Button("Button");
            cartButton.setOnAction(e -> cartButtonClick(f)); // getid

            HBox hBox = new HBox(10, vBox1, spacer, dTxt, cartButton);

            VBox vBox = new VBox(10, hBox);
            VBox.setVgrow(spacer, Priority.ALWAYS);
            vBox.setAlignment(Pos.CENTER);

            tilePane.getChildren().add(vBox);
        }

        ScrollPane scrollPane = new ScrollPane(tilePane);
        testbtn.setOnMouseClicked(this::sendData);




    }

    @FXML
    private void sendData(MouseEvent event) {
        // Step 1
        //this.cartArrayList;
        // Step 2
        Node node = (Node) event.getSource();
        // Step 3
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        try {
            // Step 4
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("stopklatka/customer_view/CustomerCart.fxml"));
            // Step 5
            stage.setUserData(this.cartArrayList);
            // Step 6
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // Step 7
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }


    public void cartButtonClick(Film f) {
        this.cartArrayList.add(f);
        for (Film x : this.cartArrayList ) {
            System.out.println(x.getId());
        }

    }



}
