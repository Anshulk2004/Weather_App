package org.example.weather.controller;

import org.example.weather.api.WeatherApiClient;
import org.example.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private WeatherApiClient weatherApiClient;

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city) {

        WeatherResponse response = weatherApiClient.getWeatherForCity(city);

        if (response != null) {

//            String description = response.getWeather()[0].getDescription();
            double temperature = response.getMain().getTemp();
            int humidity = response.getMain().getHumidity();
            return String.format("Temperature: %.2f°C\nHumidity: %d%%",
                     temperature, humidity);
        }

        return "Unable to fetch weather data for " + city;
    }
}
