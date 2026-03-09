package org.example.proyecto_tfg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;

    public Cliente() {
    }

    public Cliente(String dni, String nombre, String apellido, String telefono, String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getApellidos() {
        return apellido;
    }

    public void setApellidos(String apellidos) {
        this.apellido = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
