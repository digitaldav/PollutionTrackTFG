package com.david.trabajogradoetsiit.Sistema;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.david.trabajogradoetsiit.Datos.ComplejoDataSource;
import com.david.trabajogradoetsiit.Datos.DatabaseLoader;
import com.david.trabajogradoetsiit.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**

 * Activity principal

 * @author: David Martínez Vázquez

 */
public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private RelativeLayout bC, bP, bO, bM, bA;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private String municipioActual;

    /**

     * Método onCreate de la Activity

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ComplejoDataSource cds  = new ComplejoDataSource(this);
        cds.crear(); //cargamos la base de datos
        cds.open();
        cds.close();


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }

        bC = (RelativeLayout) findViewById(R.id.botonBusquedaCCAA);
        bP = (RelativeLayout) findViewById(R.id.botonBusquedaProvincias);
        bM = (RelativeLayout) findViewById(R.id.botonBusquedaMunicipios);
        bO = (RelativeLayout) findViewById(R.id.botonSobreAplicacion);

        bA = (RelativeLayout) findViewById(R.id.botonPosicionActual2);


        //pantalla comunidades
        bC.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP ) {
                    Intent intent = new Intent(MainActivity.this, CCAA.class);
                    startActivity(intent);
                }else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                    return false;

                }

                return true;
            }
        });


        //pantalla provincias
        bP.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP ) {
                    Intent intent = new Intent(MainActivity.this, Provincias.class);
                    startActivity(intent);
                }else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                    return false;

                }

                return true;
            }
        });

        //pantalla municipios
        bM.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP ) {
                    Intent intent = new Intent(MainActivity.this, Municipios.class);
                    startActivity(intent);
                }else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                    return false;

                }

                return true;
            }
        });

        //pantalla about
        bO.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP ) {
                    Intent intent = new Intent(MainActivity.this, about.class);
                    startActivity(intent);
                }else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                    return false;

                }

                return true;
            }
        });


        bA.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP ) {
                    if (municipioActual == null) {
                        Toast.makeText(getApplicationContext(), "No se ha encontrado ubicación", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, DatosLugar.class);
                        intent.putExtra("tipolugar", "municipios");
                        intent.putExtra("lugar", municipioActual);
                        startActivity(intent);
                    }
                }else if (event.getAction() == MotionEvent.ACTION_CANCEL){
                    return false;

                }

                return true;
            }
        });






    }

    /**

     * Metodo para poder cargar la localizacion actual

     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Geocoder geocoder;
            List<android.location.Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }
            municipioActual = addresses.get(0).getLocality();

            ((TextView) findViewById(R.id.textoPosActual)).setText(municipioActual);

        }



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /**

     * Muestra un mensaje en caso de no poder encontrar la ubicacion

     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
       ((TextView) findViewById(R.id.textoPosActual)).setText("No encontrada ubicación");
    }

    /**

     * Connect para buscar localizacion

     */
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    /**

     * Parar los servicios de localizacion

     */
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
