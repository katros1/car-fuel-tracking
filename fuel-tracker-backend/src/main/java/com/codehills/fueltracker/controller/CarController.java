package com.codehills.fueltracker.controller;

import com.codehills.fueltracker.dto.*;
import com.codehills.fueltracker.model.Car;
import com.codehills.fueltracker.service.CarService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
public class CarController {

  private final CarService carService;

  public CarController(CarService carService) {
    this.carService = carService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<CarResponse>> createCar(
      @Valid @RequestBody CreateCarRequest request) {
    Car car = carService.createCar(request);
    CarResponse carResponse = CarResponse.fromCar(car);

    String message = String.format("Car created successfully with ID %d", car.getId());
    ApiResponse<CarResponse> response = ApiResponse.success(message, carResponse);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CarResponse>>> getAllCars() {
    List<CarResponse> cars =
        carService.getAllCars().stream().map(CarResponse::fromCar).collect(Collectors.toList());

    String message = String.format("Retrieved %d car(s)", cars.size());
    ApiResponse<List<CarResponse>> response = ApiResponse.success(message, cars);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/{id}/fuel")
  public ResponseEntity<ApiResponse<CarResponse>> addFuel(
      @PathVariable Long id, @Valid @RequestBody AddFuelRequest request) {

    Car car = carService.addFuelEntry(id, request);
    CarResponse carResponse = CarResponse.fromCar(car);

    String message = String.format("Fuel entry added successfully to car with ID %d", id);
    ApiResponse<CarResponse> response = ApiResponse.success(message, carResponse);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{id}/fuel/stats")
  public ResponseEntity<ApiResponse<FuelStatsResponse>> getFuelStats(@PathVariable Long id) {
    FuelStatsResponse stats = carService.getFuelStats(id);

    String message = String.format("Fuel statistics retrieved for car ID %d", id);
    ApiResponse<FuelStatsResponse> response = ApiResponse.success(message, stats);

    return ResponseEntity.ok(response);
  }
}
