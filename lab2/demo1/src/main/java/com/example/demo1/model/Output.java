package com.example.demo1.model;

import com.example.demo1.GraphPropertiesService;
public class Output {

    private final Input input;

    GraphPropertiesService graphPropertiesService;

    public void parse(){
        graphPropertiesService.parse(input.getInput());
        graphPropertiesService.findConnectedComponents();
    }

    public Graph getGraph() {
        return graphPropertiesService.getGraph();
    }

    public Output(Input input) {
        graphPropertiesService = new GraphPropertiesService();
        this.input = input;
    }
}