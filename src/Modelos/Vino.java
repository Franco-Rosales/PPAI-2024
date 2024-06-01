package Modelos;

public class Vino {

    // LOS ATRIBUTOS NO SON DEFINITIVOS SON PARA PROBAR
    private String nombre;
    private String imagenEtiqueta;
    private double precioARS;
    private double notaDeCataBodega;

    public String getNombre(){
        return nombre;
    }
    public Vino(String nombre, String imagenEtiqueta, double notaDeCataBodega, double precioARS) {
        this.nombre = nombre;
        this.imagenEtiqueta = imagenEtiqueta;
        this.notaDeCataBodega = notaDeCataBodega;
        this.precioARS = precioARS;
    }
    public void muestraFichaTecnica(){
        System.out.println("Vino: " + nombre);
        System.out.println("Precio: " + precioARS);
        System.out.println("Nota de cata: " + notaDeCataBodega);
    }
}
