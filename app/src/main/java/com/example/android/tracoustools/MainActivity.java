package com.example.android.tracoustools;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


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
                Toast.makeText(view.getContext(), "Superficies apretado", Toast.LENGTH_LONG).show();
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
