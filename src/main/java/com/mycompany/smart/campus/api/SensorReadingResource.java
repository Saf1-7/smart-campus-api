package com.mycompany.smart.campus.api;

import com.mycompany.smart.campus.api.model.Sensor;
import com.mycompany.smart.campus.api.model.SensorReading;
import com.mycompany.smart.campus.api.storage.DataStore;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/sensors/{sensorId}/readings")
public class SensorReadingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings(@PathParam("sensorId") String sensorId) {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<SensorReading> sensorReadings =
                DataStore.readings.getOrDefault(sensorId, new ArrayList<>());

        return Response.ok(sensorReadings).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(@PathParam("sensorId") String sensorId, SensorReading reading) {
        Sensor sensor = DataStore.sensors.get(sensorId);

    if (sensor == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity("Sensor is in maintenance mode")
                .build();
    }

    List<SensorReading> sensorReadings =
            DataStore.readings.computeIfAbsent(sensorId, k -> new ArrayList<>());

    sensorReadings.add(reading);
    sensor.setCurrentValue(reading.getValue());

    return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}