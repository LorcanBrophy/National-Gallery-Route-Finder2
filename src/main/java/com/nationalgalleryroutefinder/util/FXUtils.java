package com.nationalgalleryroutefinder.util;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class FXUtils {
    /**
     * @param message The message to display in the alert.
     * @param alertType The type of alert to display.
     */
    public static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(StringUtils.capitalize(alertType.toString()));
        alert.setHeaderText(message);
        alert.show();
    }

    /**
     * @param message The message to display in the alert.
     * @return The result of the alert.
     */
    public static ButtonType showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(message);

        // wait for the user to click on a button
        alert.showAndWait();

        return alert.getResult();
    }

    /**
     * @param title The title of the popup window.
     * @param parent The parent node of the popup window.
     * @param width The width of the popup window.
     * @param height The height of the popup window.
     * @param isResizable Whether the popup window is resizable.
     * @return The popup window stage.
     */
    public static Stage showPopupWindow(String title, Parent parent, double width, double height, boolean isResizable) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(isResizable);

        Scene scene = new Scene(parent, width, height);
        stage.setScene(scene);
        stage.show();

        return stage;
    }

    /**
     * @param title The title of the input dialog.
     * @param headerText The header text of the input dialog.
     * @param contentText The content text of the input dialog.
     * @param defaultValue The default value of the input dialog.
     * @return The result of the input dialog.
     */
    public static String showInputDialog(String title, String headerText, String contentText, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);

        // wait for the user to enter a value and click on the OK button
        dialog.showAndWait();

        return dialog.getResult();
    }

    /**
     * @param node The node to add the hover scale animation to.
     * @param scaleFactor The scale factor to apply when hovering over the node.
     * @param duration The duration of the hover animation.
     */
    public static void addHoverScaleAnimation(Node node, double scaleFactor, Duration duration) {
        ScaleTransition scaleIn = new ScaleTransition(duration, node);
        scaleIn.setToX(scaleFactor);
        scaleIn.setToY(scaleFactor);

        ScaleTransition scaleOut = new ScaleTransition(duration, node);
        scaleOut.setToX(node.getScaleX());
        scaleOut.setToY(node.getScaleY());

        node.setOnMouseEntered(_ -> {
            scaleOut.stop();
            scaleIn.playFromStart();
        });

        node.setOnMouseExited(_ -> {
            scaleIn.stop();
            scaleOut.playFromStart();
        });
    }
}
