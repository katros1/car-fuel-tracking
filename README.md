# Car Management & Fuel Tracking System

Car Management & Fuel Tracking system consist of a **Spring Boot backend** with manual servlet integration and a **command-line interface (CLI)** client that communicates via HTTP. Allows users to manage cars and fuel entries via terminal commands.

## Features

- **Create Cars**: Register new vehicles in the system
- **Add Fuel Entries**: Track refueling events with liters, price, and odometer readings
- **View Statistics**: Get fuel consumption statistics for your vehicles


## Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- Terminal/Command Prompt

### Quick Start Guide

#### **Step 1: Build Both Projects**
```bash
# Build backend
cd fuel-tracker-backend
mvn clean package
cd ..

# Build CLI
cd fuel-tracker-cli
mvn clean package
cd ..
```

#### **Step 2: Start the Backend Server**
```bash
cd fuel-tracker-backend
mvn spring-boot:run
```

The server will start on `http://localhost:8080`

#### **Step 3: Use the CLI (in a new terminal)**

**Create a car:**
**Syntax:**
```bash
java -jar target/fuel-tracker-cli.jar create-car --brand <brand> --model <model> --year <year>
```

**Example:**
```bash
cd fuel-tracker-cli
java -jar target/fuel-tracker-cli.jar create-car --brand Toyota --model Corolla --year 2018
```

**Add fuel entry:**
**Syntax:**
```bash
java -jar target/fuel-tracker-cli.jar add-fuel --carId <id> --liters <liters> --price <price> --odometer <odometer_reading>
```

**Example:**
```bash
java -jar target/fuel-tracker-cli.jar add-fuel --carId 1 --liters 40 --price 52.5 --odometer 45000
```

**View statistics:**
**Syntax:**
```bash
java -jar target/fuel-tracker-cli.jar fuel-stats --carId <id>
```

**Example:**
```bash
java -jar target/fuel-tracker-cli.jar fuel-stats --carId 1
```

## API Endpoints

### REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/cars` | Create a new car |
| GET | `/api/cars` | List all cars |
| POST | `/api/cars/{id}/fuel` | Add fuel entry |
| GET | `/api/cars/{id}/fuel/stats` | Get fuel statistics |

### Manual Servlet Endpoint

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/servlet/fuel-stats?carId={id}` | Get fuel statistics (manual servlet) |


## Configuration

### Backend Configuration
- **Port**: `8080` (configurable in `backend/src/main/resources/application.properties`)

### CLI Configuration
- **Backend URL**: `http://localhost:8080` (configurable in `ApiService.java`)

