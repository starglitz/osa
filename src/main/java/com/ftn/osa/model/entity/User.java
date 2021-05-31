package com.ftn.osa.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(nullable = false)
    private String name;

//    @Column(nullable = false)
    private String surname;

//    @Column(nullable = false)
    private String username;

//    @Column(nullable = false)
    private String password;

//    @Column(nullable = false)
    private boolean enabled;

//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Long id, String name, String surname, String username, String password, boolean enabled, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    public User(String name, String surname, String username,
                String password, boolean enabled, Role role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }
}
