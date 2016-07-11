package com.david.trabajogradoetsiit.Sistema;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.david.trabajogradoetsiit.R;


/**

 * Activity para gestionar la vista deslizable de Sobre la aplicacion

 */
public class about extends FragmentActivity {


    private ViewPager pager = null;

    static MyFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.setContentView(R.layout.activity_about);

        this.pager = (ViewPager) this.findViewById(R.id.pager);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SlideAbout.newInstance(1));
        adapter.addFragment(SlideAbout.newInstance(2));
        this.pager.setAdapter(adapter);

    }

    /**

     * Gestiona pulsar el boton atras para moverse por las vistas

     */
    @Override
    public void onBackPressed() {

        if (this.pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1); //volver a pagina anterior

    }
}
