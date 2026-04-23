# Smart Campus API

## Module
Client-Server Architectures

## Overview
This project is a REST API developed using JAX-RS (Jersey) for a Smart Campus system.  
It allows management of rooms, sensors, and sensor readings, following a client-server architecture.

## Technologies Used
- Java
- JAX-RS (Jersey)
- Grizzly HTTP Server
- Maven
- NetBeans 18

## Features

### Rooms
- Get all rooms  
- Get a room by ID  
- Create a new room  
- Delete a room (cannot delete if it has sensors assigned)  

### Sensors
- Get all sensors  
- Get a sensor by ID  
- Create a new sensor  
- Delete a sensor  
- Sensors are linked to rooms using `roomId`  

### Sensor Readings
- Get all readings for a sensor  
- Add a new reading  
- Sensors can have multiple readings  
- Adding a reading updates the sensor’s current value  
- Sensors in maintenance mode cannot accept new readings  

## Data Models

### Room
- id  
- name  
- capacity  
- sensorIds  

### Sensor
- id  
- type  
- status  
- currentValue  
- roomId  

### SensorReading
- id  
- timestamp  
- value  

## Running the API

1. Open the project in NetBeans  
2. Run the `SmartCampusApi` class  
3. Open a browser and go to:

http://localhost:8081/api/v1/

## Example Endpoints

http://localhost:8081/api/v1/rooms  
http://localhost:8081/api/v1/rooms/R1  
http://localhost:8081/api/v1/sensors  
http://localhost:8081/api/v1/sensors/S1  
http://localhost:8081/api/v1/sensors/S1/readings  

## Example Requests

Create a room:
curl -X POST http://localhost:8081/api/v1/rooms

-H "Content-Type: application/json"
-d '{"id":"R2","name":"Lab","capacity":30}'


Create a sensor:
curl -X POST http://localhost:8081/api/v1/sensors

-H "Content-Type: application/json"
-d '{"id":"S2","type":"CO2","status":"ACTIVE","currentValue":400.0,"roomId":"R1"}'


Add a sensor reading:
curl -X POST http://localhost:8081/api/v1/sensors/S1/readings

-H "Content-Type: application/json"
-d '{"id":"SR2","timestamp":123456789,"value":30.0}'


Delete a room:
curl -X DELETE http://localhost:8081/api/v1/rooms/R1


## Notes
- Data is stored in memory using Java collections  
- Restarting the server resets all data  
- Basic error handling is implemented for invalid requests 
- Returns HTTP 409 Conflict for invalid operations (e.g. deleting a room with sensors) 

## Progress Summary
- Set up Maven project  
- Added Jersey and Grizzly dependencies  
- Configured REST application  
- Implemented Room, Sensor, and SensorReading models  
- Developed CRUD operations for rooms and sensors  
- Linked sensors to rooms  
- Implemented SensorReading resource  
- Enabled updating of sensor values based on readings
- Added sensor filtering using the type query parameter  