package Modelos;

public class Vino {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private String nombre;
    private String imagenEtiqueta;
    private double precioARS;
    private double notaCataBodega;
    private Maridaje maridaje;
    private Varietal varietal;

    public String getImagenEtiqueta() {
        return imagenEtiqueta;
    }

    public double getPrecioARS() {
        return precioARS;
    }

    public double getNotaCataBodega() {
        return notaCataBodega;
    }

    public Maridaje getMaridaje() {
        return maridaje;
    }

    public Varietal getVarietal() {
        return varietal;
    }

    public void setImagenEtiqueta(String imagenEtiqueta) {
        this.imagenEtiqueta = imagenEtiqueta;
    }

    public void setPrecioARS(double precioARS) {
        this.precioARS = precioARS;
    }

    public void setNotaCataBodega(double notaCataBodega) {
        this.notaCataBodega = notaCataBodega;
    }

    public String getNombre(){
        return nombre;
    }
    public Vino(String nombre, String imagenEtiqueta, double notaDeCataBodega, double precioARS, Varietal varietal, Maridaje maridaje) {
        Varietal nuevoVarietal = crearVarietal(varietal);
        this.nombre = nombre;
        this.imagenEtiqueta = imagenEtiqueta;
        this.notaCataBodega = notaDeCataBodega;
        this.precioARS = precioARS;
        this.varietal = nuevoVarietal;
        this.maridaje = maridaje;
    }
    public String getDatos() {
        String descripcionVarietal = (varietal != null) ? varietal.getDescripcion() : "-";
        String datosMaridaje = (maridaje != null) ? maridaje.getDatos() : "-";

        return String.format(
                "Nombre: %s\nImagen: %s\nNota de Cata: %.2f\nPrecio (ARS): %.2f\nVarietal: %s\nMaridaje: %s",
                nombre, imagenEtiqueta, notaCataBodega, precioARS, descripcionVarietal, datosMaridaje
        );
    }


    public Varietal crearVarietal(Varietal varietal){
        Varietal nuevoVarietal = new Varietal(varietal.getDescripcion(), varietal.getPorcentComposicion(), varietal.getTipoUva());
        return nuevoVarietal;
    }
}
