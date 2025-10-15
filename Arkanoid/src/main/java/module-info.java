module org.example.arkanoid {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.example.arkanoid.main to javafx.fxml;
    exports org.example.arkanoid.main;

    opens org.example.arkanoid.object to javafx.fxml;
    exports org.example.arkanoid.object;

    opens org.example.arkanoid.control to javafx.fxml;
    exports org.example.arkanoid.control;
}