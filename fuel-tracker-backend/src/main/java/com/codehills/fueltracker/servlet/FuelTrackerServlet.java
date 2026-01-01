package com.codehills.fueltracker.servlet;

import com.codehills.fueltracker.dto.ApiResponse;
import com.codehills.fueltracker.dto.ErrorResponse;
import com.codehills.fueltracker.dto.FuelStatsResponse;
import com.codehills.fueltracker.exception.ResourceNotFoundException;
import com.codehills.fueltracker.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FuelTrackerServlet extends HttpServlet {

  private CarService carService;
  private ObjectMapper objectMapper;

  @Override
  public void init() throws ServletException {
    super.init();

    this.carService = (CarService) getServletContext().getAttribute("carService");

    if (this.carService == null) {
      throw new ServletException("CarService not found in ServletContext");
    }

    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule());

    log("FuelTrackerServlet initialized successfully");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String carIdParam = request.getParameter("carId");

    if (carIdParam == null || carIdParam.trim().isEmpty()) {
      sendErrorResponse(
          response,
          HttpServletResponse.SC_BAD_REQUEST,
          "Bad Request",
          "Missing required parameter: carId",
          request.getRequestURI());
      return;
    }

    Long carId;
    try {
      carId = Long.parseLong(carIdParam);
    } catch (NumberFormatException e) {
      sendErrorResponse(
          response,
          HttpServletResponse.SC_BAD_REQUEST,
          "Bad Request",
          "Invalid carId format. Must be a valid number.",
          request.getRequestURI());
      return;
    }

    try {
      FuelStatsResponse stats = carService.getFuelStats(carId);

      String message = String.format("Fuel statistics retrieved for car ID %d", carId);
      ApiResponse<FuelStatsResponse> apiResponse = ApiResponse.success(message, stats);

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      response.setStatus(HttpServletResponse.SC_OK);

      String jsonResponse = objectMapper.writeValueAsString(apiResponse);
      PrintWriter out = response.getWriter();
      out.print(jsonResponse);
      out.flush();

      log("Successfully returned fuel stats for car ID: " + carId);

    } catch (ResourceNotFoundException e) {
      sendErrorResponse(
          response,
          HttpServletResponse.SC_NOT_FOUND,
          "Not Found",
          e.getMessage(),
          request.getRequestURI());

    } catch (Exception e) {
      log("Unexpected error in FuelTrackerServlet", e);
      sendErrorResponse(
          response,
          HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Internal Server Error",
          "An unexpected error occurred: " + e.getMessage(),
          request.getRequestURI());
    }
  }

  // Helper method
  private void sendErrorResponse(
      HttpServletResponse response, int statusCode, String error, String message, String path)
      throws IOException {

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    response.setStatus(statusCode);

    ErrorResponse errorResponse = new ErrorResponse(statusCode, error, message, path);

    String jsonError = objectMapper.writeValueAsString(errorResponse);
    PrintWriter out = response.getWriter();
    out.print(jsonError);
    out.flush();

    log(String.format("Returned error response: %d - %s", statusCode, message));
  }

  @Override
  public void destroy() {
    log("FuelTrackerServlet is being destroyed");
    super.destroy();
  }
}
