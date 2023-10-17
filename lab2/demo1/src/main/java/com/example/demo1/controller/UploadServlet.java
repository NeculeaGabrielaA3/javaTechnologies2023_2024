package com.example.demo1.controller;

import com.example.demo1.model.Input;
import com.example.demo1.model.Output;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    private Input input;
    private Output output;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        input = new Input(request.getPart("graphFile"));

        response.setContentType("text/html");

        output = new Output(input);
        output.parse();

        request.setAttribute("graph", this.output.getGraph());
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
