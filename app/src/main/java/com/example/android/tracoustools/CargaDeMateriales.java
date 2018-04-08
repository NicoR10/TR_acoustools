package com.example.android.tracoustools;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CargaDeMateriales {

    public static void cargar(InputStream in_data_mat, Context context) throws IOException {

        //creo buffer de lectura
        BufferedReader lector_data_mat = new BufferedReader(new InputStreamReader(in_data_mat));
        //linea leida
        String line;
        //inicializo la base para escribirla
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        while ((line = lector_data_mat.readLine()) != null){

            if(line.equals("--")){
                ContentValues registro = new ContentValues();

                //leo el material y lo registro
                String mat_buff = lector_data_mat.readLine();
                registro.put("material", mat_buff);
                //leo cada octava y la registro
                String buff_125 = lector_data_mat.readLine();
                double oct125 = Double.parseDouble(buff_125);
                registro.put("oct125", oct125);

                String buff_250 = lector_data_mat.readLine();
                double oct250 = Double.parseDouble(buff_250);
                registro.put("oct250", oct250);

                String buff_500 = lector_data_mat.readLine();
                double oct500 = Double.parseDouble(buff_500);
                registro.put("oct500", oct500);

                String buff_1k = lector_data_mat.readLine();
                double oct1k = Double.parseDouble(buff_1k);
                registro.put("oct1k", oct1k);

                String buff_2k = lector_data_mat.readLine();
                double oct2k = Double.parseDouble(buff_2k);
                registro.put("oct2k", oct2k);

                String buff_4k = lector_data_mat.readLine();
                double oct4k = Double.parseDouble(buff_4k);
                registro.put("oct4k", oct4k);
                //leo la descripcion y la registro
                String des_buff = lector_data_mat.readLine();
                registro.put("descripcion", des_buff);

                //inserto el registro en la base de datos
                BaseDeDatos.insert("materiales", null, registro);

            }

        }

        BaseDeDatos.close();

    }

}
