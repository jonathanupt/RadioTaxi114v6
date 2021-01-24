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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class PedidoCancelacionActivity extends AppCompatActivity {

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

    private String PedidoCancelacionMotivo = "";
    private Integer PedidoCancelacionTotal = 2;

    /*
    * Variables Pedido
    */
    private String PedidoId = "";

    private String ClienteNombre = "";
    private String PedidoDireccion = "";
    private String PedidoNumero = "";
    private String PedidoDetalle = "";
    private String PedidoTipo = "";
    private String PedidoFoto = "";
    private String PedidoTipoUnidad = "";

    private String PedidoReferencia = "";
    private String PedidoCoordenadaX = "0.00";
    private String PedidoCoordenadaY = "0.00";

    private String ClienteCelular = "";
    private String ClienteTelefono = "";
    private String ClienteFoto = "";

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
    /*
    * Variables Secundarias
    */
    private String VehiculoCoordenadaX = "0.00";
    private String VehiculoCoordenadaY = "0.00";
    /*
* Otras Variables
*/
    private String Identificador = "";


    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("PedidoCancelacion20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("PedidoCancelacion20", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("PedidoCancelacion20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("PedidoCancelacion20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("PedidoCancelacion20", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("PedidoCancelacion20", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("PedidoCancelacion20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("PedidoCancelacion20", "RestoreInstance");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_cancelacion_activity);
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

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_pedido_cancelacion));

        //   actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.mipmap.ic_launcher);
        // actionBar.setTitle(getString(R.string.app_title));
        //actionBar.setSubtitle("Cancelar Pedido");

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

        PedidoId = intentExtras.getStringExtra("PedidoId");

        ClienteNombre = intentExtras.getStringExtra("ClienteNombre");
        PedidoDireccion = intentExtras.getStringExtra("PedidoDireccion");
        PedidoNumero = intentExtras.getStringExtra("PedidoNumero");
        PedidoReferencia = intentExtras.getStringExtra("PedidoReferencia");
        PedidoDetalle = intentExtras.getStringExtra("PedidoDetalle");
        PedidoTipo = intentExtras.getStringExtra("PedidoTipo");

        PedidoFoto = intentExtras.getStringExtra("PedidoFoto");
        PedidoTipoUnidad = intentExtras.getStringExtra("PedidoTipoUnidad");

        PedidoCoordenadaX = intentExtras.getStringExtra("PedidoCoordenadaX");
        PedidoCoordenadaY = intentExtras.getStringExtra("PedidoCoordenadaY");

        VehiculoCoordenadaX = intentExtras.getStringExtra("VehiculoCoordenadaX");
        VehiculoCoordenadaY = intentExtras.getStringExtra("VehiculoCoordenadaY");

        ClienteCelular = intentExtras.getStringExtra("ClienteCelular");
        ClienteTelefono = intentExtras.getStringExtra("ClienteTelefono");



        PedidoCancelacionTotal = 0;

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupCancelar);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton)findViewById(checkedId);
                //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                PedidoCancelacionMotivo = rb.getText().toString();

                if(PedidoCancelacionMotivo.equals("El cliente no se encuentra")){
                    PedidoCancelacionTotal = 1;
                }

            }
        });

        
    }



    public void onBackPressed() {

        Intent intent = new Intent(PedidoCancelacionActivity.this, PedidoActualActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("PedidoId", PedidoId);

        bundle.putString("ClienteNombre", ClienteNombre);
        bundle.putString("PedidoDireccion", PedidoDireccion);
        bundle.putString("PedidoNumero", PedidoNumero);
        bundle.putString("PedidoReferencia", PedidoReferencia);
        bundle.putString("PedidoDetalle", PedidoDetalle);
        bundle.putString("PedidoTipo", PedidoTipo);



        bundle.putString("PedidoFoto", PedidoFoto);
        bundle.putString("PedidoTipoUnidad", PedidoTipoUnidad);
        bundle.putString("PedidoCoordenadaX", PedidoCoordenadaX);
        bundle.putString("PedidoCoordenadaY", PedidoCoordenadaY);
        bundle.putString("VehiculoCoordenadaX", VehiculoCoordenadaX);
        bundle.putString("VehiculoCoordenadaY", VehiculoCoordenadaY);

        bundle.putString("ClienteCelular", ClienteCelular);
        bundle.putString("ClienteTelefono", ClienteTelefono);

        intent.putExtras(bundle);//Put your id to your next Intent
        startActivity(intent);
        finish();

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedido_cancelacion, menu);
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


                Intent intent = new Intent(PedidoCancelacionActivity.this, PedidoActualActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("PedidoId", PedidoId);

                bundle.putString("ClienteNombre", ClienteNombre);
                bundle.putString("PedidoDireccion", PedidoDireccion);
                bundle.putString("PedidoNumero", PedidoNumero);
                bundle.putString("PedidoReferencia", PedidoReferencia);
                bundle.putString("PedidoDetalle", PedidoDetalle);
                bundle.putString("PedidoTipo", PedidoTipo);

                bundle.putString("PedidoFoto", PedidoFoto);
                bundle.putString("PedidoTipoUnidad", PedidoTipoUnidad);

                bundle.putString("PedidoCoordenadaX", PedidoCoordenadaX);
                bundle.putString("PedidoCoordenadaY", PedidoCoordenadaY);
                bundle.putString("VehiculoCoordenadaX", VehiculoCoordenadaX);
                bundle.putString("VehiculoCoordenadaY", VehiculoCoordenadaY);

                bundle.putString("ClienteCelular", ClienteCelular);
                bundle.putString("ClienteTelefono", ClienteTelefono);

                intent.putExtras(bundle);//Put your id to your next Intent
                startActivity(intent);
                finish();

                return true;

           /* case R.id.action_compartir:

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_compartir));
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.app_compartir_sub));
                PedidoCancelacionActivity.this.startActivity(Intent.createChooser(shareIntent, getString(R.string.alert_title)));

                return true;

            case R.id.action_acerca_de:

                FncMostrarAcercaDe();

                return true;*/

            /*case R.id.action_salir:

                FncMostrarToast("Cerrando aplicacion ...");

                finish();
                System.exit(0);
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

        Toast.makeText(PedidoCancelacionActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();
    }





    public void onClick_BtnPedidoCancelacionEnviar(View v){

        if (!PedidoCancelacionMotivo.equals("")){

            final ProgressDialog prgPedidoCancelacion = new ProgressDialog(PedidoCancelacionActivity.this);
            prgPedidoCancelacion.setIcon(R.mipmap.ic_launcher);
            prgPedidoCancelacion.setMessage("Cargando...");
            prgPedidoCancelacion.setCancelable(false);
            prgPedidoCancelacion.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgPedidoCancelacion.show();

            Thread nt = new Thread() {
                @Override
                public void run() {

                    try {

                        final String resPedidoCancelacion;

                        resPedidoCancelacion = postEnviarPedidoCancelarConductor(PedidoId,PedidoCancelacionMotivo,PedidoCancelacionTotal);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String JsRespuesta = "";

                                try {

                                    JSONObject jsonObject = new JSONObject(resPedidoCancelacion);
                                    JsRespuesta = jsonObject.getString("Respuesta");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                prgPedidoCancelacion.cancel();

                                switch(JsRespuesta){

                                    case "P026":

                                        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putBoolean("ConductorTienePedido", false);
                                        editor.apply();


                                        AlertDialog.Builder MsgGeneral = new AlertDialog.Builder(PedidoCancelacionActivity.this);
                                        MsgGeneral.setTitle(getString(R.string.alert_title));
                                        MsgGeneral.setMessage("El pedido fue cancelado correctamente");
                                        MsgGeneral.setCancelable(false);
                                        MsgGeneral.setPositiveButton("Aceptar",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog

                                                        Intent intent = new Intent(PedidoCancelacionActivity.this, MonitoreoActivity.class);
                                                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });

                                        // Remember, create doesn't show the dialog
                                        AlertDialog msgGeneral = MsgGeneral.create();
                                        msgGeneral.show();


                                        break;

                                    case "P027":
                                        FncMostrarMensaje("No se ha podido cancelar el pedido, ha ocurrido un error",false);
                                        break;

                                    /*case "P028":
                                        FncMostrarMensaje("No se ha podido cancelar el pedido, verifique el estado del pedido",false);
                                        break;*/

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

        }else{
            FncMostrarMensaje("Escoja un motivo de cancelacion de pedido",false);
        }




    }

    /**
     *  ENVIO DE VARIABLES
     * */


    public String postEnviarPedidoCancelarConductor(String oPedidoId,String oPedidoCancelacionMotivo, Integer oPedidoCancelacionTotal) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/"+getString(R.string.webservice_jnpedido));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("PedidoId", oPedidoId);

            postDataParams.put("PedidoCancelacionMotivoConductor", oPedidoCancelacionMotivo);
            postDataParams.put("PedidoCancelacionTotalConductor", oPedidoCancelacionTotal.toString());

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "CancelarPedidoConductor");

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

            Log.e("PedidoCancelacion1", response);

        } catch (Exception e) {

            Log.e("PedidoCancelacion2", e.toString());
            e.printStackTrace();
        }

        return response;


       /* HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(
                getString(R.string.app_url)+"/webservice/"+getString(R.string.webservice_jnpedido));

        HttpResponse response = null;
        String respuesta = "";

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>(4);
            params.add(new BasicNameValuePair("PedidoId", oPedidoId));
            params.add(new BasicNameValuePair("PedidoCancelacionMotivoConductor", oPedidoCancelacionMotivo));
            params.add(new BasicNameValuePair("PedidoCancelacionTotalConductor", oPedidoCancelacionTotal.toString()));
            params.add(new BasicNameValuePair("Accion","CancelarPedidoConductor"));

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
            // Log.e("PedidoActual5", respuesta.toString());
        } catch (Exception e) {

            //Log.e("PedidoActual6", e.toString());

            // TODO: handle exception
        }
        //return response.toString();
        return respuesta;*/
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
