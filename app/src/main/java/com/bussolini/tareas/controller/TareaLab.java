package com.bussolini.tareas.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import model.Tarea;

public class TareaLab {

    private static TareaLab tareaLab;

    private static List<Tarea> tareas;

    public static TareaLab get(Context context) {

        if (tareaLab == null) {
            tareaLab = new TareaLab(context);
        }

        return tareaLab;

    }

    private TareaLab(Context context) {

        tareas = new ArrayList<>();

        Tarea t1 = new Tarea(); t1.setTitulo("Sacar al perro"); tareas.add(t1);
        Tarea t2 = new Tarea(); t2.setTitulo("Comprar pan"); tareas.add(t2);
        Tarea t3 = new Tarea(); t3.setTitulo("Revisar correo de La Salle"); tareas.add(t3);
        Tarea t4 = new Tarea(); t4.setTitulo("Preparar reuniones del día"); tareas.add(t4);
        Tarea t5 = new Tarea(); t5.setTitulo("Hacer ejercicio"); tareas.add(t5);

    }

    public static List<Tarea> getTareas() {

        return tareas;

    }

}
