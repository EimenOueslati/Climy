package foodie.gj.foodie;

import com.mashape.unirest.http.exceptions.UnirestException;
import foodie.gj.foodie.model.Response;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 550);
        stage.setTitle("Climy");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(evt -> {
            exitConfirmation(stage);
            evt.consume();
        });
    }

    public static void exitConfirmation(Stage stage) {
        // allow user to decide between yes and no
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you really want to close this application?",
                ButtonType.YES, ButtonType.NO);

        // clicking X also means no
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

        if (!ButtonType.NO.equals(result)) {
            stage.close();
        }
    }


    public static void main(String[] args) {launch();}

}