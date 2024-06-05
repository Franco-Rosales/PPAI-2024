package Modelos;

public class Vino {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private String nombre;
    private String imagenEtiqueta;
    private double precioARS;
    private double notaCataBodega;
    private int aniada;
    private Maridaje maridaje;
    private Varietal varietal;

    public int getAniada() {
        return aniada;
    }

    public void setAniada(int aniada) {
        this.aniada = aniada;
    }

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
    public Vino(String nombre, String imagenEtiqueta, double notaDeCataBodega, double precioARS, int aniada,Varietal varietal, Maridaje maridaje) {
        Varietal nuevoVarietal = crearVarietal(varietal);
        this.nombre = nombre;
        this.imagenEtiqueta = imagenEtiqueta;
        this.notaCataBodega = notaDeCataBodega;
        this.precioARS = precioARS;
        this.aniada = aniada;
        this.varietal = nuevoVarietal;
        this.maridaje = maridaje;
    }
    public String getDatos() {
        String descripcionVarietal = (varietal != null) ? varietal.getDescripcion() : "-";
        String datosMaridaje = (maridaje != null) ? maridaje.getDatos() : "-";

        return String.format(
                "<html>" +
                        "<div style='font-family: SansSerif; padding: 10px;'>" +
                        "<h2 style='color: #2980b9;'>%s</h2>" +
                        "<img src='%s' alt='Imagen del Vino' style='width:100%%; height:auto;' /><br/>" +
                        "<p><strong>Nota de Cata:</strong> %.2f</p>" +
                        "<p><strong>Precio (ARS):</strong> %.2f</p>" +
                        "<p><strong>Añada:</strong> %s</p>" +
                        "<p><strong>Varietal:</strong> %s</p>" +
                        "<p><strong>Maridaje:</strong> %s</p>" +
                        "</div>" +
                        "</html>",
                nombre, imagenEtiqueta, notaCataBodega, precioARS, aniada, descripcionVarietal, datosMaridaje
        );
    }


    public Varietal crearVarietal(Varietal varietal){
        Varietal nuevoVarietal = new Varietal(varietal.getDescripcion(), varietal.getPorcentajeComposicion(), varietal.getTipoUva());
        return nuevoVarietal;
    }

    public boolean sosActualizable(Vino vinoExistente){
        return this.equals(vinoExistente);

    }
}
