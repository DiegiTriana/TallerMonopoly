package monopoly;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private int dinero;
    private int posicion;
    private boolean enJuego;
    private List<Propiedad> propiedades;

    public Jugador(String nombre, int dineroInicial) {
        this.nombre = nombre;
        this.dinero = dineroInicial;
        this.posicion = 0;
        this.enJuego = true;
        this.propiedades = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getDinero() {
        return dinero;
    }

    public void modificarDinero(int cantidad) {
        dinero += cantidad;
    }

    public int getPosicion() {
        return posicion;
    }

    public void mover(int pasos) {
        posicion = (posicion + pasos) % 40; // asumiendo un tablero de 40 posiciones
    }

    public boolean estaEnJuego() {
        return enJuego;
    }

    public void salirDelJuego() {
        enJuego = false;
    }

    public void agregarPropiedad(Propiedad propiedad) {
        propiedades.add(propiedad);
    }

    public List<Propiedad> getPropiedades() {
        return propiedades;
    }

    public String getNombresPropiedades() {
        List<String> nombres = new ArrayList<>();
        for (Propiedad propiedad : propiedades) {
            nombres.add(propiedad.getNombre());
        }
        return String.join(", ", nombres);
    }
}
