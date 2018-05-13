package com.example.casca.producto.ConnectionHelper;

import android.os.AsyncTask;
import android.util.Log;

import com.example.casca.producto.Model.Producto;
import com.example.casca.producto.Utils.Data;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by casca on 12/05/2018.
 */

public class JsonConnection extends AsyncTask<String, String, List<Producto>> {
    // doInBackground llama a los otros 2
    @Override
    protected List<Producto> doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection = null;
        List<Producto> productos= new ArrayList<>();

        try {
            if (params[1].equals("POST")) {
                Log.v("Haciendo POST","Valeria");
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    Log.v("CatalogClient-Response", "GUARDADO EN LA VALE");
                }
            }
            else{
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                //HTTP header
                //urlConnection.setRequestProperty("Authorization", "Bearer "+ token);

                int responseCode = urlConnection.getResponseCode();
                String responseMessage = urlConnection.getResponseMessage();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    String responseString = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient-Response", responseString);
                    productos = parseProductoData(responseString);
                }else{
                    Log.v("CatalogClient", "Response code:"+ responseCode);
                    Log.v("CatalogClient", "Response message:"+ responseMessage);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

        return productos;
    }
    /* --------------------------------------------------------------- */
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
    /* --------------------------------------------------------------- */
    private List<Producto> parseProductoData(String jString){

        List<Producto> productoList = new ArrayList<Producto>();
        try {
            /*
            JSONObject jObj = new JSONObject(jString);
            String totalItems= jObj.getString("totalItems");
            Log.v("totalItems",totalItems);
            if (Integer.parseInt(totalItems) == 0) {
                //((TextView) findViewById(R.id.JSON_value)).setText("You have no productos in this shelf");
            } else {*/
                //JSONArray items = jObj.getJSONArray("items");
                JSONArray items = new JSONArray(jString);
                if(items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        String codigo = items.getJSONObject(i).getString("codigo");
                        String nombreProducto = items.getJSONObject(i).getString("nombreProducto");
                        Double precio = items.getJSONObject(i).getDouble("precio");
                        int importado = items.getJSONObject(i).getInt("importado");
                        String nombreTipo = items.getJSONObject(i).getString("tipo");
                        //the value of progress is a placeholder here....
                        Producto producto = new Producto(codigo,nombreProducto,precio,importado,nombreTipo);
                        Data.listaProducto.add(producto);
                        productoList.add(producto);
                    }
                }

           // }

        } catch (JSONException e) {
            Log.e("CatalogClient", "unexpected JSON exception", e);
        }

        return productoList;
    }
    /* --------------------------------------------------------------- */
    protected void onPostExecute(List<Producto> productos) {
        super.onPostExecute(productos);
        //make use of data..
    }
    public List<Producto> call(String url){
        return doInBackground(url);
    }
}
