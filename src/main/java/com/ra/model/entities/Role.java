package com.ra.model.entities;

import com.ra.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RoleType name;
}
