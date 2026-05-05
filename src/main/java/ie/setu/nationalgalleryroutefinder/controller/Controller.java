package ie.setu.nationalgalleryroutefinder.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    BorderPane borderPane;
    @FXML
    private Label statusLabel;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ImageView imageView;

    private File imageFile;
    private WritableImage displayedImage;
    private double containerWidth;
    private double containerHeight;

    private final String STATUS_NO_IMAGE = "No image loaded.";

    // -------------------------------------------------------------------------
    //  FXML Methods
    // -------------------------------------------------------------------------
    @FXML
    private void loadImageFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter(
                        "Image Files (*.jpg, *.jpeg, *.png",
                        "*.jpg",
                        "*.jpeg",
                        "*.png"
                );

        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Open Image");

        File file = fileChooser.showOpenDialog(borderPane.getScene().getWindow());

        // check if the file is valid
        if (file == null || !file.exists() || !file.isFile()) {
            return;
        }

        imageFile = file;
        String imageURL = String.valueOf(imageFile.toURI());

        // set the image dimensions based on the image center pane container
        Pane imageContainer = (Pane) borderPane.getCenter();
        containerWidth = (int) imageContainer.getWidth();
        containerHeight = (int) imageContainer.getHeight();

        // resize the original image to fit the center pane container
        Image originalColorImage = new Image(imageURL, containerWidth, containerHeight, false, true);

        // initialize the image view with the display image
        displayedImage = new WritableImage(originalColorImage.getPixelReader(), (int) containerWidth, (int) containerHeight);
        imageView.setImage(displayedImage);

        setStatusBar("File: " + imageFile.getName(), true);
    }

    @FXML
    private void quitApplication() {
        System.exit(0);
    }

    // -------------------------------------------------------------------------
    // Event Handlers
    // -------------------------------------------------------------------------
    protected void onImageChanged(ObservableValue<?> value, Image oldImage, Image newImage) {

    }

    protected void onImageViewMouseClicked(MouseEvent event) {

    }

    protected void onBorderPaneCenterMouseClicked(MouseEvent event) {

    }

    protected void onBorderPaneMouseClicked(MouseEvent event) {

    }

    // -------------------------------------------------------------------------
    // Utility Methods
    // -------------------------------------------------------------------------
    public void setStatusBar(String message, boolean visible) {
        if (statusLabel == null) return;

        if (message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be blank");
        }

        statusLabel.setText(message);
        statusLabel.setVisible(visible);
    }

    public boolean isImageLoaded() {
        return imageView.getImage() != null || imageFile != null;
    }

    // FXML fields must be initialized here, not the constructor, otherwise they will load too early.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.imageProperty().addListener(this::onImageChanged);
        imageView.setOnMouseClicked(this::onImageViewMouseClicked);

        // fire event when the mouse is clicked anywhere in the border pane
        borderPane.setOnMouseClicked(this::onBorderPaneMouseClicked);

        // fire event when the center pane is clicked (the one that contains the image)
        borderPane.getCenter().setOnMouseClicked(this::onBorderPaneCenterMouseClicked);

        if (!isImageLoaded()) {
            setStatusBar(STATUS_NO_IMAGE, true);
        }
    }
}
