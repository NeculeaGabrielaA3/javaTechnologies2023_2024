package com.example.laborator7.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "TimetablePreference.findAll", query = "SELECT t FROM Timetable t"),
        @NamedQuery(name = "TimetablePreference.deleteById", query = "DELETE FROM Timetable t WHERE t.id = :id"),
        @NamedQuery(name = "TimetablePreference.findTimeslot", query = "SELECT t FROM Timetable t WHERE t.dayOfWeek = ?1 AND t.hourOfDay = ?2")
})
public class Timetable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String registrationNumber;
    @OneToOne
    private User teacher;
    private String content;
    private String dayOfWeek;
    private String hourOfDay;

    public Timetable(String registrationNumber, User user, String content, String dayOfWeek, String hourOfDay) {
        this.registrationNumber = registrationNumber;
        this.teacher = user;
        this.content = content;
        this.dayOfWeek = dayOfWeek;
        this.hourOfDay = hourOfDay;
    }
}
