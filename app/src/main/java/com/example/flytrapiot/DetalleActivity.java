package com.example.flytrapiot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalleActivity extends AppCompatActivity {
    TextView txt_id, txt_Codigo, txt_Trampa, txt_Nivel, txt_Observaciones;
    Button boton_editar_trampa, boton_eliminar_trampa;
    private int id_actual;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_id = findViewById(R.id.txt_id);
        txt_Codigo = findViewById(R.id.txt_codigo);
        txt_Trampa = findViewById(R.id.txt_trampa);
        txt_Nivel = findViewById(R.id.txt_nivel);
        txt_Observaciones = findViewById(R.id.txt_observaciones);
        boton_editar_trampa = findViewById(R.id.boton_editar_trampa);
        boton_eliminar_trampa = findViewById(R.id.boton_eliminar_trampa);

        Intent intent_actual = getIntent();
        id_actual = intent_actual.getIntExtra("id_actual", -1);

        cargarTrampa(id_actual);

        boton_editar_trampa.setOnClickListener(v -> {
            Intent intent_editar = new Intent(DetalleActivity.this, EditarActivity.class);
            intent_editar.putExtra("id_actual", id_actual);
            startActivity(intent_editar);
        });

        boton_eliminar_trampa.setOnClickListener(v -> {
            eliminarTrampa(id_actual);
        });
    }

    public void cargarTrampa(int id)
    {
        DBHelper admin = new DBHelper(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor fila = db.rawQuery("SELECT id, codigo_sector, tipo_trampa, nivel_captura, observaciones FROM trampas WHERE id =" + id_actual, null);

        if (fila.moveToFirst()){
            txt_id.setText("# " + fila.getString(0));
            txt_Codigo.setText("Código: " + fila.getString(1));
            txt_Trampa.setText("Trampa: " + fila.getString(2));
            txt_Nivel.setText("Nivel: " + fila.getString(3));
            txt_Observaciones.setText("Observaciones: " + fila.getString(4));
        }
        else {
            Toast.makeText(this, "No se encontró la trampa seleccionada", Toast.LENGTH_SHORT).show();
        }

        fila.close();
        db.close();
    }

    public void eliminarTrampa(int id)
    {
        DBHelper admin = new DBHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        db.delete("trampas", "id= " + id, null);
        db.close();

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarTrampa(id_actual);
    }
}