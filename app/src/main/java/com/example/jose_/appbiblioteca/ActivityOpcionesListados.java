package com.example.jose_.appbiblioteca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityOpcionesListados extends AppCompatActivity implements View.OnClickListener {

    Button btnSocio, btnLibro, btnAutor, btnEditorial, btnPrestamo, btnInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_listados);

        //Enlaces con elementos visuales del XML

        btnSocio = (Button)findViewById(R.id.btnSocio);
        btnLibro = (Button)findViewById(R.id.btnLibro);
        btnAutor = (Button)findViewById(R.id.btnAutor);
        btnEditorial = (Button)findViewById(R.id.btnEditorial);
        btnPrestamo = (Button)findViewById(R.id.btnPrestamo);
        btnInicio = (Button)findViewById(R.id.btnInicio);

        //Listener de los botones

        btnSocio.setOnClickListener(this);
        btnLibro.setOnClickListener(this);
        btnAutor.setOnClickListener(this);
        btnEditorial.setOnClickListener(this);
        btnPrestamo.setOnClickListener(this);
        btnInicio.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSocio:

                Intent intentSocio = new Intent(ActivityOpcionesListados.this, ActivityListaSocio.class);
                startActivity(intentSocio);

                break;
            case R.id.btnLibro:

                Intent intentLibro = new Intent(ActivityOpcionesListados.this, ActivityListaLibro.class);
                startActivity(intentLibro);

                break;
            case R.id.btnAutor:

                Intent intentAutor = new Intent(ActivityOpcionesListados.this, ActivityListaAutor.class);
                startActivity(intentAutor);

                break;
            case R.id.btnEditorial:

                Intent intentEditorial = new Intent(ActivityOpcionesListados.this, ActivityListaEditorial.class);
                startActivity(intentEditorial);

                break;
            case R.id.btnPrestamo:

                Intent intentPrestamo = new Intent(ActivityOpcionesListados.this, ActivityListaPrestamo.class);
                startActivity(intentPrestamo);

                break;
            case R.id.btnInicio:

                Intent intentInicio = new Intent(ActivityOpcionesListados.this, MainActivity.class);
                startActivity(intentInicio);

                break;
            default:


                break;
        }
    }
}
