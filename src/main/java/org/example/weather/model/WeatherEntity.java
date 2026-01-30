package org.example.weather.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
@Entity
@Table(name="favorite_cities")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment sql thing
    public Long id;
    @Column(nullable=false)
    public String city;
    @Column
    public double temperature;
    @Column
    public boolean alert;
    @Column
    public Timestamp last_updated;

    public WeatherEntity(long id, String city, boolean alert, Timestamp last_updated, double temperature) {
        this.id = id;
        this.city = city;
        this.alert = alert;
        this.last_updated = last_updated;
        this.temperature=temperature;

    }

    public WeatherEntity() {
    }

    public String getCity() {
        return city;
    }
    public void setCityAsFavorite(String city) {
        this.city = city;}

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setAlert(boolean alert) {
        this.alert=alert;
    }
    public boolean getAlert() {
        return alert;
    }
}

