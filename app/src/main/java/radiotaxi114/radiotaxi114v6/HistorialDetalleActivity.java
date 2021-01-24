package radiotaxi114.radiotaxi114v6;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HistorialDetalleActivity extends AppCompatActivity {

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
IDENTIFICADOR
 */
    private String Identificador = "";

    /*
* Variables Principales
*/
    private String ConductorId = "";
    private String ConductorNombre = "";
    private String ConductorApellido = "";
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
    private String VehiculoModelo = "";

    private String VehiculoCoordenadaX = "";
    private String VehiculoCoordenadaY = "";

    private Integer ConductorOcupado = 2;
    private Integer ConductorCobertura = 0;


    private String PedidoId = "";
    private String PedidoFecha= "";
    private String PedidoHora= "";
    private String PedidoDireccion= "";
    private String PedidoNumero= "";
    private String PedidoDetalle= "";
    private String PedidoReferencia= "";

    private String PedidoCambioDe= "";
    private String PedidoTipoUnidad= "";

    private String ClienteNombre= "";

    /*
    * FORMULARIOS
     */

    TextView txtPedidoFecha;
    TextView txtPedidoHora;
    TextView txtPedidoDireccion;
    TextView txtPedidoNumero;
    TextView txtPedidoDetalle;
    TextView txtPedidoReferencia;
    TextView txtPedidoCambioDe;
    TextView txtPedidoTipoUnidad;

    TextView txtClienteNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_detalle);
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


        Log.e("HistorialDetalle", "onCreate");

        //CREANDO BARRA
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_activity_historial_detalle));

        //PREFERENCIAS
        sharedPreferences2 = getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);

        RegionId = sharedPreferences2.getString("RegionId", "");
        RegionNombre = sharedPreferences2.getString("RegionNombre", "");
        RegionURL = sharedPreferences2.getString("RegionURL", "");


        //RECPERANDO VARIABLES
        displayUserSettings();

        //RECUPERANDO VARIABLES
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        PedidoId = intentExtras.getStringExtra("PedidoId");
        PedidoFecha = intentExtras.getStringExtra("PedidoFecha");
        PedidoHora = intentExtras.getStringExtra("PedidoHora");
        PedidoDireccion = intentExtras.getStringExtra("PedidoDireccion");
        PedidoNumero = intentExtras.getStringExtra("PedidoNumero");
        PedidoDetalle = intentExtras.getStringExtra("PedidoDetalle");
        PedidoReferencia = intentExtras.getStringExtra("PedidoReferencia");

        PedidoCambioDe = intentExtras.getStringExtra("PedidoCambioDe");
        PedidoTipoUnidad = intentExtras.getStringExtra("PedidoTipoUnidad");

        ClienteNombre = intentExtras.getStringExtra("ClienteNombre");

        //FORMULARIOS

        txtPedidoFecha = (TextView) findViewById(R.id.CmpHistorialDetalleFecha);
        txtPedidoHora = (TextView) findViewById(R.id.CmpHistorialDetalleHora);
        txtPedidoDireccion = (TextView) findViewById(R.id.CmpHistorialDetalleDireccion);
        txtPedidoNumero = (TextView) findViewById(R.id.CmpHistorialDetalleNumero);
        txtPedidoDetalle = (TextView) findViewById(R.id.CmpHistorialDetalleDetalle);
        txtPedidoReferencia = (TextView) findViewById(R.id.CmpHistorialDetalleReferencia);
        txtPedidoCambioDe = (TextView) findViewById(R.id.CmpHistorialDetalleCambioDe);
        txtPedidoTipoUnidad = (TextView) findViewById(R.id.CmpHistorialDetalleTipoUnidad);

        txtClienteNombre = (TextView) findViewById(R.id.CmpHistorialDetalleCliente);

        //MOSTRANDO VARIABLES

        txtPedidoFecha.setText(PedidoFecha);
        txtPedidoHora.setText(PedidoHora);
        txtPedidoDireccion.setText(PedidoDireccion);
        txtPedidoNumero.setText(PedidoNumero);
        txtPedidoDetalle.setText(PedidoDetalle);
        txtPedidoReferencia.setText(PedidoReferencia);

        txtPedidoCambioDe.setText(PedidoCambioDe);
        txtPedidoTipoUnidad.setText(PedidoTipoUnidad);

        txtClienteNombre.setText(ClienteNombre);



    }

    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");

        Intent intentHistorialActivity = new Intent(HistorialDetalleActivity.this, HistorialActivity.class);
        Bundle bundleHistorialActivity = new Bundle();

        intentHistorialActivity.putExtras(bundleHistorialActivity);//Put your id to your next Intent
        startActivity(intentHistorialActivity);
        HistorialDetalleActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();

        return;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historial_detalle, menu);
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

                Intent intentHistorialActivity = new Intent(HistorialDetalleActivity.this, HistorialActivity.class);
                Bundle bundleHistorialActivity = new Bundle();

                intentHistorialActivity.putExtras(bundleHistorialActivity);//Put your id to your next Intent
                startActivity(intentHistorialActivity);
                HistorialDetalleActivity.this.overridePendingTransition(R.anim.anim_slide_in_right,
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

        Toast.makeText(HistorialDetalleActivity.this, oMensaje,
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
        ConductorCelular = sharedPreferences.getString("ConductorCelular","");

        ConductorOcupado = sharedPreferences.getInt("ConductorOcupado",2);
        ConductorCobertura = sharedPreferences.getInt("ConductorCobertura",0);

    }



}
