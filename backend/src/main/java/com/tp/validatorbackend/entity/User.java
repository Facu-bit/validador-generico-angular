package com.tp.validatorbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String dni;

    // ─── Constructores ───────────────────────────────────────────
    public User() {}

    public User(String email, String dni) {
        this.email = email;
        this.dni   = dni;
    }

    // ─── Getters y Setters ───────────────────────────────────────
    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
}
