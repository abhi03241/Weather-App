package com.example.weather_app.scheduler;

import com.example.weather_app.model.Alert;
import com.example.weather_app.model.WeatherData;
import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.repository.AlertRepository;
import com.example.weather_app.repository.WeatherRepository;
import com.example.weather_app.service.AlertService;
import com.example.weather_app.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class WeatherScheduler {


    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private AlertService alertService;
    @Autowired
    private AlertRepository alertRepository;


    private Map<String, DailyWeatherAggregate> dailyAggregates = new HashMap<>();


    private final List<String> cities = List.of("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad", "Indore", "Bhopal");


    @Scheduled(fixedRate = 300000) // 300000 ms = 5 minutes
    public void fetchWeatherData() {
        System.out.println("Scheduler triggered at: " + LocalDateTime.now());

        for (String city : cities) {
            WeatherResponse weatherResponse = weatherService.fetchWeatherData(city);

            // Log each city for which data is being fetched
            System.out.println("Fetching weather data for: " + city);

            if (weatherResponse != null) {
                System.out.println("Data fetched for " + city + ": Temp = " + weatherResponse.getMain().getTemp());

                double temperature = weatherResponse.getMain().getTemp();
                String condition = weatherResponse.getWeather().get(0).getDescription();


                updateDailyAggregate(city, temperature, condition);


                checkTemperatureAlerts(city, temperature);

            } else {
                System.out.println("Failed to fetch data for city: " + city);
            }
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() == 23 && now.getMinute() == 59) {
            saveDailyAggregates();
            System.out.println("Daily aggregate saved at: " + now);
        }
    }




    private void updateDailyAggregate(String city, double temperature, String condition) {
        String dateKey = LocalDate.now().toString();
        String key = city + "_" + dateKey;

        System.out.println("Updating aggregate for city: " + city + ", temp: " + temperature);
        DailyWeatherAggregate aggregate = dailyAggregates.getOrDefault(key, new DailyWeatherAggregate(city));

        aggregate.addTemperature(temperature);
        aggregate.updateDominantCondition(condition);
        System.out.println("Updated aggregate: avgTemp = " + aggregate.calculateAverageTemperature() +
                ", maxTemp = " + aggregate.getMaxTemperature() +
                ", minTemp = " + aggregate.getMinTemperature());

        dailyAggregates.put(key, aggregate);
    }


    private void saveDailyAggregates() {
        for (DailyWeatherAggregate aggregate : dailyAggregates.values()) {
            WeatherData weatherData = new WeatherData();
            weatherData.setCity(aggregate.getCity());
            weatherData.setDate(LocalDate.now());
            weatherData.setAverageTemp(aggregate.calculateAverageTemperature());
            weatherData.setMaxTemp(aggregate.getMaxTemperature());
            weatherData.setMinTemp(aggregate.getMinTemperature());
            weatherData.setDominantCondition(aggregate.getDominantCondition());

            weatherRepository.save(weatherData);
        }
        dailyAggregates.clear();
    }

    public void checkTemperatureAlerts(String city, double currentTemp) {
        List<Alert> alerts = alertRepository.findByCity(city);

        List<WeatherData> weatherDataList = weatherRepository.findTopByCityOrderByTimestampDesc(city);

        if (!weatherDataList.isEmpty()) {
            WeatherData latestWeatherData = weatherDataList.get(0);
            for (Alert alert : alerts) {
                if ("above".equals(alert.getAlertType())) {
                    checkTemperatureAlertAbove(alert.getEmail(), currentTemp, alert.getTempThreshold(), Optional.of(latestWeatherData));
                } else if ("below".equals(alert.getAlertType())) {
                    checkTemperatureAlertBelow(alert.getEmail(), currentTemp, alert.getTempThreshold(), Optional.of(latestWeatherData));
                }

                    alertRepository.delete(alert);
            }
        } else {
            System.out.println("No weather data found for city: " + city);
        }
    }



    public void checkTemperatureAlertAbove(String userEmail, double currentTemp, double thresholdTemp, Optional<WeatherData> weatherData) {
        if (currentTemp > thresholdTemp) {
            String subject = "🌡️ Temperature Alert! Stay Cool, It’s Heating Up in Your City! 🌞";
            String body = "Hey there!\n\n" +
                    "We’re keeping an eye on the weather for you! 🚨\n\n" +
                    "The temperature in " + weatherData.get().getCity() + " has just soared past your set threshold of " + thresholdTemp + "°C. " +
                    "Here’s a quick weather update:\n\n" +
                    "- Current Temperature: " + currentTemp + "°C\n" +
                    "- Dominant Weather Condition: " + weatherData.get().getDominantCondition() + "\n\n" +
                    "It’s a great time to grab a cool drink and stay hydrated! 💧 If you’d like to adjust your temperature alert settings, feel free to log back into the app.\n\n" +
                    "Stay cool and take care!\n\n" +
                    "Best regards,\n" +
                    "The Weather App Team⛅";
            alertService.sendAlertEmail(userEmail, subject, body);
        }
    }

    public void checkTemperatureAlertBelow(String userEmail, double currentTemp, double thresholdTemp, Optional<WeatherData> weatherData) {
        if (currentTemp < thresholdTemp) {
            String subject = "🌡️ Temperature Alert! Brrr... It’s Getting Chilly in Your City! ❄️";
            String body = "Hello!\n\n" +
                    "We’re keeping a watchful eye on the weather for you! 🚨\n\n" +
                    "The temperature in " + weatherData.get().getCity() + " has dipped below your set threshold of " + thresholdTemp + "°C. " +
                    "Here’s a quick weather update:\n\n" +
                    "- Current Temperature: " + currentTemp + "°C\n" +
                    "- Dominant Weather Condition: " + weatherData.get().getDominantCondition() + "\n\n" +
                    "Time to cozy up and perhaps sip on a warm drink! ☕ If you’d like to adjust your temperature alert settings, feel free to log back into the app.\n\n" +
                    "Stay warm and take care!\n\n" +
                    "Best wishes,\n" +
                    "The Weather App Team ⛅";
            alertService.sendAlertEmail(userEmail, subject, body);
        }
    }





    // Helper class to aggregate daily weather data
    private static class DailyWeatherAggregate {
        private final String city;
        private double totalTemperature = 0;
        private int count = 0;
        private double maxTemperature = Double.MIN_VALUE;
        private double minTemperature = Double.MAX_VALUE;
        private String dominantCondition;
        private Map<String, Integer> conditionCounts = new HashMap<>();

        public DailyWeatherAggregate(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public void addTemperature(double temperature) {
            totalTemperature += temperature;
            count++;
            maxTemperature = Math.max(maxTemperature, temperature);
            minTemperature = Math.min(minTemperature, temperature);
        }

        public double calculateAverageTemperature() {
            return count == 0 ? 0 : totalTemperature / count;
        }

        public double getMaxTemperature() {
            return maxTemperature;
        }

        public double getMinTemperature() {
            return minTemperature;
        }

        public void updateDominantCondition(String condition) {
            conditionCounts.put(condition, conditionCounts.getOrDefault(condition, 0) + 1);
            dominantCondition = conditionCounts.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .orElseThrow(() -> new RuntimeException("No dominant condition found"))
                    .getKey();
        }

        public String getDominantCondition() {
            return dominantCondition;
        }
    }
}
