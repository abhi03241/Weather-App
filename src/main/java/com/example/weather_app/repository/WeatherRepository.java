package com.example.weather_app.repository;

import com.example.weather_app.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findByCityAndDate(String city, LocalDate date);

    List<WeatherData> findByCity(String city);


    @Query("SELECT w FROM WeatherData w WHERE w.city = :city ORDER BY w.timestamp DESC")
    List<WeatherData> findTopByCityOrderByTimestampDesc(@Param("city") String city);

}
