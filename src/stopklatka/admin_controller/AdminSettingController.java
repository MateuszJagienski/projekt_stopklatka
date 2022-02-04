package stopklatka.admin_controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import stopklatka.database.DatabaseConnection;
import stopklatka.model.Movie;
import stopklatka.model.User;
import stopklatka.model.UserAccount;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminSettingController implements Initializable {

    private Connection connection;
    private DatabaseConnection databaseConnection;
    private PreparedStatement pst;
    private ResultSet rs;
    private Statement stm;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private ArrayList<User> userArrayList = new ArrayList<>();

    @FXML
    TilePane filmTilePane;
    @FXML
    BorderPane borderPane;
    @FXML
    Button addButton;
    @FXML
    TilePane userTilePane;
    @FXML
    ScrollPane filmScrollPane;
    @FXML
    ScrollPane userScrollPane;
    @FXML
    Button refreshButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection = new DatabaseConnection();
        connection = databaseConnection.getConnection();
        selectFilmQuery();
        selectUserQuery();
        loadFilmList();
        loadUserList();
        addButton.setOnAction(e -> addButtonClick());
    }

    private void selectUserQuery() {
        String selectUser= "SELECT * FROM `stopklatka`.`user`";
        try {
            pst = connection.prepareStatement(selectUser);
            rs = pst.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("avatar"), new UserAccount(10000.0));
                user.setId(rs.getInt("id"));
                userArrayList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectFilmQuery() {
        String selectFilm = "SELECT * FROM `stopklatka`.`movies`;";
        try {
            pst = connection.prepareStatement(selectFilm);
            rs = pst.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie(rs.getString("title"), rs.getString("description"), rs.getString("image"), rs.getDouble("price"));
                movie.setId(rs.getInt("id"));
                movieArrayList.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUserList() {
        System.out.println(1);
        for (User u : userArrayList) {
            System.out.println(u);
            loadUser(u);
        }
        userScrollPane.setContent(userTilePane);
    }

    private void loadUser(User u) {

        Label userLabel = new Label();
        userLabel.setText(u.getUsername());
        userLabel.setFont(new Font("Bold", 16));
        userLabel.setWrapText(false);
        userLabel.setPrefWidth(150);
        userLabel.setMaxWidth(150);
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        HBox hBox = new HBox(10, userLabel, editButton, deleteButton);
        VBox vBox = new VBox(10, hBox);
        vBox.setAlignment(Pos.CENTER);
//        editButton.setOnAction(e -> editButtonClick(titleLabel, imageView, f));
//        deleteButton.setOnAction(e -> deleteButtonClick(vBox, f));
        userTilePane.getChildren().add(vBox);

    }

    private void addButtonClick() {
        Movie f = AdminStage.addFilmField();
        if (f == null) {
            return;
        }
        String insertFilm = "INSERT INTO `stopklatka`.`movies`\n" +
                            "(`image`,\n" +
                            "`description`,\n" +
                            "`price`, \n" +
                            "`title`) VALUES (?,?,?,?)";
        String selectId = "SELECT `id` FROM `stopklatka`.`movies`\n" +
                          "ORDER BY `id` DESC LIMIT 1";
        connection = databaseConnection.getConnection();
        try {
            pst = connection.prepareStatement(insertFilm);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            pst.setString(1, f.getImage());
            pst.setString(2, f.getDescription());
            pst.setDouble(3, f.getPrice());
            pst.setString(4, f.getTitle());
            pst.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            pst = connection.prepareStatement(selectId);
            rs = pst.executeQuery();
            while (rs.next()) {
                f.setId(rs.getInt("id"));
                System.out.println(f.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadFilm(f);
    }

    private void loadFilmList() {
        for (Movie f: movieArrayList) {
            loadFilm(f);
        }
        filmScrollPane.setContent(filmTilePane);
    }

    private void loadFilm(Movie f) {
        ImageView imageView;
        try {
            imageView = new ImageView(f.getImage());
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
        } catch (Exception e) {
            deleteFilm(f);
            e.printStackTrace();
            return;
        }

        Label titleLabel = new Label();
        titleLabel.setText(f.getTitle() + " " + f.getPrice() + " PLN");
        titleLabel.setFont(new Font("Bold", 16));
        titleLabel.setWrapText(false);
        titleLabel.setPrefWidth(150);
        titleLabel.setMaxWidth(150);
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        HBox hBox = new HBox(10, imageView, titleLabel, editButton, deleteButton);
        VBox vBox = new VBox(10, hBox);
        vBox.setAlignment(Pos.CENTER);
        editButton.setOnAction(e -> editButtonClick(titleLabel, imageView, f));
        deleteButton.setOnAction(e -> deleteButtonClick(vBox, f));
        filmTilePane.getChildren().add(vBox);
    }

    private void deleteButtonClick(VBox vBox, Movie movie) {
        if (AdminStage.confirmation("Are you sure?", "Delete", "yes", "no")) {
            filmTilePane.getChildren().remove(vBox);
            deleteFilm(movie);
        }
    }

    private void deleteFilm(Movie movie) {
        int id = movie.getId();
        String deleteFilm = "DELETE FROM `stopklatka`.`movies` \n" +
                "WHERE `id` = " + id;
        try {
            stm = connection.createStatement();
            stm.executeUpdate(deleteFilm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editButtonClick(Label label, ImageView imageView, Movie f) {
        Movie movie = AdminStage.editFilmField(f);
        if (movie == null) {
            return;
        }
        label.setText(movie.getTitle() + " " + movie.getPrice() + " PLN");
        imageView.setImage(new Image(movie.getImage()));
        String updateFilm = "UPDATE `movies` \n" +
                            "SET `image`=?, " +
                            "`description`=?," +
                            "`price`=?," +
                            "`title`=?" +
                            "WHERE `id`=?";
        System.out.println(movie.getId());
        try {
            pst = connection.prepareStatement(updateFilm);
            pst.setString(1, movie.getImage());
            pst.setString(2, movie.getDescription());
            pst.setDouble(3, movie.getPrice());
            pst.setString(4, movie.getTitle());
            pst.setInt(5, movie.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}