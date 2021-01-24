package radiotaxi114.radiotaxi114v6;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class IniciarViajeActivity extends AppCompatActivity {


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

    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;

    private String Identificador = "";

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
     * Variables Secundarias
     */
    private String VehiculoCoordenadaX = "0.00";
    private String VehiculoCoordenadaY = "0.00";
    private int VehiculoOrientacion = 0;

    public String SectorNombre = "";

    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;


    /*
     * DATOS GOOGLE MAP
     */
    private GoogleMap googleMap = null;

    private Marker pedidoMarker;
    private Marker vehiculoMarker;
    private float currentZoom;
    private int MapaVistaTipo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_viaje);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        Log.e("Historial20", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_historial));

        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.mipmap.ic_launcher);
        //actionBar.setTitle(getString(R.string.app_title));
        //actionBar.setSubtitle("Historial");

        //IDENTIFICANDO EQUIPO
        //final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        //final String tmDevice, tmSerial, androidId; tmDevice = "" + tm.getDeviceId();
        // tmSerial = "" + tm.getSimSerialNumber();
        // androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        // UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        // Identificador = deviceUuid.toString();

        //VALIDANDO INTERNET
        //if (!AppStatus.getInstance(this).isOnline()) {

        //     FncMostrarToast(getString(R.string.message_no_internet));
        //     finish();
        //    System.exit(0);

        //  }


        //RECUPERANDO VARIABLES
        //Intent intentExtras = getIntent();
        //Bundle extrasBundle = intentExtras.getExtras();

        //ConductorId = intentExtras.getStringExtra("ConductorId");
        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        PedidoId = sharedPreferences2.getString("PedidoId", "");
        ClienteId = sharedPreferences2.getString("ClienteId", "");
        VehiculoCoordenadaX = sharedPreferences2.getString("VehiculoCoordenadaX", "");
        VehiculoCoordenadaY = sharedPreferences2.getString("VehiculoCoordenadaY", "");

        //RECUPERANDO VARIABLES v2
        displayUserSettings();
        

        
    }




    public void onBackPressed() {

        AlertDialog.Builder MsgPedidoActual = new AlertDialog.Builder(this);
        MsgPedidoActual.setTitle(getString(R.string.alert_title));
        MsgPedidoActual.setMessage("¿Realmente desea cancelar el pedido?");
        MsgPedidoActual.setCancelable(false);
        MsgPedidoActual.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        Intent intent = new Intent(IniciarViajeActivity.this, PedidoCancelacionActivity.class);
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

        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_iniciar_viaje, menu);
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

                AlertDialog.Builder MsgPedidoActual = new AlertDialog.Builder(this);
                MsgPedidoActual.setTitle(getString(R.string.alert_title));
                MsgPedidoActual.setMessage("¿Realmente desea cancelar el pedido?");
                MsgPedidoActual.setCancelable(false);
                MsgPedidoActual.setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog

                                Intent intent = new Intent(IniciarViajeActivity.this, PedidoCancelacionActivity.class);
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


                return true;

         

       

            default:
                return super.onOptionsItemSelected(item);
        }
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

                    int result2 = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION);

                    if (result2 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","2AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, permiso);
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
                  //  MtdObtenerCoordenadas();
                    break;

                case 4:
                   // FncLlamar();
                    break;

            }

        } else {
            Log.e("Navegacion10","PERMISO NEGADO");
            FncMostrarToast("Permiso denegado, es posible que la aplicacion no funcione  correctamente.");
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

        Toast.makeText(IniciarViajeActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();
    }


    protected void displayNotificationOne(String oTitulo,String oContenido,String oContenidoGrande) {

        int notificationId = 001;

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(IniciarViajeActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(oTitulo)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(oContenidoGrande))
                        .setContentText(oContenido)
                        //.setVibrate(new long[] { 100, 250 })
                        .setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(IniciarViajeActivity.this);


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





}
