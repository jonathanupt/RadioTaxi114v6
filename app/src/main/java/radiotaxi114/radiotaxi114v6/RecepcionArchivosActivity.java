package radiotaxi114.radiotaxi114v6;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecepcionArchivosActivity extends AppCompatActivity {

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
        Log.e("RecepcionArchivos20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("RecepcionArchivos20", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("RecepcionArchivos20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("RecepcionArchivos20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("RecepcionArchivos20", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("RecepcionArchivos20", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("RecepcionArchivos20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("RecepcionArchivos20", "RestoreInstance");
    }


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepcion_archivos_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /*loatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Log.e("RecepcionArchivos20", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_recepcion_archivos));

        // actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.mipmap.ic_launcher);
        // actionBar.setTitle(getString(R.string.app_title));
        //actionBar.setSubtitle("Archivos");

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //RECUPERANDO VARIABLES v2
        displayUserSettings();




        FncBuscarRecepcionArchivos();
        
    }



    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(RecepcionArchivosActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        RecepcionArchivosActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recepcion_archivos, menu);
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

                Intent intentMonitoreo = new Intent(RecepcionArchivosActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                RecepcionArchivosActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();
                return true;
       /*     case R.id.action_compartir:

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_compartir));
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.app_compartir_sub));
                RecepcionArchivosActivity.this.startActivity(Intent.createChooser(shareIntent, getString(R.string.alert_title)));

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
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick_BtnRecepcionArchivosBuscar(View v){

        FncBuscarRecepcionArchivos();

    }


    private void FncBuscarRecepcionArchivos() {

        final ArrayList<RecepcionArchivosResults> recepcionArchivosResults = new ArrayList<RecepcionArchivosResults>();

        final String preFiltro = ((EditText)findViewById(R.id.CmpFiltro)).getText().toString();
        CharSequence chaFiltro = Html.fromHtml(preFiltro);
        final String valFiltro = chaFiltro.toString();

        // if(valFiltro.equals("")){

        //    FncMostrarMensaje("No ha ingresado una palabra para buscar",false);

        // }else{

        final ProgressDialog prgRecepcionArchivos = new ProgressDialog(RecepcionArchivosActivity.this);
        prgRecepcionArchivos.setIcon(R.mipmap.ic_launcher);
        prgRecepcionArchivos.setMessage("Cargando...");
        prgRecepcionArchivos.setCancelable(false);
        prgRecepcionArchivos.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prgRecepcionArchivos.show();


        //OBTENIENDO ARCHIVOS
        Thread nt = new Thread() {
            @Override
            public void run() {

                try {

                    Log.e("MsgRecepcionArchivos9","cargando...");

                    final String resObtenerRecepcionArchivos;
                    resObtenerRecepcionArchivos = MtdObtenerRecepcionArchivos(valFiltro);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.e("MsgRecepcionArchivos9","cargando2...");

                            String JsRespuesta = "";
                            JSONArray JsDatos;

                            try {

                                Log.e("MsgRecepcionArchivos9","cargando3...");

                                JSONObject jsonObject = new JSONObject(resObtenerRecepcionArchivos);
                                JsRespuesta = jsonObject.getString("Respuesta");
                                JsDatos = jsonObject.getJSONArray("Datos");

                                TextView TxtRecepcionArchivosTotal = (TextView) findViewById(R.id.CmpRecepcionArchivosTotal);
                                TxtRecepcionArchivosTotal.setText("Se encontraron ("+JsDatos.length()+") archivos");

                                for (int i = 0; i < JsDatos.length(); i++) {

                                    JSONObject jsonObj = JsDatos.getJSONObject(i);

                                    String EnvioArchivoNombre = jsonObj.getString("EnvioArchivoNombre");
                                    String EnvioArchivoDescripcion = jsonObj.getString("EnvioArchivoDescripcion");
                                    String EnvioArchivoAdjunto = jsonObj.getString("EnvioArchivoAdjunto");
                                    String EnvioArchivoTipo = jsonObj.getString("EnvioArchivoTipo");
                                    String EnvioArchivoTiempoCreacion = jsonObj.getString("EnvioArchivoTiempoCreacion");
                                    String EnvioArchivoId = jsonObj.getString("EnvioArchivoId");


                                    RecepcionArchivosResults sr1 = new RecepcionArchivosResults();
                                    sr1.setEnvioArchivoNombre(EnvioArchivoNombre);
                                    sr1.setEnvioArchivoDescripcion(EnvioArchivoDescripcion);
                                    sr1.setEnvioArchivoAdjunto(EnvioArchivoAdjunto);
                                    sr1.setEnvioArchivoTipo(EnvioArchivoTipo);
                                    sr1.setEnvioArchivoTiempoCreacion(EnvioArchivoTiempoCreacion);
                                    sr1.setEnvioArchivoId(EnvioArchivoId);

                                    recepcionArchivosResults.add(sr1);

                                }

                                final ListView lstRecepcionArchivos = (ListView) findViewById(R.id.LstRecepcionArchivos);
                                lstRecepcionArchivos.setAdapter(new RecepcionArchivosAdapter(RecepcionArchivosActivity.this, recepcionArchivosResults));

                                lstRecepcionArchivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                        Object o = lstRecepcionArchivos.getItemAtPosition(position);
                                        RecepcionArchivosResults fullObject = (RecepcionArchivosResults)o;

                                        String url = fullObject.getEnvioArchivoAdjunto();
                                        url = (url.replace(" ", "%20"));


                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(Uri.parse( "http://docs.google.com/viewer?url=" + url), "text/html");
                                        startActivity(intent);

                                        //   Uri uri = Uri.parse(url);

                                       /* Uri path = Uri.parse(url);
                                        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                                        pdfIntent.setDataAndType(path, "application/pdf");
                                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                        Log.e("RecepcionArchivos30", "ok4");

                                        try{
                                            startActivity(pdfIntent);
                                        }catch(ActivityNotFoundException e){
                                               Toast.makeText(RecepcionArchivosActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                                        }
*/
                                        //new DownloadFile().execute(url, "archivo.pdf");


                                     /*   url = (url.replace(" ", "%20"));
                                        Uri uri = Uri.parse(url);
                                        startActivity(new Intent(Intent.ACTION_VIEW, uri));*/


                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("MsgRecepcionArchivos7", e.toString());
                            }

                            prgRecepcionArchivos.cancel();

                            switch(JsRespuesta){

                                case "N001":
                                    break;

                                case "N002":
                                    FncMostrarToast("No se encontraron archivos");
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
                    Log.e("MsgRecepcionArchivos6", e.toString());
                }

            }

        };
        nt.start();

        //  }




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

        Toast.makeText(RecepcionArchivosActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }


    public void onClick_BtnRecepcionArchivoRegresar(View v){

        //    finish();

        Intent intent = new Intent(RecepcionArchivosActivity.this, MonitoreoActivity.class);
        startActivity(intent);
        finish();

    }







    /*
   * ENVIO DE VARIABLES
     */


    public String MtdObtenerRecepcionArchivos(String oFiltro) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/JnEnvioArchivo.php");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("Filtro", oFiltro);

            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerEnvioArchivos");

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

            Log.e("MsgRecepcionArchivos1", response);

        } catch (Exception e) {

            Log.e("MsgRecepcionArchivos2", e.toString());
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





    private static final int  MEGABYTE = 1024 * 1024;


    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "testthreepdf");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            Log.e("RecepcionArchivos20", fileName);
            Log.e("RecepcionArchivos21", fileUrl);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }

            //      FileDownloader.downloadFile(fileUrl, pdfFile);

            //  public static void downloadFile(String fileUrl, File directory){
            try {

                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                int totalSize = urlConnection.getContentLength();

                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer))>0 ){
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();

                Log.e("RecepcionArchivos22", "okk");

                ver();




            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //downloadFile(fileUrl, pdfFile);

/*
            //public static void downloadFile(String fileUrl, File directory){


            File pdfFile2 = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "archivo.pdf");  // -> filename = maven.pdf
            Uri path = Uri.fromFile(pdfFile2);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try{
                startActivity(pdfIntent);
            }catch(ActivityNotFoundException e){
                Toast.makeText(RecepcionArchivosActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }*/


            // }

            return null;
        }
    }

    private void ver() {

        Log.e("RecepcionArchivos22", "okk3");

        File pdfFile2 = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "archivo.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile2);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            //   Toast.makeText(RecepcionArchivosActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }

    }


    
    
    
}
