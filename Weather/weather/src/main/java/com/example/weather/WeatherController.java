/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Ahmet
 */
@Controller
public class WeatherController {

    @Autowired
    private WeatherAPIService weatherService;

    @GetMapping("/weather")
                                 public String getWeather(@RequestParam(name = "city", required = false, defaultValue = "Kayseri") String city, Model model) {
        Double temperature = weatherService.getTemperature(city);
        Integer humidity = weatherService.getHumidity(city);
        Double feels_like = weatherService.getFeels_like(city);
        Double wind_speed = weatherService.getWind_speed(city);
        Double clouds = weatherService.getClouds(city);
        Double minTemperature = weatherService.getMinTemperature(city);
        Double maxTemperature = weatherService.getMaxTemperature(city);
        String weatherCondition = weatherService.getWeatherCondition(city);
        Double pressure = weatherService.getPressure(city);
        String date = weatherService.getFormattedDate(city);
        model.addAttribute("temperature", temperature);
        model.addAttribute("city", city);
        model.addAttribute("humidity", humidity);
        model.addAttribute("feels_like",feels_like);
        model.addAttribute("wind_speed",wind_speed);
        model.addAttribute("clouds",clouds);
        model.addAttribute("minTemperature", minTemperature);
        model.addAttribute("maxTemperature", maxTemperature);
        model.addAttribute("weatherCondition", weatherCondition);
        model.addAttribute("pressure", pressure);
        model.addAttribute("date", date);
        return "weather";
    }
    @GetMapping("/city-suggestions")
    @ResponseBody
    public List<String> getCitySuggestions(@RequestParam(name = "query") String query) {
        return weatherService.getCitySuggestions(query);
    }


}
