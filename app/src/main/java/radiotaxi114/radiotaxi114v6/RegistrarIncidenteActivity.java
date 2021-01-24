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
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegistrarIncidenteActivity extends AppCompatActivity {


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
    private String ConductorNombre = "";
    private String ConductorTelefono = "";
    private String ConductorCelular = "";
    private String ConductorDireccion = "";
    private String ConductorNumeroDocumento = "";
    private String ConductorEstado = "";
    private String ConductorFoto = "";

    private String VehiculoUnidad = "";
    private String VehiculoPlaca = "";
    private String VehiculoColor = "";

    private String VehiculoCoordenadaX = "";
    private String VehiculoCoordenadaY = "";

    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;

    /*
    IDENTIFICADOR
     */
    private String Identificador = "";

    /*
    Variables Incidente
     */
    private String IncidenteFoto = "";

    /*
    Variables Upload
     */
    private static final String TAG = "upload";


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

    /*
  SUBIR FOTOS
   */
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnRegistrarIncidenteFoto;
    private ImageView imIncidenteFoto;
    private String userChoosenTask;

    private GoogleMap googleMap = null;
    private Marker incidenteMarker;

    //public static final String UPLOAD_URL = "http://simplifiedcoding.16mb.com/ImageUpload/upload.php";
    //public static final String UPLOAD_KEY = "image";
    //public static final String TAG = "MY MESSAGE";

    private static String audioFilePath;

    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("RegistrarIncidente20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("RegistrarIncidente20", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("RegistrarIncidente20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("RegistrarIncidente20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("RegistrarIncidente20", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("RegistrarIncidente20", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("RegistrarIncidente20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("RegistrarIncidente20", "RestoreInstance");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_incidente_activity);
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


        Log.e("RegistrarIncidente20", "onCreate");


        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_registrar_incidente));

        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.mipmap.ic_launcher);
        //actionBar.setTitle(getString(R.string.app_title));
        //actionBar.setSubtitle("Reportar Incidente");

        /*
        PERMISOS
        */
        context = getApplicationContext();
        activity = this;

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


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


        // Button btnRegistarIncidenteFoto = (Button) findViewById(R.id.BtnRegistrarIncidenteFoto);
        //btnRegistarIncidenteFoto.setOnClickListener(new MyClass());

        btnRegistrarIncidenteFoto = (Button) findViewById(R.id.BtnRegistrarIncidenteFoto);
        btnRegistrarIncidenteFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        imIncidenteFoto = (ImageView) findViewById(R.id.ImIncidenteFoto);

    }



    private void selectImage() {
        final CharSequence[] items = { "Tomar Foto", "Escoger de galería",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarIncidenteActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result=Utility.checkPermission(RegistrarIncidenteActivity.this);

                if (items[item].equals("Tomar Foto")) {
                    userChoosenTask ="Tomar Foto";
                    //if(result)
                    //    cameraIntent();
                    if(checkPermission(3)){
                        cameraIntent();
                    }

                } else if (items[item].equals("Escoger de galería")) {
                    userChoosenTask ="Escoger de galería";
                    //if(result)
                    //    galleryIntent();
                    if(checkPermission(4)){
                        cameraIntent();
                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
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

                case 3:

                    int result3 = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA);

                    if (result3 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","2AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, permiso);
                        Log.e("Navegacion10", "2BBB");
                    }

                    break;

                case 4:

                    int result4 = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE);

                    if (result4 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","2AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, permiso);
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

                case 3:
                    cameraIntent();
                    break;


                case 4:
                    galleryIntent();
                    break;


            }

        } else {
            Log.e("Navegacion10","PERMISO NEGADO");
            FncMostrarToast("Permiso denegado, es posible que la aplicacion no funcione  correctamente.");
        }

    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Tomar Foto"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Escoger de galería"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }*/

    public void MtdObtenerCoordenadas() {

        //OBTENIENDO UBICACION
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();

            RegistrarIncidenteListener mlocListener = new RegistrarIncidenteListener();
            mlocListener.setRegistrarIncidenteActivity(this);
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
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setPadding(0, 0, 0, 0);

                googleMap.setOnMyLocationButtonClickListener(
                        new GoogleMap.OnMyLocationButtonClickListener() {

                            @Override
                            public boolean onMyLocationButtonClick() {

                                Location location = googleMap.getMyLocation();

                                VehiculoCoordenadaX = Double.toString(location.getLatitude());
                                VehiculoCoordenadaY = Double.toString(location.getLongitude());

                                // if(!VehiculoCoordenadaX.equals("") & !VehiculoCoordenadaY.equals("")){
                                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                                    incidenteMarker = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                                            .title("¡Aqui Incidente!")
                                            .draggable(true)
                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_incidente150)));


                                    LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                            .target(latLng)      // Sets the center of the map to Mountain View
                                            .zoom(19)                   // Sets the zoom
                                            .bearing(90)                // Sets the orientation of the camera to east
                                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                            .build();                   // Creates a CameraPosition from the builder
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                }



                                return false;
                            }
                        }


                );

                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker arg0) {
                    }

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onMarkerDragEnd(Marker arg0) {
                        Log.d("System out", "onMarkerDragEnd...");

                        LatLng dragPosition = arg0.getPosition();

                        VehiculoCoordenadaX = Double.toString(dragPosition.latitude);
                        VehiculoCoordenadaY = Double.toString(dragPosition.longitude);

                        LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng)      // Sets the center of the map to Mountain View
                                .zoom(17)                   // Sets the zoom
                                .bearing(20)                   // Sets the orientation of the camera to east
                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    }

                    @Override
                    public void onMarkerDrag(Marker arg0) {
                    }
                });

                if(!VehiculoCoordenadaX.equals("") && !VehiculoCoordenadaY.equals("") && !VehiculoCoordenadaX.equals("0.00") && !VehiculoCoordenadaY.equals("0.00") && VehiculoCoordenadaX != null  && VehiculoCoordenadaY != null){

                    incidenteMarker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(VehiculoCoordenadaX), Double.parseDouble(VehiculoCoordenadaY)))
                            .title("¡Aqui Incidente!")
                            .draggable(true)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_incidente150)));

                    LatLng latLng = new LatLng(Double.parseDouble(VehiculoCoordenadaX),Double.parseDouble(VehiculoCoordenadaY));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng)      // Sets the center of the map to Mountain View
                            .zoom(19)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
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
    

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {

        if (data != null) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            imIncidenteFoto.setImageBitmap(thumbnail);

            //uploadImage(thumbnail);

            File destination = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;

            try {

                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();

                IncidenteFoto = System.currentTimeMillis() +".jpg";

                //FncMostrarToast(IncidenteFoto);

                uploadImage(thumbnail);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }







        //  FncMostrarToast(audioFilePath);

      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                //creating new thread to handle Http Operations
                uploadFile(audioFilePath);



            }
        }).start();
*/
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;

        if (data != null) {

            Uri selectedImageURI = data.getData();

            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),selectedImageURI);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                imIncidenteFoto.setImageBitmap(bm);


                File destination = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;

                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();

                IncidenteFoto = System.currentTimeMillis() +".jpg";

                //FncMostrarToast(IncidenteFoto);
                Bitmap bm2 = Bitmap.createScaledBitmap(bm, 500, 600, false);

                uploadImage(bm2);

            } catch (IOException e) {
                e.printStackTrace();
            }




          /*
            try {

                Uri selectedImageURI = data.getData();

                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),selectedImageURI);


                FncMostrarToast(IncidenteFoto);


            } catch (IOException e) {
                e.printStackTrace();
            }

            imIncidenteFoto.setImageBitmap(bm);*/

        }

        // IncidenteFoto = System.currentTimeMillis() +".jpg";

//FncMostrarToast(audioFilePath);

     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                //creating new thread to handle Http Operations
                uploadFile(audioFilePath);



            }
        }).start();*/
    }


    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    private void uploadImage(Bitmap bmp){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegistrarIncidenteActivity.this, "Subiendo archivo", "Por favor espere...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s+"",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put("ArchivoBlob", uploadImage);
                data.put("ArchivoNombre", IncidenteFoto);

                String result = rh.sendPostRequest(getString(R.string.app_url)+"/modulos/IncidenteSubirFoto.php",data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bmp);
    }



    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(RegistrarIncidenteActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        RegistrarIncidenteActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registrar_incidente, menu);
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

                Intent intentMonitoreo = new Intent(RegistrarIncidenteActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                RegistrarIncidenteActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();


            /* case R.id.action_acerca_de:

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

                return true;
*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setLocation(Location loc) {

        //Obtener la direcci—n de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {

            double latitude = loc.getLatitude();
            double longitude = loc.getLongitude();

            VehiculoCoordenadaX = Double.toString(latitude);
            VehiculoCoordenadaY = Double.toString(longitude);
        }
    }

    public void onClick_BtnRegistrarIncidenteCancelar(View v){

        Intent intentMonitoreoActivity = new Intent(RegistrarIncidenteActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreoActivity);
        finish();

    }



    public void onClick_BtnRegistrarIncidenteGuardar(View v){

        final String preIncidenteDescripcion = ((EditText)findViewById(R.id.CmpIncidenteDescripcion)).getText().toString();
        CharSequence chaIncidenteDescripcion = Html.fromHtml(preIncidenteDescripcion);
        final String valIncidenteDescripcion = chaIncidenteDescripcion.toString();

        final String preIncidenteLugar = ((EditText)findViewById(R.id.CmpIncidenteLugar)).getText().toString();
        CharSequence chaIncidenteLugar = Html.fromHtml(preIncidenteLugar);
        final String valIncidenteLugar = chaIncidenteLugar.toString();

        if(valIncidenteDescripcion.equals("")){

            FncMostrarMensaje("No ha ingresado una descripción",false);

        }else{

            final ProgressDialog PrgRegistrarIncidente = new ProgressDialog(this);
            PrgRegistrarIncidente.setIcon(R.mipmap.ic_launcher);
            PrgRegistrarIncidente.setMessage("Cargando...");
            PrgRegistrarIncidente.setCancelable(false);
            PrgRegistrarIncidente.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            PrgRegistrarIncidente.show();

            //TAREA PEDIR TAXI - INICIO
            Thread nt = new Thread() {
                @Override
                public void run() {

                    try {

                        final String resRegistrarIncidente;


                        resRegistrarIncidente = MtdRegistrarIncidente(
                                valIncidenteDescripcion,
                                valIncidenteLugar,
                                IncidenteFoto,
                                ConductorId,
                                VehiculoCoordenadaX,
                                VehiculoCoordenadaY,
                                ConductorNumeroDocumento,
                                VehiculoUnidad
                        );

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String JsRespuesta = "";
                                String JsIncidenteId = "";

                                try {
                                    JSONObject jsonObject = new JSONObject(resRegistrarIncidente);
                                    JsRespuesta = jsonObject.getString("Respuesta");
                                    JsIncidenteId = jsonObject.getString("IncidenteId");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                PrgRegistrarIncidente.cancel();

                                switch(JsRespuesta){

                                    case "I001":

                                        AlertDialog.Builder MsgRegistrarIncidente = new AlertDialog.Builder(RegistrarIncidenteActivity.this);
                                        MsgRegistrarIncidente.setTitle(getString(R.string.alert_title));
                                        MsgRegistrarIncidente.setMessage("Incidente registrado correctamente.");
                                        MsgRegistrarIncidente.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {

                                                        // Do nothing but close the dialog
                                                        Intent intentMonitoreoActivity = new Intent(RegistrarIncidenteActivity.this, MonitoreoActivity.class);
                                                        startActivity(intentMonitoreoActivity);
                                                        finish();

                                                    }
                                                });

                                        // Remember, create doesn't show the dialog
                                        AlertDialog msgRegistrarIncidente = MsgRegistrarIncidente.create();
                                        msgRegistrarIncidente.show();

                                        break;

                                    case "I002":
                                        FncMostrarMensaje(getString(R.string.message_no_proceso), false);
                                        break;

                                    case "I003":
                                        break;

                                    default:
                                        FncMostrarToast(getString(R.string.message_error_interno));
                                        break;
                                }

                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e("RegistrarIncidente26", e.toString());
                    }


                }
            };
            nt.start();
            //TAREA PEDIR TAXI - FIN

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

        Toast.makeText(RegistrarIncidenteActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }

       /*
   * ENVIO DE VARIABLES
     */


    public String MtdRegistrarIncidente(String oIncidenteDescripcion,String oIncidenteLugar,String oIncidenteFoto,String oConductorId,String oIncidenteCoordenadaX,String oIncidenteCoordenadaY,String oConductorNumeroDocumento, String oVehiculoUnidad ) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/JnIncidente.php");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("IncidenteDescripcion", oIncidenteDescripcion);
            postDataParams.put("IncidenteLugar", oIncidenteLugar);

            postDataParams.put("IncidenteFoto", oIncidenteFoto);
            postDataParams.put("IncidenteCoordenadaX", oIncidenteCoordenadaX);
            postDataParams.put("IncidenteCoordenadaY", oIncidenteCoordenadaY);

            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);
            postDataParams.put("VehiculoUnidad", oVehiculoUnidad);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "RegistrarIncidente");

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



            Log.e("RegistrarIncidente1", response);

        } catch (Exception e) {

            Log.e("RegistrarIncidente2", e.toString());
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



}
