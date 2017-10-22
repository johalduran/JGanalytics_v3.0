package com.johnduran.jganalytics;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgenciasFragment extends Fragment implements View.OnClickListener {
    private String NombrePreferencias="Mis_Preferencias";
    private RadioButton rMedellin, rBogota, rMonteria, rCartagena;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private Button bConsultar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_agencias, container, false);
        bConsultar = (Button) v.findViewById(R.id.bConsultar);
        bConsultar.setOnClickListener(this);

        //Declaro los radiobuttons
        rMedellin = (RadioButton) v.findViewById(R.id.rMedellin);
        rBogota = (RadioButton) v.findViewById(R.id.rBogota);
        rMonteria = (RadioButton) v.findViewById(R.id.rMonteria);
        rCartagena = (RadioButton) v.findViewById(R.id.rCartagena);


        prefs=this.getActivity().getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putInt("loggeado",1);
        editor.putInt("cerrarSesion",0);
        editor.commit();

        // Inflate the layout for this fragment

        return v;
    }

    @Override
    public void onClick(View view) {
        if (rMedellin.isChecked() || rBogota.isChecked() || rMonteria.isChecked() || rCartagena.isChecked()){

            Intent intent =new Intent (this.getContext(), NDrawerActivity.class);
            prefs=this.getActivity().getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
            editor = prefs.edit();
            String indicadores="on";
            editor.putString("indicadores", indicadores);
            editor.commit();
            startActivity(intent);
        }
    }
}
