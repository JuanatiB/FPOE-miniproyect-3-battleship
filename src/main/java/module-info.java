module com.example.miniproyect3fpoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.miniproyect3fpoe.controller to javafx.fxml;
    exports com.example.miniproyect3fpoe;
    exports com.example.miniproyect3fpoe.controller;
}