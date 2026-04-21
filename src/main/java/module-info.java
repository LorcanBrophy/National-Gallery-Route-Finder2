module ie.setu.nationalgalleryroutefinder {
    requires javafx.controls;
    requires javafx.fxml;

    opens ie.setu.nationalgalleryroutefinder.controller to javafx.fxml;
    opens ie.setu.nationalgalleryroutefinder.main to javafx.fxml;

    exports ie.setu.nationalgalleryroutefinder.controller;
    exports ie.setu.nationalgalleryroutefinder.main;
}