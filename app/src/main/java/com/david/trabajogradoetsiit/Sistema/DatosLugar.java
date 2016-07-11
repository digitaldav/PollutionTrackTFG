package com.david.trabajogradoetsiit.Sistema;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.david.trabajogradoetsiit.Datos.Complejo;
import com.david.trabajogradoetsiit.Datos.ComplejoDataSource;
import com.david.trabajogradoetsiit.R;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**

 * Activity para gestionar la vista que muestra los datos de un lugar

 * @author David Martínez Vázquez

 */
public class DatosLugar extends AppCompatActivity {

    private ListView lista, l2;
    private String tab1;
    private String tipo;
    private String lugar;
    private float cantidadAire;

    private List<Complejo> complejos;

    private TextView tLugar;
    private TextView tCantidadEm;
    private TextView tCantidadCom;
    private Button bMapa;
    private TabHost tabs;

    /**

     * onCreate

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_lugar);

        //obtener datos de anterior activity
        tipo = getIntent().getExtras().getString("tipolugar");
        lugar = getIntent().getExtras().getString("lugar");

        //identificar componentes de layout
        tLugar = (TextView)findViewById(R.id.textLugar);
        tCantidadCom = (TextView)findViewById(R.id.textoCantidadComplejos);
        tCantidadEm = (TextView)findViewById(R.id.textoCantidadEmisionLugar);
        bMapa = (Button)findViewById(R.id.mapaLugar);
        lista = (ListView) findViewById(R.id.lista1); //lista para municipios
        l2 = (ListView) findViewById(R.id.lista2); //lista para complejos
        tabs=(TabHost)findViewById(R.id.tabhost);


        //cargar complejos
        ComplejoDataSource cds  = new ComplejoDataSource(this);
        cds.open();
        complejos = cds.getComplejosLugar(tipo, lugar);
        cds.close();

        //calulo de emisiones del lugar
        calcularEmisiones();

        //crear la vista con pestañas y añadir lugares y complejos
        ajustarVistaTabulada();

        //asignamos texto
        tLugar.setText(lugar);
        tCantidadCom.setText(""+ complejos.size());
        tCantidadEm.setText(String.format("%.02f", cantidadAire) +" T/año");



        //funcionalidad del boton mapa
        bMapa.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DatosLugar.this, MapsActivity.class);
                intent.putExtra("tipo", tipo);
                intent.putExtra("lugar", lugar);
                //no se pasa el array porque en ocasiones puede exceder el tamaño minimo
                startActivity(intent);

            }
        });






    }

    /**

     * Muesta la vista con pestañas dependiendo del tipo de lugar

     */
    private void ajustarVistaTabulada(){

        tabs.setup();
        TabHost.TabSpec spec;

        if(tipo.equals("provincia")){
            tab1 = "Municipios";
            spec = tabs.newTabSpec("mitab1");
            spec.setContent(R.id.tab1);
            spec.setIndicator(tab1);
            tabs.addTab(spec);

            List<String> municipios = new ArrayList<>();
            for(Complejo c : complejos){
                municipios.add( c.getMunicipio() );
            }
            final ArrayAdapter adapter = new ArrayAdapter<String>(DatosLugar.this, android.R.layout.simple_list_item_1  ,ordenarString(municipios));
            lista.setAdapter(adapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(DatosLugar.this, DatosLugar.class);
                    intent.putExtra("tipolugar", "municipios");
                    intent.putExtra("lugar", adapter.getItem(position).toString());
                    startActivity(intent);
                }
            });

        }else if(tipo.equals("comunidad")){
            tab1 = "Provincias";
            spec=tabs.newTabSpec("mitab1");
            spec.setContent(R.id.tab1);
            spec.setIndicator(tab1);
            tabs.addTab(spec);

            List<String> provincias = new ArrayList<>();
            for(Complejo c : complejos){
                provincias.add( c.getProvincia() );
            }

            final ArrayAdapter adapter = new ArrayAdapter<String>(DatosLugar.this, android.R.layout.simple_list_item_1  , ordenarString(provincias) );
            lista.setAdapter(adapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(DatosLugar.this, DatosLugar.class);
                    intent.putExtra("tipolugar", "provincia");
                    intent.putExtra("lugar", adapter.getItem(position).toString());
                    startActivity(intent);
                }
            });
        }


        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Complejos");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);



        List<String> nombres = new ArrayList<>();

        for(Complejo c : complejos){
            nombres.add(  c.getNombre()  );
        }

        final ArrayAdapter adapter2 = new ArrayAdapter<String>(DatosLugar.this, android.R.layout.simple_list_item_1  , nombres);
        l2.setAdapter(adapter2);
        l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DatosLugar.this, DatosComplejo.class);
                for(Complejo c : complejos){
                    if(c.getNombre().equals( adapter2.getItem(position).toString())){
                        intent.putExtra("complejo", c);
                        break;
                    }
                }
                startActivity(intent);
            }
        });

    }

    /**

     * Calcula las emisiones totales en el aire del lugar

     */
    private void calcularEmisiones(){


        cantidadAire = 0.0f;

        for(Complejo c: complejos) {
            if(c.getEmision()!=null) {
                if(c.getEmision().getMedio().equals("Aire")) {
                    cantidadAire += getValor(Float.parseFloat(c.getEmision().getCantidad()), c.getEmision().getTipo(), c.getEmision().getMedio());
                }
            }

        }
        if(cantidadAire > 0){
            cantidadAire = (float) Math.log(cantidadAire);
        }


    }


    /**

     * Devuelve una lista de String sin repeticiones y ordenada alfabeticamente

     * @param lista Lista de string desordenada

     * @return lista ordenada

     */
    public List<String> ordenarString(List<String> lista){
        HashSet hs = new HashSet();
        hs.addAll(lista);
        lista.clear();
        lista.addAll(hs);
        hs.clear();
        java.util.Collections.sort(lista);
        return lista;
    }


    /**

     * Calcula la contaminacion de un tipo de emision
     * multiplicandola por su peso de contaminante

     * @param valor Valor de contaminacion que se multiplicara por su peso de contaminante

     * @param tipo Tipo de emision

     * @param medio medio en el que emite

     * @return valor calculado

     */
    private float getValor(float valor, String tipo, String medio) {

        float ret=0;

        switch ( tipo ) {
            case "Amoniaco (NH3)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.0001f;
                }
                break;
            case "Óxidos de nitrógeno (NOx/NO2)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.00001f;
                }
                break;
            case "Monóxido de carbono (CO)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.000002f;
                }
                break;
            case "Cadmio y compuestos (como Cd)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.1f;
                }else{
                    ret = valor * 0.2f;
                }
                break;
            case "Carbono orgánico total (COT)":
                if(medio.equals("Litoral")) {
                    ret = valor * 0.00002f;
                }
                break;
            case "Metano (CH4)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.00001f;
                }
                break;
            case "Cloro y compuestos inorgánicos (como HCl)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.0001f;
                }
                break;
            case "Dióxido de carbono (CO2)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.00000001f;
                }
                break;
            case "Compuestos orgánicos volátiles distintos del metano (COVNM)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.00001f;
                }
                break;
            case "Cromo y compuestos (como Cr)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.01f;
                }else{
                    ret = valor * 0.02f;
                }
                break;
            case "Compuestos organoestánnicos (como Sn total)":
                if(medio.equals("Litoral")) {
                    ret = valor * 0.02f;
                }
                break;
            case "Cobre y compuestos (como Cu)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.01f;
                }else{
                    ret = valor * 0.02f;
                }
                break;
            case "Níquel y compuestos (como Ni)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.02f;
                }else{
                    ret = valor * 0.05f;
                }
                break;
            case "Fenoles (como C total) ":
                if(medio.equals("Litoral")) {
                    ret = valor * 0.05f; //agua
                }
                break;
            case "Mercurio y compuestos (como Hg)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.1f;
                }else{
                    ret = valor * 1;
                }
                break;
            case "PCDD + PCDF (dioxinas + furanos) (como Teq)":
                if(medio.equals("Aire")) {
                    ret = valor * 1000;
                }
                break;
            case "Diclorometano (DCM)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.001f;
                }else{
                    ret = valor * 0.1f;
                }
                break;
            case "Partículas (PM10)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.00002f;
                }
                break;
            case "Flúor y compuestos inorgánicos (como HF)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.0002f;
                }
                break;
            case "Plomo y compuestos (como Pb)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.005f;
                }else{
                    ret = valor * 0.05f;
                }
                break;
            case "Arsénico y compuestos (como As)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.05f;
                }else{
                    ret = valor * 0.2f;
                }
                break;
            case "Fósforo total":
                if(medio.equals("Litoral")) {
                    ret = valor * 0.0002f;
                }
                break;
            case "Óxidos de azufre (SOx/SO2)":
                if(medio.equals("Aire")) {
                    ret = valor * 6.66667E-06f;
                }
                break;
            case "Antraceno":
                //ret = valor *
                break;
            case "Óxido nitroso (N2O)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.0001f;
                }
                break;
            case "Hidrofluorocarburos (HFC)":
                if(medio.equals("Aire")) {
                    ret = valor * 0.01f;
                }
                break;
            case "Nitrógeno total":
                if(medio.equals("Litoral")){
                    ret = valor * 0.00002f;
                }
                break;
            case "Zinc y compuestos (como Zn)":
                if(medio.equals("Litoral")){
                    ret = valor * 0.01f;
                }else{
                    ret = valor * 0.005f;
                }
                break;
            case "Naftaleno":
                // no encontrado
                break;
            case "Cianuro de hidrógeno (HCN)":
                if(medio.equals("Aire")){
                    ret = valor * 0.005f;
                }
                break;
            case "Hidrocarburos aromáticos policíclicos totales PRTR (HAP totales PRTR) ":
                if(medio.equals("Litoral")){
                    ret = valor * 0.2f;
                }else{
                    ret = valor * 0.02f;
                }
                break;
            case "Fluoruros (como F total)":
                if(medio.equals("Litoral")){
                    ret = valor * 0.0005f;
                }
                break;
            case "Hidroclorofluorocarburos (HCFC)":
                //no encontrado
                break;
            case "Nonifenol y Etoxilatos de nonilfenol (NP/NPE)":
                //no encontrado
                break;
            case "Cloruros (como Cl total)":
                if(medio.equals("Litoral")){
                    ret = valor * 0.0000005f;
                }
                break;
            case "Cloroalcanos, C10-C13":
                if(medio.equals("Litoral")){
                    ret = valor * 1f;
                }
                break;
            case "Benceno ":
                if(medio.equals("Aire")){
                    ret = valor * 0.001f;
                }
                break;
            case "Cloruro de vinilo":
                //no encontrado
                break;
            case "Triclorometano":
                if(medio.equals("Aire")){
                    ret = valor * 0.002f;
                }
                break;
            case "Clorofluorocarburos (CFC) ":
                //no encontrado
                break;
            case "Ftalato de bis (2-etilhexilo) (DEHP)":
                //no encontrado
                break;
            case "Tetracloroetileno (PER) ":
                if(medio.equals("Aire")){
                    ret = valor * 0.0005f;
                }
                break;
            default:
                ret = 0;
                break;
        }

        return ret;

    }

}
