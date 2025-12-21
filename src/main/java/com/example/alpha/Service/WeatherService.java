package com.example.alpha.Service;

import com.example.alpha.api.response.WeatherResponse;
import com.example.alpha.cache.AppCache;
import com.example.alpha.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;  // Your WeatherStack API key

//    @Value("${weather.api.base-url}")
//    private String apiBaseUrl; // Base URL from application.yml with placeholders

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        // Replace CITY and API_KEY placeholders with actual values
//        String finalAPI = apiBaseUrl.replace("CITY", city).replace("API_KEY", apiKey);

        String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);

        // Make API GET call and get response in one line
        WeatherResponse body = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class).getBody();

//        System.out.println("Weather API URL: " + finalAPI);

        // Optional POST example for reference (commented)
/*
        String requestBody = "{\n" +
                "    \"userName\":\"abc\",\n" +
                "    \"password\":\"abc\"\n" +
                "}";
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity, WeatherResponse.class);
*/
        return body;
    }
}
