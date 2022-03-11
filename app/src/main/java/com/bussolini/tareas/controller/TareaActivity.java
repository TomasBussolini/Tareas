package com.bussolini.tareas.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bussolini.tareas.R;

import java.util.List;

import model.Tarea;

public class TareaActivity extends AppCompatActivity {

    private Button crearTarea;
    private RecyclerView tareaRecyclerView;
    private TareaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

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
        List<Tarea> tareas = tareaLab.getTareas();

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
        private TextView estadoTextView;

        public TareaHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_tarea, parent, false));
            itemView.setOnClickListener(this);

            tituloTextView = (TextView) itemView.findViewById(R.id.titulo_tarea);
            estadoTextView = (TextView) itemView.findViewById(R.id.estado_tarea);

        }

        public void bind(Tarea mTarea) {

            tarea = mTarea;
            tituloTextView.setText(tarea.getTitulo());
            estadoTextView.setText(tarea.getEstado());

            if (estadoTextView.getText().equals("REALIZADA"))
                estadoTextView.setTextColor(getColor(R.color.green));

        }

        @Override
        public void onClick(View view){

            if (tarea.getEstado().equals("PENDIENTE")){

                tarea.setEstado("REALIZADA");
                estadoTextView.setText(getString(R.string.realizada));
                estadoTextView.setTextColor(getColor(R.color.green));

            }

            else {

                tarea.setEstado(getString(R.string.pendiente));
                estadoTextView.setText(getString(R.string.pendiente));
                estadoTextView.setTextColor(getColor(R.color.black));

            }

        }

    }
}