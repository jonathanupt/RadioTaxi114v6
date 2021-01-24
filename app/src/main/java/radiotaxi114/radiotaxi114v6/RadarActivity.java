package radiotaxi114.radiotaxi114v6;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/*
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
*/
public class RadarActivity extends AppCompatActivity {

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

    /*
    * Variables Principales
    */
    private String ConductorId = "";

    private String VehiculoUnidad = "";
    private String VehiculoPlaca = "";
    private String ConductorNombre = "";
    private String ConductorEstado = "";
    private String ConductorNumeroDocumento = "";
    private String ConductorCelular = "";

    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;
    /*
    * Variables Secundarias
    */
    private String VehiculoCoordenadaX = "0.00";
    private String VehiculoCoordenadaY = "0.00";
    private int VehiculoOrientacion = 0;
    /*
    * Otras Variables
    */
    public String Identificador;


    public String SectorNombre = "";


    /*
    * ALERTA
     */

    public String ConductorAlerta = "2";
    public String ConductorAlertaCoordenadaX = "0.00";
    public String ConductorAlertaCoordenadaY = "0.00";


    /*
    * MAPA
     */
    private GoogleMap googleMap = null;
    private Integer ActualizarCamara = 2;
    private Marker vehiculoMarker;
    private float currentZoom;

    private int TipoCamara = 1;

    /*
    * TIMERS
     */
    Timer timeRadar1;
    Timer timeRadar2;
    Timer timeRadar3;




    /**
     * PARAMETRO APLICACION
     */

    private int AplicacionRadarPedidoContador;
    private int AplicacionRadarAlertaContador;

    private int AplicacionRadarConductorContador;
    private int AplicacionRadarCoordenadaContador;

    private int AplicacionPedidoActualCoordenadaContador;
    private int AplicacionPedidoActualPedidoContador;


//    final String gpsLocationProvider = LocationManager.GPS_PROVIDER;
//    final String networkLocationProvider = LocationManager.NETWORK_PROVIDER;

    /**
     * VARIABLES GPS
     */
    final String gpsLocationProvider = LocationManager.GPS_PROVIDER;
    final String networkLocationProvider = LocationManager.NETWORK_PROVIDER;

    /*
 PERMISOS
  */
    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;


    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Radar20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // timeRadar1.
        Log.e("Radar20", "Resume");

        //FncTimerObtenerConductores();
        // FncTimerEnviarCoordenadas();

        FncCargarAplicacionParametros();
    }


    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Radar20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Radar20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Radar20", "Restart");

        ActualizarCamara = 2;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Radar20", "Destroy");


        if(timeRadar1!=null){
            timeRadar1.cancel();
        }

        /*if(timeRadar2!=null){
            timeRadar2.cancel();
        }*/

        if(timeRadar3!=null){
            timeRadar3.cancel();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Radar20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("Radar20", "RestoreInstance");
    }

    /* protected void onStart() {
         super.onPause();
 
         ActualizarCamara = 2;
 
     }*/
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_activity);
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


        Log.e("Radar20", "Create");
        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_radar));

        // actionBar.setDisplayShowHomeEnabled(true);
        // actionBar.setIcon(R.mipmap.ic_launcher);
        // actionBar.setTitle(getString(R.string.app_title));
        // actionBar.setSubtitle("Mapa");

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //VehiculoCoordenadaX = sharedPreferences2.getString("VehiculoCoordenadaX", "0.00");
       // VehiculoCoordenadaY = sharedPreferences2.getString("VehiculoCoordenadaY", "0.00");

        //RECUPERANDO VARIABLES
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        ConductorId = intentExtras.getStringExtra("ConductorId");
        ConductorAlertaCoordenadaX = intentExtras.getStringExtra("ConductorAlertaCoordenadaX");
        ConductorAlertaCoordenadaY = intentExtras.getStringExtra("ConductorAlertaCoordenadaY");
        ConductorAlerta = intentExtras.getStringExtra("ConductorAlerta");

        FncCargarAplicacionParametros();

        /*
        PERMISOS
        */
        context = getApplicationContext();
        activity = this;

        //RECUPERANDO VARIABLES v2
        displayUserSettings();


        //VARIABLES INICIALES
        currentZoom = 19;

        TipoCamara = 1;

        //OBTENER COORDENADAS
        if(checkPermission(2)){
            MtdObtenerCoordenadas();
        }

        //CREANDO MAPA
        if(checkPermission(1)){
            createMapView();
        }


        if(ConductorAlerta.equals("1")){
            TipoCamara = 2;

            if(null != googleMap){

                FncMostrarToast("Ubicando alerta...");

                if(!ConductorAlertaCoordenadaX.equals("") && !ConductorAlertaCoordenadaY.equals("") && !ConductorAlertaCoordenadaX.equals("0.00") && !ConductorAlertaCoordenadaY.equals("0.00") && ConductorAlertaCoordenadaX != null  && ConductorAlertaCoordenadaY != null){

                    TipoCamara = 2;

                    LatLng latLng = new LatLng(Double.parseDouble(ConductorAlertaCoordenadaX),Double.parseDouble(ConductorAlertaCoordenadaY));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng)      // Sets the center of the map to Mountain View
                            .zoom(currentZoom)                   // Sets the zoom
                            //.bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                }

            }

        }else{
            TipoCamara = 1;

        }


        if(timeRadar1!=null){
            timeRadar1.cancel();
        }

        //TAREA OBTENER  VEHICULOS

        //final Timer timer = new Timer();
        timeRadar1 = new Timer();
        timeRadar1.schedule(new TimerTask() {
            @Override
            public void run() {

                try {

                    final String resRadar;
                    resRadar = MtdObtenerRadar();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            JSONArray JsDatos;

                            if (googleMap != null) {
                                googleMap.clear();
                            }

                            /*
                            OBTENER ALERTAS - FIN
                             */
                            try {

                                JSONObject jsonObject = new JSONObject(resRadar);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsDatos = jsonObject.getJSONArray("Datos");

                                for (int i = 0; i < JsDatos.length(); i++) {

                                    JSONObject jsonObj = JsDatos.getJSONObject(i);

                                    String JsNombre = jsonObj.getString("Nombre");
                                    String JsEtiqueta = jsonObj.getString("Etiqueta");
                                    String JsCoordenadaX = jsonObj.getString("CoordenadaX");
                                    String JsCoordenadaY = jsonObj.getString("CoordenadaY");
                                    String JsRadarTipo = jsonObj.getString("RadarTipo");
                                    String JsOrientacion = jsonObj.getString("Orientacion");
                                    String JsTiempoTranscurrido = jsonObj.getString("TiempoTranscurrido");
                                    String JsTiempoTranscurridoTexto = jsonObj.getString("TiempoTranscurridoTexto");

                                    String JsTiempo = jsonObj.getString("Tiempo");



                                    //  if (!JsConductorCoordenadaX.equals("") & !JsConductorCoordenadaY.equals("")) {
                                    if(!JsCoordenadaX.equals("") && !JsCoordenadaY.equals("") && !JsCoordenadaX.equals("0.00") && !JsCoordenadaY.equals("0.00") && JsCoordenadaX != null  && JsCoordenadaY != null) {

                                        switch(JsRadarTipo){
                                            case "1":

                                               // Log.e("JsRadarTipo",JsRadarTipo);
                                                if (JsTiempoTranscurrido.equals("0")) {
                                                    MediaPlayer player = MediaPlayer.create(RadarActivity.this, R.raw.choque);
                                                    player = MediaPlayer.create(RadarActivity.this, R.raw.choque);
                                                    player.start();
                                                }

                                                ConductorAlertaCoordenadaX = JsCoordenadaX;
                                                ConductorAlertaCoordenadaY = JsCoordenadaY;

                                                googleMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(Double.parseDouble(JsCoordenadaX), Double.parseDouble(JsCoordenadaY)))
                                                       // .title("Choque - " + JsEtiqueta+" - "+JsTiempoTranscurridoTexto)
                                                        .title(JsEtiqueta)
                                                        .draggable(false)
                                                        .rotation(0)
                                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_choque35)));

                                                break;

                                            case "2":

                                                if (JsTiempoTranscurrido.equals("0")) {
                                                    MediaPlayer player2 = MediaPlayer.create(RadarActivity.this, R.raw.robo);
                                                    player2 = MediaPlayer.create(RadarActivity.this, R.raw.robo);
                                                    player2.start();
                                                }

                                                ConductorAlertaCoordenadaX = JsCoordenadaX;
                                                ConductorAlertaCoordenadaY = JsCoordenadaY;


                                                googleMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(Double.parseDouble(JsCoordenadaX), Double.parseDouble(JsCoordenadaY)))
                                                        //.title("Robo - " + JsEtiqueta+" - "+JsTiempoTranscurridoTexto)
                                                        .title(JsEtiqueta)
                                                        .draggable(false)
                                                        .rotation(0)
                                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_robo35)));


                                                break;


                                            case "3":

                                                //if(JsConductorAlertaTiempoTranscurrido == "0" || JsConductorAlertaTiempoTranscurrido == "1" ) {
                                                if (JsTiempoTranscurrido.equals("0")) {
                                                    MediaPlayer player3 = MediaPlayer.create(RadarActivity.this, R.raw.operativo);
                                                    player3 = MediaPlayer.create(RadarActivity.this, R.raw.operativo);
                                                    player3.start();
                                                }

                                                ConductorAlertaCoordenadaX = JsCoordenadaX;
                                                ConductorAlertaCoordenadaY = JsCoordenadaY;


                                                googleMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(Double.parseDouble(JsCoordenadaX), Double.parseDouble(JsCoordenadaY)))
                                                        //.title("Operativo Policial - " + JsEtiqueta+" - "+JsTiempoTranscurridoTexto)
                                                        .title(JsEtiqueta)
                                                        .draggable(false)
                                                        .rotation(0)
                                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_operativo35)));

                                                break;


                                            case "4":

                                                //if(JsConductorAlertaTiempoTranscurrido == "0" || JsConductorAlertaTiempoTranscurrido == "1" ) {
                                                if (JsTiempoTranscurrido.equals("0")) {
                                                    MediaPlayer player3 = MediaPlayer.create(RadarActivity.this, R.raw.sou_cliente_libre);
                                                    player3 = MediaPlayer.create(RadarActivity.this, R.raw.sou_cliente_libre);
                                                    player3.start();
                                                }

                                                ConductorAlertaCoordenadaX = JsCoordenadaX;
                                                ConductorAlertaCoordenadaY = JsCoordenadaY;


                                                googleMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(Double.parseDouble(JsCoordenadaX), Double.parseDouble(JsCoordenadaY)))
                                                        //.title("Operativo Policial - " + JsEtiqueta+" - "+JsTiempoTranscurridoTexto)
                                                        .title(JsEtiqueta)
                                                        .draggable(false)
                                                        .rotation(0)
                                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_servicio_libre35)));

                                                break;

                                            case "97":

                                                if (!JsEtiqueta.equals(VehiculoUnidad)) {

                                                    int orientacion = 0;

                                                    if(!JsOrientacion.equals("")) {
                                                        orientacion = Integer.valueOf(JsOrientacion);
                                                    }

                                                    googleMap.addMarker(new MarkerOptions()
                                                            .position(new LatLng(Double.parseDouble(JsCoordenadaX), Double.parseDouble(JsCoordenadaY)))
                                                            .title(JsEtiqueta)
                                                            .draggable(false)
                                                            .rotation(orientacion)
                                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_ocupado128)));

                                                }

                                                break;

                                            case "98":

                                                if (!JsEtiqueta.equals(VehiculoUnidad)) {

                                                    int orientacion = 0;

                                                    if(!JsOrientacion.equals("")) {
                                                        orientacion = Integer.valueOf(JsOrientacion);
                                                    }

                                                    googleMap.addMarker(new MarkerOptions()
                                                            .position(new LatLng(Double.parseDouble(JsCoordenadaX), Double.parseDouble(JsCoordenadaY)))
                                                            .title(JsEtiqueta)
                                                            .draggable(false)
                                                            .rotation(orientacion)
                                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_ocupado_app128)));

                                                }

                                                break;


                                            case "99":

                                                if (!JsEtiqueta.equals(VehiculoUnidad)) {

                                                    int orientacion = 0;

                                                    if(!JsOrientacion.equals("")) {
                                                        orientacion = Integer.valueOf(JsOrientacion);
                                                    }

                                                    googleMap.addMarker(new MarkerOptions()
                                                            .position(new LatLng(Double.parseDouble(JsCoordenadaX), Double.parseDouble(JsCoordenadaY)))
                                                            .title(JsEtiqueta)
                                                            .draggable(false)
                                                            .rotation(orientacion)
                                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro128)));

                                                }

                                                break;
                                        }





                                      /*  }*/


                                    }


                                }

                            } catch (JSONException e) {
                                Log.e("MsgRadar4", e.toString());
                                e.printStackTrace();
                            }

                            switch (JsRespuesta) {

                                case "C040":
                                    break;

                                case "C041":
                                    break;

                                default:
                                    break;

                            }


                        }
                    });


                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("MsgRadar3", e.toString());
                }

            }
        }, 1000, AplicacionRadarConductorContador);
        //TAREA OBTENER  VEHICULOS








        if(timeRadar3!=null){
            timeRadar3.cancel();
        }

        //TAREA ENVIAR COORDENADAS - INICIO
        //final Timer timer3 = new Timer();
        timeRadar3 = new Timer();
        timeRadar3.schedule(new TimerTask(){
            @Override
            public void run() {

                Log.e("MsgRadar2","Transfiriendo coordenadas..."+VehiculoUnidad);

                    if(estaConectado(false)){

                        try {

                            final String resConductorCoordenada;
                            resConductorCoordenada = MtdActualizarConductorVehiculoCoordenada(ConductorId, VehiculoCoordenadaX, VehiculoCoordenadaY,VehiculoUnidad,VehiculoPlaca,ConductorNombre,SectorNombre,ConductorNumeroDocumento,VehiculoOrientacion);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JsRespuesta = "";
                                    String JsSectorNumero = "";
                                    String JsSectorNombre = "";
                                    String JsConductorResetearSesion = "";

                                    try {
                                        JSONObject jsonObject = new JSONObject(resConductorCoordenada);
                                        JsRespuesta = jsonObject.getString("Respuesta");
                                        JsSectorNumero = jsonObject.getString("SectorNumero");
                                        JsSectorNombre = jsonObject.getString("SectorNombre");
                                        ///JsConductorResetearSesion = jsonObject.getString("ConductorResetearSesion");

                                    } catch (JSONException e) {
                                        Log.e("MsgMonitoreo7", e.toString());
                                        e.printStackTrace();
                                    }

                                    switch(JsRespuesta){

                                        case "U001":
                                           // TextView TxtSector = (TextView) findViewById(R.id.CmpSector);
                                            //TxtSector.setText((JsSectorNumero));

                                            if(!SectorNombre.equals(JsSectorNombre)) {

                                                FncMostrarToast("Cambiando a "+JsSectorNombre);

                                                SectorNombre = JsSectorNombre;
                                            }

                                            break;

                                        case "U002":
                                            break;

                                        case "U003":
                                            break;

                                        case "U004":
                                            break;

                                        default:
                                            break;

                                    }

                                }
                            });



                        } catch (Exception e) {
                            // TODO: handle exception
                            Log.e("MsgRadar3", e.toString());
                        }

                    }



            }
        }, 2000, AplicacionRadarCoordenadaContador);
        //}, 3000, 30000);






        //OBTENIENDO HISTORIAL
        Thread nt = new Thread() {
            @Override
            public void run() {

                try {

                    final String resMtdObtenerSectorCoordenada;
                    resMtdObtenerSectorCoordenada = MtdObtenerSectorCoordenada();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            JSONArray JsDatos;

                            try {
                                JSONObject jsonObject = new JSONObject(resMtdObtenerSectorCoordenada);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsDatos = jsonObject.getJSONArray("Datos");

                                // TextView TxtHistorialTotal = (TextView) findViewById(R.id.CmpHistorialTotal);
                                // TxtHistorialTotal.setText("Tienes ("+JsDatos.length()+") pedidos en tu historial ");

                                for (int i = 0; i < JsDatos.length(); i++) {

                                    JSONObject jsonObj = JsDatos.getJSONObject(i);

                                    String JsSectorId = jsonObj.getString("SectorId");
                                    String JsSectorNombre = jsonObj.getString("SectorNombre");
                                    String JsSectorCoordenadaCoordenadaX = jsonObj.getString("SectorCoordenadaCoordenadaX");
                                    String JsSectorCoordenadaCoordenadaY = jsonObj.getString("SectorCoordenadaCoordenadaY");



                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
//                              FncMostrarToast(e.toString());
                            }

                            switch(JsRespuesta){
                                case "S001":
                                    break;

                                case "S002":
                                    break;

                                case "S003":
                                    break;
                            }

                        }
                    });
                } catch (final Exception e) {
                    // TODO: handle exception
                    Log.e("Ranking6", e.toString());
                }

            }

        };
        nt.start();

    }



    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(RadarActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        RadarActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }

    private boolean checkPermission(int permiso) {
        Log.e("Navegacion10","VERIFICAR PERMISO");
        boolean respuesta = false;

        int MyVersion = Build.VERSION.SDK_INT;

        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {

            switch(permiso){
                case 1:

                    int result1 = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION);

                    if (result1 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","1AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, permiso);
                        Log.e("Navegacion10", "1BBB");
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

            Log.e("Navegacion10","PERMISO ACEPTADO");

            switch (requestCode) {
                case 1:
                    createMapView();
                    break;

                case 2:
                    MtdObtenerCoordenadas();
                    break;

            }

        } else {
            Log.e("Navegacion10","PERMISO NEGADO");
            FncMostrarToast("Permiso denegado, es posible que la aplicacion no funcione  correctamente.");
        }

    }



    public void MtdObtenerCoordenadas() {

        //OBTENIENDO UBICACION
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();

            RadarListener mlocListener = new RadarListener();
            mlocListener.setRadarActivity(this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,mlocListener);

            Location lastKnownLocation_byGps =
                    locationManager.getLastKnownLocation(gpsLocationProvider);
            Location lastKnownLocation_byNetwork =
                    locationManager.getLastKnownLocation(networkLocationProvider);

            if(lastKnownLocation_byGps==null){
                if(lastKnownLocation_byNetwork==null){

                }else{
                    VehiculoCoordenadaX = Double.toString(lastKnownLocation_byNetwork.getLatitude());
                    VehiculoCoordenadaY = Double.toString(lastKnownLocation_byNetwork.getLongitude());
                }

            }else{
                if(lastKnownLocation_byNetwork==null){
                    VehiculoCoordenadaX = Double.toString(lastKnownLocation_byGps.getLatitude());
                    VehiculoCoordenadaY = Double.toString(lastKnownLocation_byGps.getLongitude());

                }else{
                    if(lastKnownLocation_byGps.getAccuracy() <= lastKnownLocation_byNetwork.getAccuracy()){
                        VehiculoCoordenadaX = Double.toString(lastKnownLocation_byGps.getLatitude());
                        VehiculoCoordenadaY = Double.toString(lastKnownLocation_byGps.getLongitude());
                    }else{
                        VehiculoCoordenadaX = Double.toString(lastKnownLocation_byNetwork.getLatitude());
                        VehiculoCoordenadaY = Double.toString(lastKnownLocation_byNetwork.getLongitude());
                    }
                }
            }

        } else {
            Toast.makeText(this, "Su GPS no esta activo", Toast.LENGTH_SHORT).show();
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_radar, menu);
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

            case android.R.id.home:

                Intent intentMonitoreo = new Intent(RadarActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                RadarActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();


                return true;
            /*

            case R.id.action_compartir:

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_compartir));
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.app_compartir_sub));
                RadarActivity.this.startActivity(Intent.createChooser(shareIntent, getString(R.string.alert_title)));

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

                //finish();
                //System.exit(0);

                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void createMapView(){

        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {

            if(null == googleMap){

                googleMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setPadding(0, 0, 0, 0);

                googleMap.setOnMyLocationButtonClickListener(
                        new GoogleMap.OnMyLocationButtonClickListener() {


                            @Override
                            public boolean onMyLocationButtonClick() {

                                Location location = googleMap.getMyLocation();

                                if(location!=null){

                                    VehiculoCoordenadaX = Double.toString(location.getLatitude());
                                    VehiculoCoordenadaY = Double.toString(location.getLongitude());
                                    Float direction = location.getBearing();

                                    if(direction.intValue()>0){
                                        VehiculoOrientacion =  (direction.intValue());
                                    }


                                    // if(!VehiculoCoordenadaX.equals("") & !VehiculoCoordenadaY.equals("")){
                                    if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                                        if(vehiculoMarker!=null){
                                            vehiculoMarker.remove();
                                        }

                                        vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                                .title("¡Aquì estoy!")
                                                .draggable(false)
                                                .rotation(VehiculoOrientacion)
                                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo_yo100)));


                                        LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                                .target(latLng)      // Sets the center of the map to Mountain View
                                                .zoom(currentZoom)                   // Sets the zoom
                                                //.bearing(90)                // Sets the orientation of the camera to east
                                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                .build();                   // Creates a CameraPosition from the builder
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    }

                                    if(estaConectado(true)){

                                        Thread nt = new Thread() {
                                            @Override
                                            public void run() {

                                                try {

                                                    final String resConductorCoordenada;
                                                    resConductorCoordenada = MtdActualizarConductorVehiculoCoordenada(ConductorId,VehiculoCoordenadaX,VehiculoCoordenadaY,VehiculoUnidad,VehiculoPlaca,ConductorNombre,SectorNombre,ConductorNumeroDocumento,VehiculoOrientacion);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            String JsRespuesta = "";

                                                            try {

                                                                JSONObject jsonObject = new JSONObject(resConductorCoordenada);
                                                                JsRespuesta = jsonObject.getString("Respuesta");

                                                            } catch (JSONException e) {
                                                                Log.e("MsgRadar7", e.toString());
                                                                e.printStackTrace();
                                                            }

                                                            switch(JsRespuesta){

                                                                case "C007":
                                                                    break;

                                                                case "C008":
                                                                    break;

                                                                case "C009":
                                                                    break;

                                                                default:
                                                                    break;

                                                            }

                                                        }
                                                    });
                                                } catch (final Exception e) {
                                                    // TODO: handle exception
                                                    Log.e("MsgRadar11", e.toString());
                                                }

                                            }

                                        };
                                        nt.start();

                                    }

                                }else{
                                    FncMostrarToast("No se pudo obtener su ubicación");
                                }

                                return false;
                            }
                        }


                );

                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {


                        if(location!=null){


                            VehiculoCoordenadaX = Double.toString(location.getLatitude());
                            VehiculoCoordenadaY = Double.toString(location.getLongitude());
                            Float direction = location.getBearing();

                            if(direction.intValue()>0){
                                VehiculoOrientacion =  (direction.intValue());
                            }

                            //if(!VehiculoCoordenadaX.equals("") & !VehiculoCoordenadaY.equals("")){
                            if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                                if(vehiculoMarker!=null){
                                    vehiculoMarker.remove();
                                }

                                vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                        .title("¡Aquì estoy!")
                                        .draggable(false)
                                        .rotation(VehiculoOrientacion)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo_yo100)));

                                if(TipoCamara==1){

                               /* LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng)      // Sets the center of the map to Mountain View
                                        .zoom(currentZoom)                   // Sets the zoom
                                        //.bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

                                }

                            }

                        }

                    }
                });

                googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                    // private float currentZoom = -1;
                    @Override
                    public void onCameraChange(CameraPosition pos) {
                        if (pos.zoom != currentZoom){
                            currentZoom = pos.zoom;
                            // do you action here
                            Log.e("ZOOM", String.valueOf(currentZoom));

                        }
                    }

                });

                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                    if(vehiculoMarker!=null){
                        vehiculoMarker.remove();
                    }

                    vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                            .title("¡Aquì estoy!")
                            .draggable(false)
                            .rotation(VehiculoOrientacion)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo_yo100)));

                    LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng)      // Sets the center of the map to Mountain View
                            .zoom(currentZoom)                   // Sets the zoom
                            //.bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                }


              /*  int fillColor = 0x44ffffff;
                int strokeColor = 0x88ffffff;
                //float strokeWidth = getResources().getDimension(R.dimen.polygon_stroke_width);
                PolygonOptions options = new PolygonOptions().fillColor(fillColor)
                        .strokeColor(strokeColor)
                        .strokeWidth(5)
                        .zIndex(0.0f);

                options.add(new LatLng(33.8070, -10.7504));
                options.add(new LatLng(58.7526, -13.0387));
                options.add(new LatLng(65.6205, 8.7599));
                options.add(new LatLng(71.6195, 19.9833));
                options.add(new LatLng(72.0245, 34.4512));
                options.add(new LatLng(68.3408, 44.1491));
                options.add(new LatLng(48.6029, 45.2666));
                options.add(new LatLng(43.4643, 34.3700));
                options.add(new LatLng(39.4653, 25.6528));
                options.add(new LatLng(34.0572, 27.6142));
                options.add(new LatLng(35.2226, 13.8846));
                options.add(new LatLng(38.7890, 11.9232));
                options.add(new LatLng(36.1078, -5.7291));

                List<LatLng> hole = new ArrayList<LatLng>();
                hole.add(new LatLng(54.3062, 13.1940));
                hole.add(new LatLng(55.5966, 19.3098));
                hole.add(new LatLng(54.8401, 24.0624));
                hole.add(new LatLng(52.3033, 24.9097));
                hole.add(new LatLng(50.2762, 25.2413));
                hole.add(new LatLng(48.5262, 23.2150));
                hole.add(new LatLng(48.5750, 18.5729));
                hole.add(new LatLng(50.7447, 13.5256));
                options.addHole(hole);

                googleMap.addPolygon(options);*/
/*
                Polygon polygon;

                PolygonOptions rectOptions = new PolygonOptions()
                        .add(new LatLng(-18.016831, -70.223796),
                                new LatLng(-18.012963,-70.228512),
                                new LatLng(-18.009106, -70.231291),
                                new LatLng(-18.011575, -70.234917),
                                new LatLng(-18.006930, -70.238031),
                                new LatLng(-18.007440, -70.239233)
                        );


//Set the rectangle's stroke color to red
                rectOptions.strokeColor(Color.BLUE);
//Set the rectangle's fill to blue
                rectOptions.fillColor(Color.CYAN);
                rectOptions.strokeWidth(2);
//Get back the mutable Polygon
                polygon = googleMap.addPolygon(rectOptions);
                */

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    FncMostrarToast("Ha ocurrido un error cargando el mapa.");
                }

            }
        } catch (Exception exception){
            // Toast.makeText(getApplicationContext(),
            //   exception.toString(),Toast.LENGTH_SHORT).show();
            FncMostrarToast("Ha ocurrido un error cargando el mapa: "+exception.toString());
        }
    }


    public void onClick_BtnRadarBuscar(View v){


        final String preFiltro = ((EditText)findViewById(R.id.CmpRadarFiltro)).getText().toString();
        CharSequence chaFiltro = Html.fromHtml(preFiltro);
        final String valFiltro = chaFiltro.toString();


        if(null != googleMap){

            FncMostrarToast("Buscando...");


            if(estaConectado(true)){

                Thread nt = new Thread() {
                    @Override
                    public void run() {

                        try {

                            final String resMtdBuscarConductor;
                            resMtdBuscarConductor = MtdBuscarConductor(valFiltro,VehiculoCoordenadaX,VehiculoCoordenadaY);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JsRespuesta = "";
                                    String JsCoordenadaX = "";
                                    String JsCoordenadaY = "";
                                    String JsEtiqueta = "";
                                    String JsOrientacion = "";

                                    try {

                                        JSONObject jsonObject = new JSONObject(resMtdBuscarConductor);
                                        JsRespuesta = jsonObject.getString("Respuesta");
                                        JsCoordenadaX = jsonObject.getString("CoordenadaX");
                                        JsCoordenadaY = jsonObject.getString("CoordenadaY");
                                        JsEtiqueta = jsonObject.getString("Etiqueta");
                                        JsOrientacion = jsonObject.getString("Orientacion");

                                    } catch (JSONException e) {
                                        Log.e("MsgRadar7", e.toString());
                                        e.printStackTrace();
                                    }

                                    switch(JsRespuesta){

                                        case "R001":

                                           if(!JsCoordenadaX.equals("") && !JsCoordenadaY.equals("") && !JsCoordenadaX.equals("0.00") && !JsCoordenadaY.equals("0.00") && JsCoordenadaX != null  && JsCoordenadaY != null){

                                               int orientacion = 0;

                                               if(!JsOrientacion.equals("")) {
                                                   orientacion = Integer.valueOf(JsOrientacion);
                                               }

                                               googleMap.addMarker(new MarkerOptions()
                                                       .position(new LatLng(Double.parseDouble(JsCoordenadaX), Double.parseDouble(JsCoordenadaY)))
                                                       .title(JsEtiqueta)
                                                       .draggable(false)
                                                       .rotation(orientacion)
                                                       .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo80)));

                                               TipoCamara = 1;

                                               LatLng latLng = new LatLng(Double.parseDouble(JsCoordenadaX),Double.parseDouble(JsCoordenadaY));
                                               CameraPosition cameraPosition = new CameraPosition.Builder()
                                                       .target(latLng)      // Sets the center of the map to Mountain View
                                                       .zoom(18)                   // Sets the zoom
                                                       //.bearing(90)                // Sets the orientation of the camera to east
                                                       .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                       .build();                   // Creates a CameraPosition from the builder
                                               googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                                            }


                                            /*if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                TipoCamara = 1;

                LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)      // Sets the center of the map to Mountain View
                        .zoom(currentZoom)                   // Sets the zoom
                        //.bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }*/

                                            break;

                                        case "R002":
                                            FncMostrarToast("No se encontraron resultados");
                                            break;

                                        default:
                                            FncMostrarToast(getString(R.string.message_error_interno));


                                            break;

                                    }

                                }
                            });
                        } catch (final Exception e) {
                            // TODO: handle exception
                            Log.e("MsgRadar11", e.toString());
                        }

                    }

                };
                nt.start();

            }


        }
    }

    public void onClick_BtnRadarUbicar(View v){

        if(null != googleMap){

            FncMostrarToast("Ubicando unidad...");

            if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                TipoCamara = 1;

                LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)      // Sets the center of the map to Mountain View
                        .zoom(currentZoom)                   // Sets the zoom
                        //.bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }

        }
    }


    public void onClick_BtnRadarUbicarAlerta(View v){

        if(null != googleMap){

            FncMostrarToast("Ubicando alerta...");

            if(!ConductorAlertaCoordenadaX.equals("") && !ConductorAlertaCoordenadaY.equals("") && !ConductorAlertaCoordenadaX.equals("0.00") && !ConductorAlertaCoordenadaY.equals("0.00") && ConductorAlertaCoordenadaX != null  && ConductorAlertaCoordenadaY != null){

                TipoCamara = 2;

                LatLng latLng = new LatLng(Double.parseDouble(ConductorAlertaCoordenadaX),Double.parseDouble(ConductorAlertaCoordenadaY));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)      // Sets the center of the map to Mountain View
                        .zoom(currentZoom)                   // Sets the zoom
                        //.bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }

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


    private void FncMostrarMensaje(String oMensaje) {

        AlertDialog.Builder MsgGeneral = new AlertDialog.Builder(this);
        MsgGeneral.setTitle(getString(R.string.alert_title));
        MsgGeneral.setMessage(oMensaje);
        MsgGeneral.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog msgGeneral = MsgGeneral.create();
        msgGeneral.show();

    }
    private void FncMostrarToast(String oMensaje){

        Toast.makeText(RadarActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();
    }



    public String MtdObtenerRadar() {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnradar));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerRadar");

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

            Log.e("MsgRadar7", response);

        } catch (Exception e) {

            Log.e("MsgRadar8", e.toString());
            e.printStackTrace();
        }

        return response;

    }

    public String MtdBuscarConductor(String oFiltro,String oVehiculoCoordenadaX, String oVehiculoCoordenadaY) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnradar));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Filtro", oFiltro);
            postDataParams.put("VehiculoCoordenadaX", oVehiculoCoordenadaX);
            postDataParams.put("VehiculoCoordenadaY", oVehiculoCoordenadaY);

            postDataParams.put("Accion", "BuscarConductor");

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

            Log.e("MsgRadar9", response);

        } catch (Exception e) {

            Log.e("MsgRadar10", e.toString());
            e.printStackTrace();
        }

        return response;

    }

    /*public String enviarPostObtenerConductorAlertas() {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductoralerta));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String>postDataParams=new HashMap<>();

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerConductorAlertas");

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

            Log.e("MsgRadar1", response);

        } catch (Exception e) {

            Log.e("MsgRadar2", e.toString());
            e.printStackTrace();
        }

        return response;


    }*/




    public String MtdActualizarConductorVehiculoCoordenada(String oConductorId,String oVehiculoCoordenadaX,String oVehiculoCoordenadaY,String oVehiculoUnidad, String oVehiculoPlaca,String oConductorNombre,String oSectorNombre,String oConductorNumeroDocumento,Integer oVehiculoGPSOrientacion) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductorubicacion));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

//,String oVehiculoUnidad, String oVehiculoPlaca
            HashMap<String, String>postDataParams=new HashMap<>();
            postDataParams.put("ConductorId", oConductorId);

            postDataParams.put("ConductorNombre", oConductorNombre);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);

            postDataParams.put("VehiculoUnidad", oVehiculoUnidad);
            postDataParams.put("VehiculoPlaca", oVehiculoPlaca);

            postDataParams.put("VehiculoCoordenadaX", oVehiculoCoordenadaX);
            postDataParams.put("VehiculoCoordenadaY", oVehiculoCoordenadaY);
            postDataParams.put("VehiculoGPSOrientacion", String.valueOf(oVehiculoGPSOrientacion));

            postDataParams.put("Sector", oSectorNombre);
            postDataParams.put("Formulario", "MonitoreoActivity");

            postDataParams.put("AppVersion", getString(R.string.app_version));
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ActualizarConductorVehiculoCoordenada");

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

            Log.e("MsgRadar4", response);

        } catch (Exception e) {

            Log.e("MsgRadar5", e.toString());
            e.printStackTrace( );
        }

        return response;

    }



    public String MtdObtenerSectorCoordenada() {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnsector));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("Accion", "ObtenerSectorCoordenadas");

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

            /*
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }*/

            Log.e("Ranking11", response);

        } catch (Exception e) {

            Log.e("Ranking12", e.toString());
            e.printStackTrace();
        }

        return response;

    }

    public void setLocation(Location loc) {


    }

    private float calculateDistance(double fromLong, double fromLat,double toLong, double toLat) {
///private String getDistance(LatLng my_latlong,LatLng frnd_latlong){

        LatLng my_latlong = new LatLng((fromLong),(fromLat));
        LatLng frnd_latlong = new LatLng((toLong),(toLat));

       /* double d2r = Math.PI / 180;
        double dLong = (toLong - fromLong) * d2r;
        double dLat = (toLat - fromLat) * d2r;
        double a = Math.pow(Math.sin(dLat / 2.0), 2) + Math.cos(fromLat * d2r)
                * Math.cos(toLat * d2r) * Math.pow(Math.sin(dLong / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6367000 * c;
        //d = d/1000;
        return Math.round(d);*/
        Location l1=new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2=new Location("Two");
        l2.setLatitude(frnd_latlong.latitude);
        l2.setLongitude(frnd_latlong.longitude);

        float distance=l1.distanceTo(l2);
        //String dist=distance+" M";

        //if(distance>1000.0f)
        // {
        distance = distance/1000.0f;
        // dist=distance;
        // }
        return distance;

    }

    public static double redondear(double d, int decimalPlace) {

        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();

    }




        /*
    * PREFERENCIAS
    */

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
        ConductorCobertura = sharedPreferences.getInt("ConductorCobertura",0);

    }


    private void FncCargarAplicacionParametros() {

        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        AplicacionRadarPedidoContador = sharedPreferences.getInt("AplicacionRadarPedidoContador", 9000);
        AplicacionRadarCoordenadaContador = sharedPreferences.getInt("AplicacionRadarCoordenadaContador", 30000);
        AplicacionRadarAlertaContador = sharedPreferences.getInt("AplicacionRadarAlertaContador", 30000);

        AplicacionRadarConductorContador = sharedPreferences.getInt("AplicacionRadarConductorContador", 50000);
        AplicacionRadarCoordenadaContador = sharedPreferences.getInt("AplicacionRadarCoordenadaContador", 10000);

        AplicacionPedidoActualCoordenadaContador = sharedPreferences.getInt("AplicacionPedidoActualCoordenadaContador", 10000);
        AplicacionPedidoActualPedidoContador = sharedPreferences.getInt("AplicacionPedidoActualPedidoContador", 30000);


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



  /*  private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
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
*/
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
