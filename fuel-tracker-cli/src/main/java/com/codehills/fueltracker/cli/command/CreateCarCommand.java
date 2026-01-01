package com.codehills.fueltracker.cli.command;

import com.codehills.fueltracker.cli.dto.ApiResponse;
import com.codehills.fueltracker.cli.dto.CarResponse;
import com.codehills.fueltracker.cli.dto.CreateCarRequest;
import com.codehills.fueltracker.cli.service.ApiService;
import com.codehills.fueltracker.cli.util.ArgumentParser;

public class CreateCarCommand implements Command {

  private final ApiService apiService;

  public CreateCarCommand(ApiService apiService) {
    this.apiService = apiService;
  }

  @Override
  public void execute(ArgumentParser args) {
    String brand = args.getRequired("brand");
    String model = args.getRequired("model");
    Integer year = args.getRequiredInt("year");

    if (year < 1900) {
      System.err.println("Error: Year must be  1900 or later");
      System.exit(1);
    }

    CreateCarRequest request = new CreateCarRequest(brand, model, year);

    System.out.println("Creating car...");
    ApiResponse<CarResponse> apiResponse = apiService.createCar(request);

    System.out.println(apiResponse.getMessage());
    System.out.println();
    System.out.println("Car Details:");
    System.out.println("  ID: " + apiResponse.getData().getId());
    System.out.println("  Brand: " + apiResponse.getData().getBrand());
    System.out.println("  Model: " + apiResponse.getData().getModel());
    System.out.println("  Year: " + apiResponse.getData().getYear());
    System.out.println();
  }

  @Override
  public String getName() {
    return "create-car";
  }

  @Override
  public String getUsage() {
    return "create-car --brand <brand> --model <model> --year <year>";
  }
}
