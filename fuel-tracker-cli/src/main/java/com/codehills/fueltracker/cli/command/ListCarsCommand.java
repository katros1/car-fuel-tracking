package com.codehills.fueltracker.cli.command;

import com.codehills.fueltracker.cli.dto.ApiResponse;
import com.codehills.fueltracker.cli.dto.CarResponse;
import com.codehills.fueltracker.cli.service.ApiService;
import com.codehills.fueltracker.cli.util.ArgumentParser;
import java.util.List;

public class ListCarsCommand implements Command {

  private final ApiService apiService;

  public ListCarsCommand(ApiService apiService) {
    this.apiService = apiService;
  }

  @Override
  public void execute(ArgumentParser args) {

    System.out.println("Fetching all cars...");
    ApiResponse<List<CarResponse>> apiResponse = apiService.getAllCars();
    List<CarResponse> cars = apiResponse.getData();

    System.out.println(apiResponse.getMessage());
    System.out.println();

    if (cars == null || cars.isEmpty()) {
      System.out.println("No cars found in the system.");
      System.out.println();
      System.out.println("Create a car using:");
      System.out.println("  create-car --brand Toyota --model Corolla --year 2018");
      return;
    }

    System.out.println("Registered Cars");
    System.out.println();

    System.out.println(
        String.format(
            "%-5s %-15s %-15s %-6s %-10s", "ID", "Brand", "Model", "Year", "Fuel Entries"));
    System.out.println("─".repeat(60));

    for (CarResponse car : cars) {
      System.out.println(
          String.format(
              "%-5d %-15s %-15s %-6d %-10d",
              car.getId(),
              truncate(car.getBrand(), 15),
              truncate(car.getModel(), 15),
              car.getYear(),
              car.getFuelEntryCount()));
    }

    System.out.println();
    System.out.println("Total: " + cars.size() + " car(s)");
    System.out.println();
  }

  private String truncate(String str, int maxLength) {
    if (str == null) {
      return "";
    }
    if (str.length() <= maxLength) {
      return str;
    }
    return str.substring(0, maxLength - 3) + "...";
  }

  @Override
  public String getName() {
    return "list-cars";
  }

  @Override
  public String getUsage() {
    return "list-cars";
  }
}
