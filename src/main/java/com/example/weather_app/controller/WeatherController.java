package com.example.weather_app.controller;

import com.example.weather_app.model.Alert;
import com.example.weather_app.model.WeatherData;
import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.repository.AlertRepository;
import com.example.weather_app.service.AlertService;
import com.example.weather_app.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WeatherController {
    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private AlertService alertService;
    @Autowired
    private AlertRepository alertRepository;

    @GetMapping(path = {"/", "home", "welcome", "index" })
    public String getIndex(){
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model){
        if (city == null || city.trim().isEmpty() || city.trim().length() < 2) {
            model.addAttribute("error", "Please enter a valid city name (at least 2 characters)");
            return "weather";
        }
        city = city.trim();
        String url="http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appId=" + apiKey + "&units=metric";
        RestTemplate restTemplate= new RestTemplate();
        try {
            WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
            if (weatherResponse != null && weatherResponse.getName() != null) {
                double temperature = weatherResponse.getMain().getTemp();
                String dominantCondition = weatherResponse.getWeather().get(0).getDescription();
                LocalDate currentDate = LocalDate.now();

                model.addAttribute("city", weatherResponse.getName());
                model.addAttribute("country", weatherResponse.getSys().getCountry());
                model.addAttribute("weatherDescription", weatherResponse.getWeather().get(0).getDescription());
                model.addAttribute("temperature", weatherResponse.getMain().getTemp());
                model.addAttribute("humidity", weatherResponse.getMain().getHumidity());
                model.addAttribute("windSpeed", weatherResponse.getWind().getSpeed());
                String weatherIcon = "wi wi-owm-" + weatherResponse.getWeather().get(0).getId();
                model.addAttribute("weatherIcon", weatherIcon);

                weatherService.saveWeatherData(
                        weatherResponse.getName(),
                        currentDate,
                        temperature,
                        temperature,
                        temperature,
                        dominantCondition
                );
            } else {
                model.addAttribute("error", "Could not fetch weather data. Please check the city name.");
            }
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", "City '" + city + "' not found. Please check the spelling and try again.");
        } catch (Exception e) {
            model.addAttribute("error", "Unable to fetch weather data. Please try again later.");
        }
        return "weather";
    }

    @GetMapping("/history")
    public String viewHistoricalData(@RequestParam("city") String city, Model model) {
        List<WeatherData> historicalData = weatherService.getHistoricalWeatherData(city);
        model.addAttribute("historicalData", historicalData);
        model.addAttribute("city", city);
        return "historical_data"; 
    }

    @PostMapping("/setAlert")
    public String setAlert(
            @RequestParam("email") String email,
            @RequestParam("tempThreshold") double tempThreshold,
            @RequestParam("alertType") String alertType,
            @RequestParam("city") String city) {

        Alert alert = new Alert();
        alert.setEmail(email);
        alert.setTempThreshold(tempThreshold);
        alert.setAlertType(alertType); 
        alert.setCity(city);

        
        alertRepository.save(alert);

        alertService.saveAlert(email, tempThreshold, alertType, city);
        return "redirect:/weather?city=" + city;  
    }
}
