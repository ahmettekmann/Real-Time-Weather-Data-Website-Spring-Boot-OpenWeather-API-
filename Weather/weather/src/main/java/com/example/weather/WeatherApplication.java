package com.example.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
		openHomePage();

	}

	public static void openHomePage() {
		try {
			String url = "http://localhost:8080/weather";
			String os = System.getProperty("os.name").toLowerCase();
			String command = "";

			if (os.contains("win")) {
				command = "rundll32 url.dll,FileProtocolHandler " + url;
			} else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
				command = "xdg-open " + url;
			} else {
				System.err.println("Unsupported operating system.");
				return;
			}
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
