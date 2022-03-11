package model;

import java.util.UUID;

public class Tarea {

    private UUID id;
    private String titulo;
    private String estado;

    public Tarea() {
        this.id = UUID.randomUUID();
        this.estado = "PENDIENTE";
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
