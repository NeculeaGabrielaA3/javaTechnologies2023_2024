package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "numberServlet", value = "/digits-servlet")
public class DigitsOfNumberServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String numberStr = request.getParameter("number");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            int number = Integer.parseInt(numberStr);

            if (number >= 0) {
                out.println("<p>Number: " + number + "</p>");
                out.println("<ol>");
                List<Integer> digitsList = new ArrayList<>();
                while(number != 0) {
                    digitsList.add(number%10);
                    number /= 10;
                }
                Collections.reverse(digitsList);
                for(Integer digit: digitsList) {
                    out.println("<li>" + digit + "</li>");
                }
                out.println("</ol>");
            } else {
                out.println("<p>Please enter a valid positive number.</p>");
            }
        } catch (NumberFormatException e) {
            out.println("<p>Please enter a valid number.</p>");
        }

        out.println("</body></html>");
    }
}
