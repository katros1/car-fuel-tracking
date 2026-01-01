package com.codehills.fueltracker.cli.util;

import com.codehills.fueltracker.cli.exception.InvalidArgumentException;
import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {

  private final Map<String, String> arguments;

  public ArgumentParser(String[] args) {
    this.arguments = new HashMap<>();
    parseArguments(args);
  }

  private void parseArguments(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].startsWith("--")) {
        String key = args[i].substring(2);

        if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
          String value = args[i + 1];
          arguments.put(key, value);
          i++;
        } else {
          throw new InvalidArgumentException("Missing value for argument: --" + key);
        }
      }
    }
  }

  public String getRequired(String key) {
    String value = arguments.get(key);
    if (value == null || value.trim().isEmpty()) {
      throw new InvalidArgumentException("Missing required argument: --" + key);
    }
    return value;
  }

  public String getOptional(String key, String defaultValue) {
    String value = arguments.get(key);
    return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
  }

  public Integer getRequiredInt(String key) {
    String value = getRequired(key);
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new InvalidArgumentException("Invalid integer value for --" + key + ": " + value);
    }
  }

  public Long getRequiredLong(String key) {
    String value = getRequired(key);
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      throw new InvalidArgumentException("Invalid number value for --" + key + ": " + value);
    }
  }

  public Double getRequiredDouble(String key) {
    String value = getRequired(key);
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      throw new InvalidArgumentException("Invalid decimal value for --" + key + ": " + value);
    }
  }

  // Checks if an argument exists.
  public boolean has(String key) {
    return arguments.containsKey(key);
  }
}
