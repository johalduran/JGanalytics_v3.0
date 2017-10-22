package com.johnduran.jganalytics;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    private TextView tUsuario;
    private String NombrePreferencias="Mis_Preferencias";
    SharedPreferences prefs;
    private int optLog=0;
    private String RegPhoto, noAvailable = "No Available",nombreGoogle,correoGoogle,nombreFacebook,correoFacebook;
    private ImageView fotoPerfil;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        tUsuario= (TextView) v.findViewById(R.id.tUsuario);
        prefs=this.getActivity().getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
        optLog= prefs.getInt("optLog",0);
        RegPhoto=prefs.getString("foto",noAvailable);
        nombreGoogle= prefs.getString("nombreCorreo","");
        correoGoogle= prefs.getString("correoGoogle","");
        nombreFacebook= prefs.getString("nameFacebook","");
        correoFacebook= prefs.getString("emailFacebook","");
        fotoPerfil = (ImageView) v.findViewById(R.id.fotoPerfil);

        cargarImagendeURL(RegPhoto,fotoPerfil); //Carga la url de la foto segun el tipo de login
        if(optLog==3){ //  Para login con google
            tUsuario.setText("\n"+nombreGoogle+"\n"+correoGoogle+"\n");
        }else if(optLog==2){ // para login con facebook
            tUsuario.setText("\n"+nombreFacebook+"\n"+correoFacebook);
        }
        return v;
    }

    private void cargarImagendeURL(String url, ImageView imageView) {
        Picasso.with(this.getActivity()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess(){}
                    @Override
                    public void onError(){}
                });
    }

}
