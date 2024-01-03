package com.example.laborator7.service;

import javax.ws.rs.*;

@Path("/helloworld") // --> Resource Identifier
public class HelloWorldResource {
    @GET // --> Process HTTP GET requests
    @Produces("text/plain") // --> MIME Media type
    public String getMessage() {
        return "Hello World";
    }

    @PUT
    @Consumes("text/plain")
    public void setMessage(String msg) {

    }
}
