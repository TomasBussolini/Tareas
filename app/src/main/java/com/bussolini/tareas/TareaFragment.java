package com.bussolini.tareas;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

import static android.widget.CompoundButton.*;

import model.Tarea;

public class TareaFragment extends Fragment {

    private Tarea tarea;
    private EditText campoTitulo;
    private CheckBox tareaRealizada;

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
                Intent intent = new Intent(getActivity(), TareaActivity.class);
                startActivity(intent);
            }

        });

        return v;

    }
}
