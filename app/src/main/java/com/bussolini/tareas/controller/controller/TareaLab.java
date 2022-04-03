package com.bussolini.tareas.controller.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bussolini.tareas.controller.api.APIClient;
import com.bussolini.tareas.controller.api.ToDoAPI;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import model.Tarea;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        SharedPreferences pref = context.getSharedPreferences("TAREAS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().apply();
        String tareasRecuperadas = pref.getString("TAREAS", "No hay tareas guardadas.");

        if (tareasRecuperadas.equals("No hay tareas guardadas.")) {

            tareas = new ArrayList<>();

            APIClient.getInstance().getToDos(new Callback<List<ToDoAPI>>() {
                @Override
                public void onResponse(Call<List<ToDoAPI>> call, Response<List<ToDoAPI>> response) {
                    Log.d("MAIN", "TODOS RECUPERADOS CORRECTAMENTE!");

                    if (response.body() != null) {
                        List<Tarea> aux = new LinkedList<Tarea>(TareaLab.getTareas());
                        for (int i = 0; i < response.body().size(); i++) {
                            Tarea newTarea = new Tarea(response.body().get(i));
                            aux.add(newTarea);
                        }
                        TareaLab.setTareas(aux);
                    }
                }

                @Override
                public void onFailure(Call<List<ToDoAPI>> call, Throwable t) {
                    Log.d("MAIN", "ERROR AL RECUPERAR LOS TODOS!");
                }
            });

            String tareasJson = new Gson().toJson(tareas);
            editor.putString("TAREAS", tareasJson);
            editor.apply();

        }

        else {
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