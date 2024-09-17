/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ahmet
 */
@Service
public class WeatherAPIService {
    private static final String API_KEY = "5f1351fcb9c4271d4ee84ba056cb4b25";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String GEO_URL = "http://api.openweathermap.org/geo/1.0/direct";



    public String getWeather(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .toUriString();
        return restTemplate.getForObject(url, String.class);
    }

    public Double getTemperature(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                return root.path("main").path("temp").asDouble();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Integer getHumidity(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                return root.path("main").path("humidity").asInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Double getFeels_like(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                return root.path("main").path("feels_like").asDouble();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Double getWind_speed(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                return root.path("wind").path("speed").asDouble();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Double getClouds(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                return root.path("clouds").path("all").asDouble();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getWeatherCondition(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                String description = root.path("weather").get(0).path("description").asText();
                return mapDescriptionToCategory(description);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }




    private String mapDescriptionToCategory(String description) {
        switch (description.toLowerCase()) {
            case "clear sky":
            case "few clouds":
            case "mostly clear":
            case "partly cloudly":
                return "sunny";
            case "scattered clouds":
            case "broken clouds":
            case "overcast clouds":
                return "clouds";
            case "shower rain":
            case "rain":
            case "light rain":
            case "moderate rain":
            case "heavy intensity rain":
            case "very heavy rain":
            case "extreme rain":
            case "light intensity drizzle":
            case "drizzle":
            case "heavy intensity drizzle":
            case "light intensity drizzle rain":
            case "drizzle rain":
            case "heavy intensity drizzle rain":
            case "shower rain and drizzle":
            case "heavy shower rain and drizzle":
            case "shower drizzle":
                return "rainy";
            case "thunderstorm":
            case "thunderstorm with light rain":
            case "thunderstorm with rain":
            case "thunderstorm with heavy rain":
            case "light thunderstorm":
            case "heavy thunderstorm":
            case "ragged thunderstorm":
            case "thunderstorm with light drizzle":
            case "thunderstorm with drizzle":
            case "thunderstorm with heavy drizzle":
                return "thunderstorm";
            case "snow":
            case "light snow":
            case "heavy snow":
            case "sleet":
            case "light shower sleet":
            case "shower sleet":
            case "light rain and snow":
            case "rain and snow":
            case "light shower snow":
            case "shower snow":
            case "heavy shower snow":
                return "snowy";
            case "mist":
                return "mist";
            default:
                return "";
        }    }

    public Double getMinTemperature(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                return root.path("main").path("temp_min").asDouble();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Double getMaxTemperature(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                return root.path("main").path("temp_max").asDouble();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public Double getPressure(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                return root.path("main").path("pressure").asDouble();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getFormattedDate(String city) {
        String jsonResponse = getWeather(city);
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                long timestamp = root.path("dt").asLong();
                Instant instant = Instant.ofEpochSecond(timestamp);
                LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                // Tarih formatı belirleme
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                return dateTime.format(formatter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<String> getCitySuggestions(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(GEO_URL)
                .queryParam("q", query)
                .queryParam("limit", 5)
                .queryParam("appid", API_KEY)
                .toUriString();
        String jsonResponse = restTemplate.getForObject(url, String.class);
        List<String> citySuggestions = new ArrayList<>();
        if (jsonResponse != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonResponse);
                for (JsonNode node : root) {
                    String cityName = node.path("name").asText();
                    String country = node.path("country").asText();
                    citySuggestions.add(cityName + ", " + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return citySuggestions;
    }




}
