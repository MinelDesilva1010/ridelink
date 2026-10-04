package com.ridelink.ride.client;

import com.ridelink.ride.dto.AvailabilityRequest;
import com.ridelink.ride.dto.DriverResponse;
import com.ridelink.ride.exception.DriverServiceUnavailableException;
import com.ridelink.ride.exception.NoDriverAvailableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * The ONLY component in Ride Service that knows how to talk to
 * Driver Service. Everything else in Ride Service depends on this
 * client's return type (DriverResponse), never on HTTP details.
 */
@Component
public class DriverClient {

    private final RestClient restClient;

    public DriverClient(RestClient driverServiceRestClient) {
        this.restClient = driverServiceRestClient;
    }

    /**
     * Finds an available driver, reserves them (sets availability=false),
     * and returns that driver. Any failure to reach Driver Service is
     * translated into a DriverServiceUnavailableException so the caller
     * (RideService) never has to know about HTTP/connection details.
     */
    public DriverResponse reserveAvailableDriver() {
        List<DriverResponse> availableDrivers = getAvailableDrivers();

        if (availableDrivers.isEmpty()) {
            throw new NoDriverAvailableException("No available drivers at the moment.");
        }

        DriverResponse chosen = availableDrivers.get(0);
        return markUnavailable(chosen.getId());
    }

    private List<DriverResponse> getAvailableDrivers() {
        try {
            return restClient.get()
                    .uri("/api/drivers/available")
                    .retrieve()
                    .body(new org.springframework.core.ParameterizedTypeReference<List<DriverResponse>>() {
                    });
        } catch (RestClientException ex) {
            // Covers connection refused (service down), timeouts, and 5xx from Driver Service.
            throw new DriverServiceUnavailableException(
                    "Could not reach Driver Service to list available drivers", ex);
        }
    }

    private DriverResponse markUnavailable(Long driverId) {
        try {
            return restClient.patch()
                    .uri("/api/drivers/{id}/availability", driverId)
                    .body(new AvailabilityRequest(false))
                    .retrieve()
                    .body(DriverResponse.class);
        } catch (RestClientException ex) {
            throw new DriverServiceUnavailableException(
                    "Could not reach Driver Service to reserve driver " + driverId, ex);
        }
    }
}
