package com.example.casca.productos.ConnectionHelper;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.casca.productos.Model.Producto;
import com.example.casca.productos.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    @Override
    protected List<Producto> doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection = null;
        List<Producto> productos= new ArrayList<>();

        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //HTTP header
            /*urlConnection.setRequestProperty("Authorization", "Bearer "+ token);*/

            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = readStream(urlConnection.getInputStream());
                Log.v("CatalogClient-Response", responseString);
                productos = parseproductoData(responseString);
            }else{
                Log.v("CatalogClient", "Response code:"+ responseCode);
                Log.v("CatalogClient", "Response message:"+ responseMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }


        return productos;
    }

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


    private List<Producto> parseproductoData(String jString){

        List<Producto> productoList = new ArrayList<Producto>();
        try {
            JSONObject jObj = new JSONObject(jString);
            String totalItems= jObj.getString("totalItems");
            Log.v("totalItems",totalItems);
            if (Integer.parseInt(totalItems) == 0) {
                //((TextView) findViewById(R.id.JSON_value)).setText("You have no productos in this shelf");
            } else {
                JSONArray items = jObj.getJSONArray("items");
                if(items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        String title = items.getJSONObject(i).getJSONObject("volumeInfo").getString("title");
                        String picURL = items.getJSONObject(i).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
                        String isbn = items.getJSONObject(i).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers").getJSONObject(1).getString("identifier");
                        //the value of progress is a placeholder here....
                        Producto producto = new Producto();
                        productoList.add(producto);
                        Log.v("productoList", "Title "+ title + "thumbnail "+ picURL + "isbn " + isbn);
                    }

                }

            }

        } catch (JSONException e) {
            Log.e("CatalogClient", "unexpected JSON exception", e);
        }

        return productoList;
    }






    protected void onPostExecute(List<Producto> productos) {
        super.onPostExecute(productos);
        //make use of data..


    }
}
