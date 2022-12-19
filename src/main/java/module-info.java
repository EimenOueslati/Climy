module foodie.gj.foodie {
    requires javafx.controls;
    requires javafx.fxml;


    opens foodie.gj.foodie to javafx.fxml;
    exports foodie.gj.foodie;
}