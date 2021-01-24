package radiotaxi114.radiotaxi114v6;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MisDatosActivity extends AppCompatActivity {


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
    private String ConductorApellido = "";
    private String ConductorTelefono = "";
    private String ConductorCelular = "";
    private String ConductorDireccion = "";
    private String ConductorNumeroDocumento = "";
    private String ConductorEstado = "";
    private String ConductorFoto = "";
    private String ConductorVehiculoFoto = "";


    private String ConductorSupervisorNivel = "";
    private String ConductorCalificacion = "";

    private String ConductorEstadoDescripcion = "";
    private String ConductorEstadoMotivo = "";
    private String ConductorEstadoResponsable = "";
    private String ConductorEstadoResponsableContacto = "";

    private String VehiculoUnidad = "";
    private String VehiculoPlaca = "";
    private String VehiculoColor = "";
    private String VehiculoModelo = "";
    private String VehiculoCarroceria = "";
    private String VehiculoMarca = "";

    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;

    TextView txtConductorNombre;
    TextView txtConductorDireccion;
    TextView txtConductorTelefono;
    TextView txtConductorCelular;
    TextView txtVehiculoUnidad;
    TextView txtVehiculoPlaca;
    TextView txtVehiculoColor;
    TextView txtVehiculoModelo;
    TextView txtVehiculoCarroceria;
    TextView txtVehiculoMarca;

    TextView txtConductorEstadoDescripcion;
    TextView txtConductorEstadoMotivo;
    TextView txtConductorEstadoResponsable;
    TextView txtConductorEstadoResponsableContacto;

    TextView txtConductorIdentificador;

    ImageView imgConductorFoto;
    ImageView imgConductorVehiculoFoto;
    ImageView imgConductorCalificacion;

    LinearLayout capConductorFoto;
    LinearLayout capConductorVehiculoFoto;

    ImageButton btnMisDatosLlamar;
    /*
 IDENTIFICADOR
  */
    private String Identificador = "";

    /*
PERMISOS
*/
    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;

    //LinearLayout capConductorEstadoColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Log.e("MisDatos20", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        //actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_mis_datos));
        //actionBar.setIcon(R.mipmap.ic_launcher);
        //actionBar.setTitle(getString(R.string.app_title));
        //actionBar.setSubtitle("Datos de Conductor");

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");

    //PERMISOS
        context = getApplicationContext();
        activity = this;

        //RECUPERANDO VARIABLES v2
        displayUserSettings();

/*
        capConductorEstadoColor = (LinearLayout) findViewById(R.id.CapEstadoDescripcion);

        if(ConductorEstado.equals("1")) {
            capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_habilitado);

        }else if(ConductorEstado.equals("2")){
            capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_deshabilitado);

        }else if(ConductorEstado.equals("3")){
            capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_retirado);
        }*/

    //FOMRULARIO
        txtConductorNombre = (TextView) findViewById(R.id.CmpSoporteContactoCelular);
        txtConductorDireccion = (TextView) findViewById(R.id.CmpConductorDireccion);
        txtConductorTelefono = (TextView) findViewById(R.id.CmpConductorTelefono);
        txtConductorCelular = (TextView) findViewById(R.id.CmpConductorCelular);
        txtVehiculoUnidad = (TextView) findViewById(R.id.CmpSoporteContactoNombre);
        txtVehiculoPlaca = (TextView) findViewById(R.id.CmpVehiculoPlaca);
        txtVehiculoColor = (TextView) findViewById(R.id.CmpVehiculoColor);
        txtVehiculoModelo = (TextView) findViewById(R.id.CmpVehiculoModelo);
        txtVehiculoCarroceria = (TextView) findViewById(R.id.CmpVehiculoCarroceria);
        txtVehiculoMarca = (TextView) findViewById(R.id.CmpVehiculoMarca);

        txtConductorEstadoDescripcion = (TextView) findViewById(R.id.CmpConductorEstadoDescripcion);
        txtConductorEstadoMotivo = (TextView) findViewById(R.id.CmpConductorEstadoMotivo);
        txtConductorEstadoResponsable = (TextView) findViewById(R.id.CmpConductorEstadoResponsable);
        //txtConductorEstadoResponsableContacto = (TextView) findViewById(R.id.CmpConductorEstadoResponsableContacto);

        txtConductorIdentificador = (TextView) findViewById(R.id.CmpConductorIdentificador);

        imgConductorFoto = (ImageView)  findViewById(R.id.ImgConductorFoto);
        imgConductorVehiculoFoto = (ImageView)  findViewById(R.id.ImgConductorVehiculoFoto);
        imgConductorCalificacion = (ImageView)  findViewById(R.id.ImgConductorCalificacion);

        capConductorFoto = (LinearLayout)  findViewById(R.id.CapConductorFoto);
        capConductorVehiculoFoto = (LinearLayout)  findViewById(R.id.CapConductorVehiculoFoto);

        btnMisDatosLlamar = (ImageButton)findViewById(R.id.BtnMisDatosLlamar);


        //MOSTRNADO DATOS
        txtConductorNombre.setText(ConductorNombre+" "+ConductorApellido);
        txtConductorDireccion.setText(ConductorDireccion);
        txtConductorTelefono.setText(ConductorTelefono);
        txtConductorCelular.setText(ConductorCelular);
        txtVehiculoUnidad.setText(VehiculoUnidad);
        txtVehiculoPlaca.setText(VehiculoPlaca);
        txtVehiculoModelo.setText(VehiculoModelo);
        txtVehiculoColor.setText(VehiculoColor);
        txtVehiculoCarroceria.setText(VehiculoCarroceria);
        txtVehiculoMarca.setText(VehiculoMarca);

        txtConductorEstadoDescripcion.setText(ConductorEstadoDescripcion);
        txtConductorEstadoMotivo.setText(ConductorEstadoMotivo);
        txtConductorEstadoResponsable.setText(ConductorEstadoResponsable);
        txtConductorIdentificador.setText(Identificador);

        imgConductorFoto.setImageResource(R.mipmap.conductor_foto);

        if((ConductorEstadoResponsableContacto.equals("") && ConductorEstadoResponsableContacto.equals("")) ){
              btnMisDatosLlamar.setBackgroundResource(R.drawable.dra_boton_fondo2);
        }else{
              btnMisDatosLlamar.setBackgroundResource(R.drawable.dra_boton_fondo);
        }

        switch(ConductorCalificacion){

            case "1.00":
                imgConductorCalificacion.setImageResource(R.drawable.icon_1estrellas250);
                break;

            case "2.00":
                imgConductorCalificacion.setImageResource(R.drawable.icon_2estrellas250);
                break;

            case "3.00":
                imgConductorCalificacion.setImageResource(R.drawable.icon_3estrellas250);
                break;

            case "4.00":
                imgConductorCalificacion.setImageResource(R.drawable.icon_4estrellas250);
                break;

            case "5.00":
                imgConductorCalificacion.setImageResource(R.drawable.icon_5estrellas250);
                break;

            default:
                imgConductorCalificacion.setImageResource(R.drawable.icon_0estrellas250);
                break;
        }

        if(!ConductorFoto.equals("")){

            capConductorFoto.setVisibility(View.VISIBLE);

            Picasso.with(context).load(ConductorFoto).into(imgConductorFoto);

           /* runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new DownloadImageTask((ImageView) findViewById(R.id.ImgConductorFoto))
                            .execute(ConductorFoto);

                }
            });*/


            final ImagePopup imagePopupConductorFoto = new ImagePopup(MisDatosActivity.this);
            //imagePopup.setWindowHeight(100); // Optional
            //imagePopup.setWindowWidth(100); // Optional
            imagePopupConductorFoto.setBackgroundColor(Color.TRANSPARENT);  // Optional
            imagePopupConductorFoto.setFullScreen(false); // Optional
            imagePopupConductorFoto.setHideCloseIcon(true);  // Optional
            imagePopupConductorFoto.setImageOnClickClose(true);  // Optional

            //ImageView imageView = (ImageView) findViewById(R.id.imageView);
            //imagePopup.initiatePopup(imgPedidoConductorFoto.getDrawable());
            imagePopupConductorFoto.initiatePopupWithPicasso(ConductorFoto);

            imgConductorFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** Initiate Popup view **/
                    imagePopupConductorFoto.viewPopup();
                    Log.e("EsperandoTaxi","Mostrando Foto");

                }
            });



        }else{
            capConductorFoto.setVisibility(View.GONE);
        }

        if(!ConductorVehiculoFoto.equals("")){

            capConductorVehiculoFoto.setVisibility(View.VISIBLE);

            Picasso.with(context).load(ConductorVehiculoFoto).into(imgConductorVehiculoFoto);

           /* runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new DownloadImageTask((ImageView) findViewById(R.id.ImgConductorVehiculoFoto))
                            .execute(ConductorVehiculoFoto);

                }
            });*/


            final ImagePopup imagePopupConductorVehiculoFoto = new ImagePopup(MisDatosActivity.this);
            //imagePopup.setWindowHeight(100); // Optional
            //imagePopup.setWindowWidth(100); // Optional
            imagePopupConductorVehiculoFoto.setBackgroundColor(Color.TRANSPARENT);  // Optional
            imagePopupConductorVehiculoFoto.setFullScreen(false); // Optional
            imagePopupConductorVehiculoFoto.setHideCloseIcon(true);  // Optional
            imagePopupConductorVehiculoFoto.setImageOnClickClose(true);  // Optional

            //ImageView imageView = (ImageView) findViewById(R.id.imageView);
            //imagePopup.initiatePopup(imgPedidoConductorFoto.getDrawable());
            imagePopupConductorVehiculoFoto.initiatePopupWithPicasso(ConductorVehiculoFoto);

            imgConductorVehiculoFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /** Initiate Popup view **/
                    imagePopupConductorVehiculoFoto.viewPopup();
                    Log.e("EsperandoTaxi","Mostrando Foto");

                }
            });


        }else{
            capConductorVehiculoFoto.setVisibility(View.GONE);
        }


        //CONDUCTOR FOTO
        //DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //double ancho =  (metrics.widthPixels) - (metrics.widthPixels*(0.2));
        //double alto =  metrics.heightPixels  - (metrics.heightPixels*(0.4));

        //int n_ancho = (int) ancho;
        //int n_alto = (int) alto;

        //final ImagePopup imagePopup = new ImagePopup(this);
        //imagePopup.setBackgroundColor(Color.TRANSPARENT);
        //imagePopup.setWindowWidth(n_ancho);
        //imagePopup.setWindowHeight(n_alto);
        //imagePopup.setHideCloseIcon(true);
        //imagePopup.setImageOnClickClose(true);
        //imagePopup.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imagePopup.setBaselineAlignBottom(true);

        /*imgConductorFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imgConductorFoto.getDrawable()!=null){
                    //imagePopup.initiatePopup(imgConductorFoto.getDrawable());

                    android.app.AlertDialog.Builder imageDialog = new android.app.AlertDialog.Builder(MisDatosActivity.this);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                    View layout = inflater.inflate(R.layout.conductor_foto,
                            (ViewGroup) findViewById(R.id.layout_root));
                    ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
                    // image.setMaxHeight(300);
                    // image.setLa
                    image.setImageDrawable(imgConductorFoto.getDrawable());
                    imageDialog.setView(layout);


                    imageDialog.create();
                    imageDialog.show();
                }

            }
        });*/

      /*  imgConductorVehiculoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imgConductorVehiculoFoto.getDrawable()!=null){
                    //imagePopup.initiatePopup(imgConductorVehiculoFoto.getDrawable());

                    android.app.AlertDialog.Builder imageDialog = new android.app.AlertDialog.Builder(MisDatosActivity.this);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                    View layout = inflater.inflate(R.layout.conductor_foto,
                            (ViewGroup) findViewById(R.id.layout_root));
                    ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
                    // image.setMaxHeight(300);
                    // image.setLa
                    image.setImageDrawable(imgConductorVehiculoFoto.getDrawable());
                    imageDialog.setView(layout);


                    imageDialog.create();
                    imageDialog.show();

                }

            }
        });*/











        if(estaConectado(true)){

            final ProgressDialog prgMisDatos = new ProgressDialog(this);
            prgMisDatos.setIcon(R.mipmap.ic_launcher);
            prgMisDatos.setMessage("Cargando...");
            prgMisDatos.setCancelable(false);
            prgMisDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgMisDatos.show();

            Thread nt = new Thread() {
                @Override
                public void run() {

                    try {

                        final String resMtdObtenerConductor;
                        resMtdObtenerConductor = MtdObtenerConductor(ConductorId);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String JsRespuesta = "";
                                String JsConductorId = "";
                                String JsConductorIdentificador = "";

                                String JsVehiculoUnidad = "";
                                String JsVehiculoPlaca = "";
                                String JsVehiculoModelo = "";
                                String JsVehiculoColor = "";
                                String JsVehiculoCarroceria = "";
                                String JsVehiculoMarca = "";

                                String JsConductorNombre = "";
                                String JsConductorDireccion = "";
                                String JsConductorNumeroDocumento = "";
                                String JsConductorCelular = "";
                                String JsConductorTelefono = "";
                                String JsConductorFoto = "";
                                String JsConductorVehiculoFoto = "";
                                String JsConductorCalificacion = "";
                                String JsConductorEstado = "";
                                String JsConductorEstadoDescripcion = "";
                                String JsConductorEstadoMotivo = "";
                                String JsConductorEstadoResponsable = "";
                                String JsConductorEstadoResponsableContacto = "";

                                try {

                                    JSONObject jsonObject = new JSONObject(resMtdObtenerConductor);
                                    JsRespuesta = jsonObject.getString("Respuesta");

                                    JsConductorId = jsonObject.getString("ConductorId");
                                    JsVehiculoUnidad = jsonObject.getString("VehiculoUnidad");
                                    JsVehiculoPlaca = jsonObject.getString("VehiculoPlaca");
                                    JsVehiculoModelo = jsonObject.getString("VehiculoModelo");
                                    JsVehiculoColor = jsonObject.getString("VehiculoColor");
                                    JsVehiculoCarroceria = jsonObject.getString("VehiculoCarroceria");
                                    JsVehiculoMarca = jsonObject.getString("VehiculoMarca");

                                    JsConductorNombre = jsonObject.getString("ConductorNombre");
                                    JsConductorDireccion = jsonObject.getString("ConductorDireccion");
                                    JsConductorNumeroDocumento = jsonObject.getString("ConductorNumeroDocumento");
                                    JsConductorCelular = jsonObject.getString("ConductorCelular");
                                    JsConductorTelefono = jsonObject.getString("ConductorTelefono");
                                    JsConductorFoto = jsonObject.getString("ConductorFoto");
                                    JsConductorVehiculoFoto = jsonObject.getString("ConductorVehiculoFoto");
                                    JsConductorCalificacion = jsonObject.getString("ConductorCalificacion");
                                    JsConductorEstado = jsonObject.getString("ConductorEstado");

                                    JsConductorEstadoMotivo = jsonObject.getString("ConductorEstadoMotivo");
                                    JsConductorEstadoDescripcion = jsonObject.getString("ConductorEstadoDescripcion");
                                    JsConductorEstadoResponsable = jsonObject.getString("ConductorEstadoResponsable");
                                    JsConductorEstadoResponsableContacto = jsonObject.getString("ConductorEstadoResponsableContacto");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                prgMisDatos.cancel();


                                switch(JsRespuesta){
                                    case "C032":

                                        FncMostrarToast("Datos actualizados correctamente");

                                        ConductorId = JsConductorId;
                                        VehiculoUnidad = JsVehiculoUnidad;
                                        VehiculoPlaca = JsVehiculoPlaca;
                                        VehiculoModelo = JsVehiculoModelo;
                                        VehiculoColor = JsVehiculoColor;
                                        VehiculoCarroceria = JsVehiculoCarroceria;
                                        VehiculoMarca = JsVehiculoMarca;

                                        ConductorNombre = JsConductorNombre;
                                        ConductorDireccion = JsConductorDireccion;
                                        ConductorNumeroDocumento = JsConductorNumeroDocumento;
                                        ConductorCelular = JsConductorCelular;
                                        ConductorTelefono = JsConductorTelefono;
                                        ConductorFoto = JsConductorFoto;
                                        ConductorVehiculoFoto = JsConductorVehiculoFoto;

                                        ConductorCalificacion = JsConductorCalificacion;

                                        ConductorEstado = JsConductorEstado;
                                        ConductorEstadoMotivo = JsConductorEstadoMotivo;
                                        ConductorEstadoDescripcion = JsConductorEstadoDescripcion;
                                        ConductorEstadoResponsable = JsConductorEstadoResponsable;
                                        ConductorEstadoResponsableContacto = JsConductorEstadoResponsableContacto;

                                        saveUserSettings();

                                        txtConductorNombre.setText(ConductorNombre+" "+ConductorApellido);
                                        txtConductorDireccion.setText(ConductorDireccion);
                                        txtConductorTelefono.setText(ConductorTelefono);
                                        txtConductorCelular.setText(ConductorCelular);
                                        txtVehiculoUnidad.setText(VehiculoUnidad);
                                        txtVehiculoPlaca.setText(VehiculoPlaca);
                                        txtVehiculoModelo.setText(VehiculoModelo);
                                        txtVehiculoColor.setText(VehiculoColor);
                                        txtVehiculoCarroceria.setText(VehiculoCarroceria);
                                        txtVehiculoMarca.setText(VehiculoMarca);

                                        txtConductorEstadoMotivo.setText(ConductorEstadoMotivo);
                                        txtConductorEstadoDescripcion.setText(ConductorEstadoDescripcion);
                                        txtConductorEstadoResponsable.setText(ConductorEstadoResponsable);
                                        //txtConductorEstadoResponsableContacto.setText(ConductorEstadoResponsableContacto);

                                        imgConductorFoto.setImageResource(R.mipmap.conductor_foto);

                                        if(!ConductorFoto.equals("")){

                                            capConductorFoto.setVisibility(View.VISIBLE);

                                            Picasso.with(context).load(ConductorFoto).into(imgConductorFoto);


                                            /*
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    new DownloadImageTask((ImageView) findViewById(R.id.ImgConductorFoto))
                                                            .execute(ConductorFoto);

                                                }
                                            });*/

                                        }else{
                                            capConductorFoto.setVisibility(View.GONE);
                                        }

                                        if(!ConductorVehiculoFoto.equals("")){

                                            capConductorVehiculoFoto.setVisibility(View.VISIBLE);

                                            Picasso.with(context).load(ConductorVehiculoFoto).into(imgConductorVehiculoFoto);


                                           /* runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    new DownloadImageTask((ImageView) findViewById(R.id.ImgConductorVehiculoFoto))
                                                            .execute(ConductorVehiculoFoto);

                                                }
                                            });*/

                                        }else{
                                            capConductorVehiculoFoto.setVisibility(View.GONE);
                                        }



                                        switch(ConductorCalificacion){

                                            case "1.00":
                                                imgConductorCalificacion.setImageResource(R.drawable.icon_1estrellas250);
                                                break;

                                            case "2.00":
                                                imgConductorCalificacion.setImageResource(R.drawable.icon_2estrellas250);
                                                break;

                                            case "3.00":
                                                imgConductorCalificacion.setImageResource(R.drawable.icon_3estrellas250);
                                                break;

                                            case "4.00":
                                                imgConductorCalificacion.setImageResource(R.drawable.icon_4estrellas250);
                                                break;

                                            case "5.00":
                                                imgConductorCalificacion.setImageResource(R.drawable.icon_5estrellas250);
                                                break;

                                            default:
                                                imgConductorCalificacion.setImageResource(R.drawable.icon_0estrellas250);
                                                break;
                                        }

                                               /* if(ConductorEstado.equals("1")) {
                                                    capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_habilitado);

                                                }else if(ConductorEstado.equals("2")){
                                                    capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_deshabilitado);

                                                }else if(ConductorEstado.equals("3")){
                                                    capConductorEstadoColor.setBackgroundResource(R.drawable.dra_fondo_conductor_estado_retirado);
                                                }
*/


                                        break;
                                    case "C033":
                                        FncMostrarToast("Ha ocurrido un error actualizando los datos");
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



    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(MisDatosActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        MisDatosActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mis_datos, menu);
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

                Intent intentMonitoreo = new Intent(MisDatosActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                MisDatosActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();


                return true;

            /*case R.id.action_actualizar_conductor_datos:

                if(estaConectado(true)){


                    final ProgressDialog prgMisDatos = new ProgressDialog(this);
                    prgMisDatos.setIcon(R.mipmap.ic_launcher);
                    prgMisDatos.setMessage("Cargando...");
                    prgMisDatos.setCancelable(false);
                    prgMisDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    prgMisDatos.show();

                    Thread nt = new Thread() {
                        @Override
                        public void run() {

                            try {

                                final String resMisDatos;

                                resMisDatos = MtdObtenerConductor(ConductorId);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String JsRespuesta = "";
                                        String JsConductorId = "";
                                        String JsConductorIdentificador = "";

                                        String JsVehiculoUnidad = "";
                                        String JsVehiculoPlaca = "";
                                        String JsVehiculoModelo = "";
                                        String JsVehiculoColor = "";
                                        String JsVehiculoCarroceria = "";

                                        String JsConductorNombre = "";
                                        String JsConductorDireccion = "";
                                        String JsConductorNumeroDocumento = "";
                                        String JsConductorCelular = "";
                                        String JsConductorTelefono = "";
                                        String JsConductorFoto = "";
                                        String JsConductorCalificacion = "";
                                        String JsConductorEstado = "";
                                        String JsConductorEstadoDescripcion = "";
                                        String JsConductorEstadoMotivo = "";
                                        String JsConductorEstadoResponsable = "";

                                        try {

                                            JSONObject jsonObject = new JSONObject(resMisDatos);
                                            JsRespuesta = jsonObject.getString("Respuesta");

                                            JsConductorId = jsonObject.getString("ConductorId");
                                            JsVehiculoUnidad = jsonObject.getString("VehiculoUnidad");
                                            JsVehiculoPlaca = jsonObject.getString("VehiculoPlaca");
                                            JsVehiculoModelo = jsonObject.getString("VehiculoModelo");
                                            JsVehiculoColor = jsonObject.getString("VehiculoColor");
                                            JsVehiculoCarroceria = jsonObject.getString("VehiculoCarroceria");

                                            JsConductorNombre = jsonObject.getString("ConductorNombre");
                                            JsConductorDireccion = jsonObject.getString("ConductorDireccion");
                                            JsConductorNumeroDocumento = jsonObject.getString("ConductorNumeroDocumento");
                                            JsConductorCelular = jsonObject.getString("ConductorCelular");
                                            JsConductorTelefono = jsonObject.getString("ConductorTelefono");
                                            JsConductorFoto = jsonObject.getString("ConductorFoto");
                                            JsConductorCalificacion = jsonObject.getString("ConductorCalificacion");
                                            JsConductorEstado = jsonObject.getString("ConductorEstado");

                                            JsConductorEstadoMotivo = jsonObject.getString("ConductorEstadoMotivo");
                                            JsConductorEstadoDescripcion = jsonObject.getString("ConductorEstadoDescripcion");
                                            JsConductorEstadoResponsable = jsonObject.getString("ConductorEstadoResponsable");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        prgMisDatos.cancel();


                                        switch(JsRespuesta){
                                            case "C032":

                                                FncMostrarToast("Datos actualizados correctamente");

                                                ConductorId = JsConductorId;
                                                VehiculoUnidad = JsVehiculoUnidad;
                                                VehiculoPlaca = JsVehiculoPlaca;
                                                VehiculoModelo = JsVehiculoModelo;
                                                VehiculoColor = JsVehiculoColor;
                                                VehiculoCarroceria = JsVehiculoCarroceria;

                                                ConductorNombre = JsConductorNombre;
                                                ConductorDireccion = JsConductorDireccion;
                                                ConductorNumeroDocumento = JsConductorNumeroDocumento;
                                                ConductorCelular = JsConductorCelular;
                                                ConductorTelefono = JsConductorTelefono;
                                                ConductorFoto = JsConductorFoto;
                                                ConductorCalificacion = JsConductorCalificacion;

                                                ConductorEstado = JsConductorEstado;
                                                ConductorEstadoMotivo = JsConductorEstadoMotivo;
                                                ConductorEstadoDescripcion = JsConductorEstadoDescripcion;
                                                ConductorEstadoResponsable = JsConductorEstadoResponsable;

                                                saveUserSettings();

                                                txtConductorNombre.setText(ConductorNombre+" "+ConductorApellido);
                                                txtConductorDireccion.setText(ConductorDireccion);
                                                txtConductorTelefono.setText(ConductorTelefono);
                                                txtConductorCelular.setText(ConductorCelular);
                                                txtVehiculoUnidad.setText(VehiculoUnidad);
                                                txtVehiculoPlaca.setText(VehiculoPlaca);
                                                txtVehiculoModelo.setText(VehiculoModelo);
                                                txtVehiculoColor.setText(VehiculoColor);
                                                txtVehiculoCarroceria.setText(VehiculoCarroceria);

                                                txtConductorEstadoMotivo.setText(ConductorEstadoMotivo);
                                                txtConductorEstadoDescripcion.setText(ConductorEstadoDescripcion);
                                                txtConductorEstadoResponsable.setText(ConductorEstadoResponsable);

                                                if(!ConductorFoto.equals("")){

                                                    imgConductorFoto = (ImageView)  findViewById(R.id.ImgConductorFoto);
                                                    imgConductorFoto.setImageResource(R.mipmap.conductor_foto);

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            new DownloadImageTask((ImageView) findViewById(R.id.ImgConductorFoto))
                                                                    .execute(ConductorFoto);

                                                        }
                                                    });

                                                }else{

                                                    imgConductorFoto = (ImageView)  findViewById(R.id.ImgConductorFoto);
                                                    imgConductorFoto.setImageResource(R.mipmap.conductor_foto);

                                                }


                                                switch(ConductorCalificacion){

                                                    case "1.00":
                                                        imgConductorCalificacion.setImageResource(R.drawable.icon_1estrellas250);
                                                        break;

                                                    case "2.00":
                                                        imgConductorCalificacion.setImageResource(R.drawable.icon_2estrellas250);
                                                        break;

                                                    case "3.00":
                                                        imgConductorCalificacion.setImageResource(R.drawable.icon_3estrellas250);
                                                        break;

                                                    case "4.00":
                                                        imgConductorCalificacion.setImageResource(R.drawable.icon_4estrellas250);
                                                        break;

                                                    case "5.00":
                                                        imgConductorCalificacion.setImageResource(R.drawable.icon_5estrellas250);
                                                        break;

                                                    default:
                                                        imgConductorCalificacion.setImageResource(R.drawable.icon_0estrellas250);
                                                        break;
                                                }



                                                break;
                                            case "C033":
                                                FncMostrarToast("Ha ocurrido un error actualizando los datos");
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

                return true;
*/
           /* case R.id.action_acerca_de:

                FncMostrarAcercaDe();

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

        Toast.makeText(MisDatosActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }


    public void onClick_BtnMisDatosLlamar(View v){

        if(!ConductorEstadoResponsableContacto.equals("")) {
            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ConductorEstadoResponsableContacto));
                startActivity(callIntent);
            } catch (Exception e) {
                FncMostrarToast("Ha ocurrido un error interno");
                //Log.e("helloandroid dialing example", "Call failed", e);
            }
        }else{
            FncMostrarMensaje("No se encontro numero a llamar",false);
        }
    }

    public void onClick_BtnMisDatosRegresar(View v){

        Intent intentMonitoreo = new Intent(MisDatosActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        finish();
    }

    public void onClick_BtnMisDatosActualizar(View v){


        /*if(estaConectado(true)){


            final ProgressDialog prgMisDatos = new ProgressDialog(this);
            prgMisDatos.setIcon(R.mipmap.ic_launcher);
            prgMisDatos.setMessage("Cargando...");
            prgMisDatos.setCancelable(false);
            prgMisDatos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgMisDatos.show();

            Thread nt = new Thread() {
                @Override
                public void run() {

                    try {

                        final String resMisDatos;

                        resMisDatos = MtdObtenerConductor(ConductorId);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String JsRespuesta = "";
                                String JsConductorId = "";
                                String JsIdentificador = "";

                                String JsVehiculoUnidad = "";
                                String JsVehiculoPlaca = "";
                                String JsVehiculoModelo = "";
                                String JsVehiculoColor = "";
                                String JsVehiculoCarroceria = "";

                                String JsConductorNombre = "";
                                String JsConductorDireccion = "";
                                String JsConductorNumeroDocumento = "";
                                String JsConductorCelular = "";
                                String JsConductorTelefono = "";
                                String JsConductorFoto = "";
                                String JsConductorCalificacion = "";
                                String JsConductorEstado = "";
                                String JsConductorEstadoDescripcion = "";
                                String JsConductorEstadoMotivo = "";

                                try {

                                    JSONObject jsonObject = new JSONObject(resMisDatos);
                                    JsRespuesta = jsonObject.getString("Respuesta");

                                    JsConductorId = jsonObject.getString("ConductorId");
                                    JsVehiculoUnidad = jsonObject.getString("VehiculoUnidad");
                                    JsVehiculoPlaca = jsonObject.getString("VehiculoPlaca");
                                    JsVehiculoModelo = jsonObject.getString("VehiculoModelo");
                                    JsVehiculoColor = jsonObject.getString("VehiculoColor");
                                    JsVehiculoCarroceria = jsonObject.getString("VehiculoCarroceria");

                                    JsConductorNombre = jsonObject.getString("ConductorNombre");
                                    JsConductorDireccion = jsonObject.getString("ConductorDireccion");
                                    JsConductorNumeroDocumento = jsonObject.getString("ConductorNumeroDocumento");
                                    JsConductorCelular = jsonObject.getString("ConductorCelular");
                                    JsConductorTelefono = jsonObject.getString("ConductorTelefono");
                                    JsConductorFoto = jsonObject.getString("ConductorFoto");
                                    JsConductorCalificacion = jsonObject.getString("ConductorCalificacion");
                                    JsConductorEstado = jsonObject.getString("ConductorEstado");
                                    JsConductorEstadoDescripcion = jsonObject.getString("ConductorEstadoDescripcion");
                                    JsConductorEstadoMotivo = jsonObject.getString("ConductorEstadoMotivo");

                                    ConductorId = JsConductorId;
                                    VehiculoUnidad = JsVehiculoUnidad;
                                    VehiculoPlaca = JsVehiculoPlaca;
                                    VehiculoModelo = JsVehiculoModelo;
                                    VehiculoColor = JsVehiculoColor;
                                    VehiculoCarroceria = JsVehiculoCarroceria;

                                    ConductorNombre = JsConductorNombre;
                                    ConductorDireccion = JsConductorDireccion;
                                    ConductorNumeroDocumento = JsConductorNumeroDocumento;
                                    ConductorCelular = JsConductorCelular;
                                    ConductorTelefono = JsConductorTelefono;
                                    ConductorFoto = JsConductorFoto;
                                    ConductorCalificacion = JsConductorCalificacion;

                                    ConductorEstado = JsConductorEstado;
                                    ConductorEstadoDescripcion = JsConductorEstadoDescripcion;
                                    ConductorEstadoMotivo = JsConductorEstadoMotivo;

                                    saveUserSettings();

                                    txtConductorNombre.setText(ConductorNombre+" "+ConductorApellido);
                                    txtConductorDireccion.setText(ConductorDireccion);
                                    txtConductorTelefono.setText(ConductorTelefono);
                                    txtConductorCelular.setText(ConductorCelular);
                                    txtVehiculoUnidad.setText(VehiculoUnidad);
                                    txtVehiculoPlaca.setText(VehiculoPlaca);
                                    txtVehiculoColor.setText(VehiculoModelo+" "+VehiculoColor);
                                    txtVehiculoCarroceria.setText(VehiculoCarroceria);


                                    if(!ConductorFoto.equals("")){

                                        imgConductorFoto = (ImageView)  findViewById(R.id.ImgConductorFoto);
                                        imgConductorFoto.setImageResource(R.mipmap.conductor_foto);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                new DownloadImageTask((ImageView) findViewById(R.id.ImgConductorFoto))
                                                        .execute(ConductorFoto);

                                            }
                                        });

                                    }else{

                                        imgConductorFoto = (ImageView)  findViewById(R.id.ImgConductorFoto);
                                        imgConductorFoto.setImageResource(R.mipmap.conductor_foto);

                                    }


                                    switch(ConductorCalificacion){

                                        case "1.00":
                                            imgConductorCalificacion.setImageResource(R.drawable.icon_1estrellas250);
                                            break;

                                        case "2.00":
                                            imgConductorCalificacion.setImageResource(R.drawable.icon_2estrellas250);
                                            break;

                                        case "3.00":
                                            imgConductorCalificacion.setImageResource(R.drawable.icon_3estrellas250);
                                            break;

                                        case "4.00":
                                            imgConductorCalificacion.setImageResource(R.drawable.icon_4estrellas250);
                                            break;

                                        case "5.00":
                                            imgConductorCalificacion.setImageResource(R.drawable.icon_5estrellas250);
                                            break;

                                        default:
                                            imgConductorCalificacion.setImageResource(R.drawable.icon_0estrellas250);
                                            break;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                prgMisDatos.cancel();


                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }
            };



            nt.start();


        }*/


    }




  /*
   * ENVIO DE VARIABLES
     */

    public String MtdObtenerConductor(String oConductorId) {

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

            Log.e("MisDatos2", response);

        } catch (Exception e) {

            Log.e("MisDatos3", e.toString());
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
        VehiculoModelo = sharedPreferences.getString("VehiculoModelo","");
        VehiculoColor = sharedPreferences.getString("VehiculoColor","");
        VehiculoCarroceria = sharedPreferences.getString("VehiculoCarroceria","");

        ConductorNombre = sharedPreferences.getString("ConductorNombre","");
        ConductorNumeroDocumento = sharedPreferences.getString("ConductorNumeroDocumento","");
        ConductorDireccion = sharedPreferences.getString("ConductorDireccion","");
        ConductorCelular = sharedPreferences.getString("ConductorCelular","");
        ConductorTelefono = sharedPreferences.getString("ConductorTelefono","");
        ConductorFoto = sharedPreferences.getString("ConductorFoto","");
        ConductorVehiculoFoto = sharedPreferences.getString("ConductorVehiculoFoto","");
        ConductorCalificacion = sharedPreferences.getString("ConductorCalificacion","");

        ConductorEstado = sharedPreferences.getString("ConductorEstado","");
        ConductorEstadoDescripcion = sharedPreferences.getString("ConductorEstadoDescripcion","");
        ConductorEstadoMotivo = sharedPreferences.getString("ConductorEstadoMotivo","");
        ConductorEstadoResponsable = sharedPreferences.getString("ConductorEstadoResponsable","");
        ConductorEstadoResponsableContacto = sharedPreferences.getString("ConductorEstadoResponsableContacto","");

        ConductorOcupado = sharedPreferences.getInt("ConductorOcupado",2);
        ConductorCobertura = sharedPreferences.getInt("ConductorCobertura",0);

    }


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
        editor.putString("ConductorVehiculoFoto", ConductorVehiculoFoto);
        editor.putString("ConductorCalificacion", ConductorCalificacion);

        editor.putString("ConductorEstado", ConductorEstado.trim());
        editor.putString("ConductorEstadoDescripcion", ConductorEstadoDescripcion.trim());
        editor.putString("ConductorEstadoMotivo", ConductorEstadoMotivo.trim());
        editor.putString("ConductorEstadoResponsable", ConductorEstadoResponsable.trim());
        editor.putString("ConductorEstadoResponsableContacto", ConductorEstadoResponsableContacto.trim());

        editor.putInt("ConductorOcupado", ConductorOcupado);
        editor.putInt("ConductorCobertura", ConductorCobertura);


        editor.apply();

        return true;
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


   /* private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                //InputStream in = new java.net.URL(urldisplay).openStream();
                InputStream in = new java.net.URL(urldisplay.replace(" ", "%20")).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            }catch (Exception e) {
                Log.e("ErrorImagen", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
*/




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
