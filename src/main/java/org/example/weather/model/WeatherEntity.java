package org.example.weather.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "favorite_cities")
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment SQL
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column
    private double temperature;

    @Column
    private boolean alert;

    @Column
    private Timestamp lastUpdated;

    @Column
    private int humidity; // Added humidity field

    public WeatherEntity(Long id, String city, double temperature, boolean alert, Timestamp lastUpdated, int humidity) {
        this.id = id;
        this.city = city;
        this.temperature = temperature;
        this.alert = alert;
        this.lastUpdated = lastUpdated;
        this.humidity = humidity;
    }

    public WeatherEntity() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
