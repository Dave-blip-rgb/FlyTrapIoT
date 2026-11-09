package com.example.flytrapiot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditarActivity extends AppCompatActivity {
    EditText input_codigo, input_trampa, input_nivel_captura, input_observaciones;
    Button boton_editar_trampa;
    int id_actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        input_codigo = findViewById(R.id.input_codigo_editar);
        input_trampa = findViewById(R.id.input_trampa_editar);
        input_nivel_captura = findViewById(R.id.input_nivel_editar);
        input_observaciones = findViewById(R.id.input_observaciones_editar);
        boton_editar_trampa = findViewById(R.id.boton_trampa_editar);
    }
}