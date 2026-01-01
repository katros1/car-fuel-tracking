package com.codehills.fueltracker.cli.command;

import com.codehills.fueltracker.cli.dto.AddFuelRequest;
import com.codehills.fueltracker.cli.dto.ApiResponse;
import com.codehills.fueltracker.cli.dto.CarResponse;
import com.codehills.fueltracker.cli.service.ApiService;
import com.codehills.fueltracker.cli.util.ArgumentParser;

public class AddFuelCommand implements Command {

  private final ApiService apiService;

  public AddFuelCommand(ApiService apiService) {
    this.apiService = apiService;
  }

  @Override
  public void execute(ArgumentParser args) {
    Long carId = args.getRequiredLong("carId");
    Double liters = args.getRequiredDouble("liters");
    Double price = args.getRequiredDouble("price");
    Integer odometer = args.getRequiredInt("odometer");

    if (liters <= 0) {
      System.err.println("Error: Liters must be greater than 0");
      System.exit(1);
    }
    if (price < 0) {
      System.err.println("Error: Price cannot be negative");
      System.exit(1);
    }
    if (odometer < 0) {
      System.err.println("Error: Odometer reading cannot be negative");
      System.exit(1);
    }

    AddFuelRequest request = new AddFuelRequest(liters, price, odometer);

    System.out.println("Adding fuel entry...");
    ApiResponse<CarResponse> apiResponse = apiService.addFuel(carId, request);

    System.out.println(apiResponse.getMessage());
    System.out.println();
    System.out.println("Updated Car Details:");
    System.out.println("  Car ID: " + apiResponse.getData().getId());
    System.out.println("  Brand: " + apiResponse.getData().getBrand());
    System.out.println("  Model: " + apiResponse.getData().getModel());
    System.out.println("  Total Fuel Entries: " + apiResponse.getData().getFuelEntryCount());
  }

  @Override
  public String getName() {
    return "add-fuel";
  }

  @Override
  public String getUsage() {
    return "add-fuel --carId <id> --liters <liters> --price <price> --odometer <reading>";
  }
}
