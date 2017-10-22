package com.johnduran.jganalytics;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment implements View.OnClickListener {
    private String correo, contrasena, repcontrasena;
    EditText eCorreo, eContrasena, eRepContrasena;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private String NombrePreferencias="Mis_Preferencias";
    private Button bRegistrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registro, container, false);

        eCorreo = (EditText) v.findViewById(R.id.eCorreo);
        eContrasena = (EditText) v.findViewById(R.id.eContrasena);
        eRepContrasena = (EditText) v.findViewById(R.id.eRepContrasena);
        bRegistrar = (Button) v.findViewById(R.id.bRegistrar);
        bRegistrar.setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View view) {

        correo = eCorreo.getText().toString();
        contrasena = eContrasena.getText().toString();
        repcontrasena = eRepContrasena.getText().toString();

        if(correo.isEmpty() || contrasena.isEmpty() || repcontrasena.isEmpty()){
            Toast.makeText(this.getActivity(), "Faltan campos por llenar", Toast.LENGTH_SHORT).show();


        }else {
            if (!(correo.contains("@") && correo.contains("."))){
                Toast.makeText(this.getActivity(), "Debe introducir un correo válido", Toast.LENGTH_SHORT).show();

            }else {
                if(contrasena.equals(repcontrasena)){
                    prefs=this.getActivity().getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
                    editor = prefs.edit();
                    editor.putString("correoR", correo);
                    editor.putString("contrasenaR", contrasena);
                    editor.commit(); //Si no se hace commit, los cambios no son salvados
                    Toast.makeText(this.getActivity(), correo+" ha sido registrado", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent (this.getContext(), LoginActivity.class);
                    startActivity(intent);
                    this.getActivity().finish();
                }else{
                    Toast.makeText(this.getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

                }
            }
        }


    }
}
