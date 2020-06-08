module org.adrien {

    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;
    requires org.junit.jupiter.api;
    requires junit;

    opens org.adrien.model to javafx.base;
    opens org.adrien.view to javafx.fxml;
    opens org.adrien.controller to javafx.base,javafx.fxml;
    opens org.adrien to javafx.fxml;

    exports org.adrien;
    exports org.adrien.controller;
    exports test.org.adrien.model to junit;
}

