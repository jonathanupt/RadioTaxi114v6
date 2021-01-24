package radiotaxi114.radiotaxi114v6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Jonathan on 08/08/2016.
 */
public class UbicacionesRepo {


    private UbicacionesDBHelper dbHelper;

    public UbicacionesRepo(Context context) {
        dbHelper = new UbicacionesDBHelper(context);
    }

    public int insert(Ubicaciones ubicaciones) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Ubicaciones.KEY_CubId, ubicaciones.CubId);
        values.put(Ubicaciones.KEY_CubCoordenadaX,ubicaciones.CubCoordenadaX);
        values.put(Ubicaciones.KEY_CubCoordenadaY, ubicaciones.CubCoordenadaY);
        values.put(Ubicaciones.KEY_CubVehiculoGPSOrientacion, ubicaciones.CubVehiculoGPSOrientacion);
        values.put(Ubicaciones.KEY_CubVehiculoVelocidad, ubicaciones.CubVehiculoVelocidad);
        values.put(Ubicaciones.KEY_CubVehiculoGPSProveedor, ubicaciones.CubVehiculoGPSProveedor);
        values.put(Ubicaciones.KEY_CubVehiculoGPSExactitud, ubicaciones.CubVehiculoGPSExactitud);

        values.put(Ubicaciones.KEY_CubTiempoCreacion, ubicaciones.CubTiempoCreacion);

        // Inserting Row
        long ubicaciones_Id = db.insert(Ubicaciones.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) ubicaciones_Id;
    }

   /* public void delete(int ubicaciones_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Ubicaciones.TABLE, Ubicaciones.KEY_ID + "= ?", new String[] { String.valueOf(ubicaciones_Id) });
        db.close(); // Closing database connection
    }*/

    public void delete(Ubicaciones ubicaciones) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(Ubicaciones.KEY_PexEstado,ubicaciones.PexEstado);
        values.put(Ubicaciones.KEY_CubEliminado,"1");

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Ubicaciones.TABLE, values, Ubicaciones.KEY_ID + "= ?", new String[] { String.valueOf(ubicaciones.Id) });
        db.close(); // Closing database connection
    }

    public void update(Ubicaciones ubicaciones) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Ubicaciones.KEY_CubCoordenadaX,ubicaciones.CubCoordenadaX);
        values.put(Ubicaciones.KEY_CubCoordenadaY, ubicaciones.CubCoordenadaY);
        values.put(Ubicaciones.KEY_CubVehiculoGPSOrientacion, ubicaciones.CubVehiculoGPSOrientacion);
        values.put(Ubicaciones.KEY_CubVehiculoVelocidad, ubicaciones.CubVehiculoVelocidad);
        values.put(Ubicaciones.KEY_CubVehiculoGPSProveedor, ubicaciones.CubVehiculoGPSProveedor);
        values.put(Ubicaciones.KEY_CubVehiculoGPSExactitud, ubicaciones.CubVehiculoGPSExactitud);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Ubicaciones.TABLE, values, Ubicaciones.KEY_ID + "= ?", new String[] { String.valueOf(ubicaciones.Id) });
        db.close(); // Closing database connection
    }

    public void updateId(Ubicaciones ubicaciones) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Ubicaciones.KEY_CubId, ubicaciones.CubId);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Ubicaciones.TABLE, values, Ubicaciones.KEY_ID + "= ?", new String[] { String.valueOf(ubicaciones.Id) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getUbicacionesList(String oFiltro,String oFecha,String oNuevo,String oEliminado,String oOrden,String oSentido, String oLimite) {

        String filtro = "";
        String fecha = "";
        String nuevo = "";
        String eliminado = "";
        String orden = "";
        String sentido = "";
        String limite = "";

        if(!oFiltro.equals("")){
           // filtro = " AND (CubCoordenadaX LIKE \"%"+oFiltro+"%\" OR PexObservacion LIKE  \"%"+oFiltro+"%\" OR VehUnidad LIKE \"%"+oFiltro+"%\" )";
        }

        if(!oFecha.equals("")){
            fecha = " AND PexFecha = \""+oFecha+"\"";
        }

        if(oNuevo.equals("1")){
            nuevo = " AND CubId = \"\"";
        }

        if(!oEliminado.equals("")){
            eliminado = " AND PexEliminado = \""+oEliminado+"\"";
        }

        if(!oOrden.equals("")){
            orden = " "+oOrden+" ";
        }

        if(!oSentido.equals("")){
            sentido = " "+oSentido+" ";
        }

        if(!oLimite.equals("")){
            limite = " "+oLimite+" ";
        }

        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Ubicaciones.KEY_ID + "," +
                Ubicaciones.KEY_CubId + "," +
                Ubicaciones.KEY_CubCoordenadaX + "," +
                Ubicaciones.KEY_CubCoordenadaY + "," +
                Ubicaciones.KEY_CubVehiculoGPSOrientacion + "," +
                Ubicaciones.KEY_CubVehiculoVelocidad + "," +
                Ubicaciones.KEY_CubVehiculoGPSProveedor + "," +
                Ubicaciones.KEY_CubVehiculoGPSExactitud + "," +

                Ubicaciones.KEY_CubTiempoCreacion + "," +
                Ubicaciones.KEY_CubEliminado +
                " FROM " + Ubicaciones.TABLE +
                " WHERE 1 = 1 "+filtro+fecha+nuevo+eliminado+orden+sentido+limite;

        Log.e("Consulta", selectQuery);

        //Ubicaciones ubicaciones = new Ubicaciones();
        ArrayList<HashMap<String, String>> ubicacionesList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> ubicaciones = new HashMap<String, String>();
                ubicaciones.put("Id", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_ID)));
                ubicaciones.put("CubId", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubId)));
                ubicaciones.put("CubCoordenadaX", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubCoordenadaX)));
                ubicaciones.put("CubCoordenadaY", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubCoordenadaY)));
                ubicaciones.put("CubVehiculoGPSOrientacion", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoGPSOrientacion)));
                ubicaciones.put("CubVehiculoVelocidad", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoVelocidad)));
                ubicaciones.put("CubVehiculoGPSProveedor", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoGPSProveedor)));
                ubicaciones.put("CubVehiculoGPSExactitud", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoGPSExactitud)));

                ubicaciones.put("CubTiempoCreacion", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubTiempoCreacion)));
                ubicaciones.put("CubEliminado", cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubEliminado)));

                ubicacionesList.add(ubicaciones);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ubicacionesList;

    }



    public Ubicaciones getUbicacionesById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Ubicaciones.KEY_ID + "," +
                Ubicaciones.KEY_CubId + "," +
                Ubicaciones.KEY_CubCoordenadaX + "," +
                Ubicaciones.KEY_CubCoordenadaY + "," +
                Ubicaciones.KEY_CubVehiculoGPSOrientacion + "," +
                Ubicaciones.KEY_CubVehiculoVelocidad + "," +
                Ubicaciones.KEY_CubVehiculoGPSProveedor + "," +
                Ubicaciones.KEY_CubVehiculoGPSExactitud + "," +

                Ubicaciones.KEY_CubTiempoCreacion + "," +
                Ubicaciones.KEY_CubEliminado +
                " FROM " + Ubicaciones.TABLE
                + " WHERE " +
                Ubicaciones.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Ubicaciones ubicaciones = new Ubicaciones();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                ubicaciones.Id =cursor.getInt(cursor.getColumnIndex(Ubicaciones.KEY_ID));
                ubicaciones.CubId =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubId));

                ubicaciones.CubCoordenadaX  =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubCoordenadaX));
                ubicaciones.CubCoordenadaY =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubCoordenadaY));
                ubicaciones.CubVehiculoGPSOrientacion =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoGPSOrientacion));
                ubicaciones.CubVehiculoVelocidad =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoVelocidad));
                ubicaciones.CubVehiculoGPSProveedor =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoGPSProveedor));
                ubicaciones.CubVehiculoGPSExactitud =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoGPSExactitud));

                ubicaciones.CubTiempoCreacion =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubTiempoCreacion));
                ubicaciones.CubEliminado =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubEliminado));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ubicaciones;
    }



    public Ubicaciones getUbicacionesLast(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Ubicaciones.KEY_ID + "," +
                Ubicaciones.KEY_CubId + "," +
                Ubicaciones.KEY_CubCoordenadaX + "," +
                Ubicaciones.KEY_CubCoordenadaY + "," +
                Ubicaciones.KEY_CubVehiculoGPSOrientacion + "," +
                Ubicaciones.KEY_CubVehiculoVelocidad + "," +
                Ubicaciones.KEY_CubVehiculoGPSProveedor + "," +
                Ubicaciones.KEY_CubVehiculoGPSExactitud + "," +

                Ubicaciones.KEY_CubTiempoCreacion + "," +
                Ubicaciones.KEY_CubEliminado +
                " FROM " + Ubicaciones.TABLE
                + " WHERE 1 = 1 ORDER BY Id DESC LIMIT 1";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Ubicaciones ubicaciones = new Ubicaciones();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ubicaciones.Id =cursor.getInt(cursor.getColumnIndex(Ubicaciones.KEY_ID));
                ubicaciones.CubId =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubId));

                ubicaciones.CubCoordenadaX  =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubCoordenadaX));
                ubicaciones.CubCoordenadaY =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubCoordenadaY));
                ubicaciones.CubVehiculoGPSOrientacion =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoGPSOrientacion));
                ubicaciones.CubVehiculoVelocidad =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoVelocidad));
                ubicaciones.CubVehiculoGPSProveedor =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoGPSProveedor));
                ubicaciones.CubVehiculoGPSExactitud =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubVehiculoGPSExactitud));

                ubicaciones.CubTiempoCreacion =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubTiempoCreacion));
                ubicaciones.CubEliminado =cursor.getString(cursor.getColumnIndex(Ubicaciones.KEY_CubEliminado));




            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ubicaciones;
    }



}
