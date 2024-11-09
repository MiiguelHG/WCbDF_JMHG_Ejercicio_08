package com.upiiz.EquiposDeportivos.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer edad;
    private String nacionalidad;
    private String posicion;

    //Muchos jugadores pertenecen a un equipo
    @ManyToOne(targetEntity = Equipo.class)
    @JsonIgnoreProperties("jugadores")
    private Equipo equipo;
}
