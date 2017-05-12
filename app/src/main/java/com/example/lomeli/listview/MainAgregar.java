package com.example.lomeli.listview;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;


public class MainAgregar extends AppCompatActivity implements Validator.ValidationListener {

    ImageView agregar;
    @NotEmpty (message = "Campo Vacio - Ingrese Nombre")
    EditText etNombre;
    @NotEmpty (message = "Campo Vacio - Ingrese Celular" )
    EditText etCelular;
    @NotEmpty (message = "Campo Vacio - Ingrese Correo")
    @Email (message = "Correo Erroneo - ejemplo@correo.com")
    EditText etCorreo;
    TextView titulo;
    String nombre, celular,correo;
    String posicion="0";
    Intent i;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        //////////////////
        etNombre= (EditText) findViewById(R.id.etNombreContacto);
        etCelular= (EditText) findViewById(R.id.etCelularContacto);
        etCorreo= (EditText) findViewById(R.id.etCorreoContacto);
        titulo = (TextView) findViewById(R.id.tvTitloActivdad2);
        ///////////////////////////////////
        validator = new Validator(this);
        validator.setValidationListener(this);
        ////////////////////////////////
        agregar= (ImageView) findViewById(R.id.botonAgregarContactos);

         i = getIntent();
        Bundle datosContacto = i.getExtras();

       
        if (datosContacto != null){
            String cambiaTitutlo ="Editar";
            nombre = datosContacto.getString("nombreContacto");
            celular = datosContacto.getString("celularContacto");
            correo = datosContacto.getString("emailContacto");
            posicion=datosContacto.getString("posicionContacto");

            titulo.setText("Editarb");
            etNombre.setText(nombre);
            etCelular.setText(celular);
            etCorreo.setText(correo);

        }
        else{

        }

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();
              

            }
        });

    }


    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Contacto Guardado", Toast.LENGTH_SHORT).show();
        nombre=etNombre.getText().toString();
        celular=etCelular.getText().toString();
        correo=etCorreo.getText().toString();
        i.putExtra("nombre",nombre);
        i.putExtra("celular",celular);
        i.putExtra("email",correo);
        i.putExtra("posicion",posicion);

        setResult(RESULT_OK,i);


        // Finalizamos la Activity para volver a la anterior
        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
