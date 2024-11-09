package com.upiiz.EquiposDeportivos.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    //Un equipo tiene un entrenador
    @OneToOne(targetEntity = Entrenador.class)
    private Entrenador entrenador;

    //Un equipo pertenece a una liga
    @ManyToOne(targetEntity = Liga.class)
//    @JsonBackReference
    @JsonIgnoreProperties("equipos")
    private Liga liga;

    //Un quipo tiene muchos jugadores
    @OneToMany(targetEntity = Jugador.class, fetch = FetchType.LAZY, mappedBy = "equipo")
    private List<Jugador> jugadores;

    // Muchos equipos tienen muchas competencias
    @ManyToMany(targetEntity = Competencia.class, fetch = FetchType.LAZY)
    private List<Competencia> competencias;
}
