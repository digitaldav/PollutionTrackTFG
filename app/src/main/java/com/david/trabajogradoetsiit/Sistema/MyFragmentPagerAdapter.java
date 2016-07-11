package com.david.trabajogradoetsiit.Sistema;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**

 * FragmentPageAdapter personalizado

 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    //lista de fragments
    List<Fragment> fragments;

    /**

     * Constructor de MyFragmentPageAdapter

     */
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
    }

    /**
     * Agregar nuevo fragment
     *
     * @param fragment Nuevo fragment a agregar
     */
    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    /**

     * Obtener fragment

     * @param arg0 numero de fragment

     * @return Fragment elegido

     */
    @Override
    public Fragment getItem(int arg0) {
        return this.fragments.get(arg0);
    }

    /**

     * Obtener numero de fragment

     * @return numero de fragments

     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }

}
