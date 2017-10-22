package com.johnduran.jganalytics;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventariosFragment extends Fragment {

    private String NombrePreferencias="Mis_Preferencias";
    SharedPreferences prefs;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefs=this.getActivity().getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.fragment_inventarios, container, false);
    }

}
