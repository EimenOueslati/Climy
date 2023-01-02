package foodie.gj.foodie.control;

import com.mashape.unirest.http.exceptions.UnirestException;
import foodie.gj.foodie.Main;
import foodie.gj.foodie.model.Response;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    private TextField cityNameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void OnAboutClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This weather application was developed by Eimen Ouesalati " +
                "and Soufiane Orkia. The app was developed with java and css. To report bugs or suggest new feature " +
                "please contact us at aymanoueslati22@gmail.com");
        alert.show();
    }

    public void OnEnterClicked(ActionEvent actionEvent) throws IOException, UnirestException {
        String text = cityNameField.getText();
        if(containsNumbersOrSymbol(text))
        {
            new Alert(Alert.AlertType.ERROR, "Invalid input. City name cannot be blank, contain numbers or special characters").show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("info-page.fxml"));
            Scene scene = new Scene(loader.load(), 750, 520);
            Stage stage = (Stage) cityNameField.getScene().getWindow();
            InfoPageController controller = loader.getController();
            controller.initiatePage(text);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void onHelpClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "To use the app, just enter a city name in the text field " +
                "and the click in the search button. You will be taken to a page with the weather information of the city " +
                "you entered. There will be another text field where you can preform further searches");
        alert.show();
    }

    public void onExitClicked(ActionEvent actionEvent) {
        Main.exitConfirmation((Stage) cityNameField.getScene().getWindow());
    }

    public static boolean containsNumbersOrSymbol(String text)
    {
        return containsNumbers(text) || text.isBlank();
    }

    private static boolean containsNumbers(String text) {
        for (char c : text.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                return true;
            }
        }
        return false;
    }


}