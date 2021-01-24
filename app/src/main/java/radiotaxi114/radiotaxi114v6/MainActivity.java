package radiotaxi114.radiotaxi114v6;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

 /*
    * REGION
     */

    private String RegionId = "";
    private String RegionNombre = "";
    private String RegionURL = "";


    /*
    * PREFERENCIAS
    * */
    SharedPreferences sharedPreferences2;

    private String ConductorId = "";
    private String VehiculoUnidad = "";
    private String VehiculoPlaca = "";
    private String VehiculoModelo = "";
    private String VehiculoColor = "";
    private String VehiculoCarroceria = "";
    private String VehiculoMarca = "";

    private String ConductorNombre = "";
    private String ConductorDireccion = "";
    private String ConductorCelular = "";
    private String ConductorTelefono = "";
    private String ConductorEstado = "";
    private String ConductorEstadoDescripcion = "";
    private String ConductorEstadoMotivo;
    private String ConductorEstadoResponsable;
    private String ConductorNumeroDocumento = "";
    private String ConductorFoto = "";
    private String ConductorCalificacion = "";


    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;

    private String VehiculoCoordenadaX = "0.00";
    private String VehiculoCoordenadaY = "0.00";

    private String Identificador;




    /*
 PERMISOS
  */
    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;



    TextView txtRegionNombre;

    /**
     * VARIABLES GPS
     */
    final String gpsLocationProvider = LocationManager.GPS_PROVIDER;
    final String networkLocationProvider = LocationManager.NETWORK_PROVIDER;


    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Main20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Main20", "Resume");

    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Main20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Main20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Main20", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Main20", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Main20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("Main20", "RestoreInstance");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getString(R.string.app_title));
        actionBar.setSubtitle(getString(R.string.app_subtitle));

        //PERMISOS
        context = getApplicationContext();
        activity = this;

        //FORMULARIOS
        txtRegionNombre = (TextView) findViewById(R.id.CmpMainRegionNombre);

        //RECUPERANDO VARIABLES
        displayUserSettings();

        //IDENTIFICADOR
        if(checkPermission(1)){
            MtdEstablecerIdentificador();
        }


        //OBTENER COORDENADAS
        if(checkPermission(2)){
            MtdObtenerCoordenadas();
        }

        //PREFERENCIAS
        sharedPreferences2 = this.getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        boolean SesionIniciada = sharedPreferences2.getBoolean("SesionIniciada", false);
        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        if (SesionIniciada) {

            Intent intent = new Intent(MainActivity.this, MonitoreoActivity.class);
            startActivity(intent);
            finish();

        }else{

            final ProgressDialog PrgMainSesion = new ProgressDialog(this);
            PrgMainSesion.setIcon(R.mipmap.ic_launcher);
            PrgMainSesion.setMessage("Cargando...");
            PrgMainSesion.setCancelable(true);
            PrgMainSesion.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            PrgMainSesion.show();

            //TAREA ACTUALIZAR COORDENADA - INICIO
            Thread nt = new Thread() {
                @Override
                public void run() {

                    try {

                        final String resMtdObtenerRegionActual;

                        resMtdObtenerRegionActual = MtdObtenerRegionActual(
                                VehiculoCoordenadaX,
                                VehiculoCoordenadaY
                        );

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String JsRespuesta = "";
                                String JsClienteId = "";

                                String JsRegionNombre = "";
                                String JsRegionId = "";
                                String JsRegionURL = "";

                                try {
                                    JSONObject jsonObject = new JSONObject(resMtdObtenerRegionActual);
                                    JsRespuesta = jsonObject.getString("Respuesta");

                                    JsRegionNombre = jsonObject.getString("RegionNombre");
                                    JsRegionId = jsonObject.getString("RegionId");
                                    JsRegionURL = jsonObject.getString("RegionURL");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                PrgMainSesion.cancel();

                                switch(JsRespuesta){

                                    case "R001":

                                        //PrgMainSesion.cancel();
                                        SharedPreferences.Editor editor = sharedPreferences2.edit();
                                        editor.putString("RegionNombre", JsRegionNombre.trim());
                                        editor.putString("RegionId", JsRegionId.trim());
                                        editor.putString("RegionURL", JsRegionURL.trim());
                                        editor.apply();

                                        RegionNombre = JsRegionNombre.trim();
                                        RegionId = JsRegionId.trim();
                                        RegionURL = JsRegionURL.trim();

                                        txtRegionNombre.setText(RegionNombre);

                                        break;

                                    case "R002":

                                        FncMostrarToast("No se ha podido identificar la region en donde se encuentra.");

                                        break;

                                    default:
                                        // FncMostrarMensaje(getString(R.string.message_error_interno),false);
                                        FncMostrarToast(getString(R.string.message_error_interno));
                                        break;
                                }

                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e("PedirTaxiMsg", e.toString());
                    }


                }
            };
            nt.start();
            //TAREA ACTUALIZAR COORDENADA - FIN




        }



    }




    private boolean checkPermission(int permiso) {

        Log.e("Main10","AAA");

        boolean respuesta = false;

        int MyVersion = Build.VERSION.SDK_INT;

        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {


            switch(permiso) {
                case 1:

                    int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);

                    if (result == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Main10","BBB");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, permiso);
                        Log.e("Main10", "DDD");
                    }

                    break;

                case 2:

                    int result2 = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION);

                    if (result2 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","2AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, permiso);
                        Log.e("Navegacion10", "2BBB");
                    }

                    break;


            }


        }else{

            respuesta = true;
        }

        return respuesta;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Log.e("Main10","PERMISO ACEPTADO");

            switch (requestCode) {
                case 1:
                    MtdEstablecerIdentificador();
                    break;

                case 2:
                    MtdObtenerCoordenadas();
                    break;
            }

        } else {

            Log.e("Main10","PERMISO NEGADO");
            //Snackbar.make(MainActivity.this,"Permiso denegado, es posible que la aplicacion no funcione  correctamente.",Snackbar.LENGTH_LONG).show();
            FncMostrarToast("Permiso denegado, es posible que la aplicacion no funcione  correctamente.");

        }

    }



    public void MtdEstablecerIdentificador() {
/*
        //IDENTIFICANDO EQUIPO
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId; tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());

        Identificador = deviceUuid.toString();
*/

        Identificador = "000";
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {

            case R.id.action_compartir:

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_compartir));
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.app_compartir_sub));
                MainActivity.this.startActivity(Intent.createChooser(shareIntent, getString(R.string.alert_title)));

                return true;


            case R.id.action_acerca_de:

                FncMostrarAcercaDe();

                return true;
            case R.id.action_salir:

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);

                // finish();
                //System.exit(0);




                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    /*
    AUXILIARES
     */
    private void FncMostrarAcercaDe() {

        AlertDialog.Builder MsgAcercaDe = new AlertDialog.Builder(this);
        MsgAcercaDe.setTitle("ACERCA DE");
        MsgAcercaDe.setMessage(getString(R.string.app_version));
        MsgAcercaDe.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog msgAcercaDe = MsgAcercaDe.create();
        msgAcercaDe.show();

    }


    private void FncMostrarMensaje(String oMensaje, final Boolean oCerrar) {

        AlertDialog.Builder MsgGeneral = new AlertDialog.Builder(this);
        MsgGeneral.setTitle(getString(R.string.alert_title));
        MsgGeneral.setMessage(oMensaje);
        MsgGeneral.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        if(oCerrar){
                            finish();
                        }
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog msgGeneral = MsgGeneral.create();
        msgGeneral.show();

    }

    private void FncMostrarToast(String oMensaje){

        Toast.makeText(MainActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }

    public void onClick_BtnMainCompartir(View v){

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_compartir));
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.app_compartir_sub));
        MainActivity.this.startActivity(Intent.createChooser(shareIntent, getString(R.string.app_version)));

    }



    public void onClick_BtnMainCerrar(View v) {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);

    }



    public void onClick_BtnMainUbicar(View v){

        //OBTENER COORDENADAS
        if(checkPermission(2)){
            MtdObtenerCoordenadas();
        }

        final ProgressDialog PrgMainSesion = new ProgressDialog(this);
        PrgMainSesion.setIcon(R.mipmap.ic_launcher);
        PrgMainSesion.setMessage("Cargando...");
        PrgMainSesion.setCancelable(true);
        PrgMainSesion.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        PrgMainSesion.show();

        //TAREA ACTUALIZAR COORDENADA - INICIO
        Thread nt = new Thread() {
            @Override
            public void run() {

                try {

                    final String resMtdObtenerRegionActual;

                    resMtdObtenerRegionActual = MtdObtenerRegionActual(
                            VehiculoCoordenadaX,
                            VehiculoCoordenadaY
                    );

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            String JsClienteId = "";

                            String JsRegionNombre = "";
                            String JsRegionId = "";
                            String JsRegionURL = "";

                            try {
                                JSONObject jsonObject = new JSONObject(resMtdObtenerRegionActual);
                                JsRespuesta = jsonObject.getString("Respuesta");

                                JsRegionNombre = jsonObject.getString("RegionNombre");
                                JsRegionId = jsonObject.getString("RegionId");
                                JsRegionURL = jsonObject.getString("RegionURL");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            PrgMainSesion.cancel();

                            switch(JsRespuesta){

                                case "R001":

                                    //PrgMainSesion.cancel();
                                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                                    editor.putString("RegionNombre", JsRegionNombre.trim());
                                    editor.putString("RegionId", JsRegionId.trim());
                                    editor.putString("RegionURL", JsRegionURL.trim());
                                    editor.apply();

                                    RegionNombre = JsRegionNombre.trim();
                                    RegionId = JsRegionId.trim();
                                    RegionURL = JsRegionURL.trim();

                                    txtRegionNombre.setText(RegionNombre);

                                    break;

                                case "R002":

                                    FncMostrarToast("No se ha podido identificar la region en donde se encuentra.");

                                    break;

                                default:
                                    // FncMostrarMensaje(getString(R.string.message_error_interno),false);
                                    FncMostrarToast(getString(R.string.message_error_interno));
                                    break;
                            }

                        }
                    });
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("PedirTaxiMsg", e.toString());
                }


            }
        };
        nt.start();
        //TAREA ACTUALIZAR COORDENADA - FIN





    }

    public void onClick_BtnMainAcceder(View v){

        if(estaConectado(true)){

            // FncMostrarToast("Accediendo a su cuenta de conductor...");
            final EditText Contrasena = (EditText) findViewById(R.id.CmpContrasena);
            final EditText Usuario = (EditText) findViewById(R.id.CmpUsuario);

            if("".equals(Usuario.getText().toString())) {

                FncMostrarMensaje("No ha ingresado su usuario", false);

            }else if("".equals(Contrasena.getText().toString())){

                FncMostrarMensaje("No ha ingresado su contraseña",false);

            }else if(RegionId.equals("")){

                FncMostrarMensaje("No se ha podido identificar la region",false);

            }else{

                final ProgressDialog prgMainAcceder = new ProgressDialog(this);
                prgMainAcceder.setIcon(R.mipmap.ic_launcher);
                prgMainAcceder.setMessage("Cargando...");
                prgMainAcceder.setCancelable(false);
                prgMainAcceder.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                prgMainAcceder.show();

                Thread nt = new Thread() {
                    @Override
                    public void run() {

                        try {

                            final String resMainAcceder;
///aaaaa
                            resMainAcceder = MtdAccederConductor(Usuario.getText().toString(),Contrasena.getText().toString(),VehiculoCoordenadaX,VehiculoCoordenadaY);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JsRespuesta = "";
                                    String JsConductorId = "";
                                    String JsIdentificador = "";

                                    String JsVehiculoUnidad = "";
                                    String JsVehiculoPlaca = "";
                                    String JsVehiculoColor = "";
                                    String JsVehiculoModelo = "";
                                    String JsVehiculoCarroceria = "";
                                    String JsVehiculoMarca = "";

                                    String JsConductorNombre = "";
                                    String JsConductorEstado = "";
                                    String JsConductorEstadoDescripcion = "";
                                    String JsConductorEstadoMotivo = "";
                                    String JsConductorEstadoResponsable = "";

                                    String JsConductorNumeroDocumento = "";
                                    String JsConductorCelular = "";
                                    String JsConductorTelefono = "";
                                    String JsConductorDireccion = "";
                                    String JsConductorFoto = "";
                                    String JsConductorCalificacion = "";

                                    try {

                                        JSONObject jsonObject = new JSONObject(resMainAcceder);
                                        JsRespuesta = jsonObject.getString("Respuesta");

                                        JsConductorId = jsonObject.getString("ConductorId");
                                        JsVehiculoUnidad = jsonObject.getString("VehiculoUnidad");
                                        JsVehiculoPlaca = jsonObject.getString("VehiculoPlaca");
                                        JsVehiculoModelo = jsonObject.getString("VehiculoModelo");
                                        JsVehiculoColor = jsonObject.getString("VehiculoColor");
                                        JsVehiculoCarroceria = jsonObject.getString("VehiculoCarroceria");
                                        JsVehiculoMarca = jsonObject.getString("VehiculoMarca");

                                        JsConductorNombre = jsonObject.getString("ConductorNombre");
                                        JsConductorEstado = jsonObject.getString("ConductorEstado");
                                        JsConductorEstadoDescripcion = jsonObject.getString("ConductorEstadoDescripcion");
                                        JsConductorEstadoMotivo = jsonObject.getString("ConductorEstadoMotivo");
                                        JsConductorEstadoResponsable = jsonObject.getString("ConductorEstadoResponsable");

                                        JsConductorNumeroDocumento = jsonObject.getString("ConductorNumeroDocumento");
                                        JsConductorCelular = jsonObject.getString("ConductorCelular");
                                        JsConductorTelefono = jsonObject.getString("ConductorTelefono");
                                        JsConductorDireccion = jsonObject.getString("ConductorDireccion");
                                        JsConductorFoto = jsonObject.getString("ConductorFoto");
                                        JsConductorCalificacion = jsonObject.getString("ConductorCalificacion");

                                        ConductorId = JsConductorId;
                                        VehiculoUnidad = JsVehiculoUnidad;
                                        VehiculoPlaca = JsVehiculoPlaca;
                                        VehiculoModelo = JsVehiculoModelo;
                                        VehiculoColor = JsVehiculoColor;
                                        VehiculoCarroceria = JsVehiculoCarroceria;
                                        VehiculoMarca = JsVehiculoMarca;

                                        ConductorNombre = JsConductorNombre;
                                        ConductorEstado = JsConductorEstado;
                                        ConductorEstadoDescripcion = JsConductorEstadoDescripcion;
                                        ConductorEstadoMotivo = JsConductorEstadoMotivo;
                                        ConductorEstadoResponsable = JsConductorEstadoResponsable;

                                        ConductorNumeroDocumento = JsConductorNumeroDocumento;
                                        ConductorCelular = JsConductorCelular;
                                        ConductorTelefono = JsConductorTelefono;
                                        ConductorDireccion = JsConductorDireccion;
                                        ConductorFoto = JsConductorFoto;
                                        ConductorCalificacion = JsConductorCalificacion;


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    prgMainAcceder.cancel();

                                    switch(JsRespuesta){
                                        case "C001":

                                            AlertDialog.Builder MsgBienvenido = new AlertDialog.Builder(MainActivity.this);
                                            MsgBienvenido.setTitle(getString(R.string.app_title));
                                            MsgBienvenido.setCancelable(false);
                                            MsgBienvenido.setMessage("Bienvenid@ a " + getString(R.string.app_title) +"  \""+VehiculoUnidad+"\"");

                                            saveUserSettings();

                                            SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean("SesionIniciada", true);
                                            editor.apply();


                                            //final String finalConductorId = JsConductorId;
                                            //final String finalVehiculoUnidad = JsVehiculoUnidad;
                                            //final String finalVehiculoPlaca = JsVehiculoPlaca;
                                            //final String finalConductorNombre = JsConductorNombre;
                                            //final String finalConductorNumeroDocumento = JsConductorNumeroDocumento;

                                       /* MsgBienvenido.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {

                                                    Intent intent = new Intent(MainActivity.this, MonitoreoActivity.class);
                                                    //Bundle bundle = new Bundle();
                                                    //bundle.putString("ConductorId", finalConductorId);
                                                    //bundle.putString("VehiculoUnidad", finalVehiculoUnidad);
                                                   // bundle.putString("VehiculoPlaca", finalVehiculoPlaca);
                                                   // bundle.putString("ConductorNombre", finalConductorNombre);
                                                   // bundle.putString("ConductorNumeroDocumento", finalConductorNumeroDocumento);

                                                  //  intent.putExtras(bundle); //Put your id to your next Intent
                                                    startActivity(intent);
                                                    finish();
                                                    }
                                                });

                                        AlertDialog msgBienvenido = MsgBienvenido.create();
                                        msgBienvenido.show();*/

                                            Thread nt2 = new Thread() {
                                                @Override
                                                public void run() {

                                                    try {

                                                        final String resMain2;

                                                        resMain2 = enviarPostObtenerVersion(getString(R.string.app_version),getString(R.string.app_version_numero));

                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {


                                                                String JsRespuesta = "";
                                                                String JsVersionNumero = "";
                                                                String JsVersionLink = "";
                                                                String JsVersionForzar = "";
                                                                String JsVersionMensaje = "";

                                                                try {

                                                                    JSONObject jsonObject = new JSONObject(resMain2);
                                                                    JsRespuesta = jsonObject.getString("Respuesta");
                                                                    JsVersionNumero = jsonObject.getString("VersionNumero");
                                                                    JsVersionLink = jsonObject.getString("VersionLink");
                                                                    JsVersionForzar = jsonObject.getString("VersionForzar");
                                                                    JsVersionMensaje = jsonObject.getString("VersionMensaje");

                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                                switch(JsRespuesta){
                                                                    case "V001":

                                                                        AlertDialog.Builder MsgGeneral = new AlertDialog.Builder(MainActivity.this);
                                                                        MsgGeneral.setTitle(getString(R.string.alert_title));
                                                                        MsgGeneral.setCancelable(false);
                                                                        MsgGeneral.setMessage(JsVersionMensaje);

                                                                        final String finalJsVersionLink = JsVersionLink;

                                                                        MsgGeneral.setPositiveButton("Actualizar",
                                                                                new DialogInterface.OnClickListener() {

                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                        // Do nothing but close the dialog

                                                                                        Uri uriUrl = Uri.parse(finalJsVersionLink);
                                                                                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                                                                        startActivity(launchBrowser);
                                                                                    }
                                                                                });

                                                                        if(JsVersionForzar.equals("2")) {
                                                                            MsgGeneral.setNegativeButton("Omitir",
                                                                                    new DialogInterface.OnClickListener() {

                                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                                            // Do nothing but close the dialog




                                                                                            AlertDialog.Builder MsgBienvenido = new AlertDialog.Builder(MainActivity.this);
                                                                                            MsgBienvenido.setTitle(getString(R.string.app_title));
                                                                                            MsgBienvenido.setCancelable(false);
                                                                                            MsgBienvenido.setMessage("Bienvenid@ a " + getString(R.string.app_title) +"  \""+VehiculoUnidad+"\"");

                                                                                            MsgBienvenido.setPositiveButton("Aceptar",
                                                                                                    new DialogInterface.OnClickListener() {

                                                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                                                            Intent intent = new Intent(MainActivity.this, MonitoreoActivity.class);
                                                                                                            startActivity(intent);
                                                                                                            finish();
                                                                                                        }
                                                                                                    });

                                                                                            AlertDialog msgBienvenido = MsgBienvenido.create();
                                                                                            msgBienvenido.show();



                                                                                        }
                                                                                    });


                                                                        }

                                                                        // Remember, create doesn't show the dialog
                                                                        AlertDialog msgGeneral = MsgGeneral.create();
                                                                        msgGeneral.show();


                                                                        break;

                                                                    case "V002":

                                                                        Intent intent2 = new Intent(MainActivity.this, MonitoreoActivity.class);
                                                                        startActivity(intent2);
                                                                        finish();

                                                                        break;

                                                                    case "V003":

                                                                        Intent intent3 = new Intent(MainActivity.this, MonitoreoActivity.class);
                                                                        startActivity(intent3);
                                                                        finish();

                                                                        FncMostrarToast("No se ha identificado la version de la aplicacion");

                                                                        break;

                                                                    default:

                                                                        Intent intent4 = new Intent(MainActivity.this, MonitoreoActivity.class);
                                                                        startActivity(intent4);
                                                                        finish();

                                                                        FncMostrarToast(getString(R.string.message_error_interno));

                                                                        break;
                                                                }

                                                            }
                                                        });
                                                    } catch (final Exception e) {
                                                        // TODO: handle exception

                                                        Log.e("MsgMain3", e.toString());

                                                    }

                                                }

                                            };
                                            nt2.start();



                                            break;

                                        case "C002":
                                            FncMostrarMensaje("Su contraseña no ha podido ser identificada, intente nuevamente.", false);
                                            break;

                                        case "C013":

                                            FncMostrarMensaje("Su cuenta ha sido suspendida \""+VehiculoUnidad+"\".",true);

                                            break;

                                        case "C014":

                                            FncMostrarMensaje("Su unidad no esta registrada para este equipo",true);

                                            break;

                                        case "C031":

                                            FncMostrarMensaje("Su cuenta ha si do retirada \""+VehiculoUnidad+"\".",true);

                                            break;


                                        default:
                                            //FncMostrarMensaje(getString(R.string.message_error_interno),true);
                                            FncMostrarToast(getString(R.string.message_error_interno));
                                            break;
                                    }

                                }
                            });
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }
                };



                nt.start();
            }

        }

    }


    public void MtdObtenerCoordenadas() {

        Log.e("Main4", "MtdObtenerCoordenadas");

        //OBTENIENDO UBICACION
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();

            MainListener mlocListener = new MainListener();
            mlocListener.setMainActivity(this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,mlocListener);

            Location lastKnownLocation_byGps =
                    locationManager.getLastKnownLocation(gpsLocationProvider);
            Location lastKnownLocation_byNetwork =
                    locationManager.getLastKnownLocation(networkLocationProvider);

            if(lastKnownLocation_byGps==null){
                if(lastKnownLocation_byNetwork==null){
                    Log.e("Main4", "No hay Ubicacion");
                }else{
                    VehiculoCoordenadaX = Double.toString(lastKnownLocation_byNetwork.getLatitude());
                    VehiculoCoordenadaY = Double.toString(lastKnownLocation_byNetwork.getLongitude());
                    Log.e("Main4", "Ubicacion por RED");
                }

            }else{
                if(lastKnownLocation_byNetwork==null){
                    VehiculoCoordenadaX = Double.toString(lastKnownLocation_byGps.getLatitude());
                    VehiculoCoordenadaY = Double.toString(lastKnownLocation_byGps.getLongitude());
                    Log.e("Main4", "Ubicacion por GPS");
                }else{
                    if(lastKnownLocation_byGps.getAccuracy() <= lastKnownLocation_byNetwork.getAccuracy()){
                        VehiculoCoordenadaX = Double.toString(lastKnownLocation_byGps.getLatitude());
                        VehiculoCoordenadaY = Double.toString(lastKnownLocation_byGps.getLongitude());
                        Log.e("Main4", "Ubicacion por GPS");
                    }else{
                        VehiculoCoordenadaX = Double.toString(lastKnownLocation_byNetwork.getLatitude());
                        VehiculoCoordenadaY = Double.toString(lastKnownLocation_byNetwork.getLongitude());
                        Log.e("Main4", "Ubicacion por RED");
                    }
                }
            }

        } else {
            Toast.makeText(this, "Su GPS no esta activo", Toast.LENGTH_SHORT).show();
        }

    }


/*
ENVIO DE VARIABLES
*/


    public String MtdAccederConductor(String oUsuario,String oContrasena,String oVehiculoCoordenadaX, String oVehiculoCoordenadaY) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductor));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("Usuario", oUsuario);
            postDataParams.put("Contrasena", oContrasena);
            postDataParams.put("VehiculoCoordenadaX", oVehiculoCoordenadaX);
            postDataParams.put("VehiculoCoordenadaY", oVehiculoCoordenadaY);
            postDataParams.put("Identificador", Identificador);

            postDataParams.put("Accion", "AccederConductor");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();


            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response+=line;
            }

            Log.e("Main3", response);

        } catch (Exception e) {

            Log.e("Main4", e.toString());
            e.printStackTrace();
        }

        return response;

    }


    /*

    public String MtdAccederConductor(String oContrasena,String oVehiculoCoordenadaX, String oVehiculoCoordenadaY) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(
                getString(R.string.app_url)+"/webservice/"+getString(R.string.webservice_jnconductor));

        HttpResponse response = null;
        String respuesta = "";

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>(5);
            params.add(new BasicNameValuePair("Contrasena", oContrasena));
            params.add(new BasicNameValuePair("Identificador", Identificador));
            params.add(new BasicNameValuePair("VehiculoCoordenadaX", oVehiculoCoordenadaX));
            params.add(new BasicNameValuePair("VehiculoCoordenadaY", oVehiculoCoordenadaY));

            params.add(new BasicNameValuePair("Accion","AccederConductor"));

            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost, localContext);


            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            respuesta = result.toString();
            Log.e("MainAcceder", respuesta.toString());
        } catch (Exception e) {

            Log.e("MainAcceder2", e.toString());
            // TODO: handle exception
        }
        //return response.toString();
        return respuesta;

    }

*/


    public String enviarPostObtenerVersion(String oAppVersion,String oAppVersionNumero) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnversion));
            //url = new URL(getString(R.string.app_url_ubicacion)+RegionURL+"/webservice/"+getString(R.string.webservice_jnregion));


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("AppVersion", oAppVersion);
            postDataParams.put("AppVersionNumero", oAppVersionNumero);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerVersionConductor");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response+=line;
            }

            Log.e("Main1", response);

        } catch (Exception e) {

            Log.e("Main2", e.toString());
            e.printStackTrace();
        }

        return response;

    }





    public String MtdObtenerRegionActual(String oVehiculoCoordenadaX,String oVehiculoCoordenadaY) {

        URL url;
        String response = "";

        try {

            //url = new URL(getString(R.string.app_url_ubicacion)+RegionURL+"/webservice/"+getString(R.string.webservice_jnregion));
            url = new URL(getString(R.string.app_url_ubicacion)+"/webservice/"+getString(R.string.webservice_jnregion));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("CoordenadaX", oVehiculoCoordenadaX);
            postDataParams.put("CoordenadaY", oVehiculoCoordenadaY);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerRegionActual");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response+=line;
            }

            Log.e("Main5", response);

        } catch (Exception e) {

            Log.e("Main6", e.toString());
            e.printStackTrace();
        }

        return response;

    }


   /* public String enviarPostObtenerVersion(String oAppVersion) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(
                getString(R.string.app_url)+"/webservice/JnVersion.php");

        HttpResponse response = null;
        String respuesta = "";
        // JSONObject respObject = new JSONObject();

        try {

            List<NameValuePair> params = new ArrayList<NameValuePair>(3);

            params.add(new BasicNameValuePair("Identificador", Identificador));
            params.add(new BasicNameValuePair("AppVersion", oAppVersion));
            params.add(new BasicNameValuePair("Accion","ObtenerVersionConductor"));

            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost, localContext);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            respuesta = result.toString();
            Log.e("MsgMain1", respuesta.toString());

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("MsgMain2", e.toString());

        }

        return respuesta;
        // return respObject;

    }*/



    public void setLocation(Location loc) {

        Log.e("MsgMonitoreo65", "setLocation");

        // FncMostrarToast("Cargando coordenadas...");

        //Obtener la direcci—n de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {

            SimpleDateFormat sdfFechaCompletoHoy = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String StrFechaCompletoHoy = sdfFechaCompletoHoy.format(new Date());

            double latitude = loc.getLatitude();
            double longitude = loc.getLongitude();

            Float direction = loc.getBearing();
            Double speedInMilesPerHour = loc.getSpeed() * 2.2369;
            Double accuracyInFeet = loc.getAccuracy() * 3.28;

            VehiculoCoordenadaX = String.valueOf(latitude);
            VehiculoCoordenadaY = String.valueOf(longitude);
        }
    }

    /*
    * PREFERENCIAS
    */

    private boolean saveUserSettings() {

        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Identificador", Identificador.trim());
        editor.putString("ConductorId", ConductorId.trim());
        editor.putString("VehiculoUnidad", VehiculoUnidad.trim());
        editor.putString("VehiculoPlaca", VehiculoPlaca.trim());
        editor.putString("VehiculoModelo", VehiculoModelo.trim());
        editor.putString("VehiculoColor", VehiculoColor.trim());
        editor.putString("VehiculoCarroceria", VehiculoCarroceria.trim());
        editor.putString("VehiculoMarca", VehiculoMarca.trim());

        editor.putString("ConductorNumeroDocumento", ConductorNumeroDocumento.trim());
        editor.putString("ConductorNombre", ConductorNombre.trim());
        editor.putString("ConductorDireccion", ConductorDireccion.trim());
        editor.putString("ConductorCelular", ConductorCelular);
        editor.putString("ConductorTelefono", ConductorTelefono);
        editor.putString("ConductorFoto", ConductorFoto);
        editor.putString("ConductorCalificacion", ConductorCalificacion);

        editor.putString("ConductorEstado", ConductorEstado.trim());
        editor.putString("ConductorEstadoDescripcion", ConductorEstadoDescripcion.trim());
        editor.putString("ConductorEstadoMotivo", ConductorEstadoMotivo.trim());

        editor.putInt("ConductorOcupado", ConductorOcupado);
        editor.putInt("ConductorCobertura", ConductorCobertura);


        editor.apply();

        return true;
    }

    private void displayUserSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        Identificador = sharedPreferences.getString("Identificador","");
        ConductorId = sharedPreferences.getString("ConductorId","");
        VehiculoUnidad = sharedPreferences.getString("VehiculoUnidad","");
        VehiculoPlaca = sharedPreferences.getString("VehiculoPlaca","");
        ConductorNombre = sharedPreferences.getString("ConductorNombre","");
        ConductorEstado = sharedPreferences.getString("ConductorEstado","");
        ConductorNumeroDocumento = sharedPreferences.getString("ConductorNumeroDocumento","");
        ConductorCelular = sharedPreferences.getString("ConductorCelular","");

        ConductorOcupado = sharedPreferences.getInt("ConductorOcupado",2);
        ConductorCobertura = sharedPreferences.getInt("ConductorCobertura", 0);

    }


    protected Boolean estaConectado(Boolean oMostrarMensaje){
        if(conectadoWifi()){

            Log.e("Navegacion60", "Tiene wifi");
            //showAlertDialog(Main.this, "Conexion a Internet",
            //         "Tu Dispositivo tiene Conexion a Wifi.", true);
            return true;
        }else{

            Log.e("Navegacion60", "No tiene wifi");

            if(conectadoRedMovil()){
                Log.e("Navegacion60", "Tiene datos");
                //   showAlertDialog(Main.this, "Conexion a Internet",
                //         "Tu Dispositivo tiene Conexion Movil.", true);
                return true;
            }else{

                Log.e("Navegacion60", "No tiene datos");
                ///showAlertDialog(Main.this, "Conexion a Internet",
                //         "Tu Dispositivo no tiene Conexion a Internet.", false);
                if(oMostrarMensaje){
                    FncMostrarToast("Verifica tu conexion a internet");
                }

                return false;
            }
        }
    }



    protected Boolean conectadoWifi(){

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;

    }

    protected Boolean conectadoRedMovil(){

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }


    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }



}
