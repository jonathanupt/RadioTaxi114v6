package radiotaxi114.radiotaxi114v6;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PedidoDetalleActivity extends AppCompatActivity {

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

    private String PedidoReferencia = "";
    private String PedidoDetalle = "";
    private String PedidoCambioDe = "";
    private String PedidoOperadora = "";
    private String PedidoAnexo = "";
    private String PedidoInfo = "";

    private String PedidoFoto = "";

    private String PedidoOrigen = "";
    private String PedidoTipo = "";

    private String PedidoCoordenadaX = "0.00";
    private String PedidoCoordenadaY = "0.00";

    private String PedidoTiempo = "-";
    private String PedidoDistancia = "-";

    private String PedidoTipoUnidad = "-";

    private String ClienteCelular = "";
    private String ClienteTelefono = "";
    private String ClienteFoto = "";

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

    /*
* Otras Variables
*/
    private String Identificador = "";

    /*
* Otras Variables
*/

    private Marker pedidoMarker;
    private Marker vehiculoMarker;

    private GoogleMap googleMap;

    Timer timerPedidoCliente1;


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

    Timer timerPedidoDetalle1;

    private boolean MostrarMensaje = true;
    //ivate boolean MostrarMensaje2 = true;

    private boolean PresionadoBoton = true;



    TextView txtPedidoTiempo;
    TextView txtPedidoDistancia;



    MediaPlayer mediaPlayerError;
    MediaPlayer mediaPlayerAceptado;

    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("PedidoDetalle20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("PedidoDetalle20", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("PedidoDetalle20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("PedidoDetalle20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("PedidoDetalle20", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("PedidoDetalle20", "Destroy");

        if(timerPedidoDetalle1!=null) {
            timerPedidoDetalle1.cancel();
        }




        if(mediaPlayerError!=null) {
            mediaPlayerError.stop();
        }

        if(mediaPlayerAceptado!=null) {
            mediaPlayerAceptado.stop();
        }

        MostrarMensaje = false;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("PedidoDetalle20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("PedidoDetalle20", "RestoreInstance");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_detalle_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Log.e("PedidoDetalle20", "onCreate");


        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_pedido_detalle));

        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.mipmap.ic_launcher);
        // actionBar.setTitle(getString(R.string.app_title));
        //actionBar.setSubtitle("Info del Pedido");

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //RECUPERANDO VARIABLES v2
        displayUserSettings();

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

        PedidoTiempo = intentExtras.getStringExtra("PedidoTiempo");
        PedidoDistancia = intentExtras.getStringExtra("PedidoDistancia");

        PedidoTipoUnidad = intentExtras.getStringExtra("PedidoTipoUnidad");
        PedidoTipo = intentExtras.getStringExtra("PedidoTipo");

        PedidoCoordenadaX = intentExtras.getStringExtra("PedidoCoordenadaX");
        PedidoCoordenadaY = intentExtras.getStringExtra("PedidoCoordenadaY");

        VehiculoCoordenadaX = intentExtras.getStringExtra("VehiculoCoordenadaX");
        VehiculoCoordenadaY = intentExtras.getStringExtra("VehiculoCoordenadaY");

        ClienteCelular = intentExtras.getStringExtra("ClienteCelular");
        ClienteTelefono = intentExtras.getStringExtra("ClienteTelefono");
        //ClienteFoto = intentExtras.getStringExtra("ClienteFoto");
        //ConductorId = intentExtras.getStringExtra("ConductorId");

        //if(PedidoId.equals("")) {
        //    FncMostrarMensaje("Ha ocurrido un error, no hemos podido identificar el pedido, ingrese nuevamente a la aplicación.",true);
        //}

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        //COORDENADAS
        //VehiculoCoordenadaX = sharedPreferences2.getString("VehiculoCoordenadaX", "0.00");
        //VehiculoCoordenadaY = sharedPreferences2.getString("VehiculoCoordenadaY", "0.00");

        /*
        PERMISOS
        */
        context = getApplicationContext();
        activity = this;

        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        //VARIABLES INICIALES

        MostrarMensaje = true;
        //MostrarMensaje2 = true;

        PresionadoBoton = false;

        //FORMULARIO
        TextView TxtClienteNombre = (TextView) findViewById(R.id.CmpClienteNombre);
        TextView TxtPedidoDireccion = (TextView) findViewById(R.id.CmpSoporteContactoCelular);
        TextView TxtPedidoReferencia = (TextView) findViewById(R.id.CmpPedidoReferencia);
        TextView TxtPedidoDetalle = (TextView) findViewById(R.id.CmpPedidoDetalle);
        TextView TxtPedidoCambioDe = (TextView) findViewById(R.id.CmpPedidoCambioDe);

        TextView TxtPedidoTipoUnidad = (TextView) findViewById(R.id.CmpPedidoTipoUnidad);
        //TextView TxtPedidoTiempo = (TextView) findViewById(R.id.CmpPedidoTiempo);
//        TextView TxtPedidoDistancia = (TextView) findViewById(R.id.CmpPedidoDistancia);

        txtPedidoTiempo = (TextView)  findViewById(R.id.CmpPedidoTiempo);
        txtPedidoDistancia = (TextView)  findViewById(R.id.CmpPedidoDistancia);


        //TextView TxtClienteTelefono = (TextView) findViewById(R.id.CmpClienteTelefono);
        // TextView TxtClienteCelular = (TextView) findViewById(R.id.CmpClienteCelular);

        TxtClienteNombre.setText(ClienteNombre);

        TxtPedidoDireccion.setText(PedidoDireccion + " " + PedidoNumero);
        TxtPedidoReferencia.setText(PedidoReferencia);
        TxtPedidoDetalle.setText(PedidoDetalle);
        TxtPedidoCambioDe.setText(PedidoCambioDe);

        //TxtPedidoTiempo.setText(PedidoTiempo);
        //TxtPedidoDistancia.setText(PedidoDistancia);
        TxtPedidoTipoUnidad.setText(PedidoTipoUnidad);


/*        if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX != null  && PedidoCoordenadaY != null && !PedidoCoordenadaX.equals("0") && !PedidoCoordenadaY.equals("0")){

            Button btnVerUbicacion1 = (Button)findViewById(R.id.BtnVerUbicacion);
            btnVerUbicacion1.setBackgroundResource(R.drawable.dra_boton_fondo);
            //tiny.setText("Ocupado");

        }else {

            Button btnVerUbicacion2 = (Button)findViewById(R.id.BtnVerUbicacion);
            btnVerUbicacion2.setBackgroundResource(R.drawable.dra_boton_fondo2);
            //tiny2.setText("Desocupado");

        }*/


        //OBTENER COORDENADAS
        if(checkPermission(2)){
            MtdObtenerCoordenadas();
        }

        //CREANDO MAPA
        if(checkPermission(1)){
            createMapView();
        }

        //CAPAS
        LinearLayout capReferencia = (LinearLayout) findViewById(R.id.CapPedidoDetalleReferencia);
        LinearLayout capDetalle = (LinearLayout) findViewById(R.id.CapPedidoDetalleDetalle);
        LinearLayout capCambioDe = (LinearLayout) findViewById(R.id.CapPedidoDetalleCambioDe);

        LinearLayout capTipoUnidad = (LinearLayout) findViewById(R.id.CapPedidoDetalleTipoUnidad);
        LinearLayout capPedidoTiempo = (LinearLayout) findViewById(R.id.CapPedidoDetalleTiempo);
        LinearLayout capPedidoDistancia = (LinearLayout) findViewById(R.id.CapPedidoDetalleDistancia);

        //MOSTRANDO CAPAS
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

        if(!PedidoTiempo.equals("") &&  !PedidoTiempo.equals("-") && PedidoTiempo != null && !PedidoTiempo.equals("null") && !PedidoTiempo.equals("0.00") && !PedidoTiempo.equals("0")  ){
            capPedidoTiempo.setVisibility(View.VISIBLE);
        }else{
            capPedidoTiempo.setVisibility(View.GONE);
        }

        if(!PedidoDistancia.equals("") && !PedidoDistancia.equals("-") && PedidoDistancia != null && !PedidoDistancia.equals("null") &&  !PedidoDistancia.equals("0.00") && !PedidoDistancia.equals("0") ){
            capPedidoDistancia.setVisibility(View.VISIBLE);
        }else{
            capPedidoDistancia.setVisibility(View.GONE);
        }


        //CALCULANDO DISTANCIAS
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

        //if(mediaPlayerError!=null) {
        //    mediaPlayerError.release();
        //}

        //if(mediaPlayerAceptado!=null) {
       //     mediaPlayerAceptado.release();
       // }

        mediaPlayerError = MediaPlayer.create(context, R.raw.computer_error);
        mediaPlayerAceptado= MediaPlayer.create(context, R.raw.bell_ring);
/*
        mediaPlayerError.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });

        mediaPlayerAceptado.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });

        */
       // mediaPlayerError.start();




        if(timerPedidoDetalle1!=null) {
            timerPedidoDetalle1.cancel();
        }

        timerPedidoDetalle1 = new Timer();
        timerPedidoDetalle1.schedule(new TimerTask() {
            @Override
            public void run() {

                // final Timer timer = new Timer();
                //timer.schedule(new TimerTask(){
                //     @Override
                //     public void run() {
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
                            String JsPedidoVigente = "";

                            try {

                                JSONObject jsonObject = new JSONObject(resMtdObtenerPedido);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsPedidoEstado = jsonObject.getString("PedidoEstado");
                                JsPedidoCancelarMotivo = jsonObject.getString("PedidoCancelarMotivo");
                                JsVehiculoUnidad = jsonObject.getString("VehiculoUnidad");
                                JsPedidoVigente = jsonObject.getString("PedidoVigente");

                            } catch (JSONException e) {
                                //Log.e("MsgPedidoActual", e.toString());
                                e.printStackTrace();
                            }

                            switch (JsRespuesta) {

                                case "P013":

                                    if(JsPedidoEstado.equals("3")){

                                        if(timerPedidoDetalle1!=null) {
                                            timerPedidoDetalle1.cancel();
                                        }



                                            //MediaPlayer player1 = MediaPlayer.create(PedidoDetalleActivity.this,R.raw.computer_error);
                                            //player1 = MediaPlayer.create(PedidoDetalleActivity.this, R.raw.computer_error);
                                            //player1.start();

                                        if(!mediaPlayerError.isPlaying()){
                                            mediaPlayerError.start();
                                        }

                                        if(MostrarMensaje){

                                          //  MostrarMensaje2 = false;

//                                        FncMostrarMensaje("El PEDIDO fue ANULADO por el CLIENTE.",true);
                                            AlertDialog.Builder MsgPedidoDetalle1 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                            MsgPedidoDetalle1.setTitle(getString(R.string.alert_title));
                                            MsgPedidoDetalle1.setCancelable(false);
                                            MsgPedidoDetalle1.setMessage("El pedido fue anulado.");
                                        /*MsgPedidoDetalle1.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });
*/
                                            // Remember, create doesn't show the dialog
                                            final AlertDialog msgPedidoDetalle1 = MsgPedidoDetalle1.create();
                                            msgPedidoDetalle1.show();

                                            final Timer timer1 = new Timer();
                                            timer1.schedule(new TimerTask() {
                                                public void run() {

                                                    // msgPedidoDetalle1.dismiss();
                                                    if(msgPedidoDetalle1!=null && msgPedidoDetalle1.isShowing()){
                                                        msgPedidoDetalle1.dismiss();
                                                    }

                                                    timer1.cancel(); //this will cancel the timer of the system
                                                    Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }, 1500); // the timer will count 5 seconds....


                                        }/*else{

                                            Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }*/



                                    }else if(JsPedidoEstado.equals("1")){


                                    }else if(!JsVehiculoUnidad.equals("") && !JsVehiculoUnidad.equals(VehiculoUnidad)){

                                        if(timerPedidoDetalle1!=null) {
                                            timerPedidoDetalle1.cancel();
                                        }



                                           // MediaPlayer player2 = MediaPlayer.create(PedidoDetalleActivity.this,R.raw.computer_error);
                                           // player2 = MediaPlayer.create(PedidoDetalleActivity.this, R.raw.computer_error);
                                           // player2.start();

                                            if(!mediaPlayerError.isPlaying()){
                                                mediaPlayerError.start();
                                            }

                                            String Mensaje = "(1) EL pedido ya fue atendido.";

                                            if(null != JsVehiculoUnidad && !JsVehiculoUnidad.equals("")){
                                                //Mensaje += " Por: |"+JsVehiculoUnidad + "| / |"+VehiculoUnidad+"|";
                                                Mensaje += " Por: "+JsVehiculoUnidad + "";
                                            }

                                        if(MostrarMensaje){
//
                                           // MostrarMensaje2 = false;

                                            AlertDialog.Builder MsgPedidoDetalle2 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                            MsgPedidoDetalle2.setTitle(getString(R.string.alert_title));
                                            MsgPedidoDetalle2.setCancelable(false);
                                            MsgPedidoDetalle2.setMessage(Mensaje);
                                       /* MsgPedidoDetalle2.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });*/

                                            // Remember, create doesn't show the dialog
                                            final AlertDialog msgPedidoDetalle2 = MsgPedidoDetalle2.create();
                                            msgPedidoDetalle2.show();

                                            final Timer timer2 = new Timer();
                                            timer2.schedule(new TimerTask() {
                                                public void run() {

                                                    if(msgPedidoDetalle2!=null && msgPedidoDetalle2.isShowing()){
                                                        msgPedidoDetalle2.dismiss();
                                                    }

                                                    timer2.cancel(); //this will cancel the timer of the system

                                                    Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }, 3000); // the timer will count 5 seconds....


                                        }/*else{

                                            Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }*/

                                    }else if(JsPedidoVigente.equals("2")){

                                        if(timerPedidoDetalle1!=null) {
                                            timerPedidoDetalle1.cancel();
                                        }



                                           /// MediaPlayer player3 = MediaPlayer.create(PedidoDetalleActivity.this,R.raw.computer_error);
                                           // player3 = MediaPlayer.create(PedidoDetalleActivity.this, R.raw.computer_error);
                                          //  player3.start();

                                            if(!mediaPlayerError.isPlaying()){
                                                mediaPlayerError.start();
                                            }


                                        if(MostrarMensaje) {

                                            //MostrarMensaje2 = false;

                                            AlertDialog.Builder MsgPedidoDetalle3 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                            MsgPedidoDetalle3.setTitle(getString(R.string.alert_title));
                                            MsgPedidoDetalle3.setCancelable(false);
                                            MsgPedidoDetalle3.setMessage("El pedido ya no se encuentra disponible.");
                                       /* MsgPedidoDetalle3.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });*/

                                            // Remember, create doesn't show the dialog
                                            final AlertDialog msgPedidoDetalle3 = MsgPedidoDetalle3.create();
                                            msgPedidoDetalle3.show();

                                            final Timer timer3 = new Timer();
                                            timer3.schedule(new TimerTask() {
                                                public void run() {

                                                    if(msgPedidoDetalle3!=null && msgPedidoDetalle3.isShowing()){
                                                        msgPedidoDetalle3.dismiss();
                                                    }
                                                    timer3.cancel(); //this will cancel the timer of the system

                                                    Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }, 1500); // the timer will count 5 seconds....

                                        }/*else{

                                            Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }*/


                                    }

                                    break;

                                case "P014":


                                    if(MostrarMensaje) {

                                    //    MostrarMensaje2 = false;

                                        AlertDialog.Builder MsgPedidoDetalle3 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                        MsgPedidoDetalle3.setTitle(getString(R.string.alert_title));
                                        MsgPedidoDetalle3.setCancelable(false);
                                        MsgPedidoDetalle3.setMessage("El pedido ya no existe.");
                                       /* MsgPedidoDetalle3.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });*/

                                        // Remember, create doesn't show the dialog
                                        final AlertDialog msgPedidoDetalle3 = MsgPedidoDetalle3.create();
                                        msgPedidoDetalle3.show();

                                        final Timer timer3 = new Timer();
                                        timer3.schedule(new TimerTask() {
                                            public void run() {

                                                if(msgPedidoDetalle3!=null && msgPedidoDetalle3.isShowing()){
                                                    msgPedidoDetalle3.dismiss();
                                                }
                                                timer3.cancel(); //this will cancel the timer of the system

                                                Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }, 1500); // the timer will count 5 seconds....



                                    }/*else{

                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }*/



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
        }, 1000, 7500);
        //TAREA OBTENER PEDIDO ESTADO

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

            PedidoDetalleListener mlocListener = new PedidoDetalleListener();
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

                googleMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map1)).getMap();
                //googleMap.setMyLocationEnabled(true);
                //googleMap.getUiSettings().setZoomControlsEnabled(true);
                //googleMap.setPadding(0, 0, 0, 0);
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setCompassEnabled(false);
                //googleMap.setPadding(padding_right,0, 0, 0 );
                googleMap.setPadding(0,0, 0, 0 );

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

                                    if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX != null  && PedidoCoordenadaY != null) {

                                        if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null) {

                                            Double DbledidoDistancia = redondear(calculateDistance(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY),Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)),2);
                                            Double DblPedidoTiempo = 0.00;

                                            DblPedidoTiempo = redondear((((DbledidoDistancia)/50)*60),2);

                                            String Distancia = DbledidoDistancia.toString()+" km";
                                            String Tiempo = DblPedidoTiempo.toString() + " min";

                                            txtPedidoDistancia.setText(Distancia);
                                            txtPedidoTiempo.setText(Tiempo);

                                        }

                                    }


                                    if(estaConectado(true)){

                                        Thread nt = new Thread() {
                                            @Override
                                            public void run() {

                                                try {

                                                    final String resMtdActualizarConductorVehiculoCoordenada;
                                                    resMtdActualizarConductorVehiculoCoordenada = MtdActualizarConductorVehiculoCoordenada(ConductorId,VehiculoCoordenadaX,VehiculoCoordenadaY,VehiculoUnidad,VehiculoPlaca,ConductorNombre,"",ConductorNumeroDocumento);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            String JsRespuesta = "";

                                                            try {

                                                                JSONObject jsonObject = new JSONObject(resMtdActualizarConductorVehiculoCoordenada);
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

                        if(location!=null) {

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


//
                                if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX != null  && PedidoCoordenadaY != null) {

                                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null) {





                                    Double DbledidoDistancia = redondear(calculateDistance(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY),Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)),2);
                                    Double DblPedidoTiempo = 0.00;

                                    DblPedidoTiempo = redondear((((DbledidoDistancia)/50)*60),2);

                                    String Distancia = DbledidoDistancia.toString()+" km";
                                    String Tiempo = DblPedidoTiempo.toString() + " min";

                                    txtPedidoDistancia.setText(Distancia);
                                    txtPedidoTiempo.setText(Tiempo);

                                }

                            }


                            /*if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("")  && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00")) {
                                if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("")  && PedidoCoordenadaX != null  && PedidoCoordenadaY != null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00")) {

                                    LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                    builder.include(new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)));
                                    builder.include(new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY)));

                                    LatLngBounds bounds = builder.build();

                                    int width = getResources().getDisplayMetrics().widthPixels;
                                    int height = getResources().getDisplayMetrics().heightPixels;
                                    //int height = 200;
                                    int padding = (int) (width * 0.2); // offset from edges of the map 12% of screen

                                    String alto = String.valueOf(height);
                                    String ancho = String.valueOf(width);
                                    String espaci = String.valueOf(padding);

                                    Log.e("DIMEN_ALTO2",alto); //1208
                                    Log.e("DIMEN_ANCHO2",ancho); //720
                                    Log.e("DIMEN_ESPACIO2",espaci); //144

                                    //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, 350, 15);
                                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                                    googleMap.animateCamera(cu);

                                }else{

                                    LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));

                                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                            .target(latLng)      // Sets the center of the map to Mountain View
                                            .zoom(19)                   // Sets the zoom
                                            //.bearing(90)                // Sets the orientation of the camera to east
                                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                            .build();                   // Creates a CameraPosition from the builder
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                }

                            }else{

                                if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("")  && PedidoCoordenadaX != null  && PedidoCoordenadaY != null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00")) {

                                    LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));

                                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                            .target(latLng)      // Sets the center of the map to Mountain View
                                            .zoom(19)                   // Sets the zoom
                                            //.bearing(90)                // Sets the orientation of the camera to east
                                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                            .build();                   // Creates a CameraPosition from the builder
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                }else {
                                    //FncMostrarToast("No se encontraron coordenadas");
                                }

                            }*/



                        }

                    }
                });



                if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("")  && PedidoCoordenadaX != null  && PedidoCoordenadaY != null){

                    if(pedidoMarker!=null){
                        pedidoMarker.remove();
                    }


                    if( PedidoTipo.equals("1") || PedidoTipo.equals("11")) {

                        pedidoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                                .title(ClienteNombre)
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidoa128)));

                    }else if(PedidoTipo.equals("7") || PedidoTipo.equals("10") )  {

                        pedidoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                                .title(ClienteNombre)
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidoc128)));

                    }else if(PedidoTipo.equals("6"))  {

                        pedidoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(PedidoCoordenadaX), Double.parseDouble(PedidoCoordenadaY)))
                                .title(ClienteNombre)
                                .draggable(false)
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
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_pedidoa128)));


                    }


                }

                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("")  && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                    if(vehiculoMarker!=null){
                        vehiculoMarker.remove();
                    }

                   /* if(PedidoTipo.equals("1") || PedidoTipo.equals("11")) {

                        vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                .title("¡Aquì estoy!")
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_taxia35)));

                    }else if(PedidoTipo.equals("7") || PedidoTipo.equals("9") || PedidoTipo.equals("10"))  {

                        vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                .title("¡Aquì estoy!")
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_taxic35)));

                    }else{

                        vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                .title("¡Aquì estoy!")
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_taxib35)));

                    }*/

                    vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                            .title("¡Aquì estoy!")
                            .draggable(false)
                            .rotation(VehiculoOrientacion)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo_yo100)));

                }


                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("")  && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00")) {
                    if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("")  && PedidoCoordenadaX != null  && PedidoCoordenadaY != null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00")) {

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        builder.include(new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)));
                        builder.include(new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY)));

                        LatLngBounds bounds = builder.build();

                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        //int height = 200;
                        int padding = (int) (width * 0.2); // offset from edges of the map 12% of screen

                        String alto = String.valueOf(height);
                        String ancho = String.valueOf(width);
                        String espaci = String.valueOf(padding);

                        Log.e("DIMEN_ALTO1",alto); //1208
                        Log.e("DIMEN_ANCHO1",ancho); //720
                        Log.e("DIMEN_ESPACIO1",espaci); //144

                       // CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, 350, 15);
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                        googleMap.animateCamera(cu);

                    }else{

                        LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng)      // Sets the center of the map to Mountain View
                                .zoom(19)                   // Sets the zoom
                                //.bearing(90)                // Sets the orientation of the camera to east
                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }

                }else{

                    if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("")  && PedidoCoordenadaX != null  && PedidoCoordenadaY != null && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00")) {

                        LatLng latLng = new LatLng(Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY));

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng)      // Sets the center of the map to Mountain View
                                .zoom(19)                   // Sets the zoom
                                //.bearing(90)                // Sets the orientation of the camera to east
                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }else {
                        FncMostrarToast("No se encontraron coordenadas");
                    }

                }



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
            Log.e("GoogleMapError1",exception.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedido_detalle, menu);
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
                PedidoDetalleActivity.this.startActivity(Intent.createChooser(shareIntent, getString(R.string.alert_title)));

                return true;

            case R.id.action_acerca_de:

                FncMostrarAcercaDe();

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

        Toast.makeText(PedidoDetalleActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }

    public void onClick_BtnPedidoDetalleVerMapa(View v){


        /*if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && PedidoCoordenadaX != null  && PedidoCoordenadaY != null){

            Intent intent = new Intent(PedidoDetalleActivity.this, PedidoClienteActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("PedidoId", PedidoId);

            bundle.putString("PedidoDireccion", PedidoDireccion);
            bundle.putString("PedidoNumero", PedidoNumero);
            bundle.putString("PedidoReferencia", PedidoReferencia);
            bundle.putString("PedidoDetalle", PedidoDetalle);
            bundle.putString("PedidoFoto", PedidoFoto);
            bundle.putString("PedidoTipoUnidad", PedidoTipoUnidad);

            bundle.putString("ClienteNombre", ClienteNombre);

            bundle.putString("PedidoCoordenadaX", PedidoCoordenadaX);
            bundle.putString("PedidoCoordenadaY", PedidoCoordenadaY);

            bundle.putString("ClienteCelular", ClienteCelular);
            bundle.putString("ClienteTelefono", ClienteTelefono);

            bundle.putString("VehiculoCoordenadaX", VehiculoCoordenadaX);
            bundle.putString("VehiculoCoordenadaY", VehiculoCoordenadaY);

            intent.putExtras(bundle);//Put your id to your next Intent
            startActivity(intent);
            finish();

        }else {

            FncMostrarMensaje("El mapa no esta disponible para este pedido",false);

        }*/





    }

    public void onClick_BtnPedidoDetalleRechazar(View v){

        if(timerPedidoDetalle1!=null) {
            timerPedidoDetalle1.cancel();
        }

        MostrarMensaje = false;

//        finish();
        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
        startActivity(intent);
        finish();

    }

    public void onClick_BtnPedidoDetalleAceptar(final View v){


        if(estaConectado(true)){

            if(!PresionadoBoton){

               // if(timerPedidoDetalle1!=null) {
               //     timerPedidoDetalle1.cancel();
               // }

                //FncMostrarToast("Usted ha ACEPTADO el pedido");
                final ProgressDialog PrgPedidoDetallle = new ProgressDialog(this);
                PrgPedidoDetallle.setIcon(R.mipmap.ic_launcher);
                PrgPedidoDetallle.setMessage("Cargando...");
                PrgPedidoDetallle.setCancelable(false);
                PrgPedidoDetallle.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                PrgPedidoDetallle.show();

                //////MostrarMensaje = false;

                Thread nt = new Thread() {
                    @Override
                    public void run() {

                        try {

                            final String resPedidoAceptar;
                            resPedidoAceptar = MtdAceptarPedido(PedidoId, ConductorId,VehiculoCoordenadaX,VehiculoCoordenadaY,getString(R.string.app_version));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String JsRespuesta = "";
                                    String JsPedVehiculoUnidad = "";

                                    String JsConSuspensionMotivo = "";
                                    String JsConRetiroMotivo = "";

                                    // String JsPedidoTiempo = "";
                                    // String JsPedidoDistancia = "";

                                    try {

                                        JSONObject jsonObject = new JSONObject(resPedidoAceptar);
                                        JsRespuesta = jsonObject.getString("Respuesta");
                                        JsPedVehiculoUnidad = jsonObject.getString("PedVehiculoUnidad");

                                        JsConSuspensionMotivo = jsonObject.getString("ConSuspensionMotivo");
                                        JsConRetiroMotivo = jsonObject.getString("ConRetiroMotivo");

                                        //JsPedidoTiempo = jsonObject.getString("PedTiempo");
                                        //JsPedidoDistancia = jsonObject.getString("PedDistancia");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    PrgPedidoDetallle.cancel();


                                    switch(JsRespuesta){
                                        case "P009":


                                            PresionadoBoton = false;

                                            if(!PedidoCoordenadaX.equals("") && !PedidoCoordenadaY.equals("") && !PedidoCoordenadaX.equals("0.00") && !PedidoCoordenadaY.equals("0.00") && (null != PedidoCoordenadaX) && (null != PedidoCoordenadaY)) {

                                                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && (VehiculoCoordenadaX != null) && (VehiculoCoordenadaY != null)) {


                                                    try {

                                                        Double DblPedidoDistancia = redondear(calculateDistance(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY),Double.parseDouble(PedidoCoordenadaX),Double.parseDouble(PedidoCoordenadaY)),2);
                                                        Double DblPedidoTiempo = 0.00;

                                                        DblPedidoTiempo = redondear((((DblPedidoDistancia)/50)*60),2);

                                                        PedidoDistancia = DblPedidoDistancia.toString();
                                                        PedidoTiempo = DblPedidoTiempo.toString();


                                                    } catch (Exception exception){
                                                        // Toast.makeText(getApplicationContext(),
                                                        //   exception.toString(),Toast.LENGTH_SHORT).show();
                                                        //FncMostrarToast("Ha ocurrido un error cargando el mapa: "+exception.toString());
                                                        Log.e("GoogleMapError1",exception.toString());
                                                    }

                                                }

                                            }
                                            // FncMostrarMensaje("El PEDIDO fue ACEPTADO correctamente.");

                                            //LANZAR SONIDO
                                            // MediaPlayer player = MediaPlayer.create(PedidoDetalleActivity.this,R.raw.bell_ring);
                                            //player = MediaPlayer.create(PedidoDetalleActivity.this, R.raw.bell_ring);
                                            //  player.start();

                                             if(timerPedidoDetalle1!=null) {
                                                timerPedidoDetalle1.cancel();
                                               }

                                            if(!mediaPlayerAceptado.isPlaying()){
                                                mediaPlayerAceptado.start();
                                            }
                                            //GUARDAR MEMORIA
                                            SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            editor.putString("PedidoId", PedidoId.trim());


                                            editor.putString("ClienteId", ClienteId.trim());
                                            editor.putString("ClienteNombre", ClienteNombre.trim());

                                            editor.putString("PedidoDireccion", PedidoDireccion.trim());
                                            editor.putString("PedidoNumero", PedidoNumero.trim());
                                            editor.putString("PedidoReferencia", PedidoReferencia.trim());
                                            editor.putString("PedidoDetalle", PedidoDetalle.trim());
                                            editor.putString("PedidoCambioDe", PedidoCambioDe.trim());

                                            editor.putString("PedidoOperadora", PedidoOperadora.trim());
                                            editor.putString("PedidoAnexo", PedidoAnexo.trim());
                                            editor.putString("PedidoInfo", PedidoInfo.trim());

                                            editor.putString("PedidoFoto", PedidoFoto.trim());
                                            editor.putString("PedidoTipoUnidad", PedidoTipoUnidad.trim());

                                            editor.putString("PedidoCoordenadaX", PedidoCoordenadaX.trim());
                                            editor.putString("PedidoCoordenadaY", PedidoCoordenadaY.trim());
                                            editor.putString("PedidoTipo", PedidoTipo.trim());

                                            editor.putString("ClienteCelular", ClienteCelular.trim());
                                            editor.putString("ClienteTelefono", ClienteTelefono.trim());

                                            editor.putString("VehiculoCoordenadaX", VehiculoCoordenadaX.trim());
                                            editor.putString("VehiculoCoordenadaY", VehiculoCoordenadaY.trim());

                                            editor.putString("PedidoTiempo", PedidoTiempo);
                                            editor.putString("PedidoDistancia", PedidoDistancia);

                                            editor.putBoolean("ConductorTienePedido", true);
                                            editor.apply();



                                            //CARGANDO ACTIVIDAD
                                            Intent intent = new Intent(PedidoDetalleActivity.this, PedidoActualActivity.class);
                                            Bundle bundle = new Bundle();

                                            bundle.putString("PedidoId", PedidoId);

                                            bundle.putString("ClienteId", ClienteId);
                                            bundle.putString("ClienteNombre", ClienteNombre);

                                            bundle.putString("PedidoDireccion", PedidoDireccion);
                                            bundle.putString("PedidoNumero", PedidoNumero);
                                            bundle.putString("PedidoReferencia", PedidoReferencia);
                                            bundle.putString("PedidoDetalle", PedidoDetalle);
                                            bundle.putString("PedidoCambioDe", PedidoCambioDe);

                                            bundle.putString("PedidoOperadora", PedidoOperadora);
                                            bundle.putString("PedidoAnexo", PedidoAnexo);
                                            bundle.putString("PedidoInfo", PedidoInfo);




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


                                            break;

                                        case "P010":



                                            PresionadoBoton = false;
                                            // MediaPlayer player2 = MediaPlayer.create(PedidoDetalleActivity.this,R.raw.computer_error);
                                            //player2 = MediaPlayer.create(PedidoDetalleActivity.this, R.raw.computer_error);
                                            //  player2.start();
                                            if(!mediaPlayerError.isPlaying()){
                                                mediaPlayerError.start();
                                            }


                                            if(MostrarMensaje){

                                                ////MostrarMensaje = false;

                                                //FncMostrarMensaje("El PEDIDO no pudo ser ACEPTADO.",true);
                                                AlertDialog.Builder MsgPedidoDetalle2 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                                MsgPedidoDetalle2.setTitle(getString(R.string.alert_title));
                                                MsgPedidoDetalle2.setCancelable(false);
                                                MsgPedidoDetalle2.setMessage("El pedido no pudo ser aceptado.");
                                       /* MsgPedidoDetalle2.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });*/

                                                // Remember, create doesn't show the dialog
                                                final AlertDialog msgPedidoDetalle2 = MsgPedidoDetalle2.create();
                                                msgPedidoDetalle2.show();

                                                final Timer timer2 = new Timer();
                                                timer2.schedule(new TimerTask() {
                                                    public void run() {


                                                        if(msgPedidoDetalle2!=null && msgPedidoDetalle2.isShowing()){
                                                            msgPedidoDetalle2.dismiss();
                                                        }


                                                        timer2.cancel(); //this will cancel the timer of the system
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, 1500); // the timer will count 5 seconds....



                                            }/*else{

                                                Intent intent2 = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent2);
                                                finish();

                                            }*/




                                            //if(timerPedidoDetalle1!=null) {
                                            //      timerPedidoDetalle1.cancel();
                                            //   }


                                            break;

                                        case "P011":



                                            PresionadoBoton = false;

                                            //  MediaPlayer player3 = MediaPlayer.create(PedidoDetalleActivity.this,R.raw.computer_error);
                                            //  player3 = MediaPlayer.create(PedidoDetalleActivity.this, R.raw.computer_error);
                                            //    player3.start();

                                            if(!mediaPlayerError.isPlaying()){
                                                mediaPlayerError.start();
                                            }

                                            String Mensaje = "EL pedido ya fue atendido.";

                                            if(null != JsPedVehiculoUnidad && !JsPedVehiculoUnidad.equals("")){
                                                Mensaje += " por: "+JsPedVehiculoUnidad;
                                            }

                                            if(MostrarMensaje){

                                                ////MostrarMensaje = false;

                                                AlertDialog.Builder MsgPedidoDetalle3 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                                MsgPedidoDetalle3.setTitle(getString(R.string.alert_title));
                                                MsgPedidoDetalle3.setCancelable(false);
                                                MsgPedidoDetalle3.setMessage(Mensaje);
                                        /*MsgPedidoDetalle3.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                        });*/


                                                // Remember, create doesn't show the dialog
                                                final AlertDialog msgPedidoDetalle3 = MsgPedidoDetalle3.create();
                                                msgPedidoDetalle3.show();

                                                final Timer timer3 = new Timer();
                                                timer3.schedule(new TimerTask() {
                                                    public void run() {

                                                        if(msgPedidoDetalle3!=null && msgPedidoDetalle3.isShowing()){
                                                            msgPedidoDetalle3.dismiss();
                                                        }

                                                        timer3.cancel(); //this will cancel the timer of the system
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, 3000); // the timer will count 5 seconds....


                                            }/*else{

                                                Intent intent2 = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent2);
                                                finish();

                                    }*/






                                            //  if(timerPedidoDetalle1!=null) {
                                            //     timerPedidoDetalle1.cancel();
                                            //   }

                                            break;

                                        case "P012":



                                            PresionadoBoton = false;

                                            // MediaPlayer player4 = MediaPlayer.create(PedidoDetalleActivity.this,R.raw.computer_error);
                                            // player4 = MediaPlayer.create(PedidoDetalleActivity.this, R.raw.computer_error);
                                            //  player4.start();
                                            if(!mediaPlayerError.isPlaying()){
                                                mediaPlayerError.start();
                                            }
//                                        FncMostrarMensaje("El PEDIDO fue ANULADO por el CLIENTE.",true);


                                            if(MostrarMensaje){

                                                ////MostrarMensaje = false;

                                                AlertDialog.Builder MsgPedidoDetalle4 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                                MsgPedidoDetalle4.setTitle(getString(R.string.alert_title));
                                                MsgPedidoDetalle4.setCancelable(false);
                                                MsgPedidoDetalle4.setMessage("El pedido fue anulado por el cliente.");
                                       /* MsgPedidoDetalle4.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });*/

                                                // Remember, create doesn't show the dialog
                                                final AlertDialog msgPedidoDetalle4 = MsgPedidoDetalle4.create();
                                                msgPedidoDetalle4.show();

                                                final Timer timer4 = new Timer();
                                                timer4.schedule(new TimerTask() {
                                                    public void run() {

                                                        //msgPedidoDetalle4.dismiss();

                                                        if(msgPedidoDetalle4!=null && msgPedidoDetalle4.isShowing()){
                                                            msgPedidoDetalle4.dismiss();
                                                        }

                                                        timer4.cancel(); //this will cancel the timer of the system

                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, 1500); // the timer will count 5 seconds....


                                            }/*else{

                                                Intent intent2 = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent2);
                                                finish();

                                            }
*/



                                            //    if(timerPedidoDetalle1!=null) {
                                            //      timerPedidoDetalle1.cancel();
                                            //   }
                                            break;

                                        case "P013":



                                            PresionadoBoton = false;


                                            //MediaPlayer player5 = MediaPlayer.create(PedidoDetalleActivity.this,R.raw.computer_error);
                                            //player5 = MediaPlayer.create(PedidoDetalleActivity.this, R.raw.computer_error);
                                            //player5.start();

                                            if(!mediaPlayerError.isPlaying()){
                                                mediaPlayerError.start();
                                            }

                                            if(MostrarMensaje){

                                                ////MostrarMensaje = false;

                                                AlertDialog.Builder MsgPedidoDetalle5 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                                MsgPedidoDetalle5.setTitle(getString(R.string.alert_title));
                                                MsgPedidoDetalle5.setCancelable(false);
                                                MsgPedidoDetalle5.setMessage("No se identifico codigo de conductor. Codigo Error: P013");
                                       /* MsgPedidoDetalle5.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });*/

                                                // Remember, create doesn't show the dialog
                                                final AlertDialog msgPedidoDetalle5 = MsgPedidoDetalle5.create();
                                                msgPedidoDetalle5.show();

                                                final Timer timer5 = new Timer();
                                                timer5.schedule(new TimerTask() {
                                                    public void run() {

                                                        //msgPedidoDetalle5.dismiss();

                                                        if(msgPedidoDetalle5!=null && msgPedidoDetalle5.isShowing()){
                                                            msgPedidoDetalle5.dismiss();
                                                        }

                                                        timer5.cancel(); //this will cancel the timer of the system
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, 1500); // the timer will count 5 seconds....


                                            }/*else{

                                                Intent intent2 = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent2);
                                                finish();

                                            }
*/


                                            // if(timerPedidoDetalle1!=null) {
                                            //    timerPedidoDetalle1.cancel();
                                            //}
                                            break;

                                        case "P049":


                                            PresionadoBoton = false;
                                            // if(timerPedidoDetalle1!=null) {
                                            //      timerPedidoDetalle1.cancel();
                                            //  }


                                            //MediaPlayer player6 = MediaPlayer.create(PedidoDetalleActivity.this,R.raw.computer_error);
                                            //player6 = MediaPlayer.create(PedidoDetalleActivity.this, R.raw.computer_error);
                                            // player6.start();

                                            if(!mediaPlayerError.isPlaying()){
                                                mediaPlayerError.start();
                                            }

//
                                            //                                      FncMostrarMensaje("No se identifico CODIGO de PEDIDO",true);

                                            if(MostrarMensaje){

                                                ////MostrarMensaje = false;

                                                AlertDialog.Builder MsgPedidoDetalle6 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                                MsgPedidoDetalle6.setTitle(getString(R.string.alert_title));
                                                MsgPedidoDetalle6.setCancelable(false);
                                                MsgPedidoDetalle6.setMessage("No se identifico codigo de conductor. Codigo Error: P049");
                                      /*  MsgPedidoDetalle6.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });*/

                                                // Remember, create doesn't show the dialog
                                                final AlertDialog msgPedidoDetalle6 = MsgPedidoDetalle6.create();
                                                msgPedidoDetalle6.show();

                                                final Timer timer6 = new Timer();
                                                timer6.schedule(new TimerTask() {
                                                    public void run() {

                                                        //msgPedidoDetalle6.dismiss();

                                                        if(msgPedidoDetalle6!=null && msgPedidoDetalle6.isShowing()){
                                                            msgPedidoDetalle6.dismiss();
                                                        }

                                                        timer6.cancel(); //this will cancel the timer of the system
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, 1500); // the timer will count 5 seconds....


                                            }/*else{

                                                Intent intent2 = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent2);
                                                finish();

                                            }*/



                                            break;


                                        case "P050":

                                            PresionadoBoton = false;


                                            // if(timerPedidoDetalle1!=null) {
                                            //      timerPedidoDetalle1.cancel();
                                            // }

                                            //FncMostrarMensaje("Su cuenta ha sido suspendida \""+VehiculoUnidad+"\".",true);

                                            SharedPreferences.Editor editorP050 = sharedPreferences2.edit();
                                            editorP050.putString("ConductorEstado", "2");
                                            editorP050.apply();


                                            String mensaje50 = "";

                                            mensaje50 = "Su cuenta ha sido suspendida \""+VehiculoUnidad+"\".";

                                            if(!JsConSuspensionMotivo.equals("") && JsConSuspensionMotivo!=null){
                                                mensaje50 = mensaje50 + " "+JsConSuspensionMotivo;
                                            }

                                            if(MostrarMensaje) {

                                                ////MostrarMensaje = false;

                                                AlertDialog.Builder MsgPedidoDetalle7 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                                MsgPedidoDetalle7.setTitle(getString(R.string.alert_title));
                                                MsgPedidoDetalle7.setCancelable(false);
                                                MsgPedidoDetalle7.setMessage(mensaje50);
                                      /*  MsgPedidoDetalle7.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });*/

                                                // Remember, create doesn't show the dialog
                                                final AlertDialog msgPedidoDetalle7 = MsgPedidoDetalle7.create();
                                                msgPedidoDetalle7.show();

                                                final Timer timer7 = new Timer();
                                                timer7.schedule(new TimerTask() {
                                                    public void run() {

                                                        //msgPedidoDetalle7.dismiss();

                                                        if(msgPedidoDetalle7!=null && msgPedidoDetalle7.isShowing()){
                                                            msgPedidoDetalle7.dismiss();
                                                        }

                                                        timer7.cancel(); //this will cancel the timer of the system
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, 1500); // the timer will count 5 seconds....

                                            }/*else{
                                                Intent intent2 = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent2);
                                                finish();
                                            }
*/




                                            break;

                                        case "P052":


                                            PresionadoBoton = false;
                                            //    if(timerPedidoDetalle1!=null) {
                                            //        timerPedidoDetalle1.cancel();
                                            //    }


                                            SharedPreferences.Editor editorP052 = sharedPreferences2.edit();
                                            editorP052.putString("ConductorEstado", "3");
                                            editorP052.apply();

                                            //FncMostrarMensaje("Su cuenta ha sido retirada \""+VehiculoUnidad+"\".",true);
                                            String mensaje52 = "";

                                            mensaje52 = "Su cuenta ha sido retirada \""+VehiculoUnidad+"\".";

                                            if(!JsConRetiroMotivo.equals("") && JsConRetiroMotivo!=null){
                                                mensaje52 = mensaje52 + " "+JsConRetiroMotivo;
                                            }

                                            if(MostrarMensaje) {

                                                ////MostrarMensaje = false;

                                                AlertDialog.Builder MsgPedidoDetalle8 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                                MsgPedidoDetalle8.setTitle(getString(R.string.alert_title));
                                                MsgPedidoDetalle8.setCancelable(false);
                                                MsgPedidoDetalle8.setMessage(mensaje52);
                                      /*  MsgPedidoDetalle8.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });*/

                                                // Remember, create doesn't show the dialog
                                                final AlertDialog msgPedidoDetalle8 = MsgPedidoDetalle8.create();
                                                msgPedidoDetalle8.show();

                                                final Timer timer8 = new Timer();
                                                timer8.schedule(new TimerTask() {
                                                    public void run() {

                                                        //msgPedidoDetalle8.dismiss();

                                                        if(msgPedidoDetalle8!=null && msgPedidoDetalle8.isShowing()){
                                                            msgPedidoDetalle8.dismiss();
                                                        }

                                                        timer8.cancel(); //this will cancel the timer of the system
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, 1500); // the timer will count 5 seconds....

                                            }/*else{

                                                Intent intent2 = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent2);
                                                finish();

                                            }

*/


                                            break;

                                        case "P051":


                                            PresionadoBoton = false;
                                            //    if(timerPedidoDetalle1!=null) {
                                            //        timerPedidoDetalle1.cancel();
                                            //    }

                                            if(MostrarMensaje) {

                                                ////MostrarMensaje = false;

                                                AlertDialog.Builder MsgPedidoDetalle9 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                                MsgPedidoDetalle9.setTitle(getString(R.string.alert_title));
                                                MsgPedidoDetalle9.setMessage("El pedido ya no se encuentra disponible.");

                                                MsgPedidoDetalle9.setCancelable(false);

                                       /* MsgPedidoDetalle9.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });*/

                                                // Remember, create doesn't show the dialog
                                                final AlertDialog msgPedidoDetalle9 = MsgPedidoDetalle9.create();
                                                msgPedidoDetalle9.show();

                                                final Timer timer9 = new Timer();
                                                timer9.schedule(new TimerTask() {
                                                    public void run() {

                                                        //msgPedidoDetalle9.dismiss();

                                                        if(msgPedidoDetalle9!=null && msgPedidoDetalle9.isShowing()){
                                                            msgPedidoDetalle9.dismiss();
                                                        }

                                                        timer9.cancel(); //this will cancel the timer of the system
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, 1500); // the timer will count 5 seconds....


                                            }/*else{

                                                Intent intent2 = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent2);
                                                finish();

                                            }*/





                                            break;

                                        case "P060":

                                            PresionadoBoton = false;
                                            //    if(timerPedidoDetalle1!=null) {
                                            //        timerPedidoDetalle1.cancel();
                                            //    }

                                            if(MostrarMensaje) {

                                                ////MostrarMensaje = false;

                                                AlertDialog.Builder MsgPedidoDetalle10 = new AlertDialog.Builder(PedidoDetalleActivity.this);
                                                MsgPedidoDetalle10.setTitle(getString(R.string.alert_title));
                                                MsgPedidoDetalle10.setMessage("Su cuenta no esta asociado a este equipo.");
                                                MsgPedidoDetalle10.setCancelable(false);
                                        /*MsgPedidoDetalle10.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                });*/

                                                // Remember, create doesn't show the dialog
                                                final AlertDialog msgPedidoDetalle10 = MsgPedidoDetalle10.create();
                                                msgPedidoDetalle10.show();

                                                final Timer timer10 = new Timer();
                                                timer10.schedule(new TimerTask() {
                                                    public void run() {

                                                        //msgPedidoDetalle10.dismiss();

                                                        if(msgPedidoDetalle10!=null && msgPedidoDetalle10.isShowing()){
                                                            msgPedidoDetalle10.dismiss();
                                                        }

                                                        timer10.cancel(); //this will cancel the timer of the system
                                                        Intent intent = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                }, 1500); // the timer will count 5 seconds....


                                            }/*.0else{

                                                Intent intent2 = new Intent(PedidoDetalleActivity.this, MonitoreoActivity.class);
                                                startActivity(intent2);
                                                finish();

                                            }*/


                                            break;

                                        default:

                                            PresionadoBoton = false;

                                            //    if(timerPedidoDetalle1!=null) {
                                            //         timerPedidoDetalle1.cancel();
                                            //    }

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

/*
ENVIO DE VARIABLES
*/

    public String MtdAceptarPedido(String oPedidoId , String oConductorId,String oVehiculoCoordenadaX,String oVehiculoCoordenadaY,String oAppVersion) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnpedido));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("PedidoId", oPedidoId);
            postDataParams.put("ConductorId", oConductorId);

            postDataParams.put("VehiculoCoordenadaX", oVehiculoCoordenadaX);
            postDataParams.put("VehiculoCoordenadaY", oVehiculoCoordenadaY);
            postDataParams.put("AppVersion", oAppVersion);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "AceptarPedido");

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

/*            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
*/
            Log.e("PedidoDetalle1", response);

        } catch (Exception e) {

            Log.e("PedidoDetalle2", e.toString());
            e.printStackTrace();
        }

        return response;
    }

    public String MtdActualizarConductorVehiculoCoordenada(String oConductorId,String oVehiculoCoordenadaX,String oVehiculoCoordenadaY,String oVehiculoUnidad, String oVehiculoPlaca,String oConductorNombre,String oSector,String oConductorNumeroDocumento) {

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

            HashMap<String, String>postDataParams=new HashMap<>();

            postDataParams.put("ConductorId", oConductorId);

            postDataParams.put("ConductorNombre", oConductorNombre);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);

            postDataParams.put("VehiculoUnidad", oVehiculoUnidad);
            postDataParams.put("VehiculoPlaca", oVehiculoPlaca);

            postDataParams.put("VehiculoCoordenadaX", oVehiculoCoordenadaX);
            postDataParams.put("VehiculoCoordenadaY", oVehiculoCoordenadaY);
            postDataParams.put("Sector", oSector);
            postDataParams.put("Formulario", "PedidoDetalleActivity");

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

            Log.e("PedidoCliente5", response);

        } catch (Exception e) {

            Log.e("PedidoCliente6", e.toString());
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

            Log.e("PedidoDetalle1", response);

        } catch (Exception e) {

            Log.e("PedidoDetalle2", e.toString());
            e.printStackTrace();
        }

        return response;

    }


/*
OTRAS FUNCIONES Y CLASES
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
