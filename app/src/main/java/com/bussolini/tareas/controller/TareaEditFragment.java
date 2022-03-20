package com.bussolini.tareas.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

import static android.widget.CompoundButton.*;

import com.bussolini.tareas.R;

import java.util.List;

import model.Tarea;

public class TareaEditFragment extends Fragment {

    private Tarea tarea;
    private EditText campoTitulo;
    private String titulo = TareaActivity.tituloTarea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Tarea> tareas = TareaLab.getTareas();

        for (int i = 0; i < tareas.size(); i++){

            if (tareas.get(i).getTitulo().equals(titulo)) {

                tarea = tareas.get(i);
                break;

            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tarea_edit, container, false);

        campoTitulo = (EditText) v.findViewById(R.id.tarea_edit_title);
        campoTitulo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0)
                    tarea.setTitulo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        Button botonEditar = (Button) v.findViewById(R.id.editar_tarea);
        botonEditar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), TareaActivity.class);
                startActivity(intent);
            }

        });

        return v;

    }
}
