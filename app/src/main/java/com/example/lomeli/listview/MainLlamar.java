package com.example.lomeli.listview;


import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainLlamar extends AppCompatActivity {


    TextView tvNombre, tvCelular;
    ImageView botonLlamar,botonMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamar);

        tvNombre = (TextView) findViewById(R.id.textViewMostrarNombre);
        tvCelular = (TextView) findViewById(R.id.textViewMostrarCelular);
        botonLlamar = (ImageView) findViewById(R.id.imgBotonLlamar);

        botonMensaje= (ImageView) findViewById(R.id.buttonMensaje);

        //Llegan los datos del main activity 1
        Bundle datosContacto = getIntent().getExtras();
        String nombre, celular;
        nombre = datosContacto.getString("nombreContacto");
        celular = datosContacto.getString("celularContacto");
        tvNombre.setText(nombre);
        tvCelular.setText(celular);

        botonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = tvCelular.getText().toString();

                Toast.makeText(MainLlamar.this, "Presionado: " + numero, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));

                if (ActivityCompat.checkSelfPermission(MainLlamar.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                    return;

                startActivity(i);





            }
        });

       botonMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainLlamar.this, MainMensaje.class);
                String nombre,celular;
                nombre=tvNombre.getText().toString();
                celular=tvCelular.getText().toString();
                i.putExtra("nombreContacto", nombre);
                i.putExtra("celularContacto", celular);
                startActivity(i);
            }
        });




    }
}
