package com.example.demo1;

import com.example.demo1.model.Graph;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphPropertiesService {

    private final Graph graph;

    public GraphPropertiesService() {
        graph = new Graph();
    }

    public Graph getGraph() {
        return graph;
    }

    public void parse(Part file) {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            int graphOrder = -1;
            int graphSize = -1;

            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();

                if (trimmedLine.startsWith("p edge")) {
                    String[] parts = trimmedLine.split(" ");
                    if (parts.length >= 4) {
                        try {
                            graphOrder = Integer.parseInt(parts[2]);
                            this.graph.setOrder(graphOrder);
                            graphSize = Integer.parseInt(parts[3]);
                            this.graph.setSize(graphSize);

                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing graph order or size: " + e.getMessage());
                        }
                    }
                } else if (trimmedLine.startsWith("e ")) {
                    String[] parts = trimmedLine.split(" ");
                    int vertexA = Integer.parseInt(parts[1]);
                    int vertexB = Integer.parseInt(parts[2]);
                    graph.addEdgeToGraph(vertexA, vertexB);
                }
            }

            graph.countConnectedComponents();

            inputStream.close();
            reader.close();

        } catch (IOException e) {
            System.err.println("An error occurred while processing the input file: " + e.getMessage());
        }
    }

    public void findConnectedComponents() {
        graph.countConnectedComponents();
    }

}
