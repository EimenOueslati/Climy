package foodie.gj.foodie.model;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private String host;
    private String charSet;
    private String x_host;
    private String x_key;
    private Map<String, String> infoMAp;
    public Response(){
        host = "https://weatherapi-com.p.rapidapi.com/current.json";
        charSet = "UTF-8";
        x_host = "weatherapi-com.p.rapidapi.com";
        x_key = "89e1b2e058mshcb24ce92222c1f4p133c45jsn580e0f0c496c";
        infoMAp = new HashMap<>();
        initialize(infoMAp);
    }

    public boolean printWeather(String location) throws UnirestException, UnsupportedEncodingException {
        if(fillMap(location)) {
            ArrayList<String> keys = new ArrayList<>(infoMAp.keySet());
            ArrayList<String> values = new ArrayList<>(infoMAp.values());
            for(int i = 0; i < keys.size(); i++)
            {
                System.out.println(keys.get(i) + ": " + values.get(i));
            }
            return true;
        }
        return false;
    }

    private boolean fillMap(String location) throws UnirestException, UnsupportedEncodingException {
        String query = String.format("q=%s",
                URLEncoder.encode(location, charSet));
        HttpResponse<JsonNode> response = Unirest.get(host + "?" + query)
                .header("x-rapidapi-host", x_host)
                .header("x-rapidapi-key", x_key)
                .asJson();
        if(response.getStatus() != 200){return false;}
        System.out.println(response.getBody().getObject().getJSONObject("location").getString("name"));

        infoMAp.replace("Country", response.getBody().getObject().getJSONObject("location").getString("country"));
        infoMAp.replace("City", response.getBody().getObject().getJSONObject("location").getString("name"));
        infoMAp.replace("Local time", response.getBody().getObject().getJSONObject("location").getString("localtime"));
        infoMAp.replace("Is Day", ((response.getBody().getObject().getJSONObject("current").getInt("is_day")) == 1) ? "yes" : "no");
        infoMAp.replace("State", response.getBody().getObject().getJSONObject("current").getJSONObject("condition").getString("text"));
        infoMAp.replace("Current temperature", response.getBody().getObject().getJSONObject("current").getInt("temp_c") + " °C");
        infoMAp.replace("Feels like", response.getBody().getObject().getJSONObject("current").getFloat("feelslike_c") + " °C");                    //  getInt("feelslike_c") + " °C");
        infoMAp.replace("Wind speed", response.getBody().getObject().getJSONObject("current").getInt("wind_kph") + " kph");
        infoMAp.replace("Precipitation", response.getBody().getObject().getJSONObject("current").getInt("precip_mm") + " mm");
        return true;
    }

    private void initialize(Map x)
    {
        infoMAp.put("Country", "");
        infoMAp.put("City", "");
        infoMAp.put("Local time", "");
        infoMAp.put("Is Day", "");
        infoMAp.put("State", "");
        infoMAp.put("Current temperature", "");
        infoMAp.put("Feels like", "");
        infoMAp.put("Wind speed", "");
        infoMAp.put("Precipitation", "");
    }

}
