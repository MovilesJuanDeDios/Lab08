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

public class ProductoView extends AppCompatActivity {

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
        setContentView(R.layout.activity_producto_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        spinner = (Spinner) findViewById(R.id.spinner_tipo);

        list.add("Canasta Basica");
        list.add("Popular");
        list.add("Suntuario");

        adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterTipo);

        editData();
        agregar();
        cancelar();
    }

    public void editData() {
        ((EditText) findViewById(R.id.codigo)).setText(getIntent().getStringExtra("codigo"));
        (findViewById(R.id.codigo)).setEnabled(false);
        ((EditText) findViewById(R.id.nombre_producto)).setText(getIntent().getStringExtra("nombre"));
        Double precio=getIntent().getDoubleExtra("precio",0);
        ((EditText) findViewById(R.id.precio)).setText(precio.toString());
        int choosen=getIntent().getIntExtra("importado",0);
        RadioButton radio1 = findViewById(R.id.importado);
        RadioButton radio2 = findViewById(R.id.nacional);
        if(choosen==1){
            radio1.setChecked(true);
            radio2.setChecked(false);
        }
        else{
            radio1.setChecked(false);
            radio2.setChecked(true);
        }
        String tipo = getIntent().getStringExtra("tipo");
        ((Spinner)findViewById(R.id.spinner_tipo)).setSelection(adapterTipo.getPosition(getIntent().getStringExtra("tipo")));

        if(tipo.equals("Canasta Basica")){
            ((EditText) findViewById(R.id.porcentaje)).setText("5");
            String impuesto=Double.toString(0.05*precio);
            ((EditText) findViewById(R.id.impuesto)).setText(impuesto);
            String pfinal=Double.toString(Double.parseDouble(impuesto)+precio);
            ((EditText) findViewById(R.id.precio_final)).setText(pfinal);
        }
        if(tipo.equals("Popular")){
            ((EditText) findViewById(R.id.porcentaje)).setText("10");
            String impuesto=Double.toString(0.10*precio);
            ((EditText) findViewById(R.id.impuesto)).setText(impuesto);
            String pfinal=Double.toString(Double.parseDouble(impuesto)+precio);
            ((EditText) findViewById(R.id.precio_final)).setText(pfinal);
        }
        if(tipo.equals("Suntuario")){
            ((EditText) findViewById(R.id.porcentaje)).setText("15");
            String impuesto=Double.toString(0.15*precio);
            ((EditText) findViewById(R.id.impuesto)).setText(impuesto);
            String pfinal=Double.toString(Double.parseDouble(impuesto)+precio);
            ((EditText) findViewById(R.id.precio_final)).setText(pfinal);
        }
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
                    final String url = "http://10.0.2.2:8080/Servlet/Servlet?accion=set&codigo="+codigo+"&nombre="+nombreProducto+"&precio="+precio+"&importado="+imp+"&tipo="+nombreTipo;

                    JsonConnection jconexion = new JsonConnection();
                    jconexion.execute(new String[]{url,"POST"});

                    Double precioP = Double.parseDouble(precio);
                    Data.listaProducto.get(position).setNombreProducto(nombreProducto);
                    Data.listaProducto.get(position).setPrecio(precioP);
                    Data.listaProducto.get(position).setImportado(imp);
                    Data.listaProducto.get(position).setNombreTipo(nombreTipo);

                    Intent intent = new Intent(ProductoView.this, ProductosList.class);
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
                Intent intent = new Intent(ProductoView.this, ProductosList.class);
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
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.precio)).getText().toString())) {
            go = false;
        }
        if(spinner.getSelectedItem().equals(" ")) {
            go = false;
        }
        return go;
    }

}
