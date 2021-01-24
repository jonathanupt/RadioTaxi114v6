package radiotaxi114.radiotaxi114v6;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MisPreferenciasActivity extends AppCompatActivity {

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


    private Switch SwiMonitoreoEncendido;
    private Switch SwiMonitoreoSonido;
    private Switch SwiMonitoreoFondo;

    //ONSTART -> ON CREATE -> ONRESTART
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MisPreferencias", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MisPreferencias", "Resume");
    }
    //ONPAUSE -> ONRESUME -> ONSTOP
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MisPreferencias", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MisPreferencias", "Stop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("MisPreferencias", "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MisPreferencias", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("MisPreferencias", "SaveInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("MisPreferencias", "RestoreInstance");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_preferencias_activity);
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


        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_mis_preferencias));

        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setIcon(R.mipmap.ic_launcher);
        //actionBar.setTitle(getString(R.string.app_title));
        //actionBar.setSubtitle("Mis Preferencias");
        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //RECUPERANDO VARIABLES v2
        displayUserSettings();

        SwiMonitoreoEncendido = (Switch) findViewById(R.id.SwiMonitoreoEncendido);

// set the switch to ON
        //SwiMonitoreoEncendido.setChecked(true);
// attach a listener to check for changes in state
        SwiMonitoreoEncendido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (isChecked) {
                    Log.e("MisPreferencias","verda");
                    editor.putBoolean("MonitoreoEncendido", true);


                } else {
                    Log.e("MisPreferencias","falso");
                    editor.putBoolean("MonitoreoEncendido", false);
                    // Toast.makeText(getApplicationContext(),
                    //         “The switch is OFF”,Toast.LENGTH_SHORT).show();
                }

                editor.apply();

            }
        });


        SwiMonitoreoSonido = (Switch) findViewById(R.id.SwiMonitoreoSonido);

        SwiMonitoreoSonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (isChecked) {
                    Log.e("MisPreferencias","2verda");
                    editor.putBoolean("MonitoreoSonido", true);


                } else {
                    Log.e("MisPreferencias","2falso");
                    editor.putBoolean("MonitoreoSonido", false);
                    // Toast.makeText(getApplicationContext(),
                    //         “The switch is OFF”,Toast.LENGTH_SHORT).show();
                }

                editor.apply();

            }
        });

        SwiMonitoreoFondo = (Switch) findViewById(R.id.SwiMonitoreoFondo);

        SwiMonitoreoFondo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (isChecked) {
                    Log.e("MisPreferencias","2verda");
                    editor.putString("MonitoreoFondo", "1");


                } else {
                    Log.e("MisPreferencias","2falso");
                    editor.putString("MonitoreoFondo", "2");
                    // Toast.makeText(getApplicationContext(),
                    //         “The switch is OFF”,Toast.LENGTH_SHORT).show();
                }

                editor.apply();

            }
        });


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton)findViewById(checkedId);
                //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String TonoOpcion = rb.getText().toString();

                switch (TonoOpcion){
                    case "Ninguno":

                        editor.putString("MonitoreoTono", "0");
                        editor.putBoolean("MonitoreoSonido", false);
                        break;

                    case "Tono 1":

                        MediaPlayer player1 = MediaPlayer.create(MisPreferenciasActivity.this, R.raw.sou_pedido1);
                        player1 = MediaPlayer.create(MisPreferenciasActivity.this, R.raw.sou_pedido1);
                        player1.start();

                        editor.putString("MonitoreoTono", "1");
                        editor.putBoolean("MonitoreoSonido", true);
                        break;

                    case "Tono 2":

                        MediaPlayer player2 = MediaPlayer.create(MisPreferenciasActivity.this, R.raw.sou_pedido2);
                        player2 = MediaPlayer.create(MisPreferenciasActivity.this, R.raw.sou_pedido2);
                        player2.start();

                        editor.putString("MonitoreoTono", "2");
                        editor.putBoolean("MonitoreoSonido", true);
                        break;

                }

                editor.apply();

            }
        });



        SharedPreferences sharedPreferences = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        boolean MonitoreoEncendido = sharedPreferences.getBoolean("MonitoreoEncendido", true);
        boolean MonitoreoSonido = sharedPreferences.getBoolean("MonitoreoSonido",true);
        String MonitoreoFondo = sharedPreferences.getString("MonitoreoFondo","2");
        String MonitoreoTono = sharedPreferences.getString("MonitoreoTono","1");

        if(MonitoreoEncendido){
            SwiMonitoreoEncendido.setChecked(true);
        }else{
            SwiMonitoreoEncendido.setChecked(false);
        }

        if(MonitoreoSonido){
            SwiMonitoreoSonido.setChecked(true);
        }else{
            SwiMonitoreoSonido.setChecked(false);
        }

        if(MonitoreoFondo.equals("1")){
            SwiMonitoreoFondo.setChecked(true);
        }else{
            SwiMonitoreoFondo.setChecked(false);
        }

        RadioButton radioButton0 = (RadioButton)findViewById(R.id.RadioButton0);
        RadioButton radioButton1 = (RadioButton)findViewById(R.id.RadioButton1);
        RadioButton radioButton2 = (RadioButton)findViewById(R.id.RadioButton2);

        switch(MonitoreoTono){
            case "0":
                radioButton0.setChecked(true);
                break;

            case "1":
                radioButton1.setChecked(true);
                break;

            case "2":
                radioButton2.setChecked(true);
                break;

        }

/*
          TelephonyManager mTelephonyManager;
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.getLine1Number();

        String Celularlocal = mTelephonyManager.getLine1Number();

        FncMostrarMensaje(Celularlocal,false);*/

        // FncMostrarToast(mTelephonyManager.getLine1Number());


    }


    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent intentMonitoreo = new Intent(MisPreferenciasActivity.this, MonitoreoActivity.class);
        startActivity(intentMonitoreo);
        MisPreferenciasActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mis_preferencias, menu);
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

                Intent intentMonitoreo = new Intent(MisPreferenciasActivity.this, MonitoreoActivity.class);
                startActivity(intentMonitoreo);
                MisPreferenciasActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                finish();


/*
            case R.id.action_acerca_de:

                FncMostrarAcercaDe();

                return true;
*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public void onClick_BtnMisPreferenciasRegresar(View v){

        //    finish();

        Intent intent = new Intent(MisPreferenciasActivity.this, MonitoreoActivity.class);
        startActivity(intent);
        finish();

    }

    public void onClick_BtnMisPreferenciasConfigurarGPS(View v){

        Intent intent_ajustes_gps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        MisPreferenciasActivity.this.startActivity(intent_ajustes_gps);

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

        Toast.makeText(MisPreferenciasActivity.this, oMensaje,
                Toast.LENGTH_LONG).show();

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
    

}
