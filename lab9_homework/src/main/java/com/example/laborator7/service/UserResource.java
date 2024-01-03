package com.example.laborator7.service;

import com.example.laborator7.bean.UserBean;
import com.example.laborator7.dao.impl.UserDaoImpl;
import com.example.laborator7.entity.User;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("/users")
public class UserResource {

    @Inject
    UserDaoImpl userDaoImpl;

    @Context
    UriInfo uriInfo;

    @Context
    HttpServletResponse response;

    public UserResource() {
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserBean userbean) {
        try {
            userDaoImpl.create(userbean.ConvertToEntity());
            URI loginUri = uriInfo.getBaseUriBuilder().path("login.xhtml").build();
            return Response.seeOther(loginUri).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserBean userBean) {
        try {
            User user = userDaoImpl.login(userBean.ConvertToEntity());
            if(user != null) {
                NewCookie userCookie = createNewCookie("user", user.getUsername());
                NewCookie userIdCookie = createNewCookie("userId", String.valueOf(user.getUserId()));
                NewCookie isAdminCookie = createNewCookie("isAdmin", String.valueOf(user.isAdmin()));

                //URI redirectUri = uriInfo.getBaseUriBuilder().path(user.isAdmin() ? "viewTimetables.xhtml" : "addTimetable.xhtml").build();
                URI uri = UriBuilder.fromResource(this.getClass())
                        .path("").build();
                return Response.created(uri).entity(userBean.ConvertToEntity()).cookie(userCookie, userIdCookie, isAdminCookie).build();
                //return Response.seeOther(uri).cookie(userCookie, userIdCookie, isAdminCookie).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/logout")
    public Response logout() {
        NewCookie userCookie = createNewCookie("user", "");
        NewCookie isAdminCookie = createNewCookie("isAdmin", "");
        NewCookie userIdCookie = createNewCookie("userId", "");

        URI loginUri = uriInfo.getBaseUriBuilder().path("login.xhtml").build();
        return Response.seeOther(loginUri).cookie(userCookie, isAdminCookie, userIdCookie).build();
    }

    private NewCookie createNewCookie(String name, String value) {
        return new NewCookie(name, value, "/", null, null, 0, false);
    }
}