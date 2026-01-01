package com.codehills.fueltracker.cli.dto;

public class CarResponse {

  private Long id;
  private String brand;
  private String model;
  private Integer year;
  private Integer fuelEntryCount;

  public CarResponse() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getFuelEntryCount() {
    return fuelEntryCount;
  }

  public void setFuelEntryCount(Integer fuelEntryCount) {
    this.fuelEntryCount = fuelEntryCount;
  }
}
