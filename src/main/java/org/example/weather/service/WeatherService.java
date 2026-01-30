package org.example.weather.service;

import org.example.weather.api.WeatherApiClient;
import org.example.weather.model.WeatherEntity;
import org.example.weather.model.WeatherResponse;
import org.example.weather.Repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private WeatherApiClient weatherApiClient;

    @Autowired
    private WeatherRepository weatherRepository;

    // List of cities in India for testing
    private static final List<String> CITIES_IN_INDIA = Arrays.asList(
            "Mumbai", "Noida", "Pune"
    );

    public void fetchAndSaveWeatherData() {
        for (String city : CITIES_IN_INDIA) {
            WeatherResponse response = weatherApiClient.getWeatherForCity(city);

            if (response != null) {
                // Extract data from the response
                double temperature = response.getMain().getTemp();
                int humidity = response.getMain().getHumidity();

                // Create a WeatherEntity object to save to DB
                WeatherEntity weatherEntity = new WeatherEntity();
                weatherEntity.setCity(city);
                weatherEntity.setTemperature(temperature);
                weatherEntity.setHumidity(humidity);
                weatherEntity.setLastUpdated(new Timestamp(System.currentTimeMillis())); // Set current time

                // Set initial alert state (you can adjust the alert threshold later)
                weatherEntity.setAlert(false);

                // Save to the database
                weatherRepository.save(weatherEntity);
            }
        }
    }
}
