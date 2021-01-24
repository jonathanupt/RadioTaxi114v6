package radiotaxi114.radiotaxi114v6;

/**
 * Created by Jonathan on 08/08/2016.
 */
public class Ubicaciones {

    // Labels table name
    public static final String TABLE = "tblcubconductorubicacion";

    // Labels Table Columns names
    public static final String KEY_ID = "Id";
    public static final String KEY_CubId = "CubId";
    public static final String KEY_CubCoordenadaX = "CubCoordenadaX";
    public static final String KEY_CubCoordenadaY = "CubCoordenadaY";
    public static final String KEY_CubVehiculoGPSOrientacion = "CubVehiculoGPSOrientacion";
    public static final String KEY_CubVehiculoVelocidad = "CubVehiculoVelocidad";
    public static final String KEY_CubVehiculoGPSProveedor = "CubVehiculoGPSProveedor";
    public static final String KEY_CubVehiculoGPSExactitud = "CubVehiculoGPSExactitud";

    public static final String KEY_CubTiempoCreacion = "CubTiempoCreacion";
    public static final String KEY_CubEliminado = "CubEliminado";

    // property help us to keep data
    public int Id;
    public String CubId;
    public String CubCoordenadaX;
    public String CubCoordenadaY;
    public String CubVehiculoGPSOrientacion;
    public String CubVehiculoVelocidad;
    public String CubVehiculoGPSProveedor;
    public String CubVehiculoGPSExactitud;

    public String CubTiempoCreacion;
    public String CubEliminado;

}
