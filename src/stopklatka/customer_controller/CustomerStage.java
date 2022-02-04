package stopklatka.customer_controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import stopklatka.model.Movie;
import stopklatka.model.User;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerStage {
    private static Stage stage;
    private static Double sum = 0.0;
    private static Label priceLabel = new Label();

    public static void finalizePayment(ArrayList<Movie> arrayList, User user) throws IOException {
        stage = new Stage();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println(12);
                sum = 0.0;
            }
        });
        VBox vBox = new VBox();
        ScrollPane scrollPane = new ScrollPane();

        for (Movie f : arrayList) {
            System.out.println(2);
            sum += f.getPrice();
            ImageView imageView = new ImageView(new Image(f.getImage()));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            Label titleLabel = new Label(f.getTitle());
            titleLabel.setWrapText(true);
            Button deleteButton = new Button("Delete");
            HBox hBox = new HBox(10, imageView, titleLabel, deleteButton);
            vBox.getChildren().addAll(hBox);
            deleteButton.setOnAction(e -> deleteButtonClick(f, hBox));
        }
        Button buyButton = new Button("Buy");
        buyButton.setOnAction(e -> buyButtonClick(arrayList, user));
        priceLabel.setText(sum + " PLN");
        HBox hBox1 = new HBox(10, priceLabel, buyButton);
        HBox hBox = new HBox(180, vBox, hBox1);
        scrollPane.setContent(hBox);
        Scene scene = new Scene(scrollPane);
        stage.setWidth(650);
        stage.setHeight(650);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private static void buyButtonClick(ArrayList<Movie> arrayList, User u) {
        u.getUserAccount().addToList(arrayList);
        u.getUserAccount().sub(sum);
        System.out.println(u.getUserAccount().getMoney());
        stage.close();
    }

    private static void deleteButtonClick(Movie movie, HBox hBox) {
        sum -= movie.getPrice();
        priceLabel.setText(sum + " PLN");
        hBox.getChildren().clear();
    }
}