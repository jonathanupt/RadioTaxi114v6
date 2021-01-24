package radiotaxi114.radiotaxi114v6;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

//import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {


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
    * PUBLICIDAD
     */

    ImageView imgPublicidadArchivo;
    ImageView imgPublicidadArchivo2;


    /*
PERMISOS
*/
    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_activity);
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


        Log.e("Ranking20", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_ranking));

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");

        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        //PERMISOS
        context = getApplicationContext();
        activity = this;

        //FORMULARIOS
        imgPublicidadArchivo2 = (ImageView) findViewById(R.id.ImgPublicidadArchivo2);

        //PUBLICIDAD ABAJO
        //DisplayMetrics metrics2 = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics2);

        //double ancho2 =  (metrics2.widthPixels) - (metrics2.widthPixels*(0.2));
        //double alto2 =  metrics2.heightPixels  - (metrics2.heightPixels*(0.6));

        //int n_ancho2 = (int) ancho2;
        //int n_alto2 = (int) alto2;

        //final ImagePopup imagePopup2 = new ImagePopup(RankingActivity.this);
        //imagePopup2.setBackgroundColor(Color.TRANSPARENT);
        //imagePopup2.setWindowWidth(n_ancho2);
        //imagePopup2.setWindowHeight(n_alto2);
        //imagePopup2.setHideCloseIcon(true);
        //imagePopup2.setImageOnClickClose(true);
        //imagePopup2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imagePopup2.setBaselineAlignBottom(true);


        /*imgPublicidadArchivo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgPublicidadArchivo2.getDrawable()!=null){
                    //imagePopup2.initiatePopup(imgPublicidadArchivo2.getDrawable());

                    android.app.AlertDialog.Builder imageDialog = new android.app.AlertDialog.Builder(RankingActivity.this);
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

                                      /*  try{

                                            CargaImagenesPublicidadArchivo2 cargaImagenesPublicidadArchivo2 = new CargaImagenesPublicidadArchivo2();
                                            cargaImagenesPublicidadArchivo2.execute(JsPublicidadArchivo);

                                        }catch (Exception ex){
                                            imgPublicidadArchivo2.setImageResource(R.drawable.icon_anuncie);
                                            Log.i("imgPublicidadArchivo2",ex.toString());
                                        }*/


                                        final ImagePopup imagePopupPublicidadArchivo2 = new ImagePopup(RankingActivity.this);
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
                                                /** Initiate Popup view **/
                                                imagePopupPublicidadArchivo2.viewPopup();
                                                Log.e("EsperandoTaxi","Mostrando Foto");

                                            }
                                        });



                                    }


                                    break;

                                case "U004":
                                    //FncMostrarMensaje("No se encontro ningun aviso el dia de hoy",false);
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



        final ArrayList<RankingResults> rankingResults = new ArrayList<RankingResults>();

        final ProgressDialog PrgRanking = new ProgressDialog(RankingActivity.this);
        PrgRanking.setIcon(R.mipmap.ic_launcher);
        PrgRanking.setMessage("Cargando...");
        PrgRanking.setCancelable(true);
        PrgRanking.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        PrgRanking.show();


        //OBTENIENDO HISTORIAL
        Thread nt = new Thread() {
            @Override
            public void run() {

                try {

                    final String resMtdObtenerRankingConductor;
                    resMtdObtenerRankingConductor = MtdObtenerRankingConductor(ConductorId,"","");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            JSONArray JsDatos;

                            try {
                                JSONObject jsonObject = new JSONObject(resMtdObtenerRankingConductor);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsDatos = jsonObject.getJSONArray("Datos");

                                // TextView TxtHistorialTotal = (TextView) findViewById(R.id.CmpHistorialTotal);
                                // TxtHistorialTotal.setText("Tienes ("+JsDatos.length()+") pedidos en tu historial ");

                                for (int i = 0; i < JsDatos.length(); i++) {

                                    JSONObject jsonObj = JsDatos.getJSONObject(i);

                                    String JsConductorId = jsonObj.getString("ConductorId");
                                    String JsConductorNombre = jsonObj.getString("ConductorNombre");
                                    String JsVehiculoUnidad = jsonObj.getString("VehiculoUnidad");
                                    String JsRankingTotal = jsonObj.getString("RankingTotal");

                                    RankingResults sr1 = new RankingResults();

                                    sr1.setConductorId(JsConductorId);
                                    sr1.setConductorNombre(JsConductorNombre);
                                    sr1.setVehiculoUnidad(JsVehiculoUnidad);
                                    sr1.setRankingTotal(JsRankingTotal);

                                    rankingResults.add(sr1);

                                }

                                final ListView lstRanking = (ListView) findViewById(R.id.LstRanking);
                                lstRanking.setAdapter(new RankingAdapter(RankingActivity.this, rankingResults));


                            } catch (JSONException e) {
                                e.printStackTrace();
//                              FncMostrarToast(e.toString());
                            }

                            PrgRanking.cancel();

                            switch(JsRespuesta){
                                case "C034":
                                    break;

                                case "C035":
                                    FncMostrarToast("No se encontraron registros");
                                    break;

                                case "C036":
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
        Intent intentMonitoreo = new Intent(RankingActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        RankingActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
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

                Intent intentMonitoreo = new Intent(RankingActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                RankingActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();

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

        Toast.makeText(RankingActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }


    /*
   * ENVIO DE VARIABLES
     */


    public String MtdObtenerRankingConductor(String oConductorId,String oFiltro,String oFecha) {

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
            postDataParams.put("Filtro", oFiltro);
            postDataParams.put("Fecha", oFecha);
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerRanking");

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
