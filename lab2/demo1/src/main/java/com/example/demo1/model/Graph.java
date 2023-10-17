package com.example.demo1.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Integer order;

    private Integer size;

    private Integer numberOfConnectedComponents;

    private boolean[] visited;

    private final Map<Integer, List<Integer>> graphEdges = new HashMap<>();

    public Integer getOrder() {
        return this.order;
    }

    public Integer getSize() {
        return this.size;
    }

    public Integer getNumberOfConnectedComponents() {
        return this.numberOfConnectedComponents;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setNumberOfConnectedComponents(Integer numberOfConnectedComponents) {
        this.numberOfConnectedComponents = numberOfConnectedComponents;
    }

    public  Map<Integer, List<Integer>> getGraphEdges() {
        return graphEdges;
    }

    public void addEdgeToGraph(int vertexA, int vertexB) {
        if (!graphEdges.containsKey(vertexA)) {
            graphEdges.put(vertexA, new ArrayList<>());
        }
        if (!graphEdges.containsKey(vertexB)) {
            graphEdges.put(vertexB, new ArrayList<>());
        }
        graphEdges.get(vertexA).add(vertexB);
        graphEdges.get(vertexB).add(vertexA);
    }


    public void countConnectedComponents() {
        visited = new boolean[graphEdges.size() + 1];
        int components = 0;

        for (int vertex : graphEdges.keySet()) {
            if (!visited[vertex]) {
                dfs(vertex);
                components++;
            }
        }
        this.numberOfConnectedComponents = components;
    }

    private void dfs(int vertex) {
        visited[vertex] = true;
        if (graphEdges.containsKey(vertex)) {
            for (int neighbor : graphEdges.get(vertex)) {
                if (!visited[neighbor]) {
                    dfs(neighbor);
                }
            }
        }
    }
}
