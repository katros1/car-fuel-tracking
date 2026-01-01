package com.codehills.fueltracker.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class ApiResponse<T> {

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private String message;
  private T data;
  private String timestamp;

  public ApiResponse() {
    this.timestamp = LocalDateTime.now().format(FORMATTER);
  }

  public ApiResponse(String message, T data) {
    this.message = message;
    this.data = data;
    this.timestamp = LocalDateTime.now().format(FORMATTER);
  }

  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>(message, data);
  }
}
