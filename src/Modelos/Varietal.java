package Modelos;

public class Varietal {
    private String descripcion;
    private int porcentComposicion;
    private TipoUva tipoUva;

    public String getDescripcion() {
        if (descripcion != null){
            return descripcion;
        }
        return "-";
    }

    public int getPorcentComposicion() {
        return porcentComposicion;
    }

    public TipoUva getTipoUva() {
        return tipoUva;
    }

    public Varietal(String descripcion, int porcentComposicion, TipoUva tipoUva) {
        this.descripcion = descripcion;
        this.porcentComposicion = porcentComposicion;
        this.tipoUva = tipoUva;
    }
}
