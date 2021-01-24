package radiotaxi114.radiotaxi114v6;

/**
 * Created by USUARIO on 02/07/2015.
 */
public class RankingResults {

    private String ConductorId = "";
    private String ConductorNombre = "";
    private String ConductorFoto = "";
    private String VehiculoUnidad = "";
    private String RankingTotal = "";


    public String getConductorNombre() {
        return ConductorNombre;
    }

    public void setConductorNombre(String conductorNombre) {
        ConductorNombre = conductorNombre;
    }

    public String getConductorFoto() {
        return ConductorFoto;
    }

    public void setConductorFoto(String conductorFoto) {
        ConductorFoto = conductorFoto;
    }

    public String getVehiculoUnidad() {
        return VehiculoUnidad;
    }

    public void setVehiculoUnidad(String vehiculoUnidad) {
        VehiculoUnidad = vehiculoUnidad;
    }

    public String getRankingTotal() {
        return RankingTotal;
    }

    public void setRankingTotal(String rankingTotal) {
        RankingTotal = rankingTotal;
    }

    public String getConductorId() {
        return ConductorId;
    }

    public void setConductorId(String conductorId) {
        ConductorId = conductorId;
    }
}
