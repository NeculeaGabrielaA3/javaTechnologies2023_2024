package com.example.demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Logger;

@WebServlet("/randomTreeMatrix")
public class GenerateTreeMatrixServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(GenerateTreeMatrixServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String numVerticesStr = request.getParameter("numVertices");

        int numVertices = Integer.parseInt(numVerticesStr);

        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        if (numVertices >= 1) {
            List<Edge> edges = generateTreeEdges(numVertices);

            int[][] adjacencyMatrix2 = new int[numVertices][numVertices];
            for (Edge edge : edges) {
                adjacencyMatrix2[edge.from][edge.to] = 1;
                adjacencyMatrix2[edge.to][edge.from] = 1;
            }

            out.println("<h2>Adjacency Matrix:</h2>");
            out.println("<table border='1'>");
            for (int i = 0; i < numVertices; i++) {
                out.println("<tr>");
                for (int j = 0; j < numVertices; j++) {
                    out.println("<td>" + adjacencyMatrix2[i][j] + "</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            out.println("<p>Please enter a valid number of vertices (>= 1).</p>");
        }

        serverInfoAboutRequest(request);

        out.println("</body></html>");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Random Tree</title></head>");
        out.println("<body>");

        out.println("<h1>Random Tree - Generate</h1>");
        out.println("<form name=\"numVerticesForm\" method=\"post\">");
        out.println("Write the number of Vertices here: <input type=\"text\" name=\"numVertices\"/> <br/>");
        out.println("<input type=\"submit\" value=\"Generate Tree\" />");

        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        serverInfoAboutRequest(request);
    }

    private void serverInfoAboutRequest(HttpServletRequest request) {

        String httpMethod = request.getMethod();
        logger.info("HTTP Method: " + httpMethod);

        // Log client IP address
        String clientIpAddress = request.getRemoteAddr();
        logger.info("Client IP Address: " + clientIpAddress);

        // Log User-Agent header
        String userAgent = request.getHeader("User-Agent");
        logger.info("User-Agent: " + userAgent);

        // Log client languages
        Enumeration<Locale> locales = request.getLocales();
        StringBuilder languages = new StringBuilder();
        while (locales.hasMoreElements()) {
            Locale locale = locales.nextElement();
            languages.append(locale.toString()).append(", ");
        }
        logger.info("Client Languages: " + languages.toString());

        // Log request parameters
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (String paramValue : paramValues) {
                logger.info("Request Parameter - " + paramName + ": " + paramValue);
            }
        }
    }

    private static class Edge {
        int from, to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    private static List<Edge> generateTreeEdges(int n) {
        List<Edge> edges = new ArrayList<>();
        boolean[] visited = new boolean[n];
        Random random = new Random();

        dfs(0, visited, edges, random, n);

        return edges;
    }

    private static void dfs(int vertex, boolean[] visited, List<Edge> edges, Random random, int n) {
        visited[vertex] = true;

        while (edges.size() < n - 1) {
            int nextVertex = random.nextInt(n);
            if (!visited[nextVertex]) {
                edges.add(new Edge(vertex, nextVertex));
                dfs(nextVertex, visited, edges, random, n);
            }
        }
    }
}
