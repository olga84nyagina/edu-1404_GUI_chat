module com.example.chat_gui_1404 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chat_gui_1404 to javafx.fxml;
    exports com.example.chat_gui_1404;
}