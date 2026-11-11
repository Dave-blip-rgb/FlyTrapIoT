package com.example.flytrapiot;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText input_codigo, input_trampa, input_nivel_caputura, input_observaciones;
    Button boton_enviar;
    ListView lista_trampas;
    List<Integer> id_trampas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        input_codigo = findViewById(R.id.input_codigo);
        input_trampa = findViewById(R.id.input_trampa);
        input_nivel_caputura = findViewById(R.id.input_nivel_captura);
        input_observaciones = findViewById(R.id.input_observaciones);
        boton_enviar = findViewById(R.id.boton_enviar);
        lista_trampas = findViewById(R.id.lista_trampas);
        
        cargarListaTrampas();
        
        boton_enviar.setOnClickListener(v -> {
            crearTrampa();
        });
        
        lista_trampas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicion, long id) {
                int id_actual = id_trampas.get(posicion);

                Intent intent_detalle = new Intent(MainActivity.this, DetalleActivity.class);
                intent_detalle.putExtra("id_actual", id_actual);
                startActivity(intent_detalle);
            }
        });
    }
    
    public void crearTrampa() 
    {
        String codigo_actual = String.valueOf(input_codigo.getText());
        String trampa_actual = String.valueOf(input_trampa.getText());
        String nivel_actual = String.valueOf(input_nivel_caputura.getText());
        String observaciones_actual = String.valueOf(input_observaciones.getText());
        
        DBHelper admin = new DBHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        
        if (codigo_actual.isEmpty() == false && trampa_actual.isEmpty() == false && nivel_actual.isEmpty() == false && observaciones_actual.isEmpty() == false)
        {
            ContentValues registro = new ContentValues();
            registro.put("codigo_sector", codigo_actual);
            registro.put("tipo_trampa", trampa_actual);
            registro.put("nivel_captura", nivel_actual);
            registro.put("observaciones", observaciones_actual);
            
            db.insert("trampas", null, registro);
            db.close();
            
            input_codigo.setText("");
            input_trampa.setText("");
            input_nivel_caputura.setText("");
            input_observaciones.setText("");

            cargarListaTrampas();
        }
        else 
        {
            Toast.makeText(this, "Rellene todos los campos para poder continuar.", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void cargarListaTrampas()
    {
        DBHelper admin = new DBHelper(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        ArrayList<String> listaTrampas = new ArrayList<>();
        id_trampas = new ArrayList<>();
        Cursor fila = db.rawQuery("SELECT id, codigo_sector, tipo_trampa, nivel_captura, observaciones FROM trampas", null);
        
        if (fila.moveToFirst()) 
        {
            do 
            {
                String trampa_texto = "Código: "+fila.getString(1)+"/ Trampa: "+fila.getString(2)+"/ Nivel: "+fila.getString(3)+"/ Observaciones: "+fila.getString(4);
                listaTrampas.add(trampa_texto);
                id_trampas.add(fila.getInt(0));
            }
            while(fila.moveToNext());
        }
        else 
        {
            listaTrampas.add("No se encontraron trampas en la base de datos");
        }
        fila.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaTrampas);
        lista_trampas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarListaTrampas();
    }
}