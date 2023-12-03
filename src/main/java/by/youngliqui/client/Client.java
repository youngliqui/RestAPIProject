package by.youngliqui.client;

import by.youngliqui.RestAPIProject.models.Sensor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", "Test sensor");

        Sensor sensor = new Sensor("Test sensor");

        String url = "http://localhost:8080/sensors/registration";
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(jsonToSend);

        String response = restTemplate.postForObject(url,
               entity,
               String.class);



        String urlForAdd = "http://localhost:8080/measurements/add";
        Random random = new Random(112288922);

        for (int i = 0; i < 1000; i++) {
            float temp = random.nextFloat() * 200 - 100;
            boolean raining = random.nextBoolean();

            Map<String, Object> jsonBody = new HashMap<>();
            jsonBody.put("value", temp);
            jsonBody.put("raining", raining);
            jsonBody.put("sensor", sensor);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonBody);

            response = restTemplate.postForObject(urlForAdd, request, String.class);
        }


        System.out.println(restTemplate.getForObject("http://localhost:8080/measurements", String.class));
    }
}
