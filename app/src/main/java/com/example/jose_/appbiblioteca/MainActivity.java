package com.example.jose_.appbiblioteca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnRegistro, btnListados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistro = (Button)findViewById(R.id.btnRegistro);
        btnListados = (Button)findViewById(R.id.btnListados);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentRegistro = new Intent(MainActivity.this, ActivityOpcionesAnadir.class);
                startActivity(intentRegistro);

            }
        });

        btnListados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentListados = new Intent(MainActivity.this, ActivityOpcionesListados.class);
                startActivity(intentListados);

            }
        });
    }
}

