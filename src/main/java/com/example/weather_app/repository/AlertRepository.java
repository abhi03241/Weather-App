package com.example.weather_app.repository;

import com.example.weather_app.model.Alert;
import com.example.weather_app.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByCity(String city);
}
