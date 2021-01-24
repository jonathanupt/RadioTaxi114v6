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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Map;

public class HistorialActivity extends AppCompatActivity {


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


    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Historial20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Historial20", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Historial20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Historial20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Historial20", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Historial20", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Historial20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("Historial20", "RestoreInstance");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_activity);
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

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.SpiFechaIntervalo);

        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> LstFechaIntervalo = new ArrayList<String>();
        LstFechaIntervalo.add("Hoy");
        LstFechaIntervalo.add("Semana Actual");
        LstFechaIntervalo.add("Semana Pasada");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LstFechaIntervalo);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                final String item = parentView.getItemAtPosition(position).toString();

                // Showing selected spinner item
                //Toast.makeText(parentView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

                //FncMostrarToast("Cargando historial...");

                Log.e("MsgHistorial8", item);

                final ArrayList<HistorialResults> historialResults = new ArrayList<HistorialResults>();

                final ProgressDialog prgHistorial = new ProgressDialog(HistorialActivity.this);
                prgHistorial.setIcon(R.mipmap.ic_launcher);
                prgHistorial.setMessage("Cargando...");
                prgHistorial.setCancelable(true);
                prgHistorial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                prgHistorial.show();


                //OBTENIENDO HISTORIAL
                Thread nt = new Thread() {
                    @Override
                    public void run() {

                        try {

                            Log.e("MsgHistorial9","cargando...");

                            final String resObtenerHistorial;
                            resObtenerHistorial = MtdObtenerHistorial(ConductorId, item);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Log.e("MsgHistorial9","cargando2...");

                                    String JsRespuesta = "";
                                    JSONArray JsDatos;

                                    try {

                                        Log.e("MsgHistorial9","cargando3...");

                                        JSONObject jsonObject = new JSONObject(resObtenerHistorial);
                                        JsRespuesta = jsonObject.getString("Respuesta");
                                        JsDatos = jsonObject.getJSONArray("Datos");

                                        TextView TxtFavoritosTotal = (TextView) findViewById(R.id.CmpRecepcionArchivosTotal);
                                        TxtFavoritosTotal.setText("Tienes ("+JsDatos.length()+") pedidos en tu historial");

                                        for (int i = 0; i < JsDatos.length(); i++) {

                                            JSONObject jsonObj = JsDatos.getJSONObject(i);

                                            String PedidoDireccion = jsonObj.getString("PedidoDireccion");
                                            String PedidoNumero = jsonObj.getString("PedidoNumero");
                                            String PedidoReferencia = jsonObj.getString("PedidoReferencia");
                                            String PedidoDetalle = jsonObj.getString("PedidoDetalle");
                                            String PedidoCambioDe = jsonObj.getString("PedidoCambioDe");
                                            String PedidoTipoUnidad = jsonObj.getString("PedidoTipoUnidad");

                                            String PedidoFecha = jsonObj.getString("PedidoFecha");
                                            String PedidoHora = jsonObj.getString("PedidoHora");

                                            String PedidoCalificacionTexto = jsonObj.getString("PedidoCalificacionTexto");

                                            String VehiculoUnidad = jsonObj.getString("VehiculoUnidad");
                                            String VehiculoPlaca = jsonObj.getString("VehiculoPlaca");
                                            String VehiculoColor = jsonObj.getString("VehiculoColor");

                                            String VehiculoConductor = jsonObj.getString("VehiculoConductor");

                                            String ClienteNombre = jsonObj.getString("ClienteNombre");


                                            
                                            HistorialResults sr1 = new HistorialResults();
                                            sr1.setPedidoDireccion(PedidoDireccion);
                                            sr1.setPedidoNumero(PedidoNumero);
                                            sr1.setPedidoReferencia(PedidoReferencia);
                                            sr1.setPedidoDetalle(PedidoDetalle);
                                            sr1.setPedidoCalificacion(PedidoCalificacionTexto);

                                            sr1.setPedidoFecha(PedidoFecha);
                                            sr1.setPedidoHora(PedidoHora);

                                            sr1.setVehiculoUnidad(VehiculoUnidad);
                                            sr1.setVehiculoPlaca(VehiculoPlaca);
                                            sr1.setVehiculoColor(VehiculoColor);

                                            sr1.setVehiculoConductor(VehiculoConductor);

                                            sr1.setClienteNombre(ClienteNombre);

                                            historialResults.add(sr1);

                                        }

                                        final ListView lstHistorial = (ListView) findViewById(R.id.LstHistorial);
                                        lstHistorial.setAdapter(new HistorialAdapter(HistorialActivity.this, historialResults));


                                        lstHistorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                            Object o = lstHistorial.getItemAtPosition(position);
                                            HistorialResults fullObject = (HistorialResults)o;

                                            Intent intentHistorialDetalleActivity = new Intent(HistorialActivity.this, HistorialDetalleActivity.class);
                                            Bundle bundleHistorialDetalleActivity  = new Bundle();

                                            bundleHistorialDetalleActivity.putString("PedidoId", fullObject.getPedidoId());
                                            bundleHistorialDetalleActivity.putString("PedidoFecha", fullObject.getPedidoFecha());
                                            bundleHistorialDetalleActivity.putString("PedidoHora", fullObject.getPedidoHora());
                                            bundleHistorialDetalleActivity.putString("ClienteNombre", fullObject.getClienteNombre());

                                            bundleHistorialDetalleActivity.putString("PedidoDireccion", fullObject.getPedidoDireccion());
                                            bundleHistorialDetalleActivity.putString("PedidoNumero", fullObject.getPedidoNumero());
                                            bundleHistorialDetalleActivity.putString("PedidoReferencia", fullObject.getPedidoReferencia());
                                            bundleHistorialDetalleActivity.putString("PedidoDetalle", fullObject.getPedidoDetalle());


                                            intentHistorialDetalleActivity.putExtras(bundleHistorialDetalleActivity);//Put your id to your next Intent
                                            startActivity(intentHistorialDetalleActivity);
                                            finish();//AGREGADO 19-11-15


                                        }
                                        });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e("MsgHistorial7", e.toString());
                                    }

                                    prgHistorial.cancel();

                                    switch(JsRespuesta){

                                        case "C020":
                                            break;

                                        case "C021":
                                            FncMostrarToast("No se encontraron pedidos");
                                            // FncMostrarMensaje(getString(R.string.message_no_proceso),false);
                                            break;

                                        case "C022":
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
                            Log.e("MsgHistorial6", e.toString());
                        }

                    }

                };
                nt.start();





            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

















        final ArrayList<HistorialResults> historialResults = new ArrayList<HistorialResults>();

        final ProgressDialog prgHistorial = new ProgressDialog(HistorialActivity.this);
        prgHistorial.setIcon(R.mipmap.ic_launcher);
        prgHistorial.setMessage("Cargando...");
        prgHistorial.setCancelable(false);
        prgHistorial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prgHistorial.show();

        //OBTENIENDO HISTORIAL
        Thread nt = new Thread() {
            @Override
            public void run() {

                try {

                    final String resObtenerHistorial;
                    resObtenerHistorial = MtdObtenerHistorial(ConductorId, "");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String JsRespuesta = "";
                            JSONArray JsDatos;

                            try {
                                JSONObject jsonObject = new JSONObject(resObtenerHistorial);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsDatos = jsonObject.getJSONArray("Datos");

                                TextView TxtFavoritosTotal = (TextView) findViewById(R.id.CmpRecepcionArchivosTotal);
                                TxtFavoritosTotal.setText("Tienes ("+JsDatos.length()+") pedidos en tu historial");

                                for (int i = 0; i < JsDatos.length(); i++) {

                                    JSONObject jsonObj = JsDatos.getJSONObject(i);

                                    String PedidoDireccion = jsonObj.getString("PedidoDireccion");
                                    String PedidoNumero = jsonObj.getString("PedidoNumero");
                                    String PedidoReferencia = jsonObj.getString("PedidoReferencia");
                                    String PedidoDetalle = jsonObj.getString("PedidoDetalle");
                                    String PedidoFecha = jsonObj.getString("PedidoFecha");
                                    String PedidoHora = jsonObj.getString("PedidoHora");

                                    String VehiculoUnidad = jsonObj.getString("VehiculoUnidad");
                                    String VehiculoPlaca = jsonObj.getString("VehiculoPlaca");
                                    String VehiculoColor = jsonObj.getString("VehiculoColor");

                                    String VehiculoConductor = jsonObj.getString("VehiculoConductor");

                                    String ClienteNombre = jsonObj.getString("ClienteNombre");


                                    HistorialResults sr1 = new HistorialResults();
                                    sr1.setPedidoDireccion(PedidoDireccion);
                                    sr1.setPedidoNumero(PedidoNumero);
                                    sr1.setPedidoReferencia(PedidoReferencia);
                                    sr1.setPedidoDetalle(PedidoDetalle);

                                    sr1.setPedidoFecha(PedidoFecha);
                                    sr1.setPedidoHora(PedidoHora);

                                    sr1.setVehiculoUnidad(VehiculoUnidad);
                                    sr1.setVehiculoPlaca(VehiculoPlaca);
                                    sr1.setVehiculoColor(VehiculoColor);

                                    sr1.setVehiculoConductor(VehiculoConductor);

                                    sr1.setClienteNombre(ClienteNombre);

                                    historialResults.add(sr1);

                                }

                                final ListView lstHistorial = (ListView) findViewById(R.id.LstHistorial);
                                lstHistorial.setAdapter(new HistorialAdapter(HistorialActivity.this, historialResults));



                                // lstHistorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                // @Override
                                //   public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                //   Object o = lstHistorial.getItemAtPosition(position);
                                // HistorialResults fullObject = (HistorialResults)o;

                                //}
                                //});

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            prgHistorial.cancel();

                            switch(JsRespuesta){

                                case "C020":
                                    break;

                                case "C021":
                                    // FncMostrarMensaje(getString(R.string.message_no_proceso),false);
                                    break;

                                case "C022":
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




    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(HistorialActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        HistorialActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historial, menu);
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

                Intent intentMonitoreo = new Intent(HistorialActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                HistorialActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();


                return true;

         /*
            case R.id.action_compartir:

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_compartir));
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.app_compartir_sub));
                HistorialActivity.this.startActivity(Intent.createChooser(shareIntent, getString(R.string.alert_title)));

                return true;

            case R.id.action_acerca_de:

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

        Toast.makeText(HistorialActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }


    public void onClick_BtnHistorialRegresar(View v){

        //    finish();

        Intent intent = new Intent(HistorialActivity.this, MonitoreoActivity.class);
        startActivity(intent);
        finish();

    }




    /*
   * ENVIO DE VARIABLES
     */


    public String MtdObtenerHistorial(String oConductorId,String oFechaIntervalo) {

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
            postDataParams.put("FechaIntervalo", oFechaIntervalo);
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerHistorial");

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

       /*
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(
                getString(R.string.app_url)+ "/webservice/JnConductor.php");

        HttpResponse response = null;
        String respuesta = "";

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>(3);
            params.add(new BasicNameValuePair("ConductorId", oConductorId));
            params.add(new BasicNameValuePair("FechaIntervalo", oFechaIntervalo));
            params.add(new BasicNameValuePair("Accion", "ObtenerHistorial"));

            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost, localContext);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            respuesta = result.toString();
            Log.e("MsgHistorial1", respuesta.toString());
        } catch (Exception e) {

             Log.e("MsgHistorial2", e.toString());
            // TODO: handle exception
        }
        //return response.toString();
        return respuesta;
        */




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
