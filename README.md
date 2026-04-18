# Smart Campus API

## Module
Client-Server Architectures

## Overview
This project is a REST API built using JAX-RS for a Smart Campus system.

## Technologies Used
- Java
- JAX-RS (Jersey)
- Maven
- NetBeans 18

## Progress
- Set up Maven project
- Added required dependencies (Jersey, Grizzly)
- Configured REST application
- Created first test endpoint
- Successfully ran the API on localhost
- Created the Room model
- Created the Sensor model
- Created the SensorReading model
- Added GET /rooms/{id} endpoint
- Added DELETE /rooms/{id} endpoint
- Added SensorResource with GET, POST, GET by ID, and DELETE endpoints
- Linked sensors to rooms using roomId and sensorIds
- Implemented SensorReadingResource
- Added endpoints to get and create sensor readings
- Linked readings to sensors and update current value dynamically

## Running the API

1. Open the project in NetBeans
2. Run the SmartCampusApi class
3. Open a browser and go to:
   http://localhost:8081/api/v1/

Expected output:
API is working