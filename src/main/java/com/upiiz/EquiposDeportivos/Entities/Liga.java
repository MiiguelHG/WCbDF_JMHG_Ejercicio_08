package com.upiiz.EquiposDeportivos.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Liga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String pais;
    private String presidente;

    //Una liga tiene muchos equipos
    @OneToMany(targetEntity = Equipo.class, fetch = FetchType.LAZY, mappedBy = "liga")
//    @JsonManagedReference
    @JsonIgnoreProperties("liga")
    private List<Equipo> equipos;
}
