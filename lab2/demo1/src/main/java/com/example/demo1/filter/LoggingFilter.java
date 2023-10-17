package com.example.demo1.filter;

import jakarta.servlet.*;
import java.io.*;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

@WebFilter(
        filterName = "LoggingFilter",
        urlPatterns = {"/UploadServlet"}
)
public class LoggingFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("Request received for: " + httpRequest.getRequestURI());
        chain.doFilter(request, response);
    }

}
