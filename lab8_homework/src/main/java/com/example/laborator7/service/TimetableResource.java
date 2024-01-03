package com.example.laborator7.service;

import com.example.laborator7.bean.TimetableBean;
import com.example.laborator7.dao.impl.TimetableDaoImpl;
import com.example.laborator7.dao.impl.UserDaoImpl;
import com.example.laborator7.entity.Timetable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import io.swagger.annotations.*;

@Path("/timetables")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class TimetableResource {
    @Inject
    TimetableDaoImpl timetableDaoImpl;
    @Inject
    private UserDaoImpl userDaoImpl;

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
    @ApiOperation(value = "Creates a timetable",
            response = Timetable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the timetable. The location is in headers."),
            @ApiResponse(code = 500, message = "Timetable couldn't be saved.")})
    public Response createTimetable(TimetableBean timetable) throws Exception {
        Timetable addedTimetable = timetableDaoImpl.create(timetable.ConvertToEntity());
        URI uri = UriBuilder.fromResource(this.getClass())
                .path("" + addedTimetable.getId()).build();
        return Response.created(uri).entity(addedTimetable).build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete a timetable", notes = "Deletes the timetable with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Timetable deleted successfully"),
            @ApiResponse(code = 404, message = "Timetable not found")
    })
    public Response deleteTimetable(@PathParam("id") Long id) {
        boolean wasDeleted = timetableDaoImpl.delete(id);

        if (wasDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Timetable with ID " + id + " not found.").build();
        }
    }

    @GET
    @Path("/user/{userId}")
    @ApiOperation(value = "Get timetables for a user", notes = "Returns a list of timetables for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Timetables found"),
            @ApiResponse(code = 404, message = "No timetables found for the user")
    })
    public Response getTimetablesForUser(@PathParam("userId") Integer userId) {
        List<Timetable> timetables = timetableDaoImpl.getTimetablesForUser(userId);
        if (timetables.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No timetables found for user with ID " + userId).build();
        }
        return Response.ok(timetables).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update an existing timetable", notes = "Updates the timetable with the given ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Timetable updated successfully"),
            @ApiResponse(code = 404, message = "Timetable not found")
    })
    public Response updateTimetable(@PathParam("id") Long id, TimetableBean timetableBean) {
        Timetable existingTimetable = timetableDaoImpl.get(id);
        if (existingTimetable == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Timetable with ID " + id + " not found.").build();
        }
        existingTimetable.setContent(timetableBean.getContent());
        existingTimetable.setDayOfWeek(timetableBean.getDayOfWeek());
        existingTimetable.setHourOfDay(timetableBean.getHourOfDay());

        Timetable updatedTimetable = timetableDaoImpl.update(existingTimetable);
        return Response.ok(updatedTimetable).build();
    }

    @GET
    @Produces("text/plain")
    public String getMessage() {
        return "Timetables";
    }

    @PUT
    @Consumes("text/plain")
    public void setMessage(String msg) {

    }
}