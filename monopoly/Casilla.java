package monopoly;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Casilla extends JPanel {
    private int posicion;
    private Propiedad propiedad;
    private List<Jugador> jugadoresEnCasilla;

    public Casilla(int posicion, Propiedad propiedad) {
        this.posicion = posicion;
        this.propiedad = propiedad;
        this.jugadoresEnCasilla = new ArrayList<>();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(50, 50));
        actualizarVisualizacion();
    }

    public void agregarJugador(Jugador jugador) {
        jugadoresEnCasilla.add(jugador);
        actualizarVisualizacion();
    }

    public void removerJugador(Jugador jugador) {
        jugadoresEnCasilla.remove(jugador);
        actualizarVisualizacion();
    }

    private void actualizarVisualizacion() {
        removeAll();
        setLayout(new BorderLayout());
        if (propiedad != null) {
            JLabel nombrePropiedad = new JLabel("<html><div style='text-align: center;'>" + propiedad.getNombre() + "</div></html>");
            nombrePropiedad.setFont(new Font("Arial", Font.PLAIN, 10));
            add(nombrePropiedad, BorderLayout.NORTH);
        }
        JPanel panelJugadores = new JPanel();
        panelJugadores.setLayout(new GridLayout(1, jugadoresEnCasilla.size()));
        for (Jugador jugador : jugadoresEnCasilla) {
            JLabel label = new JLabel(jugador.getNombre().substring(0, 2));
            label.setFont(new Font("Arial", Font.BOLD, 12));
            panelJugadores.add(label);
        }
        add(panelJugadores, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
