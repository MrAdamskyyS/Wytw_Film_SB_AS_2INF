module com.example.wytwornia {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wytwornia to javafx.fxml;
    exports com.example.wytwornia;
}