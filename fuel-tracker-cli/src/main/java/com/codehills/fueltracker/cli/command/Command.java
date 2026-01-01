package com.codehills.fueltracker.cli.command;

import com.codehills.fueltracker.cli.util.ArgumentParser;

public interface Command {

  void execute(ArgumentParser args);

  String getName();

  String getUsage();
}
