package com.david.trabajogradoetsiit.Sistema;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.david.trabajogradoetsiit.R;

/**

 * Gestiona las vistas del slideview

 */
public class SlideAbout extends Fragment {

    private static final String INDEX = "index";

    private int index;

    /**
     * Crear un nuevo fragment con el index

     * @param index Valor de la pagina

     * @return pagina Slide nueva
     */
    public static SlideAbout newInstance( int index) {


        SlideAbout fragment = new SlideAbout();


        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    /**

     * onCreate

     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //cargar el valor de index
        this.index = (getArguments() != null) ? getArguments().getInt(INDEX)
                : -1;

    }

    /**

     * onCreateView que carga el layout dependiendo del index

     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.about_page, container, false);

        //si index es 2 mostramos la segunda pagina
        if(index==2){
            rootView = (ViewGroup) inflater.inflate(
                    R.layout.about_page2, container, false);
        }


        return rootView;

    }

}
