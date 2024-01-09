package com.example.laborator7.filter;

import com.example.laborator7.entity.Timetable;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@Provider
public class CachingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger LOGGER = Logger.getLogger(CachingFilter.class.getName());
    private volatile List<Timetable> cachedTimetables = null;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("[FILTER]Request filter invoked for path: " + requestContext.getUriInfo().getPath());

        if (cachedTimetables != null) {
            Response response = Response.ok(cachedTimetables, MediaType.APPLICATION_JSON).build();
            LOGGER.info("[FILTER]The cache was used." );
            requestContext.abortWith(response);
        }

    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        LOGGER.info("[FILTER]Response filter invoked for path: " + requestContext.getUriInfo().getPath() + ".");

        if (responseContext.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL && requestContext.getMethod().equalsIgnoreCase("GET")
                && isRequestForTimetables(requestContext) && cachedTimetables == null) {
            LOGGER.info("[FILTER]Caching starting...");
            Object entity = responseContext.getEntity();
            if (entity instanceof List<?>) {
                cachedTimetables = new CopyOnWriteArrayList<>((List<Timetable>)entity);
            }
        } else if(requestContext.getMethod().equalsIgnoreCase("POST") || requestContext.getMethod().equalsIgnoreCase("PUT")
                || requestContext.getMethod().equalsIgnoreCase("DELETE")) {
            clearCache();
        }
    }

    private boolean isRequestForTimetables(ContainerRequestContext request) {
        String path = request.getUriInfo().getPath();
        return path.equals("http://localhost:8080/Laborator7-1.0-SNAPSHOT/resources/timetables");
    }

    public void clearCache() {
        cachedTimetables = null;
    }
}
