package com.ridelink.driver.dto;

import jakarta.validation.constraints.NotNull;

public class AvailabilityRequest {

    @NotNull(message = "available is required")
    private Boolean available;

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}
