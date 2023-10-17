package com.example.demo1.model;

import jakarta.servlet.http.Part;

public class Input {

    private Part file;

    public Input(Part file) {
        this.file = file;
    }

    public Part getInput() {
        return this.file;
    }

    public void setInput(Part file) {
        this.file = file;
    }
}
