package com.codehills.fueltracker.service;

import static org.junit.jupiter.api.Assertions.*;

import com.codehills.fueltracker.dto.AddFuelRequest;
import com.codehills.fueltracker.dto.CreateCarRequest;
import com.codehills.fueltracker.dto.FuelStatsResponse;
import com.codehills.fueltracker.exception.ResourceNotFoundException;
import com.codehills.fueltracker.model.Car;
import com.codehills.fueltracker.repository.CarRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarServiceTest {

  private CarService carService;
  private CarRepository carRepository;

  @BeforeEach
  void setUp() {
    carRepository = new CarRepository();
    carService = new CarService(carRepository);
  }

  @Test
  @DisplayName("Should create car successfully")
  void testCreateCar() {
    CreateCarRequest request = new CreateCarRequest("Toyota", "Corolla", 2020);

    Car car = carService.createCar(request);

    assertNotNull(car);
    assertNotNull(car.getId());
    assertEquals("Toyota", car.getBrand());
    assertEquals("Corolla", car.getModel());
    assertEquals(2020, car.getYear());
    assertEquals(0, car.getFuelEntries().size());
  }

  @Test
  @DisplayName("Should retrieve all cars")
  void testGetAllCars() {
    CreateCarRequest request1 = new CreateCarRequest("Toyota", "Corolla", 2020);
    CreateCarRequest request2 = new CreateCarRequest("Honda", "Civic", 2019);

    carService.createCar(request1);
    carService.createCar(request2);

    List<Car> cars = carService.getAllCars();

    assertEquals(2, cars.size());
  }

  @Test
  @DisplayName("Should throw exception when car not found")
  void testGetCarByIdNotFound() {
    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          carService.getCarById(999L);
        });
  }

  @Test
  @DisplayName("Should add fuel entry successfully")
  void testAddFuelEntry() {
    CreateCarRequest carRequest = new CreateCarRequest("Toyota", "Corolla", 2020);
    Car car = carService.createCar(carRequest);

    AddFuelRequest fuelRequest = new AddFuelRequest(45.5, 60000.0, 15000);
    Car updatedCar = carService.addFuelEntry(car.getId(), fuelRequest);

    assertEquals(1, updatedCar.getFuelEntries().size());
    assertEquals(45.5, updatedCar.getFuelEntries().get(0).getLiters());
  }

  @Test
  @DisplayName("Should calculate fuel statistics correctly")
  void testGetFuelStatsSuccess() {
    CreateCarRequest carRequest = new CreateCarRequest("Toyota", "Corolla", 2020);
    Car car = carService.createCar(carRequest);

    AddFuelRequest fuelRequest1 = new AddFuelRequest(45.5, 60000.0, 15000);
    AddFuelRequest fuelRequest2 = new AddFuelRequest(44.5, 60000.0, 16058);

    carService.addFuelEntry(car.getId(), fuelRequest1);
    carService.addFuelEntry(car.getId(), fuelRequest2);

    FuelStatsResponse stats = carService.getFuelStats(car.getId());

    assertNotNull(stats);
    assertEquals(car.getId(), stats.getCarId());
    assertEquals("Toyota", stats.getCarBrand());
    assertEquals("Corolla", stats.getCarModel());
    assertEquals(90.0, stats.getTotalLiters());
    assertEquals(120000.0, stats.getTotalCost());
    assertEquals(1058, stats.getTotalDistanceTraveled());
    assertEquals(2, stats.getNumberOfRefuels());
    assertTrue(stats.getAveragePer100Km() > 8.4 && stats.getAveragePer100Km() < 8.6);
  }
}
