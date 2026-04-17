package org.example.proyecto_tfg.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "usa")
public class Usa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usa")
    private Integer id_usa;

    @ManyToOne
    @JoinColumn(name="id_pieza", referencedColumnName="id_pieza", nullable=false)
    private Pieza id_pieza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bicicleta", referencedColumnName = "id_ref", nullable = false)
    private Bicicleta id_bicicleta;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "id_cliente")
    private String id_cliente;


    public Usa() {
    }

    public Usa(Integer id_usa, Pieza id_pieza, Bicicleta id_bicicleta, int cantidad, String id_cliente) {
        this.id_usa = id_usa;
        this.id_pieza = id_pieza;
        this.id_bicicleta = id_bicicleta;
        this.cantidad = cantidad;
        this.id_cliente = id_cliente;
    }

    public Integer getId_usa() {
        return id_usa;
    }

    public void setId_usa(Integer id_usa) {
        this.id_usa = id_usa;
    }

    public Pieza getId_pieza() {
        return id_pieza;
    }

    public void setId_pieza(Pieza id_pieza) {
        this.id_pieza = id_pieza;
    }

    public Bicicleta getId_bicicleta() {
        return id_bicicleta;
    }

    public void setId_bicicleta(Bicicleta id_bicicleta) {
        this.id_bicicleta = id_bicicleta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public String toString() {
        return "Usa{" +
                "id_usa=" + id_usa +
                ", id_pieza=" + id_pieza +
                ", id_bicicleta=" + id_bicicleta +
                ", cantidad=" + cantidad +
                ", id_cliente='" + id_cliente + '\'' +
                '}';
    }
}