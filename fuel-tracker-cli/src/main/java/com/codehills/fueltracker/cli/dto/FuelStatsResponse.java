package com.codehills.fueltracker.cli.dto;

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

  public Long getCarId() {
    return carId;
  }

  public void setCarId(Long carId) {
    this.carId = carId;
  }

  public String getCarBrand() {
    return carBrand;
  }

  public void setCarBrand(String carBrand) {
    this.carBrand = carBrand;
  }

  public String getCarModel() {
    return carModel;
  }

  public void setCarModel(String carModel) {
    this.carModel = carModel;
  }

  public Double getTotalLiters() {
    return totalLiters;
  }

  public void setTotalLiters(Double totalLiters) {
    this.totalLiters = totalLiters;
  }

  public Double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(Double totalCost) {
    this.totalCost = totalCost;
  }

  public Double getAveragePer100Km() {
    return averagePer100Km;
  }

  public void setAveragePer100Km(Double averagePer100Km) {
    this.averagePer100Km = averagePer100Km;
  }

  public Integer getTotalDistanceTraveled() {
    return totalDistanceTraveled;
  }

  public void setTotalDistanceTraveled(Integer totalDistanceTraveled) {
    this.totalDistanceTraveled = totalDistanceTraveled;
  }

  public Integer getNumberOfRefuels() {
    return numberOfRefuels;
  }

  public void setNumberOfRefuels(Integer numberOfRefuels) {
    this.numberOfRefuels = numberOfRefuels;
  }
}
