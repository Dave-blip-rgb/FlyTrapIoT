package com.example.flytrapiot;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        Intent intent_actual = getIntent();
        id_actual = intent_actual.getIntExtra("id_actual", -1);

        cargarTrampa(id_actual);

        boton_editar_trampa.setOnClickListener(v -> {
            editarTrampa();
        });
    }

    public void editarTrampa()
    {
        String codigo_valor = String.valueOf(input_codigo.getText());
        String trampa_valor = String.valueOf(input_trampa.getText());
        String nivel_valor = String.valueOf(input_nivel_captura.getText());
        String observaciones_valor = String.valueOf(input_observaciones.getText());

        if (!codigo_valor.isEmpty() && !trampa_valor.isEmpty() &&
                !nivel_valor.isEmpty() && !observaciones_valor.isEmpty())
        {
            DBHelper admin = new DBHelper(this);
            SQLiteDatabase db = admin.getWritableDatabase();

            ContentValues dato_update = new ContentValues();
            dato_update.put("codigo_sector", codigo_valor);
            dato_update.put("tipo_trampa", trampa_valor);
            dato_update.put("nivel_captura", nivel_valor);
            dato_update.put("observaciones", observaciones_valor);

            db.update("trampas", dato_update, "id= " + id_actual, null);
            db.close();

            Toast.makeText(this, "Trampa editado correctamente.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(this, "Ingrese el dato correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarTrampa(int id)
    {
        DBHelper admin = new DBHelper(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor fila = db.rawQuery("SELECT id, codigo_sector, tipo_trampa, nivel_captura, observaciones FROM trampas WHERE id=" + id_actual, null);

        if (fila.moveToFirst())
        {
            input_codigo.setText(fila.getString(1));
            input_trampa.setText(fila.getString(2));
            input_nivel_captura.setText(fila.getString(3));
            input_observaciones.setText(fila.getString(4));
        }
        else
        {
            Toast.makeText(this, "No se encontró la trampa con ID " + id, Toast.LENGTH_SHORT).show();
        }

        fila.close();
        db.close();
    }
}