package com.codehills.fueltracker.repository;

import com.codehills.fueltracker.model.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepository {

  private final Map<Long, Car> carStore = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  public Car save(Car car) {
    if (car.getId() == null) {
      car.setId(idGenerator.getAndIncrement());
    }
    carStore.put(car.getId(), car);
    return car;
  }

  public Optional<Car> findById(Long id) {
    return Optional.ofNullable(carStore.get(id));
  }

  public List<Car> findAll() {
    return new ArrayList<>(carStore.values());
  }
}
