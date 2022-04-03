package com.bussolini.tareas.controller.api;

import com.bussolini.tareas.controller.controller.TareaLab;

import java.util.List;

import model.Tarea;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static APIClient shared;
    private Retrofit retrofit;
    private JsonPlaceholderAPI service;

    public static APIClient getInstance() {
        if (shared == null){
            shared= new APIClient();
        }
        return shared;
    }

    public APIClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = this.retrofit.create(JsonPlaceholderAPI.class);
    }

    public void getToDo(Integer toDoID, Callback<ToDoAPI> callback){
        this.service.getToDo(toDoID).enqueue(callback);
    }

    public void getToDos(Callback<List<ToDoAPI>> callback){
        this.service.getToDos().enqueue(callback);
    }

    public void postToDo(Integer toDoID, String title, boolean completed, Callback<ToDoAPI> callback){
        this.service.postToDo(toDoID, title, completed).enqueue(callback);
    }

    public void updateToDo(Integer toDoID1, Integer toDoID2, String title, boolean completed, Callback<ToDoAPI> callback){
        this.service.updateToDo(toDoID1, toDoID2, title, completed).enqueue(callback);
    }

}
