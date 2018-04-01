package com.example.android.tracoustools;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    private Sala sala_datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Establezco y busco el textview de cada vista para poder manejar el click de cada una
        TextView sala = (TextView)findViewById(R.id.sala);
        TextView superficies = (TextView)findViewById(R.id.superficies);
        TextView extras = (TextView)findViewById(R.id.extras);
        TextView materiales = (TextView)findViewById(R.id.materiales);
        TextView calcular = (TextView)findViewById(R.id.calcular);

//-----------copia el arhivo de datos de materiales de la carpeta raw al almacenamieto interno para poder leerlo en runtime
        //stream de entrada con los datos de materiales en raw folder
        InputStream in_data_mat = getResources().openRawResource(R.raw.materiales);
        //creo buffer de lectura
        BufferedReader lector_data_mat = new BufferedReader(new InputStreamReader(in_data_mat));
        //creo un string builder para guardar el txt en un string
        StringBuilder data_mat_leida = new StringBuilder();
        //creo el string por linea y lo agrego al string del texto completo: data_mat_leida
        String line;
        try {
            while ((line = lector_data_mat.readLine()) != null) {
                data_mat_leida.append(line);
                data_mat_leida.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //creo un outputwriter para el almacenamieto interno

        try {
            OutputStreamWriter datos_mat = new OutputStreamWriter(openFileOutput("datos_mat.txt", Activity.MODE_PRIVATE));
            datos_mat.write(data_mat_leida.toString());
            datos_mat.flush();
            //cierrotodo
            datos_mat.close();
            in_data_mat.close();
            lector_data_mat.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//----------------------------------------
        //click listener sala
        sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent salaintent = new Intent(view.getContext(), Sala_act.class);
                startActivityForResult(salaintent, 1);
            }
        });

        //click listener superficies
        superficies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //codigo on click
                Intent supintent = new Intent(view.getContext(), Superficies_act.class);
                startActivity(supintent);
            }
        });

        // click listener extras
        extras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //codigo click
            }
        });

        //click listener materiales
        materiales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //codigo on click
            }
        });

        //click listener calcular
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // codigo on click
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                sala_datos =(Sala) data.getSerializableExtra("salaguardada");
                //Toast.makeText(this, "Sala guardada con éxito" +'\n'+sala_datos.toString(), Toast.LENGTH_LONG).show();
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.sala),"Sala guardada con éxito", Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }
        }
    }
}
