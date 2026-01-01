package com.codehills.fueltracker.dto;

import lombok.Data;

@Data
public class FuelStatsResponse {

  private Long carId;
  private String carBrand;
  private String carModel;
  private Double totalLiters;
  private Double totalCost;
  private Double averagePer100Km;
  private Integer totalDistanceTraveled;
  private Integer numberOfRefuels;

  public FuelStatsResponse() {}

  public FuelStatsResponse(
      Long carId,
      String carBrand,
      String carModel,
      Double totalLiters,
      Double totalCost,
      Double averagePer100Km,
      Integer totalDistanceTraveled,
      Integer numberOfRefuels) {
    this.carId = carId;
    this.carBrand = carBrand;
    this.carModel = carModel;
    this.totalLiters = totalLiters;
    this.totalCost = totalCost;
    this.averagePer100Km = averagePer100Km;
    this.totalDistanceTraveled = totalDistanceTraveled;
    this.numberOfRefuels = numberOfRefuels;
  }
}
