package radiotaxi114.radiotaxi114v6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jonathan on 08/08/2016.
 */
public class UbicacionesDBHelper extends SQLiteOpenHelper  {

    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "apptaxi114.db";

    public UbicacionesDBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_UBICACIONES = "CREATE TABLE " + Ubicaciones.TABLE  + "("
                + Ubicaciones.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Ubicaciones.KEY_CubId + " TEXT, "
                + Ubicaciones.KEY_CubCoordenadaX + " TEXT, "
                + Ubicaciones.KEY_CubCoordenadaY + " TEXT, "
                + Ubicaciones.KEY_CubVehiculoGPSOrientacion + " TEXT, "
                + Ubicaciones.KEY_CubVehiculoVelocidad + " TEXT, "
                + Ubicaciones.KEY_CubVehiculoGPSProveedor + " TEXT, "
                + Ubicaciones.KEY_CubVehiculoGPSExactitud + " TEXT, "

                + Ubicaciones.KEY_CubTiempoCreacion + " TEXT, "
                + Ubicaciones.KEY_CubEliminado + " TEXT )";

        db.execSQL(CREATE_TABLE_UBICACIONES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Ubicaciones.TABLE);
        // Create tables again
        onCreate(db);

    }



}
