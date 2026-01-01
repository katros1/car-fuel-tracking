package com.codehills.fueltracker.cli.command;

import com.codehills.fueltracker.cli.dto.ApiResponse;
import com.codehills.fueltracker.cli.dto.FuelStatsResponse;
import com.codehills.fueltracker.cli.service.ApiService;
import com.codehills.fueltracker.cli.util.ArgumentParser;

/**
 * Command to view fuel statistics for a car.
 *
 * <p>Usage: fuel-stats --carId 1
 */
public class FuelStatsCommand implements Command {

  private final ApiService apiService;

  public FuelStatsCommand(ApiService apiService) {
    this.apiService = apiService;
  }

  @Override
  public void execute(ArgumentParser args) {
    Long carId = args.getRequiredLong("carId");

    System.out.println("Fetching fuel statistics...");
    ApiResponse<FuelStatsResponse> apiResponse = apiService.getFuelStats(carId);
    FuelStatsResponse stats = apiResponse.getData();

    System.out.println(apiResponse.getMessage());

    System.out.println();
    System.out.println("Fuel Statistics for " + stats.getCarBrand() + " " + stats.getCarModel());
    System.out.println();

    System.out.println("Total fuel: " + String.format("%.1f", stats.getTotalLiters()) + " L");
    System.out.println("Total cost: " + String.format("%.2f", stats.getTotalCost()));

    if (stats.getTotalDistanceTraveled() > 0) {
      System.out.println(
          "Average consumption: " + String.format("%.1f", stats.getAveragePer100Km()) + " L/100km");
    } else {
      System.out.println("Average consumption: N/A");
    }

    System.out.println();
    System.out.println("Additional Info:");
    System.out.println("  Number of refuels: " + stats.getNumberOfRefuels());

    if (stats.getTotalDistanceTraveled() > 0) {
      System.out.println("  Distance traveled: " + stats.getTotalDistanceTraveled() + " km");
    }
  }

  @Override
  public String getName() {
    return "fuel-stats";
  }

  @Override
  public String getUsage() {
    return "fuel-stats --carId <id>";
  }
}
