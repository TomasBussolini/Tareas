package com.bussolini.tareas.controller.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface JsonPlaceholderAPI {

    @GET("todos")
    Call<List<ToDoAPI>> getToDos();

    @GET("todos/{todoID}")
    Call<ToDoAPI> getToDo(@Path("todoID") Integer toDoID);

    @FormUrlEncoded
    @POST("todos")
    Call<ToDoAPI> postToDo(@Field("toDoID")Integer toDoID, @Field("title")String title, @Field("completed")boolean completed);

    @Multipart
    @PUT("todos/{todoID}")
    Call<ToDoAPI> updateToDo(@Path("todoID") Integer toDoID1, @Part("toDoID")Integer toDoID2, @Part("title")String title, @Part("completed")boolean completed);

}