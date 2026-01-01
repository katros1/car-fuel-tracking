package com.codehills.fueltracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddFuelRequest {

  @NotNull(message = "Liters is required")
  @Min(value = 0, message = "Liters must be positive")
  private Double liters;

  @NotNull(message = "Price is required")
  @Min(value = 0, message = "Price must be positive")
  private Double price;

  @NotNull(message = "Odometer reading is required")
  @Min(value = 0, message = "Odometer must be positive")
  private Integer odometer;

  public AddFuelRequest() {}

  public AddFuelRequest(Double liters, Double price, Integer odometer) {
    this.liters = liters;
    this.price = price;
    this.odometer = odometer;
  }
}
