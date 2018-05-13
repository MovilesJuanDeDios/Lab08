package com.example.casca.producto.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.casca.producto.ConnectionHelper.JsonConnection;
import com.example.casca.producto.Model.Producto;
import com.example.casca.producto.R;
import com.example.casca.producto.Utils.Data;

import static android.R.layout.simple_list_item_1;

public class ProductosList extends AppCompatActivity {
    public static final String url="http://10.0.2.2:8080/Servlet/Servlet?accion=consultarProductos";
    ArrayAdapter<Producto> adapter;
    SwipeMenuListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listview = (SwipeMenuListView) findViewById(R.id.lista_productos);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductosList.this, Productos.class);
                startActivity(intent);
                finish();
            }
        });

        final SearchView searchAlumno = (SearchView)findViewById(R.id.buscar_alumno);
        searchAlumno.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        adapter = new ArrayAdapter<>(this, simple_list_item_1, Data.listaProducto);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "edit" item
                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x00, 0x00)));
                // set item width
                editItem.setWidth(120);
                // set a icon
                editItem.setIcon(R.drawable.ic_action_edit);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x00, 0x00)));
                // set item width
                deleteItem.setWidth(120);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_delete);
                // add to menu
                menu.addMenuItem(deleteItem);

            }
        };

        listview.setMenuCreator(creator);

        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String nombre;
                String codigo;
                int importado;
                double precio;
                String tipo;

                Boolean edit;

                switch (index) {
                    case 0:
                        codigo = Data.listaProducto.get(position).getCodigo();
                        nombre = Data.listaProducto.get(position).getNombreProducto();
                        precio = Data.listaProducto.get(position).getPrecio();
                        importado = Data.listaProducto.get(position).getImportado();
                        tipo = Data.listaProducto.get(position).getNombreTipo();
                        edit = true;

                        Intent intent = new Intent(ProductosList.this, ProductoView.class);

                        Producto producto = (Producto) listview.getItemAtPosition(position);
                        //intent.putExtra("producto", producto);

                        intent.putExtra("codigo", codigo);
                        intent.putExtra("nombre", nombre);
                        intent.putExtra("precio", precio);
                        intent.putExtra("importado", importado);
                        intent.putExtra("tipo", tipo);
                        intent.putExtra("edit", edit);
                        intent.putExtra("position", position);

                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        Data.listaProducto.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }

    public void addData() {
        JsonConnection jconexion = new JsonConnection();
        jconexion.execute(new String[]{url,"GET"});
    }

}
