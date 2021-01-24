package radiotaxi114.radiotaxi114v6;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.util.Timer;
import java.util.TimerTask;

public class RadioActivity extends AppCompatActivity {

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




    ProgressDialog dialog;

    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer = null;

    private static String audioFilePath;

    private static Button recordButton;


    private boolean isRecording = false;
    private boolean isPlaying = false;

    private boolean RadioMensajeReproducir = false;
    ArrayList RadioMensajeRecibidos = new ArrayList();

    private String RadioAccion = "";
    private String ConductorCanal = "";

    private String ConductorVehiculoUnidadDestino="";
    private String ConductorIdDestino="";


    /*
    PERMISOS
     */
    private Context context;
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;


    /**
     * DATOS TIMER
     */

    Timer timerRadio1;

    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Radio20", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Radio20", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Radio20", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Radio20", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Radio20", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Radio20", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Radio20", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("Radio20", "RestoreInstance");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_activity);
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

        Log.e("Radio20", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_radio));

        //   actionBar.setDisplayShowHomeEnabled(true);
        //  actionBar.setIcon(R.mipmap.ic_launcher);
        // actionBar.setTitle(getString(R.string.app_title));
        //actionBar.setSubtitle("Radio Digital");

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        context = getApplicationContext();
        activity = this;

        RadioAccion = "1";
        ConductorCanal = "1";

        isRecording = false;
        isPlaying = false;


        TextView TxtCanalActual = (TextView) findViewById(R.id.CmpCanalActual);
        TxtCanalActual.setText("Canal "+ConductorCanal +"/"+ConductorVehiculoUnidadDestino);

        recordButton = (Button) findViewById(R.id.BtnRadioGrabar);
        recordButton.setBackgroundResource(R.drawable.dra_boton_fondo4);



        if(timerRadio1!=null){
            timerRadio1.cancel();
        }

        timerRadio1 = new Timer();
        timerRadio1.schedule(new TimerTask(){
            @Override
            public void run() {

                if(!isRecording){

                    if(!isPlaying){


                        Thread nt = new Thread() {
                            @Override
                            public void run() {

                                //if(!mediaPlayer.isPlaying()){
                                try {

                                    final String resObtenerRadioMensajes;
                                    resObtenerRadioMensajes = MtdObtenerRadioMensajes(ConductorId,ConductorCanal);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            String JsRespuesta = "";
                                            JSONArray JsDatos;


                                            try {

                                                JSONObject jsonObject = new JSONObject(resObtenerRadioMensajes);
                                                JsRespuesta = jsonObject.getString("Respuesta");
                                                JsDatos = jsonObject.getJSONArray("Datos");

                                                for (int i = 0; i < JsDatos.length(); i++) {

                                                    JSONObject jsonObj = JsDatos.getJSONObject(i);
                                                    String JsRadioMensajeId = jsonObj.getString("RadioMensajeId");
                                                    String JsRadioMensajeArchivo = jsonObj.getString("RadioMensajeArchivo");
                                                    String JsConductorVehiculoUnidadOrigen = jsonObj.getString("ConductorVehiculoUnidadOrigen");

                                                    switch(JsRespuesta){

                                                        case "R006":
                                                            // Log.e("Navegacion70","AAA")
                                                            if(RadioMensajeRecibidos.size()>0){
                                                                for (int f = 0; f < RadioMensajeRecibidos.size(); f++) {
                                                                    if(RadioMensajeRecibidos.get(f).equals(JsRadioMensajeId)){

                                                                        RadioMensajeReproducir = false;
                                                                        //break outerloop;
                                                                        break;
                                                                    }else{
                                                                        RadioMensajeReproducir = true;
                                                                    }
                                                                }
                                                            }else{
                                                                RadioMensajeReproducir = true;
                                                            }


                                                            if(RadioMensajeReproducir){


                                                                FncMostrarToast(JsConductorVehiculoUnidadOrigen);
                                                                Log.e("Navegacion70","Reproducir SI...");

                                                                RadioMensajeRecibidos.add(JsRadioMensajeId);

                                                                isPlaying = true;
                                                                //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                                try {

                                                                    // int maxVolume = 50;
                                                                    //float log1=(float)(Math.log(maxVolume-currVolume)/Math.log(maxVolume));
                                                                    // yourMediaPlayer.setVolume(1-log1);

                                                                    recordButton = (Button) findViewById(R.id.BtnRadioGrabar);
                                                                    recordButton.setBackgroundResource(R.drawable.dra_boton_fondo5);


                                                                    //AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                                                    //int maxVolume = amanager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
                                                                    //amanager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);

                                                                    mediaPlayer = new MediaPlayer();
                                                                    mediaPlayer.setDataSource(JsRadioMensajeArchivo);
                                                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                                                    // mediaPlayer.setVolume(30,00);
                                                                    mediaPlayer.prepare();

                                                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                                                        @Override
                                                                        public void onCompletion(MediaPlayer mp) {

                                                                            recordButton = (Button) findViewById(R.id.BtnRadioGrabar);
                                                                            recordButton.setBackgroundResource(R.drawable.dra_boton_fondo4);
                                                                            isPlaying = false;

                                                                        }

                                                                    });

                                                                    mediaPlayer.setVolume(9.0f, 9.0f);
                                                                    mediaPlayer.start();

                                                                    //MonitoreoSonar = true;
                                                                    PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                                                                    PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
                                                                    wakeLock.acquire();

                                                                    // Here, thisActivity is the current activity
                                                                    KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                                                                    KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
                                                                    keyguardLock.disableKeyguard();

                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                    isPlaying = false;

                                                                    recordButton = (Button) findViewById(R.id.BtnRadioGrabar);
                                                                    recordButton.setBackgroundResource(R.drawable.dra_boton_fondo4);
                                                                }

                                                                //String in = new java.net.URL(JsRadioMensajeArchivo).toString();
                                                                //Log.e("Navegacion70",JsRadioMensajeArchivo);

                                                           /*
                                                            mediaPlayer.prepare();
                                                            //mediaPlayer.start();

                                                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                                @Override
                                                                public void onPrepared(MediaPlayer mp) {
                                                                    if (mp == mediaPlayer) {
                                                                        mediaPlayer.start();
                                                                    }
                                                                }
                                                            });*/

                                                            }else{
                                                                Log.e("Navegacion70","Reproducir NO...");

                                                            }

                                                            break;

                                                        case "R007":
                                                            break;

                                                        default:
                                                            break;

                                                    }

                                                }

                                            } catch (JSONException e) {
                                                Log.e("Navegacion66", e.toString());
                                                e.printStackTrace();
                                            }

                                        }
                                    });



                                } catch (Exception e) {
                                    // TODO: handle exception
                                    Log.e("Navegacion67", e.toString());
                                }
                                //}else{
                                //     Log.e("Navegacion69","Reproduccion ya en curso");

                                //  }



                            }

                        };
                        nt.start();


                    }


                }



            }

        }, 1000, 1500);



    }




    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(RadioActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        RadioActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_radio, menu);
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

                Intent intentMonitoreo = new Intent(RadioActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                RadioActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private boolean checkPermission(int permiso) {
        Log.e("Navegacion10","VERIFICAR PERMISO");
        boolean respuesta = false;

        int MyVersion = Build.VERSION.SDK_INT;

        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {

            switch(permiso){
                case 1:

                    int result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

                    if (result1 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","1AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, permiso);
                        Log.e("Navegacion10", "1BBB");
                    }

                    break;

                case 2:

                    int result2 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

                    if (result2 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","2AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, permiso);
                        Log.e("Navegacion10", "2BBB");
                    }

                    break;

                case 3:

                    int result3 = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);

                    if (result3 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","3AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, permiso);
                        Log.e("Navegacion10", "3BBB");
                    }

                    break;

                case 4:

                    int result4 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (result4 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","4AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, permiso);
                        Log.e("Navegacion10", "4BBB");
                    }

                    break;

                case 5:

                    int result5 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

                    if (result5 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","5AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, permiso);
                        Log.e("Navegacion10", "5BBB");
                    }

                    break;

                case 6:

                    int result6 = ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE);

                    if (result6 == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Navegacion10","5AAA");
                        respuesta = true;
                    }else {
                        respuesta = false;
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.VIBRATE}, permiso);
                        Log.e("Navegacion10", "5BBB");
                    }

                    break;
            }

        }else{
            respuesta = true;
        }





        return respuesta;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Log.e("Navegacion10","PERMISO ACEPTADO");

            switch (requestCode) {
                case 1:
                    break;

                case 2:
                    break;

                case 3:

                    if(checkPermission(4)){
                        MtdIniciarGrabarAudio();
                    }

                    break;

                case 4:

                    MtdIniciarGrabarAudio();

                    break;

                case 5:

                    MtdDetenerGrabarAudio();

                    break;

            }

        } else {
            Log.e("Navegacion10","PERMISO NEGADO");
            FncMostrarToast("Permiso denegado, es posible que la aplicacion no funcione  correctamente.");
        }

    }




    public void onClick_BtnRadioRegresar(View v){

        //    finish();

        Intent intent = new Intent(RadioActivity.this, MonitoreoActivity.class);
        startActivity(intent);
        finish();

    }

    public void onClick_BtnRadioGrabar(View v){


        Log.e("Navegacion500", "Radio Accion");

        if(RadioAccion.equals("1")){

            RadioAccion = "2";
            isRecording = true;

            Log.e("Navegacion500", "Iniciar Radio");

            recordButton = (Button) findViewById(R.id.BtnRadioGrabar);
            recordButton.setBackgroundResource(R.drawable.dra_boton_fondo3);

            MediaPlayer player = MediaPlayer.create(RadioActivity.this, R.raw.sou_radio11a);
            player = MediaPlayer.create(RadioActivity.this, R.raw.sou_radio11a);
            player.start();

            Vibrator vv = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            vv.vibrate(250);

            if(checkPermission(3)){
                MtdIniciarGrabarAudio();
            }

        }else if(RadioAccion.equals("2")){

            RadioAccion = "1";
            isRecording = false;

            Log.e("Navegacion500", "Detener Radio");

            recordButton = (Button) findViewById(R.id.BtnRadioGrabar);
            recordButton.setBackgroundResource(R.drawable.dra_boton_fondo4);

            MediaPlayer player = MediaPlayer.create(RadioActivity.this, R.raw.sou_radio11b);
            player = MediaPlayer.create(RadioActivity.this, R.raw.sou_radio11b);
            player.start();

            if(checkPermission(5)){
                MtdDetenerGrabarAudio();
            }

        }else{
            Log.e("Navegacion500", "ERROR Radio");
        }


       /* Log.e("Navegacion500", "Radio Accion");

        if(RadioAccion.equals("1")){

            RadioAccion = "2";
            isRecording = true;

            Log.e("Navegacion500", "Iniciar Radio");

            recordButton = (Button) findViewById(R.id.BtnRadioGrabar);
            recordButton.setBackgroundResource(R.drawable.dra_boton_fondo3);

            MediaPlayer player = MediaPlayer.create(RadioActivity.this, R.raw.sou_radio11a);
            player = MediaPlayer.create(RadioActivity.this, R.raw.sou_radio11a);
            player.start();

            Vibrator vv = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            vv.vibrate(250);

            if(checkPermission(3)){
                MtdIniciarGrabarAudio();
            }

        }else if(RadioAccion.equals("2")){

            RadioAccion = "1";
            isRecording = false;

            Log.e("Navegacion500", "Detener Radio");

            recordButton = (Button) findViewById(R.id.BtnRadioGrabar);
            recordButton.setBackgroundResource(R.drawable.dra_boton_fondo2);

            MediaPlayer player = MediaPlayer.create(RadioActivity.this, R.raw.sou_radio11b);
            player = MediaPlayer.create(RadioActivity.this, R.raw.sou_radio11b);
            player.start();

            if(checkPermission(5)){
                MtdDetenerGrabarAudio();
            }

        }else{
            Log.e("Navegacion500", "ERROR Radio");
        }*/

    }




    public void MtdIniciarGrabarAudio(){

        //    if(!isPlaying){



        try {

            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myaudio"+ts+"_"+ConductorId+"_"+ConductorCanal+"_"+ConductorIdDestino+".3gp";
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //mediaRecorder.setAudioEncoder(MediaRecorder.getAudioSourceMax());
            mediaRecorder.setAudioEncodingBitRate(256);//32
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setOutputFile(audioFilePath);

            //isRecording = true;

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  }




    }


    public void MtdDetenerGrabarAudio(){

        try {

            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;

            //isRecording = false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        // dialog = ProgressDialog.show(NavegacionActivity.this,"","Cargando...",true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //creating new thread to handle Http Operations
                uploadFile(audioFilePath);
            }
        }).start();

    }




   /* public void MtdIniciarGrabarAudio(){

        if(!isPlaying){


            try {

                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myaudio"+ts+"_"+ConductorId+"_"+ConductorCanal+"_"+ConductorIdDestino+".3gp";
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                //mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                //mediaRecorder.setAudioEncoder(MediaRecorder.getAudioSourceMax());
                mediaRecorder.setAudioEncodingBitRate(32);
                mediaRecorder.setAudioSamplingRate(44100);
                mediaRecorder.setOutputFile(audioFilePath);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }




    }


    public void MtdDetenerGrabarAudio(){

        try {

            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;

            isRecording = false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        // dialog = ProgressDialog.show(NavegacionActivity.this,"","Cargando...",true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //creating new thread to handle Http Operations
                uploadFile(audioFilePath);
            }
        }).start();

    }*/


    public void onClick_BtnRadioEstablecerCanal(View v){

        //ANIMACION
        // final Animation animRotate = AnimationUtils.loadAnimation(this,
        //         R.anim.anim_rotate);
        //  v.startAnimation(animRotate);


        //ACCIONES
        final CharSequence[] items = { "Canal 1", "Canal 2","Canal 3","Canal 4" };

        int intConductorCanal = Integer.parseInt(ConductorCanal);
        intConductorCanal = intConductorCanal -1;

        // Creating and Building the Dialog
        android.support.v7.app.AlertDialog.Builder MsgNavegacion = new android.support.v7.app.AlertDialog.Builder(this);
        MsgNavegacion.setTitle("Escoja un canal");
        //MsgNavegacion.setCancelable(false);
        MsgNavegacion.setSingleChoiceItems(items,intConductorCanal,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        TextView TxtCanalActual = (TextView) findViewById(R.id.CmpCanalActual);

                        switch (item) {
                            case 0:

                                ConductorCanal = "1";
                                TxtCanalActual.setText("Canal "+ConductorCanal+"/"+ConductorVehiculoUnidadDestino);

                                SharedPreferences sharedPreferences0 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor0 = sharedPreferences0.edit();
                                //editor0.putString("ConductorCanal", "Canal 1");
                                editor0.putString("ConductorCanal", "1");
                                editor0.apply();

                                dialog.dismiss();

                                break;
                            case 1:

                                ConductorCanal = "2";
                                TxtCanalActual.setText("Canal "+ConductorCanal+"/"+ConductorVehiculoUnidadDestino);

                                SharedPreferences sharedPreferences1 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                                //editor1.putString("ConductorCanal", "Canal 2");
                                editor1.putString("ConductorCanal", "2");
                                editor1.apply();

                                dialog.dismiss();

                                break;


                            case 2:


                                ConductorCanal = "3";
                                TxtCanalActual.setText("Canal "+ConductorCanal+"/"+ConductorVehiculoUnidadDestino);

                                SharedPreferences sharedPreferences3 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                                //editor3.putString("ConductorCanal", "Canal 3");
                                editor3.putString("ConductorCanal", "3");
                                editor3.apply();

                                dialog.dismiss();

                                break;

                            case 3:


                                ConductorCanal = "4";
                                TxtCanalActual.setText("Canal "+ConductorCanal+"/"+ConductorVehiculoUnidadDestino);

                                SharedPreferences sharedPreferences4 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor4 = sharedPreferences4.edit();
                                //editor4.putString("ConductorCanal", "Canal 4");
                                editor4.putString("ConductorCanal", "4");
                                editor4.apply();

                                dialog.dismiss();

                                break;

                        }

                    }
                });
        //levelDialog = MsgNavegacion.create();
        //levelDialog.show();

        android.support.v7.app.AlertDialog msgNavegacion = MsgNavegacion.create();
        msgNavegacion.show();
    }

    public void onClick_BtnConductorCanalInterno(View v){

        FncMostrarMensaje("En desarrollo",false);

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

        Toast.makeText(RadioActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

    }







    public String MtdObtenerRadioMensajes(String oConductorId,String oRadioMensajeCanal) {

        URL url;
        String response = "";

        try {

            url = new URL(getString(R.string.app_url)+RegionURL+"/webservice/JnRadioMensaje.php");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
           conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("ConductorId", oConductorId);
            postDataParams.put("RadioMensajeCanal", oRadioMensajeCanal);
            postDataParams.put("Identificador", Identificador);
            postDataParams.put("Accion", "ObtenerRadioMensajeUltimo");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response+=line;
            }

            Log.e("Navegacion11", response);

        } catch (Exception e) {

            Log.e("Navegacion12", e.toString());
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



/*
* FUNCIONES AUXILIARES
 */

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


    //android upload file to server
    public int uploadFile(final String selectedFilePath){

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];


        //String response = "";

        try{

            //  String otherParametersUrServiceNeed =  "ConductorCanal="+ConductorCanal+"&Accion=RegistrarRadioMensaje";


            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            URL url = new URL( getString(R.string.app_url)+"/radio/AccRadioSubirArchivo.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);//Allow Inputs
            connection.setDoOutput(true);//Allow Outputs
            connection.setUseCaches(false);//Don't use a cached Copy
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("ENCTYPE", "multipart/form-data");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.setRequestProperty("uploaded_file",selectedFilePath);








            //creating new dataoutputstream
            dataOutputStream = new DataOutputStream(connection.getOutputStream());

            //writing bytes to data outputstream
            //dataOutputStream.writeBytes(otherParametersUrServiceNeed);
            dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                    + selectedFilePath + "\"" + lineEnd);

            dataOutputStream.writeBytes(lineEnd);

            //returns no. of bytes present in fileInputStream
            bytesAvailable = fileInputStream.available();
            //selecting the buffer size as minimum of available bytes or 1 MB
            bufferSize = Math.min(bytesAvailable,maxBufferSize);
            //setting the buffer as byte array of size of bufferSize
            buffer = new byte[bufferSize];

            //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
            bytesRead = fileInputStream.read(buffer,0,bufferSize);

            //loop repeats till bytesRead = -1, i.e., no bytes are left to read
            while (bytesRead > 0){
                //write the bytes read from inputstream
                dataOutputStream.write(buffer,0,bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                bytesRead = fileInputStream.read(buffer,0,bufferSize);
            }

            dataOutputStream.writeBytes(lineEnd);
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

               /* HashMap<String, String>postDataParams=new HashMap<>();
                postDataParams.put("ConductorCanal", ConductorCanal);
                postDataParams.put("ConductorId", ConductorId);

                postDataParams.put("VehiculoCoordenadaX", VehiculoCoordenadaX);
                postDataParams.put("VehiculoCoordenadaY", VehiculoCoordenadaY);
                postDataParams.put("Identificador", Identificador);
                postDataParams.put("AppVersion", getString(R.string.app_version));
                postDataParams.put("Accion", "RegistrarRadioMensaje");
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));*/
/*

                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
                Log.i("UploadFile2", ":3 "+response);*/


            serverResponseCode = connection.getResponseCode();
            String serverResponseMessage = connection.getResponseMessage();

            Log.i("UploadFile", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

            //response code of 200 indicates the server status OK
            if(serverResponseCode == 200){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // Log.e("UPLOAD", serverResponseMessage.toString());

                        // tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/"+ fileName);
                        // FncMostrarToast("Listo");
                    }
                });
            }

            //closing the input and output streams
            fileInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RadioActivity.this,"Archivo no encontrado.",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(RadioActivity.this, "Error de URL.", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(RadioActivity.this, "No se pudo leer/escribir archivo.", Toast.LENGTH_SHORT).show();
        }

        // dialog.dismiss();
        return serverResponseCode;

    }





}
