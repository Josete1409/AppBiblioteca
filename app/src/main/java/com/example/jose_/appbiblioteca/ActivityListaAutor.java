package com.example.jose_.appbiblioteca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ActivityListaAutor extends AppCompatActivity implements View.OnClickListener {

    Button btnListar, btnVolver, btnInicio;
    ListView listAutor;


    // IP de mi Url
    String IP = "http://appbiblioteca.esy.es";
    // Rutas de los Web Services
    String GET = IP + "/appbiblioteca/autores/listado_autores.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_autor);

        // Enlaces con elementos visuales del XML

        btnListar = (Button)findViewById(R.id.btnListar);
        btnInicio = (Button)findViewById(R.id.btnInicio);
        btnVolver = (Button)findViewById(R.id.btnVolver);
        listAutor = (ListView)findViewById(R.id.listAutor);

        // Listener de los botones

        btnListar.setOnClickListener(this);
        btnInicio.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnListar:

                String consulta = GET;
                EnviarRecibirDatos(consulta);

                break;
            case R.id.btnInicio:

                Intent intentInicio = new Intent(ActivityListaAutor.this, MainActivity.class);
                startActivity(intentInicio);

                break;
            case R.id.btnVolver:

                Intent intentVolver = new Intent(ActivityListaAutor.this, ActivityOpcionesListados.class);
                startActivity(intentVolver);

                break;
            default:

                break;
        }
    }

    public void EnviarRecibirDatos(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListView(JSONArray ja){

        ArrayList<String> lista = new ArrayList<>();

        for(int i=0;i<ja.length();i+=3){

            try {

                lista.add(ja.getString(i)+" - "+ja.getString(i+1)+" - "+ja.getString(i+2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listAutor.setAdapter(adaptador);


    }
}