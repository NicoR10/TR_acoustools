package com.example.android.tracoustools;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NuevaSup_act extends AppCompatActivity {

    Spinner sp_materiales;
    EditText et_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_sup_act);

        sp_materiales = (Spinner)findViewById(R.id.spinner_mat);
        et_area = (EditText)findViewById(R.id.et_area);

        //-----obtengo el arreglo de strings de los materiales
        ArrayList<String> materiales_spinner = new ArrayList<>();
        try {
            InputStreamReader archivomateriales = new InputStreamReader(openFileInput("datos_mat.txt"));
            BufferedReader reader = new BufferedReader(archivomateriales);
            String linea;
            String mat_buff;
            int i = 0;
            while((linea = reader.readLine()) != null){
                if(linea.equals("--")){
                    mat_buff = reader.readLine();
                    materiales_spinner.add(i, mat_buff);
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //------------------
        //array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, materiales_spinner);
        sp_materiales.setAdapter(adapter);
    }

    public void Guardar(View view){
        Superficie nueva_sup = new Superficie(sp_materiales.getSelectedItem().toString(), Double.parseDouble(et_area.getText().toString()));
        Intent i = new Intent(this, Superficies_act.class);
        i.putExtra("nuevasup", nueva_sup);
        setResult(RESULT_OK, i);
        finish();
    }
}
