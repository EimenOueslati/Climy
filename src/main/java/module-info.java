module foodie.gj.foodie {
    requires javafx.controls;
    requires javafx.fxml;
    requires unirest.java;
    requires org.json;


    opens foodie.gj.foodie to javafx.fxml;
    exports foodie.gj.foodie.control;
    opens foodie.gj.foodie.control to javafx.fxml;
    exports foodie.gj.foodie.model;
    opens foodie.gj.foodie.model to javafx.fxml;
    exports foodie.gj.foodie;
}