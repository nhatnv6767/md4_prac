package com.ra.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Position extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();
}
