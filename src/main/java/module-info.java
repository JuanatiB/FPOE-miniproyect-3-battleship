module com.example.miniproyect3fpoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miniproyect3fpoe to javafx.fxml;
    exports com.example.miniproyect3fpoe;
}