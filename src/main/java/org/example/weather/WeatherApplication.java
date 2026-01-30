package org.example.weather;

import org.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherApplication implements CommandLineRunner {

    @Autowired
    private WeatherService weatherService;

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

//    @Override
//    public void run(String... args) throws Exception {
//        weatherService.fetchAndSaveWeatherData();
//    }
}
