package org.example.proyecto_tfg.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "mantenimiento")
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_mantenimiento")
    private int id_mantenimiento;

    @Column(name="id_mecanico")
    String id_mecanico;

    @Column(name="id_bicicleta")
    String id_bicicleta;

    @Column(name="fecha")
    LocalDate fecha;

    @Column(name = "observaciones")
    String observaciones;

    @Column(name = "horas_trabajadas")
    double horas_trabajadas;

    // Constructor vacío
    public Mantenimiento() {
    }

    // Constructor completo
    public Mantenimiento(int id_mantenimiento, String id_mecanico, String id_bicicleta, LocalDate fecha, String observaciones, double horas_trabajadas) {
        this.id_mantenimiento = id_mantenimiento;
        this.id_mecanico = id_mecanico;
        this.id_bicicleta = id_bicicleta;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.horas_trabajadas = horas_trabajadas;
    }

    // Constructor sin ID (para crear nuevos registros)
    public Mantenimiento(String id_mecanico, String id_bicicleta, LocalDate fecha, String observaciones, double horas_trabajadas) {
        this.id_mecanico = id_mecanico;
        this.id_bicicleta = id_bicicleta;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.horas_trabajadas = horas_trabajadas;
    }

    // Getters
    public int getId_mantenimiento() {
        return id_mantenimiento;
    }

    public String getId_mecanico() {
        return id_mecanico;
    }

    public String getId_bicicleta() {
        return id_bicicleta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public double getHoras_trabajadas() {
        return horas_trabajadas;
    }

    // Setters
    public void setId_mantenimiento(int id_mantenimiento) {
        this.id_mantenimiento = id_mantenimiento;
    }

    public void setId_mecanico(String id_mecanico) {
        this.id_mecanico = id_mecanico;
    }

    public void setId_bicicleta(String id_bicicleta) {
        this.id_bicicleta = id_bicicleta;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setHoras_trabajadas(double horas_trabajadas) {
        this.horas_trabajadas = horas_trabajadas;
    }


    @Override
    public String toString() {
        return "Mantenimiento{" +
                "id_mantenimiento=" + id_mantenimiento +
                ", id_mecanico='" + id_mecanico + '\'' +
                ", id_bicicleta='" + id_bicicleta + '\'' +
                ", fecha=" + fecha +
                ", observaciones='" + observaciones + '\'' +
                ", horas_trabajadas=" + horas_trabajadas +
                '}';
    }




}
