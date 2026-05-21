package com.peluquerias.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class rol {

    @Id
    private Short id;

    @Column(nullable = false, unique = true)
    private String nombre;

    public rol() {}

    public Short getId() { return id; }
    public void setId(Short id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}