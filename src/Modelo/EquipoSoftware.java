package Modelo;

public class EquipoSoftware {

    private int idEquipoSoftware;
    private int idEquipo;
    private int idSoftware;
    private String nombreEquipo;
    private String numeroSerieEquipo;
    private String nombreSoftware;
    private String tipoSoftware;
    private String versionInstalada;

    public int getIdEquipoSoftware() {
        return idEquipoSoftware;
    }

    public void setIdEquipoSoftware(int idEquipoSoftware) {
        this.idEquipoSoftware = idEquipoSoftware;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(int idSoftware) {
        this.idSoftware = idSoftware;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getNumeroSerieEquipo() {
        return numeroSerieEquipo;
    }

    public void setNumeroSerieEquipo(String numeroSerieEquipo) {
        this.numeroSerieEquipo = numeroSerieEquipo;
    }

    public String getNombreSoftware() {
        return nombreSoftware;
    }

    public void setNombreSoftware(String nombreSoftware) {
        this.nombreSoftware = nombreSoftware;
    }

    public String getTipoSoftware() {
        return tipoSoftware;
    }

    public void setTipoSoftware(String tipoSoftware) {
        this.tipoSoftware = tipoSoftware;
    }

    public String getVersionInstalada() {
        return versionInstalada;
    }

    public void setVersionInstalada(String versionInstalada) {
        this.versionInstalada = versionInstalada;
    }
    @Override
    public String toString() {
        return nombreSoftware + " - " + versionInstalada;
    }
}