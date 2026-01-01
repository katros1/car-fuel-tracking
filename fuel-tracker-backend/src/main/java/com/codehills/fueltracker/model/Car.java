package com.codehills.fueltracker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;

@Data
public class Car {

  private Long id;
  private String brand;
  private String model;
  private Integer year;
  private List<FuelEntry> fuelEntries;

  public Car() {
    this.fuelEntries = new ArrayList<>();
  }

  public Car(Long id, String brand, String model, Integer year) {
    this.id = id;
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.fuelEntries = new ArrayList<>();
  }

  public void addFuelEntry(FuelEntry fuelEntry) {
    if (this.fuelEntries == null) {
      this.fuelEntries = new ArrayList<>();
    }
    this.fuelEntries.add(fuelEntry);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Car car = (Car) o;
    return Objects.equals(id, car.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Car{"
        + "id="
        + id
        + ", brand='"
        + brand
        + '\''
        + ", model='"
        + model
        + '\''
        + ", year="
        + year
        + ", fuelEntries="
        + (fuelEntries != null ? fuelEntries.size() : 0)
        + '}';
  }
}
