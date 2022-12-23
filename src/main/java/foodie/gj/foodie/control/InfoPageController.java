package foodie.gj.foodie.control;

import com.mashape.unirest.http.exceptions.UnirestException;
import foodie.gj.foodie.Main;
import foodie.gj.foodie.model.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    public Label cityName;
    public TextField cityNameTF;

    private Response response;

    private Map<String, String> infoMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initiatePage(String text) throws UnirestException, UnsupportedEncodingException {
        response = new Response();
        infoMap = response.getInfoMap(text);
        fillInfo(text);
    }

    private void fillInfo(String text) throws UnirestException, UnsupportedEncodingException {
        if(response.getStatusCode() == 401)
        {
            new Alert(Alert.AlertType.WARNING, "No matching location found. Please refrain from using non-ASCII characters." +
                    "Please replace those characters with:" +
                    "å => aa" +
                    "ø => o" +
                    "æ => ae");
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
}
