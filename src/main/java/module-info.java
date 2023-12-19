module ensisa.connectfour {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.bootstrapicons;

    opens ensisa.connectfour to javafx.fxml;
    exports ensisa.connectfour;
    exports ensisa.connectfour.model;
    opens ensisa.connectfour.model to javafx.fxml;
}