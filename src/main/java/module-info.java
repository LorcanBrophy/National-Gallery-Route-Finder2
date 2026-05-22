module com.nationalgalleryroutefinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.base;
    requires jmh.core;

    opens com.nationalgalleryroutefinder.controller to javafx.fxml;
    opens com.nationalgalleryroutefinder.main to javafx.fxml;

    exports com.nationalgalleryroutefinder.controller;
    exports com.nationalgalleryroutefinder.main;
}