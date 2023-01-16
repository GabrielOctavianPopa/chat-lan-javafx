module com.example.chatlanjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.chatlanjavafx;
    opens com.example.chatlanjavafx to javafx.fxml;
}