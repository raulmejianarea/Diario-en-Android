package com.example.alumnos.diario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class Main2Activity extends AppCompatActivity {

    ArrayList<String> tareas;
    EditText edit_diario;

    Button button_cancelar;
    Button guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edit_diario = findViewById(R.id.diario_edit);
        button_cancelar = findViewById(R.id.button_cancelar);
        guardar = findViewById(R.id.guardar);

        Intent intent = getIntent();
        String resultado = intent.getStringExtra("text");
        edit_diario.setText(resultado);

    button_cancelar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });

    }
    public void enviartexto (View v) {
        String texto = edit_diario.getText().toString();
        if (guardar.isClickable()) {
            Intent intentResultado = new Intent();
            intentResultado.putExtra("resultado", texto);
            setResult(RESULT_OK, intentResultado);
            finish();
        }

    }

}
