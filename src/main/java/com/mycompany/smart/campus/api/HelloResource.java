package com.mycompany.smart.campus.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloResource {

    @GET
    public String test() {
        return "API is working";
    }
}