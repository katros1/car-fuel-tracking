package com.codehills.fueltracker.config;

import com.codehills.fueltracker.service.CarService;
import com.codehills.fueltracker.servlet.FuelTrackerServlet;
import jakarta.servlet.ServletContext;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

  private final CarService carService;
  private final ServletContext servletContext;

  public ServletConfig(CarService carService, ServletContext servletContext) {
    this.carService = carService;
    this.servletContext = servletContext;

    servletContext.setAttribute("carService", carService);
  }

  @Bean
  public ServletRegistrationBean<FuelTrackerServlet> fuelStatsServlet() {
    ServletRegistrationBean<FuelTrackerServlet> registrationBean =
        new ServletRegistrationBean<>(new FuelTrackerServlet(), "/servlet/fuel-stats");

    registrationBean.setLoadOnStartup(1);
    registrationBean.setName("FuelTrackerServlet");

    return registrationBean;
  }
}
