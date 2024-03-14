package climy.gj.climy.model;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Response {
    private final String host;
    private final String charSet;
    private final String x_host;
    private final String x_key;
    private int statusCode;
    private final HashMap<String, String> infoMAp;
    public Response() throws IOException {
        host = "https://weatherapi-com.p.rapidapi.com/current.json";
        charSet = "UTF-8";
        x_host = "weatherapi-com.p.rapidapi.com";
        x_key = readKey();
        infoMAp = new HashMap<>();
        initialize(infoMAp);
    }

    public int getStatusCode(){return statusCode;}

    public Map<String, String> getInfoMap(String location) throws UnirestException, UnsupportedEncodingException {
        fillMap(location);
        return infoMAp;
    }

    private void fillMap(String location) throws UnirestException, UnsupportedEncodingException {
        String query = String.format("q=%s",
                URLEncoder.encode(location, charSet));
        HttpResponse<JsonNode> response = Unirest.get(host + "?" + query)
                .header("x-rapidapi-host", x_host)
                .header("x-rapidapi-key", x_key)
                .asJson();
        statusCode = response.getStatus();
        if(statusCode != 200){return;}
        infoMAp.replace("Country", response.getBody().getObject().getJSONObject("location").getString("country"));
        infoMAp.replace("City", response.getBody().getObject().getJSONObject("location").getString("name"));
        infoMAp.replace("Local time", response.getBody().getObject().getJSONObject("location").getString("localtime"));
        infoMAp.replace("Is Day", ((response.getBody().getObject().getJSONObject("current").getInt("is_day")) == 1) ? "yes" : "no");
        infoMAp.replace("State", response.getBody().getObject().getJSONObject("current").getJSONObject("condition").getString("text"));
        infoMAp.replace("Current temperature", response.getBody().getObject().getJSONObject("current").getInt("temp_c") + " °C");
        infoMAp.replace("Feels like", response.getBody().getObject().getJSONObject("current").getFloat("feelslike_c") + " °C");
        infoMAp.replace("Wind speed", response.getBody().getObject().getJSONObject("current").getInt("wind_kph") + " kph");
        infoMAp.replace("Precipitation", response.getBody().getObject().getJSONObject("current").getInt("precip_mm") + " mm");
    }

    private void initialize(HashMap<String, String> x)
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

    public Map<String, String> getEmptyInfoMap() {
        return infoMAp;
    }

    private String readKey() throws IOException {
        Path path = Paths.get("src/main/resources/climy/gj/climy/key.txt");
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
            line = reader.readLine();
            return line;
        }
    }

    public static boolean  isValidKey(String key) throws UnsupportedEncodingException, UnirestException {
        String query = String.format("q=%s",
                URLEncoder.encode("oslo", "UTF-8"));
        HttpResponse<JsonNode> response = Unirest.get("https://weatherapi-com.p.rapidapi.com/current.json" + "?" + query)
                .header("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
                .header("x-rapidapi-key", key)
                .asJson();
        int statusCode = response.getStatus();
        return statusCode == 200;
    }
}
