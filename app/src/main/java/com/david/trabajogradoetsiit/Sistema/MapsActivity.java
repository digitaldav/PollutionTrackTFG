package com.david.trabajogradoetsiit.Sistema;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.david.trabajogradoetsiit.Datos.Complejo;
import com.david.trabajogradoetsiit.Datos.ComplejoDataSource;
import com.david.trabajogradoetsiit.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**

 * Gestiona el mapa y toda su informacion

 * @author David Martínez Vázquez

 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private List<Complejo> complejos;
    private Complejo complejo;

    /**

     * onCreate

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**

     * Para modificar informacion del mapa

     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String tipo = getIntent().getExtras().getString("tipo");

        if(tipo.equals("complejo")) {

            complejo = (Complejo) getIntent().getExtras().getSerializable("complejo");

            LatLng nuevo = new LatLng(complejo.getLongitud(), complejo.getLatitud());
            mMap.addMarker(new MarkerOptions().position(nuevo).title("Complejo: " + complejo.getNombre()));
       
        }else{
            String lugar = getIntent().getExtras().getString("lugar");
            ComplejoDataSource cds = new ComplejoDataSource(this);
            cds.open();
            complejos = cds.getComplejosLugar(tipo, lugar);

            for (Complejo c : complejos) {
                LatLng nuevo = new LatLng(c.getLongitud(), c.getLatitud());
                mMap.addMarker(new MarkerOptions().position(nuevo).title("Complejo: " + c.getNombre()));
            }

        }


    }
}
