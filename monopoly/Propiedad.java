package monopoly;

public class Propiedad {
    private String nombre;
    private int costoCompra;
    private int costoAlquiler;
    private Jugador propietario;

    public Propiedad(String nombre, int costoCompra, int costoAlquiler) {
        this.nombre = nombre;
        this.costoCompra = costoCompra;
        this.costoAlquiler = costoAlquiler;
        this.propietario = null;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCostoCompra() {
        return costoCompra;
    }

    public int getCostoAlquiler() {
        return costoAlquiler;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }
}

