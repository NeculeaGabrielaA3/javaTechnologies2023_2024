package com.example.demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Logger;

@WebServlet("/randomTreeMatrix2")
public class GenerateRandomTreeMatrix2 extends HttpServlet {

    private static final Logger logger = Logger.getLogger(GenerateTreeMatrixServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String numVerticesStr = request.getParameter("numVertices");

        int numVertices = Integer.parseInt(numVerticesStr);

        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        if (numVertices >= 1) {

            int[][] adjacencyMatrix2 = generateRandomTreeAdjacencyMatrix(numVertices);

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

        //serverInfoAboutRequest(request);

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
        out.println("Number of Vertices: <input type=\"text\" name=\"numVertices\"/> <br/>");
        out.println("<input type=\"submit\" value=\"Generate Tree\" />");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

    }
    public static int[][] generateRandomTreeAdjacencyMatrix(int numNodes) {
        if (numNodes <= 0) {
            return new int[0][0];
        }

        Random random = new Random();
        int[][] adjacencyMatrix = new int[numNodes][numNodes];

        // Initialize the matrix with zeros
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }

        boolean[] visited = new boolean[numNodes];
        generateRandomTree(adjacencyMatrix, 0, numNodes, visited, random);

        return adjacencyMatrix;
    }

    public static void generateRandomTree(int[][] adjacencyMatrix, int currentNode, int remainingNodes, boolean[] visited, Random random) {
        visited[currentNode] = true;

        while (remainingNodes > 1) {
            int nextNode = random.nextInt(adjacencyMatrix.length);

            if (!visited[nextNode] && nextNode != currentNode && adjacencyMatrix[currentNode][nextNode] == 0) {
                // Create an edge between currentNode and nextNode
                adjacencyMatrix[currentNode][nextNode] = 1;
                adjacencyMatrix[nextNode][currentNode] = 1;

                // Recursively generate the subtree rooted at nextNode
                generateRandomTree(adjacencyMatrix, nextNode, remainingNodes - 1, visited, random);
            }
        }
    }


}
