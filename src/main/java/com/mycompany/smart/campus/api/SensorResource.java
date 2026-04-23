package com.mycompany.smart.campus.api;

import com.mycompany.smart.campus.api.model.Room;
import com.mycompany.smart.campus.api.model.Sensor;
import com.mycompany.smart.campus.api.storage.DataStore;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;


@Path("/sensors")
public class SensorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensors(@QueryParam("type") String type) {
        if (type == null || type.isEmpty()) {
        return Response.ok(DataStore.sensors.values()).build();
    }

    java.util.List<Sensor> filteredSensors = new java.util.ArrayList<>();

    for (Sensor sensor : DataStore.sensors.values()) {
        if (sensor.getType() != null && sensor.getType().equalsIgnoreCase(type)) {
            filteredSensors.add(sensor);
        }
    }

    if (filteredSensors.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("No sensors found for type: " + type)
                .build();
    }

    return Response.ok(filteredSensors).build();
}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor) {
        Room room = DataStore.rooms.get(sensor.getRoomId());

    if (room == null) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Room does not exist")
                .build();
    }

    if (DataStore.sensors.containsKey(sensor.getId())) {
        return Response.status(Response.Status.CONFLICT)
                .entity("Sensor with this ID already exists")
                .build();
    }

    DataStore.sensors.put(sensor.getId(), sensor);
    room.getSensorIds().add(sensor.getId());

    return Response.status(Response.Status.CREATED).entity(sensor).build();
}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorById(@PathParam("id") String id) {
        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(sensor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {
        Sensor removed = DataStore.sensors.remove(id);

        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Room room = DataStore.rooms.get(removed.getRoomId());
        if (room != null) {
            room.getSensorIds().remove(id);
        }

        return Response.noContent().build();
    }
}