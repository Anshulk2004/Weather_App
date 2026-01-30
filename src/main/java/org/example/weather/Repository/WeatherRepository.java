package org.example.weather.Repository;

import org.example.weather.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    // JpaRepository already provides the basic CRUD operations
}
