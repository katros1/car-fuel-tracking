package com.codehills.fueltracker.dto;

import com.codehills.fueltracker.model.Car;
import lombok.Data;

@Data
public class CarResponse {

  private Long id;
  private String brand;
  private String model;
  private Integer year;
  private Integer fuelEntryCount;

  public CarResponse() {}

  public CarResponse(Long id, String brand, String model, Integer year, Integer fuelEntryCount) {
    this.id = id;
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.fuelEntryCount = fuelEntryCount;
  }

  public static CarResponse fromCar(Car car) {
    return new CarResponse(
        car.getId(),
        car.getBrand(),
        car.getModel(),
        car.getYear(),
        car.getFuelEntries() != null ? car.getFuelEntries().size() : 0);
  }
}
