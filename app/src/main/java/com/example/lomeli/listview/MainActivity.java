package com.example.lomeli.listview;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Contactos> listaContactos = new ArrayList<Contactos>();
    ImageView botonagregar;
    Adaptador adaptador = new Adaptador(MainActivity.this, listaContactos);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /////////////////////////////////////
        lv = (ListView) findViewById(R.id.listView);
        botonagregar = (ImageView) findViewById(R.id.ivAgregar);
        registerForContextMenu(lv);


///////////////////////////////////////////////////////
       ////BASE DE DATOS ////////////
//////////////////////////////////////////////////////
        //Cargar la base de datos

        AdminBD bd = new AdminBD(this);
        Cursor c = bd.seleccionarTablaContactos();

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String nombre= c.getString(1);
                String celular = c.getString(2);
                String correo  = c.getString(3);

                Contactos user = new Contactos(nombre,celular,correo);
                listaContactos.add(user);
                lv.setAdapter(adaptador);

            } while(c.moveToNext());
        }
///////////////////////////////////////////////////////
        ////Aqui termina lo de la base de datos ////////////
//////////////////////////////////////////////////////

        //Click sobre una celda  del listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this, MainLlamar.class);
                String nombre, celular;
                nombre = listaContactos.get(position).getNombre().toString();
                celular = listaContactos.get(position).getCelular().toString();
                i.putExtra("nombreContacto", nombre);
                i.putExtra("celularContacto", celular);
                startActivityForResult(i, 1);

            }
        });

        //Click boton agregar
        botonagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, MainAgregar.class);

                startActivityForResult(i, 0);


            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            // Si es así mostramos mensaje de cancelado por pantalla.

        } else {

            // De lo contrario, recogemos el resultado de la segunda actividad.
            String nombre, celular, correo,posicion;
            nombre = data.getExtras().getString("nombre").toString();
            celular = data.getExtras().getString("celular").toString();
            correo = data.getExtras().getString("email").toString();
            posicion=data.getExtras().getString("posicion").toString();

            switch (requestCode){

                case 0:

                    AdminBD abd  = new AdminBD(this);
                    abd.insertarRegistro(nombre,celular,correo);
                    listaContactos.add(new Contactos(nombre, celular, correo));
                    lv.setAdapter(adaptador);
                    break;
                case 2:

                    Contactos c = new Contactos(nombre,celular,correo);
                    listaContactos.set(Integer.parseInt(posicion),c);
                    lv.setAdapter(adaptador);
                    break;
            }

        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.listView) {
            AdapterView.AdapterContextMenuInfo infoContacto = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String[] elementoMenu = getResources().getStringArray(R.array.elementoMenuContextual);
            for (int i = 0; i < elementoMenu.length; i++) {
                menu.add(Menu.NONE, i, i, elementoMenu[i]);
                System.out.println("holi");






            }
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo infoContacto = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posicionSeleccionado = item.getItemId();
        String nombre,celular,correo,posicion;


        switch (posicionSeleccionado) {


            case 0:

                Intent e = new Intent(MainActivity.this, MainAgregar.class);

                nombre=listaContactos.get(infoContacto.position).getNombre();
                celular=listaContactos.get(infoContacto.position).getCelular();
                correo=listaContactos.get(infoContacto.position).getEmail();
                posicion= String.valueOf(infoContacto.position);
                e.putExtra("nombreContacto", nombre);
                e.putExtra("celularContacto", celular);
                e.putExtra("emailContacto",correo);
                e.putExtra("posicionContacto",posicion);
                startActivityForResult(e, 2);
                break;

            case 1:


                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Está seguro de eliminar el contacto ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        listaContactos.remove(infoContacto.position);
                        lv.setAdapter(adaptador);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();



                break;

            case 2:

                String numero = listaContactos.get(infoContacto.position).getCelular();
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                startActivity(i);
                break;

            case 3:

                Intent m = new Intent(MainActivity.this, MainMensaje.class);

                nombre=listaContactos.get(infoContacto.position).getNombre();
                celular=listaContactos.get(infoContacto.position).getCelular();
                m.putExtra("nombreContacto", nombre);
                m.putExtra("celularContacto", celular);
                startActivity(m);
                break;

        }
        return true;

    }
}
