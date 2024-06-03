package Modelos;

public class Usuario {
    private String nombre;
    private String contrasenia;
    private Boolean esPremium;

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getEsPremium() {
        return esPremium;
    }

    public void setEsPremium(Boolean esPremium) {
        this.esPremium = esPremium;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public Usuario(String nombre, String contrasenia, Boolean esPremium) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.esPremium = esPremium;
    }

}
