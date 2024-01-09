package com.example.laborator7.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "group_id")
    private String groupId;

    // Default constructor, getters, setters, hashCode, and equals methods
}
