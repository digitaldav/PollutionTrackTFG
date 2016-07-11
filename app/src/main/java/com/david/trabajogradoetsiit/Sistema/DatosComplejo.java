package com.david.trabajogradoetsiit.Sistema;

import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.david.trabajogradoetsiit.Datos.Complejo;
import com.david.trabajogradoetsiit.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**

 * Gestiona la vista de los detalles del complejo

 * @author David Martínez Vázquez

 */
public class DatosComplejo extends AppCompatActivity {


    private String nombre;
    private Complejo complejo;

    /**

     * onCreate

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complejo);


        complejo = (Complejo) getIntent().getExtras().get("complejo");


        Button b = (Button)findViewById(R.id.mapaComplejo);

        TextView t=  (TextView) findViewById(R.id.textComplejo);
        t.setText(complejo.getNombre());


        ((TextView) findViewById(R.id.textoIDComplejo)).setText(""+complejo.getId_complejo());
        ((TextView) findViewById(R.id.textoDireccionComplejo)).setText(complejo.getDireccion() + ", "+ complejo.getMunicipio());
        ((TextView) findViewById(R.id.textoActividadComplejo)).setText(""+ complejo.getActividad());
        ((TextView) findViewById(R.id.textoEmpresaComplejo)).setText(""+complejo.getEmpresa());
        ((TextView) findViewById(R.id.textoProvinciaComplejo)).setText(""+complejo.getProvincia());
        ((TextView) findViewById(R.id.textoComunidadComplejo)).setText(""+complejo.getComunidad());


        if(complejo.getEmision() == null){
            ((TextView) findViewById(R.id.contaminacionText)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.medioText)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textoCantidadEmision)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textoEmisionComplejo)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textoMedioEmision)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.tipoECtext)).setText("No hay datos de emisiones");
        }else{
            ((TextView) findViewById(R.id.textoEmisionComplejo)).setText(""+complejo.getEmision().getTipo());
            ((TextView) findViewById(R.id.textoCantidadEmision)).setText(""+String.format("%.02f", Float.parseFloat(complejo.getEmision().getCantidad())) +" T/año" );
            ((TextView) findViewById(R.id.textoMedioEmision)).setText(""+complejo.getEmision().getMedio());
        }


        if(complejo.getResiduo() == null){
            ((TextView) findViewById(R.id.cantidadRText)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.tratamientoText)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textoCantidadResiduo)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textoTratamientoResiduo)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textoResiduoComplejo)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.residuoText)).setText("No hay datos de residuos");
        }else{
            ((TextView) findViewById(R.id.textoResiduoComplejo)).setText(""+ complejo.getResiduo().getTipo());
            ((TextView) findViewById(R.id.textoCantidadResiduo)).setText(""+String.format("%.04f", Float.parseFloat(complejo.getResiduo().getCantidad()))+" T/año" );
            ((TextView) findViewById(R.id.textoTratamientoResiduo)).setText(""+complejo.getResiduo().getTratamiento());

        }


        if (b != null) {
            b.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(DatosComplejo.this, MapsActivity.class);
                    intent.putExtra("complejo", (Serializable) complejo);
                    intent.putExtra("tipo", "complejo");
                    startActivity(intent);

                }
            });
        }

    }
}
