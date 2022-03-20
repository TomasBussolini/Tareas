package com.bussolini.tareas.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
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

        SharedPreferences primeraVez = context.getSharedPreferences("PRIMERA", Context.MODE_PRIVATE);
        String esPrimeraVez = primeraVez.getString("PRIMERA", "No hay tareas guardadas.");

        if (esPrimeraVez.equals("No hay tareas guardadas.")){
            tareas = new ArrayList<>();

            Tarea t1 = new Tarea(); t1.setTitulo("Sacar al perro"); tareas.add(t1);
            Tarea t2 = new Tarea(); t2.setTitulo("Comprar pan"); tareas.add(t2);
            Tarea t3 = new Tarea(); t3.setTitulo("Revisar correo de La Salle"); tareas.add(t3);
            Tarea t4 = new Tarea(); t4.setTitulo("Preparar reuniones del día"); tareas.add(t4);
            Tarea t5 = new Tarea(); t5.setTitulo("Hacer ejercicio"); tareas.add(t5);

            SharedPreferences.Editor editor = primeraVez.edit();
            String tareasJson = new Gson().toJson(tareas);
            editor.putString("PRIMERA", tareasJson);
            editor.apply();

        }

        else {

            SharedPreferences pref = context.getSharedPreferences("TAREAS", Context.MODE_PRIVATE);
            String tareasRecuperadas = pref.getString("TAREAS", "No hay tareas guardadas.");
            Tarea[] tareasGuardadas = new Gson().fromJson(tareasRecuperadas, Tarea[].class);

            tareas = Arrays.asList(tareasGuardadas);
        }

    }

    public static List<Tarea> getTareas() {
        return tareas;
    }

    public static void setTareas(List<Tarea> tareas) {
        TareaLab.tareas = tareas;
    }
}
