package com.david.trabajogradoetsiit.Sistema;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

 * Activity para gestionar la lista de comunidades

 * @author: David Martínez Vázquez

 */
public class CCAA extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cca);



        List<String> com = new ArrayList<>();

        ComplejoDataSource cds = new ComplejoDataSource(this);
        cds.open();
        com = cds.getCCAA();
        cds.close();

        lista = (ListView) findViewById(R.id.listaCCAA);
        final ArrayAdapter adapter = new ArrayAdapter<String>(CCAA.this, android.R.layout.simple_list_item_1  ,com);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CCAA.this, DatosLugar.class);
                intent.putExtra("tipolugar", "comunidad");
                intent.putExtra("lugar", adapter.getItem(position).toString());
                startActivity(intent);
            }
        });

    }
}
