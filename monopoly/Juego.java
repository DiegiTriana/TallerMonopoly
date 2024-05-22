package monopoly;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego {
    private List<Jugador> jugadores;
    private Tablero tablero;
    private int jugadorActual;
    private JFrame frame;
    private JLabel labelTurno;
    private JButton botonLanzarDado;
    private JTextArea areaEventos;
    private JPanel panelInfo;
    private JPanel panelEventos;
    private int dineroInicial;

    public Juego() {
        this.jugadores = new ArrayList<>();
        this.tablero = new Tablero();
        this.jugadorActual = 0;
    }

    public void mostrarMenuInicial() {
        frame = new JFrame("Configuración del Juego");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 2));

        JLabel labelJugadores = new JLabel("Número de Jugadores:");
        JComboBox<Integer> comboJugadores = new JComboBox<>(new Integer[]{2, 3, 4, 5, 6});
        JLabel labelDineroInicial = new JLabel("Dinero Inicial:");
        JTextField textoDineroInicial = new JTextField("1500");
        JButton botonIniciar = new JButton("Iniciar Juego");

        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numJugadores = (Integer) comboJugadores.getSelectedItem();
                    dineroInicial = Integer.parseInt(textoDineroInicial.getText());
                    iniciarJugadores(numJugadores);
                    frame.dispose();
                    iniciarJuego();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingresa un número válido para el dinero inicial.");
                }
            }
        });

        frame.add(labelJugadores);
        frame.add(comboJugadores);
        frame.add(labelDineroInicial);
        frame.add(textoDineroInicial);
        frame.add(new JLabel());
        frame.add(botonIniciar);

        frame.setVisible(true);
    }

    private void iniciarJugadores(int numJugadores) {
        for (int i = 1; i <= numJugadores; i++) {
            jugadores.add(new Jugador("Jugador " + i, dineroInicial));
        }
    }

    public void iniciarJuego() {
        frame = new JFrame("Monopoly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());

        frame.add(tablero, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        labelTurno = new JLabel("Turno de: " + jugadores.get(jugadorActual).getNombre());
        botonLanzarDado = new JButton("Lanzar Dado");
        botonLanzarDado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turno();
            }
        });
        panelInferior.add(labelTurno);
        panelInferior.add(botonLanzarDado);
        frame.add(panelInferior, BorderLayout.SOUTH);

        panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información de Jugadores"));

        panelEventos = new JPanel();
        panelEventos.setLayout(new BoxLayout(panelEventos, BoxLayout.Y_AXIS));
        panelEventos.setBorder(BorderFactory.createTitledBorder("Eventos"));
        areaEventos = new JTextArea(15, 20);
        areaEventos.setEditable(false);
        JScrollPane scrollPaneEventos = new JScrollPane(areaEventos);
        panelEventos.add(scrollPaneEventos);

        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(new BorderLayout());
        panelLateral.add(panelInfo, BorderLayout.NORTH);
        panelLateral.add(panelEventos, BorderLayout.CENTER);

        frame.add(panelLateral, BorderLayout.EAST);

        actualizarInformacionJugadores();

        frame.setVisible(true);
    }

    private void turno() {
        Jugador jugador = jugadores.get(jugadorActual);
        if (!jugador.estaEnJuego()) {
            siguienteTurno();
            return;
        }

        int dado = lanzarDado();
        areaEventos.append(jugador.getNombre() + " lanzó el dado y obtuvo: " + dado + "\n");
        jugador.mover(dado);

        Propiedad propiedad = tablero.getPropiedad(jugador.getPosicion());
        if (propiedad != null) {
            if (propiedad.getPropietario() == null) {
                if (jugador.getDinero() >= propiedad.getCostoCompra()) {
                    int opcion = JOptionPane.showConfirmDialog(frame, "¿Quieres comprar " + propiedad.getNombre() + " por $" + propiedad.getCostoCompra() + "?", "Comprar Propiedad", JOptionPane.YES_NO_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        jugador.modificarDinero(-propiedad.getCostoCompra());
                        propiedad.setPropietario(jugador);
                        jugador.agregarPropiedad(propiedad);
                        areaEventos.append(jugador.getNombre() + " compró " + propiedad.getNombre() + "\n");
                    }
                }
            } else if (!propiedad.getPropietario().equals(jugador)) {
                int alquiler = propiedad.getCostoAlquiler();
                jugador.modificarDinero(-alquiler);
                propiedad.getPropietario().modificarDinero(alquiler);
                areaEventos.append(jugador.getNombre() + " pagó $" + alquiler + " de alquiler a " + propiedad.getPropietario().getNombre() + "\n");
                if (jugador.getDinero() < 0) {
                    jugador.salirDelJuego();
                    areaEventos.append(jugador.getNombre() + " ha salido del juego\n");
                }
            }
        }

        areaEventos.append(jugador.getNombre() + " se encuentra en " + propiedad.getNombre() + "\n");

        actualizarInformacionJugadores();
        siguienteTurno();
    }

    private int lanzarDado() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    private void siguienteTurno() {
        jugadorActual = (jugadorActual + 1) % jugadores.size();
        labelTurno.setText("Turno de: " + jugadores.get(jugadorActual).getNombre());
    }

    private void actualizarInformacionJugadores() {
        panelInfo.removeAll();
        for (Jugador jugador : jugadores) {
            JLabel infoJugador = new JLabel(jugador.getNombre() + " - Dinero: $" + jugador.getDinero() + " - Propiedades: " + jugador.getNombresPropiedades());
            infoJugador.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panelInfo.add(infoJugador);
        }
        panelInfo.revalidate();
        panelInfo.repaint();
    }
}
