package com.example.jose_.appbiblioteca;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ActivitySocio extends AppCompatActivity implements View.OnClickListener {

    Button btnAñadir, btnVolver, btnInicio;
    EditText txtNombre, txtPoblacion, txtNif, txtFecha_Nacimiento, txtFecha_alta;

    //IP de mi Url
    String IP = "http://appbiblioteca.esy.es";
    //Ruta de los Web Services
    String INSERT = IP + "/insertar_socio.php";

    ObtenerWebService hiloconexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socio);

        //Enlaces con elementos visuales del XML

        btnAñadir = (Button) findViewById(R.id.btnAñadir);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnInicio = (Button) findViewById(R.id.btnInicio);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtPoblacion = (EditText) findViewById(R.id.txtPoblacion);
        txtNif = (EditText) findViewById(R.id.txtNif);
        txtFecha_Nacimiento = (EditText) findViewById(R.id.txtFecha_Nacimiento);
        txtFecha_alta = (EditText) findViewById(R.id.txtFecha_alta);

        //Listener de los botones

        btnVolver.setOnClickListener(this);
        btnInicio.setOnClickListener(this);
        btnAñadir.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAñadir:

                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(INSERT, "1", txtNombre.getText().toString(), txtPoblacion.getText().toString(), txtNif.getText().toString(), txtFecha_alta.getText().toString(), txtFecha_Nacimiento.getText().toString());
                txtNombre.setText("");
                txtPoblacion.setText("");
                txtNif.setText("");
                txtFecha_alta.setText("");
                txtFecha_Nacimiento.setText("");

                break;
            case R.id.btnVolver:

                Intent intentVolver = new Intent(ActivitySocio.this, ActivityOpcionesAnadir.class);
                startActivity(intentVolver);

                break;
            case R.id.btnInicio:

                Intent intentInicio = new Intent(ActivitySocio.this, MainActivity.class);
                startActivity(intentInicio);

                break;
            default:

                break;
        }
    }

    public class ObtenerWebService extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null;
            String devuelve = "";

            if (params[1] == "1") {

                try {
                    HttpURLConnection urlConn;
                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("nombre", params[2]);
                    jsonParam.put("poblacion", params[3]);
                    jsonParam.put("nif", params[4]);
                    jsonParam.put("fecha_alta", params[5]);
                    jsonParam.put("fecha_nacimiento", params[6]);

                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }

                        JSONObject respuestaJSON = new JSONObject(result.toString());

                        String resultJSON = respuestaJSON.getString("estado");

                        if (resultJSON == "1") {
                            devuelve = "Socio añadido correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "El socio no pudo añadirse";
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return devuelve;
            }

            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) { super.onPostExecute(s); }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
