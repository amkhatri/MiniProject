import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherForecast {
    public static void main(String[] args) {
        try {
            double latitude = 39.1651; // Example latitude for Bloomington, IN
            double longitude = -86.5264; // Example longitude for Bloomington, IN
            String hourly = "temperature_2m";
            String temperatureUnit = "fahrenheit";
            String timezone = "EST";

            String urlString = "https://api.open-meteo.com/v1/forecast?";
            String secondURL = String.format("latitude=%f&longitude=%f&hourly=%s&temperature_unit=%s&timezone=%s",
                    latitude,longitude, hourly, temperatureUnit, timezone);

            String full = urlString + secondURL;

            URL url = new URL(full);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    JsonElement jsonElement = JsonParser.parseString(response.toString());
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonObject hourlyObject = jsonObject.getAsJsonObject("hourly");
                    JsonArray time_stamp = hourlyObject.getAsJsonArray("time");
                    JsonArray temp_stamp = hourlyObject.getAsJsonArray("temperature_2m");

                    System.out.println("Bloomington 7-Day Forecast in Fahrenheit:");


                    for (int i = 0; i < time_stamp.size(); i++) { // 56 hours (7 days * 8 intervals per day)
                        String date = time_stamp.get(i).getAsString();
                        System.out.println("Forecast for " + date + ":");
                        double temp_2 = temp_stamp.get(i).getAsDouble();
                        System.out.println("Temperature: " + temp_2 + "F");

                    }
                }
            } else {
                throw new IOException("HTTP request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

