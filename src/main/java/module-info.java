module org.example.nationalgalleryroutefinder {
    requires javafx.controls;
    requires javafx.fxml;


    exports ie.setu.nationalgalleryroutefinder.controller;
    opens ie.setu.nationalgalleryroutefinder.controller to javafx.fxml;
    exports ie.setu.nationalgalleryroutefinder.main;
    opens ie.setu.nationalgalleryroutefinder.main to javafx.fxml;
}