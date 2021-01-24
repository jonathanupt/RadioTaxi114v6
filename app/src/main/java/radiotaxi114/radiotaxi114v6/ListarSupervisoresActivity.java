package radiotaxi114.radiotaxi114v6;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

public class ListarSupervisoresActivity extends AppCompatActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_supervisores_activity);
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


        Log.e("ListarSupervisores20", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_listar_supervisores));

        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //RECUPERANDO VARIABLES
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        // ConductorSupervisorNivel = intentExtras.getStringExtra("ConductorSupervisorNivel");

        FncObtenerSupervisores();

    }

    public void onClick_BtnListarSupervisorBuscar(View v){

        FncObtenerSupervisores();

    }




    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(ListarSupervisoresActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        ListarSupervisoresActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listar_supervisores, menu);
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

                Intent intentMonitoreo = new Intent(ListarSupervisoresActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                ListarSupervisoresActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
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


    private void FncObtenerSupervisores() {

        final ArrayList<SupervisoresResults> supervisoresResults = new ArrayList<SupervisoresResults>();

        final String preFiltro = ((EditText)findViewById(R.id.CmpFiltro)).getText().toString();
        CharSequence chaFiltro = Html.fromHtml(preFiltro);
        final String valFiltro = chaFiltro.toString();


        //  if(valFiltro.equals("")){

        //      FncMostrarMensaje("No ha ingresado una palabra para buscar",false);

        //  }else{

        final ProgressDialog prgBuscarConductor = new ProgressDialog(ListarSupervisoresActivity.this);
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

                    final String resObtenerSupervisores;
                    resObtenerSupervisores = MtdObtenerSupervisores(valFiltro);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            JSONArray JsDatos;

                            try {
                                JSONObject jsonObject = new JSONObject(resObtenerSupervisores);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsDatos = jsonObject.getJSONArray("Datos");

                                TextView TxtConductoresTotal = (TextView) findViewById(R.id.CmpConductoresTotal);
                                TxtConductoresTotal.setText("Se encontraron ("+JsDatos.length()+") supervisores");

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
                                    String JsConductorEstado = jsonObj.getString("ConductorEstado");
                                    String JsConductorEstadoDescripcion = jsonObj.getString("ConductorEstadoDescripcion");
                                    String JsConductorInspectorTurno = jsonObj.getString("ConductorInspectorTurno");
                                    String JsConductorInspectorVigente = jsonObj.getString("ConductorInspectorVigente");

                                    String JsVehiculoUnidad = jsonObj.getString("VehiculoUnidad");
                                    String JsVehiculoPlaca = jsonObj.getString("VehiculoPlaca");
                                    String JsVehiculoColor = jsonObj.getString("VehiculoColor");
                                    String JsVehiculoModelo = jsonObj.getString("VehiculoModelo");

                                    String JsVehiculoCoordenadaX = jsonObj.getString("ConductorCoordenadaX");
                                    String JsVehiculoCoordenadaY = jsonObj.getString("ConductorCoordenadaY");


                                    SupervisoresResults sr1 = new SupervisoresResults();

                                    sr1.setConductorId(JsConductorId);
                                    sr1.setConductorNombre(JsConductorNombre);
                                    sr1.setConductorApellido(JsConductorApellido);
                                    sr1.setConductorNumeroDocumento(JsConductorNumeroDocumento);
                                    sr1.setConductorTelefono(JsConductorTelefono);
                                    sr1.setConductorCelular(JsConductorCelular);
                                    sr1.setConductorDireccion(JsConductorDireccion);
                                    sr1.setConductorFoto(JsConductorFoto);
                                    sr1.setConductorEstado(JsConductorEstado);
                                    sr1.setConductorEstadoDescripcion(JsConductorEstadoDescripcion);
                                    sr1.setConductorInspectorTurno(JsConductorInspectorTurno);
                                    sr1.setConductorInspectorVigente(JsConductorInspectorVigente);

                                    sr1.setVehiculoCoordenadaX(JsVehiculoCoordenadaX);
                                    sr1.setVehiculoCoordenadaY(JsVehiculoCoordenadaY);

                                    sr1.setVehiculoUnidad(JsVehiculoUnidad);
                                    sr1.setVehiculoPlaca(JsVehiculoPlaca);
                                    sr1.setVehiculoColor(JsVehiculoColor);
                                    sr1.setVehiculoModelo(JsVehiculoModelo);

                                    supervisoresResults.add(sr1);

                                }

                                final ListView lstSupervisores = (ListView) findViewById(R.id.LstSupervisores);
                                lstSupervisores.setAdapter(new SupervisoresAdapter(ListarSupervisoresActivity.this, supervisoresResults));
                                lstSupervisores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                        Object o = lstSupervisores.getItemAtPosition(position);
                                        final SupervisoresResults fullObject = (SupervisoresResults) o;

                                        final String ConductorCelular = fullObject.getConductorCelular();

                                        AlertDialog.Builder MsgListarSupervisores = new AlertDialog.Builder(ListarSupervisoresActivity.this);
                                        MsgListarSupervisores.setTitle("¿Deseas llamar a este supervisor?");
                                        //MsgListarSupervisores.setMessage(getString(R.string.app_version));
                                        MsgListarSupervisores.setPositiveButton("Llamar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        if(!ConductorCelular.equals("")) {

                                                            try {
                                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                                callIntent.setData(Uri.parse("tel:" + ConductorCelular));
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

                                        MsgListarSupervisores.setNegativeButton("Cancelar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
                                                    }
                                                });

                                        // Remember, create doesn't show the dialog
                                        AlertDialog msgListarSupervisores = MsgListarSupervisores.create();
                                        msgListarSupervisores.show();




                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            prgBuscarConductor.cancel();

                            switch(JsRespuesta){

                                case "S028":
                                    break;

                                case "S029":
                                    FncMostrarToast("No se encontraron supervisores");
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
        //    }




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

        Toast.makeText(ListarSupervisoresActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }




       /*
   * ENVIO DE VARIABLES
     */


    public String MtdObtenerSupervisores(String oFiltro) {

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
            postDataParams.put("Accion", "ObtenerSupervisores");

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
