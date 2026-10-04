package com.ridelink.ride.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class DriverServiceClientConfig {

    @Bean
    public RestClient driverServiceRestClient(RestClient.Builder builder,
                                               @Value("${driver.service.base-url}") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }
}
