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
            String hourly = "temperature_2m,time";
            String temperatureUnit = "fahrenheit";
            String timezone = "EST";

            String urlString = "https://api.open-meteo.com/v1/forecast?" +
                    "latitude=" + latitude +
                    "&longitude=" + longitude +
                    "&hourly=" + hourly +
                    "&temperature_unit=" + temperatureUnit +
                    "&timezone=" + timezone;

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
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

                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    for (int i = 0; i < 56; i += 8) { // 56 hours (7 days * 8 intervals per day)
                        System.out.println("Forecast for " + currentDateTime.format(formatter) + ":");

                        for (int j = i; j < i + 8; j++) {
                            LocalDateTime forecastTime = LocalDateTime.parse(time_stamp.get(j).getAsString());
                            double temperature = temp_stamp.get(j).getAsDouble();
                            System.out.printf("%02d:00: %.1f°F%n", forecastTime.getHour(), temperature);
                        }

                        currentDateTime = currentDateTime.plusDays(1);
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

