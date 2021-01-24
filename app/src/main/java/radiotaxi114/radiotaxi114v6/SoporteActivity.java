package radiotaxi114.radiotaxi114v6;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class SoporteActivity extends AppCompatActivity {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soporte);
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

        Log.e("Ranking20", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_soporte));

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");

        //RECUPERANDO VARIABLES v2
        displayUserSettings();


        final ArrayList<SoporteContactoResults> soporteContactoResults = new ArrayList<SoporteContactoResults>();

        final ProgressDialog PrgSoporte = new ProgressDialog(SoporteActivity.this);
        PrgSoporte.setIcon(R.mipmap.ic_launcher);
        PrgSoporte.setMessage("Cargando...");
        PrgSoporte.setCancelable(false);
        PrgSoporte.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        PrgSoporte.show();


        //OBTENIENDO SOPORTE
        Thread nt = new Thread() {
            @Override
            public void run() {

                try {

                    final String resMtdObtenerSoporteContactos;
                    resMtdObtenerSoporteContactos = MtdObtenerSoporteContactos();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            JSONArray JsDatos;

                            try {
                                JSONObject jsonObject = new JSONObject(resMtdObtenerSoporteContactos);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsDatos = jsonObject.getJSONArray("Datos");
                                // TextView TxtHistorialTotal = (TextView) findViewById(R.id.CmpHistorialTotal);
                                // TxtHistorialTotal.setText("Tienes ("+JsDatos.length()+") pedidos en tu historial ");
                                for (int i = 0; i < JsDatos.length(); i++) {

                                    JSONObject jsonObj = JsDatos.getJSONObject(i);

                                    String JsSoporteContactoNombre = jsonObj.getString("SoporteContactoNombre");
                                    String JsSoporteContactoCelular = jsonObj.getString("SoporteContactoCelular");
                                    String JsSoporteContactoTelefono = jsonObj.getString("SoporteContactoTelefono");

                                    SoporteContactoResults sr1 = new SoporteContactoResults();

                                    sr1.setSoporteContactoNombre(JsSoporteContactoNombre);
                                    sr1.setSoporteContactoCelular(JsSoporteContactoCelular);
                                    sr1.setSoporteContactoTelefono(JsSoporteContactoTelefono);

                                    soporteContactoResults.add(sr1);
                                }

                                final ListView lstSoporteContacto = (ListView) findViewById(R.id.LstSoporteContacto);
                                lstSoporteContacto.setAdapter(new SoporteContactoAdapter(SoporteActivity.this, soporteContactoResults));

                                lstSoporteContacto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                        Object o = lstSoporteContacto.getItemAtPosition(position);
                                        final SoporteContactoResults fullObject = (SoporteContactoResults) o;

                                        final String SoporteContactoCelular = fullObject.getSoporteContactoCelular();

                                        android.support.v7.app.AlertDialog.Builder MsgSoporteContacto = new android.support.v7.app.AlertDialog.Builder(SoporteActivity.this);
                                        MsgSoporteContacto.setTitle("¿Deseas llamar a este número?");
                                        MsgSoporteContacto.setPositiveButton("Llamar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                        if(!SoporteContactoCelular.equals("")) {
                                                            try {
                                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                                callIntent.setData(Uri.parse("tel:" + SoporteContactoCelular));
                                                                startActivity(callIntent);
                                                            } catch (Exception e) {
                                                                FncMostrarToast("Ha ocurrido un error interno");
                                                                //Log.e("helloandroid dialing example", "Call failed", e);
                                                            }
                                                        }else{
                                                            FncMostrarMensaje("No se encontro numero a llamar",false);
                                                        }
                                                    }
                                                });

                                        MsgSoporteContacto.setNegativeButton("Cancelar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                    }
                                                });
                                        // Remember, create doesn't show the dialog
                                        android.support.v7.app.AlertDialog msgSoporteContacto = MsgSoporteContacto.create();
                                        msgSoporteContacto.show();

                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            PrgSoporte.cancel();

                            switch(JsRespuesta){
                                case "T001":
                                    break;

                                case "T002":
                                    break;
                                default:
                                    break;

                            }

                        }
                    });
                } catch (final Exception e) {
                    // TODO: handle exception
                    Log.e("MsgSoporte4", e.toString());
                }

            }

        };
        nt.start();
        
    }


    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(SoporteActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        SoporteActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
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

                Intent intentMonitoreo = new Intent(SoporteActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                SoporteActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
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

        Toast.makeText(SoporteActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }


    /*
   * ENVIO DE VARIABLES
     */


    public String MtdObtenerSoporteContactos() {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnsoportecontacto));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerSoporteContactos");

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

            Log.e("MsgSoporte1", response);

        } catch (Exception e) {

            Log.e("MsgSoporte2", e.toString());
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
