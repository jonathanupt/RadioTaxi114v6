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
import android.widget.EditText;
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
import java.io.IOException;
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

public class ConductorActualActivity extends AppCompatActivity {


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
    private String ConductorSupervisorNivel = "";

    private String VehiculoUnidad = "";
    private String VehiculoPlaca = "";
    private String VehiculoColor = "";
    private String VehiculoModelo = "";

    private String VehiculoCoordenadaX = "";
    private String VehiculoCoordenadaY = "";

    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;


    /*
    *
     */

    private String ConductorIdActual = "";
    private String ConductorNombreActual = "";
    private String ConductorApellidoActual = "";
    private String ConductorTelefonoActual = "";
    private String ConductorCelularActual = "";
    private String ConductorDireccionActual = "";
    private String ConductorNumeroDocumentoActual = "";
    private String ConductorEstadoDescripcionActual = "";
    private String ConductorEstadoMotivoActual = "";
    private String ConductorEstadoResponsableActual = "";
    private String ConductorAppVersionActual = "";




    private String ConductorEstadoActual = "";
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
    private String VehiculoMarcaActual = "";

    private String VehiculoCoordenadaXActual = "";
    private String VehiculoCoordenadaYActual = "";
    private int VehiculoGPSOrientacionActual = 0;

    private String PropietarioNombre = "";
    private String PropietarioCelular = "";

    /*
    IDENTIFICADOR
     */
    private String Identificador = "";

    ImageView imgConductorFoto1;
    ImageView imgConductorFoto2;

    ImageView imgConductorVehiculoFoto1;
    ImageView imgConductorVehiculoFoto2;

    LinearLayout capConductorFoto;
    LinearLayout capConductorVehiculoFoto;


    TextView txtPropietarioNombre;
    TextView txtPropietarioCelular;

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
        Log.e("ConductorActual", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ConductorActual", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ConductorActual", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ConductorActual", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ConductorActual", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ConductorActual", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("ConductorActual", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("ConductorActual", "RestoreInstance");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_actual_activity);
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

        Log.e("ConductorActual", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_conductor_actual));

        // actionBar.setDisplayShowHomeEnabled(true);
        //    actionBar.setIcon(R.mipmap.ic_launcher);
        //    actionBar.setTitle(getString(R.string.app_title));
        //   actionBar.setSubtitle("Datos de Conductor");

        //RECPERANDO VARIABLES
        displayUserSettings();

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");

        //PERMISOS
        context = getApplicationContext();
        activity = this;

        //RECUPERANDO VARIABLES
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

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
        ConductorAppVersionActual = intentExtras.getStringExtra("ConductorAppVersionActual");

        VehiculoPlacaActual = intentExtras.getStringExtra("VehiculoPlacaActual");
        VehiculoUnidadActual = intentExtras.getStringExtra("VehiculoUnidadActual");
        VehiculoColorActual = intentExtras.getStringExtra("VehiculoColorActual");
        VehiculoModeloActual = intentExtras.getStringExtra("VehiculoModeloActual");
        VehiculoCarroceriaActual = intentExtras.getStringExtra("VehiculoCarroceriaActual");
        VehiculoMarcaActual = intentExtras.getStringExtra("VehiculoMarcaActual");

        VehiculoCoordenadaXActual = intentExtras.getStringExtra("VehiculoCoordenadaXActual");
        VehiculoCoordenadaYActual = intentExtras.getStringExtra("VehiculoCoordenadaYActual");
        VehiculoGPSOrientacionActual = intentExtras.getIntExtra("VehiculoGPSOrientacionActual",0);

        PropietarioNombre = intentExtras.getStringExtra("PropietarioNombre");
        PropietarioCelular = intentExtras.getStringExtra("PropietarioCelular");


        ConductorSupervisorNivel = intentExtras.getStringExtra("ConductorSupervisorNivel");



        //FORMULARIOS
        TextView TxtConductorNombre = (TextView) findViewById(R.id.CmpSoporteContactoCelular);
        TextView TxtConductorDireccion = (TextView) findViewById(R.id.CmpConductorDireccion);

        TextView TxtConductorTelefono = (TextView) findViewById(R.id.CmpConductorTelefono);
        TextView TxtConductorCelular = (TextView) findViewById(R.id.CmpConductorCelular);
        TextView TxtVehiculoUnidad = (TextView) findViewById(R.id.CmpSoporteContactoNombre);
        TextView TxtVehiculoPlaca = (TextView) findViewById(R.id.CmpVehiculoPlaca);
        TextView TxtVehiculoModelo = (TextView) findViewById(R.id.CmpVehiculoModelo);
        TextView TxtVehiculoColor = (TextView) findViewById(R.id.CmpVehiculoColor);
        TextView TxtConductorEstadoDescripcion = (TextView) findViewById(R.id.CmpConductorEstadoDescripcion);
        TextView TxtConductorEstadoMotivo = (TextView) findViewById(R.id.CmpConductorEstadoMotivo);
        TextView TxtConductorEstadoResponsable = (TextView) findViewById(R.id.CmpConductorEstadoResponsable);
        TextView TxtConductorAppVersion = (TextView) findViewById(R.id.CmpConductorActualConductorVersion);


        TextView TxtVehiculoCarroceria = (TextView) findViewById(R.id.CmpVehiculoCarroceria);
        TextView TxtVehiculoMarca = (TextView) findViewById(R.id.CmpVehiculoMarca);

        txtPropietarioNombre = (TextView) findViewById(R.id.CmpConductorActualPropietarioNombre);
        txtPropietarioCelular = (TextView) findViewById(R.id.CmpConductorActualPropietarioCelular);

        imgConductorFoto1 = (ImageView)  findViewById(R.id.ImgConductorFoto1);
        imgConductorFoto2 = (ImageView)  findViewById(R.id.ImgConductorFoto2);

        imgConductorVehiculoFoto1 = (ImageView)  findViewById(R.id.ImgConductorVehiculoFoto1);
        imgConductorVehiculoFoto2 = (ImageView)  findViewById(R.id.ImgConductorVehiculoFoto2);

        capConductorFoto = (LinearLayout)  findViewById(R.id.CapConductorFoto);
        capConductorVehiculoFoto = (LinearLayout)  findViewById(R.id.CapConductorVehiculoFoto);

        //MOSTRANDO VARIABLES
        TxtConductorDireccion.setText(ConductorDireccionActual);
        TxtConductorTelefono.setText(ConductorTelefonoActual);
        TxtConductorCelular.setText(ConductorCelularActual);
        TxtVehiculoUnidad.setText(VehiculoUnidadActual);
        TxtVehiculoPlaca.setText(VehiculoPlacaActual);
        TxtVehiculoModelo.setText(VehiculoModeloActual);
        TxtVehiculoColor.setText(VehiculoColorActual);

        TxtConductorEstadoDescripcion.setText(ConductorEstadoDescripcionActual);
        TxtConductorEstadoMotivo.setText(ConductorEstadoMotivoActual);
        TxtConductorEstadoResponsable.setText(ConductorEstadoResponsableActual);
        TxtConductorAppVersion.setText(ConductorAppVersionActual);

        TxtVehiculoCarroceria.setText(VehiculoCarroceriaActual);
        TxtVehiculoMarca.setText(VehiculoMarcaActual);

        TxtConductorNombre.setText(ConductorNombreActual+" "+ConductorApellidoActual);



        txtPropietarioNombre.setText(PropietarioNombre);
        txtPropietarioCelular.setText(PropietarioCelular);



        imgConductorFoto1.setImageResource(R.mipmap.conductor_foto);
        imgConductorFoto2.setImageResource(R.mipmap.conductor_foto);

        imgConductorVehiculoFoto1.setImageResource(R.mipmap.conductor_vehiculo_foto);
        imgConductorVehiculoFoto2.setImageResource(R.mipmap.conductor_vehiculo_foto);

       /* imgConductorFoto1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here

                ImageView tempImageView = imgConductorFoto2;

                AlertDialog.Builder imageDialog = new AlertDialog.Builder(ConductorActualActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.conductor_foto,
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


            }
        });*/



        imgConductorFoto1 = (ImageView) findViewById(R.id.ImgConductorFoto1);
        imgConductorVehiculoFoto1 = (ImageView) findViewById(R.id.ImgConductorVehiculoFoto1);



        if(!ConductorFotoActual.equals("")){

            capConductorFoto.setVisibility(View.VISIBLE);
/*
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new DownloadImageTask((ImageView) findViewById(R.id.ImgConductorFoto1))
                            .execute(ConductorFotoActual);

                }
            });

*/
            Picasso.with(context).load(ConductorFotoActual).into(imgConductorFoto1);


            final ImagePopup imagePopupConductorFoto = new ImagePopup(ConductorActualActivity.this);
            //imagePopup.setWindowHeight(100); // Optional
            //imagePopup.setWindowWidth(100); // Optional
            imagePopupConductorFoto.setBackgroundColor(Color.TRANSPARENT);  // Optional
            imagePopupConductorFoto.setFullScreen(false); // Optional
            imagePopupConductorFoto.setHideCloseIcon(true);  // Optional
            imagePopupConductorFoto.setImageOnClickClose(true);  // Optional

            //ImageView imageView = (ImageView) findViewById(R.id.imageView);
            //imagePopup.initiatePopup(imgPedidoConductorFoto.getDrawable());
            imagePopupConductorFoto.initiatePopupWithPicasso(ConductorFotoActual);

            imgConductorFoto1.setOnClickListener(new View.OnClickListener() {
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

        if(!ConductorVehiculoFotoActual.equals("")){

            capConductorVehiculoFoto.setVisibility(View.VISIBLE);

            /*
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new DownloadImageTask((ImageView) findViewById(R.id.ImgConductorVehiculoFoto1))
                            .execute(ConductorVehiculoFotoActual);

                }
            });
            */

            Picasso.with(context).load(ConductorVehiculoFotoActual).into(imgConductorVehiculoFoto1);


            final ImagePopup imagePopupConductorVehiculoFoto = new ImagePopup(ConductorActualActivity.this);
            //imagePopup.setWindowHeight(100); // Optional
            //imagePopup.setWindowWidth(100); // Optional
            imagePopupConductorVehiculoFoto.setBackgroundColor(Color.TRANSPARENT);  // Optional
            imagePopupConductorVehiculoFoto.setFullScreen(false); // Optional
            imagePopupConductorVehiculoFoto.setHideCloseIcon(true);  // Optional
            imagePopupConductorVehiculoFoto.setImageOnClickClose(true);  // Optional

            //ImageView imageView = (ImageView) findViewById(R.id.imageView);
            //imagePopup.initiatePopup(imgPedidoConductorFoto.getDrawable());
            imagePopupConductorVehiculoFoto.initiatePopupWithPicasso(ConductorVehiculoFotoActual);

            imgConductorVehiculoFoto1.setOnClickListener(new View.OnClickListener() {
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


       /* if(!ConductorFotoThumb1Actual.equals("")){
            Log.i("ErrorimgConductorFoto1","AA");

            try{

                CargaImagenesThumb1 cargaImagenesThumb1 = new CargaImagenesThumb1();
                cargaImagenesThumb1.execute(ConductorFotoThumb1Actual);

            }catch (Exception ex){
                imgConductorFoto1.setImageResource(R.mipmap.conductor_foto);
                Log.i("ErrorimgConductorFoto1",ex.toString());
            }

        }*/



     /*   if(!ConductorFotoThumb2Actual.equals("")){

            Log.i("ErrorimgConductorFoto2","AA");
            try{

                CargaImagenesThumb2 cargaImagenesThumb2 = new CargaImagenesThumb2();
                cargaImagenesThumb2.execute(ConductorFotoThumb2Actual);

            }catch (Exception ex){
                imgConductorFoto2.setImageResource(R.mipmap.conductor_foto);
                Log.i("ErrorimgConductorFoto2",ex.toString());
            }

        }
*/

        //DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //double ancho =  (metrics.widthPixels) - (metrics.widthPixels*(0.2));
        //double alto =  metrics.heightPixels  - (metrics.heightPixels*(0.4));

        //int n_ancho = (int) ancho;
        //int n_alto = (int) alto;

        //final ImagePopup imagePopup = new ImagePopup(this);
        //imagePopup.setBackgroundColor(Color.BLACK);
        //imagePopup.setWindowWidth(n_ancho);
        //imagePopup.setWindowHeight(n_alto);
        //imagePopup.setHideCloseIcon(true);
        //imagePopup.setImageOnClickClose(true);
        //imagePopup.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imagePopup.setBaselineAlignBottom(true);

       // final ImageView imageView = (ImageView) findViewById(R.id.ImgConductorFoto1);
        //final ImageView imageView2 = (ImageView) findViewById(R.id.ImgConductorFoto2);
        //imgConductorFoto1 = (ImageView) findViewById(R.id.ImgConductorFoto1);

      /*  imgConductorFoto1.setClickable(true);

        imgConductorFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //imagePopup.initiatePopup(imgConductorFoto1.getDrawable());
                //imagePopup.setBackgroundColor(R.color.colorAccent);
                android.app.AlertDialog.Builder imageDialog = new android.app.AlertDialog.Builder(ConductorActualActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.conductor_foto,
                        (ViewGroup) findViewById(R.id.layout_root));
                ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
                // image.setMaxHeight(300);
                // image.setLa
                image.setImageDrawable(imgConductorFoto1.getDrawable());
                imageDialog.setView(layout);


                imageDialog.create();
                imageDialog.show();

            }
        });
*/



       // imgConductorVehiculoFoto1 = (ImageView) findViewById(R.id.ImgConductorVehiculoFoto1);

       /* imgConductorVehiculoFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //imagePopup.initiatePopup(imgConductorVehiculoFoto1.getDrawable());
                //imagePopup.setBackgroundColor(R.color.colorAccent);

                android.app.AlertDialog.Builder imageDialog = new android.app.AlertDialog.Builder(ConductorActualActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.conductor_foto,
                        (ViewGroup) findViewById(R.id.layout_root));
                ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
                // image.setMaxHeight(300);
                // image.setLa
                image.setImageDrawable(imgConductorVehiculoFoto1.getDrawable());
                imageDialog.setView(layout);


                imageDialog.create();
                imageDialog.show();
            }
        });
*/












        /*if(!ConductorFotoActual.equals("")){
            ImageView imageView = (ImageView)  findViewById(R.id.imageView4);
            imageView.setImageResource(R.mipmap.conductor_foto);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new DownloadImageTask((ImageView) findViewById(R.id.imageView4))
                            .execute(ConductorFotoActual);

                }
            });
        }else{
            ImageView imageView = (ImageView)  findViewById(R.id.imageView4);
            imageView.setImageResource(R.mipmap.conductor_foto);
        }
*/

        if(ConductorSupervisorNivel.equals("2") || ConductorSupervisorNivel.equals("3")){

            if(ConductorEstadoActual.equals("1")){

                ImageButton btnConductorActualHabilitar = (ImageButton)findViewById(R.id.BtnConductorActualHabilitar);
                btnConductorActualHabilitar.setBackgroundResource(R.drawable.dra_boton_fondo2);

                ImageButton btnConductorActualDeshabilitar = (ImageButton)findViewById(R.id.BtnConductorActualDeshabilitar);
                btnConductorActualDeshabilitar.setBackgroundResource(R.drawable.dra_boton_fondo);


            }else {

                ImageButton btnConductorActualHabilitar = (ImageButton)findViewById(R.id.BtnConductorActualHabilitar);
                btnConductorActualHabilitar.setBackgroundResource(R.drawable.dra_boton_fondo);

                ImageButton btnConductorActualDeshabilitar = (ImageButton)findViewById(R.id.BtnConductorActualDeshabilitar);
                btnConductorActualDeshabilitar.setBackgroundResource(R.drawable.dra_boton_fondo2);

            }

        }else {

            ImageButton btnConductorActualHabilitar = (ImageButton)findViewById(R.id.BtnConductorActualHabilitar);
            btnConductorActualHabilitar.setBackgroundResource(R.drawable.dra_boton_fondo2);

            ImageButton btnConductorActualDeshabilitar = (ImageButton)findViewById(R.id.BtnConductorActualDeshabilitar);
            btnConductorActualDeshabilitar.setBackgroundResource(R.drawable.dra_boton_fondo2);

        }

        if(ConductorCelularActual.equals("") && ConductorCelularActual.equals("")){

            ImageButton btnConductorActualLlamar = (ImageButton)findViewById(R.id.BtnConductorActualLlamar);
            btnConductorActualLlamar.setBackgroundResource(R.drawable.dra_boton_fondo2);

        }else {

            ImageButton btnConductorActualLlamar2 = (ImageButton)findViewById(R.id.BtnConductorActualLlamar);
            btnConductorActualLlamar2.setBackgroundResource(R.drawable.dra_boton_fondo);

        }


        if(ConductorSupervisorNivel.equals("3")){

            ImageButton btnConductorActualResetear = (ImageButton)findViewById(R.id.BtnConductorActualResetear);
            btnConductorActualResetear.setBackgroundResource(R.drawable.dra_boton_fondo);

        }else {

            ImageButton btnConductorActualResetear = (ImageButton)findViewById(R.id.BtnConductorActualResetear);
            btnConductorActualResetear.setBackgroundResource(R.drawable.dra_boton_fondo2);


        }


        /*if((VehiculoCoordenadaXActual.equals("") && VehiculoCoordenadaYActual.equals("")) || VehiculoCoordenadaXActual.equals("0.00") && VehiculoCoordenadaYActual.equals("0.00")){

            ImageButton btnConductorActualUbicar = (ImageButton)findViewById(R.id.BtnConductorActualUbicar);
            btnConductorActualUbicar.setBackgroundResource(R.drawable.dra_boton_fondo2);

        }else{

            ImageButton btnConductorActualUbicar2 = (ImageButton)findViewById(R.id.BtnConductorActualUbicar);
            btnConductorActualUbicar2.setBackgroundResource(R.drawable.dra_boton_fondo);

        }*/

    }


    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");

        Intent intentBuscarConductor = new Intent(ConductorActualActivity.this, BuscarConductorActivity.class);
        Bundle bundleBuscarConductor = new Bundle();

        bundleBuscarConductor.putString("ConductorSupervisorNivel", ConductorSupervisorNivel);

        intentBuscarConductor.putExtras(bundleBuscarConductor);//Put your id to your next Intent
        startActivity(intentBuscarConductor);
        ConductorActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conductor_actual, menu);
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

                Intent intentBuscarConductor = new Intent(ConductorActualActivity.this, BuscarConductorActivity.class);
                Bundle bundleBuscarConductor = new Bundle();

                bundleBuscarConductor.putString("ConductorSupervisorNivel", ConductorSupervisorNivel);

                intentBuscarConductor.putExtras(bundleBuscarConductor);//Put your id to your next Intent
                startActivity(intentBuscarConductor);
                ConductorActualActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
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

        Toast.makeText(ConductorActualActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }




    public void onClick_BtnConductorActualRegresar(View v){

        /*Intent intent = new Intent(ConductorActualActivity.this, BuscarConductorActivity.class);
        startActivity(intent);
        finish();*/

        Intent intentBuscarConductor = new Intent(ConductorActualActivity.this, BuscarConductorActivity.class);
        Bundle bundleBuscarConductor = new Bundle();

        bundleBuscarConductor.putString("ConductorSupervisorNivel", ConductorSupervisorNivel);

        intentBuscarConductor.putExtras(bundleBuscarConductor);//Put your id to your next Intent
        startActivity(intentBuscarConductor);
        finish();


    }


    public void onClick_BtnConductorActualUbicar(View v){

        //FncMostrarMensaje("Esta opcion esta en desarrollo.", false);



        if((!VehiculoCoordenadaXActual.equals("") && !VehiculoCoordenadaYActual.equals("")) || !VehiculoCoordenadaXActual.equals("0.00") && !VehiculoCoordenadaYActual.equals("0.00")){

            Intent intentConductorUbicacion = new Intent(ConductorActualActivity.this, ConductorUbicacionActivity.class);
            Bundle bundleConductorUbicacion = new Bundle();

            bundleConductorUbicacion.putString("ConductorIdActual",ConductorIdActual);

            bundleConductorUbicacion.putString("ConductorNombreActual", ConductorNombreActual);
            bundleConductorUbicacion.putString("ConductorApellidoActual", ConductorApellidoActual);

            bundleConductorUbicacion.putString("ConductorCelularActual", ConductorCelularActual);
            bundleConductorUbicacion.putString("ConductorTelefonoActual",ConductorTelefonoActual);
            bundleConductorUbicacion.putString("ConductorDireccionActual", ConductorDireccionActual);

            bundleConductorUbicacion.putString("ConductorFotoActual", ConductorFotoActual);
            bundleConductorUbicacion.putString("ConductorFotoThumb1Actual", ConductorFotoThumb1Actual);
            bundleConductorUbicacion.putString("ConductorFotoThumb2Actual", ConductorFotoThumb2Actual);

            bundleConductorUbicacion.putString("ConductorVehiculoFotoActual", ConductorVehiculoFotoActual);
            bundleConductorUbicacion.putString("ConductorVehiculoFotoThumb1Actual", ConductorVehiculoFotoThumb1Actual);
            bundleConductorUbicacion.putString("ConductorVehiculoFotoThumb2Actual", ConductorVehiculoFotoThumb2Actual);


            bundleConductorUbicacion.putString("ConductorEstadoActual", ConductorEstadoActual);
            bundleConductorUbicacion.putString("ConductorEstadoDescripcionActual", ConductorEstadoDescripcionActual);
            bundleConductorUbicacion.putString("ConductorEstadoMotivoActual", ConductorEstadoMotivoActual);
            bundleConductorUbicacion.putString("ConductorEstadoResponsableActual", ConductorEstadoResponsableActual);

            bundleConductorUbicacion.putString("VehiculoPlacaActual", VehiculoPlacaActual);
            bundleConductorUbicacion.putString("VehiculoUnidadActual", VehiculoUnidadActual);
            bundleConductorUbicacion.putString("VehiculoColorActual", VehiculoColorActual);
            bundleConductorUbicacion.putString("VehiculoModeloActual", VehiculoModeloActual);
            bundleConductorUbicacion.putString("VehiculoCarroceriaActual", VehiculoCarroceriaActual);

            bundleConductorUbicacion.putString("VehiculoCoordenadaXActual",VehiculoCoordenadaXActual);
            bundleConductorUbicacion.putString("VehiculoCoordenadaYActual",VehiculoCoordenadaYActual);
            bundleConductorUbicacion.putInt("VehiculoGPSOrientacionActual",VehiculoGPSOrientacionActual);

            bundleConductorUbicacion.putString("ConductorSupervisorNivel",ConductorSupervisorNivel);

            intentConductorUbicacion.putExtras(bundleConductorUbicacion);//Put your id to your next Intent
            startActivity(intentConductorUbicacion);
            finish();


        }





    }

    public void onClick_BtnConductorActualLlamar(View v){

        if(!ConductorCelularActual.equals("")) {

            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ConductorCelularActual));
                startActivity(callIntent);
            } catch (Exception e) {
                FncMostrarToast("Ha ocurrido un error interno");
                //Log.e("helloandroid dialing example", "Call failed", e);
            }

            //FncLlamarNumero(ConductorCelular);
        }else if(!ConductorTelefonoActual.equals("")){

            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ConductorTelefonoActual));
                startActivity(callIntent);
            } catch (Exception e) {
                FncMostrarToast("Ha ocurrido un error interno");
                //Log.e("helloandroid dialing example", "Call failed", e);
            }

        }else{

            FncMostrarMensaje("No se encontró número de celular o teléfono del conductor",false);

        }

    }


    public void onClick_BtnConductorActualHabilitar(View v){

        if(!ConductorEstadoActual.equals("1")){

             if(ConductorSupervisorNivel.equals("2") || ConductorSupervisorNivel.equals("3")){

                AlertDialog.Builder MsgCondutorActual = new AlertDialog.Builder(this);
                MsgCondutorActual.setTitle(getString(R.string.alert_title));
                MsgCondutorActual.setMessage("¿Realmente desea habilitar al conductor?");
                MsgCondutorActual.setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog





                                // get prompts.xml view
                                LayoutInflater layoutInflater = LayoutInflater.from(ConductorActualActivity.this);

                                View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConductorActualActivity.this);
                                alertDialogBuilder.setView(promptView);

                                final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                                // setup a dialog window
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {



if(editText.getText().toString().equals("")){

    FncMostrarMensaje("No ha ingresado un motivo.",false);

}else{

    final ProgressDialog PrgConductorActual = new ProgressDialog(ConductorActualActivity.this);
    PrgConductorActual.setIcon(R.mipmap.ic_launcher);
    PrgConductorActual.setMessage("Cargando...");
    PrgConductorActual.setCancelable(false);
    PrgConductorActual.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    PrgConductorActual.show();

    Thread nt = new Thread() {
        @Override
        public void run() {

            try {

                final String resMtdHabilitarConductor;
                resMtdHabilitarConductor = MtdHabilitarConductor(
                        ConductorIdActual,
                        ConductorSupervisorNivel,
                        ConductorId,
                        ConductorNumeroDocumento,
                        VehiculoUnidad,
                        editText.getText().toString(),
                        ConductorCelular);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String JsRespuesta = "";

                        try {

                            JSONObject jsonObject = new JSONObject(resMtdHabilitarConductor);
                            JsRespuesta = jsonObject.getString("Respuesta");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        PrgConductorActual.cancel();

                        switch(JsRespuesta){

                            case "S006":

                                FncMostrarMensaje("Conductor habilitado correctamente",false);

                                ImageButton btnConductorActualHabilitar = (ImageButton)findViewById(R.id.BtnConductorActualHabilitar);
                                btnConductorActualHabilitar.setBackgroundResource(R.drawable.dra_boton_fondo2);

                                ImageButton btnConductorActualDeshabilitar = (ImageButton)findViewById(R.id.BtnConductorActualDeshabilitar);
                                btnConductorActualDeshabilitar.setBackgroundResource(R.drawable.dra_boton_fondo);

                                TextView TxtConductorEstadoMotivo = (TextView) findViewById(R.id.CmpConductorEstadoMotivo);
                                TxtConductorEstadoMotivo.setText( editText.getText().toString());


                                ConductorEstadoActual = "1";

                                break;

                            case "S007":
                                FncMostrarMensaje("No se ha podido habilitar al conductor, ha ocurrido un error",false);

                                break;

                            case "S008":

                                AlertDialog.Builder MsgConductorActual11 = new AlertDialog.Builder(ConductorActualActivity.this);
                                MsgConductorActual11.setTitle(getString(R.string.alert_title));
                                MsgConductorActual11.setMessage("No se identifico codigo de conductor.");
                                MsgConductorActual11.setPositiveButton("Aceptar",
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                // Do nothing but close the dialog
                                                Intent intentBuscarConductor = new Intent(ConductorActualActivity.this, SupervisorActivity.class);
                                                startActivity(intentBuscarConductor);
                                                finish();
                                            }
                                        });

                                // Remember, create doesn't show the dialog
                                AlertDialog msgConductorActual11 = MsgConductorActual11.create();
                                msgConductorActual11.show();

                                break;


                            case "S014":

                                FncMostrarMensaje("No tienes permisos para realizar esta accion", false);

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

    nt.start();

}








                                            }
                                        })
                                        .setNegativeButton("Cancelar",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();

                                                    }
                                                });

                                // create an alert dialog
                                AlertDialog alert = alertDialogBuilder.create();
                                alert.show();











                            }
                        });

                MsgCondutorActual.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                            }
                        });

                // Remember, create doesn't show the dialog
                AlertDialog msgCondutorActual = MsgCondutorActual.create();
                msgCondutorActual.show();

            }



        }else{
            FncMostrarMensaje("El conductor ya se encuentra habilitado", false);
        }


    }

    public void onClick_BtnConductorActualDeshabilitar(View v){

        if(!ConductorEstadoActual.equals("2")){

              if(ConductorSupervisorNivel.equals("2") || ConductorSupervisorNivel.equals("3")){

                AlertDialog.Builder MsgCondutorActual = new AlertDialog.Builder(this);
                MsgCondutorActual.setTitle(getString(R.string.alert_title));
                MsgCondutorActual.setMessage("¿Realmente desea deshabilitar al conductor?");
                MsgCondutorActual.setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog





                                // get prompts.xml view
                                LayoutInflater layoutInflater = LayoutInflater.from(ConductorActualActivity.this);

                                View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConductorActualActivity.this);
                                alertDialogBuilder.setView(promptView);

                                final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                                // setup a dialog window
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                // resultText.setText("Hello, " + editText.getText());


                                                if(editText.getText().toString().equals("")){

                                                    FncMostrarMensaje("No ha ingresado un motivo.",false);

                                                }else{


                                                    final ProgressDialog PrgConductorActual = new ProgressDialog(ConductorActualActivity.this);
                                                    PrgConductorActual.setIcon(R.mipmap.ic_launcher);
                                                    PrgConductorActual.setMessage("Cargando...");
                                                    PrgConductorActual.setCancelable(false);
                                                    PrgConductorActual.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                    PrgConductorActual.show();

                                                    Thread nt = new Thread() {
                                                        @Override
                                                        public void run() {

                                                            try {

                                                                final String resMtdDeshabilitarConductor;

                                                                resMtdDeshabilitarConductor = MtdDeshabilitarConductor(
                                                                        ConductorIdActual,
                                                                        ConductorSupervisorNivel,
                                                                        ConductorId,
                                                                        ConductorNumeroDocumento,
                                                                        VehiculoUnidad,
                                                                        editText.getText().toString(),
                                                                        ConductorCelular);

                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {

                                                                        String JsRespuesta = "";

                                                                        try {

                                                                            JSONObject jsonObject = new JSONObject(resMtdDeshabilitarConductor);
                                                                            JsRespuesta = jsonObject.getString("Respuesta");

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        PrgConductorActual.cancel();

                                                                        switch(JsRespuesta){

                                                                            case "S009":

                                                                                FncMostrarMensaje("Conductor deshabilitado correctamente",false);

                                                                                ImageButton btnConductorActualHabilitar = (ImageButton)findViewById(R.id.BtnConductorActualHabilitar);
                                                                                btnConductorActualHabilitar.setBackgroundResource(R.drawable.dra_boton_fondo);

                                                                                ImageButton btnConductorActualDeshabilitar = (ImageButton)findViewById(R.id.BtnConductorActualDeshabilitar);
                                                                                btnConductorActualDeshabilitar.setBackgroundResource(R.drawable.dra_boton_fondo2);

                                                                                TextView TxtConductorEstadoMotivo = (TextView) findViewById(R.id.CmpConductorEstadoMotivo);
                                                                                TxtConductorEstadoMotivo.setText( editText.getText().toString());

                                                                                ConductorEstadoActual = "2";
                                                                                break;



                                                                            case "S010":
                                                                                FncMostrarMensaje("No se ha podido deshabilitar al conductor, ha ocurrido un error",false);

                                                                                break;

                                                                            case "S011":

                                                                                AlertDialog.Builder MsgConductorActual8 = new AlertDialog.Builder(ConductorActualActivity.this);
                                                                                MsgConductorActual8.setTitle(getString(R.string.alert_title));
                                                                                MsgConductorActual8.setMessage("No se identifico codigo de conductor.");
                                                                                MsgConductorActual8.setPositiveButton("Aceptar",
                                                                                        new DialogInterface.OnClickListener() {

                                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                                // Do nothing but close the dialog
                                                                                                Intent intentBuscarConductor = new Intent(ConductorActualActivity.this, SupervisorActivity.class);
                                                                                                startActivity(intentBuscarConductor);
                                                                                                finish();
                                                                                            }
                                                                                        });

                                                                                // Remember, create doesn't show the dialog
                                                                                AlertDialog msgConductorActual8 = MsgConductorActual8.create();
                                                                                msgConductorActual8.show();

                                                                                break;

                                                                            case "S015":

                                                                                FncMostrarMensaje("No tienes permisos para realizar esta accion", false);


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

                                                    nt.start();

                                                }






                                            }
                                        })
                                        .setNegativeButton("Cancelar",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();

                                                    }
                                                });

                                // create an alert dialog
                                AlertDialog alert = alertDialogBuilder.create();
                                alert.show();












                            }
                        });

                MsgCondutorActual.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                            }
                        });

                // Remember, create doesn't show the dialog
                AlertDialog msgCondutorActual = MsgCondutorActual.create();
                msgCondutorActual.show();

            }

        }else {
            FncMostrarMensaje("El conductor ya se encuentra deshabilitado", false);
        }

    }







    public void onClick_BtnConductorActualRetirar(View v){

        if(!ConductorEstadoActual.equals("3")){

            if(ConductorSupervisorNivel.equals("2") || ConductorSupervisorNivel.equals("3")){

                AlertDialog.Builder MsgCondutorActual = new AlertDialog.Builder(this);
                MsgCondutorActual.setTitle(getString(R.string.alert_title));
                MsgCondutorActual.setMessage("¿Realmente desea retirar al conductor?. NOTA: Al retirar solo podra revertirse el cambio desde la central.");
                MsgCondutorActual.setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog





                                // get prompts.xml view
                                LayoutInflater layoutInflater = LayoutInflater.from(ConductorActualActivity.this);

                                View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConductorActualActivity.this);
                                alertDialogBuilder.setView(promptView);

                                final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                                // setup a dialog window
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {



                                                if(editText.getText().toString().equals("")){

                                                    FncMostrarMensaje("No ha ingresado un motivo.",false);

                                                }else{

                                                    final ProgressDialog PrgConductorActual = new ProgressDialog(ConductorActualActivity.this);
                                                    PrgConductorActual.setIcon(R.mipmap.ic_launcher);
                                                    PrgConductorActual.setMessage("Cargando...");
                                                    PrgConductorActual.setCancelable(false);
                                                    PrgConductorActual.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                    PrgConductorActual.show();

                                                    Thread nt = new Thread() {
                                                        @Override
                                                        public void run() {

                                                            try {

                                                                final String resMtdRetirarConductor;
                                                                resMtdRetirarConductor = MtdRetirarConductor(
                                                                        ConductorIdActual,
                                                                        ConductorSupervisorNivel,
                                                                        ConductorId,
                                                                        ConductorNumeroDocumento,
                                                                        VehiculoUnidad,
                                                                        editText.getText().toString(),
                                                                        ConductorCelular);

                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {

                                                                        String JsRespuesta = "";

                                                                        try {

                                                                            JSONObject jsonObject = new JSONObject(resMtdRetirarConductor);
                                                                            JsRespuesta = jsonObject.getString("Respuesta");

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        PrgConductorActual.cancel();

                                                                        switch(JsRespuesta){

                                                                            case "S030":

                                                                                FncMostrarMensaje("Conductor retirado correctamente",false);

                                                                                ImageButton btnConductorActualHabilitar = (ImageButton)findViewById(R.id.BtnConductorActualHabilitar);
                                                                                btnConductorActualHabilitar.setBackgroundResource(R.drawable.dra_boton_fondo);

                                                                                ImageButton btnConductorActualDeshabilitar = (ImageButton)findViewById(R.id.BtnConductorActualDeshabilitar);
                                                                                btnConductorActualDeshabilitar.setBackgroundResource(R.drawable.dra_boton_fondo);

                                                                                ImageButton btnConductorActualRetirar = (ImageButton)findViewById(R.id.BtnConductorActualRetirar);
                                                                                btnConductorActualRetirar.setBackgroundResource(R.drawable.dra_boton_fondo2);


                                                                                TextView TxtConductorEstadoMotivo = (TextView) findViewById(R.id.CmpConductorEstadoMotivo);
                                                                                TxtConductorEstadoMotivo.setText( editText.getText().toString());


                                                                                ConductorEstadoActual = "3";

                                                                                break;

                                                                            case "S031":
                                                                                FncMostrarMensaje("No se ha podido retirar al conductor, ha ocurrido un error",false);

                                                                                break;

                                                                            case "S033":

                                                                                AlertDialog.Builder MsgConductorActual11 = new AlertDialog.Builder(ConductorActualActivity.this);
                                                                                MsgConductorActual11.setTitle(getString(R.string.alert_title));
                                                                                MsgConductorActual11.setMessage("No se identifico codigo de conductor.");
                                                                                MsgConductorActual11.setPositiveButton("Aceptar",
                                                                                        new DialogInterface.OnClickListener() {

                                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                                // Do nothing but close the dialog
                                                                                                Intent intentBuscarConductor = new Intent(ConductorActualActivity.this, SupervisorActivity.class);
                                                                                                startActivity(intentBuscarConductor);
                                                                                                finish();
                                                                                            }
                                                                                        });

                                                                                // Remember, create doesn't show the dialog
                                                                                AlertDialog msgConductorActual11 = MsgConductorActual11.create();
                                                                                msgConductorActual11.show();

                                                                                break;

                                                                            case "S032":

                                                                                FncMostrarMensaje("No tienes permisos para realizar esta accion", false);

                                                                                break;

                                                                            default://NO ES CRITICO

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
                                        })
                                        .setNegativeButton("Cancelar",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();

                                                    }
                                                });

                                // create an alert dialog
                                AlertDialog alert = alertDialogBuilder.create();
                                alert.show();











                            }
                        });

                MsgCondutorActual.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                            }
                        });

                // Remember, create doesn't show the dialog
                AlertDialog msgCondutorActual = MsgCondutorActual.create();
                msgCondutorActual.show();

            }



        }else{
            FncMostrarMensaje("El conductor ya se encuentra retirado", false);
        }


    }



    public void onClick_BtnConductorActualSincronizar(View v){

          if(ConductorSupervisorNivel.equals("2") || ConductorSupervisorNivel.equals("3")){

            AlertDialog.Builder MsgCondutorActual = new AlertDialog.Builder(this);
            MsgCondutorActual.setTitle(getString(R.string.alert_title));
            MsgCondutorActual.setMessage("¿Realmente desea sincronizar los datos del conductor?");
            MsgCondutorActual.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            final ProgressDialog PrgConductorActual = new ProgressDialog(ConductorActualActivity.this);
                            PrgConductorActual.setIcon(R.mipmap.ic_launcher);
                            PrgConductorActual.setMessage("Cargando...");
                            PrgConductorActual.setCancelable(false);
                            PrgConductorActual.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            PrgConductorActual.show();

                            Thread nt = new Thread() {
                                @Override
                                public void run() {

                                    try {

                                        final String resMtdSincronizarConductor;
                                        resMtdSincronizarConductor = MtdSincronizarConductor(ConductorIdActual,ConductorSupervisorNivel,ConductorId,ConductorNumeroDocumento,VehiculoUnidad);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                String JsRespuesta = "";

                                                try {

                                                    JSONObject jsonObject = new JSONObject(resMtdSincronizarConductor);
                                                    JsRespuesta = jsonObject.getString("Respuesta");

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                PrgConductorActual.cancel();

                                                switch(JsRespuesta){

                                                    case "S016":

                                                        FncMostrarMensaje("No se ha podido extablecer conexion con el servidor",false);

                                                        break;

                                                    case "S017":

                                                        FncMostrarMensaje("Datos de conductor sincronizado correctamente",false);

                                                        break;

                                                    case "S018":
                                                        FncMostrarMensaje("No se ha podido sincronizar los datos del conductor, ha ocurrido un error",false);

                                                        break;

                                                    case "S019":

                                                        FncMostrarMensaje("Datos de conductor sincronizado correctamente",false);

                                                        break;

                                                    case "S020":
                                                        FncMostrarMensaje("No se ha podido sincronizar los datos del conductor, ha ocurrido un error",false);

                                                        break;

                                                    case "S021":

                                                        FncMostrarMensaje("No tienes permisos para realizar esta accion", false);

                                                        break;

                                                    case "S022":

                                                        AlertDialog.Builder MsgConductorActual8 = new AlertDialog.Builder(ConductorActualActivity.this);
                                                        MsgConductorActual8.setTitle(getString(R.string.alert_title));
                                                        MsgConductorActual8.setMessage("No se identifico codigo de conductor.");
                                                        MsgConductorActual8.setPositiveButton("Aceptar",
                                                                new DialogInterface.OnClickListener() {

                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        // Do nothing but close the dialog
                                                                        Intent intentBuscarConductor = new Intent(ConductorActualActivity.this, SupervisorActivity.class);
                                                                        startActivity(intentBuscarConductor);
                                                                        finish();
                                                                    }
                                                                });

                                                        // Remember, create doesn't show the dialog
                                                        AlertDialog msgConductorActual8 = MsgConductorActual8.create();
                                                        msgConductorActual8.show();

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

                            nt.start();


                        }
                    });

            MsgCondutorActual.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });

            // Remember, create doesn't show the dialog
            AlertDialog msgCondutorActual = MsgCondutorActual.create();
            msgCondutorActual.show();


        }


    }


    public void onClick_BtnConductorActualResetear(View v){

          //if(ConductorSupervisorNivel.equals("2") || ConductorSupervisorNivel.equals("3")){
        if(ConductorSupervisorNivel.equals("3")){

            AlertDialog.Builder MsgCondutorActual = new AlertDialog.Builder(this);
            MsgCondutorActual.setTitle(getString(R.string.alert_title));
            MsgCondutorActual.setMessage("¿Realmente desea resetear el identificador de equipo del conductor?");
            MsgCondutorActual.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            final ProgressDialog PrgConductorActual = new ProgressDialog(ConductorActualActivity.this);
                            PrgConductorActual.setIcon(R.mipmap.ic_launcher);
                            PrgConductorActual.setMessage("Cargando...");
                            PrgConductorActual.setCancelable(false);
                            PrgConductorActual.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            PrgConductorActual.show();

                            Thread nt = new Thread() {
                                @Override
                                public void run() {

                                    try {

                                        final String resMtdResetearConductor;
                                        resMtdResetearConductor = MtdResetearConductor(ConductorIdActual,ConductorSupervisorNivel,ConductorId,ConductorNumeroDocumento,VehiculoUnidad);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                String JsRespuesta = "";

                                                try {

                                                    JSONObject jsonObject = new JSONObject(resMtdResetearConductor);
                                                    JsRespuesta = jsonObject.getString("Respuesta");

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                PrgConductorActual.cancel();

                                                switch(JsRespuesta){

                                                    case "S023":

                                                        FncMostrarMensaje("No se ha podido extablecer conexion con el servidor",false);

                                                        break;

                                                    case "S024":

                                                        FncMostrarMensaje("Identificador de equipo de conductor reseteado correctamente",false);

                                                        ImageButton btnConductorActualHabilitar = (ImageButton)findViewById(R.id.BtnConductorActualHabilitar);
                                                        btnConductorActualHabilitar.setBackgroundResource(R.drawable.dra_boton_fondo);

                                                        ImageButton btnConductorActualDeshabilitar = (ImageButton)findViewById(R.id.BtnConductorActualDeshabilitar);
                                                        btnConductorActualDeshabilitar.setBackgroundResource(R.drawable.dra_boton_fondo2);

                                                        break;



                                                    case "S025":

                                                        FncMostrarMensaje("No se ha podido resetear el identificador de equipo del conductor, ha ocurrido un error",false);

                                                        break;

                                                    case "S026":

                                                        FncMostrarMensaje("No tienes permisos para realizar esta accion", false);

                                                        break;

                                                    case "S027":

                                                        AlertDialog.Builder MsgConductorActual8 = new AlertDialog.Builder(ConductorActualActivity.this);
                                                        MsgConductorActual8.setTitle(getString(R.string.alert_title));
                                                        MsgConductorActual8.setMessage("No se identifico codigo de conductor.");
                                                        MsgConductorActual8.setPositiveButton("Aceptar",
                                                                new DialogInterface.OnClickListener() {

                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        // Do nothing but close the dialog
                                                                        Intent intentBuscarConductor = new Intent(ConductorActualActivity.this, SupervisorActivity.class);
                                                                        startActivity(intentBuscarConductor);
                                                                        finish();
                                                                    }
                                                                });

                                                        // Remember, create doesn't show the dialog
                                                        AlertDialog msgConductorActual8 = MsgConductorActual8.create();
                                                        msgConductorActual8.show();

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

                            nt.start();


                        }
                    });

            MsgCondutorActual.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                        }
                    });

            // Remember, create doesn't show the dialog
            AlertDialog msgCondutorActual = MsgCondutorActual.create();
            msgCondutorActual.show();


        }


    }
  /*
   * ENVIO DE VARIABLES
     */



    public String MtdHabilitarConductor(String oConductorIdActual,String oConductorSupervisorNivel,String oConductorId,String oConductorNumeroDocumento,String oVehiculoUnidad,String oEstadoMotivo,String oCelular) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductorsupervisor));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("ConductorIdActual", oConductorIdActual);
            postDataParams.put("ConductorEstadoMotivoActual", oEstadoMotivo);

            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);
            postDataParams.put("ConductorVehiculoUnidad", oVehiculoUnidad);
            postDataParams.put("ConductorCelular", oCelular);
            postDataParams.put("ConductorSupervisorNivel", oConductorSupervisorNivel);


            postDataParams.put("Identificador", Identificador);
            postDataParams.put("AppVersion", getString(R.string.app_version));
            postDataParams.put("Accion", "HabilitarConductor");

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

            Log.e("MsgConductorActual1", response);

        } catch (Exception e) {

            Log.e("MsgConductorActual2", e.toString());
            e.printStackTrace();
        }

        return response;

    }

    public String MtdDeshabilitarConductor(String oConductorIdActual,String oConductorSupervisorNivel,String oConductorId,String oConductorNumeroDocumento,String oVehiculoUnidad,String oEstadoMotivo,String oCelular) {
        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductorsupervisor));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("ConductorIdActual", oConductorIdActual);
            postDataParams.put("ConductorEstadoMotivoActual", oEstadoMotivo);

            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);
            postDataParams.put("ConductorVehiculoUnidad", oVehiculoUnidad);
            postDataParams.put("ConductorCelular", oCelular);
            postDataParams.put("ConductorSupervisorNivel", oConductorSupervisorNivel);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("AppVersion", getString(R.string.app_version));
            postDataParams.put("Accion", "DeshabilitarConductor");

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

            Log.e("MsgConductorActual1", response);

        } catch (Exception e) {

            Log.e("MsgConductorActual2", e.toString());
            e.printStackTrace();
        }

        return response;

    }


    public String MtdRetirarConductor(String oConductorIdActual,String oConductorSupervisorNivel,String oConductorId,String oConductorNumeroDocumento,String oVehiculoUnidad,String oEstadoMotivo,String oCelular) {
        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductorsupervisor));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("ConductorIdActual", oConductorIdActual);
            postDataParams.put("ConductorEstadoMotivoActual", oEstadoMotivo);

            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);
            postDataParams.put("ConductorVehiculoUnidad", oVehiculoUnidad);
            postDataParams.put("ConductorCelular", oCelular);
            postDataParams.put("ConductorSupervisorNivel", oConductorSupervisorNivel);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("AppVersion", getString(R.string.app_version));
            postDataParams.put("Accion", "RetirarConductor");

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

            Log.e("MsgConductorActual7", response);

        } catch (Exception e) {

            Log.e("MsgConductorActual8", e.toString());
            e.printStackTrace();
        }

        return response;

    }



    public String MtdSincronizarConductor(String oConductorIdActual,String oConductorSupervisorNivel,String oConductorId,String oConductorNumeroDocumento,String oVehiculoUnidad) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductorsupervisor));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("ConductorIdActual", oConductorIdActual);

            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);
            postDataParams.put("ConductorVehiculoUnidad", oVehiculoUnidad);
            postDataParams.put("ConductorSupervisorNivel", oConductorSupervisorNivel);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("AppVersion", getString(R.string.app_version));
            postDataParams.put("Accion", "SincronizarConductor");

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

            Log.e("MsgConductorActual1", response);

        } catch (Exception e) {

            Log.e("MsgConductorActual2", e.toString());
            e.printStackTrace();
        }

        return response;

    }

    public String MtdResetearConductor(String oConductorIdActual,String oConductorSupervisorNivel,String oConductorId,String oConductorNumeroDocumento,String oVehiculoUnidad) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductorsupervisor));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();

            postDataParams.put("ConductorIdActual", oConductorIdActual);

            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);
            postDataParams.put("ConductorVehiculoUnidad", oVehiculoUnidad);
            postDataParams.put("ConductorSupervisorNivel", oConductorSupervisorNivel);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("AppVersion", getString(R.string.app_version));
            postDataParams.put("Accion", "ResetearConductor");

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

            Log.e("MsgConductorActual1", response);

        } catch (Exception e) {

            Log.e("MsgConductorActual2", e.toString());
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
        ConductorCelular = sharedPreferences.getString("ConductorCelular","");

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
/*

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

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



   /* private class CargaImagenesThumb1 extends AsyncTask<String, Void, Bitmap>{

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

           // pDialog = new ProgressDialog(ConductorActualActivity.this);
            //pDialog.setMessage("Cargando Imagen");
           // pDialog.setCancelable(true);
           // pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //pDialog.show();

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

            imgConductorFoto1.setImageBitmap(result);
            //imgConductorFoto2.setImageBitmap(result);
            //timConductorFoto2.setImageBitmap(result);

            //pDialog.dismiss();
        }

    }*/

/*
    private class CargaImagenesThumb2 extends AsyncTask<String, Void, Bitmap>{

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            //pDialog = new ProgressDialog(ConductorActualActivity.this);
            //pDialog.setMessage("Cargando Imagen");
            //pDialog.setCancelable(true);
           // pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           // pDialog.show();

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

            //imgConductorFoto1.setImageBitmap(result);
            imgConductorFoto2.setImageBitmap(result);
            //timConductorFoto2.setImageBitmap(result);

            //pDialog.dismiss();
        }

    }*/

    /*private Bitmap descargarImagen (String imageHttpAddress){
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



}
