package com.example.android.tracoustools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NuevaSup_act extends AppCompatActivity {

    Spinner sp_materiales;
    EditText et_area, et_lado1, et_lado2;
    TextView tv_detalles, tv_125, tv_250, tv_500, tv_1k, tv_2k, tv_4k, tv_suptotal, tv_supsalares;
    double suptotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_sup_act);

        sp_materiales = (Spinner)findViewById(R.id.spinner_mat);
        et_area = (EditText)findViewById(R.id.et_area);
        et_lado1 = (EditText)findViewById(R.id.et_lado1);
        et_lado2 = (EditText)findViewById(R.id.et_lado2);
        tv_detalles = (TextView)findViewById(R.id.detalles);
        tv_1k = (TextView)findViewById(R.id.oct1k);
        tv_2k = (TextView)findViewById(R.id.oct2k);
        tv_4k = (TextView)findViewById(R.id.oct4k);
        tv_125 = (TextView)findViewById(R.id.oct125);
        tv_250 = (TextView)findViewById(R.id.oct250);
        tv_500 = (TextView)findViewById(R.id.oct500);
        tv_suptotal = (TextView)findViewById(R.id.suprestante);
        tv_supsalares = (TextView)findViewById(R.id.tv_supsalares);
        et_area.requestFocus();

        //seteo el valor de la superficie total q viene de superficies activity
        Intent intent = getIntent();
        suptotal = intent.getDoubleExtra("suptotal", 0);
        tv_suptotal.setText(String.valueOf(suptotal));

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
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, materiales_spinner);
        sp_materiales.setAdapter(adapter);


        //accion cuando selecciono el spinner
        sp_materiales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                //inicializo la base de datos
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(selectedItemView.getContext(), "administracion", null, 1);
                final SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                String mat = adapter.getItem(position).toString();
                String query = "SELECT descripcion, oct125, oct250, oct500, oct1k, oct2k, oct4k FROM materiales WHERE material = " + "'" + mat + "'";
                Cursor fila = BaseDeDatos.rawQuery(query, null);

                if(fila.moveToFirst()){
                    tv_detalles.setText(fila.getString(0));
                    tv_125.setText(String.valueOf(fila.getDouble(1)));
                    tv_250.setText(String.valueOf(fila.getDouble(2)));
                    tv_500.setText(String.valueOf(fila.getDouble(3)));
                    tv_1k.setText(String.valueOf(fila.getDouble(4)));
                    tv_2k.setText(String.valueOf(fila.getDouble(5)));
                    tv_4k.setText(String.valueOf(fila.getDouble(6)));
                    BaseDeDatos.close();
                }else{
                    BaseDeDatos.close();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void Guardar(View view){

        if(et_area.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese el área", Toast.LENGTH_SHORT).show();
        }
        else if(Double.parseDouble(et_area.getText().toString()) == 0){
            Toast.makeText(this, "El área no puede ser nula", Toast.LENGTH_SHORT).show();
        }
        else {
            Superficie nueva_sup = new Superficie(sp_materiales.getSelectedItem().toString(), Double.parseDouble(et_area.getText().toString()));
            Intent i = new Intent(this, Superficies_act.class);
            i.putExtra("nuevasup", nueva_sup);
            //actualizo la superficie total
            suptotal = suptotal - nueva_sup.getArea();
            suptotal = Math.round(suptotal*100.00)/100.00;

            if(suptotal < 0){

                Toast.makeText(this, "Se completó la superfice total de la sala.", Toast.LENGTH_LONG).show();

            }
            //la agrego al intent
            i.putExtra("suptotal", suptotal);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    public void calcarea(View view) {
        if (et_lado1.getText().toString().isEmpty()|et_lado2.getText().toString().isEmpty()) {

            Toast.makeText(this, "Faltan valores", Toast.LENGTH_SHORT).show();
        }
        else{

            double lado1 = Double.parseDouble(et_lado1.getText().toString());
            double lado2 = Double.parseDouble(et_lado2.getText().toString());
            double area = lado1 * lado2;
            area = Math.round(area*100.00)/100.00;
            et_area.setText(String.valueOf(area));

        }
    }
}
