package com.david.trabajogradoetsiit.Sistema;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.david.trabajogradoetsiit.Datos.ComplejoDataSource;
import com.david.trabajogradoetsiit.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**

 * Activity para gestionar la lista de municipios

 * @author: David Martínez Vázquez

 */
public class Municipios extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView lista;
    private SearchView mSearchView;

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipios);

        mSearchView = (SearchView) findViewById(R.id.busquedaMunicipios);
        lista = (ListView) findViewById(R.id.listaMunicipios);

        lista.setTextFilterEnabled(true); //buscador

        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar municipios");


        List<String> mun = new ArrayList<>();

        ComplejoDataSource cds = new ComplejoDataSource(this);
        cds.open();
        mun = cds.getMunicipios();
        cds.close();

        adapter = new ArrayAdapter<String>(Municipios.this, android.R.layout.simple_list_item_1  ,mun);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Municipios.this, DatosLugar.class);
                intent.putExtra("tipolugar", "municipios");
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
