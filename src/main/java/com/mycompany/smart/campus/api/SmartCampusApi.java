package com.mycompany.smart.campus.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

import com.mycompany.smart.campus.api.model.Room;
import com.mycompany.smart.campus.api.storage.DataStore;

public class SmartCampusApi {

    public static final String BASE_URI = "http://localhost:8081/api/v1/";

    public static HttpServer startServer() {
        final ResourceConfig config = new ResourceConfig()
                .packages("com.mycompany.smart.campus.api");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    public static void main(String[] args) throws IOException {
        Room r1 = new Room("R1", "Library", 50);
        DataStore.rooms.put(r1.getId(), r1);
        
        HttpServer server = startServer();
        System.out.println("Server started at " + BASE_URI);
        System.out.println("Press Enter to stop the server...");
        System.in.read();
        server.shutdownNow();
    }
}