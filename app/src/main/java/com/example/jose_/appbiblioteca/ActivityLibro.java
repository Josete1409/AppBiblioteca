package com.example.jose_.appbiblioteca;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class ActivityLibro extends AppCompatActivity implements View.OnClickListener {

    Button btnAñadir, btnVolver, btnInicio, btnAutor, btnEditorial;
    CheckBox cbDisponible;
    EditText txtIsbn, txtTitulo, txtAutor, txtEditorial, txtGenero, resultado;

    //IP de mi Url
    String IP = "http://appbiblioteca.esy.es";
    //Ruta de los Web Services
    String INSERT = IP + "/insertar_libro.php";

    ObtenerWebService hiloconexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro);

        //Enlaces con elementos visuales del XML

        btnAñadir = (Button)findViewById(R.id.btnAñadir);
        btnVolver = (Button)findViewById(R.id.btnVolver);
        btnInicio = (Button)findViewById(R.id.btnInicio);
        btnAutor = (Button)findViewById(R.id.btnAutor);
        btnEditorial = (Button)findViewById(R.id.btnEditorial);
        cbDisponible = (CheckBox)findViewById(R.id.cbDisponible);
        txtIsbn = (EditText)findViewById(R.id.txtIsbn);
        txtTitulo = (EditText)findViewById(R.id.txtTitulo);
        txtAutor = (EditText)findViewById(R.id.txtAutor);
        txtEditorial = (EditText)findViewById(R.id.txtEditorial);
        txtGenero = (EditText)findViewById(R.id.txtGenero);
        resultado = (EditText)findViewById(R.id.resultado);

        //Listener de los botones

        btnAñadir.setOnClickListener(this);
        btnInicio.setOnClickListener(this);
        btnVolver.setOnClickListener(this);
        btnAutor.setOnClickListener(this);
        btnEditorial.setOnClickListener(this);

    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.cbDisponible:
                if (checked)
                    resultado.setText("1");
                else
                    resultado.setText("0");
                break;
            default:

                break;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnAñadir:

                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(INSERT, "1", txtIsbn.getText().toString(), txtTitulo.getText().toString(), txtAutor.getText().toString(), txtEditorial.getText().toString(), txtGenero.getText().toString(), resultado.getText().toString());
                txtIsbn.setText("");
                txtTitulo.setText("");
                txtAutor.setText("");
                txtEditorial.setText("");
                txtGenero.setText("");
                cbDisponible.setChecked(false);

                break;
            case R.id.btnAutor:

                Intent intentAutorLibro = new Intent(ActivityLibro.this, ActivityAutorLibro.class);
                startActivity(intentAutorLibro);

                break;
            case R.id.btnEditorial:

                Intent intentEditorialLibro = new Intent(ActivityLibro.this, ActivityEditorialLibro.class);
                startActivity(intentEditorialLibro);

                break;
            case R.id.btnInicio:

                Intent intentInicio = new Intent(ActivityLibro.this, MainActivity.class);
                startActivity(intentInicio);

                break;
            case R.id.btnVolver:

                Intent intentVolver = new Intent(ActivityLibro.this, ActivityOpcionesAnadir.class);
                startActivity(intentVolver);

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
                    jsonParam.put("isbn", params[2]);
                    jsonParam.put("titulo", params[3]);
                    jsonParam.put("id_autor", params[4]);
                    jsonParam.put("id_editorial", params[5]);
                    jsonParam.put("tema", params[6]);
                    jsonParam.put("disponible", params[7]);

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
                            devuelve = "Libro añadido correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "El libro no pudo añadirse";
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

