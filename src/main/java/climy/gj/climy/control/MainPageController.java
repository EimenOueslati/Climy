package climy.gj.climy.control;

import climy.gj.climy.Main;
import climy.gj.climy.model.Response;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
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
            Path path = Paths.get("src/main/resources/climy/gj/climy/key.txt");
            Alert alert = new Alert(Alert.AlertType.ERROR, "No API key was found!");
            if (!Files.exists(path)) {
                // Create the file if it doesn't exist
                alert.showAndWait();
                return;
            }
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "To use the app, you need an API key for the weather api. you can get a free key by signing up "+
                " at https://rapidapi.com and subscribing to the weatherapi at https://rapidapi.com/weatherapi/api/weatherapi-com/ "+
                " After that you can just enter a city name in the text field " +
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


    public void OnEnterKeyClicked(ActionEvent actionEvent) throws IOException, UnirestException {
        TextInputDialog keyInput = new TextInputDialog();
        keyInput.setTitle("API key input");
        keyInput.setHeaderText("Please enter a valid API key for weatherapi");
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid API key");
        Optional<String> key = keyInput.showAndWait();
        if(key.isPresent())
        {
            while(key.isPresent() && !Response.isValidKey(key.get()))
            {
                alert.showAndWait();
                key = keyInput.showAndWait();
            }
            if(!key.isPresent()) return;
            Path path = Paths.get("src/main/resources/climy/gj/climy/key.txt");

            // Check if the file already exists
            if (!Files.exists(path)) {
                // Create the file if it doesn't exist
                Files.createFile(path);
            }else{
                Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING).close();
            }

            Alert success = new Alert(Alert.AlertType.INFORMATION, "The API key was successfully registered");
            success.setTitle("Success");
            success.setHeaderText("Success");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()))) {
                writer.write(key.get());
                success.showAndWait();
            }
        }

    }
}