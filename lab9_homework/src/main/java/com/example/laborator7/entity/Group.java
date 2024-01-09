package com.example.laborator7.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "groups")
@NamedQueries({
        @NamedQuery(name = "Group.findByGroupId", query = "select g from Group g where g.id=?1")
})
public class Group {
    @Id
    private String id;
    private String name;
}