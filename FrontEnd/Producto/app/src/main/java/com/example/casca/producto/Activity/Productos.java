package com.example.casca.producto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.casca.producto.ConnectionHelper.JsonConnection;
import com.example.casca.producto.Model.Producto;
import com.example.casca.producto.R;
import com.example.casca.producto.Utils.Data;

import java.util.ArrayList;
import java.util.List;


public class Productos extends AppCompatActivity {
    public static final String url2="http://10.0.2.2:8080/Servlet/Servlet?accion=consultarProductos";
    private Button btn_agregar;
    private Button btn_cancelar;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    String codigo;
    String nombreProducto;
    String precio;
    String importado;
    String nombreTipo;
    private int position;

    ArrayAdapter<String> adapterTipo;
    Spinner spinner;

    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.spinner_tipo);

        list.add("Canasta Basica");
        list.add("Popular");
        list.add("Suntuario");

        adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterTipo);

        agregar();
        cancelar();
    }

    private void agregar() {
        btn_agregar = (Button) findViewById(R.id.button_aceptar_producto);

        btn_agregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                codigo = ((EditText) findViewById(R.id.codigo)).getText().toString();
                nombreProducto = ((EditText) findViewById(R.id.nombre_producto)).getText().toString();
                precio = ((EditText) findViewById(R.id.precio)).getText().toString();
                nombreTipo =  spinner.getSelectedItem().toString();
                position = getIntent().getIntExtra("position",-1);

                radioGroup = (RadioGroup) findViewById(R.id.rdgGrupo);

                if (validate()) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    importado = radioButton.getText().toString();
                    int imp=0;
                    if(importado.equals("Importado"))
                        imp=1;
                    final String url = "http://10.0.2.2:8080/Servlet/Servlet?accion=agregar&codigo="+codigo+"&nombre="+nombreProducto+"&precio="+precio+"&importado="+imp+"&tipo="+nombreTipo;

                    //Data.listaProducto.clear();
                    JsonConnection jconexion = new JsonConnection();
                    jconexion.execute(new String[]{url,"POST"});

                    Double precioP = Double.parseDouble(precio);
                    Producto producto= new Producto(codigo,nombreProducto,precioP,imp,nombreTipo);
                    Data.listaProducto.add(producto);
                    /*JsonConnection jconexion2 = new JsonConnection();
                    jconexion2.execute(new String[]{url2,"GET"});*/

                    Intent intent = new Intent(Productos.this, ProductosList.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void cancelar() {
        btn_cancelar = (Button) findViewById(R.id.button_cancelar_producto);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Productos.this, ProductosList.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validate() {
        boolean go = true;

        if (TextUtils.isEmpty(codigo)) {
            go = false;
        }
        if (TextUtils.isEmpty(nombreProducto)) {
            go = false;
        }
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            go = false;
        }
        if (TextUtils.isEmpty(precio)) {
            go = false;
        }
        if(spinner.getSelectedItem().equals(" ")) {
            go = false;
        }
        return go;
    }


}
