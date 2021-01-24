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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

public class SupervisorActivity extends AppCompatActivity {

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

    /*
     VALIDAR
     */
    private Integer ConductorSupervidor = 0;


    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("SupervisorActivitty", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("SupervisorActivitty", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("SupervisorActivitty", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("SupervisorActivitty", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("SupervisorActivitty", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("SupervisorActivitty", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("SupervisorActivitty", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("SupervisorActivitty", "RestoreInstance");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /*- FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Log.e("SupervisorActivitty", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
      //  actionBar.setTitle(getString(R.string.app_title));
     //   actionBar.setSubtitle("Modulo Supervisor");
        actionBar.setTitle(getString(R.string.title_activity_supervisor));

        //RECUPERANDO VARIABLES
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        ConductorId = intentExtras.getStringExtra("ConductorId");

        ConductorSupervidor = 2;

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        final ProgressDialog prgSupervisor = new ProgressDialog(SupervisorActivity.this);
        prgSupervisor.setIcon(R.mipmap.ic_launcher);
        prgSupervisor.setMessage("Verificando...");
        prgSupervisor.setCancelable(false);
        prgSupervisor.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prgSupervisor.show();

        Thread nt = new Thread() {
            @Override
            public void run() {

                try {

                    final String resMtdVerificarSupervisorConductor;

                    resMtdVerificarSupervisorConductor = MtdVerificarSupervisorConductor(ConductorId,ConductorNumeroDocumento);

                    // runOnUiThread(new Runnable() {
                    //    @Override
                    //    public void run() {

                    String JsRespuesta = "";
                    String JsConductorSupervisorNivel = "";


                    try {

                        JSONObject jsonObject = new JSONObject(resMtdVerificarSupervisorConductor);
                        JsRespuesta = jsonObject.getString("Respuesta");
                        JsConductorSupervisorNivel = jsonObject.getString("ConductorSupervisorNivel");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    prgSupervisor.cancel();

                    switch(JsRespuesta){

                        case "S001":

                            ConductorSupervidor = 1;

                            ConductorSupervisorNivel = JsConductorSupervisorNivel;

                            // FncMostrarMensaje("Conductor habilitado correctamente",false);
                            FncMostrarMensaje("Bienvenido al modulo de supervisor.",false);

                                                    /*
                                                    SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("ConductorSupervisorNivel", ConductorSupervisorNivel);
                                                    editor.apply();
                                                    */

                            break;

                        case "S002":

                            ConductorSupervidor = 2;

                            AlertDialog.Builder MsgSupervisor2 = new AlertDialog.Builder(SupervisorActivity.this);
                            MsgSupervisor2.setTitle(getString(R.string.alert_title));
                            MsgSupervisor2.setMessage("Usted no esta asignado como supervisor.");
                            MsgSupervisor2.setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do nothing but close the dialog

                                            Intent intentBuscarConductor = new Intent(SupervisorActivity.this, MonitoreoActivity.class);
                                            startActivity(intentBuscarConductor);
                                            finish();

                                        }
                                    });

                            // Remember, create doesn't show the dialog
                            AlertDialog msgSupervisor2 = MsgSupervisor2.create();
                            msgSupervisor2.show();

                            break;

                        case "S003":

                            ConductorSupervidor = 2;

                            AlertDialog.Builder MsgSupervisor3 = new AlertDialog.Builder(SupervisorActivity.this);
                            MsgSupervisor3.setTitle(getString(R.string.alert_title));
                            MsgSupervisor3.setMessage("No se identifico codigo de conductor.");
                            MsgSupervisor3.setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do nothing but close the dialog

                                            Intent intentBuscarConductor = new Intent(SupervisorActivity.this, MonitoreoActivity.class);
                                            startActivity(intentBuscarConductor);
                                            finish();

                                        }
                                    });

                            // Remember, create doesn't show the dialog
                            AlertDialog msgSupervisor3 = MsgSupervisor3.create();
                            msgSupervisor3.show();

                            break;


                        default://NO ES CRITICO

                            ConductorSupervidor = 2;

                            FncMostrarToast(getString(R.string.message_error_interno));

                            Intent intentBuscarConductor = new Intent(SupervisorActivity.this, MonitoreoActivity.class);
                            startActivity(intentBuscarConductor);
                            finish();


                            //FncMostrarMensaje(getString(R.string.message_error_interno),false);
                            break;

                    }

                    //    }
                    // });

                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        };

        nt.start();



        
    }

    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(SupervisorActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        SupervisorActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_supervisor_activitty, menu);
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

                Intent intentMonitoreo = new Intent(SupervisorActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                SupervisorActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();

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
                        if (oCerrar) {
                            finish();
                        }
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog msgGeneral = MsgGeneral.create();
        msgGeneral.show();

    }
    private void FncMostrarToast(String oMensaje){

        Toast.makeText(SupervisorActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }


    public void onClick_BtnSupervisorOpcion1(View v){

        /*

        Intent intentBuscarConductor = new Intent(SupervisorActivity.this, BuscarConductorActivity.class);
        startActivity(intentBuscarConductor);
        finish();

*/
        if(ConductorSupervidor == 1){

          /*  Intent intentBuscarConductor = new Intent(SupervisorActivity.this, BuscarConductorActivity.class);
            Bundle bundleBuscarConductor = new Bundle();

            bundleBuscarConductor.putString("ConductorSupervisorNivel", ConductorSupervisorNivel);

            intentBuscarConductor.putExtras(bundleBuscarConductor);//Put your id to your next Intent
            startActivity(intentBuscarConductor);
            finish();
*/


            Intent intentBuscarConductor = new Intent(SupervisorActivity.this, BuscarConductorActivity.class);
            Bundle bundleBuscarConductor = new Bundle();

            bundleBuscarConductor.putString("ConductorSupervisorNivel", ConductorSupervisorNivel);

            intentBuscarConductor.putExtras(bundleBuscarConductor);//Put your id to your next Intent
            startActivity(intentBuscarConductor);

            SupervisorActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);

            finish();

        }else{

            AlertDialog.Builder MsgSupervisor2 = new AlertDialog.Builder(SupervisorActivity.this);
            MsgSupervisor2.setTitle(getString(R.string.alert_title));
            MsgSupervisor2.setMessage("Usted no esta asignado como supervisor.");
            MsgSupervisor2.setPositiveButton("Aceptar",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            Intent intentBuscarConductor = new Intent(SupervisorActivity.this, MonitoreoActivity.class);
                            startActivity(intentBuscarConductor);
                            finish();

                        }
                    });

            // Remember, create doesn't show the dialog
            AlertDialog msgSupervisor2 = MsgSupervisor2.create();
            msgSupervisor2.show();

        }



    }


    public void onClick_BtnSupervisorRegresar(View v){

      //  Intent intentBuscarConductor = new Intent(SupervisorActivity.this, MonitoreoActivity.class);
       // startActivity(intentBuscarConductor);
      //  finish();

        Intent intentMonitoreo = new Intent(SupervisorActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        SupervisorActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

    }




    /*
   * ENVIO DE VARIABLES
     */
    public String MtdVerificarSupervisorConductor(String oConductorId,String oConductorNumeroDocumento) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnconductorsupervisor) );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("ConductorNumeroDocumento", oConductorNumeroDocumento);
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "VerificarSupervisorConductor");

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

            Log.e("MsgSupervisor1", response);

        } catch (Exception e) {

            Log.e("MsgSupervisor2", e.toString());
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
