package com.bussolini.tareas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TareaActivity extends AppCompatActivity {

    private Button crearTarea;

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


    }
}