package Modelos;

public class Usuario {
    private String nombre;
    private String contrasenia;
    private Boolean esPremium;

    public Usuario(String nombre, String contrasenia, Boolean esPremium) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.esPremium = esPremium;
    }

    public String getNombre() {
        return nombre;
    }
}
