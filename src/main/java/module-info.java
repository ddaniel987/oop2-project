module com.example.oop2proekt2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.oop2proekt2 to javafx.fxml;
    exports com.example.oop2proekt2;
}