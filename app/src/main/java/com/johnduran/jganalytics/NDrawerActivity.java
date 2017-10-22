package com.johnduran.jganalytics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class NDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String NombrePreferencias="Mis_Preferencias", indicadores="off";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private int optLog=0;
    GoogleApiClient mGoogleApiClient;
    private BottomNavigationView bottom;
    private AppBarLayout tabs;

    private NDrawerActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private LinearLayout Lineartabs, Linearbottom;

    private String RegPhoto, noAvailable = "No Available",nombreGoogle="",correoGoogle="",nombreFacebook="",correoFacebook="",correoL="";
    private ImageView fotoPerfil;
    private TextView tCorreoNaviD, tNombreNaviD;
    private String BotonBack;

    //____________ para el bottom navigation drawer
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm= getSupportFragmentManager();
            FragmentTransaction ft= fm.beginTransaction();
            if(indicadores=="off") {
                Linearbottom.setVisibility(View.VISIBLE);
                Lineartabs.setVisibility(View.GONE);
            }
            switch (item.getItemId()) {
                case R.id.navigation_galeria:
                    GaleriaFragment fragment= new GaleriaFragment();
                    ft.replace(R.id.content_frame, fragment).commit();
                    return true;
                case R.id.navigation_agencias:
                    AgenciasFragment fragment2= new AgenciasFragment();
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
        setContentView(R.layout.activity_ndrawer);

        bottom= (BottomNavigationView) findViewById(R.id.navigation);
        Lineartabs= (LinearLayout) findViewById(R.id.Lineartabs);
        Linearbottom= (LinearLayout) findViewById(R.id.Linearbottom);

        //_____________ Para navigation drawer________________
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true); //selecciona la opcion cero por defecto (Agencias)


        //________________________________________________________

        prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
        optLog= prefs.getInt("optLog",0);
        RegPhoto=prefs.getString("foto",noAvailable);
        nombreGoogle= prefs.getString("nombreCorreo","none");
        correoGoogle= prefs.getString("correoGoogle","none");
        nombreFacebook= prefs.getString("nameFacebook","none");
        correoFacebook= prefs.getString("emailFacebook","none");
        correoL= prefs.getString("correoL","");
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        tNombreNaviD= (TextView)hView.findViewById(R.id.tNombreNaviD);
        tCorreoNaviD= (TextView)hView.findViewById(R.id.tCorreoNaviD);
        fotoPerfil = (ImageView) hView.findViewById(R.id.imageView);

        //Toast.makeText(getApplicationContext(),"optLog: "+optLog, Toast.LENGTH_LONG).show();
        cargarImagendeURL(RegPhoto,fotoPerfil); //Carga la url de la foto segun el tipo de login
        //Configuro el correo y el nombre en el N D
        if(optLog==3){ //  Para login con google
            tNombreNaviD.setText(nombreGoogle);
            tCorreoNaviD.setText(correoGoogle);
        }else if(optLog==2){ // para login con facebook
            tNombreNaviD.setText(nombreFacebook);
            tCorreoNaviD.setText(correoFacebook);
        }else if(optLog==1){
            tCorreoNaviD.setText(correoL);
        }





        prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
        optLog= prefs.getInt("optLog",0);


        /// Para el bottom navigation drawer
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true); //selecciona la opción cero por defecto (Agencias)

        //____________________________________google___________________________
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id)) // Cuando se conecte a un servidor
                .requestProfile()
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"Error en login", Toast.LENGTH_LONG).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //____________________________________________________________

        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        AgenciasFragment fragment= new AgenciasFragment();
        ft.add(R.id.content_frame, fragment).commit();
        indicadores= prefs.getString("indicadores","off");

        if(indicadores=="on"){
            BotonBack="on";
            prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
            editor = prefs.edit();
            editor.putString("indicadores","off");
            editor.commit();

            Linearbottom.setVisibility(View.GONE);
            Lineartabs.setVisibility(View.VISIBLE);

            //______________Para swipe tabs_____________________________
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new NDrawerActivity.SectionsPagerAdapter(getSupportFragmentManager());
            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            //Toolbar toolbar_tabs = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar_tabs);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);


            //__________________________________________________________



        } else{
            Linearbottom.setVisibility(View.VISIBLE);
            Lineartabs.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(getApplicationContext(),"Back", Toast.LENGTH_LONG).show();

        if(BotonBack=="on"){
            BotonBack="off";
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }

        }else {
            prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
            editor = prefs.edit();
            editor.putString("MataApp","si");
            editor.commit();
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent, intent2;
        BotonBack="off";
        prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
        indicadores= prefs.getString("indicadores","off");
        editor = prefs.edit();
        if(indicadores=="off") {
            Linearbottom.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            Lineartabs.setVisibility(View.GONE);
        }
        editor.commit();


        switch(id){
            case R.id.mPerfil:
                bottom.setVisibility(View.GONE);
                FragmentManager fm= getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                PerfilFragment fragment= new PerfilFragment();
                ft.replace(R.id.content_frame, fragment).commit();
                break;
            case R.id.mCerrar:
                prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
                editor = prefs.edit();
                editor.putInt("cerrarSesion",1);
                editor.putInt("loggeado",0);
                editor.putString("correoL","none");
                editor.putString("contrasenaL","none");
                editor.commit();
                if(optLog==1){ //Para facebook
                    LoginManager.getInstance().logOut();
                    intent =  new Intent(NDrawerActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if(optLog==2){
                    LoginManager.getInstance().logOut(); // Logout con facebook
                    intent2 =  new Intent(NDrawerActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                }else if(optLog==3){
                    //__________________logout con google_______________

                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    // ...
                                }
                            });
                    //________________________________________________________
                    intent2 =  new Intent(NDrawerActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        BotonBack="off";
        prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
        indicadores= prefs.getString("indicadores","off");
        editor = prefs.edit();
        if(indicadores=="off") {
            Linearbottom.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            Lineartabs.setVisibility(View.GONE);
        }
        editor.commit();

        //Linearbottom.setVisibility(View.VISIBLE);
        //Lineartabs.setVisibility(View.GONE);
        int id = item.getItemId();

        if (id == R.id.nav_agencias) {
            bottom.setVisibility(View.VISIBLE);
            AgenciasFragment fragment= new AgenciasFragment();
            ft.replace(R.id.content_frame, fragment).commit();
        } else if (id == R.id.nav_perfil) {
            bottom.setVisibility(View.GONE);
            PerfilFragment fragment= new PerfilFragment();
            ft.replace(R.id.content_frame, fragment).commit();
        } else if (id == R.id.nav_reportar_error) {

        } else if (id == R.id.nav_ajustes) {

        } else if (id == R.id.nav_ayuda) {

        } else if (id == R.id.nav_cerrar_sesion) {
            Intent intent,intent2;
            prefs=getSharedPreferences(NombrePreferencias, Context.MODE_PRIVATE);
            editor = prefs.edit();
            editor.putInt("cerrarSesion",1);
            editor.putInt("loggeado",0);
            editor.putString("correoL","none");
            editor.putString("contrasenaL","none");
            editor.commit();
            if(optLog==1){ //Para facebook
                LoginManager.getInstance().logOut();
                intent =  new Intent(NDrawerActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else if(optLog==2){
                LoginManager.getInstance().logOut(); // Logout con facebook
                intent2 =  new Intent(NDrawerActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            }else if(optLog==3){
                //__________________logout con google_______________

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                            }
                        });
                //________________________________________________________
                intent2 =  new Intent(NDrawerActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //_______________________Para los swipe tabs________________________
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    VentasFragment tab0 = new VentasFragment();
                    return tab0;
                case 1:
                    NivelServicioFragment tab1 = new NivelServicioFragment();
                    return tab1;
                case 2:
                    DevolucionesFragment tab2 = new DevolucionesFragment();
                    return tab2;
                case 3:
                    InventariosFragment tab3 = new InventariosFragment();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Ventas";
                case 1:
                    return "Nivel de Servicio";
                case 2:
                    return "Devoluciones";
                case 3:
                    return "Inventarios";
            }
            return null;
        }
    }

    //_______________________________________________________

    private void cargarImagendeURL(String url, ImageView imageView) {
        Picasso.with(NDrawerActivity.this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess(){}
                    @Override
                    public void onError(){}
                });
    }
}
