package com.david.trabajogradoetsiit.Sistema;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.david.trabajogradoetsiit.Datos.ComplejoDataSource;
import com.david.trabajogradoetsiit.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**

 * Activity para gestionar la lista de provincias

 * @author: David Martínez Vázquez

 */
public class Provincias extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ListView lista;
    private SearchView mSearchView;

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provincias);

        mSearchView = (SearchView) findViewById(R.id.busquedaProvincias);
        lista = (ListView) findViewById(R.id.listaProvincias);

        lista.setTextFilterEnabled(true); //buscador

        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar provincias");


        List<String> prov = new ArrayList<>();

        ComplejoDataSource cds = new ComplejoDataSource(this);
        cds.open();
        prov = cds.getProvincias();
        cds.close();


        adapter = new ArrayAdapter<String>(Provincias.this, android.R.layout.simple_list_item_1  ,prov);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Provincias.this, DatosLugar.class);
                intent.putExtra("tipolugar", "provincia");
                intent.putExtra("lugar", adapter.getItem(position).toString());
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**

     * Actualiza el texto del cuadro de busqueda

     * @param newText Texto que se muestra en el cuadro de busqueda

     */
    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter("");
            lista.clearTextFilter();
        } else {
            adapter.getFilter().filter(newText.toString());
        }
        return true;
    }
}
