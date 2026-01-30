package org.example.weather.controller;

import org.example.weather.model.WeatherEntity;
import org.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    // Get all favorite cities
    @GetMapping("/favorites")
    public List<WeatherEntity> getFavoriteCities() {
        return weatherService.getFavoriteCities();
    }

    // Add a new favorite city
//    @PostMapping("/favorites")
//    public ResponseEntity<WeatherEntity> addFavoriteCity(@RequestBody WeatherEntity weatherEntity) {
//        WeatherEntity savedCity = weatherService.saveFavoriteCity(weatherEntity);
//        return ResponseEntity.ok(savedCity);
//    }

    @PostMapping("/favorites")
    public ResponseEntity<WeatherEntity> addFavoriteCity(@RequestBody String city) {
        // Call the service to fetch and save the city weather data
        WeatherEntity savedCity = weatherService.saveFavoriteCity(city);

        if (savedCity != null) {
            return ResponseEntity.ok(savedCity);
        } else {
            return ResponseEntity.badRequest().body(null); // Return 400 if city data couldn't be fetched or saved
        }
    }


    // Get favorite city by ID
    @GetMapping("/favorites/{id}")
    public ResponseEntity<WeatherEntity> getFavoriteCityById(@PathVariable Long id) {
        Optional<WeatherEntity> city = weatherService.getFavoriteCityById(id);
        return city.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a favorite city by ID
    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<Void> deleteFavoriteCity(@PathVariable Long id) {
        if (weatherService.deleteFavoriteCity(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Check alerts (with the set temperature thresholds)
    @PutMapping("/favorites/alert/{id}")
    public ResponseEntity<WeatherEntity> setAlertForCity(@PathVariable Long id) {
        WeatherEntity updatedCity = weatherService.checkAndSetAlert(id);
        return ResponseEntity.ok(updatedCity);
    }
}
