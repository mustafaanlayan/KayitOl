module com.example.kayitol {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kayitol to javafx.fxml;
    exports com.example.kayitol;
}