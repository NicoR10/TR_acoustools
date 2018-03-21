package com.example.android.tracoustools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Sala_act extends AppCompatActivity {

    EditText et_nombre, et_alto, et_ancho, et_largo, et_volumen, et_suptotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_act);

        et_nombre = (EditText)findViewById(R.id.et_nombre);
        et_alto = (EditText)findViewById(R.id.et_alto);
        et_ancho = (EditText)findViewById(R.id.et_ancho);
        et_largo = (EditText)findViewById(R.id.et_largo);
        et_volumen = (EditText)findViewById(R.id.et_volumen);
        et_suptotal = (EditText)findViewById(R.id.et_suptotal);

        SharedPreferences datos_sala = getSharedPreferences("data", Context.MODE_PRIVATE);
        et_nombre.setText(datos_sala.getString("nombre", ""));
        et_volumen.setText(datos_sala.getString("volumen", ""));
        et_suptotal.setText(datos_sala.getString("suptotal", ""));
        et_alto.setText(datos_sala.getString("alto", ""));
        et_ancho.setText(datos_sala.getString("ancho", ""));
        et_largo.setText(datos_sala.getString("largo", ""));
    }

    public void Calcular(View view){
        if(et_alto.getText().toString().isEmpty()|et_ancho.getText().toString().isEmpty()|et_largo.getText().toString().isEmpty()){
            Toast.makeText(this, "Faltan valores", Toast.LENGTH_LONG).show();
        }
        else{
            double alto = Double.parseDouble(et_alto.getText().toString());
            double ancho = Double.parseDouble(et_ancho.getText().toString());
            double largo = Double.parseDouble(et_largo.getText().toString());
            //calculo el volumen y la sup total
            double volumen = alto*ancho*largo;
            double suptotal = (2*alto*largo)+(2*alto*ancho)+(2*ancho*largo);
            //redondeo ambos a dos decimales
            volumen = Math.round(volumen*100.00)/100.00;
            suptotal = Math.round(suptotal*100.00)/100.00;
            //seteo el resultado como texto
            et_volumen.setText(String.valueOf(volumen));
            et_suptotal.setText(String.valueOf(suptotal));
        }
    }

    public void Guardar(View view){
        if (et_nombre.getText().toString().isEmpty()|et_volumen.getText().toString().isEmpty()|et_suptotal.getText().toString().isEmpty()){
            Toast.makeText(this, "Los campos Nombre, Volumen y Superficie Total no pueden estar vacíos.", Toast.LENGTH_LONG).show();
        }
        else if((int)Double.parseDouble(et_volumen.getText().toString()) == 0|(int)Double.parseDouble(et_suptotal.getText().toString()) == 0){
            Toast.makeText(this, "Los campos Volumen y Superficie Total no pueden ser nulos.", Toast.LENGTH_LONG).show();
        }
        else{
            double volumen = Double.parseDouble(et_volumen.getText().toString());
            double suptotal = Double.parseDouble(et_suptotal.getText().toString());
            String nombre = et_nombre.getText().toString();

            Sala nueva_sala = new Sala(nombre, volumen, suptotal);
            //creo un intent
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("salaguardada", nueva_sala);
            setResult(RESULT_OK, i);

            //guardo los datos con shared preferences
            SharedPreferences data_sala = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = data_sala.edit();
            editor.putString("nombre", et_nombre.getText().toString());
            editor.putString("volumen", et_volumen.getText().toString());
            editor.putString("suptotal", et_suptotal.getText().toString());
            editor.putString("alto", "");
            editor.putString("ancho", "");
            editor.putString("largo", "");
            if(!et_alto.getText().toString().isEmpty()){
                editor.putString("alto", et_alto.getText().toString());
            }
            if(!et_ancho.getText().toString().isEmpty()){
                editor.putString("ancho", et_ancho.getText().toString());
            }
            if(!et_largo.getText().toString().isEmpty()){
                editor.putString("largo", et_largo.getText().toString());
            }
            editor.commit();
            finish();
        }
    }

    public void Borrar(View view){
        et_nombre.setText("");
        et_alto.setText("");
        et_ancho.setText("");
        et_largo.setText("");
        et_volumen.setText("");
        et_suptotal.setText("");
    }
}
