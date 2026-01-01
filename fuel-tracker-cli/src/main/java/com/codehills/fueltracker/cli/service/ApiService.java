package com.codehills.fueltracker.cli.service;

import com.codehills.fueltracker.cli.dto.*;
import com.codehills.fueltracker.cli.exception.ApiException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class ApiService {

  private final HttpClient httpClient;
  private final Gson gson;
  private final String baseUrl;

  public ApiService() {
    this("http://localhost:8080");
  }

  public ApiService(String baseUrl) {
    this.baseUrl = baseUrl;
    this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    this.gson = new GsonBuilder().setPrettyPrinting().create();
  }

  public ApiResponse<CarResponse> createCar(CreateCarRequest request) {
    String endpoint = baseUrl + "/api/cars";
    String requestBody = gson.toJson(request);

    HttpRequest httpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    return sendRequestWithWrapper(httpRequest, CarResponse.class);
  }

  public ApiResponse<CarResponse> addFuel(Long carId, AddFuelRequest request) {
    String endpoint = baseUrl + "/api/cars/" + carId + "/fuel";
    String requestBody = gson.toJson(request);

    HttpRequest httpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    try {
      HttpResponse<String> response =
          httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() >= 200 && response.statusCode() < 300) {
        return gson.fromJson(
            response.body(),
            new com.google.gson.reflect.TypeToken<ApiResponse<CarResponse>>() {}.getType());
      } else {
        handleErrorResponse(response);
        throw new ApiException(
            "Request failed with status code: " + response.statusCode(), response.statusCode());
      }

    } catch (ApiException e) {
      throw e;
    } catch (Exception e) {
      throw new ApiException("Failed to communicate with server: " + e.getMessage(), 0, e);
    }
  }

  public ApiResponse<FuelStatsResponse> getFuelStats(Long carId) {
    String endpoint = baseUrl + "/servlet/fuel-stats?carId=" + carId;

    HttpRequest httpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .header("Accept", "application/json")
            .GET()
            .build();

    return sendRequestWithWrapper(httpRequest, FuelStatsResponse.class);
  }

  public ApiResponse<List<CarResponse>> getAllCars() {
    String endpoint = baseUrl + "/api/cars";

    HttpRequest httpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .header("Accept", "application/json")
            .GET()
            .build();

    return sendRequestWithListWrapper(httpRequest, CarResponse.class);
  }

  private void handleErrorResponse(HttpResponse<String> response) {
    try {
      ErrorResponse errorResponse = gson.fromJson(response.body(), ErrorResponse.class);

      StringBuilder errorMessage = new StringBuilder();
      errorMessage.append("API Error (").append(response.statusCode()).append("): ");
      errorMessage.append(errorResponse.getMessage());

      if (errorResponse.getValidationErrors() != null
          && !errorResponse.getValidationErrors().isEmpty()) {
        errorMessage.append("\nValidation errors:");
        for (String validationError : errorResponse.getValidationErrors()) {
          errorMessage.append("\n  - ").append(validationError);
        }
      }

      throw new ApiException(errorMessage.toString(), response.statusCode());

    } catch (ApiException e) {
      throw e;
    } catch (Exception e) {
      throw new ApiException("Request failed: " + response.body(), response.statusCode());
    }
  }

  private <T> ApiResponse<T> sendRequestWithWrapper(HttpRequest request, Class<T> dataType) {
    try {
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() >= 200 && response.statusCode() < 300) {
        Type type =
            com.google.gson.reflect.TypeToken.getParameterized(ApiResponse.class, dataType)
                .getType();

        return gson.fromJson(response.body(), type);
      } else {
        handleErrorResponse(response);
        throw new ApiException(
            "Request failed with status code: " + response.statusCode(), response.statusCode());
      }

    } catch (ApiException e) {
      throw e;
    } catch (Exception e) {
      throw new ApiException("Failed to communicate with server: " + e.getMessage(), 0, e);
    }
  }

  private <T> ApiResponse<List<T>> sendRequestWithListWrapper(
      HttpRequest request, Class<T> dataType) {
    try {
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() >= 200 && response.statusCode() < 300) {
        com.google.gson.JsonObject jsonObject =
            gson.fromJson(response.body(), com.google.gson.JsonObject.class);

        ApiResponse<List<T>> apiResponse = new ApiResponse<>();

        if (jsonObject.has("message")) {
          apiResponse.setMessage(jsonObject.get("message").getAsString());
        }

        if (jsonObject.has("timestamp")) {
          apiResponse.setTimestamp(jsonObject.get("timestamp").getAsString());
        }

        if (jsonObject.has("data")) {
          com.google.gson.JsonArray jsonArray = jsonObject.getAsJsonArray("data");
          List<T> dataList = new java.util.ArrayList<>();

          for (com.google.gson.JsonElement element : jsonArray) {
            T item = gson.fromJson(element, dataType);
            dataList.add(item);
          }

          apiResponse.setData(dataList);
        }

        return apiResponse;

      } else {
        handleErrorResponse(response);
        throw new ApiException(
            "Request failed with status code: " + response.statusCode(), response.statusCode());
      }

    } catch (ApiException e) {
      throw e;
    } catch (Exception e) {
      throw new ApiException("Failed to communicate with server: " + e.getMessage(), 0, e);
    }
  }
}
