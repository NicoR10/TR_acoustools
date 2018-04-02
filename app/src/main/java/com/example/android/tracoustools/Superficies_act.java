package com.example.android.tracoustools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Superficies_act extends AppCompatActivity {

    private Superficie nueva_sup;
    ArrayList<Superficie> sups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superficies_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), NuevaSup_act.class);
                startActivityForResult(i, 1);
            }
        });

        Toast toast = Toast.makeText(this, "Puede comenzar agregando una nueva Superficie", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                nueva_sup =(Superficie) data.getSerializableExtra("nuevasup");
                sups.add(nueva_sup);
                //verifica si dos materiales estan repetidos y los suma en un solo objeto
                for (int i = 0; i < sups.size(); i++) {
                    for (int j = i+1; j < sups.size(); j++) {
                        // compare list.get(i) and list.get(j)
                        Superficie obj1 = sups.get(i);
                        Superficie obj2 = sups.get(j);
                        if(obj1.getMaterial().equals(obj2.getMaterial())){
                            double area_res = obj1.getArea() + obj2.getArea();
                            obj1.setArea(area_res);
                            sups.remove(j);
                            sups.remove(i);
                            sups.add(obj1);
                        }
                    }
                }
                Toast.makeText(this, "Superficie agregada con éxito", Toast.LENGTH_LONG).show();
//------creacion de adapter para actualizar la vista luego de la primer superficie agregada.
                //creo el adapter personalizado
                final SuperficiesAdapter adapter = new SuperficiesAdapter(this, sups);

                //creo y asocio la listview donde se veran los objetos
                ListView list = (ListView)findViewById(R.id.sup_listview);

                //le seteo el adapter
                list.setAdapter(adapter);

                // Setea un click largo en cada vista
                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                        //crea una alerta y elimina la vista-----
                        // setup the alert builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(adapterView.getContext());
                        builder.setTitle("¡Atención!");
                        builder.setMessage("¿Desea eliminar la superficie?");

                        // add the buttons and listener
                        builder.setNegativeButton("Cancelar", null);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //elimina la vista
                                adapter.remove(adapter.getItem(pos));
                                adapter.notifyDataSetChanged();
                            }
                        });


                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        //------
                        return true;
                    }
                });

            }
        }
    }

}
