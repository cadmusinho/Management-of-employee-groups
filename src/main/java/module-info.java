module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    exports org.example.demo.controller to javafx.fxml;
    opens org.example.demo.controller to javafx.fxml;
    exports org.example.demo;

}