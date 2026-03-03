package org.example.proyecto_tfg.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private int id_factura;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "total")
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "dni", nullable = false)
    private Cliente id_cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bicicleta", referencedColumnName = "id_ref", nullable = true)
    private Bicicleta id_bicicleta;


    public Factura() {
    }

    public Factura(LocalDate fecha, BigDecimal total, Cliente id_cliente, Bicicleta id_bicicleta) {
        this.fecha = fecha;
        this.total = total;
        this.id_cliente = id_cliente;
        this.id_bicicleta = id_bicicleta;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Cliente getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Cliente id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Bicicleta getId_bicicleta() {
        return id_bicicleta;
    }

    public void setId_bicicleta(Bicicleta id_bicicleta) {
        this.id_bicicleta = id_bicicleta;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id_factura=" + id_factura +
                ", fecha=" + fecha +
                ", total=" + total +
                ", id_cliente=" + id_cliente +
                ", id_bicicleta=" + id_bicicleta +
                '}';
    }
}