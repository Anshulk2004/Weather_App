package org.example.weather.service;

import org.example.weather.model.WeatherEntity;
import org.example.weather.Repository.WeatherRepository;
import org.example.weather.api.WeatherApiClient;
import org.example.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private WeatherApiClient weatherApiClient;

    @Autowired
    private WeatherRepository weatherRepository;

    private static final double MIN_TEMP = 5.0;
    private static final double MAX_TEMP = 28.0;

    // Fetch and save a new city weather data (for adding city manually)
//    public WeatherEntity saveFavoriteCity(WeatherEntity weatherEntity) {
//        // Fetch weather for the city from the API
//        WeatherResponse response = weatherApiClient.getWeatherForCity(weatherEntity.getCity());
//
//        if (response != null) {
//            // Extract data from response
//            double temperature = response.getMain().getTemp();
//            int humidity = response.getMain().getHumidity();
//
//            // Update the weather entity with temperature and humidity
//            weatherEntity.setTemperature(temperature);
//            weatherEntity.setHumidity(humidity);
//            weatherEntity.setLastUpdated(new Timestamp(System.currentTimeMillis()));
//
//            // Set alert based on the temperature thresholds
//            weatherEntity = setAlertBasedOnTemperature(weatherEntity);
//
//            // Save it to the database
//            return weatherRepository.save(weatherEntity);
//        }
//        return null;
//    }
    public WeatherEntity saveFavoriteCity(String city) {
        // Fetch weather data from the API
        WeatherResponse response = weatherApiClient.getWeatherForCity(city);

        if (response != null) {
            // Extract data from the response
            double temperature = response.getMain().getTemp();
            int humidity = response.getMain().getHumidity();

            // Create a new WeatherEntity to save to the DB
            WeatherEntity weatherEntity = new WeatherEntity();
            weatherEntity.setCity(city);
            weatherEntity.setTemperature(temperature);
            weatherEntity.setHumidity(humidity);
            weatherEntity.setLastUpdated(new Timestamp(System.currentTimeMillis()));

            // Set initial alert status based on temperature thresholds
            weatherEntity = setAlertBasedOnTemperature(weatherEntity);

            // Save to the database
            return weatherRepository.save(weatherEntity);
        }

        return null; // Return null if the API call fails or city data can't be fetched
    }


    // Get all favorite cities from the database
    public List<WeatherEntity> getFavoriteCities() {
        return weatherRepository.findAll();
    }

    // Get a specific city by ID
    public Optional<WeatherEntity> getFavoriteCityById(Long id) {
        return weatherRepository.findById(id);
    }

    // Delete a city by ID
    public boolean deleteFavoriteCity(Long id) {
        if (weatherRepository.existsById(id)) {
            weatherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Check and set alerts for a city based on the temperature thresholds
    public WeatherEntity checkAndSetAlert(Long id) {
        Optional<WeatherEntity> cityOpt = weatherRepository.findById(id);
        if (cityOpt.isPresent()) {
            WeatherEntity city = cityOpt.get();
            // Fetch current weather for the city
            WeatherResponse response = weatherApiClient.getWeatherForCity(city.getCity());
            if (response != null) {
                double temperature = response.getMain().getTemp();
                city.setTemperature(temperature);
                city = setAlertBasedOnTemperature(city);
                // Update the city with new temperature and alert status
                city.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                return weatherRepository.save(city);
            }
        }
        return null;
    }

    // Helper method to set alerts based on temperature
    private WeatherEntity setAlertBasedOnTemperature(WeatherEntity city) {
        if (city.getTemperature() > MAX_TEMP || city.getTemperature() < MIN_TEMP) {
            city.setAlert(true);
        } else {
            city.setAlert(false);
        }
        return city;
    }
}
