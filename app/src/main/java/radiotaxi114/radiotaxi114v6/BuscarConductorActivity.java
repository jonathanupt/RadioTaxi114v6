package radiotaxi114.radiotaxi114v6;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class BuscarConductorActivity extends AppCompatActivity {

     /*
    * REGION
     */

    private String RegionId = "";
    private String RegionNombre = "";
    private String RegionURL = "";

    /* PREFERENCIAS
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
    private String ConductorSupervisorNivel = "";

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


    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("BuscarConductor", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("BuscarConductor", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("BuscarConductor", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("BuscarConductor", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("BuscarConductor", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("BuscarConductor", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("BuscarConductor", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("BuscarConductor", "RestoreInstance");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_conductor_activity);
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


        Log.e("BuscarConductor", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_buscar_conductor));

        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.mipmap.ic_launcher);
        //actionBar.setTitle(getString(R.string.app_title));
        //actionBar.setSubtitle("Buscar conductor");

        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        //RECUPERANDO VARIABLES
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        ConductorSupervisorNivel = intentExtras.getStringExtra("ConductorSupervisorNivel");

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");



    }



    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentSupervisor = new Intent(BuscarConductorActivity.this, SupervisorActivity.class);
        startActivity(intentSupervisor);
        BuscarConductorActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buscar_conductor, menu);
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

                Intent intentMonitoreo = new Intent(BuscarConductorActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                BuscarConductorActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
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


    private void FncBuscarConductores() {

        final ArrayList<ConductoresResults> conductoresResults = new ArrayList<ConductoresResults>();

        final String preFiltro = ((EditText)findViewById(R.id.CmpFiltro)).getText().toString();
        CharSequence chaFiltro = Html.fromHtml(preFiltro);
        final String valFiltro = chaFiltro.toString();


        if(valFiltro.equals("")){

            FncMostrarMensaje("No ha ingresado una palabra para buscar",false);

        }else{

            final ProgressDialog prgBuscarConductor = new ProgressDialog(BuscarConductorActivity.this);
            prgBuscarConductor.setIcon(R.mipmap.ic_launcher);
            prgBuscarConductor.setMessage("Cargando...");
            prgBuscarConductor.setCancelable(false);
            prgBuscarConductor.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgBuscarConductor.show();

            //OBTENIENDO HISTORIAL
            Thread nt = new Thread() {
                @Override
                public void run() {

                    try {

                        final String resBuscarConductores;
                        resBuscarConductores = MtdBuscarConductores(valFiltro);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String JsRespuesta = "";
                                JSONArray JsDatos;

                                try {
                                    JSONObject jsonObject = new JSONObject(resBuscarConductores);
                                    JsRespuesta = jsonObject.getString("Respuesta");
                                    JsDatos = jsonObject.getJSONArray("Datos");

                                    TextView TxtConductoresTotal = (TextView) findViewById(R.id.CmpConductoresTotal);
                                    TxtConductoresTotal.setText("Se encontraron ("+JsDatos.length()+") resultados");

                                    for (int i = 0; i < JsDatos.length(); i++) {

                                        JSONObject jsonObj = JsDatos.getJSONObject(i);

                                        String JsConductorId = jsonObj.getString("ConductorId");

                                        String JsConductorNombre = jsonObj.getString("ConductorNombre");
                                        String JsConductorApellido = jsonObj.getString("ConductorApellido");

                                        String JsConductorNumeroDocumento = jsonObj.getString("ConductorNumeroDocumento");
                                        String JsConductorTelefono = jsonObj.getString("ConductorTelefono");
                                        String JsConductorCelular = jsonObj.getString("ConductorCelular");
                                        String JsConductorDireccion = jsonObj.getString("ConductorDireccion");
                                        String JsConductorFoto = jsonObj.getString("ConductorFoto");
                                        String JsConductorFotoThumb1 = jsonObj.getString("ConductorFotoThumb1");
                                        String JsConductorFotoThumb2 = jsonObj.getString("ConductorFotoThumb2");

                                        String JsConductorVehiculoFoto = jsonObj.getString("ConductorVehiculoFoto");
                                        String JsConductorVehiculoFotoThumb1 = jsonObj.getString("ConductorVehiculoFotoThumb1");
                                        String JsConductorVehiculoFotoThumb2 = jsonObj.getString("ConductorVehiculoFotoThumb2");

                                        String JsConductorEstado = jsonObj.getString("ConductorEstado");
                                        String JsConductorEstadoDescripcion = jsonObj.getString("ConductorEstadoDescripcion");
                                        String JsConductorEstadoMotivo = jsonObj.getString("ConductorEstadoMotivo");
                                        String JsConductorEstadoResponsable = jsonObj.getString("ConductorEstadoResponsable");

                                        String JsConductorAppVersion = jsonObj.getString("ConductorAppVersion");

                                        String JsVehiculoUnidad = jsonObj.getString("VehiculoUnidad");
                                        String JsVehiculoPlaca = jsonObj.getString("VehiculoPlaca");
                                        String JsVehiculoColor = jsonObj.getString("VehiculoColor");
                                        String JsVehiculoModelo = jsonObj.getString("VehiculoModelo");
                                        String JsVehiculoCarroceria = jsonObj.getString("VehiculoCarroceria");
                                        String JsVehiculoMarca = jsonObj.getString("VehiculoMarca");

                                        String JsVehiculoCoordenadaX = jsonObj.getString("VehiculoCoordenadaX");
                                        String JsVehiculoCoordenadaY = jsonObj.getString("VehiculoCoordenadaY");
                                        Integer JsVehiculoGPSOrientacionActual = jsonObj.getInt("VehiculoGPSOrientacion");

                                        String JsPropietarioNombre = jsonObj.getString("PropietarioNombre");
                                        String JsPropietarioCelular = jsonObj.getString("PropietarioCelular");


                                        ConductoresResults sr1 = new ConductoresResults();

                                        sr1.setConductorId(JsConductorId);
                                        sr1.setConductorNombre(JsConductorNombre);
                                        sr1.setConductorApellido(JsConductorApellido);
                                        sr1.setConductorNumeroDocumento(JsConductorNumeroDocumento);
                                        sr1.setConductorTelefono(JsConductorTelefono);
                                        sr1.setConductorCelular(JsConductorCelular);
                                        sr1.setConductorDireccion(JsConductorDireccion);

                                        sr1.setConductorFoto(JsConductorFoto);
                                        sr1.setConductorFotoThumb1(JsConductorFotoThumb1);
                                        sr1.setConductorFotoThumb2(JsConductorFotoThumb2);

                                        sr1.setConductorVehiculoFoto(JsConductorVehiculoFoto);
                                        sr1.setConductorVehiculoFotoThumb1(JsConductorVehiculoFotoThumb1);
                                        sr1.setConductorVehiculoFotoThumb2(JsConductorVehiculoFotoThumb2);


                                        sr1.setConductorEstado(JsConductorEstado);
                                        sr1.setConductorEstadoDescripcion(JsConductorEstadoDescripcion);
                                        sr1.setConductorEstadoMotivo(JsConductorEstadoMotivo);
                                        sr1.setConductorEstadoResponsable(JsConductorEstadoResponsable);

                                        sr1.setConductorAppVersion(JsConductorAppVersion);





                                        sr1.setVehiculoCoordenadaX(JsVehiculoCoordenadaX);
                                        sr1.setVehiculoCoordenadaY(JsVehiculoCoordenadaY);
                                        sr1.setVehiculoGPSOrientacionActual(JsVehiculoGPSOrientacionActual);

                                        sr1.setVehiculoUnidad(JsVehiculoUnidad);
                                        sr1.setVehiculoPlaca(JsVehiculoPlaca);
                                        sr1.setVehiculoColor(JsVehiculoColor);
                                        sr1.setVehiculoModelo(JsVehiculoModelo);
                                        sr1.setVehiculoCarroceria(JsVehiculoCarroceria);
                                        sr1.setVehiculoMarca(JsVehiculoMarca);

                                        sr1.setPropietarioNombre(JsPropietarioNombre);
                                        sr1.setPropietarioCelular(JsPropietarioCelular);


                                        conductoresResults.add(sr1);

                                    }

                                    final ListView lstConductores = (ListView) findViewById(R.id.LstConductores);
                                    lstConductores.setAdapter(new ConductoresAdapter(BuscarConductorActivity.this, conductoresResults));
                                    lstConductores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                            Object o = lstConductores.getItemAtPosition(position);
                                            ConductoresResults fullObject = (ConductoresResults) o;

                                            Intent intent = new Intent(BuscarConductorActivity.this, ConductorActualActivity.class);
                                            Bundle bundle = new Bundle();

                                            bundle.putString("ConductorIdActual", fullObject.getConductorId());

                                            bundle.putString("ConductorNombreActual", fullObject.getConductorNombre());
                                            bundle.putString("ConductorApellidoActual", fullObject.getConductorApellido());
                                            bundle.putString("ConductorCelularActual", fullObject.getConductorCelular());
                                            bundle.putString("ConductorTelefonoActual", fullObject.getConductorTelefono());
                                            bundle.putString("ConductorDireccionActual", fullObject.getConductorDireccion());

                                            bundle.putString("ConductorFotoActual", fullObject.getConductorFoto());
                                            bundle.putString("ConductorFotoThumb1Actual", fullObject.getConductorFotoThumb1());
                                            bundle.putString("ConductorFotoThumb2Actual", fullObject.getConductorFotoThumb2());

                                            bundle.putString("ConductorVehiculoFotoActual", fullObject.getConductorVehiculoFoto());
                                            bundle.putString("ConductorVehiculoFotoThumb1Actual", fullObject.getConductorVehiculoFotoThumb1());
                                            bundle.putString("ConductorVehiculoFotoThumb2Actual", fullObject.getConductorVehiculoFotoThumb2());

                                            bundle.putString("ConductorEstadoActual", fullObject.getConductorEstado());
                                            bundle.putString("ConductorEstadoDescripcionActual", fullObject.getConductorEstadoDescripcion());
                                            bundle.putString("ConductorEstadoMotivoActual", fullObject.getConductorEstadoMotivo());
                                            bundle.putString("ConductorEstadoResponsableActual", fullObject.getConductorEstadoResponsable());
                                            bundle.putString("ConductorAppVersionActual", fullObject.getConductorAppVersion());

                                            bundle.putString("VehiculoPlacaActual", fullObject.getVehiculoPlaca());
                                            bundle.putString("VehiculoUnidadActual", fullObject.getVehiculoUnidad());
                                            bundle.putString("VehiculoColorActual", fullObject.getVehiculoColor());
                                            bundle.putString("VehiculoModeloActual", fullObject.getVehiculoModelo());
                                            bundle.putString("VehiculoCarroceriaActual", fullObject.getVehiculoCarroceria());
                                            bundle.putString("VehiculoMarcaActual", fullObject.getVehiculoMarca());

                                            bundle.putString("VehiculoCoordenadaXActual",fullObject.getVehiculoCoordenadaX());
                                            bundle.putString("VehiculoCoordenadaYActual",fullObject.getVehiculoCoordenadaY());
                                            bundle.putInt("VehiculoGPSOrientacionActual",fullObject.getVehiculoGPSOrientacionActual());

                                            bundle.putString("PropietarioNombre",fullObject.getPropietarioNombre());
                                            bundle.putString("PropietarioCelular",fullObject.getPropietarioCelular());


                                            bundle.putString("ConductorSupervisorNivel",ConductorSupervisorNivel);

                                            intent.putExtras(bundle);//Put your id to your next Intent
                                            startActivity(intent);
                                            finish();//AGREGADO 19-11-15


                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                prgBuscarConductor.cancel();

                                switch(JsRespuesta){

                                    case "S004":
                                        break;

                                    case "S005":
                                        // FncMostrarMensaje(getString(R.string.message_no_proceso),false);
                                        break;


                                    default:
                                        FncMostrarToast(getString(R.string.message_error_interno));
                                        //FncMostrarMensaje(getString(R.string.message_error_interno),false);
                                        break;

                                }

                            }
                        });
                    } catch (final Exception e) {
                        // TODO: handle exception
                        // Log.e("MsgHistorial6", e.toString());
                    }

                }

            };
            nt.start();
        }




    }
    public void onClick_BtnBuscarConductorBuscar(View v){

        FncBuscarConductores();

    }



    public void onClick_BtnBuscarConductorRegresar(View v){

        Intent intent = new Intent(BuscarConductorActivity.this, MonitoreoActivity.class);
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

        Toast.makeText(BuscarConductorActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }




       /*
   * ENVIO DE VARIABLES
     */


    public String MtdBuscarConductores(String oFiltro) {

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
            postDataParams.put("Filtro", oFiltro);
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "BuscarConductores");

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

            Log.e("MsgHistorial1", response);

        } catch (Exception e) {

            Log.e("MsgHistorial2", e.toString());
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
