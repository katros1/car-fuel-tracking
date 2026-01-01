package com.codehills.fueltracker.cli;

import com.codehills.fueltracker.cli.command.*;
import com.codehills.fueltracker.cli.exception.ApiException;
import com.codehills.fueltracker.cli.exception.InvalidArgumentException;
import com.codehills.fueltracker.cli.service.ApiService;
import com.codehills.fueltracker.cli.util.ArgumentParser;
import java.util.HashMap;
import java.util.Map;

public class FuelTrackerCLI {

  private final Map<String, Command> commands;
  private final ApiService apiService;

  public FuelTrackerCLI() {
    this.apiService = new ApiService();
    this.commands = new HashMap<>();

    registerCommand(new CreateCarCommand(apiService));
    registerCommand(new AddFuelCommand(apiService));
    registerCommand(new FuelStatsCommand(apiService));
    registerCommand(new ListCarsCommand(apiService));
  }

  private void registerCommand(Command command) {
    commands.put(command.getName(), command);
  }

  public void run(String[] args) {
    try {

      if (args.length == 0) {
        printUsage();
        System.exit(0);
      }

      String commandName = args[0];

      if (commandName.equals("--help") || commandName.equals("-h")) {
        printUsage();
        System.exit(0);
      }

      Command command = commands.get(commandName);
      if (command == null) {
        System.err.println("Error: Unknown command: " + commandName);
        System.err.println();
        printUsage();
        System.exit(1);
      }

      String[] commandArgs = new String[args.length - 1];
      System.arraycopy(args, 1, commandArgs, 0, commandArgs.length);
      ArgumentParser parser = new ArgumentParser(commandArgs);

      command.execute(parser);

    } catch (InvalidArgumentException e) {
      System.err.println("Error: " + e.getMessage());
      System.err.println();
      printUsage();
      System.exit(1);

    } catch (ApiException e) {
      System.err.println("API Error: " + e.getMessage());
      System.err.println();
      System.exit(1);

    } catch (Exception e) {
      System.err.println("Unexpected error: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void printUsage() {
    System.out.println();
    System.out.println("Available Commands:");
    System.out.println();

    for (Command command : commands.values()) {
      System.out.println("  " + command.getUsage());
    }
  }

  public static void main(String[] args) {
    FuelTrackerCLI cli = new FuelTrackerCLI();
    cli.run(args);
  }
}
