package com.codehills.fueltracker.cli.dto;

public class ApiResponse<T> {

  private String message;
  private T data;
  private String timestamp;

  public ApiResponse() {}

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
}
