package com.edured.services.util;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveScheduler {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String keepAliveUrl = "https://www.edured.in"; // Replace with your application's URL

    // @Scheduled(fixedRate = 600000) // Schedule the task to run every 10 minutes (adjust the interval as needed)
    @Scheduled(fixedRate = 300000) // Schedule the task to run every 5 minutes (adjust the interval as needed)
    public void sendKeepAliveRequest() {
        // System.out.println("helo");
        try {
            restTemplate.getForObject(keepAliveUrl, String.class);
            System.out.println("Keep-alive request sent successfully.");
        } catch (Exception e) {
            System.err.println("Error sending keep-alive request: " + e.getMessage());
        }
    }
}
