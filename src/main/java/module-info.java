module climy.gj.climy {
    requires javafx.controls;
    requires javafx.fxml;
    requires unirest.java;
    requires org.json;


    opens climy.gj.climy to javafx.fxml;
    exports climy.gj.climy.control;
    opens climy.gj.climy.control to javafx.fxml;
    exports climy.gj.climy.model;
    opens climy.gj.climy.model to javafx.fxml;
    exports climy.gj.climy;
}