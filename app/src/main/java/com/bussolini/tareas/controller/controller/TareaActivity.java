package com.bussolini.tareas.controller.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import static android.widget.CompoundButton.*;
import com.bussolini.tareas.R;
import com.bussolini.tareas.controller.api.APIClient;
import com.bussolini.tareas.controller.api.ToDoAPI;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import model.Tarea;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaActivity extends AppCompatActivity {

    private static final String TAG = "LogLifecycleCallbacks";
    private static final String KEY_INDEX = "index";

    private Button crearTarea;
    private RecyclerView tareaRecyclerView;
    private TareaAdapter adapter;
    public static String tituloTarea;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, TareaActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        // NO PUDE SOLUCIONAR EL ERROR DE QUE PARA QUE SE MUESTREN POR PANTALLA LOS TODOS
        // OBTENIDOS DE LA API DEBO CREAR PRIMERO UNA TAREA, LUEGO APARECEN. NO PUDE SOLUCIONARLO.

        crearTarea = (Button) findViewById(R.id.crear_tarea);
        crearTarea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = fm.findFragmentById(R.id.fragment_container);

                if (fragment == null){
                    fragment = new TareaFragment();
                    fm.beginTransaction()
                            .add(R.id.fragment_container, fragment)
                            .commit();
                }

            }
        });

        tareaRecyclerView = (RecyclerView) findViewById(R.id.tarea_recycler_view);
        tareaRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

    }

    private void updateUI() {

        TareaLab tareaLab = TareaLab.get(this);

        SharedPreferences pref = TareaActivity.this.getSharedPreferences("TAREAS", Context.MODE_PRIVATE);
        String tareasRecuperadas = pref.getString("TAREAS", "No hay tareas guardadas.");

        Tarea[] tareasGuardadas = new Gson().fromJson(tareasRecuperadas, Tarea[].class);
        List<Tarea> tareas = Arrays.asList(tareasGuardadas);
        TareaLab.setTareas(tareas);

        adapter = new TareaAdapter(tareas);
        tareaRecyclerView.setAdapter(adapter);

    }

    private class TareaAdapter extends RecyclerView.Adapter<TareaHolder> {

        private List<Tarea> tareas;

        public TareaAdapter(List<Tarea> mTareas) {
            tareas = mTareas;
        }

        @Override
        public TareaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new TareaHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TareaHolder holder, int position) {
            Tarea tarea = tareas.get(position);
            holder.bind(tarea);
        }

        @Override
        public int getItemCount() {
            return tareas.size();
        }

    }

    private class TareaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Tarea tarea;

        private TextView tituloTextView;
        private CheckBox estadoCheckBox;

        public TareaHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_tarea, parent, false));
            itemView.setOnClickListener(this);

            tituloTextView = (TextView) itemView.findViewById(R.id.titulo_tarea);
            estadoCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);

            estadoCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    List<Tarea> lista = TareaLab.getTareas();

                    for (int i = 0; i < lista.size(); i++){

                        if (lista.get(i).getTitulo().equals(tarea.getTitulo())){

                            if (isChecked)
                                lista.get(i).setEstado("REALIZADA");
                            else
                                lista.get(i).setEstado("PENDIENTE");

                            TareaLab.setTareas(lista);

                            SharedPreferences pref = TareaActivity.this.getSharedPreferences("TAREAS", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            String tareasJson = new Gson().toJson(lista);
                            editor.putString("TAREAS", tareasJson);
                            editor.apply();

                            break;

                        }

                    }

                }
            });

        }

        public void bind(Tarea mTarea) {

            tarea = mTarea;
            tituloTextView.setText(tarea.getTitulo());

            estadoCheckBox.setChecked(tarea.getEstado().equals("REALIZADA"));

        }

        @Override
        public void onClick(View view) {

            tituloTarea = tarea.getTitulo();

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);

            if (fragment == null){
                fragment = new TareaEditFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart(Bundle) called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume(Bundle) called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause(Bundle) called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(Bundle) called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy(Bundle) called");

        SharedPreferences pref = TareaActivity.this.getSharedPreferences("TAREAS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String tareasJson = new Gson().toJson(TareaLab.getTareas());
        editor.putString("TAREAS", tareasJson);
        editor.apply();
    }

}