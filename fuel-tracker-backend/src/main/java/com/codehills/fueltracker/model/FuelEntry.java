package com.codehills.fueltracker.model;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Data;

@Data
public class FuelEntry {

  private Long id;
  private Double liters;
  private Double price;
  private Integer odometer;
  private LocalDateTime timestamp;

  public FuelEntry() {
    this.timestamp = LocalDateTime.now();
  }

  public FuelEntry(Long id, Double liters, Double price, Integer odometer) {
    this.id = id;
    this.liters = liters;
    this.price = price;
    this.odometer = odometer;
    this.timestamp = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FuelEntry fuelEntry = (FuelEntry) o;
    return Objects.equals(id, fuelEntry.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "FuelEntry{"
        + "id="
        + id
        + ", liters="
        + liters
        + ", price="
        + price
        + ", odometer="
        + odometer
        + ", timestamp="
        + timestamp
        + '}';
  }
}
