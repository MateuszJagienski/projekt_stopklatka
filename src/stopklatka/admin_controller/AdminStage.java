package stopklatka.admin_controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stopklatka.model.Film;

import java.io.File;

public class AdminStage {
    private static Stage stage;
    private static boolean conf;

    public static Film addFilmField() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        final String[] image = new String[1];
        final Film[] film = new Film[1];
        TextField titleTextField = new TextField();
        titleTextField.setPromptText("Title");
        TextField descriptionTextField = new TextField();
        descriptionTextField.setPromptText("Description");
        TextField priceTextField = new TextField();
        priceTextField.setPromptText("Price");
        Button imageButton = new Button("Select Image");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(true);
        Button applyButton = new Button("Apply");
        imageButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                image[0] = imageButtonClick();
                imageView.setImage(new Image(image[0]));
            }
        } );
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                film[0] = applyButtonClick(titleTextField.getText(), descriptionTextField.getText(), image[0], priceTextField.getText());
            }
        } );
        VBox vBox = new VBox(10, titleTextField, descriptionTextField, priceTextField, imageButton, imageView, applyButton);
        Scene scene = new Scene(vBox);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setScene(scene);
        stage.showAndWait();
        return film[0];
    }

    private static Film applyButtonClick(String s1, String s2, String s3, String d) {
        System.out.println(1);
        stage.close();
        if (s1.isBlank() || s2.isBlank() || s3.isBlank() || d.isBlank()) {
            return null;
        }
        try {
            Film film = new Film(s1, s2, s3 , Double.valueOf(d));
            return film;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String imageButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("stopklatka/images/filmlogo.png");
        File testFile = new File("src/stopklatka/images");
        fileChooser.setInitialDirectory(testFile);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png file", "*.png"));
        File f = fileChooser.showOpenDialog(null);
        if (f != null) {
            return "stopklatka/images/" + f.getName();
        }
        return null;
    }

    public static boolean confirmation(String message, String title, String yes, String no) {
        conf = false;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);
        Label label = new Label();
        label.setText(message);
        Button buttonYes = new Button(yes);
        buttonYes.setOnAction(e -> buttonYesClick());
        Button buttonNo = new Button(no);
        buttonNo.setOnAction(e -> buttonNoClick());
        HBox hBox = new HBox(20, buttonYes, buttonNo);
        VBox vBox = new VBox(20, label, hBox);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
        return conf;
    }

    private static void buttonNoClick() {
        stage.close();
        conf = false;
    }

    private static void buttonYesClick() {
        stage.close();
        conf = true;
    }

    public static Film editFilmField(Film f) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Film[] film = new Film[1];
        String[] image = new String[1];
        ImageView imageView = new ImageView(new Image(f.getImage()));
        Label titleLabel = new Label(f.getTitle());
        Label descriptionLabel = new Label(f.getDescription());
        Label priceLabel = new Label(f.getPrice().toString());
        TextField titleTF = new TextField();
        titleTF.setPromptText("New title");
        TextField descriptionTF = new TextField();
        descriptionTF.setPromptText("New description");
        TextField priceTF = new TextField();
        priceTF.setPromptText("New price");
        Button imageButton = new Button("Select image");
        imageButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                image[0] = imageButtonClick();
                if (image[0] == null) {
                    imageView.setImage(new Image(f.getImage()));
                } else {
                    imageView.setImage(new Image(image[0]));
                }
            }
        } );
        Button applyButton = new Button("Apply");
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String t1;
                String t2;
                String t3;
                String t4;
                t1 = titleTF.getText().isBlank() ? f.getTitle() : titleTF.getText();
                t2 = descriptionTF.getText().isBlank() ? f.getDescription() : descriptionTF.getText();
                t3 = image[0] == null ? f.getImage() : image[0];
                t4 = priceTF.getText().isBlank() ? f.getPrice().toString() : priceTF.getText();
                film[0] = applyButtonClick(t1, t2, t3, t4);
                if (film[0] != null) {
                    film[0].setId(f.getId());
                }
            }
        } );
        VBox vBox = new VBox(10, titleLabel, titleTF, descriptionLabel, descriptionTF, priceLabel, priceTF, imageButton, applyButton);
        HBox hBox = new HBox(10, imageView, vBox);
        Scene scene = new Scene(hBox);
        stage.centerOnScreen();
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setScene(scene);
        stage.showAndWait();
        return film[0];

    }
}
