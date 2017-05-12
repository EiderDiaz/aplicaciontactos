package com.example.lomeli.listview;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminBD extends SQLiteOpenHelper {

    private static final String NOMBREBD = "contactos.bd";
    private static final int VERSIONBD = 1;
    private SQLiteDatabase BD;

    public AdminBD(Context context){

        super(context, NOMBREBD, null, VERSIONBD);
        BD = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(LogicaBD.CREARTBLCONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tblcontactos");

        onCreate(db);
    }

    public void insertarRegistro(String nombre, String telefono, String email){

        BD.execSQL(LogicaBD.INSERTARREGISTRO(nombre,telefono,email));

    }

    public Cursor seleccionarTablaContactos(){

        Cursor c = BD.rawQuery(LogicaBD.SELECCIONARDATOSTABLACONTACTOS,null);

        return c;

    }





}
