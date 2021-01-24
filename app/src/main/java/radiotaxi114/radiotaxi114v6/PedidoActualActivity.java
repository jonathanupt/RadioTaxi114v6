package radiotaxi114.radiotaxi114v6;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.Notification;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
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

import javax.net.ssl.HttpsURLConnection;

public class PedidoActualActivity extends AppCompatActivity {

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
 * Variables Pedido
 */
    private String PedidoId = "";


    private String ClienteId = "";
    private String ClienteNombre = "";

    private String PedidoDireccion = "";
    private String PedidoNumero = "";
    private String PedidoDetalle = "";
    private String PedidoCambioDe = "";

    private String PedidoOperadora = "";
    private String PedidoAnexo = "";
    private String PedidoInfo = "";

    private String PedidoFoto = "";
    private String PedidoTipoUnidad = "";

    private String PedidoReferencia = "";
    private String PedidoCoordenadaX = "0.00";
    private String PedidoCoordenadaY = "0.00";
    private String PedidoTipo = "";

    private String ClienteCelular = "";
    private String ClienteTelefono = "";
    private String ClienteFoto = "";

    private String PedidoTiempo;
    private String PedidoDistancia;
    /*
    * Variables Principales
    */
    private String ConductorId = "";

    private String VehiculoUnidad = "";
    private String VehiculoPlaca = "";
    private String ConductorNombre = "";
    private String ConductorEstado = "";
    private String ConductorNumeroDocumento = "";

    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;
    /*
    * Variables Secundarias
    */
    private String VehiculoCoordenadaX = "0.00";
    private String VehiculoCoordenadaY = "0.00";
    private int VehiculoOrientacion = 0;
    public String SectorNombre = "";
    //private String Sector;

    /*
    * Otras Variables
    */
    private String Identificador;

    /*
    * DATOS GOOGLE MAP
     */
    private GoogleMap googleMap = null;
    private MapFragment  googleMapMF = null;

    private Marker pedidoMarker;
    private Marker vehiculoMarker;
    private float currentZoom;
    private int MapaVistaTipo;

    /*
    * DATOS TIMER
     */
    Timer timerPedidoActual1;
    Timer timerPedidoActual2;
    Timer timerPedidoActual3;
    Timer timerPedidoActual4;
    Timer timerPedidoActual5;


    private boolean PedidoActualObtenerPedidoContinuar = true;
    private boolean PedidoActualEnviarCoordenadaContinuar = true;

    /**
     * PARAMETRO APLICACION
     */
    private int AplicacionMonitoreoPedidoContador;
    private int AplicacionMonitoreoCoordenadaContador;
    private int AplicacionMonitoreoAlertaContador;
    private int AplicacionMonitoreoMensajeContador;

    private int AplicacionRadarConductorContador;
    private int AplicacionRadarCoordenadaContador;

    private int AplicacionPedidoActualCoordenadaContador;
    private int AplicacionPedidoActualPedidoContador;

    /**
     * VARIABLES GPS
     */
    final String gpsLocationProvider = LocationManager.GPS_PROVIDER;
    final String networkLocationProvider = LocationManager.NETWORK_PROVIDER;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    /*
    VARIABLES FORMULARIO
    */
    private Boolean  valMonitoreoSonido;
    private Boolean  ConductorTienePedido;




    /*
PERMISOS
*/
    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;



    TextView txtPedidoTiempo;
    TextView txtPedidoDistancia;
    //TxtClienteTelefono.setText(ClienteTelefono);


    CountDownTimer countTimerCancelar;


    /*
    * SONIDOS
     */

    /*
    MediaPlayer mediaPlayerError;
    MediaPlayer mediaPlayerMensajeCentral;
    MediaPlayer mediaPlayerMensaje;
    MediaPlayer mediaPlayerBocina;


    MediaPlayer mediaPlayerAlerta;
    */

    /*
    * PUBLICIDAD
     */

    ImageView imgPublicidadArchivo;
    ImageView imgPublicidadArchivo2;

/*
* FORMULARIOS
 */
    TextView txtClienteNombre;
    TextView txtPedidoDireccion;
    TextView txtPedidoReferencia;
    TextView txtPedidoDetalle;
    TextView txtPedidoCambioDe;
    TextView txtClienteContacto;
    TextView txtPedidoTipoUnidad;


    ImageButton btnCancelar;


    /*
    * CAPAS
     */

    LinearLayout capContenidos;
    LinearLayout capBotones;

    /*
    * FLAGS
     */

    private Integer AmpliarMapa;
    private Integer OcultarMapa;


    ImageView imgPedidoFoto;

    //ImagePopup imagePopup3;


    /**
     * MENSAJES
     */

    ImageView imgCentralMensajeImagen;


    private MediaPlayer mediaPlayer = null;


    private Boolean MonitoreoSonido;

/**
* FLAGS
 */
    Boolean Cancelar30;
    Boolean TieneCoordenadas;


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

    private int soundIdError;
    private int soundIdMensaje;
    private int soundIdMensajeCentral;
    private int soundIdBocina;
    private int soundIdAlerta;

    private int soundIdPedidoOperativo;
    private int soundIdPedidoRobo;
    private int soundIdPPedidoAccidente;
    private int soundIdPedidoClienteLibre;



    private float volume;

    LinearLayout capPublicidad;

    /*
     * CONDUCTOR ALERTA
     */
    private String ConductorAlertaCoordenadaX = "";
    private String ConductorAlertaCoordenadaY = "";


    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("PedidoActual20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("PedidoActual20", "Resume");

        FncCargarAplicacionParametros();

        //SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

       //MonitoreoSonido = sharedPreferences.getBoolean("MonitoreoSonido", true);

    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("PedidoActual20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("PedidoActual20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("PedidoActual20", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("PedidoActual20", "Destroy");

        if(timerPedidoActual1 != null){
            timerPedidoActual1.cancel();
        }

        if(timerPedidoActual2!=null){
            timerPedidoActual2.cancel();
        }

        if(timerPedidoActual3!=null){
            timerPedidoActual3.cancel();
        }

        if(timerPedidoActual4!=null){
            timerPedidoActual4.cancel();
        }


        if(timerPedidoActual5!=null){
            timerPedidoActual5.cancel();
        }


     /*   if(mediaPlayerError!=null) {
            mediaPlayerError.stop();
        }
        if(mediaPlayerMensaje!=null) {
            mediaPlayerMensaje.stop();
        }
        if(mediaPlayerMensajeCentral!=null) {
            mediaPlayerMensajeCentral.stop();
        }

        if(mediaPlayerBocina!=null) {
            mediaPlayerBocina.stop();
        }

        if(mediaPlayerAlerta!=null) {
            mediaPlayerAlerta.stop();
        }
*/

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("PedidoActual20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("PedidoActual20", "RestoreInstance");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_actual_activity);
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

        Log.e("PedidoActual20", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        //actionBar.setDisplayShowHomeEnabled(true);
        // actionBar.setIcon(R.mipmap.ic_launcher);
        // actionBar.setTitle(getString(R.string.app_title));
        //  actionBar.setSubtitle("Pedido Actual");
        //   actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_pedido_actual));


        FncCargarAplicacionParametros();


        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");

        Cancelar30 = sharedPreferences2.getBoolean("Cancelar30", true);

        //VARIABLES
        valMonitoreoSonido = sharedPreferences2.getBoolean("MonitoreoSonido", true);
        //MonitoreoSonido = sharedPreferences.getBoolean("MonitoreoSonido", true);


        //COORDENADAS
        //VehiculoCoordenadaX = sharedPreferences2.getString("VehiculoCoordenadaX", "0.00");
       // VehiculoCoordenadaY = sharedPreferences2.getString("VehiculoCoordenadaY", "0.00");

       // imgPublicidadArchivo2 = (ImageView)  findViewById(R.id.ImgPublicidadArchivo2);

        txtPedidoTiempo = (TextView)  findViewById(R.id.CmpPedidoTiempo);
        txtPedidoDistancia = (TextView)  findViewById(R.id.CmpPedidoDistancia);



        //PedidoActualEnviarCoordenadaContinuar = true;
        //PedidoActualObtenerPedidoContinuar = true;

        //IDENTIFICANDO EQUIPO
        //final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        //final String tmDevice, tmSerial, androidId; tmDevice = "" + tm.getDeviceId();
        //tmSerial = "" + tm.getSimSerialNumber();
        //androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        //UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        //Identificador = deviceUuid.toString();

        //VALIDANDO INTERNET
        //if (!AppStatus.getInstance(this).isOnline()) {

        //    FncMostrarToast(getString(R.string.message_no_internet));
        //     finish();
        //    System.exit(0);

        // }

        //RECUPERANDO VARIABLES
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        PedidoId = intentExtras.getStringExtra("PedidoId");



        ClienteId = intentExtras.getStringExtra("ClienteId");
                ClienteNombre = intentExtras.getStringExtra("ClienteNombre");

        PedidoDireccion = intentExtras.getStringExtra("PedidoDireccion");
        PedidoNumero = intentExtras.getStringExtra("PedidoNumero");
        PedidoReferencia = intentExtras.getStringExtra("PedidoReferencia");
        PedidoDetalle = intentExtras.getStringExtra("PedidoDetalle");
        PedidoCambioDe = intentExtras.getStringExtra("PedidoCambioDe");

        PedidoOperadora = intentExtras.getStringExtra("PedidoOperadora");
        PedidoAnexo = intentExtras.getStringExtra("PedidoAnexo");
        PedidoInfo = intentExtras.getStringExtra("PedidoInfo");

        PedidoFoto = intentExtras.getStringExtra("PedidoFoto");
        PedidoTipoUnidad = intentExtras.getStringExtra("PedidoTipoUnidad");

        PedidoCoordenadaX = intentExtras.getStringExtra("PedidoCoordenadaX");
        PedidoCoordenadaY = intentExtras.getStringExtra("PedidoCoordenadaY");
        PedidoTipo = intentExtras.getStringExtra("PedidoTipo");

        VehiculoCoordenadaX = intentExtras.getStringExtra("VehiculoCoordenadaX");
        VehiculoCoordenadaY = intentExtras.getStringExtra("VehiculoCoordenadaY");

        ClienteCelular = intentExtras.getStringExtra("ClienteCelular");
        ClienteTelefono = intentExtras.getStringExtra("ClienteTelefono");

        PedidoTiempo = intentExtras.getStringExtra("PedidoTiempo");
        PedidoDistancia = intentExtras.getStringExtra("PedidoDistancia");

        //VARIABLES INICIALES
        currentZoom = 19;
        //Cancelar30 = true;
        TieneCoordenadas = false;

//CAPAS
        LinearLayout capReferencia = (LinearLayout) findViewById(R.id.CapPedidoActualReferencia);
        LinearLayout capDetalle = (LinearLayout) findViewById(R.id.CapPedidoActualDetalle);
        LinearLayout capCambioDe = (LinearLayout) findViewById(R.id.CapPedidoActualCambioDe);
        LinearLayout capTipoUnidad = (LinearLayout) findViewById(R.id.CapPedidoActualTipoUnidad);

        LinearLayout capBotonVista = (LinearLayout) findViewById(R.id.CapPedidoActualBotonCamara);
        LinearLayout capBotonAlertar = (LinearLayout) findViewById(R.id.CapPedidoActualBotonAlertar);
        LinearLayout capBotonUbicar = (LinearLayout) findViewById(R.id.CapPedidoActualBotonUbicar);
        LinearLayout capPedidoActualMensajear = (LinearLayout) findViewById(R.id.CapPedidoActualMensajear);

        LinearLayout capTiempoDistancia = (LinearLayout) findViewById(R.id.CapTiempoDistancia);
        LinearLayout capPedidoTiempo = (LinearLayout) findViewById(R.id.CapPedidoActualTiempo);
        LinearLayout capPedidoDistancia = (LinearLayout) findViewById(R.id.CapPedidoActualDistancia);

        capPublicidad = findViewById(R.id.CapPedidoActualPublicidad);

        capContenidos = (LinearLayout) findViewById(R.id.CapPedidoActualContenidos);
        capBotones = (LinearLayout) findViewById(R.id.CapPedidoActualBotones);

        if(!PedidoReferencia.equals("") && !PedidoReferencia.equals("-") && PedidoReferencia != null && !PedidoReferencia.equals("null") ){
            capReferencia.setVisibility(View.VISIBLE);
        }else{
            capReferencia.setVisibility(View.GONE);
        }

        if(!PedidoDetalle.equals("") && !PedidoDetalle.equals("-") && PedidoDetalle != null && !PedidoDetalle.equals("null") ){
            capDetalle.setVisibility(View.VISIBLE);
        }else{
            capDetalle.setVisibility(View.GONE);
        }

        if(!PedidoCambioDe.equals("") && !PedidoCambioDe.equals("-") && PedidoCambioDe != null && !PedidoCambioDe.equals("null") ){
            capCambioDe.setVisibility(View.VISIBLE);
        }else{
            capCambioDe.setVisibility(View.GONE);
        }

        if(!PedidoTipoUnidad.equals("") && !PedidoTipoUnidad.equals("-") && PedidoTipoUnidad != null && !PedidoTipoUnidad.equals("null") ){
            capTipoUnidad.setVisibility(View.VISIBLE);
        }else{
            capTipoUnidad.setVisibility(View.GONE);
        }

        if(!PedidoTiempo.equals("") && !PedidoTiempo.equals("-") && PedidoTiempo != null && !PedidoTiempo.equals("null") && !PedidoTiempo.equals("0.00") && !PedidoTiempo.equals("0")  ){
            capPedidoTiempo.setVisibility(View.VISIBLE);
        }else{
            capPedidoTiempo.setVisibility(View.GONE);
        }

        if(!PedidoDistancia.equals("") && !PedidoDistancia.equals("-") && PedidoDistancia != null && !PedidoDistancia.equals("null") && !PedidoDistancia.equals("0.00") && !PedidoDistancia.equals("0") ){
            capPedidoDistancia.setVisibility(View.VISIBLE);
        }else{
            capPedidoDistancia.setVisibility(View.GONE);
        }

        if(PedidoTipo.equals("1")) {

            capBotonVista.setVisibility(View.GONE);
            capBotonAlertar.setVisibility(View.GONE);
            capBotonUbicar.setVisibility(View.GONE);
            capPedidoActualMensajear.setVisibility(View.GONE);

        }else if(PedidoTipo.equals("7") || PedidoTipo.equals("9") || PedidoTipo.equals("10") || PedidoTipo.equals("13"))  {

            capBotonVista.setVisibility(View.GONE);
            capBotonAlertar.setVisibility(View.GONE);
            capBotonUbicar.setVisibility(View.GONE);
            capPedidoActualMensajear.setVisibility(View.GONE);

        }else if(PedidoTipo.equals("6") )  {

            capBotonVista.setVisibility(View.VISIBLE);
            capBotonAlertar.setVisibility(View.VISIBLE);
            capBotonUbicar.setVisibility(View.VISIBLE);
            capPedidoActualMensajear.setVisibility(View.VISIBLE);

        }else if(PedidoTipo.equals("11") )  {

            capBotonVista.setVisibility(View.GONE);
            capBotonAlertar.setVisibility(View.GONE);
            capBotonUbicar.setVisibility(View.GONE);
            capPedidoActualMensajear.setVisibility(View.GONE);
        }else{

            capBotonVista.setVisibility(View.GONE);
            capBotonAlertar.setVisibility(View.GONE);
            capBotonUbicar.setVisibility(View.GONE);
            capBotonUbicar.setVisibility(View.GONE);
        }

        /*
        PERMISOS
        */
        context = getApplicationContext();
        activity = this;

        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        //VARIABLES INICIALES



        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00")) {

            Log.e("TieneCoordenadas:","SI");
            if (!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null && VehiculoCoordenadaY != null) {
                MapaVistaTipo = 2;
            }else {
                MapaVistaTipo = 1;
            }

            TieneCoordenadas = true;

            OcultarMapa = 2;
            AmpliarMapa = 2;

         //   menuOcultarMapa.setVisible(false);
          //  menuMostrarMapa.setVisible(true);

         //   menuAmpliarMapa.setVisible(true);
         //   menuReducirMapa.setVisible(false);


        }else {
            Log.e("TieneCoordenadas:","NO");
            OcultarMapa = 2;
            AmpliarMapa = 2;

           // menuOcultarMapa.setVisible(true);
        //    menuMostrarMapa.setVisible(false);

         //   menuAmpliarMapa.setVisible(true);
         //   menuReducirMapa.setVisible(false);



            if (!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null && VehiculoCoordenadaY != null) {

                MapaVistaTipo = 3;

            }else{


            }

        }


        //MapaVistaTipo = 2;
        

        Log.e("MapaVistaTipo",String.valueOf(MapaVistaTipo));

        //AmpliarMapa = 2;
       // OcultarMapa= 2;

        //OBTENER COORDENADAS
        if(checkPermission(2)){
            MtdObtenerCoordenadas();
        }

        //OBTENIENDO UBICACION
        if(checkPermission(1)){
            createMapView();
        }

        //FORMULARIOS
        txtClienteNombre = (TextView) findViewById(R.id.CmpClienteNombre);
        txtPedidoDireccion = (TextView) findViewById(R.id.CmpSoporteContactoCelular);
        txtPedidoReferencia = (TextView) findViewById(R.id.CmpPedidoReferencia);
        txtPedidoDetalle = (TextView) findViewById(R.id.CmpPedidoDetalle);
        txtPedidoCambioDe = (TextView) findViewById(R.id.CmpPedidoActualCambioDe);
        txtClienteContacto = (TextView) findViewById(R.id.CmpClienteContacto);
        txtPedidoTipoUnidad = (TextView) findViewById(R.id.CmpPedidoTipoUnidad);

        imgPedidoFoto = (ImageView)  findViewById(R.id.ImgPedidoFoto);
        imgPublicidadArchivo2 = (ImageView)  findViewById(R.id.ImgPublicidadArchivo2);
        imgCentralMensajeImagen = (ImageView)  findViewById(R.id.ImgCentralMensajeImagen);



        btnCancelar = (ImageButton) findViewById(R.id.BtnPedidoActualCancelar);



        //MOSTRANDO DATOS
        txtClienteNombre.setText(ClienteNombre);
        txtPedidoDireccion.setText(PedidoDireccion + " " + PedidoNumero);
        txtPedidoReferencia.setText(PedidoReferencia);
        txtPedidoDetalle.setText(PedidoDetalle);
        txtPedidoCambioDe.setText(PedidoCambioDe);
        txtClienteContacto.setText(ClienteCelular + "/" + ClienteTelefono);
        txtPedidoTipoUnidad.setText(PedidoTipoUnidad);

        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX != null  && PedidoCoordenadaY != null) {

            if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null) {

                Double DblPedidoDistancia = redondear(calculateDistance(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY),Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)),2);
                Double DblPedidoTiempo = 0.00;

                DblPedidoTiempo = redondear((((DblPedidoDistancia)/50)*60),2);

                String Distancia = DblPedidoDistancia.toString()+" km";
                String Tiempo = DblPedidoTiempo.toString() + " min";

                txtPedidoDistancia.setText(Distancia);
                txtPedidoTiempo.setText(Tiempo);

            }

        }


        if(Cancelar30){

            countTimerCancelar = new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {



                }

                public void onFinish() {

                    //TextView TxtTiempoTranscurrido = (TextView) findViewById(R.id.CmpTiempoTranscurrido);

                    btnCancelar.setBackgroundResource(R.drawable.dra_boton_fondo2);

                    Cancelar30 = false;

                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    editor.putBoolean("Cancelar30", false);
                    editor.apply();


                }

            }.start();

        }else{

            btnCancelar.setBackgroundResource(R.drawable.dra_boton_fondo2);

        }




        String Mensaje = "";
        String MensajeCorto = "";

        if(!ClienteNombre.equals("")){
            Mensaje += "Cliente: "+ClienteNombre;
        }

        if(!PedidoDireccion.equals("")){
            Mensaje += " Dirección: "+PedidoDireccion;
            MensajeCorto += " Dirección: "+PedidoDireccion;
        }

        if(!PedidoNumero.equals("")){
            Mensaje += " Num.: "+PedidoNumero;
            MensajeCorto += " Num.: "+PedidoNumero;
        }

        if(!PedidoReferencia.equals("")){
            Mensaje += " Ref.: "+PedidoReferencia;
            MensajeCorto += " Ref.: "+PedidoReferencia;
        }

        if(!PedidoDetalle.equals("")){
            Mensaje += " Det.: "+PedidoDetalle;
        }

        if(!ClienteCelular.equals("")){
            Mensaje += " Cel.: "+ClienteCelular;
        }

        if(!ClienteCelular.equals("")){
            Mensaje += " Tel.: "+ClienteTelefono;
        }

        displayNotificationOne(getString(R.string.alert_title) + " - Pedido Actual", Mensaje, MensajeCorto);



        if(!PedidoFoto.equals("")){

            Picasso.with(context).load(PedidoFoto).into(imgPedidoFoto);

        }

        /*
        if(!PedidoFoto.equals("")){
            //ImageView imageView = (ImageView)  findViewById(R.id.imageView4);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new DownloadImageTask((ImageView) findViewById(R.id.imageView4))
                            .execute(PedidoFoto);
                }
            });
        }
        */

        // Retrieve and cache the system's default "short" animation time.
       /* mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);*/



        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX != null  && PedidoCoordenadaY != null){

            ImageButton btnPedidoActualAlertar = (ImageButton)findViewById(R.id.BtnPedidoActualAlertar);
            btnPedidoActualAlertar.setBackgroundResource(R.drawable.dra_boton_fondo);

            ImageButton btnPedidoActualUbicar = (ImageButton)findViewById(R.id.BtnPedidoActualUbicar);
            btnPedidoActualUbicar.setBackgroundResource(R.drawable.dra_boton_fondo);


            ImageButton btnPedidoActualMensajear = (ImageButton)findViewById(R.id.BtnPedidoActualMensajear);
            btnPedidoActualMensajear.setBackgroundResource(R.drawable.dra_boton_fondo);

        }else {

            ImageButton btnPedidoActualAlertar2 = (ImageButton)findViewById(R.id.BtnPedidoActualAlertar);
            btnPedidoActualAlertar2.setBackgroundResource(R.drawable.dra_boton_fondo2);

            ImageButton btnPedidoActualUbicar2 = (ImageButton)findViewById(R.id.BtnPedidoActualUbicar);
            btnPedidoActualUbicar2.setBackgroundResource(R.drawable.dra_boton_fondo2);

            ImageButton btnPedidoActualMensajear2 = (ImageButton)findViewById(R.id.BtnPedidoActualMensajear);
            btnPedidoActualMensajear2.setBackgroundResource(R.drawable.dra_boton_fondo2);

        }

        if(!PedidoFoto.equals("") ){

            ImageButton BtnPedidoActualCamara = (ImageButton)findViewById(R.id.BtnPedidoActualCamara);
            BtnPedidoActualCamara.setBackgroundResource(R.drawable.dra_boton_fondo3);

        }else {

            ImageButton BtnPedidoActualCamara2 = (ImageButton)findViewById(R.id.BtnPedidoActualCamara);
            BtnPedidoActualCamara2.setBackgroundResource(R.drawable.dra_boton_fondo2);

        }


      /*  if(mediaPlayerError!=null) {
            mediaPlayerError.release();
        }
        if(mediaPlayerMensaje!=null) {
            mediaPlayerMensaje.release();
        }

        if(mediaPlayerMensajeCentral!=null) {
            mediaPlayerMensajeCentral.release();
        }

        if(mediaPlayerBocina!=null) {
            mediaPlayerBocina.release();
        }

        if(mediaPlayerAlerta!=null) {
            mediaPlayerAlerta.release();
        }
*/

        //mediaPlayerError = MediaPlayer.create(context, R.raw.alerta);
        //mediaPlayerMensaje = MediaPlayer.create(context, R.raw.sou_mensaje);
       // mediaPlayerMensajeCentral = MediaPlayer.create(context, R.raw.aud_mensaje);
        //mediaPlayerBocina = MediaPlayer.create(context, R.raw.taxi2);
       // mediaPlayerAlerta = MediaPlayer.create(context, R.raw.sou_enviar_alerta);



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

        soundIdError = soundPool.load(this, R.raw.alerta,1);
        soundIdMensaje = soundPool.load(this, R.raw.sou_mensaje,1);
        soundIdMensajeCentral = soundPool.load(this, R.raw.aud_mensaje,1);
        soundIdBocina = soundPool.load(this, R.raw.taxi2,1);
        soundIdAlerta = soundPool.load(this, R.raw.sou_enviar_alerta,1);

        soundIdPedidoOperativo= soundPool.load(this, R.raw.operativo,1);
        soundIdPedidoRobo = soundPool.load(this, R.raw.sou_robo,1);
        soundIdPPedidoAccidente = soundPool.load(this, R.raw.sou_choque,1);
        soundIdPedidoClienteLibre = soundPool.load(this, R.raw.sou_cliente_libre,1);







/*
        mediaPlayerError.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mediaPlayerMensaje.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
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
        mediaPlayerBocina.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
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
        });
        */

        //PUBLICIDAD ABAJO
        //DisplayMetrics metrics2 = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics2);

        //double ancho2 =  (metrics2.widthPixels) - (metrics2.widthPixels*(0.2));
        //double alto2 =  metrics2.heightPixels  - (metrics2.heightPixels*(0.6));

        //int n_ancho2 = (int) ancho2;
        //int n_alto2 = (int) alto2;

        //final ImagePopup imagePopup2 = new ImagePopup(PedidoActualActivity.this);
        //imagePopup2.setBackgroundColor(Color.TRANSPARENT);
        //imagePopup2.setWindowWidth(n_ancho2);
        //imagePopup2.setWindowHeight(n_alto2);
        //imagePopup2.setHideCloseIcon(true);
        //imagePopup2.setImageOnClickClose(true);
        //imagePopup2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imagePopup2.setBaselineAlignBottom(true);

      /*  imgPublicidadArchivo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgPublicidadArchivo2.getDrawable()!=null){
                    //imagePopup2.initiatePopup(imgPublicidadArchivo2.getDrawable());

                    android.app.AlertDialog.Builder imageDialog = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                    View layout = inflater.inflate(R.layout.conductor_foto,
                            (ViewGroup) findViewById(R.id.layout_root));
                    ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
                    // image.setMaxHeight(300);
                    // image.setLa
                    image.setImageDrawable(imgPublicidadArchivo2.getDrawable());
                    imageDialog.setView(layout);



                    imageDialog.create();
                    imageDialog.show();
                }
            }
        });
*/



        //PEDIDO FOTO
        //DisplayMetrics metrics3 = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics2);

        //double ancho3 =  (metrics3.widthPixels) - (metrics3.widthPixels*(0.2));
        //double alto3 =  metrics3.heightPixels  - (metrics3.heightPixels*(0.6));

        //int n_ancho3 = (int) ancho3;
        //int n_alto3 = (int) alto3;

        //imagePopup3 = new ImagePopup(PedidoActualActivity.this);
        //imagePopup3.setBackgroundColor(Color.TRANSPARENT);
        //imagePopup3.setWindowWidth(n_ancho2);
        //imagePopup3.setWindowHeight(n_alto2);
        //imagePopup3.setHideCloseIcon(true);
        //imagePopup3.setImageOnClickClose(true);
        //imagePopup3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imagePopup3.setBaselineAlignBottom(true);


        /*imgPedidoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgPublicidadArchivo2.getDrawable()!=null){
                    imagePopup2.initiatePopup(imgPublicidadArchivo2.getDrawable());
                }
            }
        });*/


        //CARGAR PUBLICIDAD - INICIO


        Thread ntPublicidadAbajo = new Thread() {
            @Override
            public void run() {

                try {
                    final String resObtenerPublicidadUltimo;

                    resObtenerPublicidadUltimo = MtdObtenerPublicidadUltimo("ABAJO");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //PrgConductorDato.cancel();

                            String JsRespuesta = "";

                            String JsPublicidadId = "";
                            String JsPublicidadNombre = "";
                            String JsPublicidadDescripcion = "";
                            String JsPublicidadArchivo = "";

                            try {

                                JSONObject jsonObject = new JSONObject(resObtenerPublicidadUltimo);
                                JsRespuesta = jsonObject.getString("Respuesta");

                                JsPublicidadId = jsonObject.getString("PublicidadId");
                                JsPublicidadNombre = jsonObject.getString("PublicidadNombre");
                                JsPublicidadDescripcion = jsonObject.getString("PublicidadDescripcion");
                                JsPublicidadArchivo = jsonObject.getString("PublicidadArchivo");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            switch(JsRespuesta){
                                case "U003":

                                    if(!JsPublicidadArchivo.equals("")){

                                       Picasso.with(context).load(JsPublicidadArchivo).into(imgPublicidadArchivo2);



                                        final ImagePopup imagePopupPublicidadArchivo2 = new ImagePopup(PedidoActualActivity.this);
                                        //imagePopup.setWindowHeight(100); // Optional
                                        //imagePopup.setWindowWidth(100); // Optional
                                        imagePopupPublicidadArchivo2.setBackgroundColor(Color.TRANSPARENT);  // Optional
                                        imagePopupPublicidadArchivo2.setFullScreen(false); // Optional
                                        imagePopupPublicidadArchivo2.setHideCloseIcon(true);  // Optional
                                        imagePopupPublicidadArchivo2.setImageOnClickClose(true);  // Optional

                                        //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                        //imagePopup.initiatePopup(imgPedidoConductorFoto.getDrawable());
                                        imagePopupPublicidadArchivo2.initiatePopupWithPicasso(JsPublicidadArchivo);

                                        imgPublicidadArchivo2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                imagePopupPublicidadArchivo2.viewPopup();
                                                Log.e("EsperandoTaxi","Mostrando Foto");

                                            }
                                        });

                                    }


                                    break;

                                case "U004":
                                    //FncMostrarMensaje("No se encontraron ningun aviso el dia de hoy",false);
                                    break;

                                default:
                                    //FncMostrarMensaje(getString(R.string.message_error_interno),false);
                                    // FncMostrarToast(getString(R.string.message_error_interno));
                                    break;


                            }

                        }
                    });
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        };

        ntPublicidadAbajo.start();

















/*
        final ProgressDialog prgPedidoActual = new ProgressDialog(PedidoActualActivity.this);
        prgPedidoActual.setIcon(R.mipmap.ic_launcher);
        prgPedidoActual.setMessage("Cargando...");
        prgPedidoActual.setCancelable(false);
        prgPedidoActual.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prgPedidoActual.show();

        Thread nt = new Thread() {
            @Override
            public void run() {

                try {

                    final String resMtdObtenerPedido;
                    resMtdObtenerPedido = MtdObtenerPedido(PedidoId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            String JsPedidoEstado = "";
                            String JsPedidoCancelarMotivo = "";
                            String JsVehiculoUnidad = "";

                            try {

                                JSONObject jsonObject = new JSONObject(resMtdObtenerPedido);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsPedidoEstado = jsonObject.getString("PedidoEstado");
                                JsPedidoCancelarMotivo = jsonObject.getString("PedidoCancelarMotivo");
                                JsVehiculoUnidad = jsonObject.getString("VehiculoUnidad");

                            } catch (JSONException e) {
                                //Log.e("MsgPedidoActual", e.toString());
                                e.printStackTrace();
                            }

                            prgPedidoActual.cancel();

                            switch(JsRespuesta){

                                case "P013":

                                    if (JsPedidoEstado.equals("3")) {

                                        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("ConductorTienePedido", false);
                                        editor.apply();

                                        String PedidoActualMensaje1 = "ATENCION: El cliente ha cancelado el pedido.";

                                        if ((JsPedidoCancelarMotivo != null) && !JsPedidoCancelarMotivo.equals("") && !JsPedidoCancelarMotivo.equals("null")) {
                                            PedidoActualMensaje1 += " MOTIVO:" + JsPedidoCancelarMotivo;
                                        }

                                        displayNotificationOne(getString(R.string.app_name), "ATENCION: El cliente ha cancelado el pedido.", PedidoActualMensaje1);

                                        String PedidoActualMensaje2 = "El cliente ha cancelado el pedido.";

                                        if ((JsPedidoCancelarMotivo != null) && !JsPedidoCancelarMotivo.equals("") && !JsPedidoCancelarMotivo.equals("null")) {
                                            PedidoActualMensaje2 += " MOTIVO:" + JsPedidoCancelarMotivo;
                                        }

                                        AlertDialog.Builder MsgPedidoActual1 = new AlertDialog.Builder(PedidoActualActivity.this);
                                        MsgPedidoActual1.setTitle(getString(R.string.alert_title));
                                        MsgPedidoActual1.setMessage(PedidoActualMensaje2);
                                        MsgPedidoActual1.setCancelable(false);
                                        MsgPedidoActual1.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });

                                        // Remember, create doesn't show the dialog
                                        final AlertDialog msgPedidoActual1 = MsgPedidoActual1.create();
                                        msgPedidoActual1.show();

                                        final Timer timer1 = new Timer();
                                        timer1.schedule(new TimerTask() {
                                            public void run() {

                                                // msgPedidoDetalle1.dismiss();
                                                if(msgPedidoActual1!=null && msgPedidoActual1.isShowing()){
                                                    msgPedidoActual1.dismiss();
                                                }

                                                timer1.cancel(); //this will cancel the timer of the system
                                                Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }, 3500); // the timer will count 5 seconds....

                                    }else if(!JsVehiculoUnidad.equals(VehiculoUnidad)){

                                        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putBoolean("ConductorTienePedido", false);
                                        editor.apply();

                                        AlertDialog.Builder MsgPedidoActual1 = new AlertDialog.Builder(PedidoActualActivity.this);
                                        MsgPedidoActual1.setTitle(getString(R.string.alert_title));
                                        MsgPedidoActual1.setMessage("El pedido fue atendido por otra unidad.");
                                        MsgPedidoActual1.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });

                                        // Remember, create doesn't show the dialog
                                        final AlertDialog msgPedidoActual1 = MsgPedidoActual1.create();
                                        msgPedidoActual1.show();

                                        final Timer timer1 = new Timer();
                                        timer1.schedule(new TimerTask() {
                                            public void run() {

                                                // msgPedidoDetalle1.dismiss();
                                                if(msgPedidoActual1!=null && msgPedidoActual1.isShowing()){
                                                    msgPedidoActual1.dismiss();
                                                }

                                                timer1.cancel(); //this will cancel the timer of the system
                                                Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }, 3500); // the timer will count 5 seconds....

                                    }

                                    break;

                                default://NO ES CRITICO
                                    FncMostrarToast(getString(R.string.message_error_interno));
                                    // FncMostrarMensaje(getString(R.string.message_error_interno),false);
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
*/



        if(timerPedidoActual2!=null) {
            timerPedidoActual2.cancel();
        }

        timerPedidoActual2 = new Timer();
        timerPedidoActual2.schedule(new TimerTask() {
            @Override
            public void run() {

                try {

                    final String resMtdActualizarConductorVehiculoCoordenada;
                    resMtdActualizarConductorVehiculoCoordenada = MtdActualizarConductorVehiculoCoordenada(PedidoId, ConductorId, VehiculoCoordenadaX, VehiculoCoordenadaY,VehiculoUnidad,VehiculoPlaca,ConductorNombre,SectorNombre,ConductorNumeroDocumento,VehiculoOrientacion);
//resConductorCoordenada = postEnviarConductorCoordenada(ConductorId, VehiculoCoordenadaX, VehiculoCoordenadaY,VehiculoUnidad,VehiculoPlaca,ConductorNombre);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            String JsSectorNumero = "";
                            String JsSectorNombre = "";

                            try {

                                JSONObject jsonObject = new JSONObject(resMtdActualizarConductorVehiculoCoordenada);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsSectorNumero = jsonObject.getString("SectorNumero");
                                JsSectorNombre = jsonObject.getString("SectorNombre");

                            } catch (JSONException e) {
                                Log.e("MsgPedidoActual7", e.toString());
                                e.printStackTrace();
                            }

                            switch (JsRespuesta) {

                                case "U001":
                                 
                                    PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                                    PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
                                    wakeLock.acquire();

                                    KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                                    KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
                                    keyguardLock.disableKeyguard();

                                    if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX != null  && PedidoCoordenadaY != null) {

                                        if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null) {


                                            try {

                                                Double PedidoDistancia = redondear(calculateDistance(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY),Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)),2);
                                                Double PedidoTiempo = 0.00;

                                                PedidoTiempo = redondear((((PedidoDistancia )/50)*60),2);

                                                String Distancia = PedidoDistancia.toString()+" km";
                                                String Tiempo = PedidoTiempo.toString() + " min";

                                                txtPedidoDistancia.setText(Distancia);
                                                txtPedidoTiempo.setText(Tiempo);


                                            } catch (Exception exception){
                                                // Toast.makeText(getApplicationContext(),
                                                //   exception.toString(),Toast.LENGTH_SHORT).show();
                                                //FncMostrarToast("Ha ocurrido un error cargando el mapa: "+exception.toString());
                                                Log.e("GoogleMapError1",exception.toString());
                                            }


                                        }

                                    }

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
                } catch (final Exception e) {
                    // TODO: handle exception
                    Log.e("MsgPedidoActual8", e.toString());
                }


            }
        }, 1000, AplicacionPedidoActualCoordenadaContador);
        //TAREA ENVIAR COORDENADA PEDIDO



        if(timerPedidoActual1!=null) {
            timerPedidoActual1.cancel();
        }

        timerPedidoActual1 = new Timer();
        timerPedidoActual1.schedule(new TimerTask() {
            @Override
            public void run() {

                // final Timer timer = new Timer();
                //timer.schedule(new TimerTask(){
                //     @Override
                //     public void run() {
                try {

                    Log.e("MsgPedidoActual", "timerPedidoActual1");

                    final String resMtdObtenerPedido;
                    resMtdObtenerPedido = MtdObtenerPedido(PedidoId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            String JsPedidoEstado = "";
                            String JsPedidoCancelarMotivo = "";
                            String JsVehiculoUnidad = "";

                            try {

                                JSONObject jsonObject = new JSONObject(resMtdObtenerPedido);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsPedidoEstado = jsonObject.getString("PedidoEstado");
                                JsPedidoCancelarMotivo = jsonObject.getString("PedidoCancelarMotivo");
                                JsVehiculoUnidad = jsonObject.getString("VehiculoUnidad");

                            } catch (JSONException e) {
                                //Log.e("MsgPedidoActual", e.toString());
                                e.printStackTrace();
                            }

                            switch (JsRespuesta) {

                                case "P013":

                                    if (JsPedidoEstado.equals("3")) {

                                       // if(!mediaPlayerError.isPlaying()){
                                        //    mediaPlayerError.start(); // no need to call prepare(); create() does that for you
                                       // }




                                        if(loaded)  {

                                            // Play sound of gunfire. Returns the ID of the new stream.
                                            soundPool.play(soundIdError,volume, volume, 1, 0, 1f);

                                        }


                                        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("ConductorTienePedido", false);
                                        editor.apply();

                                        String PedidoActualMensaje1 = "ATENCION: El cliente ha cancelado el pedido.";

                                        if ((JsPedidoCancelarMotivo != null) && !JsPedidoCancelarMotivo.equals("") && !JsPedidoCancelarMotivo.equals("null")) {
                                            PedidoActualMensaje1 += " MOTIVO:" + JsPedidoCancelarMotivo;
                                        }

                                        displayNotificationOne(getString(R.string.app_name), "ATENCION: El cliente ha cancelado el pedido.", PedidoActualMensaje1);

                                        String PedidoActualMensaje2 = "El cliente ha cancelado el pedido.";

                                        if ((JsPedidoCancelarMotivo != null) && !JsPedidoCancelarMotivo.equals("") && !JsPedidoCancelarMotivo.equals("null")) {
                                            PedidoActualMensaje2 += " MOTIVO:" + JsPedidoCancelarMotivo;
                                        }


                                        AlertDialog.Builder MsgPedidoActual1 = new AlertDialog.Builder(new ContextThemeWrapper(PedidoActualActivity.this,R.style.AlertDialogDanger));
                                       // AlertDialog.Builder MsgPedidoActual1 = new AlertDialog.Builder(PedidoActualActivity.this);
                                        MsgPedidoActual1.setTitle(getString(R.string.alert_title));
                                        MsgPedidoActual1.setIcon(R.drawable.icon_mensaje110);
                                        MsgPedidoActual1.setMessage(PedidoActualMensaje2);
                                        MsgPedidoActual1.setCancelable(false);
                                        MsgPedidoActual1.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });

                                        // Remember, create doesn't show the dialog
                                        AlertDialog msgPedidoActual1 = MsgPedidoActual1.create();
                                        msgPedidoActual1.show();


                                        if(timerPedidoActual1!=null) {
                                            timerPedidoActual1.cancel();
                                        }

                                    //}else if(!JsVehiculoUnidad.equals(VehiculoUnidad) && !JsVehiculoUnidad.equals("")){
                                    }else if(!JsVehiculoUnidad.equals(VehiculoUnidad) ){



                                       // if(!mediaPlayerError.isPlaying()){
                                        //    mediaPlayerError.start(); // no need to call prepare(); create() does that for you
                                      //  }


                                        if(loaded)  {

                                            soundPool.play(soundIdError,volume, volume, 1, 0, 1f);

                                        }

                                        //SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences2.edit();
                                        editor.putBoolean("ConductorTienePedido", false);
                                        editor.apply();


                                        String Mensaje = "(2) EL pedido ya fue atendido.";

                                        if(null != JsVehiculoUnidad && !JsVehiculoUnidad.equals("")){
                                            //Mensaje += " Por: |"+JsVehiculoUnidad + "| / |"+VehiculoUnidad+"|";
                                            Mensaje += " Por: "+JsVehiculoUnidad;
                                        }

                                        AlertDialog.Builder MsgPedidoActual1 = new AlertDialog.Builder(new ContextThemeWrapper(PedidoActualActivity.this,R.style.AlertDialogDanger));

                                        //AlertDialog.Builder MsgPedidoActual1 = new AlertDialog.Builder(PedidoActualActivity.this);
                                        MsgPedidoActual1.setTitle(getString(R.string.alert_title));
                                        MsgPedidoActual1.setIcon(R.drawable.icon_mensaje110);
                                        MsgPedidoActual1.setCancelable(false);
                                        //MsgPedidoActual1.setMessage("(2) El pedido fue atendido por otra unidad. ("+JsVehiculoUnidad+" - "+VehiculoUnidad+")");
                                        MsgPedidoActual1.setMessage(Mensaje);

                                        MsgPedidoActual1.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });

                                        // Remember, create doesn't show the dialog
                                        AlertDialog msgPedidoActual1 = MsgPedidoActual1.create();
                                        msgPedidoActual1.show();


                                        if(timerPedidoActual1!=null) {
                                            timerPedidoActual1.cancel();
                                        }

                                    }

                                    break;

                                case "P014":
                                    break;

                                default:
                                    break;

                            }


                        }
                    });


                } catch (Exception e) {
                    // TODO: handle exception
                    // Log.e("MsgPedidoCliente", e.toString());
                }


            }
        }, 5000, AplicacionPedidoActualPedidoContador);
        //TAREA OBTENER PEDIDO ESTADO







        if(timerPedidoActual3!=null){
            timerPedidoActual3.cancel();
        }

        //final Timer timer3 = new Timer();
        timerPedidoActual3 = new Timer();
        timerPedidoActual3.schedule(new TimerTask(){
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


                                                            Picasso.with(context).load(JsConductorMensajeImagen).into(imgCentralMensajeImagen);

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

                                                            if(valMonitoreoSonido){

                                                                //MediaPlayer player = MediaPlayer.create(MonitoreoActivity.this, R.raw.taxi4);
                                                                ////player = MediaPlayer.create(MonitoreoActivity.this, R.raw.aud_mensaje);
                                                                //player.start();

                                                                //MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.aud_mensaje);
                                                                //mediaPlayer.start(); // no need to call prepare(); create() does that for you
                                                               // mediaPlayerMensajeCentral.start();
                                                                       /* mediaPlayerMensajeCentral.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                            public void onCompletion(MediaPlayer mp) {
                                                                                mp.release();

                                                                            };
                                                                        });*/

                                                                //if(!mediaPlayerMensajeCentral.isPlaying()){
                                                               //     mediaPlayerMensajeCentral.start(); // no need to call prepare(); create() does that for you
                                                               // }




                                                                if(loaded)  {


                                                                    soundPool.play(soundIdMensajeCentral,volume, volume, 1, 0, 1f);
                                                                }


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

                                                            if(valMonitoreoSonido){

                                                                // MediaPlayer player = MediaPlayer.create(MonitoreoActivity.this, R.raw.taxi4);
                                                                // player = MediaPlayer.create(MonitoreoActivity.this, R.raw.aud_mensaje);
                                                                // player.start();

                                                                //MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.aud_mensaje);
                                                                //mediaPlayer.start(); // no need to call prepare(); create() does that for you



                                                                //if(!mediaPlayerMensajeCentral.isPlaying()){
                                                                //    mediaPlayerMensajeCentral.start(); // no need to call prepare(); create() does that for you
                                                               // }


                                                                if(loaded)  {


                                                                    soundPool.play(soundIdMensajeCentral,volume, volume, 1, 0, 1f);

                                                                }
                                                                       /* mediaPlayerMensajeCentral.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                            public void onCompletion(MediaPlayer mp) {
                                                                                mp.release();

                                                                            };
                                                                        });*/

                                                            }

                                                        }

                                                        displayNotificationOne("Aviso:", JsConductorMensajeDescripcion, JsConductorMensajeDescripcionCorto);

                                                        // FncMostrarMensaje(JsConductorMensajeDescripcion,false);

                                                        android.support.v7.app.AlertDialog.Builder MsgMonitoreo = new android.support.v7.app.AlertDialog.Builder(PedidoActualActivity.this);
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
                                                        android.support.v7.app.AlertDialog msgMonitoreo = MsgMonitoreo.create();

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
                                                                    });

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



/*
        if(timerPedidoActual3!=null){
            timerPedidoActual3.cancel();
        }

        timerPedidoActual3 = new Timer();
        timerPedidoActual3.schedule(new TimerTask(){
            @Override
            public void run() {

                Thread nt = new Thread() {
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




                                                    //if (!isFinishing()) {

                                                    // FncMostrarMensaje(JsCentralMensajeDescripcion,false);

                                                    //  }


                                                    AlertDialog.Builder MsgPedidoActual = new AlertDialog.Builder(PedidoActualActivity.this);
                                                    MsgPedidoActual.setTitle(getString(R.string.alert_title));
                                                    MsgPedidoActual.setMessage(JsCentralMensajeDescripcion);
                                                    MsgPedidoActual.setCancelable(false);
                                                    MsgPedidoActual.setPositiveButton("Aceptar",
                                                            new DialogInterface.OnClickListener() {

                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    // Do nothing but close the dialog

                                                                }
                                                            });

                                                    // Remember, create doesn't show the dialog
                                                    AlertDialog msgPedidoActual = MsgPedidoActual.create();

                                                    if(!msgPedidoActual.isShowing()){
                                                        msgPedidoActual.show();
                                                    }

                                                    if(valMonitoreoSonido){

                                                        //MediaPlayer player = MediaPlayer.create(PedidoActualActivity.this, R.raw.aud_mensaje);
                                                        //player = MediaPlayer.create(PedidoActualActivity.this, R.raw.aud_mensaje);
                                                       // player.start();

                                                        mediaPlayerMensajeCentral.start();

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
                nt.start();


            }
            //}, 1000, AplicacionMonitoreoAlertaContador);
        }, 1000, AplicacionMonitoreoMensajeContador);
        */









        if(timerPedidoActual4!=null){
            timerPedidoActual4.cancel();
        }

        //TAREA VERIFICAR MENSAJE

        timerPedidoActual4 = new Timer();
        timerPedidoActual4.schedule(new TimerTask(){
            @Override
            public void run() {

                try {

                    final String resVerificarPedidoMensajeConductor;
                    resVerificarPedidoMensajeConductor = MtdVerificarPedidoMensajeConductor(PedidoId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            String JsPedidoMensajeConductor = "";
                            try {

                                JSONObject jsonObject = new JSONObject(resVerificarPedidoMensajeConductor);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsPedidoMensajeConductor = jsonObject.getString("PedidoMensajeConductor");

                            } catch (JSONException e) {
                                Log.e("PedidoActual101", e.toString());
                                e.printStackTrace();
                            }

                            switch(JsRespuesta){

                                case "C103":

                                    //FncMostrarMensaje(JsPedidoMensajeConductor,false);


                                    AlertDialog.Builder MsgEsperandoTaxi = new AlertDialog.Builder(PedidoActualActivity.this);
                                    MsgEsperandoTaxi.setTitle(getString(R.string.app_titulo));
                                    MsgEsperandoTaxi.setIcon(R.mipmap.icon_logo75);
                                    MsgEsperandoTaxi.setMessage(JsPedidoMensajeConductor);
                                    MsgEsperandoTaxi.setCancelable(false);
                                    MsgEsperandoTaxi.setPositiveButton("Aceptar",
                                            new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Do nothing but close the dialog


                                                }
                                            });

                                    // Remember, create doesn't show the dialog
                                    final AlertDialog msgEsperandoTaxi = MsgEsperandoTaxi.create();
                                    msgEsperandoTaxi.show();

                                    //FncMostrarToast("¡Alerta recibida!");

                                    if(valMonitoreoSonido){

                                        //HABILITAR AUDIO MAS ADELANTE
                                        //MediaPlayer player = MediaPlayer.create(PedidoActualActivity.this, R.raw.sou_mensaje);
                                        //player = MediaPlayer.create(PedidoActualActivity.this, R.raw.sou_mensaje);
                                        //player.start();

                                        //if(!mediaPlayerMensaje.isPlaying()){
                                        //    mediaPlayerMensaje.start(); // no need to call prepare(); create() does that for you
                                       // }


                                        if(loaded)  {

                                            soundPool.play(soundIdMensaje,volume, volume, 1, 0, 1f);

                                        }

                                    }

                                    break;

                                case "C104":
                                    break;

                                case "C105":
                                    break;

                                case "C106":
                                    break;

                                default:
                                    break;
                            }

                        }
                    });

                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("PedidoActual101", e.toString());
                }

            }
        }, 1000, 6000);
        //TAREA VERIFICAR MENSAJE





        //TAREA OBTENER  ALERTAS
        if(timerPedidoActual5!=null){
            timerPedidoActual5.cancel();
        }

        //final Timer timer2 = new Timer();
        timerPedidoActual5 = new Timer();
        timerPedidoActual5.schedule(new TimerTask(){
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


                case 4:

                    int result4 = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE);

                    if (result4 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","3AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE}, permiso);
                        Log.e("Navegacion10", "3BBB");
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

                case 4:
                    FncLlamar();
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

            PedidoActualListener mlocListener = new PedidoActualListener();
            mlocListener.setMainActivity(this);
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

        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {

            if(null == googleMap){

                //googleMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map1)).getMap();
                googleMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map1)).getMap();
                googleMapMF= ((MapFragment) getFragmentManager().findFragmentById(R.id.map1));


                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setCompassEnabled(false);

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

                                    if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                                        if(vehiculoMarker!=null){
                                            vehiculoMarker.remove();
                                        }

                                        vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                                .title("¡Aquì estoy!")
                                                .draggable(false)
                                                .rotation(VehiculoOrientacion)
                                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo_yo100)));

                                    }


                                    Thread nt = new Thread() {
                                        @Override
                                        public void run() {

                                            if(estaConectado(false)){

                                                try {

                                                    final String resConductorCoordenada;
                                                    resConductorCoordenada = MtdActualizarConductorVehiculoCoordenada(PedidoId,ConductorId,VehiculoCoordenadaX,VehiculoCoordenadaY,VehiculoUnidad,VehiculoPlaca,ConductorNombre,SectorNombre,ConductorNumeroDocumento,VehiculoOrientacion);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            String JsRespuesta = "";

                                                            try {

                                                                JSONObject jsonObject = new JSONObject(resConductorCoordenada);
                                                                JsRespuesta = jsonObject.getString("Respuesta");

                                                            } catch (JSONException e) {
                                                                // Log.e("MsgMonitoreo7", e.toString());
                                                                e.printStackTrace();
                                                            }

                                                            switch(JsRespuesta){

                                                                case "U001":
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
                                                } catch (final Exception e) {
                                                    // TODO: handle exception
                                                    // Log.e("MsgMonitoreo11", e.toString());
                                                }

                                            }



                                        }

                                    };
                                    nt.start();


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

                        if(location!=null) {

                            VehiculoCoordenadaX = Double.toString(location.getLatitude());
                            VehiculoCoordenadaY = Double.toString(location.getLongitude());
                            Float direction = location.getBearing();

                            if(direction.intValue()>0){
                                VehiculoOrientacion =  (direction.intValue());
                            }

                            Log.e("ChangeListenerX",VehiculoCoordenadaX);
                            Log.e("ChangeListenerY",VehiculoCoordenadaY);


                            if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){

                                Log.e("vehiculoMarker","Actualizndo marker");

                                if(vehiculoMarker!=null){
                                    vehiculoMarker.remove();
                                }

                                vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                        .title("¡Aquì estoy!")
                                        .draggable(false)
                                        .rotation(VehiculoOrientacion)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo_yo100)));
                            }

                            switch(MapaVistaTipo){

                                case 1:

                                    if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") ) {

                                        LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                                .target(latLng)      // Sets the center of the map to Mountain View
                                                .zoom(19)                   // Sets the zoom
                                                //.bearing(90)                // Sets the orientation of the camera to east
                                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                .build();                   // Creates a CameraPosition from the builder
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    }else{

                                        /*if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                                            LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                                    .target(latLng)      // Sets the center of the map to Mountain View
                                                    .zoom(19)                   // Sets the zoom
                                                    //.bearing(90)                // Sets the orientation of the camera to east
                                                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                    .build();                   // Creates a CameraPosition from the builder
                                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                        }else{

                                            FncMostrarToast("No se encontraron coordenadas");

                                        }*/

                                    }

                                    break;

                                case 2:

                                     /*if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00")) {

                                        if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){

                                            LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                            builder.include(new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)));

                                                builder.include(new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY)));

                                                LatLngBounds bounds = builder.build();

                                                // begin new code:
                                                int width = getResources().getDisplayMetrics().widthPixels;
                                                int height = getResources().getDisplayMetrics().heightPixels;
                                                //int height = 350;
                                                int padding = (int) (width * 0.2) ; // offset from edges of the map 12% of screen

                                                //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, 350, 15);
                                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                                                googleMap.animateCamera(cu);

                                                //googleMap.animateCamera(CameraUpdateFactory.zoomOut());

                                        }else{

                                            LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                                    .target(latLng)      // Sets the center of the map to Mountain View
                                                    .zoom(19)                   // Sets the zoom
                                                    //.bearing(90)                // Sets the orientation of the camera to east
                                                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                    .build();                   // Creates a CameraPosition from the builder
                                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                        }

                                    }else{

                                        if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                                            LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                                    .target(latLng)      // Sets the center of the map to Mountain View
                                                    .zoom(19)                   // Sets the zoom
                                                    //.bearing(90)                // Sets the orientation of the camera to east
                                                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                    .build();                   // Creates a CameraPosition from the builder
                                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                        }else{
                                            FncMostrarToast("No se encontraron coordenadas");
                                        }

                                    }*/


                                    break;

                                case 3:

                                    if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                                        LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                                .target(latLng)      // Sets the center of the map to Mountain View
                                                .zoom(19)                   // Sets the zoom
                                                //.bearing(90)                // Sets the orientation of the camera to east
                                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                .build();                   // Creates a CameraPosition from the builder
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                        //FncMostrarToast("Cambiando a Vista 1");

                                    }else {

                                       /*
                                        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") ) {

                                            LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                                    .target(latLng)      // Sets the center of the map to Mountain View
                                                    .zoom(19)                   // Sets the zoom
                                                    //.bearing(90)                // Sets the orientation of the camera to east
                                                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                    .build();                   // Creates a CameraPosition from the builder
                                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                        }else{
                                            FncMostrarToast("No se encontraron coordenadas");
                                        }
                                        */

                                    }

                                    break;

                                case 4:

                                    break;
                            }
                            
                            
                            
                            
                        }





                    }
                });

                googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                    // private float currentZoom = -1;
                    @Override
                    public void onCameraChange(CameraPosition pos) {
                        if (pos.zoom != currentZoom){
                           // currentZoom = pos.zoom;
                            // do you action here
                            Log.e("ZOOM", String.valueOf(currentZoom));

                        }
                    }

                });
                //if(!PedidoCoordenadaX.equals("") & !PedidoCoordenadaY.equals("")){

                if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY != null){

                    if(pedidoMarker!=null){
                        pedidoMarker.remove();
                    }

                    if( PedidoTipo.equals("1") || PedidoTipo.equals("11")) {

                        pedidoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                                .title(ClienteNombre)
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidoa128)));

                    }else if(PedidoTipo.equals("7") || PedidoTipo.equals("10") )  {

                        pedidoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                                .title(ClienteNombre)
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidoc128)));

                    }else if(PedidoTipo.equals("6") )  {

                        pedidoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                                .title(ClienteNombre)
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidob128)));

                    }else if(PedidoTipo.equals("9") || PedidoTipo.equals("13"))  {

                        pedidoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                                .title(ClienteNombre)
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidod128)));

                    }else{

                        pedidoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                                .title(ClienteNombre)
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidoa128)));

                    }


                }


                    if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                        if(vehiculoMarker!=null){
                            vehiculoMarker.remove();
                        }

                        vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                .title("¡Aquì estoy!")
                                .draggable(false)
                                .rotation(VehiculoOrientacion)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo_yo100)));

                    }

                    // googleMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

                   /* if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){

                        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") &&  PedidoCoordenadaX !=null && PedidoCoordenadaY != null) {

                            LatLngBounds.Builder builder = new LatLngBounds.Builder();

                            builder.include(new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)));
                            builder.include(new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY)));

                            LatLngBounds bounds = builder.build();

                            int width = getResources().getDisplayMetrics().widthPixels;
                            int height = getResources().getDisplayMetrics().heightPixels;
                            int padding = (int) (width * 0.2); // offset from edges of the map 12% of screen

                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
// end of new code
                            googleMap.animateCamera(cu);

                        }else{

                            LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(latLng)      // Sets the center of the map to Mountain View
                                    .zoom(currentZoom)                   // Sets the zoom
                                    //.bearing(90)                // Sets the orientation of the camera to east
                                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                    .build();                   // Creates a CameraPosition from the builder
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }

                    }else{

                        LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng)      // Sets the center of the map to Mountain View
                                .zoom(currentZoom)                   // Sets the zoom
                                //.bearing(90)                // Sets the orientation of the camera to east
                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }*/



                switch(MapaVistaTipo){

                    case 1:

                        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") ) {

                            LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(latLng)      // Sets the center of the map to Mountain View
                                    .zoom(19)                   // Sets the zoom
                                    //.bearing(90)                // Sets the orientation of the camera to east
                                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                    .build();                   // Creates a CameraPosition from the builder
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        }else{

                           /* if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                                LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        //.bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            }else{

                                FncMostrarToast("No se encontraron coordenadas");

                            }*/

                        }

                        break;

                    case 2:

                        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00")) {

                            if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){

                                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                builder.include(new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)));

                                //if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){
                                builder.include(new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY)));
                                //}

                                LatLngBounds bounds = builder.build();

// begin new code:
                                int width = getResources().getDisplayMetrics().widthPixels;
                                int height = getResources().getDisplayMetrics().heightPixels;
                                //int height = 350;
                                //int padding = (int) (width * 0.2) + 10; // offset from edges of the map 12% of screen
                                int padding = (int) (width * 0.1); // offset from edges of the map 12% of screen

                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                                googleMap.animateCamera(cu);

                             //   googleMap.animateCamera(CameraUpdateFactory.zoomOut());

                            }else{

                                /*LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        //.bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

                            }

                        }else{

                            /*if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                                LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        //.bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            }else{
                                FncMostrarToast("No se encontraron coordenadas");
                            }
*/
                        }


                        break;

                    case 3:

                        if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                            LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(latLng)      // Sets the center of the map to Mountain View
                                    .zoom(19)                   // Sets the zoom
                                    //.bearing(90)                // Sets the orientation of the camera to east
                                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                    .build();                   // Creates a CameraPosition from the builder
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            //FncMostrarToast("Cambiando a Vista 1");

                        }else {

                           /* if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") ) {

                                LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        //.bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            }else{
                                FncMostrarToast("No se encontraron coordenadas");
                            }
*/
                        }

                        break;


                    case 4:

                        /*if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00")) {

                            if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){

                                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                builder.include(new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)));

                                //if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){
                                builder.include(new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY)));
                                //}

                                LatLngBounds bounds = builder.build();

// begin new code:
                                int width = getResources().getDisplayMetrics().widthPixels;
                                int height = getResources().getDisplayMetrics().heightPixels;
                                //int height = 350;
                                //int padding = (int) (width * 0.2) + 10; // offset from edges of the map 12% of screen
                                int padding = (int) (width * 0.1); // offset from edges of the map 12% of screen

                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                                googleMap.animateCamera(cu);

                                //   googleMap.animateCamera(CameraUpdateFactory.zoomOut());

                            }else{

                                LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        //.bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            }

                        }else{

                            if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                                LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        //.bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            }else{
                                FncMostrarToast("No se encontraron coordenadas");
                            }

                        }*/


                        break;


                }





                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {

                    FncMostrarToast("Ha ocurrido un error cargando el mapa.");

                }else{

                    if(MapaVistaTipo!=2){

                        googleMapMF.getView().setVisibility(View.GONE);

                    }

                }






            }





        } catch (Exception exception){
            // Toast.makeText(getApplicationContext(),
            //   exception.toString(),Toast.LENGTH_SHORT).show();
            //FncMostrarToast(exception.toString());
            FncMostrarToast("Ha ocurrido un error cargando el mapa: "+exception.toString());

            Log.e("PedidoActual90", exception.toString());

        }

    }


    public void onBackPressed() {


        if(Cancelar30){


            AlertDialog.Builder MsgPedidoActual = new AlertDialog.Builder(this);
            MsgPedidoActual.setTitle(getString(R.string.alert_title));
            MsgPedidoActual.setMessage("¿Realmente desea cancelar el pedido?");
            MsgPedidoActual.setCancelable(false);
            MsgPedidoActual.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            Intent intent = new Intent(PedidoActualActivity.this, PedidoCancelacionActivity.class);
                            Bundle bundle = new Bundle();

                            bundle.putString("PedidoId", PedidoId);

                            bundle.putString("ClienteNombre", ClienteNombre);
                            bundle.putString("PedidoDireccion", PedidoDireccion);
                            bundle.putString("PedidoNumero", PedidoNumero);
                            bundle.putString("PedidoReferencia", PedidoReferencia);
                            bundle.putString("PedidoDetalle", PedidoDetalle);
                            bundle.putString("PedidoCambioDe", PedidoCambioDe);

                            bundle.putString("PedidoFoto", PedidoFoto);
                            bundle.putString("PedidoTipoUnidad", PedidoTipoUnidad);

                            bundle.putString("PedidoCoordenadaX", PedidoCoordenadaX);
                            bundle.putString("PedidoCoordenadaY", PedidoCoordenadaY);
                            bundle.putString("PedidoTipo", PedidoTipo);

                            bundle.putString("VehiculoCoordenadaX", VehiculoCoordenadaX);
                            bundle.putString("VehiculoCoordenadaY", VehiculoCoordenadaY);

                            bundle.putString("ClienteCelular", ClienteCelular);
                            bundle.putString("ClienteTelefono", ClienteTelefono);

                            bundle.putString("PedidoTiempo", PedidoTiempo);
                            bundle.putString("PedidoDistancia", PedidoDistancia);

                            intent.putExtras(bundle);//Put your id to your next Intent
                            startActivity(intent);
                            finish();

                        }
                    });

            MsgPedidoActual.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });

            // Remember, create doesn't show the dialog
            AlertDialog msgPedidoActual = MsgPedidoActual.create();
            msgPedidoActual.show();


        }else{

            FncMostrarMensaje("Opcion deshabilitada despues de 30 segundos",false);
        }





        return;
    }



    private MenuItem menuAlerta;
    private MenuItem menuVerAlerta;


    private MenuItem menuReducirMapa;
    private MenuItem menuAmpliarMapa;

    private MenuItem menuMostrarMapa;
    private MenuItem menuOcultarMapa;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_pedido_actual, menu);

        menuAlerta = menu.findItem(R.id.action_alerta);
        menuVerAlerta = menu.findItem(R.id.action_ver_alerta);

        menuReducirMapa = menu.findItem(R.id.action_reducir_mapa);
        menuAmpliarMapa = menu.findItem(R.id.action_ampliar_mapa);

        menuMostrarMapa = menu.findItem(R.id.action_mostrar_mapa);
        menuOcultarMapa = menu.findItem(R.id.action_ocultar_mapa);

        if(TieneCoordenadas){

            menuOcultarMapa.setVisible(false);
            menuMostrarMapa.setVisible(false);

            menuAmpliarMapa.setVisible(true);
            menuReducirMapa.setVisible(false);

        }else{

            menuOcultarMapa.setVisible(false);
            menuMostrarMapa.setVisible(true);

            menuAmpliarMapa.setVisible(false);
            menuReducirMapa.setVisible(false);

        }



        menuAlerta.setVisible(true);
        menuVerAlerta.setVisible(false);

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


                if(Cancelar30){

                    AlertDialog.Builder MsgPedidoActual = new AlertDialog.Builder(this);
                    MsgPedidoActual.setTitle(getString(R.string.alert_title));
                    MsgPedidoActual.setMessage("¿Realmente desea cancelar el pedido?");
                    MsgPedidoActual.setCancelable(false);
                    MsgPedidoActual.setPositiveButton("Si",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog

                                    Intent intent = new Intent(PedidoActualActivity.this, PedidoCancelacionActivity.class);
                                    Bundle bundle = new Bundle();

                                    bundle.putString("PedidoId", PedidoId);

                                    bundle.putString("ClienteNombre", ClienteNombre);
                                    bundle.putString("PedidoDireccion", PedidoDireccion);
                                    bundle.putString("PedidoNumero", PedidoNumero);
                                    bundle.putString("PedidoReferencia", PedidoReferencia);
                                    bundle.putString("PedidoDetalle", PedidoDetalle);
                                    bundle.putString("PedidoCambioDe", PedidoCambioDe);

                                    bundle.putString("PedidoFoto", PedidoFoto);
                                    bundle.putString("PedidoTipoUnidad", PedidoTipoUnidad);

                                    bundle.putString("PedidoCoordenadaX", PedidoCoordenadaX);
                                    bundle.putString("PedidoCoordenadaY", PedidoCoordenadaY);
                                    bundle.putString("PedidoTipo", PedidoTipo);

                                    bundle.putString("VehiculoCoordenadaX", VehiculoCoordenadaX);
                                    bundle.putString("VehiculoCoordenadaY", VehiculoCoordenadaY);

                                    bundle.putString("ClienteCelular", ClienteCelular);
                                    bundle.putString("ClienteTelefono", ClienteTelefono);

                                    bundle.putString("PedidoTiempo", PedidoTiempo);
                                    bundle.putString("PedidoDistancia", PedidoDistancia);

                                    intent.putExtras(bundle);//Put your id to your next Intent
                                    startActivity(intent);
                                    finish();

                                }
                            });

                    MsgPedidoActual.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog
                                }
                            });

                    // Remember, create doesn't show the dialog
                    AlertDialog msgPedidoActual = MsgPedidoActual.create();
                    msgPedidoActual.show();
        }else{

            FncMostrarMensaje("Opcion deshabilitada despues de 30 segundos",false);
        }



                return true;



            case R.id.action_mostrar_mapa:

                if(!TieneCoordenadas){

                    Log.e("TieneCoordenadas","false");

                    if(AmpliarMapa==2){

                        Log.e("AmpliarMapa","2");

                        if(OcultarMapa==2) {

                            Log.e("OcultarMapa", "2");

                            if (googleMapMF != null) {

                                googleMapMF.getView().setVisibility(View.VISIBLE);

                            }

                            OcultarMapa = 1;

                            menuOcultarMapa.setVisible(true);
                            menuMostrarMapa.setVisible(false);

                            menuAmpliarMapa.setVisible(true);
                            menuReducirMapa.setVisible(false);


                        }else{

                            Log.e("OcultarMapa","1");


                            /*
                          if(googleMapMF!=null){

                                googleMapMF.getView().setVisibility(View.GONE);

                            }

                            OcultarMapa = 2;

                            menuOcultarMapa.setVisible(false);
                            menuMostrarMapa.setVisible(true);

                            menuAmpliarMapa.setVisible(false);
                            menuReducirMapa.setVisible(false);
                            */


                        }

                    }else{
                        FncMostrarMensaje("Esta funcion no esta disponible con el mapa ampliado",false);

                    }


                }else{

                    FncMostrarMensaje("Esta funcion no esta disponible para este pedido",false);

                }



                return true;


            case R.id.action_ocultar_mapa:

                if(!TieneCoordenadas){

                    Log.e("TieneCoordenadas","false");

                    if(AmpliarMapa==2){

                        Log.e("AmpliarMapa","2");




                        if(OcultarMapa==2){

                            Log.e("OcultarMapa","2");

                         /*

                            if(googleMapMF!=null){

                                googleMapMF.getView().setVisibility(View.VISIBLE);

                            }

                            OcultarMapa = 1;

                            menuOcultarMapa.setVisible(true);
                            menuMostrarMapa.setVisible(false);

                            menuAmpliarMapa.setVisible(true);
                            menuReducirMapa.setVisible(false);
*/


                        }else{

                            Log.e("OcultarMapa","1");

                            if(googleMapMF!=null){

                                googleMapMF.getView().setVisibility(View.GONE);

                            }

                            OcultarMapa = 2;

                            menuOcultarMapa.setVisible(false);
                            menuMostrarMapa.setVisible(true);

                            menuAmpliarMapa.setVisible(false);
                            menuReducirMapa.setVisible(false);


                        }

                    }else{

                        FncMostrarMensaje("Esta funcion no esta disponible con el mapa ampliado",false);

                    }


                }else{

                    FncMostrarMensaje("Esta funcion no esta disponible para este pedido",false);

                }



                return true;


            case R.id.action_ampliar_mapa:

             //   if(OcultarMapa==2){

                    Log.e("OcultarMapa","2");

                    if(AmpliarMapa==2){

                        Log.e("AmpliarMapa","2");

                        capContenidos.setVisibility(View.GONE);
                        capBotones.setVisibility(View.GONE);
                        capPublicidad.setVisibility(View.GONE);


                        if(googleMapMF!=null){

                            Log.e("action_ampliar_mapa","H900");


                            //googleMapMF.getView().setVisibility(View.VISIBLE);

                            ViewGroup.LayoutParams params = googleMapMF.getView().getLayoutParams();
                            params.height = 1200;

                            googleMapMF.getView().setLayoutParams(params);

                        }else{
                            Log.e("action_ampliar_mapa","H900 ER");

                        }

                        AmpliarMapa = 1;

                        menuOcultarMapa.setVisible(false);
                        menuMostrarMapa.setVisible(false);

                        menuAmpliarMapa.setVisible(false);
                        menuReducirMapa.setVisible(true);


                    }else{

                        Log.e("AmpliarMapa","1");

                        /*capContenidos.setVisibility(View.VISIBLE);
                        capBotones.setVisibility(View.VISIBLE);
                        capPublicidad.setVisibility(View.VISIBLE);

                        if(googleMapMF!=null){

                            //googleMapMF.getView().setVisibility(View.GONE);
                            Log.e("action_ampliar_mapa","H400");


                            ViewGroup.LayoutParams params = googleMapMF.getView().getLayoutParams();
                            params.height = 600;

                            googleMapMF.getView().setLayoutParams(params);

                        }else{
                            Log.e("action_ampliar_mapa","H400 ERROR");

                        }

                        AmpliarMapa = 2;

                        menuOcultarMapa.setVisible(true);
                        menuMostrarMapa.setVisible(false);

                        menuAmpliarMapa.setVisible(true);
                        menuReducirMapa.setVisible(false);
*/
                    }

           /*     }else{

                    FncMostrarMensaje("Esta funcion no esta disponible con el mapa oculto",false);

                }
*/




                return true;

            case R.id.action_reducir_mapa:

               // if(OcultarMapa==2){

                    Log.e("OcultarMapa","2");

                    if(AmpliarMapa==2){

                        Log.e("AmpliarMapa","2");
/*


                        capContenidos.setVisibility(View.GONE);
                        capBotones.setVisibility(View.GONE);
                        capPublicidad.setVisibility(View.GONE);


                        if(googleMapMF!=null){

                            Log.e("action_ampliar_mapa","H900");

                            //googleMapMF.getView().setVisibility(View.VISIBLE);

                            ViewGroup.LayoutParams params = googleMapMF.getView().getLayoutParams();
                            params.height = 1200;

                            googleMapMF.getView().setLayoutParams(params);

                        }else{

                            Log.e("action_ampliar_mapa","H900 ER");

                        }

                        AmpliarMapa = 1;

                        menuOcultarMapa.setVisible(false);
                        menuMostrarMapa.setVisible(false);

                        menuAmpliarMapa.setVisible(false);
                        menuReducirMapa.setVisible(true);
*/

                    }else{

                        Log.e("AmpliarMapa","1");

                        capContenidos.setVisibility(View.VISIBLE);
                        capBotones.setVisibility(View.VISIBLE);
                        capPublicidad.setVisibility(View.VISIBLE);

                        if(googleMapMF!=null){

                            Log.e("action_ampliar_mapa","H400");


                            //googleMapMF.getView().setVisibility(View.GONE);

                            ViewGroup.LayoutParams params = googleMapMF.getView().getLayoutParams();
                            params.height = 600;

                            googleMapMF.getView().setLayoutParams(params);

                        }else{
                            Log.e("action_ampliar_mapa","H400 ERROR");

                        }

                        AmpliarMapa = 2;


                        menuOcultarMapa.setVisible(true);
                        menuMostrarMapa.setVisible(false);

                        menuAmpliarMapa.setVisible(true);
                        menuReducirMapa.setVisible(false);


                    }

              /*  }else{

                    FncMostrarMensaje("Esta funcion no esta disponible con el mapa oculto",false);

                }
*/




                return true;

            case R.id.action_info:

                String MensajeInfo ="";

                if(!PedidoInfo.equals("")){

                    //MensajeInfo += "Operadora: "+PedidoOperadora;
                    MensajeInfo += PedidoInfo;


                }

                FncMostrarMensaje(MensajeInfo,false);

                return true;


            case R.id.action_reportar_ubicacion:


                if(!PedidoTipo.equals("6")) {



                    AlertDialog.Builder MsgPedidoActual1 = new AlertDialog.Builder(this);
                    MsgPedidoActual1.setTitle(getString(R.string.alert_title));
                    MsgPedidoActual1.setMessage("¿Realmente desea reportar esta ubicacion?");
                    MsgPedidoActual1.setCancelable(false);
                    MsgPedidoActual1.setPositiveButton("Si",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog




                                    Thread ntMtdReportarUbicacion = new Thread() {
                                        @Override
                                        public void run() {

                                            try {
                                                final String resMtdReportarUbicacion;

                                                resMtdReportarUbicacion = MtdReportarUbicacion(PedidoId,VehiculoCoordenadaX,VehiculoCoordenadaY,ClienteId,VehiculoUnidad);

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        //PrgConductorDato.cancel();

                                                        String JsRespuesta = "";


                                                        try {

                                                            JSONObject jsonObject = new JSONObject(resMtdReportarUbicacion);
                                                            JsRespuesta = jsonObject.getString("Respuesta");


                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        switch(JsRespuesta){

                                                            case "L107":

                                                                FncMostrarToast("Ubicacion reportada correctamente");
                                                                break;

                                                            case "L108":

                                                                FncMostrarMensaje("No se pudo registrar la ubicacion, intente nuevamente.",false);

                                                                break;

                                                            case "L109":

                                                                FncMostrarMensaje("No se pudo identificar al cliente. Proceso Cancelado",false);

                                                                break;

                                                            default:
                                                                //FncMostrarMensaje(getString(R.string.message_error_interno),false);
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

                                    ntMtdReportarUbicacion.start();



                                }
                            });

                    MsgPedidoActual1.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog
                                }
                            });

                    // Remember, create doesn't show the dialog
                    AlertDialog msgPedidoActual1 = MsgPedidoActual1.create();
                    msgPedidoActual1.show();



                }else{

                    FncMostrarMensaje("Esta acccion no esta habilitado para este tipo de pedido",false);

                }

                return true;


            case R.id.action_aviso:


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


                                                        ImagePopup imagePopupConductorFoto = new ImagePopup(PedidoActualActivity.this);
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
                                                FncMostrarToast("No se encontraron mensajes");
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


            case R.id.action_registrar_alerta1:

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


                                                //if(!mediaPlayerAlerta.isPlaying()){
                                                //    mediaPlayerAlerta.start(); // no need to call prepare(); create() does that for you
                                                //}


                                                if(loaded)  {

                                                    soundPool.play(soundIdAlerta,volume, volume, 1, 0, 1f);

                                                }

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

            case R.id.action_registrar_alerta2:

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


                                                //if(!mediaPlayerAlerta.isPlaying()){
                                                //    mediaPlayerAlerta.start(); // no need to call prepare(); create() does that for you
                                               // }


                                                if(loaded)  {

                                                    soundPool.play(soundIdAlerta,volume, volume, 1, 0, 1f);


                                                }
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

            case R.id.action_registrar_alerta3:

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


                                                //if(!mediaPlayerAlerta.isPlaying()){
                                                //    mediaPlayerAlerta.start(); // no need to call prepare(); create() does that for you
                                               // }



                                                if(loaded)  {
                                                    soundPool.play(soundIdAlerta,volume, volume, 1, 0, 1f);


                                                }
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

            case R.id.action_registrar_alerta4:

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

                                                //if(!mediaPlayerAlerta.isPlaying()){
                                                //    mediaPlayerAlerta.start(); // no need to call prepare(); create() does that for you
                                                //}


                                                if(loaded)  {
                                                    soundPool.play(soundIdAlerta,volume, volume, 1, 0, 1f);


                                                }
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


           /* case R.id.action_compartir:

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                return true;

            case R.id.action_acerca_de:

                FncMostrarAcercaDe();

                return true;*/

            /*case R.id.action_salir:

                FncMostrarToast("Cerrando aplicacion ...");

                finish();
                System.exit(0);
                return true;*/

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

        AlertDialog.Builder MsgGeneral = new AlertDialog.Builder(this);
        MsgGeneral.setTitle(getString(R.string.alert_title));
        MsgGeneral.setMessage(oMensaje);
        MsgGeneral.setCancelable(false);
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

        Toast.makeText(PedidoActualActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();
    }


    protected void displayNotificationOne(String oTitulo,String oContenido,String oContenidoGrande) {

        int notificationId = 001;

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(PedidoActualActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(oTitulo)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(oContenidoGrande))
                        .setContentText(oContenido)
                        //.setVibrate(new long[] { 100, 250 })
                        .setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(PedidoActualActivity.this);


       /* SharedPreferences sharedPreferences = this.getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
        boolean ConductorTienePedido = sharedPreferences.getBoolean("ConductorTienePedido", false);

        if (ConductorTienePedido) {

            Intent intent = new Intent(this, PedidoActualActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            stackBuilder.addParentStack(PedidoActualActivity.class);

            stackBuilder.addNextIntent(intent);

            PendingIntent contentIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            notificationBuilder.setContentIntent(contentIntent);

        }*/

        notificationManager.notify(notificationId, notificationBuilder.build());

    }



    public void onClick_BtnPedidoActualCamara(View v){

        if(!PedidoFoto.equals("")){


            //CARGAR PUBLICIDAD - INICIO

            final ImagePopup imagePopupPedidoFoto = new ImagePopup(PedidoActualActivity.this);
            //imagePopup.setWindowHeight(100); // Optional
            //imagePopup.setWindowWidth(100); // Optional
            imagePopupPedidoFoto.setBackgroundColor(Color.TRANSPARENT);  // Optional
            imagePopupPedidoFoto.setFullScreen(false); // Optional
            imagePopupPedidoFoto.setHideCloseIcon(true);  // Optional
            imagePopupPedidoFoto.setImageOnClickClose(true);  // Optional


            //ImageView imageView = (ImageView) findViewById(R.id.imageView);
            //imagePopup.initiatePopup(imgPedidoConductorFoto.getDrawable());
            imagePopupPedidoFoto.initiatePopupWithPicasso(PedidoFoto);

                    /** Initiate Popup view **/
            imagePopupPedidoFoto.viewPopup();
                    Log.e("EsperandoTaxi","Mostrando Foto");


/*
            if(imgPedidoFoto.getDrawable()!=null){
                //imagePopup3.initiatePopup(imgPedidoFoto.getDrawable());

                android.app.AlertDialog.Builder imageDialog = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.conductor_foto,
                        (ViewGroup) findViewById(R.id.layout_root));
                ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
                // image.setMaxHeight(300);
                // image.setLa
                image.setImageDrawable(imgPedidoFoto.getDrawable());
                imageDialog.setView(layout);


                imageDialog.create();
                imageDialog.show();
            }

            */
            /*Thread nt = new Thread() {
                @Override
                public void run() {

                    try {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                CargaImagenesPublicidadArchivo cargaImagenesPublicidadArchivo = new CargaImagenesPublicidadArchivo();
                                cargaImagenesPublicidadArchivo.execute(PedidoFoto);




                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }
            };

            nt.start();
*/


            //CARGANDO ACTIVIDAD
   /* Intent intent = new Intent(PedidoActualActivity.this, PedidoActualFotoActivity.class);
    Bundle bundle = new Bundle();

    bundle.putString("PedidoId", PedidoId);

    bundle.putString("ClienteNombre", ClienteNombre);

    bundle.putString("PedidoDireccion", PedidoDireccion);
    bundle.putString("PedidoNumero", PedidoNumero);
    bundle.putString("PedidoReferencia", PedidoReferencia);
    bundle.putString("PedidoDetalle", PedidoDetalle);
    bundle.putString("PedidoFoto", PedidoFoto);

    bundle.putString("PedidoCoordenadaX", PedidoCoordenadaX);
    bundle.putString("PedidoCoordenadaY", PedidoCoordenadaY);

    bundle.putString("VehiculoCoordenadaX", VehiculoCoordenadaX);
    bundle.putString("VehiculoCoordenadaY", VehiculoCoordenadaY);

    bundle.putString("ClienteCelular", ClienteCelular);
    bundle.putString("ClienteTelefono", ClienteTelefono);

    intent.putExtras(bundle);//Put your id to your next Intent
    startActivity(intent);
    finish();
*/
        }else{
            FncMostrarMensaje("La funcion FOTO REFERENCIA no esta disponible para este pedido",false);
        }




//        Intent intent = new Intent(PedidoActualActivity.this, PedidoActualFotoActivity.class);
        //    startActivity(intent);
        //      finish();



///        final View thumb1View = findViewById(R.id.BtnPedidoActualCamara);

        //zoomImageFromThumb(thumb1View, PedidoFoto);
    }
    /**
     *  ENVIO DE VARIABLES
     * */


    public void onClick_BtnPedidoActualUbicar(View v){

        switch(MapaVistaTipo){
            case 1:

                if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null ) {

                    if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        builder.include(new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)));

                        //if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){
                        builder.include(new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY)));
                        //}

                        LatLngBounds bounds = builder.build();

                        // begin new code:
                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        //int height = 350;
                        //int padding = (int) (width * 0.2) + 10; // offset from edges of the map 12% of screen
                        int padding = (int) (width * 0.2); // offset from edges of the map 12% of screen

                        //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, 350, 15);
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                        googleMap.animateCamera(cu);

                        FncMostrarToast("Cambiando a Vista 2");

                    }else{
                        FncMostrarToast("No se encontraron coordenadas de la unidad");
                    }

                }else{
                    
                    FncMostrarToast("No se encontraron coordenadas del pedido");
                    
                }

                MapaVistaTipo = 2;

                break;

            case 2:

                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                    LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng)      // Sets the center of the map to Mountain View
                            .zoom(19)                   // Sets the zoom
                            //.bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    FncMostrarToast("Cambiando a Vista 3");

                }else {

                    FncMostrarToast("No se encontraron coordenadas de la unidad");

                }

                MapaVistaTipo = 3;

                break;

            case 3:


                /*if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null ) {

                    if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        builder.include(new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)));

                        //if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){
                        builder.include(new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY)));
                        //}

                        LatLngBounds bounds = builder.build();

// begin new code:
                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        //int height = 350;
                        //int padding = (int) (width * 0.2) + 10; // offset from edges of the map 12% of screen
                        int padding = (int) (width * 0.2); // offset from edges of the map 12% of screen

                        //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, 350, 15);
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                        googleMap.animateCamera(cu);

                        FncMostrarToast("Cambiando a Vista 1");

                    }else{
                        FncMostrarToast("No se encontraron coordenadas de la unidad");
                    }

                }else{
                    FncMostrarToast("No se encontraron coordenadas del pedido");
                }
*/


                if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") ) {

                    LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng)      // Sets the center of the map to Mountain View
                            .zoom(19)                   // Sets the zoom
                            //.bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    FncMostrarToast("Cambiando a Vista 1");

                }else{

                    FncMostrarToast("No se encontraron coordenadas del pedido");

                }
                
                
                MapaVistaTipo = 1;

                break;

            case 4:

               /* if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && PedidoCoordenadaX !=null && PedidoCoordenadaY !=null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") ) {

                    LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng)      // Sets the center of the map to Mountain View
                            .zoom(19)                   // Sets the zoom
                            //.bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    FncMostrarToast("Cambiando a Vista 1");

                }else{

                    if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null) {

                        LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng)      // Sets the center of the map to Mountain View
                                .zoom(19)                   // Sets the zoom
                                //.bearing(90)                // Sets the orientation of the camera to east
                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }else{

                        FncMostrarToast("No se encontraron coordenadas");

                    }

                }


                MapaVistaTipo = 1;*/

                break;

        }

        
        
        
        
 /*       if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX !=null && PedidoCoordenadaY != null){

            if(pedidoMarker!=null){
                pedidoMarker.remove();
            }


            if(PedidoTipo.equals("1") || PedidoTipo.equals("11")) {

                pedidoMarker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                        .title(ClienteNombre)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidoa128)));

            }else if(PedidoTipo.equals("7") || PedidoTipo.equals("9") || PedidoTipo.equals("10"))  {

                pedidoMarker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                        .title(ClienteNombre)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidoc128)));

            }else{

                pedidoMarker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                        .title(ClienteNombre)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidob128)));

            }


            if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                builder.include(new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)));

                //if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") &&  VehiculoCoordenadaX !=null && VehiculoCoordenadaY != null){
                builder.include(new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY)));
                //}

                LatLngBounds bounds = builder.build();

// begin new code:
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.2); // offset from edges of the map 12% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                googleMap.animateCamera(cu);

                //googleMap.animateCamera(CameraUpdateFactory.zoomOut());

            }else{

                LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)      // Sets the center of the map to Mountain View
                        .zoom(19)                   // Sets the zoom
                        //.bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }





        }else {

            FncMostrarMensaje("La funcion UBICAR no esta disponible para este pedido",false);

        }

*/

    }

    public void onClick_BtnPedidoActualCancelar(View v){

        if(Cancelar30){

            AlertDialog.Builder MsgPedidoActual = new AlertDialog.Builder(this);
            MsgPedidoActual.setTitle(getString(R.string.alert_title));
            MsgPedidoActual.setMessage("¿Realmente desea cancelar el pedido?");
            MsgPedidoActual.setCancelable(false);
            MsgPedidoActual.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog


                            if(Cancelar30){
                                Intent intent = new Intent(PedidoActualActivity.this, PedidoCancelacionActivity.class);
                                Bundle bundle = new Bundle();

                                bundle.putString("PedidoId", PedidoId);

                                bundle.putString("ClienteNombre", ClienteNombre);
                                bundle.putString("PedidoDireccion", PedidoDireccion);
                                bundle.putString("PedidoNumero", PedidoNumero);
                                bundle.putString("PedidoReferencia", PedidoReferencia);
                                bundle.putString("PedidoDetalle", PedidoDetalle);

                                bundle.putString("PedidoFoto", PedidoFoto);
                                bundle.putString("PedidoTipoUnidad", PedidoTipoUnidad);

                                bundle.putString("PedidoCoordenadaX", PedidoCoordenadaX);
                                bundle.putString("PedidoCoordenadaY", PedidoCoordenadaY);
                                bundle.putString("PedidoTipo", PedidoTipo);

                                bundle.putString("VehiculoCoordenadaX", VehiculoCoordenadaX);
                                bundle.putString("VehiculoCoordenadaY", VehiculoCoordenadaY);

                                bundle.putString("ClienteCelular", ClienteCelular);
                                bundle.putString("ClienteTelefono", ClienteTelefono);


                                intent.putExtras(bundle);//Put your id to your next Intent
                                startActivity(intent);
                                finish();
                            }else{
                                FncMostrarMensaje("Opcion deshabilitada despues de 30 segundos",false);
                            }



                        /*final ProgressDialog prgPedidoActual = new ProgressDialog(PedidoActualActivity.this);
                        prgPedidoActual.setIcon(R.mipmap.ic_launcher);
                        prgPedidoActual.setMessage("Cargando...");
                        prgPedidoActual.setCancelable(false);
                        prgPedidoActual.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        prgPedidoActual.show();

                        Thread nt = new Thread() {
                            @Override
                            public void run() {

                                try {

                                    final String resMtdObtenerPedido;

                                    resMtdObtenerPedido = postEnviarPedidoCancelarConductor(PedidoId);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            String JsRespuesta = "";

                                            try {

                                                JSONObject jsonObject = new JSONObject(resMtdObtenerPedido);
                                                JsRespuesta = jsonObject.getString("Respuesta");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            prgPedidoActual.cancel();

                                            switch(JsRespuesta){

                                                case "P026":


                                                    SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                                    editor.putBoolean("ConductorTienePedido", false);
                                                    editor.apply();

                                                    // Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                    // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                    // startActivity(intent);
                                                    //  finish();

                                                    Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();

                                                    break;

                                                case "P027":
                                                    FncMostrarMensaje("No se ha podido cancelar el pedido, ha ocurrido un error",false);

                                                    break;

                                                case "P028":
                                                    FncMostrarMensaje("No se ha podido cancelar el pedido, verifique el estado del pedido",false);
                                                    break;

                                                default://NO ES CRITICO
                                                    FncMostrarToast(getString(R.string.message_error_interno));
                                                    //FncMostrarMensaje(getString(R.string.message_error_interno),false);
                                                    break;

                                            }

                                        }
                                    });
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }

                            }
                        };

                        nt.start();*/


                        }
                    });

            MsgPedidoActual.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });

            // Remember, create doesn't show the dialog
            AlertDialog msgPedidoActual = MsgPedidoActual.create();
            msgPedidoActual.show();
        }else{

            FncMostrarMensaje("Opcion deshabilitada despues de 30 segundos",false);
        }


    }

    public void onClick_BtnPedidoActualAlertar(View v){


        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX !=null && PedidoCoordenadaY != null){


            if(estaConectado(true)){

                final ProgressDialog prgPedidoActual = new ProgressDialog(PedidoActualActivity.this);
                prgPedidoActual.setIcon(R.mipmap.ic_launcher);
                prgPedidoActual.setMessage("Cargando...");
                prgPedidoActual.setCancelable(false);
                prgPedidoActual.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                prgPedidoActual.show();

                Thread nt = new Thread() {
                    @Override
                    public void run() {

                        try {

                            final String resMtdObtenerPedido;

                            resMtdObtenerPedido = postEnviarPedidoAlerta(PedidoId);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JsRespuesta = "";

                                    try {

                                        JSONObject jsonObject = new JSONObject(resMtdObtenerPedido);
                                        JsRespuesta = jsonObject.getString("Respuesta");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    prgPedidoActual.cancel();

                                    switch(JsRespuesta){

                                        case "P021":

                                           // MediaPlayer player = MediaPlayer.create(PedidoActualActivity.this,R.raw.taxi2);
                                           // player = MediaPlayer.create(PedidoActualActivity.this, R.raw.taxi2);
                                           // player.start();



                                            //if(!mediaPlayerBocina.isPlaying()){
                                             //   mediaPlayerBocina.start(); // no need to call prepare(); create() does that for you
                                           /// }



                                            if(loaded)  {
                                                soundPool.play(soundIdBocina,volume, volume, 1, 0, 1f);


                                            }


                                            break;
                                        case "P022":
                                            FncMostrarMensaje("No se ha podido enviar la alerta, ha ocurrido un error",false);
                                            break;

                                        case "P023":
                                            FncMostrarMensaje("No se ha podido enviar la alerta, verifique el estado del pedido",false);
                                            break;

                                        default://NO ES CRITICO
                                            FncMostrarToast(getString(R.string.message_error_interno));
                                            // FncMostrarMensaje(getString(R.string.message_error_interno),false);
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


        }else{

            FncMostrarMensaje("La funcion ALERTAR no esta disponible para este pedido",false);
        }



    }

    public void onClick_BtnPedidoActualAbordo(View v){

        if(estaConectado(true)){

            AlertDialog.Builder MsgPedidoActual = new AlertDialog.Builder(this);
            MsgPedidoActual.setTitle(getString(R.string.alert_title));
            MsgPedidoActual.setMessage("¿El cliente esta abordo?");
            MsgPedidoActual.setCancelable(false);
            MsgPedidoActual.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            final ProgressDialog prgPedidoActual = new ProgressDialog(PedidoActualActivity.this);
                            prgPedidoActual.setIcon(R.mipmap.ic_launcher);
                            prgPedidoActual.setMessage("Cargando...");
                            prgPedidoActual.setCancelable(false);
                            prgPedidoActual.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            prgPedidoActual.show();

                            Thread nt = new Thread() {
                                @Override
                                public void run() {

                                    try {

                                        final String resMtdObtenerPedido;
                                        resMtdObtenerPedido = MtdAbordoPedido(PedidoId);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                String JsRespuesta = "";

                                                try {

                                                    JSONObject jsonObject = new JSONObject(resMtdObtenerPedido);
                                                    JsRespuesta = jsonObject.getString("Respuesta");

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                prgPedidoActual.cancel();

                                                switch(JsRespuesta){

                                                    case "P016":

                                                        PedidoActualObtenerPedidoContinuar = false;
                                                        PedidoActualEnviarCoordenadaContinuar = false;

                                                        //timerPedidoActual1.cancel();
                                                        //timerPedidoActual2.cancel();

                                                        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putBoolean("ConductorTienePedido", false);
                                                        editor.putBoolean("Cancelar30", true);
                                                        editor.apply();


                                                        Cancelar30 = true;



                                                        //Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                        //startActivity(intent);

                                                        // finish();

                                                        Intent intent = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                        //2intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        finish();

                                                        break;

                                                    case "P017":
                                                        FncMostrarMensaje("No se pudo cambiar a abordo, ha ocurrido un error",false);
                                                        break;

                                                    case "P018":
                                                        FncMostrarMensaje("No se pudo cambiar a abordo, verifique el estado del pedido",false);
                                                        break;

                                                    default:
                                                        FncMostrarToast(getString(R.string.message_error_interno));
                                                        //FncMostrarMensaje(getString(R.string.message_error_interno),false);
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
                    });

            MsgPedidoActual.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });

            // Remember, create doesn't show the dialog
            AlertDialog msgPedidoActual = MsgPedidoActual.create();
            msgPedidoActual.show();


        }



    }


    public void FncLlamar(){

        if(!ClienteCelular.equals("")) {

            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ClienteCelular));
                startActivity(callIntent);
            } catch (Exception e) {
                FncMostrarToast("Ha ocurrido un error interno");
                //Log.e("helloandroid dialing example", "Call failed", e);
            }

            //FncLlamarNumero(ConductorCelular);
        }else if(!ClienteTelefono.equals("")){

            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ClienteTelefono));
                startActivity(callIntent);
            } catch (Exception e) {
                FncMostrarToast("Ha ocurrido un error interno");
                //Log.e("helloandroid dialing example", "Call failed", e);
            }

        }

    }


    public void onClick_BtnPedidoActuallLlamar(View v){

        if( (!ClienteCelular.equals("") && !ClienteCelular.equals("-")) || (!ClienteTelefono.equals("") && !ClienteTelefono.equals("-"))) {

            if (checkPermission(4)) {
                FncLlamar();
            }

        }else{

            FncMostrarMensaje("No se encontró número de celular o teléfono del cliente",false);

        }


    }







    public void onClick_BtnPedidoActualMensajeCentral(final View v) {

       // if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX !=null && PedidoCoordenadaY != null) {

            //ACCIONES
            //final CharSequence[] items = { "Ninguno","Estoy Afuera","Estoy en camino","Ok","Gracias" };
            final CharSequence[] items = { "Ninguno","Conforme con el servicio","Objetivo Central","X-22 Central" };

            // int intConductorCanal = Integer.parseInt(ConductorCanal);
            //   intConductorCanal = intConductorCanal -1;

            // Creating and Building the Dialog
            android.support.v7.app.AlertDialog.Builder MsgAceptarPedido = new android.support.v7.app.AlertDialog.Builder(this);
            MsgAceptarPedido.setTitle("Escoja un opcion");
            //MsgAceptarPedido.setCancelable(false);
            MsgAceptarPedido.setSingleChoiceItems(items,0,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            // TextView TxtCanalActual = (TextView) findViewById(R.id.CmpCanalActual);

                            switch (item) {
                                case 0:

                                    dialog.dismiss();

                                    break;
                                case 1:

                                    final ProgressDialog PrgAceptarPedido = new ProgressDialog(PedidoActualActivity.this);
                                    PrgAceptarPedido.setIcon(R.mipmap.ic_launcher);
                                    PrgAceptarPedido.setMessage("Cargando...");
                                    PrgAceptarPedido.setCancelable(false);
                                    PrgAceptarPedido.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    PrgAceptarPedido.show();

                                    //TAREA CANCELAR TAXI - INICIO
                                    Thread ntAceptarPedido1 = new Thread() {
                                        @Override
                                        public void run() {

                                            try {

                                                final String resMtdEnviarPedidoMensajeCentral;
                                                resMtdEnviarPedidoMensajeCentral = MtdEnviarPedidoMensajeCentral(PedidoId,"Conforme con el servicio");

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        String JsRespuesta = "";
                                                        String JsPedidoId = "";

                                                        try {

                                                            JSONObject jsonObject = new JSONObject(resMtdEnviarPedidoMensajeCentral);
                                                            JsRespuesta = jsonObject.getString("Respuesta");

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        PrgAceptarPedido.cancel();

                                                        switch (JsRespuesta) {
                                                            case "C107":
                                                                FncMostrarToast("Mensaje enviado...");
                                                                break;

                                                            case "C108":
                                                                FncMostrarMensaje("No se ha podido enviar el mensaje, intente nuevamente", false);
                                                                break;
                                                            case "C109":

                                                                //FncMostrarMensaje("No se ha podido identificar el servicio, proceso cancelado", true);
                                                                android.app.AlertDialog.Builder MsgAceptarPedido83 = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                                                                MsgAceptarPedido83.setTitle(getString(R.string.alert_title));
                                                                MsgAceptarPedido83.setMessage("No se ha podido identificar el servicio, se reiniciara la aplicación");
                                                                MsgAceptarPedido83.setCancelable(false);
                                                                MsgAceptarPedido83.setPositiveButton("Aceptar",
                                                                        new DialogInterface.OnClickListener() {

                                                                            public void onClick(DialogInterface dialog, int which) {


                                                                                Intent intentMonitoreo = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                                                startActivity(intentMonitoreo);
                                                                                PedidoActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                                                                        R.anim.anim_slide_out_left);
                                                                                finish();

                                                                            }
                                                                        });

                                                                // Remember, create doesn't show the dialog
                                                                android.app.AlertDialog msgAceptarPedido83 = MsgAceptarPedido83.create();
                                                                msgAceptarPedido83.show();

                                                                break;

                                                            default:
                                                                FncMostrarToast(getString(R.string.message_error_interno));
                                                                break;
                                                        }

                                                    }
                                                });

                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                // Log.e("CancelarTaxiMsg", e.toString());
                                            }


                                        }
                                    };
                                    ntAceptarPedido1.start();

                                    dialog.dismiss();

                                    break;


                                case 2:

                                    final ProgressDialog PrgAceptarPedido2 = new ProgressDialog(PedidoActualActivity.this);
                                    PrgAceptarPedido2.setIcon(R.mipmap.ic_launcher);
                                    PrgAceptarPedido2.setMessage("Cargando...");
                                    PrgAceptarPedido2.setCancelable(false);
                                    PrgAceptarPedido2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    PrgAceptarPedido2.show();

                                    //TAREA CANCELAR TAXI - INICIO
                                    Thread ntAceptarPedido2 = new Thread() {
                                        @Override
                                        public void run() {

                                            try {

                                                final String resMtdEnviarPedidoMensajeCentral;
                                                resMtdEnviarPedidoMensajeCentral = MtdEnviarPedidoMensajeCentral(PedidoId,"Objetivo Central");

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        String JsRespuesta = "";
                                                        String JsPedidoId = "";

                                                        try {

                                                            JSONObject jsonObject = new JSONObject(resMtdEnviarPedidoMensajeCentral);
                                                            JsRespuesta = jsonObject.getString("Respuesta");

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        PrgAceptarPedido2.cancel();

                                                        switch (JsRespuesta) {
                                                            case "C107":
                                                                FncMostrarToast("Mensaje enviado...");
                                                                break;

                                                            case "C108":
                                                                FncMostrarMensaje("No se ha podido enviar el mensaje, intente nuevamente", false);
                                                                break;
                                                            case "C109":

                                                                //FncMostrarMensaje("No se ha podido identificar el servicio, proceso cancelado", true);
                                                                android.app.AlertDialog.Builder MsgAceptarPedido83 = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                                                                MsgAceptarPedido83.setTitle(getString(R.string.alert_title));
                                                                MsgAceptarPedido83.setMessage("No se ha podido identificar el servicio, se reiniciara la aplicación");
                                                                MsgAceptarPedido83.setCancelable(false);
                                                                MsgAceptarPedido83.setPositiveButton("Aceptar",
                                                                        new DialogInterface.OnClickListener() {

                                                                            public void onClick(DialogInterface dialog, int which) {


                                                                                Intent intentMonitoreo = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                                                startActivity(intentMonitoreo);
                                                                                PedidoActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                                                                        R.anim.anim_slide_out_left);
                                                                                finish();

                                                                            }
                                                                        });

                                                                // Remember, create doesn't show the dialog
                                                                android.app.AlertDialog msgAceptarPedido83 = MsgAceptarPedido83.create();
                                                                msgAceptarPedido83.show();

                                                                break;

                                                            default:
                                                                FncMostrarToast(getString(R.string.message_error_interno));
                                                                break;
                                                        }

                                                    }
                                                });

                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                // Log.e("CancelarTaxiMsg", e.toString());
                                            }


                                        }
                                    };
                                    ntAceptarPedido2.start();

                                    dialog.dismiss();

                                    break;



                                case 3:

                                    final ProgressDialog PrgAceptarPedido3 = new ProgressDialog(PedidoActualActivity.this);
                                    PrgAceptarPedido3.setIcon(R.mipmap.ic_launcher);
                                    PrgAceptarPedido3.setMessage("Cargando...");
                                    PrgAceptarPedido3.setCancelable(false);
                                    PrgAceptarPedido3.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    PrgAceptarPedido3.show();

                                    //TAREA CANCELAR TAXI - INICIO
                                    Thread ntAceptarPedido3 = new Thread() {
                                        @Override
                                        public void run() {

                                            try {

                                                final String resMtdEnviarPedidoMensajeCentral;
                                                resMtdEnviarPedidoMensajeCentral = MtdEnviarPedidoMensajeCentral(PedidoId,"X-22 Central");

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        String JsRespuesta = "";
                                                        String JsPedidoId = "";

                                                        try {

                                                            JSONObject jsonObject = new JSONObject(resMtdEnviarPedidoMensajeCentral);
                                                            JsRespuesta = jsonObject.getString("Respuesta");

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        PrgAceptarPedido3.cancel();

                                                        switch (JsRespuesta) {
                                                            case "C107":
                                                                FncMostrarToast("Mensaje enviado...");
                                                                break;

                                                            case "C108":
                                                                FncMostrarMensaje("No se ha podido enviar el mensaje, intente nuevamente", false);
                                                                break;
                                                            case "C109":

                                                                //FncMostrarMensaje("No se ha podido identificar el servicio, proceso cancelado", true);
                                                                android.app.AlertDialog.Builder MsgAceptarPedido83 = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                                                                MsgAceptarPedido83.setTitle(getString(R.string.alert_title));
                                                                MsgAceptarPedido83.setMessage("No se ha podido identificar el servicio, se reiniciara la aplicación");
                                                                MsgAceptarPedido83.setCancelable(false);
                                                                MsgAceptarPedido83.setPositiveButton("Aceptar",
                                                                        new DialogInterface.OnClickListener() {

                                                                            public void onClick(DialogInterface dialog, int which) {


                                                                                Intent intentMonitoreo = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                                                startActivity(intentMonitoreo);
                                                                                PedidoActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                                                                        R.anim.anim_slide_out_left);
                                                                                finish();

                                                                            }
                                                                        });

                                                                // Remember, create doesn't show the dialog
                                                                android.app.AlertDialog msgAceptarPedido83 = MsgAceptarPedido83.create();
                                                                msgAceptarPedido83.show();

                                                                break;

                                                            default:
                                                                FncMostrarToast(getString(R.string.message_error_interno));
                                                                break;
                                                        }

                                                    }
                                                });

                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                // Log.e("CancelarTaxiMsg", e.toString());
                                            }


                                        }
                                    };
                                    ntAceptarPedido3.start();

                                    dialog.dismiss();

                                    break;





                                case 4:

                                    final ProgressDialog PrgAceptarPedido4 = new ProgressDialog(PedidoActualActivity.this);
                                    PrgAceptarPedido4.setIcon(R.mipmap.ic_launcher);
                                    PrgAceptarPedido4.setMessage("Cargando...");
                                    PrgAceptarPedido4.setCancelable(false);
                                    PrgAceptarPedido4.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    PrgAceptarPedido4.show();

                                    //TAREA CANCELAR TAXI - INICIO
                                    Thread ntAceptarPedido4 = new Thread() {
                                        @Override
                                        public void run() {

                                            try {

                                                final String resMtdEnviarPedidoMensajeCentral;
                                                resMtdEnviarPedidoMensajeCentral = MtdEnviarPedidoMensajeCentral(PedidoId,"X-22 Central");

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        String JsRespuesta = "";
                                                        String JsPedidoId = "";

                                                        try {

                                                            JSONObject jsonObject = new JSONObject(resMtdEnviarPedidoMensajeCentral);
                                                            JsRespuesta = jsonObject.getString("Respuesta");

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        PrgAceptarPedido4.cancel();

                                                        switch (JsRespuesta) {
                                                            case "C107":
                                                                FncMostrarToast("Mensaje enviado...");
                                                                break;

                                                            case "C108":
                                                                FncMostrarMensaje("No se ha podido enviar el mensaje, intente nuevamente", false);
                                                                break;
                                                            case "C109":

                                                                //FncMostrarMensaje("No se ha podido identificar el servicio, proceso cancelado", true);
                                                                android.app.AlertDialog.Builder MsgAceptarPedido83 = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                                                                MsgAceptarPedido83.setTitle(getString(R.string.alert_title));
                                                                MsgAceptarPedido83.setMessage("No se ha podido identificar el servicio, se reiniciara la aplicación");
                                                                MsgAceptarPedido83.setCancelable(false);
                                                                MsgAceptarPedido83.setPositiveButton("Aceptar",
                                                                        new DialogInterface.OnClickListener() {

                                                                            public void onClick(DialogInterface dialog, int which) {


                                                                                Intent intentMonitoreo = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                                                startActivity(intentMonitoreo);
                                                                                PedidoActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                                                                        R.anim.anim_slide_out_left);
                                                                                finish();

                                                                            }
                                                                        });

                                                                // Remember, create doesn't show the dialog
                                                                android.app.AlertDialog msgAceptarPedido83 = MsgAceptarPedido83.create();
                                                                msgAceptarPedido83.show();

                                                                break;

                                                            default:
                                                                FncMostrarToast(getString(R.string.message_error_interno));
                                                                break;
                                                        }

                                                    }
                                                });

                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                // Log.e("CancelarTaxiMsg", e.toString());
                                            }


                                        }
                                    };
                                    ntAceptarPedido4.start();

                                    dialog.dismiss();

                                    break;
                            }

                        }
                    });
            //levelDialog = MsgAceptarPedido.create();
            //levelDialog.show();

            android.support.v7.app.AlertDialog msgNavegacion = MsgAceptarPedido.create();
            msgNavegacion.show();

       // }else{

       //     FncMostrarMensaje("La funcion MENSAJE no esta disponible para este pedido",false);

      //  }



    }




    public void onClick_BtnPedidoActualMensajear(View v) {

        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX !=null && PedidoCoordenadaY != null) {

            //ACCIONES
            //final CharSequence[] items = { "Ninguno","Estoy Afuera","Estoy en camino","Ok","Gracias" };
            final CharSequence[] items = { "Ninguno","Estoy Afuera","Ok","Estoy en camino, espere por favor" };

            // int intConductorCanal = Integer.parseInt(ConductorCanal);
            //   intConductorCanal = intConductorCanal -1;

            // Creating and Building the Dialog
            android.support.v7.app.AlertDialog.Builder MsgAceptarPedido = new android.support.v7.app.AlertDialog.Builder(this);
            MsgAceptarPedido.setTitle("Escoja un opcion");
            //MsgAceptarPedido.setCancelable(false);
            MsgAceptarPedido.setSingleChoiceItems(items,0,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            // TextView TxtCanalActual = (TextView) findViewById(R.id.CmpCanalActual);

                            switch (item) {
                                case 0:

                                    dialog.dismiss();

                                    break;
                                case 1:

                                    final ProgressDialog PrgAceptarPedido = new ProgressDialog(PedidoActualActivity.this);
                                    PrgAceptarPedido.setIcon(R.mipmap.ic_launcher);
                                    PrgAceptarPedido.setMessage("Cargando...");
                                    PrgAceptarPedido.setCancelable(false);
                                    PrgAceptarPedido.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    PrgAceptarPedido.show();

                                    //TAREA CANCELAR TAXI - INICIO
                                    Thread ntAceptarPedido1 = new Thread() {
                                        @Override
                                        public void run() {

                                            try {

                                                final String resAceptarPedido;
                                                resAceptarPedido = MtdEnviarPedidoMensajeCliente(PedidoId,"Estoy afuera");

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {



                                                        String JsRespuesta = "";
                                                        String JsPedidoId = "";

                                                        try {

                                                            JSONObject jsonObject = new JSONObject(resAceptarPedido);
                                                            JsRespuesta = jsonObject.getString("Respuesta");

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        PrgAceptarPedido.cancel();

                                                        switch (JsRespuesta) {
                                                            case "C100":
                                                                FncMostrarToast("Mensaje enviado...");
                                                                break;

                                                            case "C101":
                                                                FncMostrarMensaje("No se ha podido enviar el mensaje, intente nuevamente", false);
                                                                break;
                                                            case "C102":

                                                                //FncMostrarMensaje("No se ha podido identificar el servicio, proceso cancelado", true);
                                                                android.app.AlertDialog.Builder MsgAceptarPedido83 = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                                                                MsgAceptarPedido83.setTitle(getString(R.string.alert_title));
                                                                MsgAceptarPedido83.setMessage("No se ha podido identificar el servicio, se reiniciara la aplicación");
                                                                MsgAceptarPedido83.setCancelable(false);
                                                                MsgAceptarPedido83.setPositiveButton("Aceptar",
                                                                        new DialogInterface.OnClickListener() {

                                                                            public void onClick(DialogInterface dialog, int which) {


                                                                                Intent intentMonitoreo = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                                                startActivity(intentMonitoreo);
                                                                                PedidoActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                                                                        R.anim.anim_slide_out_left);
                                                                                finish();

                                                                            }
                                                                        });

                                                                // Remember, create doesn't show the dialog
                                                                android.app.AlertDialog msgAceptarPedido83 = MsgAceptarPedido83.create();
                                                                msgAceptarPedido83.show();

                                                                break;

                                                            default:
                                                                FncMostrarToast(getString(R.string.message_error_interno));
                                                                break;
                                                        }

                                                    }
                                                });

                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                // Log.e("CancelarTaxiMsg", e.toString());
                                            }


                                        }
                                    };
                                    ntAceptarPedido1.start();

                                    dialog.dismiss();

                                    break;


                           /* case 2:

                                final ProgressDialog PrgAceptarPedido2 = new ProgressDialog(PedidoActualActivity.this);
                                PrgAceptarPedido2.setIcon(R.mipmap.ic_launcher);
                                PrgAceptarPedido2.setMessage("Cargando...");
                                PrgAceptarPedido2.setCancelable(false);
                                PrgAceptarPedido2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                PrgAceptarPedido2.show();

                                //TAREA CANCELAR TAXI - INICIO
                                Thread ntAceptarPedido2 = new Thread() {
                                    @Override
                                    public void run() {

                                        try {

                                            final String resAceptarPedido;
                                            resAceptarPedido = MtdEnviarPedidoMensajeCliente(PedidoId,"Estoy en camino.");

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {



                                                    String JsRespuesta = "";
                                                    String JsPedidoId = "";

                                                    try {

                                                        JSONObject jsonObject = new JSONObject(resAceptarPedido);
                                                        JsRespuesta = jsonObject.getString("Respuesta");

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    PrgAceptarPedido2.cancel();

                                                    switch (JsRespuesta) {
                                                        case "C100":
                                                            FncMostrarToast("Mensaje enviado...");
                                                            break;

                                                        case "C101":
                                                            FncMostrarMensaje("No se ha podido enviar el mensaje, intente nuevamente", false);
                                                            break;
                                                        case "C102":

                                                            //FncMostrarMensaje("No se ha podido identificar el servicio, proceso cancelado", true);
                                                            android.app.AlertDialog.Builder MsgAceptarPedido83 = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                                                            MsgAceptarPedido83.setTitle(getString(R.string.alert_title));
                                                            MsgAceptarPedido83.setMessage("No se ha podido identificar el servicio, se reiniciara la aplicación");
                                                            MsgAceptarPedido83.setCancelable(false);
                                                            MsgAceptarPedido83.setPositiveButton("Aceptar",
                                                                    new DialogInterface.OnClickListener() {

                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                            Intent intentMonitoreo = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                                            startActivity(intentMonitoreo);
                                                                            PedidoActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                                                                    R.anim.anim_slide_out_left);
                                                                            finish();

                                                                        }
                                                                    });

                                                            // Remember, create doesn't show the dialog
                                                            android.app.AlertDialog msgAceptarPedido83 = MsgAceptarPedido83.create();
                                                            msgAceptarPedido83.show();

                                                            break;

                                                        default:
                                                            FncMostrarToast(getString(R.string.message_error_interno));
                                                            break;
                                                    }

                                                }
                                            });

                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            // Log.e("CancelarTaxiMsg", e.toString());
                                        }


                                    }
                                };
                                ntAceptarPedido2.start();

                                dialog.dismiss();

                                break;*/

                                case 2:

                                    final ProgressDialog PrgAceptarPedido3 = new ProgressDialog(PedidoActualActivity.this);
                                    PrgAceptarPedido3.setIcon(R.mipmap.ic_launcher);
                                    PrgAceptarPedido3.setMessage("Cargando...");
                                    PrgAceptarPedido3.setCancelable(false);
                                    PrgAceptarPedido3.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    PrgAceptarPedido3.show();

                                    //TAREA CANCELAR TAXI - INICIO
                                    Thread ntAceptarPedido3 = new Thread() {
                                        @Override
                                        public void run() {

                                            try {

                                                final String resAceptarPedido;
                                                resAceptarPedido = MtdEnviarPedidoMensajeCliente(PedidoId,"Ok");

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {



                                                        String JsRespuesta = "";
                                                        String JsPedidoId = "";

                                                        try {

                                                            JSONObject jsonObject = new JSONObject(resAceptarPedido);
                                                            JsRespuesta = jsonObject.getString("Respuesta");

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        PrgAceptarPedido3.cancel();

                                                        switch (JsRespuesta) {
                                                            case "C100":
                                                                FncMostrarToast("Mensaje enviado...");
                                                                break;

                                                            case "C101":
                                                                FncMostrarMensaje("No se ha podido enviar el mensaje, intente nuevamente", false);
                                                                break;
                                                            case "C102":

                                                                //FncMostrarMensaje("No se ha podido identificar el servicio, proceso cancelado", true);
                                                                android.app.AlertDialog.Builder MsgAceptarPedido83 = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                                                                MsgAceptarPedido83.setTitle(getString(R.string.alert_title));
                                                                MsgAceptarPedido83.setMessage("No se ha podido identificar el servicio, se reiniciara la aplicación");
                                                                MsgAceptarPedido83.setCancelable(false);
                                                                MsgAceptarPedido83.setPositiveButton("Aceptar",
                                                                        new DialogInterface.OnClickListener() {

                                                                            public void onClick(DialogInterface dialog, int which) {



                                                                                Intent intentMonitoreo = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                                                startActivity(intentMonitoreo);
                                                                                PedidoActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                                                                        R.anim.anim_slide_out_left);
                                                                                finish();

                                                                            }
                                                                        });

                                                                // Remember, create doesn't show the dialog
                                                                android.app.AlertDialog msgAceptarPedido83 = MsgAceptarPedido83.create();
                                                                msgAceptarPedido83.show();

                                                                break;

                                                            default:
                                                                FncMostrarToast(getString(R.string.message_error_interno));
                                                                break;
                                                        }

                                                    }
                                                });

                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                // Log.e("CancelarTaxiMsg", e.toString());
                                            }


                                        }
                                    };
                                    ntAceptarPedido3.start();

                                    dialog.dismiss();

                                    break;




                                case 3:

                                    final ProgressDialog PrgAceptarPedido4 = new ProgressDialog(PedidoActualActivity.this);
                                    PrgAceptarPedido4.setIcon(R.mipmap.ic_launcher);
                                    PrgAceptarPedido4.setMessage("Cargando...");
                                    PrgAceptarPedido4.setCancelable(false);
                                    PrgAceptarPedido4.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    PrgAceptarPedido4.show();

                                    //TAREA CANCELAR TAXI - INICIO
                                    Thread ntAceptarPedido4 = new Thread() {
                                        @Override
                                        public void run() {

                                            try {

                                                final String resAceptarPedido;
                                                resAceptarPedido = MtdEnviarPedidoMensajeCliente(PedidoId,"Estoy en camino, espere por favor");

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {



                                                        String JsRespuesta = "";
                                                        String JsPedidoId = "";

                                                        try {

                                                            JSONObject jsonObject = new JSONObject(resAceptarPedido);
                                                            JsRespuesta = jsonObject.getString("Respuesta");

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        PrgAceptarPedido4.cancel();

                                                        switch (JsRespuesta) {
                                                            case "C100":
                                                                FncMostrarToast("Mensaje enviado...");
                                                                break;

                                                            case "C101":
                                                                FncMostrarMensaje("No se ha podido enviar el mensaje, intente nuevamente", false);
                                                                break;
                                                            case "C102":

                                                                //FncMostrarMensaje("No se ha podido identificar el servicio, proceso cancelado", true);
                                                                android.app.AlertDialog.Builder MsgAceptarPedido83 = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                                                                MsgAceptarPedido83.setTitle(getString(R.string.alert_title));
                                                                MsgAceptarPedido83.setMessage("No se ha podido identificar el servicio, se reiniciara la aplicación");
                                                                MsgAceptarPedido83.setCancelable(false);
                                                                MsgAceptarPedido83.setPositiveButton("Aceptar",
                                                                        new DialogInterface.OnClickListener() {

                                                                            public void onClick(DialogInterface dialog, int which) {



                                                                                Intent intentMonitoreo = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                                                startActivity(intentMonitoreo);
                                                                                PedidoActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                                                                        R.anim.anim_slide_out_left);
                                                                                finish();

                                                                            }
                                                                        });

                                                                // Remember, create doesn't show the dialog
                                                                android.app.AlertDialog msgAceptarPedido83 = MsgAceptarPedido83.create();
                                                                msgAceptarPedido83.show();

                                                                break;

                                                            default:
                                                                FncMostrarToast(getString(R.string.message_error_interno));
                                                                break;
                                                        }

                                                    }
                                                });

                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                // Log.e("CancelarTaxiMsg", e.toString());
                                            }


                                        }
                                    };
                                    ntAceptarPedido4.start();

                                    dialog.dismiss();

                                    break;



                            /*case 4:

                                final ProgressDialog PrgAceptarPedido4 = new ProgressDialog(PedidoActualActivity.this);
                                PrgAceptarPedido4.setIcon(R.mipmap.ic_launcher);
                                PrgAceptarPedido4.setMessage("Cargando...");
                                PrgAceptarPedido4.setCancelable(false);
                                PrgAceptarPedido4.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                PrgAceptarPedido4.show();

                                //TAREA CANCELAR TAXI - INICIO
                                Thread ntAceptarPedido4 = new Thread() {
                                    @Override
                                    public void run() {

                                        try {

                                            final String resAceptarPedido;
                                            resAceptarPedido = MtdEnviarPedidoMensajeCliente(PedidoId,"Gracias");

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {



                                                    String JsRespuesta = "";
                                                    String JsPedidoId = "";

                                                    try {

                                                        JSONObject jsonObject = new JSONObject(resAceptarPedido);
                                                        JsRespuesta = jsonObject.getString("Respuesta");

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    PrgAceptarPedido4.cancel();

                                                    switch (JsRespuesta) {
                                                        case "C100":
                                                            FncMostrarToast("Mensaje enviado...");
                                                            break;

                                                        case "C101":
                                                            FncMostrarMensaje("No se ha podido enviar el mensaje, intente nuevamente", false);
                                                            break;
                                                        case "C102":

                                                            //FncMostrarMensaje("No se ha podido identificar el servicio, proceso cancelado", true);
                                                            android.app.AlertDialog.Builder MsgAceptarPedido83 = new android.app.AlertDialog.Builder(PedidoActualActivity.this);
                                                            MsgAceptarPedido83.setTitle(getString(R.string.alert_title));
                                                            MsgAceptarPedido83.setMessage("No se ha podido identificar el servicio, se reiniciara la aplicación");
                                                            MsgAceptarPedido83.setCancelable(false);
                                                            MsgAceptarPedido83.setPositiveButton("Aceptar",
                                                                    new DialogInterface.OnClickListener() {

                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                            Intent intentMonitoreo = new Intent(PedidoActualActivity.this, MonitoreoActivity.class);
                                                                            startActivity(intentMonitoreo);
                                                                            PedidoActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                                                                                    R.anim.anim_slide_out_left);
                                                                            finish();

                                                                        }
                                                                    });

                                                            // Remember, create doesn't show the dialog
                                                            android.app.AlertDialog msgAceptarPedido83 = MsgAceptarPedido83.create();
                                                            msgAceptarPedido83.show();

                                                            break;

                                                        default:
                                                            FncMostrarToast(getString(R.string.message_error_interno));
                                                            break;
                                                    }

                                                }
                                            });

                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            // Log.e("CancelarTaxiMsg", e.toString());
                                        }


                                    }
                                };
                                ntAceptarPedido4.start();

                                dialog.dismiss();

                                break;*/

                            }

                        }
                    });
            //levelDialog = MsgAceptarPedido.create();
            //levelDialog.show();

            android.support.v7.app.AlertDialog msgNavegacion = MsgAceptarPedido.create();
            msgNavegacion.show();

        }else{

            FncMostrarMensaje("La funcion MENSAJE no esta disponible para este pedido",false);

        }



    }
    
    


         /*
    ENVIO DE VARIABLES
     */



    public String MtdReportarUbicacion(String oPedidoId,String oCoordenadaX,String oCoordenadaY, String oClienteId,String oVehiculoUnidad) {


        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jncliente));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("PedidoId", oPedidoId);

            postDataParams.put("CoordenadaX", oCoordenadaX);
            postDataParams.put("CoordenadaY", oCoordenadaY);

            postDataParams.put("ClienteId", oClienteId);
            postDataParams.put("VehiculoUnidad", oVehiculoUnidad);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ReportarUbicacion");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }

            Log.e("PedidoActual7", response);

        } catch (Exception e) {

            Log.e("PedidoActual8", e.toString());
            e.printStackTrace();
        }

        return response;

    }



    public String MtdAbordoPedido(String oPedidoId) {


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
            postDataParams.put("PedidoId", oPedidoId);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "AbordoPedido");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }

            Log.e("PedidoActual7", response);

        } catch (Exception e) {

            Log.e("PedidoActual8", e.toString());
            e.printStackTrace();
        }

        return response;

    }

    public String postEnviarPedidoAlerta(String oPedidoId) {


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


            HashMap<String, String>postDataParams=new HashMap<>();
            postDataParams.put("PedidoId", oPedidoId);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "AlertarPedido");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }

            Log.e("PedidoActual5", response);

        } catch (Exception e) {

            Log.e("PedidoActual6", e.toString());
            e.printStackTrace();
        }

        return response;

    }

       public String MtdActualizarConductorVehiculoCoordenada(String oPedidoId,String oConductorId,String oVehiculoCoordenadaX,String oVehiculoCoordenadaY,String oVehiculoUnidad, String oVehiculoPlaca,String oConductorNombre,String oSectorNombre,String oConductorNumeroDocumento,Integer oVehiculoGPSOrientacion) {

        URL url;
        String response = "";

        //Log.e("PedidoActual3", getString(R.string.app_url)+"/webservice/"+getString(R.string.webservice_jnconductor));

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductorubicacion));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String>postDataParams=new HashMap<>();
            postDataParams.put("PedidoId", oPedidoId);

            postDataParams.put("ConductorId", oConductorId);

            postDataParams.put("ConductorNombre", oConductorNombre);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);

            postDataParams.put("VehiculoUnidad", oVehiculoUnidad);
            postDataParams.put("VehiculoPlaca", oVehiculoPlaca);

            postDataParams.put("VehiculoCoordenadaX", oVehiculoCoordenadaX);
            postDataParams.put("VehiculoCoordenadaY", oVehiculoCoordenadaY);
            postDataParams.put("VehiculoGPSOrientacion", String.valueOf(oVehiculoGPSOrientacion));

            postDataParams.put("Sector", oSectorNombre);
            postDataParams.put("Formulario", "PedidoActualActivity");

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
            Log.e("PedidoActual3", response);

        } catch (Exception e) {

            Log.e("PedidoActual4", e.toString());
            e.printStackTrace();
        }

        return response;

    }


    public String MtdObtenerPedido(String oPedidoId) {


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

            HashMap<String, String>postDataParams=new HashMap<>();
            postDataParams.put("PedidoId", oPedidoId);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerPedido");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
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

            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response+=line;
            }

            Log.e("PedidoActual1", response);

        } catch (Exception e) {

            Log.e("PedidoActual2", e.toString());
            e.printStackTrace();
        }

        return response;

    }


    public String MtdObtenerCentralMensajes(String oConductorId,String oCentralMensajeVigente) {

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

            HashMap<String, String>postDataParams=new HashMap<>();
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

            Log.e("PedidoActual201", response);

        } catch (Exception e) {

            Log.e("PedidoActual202", e.toString());
            e.printStackTrace();
        }

        return response;

    }


    public String MtdVerificarPedidoMensajeConductor(String oPedidoId) {

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

            postDataParams.put("PedidoId", oPedidoId);


            postDataParams.put("Identificador", Identificador);
            postDataParams.put("ConductorAppVersion", getString(R.string.app_version));
            postDataParams.put("Accion", "VerificarPedidoMensajeConductor");

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

            Log.e("AceptarPedido11", response);

        } catch (Exception e) {

            Log.e("AceptarPedido12", e.toString());
            e.printStackTrace();
        }

        return response;


    }



    public String MtdEnviarPedidoMensajeCliente(String oPedidoId,String oMensaje) {

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

            postDataParams.put("PedidoId", oPedidoId);
            postDataParams.put("PedidoMensajeCliente", oMensaje);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("ConductorAppVersion", getString(R.string.app_version));
            postDataParams.put("Accion", "EnviarPedidoMensajeCliente");

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

            Log.e("AceptarPedido12", response);

        } catch (Exception e) {

            Log.e("AceptarPedido13", e.toString());
            e.printStackTrace();
        }

        return response;


    }


    public String MtdEnviarPedidoMensajeCentral(String oPedidoId,String oMensaje) {

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

            postDataParams.put("PedidoId", oPedidoId);
            postDataParams.put("PedidoMensajeCliente", oMensaje);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("ConductorAppVersion", getString(R.string.app_version));
            postDataParams.put("Accion", "EnviarPedidoMensajeCentral");

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

            Log.e("AceptarPedido12", response);

        } catch (Exception e) {

            Log.e("AceptarPedido13", e.toString());
            e.printStackTrace();
        }

        return response;


    }


    public String MtdObtenerPublicidadUltimo(String oPublicidadUbicacion) {


        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnpublicidad));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("PublicidadUbicacion", oPublicidadUbicacion);
            postDataParams.put("ClienteAppVersion", getString(R.string.app_version));
            postDataParams.put("Accion", "ObtenerPublicidadUltimo");


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

            Log.e("MsgConductorDato5", response);

        } catch (Exception e) {

            Log.e("MsgConductorDato6", e.toString());
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

            Log.e("PedidoActual10", response);

        } catch (Exception e) {

            Log.e("PedidoActual10", e.toString());
            e.printStackTrace();
        }

        return response;

    }


    /**
     * OTRAS FUNCIONES  Y CLASES
     */


    public void setLocation(Location loc) {


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

        ConductorOcupado = sharedPreferences.getInt("ConductorOcupado",2);
        ConductorCobertura = sharedPreferences.getInt("ConductorCobertura",0);

    }



    private void FncCargarAplicacionParametros() {

        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        AplicacionMonitoreoPedidoContador = sharedPreferences.getInt("AplicacionMonitoreoPedidoContador", 11000);
        AplicacionMonitoreoCoordenadaContador = sharedPreferences.getInt("AplicacionMonitoreoCoordenadaContador", 30000);
        AplicacionMonitoreoAlertaContador = sharedPreferences.getInt("AplicacionMonitoreoAlertaContador", 30000);
        AplicacionMonitoreoMensajeContador = sharedPreferences.getInt("AplicacionMonitoreoAlertaContador", 30000);

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


/*
    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.imageView4);
        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.PedidoActual)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }*/

    /*private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("ErrorImagen", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }*/





/*
    private class CargaImagenesPublicidadArchivo extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(PedidoActualActivity.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            String url = params[0];
            url = (url.replace(" ", "%20"));
            Bitmap imagen = descargarImagen(url);
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            imgPublicidadArchivo.setImageBitmap(result);

            ImageView tempImageView = imgPublicidadArchivo;

            AlertDialog.Builder imageDialog = new AlertDialog.Builder(PedidoActualActivity.this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.pedido_foto,
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


            pDialog.dismiss();
        }

    }
*/


   /* private Bitmap descargarImagen (String imageHttpAddress){
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
    }*/



    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while dow url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }


    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
            }


            try{
                // Drawing polyline in the Google Map for the i-th route
                googleMap.addPolyline(lineOptions);

            } catch (Exception exception){
                // Toast.makeText(getApplicationContext(),
                //   exception.toString(),Toast.LENGTH_SHORT).show();
                FncMostrarToast("Ha ocurrido un error cargando la ruta: "+exception.toString());
            }


        }
    }



     public static int getDistance(double lat_a,double lng_a, double lat_b, double lon_b){
        int Radius = 6371000; //Radio de la tierra
        double lat1 = lat_a / 1E6;
        double lat2 = lat_b / 1E6;
        double lon1 = lng_a / 1E6;
        double lon2 = lon_b / 1E6;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon /2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return (int) (Radius * c);

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


}
