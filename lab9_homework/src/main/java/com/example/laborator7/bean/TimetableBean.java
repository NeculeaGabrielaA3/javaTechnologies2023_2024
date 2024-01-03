package com.example.laborator7.bean;

import com.example.laborator7.entity.Timetable;
import com.example.laborator7.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Setter
@Getter
@ManagedBean(name = "timetableBean")
@RequestScoped
public class TimetableBean {


    private String registrationNumber;
    private User teacher;
    @Size(min = 4, max=30, message = "The name should have at least 4 characters.")
    private String content;
    @NotEmpty
    private String dayOfWeek;
    @NotEmpty
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$")
    private String hourOfDay;
    public TimetableBean() {

    }

    public Timetable ConvertToEntity() {
        return new Timetable(this.registrationNumber, this.teacher, this.content, this.dayOfWeek, this.hourOfDay);
    }
}
