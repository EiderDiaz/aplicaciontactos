package com.example.lomeli.listview;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class MainMensaje extends AppCompatActivity implements Validator.ValidationListener {

    TextView nombreC,celularC;
    @NotEmpty (message = "Campo Vacio")
    EditText cajaDeTexto;
    ImageView enviar;
    Validator validator;
    String celular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        nombreC= (TextView) findViewById(R.id.txtMensajeNombre);
        celularC= (TextView) findViewById(R.id.txtCelularMensaje);
        cajaDeTexto= (EditText) findViewById(R.id.edtxtMensaje);
        enviar= (ImageView) findViewById(R.id.btnEnviarMensaje);
        ///////////////////////////////////
        validator = new Validator(this);
        validator.setValidationListener(this);
        ////////////////////////////////


        Bundle datosContacto = getIntent().getExtras();

        final String nombre;
        nombre = datosContacto.getString("nombreContacto");
        celular = datosContacto.getString("celularContacto");


        nombreC.setText(nombre);
        celularC.setText(celular);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                validator.validate();


            }
        });

    }

    @Override
    public void onValidationSucceeded() {
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(celular, null, cajaDeTexto.getText().toString(), null, null);

            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos." + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

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
