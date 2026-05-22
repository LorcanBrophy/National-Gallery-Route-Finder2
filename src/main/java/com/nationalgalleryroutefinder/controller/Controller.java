package com.nationalgalleryroutefinder.controller;

import com.nationalgalleryroutefinder.graph.Graph;
import com.nationalgalleryroutefinder.main.Application;
import com.nationalgalleryroutefinder.model.Room;
import com.nationalgalleryroutefinder.util.CSVLoader;
import com.nationalgalleryroutefinder.util.FXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.InputStream;
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

    private WritableImage displayedImage;
    private Image blackAndWhiteImage;

    private Point2D startPoint;
    private Point2D endPoint;

    private Graph<Room> graph;

    // FXML fields must be initialized here, not the constructor, otherwise they will load too early.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setOnMouseClicked(this::onImageViewMouseClicked);

        // fire event when the mouse is clicked anywhere in the border pane
        borderPane.setOnMouseClicked(this::onBorderPaneMouseClicked);

        // fire event when the center pane is clicked (the one that contains the image)
        borderPane.getCenter().setOnMouseClicked(this::onBorderPaneCenterMouseClicked);

        configureImageViewSize();

        loadFloorPlanImage();
        loadBlackAndWhiteImage();

        createGraph();
    }

    // -------------------------------------------------------------------------
    //  FXML Methods
    // -------------------------------------------------------------------------
    @FXML
    private void showBlackAndWhiteImage() {
        Pane imageContainer = new Pane(new ImageView(blackAndWhiteImage));
        FXUtils.showPopupWindow("Gallery Floor Plan BW", imageContainer, blackAndWhiteImage.getWidth(), blackAndWhiteImage.getHeight(), false);
    }

    @FXML
    private void quitApplication() {
        System.exit(0);
    }

    // -------------------------------------------------------------------------
    // Event Handlers
    // -------------------------------------------------------------------------
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

    private void createGraph() {
        CSVLoader loader = new CSVLoader();

        try {
            graph = loader.loadGraph(getCSVFilePath("rooms.csv"), getCSVFilePath("exhibits.csv"), getCSVFilePath("edges.csv"));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCSVFilePath(String csvFileName) {
        return "src/main/resources/csv/" + csvFileName;
    }

    private void loadFloorPlanImage() {
        // resize the image to fit the center pane container
        Image floorPlanColorImage = loadImage("/images/gallery_floor_plan.png");

        // initialize the image view with the display image
        displayedImage = new WritableImage(floorPlanColorImage.getPixelReader(), (int) floorPlanColorImage.getWidth(), (int) floorPlanColorImage.getHeight());
        imageView.setImage(displayedImage);
    }

    private void loadBlackAndWhiteImage() {
        blackAndWhiteImage = loadImage("/images/gallery_floor_plan_bw.png");
    }

    private Image loadImage(String resourcePath) {
        InputStream imageStream = Application.class.getResourceAsStream(resourcePath);

        if (imageStream == null) {
            throw new IllegalStateException("Could not find image resource: " + resourcePath);
        }

        Image image = new Image(imageStream);

        if (image.isError()) {
            throw new IllegalStateException("Could not load image resource: " + resourcePath, image.getException());
        }

        return image;
    }

    private void configureImageViewSize() {
        if (borderPane.getCenter() instanceof Pane imageContainer) {
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            imageView.fitWidthProperty().bind(imageContainer.widthProperty());
            imageView.fitHeightProperty().bind(imageContainer.heightProperty());
        }
    }
}
