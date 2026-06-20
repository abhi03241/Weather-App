package com.example.weather_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private double tempThreshold;
    private String alertType;
    private String city;
}
