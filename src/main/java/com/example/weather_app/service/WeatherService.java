package com.example.weather_app.service;

import com.example.weather_app.model.WeatherData;
import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.repository.WeatherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;


@Service
public class WeatherService {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private WeatherRepository weatherRepository;

    public WeatherResponse fetchWeatherData(String city) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appId=" + apiKey + "&units=metric";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
    @Transactional
    public void saveWeatherData(String city, LocalDate date, double averageTemp, double maxTemp, double minTemp, String dominantCondition) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setDate(date);
        weatherData.setAverageTemp(averageTemp);
        weatherData.setMaxTemp(maxTemp);
        weatherData.setMinTemp(minTemp);
        weatherData.setDominantCondition(dominantCondition);
        weatherRepository.save(weatherData);
    }

    public List<WeatherData> getHistoricalWeatherData(String city) {
        return weatherRepository.findByCity(city);
    }

    public List<WeatherData> getLatestWeatherData(String city) {
        return weatherRepository.findTopByCityOrderByTimestampDesc(city);
    }

}
