package org.example.proyecto_tfg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pieza")
public class Pieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pieza")
    private Integer id_pieza;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "marca")
    private String marca;

    @Column(name = "stock")
    private int stock;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "precio", precision = 10, scale = 2)
    private float precio;



    public Pieza() {
    }

    public Pieza(Integer id_pieza, String modelo, String marca, int stock, String tipo, float precio) {
        this.id_pieza = id_pieza;
        this.modelo = modelo;
        this.marca = marca;
        this.stock = stock;
        this.tipo = tipo;
        this.precio = precio;
    }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

    public Integer getId_pieza() {
        return id_pieza;
    }

    public void setId_pieza(Integer id_pieza) {
        this.id_pieza = id_pieza;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Pieza{" +
                "id_pieza=" + id_pieza +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", stock=" + stock +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}