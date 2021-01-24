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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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


import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MonitoreoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    private String ConductorNombre = "";
    private String ConductorEstado = "";
    private String ConductorNumeroDocumento = "";
    private String ConductorCelular = "";

    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;

    private String VehiculoCoordenadaX = "0.00";
    private String VehiculoCoordenadaY = "0.00";
    private int VehiculoOrientacion = 0;

    private String PedidoDistancia = "";
    private String PedidoTiempo = "";

    private String Identificador;

    private String SectorNombre = "";

    /**
     * DATOS GOOGLE MAP
     */
    private GoogleMap googleMap = null;
    private String MostrarMapa = "1";


    //TextView TxtMensaje = (TextView) findViewById(R.id.CmpMensaje);

    /**
    *2D PLANO
     */

    //private boolean currentlyTracking;
    //private RadioGroup intervalRadioGroup;
    private int intervalInMinutes = 1;
    private AlarmManager alarmManager;
    private Intent gpsTrackerIntent;
    private PendingIntent pendingIntent;

    /**
     * TIMERS
     */
    Timer timeMonitoreo1;
    Timer timeMonitoreo2;
    Timer timeMonitoreo3;
    Timer timeMonitoreo4;
    Timer timeMonitoreo5;
    Timer timeMonitoreo6;

    Timer timeMonitoreo7;

    //private boolean MonitoreoEnviarCoordenadaContinuar = true;
    //private boolean MonitoreoObtenerAlertaContinuar = true;
    //private boolean MonitoreoCargarContinuar = true;


    /**
     * PARAMETRO APLICACION
     */

    private int AplicacionMonitoreoPedidoContador;
    private int AplicacionMonitoreoCoordenadaContador;
    private int AplicacionMonitoreoCoordenadaPermanenteContador;

    private int AplicacionMonitoreoAlertaContador;
    private int AplicacionMonitoreoMensajeContador;

    private int AplicacionRadarConductorContador;
    private int AplicacionRadarCoordenadaContador;

    private int AplicacionPedidoActualCoordenadaContador;
    private int AplicacionPedidoActualPedidoContador;
    private int AplicacionMonitoreoPedidoAsignadoContador;


    private int AplicacionMonitoreoSincronizarConductorContador;

    private int AplicacionMonitoreoSincronizarConductor;
    private int AplicacionActualizarCoordenadasPermanente;
    private int AplicacionRestringirIdentificador;
    private int AplicacionObtenerAsignado;


    /**
     * VARIABLES FORMULARIO
     */
    private Boolean MonitoreoSonido;
    private Boolean ConductorTienePedido;
    private String MonitoreoFondo;
    private String MonitoreoTono;
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

    private Boolean currentlyTracking;

/**
 *
 */
    LinearLayout capConductorEstadoColor;

    Button btnMonitoreoOcupado;

    /**
     * MENSAJES
     */

    ImageView imgCentralMensajeImagen;


    private MediaPlayer mediaPlayer = null;

/*
*SOCKETS
 */
    private WebSocketClient mWebSocketClient;
    private boolean mWebSocketClientConnected = false;

    private String ConductorUbicacionVehiculoGPSOrientacion = "";
    private String ConductorUbicacionVehiculoVelocidad = "";
    private String ConductorUbicacionVehiculoGPSProveedor = "";
    private String ConductorUbicacionVehiculoGPSExactitud = "";

    /*
    * CONDUCTOR ALERTA
     */
    private String ConductorAlertaCoordenadaX = "";
    private String ConductorAlertaCoordenadaY = "";



/*
* SONIDOS
 */
   /* MediaPlayer mediaPlayerPedido1;
    MediaPlayer mediaPlayerPedido2;

    MediaPlayer mediaPlayerPedidoOperativo;
    MediaPlayer mediaPlayerPedidoRobo;
    MediaPlayer mediaPlayerPedidoAccidente ;
    MediaPlayer mediaPlayerPedidoClienteLibre ;


    MediaPlayer mediaPlayerMensajeCentral;

    MediaPlayer mediaPlayerAlerta;
*/

    private String EmpresaRankingMostrar = "";



    /**
     * NUEVO AUDIO
     */

    private SoundPool soundPool;
    private AudioManager audioManager;

    // Maximumn sound stream.
    private static final int MAX_STREAMS = 5;

    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;

    private boolean loaded;

    private int soundIdDestroy;
    private int soundIdGun;




    private int soundIdPedido1;
    private int soundIdPedido2;

    private int soundIdPedidoOperativo;
    private int soundIdPedidoRobo;
    private int soundIdPPedidoAccidente;
    private int soundIdPedidoClienteLibre;

    private int soundIdError;
    private int soundIdMensaje;
    private int soundIdMensajeCentral;
    private int soundIdBocina;
    private int soundIdAlerta;


    private float volume;





    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Monitoreo20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Monitoreo20", "Resume");

        SharedPreferences sharedPreferences = this.getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        MonitoreoSonido = sharedPreferences.getBoolean("MonitoreoSonido", true);
        ConductorTienePedido = sharedPreferences.getBoolean("ConductorTienePedido", false);
        MonitoreoFondo = sharedPreferences2.getString("MonitoreoFondo", "2");
        MonitoreoTono = sharedPreferences2.getString("MonitoreoTono", "1");

        FncCargarAplicacionParametros();

       /* String Celularlocal = "";

        try{

            TelephonyManager mTelephonyManager;
            mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            mTelephonyManager.getLine1Number();

            Celularlocal = mTelephonyManager.getLine1Number();
            Celularlocal = Celularlocal.substring(Celularlocal.length(),-9);


        }catch (Exception ex){
            FncMostrarMensaje("No se ha podido identificar su celular",false);
            FncMostrarToast(ex.toString());
        }
*/



        //  FncMostrarToast(ConductorCelular);

       // if (!ConductorCelular.equals("")) {

            //FncMostrarToast("a");

            //if (!Celularlocal.equals("")) {

                /*if (!ConductorCelular.equals(Celularlocal)) {

                    AlertDialog.Builder MsgMonitoreo = new AlertDialog.Builder(MonitoreoActivity.this);
                    MsgMonitoreo.setTitle(getString(R.string.alert_title));
                    MsgMonitoreo.setMessage("Su cuenta no esta asociada a este número de celular: " + Celularlocal);
                    MsgMonitoreo.setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog

                                    ConductorId = "";
                    VehiculoUnidad = "";
                    VehiculoPlaca = "";
                    ConductorNombre = "";
                    ConductorEstado = "";
                    ConductorNumeroDocumento = "";

                    saveUserSettings();

                    SharedPreferences sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    editor.putBoolean("SesionIniciada", false);
                    editor.putBoolean("ConductorTienePedidoRecepcionar", false);
                    editor.putBoolean("ConductorTienePedido", false);
                    editor.apply();

                    Intent intentMain1 = new Intent(MonitoreoActivity.this, MainActivity.class);
                    startActivity(intentMain1);
                    finish();

                                }
                            });


                    // Remember, create doesn't show the dialog
                    AlertDialog msgMonitoreo = MsgMonitoreo.create();
                    msgMonitoreo.show();


                } else {*/

            if (ConductorTienePedido) {

                Intent intent = new Intent(MonitoreoActivity.this, PedidoActualActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("PedidoId", sharedPreferences.getString("PedidoId", ""));

                bundle.putString("ClienteId", sharedPreferences.getString("ClienteId", ""));
                bundle.putString("ClienteNombre", sharedPreferences.getString("ClienteNombre", ""));

                bundle.putString("PedidoDireccion", sharedPreferences.getString("PedidoDireccion", ""));
                bundle.putString("PedidoNumero", sharedPreferences.getString("PedidoNumero", ""));
                bundle.putString("PedidoReferencia", sharedPreferences.getString("PedidoReferencia", ""));
                bundle.putString("PedidoDetalle", sharedPreferences.getString("PedidoDetalle", ""));
                bundle.putString("PedidoCambioDe", sharedPreferences.getString("PedidoCambioDe", ""));

                bundle.putString("PedidoOperadora", sharedPreferences.getString("PedidoOperadora", ""));
                bundle.putString("PedidoAnexo", sharedPreferences.getString("PedidoAnexo", ""));
                bundle.putString("PedidoInfo", sharedPreferences.getString("PedidoInfo", ""));


                bundle.putString("PedidoFoto", sharedPreferences.getString("PedidoFoto", ""));
                bundle.putString("PedidoTipoUnidad", sharedPreferences.getString("PedidoTipoUnidad", ""));

                bundle.putString("PedidoCoordenadaX", sharedPreferences.getString("PedidoCoordenadaX", "0.00"));
                bundle.putString("PedidoCoordenadaY", sharedPreferences.getString("PedidoCoordenadaY", "0.00"));
                bundle.putString("PedidoTipo", sharedPreferences.getString("PedidoTipo", ""));

                bundle.putString("VehiculoCoordenadaX", sharedPreferences.getString("VehiculoCoordenadaX", "0.00"));
                bundle.putString("VehiculoCoordenadaY", sharedPreferences.getString("VehiculoCoordenadaX", "0.00"));

                bundle.putString("ClienteCelular", sharedPreferences.getString("ClienteCelular", ""));
                bundle.putString("ClienteTelefono", sharedPreferences.getString("ClienteTelefono", ""));

                bundle.putString("PedidoTiempo", sharedPreferences.getString("PedidoTiempo", ""));
                bundle.putString("PedidoDistancia", sharedPreferences.getString("PedidoDistancia", ""));

                intent.putExtras(bundle);//Put your id to your next Intent
                startActivity(intent);
                finish();
                // }

                // }

            } /*else{

               if (ConductorTienePedido) {

                    Intent intent = new Intent(MonitoreoActivity.this, PedidoActualActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("PedidoId", sharedPreferences.getString("PedidoId", ""));

                    bundle.putString("ClienteNombre", sharedPreferences.getString("ClienteNombre", ""));

                    bundle.putString("PedidoDireccion", sharedPreferences.getString("PedidoDireccion", ""));
                    bundle.putString("PedidoNumero", sharedPreferences.getString("PedidoNumero", ""));
                    bundle.putString("PedidoReferencia", sharedPreferences.getString("PedidoReferencia", ""));
                    bundle.putString("PedidoDetalle", sharedPreferences.getString("PedidoDetalle", ""));
                    bundle.putString("PedidoFoto", sharedPreferences.getString("PedidoFoto", ""));
                    bundle.putString("PedidoTipoUnidad", sharedPreferences.getString("PedidoTipoUnidad", ""));

                    bundle.putString("PedidoCoordenadaX", sharedPreferences.getString("PedidoCoordenadaX", "0.00"));
                    bundle.putString("PedidoCoordenadaY", sharedPreferences.getString("PedidoCoordenadaY", "0.00"));

                    bundle.putString("VehiculoCoordenadaX", sharedPreferences.getString("VehiculoCoordenadaX", "0.00"));
                    bundle.putString("VehiculoCoordenadaY", sharedPreferences.getString("VehiculoCoordenadaX", "0.00"));

                    bundle.putString("ClienteCelular", sharedPreferences.getString("ClienteCelular", ""));
                    bundle.putString("ClienteTelefono", sharedPreferences.getString("ClienteTelefono", ""));

                    intent.putExtras(bundle);//Put your id to your next Intent
                    startActivity(intent);
                    finish();

                }

            }*/


            // FncMostrarToast(mTelephonyManager.getLine1Number());



      /*  } else {

            //  FncMostrarToast("c");

            if (ConductorTienePedido) {

                Intent intent = new Intent(MonitoreoActivity.this, PedidoActualActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("PedidoId", sharedPreferences.getString("PedidoId", ""));

                bundle.putString("ClienteNombre", sharedPreferences.getString("ClienteNombre", ""));

                bundle.putString("PedidoDireccion", sharedPreferences.getString("PedidoDireccion", ""));
                bundle.putString("PedidoNumero", sharedPreferences.getString("PedidoNumero", ""));
                bundle.putString("PedidoReferencia", sharedPreferences.getString("PedidoReferencia", ""));
                bundle.putString("PedidoDetalle", sharedPreferences.getString("PedidoDetalle", ""));
                bundle.putString("PedidoFoto", sharedPreferences.getString("PedidoFoto", ""));

                bundle.putString("PedidoCoordenadaX", sharedPreferences.getString("PedidoCoordenadaX", "0.00"));
                bundle.putString("PedidoCoordenadaY", sharedPreferences.getString("PedidoCoordenadaY", "0.00"));

                bundle.putString("VehiculoCoordenadaX", sharedPreferences.getString("VehiculoCoordenadaX", "0.00"));
                bundle.putString("VehiculoCoordenadaY", sharedPreferences.getString("VehiculoCoordenadaX", "0.00"));

                bundle.putString("ClienteCelular", sharedPreferences.getString("ClienteCelular", ""));
                bundle.putString("ClienteTelefono", sharedPreferences.getString("ClienteTelefono", ""));

                intent.putExtras(bundle);//Put your id to your next Intent
                startActivity(intent);
                finish();
            }

        }*/

    }

    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Monitoreo20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Monitoreo20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Monitoreo20", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Monitoreo20", "Destroy");

        if(timeMonitoreo1!=null){
            timeMonitoreo1.cancel();
        }

        if(timeMonitoreo2!=null){
            timeMonitoreo2.cancel();
        }

        if(timeMonitoreo3!=null){
            timeMonitoreo3.cancel();
        }

        if(timeMonitoreo4!=null){
            timeMonitoreo4.cancel();
        }

        if(timeMonitoreo5!=null){
            timeMonitoreo5.cancel();
        }

        if(timeMonitoreo6!=null){
            timeMonitoreo6.cancel();
        }

        if(timeMonitoreo7!=null){
            timeMonitoreo7.cancel();
        }

        /**
         *
         */
/*
        if(mediaPlayerPedido1!=null) {
            mediaPlayerPedido1.stop();
        }
        if(mediaPlayerPedido2!=null) {
            mediaPlayerPedido2.stop();
        }

        if(mediaPlayerPedidoOperativo!=null) {
            mediaPlayerPedidoOperativo.stop();
        }
        if(mediaPlayerPedidoRobo!=null) {
            mediaPlayerPedidoRobo.stop();
        }
        if(mediaPlayerPedidoAccidente!=null) {
            mediaPlayerPedidoAccidente.stop();
        }
        if(mediaPlayerMensajeCentral!=null) {
            mediaPlayerMensajeCentral.stop();
        }
        if(mediaPlayerAlerta!=null) {
            mediaPlayerAlerta.stop();
        }
*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Monitoreo20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("Monitoreo20", "RestoreInstance");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

       // actionBar.setDisplayShowHomeEnabled(true);
       // actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(getString(R.string.app_title));
        actionBar.setSubtitle(getString(R.string.title_activity_monitoreo));

        FncCargarAplicacionParametros();





        /*connectWebSocket();


        try {
            mWebSocketClient.send("larva");
        }catch(Exception e){
            Log.e("mWebSocketClient",e.toString());
        }
*/

        /*
        PERMISOS
        */
        context = getApplicationContext();
        activity = this;


        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        MonitoreoSonido = sharedPreferences2.getBoolean("MonitoreoSonido", true);
        MonitoreoFondo = sharedPreferences2.getString("MonitoreoFondo", "2");
        MonitoreoTono = sharedPreferences2.getString("MonitoreoTono", "1");
        EmpresaRankingMostrar = sharedPreferences2.getString("EmpresaRankingMostrar", "NO");




        ConductorTienePedido = sharedPreferences2.getBoolean("ConductorTienePedido", false);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


       // currentlyTracking = sharedPreferences2.getBoolean("currentlyTracking", false);

        ListView lstPedidoPendientes = (ListView)findViewById(R.id.LstPedidoPendientes);

        Log.e("Res:MonitoreoFondo",MonitoreoFondo);

        if(MonitoreoFondo.equals("1")){
            lstPedidoPendientes.setBackgroundResource(R.drawable.fondo_monitoreo_noche);
        }else{
            lstPedidoPendientes.setBackgroundResource(R.drawable.fondo_monitoreov2);
        }




        /*
        if(!ConductorCelular.equals(mTelephonyManager.getLine1Number())){
            FncMostrarToast("No se pudo verificar su numero de celular");
        }else{
            FncMostrarToast(mTelephonyManager.getLine1Number());
        }*/

        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        //OBTENER COORDENADAS
        if(checkPermission(2)){
            MtdObtenerCoordenadas();
        }

        //CREANDO MAPA
       if(checkPermission(1)){
           createMapView();
       }


        //CARGANDO DATOS
        TextView TxtVehiculoUnidad = (TextView) findViewById(R.id.CmpSoporteContactoNombre);
        TxtVehiculoUnidad.setText(VehiculoUnidad);

        imgCentralMensajeImagen = (ImageView)  findViewById(R.id.ImgCentralMensajeImagen);
        //TextView TxtCobertura = (TextView) findViewById(R.id.CmpCobertura);
        // TxtCobertura.setText(FncConvertirCobertura(ConductorCobertura));


        btnMonitoreoOcupado = (Button) findViewById(R.id.BtnMonitoreoOcupado);


       /* //LinearLayout capConductorEstadoColor = (LinearLayout) findViewById(R.id.CapConductorEstadoColor);
        capConductorEstadoColor = (LinearLayout) findViewById(R.id.CapEstadoDescripcion);

        if(ConductorEstado.equals("1")) {
            capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_habilitado);

        }else if(ConductorEstado.equals("2")){
            capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_deshabilitado);

        }else if(ConductorEstado.equals("3")){
            capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_retirado);
        }*/


        if(ConductorEstado.equals("2")){

            btnMonitoreoOcupado.setBackgroundResource(R.drawable.dra_boton_fondo3);
            btnMonitoreoOcupado.setText("Deshabilitado");

        }else{

            if(ConductorOcupado == 1){

                btnMonitoreoOcupado.setBackgroundResource(R.drawable.dra_boton_fondo2);
                btnMonitoreoOcupado.setText("Ocupado");

            }else if(ConductorOcupado == 2){

                btnMonitoreoOcupado.setBackgroundResource(R.drawable.dra_boton_fondo);
                btnMonitoreoOcupado.setText("Desocupado");

            }

        }





      /*  if(ConductorOcupado == 1){

            Button tiny = (Button)findViewById(R.id.BtnMonitoreoOcupado);
            tiny.setBackgroundResource(R.drawable.dra_boton_fondo2);
            tiny.setText("Ocupado");

        }else if(ConductorOcupado == 2){

            Button tiny2 = (Button)findViewById(R.id.BtnMonitoreoOcupado);
            tiny2.setBackgroundResource(R.drawable.dra_boton_fondo);
            tiny2.setText("Desocupado");

        }*/

        /*if (ConductorTienePedido) {

            if(timeMonitoreo1!=null){
                timeMonitoreo1.cancel();
            }

            if(timeMonitoreo2!=null){
                timeMonitoreo2.cancel();
            }

            if(timeMonitoreo3!=null){
                timeMonitoreo3.cancel();
            }

            if(timeMonitoreo4!=null){
                timeMonitoreo4.cancel();
            }

            if(timeMonitoreo5!=null){
                timeMonitoreo5.cancel();
            }

            if(timeMonitoreo6!=null){
                timeMonitoreo6.cancel();
            }

            Intent intent = new Intent(MonitoreoActivity.this, PedidoActualActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("PedidoId", sharedPreferences2.getString("PedidoId",""));

            bundle.putString("ClienteNombre", sharedPreferences2.getString("ClienteNombre",""));

            bundle.putString("PedidoDireccion", sharedPreferences2.getString("PedidoDireccion",""));
            bundle.putString("PedidoNumero", sharedPreferences2.getString("PedidoNumero",""));
            bundle.putString("PedidoReferencia", sharedPreferences2.getString("PedidoReferencia",""));
            bundle.putString("PedidoDetalle", sharedPreferences2.getString("PedidoDetalle",""));
            bundle.putString("PedidoFoto", sharedPreferences2.getString("PedidoFoto",""));

            bundle.putString("PedidoCoordenadaX", sharedPreferences2.getString("PedidoCoordenadaX","0.00"));
            bundle.putString("PedidoCoordenadaY", sharedPreferences2.getString("PedidoCoordenadaY","0.00"));

            bundle.putString("VehiculoCoordenadaX", VehiculoCoordenadaX);
            bundle.putString("VehiculoCoordenadaY", VehiculoCoordenadaY);

            bundle.putString("ClienteCelular", sharedPreferences2.getString("ClienteCelular",""));
            bundle.putString("ClienteTelefono", sharedPreferences2.getString("ClienteTelefono",""));

            intent.putExtras(bundle);//Put your id to your next Intent
            startActivity(intent);
            finish();

        }*/

      //  FncMostrarMensaje("lol",false);




        //OBTENIENDO UBICACION
        //createMapView();

    /*    if(mediaPlayerPedido1!=null) {
            mediaPlayerPedido1.release();
        }
        if(mediaPlayerPedido2!=null) {
            mediaPlayerPedido2.release();
        }

        if(mediaPlayerPedidoOperativo!=null) {
            mediaPlayerPedidoOperativo.release();
        }
        if(mediaPlayerPedidoRobo!=null) {
            mediaPlayerPedidoRobo.release();
        }
        if(mediaPlayerPedidoAccidente!=null) {
            mediaPlayerPedidoAccidente.release();
        }
        if(mediaPlayerPedidoAccidente!=null) {
            mediaPlayerPedidoAccidente.release();
        }

        if(mediaPlayerPedidoClienteLibre!=null) {
            mediaPlayerPedidoClienteLibre.release();
        }
        if(mediaPlayerAlerta!=null) {
            mediaPlayerAlerta.release();
        }
*/




      /*
      mediaPlayerPedido1 = MediaPlayer.create(context, R.raw.sou_pedido1);//TAXI NORMAL
        mediaPlayerPedido2 = MediaPlayer.create(context, R.raw.sou_pedido11);//TAXI ARJONA

        mediaPlayerPedidoOperativo = MediaPlayer.create(context, R.raw.operativo);
        mediaPlayerPedidoRobo = MediaPlayer.create(context, R.raw.sou_robo);
        mediaPlayerPedidoAccidente = MediaPlayer.create(context, R.raw.sou_choque);
        mediaPlayerPedidoClienteLibre = MediaPlayer.create(context, R.raw.sou_cliente_libre);

        mediaPlayerMensajeCentral = MediaPlayer.create(context, R.raw.aud_mensaje);
        //mediaPlayerAlerta = MediaPlayer.create(context, R.raw.alerta);
        mediaPlayerAlerta = MediaPlayer.create(context, R.raw.sou_enviar_alerta);


       */



        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Current volumn Index of particular stream type.
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

        // Get the maximum volume index for a particular stream type.
        float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);

        // Volumn (0 --> 1)
        volume = currentVolumeIndex / maxVolumeIndex;

        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        this.setVolumeControlStream(streamType);

        // For Android SDK >= 21
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            soundPool = builder.build();
        }
        // for Android SDK < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When Sound Pool load complete.
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        // Load sound file (destroy.wav) into SoundPool.
        //this.soundIdDestroy = this.soundPool.load(this, R.raw.destroy,1);

        // Load sound file (gun.wav) into SoundPool.
        //this.soundIdGun = this.soundPool.load(this, R.raw.gun,1);


        soundIdPedido1 = soundPool.load(this, R.raw.sou_pedido1,1);
        soundIdPedido2 = soundPool.load(this, R.raw.sou_pedido11,1);

        soundIdPedidoOperativo= soundPool.load(this, R.raw.operativo,1);
        soundIdPedidoRobo = soundPool.load(this, R.raw.sou_robo,1);
        soundIdPPedidoAccidente = soundPool.load(this, R.raw.sou_choque,1);
        soundIdPedidoClienteLibre = soundPool.load(this, R.raw.sou_cliente_libre,1);


        soundIdError = soundPool.load(this, R.raw.alerta,1);
        soundIdMensaje = soundPool.load(this, R.raw.sou_mensaje,1);
        soundIdMensajeCentral = soundPool.load(this, R.raw.aud_mensaje,1);
        soundIdBocina = soundPool.load(this, R.raw.taxi2,1);
        soundIdAlerta = soundPool.load(this, R.raw.sou_enviar_alerta,1);







       /* mediaPlayerPedido1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mediaPlayerPedido2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mediaPlayerPedidoOperativo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mediaPlayerPedidoRobo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mediaPlayerPedidoAccidente.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mediaPlayerPedidoClienteLibre.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mediaPlayerMensajeCentral.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mediaPlayerAlerta.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });*/

        if(timeMonitoreo1!=null){
            timeMonitoreo1.cancel();
        }

        timeMonitoreo1 = new Timer();
        timeMonitoreo1.schedule(new TimerTask(){
            @Override
            public void run() {

               // Log.e("MsgMonitoreo30", "Cargando pedidos: iniciando time");

                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

                Integer conductorOcupado = sharedPreferences.getInt("ConductorOcupado", 2);
                final boolean MonitoreoEncendido = sharedPreferences.getBoolean("MonitoreoEncendido", true);
                final boolean MonitoreoSonido = sharedPreferences.getBoolean("MonitoreoSonido", true);

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                String GpsActivado = "0";

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    GpsActivado = "1";
                }else{
                    GpsActivado = "2";
                }



                if(conductorOcupado == 2){

                    if(estaConectado(false)){

                        try {

                            //Log.e("MsgMonitoreo30", "Cargando pedidos: iniciando post");

                            final ArrayList<MonitoreoResults> monitoreoResults = new ArrayList<MonitoreoResults>();

                            final String resMtdObtenerPedidoPendientes;
                            resMtdObtenerPedidoPendientes = MtdObtenerPedidoPendientes(ConductorId,GpsActivado,VehiculoCoordenadaX,VehiculoCoordenadaY);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //Log.e("MsgMonitoreo30", "Cargando pedidos: parseando datos del post");

                                    String JsRespuesta = "";
                                    JSONArray JsDatos;

                                    try {

                                        //Log.e("MsgMonitoreo3030", resObtenerPedidoPendientes);

                                        JSONObject jsonObject = new JSONObject(resMtdObtenerPedidoPendientes);
                                        JsRespuesta = jsonObject.getString("Respuesta");
                                        JsDatos = jsonObject.getJSONArray("Datos");

                                      //  Log.e("MsgMonitoreo30", "Cargando pedidos: datos parseados");

                                        for (int i = 0; i < JsDatos.length(); i++) {

                                            JSONObject jsonObj = JsDatos.getJSONObject(i);

                                            String JsPedidoId = jsonObj.getString("PedidoId");


                                            String JsClienteId = jsonObj.getString("ClienteId");
                                            String JsClienteNombre = jsonObj.getString("ClienteNombre");

                                            String JsPedidoDireccion = jsonObj.getString("PedidoDireccion");
                                            String JsPedidoNumero = jsonObj.getString("PedidoNumero");
                                            String JsPedidoReferencia = jsonObj.getString("PedidoReferencia");
                                            String JsPedidoDetalle = jsonObj.getString("PedidoDetalle");
                                            String JsPedidoCambioDe = jsonObj.getString("PedidoCambioDe");
                                            String JsPedidoDetalleCorto = jsonObj.getString("PedidoDetalleCorto");

                                            String JsPedidoOperadora = jsonObj.getString("PedidoOperadora");
                                            String JsPedidoAnexo = jsonObj.getString("PedidoAnexo");
                                            String JsPedidoInfo = jsonObj.getString("PedidoInfo");

                                            String JsPedidoFoto = jsonObj.getString("PedidoFoto");
                                            String JsPedidoTipoUnidad = jsonObj.getString("PedidoTipoUnidad");
                                            String JsPedidoTipo = jsonObj.getString("PedidoTipo");

                                            String JsPedidoCoordenadaX = jsonObj.getString("PedidoCoordenadaX");
                                            String JsPedidoCoordenadaY = jsonObj.getString("PedidoCoordenadaY");

                                            String JsClienteCelular = jsonObj.getString("ClienteCelular");
                                            String JsClienteTelefono = jsonObj.getString("ClienteTelefono");

                                            String JsClienteFoto = jsonObj.getString("ClienteFoto");
                                            String JsClienteVerificado = jsonObj.getString("ClienteVerificado");

                                            String JsPedidoOrigen = jsonObj.getString("PedidoOrigen");
                                            //String JsPedidoTipo = jsonObj.getString("PedidoTipo");

                                            String JsPedidoDistancia = jsonObj.getString("PedidoDistancia");
                                            String JsPedidoTiempo = jsonObj.getString("PedidoTiempo");

                                            //Double PedidoDistancia = redondear(calculateDistance(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY),Double.parseDouble(JsPedidoCoordenadaX),Double.parseDouble(JsPedidoCoordenadaY)),2);
                                            //Double PedidoTiempo = 0.00;

                                            MonitoreoResults sr1 = new MonitoreoResults();

                                            sr1.setPedidoId(JsPedidoId);

                                            sr1.setClienteId(JsClienteId);
                                            sr1.setClienteNombre(JsClienteNombre);
                                            sr1.setPedidoDireccion(JsPedidoDireccion);
                                            sr1.setPedidoNumero(JsPedidoNumero);
                                            sr1.setPedidoReferencia(JsPedidoReferencia);
                                            sr1.setPedidoDetalle(JsPedidoDetalle);
                                            sr1.setPedidoCambioDe(JsPedidoCambioDe);
                                            sr1.setPedidoDetalleCorto(JsPedidoDetalleCorto);

                                            sr1.setPedidoOperadora(JsPedidoOperadora);
                                            sr1.setPedidoAnexo(JsPedidoAnexo);
                                            sr1.setPedidoInfo(JsPedidoInfo);

                                            sr1.setPedidoFoto(JsPedidoFoto);

                                            sr1.setPedidoOrigen(JsPedidoOrigen);
                                            sr1.setPedidoTipoUnidad(JsPedidoTipoUnidad);
                                            sr1.setPedidoTipo(JsPedidoTipo);

                                            sr1.setPedidoCoordenadaX(JsPedidoCoordenadaX);
                                            sr1.setPedidoCoordenadaY(JsPedidoCoordenadaY);

                                            sr1.setClienteCelular(JsClienteCelular);
                                            sr1.setClienteTelefono(JsClienteTelefono);
                                            sr1.setClienteFoto(JsClienteFoto);

                                          /*  if(JsClienteVerificado.equals("1")) {
                                                JsClienteVerificado = "Verificado";
                                            }else {
                                                JsClienteVerificado = "";
                                            }*/

                                            sr1.setClienteVerificado(JsClienteVerificado);
                                          //  PedidoTiempo = redondear(((PedidoDistancia * 1000)/85),2);
                                            //if(JsPedidoOrigen.equals("FIJO")){
                                            //    sr1.setPedidoDistancia("-");
                                                //sr1.setPedidoTiempo("-");
                                           // }else {
                                                //sr1.setPedidoDistancia(""+PedidoDistancia.toString()+" km");
                                                ///sr1.setPedidoTiempo(PedidoTiempo.toString() + " min");
                                                sr1.setPedidoDistancia(JsPedidoDistancia);
                                                sr1.setPedidoTiempo(JsPedidoTiempo);
                                           // }

                                            monitoreoResults.add(sr1);

                                            //                              if(ConductorCobertura>=(PedidoDistancia * 1000)){
                                            //                                    monitoreoResults.add(sr1);
                                            //                                  }else if(ConductorCobertura == 0){
                                            //                                        monitoreoResults.add(sr1);
                                            //                                      }else if(JsPedidoOrigen.equals("FIJO")){
                                            //                                            monitoreoResults.add(sr1);
                                            //                                          }
//
                                        }

                                        TextView TxtPedidoDisponible = (TextView) findViewById(R.id.CmpPedidoDisponible);
                                        TxtPedidoDisponible.setText("" + monitoreoResults.size() + "");

                                        final ListView lstPedidoPendientes = (ListView) findViewById(R.id.LstPedidoPendientes);
                                        lstPedidoPendientes.setAdapter(new MonitoreoAdapter(MonitoreoActivity.this, monitoreoResults));

                                        lstPedidoPendientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                                Object o = lstPedidoPendientes.getItemAtPosition(position);
                                                MonitoreoResults fullObject = (MonitoreoResults)o;


                                                if(!fullObject.getPedidoCoordenadaX().equals("") && !fullObject.getPedidoCoordenadaY().equals("") && !fullObject.getPedidoCoordenadaX().equals("0.00") && !fullObject.getPedidoCoordenadaY().equals("0.00") && fullObject.getPedidoCoordenadaX() != null  && fullObject.getPedidoCoordenadaY() != null) {

                                                    if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null) {

                                                        Double DblPedidoDistancia = redondear(calculateDistance(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY),Double.parseDouble(fullObject.getPedidoCoordenadaX()),Double.parseDouble(fullObject.getPedidoCoordenadaY())),2);
                                                        Double DblPedidoTiempo = 0.00;

                                                        DblPedidoTiempo = redondear((((DblPedidoDistancia)/50)*60),2);

                                                        PedidoDistancia = DblPedidoDistancia.toString();
                                                        PedidoTiempo = DblPedidoTiempo.toString();

                                                    }

                                                }

                                                Intent intent = new Intent(MonitoreoActivity.this, PedidoDetalleActivity.class);
                                                Bundle bundle = new Bundle();

                                                bundle.putString("PedidoId", fullObject.getPedidoId());


                                                bundle.putString("ClienteId", fullObject.getClienteId());
                                                bundle.putString("ClienteNombre", fullObject.getClienteNombre());

                                                bundle.putString("PedidoDireccion", fullObject.getPedidoDireccion());
                                                bundle.putString("PedidoNumero", fullObject.getPedidoNumero());
                                                bundle.putString("PedidoReferencia", fullObject.getPedidoReferencia());
                                                bundle.putString("PedidoDetalle", fullObject.getPedidoDetalle());
                                                bundle.putString("PedidoCambioDe", fullObject.getPedidoCambioDe());

                                                bundle.putString("PedidoOperadora", fullObject.getPedidoOperadora());
                                                bundle.putString("PedidoAnexo", fullObject.getPedidoAnexo());
                                                bundle.putString("PedidoInfo", fullObject.getPedidoInfo());

                                                bundle.putString("PedidoFoto", fullObject.getPedidoFoto());

                                                bundle.putString("PedidoTiempo", PedidoTiempo);
                                                bundle.putString("PedidoDistancia", PedidoDistancia);

                                                bundle.putString("PedidoTipoUnidad", fullObject.getPedidoTipoUnidad());

                                                bundle.putString("PedidoCoordenadaX", fullObject.getPedidoCoordenadaX());
                                                bundle.putString("PedidoCoordenadaY", fullObject.getPedidoCoordenadaY());
                                                bundle.putString("PedidoTipo", fullObject.getPedidoTipo());

                                                bundle.putString("ClienteCelular", fullObject.getClienteCelular());
                                                bundle.putString("ClienteTelefono", fullObject.getClienteTelefono());

                                                //bundle.putString("ConductorId", ConductorId);

                                                bundle.putString("VehiculoCoordenadaX", VehiculoCoordenadaX);
                                                bundle.putString("VehiculoCoordenadaY", VehiculoCoordenadaY);

                                                intent.putExtras(bundle);//Put your id to your next Intent
                                                startActivity(intent);
                                                finish();//AGREGADO 19-11-15

                                            }
                                        });

                                    } catch (JSONException e) {
                                        Log.e("MonitoreoMsg10", e.toString());
                                        e.printStackTrace();
                                    }

                                    // FncMostrarToast(JsRespuesta);
                                    switch(JsRespuesta){
                                        case "P007":

                                            if(monitoreoResults.size()>0 ){

                                                if(MonitoreoEncendido){

                                                    PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                                                    PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
                                                    wakeLock.acquire();

                                                    KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                                                    KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
                                                    keyguardLock.disableKeyguard();
                                                }

                                                if(MonitoreoSonido){

                                                    switch(MonitoreoTono){
                                                        case "1":
                                                            Log.e("MonitoreoTono","1");
                                                            //MediaPlayer player1 = MediaPlayer.create(MonitoreoActivity.this, R.raw.sou_pedido1);
                                                            //player1 = MediaPlayer.create(MonitoreoActivity.this, R.raw.sou_pedido1);
                                                           // player1.start();

                                                          //  if (!mediaPlayerPedido1.isPlaying()) {
                                                            //    mediaPlayerPedido1.start();
                                                          //  }



                                                            if(loaded)  {

                                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                                soundPool.play(soundIdPedido1,volume, volume, 1, 0, 1f);
                                                            }


                                                            //MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.sou_pedido1);
                                                            // no need to call prepare(); create() does that for you
                                                            /*mediaPlayerPedido1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                public void onCompletion(MediaPlayer mp) {
                                                                    mp.release();

                                                                };
                                                            });*/
                                                            break;

                                                        case "2":
                                                            Log.e("MonitoreoTono","2");
                                                            //MediaPlayer player2 = MediaPlayer.create(MonitoreoActivity.this, R.raw.sou_pedido11);
                                                            //player2 = MediaPlayer.create(MonitoreoActivity.this, R.raw.sou_pedido11);
                                                            //player2.start();

                                                            //if (!mediaPlayerPedido2.isPlaying()) {
                                                           //     mediaPlayerPedido2.start();
                                                           // }

                                                            if(loaded)  {

                                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                                soundPool.play(soundIdPedido2,volume, volume, 1, 0, 1f);
                                                            }



                                                            //MediaPlayer mediaPlayer2 = MediaPlayer.create(context, R.raw.sou_pedido11);
                                                           // mediaPlayerPedido2.start(); // no need to call prepare(); create() does that for you
                                                           /* mediaPlayerPedido2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                public void onCompletion(MediaPlayer mp) {
                                                                    mp.release();

                                                                };
                                                            });*/
                                                            break;

                                                    }


                                                }

                                            }

                                            break;

                                        case "P008":
                                            break;

                                        case "P053":
                                            Log.e("PedidosPendientes","No se encontraron pedidos");
                                            break;

                                        case "P057":
                                            Log.e("PedidosPendientes","Version de aplicacion no permitida");
                                            break;

                                        case "P058":
                                            Log.e("PedidosPendientes","Tienes tu GPS desactivado");
                                            break;

                                        case "P059":
                                            Log.e("PedidosPendientes","No se pudo reconocer tu unidad");
                                            break;


                                        default:
                                            break;
                                    }


                                }
                            });


                        } catch (Exception e) {
                            // TODO: handle exception
                            Log.e("MsgMonitoreo9", e.toString());
                        }

                    }




                }else{

                    // final ListView lstPedidoPendientes = (ListView) findViewById(R.id.LstPedidoPendientes);
                    // lstPedidoPendientes.setAdapter(null);

                }


            }
            //}, 1000, AplicacionMonitoreoAlertaContador);
        }, 1000, AplicacionMonitoreoPedidoContador);


        //if(timeMonitoreo1!=null){
        //    timeMonitoreo1.cancel();
        // }




        //TAREA OBTENER  ALERTAS
        if(timeMonitoreo2!=null){
            timeMonitoreo2.cancel();
        }

        //final Timer timer2 = new Timer();
        timeMonitoreo2 = new Timer();
        timeMonitoreo2.schedule(new TimerTask(){
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                Integer conductorOcupado = sharedPreferences.getInt("ConductorOcupado", 2);

                if(conductorOcupado == 2){

                    if(estaConectado(false)){


                        try {

                            final String resConductorAlertas;
                            resConductorAlertas = MtdObtenerConductorAlertas();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JsRespuesta = "";
                                    JSONArray JsDatos;

                                    //if(googleMap != null){

                                        try {

                                            JSONObject jsonObject = new JSONObject(resConductorAlertas);
                                            JsRespuesta = jsonObject.getString("Respuesta");
                                            JsDatos = jsonObject.getJSONArray("Datos");

                                            for (int i = 0; i < JsDatos.length(); i++) {

                                                JSONObject jsonObj = JsDatos.getJSONObject(i);

                                                String JsConductorAlertaTiempoTranscurrido = jsonObj.getString("ConductorAlertaTiempoTranscurrido");
                                                String JsConductorAlertaTipo = jsonObj.getString("ConductorAlertaTipo");
                                                String JsConductorAlertaCoordenadaX = jsonObj.getString("ConductorAlertaCoordenadaX");
                                                String JsConductorAlertaCoordenadaY = jsonObj.getString("ConductorAlertaCoordenadaY");


                                                if(JsConductorAlertaTiempoTranscurrido.equals("0")){

                                                    ConductorAlertaCoordenadaX = JsConductorAlertaCoordenadaX;
                                                    ConductorAlertaCoordenadaY = JsConductorAlertaCoordenadaY;

                                                    menuAlerta.setVisible(false);
                                                    menuVerAlerta.setVisible(true);

                                                    switch (JsConductorAlertaTipo){

                                                        case "1":

                                                           // MediaPlayer mediaPlayer1 = MediaPlayer.create(context, R.raw.choque);
                                                           // mediaPlayer1.start(); // no need to call prepare(); create() does that for you

                                                            //if(!mediaPlayerPedidoAccidente.isPlaying()){
                                                            //    mediaPlayerPedidoAccidente.start();
                                                           // }

                                                            if(loaded)  {

                                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                                soundPool.play(soundIdPPedidoAccidente,volume, volume, 1, 0, 1f);
                                                            }

                                                            /*mediaPlayerPedidoAccidente.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                public void onCompletion(MediaPlayer mp) {
                                                                    mp.release();

                                                                };
                                                            });*/

                                                            break;

                                                        case "2":

                                                            //MediaPlayer mediaPlayer2 = MediaPlayer.create(context, R.raw.robo);
                                                            //mediaPlayer2.start(); // no need to call prepare(); create() does that for you


                                                            //if(!mediaPlayerPedidoRobo.isPlaying()){
                                                           //     mediaPlayerPedidoRobo.start();
                                                          //  }


                                                            if(loaded)  {

                                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                                soundPool.play(soundIdPedidoRobo,volume, volume, 1, 0, 1f);
                                                            }

                                                           /* mediaPlayerPedidoRobo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                public void onCompletion(MediaPlayer mp) {
                                                                    mp.release();

                                                                };
                                                            });*/
                                                            break;

                                                        case "3":


                                                           // MediaPlayer mediaPlayer3 = MediaPlayer.create(context, R.raw.operativo);
                                                           // mediaPlayer3.start(); // no need to call prepare(); create() does that for you



                                                            ////if(!mediaPlayerPedidoOperativo.isPlaying()){
                                                            //    mediaPlayerPedidoOperativo.start();
                                                           // }



                                                            if(loaded)  {

                                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                                soundPool.play(soundIdPedidoOperativo,volume, volume, 1, 0, 1f);
                                                            }



                                                            /*mediaPlayerPedidoOperativo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                public void onCompletion(MediaPlayer mp) {
                                                                    mp.release();

                                                                };
                                                            });*/

                                                            break;

                                                        case "4":

                                                           // if(!mediaPlayerPedidoClienteLibre.isPlaying()){
                                                            //    mediaPlayerPedidoClienteLibre.start();
                                                           // }

                                                            if(loaded)  {

                                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                                soundPool.play(soundIdPedidoClienteLibre,volume, volume, 1, 0, 1f);
                                                            }


                                                            break;



                                                    }


                                                }else{

                                                    menuAlerta.setVisible(true);
                                                    menuVerAlerta.setVisible(false);

                                                    ConductorAlertaCoordenadaX = "";
                                                    ConductorAlertaCoordenadaY = "";

                                                }



                                            }


                                        } catch (JSONException e) {
                                            Log.e("MsgRadar4", e.toString());
                                            e.printStackTrace();
                                        }

                                        switch(JsRespuesta){

                                            case "A001":

                                                break;

                                            case "A002":

                                                menuAlerta.setVisible(true);
                                                menuVerAlerta.setVisible(false);

                                                ConductorAlertaCoordenadaX = "";
                                                ConductorAlertaCoordenadaY = "";

                                                break;

                                            default:
                                                break;

                                        }


                                   // }



                                }
                            });

                        } catch (Exception e) {
                            // TODO: handle exception
                            Log.e("MsgRadar3", e.toString());
                        }

                    }




                }

            }
        }, 10000, AplicacionMonitoreoAlertaContador);
        //}, 2000, 59000);


        //TAREA OBTENER ALERTAS




/*

       if(timeMonitoreo3!=null){
            timeMonitoreo3.cancel();
        }

        //TAREA ENVIAR COORDENADAS - INICIO


        timeMonitoreo3 = new Timer();
        timeMonitoreo3.schedule(new TimerTask(){
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                Integer conductorOcupado = sharedPreferences.getInt("ConductorOcupado", 2);

                Log.e("MsgMonitoreo7","Transfiriendo coordenadas..."+VehiculoUnidad);

                if(conductorOcupado == 2){

                    if(estaConectado(false)){


                    }



                }



            }
        }, 2000, AplicacionMonitoreoCoordenadaContador);
       */


        if(timeMonitoreo3!=null){
            timeMonitoreo3.cancel();
        }

        //TAREA ENVIAR COORDENADAS - INICIO

        //final Timer timer3 = new Timer();
        timeMonitoreo3 = new Timer();
        timeMonitoreo3.schedule(new TimerTask(){
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                Integer conductorOcupado = sharedPreferences.getInt("ConductorOcupado", 2);

                Log.e("MsgMonitoreo7","Transfiriendo coordenadas..."+VehiculoUnidad);

                if(conductorOcupado == 2){

                    if(estaConectado(false)){

                        try {

                            final String resMtdActualizarConductorVehiculoCoordenada;
                            resMtdActualizarConductorVehiculoCoordenada = MtdActualizarConductorVehiculoCoordenada(ConductorId, VehiculoCoordenadaX, VehiculoCoordenadaY,VehiculoUnidad,VehiculoPlaca,ConductorNombre,SectorNombre,ConductorNumeroDocumento,VehiculoOrientacion);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JsRespuesta = "";
                                    String JsSectorNumero = "";
                                    String JsSectorNombre = "";
                                    String JsConductorResetearSesion = "";

                                    try {
                                        JSONObject jsonObject = new JSONObject(resMtdActualizarConductorVehiculoCoordenada);
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

                                            TextView TxtSector = (TextView) findViewById(R.id.CmpSector);
                                            TxtSector.setText((JsSectorNumero));

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



            }
        }, 2000, AplicacionMonitoreoCoordenadaContador);
        //}, 3000, 30000);






        if(timeMonitoreo4!=null){
            timeMonitoreo4.cancel();
        }

        //TAREA OBTENER PARAMETROS

        //final Timer timer3 = new Timer();
        timeMonitoreo4 = new Timer();
        timeMonitoreo4.schedule(new TimerTask(){
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                Integer conductorOcupado = sharedPreferences.getInt("ConductorOcupado", 2);
                final Integer aplicacionActualizarCoordenadasPermanente = sharedPreferences.getInt("AplicacionActualizarCoordenadasPermanente", 2);

               // if(conductorOcupado == 2){

                    if(estaConectado(false)){

                        Thread nt = new Thread() {
                            @Override
                            public void run() {

                                try {

                                    Log.e("Monitoreo2000","Obteniendo parametros aplicacion");

                                    final String resMtdObtenerParametros;
                                    resMtdObtenerParametros = MtdObtenerParametros(getString(R.string.app_version));

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            String JsRespuesta = "";

                                            String JsAplicacionMonitoreoPedidoContador = "";
                                            String JsAplicacionMonitoreoCoordenadaContador = "";
                                            String JsAplicacionMonitoreoCoordenadaPermanenteContador = "";
                                            String JsAplicacionMonitoreoSincronizarConductor = "";

                                            String JsAplicacionMonitoreoAlertaContador = "";

                                            String JsAplicacionRadarConductorContador = "";
                                            String JsAplicacionRadarCoordenadaContador = "";

                                            String JsAplicacionPedidoActualCoordenadaContador = "";
                                            String JsAplicacionPedidoActualPedidoContador = "";

                                            String JsAplicacionActualizarCoordenadasPermanente = "";
                                            String JsAplicacionRestringirIdentificador = "";
                                            String JsAplicacionObtenerAsignado = "";

                                            String JsEmpresaRankingMostrar = "";

                                            try {

                                                JSONObject jsonObject = new JSONObject(resMtdObtenerParametros);
                                                JsRespuesta = jsonObject.getString("Respuesta");

                                                JsAplicacionMonitoreoPedidoContador = jsonObject.getString("AplicacionMonitoreoPedidoContador");
                                                JsAplicacionMonitoreoCoordenadaContador = jsonObject.getString("AplicacionMonitoreoCoordenadaContador");
                                                JsAplicacionMonitoreoCoordenadaPermanenteContador = jsonObject.getString("AplicacionMonitoreoCoordenadaPermanenteContador");
                                                JsAplicacionMonitoreoSincronizarConductor = jsonObject.getString("AplicacionMonitoreoSincronizarConductor");

                                                JsAplicacionMonitoreoAlertaContador = jsonObject.getString("AplicacionMonitoreoAlertaContador");

                                                JsAplicacionRadarConductorContador = jsonObject.getString("AplicacionRadarConductorContador");
                                                JsAplicacionRadarCoordenadaContador = jsonObject.getString("AplicacionRadarCoordenadaContador");

                                                JsAplicacionPedidoActualCoordenadaContador = jsonObject.getString("AplicacionPedidoActualCoordenadaContador");
                                                JsAplicacionPedidoActualPedidoContador = jsonObject.getString("AplicacionPedidoActualPedidoContador");

                                                JsAplicacionActualizarCoordenadasPermanente = jsonObject.getString("AplicacionActualizarCoordenadasPermanente");
                                                JsAplicacionRestringirIdentificador = jsonObject.getString("AplicacionRestringirIdentificador");
                                                JsAplicacionObtenerAsignado = jsonObject.getString("AplicacionObtenerAsignado");

                                                JsEmpresaRankingMostrar = jsonObject.getString("EmpresaRankingMostrar");


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            switch(JsRespuesta){
                                                case "N001":


                                                    EmpresaRankingMostrar = JsEmpresaRankingMostrar;



                                                    SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                                    editor.putInt("AplicacionMonitoreoPedidoContador", Integer.parseInt(JsAplicacionMonitoreoPedidoContador));
                                                    editor.putInt("AplicacionMonitoreoCoordenadaContador", Integer.parseInt(JsAplicacionMonitoreoCoordenadaContador));
                                                    editor.putInt("AplicacionMonitoreoCoordenadaPermanenteContador", Integer.parseInt(JsAplicacionMonitoreoCoordenadaPermanenteContador));
                                                    editor.putInt("AplicacionMonitoreoSincronizarConductor", Integer.parseInt(JsAplicacionMonitoreoSincronizarConductor));

                                                    editor.putInt("AplicacionMonitoreoAlertaContador", Integer.parseInt(JsAplicacionMonitoreoAlertaContador));

                                                    editor.putInt("AplicacionRadarConductorContador", Integer.parseInt(JsAplicacionRadarConductorContador));
                                                    editor.putInt("AplicacionRadarCoordenadaContador", Integer.parseInt(JsAplicacionRadarCoordenadaContador));

                                                    editor.putInt("AplicacionPedidoActualCoordenadaContador", Integer.parseInt(JsAplicacionPedidoActualCoordenadaContador));
                                                    editor.putInt("AplicacionPedidoActualPedidoContador", Integer.parseInt(JsAplicacionPedidoActualPedidoContador));

                                                    editor.putInt("AplicacionActualizarCoordenadasPermanente", Integer.parseInt(JsAplicacionActualizarCoordenadasPermanente));
                                                    editor.putInt("AplicacionRestringirIdentificador", Integer.parseInt(JsAplicacionRestringirIdentificador));
                                                    editor.putInt("AplicacionObtenerAsignado", Integer.parseInt(JsAplicacionObtenerAsignado));

                                                    editor.putString("EmpresaRankingMostrar", (JsEmpresaRankingMostrar));

                                                    editor.apply();





                                                    if(!(AplicacionMonitoreoCoordenadaPermanenteContador == Integer.parseInt(JsAplicacionMonitoreoCoordenadaPermanenteContador))){

                                                        if(aplicacionActualizarCoordenadasPermanente==1){

                                                            if (checkIfGooglePlayEnabled()) {

                                                                Log.e("Main10","Google Play Activado");

                                                                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                                                editor2.putBoolean("currentlyTracking", true);
                                                                editor2.apply();

                                                                if(currentlyTracking){
                                                                    cancelAlarmManager();
                                                                }

                                                                startAlarmManager();

                                                            }else{
                                                                Log.e("Main10","Google Play Desactivado");
                                                            }

                                                        }


                                                    }

                                                    // FncMostrarToast("Se cargaron correctamente los parametros");

                                                    break;

                                                default:

                                                                /*editor.putInt("AplicacionMonitoreoPedidoContador", 12000);
                                                                editor.putInt("AplicacionMonitoreoCoordenadaContador", 30000);
                                                                editor.putInt("AplicacionMonitoreoAlertaContador", 30000);

                                                                editor.putInt("AplicacionRadarConductorContador", 50000);
                                                                editor.putInt("AplicacionRadarCoordenadaContador", 10000);

                                                                editor.putInt("AplicacionPedidoActualCoordenadaContador", 10000);
                                                                editor.putInt("AplicacionPedidoActualPedidoContador", 30000);


                                                                editor.apply();*/

                                                    // FncMostrarToast("No se pudieron cargar los parametros de la aplicacion");
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



               // }



            }
        }, 1000, 60000);

        //TAREA OBTENER PARAMETROS






/*
        timeMonitoreo5 = new Timer();
        timeMonitoreo5.schedule(new TimerTask(){
            @Override
            public void run() {


                Thread nt5 = new Thread() {
                    @Override
                    public void run() {

                        try {

                            final String resObtenerCentralMensaje;
                            resObtenerCentralMensaje = MtdObtenerCentralMensajes(ConductorId,"1");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JsRespuesta = "";
                                    JSONArray JsDatos;

                                    try {

                                        JSONObject jsonObject = new JSONObject(resObtenerCentralMensaje);
                                        JsRespuesta = jsonObject.getString("Respuesta");
                                        JsDatos = jsonObject.getJSONArray("Datos");

                                        for (int i = 0; i < JsDatos.length(); i++) {

                                            JSONObject jsonObj = JsDatos.getJSONObject(i);
                                            String JsCentralMensajeDescripcion = jsonObj.getString("CentralMensajeDescripcion");
                                            String JsCentralMensajeDescripcionCorto = jsonObj.getString("CentralMensajeDescripcionCorto");

                                            switch(JsRespuesta){

                                                case "M001":

                                                    AlertDialog.Builder MsgMonitoreo = new AlertDialog.Builder(MonitoreoActivity.this);
                                                    MsgMonitoreo.setTitle(getString(R.string.alert_title));
                                                    MsgMonitoreo.setMessage(JsCentralMensajeDescripcion);
                                                    MsgMonitoreo.setPositiveButton("Aceptar",
                                                            new DialogInterface.OnClickListener() {

                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    // Do nothing but close the dialog

                                                                }
                                                            });

                                                    // Remember, create doesn't show the dialog
                                                    AlertDialog msgMonitoreo = MsgMonitoreo.create();

                                                    if(!msgMonitoreo.isShowing()){
                                                        msgMonitoreo.show();
                                                    }

                                                    if(MonitoreoSonido){

                                                        MediaPlayer player = MediaPlayer.create(MonitoreoActivity.this, R.raw.taxi4);
                                                        player = MediaPlayer.create(MonitoreoActivity.this, R.raw.aud_mensaje);
                                                        player.start();

                                                    }

                                                    break;

                                                case "M002":
                                                    break;

                                                default:
                                                    break;

                                            }


                                        }

                                    } catch (JSONException e) {
                                        Log.e("MsgMonitoreo100", e.toString());
                                        e.printStackTrace();
                                    }


                                }
                            });
                        } catch (final Exception e) {
                            // TODO: handle exception
                            Log.e("MsgMonitoreo101", e.toString());
                        }

                    }

                };
                nt5.start();


            }
            //}, 1000, AplicacionMonitoreoAlertaContador);
        }, 1000, AplicacionMonitoreoMensajeContador);

*/




        if(timeMonitoreo5!=null){
            timeMonitoreo5.cancel();
        }

        //final Timer timer3 = new Timer();
        timeMonitoreo5 = new Timer();
        timeMonitoreo5.schedule(new TimerTask(){
            @Override
            public void run() {

                if(estaConectado(false)) {

                    Thread nt = new Thread() {
                            @Override
                            public void run() {

                                try {

                                    final String resMtdObtenerConductorMensajeUltimo;
                                    resMtdObtenerConductorMensajeUltimo = MtdObtenerConductorMensajeUltimo(ConductorId,"1","1");


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            String JsRespuesta = "";
                                            String JsConductorMensajeDescripcion = "";
                                            String JsConductorMensajeDescripcionCorto = "";
                                            String JsConductorMensajeImagen = "";
                                            String JsConductorMensajeAudio = "";

                                            try {

                                                JSONObject jsonObject = new JSONObject(resMtdObtenerConductorMensajeUltimo);
                                                JsRespuesta = jsonObject.getString("Respuesta");
                                                JsConductorMensajeDescripcion = jsonObject.getString("ConductorMensajeDescripcion");
                                                JsConductorMensajeDescripcionCorto = jsonObject.getString("ConductorMensajeDescripcionCorto");
                                                JsConductorMensajeImagen = jsonObject.getString("ConductorMensajeImagen");
                                                JsConductorMensajeAudio = jsonObject.getString("ConductorMensajeAudio");
                                                // JsDatos = jsonObject.getJSONArray("Datos");

                                                //for (int i = 0; i < JsDatos.length(); i++) {

                                                   // JSONObject jsonObj = JsDatos.getJSONObject(i);
                                                    //String JsCentralMensajeDescripcion = jsonObj.getString("CentralMensajeDescripcion");
                                                   // String JsCentralMensajeDescripcionCorto = jsonObj.getString("CentralMensajeDescripcionCorto");

                                                    switch(JsRespuesta){

                                                        case "A001":


                                                            if(!JsConductorMensajeImagen.equals("")){

                                                                imgCentralMensajeImagen.setImageResource(R.drawable.central_mensaje_imagen);

                                                                try{

                                                                    //CargarImagenCentralMensajeImagen cargarImagenCentralMensajeImagen = new CargarImagenCentralMensajeImagen();
                                                                    //cargarImagenCentralMensajeImagen.execute(JsConductorMensajeImagen);


                                                                    ImagePopup imagePopupConductorFoto = new ImagePopup(MonitoreoActivity.this);
                                                                    //imagePopup.setWindowHeight(100); // Optional
                                                                    //imagePopup.setWindowWidth(100); // Optional
                                                                    imagePopupConductorFoto.setBackgroundColor(Color.TRANSPARENT);  // Optional
                                                                    imagePopupConductorFoto.setFullScreen(false); // Optional
                                                                    imagePopupConductorFoto.setHideCloseIcon(true);  // Optional
                                                                    imagePopupConductorFoto.setImageOnClickClose(true);  // Optional

                                                                    //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                                                    //imagePopup.initiatePopup(imgPedidoConductorFoto.getDrawable());
                                                                    imagePopupConductorFoto.initiatePopupWithPicasso(JsConductorMensajeImagen);

                                                                    /** Initiate Popup view **/
                                                                    imagePopupConductorFoto.viewPopup();



                                                                }catch (Exception ex){
                                                                    // FncMostrarToast("Ha ocurrido un error cargando la foto.");
                                                                    Log.i("CentralMensajeImagen",ex.toString());
                                                                }


                                                                if(!JsConductorMensajeAudio.equals("")){

                                                                    try{

                                                                        mediaPlayer= new MediaPlayer();
                                                                        mediaPlayer.setDataSource(JsConductorMensajeAudio);
                                                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                                        // mediaPlayer.setVolume(30,00);
                                                                        mediaPlayer.prepare();

                                                                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                                                            @Override
                                                                            public void onCompletion(MediaPlayer mp) {

                                                                            }

                                                                        });

                                                                        mediaPlayer.setVolume(9.0f, 9.0f);
                                                                        mediaPlayer.start();

                                                                    } catch (IOException e) {
                                                                        FncMostrarToast("No se pudo reproducir el mensaje de audio");
                                                                    }

                                                                }else{

                                                                    if(MonitoreoSonido){


                                                                       // if (!mediaPlayerMensajeCentral.isPlaying()) {
                                                                       //     mediaPlayerMensajeCentral.start();
                                                                      //  }


                                                                        if(loaded)  {

                                                                            // Play sound of gunfire. Returns the ID of the new stream.
                                                                            soundPool.play(soundIdMensajeCentral,volume, volume, 1, 0, 1f);
                                                                        }


                                                                        //MediaPlayer player = MediaPlayer.create(MonitoreoActivity.this, R.raw.taxi4);
                                                                        ////player = MediaPlayer.create(MonitoreoActivity.this, R.raw.aud_mensaje);
                                                                        //player.start();

                                                                        //MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.aud_mensaje);
                                                                        //mediaPlayer.start(); // no need to call prepare(); create() does that for you
                                                                        //mediaPlayerMensajeCentral.start();
                                                                       /* mediaPlayerMensajeCentral.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                            public void onCompletion(MediaPlayer mp) {
                                                                                mp.release();

                                                                            };
                                                                        });*/
                                                                    }


                                                                }

                                                                // if(!JsConductorMensajeImagen.equals("")){



                                                                //}

                                                                displayNotificationOne("Aviso:", JsConductorMensajeDescripcion, JsConductorMensajeDescripcionCorto);


                                                            }else{

                                                                if(!JsConductorMensajeAudio.equals("")){

                                                                    try{

                                                                        mediaPlayer = new MediaPlayer();
                                                                        mediaPlayer.setDataSource(JsConductorMensajeAudio);
                                                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                                        // mediaPlayer.setVolume(30,00);
                                                                        mediaPlayer.prepare();

                                                                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                                                            @Override
                                                                            public void onCompletion(MediaPlayer mp) {

                                                                            }

                                                                        });

                                                                        mediaPlayer.setVolume(9.0f, 9.0f);
                                                                        mediaPlayer.start();

                                                                    } catch (IOException e) {
                                                                        FncMostrarToast("No se pudo reproducir el mensaje de audio");
                                                                    }

                                                                }else{

                                                                    if(MonitoreoSonido){

                                                                       // if (!mediaPlayerMensajeCentral.isPlaying()) {
                                                                        //    mediaPlayerMensajeCentral.start();
                                                                       // }


                                                                        if(loaded)  {

                                                                            // Play sound of gunfire. Returns the ID of the new stream.
                                                                            soundPool.play(soundIdMensajeCentral,volume, volume, 1, 0, 1f);
                                                                        }

                                                                       // MediaPlayer player = MediaPlayer.create(MonitoreoActivity.this, R.raw.taxi4);
                                                                       // player = MediaPlayer.create(MonitoreoActivity.this, R.raw.aud_mensaje);
                                                                       // player.start();

                                                                        //MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.aud_mensaje);
                                                                        //mediaPlayer.start(); // no need to call prepare(); create() does that for you
                                                                        //mediaPlayerMensajeCentral.start();
                                                                       /* mediaPlayerMensajeCentral.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                            public void onCompletion(MediaPlayer mp) {
                                                                                mp.release();

                                                                            };
                                                                        });*/

                                                                    }

                                                                }

                                                                displayNotificationOne("Aviso:", JsConductorMensajeDescripcion, JsConductorMensajeDescripcionCorto);

                                                               // FncMostrarMensaje(JsConductorMensajeDescripcion,false);

                                                                AlertDialog.Builder MsgMonitoreo = new AlertDialog.Builder(MonitoreoActivity.this);
                                                                MsgMonitoreo.setTitle(getString(R.string.alert_title));
                                                                MsgMonitoreo.setMessage(JsConductorMensajeDescripcion);
                                                                MsgMonitoreo.setCancelable(false);
                                                                MsgMonitoreo.setPositiveButton("Aceptar",
                                                                        new DialogInterface.OnClickListener() {

                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                // Do nothing but close the dialog

                                                                            }
                                                                        });

                                                                // Remember, create doesn't show the dialog
                                                                AlertDialog msgMonitoreo = MsgMonitoreo.create();

                                                                if(!msgMonitoreo.isShowing()){
                                                                    msgMonitoreo.show();
                                                                }

                                                            }


                                                           /* AlertDialog.Builder MsgMonitoreo = new AlertDialog.Builder(MonitoreoActivity.this);
                                                            MsgMonitoreo.setTitle(getString(R.string.alert_title));
                                                            MsgMonitoreo.setMessage(JsConductorMensajeDescripcion);
                                                            MsgMonitoreo.setCancelable(false);
                                                            MsgMonitoreo.setPositiveButton("Aceptar",
                                                                    new DialogInterface.OnClickListener() {

                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            // Do nothing but close the dialog

                                                                        }
                                                                    });Pend

                                                            // Remember, create doesn't show the dialog
                                                            AlertDialog msgMonitoreo = MsgMonitoreo.create();

                                                            if(!msgMonitoreo.isShowing()){
                                                                msgMonitoreo.show();
                                                            }*/



                                                            break;

                                                        case "A002":
                                                            break;

                                                        default:
                                                            break;

                                                    }


                                              //  }

                                            } catch (JSONException e) {
                                                Log.e("MsgMonitoreo100", e.toString());
                                                e.printStackTrace();
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
        }, 1000, AplicacionMonitoreoMensajeContador);






        //TAREA OBTENER  DESHABILITADO
        if(timeMonitoreo6!=null){
            timeMonitoreo6.cancel();
        }

        //final Timer timer2 = new Timer();
        timeMonitoreo6 = new Timer();
        timeMonitoreo6.schedule(new TimerTask(){
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                Integer aplicacionMonitoreoSincronizarConductor = sharedPreferences.getInt("AplicacionMonitoreoSincronizarConductor", 1);

                    if(estaConectado(false)){

                        if(aplicacionMonitoreoSincronizarConductor==1){

                            try {

                                Log.e("Monitoreo2000","Obteniendo sincronizacion conductor");

                                final String resMtdObtenerConductor;
                                resMtdObtenerConductor = MtdObtenerConductor(ConductorId,"1");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String JsRespuesta = "";
                                        String JsConductorEstado = "";
                                        String JsConductorEstadoDescripcion = "";
                                        String JsConductorEstadoMotivo = "";
                                        String JsConductorIdentificador = "";
                                        String JsConductorResetearSesion = "";

                                        try {

                                            JSONObject jsonObject = new JSONObject(resMtdObtenerConductor);
                                            JsRespuesta = jsonObject.getString("Respuesta");
                                            JsConductorEstado = jsonObject.getString("ConductorEstado");
                                            JsConductorEstadoDescripcion = jsonObject.getString("ConductorEstadoDescripcion");
                                            JsConductorEstadoMotivo = jsonObject.getString("ConductorEstadoMotivo");
                                            JsConductorIdentificador = jsonObject.getString("ConductorIdentificador");
                                            JsConductorResetearSesion = jsonObject.getString("ConductorResetearSesion");

                                        } catch (JSONException e) {
                                            Log.e("MsgRadar4", e.toString());
                                            e.printStackTrace();
                                        }

                                        switch(JsRespuesta){

                                            case "C032":

                                                ConductorEstado = JsConductorEstado;

                                                SharedPreferences.Editor editor1 = sharedPreferences2.edit();
                                                editor1.putString("ConductorEstado", ConductorEstado);
                                                editor1.apply();


                                                if(JsConductorResetearSesion.equals("1")){

                                                  //  if(JsConductorResetearSesion.equals("1")){

                                                   // }


                                                    if(timeMonitoreo1!=null) {
                                                        timeMonitoreo1.cancel();
                                                    }

                                                    if(timeMonitoreo2!=null) {
                                                        timeMonitoreo2.cancel();
                                                    }

                                                    if(timeMonitoreo3!=null) {
                                                        timeMonitoreo3.cancel();
                                                    }

                                                    if(timeMonitoreo4!=null) {
                                                        timeMonitoreo4.cancel();
                                                    }

                                                    if(timeMonitoreo5!=null) {
                                                        timeMonitoreo5.cancel();
                                                    }

                                                    if(timeMonitoreo6!=null) {
                                                        timeMonitoreo6.cancel();
                                                    }

                                                    ConductorId = "";
                                                    VehiculoUnidad = "";
                                                    VehiculoPlaca = "";
                                                    ConductorNombre = "";
                                                    ConductorEstado = "";
                                                    ConductorNumeroDocumento = "";

                                                    saveUserSettings();

                                                    // SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                                    editor2.putBoolean("SesionIniciada", false);
                                                    editor2.putBoolean("ConductorTienePedidoRecepcionar", false);
                                                    editor2.putBoolean("ConductorTienePedido", false);
                                                    editor2.apply();


                                                    android.app.AlertDialog.Builder MsgMonitoreo = new android.app.AlertDialog.Builder(MonitoreoActivity.this);
                                                    MsgMonitoreo.setTitle(getString(R.string.alert_title));
                                                    MsgMonitoreo.setMessage(JsConductorResetearSesion+" Su sesion ha sido cerrada");
                                                    MsgMonitoreo.setCancelable(false);
                                                    MsgMonitoreo.setPositiveButton("Aceptar",
                                                            new DialogInterface.OnClickListener() {

                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    // Do nothing but close the dialog

                                                                    Intent intentMain = new Intent(MonitoreoActivity.this, MainActivity.class);
                                                                    startActivity(intentMain);
                                                                    finish();
                                                                }
                                                            });

                                                    // Remember, create doesn't show the dialog
                                                    android.app.AlertDialog msgMonitoreo = MsgMonitoreo.create();
                                                    msgMonitoreo.show();



                                                }else{


                                                    if(!JsConductorIdentificador.equals(Identificador) && !JsConductorIdentificador.equals("")){

                                                        if(timeMonitoreo1!=null) {
                                                            timeMonitoreo1.cancel();
                                                        }

                                                        if(timeMonitoreo2!=null) {
                                                            timeMonitoreo2.cancel();
                                                        }

                                                        if(timeMonitoreo3!=null) {
                                                            timeMonitoreo3.cancel();
                                                        }

                                                        if(timeMonitoreo4!=null) {
                                                            timeMonitoreo4.cancel();
                                                        }

                                                        if(timeMonitoreo5!=null) {
                                                            timeMonitoreo5.cancel();
                                                        }

                                                        if(timeMonitoreo6!=null) {
                                                            timeMonitoreo6.cancel();
                                                        }




                                                        ConductorId = "";
                                                        VehiculoUnidad = "";
                                                        VehiculoPlaca = "";
                                                        ConductorNombre = "";
                                                        ConductorEstado = "";
                                                        ConductorNumeroDocumento = "";

                                                        saveUserSettings();

                                                        // SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                                        editor2.putBoolean("SesionIniciada", false);
                                                        editor2.putBoolean("ConductorTienePedidoRecepcionar", false);
                                                        editor2.putBoolean("ConductorTienePedido", false);
                                                        editor2.apply();



                                                        android.app.AlertDialog.Builder MsgMonitoreo = new android.app.AlertDialog.Builder(MonitoreoActivity.this);
                                                        MsgMonitoreo.setTitle(getString(R.string.alert_title));
                                                        MsgMonitoreo.setMessage("Su unidad no esta registrada para este equipo");
                                                        MsgMonitoreo.setCancelable(false);
                                                        MsgMonitoreo.setPositiveButton("Aceptar",
                                                                new DialogInterface.OnClickListener() {

                                                                    public void onClick(DialogInterface dialog, int which) {

                                                                        // Do nothing but close the dialog
                                                                        Intent intentMain = new Intent(MonitoreoActivity.this, MainActivity.class);
                                                                        startActivity(intentMain);
                                                                        finish();

                                                                    }
                                                                });

                                                        // Remember, create doesn't show the dialog
                                                        android.app.AlertDialog msgMonitoreo = MsgMonitoreo.create();
                                                        msgMonitoreo.show();

                                                    }else{

                                                        //ConductorEstadoDescripcion = JsConductorEstadoDescripcion;
                                                        //ConductorEstadoMotivo = JsConductorEstadoMotivo;

                                                        //SharedPreferences.Editor editor = sharedPreferences2.edit();
                                                        // editor.putString("ConductorEstado", ConductorEstado);
                                                        //editor.putString("ConductorEstadoDescripcion", JsConductorEstadoDescripcion);
                                                        //editor.putString("ConductorEstadoMotivo", JsConductorEstadoMotivo);
                                                        //editor.apply();

                                                        if(ConductorEstado.equals("1")) {

                                                            if(ConductorOcupado==2){
                                                                // SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                                                // editor2.putInt("ConductorOcupado", ConductorOcupado);
                                                                // editor2.apply();
                                                                btnMonitoreoOcupado.setBackgroundResource(R.drawable.dra_boton_fondo);
                                                                btnMonitoreoOcupado.setText("Desocupado");
                                                            }else{
                                                                //  SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                                                //  editor2.putInt("ConductorOcupado", ConductorOcupado);
                                                                // editor2.apply();
                                                                btnMonitoreoOcupado.setBackgroundResource(R.drawable.dra_boton_fondo2);
                                                                btnMonitoreoOcupado.setText("Ocupado");
                                                            }
                                                            // ConductorOcupado = 2;


                                                            //capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_habilitado);

                                                        }else if(ConductorEstado.equals("2")){

                                                            ListView lstPedidoPendientes = (ListView) findViewById(R.id.LstPedidoPendientes);
                                                            lstPedidoPendientes.setAdapter(null);

                                                            //capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_deshabilitado);

                                                            // Button tiny2 = (Button)findViewById(R.id.BtnMonitoreoOcupado);

                                                            //  tiny2.setBackgroundResource(R.drawable.dra_boton_fondo2);
                                                            /// tiny2.setText("Ocupado");

                                                            btnMonitoreoOcupado.setBackgroundResource(R.drawable.dra_boton_fondo3);
                                                            btnMonitoreoOcupado.setText("Deshabilitado");

                                                            if(ConductorOcupado==2){

                                                                ConductorOcupado = 1;

                                                                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                                                editor2.putInt("ConductorOcupado", ConductorOcupado);
                                                                editor2.apply();

                                                            }


                                                        }else if(ConductorEstado.equals("3")){

                                                            if(timeMonitoreo1!=null) {
                                                                timeMonitoreo1.cancel();
                                                            }

                                                            if(timeMonitoreo2!=null) {
                                                                timeMonitoreo2.cancel();
                                                            }

                                                            if(timeMonitoreo3!=null) {
                                                                timeMonitoreo3.cancel();
                                                            }

                                                            if(timeMonitoreo4!=null) {
                                                                timeMonitoreo4.cancel();
                                                            }

                                                            if(timeMonitoreo5!=null) {
                                                                timeMonitoreo5.cancel();
                                                            }

                                                            if(timeMonitoreo6!=null) {
                                                                timeMonitoreo6.cancel();
                                                            }

                                                            ConductorId = "";
                                                            VehiculoUnidad = "";
                                                            VehiculoPlaca = "";
                                                            ConductorNombre = "";
                                                            ConductorEstado = "";
                                                            ConductorNumeroDocumento = "";

                                                            saveUserSettings();

                                                            // SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                                            editor2.putBoolean("SesionIniciada", false);
                                                            editor2.putBoolean("ConductorTienePedidoRecepcionar", false);
                                                            editor2.putBoolean("ConductorTienePedido", false);
                                                            editor2.apply();


                                                            android.app.AlertDialog.Builder MsgMonitoreo = new android.app.AlertDialog.Builder(MonitoreoActivity.this);
                                                            MsgMonitoreo.setTitle(getString(R.string.alert_title));
                                                            MsgMonitoreo.setMessage("Su unidad ha sido retirada");
                                                            MsgMonitoreo.setCancelable(false);
                                                            MsgMonitoreo.setPositiveButton("Aceptar",
                                                                    new DialogInterface.OnClickListener() {

                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            // Do nothing but close the dialog

                                                                            Intent intentMain = new Intent(MonitoreoActivity.this, MainActivity.class);
                                                                            startActivity(intentMain);
                                                                            finish();
                                                                        }
                                                                    });

                                                            // Remember, create doesn't show the dialog
                                                            android.app.AlertDialog msgMonitoreo = MsgMonitoreo.create();
                                                            msgMonitoreo.show();

                                                        /*capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_retirado);

                                                        Button tiny2 = (Button)findViewById(R.id.BtnMonitoreoOcupado);
                                                        tiny2.setBackgroundResource(R.drawable.dra_boton_fondo2);
                                                        tiny2.setText("Ocupado");
                                                        ConductorOcupado = 1;

                                                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                                                        editor2.putInt("ConductorOcupado", ConductorOcupado);
                                                        editor2.apply();*/

                                                        }

                                                    /*
                                                    if(ConductorEstado.equals("2")){

                                                        btnConductorOcupado.setBackgroundResource(R.drawable.dra_boton_fondo3);
                                                        btnConductorOcupado.setText("Deshabilitado");

                                                    }else{

                                                        if(ConductorOcupado == 1){

                                                            btnConductorOcupado.setBackgroundResource(R.drawable.dra_boton_fondo2);
                                                            btnConductorOcupado.setText("Ocupado");

                                                        }else if(ConductorOcupado == 2){

                                                            btnConductorOcupado.setBackgroundResource(R.drawable.dra_boton_fondo);
                                                            btnConductorOcupado.setText("Desocupado");

                                                        }

                                                    }
                                                    */

                                                    }


                                                }




                                                break;

                                            case "C033":
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

                        }else{

                            //Log.e("Monitoreo2000","Sincronizar conductor activado");
                        }



                    }



            }
        }, 1000, AplicacionMonitoreoSincronizarConductorContador);



        //TAREA OBTENER  ASIGNADO
        if(timeMonitoreo7!=null){
            timeMonitoreo7.cancel();
        }

        //final Timer timer2 = new Timer();
        timeMonitoreo7 = new Timer();
        timeMonitoreo7.schedule(new TimerTask(){
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                Integer conductorOcupado = sharedPreferences.getInt("ConductorOcupado", 2);
                Integer aplicacionObtenerAsignado = sharedPreferences.getInt("AplicacionObtenerAsignado", 1);

                if (conductorOcupado == 2) {

                    if (estaConectado(false)) {

                        if (!ConductorTienePedido) {

                            if(aplicacionObtenerAsignado==1){

                                try {

                                    Log.e("Monitoreo2000","Buscanddo pedido asignado");

                                    final String resMtdObtenerPedidoAsignado;
                                    resMtdObtenerPedidoAsignado = MtdObtenerPedidoAsignado(ConductorId);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            String JsRespuesta = "";
                                            String JsPedidoId = "";


                                            String JsClienteId = "";
                                            String JsClienteNombre = "";

                                            String JsPedidoDireccion = "";
                                            String JsPedidoNumero = "";
                                            String JsPedidoReferencia = "";
                                            String JsPedidoDetalle = "";

                                            String JsPedidoOperadora = "";
                                            String JsPedidoAnexo = "";
                                            String JsPedidoInfo = "";
                                            String JsPedidoCambioDe = "";


                                            String JsPedidoFoto = "";
                                            String JsPedidoTipoUnidad = "";
                                            String JsPedidoCoordenadaX = "";
                                            String JsPedidoCoordenadaY = "";
                                            String JsPedidoTipo = "";

                                            String JsVehiculoCoordenadaX = "";
                                            String JsVehiculoCoordenadaY = "";

                                            String JsClienteCelular = "";
                                            String JsClienteTelefono = "";

                                            try {

                                                JSONObject jsonObject = new JSONObject(resMtdObtenerPedidoAsignado);
                                                JsRespuesta = jsonObject.getString("Respuesta");
                                                JsPedidoId = jsonObject.getString("PedidoId");

                                                JsClienteId = jsonObject.getString("ClienteId");
                                                JsClienteNombre = jsonObject.getString("ClienteNombre");

                                                JsPedidoDireccion = jsonObject.getString("PedidoDireccion");
                                                JsPedidoNumero = jsonObject.getString("PedidoNumero");
                                                JsPedidoReferencia = jsonObject.getString("PedidoReferencia");
                                                JsPedidoDetalle = jsonObject.getString("PedidoDetalle");
                                                JsPedidoCambioDe = jsonObject.getString("PedidoCambioDe");

                                                JsPedidoOperadora = jsonObject.getString("PedidoOperadora");
                                                JsPedidoAnexo = jsonObject.getString("PedidoAnexo");
                                                JsPedidoInfo = jsonObject.getString("PedidoInfo");


                                                JsPedidoFoto = jsonObject.getString("PedidoFoto");
                                                JsPedidoTipoUnidad = jsonObject.getString("PedidoTipoUnidad");
                                                JsPedidoCoordenadaX = jsonObject.getString("PedidoCoordenadaX");
                                                JsPedidoCoordenadaY = jsonObject.getString("PedidoCoordenadaY");
                                                JsPedidoTipo = jsonObject.getString("PedidoTipo");

                                                JsVehiculoCoordenadaX = jsonObject.getString("VehiculoCoordenadaX");
                                                JsVehiculoCoordenadaY = jsonObject.getString("VehiculoCoordenadaY");

                                                JsClienteCelular = jsonObject.getString("ClienteCelular");
                                                JsClienteTelefono = jsonObject.getString("ClienteTelefono");

                                            } catch (JSONException e) {
                                                Log.e("MsgRadar4", e.toString());
                                                e.printStackTrace();
                                            }

                                            switch (JsRespuesta) {

                                                case "P060":

                                                    Intent intent = new Intent(MonitoreoActivity.this, PedidoActualActivity.class);
                                                    Bundle bundle = new Bundle();

                                                    bundle.putString("PedidoId", JsPedidoId);

                                                    bundle.putString("ClienteId", JsClienteId);
                                                    bundle.putString("ClienteNombre", JsClienteNombre);

                                                    bundle.putString("PedidoDireccion", JsPedidoDireccion);
                                                    bundle.putString("PedidoNumero", JsPedidoNumero);
                                                    bundle.putString("PedidoReferencia", JsPedidoReferencia);
                                                    bundle.putString("PedidoDetalle", JsPedidoDetalle);
                                                    bundle.putString("PedidoCambioDe", JsPedidoCambioDe);

                                                    bundle.putString("PedidoAnexo", JsPedidoAnexo);
                                                    bundle.putString("PedidoOperadora", JsPedidoOperadora);
                                                    bundle.putString("PedidoInfo", JsPedidoInfo);

                                                    bundle.putString("PedidoFoto", JsPedidoFoto);
                                                    bundle.putString("PedidoTipoUnidad", JsPedidoTipoUnidad);

                                                    bundle.putString("PedidoCoordenadaX", JsPedidoCoordenadaX);
                                                    bundle.putString("PedidoCoordenadaY", JsPedidoCoordenadaY);
                                                    bundle.putString("PedidoTipo", JsPedidoTipo);

                                                    bundle.putString("VehiculoCoordenadaX", JsVehiculoCoordenadaX);
                                                    bundle.putString("VehiculoCoordenadaY", JsVehiculoCoordenadaY);

                                                    bundle.putString("ClienteCelular", JsClienteCelular);
                                                    bundle.putString("ClienteTelefono", JsClienteTelefono);

                                                    //editor.putBoolean("ConductorTienePedido", true);

                                                    intent.putExtras(bundle);//Put your id to your next Intent
                                                    startActivity(intent);
                                                    finish();

                                                    break;

                                                case "P061":
                                                    break;

                                                case "P062":
                                                    break;
                                                case "P069":
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

                            }else{

                               // Log.e("Monitoreo2000","Pedido Asignado desactivado");

                            }
                        }


                    }


                }
            }
        }, 1000, AplicacionMonitoreoPedidoAsignadoContador);





        currentlyTracking = sharedPreferences2.getBoolean("currentlyTracking", false);

        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
        Integer aplicacionActualizarCoordenadasPermanente = sharedPreferences.getInt("AplicacionActualizarCoordenadasPermanente", 1);

        if(aplicacionActualizarCoordenadasPermanente==1){

            if (checkIfGooglePlayEnabled()) {
                // currentlyTracking = true;
                SharedPreferences.Editor editor = sharedPreferences2.edit();
                editor.putBoolean("currentlyTracking", true);
                //editor.putFloat("totalDistanceInMeters", 0f);
                //editor.putBoolean("firstTimeGettingPosition", true);
                // editor.putString("sessionID",  UUID.randomUUID().toString());
                editor.apply();

                Log.e("Main10","Google Play Activado");

                if(currentlyTracking) {
                    cancelAlarmManager();
                }

                   startAlarmManager();

            }

        }else{
            Log.e("Monitoreo2000","Sincronizacion permanmente desactivada");
        }



      /*  if (checkIfGooglePlayEnabled()) {

            Log.e("Main10","Google Play Activado");

            SharedPreferences.Editor editor = sharedPreferences2.edit();
            editor.putBoolean("currentlyTracking", true);
            //editor.putInt("intervalInMinutes", 5000);
            editor.apply();

            if(currentlyTracking){
                cancelAlarmManager();
            }
            startAlarmManager();

        }else{
            Log.e("Main10","Google Play Desactivado");
        }*/

    }

    private boolean checkIfGooglePlayEnabled() {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            return true;
        } else {
            //Log.e(TAG, "unable to connect to google play services.");
            Log.e("Main10","unable to connect to google play services.");
            Toast.makeText(getApplicationContext(), "No se encontro Google Play Services instalado", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private void startAlarmManager() {
        //Log.d(TAG, "startAlarmManager");
        Log.e("Monitoreo10","startAlarmManager");

        Context context = getBaseContext();
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        gpsTrackerIntent = new Intent(context, GpsTrackerAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);

        intervalInMinutes = sharedPreferences2.getInt("AplicacionMonitoreoCoordenadaPermanenteContador", AplicacionMonitoreoCoordenadaPermanenteContador);

        Log.e("Monitoreo103", String.valueOf(intervalInMinutes));

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                intervalInMinutes, // 60000 = 1 minute
                pendingIntent);
    }

    private void cancelAlarmManager() {
//        Log.d(TAG, "cancelAlarmManager");
        Log.e("Monitoreo10","cancelAlarmManager");

        Context context = getBaseContext();
        Intent gpsTrackerIntent = new Intent(context, GpsTrackerAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private MenuItem menuAlerta;
    private MenuItem menuVerAlerta;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.monitoreo, menu);

        menuAlerta = menu.findItem(R.id.action_alerta);
        menuVerAlerta = menu.findItem(R.id.action_ver_alerta);

        menuAlerta.setVisible(true);
        menuVerAlerta.setVisible(false);
        //MenuItem itemVerAlerta = menu.findItem(R.id.action_ver_alerta);
       // itemVerAlerta.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aviso) {

            final ProgressDialog PrgMonitoreo = new ProgressDialog(this);
            PrgMonitoreo.setIcon(R.mipmap.ic_launcher);
            PrgMonitoreo.setMessage("Cargando...");
            PrgMonitoreo.setCancelable(false);
            PrgMonitoreo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            PrgMonitoreo.show();

            Thread nt_aviso = new Thread() {
                @Override
                public void run() {

                    try {

                        final String resMtdObtenerConductorMensajeUltimo;
                        resMtdObtenerConductorMensajeUltimo = MtdObtenerConductorMensajeUltimo(ConductorId,"2","");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                PrgMonitoreo.cancel();

                                String JsRespuesta = "";
                                String JsConductorMensajeDescripcion = "";
                                String JsConductorMensajeDescripcionCorto = "";
                                String JsConductorMensajeImagen = "";
                                String JsConductorMensajeAudio = "";
                                //JSONArray JsDatos;

                                try {

                                    JSONObject jsonObject = new JSONObject(resMtdObtenerConductorMensajeUltimo);
                                    JsRespuesta = jsonObject.getString("Respuesta");
                                    JsConductorMensajeDescripcion = jsonObject.getString("ConductorMensajeDescripcion");
                                    JsConductorMensajeDescripcionCorto = jsonObject.getString("ConductorMensajeDescripcionCorto");
                                    JsConductorMensajeImagen = jsonObject.getString("ConductorMensajeImagen");
                                    JsConductorMensajeAudio = jsonObject.getString("ConductorMensajeAudio");
                                    //JsDatos = jsonObject.getJSONArray("Datos");

                                   // for (int i = 0; i < JsDatos.length(); i++) {

                                      //  JSONObject jsonObj = JsDatos.getJSONObject(i);
                                      //  String JsCentralMensajeDescripcion = jsonObj.getString("CentralMensajeDescripcion");
                                      //  String JsCentralMensajeDescripcionCorto = jsonObj.getString("CentralMensajeDescripcionCorto");

                                   /// Log.i("ConductorMensajeImagen",JsConductorMensajeImagen);

                                        switch(JsRespuesta){

                                            case "A001":

                                                if(!JsConductorMensajeImagen.equals("")){

                                                    imgCentralMensajeImagen.setImageResource(R.drawable.central_mensaje_imagen);

                                                    try{

                                                        //CargarImagenCentralMensajeImagen cargarImagenCentralMensajeImagen = new CargarImagenCentralMensajeImagen();
                                                        //cargarImagenCentralMensajeImagen.execute(JsConductorMensajeImagen);

                                                            //Picasso.with(context).load(JsConductorMensajeImagen).into(imgCentralMensajeImagen);


                                                            ImagePopup imagePopupConductorFoto = new ImagePopup(MonitoreoActivity.this);
                                                            //imagePopup.setWindowHeight(100); // Optional
                                                            //imagePopup.setWindowWidth(100); // Optional
                                                            imagePopupConductorFoto.setBackgroundColor(Color.TRANSPARENT);  // Optional
                                                            imagePopupConductorFoto.setFullScreen(false); // Optional
                                                            imagePopupConductorFoto.setHideCloseIcon(true);  // Optional
                                                            imagePopupConductorFoto.setImageOnClickClose(true);  // Optional

                                                            //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                                            //imagePopup.initiatePopup(imgPedidoConductorFoto.getDrawable());
                                                            imagePopupConductorFoto.initiatePopupWithPicasso(JsConductorMensajeImagen);

                                                            /** Initiate Popup view **/
                                                            imagePopupConductorFoto.viewPopup();



                                                    }catch (Exception ex){
                                                        // FncMostrarToast("Ha ocurrido un error cargando la foto.");
                                                        Log.i("CentralMensajeImagen",ex.toString());
                                                    }


                                                    if(!JsConductorMensajeAudio.equals("")){

                                                        try{

                                                            mediaPlayer = new MediaPlayer();
                                                            mediaPlayer.setDataSource(JsConductorMensajeAudio);
                                                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                            // mediaPlayer.setVolume(30,00);
                                                            mediaPlayer.prepare();

                                                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                                                @Override
                                                                public void onCompletion(MediaPlayer mp) {

                                                                }

                                                            });

                                                            mediaPlayer.setVolume(9.0f, 9.0f);
                                                            mediaPlayer.start();

                                                        } catch (IOException e) {
                                                            FncMostrarToast("No se pudo reproducir el mensaje de audio");
                                                        }

                                                    }

                                                   // if(!JsConductorMensajeImagen.equals("")){



                                                    //}

                                                    displayNotificationOne("Aviso:", JsConductorMensajeDescripcion, JsConductorMensajeDescripcionCorto);


                                                }else{

                                                    if(!JsConductorMensajeAudio.equals("")){

                                                        try{

                                                            mediaPlayer = new MediaPlayer();
                                                            mediaPlayer.setDataSource(JsConductorMensajeAudio);
                                                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                            // mediaPlayer.setVolume(30,00);
                                                            mediaPlayer.prepare();

                                                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                                                @Override
                                                                public void onCompletion(MediaPlayer mp) {

                                                                }

                                                            });

                                                            mediaPlayer.setVolume(9.0f, 9.0f);
                                                            mediaPlayer.start();

                                                        } catch (IOException e) {
                                                            FncMostrarToast("No se pudo reproducir el mensaje de audio");
                                                        }

                                                    }

                                                    displayNotificationOne("Aviso:", JsConductorMensajeDescripcion, JsConductorMensajeDescripcionCorto);

                                                    FncMostrarMensaje(JsConductorMensajeDescripcion,false);

                                                }


                                                break;

                                            case "A002":
                                                FncMostrarToast("No se encuentraron mensajes");
                                                break;

                                            default:
                                                break;

                                        }


                                 //   }



                                } catch (JSONException e) {
                                    Log.e("MsgMonitoreo100", e.toString());
                                    e.printStackTrace();
                                }



                            }
                        });
                    } catch (final Exception e) {
                        // TODO: handle exception
                        Log.e("MsgMonitoreo101", e.toString());
                    }

                }

            };
            nt_aviso.start();

            return true;

        }else if(id == R.id.action_ver_alerta){

            Intent intentRadar = new Intent(MonitoreoActivity.this, RadarActivity.class);

            Bundle bundleRadar = new Bundle();
            bundleRadar.putString("ConductorId", ConductorId);
            bundleRadar.putString("ConductorAlertaCoordenadaX", ConductorAlertaCoordenadaX);
            bundleRadar.putString("ConductorAlertaCoordenadaY", ConductorAlertaCoordenadaY);
            bundleRadar.putString("ConductorAlerta", "1");

            intentRadar.putExtras(bundleRadar); //Put your id to your next Intent

            startActivity(intentRadar);
            finish();

            return true;
        }else if(id == R.id.action_registrar_alerta1){

            if(estaConectado(true)){

                //REGISTRANDO ALERTA

                Thread nt = new Thread() {
                    @Override
                    public void run() {

                        try {

                            final String resMonitoreoRegistrarConductorAlerta;

                            resMonitoreoRegistrarConductorAlerta = postEnviarMonitoreoRegistrarAlerta(ConductorId,"1",VehiculoCoordenadaX,VehiculoCoordenadaY,ConductorNombre,ConductorNumeroDocumento,VehiculoUnidad,VehiculoPlaca);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JSRespuesta = "";
                                    String JSConductorId = "";

                                    try {
                                        JSONObject jsonObject = new JSONObject(resMonitoreoRegistrarConductorAlerta);
                                        JSRespuesta = jsonObject.getString("Respuesta");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    switch(JSRespuesta){
                                        case "C023":

                                            //MediaPlayer player=MediaPlayer.create(MonitoreoActivity.this,R.raw.alerta);
                                            //player = MediaPlayer.create(MonitoreoActivity.this, R.raw.alerta);
                                            //player.start();

                                           // MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alerta);
                                            //mediaPlayer.start(); // no need to call prepare(); create() does that for you
                                         /*   mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                public void onCompletion(MediaPlayer mp) {
                                                    mp.release();

                                                };
                                            });*/

                                            //if (!mediaPlayerAlerta.isPlaying()) {
                                            //    mediaPlayerAlerta.start();
                                           // }

                                            if(loaded)  {

                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                soundPool.play(soundIdAlerta,volume, volume, 1, 0, 1f);
                                            }


                                            //mediaPlayerAlerta.start();

                                            break;

                                        case "C024":
                                            FncMostrarMensaje("No se ha podido registrar la alerta, intente nuevamente",false);
                                            break;

                                        case "C025":
                                            FncMostrarMensaje("No se ha podido identificarlo, ingrese nuevamente a la aplicacion",false);
                                            break;

                                        default:
                                            FncMostrarToast(getString(R.string.message_error_interno));
                                            break;
                                    }

                                }
                            });
                        } catch (final Exception e) {
                            // TODO: handle exception
                            Log.e("MsgMonitoreo11", e.toString());
                        }

                    }

                };
                nt.start();


            }



            return true;
        }else if(id == R.id.action_registrar_alerta2){

            if(estaConectado(true)){


                //REGISTRANDO ALERTA

                Thread nt2 = new Thread() {
                    @Override
                    public void run() {

                        try {

                            final String resMonitoreoRegistrarConductorAlerta;

                            resMonitoreoRegistrarConductorAlerta = postEnviarMonitoreoRegistrarAlerta(ConductorId,"2",VehiculoCoordenadaX,VehiculoCoordenadaY,ConductorNombre,ConductorNumeroDocumento,VehiculoUnidad,VehiculoPlaca);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JSRespuesta = "";
                                    String JSConductorId = "";

                                    try {
                                        JSONObject jsonObject = new JSONObject(resMonitoreoRegistrarConductorAlerta);
                                        JSRespuesta = jsonObject.getString("Respuesta");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    switch(JSRespuesta){
                                        case "C023":

                                          //  MediaPlayer player=MediaPlayer.create(MonitoreoActivity.this,R.raw.alerta);
                                           // player = MediaPlayer.create(MonitoreoActivity.this, R.raw.alerta);
                                          //  player.start();

                                          //  MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alerta);
                                           // mediaPlayer.start(); // no need to call prepare(); create() does that for you
                                          /*  mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                public void onCompletion(MediaPlayer mp) {
                                                    mp.release();

                                                };
                                            });*/


                                            //if (!mediaPlayerAlerta.isPlaying()) {
                                            //    mediaPlayerAlerta.start();
                                           // }

                                            if(loaded)  {

                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                soundPool.play(soundIdAlerta,volume, volume, 1, 0, 1f);
                                            }


                                            //mediaPlayerAlerta.start();
                                            break;

                                        case "C024":
                                            FncMostrarMensaje("No se ha podido registrar la alerta, intente nuevamente",false);
                                            break;

                                        case "C025":
                                            FncMostrarMensaje("No se ha podido identificarlo, ingrese nuevamente a la aplicacion",false);
                                            break;

                                        default:
                                            FncMostrarToast(getString(R.string.message_error_interno));
                                            break;
                                    }

                                }
                            });
                        } catch (final Exception e) {
                            // TODO: handle exception
                            Log.e("MsgMonitoreo11", e.toString());
                        }

                    }

                };
                nt2.start();



            }


            return true;
        }else if(id == R.id.action_registrar_alerta3){

            if(estaConectado(true)){


                //REGISTRANDO ALERTA

                Thread nt3 = new Thread() {
                    @Override
                    public void run() {

                        try {

                            final String resMonitoreoRegistrarConductorAlerta;

                            resMonitoreoRegistrarConductorAlerta = postEnviarMonitoreoRegistrarAlerta(ConductorId,"3",VehiculoCoordenadaX,VehiculoCoordenadaY,ConductorNombre,ConductorNumeroDocumento,VehiculoUnidad,VehiculoPlaca);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JSRespuesta = "";
                                    String JSConductorId = "";

                                    try {
                                        JSONObject jsonObject = new JSONObject(resMonitoreoRegistrarConductorAlerta);
                                        JSRespuesta = jsonObject.getString("Respuesta");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    switch(JSRespuesta){
                                        case "C023":

                                            //MediaPlayer player=MediaPlayer.create(MonitoreoActivity.this,R.raw.alerta);
                                            //player = MediaPlayer.create(MonitoreoActivity.this, R.raw.alerta);
                                           // player.start();

                                           // MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alerta);
                                           // mediaPlayer.start(); // no need to call prepare(); create() does that for you
                                           /* mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                public void onCompletion(MediaPlayer mp) {
                                                    mp.release();

                                                };
                                            });*/

                                           //if (!mediaPlayerAlerta.isPlaying()) {
                                           //     mediaPlayerAlerta.start();
                                          //  }

                                            if(loaded)  {

                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                soundPool.play(soundIdAlerta,volume, volume, 1, 0, 1f);
                                            }


                                            //mediaPlayerAlerta.start();
                                            break;

                                        case "C024":
                                            FncMostrarMensaje("No se ha podido registrar la alerta, intente nuevamente",false);
                                            break;

                                        case "C025":
                                            FncMostrarMensaje("No se ha podido identificarlo, ingrese nuevamente a la aplicacion",false);
                                            break;

                                        default:
                                            FncMostrarToast(getString(R.string.message_error_interno));
                                            break;
                                    }

                                }
                            });
                        } catch (final Exception e) {
                            // TODO: handle exception
                            Log.e("MsgMonitoreo11", e.toString());
                        }

                    }

                };
                nt3.start();




            }

            return true;
        }else if(id == R.id.action_registrar_alerta4){

            if(estaConectado(true)){


                //REGISTRANDO ALERTA

                Thread nt4 = new Thread() {
                    @Override
                    public void run() {

                        try {

                            final String resMonitoreoRegistrarConductorAlerta;

                            resMonitoreoRegistrarConductorAlerta = postEnviarMonitoreoRegistrarAlerta(ConductorId,"4",VehiculoCoordenadaX,VehiculoCoordenadaY,ConductorNombre,ConductorNumeroDocumento,VehiculoUnidad,VehiculoPlaca);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JSRespuesta = "";
                                    String JSConductorId = "";

                                    try {
                                        JSONObject jsonObject = new JSONObject(resMonitoreoRegistrarConductorAlerta);
                                        JSRespuesta = jsonObject.getString("Respuesta");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    switch(JSRespuesta){
                                        case "C023":

                                            //MediaPlayer player=MediaPlayer.create(MonitoreoActivity.this,R.raw.alerta);
                                            //player = MediaPlayer.create(MonitoreoActivity.this, R.raw.alerta);
                                            // player.start();

                                            // MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alerta);
                                            // mediaPlayer.start(); // no need to call prepare(); create() does that for you
                                           /* mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                public void onCompletion(MediaPlayer mp) {
                                                    mp.release();

                                                };
                                            });*/


                                           //if (!mediaPlayerAlerta.isPlaying()) {
                                           //     mediaPlayerAlerta.start();
                                            //}

                                            if(loaded)  {

                                                // Play sound of gunfire. Returns the ID of the new stream.
                                                soundPool.play(soundIdAlerta,volume, volume, 1, 0, 1f);
                                            }

                                            //mediaPlayerAlerta.start();
                                            break;

                                        case "C024":
                                            FncMostrarMensaje("No se ha podido registrar la alerta, intente nuevamente",false);
                                            break;

                                        case "C025":
                                            FncMostrarMensaje("No se ha podido identificarlo, ingrese nuevamente a la aplicacion",false);
                                            break;

                                        default:
                                            FncMostrarToast(getString(R.string.message_error_interno));
                                            break;
                                    }

                                }
                            });
                        } catch (final Exception e) {
                            // TODO: handle exception
                            Log.e("MsgMonitoreo11", e.toString());
                        }

                    }

                };
                nt4.start();




            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ver_cuenta) {
            // Handle the camera action
            Intent intentMisDatos = new Intent(MonitoreoActivity.this, MisDatosActivity.class);
            startActivity(intentMisDatos);

            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            finish();

        } else if (id == R.id.nav_soporte) {

            Intent intentSoporte = new Intent(MonitoreoActivity.this, SoporteActivity.class);
            startActivity(intentSoporte);

            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            finish();

        } else if (id == R.id.nav_mis_preferencias) {

            Intent intent_mis_preferencias = new Intent(MonitoreoActivity.this, MisPreferenciasActivity.class);
            startActivity(intent_mis_preferencias);

            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            finish();

        } else if (id == R.id.nav_listar_operadoras) {

            Intent intentOperadorasActivity = new Intent(MonitoreoActivity.this, OperadorasActivity.class);
            startActivity(intentOperadorasActivity);

            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            finish();

        /*} else if (id == R.id.nav_recepcion_archivos) {

            Intent intentRecepcionArchivos = new Intent(MonitoreoActivity.this, RecepcionArchivosActivity.class);
            startActivity(intentRecepcionArchivos);
            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            finish();*/

        } else if (id == R.id.nav_listar_supervisores) {

            Intent intentListarSupervisores = new Intent(MonitoreoActivity.this, ListarSupervisoresActivity.class);
            startActivity(intentListarSupervisores);
            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            finish();

        } else if (id == R.id.nav_historial) {

            Intent intent_historial = new Intent(MonitoreoActivity.this, HistorialActivity.class);
            Bundle bundle_historial = new Bundle();
            bundle_historial.putString("ConductorId", ConductorId);

            intent_historial.putExtras(bundle_historial); //Put your id to your next Intent
            startActivity(intent_historial);

            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            finish();



        } else if (id == R.id.nav_modulo_supervisor) {

            Intent intentSupervisor = new Intent(MonitoreoActivity.this, SupervisorActivity.class);
            startActivity(intentSupervisor);

            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            finish();

        } else if (id == R.id.nav_leyenda) {


            Intent intentLeyenda = new Intent(MonitoreoActivity.this, LeyendaActivity.class);
            startActivity(intentLeyenda);

            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);

            finish();

        } else if (id == R.id.nav_ranking) {


if(EmpresaRankingMostrar.equals("SI")){

    Intent intentRanking = new Intent(MonitoreoActivity.this, RankingActivity.class);
    startActivity(intentRanking);

    MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
            R.anim.anim_slide_out_left);

    finish();


}else{

    FncMostrarMensaje("El ranking se encuentra deshabilitado en estos momentos.",false);
}



       /* } else if (id == R.id.nav_registrar_incidente) {

            Intent intentRegistrarIncidente = new Intent(MonitoreoActivity.this, RegistrarIncidenteActivity.class);
            startActivity(intentRegistrarIncidente);

            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);

            finish();*/

       /* } else if (id == R.id.nav_radio) {

            Intent intentRadio = new Intent(MonitoreoActivity.this, RadioActivity.class);
            startActivity(intentRadio);

            MonitoreoActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);

            finish();
*/

        } else if (id == R.id.nav_acerca_de) {

            FncMostrarAcercaDe();

        } else if (id == R.id.nav_salir_cuenta) {


            AlertDialog.Builder MsgCerrarSesion = new AlertDialog.Builder(this);
            MsgCerrarSesion.setTitle(getString(R.string.alert_title));
            MsgCerrarSesion.setMessage("¿Realmente desea cerrar sesion?");
            MsgCerrarSesion.setCancelable(false);
            MsgCerrarSesion.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog


                            ConductorId = "";
                            VehiculoUnidad = "";
                            VehiculoPlaca = "";
                            ConductorNombre = "";
                            ConductorEstado = "";
                            ConductorNumeroDocumento = "";

                            saveUserSettings();

                            SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("SesionIniciada", false);
                            editor.putBoolean("ConductorTienePedidoRecepcionar", false);
                            editor.putBoolean("ConductorTienePedido", false);
                            editor.apply();

                            Intent intentMonitoreo4 = new Intent(MonitoreoActivity.this, MainActivity.class);
                            startActivity(intentMonitoreo4);
                            finish();


                        }
                    });

            MsgCerrarSesion.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                        }
                    });

            // Remember, create doesn't show the dialog
            AlertDialog msgCerrarSesion = MsgCerrarSesion.create();
            msgCerrarSesion.show();

        } else if (id == R.id.nav_salir) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

            MonitoreoListener mlocListener = new MonitoreoListener();
            mlocListener.setMonitoreoActivity(this);
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




    private void createMapView(){


        try {

            if(null == googleMap){


                //googleMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map1)).getMap();
                //googleMap.setMyLocationEnabled(true);
                //googleMap.getUiSettings().setZoomControlsEnabled(true);
                //googleMap.setPadding(0, 0, 0, 120);

                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map1)).getMap();
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setPadding(0, 0, 0, 120);

                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {

                        if(location!=null){

                            VehiculoCoordenadaX = Double.toString(location.getLatitude());
                            VehiculoCoordenadaY = Double.toString(location.getLongitude());
                            Float direction = location.getBearing();

                           // Log.e("VehiculoCoordenadaX",VehiculoCoordenadaX);
                           // Log.e("VehiculoCoordenadaY",VehiculoCoordenadaY);
                            if(direction.intValue()>0){
                                VehiculoOrientacion =  (direction.intValue());
                            }


                        }



                    }
                });


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

    public void onClick_BtnMonitoreoOcupado(View v){

        if(ConductorEstado.equals("1")){

            if(ConductorOcupado == 1){

                Button tiny = (Button)findViewById(R.id.BtnMonitoreoOcupado);
                tiny.setBackgroundResource(R.drawable.dra_boton_fondo);
                tiny.setText("Desocupado");

                ConductorOcupado = 2;

                if(estaConectado(true)){

                    //REGISTRANDO OCUPADO

                    Thread ntMtdActualizarOcupado = new Thread() {
                        @Override
                        public void run() {

                            try {

                                final String resMtdActualizarOcupado;

                                resMtdActualizarOcupado = MtdActualizarOcupado(ConductorId,"2");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String JSRespuesta = "";
                                        String JSConductorId = "";

                                        try {
                                            JSONObject jsonObject = new JSONObject(resMtdActualizarOcupado);
                                            JSRespuesta = jsonObject.getString("Respuesta");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        switch(JSRespuesta){

                                            case "U010":
                                                break;

                                            case "U011":
                                                break;

                                            case "U012":
                                                break;

                                            case "U013":
                                                break;

                                            default:
                                                //FncMostrarToast(getString(R.string.message_error_interno));
                                                break;
                                        }

                                    }
                                });
                            } catch (final Exception e) {
                                // TODO: handle exception
                                Log.e("MsgMonitoreo11", e.toString());
                            }

                        }

                    };
                    ntMtdActualizarOcupado.start();




                }


            }else if(ConductorOcupado == 2){

                ListView lstPedidoPendientes = (ListView) findViewById(R.id.LstPedidoPendientes);
                lstPedidoPendientes.setAdapter(null);


                Button tiny2 = (Button)findViewById(R.id.BtnMonitoreoOcupado);
                tiny2.setBackgroundResource(R.drawable.dra_boton_fondo2);
                tiny2.setText("Ocupado");

                ConductorOcupado = 1;

                if(estaConectado(true)){

                    //REGISTRANDO OCUPADO

                    Thread ntMtdActualizarOcupado = new Thread() {
                        @Override
                        public void run() {

                            try {

                                final String resMtdActualizarOcupado;

                                resMtdActualizarOcupado = MtdActualizarOcupado(ConductorId,"1");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String JSRespuesta = "";
                                        String JSConductorId = "";

                                        try {
                                            JSONObject jsonObject = new JSONObject(resMtdActualizarOcupado);
                                            JSRespuesta = jsonObject.getString("Respuesta");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        switch(JSRespuesta){

                                            case "U010":
                                                break;

                                            case "U011":
                                                break;

                                            case "U012":
                                                break;

                                            case "U013":
                                                break;

                                            default:
                                                //FncMostrarToast(getString(R.string.message_error_interno));
                                                break;
                                        }

                                    }
                                });
                            } catch (final Exception e) {
                                // TODO: handle exception
                                Log.e("MsgMonitoreo11", e.toString());
                            }

                        }

                    };
                    ntMtdActualizarOcupado.start();




                }

            }

            SharedPreferences sharedPreferences = this.getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("ConductorOcupado", ConductorOcupado);
            editor.apply();




        }else {

            AlertDialog.Builder MsgMonitoreo = new AlertDialog.Builder(this);
            MsgMonitoreo.setTitle(getString(R.string.alert_title));
            MsgMonitoreo.setMessage("Su unidad no se encuentra habilitada");
            MsgMonitoreo.setCancelable(false);
            MsgMonitoreo.setPositiveButton("Aceptar",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });

            // Remember, create doesn't show the dialog
            AlertDialog msgMonitoreo = MsgMonitoreo.create();
            msgMonitoreo.show();

        }


    }

    public void onClick_BtnMonitoreoUbicacion(View v){

        //FncMostrarToast("Cargando formulario de ubicacion ...");

      /*  Intent intent_ubicacion = new Intent(MonitoreoActivity.this, RadarActivity.class);

        Bundle bundle_ubicacion = new Bundle();
        bundle_ubicacion.putString("ConductorId", ConductorId);

        intent_ubicacion.putExtras(bundle_ubicacion); //Put your id to your next Intent

        startActivity(intent_ubicacion);
        finish();*/


        Intent intentRadar = new Intent(MonitoreoActivity.this, RadarActivity.class);

        Bundle bundleRadar = new Bundle();
        bundleRadar.putString("ConductorId", ConductorId);
        bundleRadar.putString("ConductorAlertaCoordenadaX", "");
        bundleRadar.putString("ConductorAlertaCoordenadaY", "");
        bundleRadar.putString("ConductorAlerta", "2");

        intentRadar.putExtras(bundleRadar); //Put your id to your next Intent

        startActivity(intentRadar);
        finish();


    }


    /*
    AUXILIARES
     */
    private void FncMostrarAcercaDe() {

        AlertDialog.Builder MsgAcercaDe = new AlertDialog.Builder(this);
        MsgAcercaDe.setTitle("ACERCA DE");
        MsgAcercaDe.setMessage(getString(R.string.app_version));
        MsgAcercaDe.setCancelable(false);
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



        AlertDialog.Builder MsgGeneral = new AlertDialog.Builder(this,R.style.CustomAlertDialog);

      //  TextView resultMessage = new TextView(MonitoreoActivity.this);
      //  resultMessage.setTextSize(22);
      //  resultMessage.setText(oMensaje);
      //      resultMessage.setGravity(Gravity.FILL_HORIZONTAL);
      //  MsgGeneral.setView(resultMessage);



         MsgGeneral.setTitle(getString(R.string.alert_title));
      MsgGeneral.setMessage(oMensaje);
       MsgGeneral.setCancelable(false);


       // TextView myMsg = new TextView(this);
       // myMsg.setText("Central");
      //  myMsg.setGravity(Gravity.RIGHT);


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

        Toast.makeText(MonitoreoActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();
    }

    protected void displayNotificationOne(String oTitulo,String oContenido,String oContenidoGrande) {

        int notificationId = 001;

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(MonitoreoActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(oTitulo)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(oContenidoGrande))
                        .setContentText(oContenido)
                        //.setVibrate(new long[] { 100, 250 })
                        .setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(MonitoreoActivity.this);

        notificationManager.notify(notificationId, notificationBuilder.build());

    }



    /*
    * ENVIO DE VARIABLES
    */
    public String MtdObtenerPedidoPendientes(String oConductorId,String oGpsActivado,String oVehiculoCoordenadaX,String oVehiculoCoordenadaY) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnpedido));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String>postDataParams=new HashMap<>();
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("GpsActivado", oGpsActivado);
            postDataParams.put("VehiculoCoordenadaX", oVehiculoCoordenadaX);
            postDataParams.put("VehiculoCoordenadaY", oVehiculoCoordenadaY);

            postDataParams.put("AppVersion", getString(R.string.app_version));
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerPedidoPendientes");

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

            Log.e("Monitoreo16", response);

        }catch (Exception e) {
            Log.e("Monitoreo17", e.toString());
            e.printStackTrace();
        }

        return response;

    }




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
            postDataParams.put("VehiculoGPSOrientacion", String.valueOf(oVehiculoGPSOrientacion));

            postDataParams.put("VehiculoCoordenadaX", oVehiculoCoordenadaX);
            postDataParams.put("VehiculoCoordenadaY", oVehiculoCoordenadaY);
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

            Log.e("Monitoreo14", response);

        } catch (Exception e) {

            Log.e("Monitoreo15", e.toString());
            e.printStackTrace( );
        }

        return response;

    }


    public String postEnviarMonitoreoRegistrarAlerta(String oConductorId,String oAlertaTipo,String oAlertaCoordenadaX,String oAlertaCoordenadaY,String oConductorNombre,String oConductorNumeroDocumento,String oVehiculoUnidad,String oVehiculoPlaca) {

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
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("ConductorNombre", oConductorNombre);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);

            postDataParams.put("VehiculoUnidad", oVehiculoUnidad);
            postDataParams.put("VehiculoPlaca", oVehiculoPlaca);


            postDataParams.put("AlertaTipo", oAlertaTipo);
            postDataParams.put("AlertaCoordenadaX", oAlertaCoordenadaX);
            postDataParams.put("AlertaCoordenadaY", oAlertaCoordenadaY);
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "RegistrarConductorAlerta");

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

            Log.e("Monitoreo12", response);

        } catch (Exception e) {

            Log.e("Monitoreo13", e.toString());
            e.printStackTrace();
        }

        return response;

    }



    public String MtdObtenerConductorAlertas() {

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
            postDataParams.put("ConductorId", ConductorId);
            postDataParams.put("TiempoTranscurrido", "0");
            postDataParams.put("Vigente", "1");
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

            Log.e("Monitoreo10", response);

        } catch (Exception e) {

            Log.e("Monitoreo11", e.toString());
            e.printStackTrace();
        }

        return response;

    }



    public String MtdObtenerParametros(String oAppVersion) {


        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnaplicacion));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String>postDataParams=new HashMap<>();
            postDataParams.put("AppVersion", oAppVersion);
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerParametros");

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


            /*if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }*/

            Log.e("Monitoreo1", response);

        } catch (Exception e) {

            Log.e("Monitoreo2", e.toString());
            e.printStackTrace();
        }

        return response;

    }


    public String MtdObtenerConductorMensajeUltimo(String oConductorId,String oMarcarLeido,String oEstado) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductormensaje));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("MarcarLeido", oMarcarLeido);
            postDataParams.put("Estado", oEstado);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerConductorMensajeUltimo");

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

            Log.e("Monitoreo1", response);

        } catch (Exception e) {

            Log.e("Monitoreo2", e.toString());
            e.printStackTrace();
        }

        return response;

    }

   /* public String MtdObtenerCentralMensajes(String oConductorId,String oCentralMensajeVigente) {

        URL url;
        String response = "";

        try {
            
            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jncentralmensaje));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("CentralMensajeVigente", oCentralMensajeVigente);
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerCentralMensajes");

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

            Log.e("Monitoreo1", response);

        } catch (Exception e) {

            Log.e("Monitoreo2", e.toString());
            e.printStackTrace();
        }

        return response;

    }*/


    public String MtdObtenerConductor(String oConductorId,String oResetear) {

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
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("Resetear", oResetear);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerConductor");

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

            Log.e("MsgMonitoreo41", response);

        } catch (Exception e) {

            Log.e("MsgMonitoreo42", e.toString());
            e.printStackTrace();
        }

        return response;

    }






    public String MtdObtenerPedidoAsignado(String oConductorId) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnpedido));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerPedidoAsignado");

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

            Log.e("MsgMonitoreo45", response);

        } catch (Exception e) {

            Log.e("MsgMonitoreo45", e.toString());
            e.printStackTrace();
        }

        return response;

    }


    public String MtdActualizarOcupado(String oConductorId,String oOcupado) {

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

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("Ocupado", oOcupado);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ActualizarConductorOcupado");

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

            Log.e("MsgMonitoreo45", response);

        } catch (Exception e) {

            Log.e("MsgMonitoreo45", e.toString());
            e.printStackTrace();
        }

        return response;

    }


    /*
OTRAS FUNCIONES Y CLASES
 */

    public static String FncConvertirCobertura(double d){

        double cobertura = redondear( d/1000.00 ,0);

        String ncobertura = cobertura + "km";

        return ncobertura;
    }
    public static double redondear(double d, int decimalPlace) {

        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();

    }

    private float calculateDistance(double fromLong, double fromLat,double toLong, double toLat) {

        LatLng my_latlong = new LatLng((fromLong),(fromLat));
        LatLng frnd_latlong = new LatLng((toLong),(toLat));

        Location l1=new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2=new Location("Two");
        l2.setLatitude(frnd_latlong.latitude);
        l2.setLongitude(frnd_latlong.longitude);

        float distance=l1.distanceTo(l2);

        //if(distance>1000.0f)
        // {
        distance=distance/1000.0f;
        // dist=distance;
        // }
        return distance;

    }


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
            Double speedInMilesPerHour = loc.getSpeed()* 2.2369;
            Double accuracyInFeet = loc.getAccuracy()* 3.28;

            //VehiculoCoordenadaX = Double.toString(latitude);
            //VehiculoCoordenadaY = Double.toString(longitude);

          /*  UbicacionesRepo repo = new UbicacionesRepo(this);
            Ubicaciones ubicaciones = new Ubicaciones();

            ubicaciones.CubId = "";
            ubicaciones.CubCoordenadaX = Double.toString(latitude);;
            ubicaciones.CubCoordenadaY = Double.toString(longitude);;
            ubicaciones.CubVehiculoGPSOrientacion = Integer.toString(direction.intValue());
            ubicaciones.CubVehiculoVelocidad = Integer.toString(speedInMilesPerHour.intValue());
            ubicaciones.CubVehiculoGPSProveedor = loc.getProvider();
            ubicaciones.CubVehiculoGPSExactitud = Integer.toString(accuracyInFeet.intValue());
            ubicaciones.CubTiempoCreacion = StrFechaCompletoHoy;

            ubicaciones.Id = 0;

            repo.insert(ubicaciones);*/



            ConductorUbicacionVehiculoGPSOrientacion = Integer.toString(direction.intValue());
            ConductorUbicacionVehiculoVelocidad = Integer.toString(speedInMilesPerHour.intValue());
            ConductorUbicacionVehiculoGPSProveedor = loc.getProvider();
            ConductorUbicacionVehiculoGPSExactitud = Integer.toString(accuracyInFeet.intValue());

/*
            String jsonStr = "";

            JSONObject conductorUbicacion = new JSONObject();

            try {

                conductorUbicacion.put("ConId",ConductorId);
                conductorUbicacion.put("CubConductorNumeroDocumento",ConductorNumeroDocumento);
                conductorUbicacion.put("CubConductor",ConductorNombre);
                conductorUbicacion.put("CubConductorVehiculoUnidad",VehiculoUnidad);
                conductorUbicacion.put("CubConductorVehiculoPlaca",VehiculoPlaca);
                conductorUbicacion.put("CubSector",SectorNombre);
                conductorUbicacion.put("CubConductorAppVersion",getString(R.string.app_version));

                conductorUbicacion.put("CubCoordenadaX",Double.toString(longitude));
                conductorUbicacion.put("CubCoordenadaY", Double.toString(latitude));
                conductorUbicacion.put("CubVehiculoGPSOrientacion", Integer.toString(direction.intValue()));
                conductorUbicacion.put("CubVehiculoVelocidad", Integer.toString(speedInMilesPerHour.intValue()));
                conductorUbicacion.put("CubVehiculoGPSProveedor", loc.getProvider());
                conductorUbicacion.put("CubVehiculoGPSExactitud", Integer.toString(accuracyInFeet.intValue()));
                conductorUbicacion.put("CubTiempoCreacion", StrFechaCompletoHoy);

                jsonStr = conductorUbicacion.toString();

                Log.e("MsgMonitoreo65",jsonStr);


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            //String envio = "";

           // envio = Double.toString(latitude)+"%%%"+Double.toString(longitude);;

            if(!mWebSocketClientConnected){
                Log.e("MsgMonitoreo65","Sokect no conectado, se intentera reconectar");
                connectWebSocket();
            }

            if(mWebSocketClientConnected){
                try {
                    mWebSocketClient.send(jsonStr);
                }catch(Exception e){
                    Log.e("mWebSocketClient",e.toString());
                }
            }else {
                Log.e("MsgMonitoreo65","No se pudo conectar al socket");
            }
            */





          /*  try {
                JSONObject parent = new JSONObject();
                JSONObject jsonObject = new JSONObject();

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(Double.toString(latitude));
                jsonObject.put("CubCoordenadaX", jsonArray);

                parent.put("k2", jsonObject);

                Log.d("output", parent.toString(2));

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
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
        editor.putString("ConductorNombre", ConductorNombre.trim());
        editor.putString("ConductorEstado", ConductorEstado.trim());
        editor.putString("ConductorNumeroDocumento", ConductorNumeroDocumento.trim());
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
        ConductorCobertura = sharedPreferences.getInt("ConductorCobertura",0);

    }

    private void FncCargarAplicacionParametros() {

        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        AplicacionMonitoreoPedidoContador = sharedPreferences.getInt("AplicacionMonitoreoPedidoContador", 11000);
        AplicacionMonitoreoCoordenadaContador = sharedPreferences.getInt("AplicacionMonitoreoCoordenadaContador", 30000);
        AplicacionMonitoreoCoordenadaPermanenteContador = sharedPreferences.getInt("AplicacionMonitoreoCoordenadaPermanenteContador", 30000);


        AplicacionMonitoreoAlertaContador = sharedPreferences.getInt("AplicacionMonitoreoAlertaContador", 30000);
        AplicacionMonitoreoMensajeContador = sharedPreferences.getInt("AplicacionMonitoreoAlertaContador", 30000);

        AplicacionRadarConductorContador = sharedPreferences.getInt("AplicacionRadarConductorContador", 50000);
        AplicacionRadarCoordenadaContador = sharedPreferences.getInt("AplicacionRadarCoordenadaContador", 10000);

        AplicacionPedidoActualCoordenadaContador = sharedPreferences.getInt("AplicacionPedidoActualCoordenadaContador", 10000);
        AplicacionPedidoActualPedidoContador = sharedPreferences.getInt("AplicacionPedidoActualPedidoContador", 30000);
        AplicacionMonitoreoPedidoAsignadoContador = sharedPreferences.getInt("AplicacionMonitoreoPedidoAsignadoContador", 15000);
        AplicacionMonitoreoSincronizarConductorContador = sharedPreferences.getInt("AplicacionMonitoreoSincronizarConductorContador", 45000);

        AplicacionActualizarCoordenadasPermanente = sharedPreferences.getInt("AplicacionActualizarCoordenadasPermanente", 2);
        AplicacionRestringirIdentificador = sharedPreferences.getInt("AplicacionRestringirIdentificador", 2);
        AplicacionObtenerAsignado = sharedPreferences.getInt("AplicacionObtenerAsignado", 2);
        AplicacionMonitoreoSincronizarConductor = sharedPreferences.getInt("AplicacionMonitoreoSincronizarConductor", 2);

    }


    /**
     *
     * @param oMostrarMensaje
     * @return
     */


    protected Boolean estaConectado(Boolean oMostrarMensaje){
        if(conectadoWifi()){

         //   Log.e("Navegacion60", "Tiene wifi");
            //showAlertDialog(Main.this, "Conexion a Internet",
            //         "Tu Dispositivo tiene Conexion a Wifi.", true);


               /* try {
                    HttpURLConnection urlc = (HttpURLConnection)
                            (new URL("http://clients3.google.com/generate_204")
                                    .openConnection());
                    urlc.setRequestProperty("User-Agent", "Android");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    return (urlc.getResponseCode() == 204 &&
                            urlc.getContentLength() == 0);
                } catch (IOException e) {

                    Log.e("Navegacion60", "Error checking internet connection", e);

                }*/

            //   return false;
            return true;
        }else{

           // Log.e("Navegacion60", "No tiene wifi");

            if(conectadoRedMovil()){

                //Log.e("Navegacion60", "Tiene datos");

               /* try {

                    HttpURLConnection urlc = (HttpURLConnection)
                            (new URL("http://clients3.google.com/generate_204")
                                    .openConnection());
                    urlc.setRequestProperty("User-Agent", "Android");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    return (urlc.getResponseCode() == 204 &&
                            urlc.getContentLength() == 0);

                } catch (IOException e) {

                    Log.e("Navegacion60", "Error checking internet connection", e);

                }*/

                //   showAlertDialog(Main.this, "Conexion a Internet",
                //         "Tu Dispositivo tiene Conexion Movil.", true);
                //return false;
                return true;
            }else{

               // Log.e("Navegacion60", "No tiene datos");
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


    private class CargarImagenCentralMensajeImagen extends AsyncTask<String, Void, Bitmap> {

        //ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

          // pDialog = new ProgressDialog(MonitoreoActivity.this);
           // pDialog.setMessage("Cargando Imagen");
           // pDialog.setCancelable(true);
           // pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           // pDialog.show();

            Log.e("CargarImagen","onPreExecute");
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            String url = params[0];
            url = (url.replace(" ", "%20"));
            Bitmap imagen = descargarImagen(url);

            Log.e("doInBackground",url);
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            imgCentralMensajeImagen.setImageBitmap(result);
            //imgPedidoConductorFoto2.setImageBitmap(result);
            //timConductorFoto2.setImageBitmap(result);

           // pDialog.dismiss();
            Log.e("CargarImagen","onPostExecute");

            ImageView tempImageView = imgCentralMensajeImagen;

            android.app.AlertDialog.Builder imageDialog = new android.app.AlertDialog.Builder(MonitoreoActivity.this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.central_mensaje_imagen,
                    (ViewGroup) findViewById(R.id.layout_root));
            ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
            image.setImageDrawable(tempImageView.getDrawable());
            imageDialog.setView(layout);
            imageDialog.setPositiveButton("Cerrar", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }

            });

            imageDialog.create();
            imageDialog.show();


/*            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);


            double ancho =  (metrics.widthPixels) - (metrics.widthPixels*(0.2));
            double alto =  metrics.heightPixels  - (metrics.heightPixels*(0.4));

            int n_ancho = (int) ancho;
            int n_alto = (int) alto;

            final ImagePopup imagePopup = new ImagePopup(MonitoreoActivity.this);
            imagePopup.setBackgroundColor(Color.BLACK);
            imagePopup.setWindowWidth(n_ancho);
            imagePopup.setWindowHeight(n_alto);
            imagePopup.setHideCloseIcon(true);
            imagePopup.setImageOnClickClose(true);
            imagePopup.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imagePopup.setBaselineAlignBottom(true);

            imagePopup.initiatePopup(imgCentralMensajeImagen.getDrawable());*/

        }


       /* @Override
        protected void on() throws Throwable {
            super.finalize();

            Log.e("CargarImagen","finalize");

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);


            double ancho =  (metrics.widthPixels) - (metrics.widthPixels*(0.2));
            double alto =  metrics.heightPixels  - (metrics.heightPixels*(0.4));

            int n_ancho = (int) ancho;
            int n_alto = (int) alto;

            final ImagePopup imagePopup = new ImagePopup(MonitoreoActivity.this);
            imagePopup.setBackgroundColor(Color.BLACK);
            imagePopup.setWindowWidth(n_ancho);
            imagePopup.setWindowHeight(n_alto);
            imagePopup.setHideCloseIcon(true);
            imagePopup.setImageOnClickClose(true);
            imagePopup.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imagePopup.setBaselineAlignBottom(true);
            imagePopup.initiatePopup(imgCentralMensajeImagen.getDrawable());
        }*/
    }

    private Bitmap descargarImagen (String imageHttpAddress){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return imagen;
    }







    private void connectWebSocket() {
        Log.i("Websocket", "connectWebSocket");

        URI uri;
        try {
            uri = new URI("ws://rt114.ddns.net:9001");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                mWebSocketClientConnected = true;
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // TextView textView = (TextView)findViewById(R.id.messages);
                       // textView.setText(textView.getText() + "\n" + message);
                        Log.e("Websocket",message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
                mWebSocketClientConnected = false;
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
                mWebSocketClientConnected = false;
            }
        };
        mWebSocketClient.connect();
    }

    //public void sendMessage(View view) {
    //   // EditText editText = (EditText)findViewById(R.id.message);
        //mWebSocketClient.send(editText.getText().toString());
       // editText.setText("");
   //     mWebSocketClient.send("lola");
   // }



}
