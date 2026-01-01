package com.codehills.fueltracker.service;

import com.codehills.fueltracker.dto.AddFuelRequest;
import com.codehills.fueltracker.dto.CreateCarRequest;
import com.codehills.fueltracker.dto.FuelStatsResponse;
import com.codehills.fueltracker.exception.ResourceNotFoundException;
import com.codehills.fueltracker.model.Car;
import com.codehills.fueltracker.model.FuelEntry;
import com.codehills.fueltracker.repository.CarRepository;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class CarService {

  private final CarRepository carRepository;
  private final AtomicLong fuelEntryIdGenerator = new AtomicLong(1);

  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public Car createCar(CreateCarRequest request) {
    Car car = new Car();
    car.setBrand(request.getBrand());
    car.setModel(request.getModel());
    car.setYear(request.getYear());

    return carRepository.save(car);
  }

  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  public Car getCarById(Long id) {
    return carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car", id));
  }

  public Car addFuelEntry(Long carId, AddFuelRequest request) {
    Car car = getCarById(carId);

    if (!car.getFuelEntries().isEmpty()) {
      Integer lastOdometer =
          car.getFuelEntries().stream()
              .map(FuelEntry::getOdometer)
              .max(Comparator.naturalOrder())
              .orElse(0);

      if (request.getOdometer() <= lastOdometer) {
        throw new IllegalArgumentException(
            String.format(
                "Odometer reading must be greater than previous reading (%d)", lastOdometer));
      }
    }

    FuelEntry fuelEntry = new FuelEntry();
    fuelEntry.setId(fuelEntryIdGenerator.getAndIncrement());
    fuelEntry.setLiters(request.getLiters());
    fuelEntry.setPrice(request.getPrice());
    fuelEntry.setOdometer(request.getOdometer());

    car.addFuelEntry(fuelEntry);
    return carRepository.save(car);
  }

  public FuelStatsResponse getFuelStats(Long carId) {
    Car car = getCarById(carId);
    List<FuelEntry> entries = car.getFuelEntries();

    double totalLiters = 0.0;
    double totalCost = 0.0;
    int totalDistance = 0;
    double averagePer100Km = 0.0;

    if (!entries.isEmpty()) {
      List<FuelEntry> sortedEntries =
          entries.stream().sorted(Comparator.comparing(FuelEntry::getOdometer)).toList();

      totalLiters = sortedEntries.stream().mapToDouble(FuelEntry::getLiters).sum();

      totalCost = sortedEntries.stream().mapToDouble(FuelEntry::getPrice).sum();

      if (entries.size() >= 2) {

        int firstOdometer = sortedEntries.get(0).getOdometer();
        int lastOdometer = sortedEntries.get(sortedEntries.size() - 1).getOdometer();
        totalDistance = lastOdometer - firstOdometer;

        if (totalDistance > 0) {
          averagePer100Km = (totalLiters / totalDistance) * 100;
          averagePer100Km = Math.round(averagePer100Km * 100.0) / 100.0;
        }
      }
    }

    FuelStatsResponse response = new FuelStatsResponse();
    response.setCarId(car.getId());
    response.setCarBrand(car.getBrand());
    response.setCarModel(car.getModel());
    response.setTotalLiters(Math.round(totalLiters * 100.0) / 100.0);
    response.setTotalCost(Math.round(totalCost * 100.0) / 100.0);
    response.setAveragePer100Km(averagePer100Km);
    response.setTotalDistanceTraveled(totalDistance);
    response.setNumberOfRefuels(entries.size());

    return response;
  }
}
