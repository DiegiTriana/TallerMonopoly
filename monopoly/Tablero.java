package monopoly;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tablero extends JPanel {
    private List<Propiedad> propiedades;

    public Tablero() {
        this.propiedades = new ArrayList<>();
        inicializarPropiedades();
        setLayout(new GridLayout(11, 11));
        inicializarCasillas();
    }

    private void inicializarPropiedades() {
        // Aquí se inicializan todas las propiedades del tablero en el orden correcto
        propiedades.add(new Propiedad("Mediterranean Avenue", 60, 2));
        propiedades.add(null); // Casilla de comunidad
        propiedades.add(new Propiedad("Baltic Avenue", 60, 4));
        propiedades.add(null); // Casilla de impuesto
        propiedades.add(new Propiedad("Reading Railroad", 200, 25));
        propiedades.add(new Propiedad("Oriental Avenue", 100, 6));
        propiedades.add(null); // Casilla de comunidad
        propiedades.add(new Propiedad("Vermont Avenue", 100, 6));
        propiedades.add(new Propiedad("Connecticut Avenue", 120, 8));
        propiedades.add(null); // Jail
        propiedades.add(new Propiedad("St. Charles Place", 140, 10));
        propiedades.add(new Propiedad("Electric Company", 150, 0));
        propiedades.add(new Propiedad("States Avenue", 140, 10));
        propiedades.add(new Propiedad("Virginia Avenue", 160, 12));
        propiedades.add(new Propiedad("Pennsylvania Railroad", 200, 25));
        propiedades.add(new Propiedad("St. James Place", 180, 14));
        propiedades.add(null); // Casilla de comunidad
        propiedades.add(new Propiedad("Tennessee Avenue", 180, 14));
        propiedades.add(new Propiedad("New York Avenue", 200, 16));
        propiedades.add(null); // Parking gratuito
        propiedades.add(new Propiedad("Kentucky Avenue", 220, 18));
        propiedades.add(null); // Casilla de comunidad
        propiedades.add(new Propiedad("Indiana Avenue", 220, 18));
        propiedades.add(new Propiedad("Illinois Avenue", 240, 20));
        propiedades.add(new Propiedad("B. & O. Railroad", 200, 25));
        propiedades.add(new Propiedad("Atlantic Avenue", 260, 22));
        propiedades.add(new Propiedad("Ventnor Avenue", 260, 22));
        propiedades.add(new Propiedad("Water Works", 150, 0));
        propiedades.add(new Propiedad("Marvin Gardens", 280, 24));
        propiedades.add(null); // Go to Jail
        propiedades.add(new Propiedad("Pacific Avenue", 300, 26));
        propiedades.add(new Propiedad("North Carolina Avenue", 300, 26));
        propiedades.add(null); // Casilla de comunidad
        propiedades.add(new Propiedad("Pennsylvania Avenue", 320, 28));
        propiedades.add(new Propiedad("Short Line", 200, 25));
        propiedades.add(null); // Casilla de comunidad
        propiedades.add(new Propiedad("Park Place", 350, 35));
        propiedades.add(null); // Luxury tax
        propiedades.add(new Propiedad("Boardwalk", 400, 50));
    }

    private void inicializarCasillas() {
        int contadorPropiedades = 0;
        for (int fila = 0; fila < 11; fila++) {
            for (int columna = 0; columna < 11; columna++) {
                int posicion = calcularPosicion(fila, columna);
                if (posicion >= 0 && posicion < propiedades.size()) {
                    Casilla casilla = new Casilla(posicion, propiedades.get(posicion));
                    add(casilla);
                } else {
                    add(new JPanel()); // Agrega un panel vacío para las posiciones fuera del tablero.
                }
            }
        }
    }

    private int calcularPosicion(int fila, int columna) {
        // Lógica para calcular la posición lineal en base a la fila y columna
        if (fila == 0) {
            return columna;
        } else if (columna == 10) {
            return 10 + fila - 1;
        } else if (fila == 10) {
            return 30 - columna;
        } else if (columna == 0) {
            return 20 + (10 - fila);
        } else {
            return -1; // Fuera del tablero
        }
    }

    public Propiedad getPropiedad(int posicion) {
        if (posicion >= 0 && posicion < propiedades.size()) {
            return propiedades.get(posicion);
        }
        return null;
    }
}
