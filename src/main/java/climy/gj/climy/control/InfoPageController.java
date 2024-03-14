package climy.gj.climy.control;

import com.mashape.unirest.http.exceptions.UnirestException;
import climy.gj.climy.Main;
import climy.gj.climy.model.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class InfoPageController implements Initializable {
    @FXML public Label country;
    @FXML public Label localTime;
    @FXML public Label isDay;
    @FXML public Label state;
    @FXML public Label temp;
    @FXML public Label feelsLike;
    @FXML public Label perc;
    @FXML public Label windSpeed;
    @FXML public Label cityName;
    @FXML public TextField cityNameTF;
    @FXML public ImageView searchlogo;

    private Response response;

    private Map<String, String> infoMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void initiatePage(String text) throws UnirestException, IOException {
        response = new Response();
        infoMap = response.getEmptyInfoMap();
        infoMap = response.getInfoMap(text);
        fillInfo(text);
    }

    private void fillInfo(String text) throws UnirestException, UnsupportedEncodingException {
        response.getInfoMap(text);
        if(response.getStatusCode() == 400)
        {
            new Alert(Alert.AlertType.WARNING, """
                    No matching location found. Please refrain from using non-ASCII characters.Please replace those characters with:
                    å => aa
                    ø => o
                    æ => ae""").show();
        } else if (response.getStatusCode() == 200) {
            infoMap = response.getInfoMap(text);
            cityName.setText(text.toUpperCase());
            country.setText(infoMap.get("Country"));
            localTime.setText(infoMap.get("Local time"));
            isDay.setText(infoMap.get("Is Day"));
            state.setText(infoMap.get("State"));
            temp.setText(infoMap.get("Current temperature"));
            feelsLike.setText(infoMap.get("Feels like"));
            windSpeed.setText(infoMap.get("Wind speed"));
            perc.setText(infoMap.get("Precipitation"));
        }else {
            new Alert(Alert.AlertType.ERROR, "An unexpected error has occurred please restart the application").show();
        }
    }

    public void onSearchClicked(ActionEvent actionEvent) throws UnirestException, UnsupportedEncodingException {
        if(!MainPageController.containsNumbersOrSymbol(cityNameTF.getText()))
        {
            if(!cityNameTF.getText().equals(cityName.getText()))
            {
                fillInfo(cityNameTF.getText());
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid input. City name cannot be blank, contain numbers or special characters").show();
        }
    }

    public void onExitClicked(ActionEvent actionEvent) {
        Main.exitConfirmation((Stage) country.getScene().getWindow());
    }

    public void onBoutMenuClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This weather application was developed by Eimen Ouesalati " +
                " The app was developed using javafx. To report bugs or suggest new feature " +
                "please contact us at aymanoueslati22@gmail.com");
        alert.show();

    }

    public void onHelpMenuClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "To use the app, you need an API key for the weather api. you can get a free key by signing up "+
                " at https://rapidapi.com and subscribing to the weatherapi at https://rapidapi.com/weatherapi/api/weatherapi-com/ "+
                " After that you can just enter a city name in the text field " +
                "and the click in the search button. You will be taken to a page with the weather information of the city " +
                "you entered. There will be another text field where you can preform further searches");
        alert.show();
    }
}
