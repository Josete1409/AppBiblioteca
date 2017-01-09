package com.example.jose_.appbiblioteca;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

public class ActivityPrestamo extends AppCompatActivity implements View.OnClickListener {

    Button btnInicio, btnVolver, btnAñadir, btnLibro, btnSocio;
    EditText txtFecha, txtLibro, txtSocio, txtFechaDevolucion, txtResultado;
    CheckBox cbPrestado;

    //IP de mi Url
    String IP = "http://appbiblioteca.esy.es";
    //Ruta de los Web Services
    String INSERT = IP + "/insertar_prestamo.php";

    ObtenerWebService hiliconexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamo);

        //Enlaces con elementos visuales del XML

        btnInicio = (Button)findViewById(R.id.btnInicio);
        btnVolver = (Button)findViewById(R.id.btnVolver);
        btnAñadir = (Button)findViewById(R.id.btnAñadir);
        btnLibro = (Button)findViewById(R.id.btnLibro);
        btnSocio = (Button)findViewById(R.id.btnSocio);
        txtFecha = (EditText)findViewById(R.id.txtFecha);
        txtLibro = (EditText)findViewById(R.id.txtLibro);
        txtSocio = (EditText)findViewById(R.id.txtSocio);
        txtFechaDevolucion = (EditText)findViewById(R.id.txtFechaDevolucion);
        txtResultado = (EditText)findViewById(R.id.txtResultado);
        cbPrestado = (CheckBox)findViewById(R.id.cbPrestado);

        //Listener de los botones

        btnInicio.setOnClickListener(this);
        btnVolver.setOnClickListener(this);
        btnAñadir.setOnClickListener(this);
        btnLibro.setOnClickListener(this);
        btnSocio.setOnClickListener(this);

    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.cbPrestado:
                if (checked)
                    txtResultado.setText("1");
                else
                    txtResultado.setText("0");
                break;
            default:

                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnInicio:

                Intent intentInicio = new Intent(ActivityPrestamo.this, MainActivity.class);
                startActivity(intentInicio);

                break;
            case R.id.btnVolver:

                Intent intentVolver = new Intent(ActivityPrestamo.this, ActivityOpcionesAnadir.class);
                startActivity(intentVolver);

                break;
            case R.id.btnAñadir:

                hiliconexion = new ObtenerWebService();
                hiliconexion.execute(INSERT, "1", txtFecha.getText().toString(), txtLibro.getText().toString(), txtSocio.getText().toString(), txtFechaDevolucion.getText().toString(), txtResultado.getText().toString());
                txtFecha.setText("");
                txtLibro.setText("");
                txtSocio.setText("");
                txtFechaDevolucion.setText("");
                cbPrestado.setChecked(false);

                break;
            case R.id.btnLibro:

                Intent intentLibroPrestamo = new Intent(ActivityPrestamo.this, ActivityLibroPrestamo.class);
                startActivity(intentLibroPrestamo);

                break;
            case R.id.btnSocio:

                Intent intentSocioPrestamo = new Intent(ActivityPrestamo.this, ActivitySocioPrestamo.class);
                startActivity(intentSocioPrestamo);

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
                    jsonParam.put("fecha", params[2]);
                    jsonParam.put("id_libro", params[3]);
                    jsonParam.put("id_socio", params[4]);
                    jsonParam.put("Fecha_devolucion", params[5]);
                    jsonParam.put("Prestado", params[6]);

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
                            devuelve = "Prestamo añadido correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "El prestamo no pudo añadirse";
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

