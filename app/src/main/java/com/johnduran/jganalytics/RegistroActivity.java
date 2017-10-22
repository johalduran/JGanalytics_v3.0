package com.johnduran.jganalytics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    private String correo, contrasena, repcontrasena;
    EditText eCorreo, eContrasena, eRepContrasena;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private String NombrePreferencias="Mis_Preferencias";

    //____________ para el bottom navigation drawer
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm= getSupportFragmentManager();
            FragmentTransaction ft= fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_info:
                    InformacionFragment fragment= new InformacionFragment();
                    ft.replace(R.id.content_frame, fragment).commit();
                    return true;
                case R.id.navigation_registrarse:
                    RegistroFragment fragment2= new RegistroFragment();
                    ft.replace(R.id.content_frame, fragment2).commit();
                    return true;
                case R.id.navigation_contactanos:
                    ContactenosFragment fragment3= new ContactenosFragment();
                    ft.replace(R.id.content_frame, fragment3).commit();
                    return true;
            }
            return false;
        }

    };
    //_______________


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        RegistroFragment fragment= new RegistroFragment();
        ft.replace(R.id.content_frame, fragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_registro);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true); //selecciona la opción cero por defecto (Agencias)



    }
}

