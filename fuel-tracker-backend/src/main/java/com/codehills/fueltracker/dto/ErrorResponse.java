package com.codehills.fueltracker.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ErrorResponse {

  private LocalDateTime timestamp;
  private Integer status;
  private String error;
  private String message;
  private String path;
  private List<String> validationErrors;

  public ErrorResponse() {
    this.timestamp = LocalDateTime.now();
  }

  public ErrorResponse(Integer status, String error, String message, String path) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }
}
