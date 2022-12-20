package foodie.gj.foodie.model;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
;

public class HelloApplication extends Application {

    private static Response response;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws UnirestException, UnsupportedEncodingException {
        response = new Response();
        while(true)
        {
            System.out.println("======================================================");
            System.out.println("\nPlease input the city name:");
            Scanner sc = new Scanner(System.in);
            String city = sc.nextLine();
            if(city.equals("exit")) System.exit(1);
            else response.printWeather(city);
        }

    }

}