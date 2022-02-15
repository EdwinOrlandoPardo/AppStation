package com.apps.project.appstation.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.apps.project.appstation.fragments.Home;
import com.apps.project.appstation.fragments.Antenas;
import com.apps.project.appstation.fragments.Alarmas;
import com.apps.project.appstation.fragments.Historial;

/**
 * Created by Edwin on 14/08/2015.
 */
public class AdapterViewPager extends FragmentPagerAdapter {
    public static int posicion;
    private String tabTitles[] ;
    int numTabs;

    public AdapterViewPager(FragmentManager fm,String title[],int numTabs) {
        super(fm);
        this.tabTitles=title;
        this.numTabs=numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        this.posicion=position;
        switch (position) {
            case 0:
                Home home = new Home();
                return home;
            case 1:
                Antenas antenas = new Antenas();
                return antenas;
            case 2:
                Alarmas alarmas = new Alarmas();
                return alarmas;
            case 3:
                Historial historial = new Historial();
                return historial;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //Retorna el titulo basado en la posicion que se encuentre
        return tabTitles[position];
    }
}
