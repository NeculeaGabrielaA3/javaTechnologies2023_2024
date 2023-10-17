package com.example.demo1.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(
        filterName = "CustomResultDecoratorFilter",
        urlPatterns = {"/result.jsp"}
)
public class CustomResultDecoratorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PrintWriter out = servletResponse.getWriter();
        out.println();

        CharResponseWrapper wrappedResponse = new CharResponseWrapper((HttpServletResponse) servletResponse);

        filterChain.doFilter(servletRequest, wrappedResponse);

        String modifiedContent = decorateResultPage(wrappedResponse.toString());

        wrappedResponse.setContentLength(modifiedContent.length());
        out.write(modifiedContent);
    }

    private String decorateResultPage(String content) {
        String prelude = "<!DOCTYPE html>\n<html>\n<head>\n<title>Custom Decorated Page</title>\n</head>\n<body style=\"background-color: #f5f5f5; font-family: Verdana, sans-serif; margin: 0; padding: 0;\">\n";
        String coda = "</body>\n</html>\n";

        return prelude + content + coda;
    }
}

class CharResponseWrapper extends HttpServletResponseWrapper {
    CharResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}
