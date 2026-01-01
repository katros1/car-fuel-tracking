package com.codehills.fueltracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCarRequest {

  @NotBlank(message = "Brand is required")
  private String brand;

  @NotBlank(message = "Model is required")
  private String model;

  @NotNull(message = "Year is required")
  @Min(value = 1900, message = "Year must be 1900 or later")
  private Integer year;

  public CreateCarRequest() {}

  public CreateCarRequest(String brand, String model, Integer year) {
    this.brand = brand;
    this.model = model;
    this.year = year;
  }
}
