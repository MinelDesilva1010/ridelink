package com.ridelink.ride.dto;

import jakarta.validation.constraints.NotNull;

public record AssignDriverRequest(@NotNull Long driverId) {}
