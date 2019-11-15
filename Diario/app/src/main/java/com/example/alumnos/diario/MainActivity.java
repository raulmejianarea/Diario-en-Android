package com.example.alumnos.diario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> tareas; // Datos
    ListView lista; // Vista
    ArrayAdapter<String> adaptador;
    SharedPreferences sharedPreferences;
    private  Context contexto;
    int posicion;

    Button buttonadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("text", MODE_PRIVATE);
        buttonadd = findViewById(R.id.buttonadd);

        tareas = new ArrayList<>();
        lista = findViewById(R.id.listado);
        tareas = cargarLista();

        contexto= this;

        //editar la lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent = new Intent(contexto, Main2Activity.class);
                posicion=pos;
                String texto = tareas.get(pos);
                if(!texto.isEmpty()){
                    intent.putExtra("text", texto);
                    startActivityForResult(intent, 2);
                }

            }
        });
        //Borrar elemento de una lista con un click largo
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tareas.remove(position);
                adaptador.notifyDataSetChanged();
                guardalista(tareas);
                return true;
            }
        });

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tareas);
        lista.setAdapter(adaptador);

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivityForResult(intent,1);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Importante");
        builder.setMessage("Para borrar un elemento de la lista tienes que mantenerlo pulsado");
        builder.setPositiveButton("OK",null);
        builder.create();
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Datos que recoge cuando crea
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
               String resultado = data.getStringExtra("resultado");
                tareas.add(resultado);
                adaptador.notifyDataSetChanged();

            }
        }

        //Datos que recoge cuando edita
        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                String editar = data.getStringExtra("resultado");
                tareas.set(posicion, editar);
                adaptador.notifyDataSetChanged();
            }
        }
        guardalista(tareas);

    }
    private void guardalista(ArrayList<String> textos) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < textos.size(); i++) {
            editor.putString("text" + i, textos.get(i));
        }
        editor.putInt("longitud", textos.size()); // Guardar el tamaño de la lista
        editor.commit();
    }
    private ArrayList<String> cargarLista() {
        ArrayList<String> textos = new ArrayList<>();
        // Obtener el tamaño de la lista

        int longitud = sharedPreferences.getInt("longitud", 0);
        // Obtener todos los textos
        for (int i = 0; i < longitud; i++) {
            String texto = sharedPreferences.getString("text" + i, "");
            textos.add(texto);
        }

        return textos;
    }



}
