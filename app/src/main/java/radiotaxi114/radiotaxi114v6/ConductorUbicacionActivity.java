package radiotaxi114.radiotaxi114v6;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class ConductorUbicacionActivity extends AppCompatActivity {

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
* A
*/
    private String ConductorIdActual = "";
    private String ConductorNombreActual = "";
    private String ConductorApellidoActual = "";

    private String ConductorTelefonoActual = "";
    private String ConductorCelularActual = "";
    private String ConductorDireccionActual = "";
    private String ConductorNumeroDocumentoActual = "";
    private String ConductorEstadoActual = "";
    private String ConductorEstadoDescripcionActual = "";
    private String ConductorEstadoMotivoActual = "";
    private String ConductorEstadoResponsableActual = "";

    private String ConductorFotoActual = "";
    private String ConductorFotoThumb1Actual = "";
    private String ConductorFotoThumb2Actual = "";

    private String ConductorVehiculoFotoActual = "";
    private String ConductorVehiculoFotoThumb1Actual = "";
    private String ConductorVehiculoFotoThumb2Actual = "";


    private String VehiculoUnidadActual = "";
    private String VehiculoPlacaActual = "";
    private String VehiculoColorActual = "";
    private String VehiculoModeloActual = "";
    private String VehiculoCarroceriaActual = "";

    private String VehiculoCoordenadaXActual = "0.00";
    private String VehiculoCoordenadaYActual = "0.00";
    private int VehiculoGPSOrientacionActual = 0;

    /*
* Variables Principales
*/
    private String ConductorId = "";
    private String ConductorNombre = "";
    private String ConductorTelefono = "";
    private String ConductorCelular = "";
    private String ConductorDireccion = "";
    private String ConductorNumeroDocumento = "";
    private String ConductorEstado = "";
    private String ConductorFoto = "";
    private String ConductorSupervisorNivel = "";

    private String VehiculoUnidad = "";
    private String VehiculoPlaca = "";
    private String VehiculoColor = "";

    private String VehiculoCoordenadaX = "";
    private String VehiculoCoordenadaY = "";
    private int VehiculoGPSOrientacion = 0;


    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;
    /**
     * Variables Timer
     */
    Timer timeConductorUbicacion1;

    /*
    * Variables Mapa
     */
    private GoogleMap googleMap = null;
    private float currentZoom;
    /*
    * Otras Variables
    */
    public String Identificador;

    /*
 * Variables Secundarias
 */

    private Marker vehiculoMarker;
    private Marker vehiculoMarkerActual;


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
        Log.e("ConductorUbicacion20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // timeRadar1.
        Log.e("ConductorUbicacion20", "Resume");

    }


    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ConductorUbicacion20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ConductorUbicacion20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ConductorUbicacion20", "Restart");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ConductorUbicacion20", "Destroy");

        if(timeConductorUbicacion1!=null){
            timeConductorUbicacion1.cancel();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("ConductorUbicacion20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("ConductorUbicacion20", "RestoreInstance");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_ubicacion_activity);
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

        Log.e("ConductorUbicacion20", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_conductor_ubicacion));

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //RECUPERANDO VARIABLES
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        //RECUPERANDO VARIABLES v2
        displayUserSettings();


        ConductorIdActual = intentExtras.getStringExtra("ConductorIdActual");

        ConductorNombreActual = intentExtras.getStringExtra("ConductorNombreActual");
        ConductorApellidoActual = intentExtras.getStringExtra("ConductorApellidoActual");

        ConductorDireccionActual = intentExtras.getStringExtra("ConductorDireccionActual");
        ConductorTelefonoActual = intentExtras.getStringExtra("ConductorTelefonoActual");
        ConductorCelularActual = intentExtras.getStringExtra("ConductorCelularActual");

        ConductorFotoActual = intentExtras.getStringExtra("ConductorFotoActual");
        ConductorFotoThumb1Actual = intentExtras.getStringExtra("ConductorFotoThumb1Actual");
        ConductorFotoThumb2Actual = intentExtras.getStringExtra("ConductorFotoThumb2Actual");

        ConductorVehiculoFotoActual = intentExtras.getStringExtra("ConductorVehiculoFotoActual");
        ConductorVehiculoFotoThumb1Actual = intentExtras.getStringExtra("ConductorVehiculoFotoThumb1Actual");
        ConductorVehiculoFotoThumb2Actual = intentExtras.getStringExtra("ConductorVehiculoFotoThumb2Actual");

        ConductorNumeroDocumentoActual = intentExtras.getStringExtra("ConductorNumeroDocumentoActual");
        ConductorEstadoActual = intentExtras.getStringExtra("ConductorEstadoActual");
        ConductorEstadoDescripcionActual = intentExtras.getStringExtra("ConductorEstadoDescripcionActual");
        ConductorEstadoMotivoActual = intentExtras.getStringExtra("ConductorEstadoMotivoActual");
        ConductorEstadoResponsableActual = intentExtras.getStringExtra("ConductorEstadoResponsableActual");

        VehiculoPlacaActual = intentExtras.getStringExtra("VehiculoPlacaActual");
        VehiculoUnidadActual = intentExtras.getStringExtra("VehiculoUnidadActual");
        VehiculoColorActual = intentExtras.getStringExtra("VehiculoColorActual");
        VehiculoModeloActual = intentExtras.getStringExtra("VehiculoModeloActual");
        VehiculoCarroceriaActual = intentExtras.getStringExtra("VehiculoCarroceriaActual");

        VehiculoCoordenadaXActual = intentExtras.getStringExtra("VehiculoCoordenadaXActual");
        VehiculoCoordenadaYActual = intentExtras.getStringExtra("VehiculoCoordenadaYActual");
        VehiculoGPSOrientacionActual = intentExtras.getIntExtra("VehiculoGPSOrientacionActual",0);

        ConductorSupervisorNivel = intentExtras.getStringExtra("ConductorSupervisorNivel");



/*
        //OBTENIENDO UBICACION
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ConductorUbicacionListener mlocListener = new ConductorUbicacionListener();
        mlocListener.setMainActivity(this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,mlocListener);

        Location lastKnownLocation_byGps =
                locationManager.getLastKnownLocation(gpsLocationProvider);
        Location lastKnownLocation_byNetwork =
                locationManager.getLastKnownLocation(networkLocationProvider);

        if(lastKnownLocation_byGps==null){
            if(lastKnownLocation_byNetwork==null){

            }else{
                VehiculoCoordenadaXActual = Double.toString(lastKnownLocation_byNetwork.getLatitude());
                VehiculoCoordenadaYActual = Double.toString(lastKnownLocation_byNetwork.getLongitude());
            }

        }else{
            if(lastKnownLocation_byNetwork==null){
                VehiculoCoordenadaXActual = Double.toString(lastKnownLocation_byGps.getLatitude());
                VehiculoCoordenadaYActual = Double.toString(lastKnownLocation_byGps.getLongitude());
            }else{
                if(lastKnownLocation_byGps.getAccuracy() <= lastKnownLocation_byNetwork.getAccuracy()){
                    VehiculoCoordenadaXActual = Double.toString(lastKnownLocation_byGps.getLatitude());
                    VehiculoCoordenadaYActual = Double.toString(lastKnownLocation_byGps.getLongitude());
                }else{
                    VehiculoCoordenadaXActual = Double.toString(lastKnownLocation_byNetwork.getLatitude());
                    VehiculoCoordenadaYActual = Double.toString(lastKnownLocation_byNetwork.getLongitude());
                }
            }
        }

*/

/*
        PERMISOS
        */
        context = getApplicationContext();
        activity = this;

        //VARIABLES INICIALES
        currentZoom = 19;
     

        //OBTENER COORDENADAS
        if(checkPermission(2)){
           MtdObtenerCoordenadas();
        }

        //CREANDO MAPA
        if(checkPermission(1)){
            createMapView();
        }



        if(timeConductorUbicacion1!=null){
            timeConductorUbicacion1.cancel();
        }

        //TAREA OBTENER  CONDUCTOR

        //final Timer timer = new Timer();
        timeConductorUbicacion1 = new Timer();
        timeConductorUbicacion1.schedule(new TimerTask() {
            @Override
            public void run() {

                try {

                    final String resObtenerConductor;
                    resObtenerConductor = MtdObtenerConductorUbicacion(ConductorIdActual);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            String JsConductorId = "";
                            String JsVehiculoUnidad = "";
                            String JsVehiculoCoordenadaX = "";
                            String JsVehiculoCoordenadaY = "";
                            String JsConductorActividad = "";
                            String JsVehiculoGPSOrientacion = "";

                            try {

                                JSONObject jsonObject = new JSONObject(resObtenerConductor);
                                JsRespuesta = jsonObject.getString("Respuesta");
                             //   JsConductorId = jsonObject.getString("ConductorId");
                                JsVehiculoUnidad = jsonObject.getString("VehiculoUnidad");

                                JsVehiculoCoordenadaX = jsonObject.getString("VehiculoCoordenadaX");
                                JsVehiculoCoordenadaY = jsonObject.getString("VehiculoCoordenadaY");
                                JsConductorActividad = jsonObject.getString("ConductorActividad");
                                JsVehiculoGPSOrientacion = jsonObject.getString("VehiculoGPSOrientacion");

                                // Double PedidoDistancia = redondear(calculateDistance(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY), Double.parseDouble(JsVehiculoCoordenadaX), Double.parseDouble(JsVehiculoCoordenadaY)), 2);

                                if(!JsVehiculoCoordenadaX.equals("") && !JsVehiculoCoordenadaY.equals("") && !JsVehiculoCoordenadaX.equals("0.00") && !JsVehiculoCoordenadaY.equals("0.00") && JsVehiculoCoordenadaX != null  && JsVehiculoCoordenadaY != null){

                                    if (JsConductorActividad.equals("Inactivo")) {

                                        if(vehiculoMarkerActual!=null){
                                            vehiculoMarkerActual.remove();
                                        }

                                        if (googleMap != null) {

                                            int orientacion = 0;

                                            if(!JsVehiculoGPSOrientacion.equals("")) {
                                                orientacion = Integer.valueOf(JsVehiculoGPSOrientacion);
                                            }

                                            vehiculoMarkerActual = googleMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(Double.parseDouble(JsVehiculoCoordenadaX), Double.parseDouble(JsVehiculoCoordenadaY)))
                                                    .title(JsVehiculoUnidad)
                                                    .draggable(false)
                                                    .rotation(orientacion)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo80)));

                                        }

                                    } else {

                                        if(vehiculoMarkerActual!=null){
                                            vehiculoMarkerActual.remove();
                                        }
                                        if (googleMap != null) {

                                            int orientacion = 0;

                                            if(!JsVehiculoGPSOrientacion.equals("")) {
                                                orientacion = Integer.valueOf(JsVehiculoGPSOrientacion);
                                            }
                                            vehiculoMarkerActual = googleMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(Double.parseDouble(JsVehiculoCoordenadaX), Double.parseDouble(JsVehiculoCoordenadaY)))
                                                    .title(JsVehiculoUnidad)
                                                    .draggable(false)
                                                    .rotation(orientacion)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo80)));

                                        }
                                    }


                                    LatLng latLng = new LatLng(Double.parseDouble(JsVehiculoCoordenadaX),Double.parseDouble(JsVehiculoCoordenadaY));
                                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                            .target(latLng)      // Sets the center of the map to Mountain View
                                            .zoom(currentZoom)                   // Sets the zoom
                                            //.bearing(90)                // Sets the orientation of the camera to east
                                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                            .build();                   // Creates a CameraPosition from the builder
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                }


                            } catch (JSONException e) {
                                Log.e("ConductorUbicacion61", e.toString());
                                e.printStackTrace();
                            }

                            switch (JsRespuesta) {

                                case "S012":
                                    break;

                                case "S013":
                                    break;

                                default:
                                    break;

                            }


                        }
                    });


                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("ConductorUbicacion60", e.toString());
                }

            }
        }, 1000, 2500);
        //TAREA OBTENER  CONDUCTOR
        
        
    }


    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentConductorActual = new Intent(ConductorUbicacionActivity.this, ConductorActualActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("ConductorIdActual", ConductorIdActual);

        bundle.putString("ConductorNombreActual", ConductorNombreActual);
        bundle.putString("ConductorApellidoActual", ConductorApellidoActual);

        bundle.putString("ConductorCelularActual", ConductorCelularActual);
        bundle.putString("ConductorTelefonoActual", ConductorTelefonoActual);
        bundle.putString("ConductorDireccionActual", ConductorDireccionActual);

        bundle.putString("ConductorEstadoActual", ConductorEstadoActual);
        bundle.putString("ConductorEstadoDescripcionActual",ConductorEstadoDescripcionActual);
        bundle.putString("ConductorEstadoMotivoActual", ConductorEstadoMotivoActual);
        bundle.putString("ConductorEstadoResponsableActual", ConductorEstadoResponsableActual);

        bundle.putString("ConductorFotoActual", ConductorFotoActual);
        bundle.putString("ConductorFotoThumb1Actual", ConductorFotoThumb1Actual);
        bundle.putString("ConductorFotoThumb2Actual", ConductorFotoThumb2Actual);

        bundle.putString("ConductorVehiculoFotoActual", ConductorVehiculoFotoActual);
        bundle.putString("ConductorVehiculoFotoThumb1Actual", ConductorVehiculoFotoThumb1Actual);
        bundle.putString("ConductorVehiculoFotoThumb2Actual", ConductorVehiculoFotoThumb2Actual);



        bundle.putString("VehiculoPlacaActual", VehiculoPlacaActual);
        bundle.putString("VehiculoUnidadActual", VehiculoUnidadActual);
        bundle.putString("VehiculoColorActual",VehiculoColorActual);
        bundle.putString("VehiculoModeloActual",VehiculoModeloActual);
        bundle.putString("VehiculoCarroceriaActual",VehiculoCarroceriaActual);

        bundle.putString("VehiculoCoordenadaXActual", VehiculoCoordenadaXActual);
        bundle.putString("VehiculoCoordenadaYActual", VehiculoCoordenadaYActual);
        bundle.putInt("VehiculoGPSOrientacionActual",(VehiculoGPSOrientacionActual));

        bundle.putString("ConductorSupervisorNivel", ConductorSupervisorNivel);

        intentConductorActual.putExtras(bundle);//Put your id to your next Intent
        startActivity(intentConductorActual);

        ConductorUbicacionActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
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

            ConductorUbicacionListener mlocListener = new ConductorUbicacionListener();
            mlocListener.setConductorUbicacionActivity(this);
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
        getMenuInflater().inflate(R.menu.menu_conductor_ubicacion, menu);
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

                Intent intentConductorActual = new Intent(ConductorUbicacionActivity.this, ConductorActualActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("ConductorIdActual", ConductorIdActual);

                bundle.putString("ConductorNombreActual", ConductorNombreActual);
                bundle.putString("ConductorApellidoActual", ConductorApellidoActual);

                bundle.putString("ConductorCelularActual", ConductorCelularActual);
                bundle.putString("ConductorTelefonoActual", ConductorTelefonoActual);
                bundle.putString("ConductorDireccionActual", ConductorDireccionActual);

                bundle.putString("ConductorFotoActual", ConductorFotoActual);
                bundle.putString("ConductorFotoThumb1Actual", ConductorFotoThumb1Actual);
                bundle.putString("ConductorFotoThumb2Actual", ConductorFotoThumb2Actual);

                bundle.putString("ConductorVehiculoFotoActual", ConductorVehiculoFotoActual);
                bundle.putString("ConductorVehiculoFotoThumb1Actual", ConductorVehiculoFotoThumb1Actual);
                bundle.putString("ConductorVehiculoFotoThumb2Actual", ConductorVehiculoFotoThumb2Actual);

                bundle.putString("ConductorEstadoActual", ConductorEstadoActual);
                bundle.putString("ConductorEstadoDescripcionActual",ConductorEstadoDescripcionActual);
                bundle.putString("ConductorEstadoMotivoActual", ConductorEstadoMotivoActual);
                bundle.putString("ConductorEstadoResponsableActual", ConductorEstadoResponsableActual);


                bundle.putString("VehiculoPlacaActual", VehiculoPlacaActual);
                bundle.putString("VehiculoUnidadActual", VehiculoUnidadActual);
                bundle.putString("VehiculoColorActual",VehiculoColorActual);
                bundle.putString("VehiculoModeloActual",VehiculoModeloActual);
                bundle.putString("VehiculoCarroceriaActual",VehiculoCarroceriaActual);

                bundle.putString("VehiculoCoordenadaXActual", VehiculoCoordenadaXActual);
                bundle.putString("VehiculoCoordenadaYActual", VehiculoCoordenadaYActual);
                bundle.putInt("VehiculoGPSOrientacionActual",(VehiculoGPSOrientacionActual));


                bundle.putString("ConductorSupervisorNivel", ConductorSupervisorNivel);

                intentConductorActual.putExtras(bundle);//Put your id to your next Intent
                startActivity(intentConductorActual);

                ConductorUbicacionActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();


                return true;

            /*

            case R.id.action_acerca_de:

                FncMostrarAcercaDe();

                return true;
*/

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

                googleMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map2)).getMap();
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setPadding(0, 0, 0, 0);
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                //GoogleMap.MAP_TYPE_NORMAL
                //GoogleMap.MAP_TYPE_HYBRID
                googleMap.setOnMyLocationButtonClickListener(
                        new GoogleMap.OnMyLocationButtonClickListener() {


                            @Override
                            public boolean onMyLocationButtonClick() {


                                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                                    if(vehiculoMarker!=null){
                                        vehiculoMarker.remove();
                                    }

                                    vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                            .title(VehiculoUnidad)
                                            .draggable(false)
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


/*
                                Location location = googleMap.getMyLocation();

                                VehiculoCoordenadaX = Double.toString(location.getLatitude());
                                VehiculoCoordenadaY = Double.toString(location.getLongitude());

                                // if(!VehiculoCoordenadaX.equals("") & !VehiculoCoordenadaY.equals("")){
                                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                                    if(vehiculoMarker!=null){
                                        vehiculoMarker.remove();
                                    }

                                    vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                            .title("¡Aquì estoy!")
                                            .draggable(true)
                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.taxi114)));


                                    LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                            .target(latLng)      // Sets the center of the map to Mountain View
                                            .zoom(19)                   // Sets the zoom
                                            //.bearing(90)                // Sets the orientation of the camera to east
                                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                            .build();                   // Creates a CameraPosition from the builder
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                }*/


                                return false;
                            }
                        }


                );

                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {

                        VehiculoCoordenadaX = Double.toString(location.getLatitude());
                        VehiculoCoordenadaY = Double.toString(location.getLongitude());
                        Float direction = location.getBearing();

                        if(direction.intValue()>0){
                            VehiculoGPSOrientacion =  (direction.intValue());
                        }

                        if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                            if(vehiculoMarker!=null){
                                vehiculoMarker.remove();
                            }

                            vehiculoMarker = googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                    .title(VehiculoUnidad)
                                    .draggable(false)
                                    .rotation(VehiculoGPSOrientacion)
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo_yo100)));



                              /*  LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng)      // Sets the center of the map to Mountain View
                                        .zoom(19)                   // Sets the zoom
                                        //.bearing(90)                // Sets the orientation of the camera to east
                                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/


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
                            .title(VehiculoUnidad)
                            .draggable(false)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo_yo100)));


                }

                if(!VehiculoCoordenadaXActual.equals("") && !VehiculoCoordenadaYActual.equals("") && !VehiculoCoordenadaXActual.equals("0.00") && !VehiculoCoordenadaYActual.equals("0.00") && VehiculoCoordenadaXActual != null  && VehiculoCoordenadaYActual != null){

                    if(vehiculoMarkerActual!=null){
                        vehiculoMarkerActual.remove();
                    }

                    vehiculoMarkerActual = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(VehiculoCoordenadaXActual), Double.parseDouble(VehiculoCoordenadaYActual)))
                            .title(VehiculoUnidadActual)
                            .draggable(false)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_carro_nuevo80)));

                }

                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                    LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng)      // Sets the center of the map to Mountain View
                            .zoom(13)                   // Sets the zoom
                            //.bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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
        }
    }




    public void onClick_BtnConductorUbicacionRegresar(View v){

        Intent intent = new Intent(ConductorUbicacionActivity.this, ConductorActualActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("ConductorIdActual", ConductorIdActual);

        bundle.putString("ConductorNombreActual", ConductorNombreActual);
        bundle.putString("ConductorApellidoActual", ConductorApellidoActual);

        bundle.putString("ConductorCelularActual", ConductorCelularActual);
        bundle.putString("ConductorTelefonoActual", ConductorTelefonoActual);
        bundle.putString("ConductorDireccionActual", ConductorDireccionActual);

        bundle.putString("ConductorFotoActual", ConductorFotoActual);
        bundle.putString("ConductorFotoThumb1Actual", ConductorFotoThumb1Actual);
        bundle.putString("ConductorFotoThumb2Actual", ConductorFotoThumb2Actual);

        bundle.putString("ConductorVehiculoFotoActual", ConductorVehiculoFotoActual);
        bundle.putString("ConductorVehiculoFotoThumb1Actual", ConductorVehiculoFotoThumb1Actual);
        bundle.putString("ConductorVehiculoFotoThumb2Actual", ConductorVehiculoFotoThumb2Actual);


        bundle.putString("ConductorEstadoActual", ConductorEstadoActual);
        bundle.putString("ConductorEstadoDescripcionActual",ConductorEstadoDescripcionActual);
        bundle.putString("ConductorEstadoMotivoActual", ConductorEstadoMotivoActual);
        bundle.putString("ConductorEstadoResponsableActual", ConductorEstadoResponsableActual);



        bundle.putString("VehiculoPlacaActual", VehiculoPlacaActual);
        bundle.putString("VehiculoUnidadActual", VehiculoUnidadActual);
        bundle.putString("VehiculoColorActual",VehiculoColorActual);
        bundle.putString("VehiculoModeloActual",VehiculoModeloActual);
        bundle.putString("VehiculoCarroceriaActual",VehiculoCarroceriaActual);

        bundle.putString("VehiculoCoordenadaXActual", VehiculoCoordenadaXActual);
        bundle.putString("VehiculoCoordenadaYActual", VehiculoCoordenadaYActual);
        bundle.putInt("VehiculoGPSOrientacionActual",(VehiculoGPSOrientacionActual));

        bundle.putString("ConductorSupervisorNivel", ConductorSupervisorNivel);

        intent.putExtras(bundle);//Put your id to your next Intent
        startActivity(intent);
        finish();



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

        Toast.makeText(ConductorUbicacionActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();
    }




    public String MtdObtenerConductorUbicacion(String oConductorId) {

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

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("Accion", "ObtenerConductorVehiculoCoordenada");

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

            Log.e("ConductorUbicacion7", response);

        } catch (Exception e) {

            Log.e("ConductorUbicacion8", e.toString());
            e.printStackTrace();
        }

        return response;



    }


/*
    public String MtdObtenerConductorUbicacion(String oConductorId) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/JnConductorSupervisor.php");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("ConductorId", oConductorId);
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

            Log.e("ConductorUbicacion7", response);

        } catch (Exception e) {

            Log.e("ConductorUbicacion8", e.toString());
            e.printStackTrace();
        }

        return response;



    }
*/


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

        ConductorOcupado = sharedPreferences.getInt("ConductorOcupado",2);
        ConductorCobertura = sharedPreferences.getInt("ConductorCobertura",0);

    }


    /*private void FncCargarAplicacionParametros() {

        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        AplicacionMonitoreoPedidoContador = sharedPreferences.getInt("AplicacionMonitoreoPedidoContador", 9000);
        AplicacionMonitoreoCoordenadaContador = sharedPreferences.getInt("AplicacionMonitoreoCoordenadaContador", 30000);
        AplicacionMonitoreoAlertaContador = sharedPreferences.getInt("AplicacionMonitoreoAlertaContador", 30000);

        AplicacionRadarConductorContador = sharedPreferences.getInt("AplicacionRadarConductorContador", 50000);
        AplicacionRadarCoordenadaContador = sharedPreferences.getInt("AplicacionRadarCoordenadaContador", 10000);

        AplicacionPedidoActualCoordenadaContador = sharedPreferences.getInt("AplicacionPedidoActualCoordenadaContador", 10000);
        AplicacionPedidoActualPedidoContador = sharedPreferences.getInt("AplicacionPedidoActualPedidoContador", 30000);


    }*/


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




    public void setLocation(Location loc) {


    }

    
}
