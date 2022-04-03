package com.bussolini.tareas.controller.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

import static android.widget.CompoundButton.*;

import com.bussolini.tareas.R;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import model.Tarea;

public class TareaFragment extends Fragment {

    private Tarea tarea;
    private EditText campoTitulo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tarea = new Tarea();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tarea, container, false);

        campoTitulo = (EditText) v.findViewById(R.id.tarea_title);
        campoTitulo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tarea.setTitulo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        Button botonCrear = (Button) v.findViewById(R.id.crear_tarea2);
        botonCrear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                List<Tarea> tareas = new LinkedList<Tarea>(TareaLab.getTareas());
                tareas.add(tarea);

                TareaLab.setTareas(tareas);

                SharedPreferences pref = getActivity().getSharedPreferences("TAREAS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                String tareasJson = new Gson().toJson(tareas);
                editor.putString("TAREAS", tareasJson);
                editor.apply();


                Intent intent = new Intent(getActivity(), TareaActivity.class);
                startActivity(intent);
            }

        });

        return v;

    }
}
