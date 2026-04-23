package com.mycompany.smart.campus.api;

import com.mycompany.smart.campus.api.model.Room;
import com.mycompany.smart.campus.api.storage.DataStore;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;

import java.util.Collection;

@Path("/rooms")
public class RoomResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Room> getRooms() {
        return DataStore.rooms.values();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {
        if (DataStore.rooms.containsKey(room.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Room with this ID already exists")
                    .build();
        }

    DataStore.rooms.put(room.getId(), room);
    return Response.status(Response.Status.CREATED).entity(room).build();
}
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomById(@PathParam("id") String id) {
        Room room = DataStore.rooms.get(id);

        if (room == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(room).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("id") String id) {
        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!room.getSensorIds().isEmpty()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"Room cannot be deleted because it still has sensors assigned.\"}")
                    .build();
    }

    DataStore.rooms.remove(id);
    return Response.noContent().build();
}
}