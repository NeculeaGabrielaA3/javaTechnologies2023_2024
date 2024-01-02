package com.example.laborator7.bean;

import javax.enterprise.inject.Produces;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import java.util.UUID;

@ApplicationScoped
public class RegistrationGenerator {

    @Produces
    String getRegistrationNumber() {
        return UUID.randomUUID().toString();
    }

}
