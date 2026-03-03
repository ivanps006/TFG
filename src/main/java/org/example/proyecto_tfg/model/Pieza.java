package org.example.proyecto_tfg.model;


import jakarta.persistence.*;

@Entity
@Table (name="pieza")
public class Pieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pieza")
    private int id_pieza;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "marca")
    private String marca;
    @Column(name = "stock")
    private int stock;
    @Column (name = "tipo")
    private String tipo;




}
