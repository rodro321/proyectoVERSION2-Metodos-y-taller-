package controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import rodrocompany.model.Modelo;
import view.FrameTablero;

public class controller {

    private FrameTablero view;
    private Modelo model;
    private int nivel;
    private boolean marcar;
    private boolean desmarcar;
    private long score;

    public controller(FrameTablero view, Modelo model) {
        this.model = model;
        this.view = view;
        nivel = 1;
        marcar = desmarcar = false;
        score = System.currentTimeMillis() / 1000;
        inicializandoButtonListeners();
        inicializarMenuItems();
    }
    
    private void reset(int nivel) {
        this.nivel = nivel;
        marcar = desmarcar = false;
        score = System.currentTimeMillis() / 1000;
        inicializandoButtonListeners();
        inicializarMenuItems();

    }

    public void setNivel(int nuevoNivel) {
        descargarControles();
        this.nivel = nuevoNivel;

        int filas;
        int columnas;

        if (nivel == 1) {
            filas = 8;
            columnas = 10;
            view.reset(filas, columnas);
            model.reset(nivel);
        } else {
            if (nivel == 2) {
                filas = 14;
                columnas = 18;
                view.reset(filas, columnas);
                model.reset(nivel);

            } else {
                if (nivel == 3) {
                    filas = 20;
                    columnas = 24;
                    view.reset(filas, columnas);
                    model.reset(nivel);

                }

            }
        }
        view.revalidate();
        view.repaint();
        reset(nivel);
    }
    


    
    

    private void inicializarMenuItems() {
        JMenuItem nivelFacil = view.getnivelFacil();
        JMenuItem nivelMedio = view.getnivelMedio();
        JMenuItem nivelDificil = view.getnivelDificil();

        nivelFacil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleFacil(e);
            }
        });
        nivelMedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleMedio(e);
            }
        });
        nivelDificil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleDificil(e);
            }
        });
    }
       

    private void inicializandoButtonListeners() {
        model.selectNivel(nivel);
        JButton bandera = new JButton();
        bandera = view.getBandera();
        JButton[][] botones = view.getBotones();
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                botones[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnClick(e);
                    }
                });
            }
        }

        bandera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcar = true;
            }
        });
        view.revalidate();
        view.repaint();
    }
    
    public void seleFacil(ActionEvent e) {
        setNivel(1);

    }

    public void seleMedio(ActionEvent e) {
        setNivel(2);

    }

    public void seleDificil(ActionEvent e) {
        setNivel(3);

    }
    


    private void btnClick(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String[] coordenada = btn.getName().split(",");
        int posFila = Integer.parseInt(coordenada[0]);
        int posColumna = Integer.parseInt(coordenada[1]);

        if (!model.getEnd()) {
            view.revalidate();
            view.repaint();
            if (marcar) {
                marcar(posFila, posColumna);
            } else {
                seleccionarCasilla(posFila, posColumna);
            }
        }
    }
    
    public void marcar(int posFila, int posColumna){

        if(marcar){
                if(!model.getBoard().getCelda(posFila, posColumna).tieneBandera()){
                     JButton[][] botones = view.getBotones();
                    ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Bandera01.png"));
                    Image resizedImage = originalIcon.getImage().getScaledInstance(view.getboton(0, 0).getWidth(), view.getboton(0, 0).getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    botones[posFila][posColumna].setIcon(resizedIcon);
                    model.seleccionarCasilla(posFila, posColumna, marcar, desmarcar, score);
                    marcar = false;
                } else {
                     JButton[][] botones = view.getBotones();
                    if (model.getBoard().getCelda(posFila, posColumna).tieneBandera()) {
                        botones[posFila][posColumna].setIcon(null);
                        botones[posFila][posColumna].setBackground(new Color(211, 211, 211));
                        model.seleccionarCasilla(posFila, posColumna, marcar, desmarcar, score);

                    }

                    marcar = false;

             }
        }
        view.revalidate();
        view.repaint();
    }

    public void seleccionarCasilla(int posFila, int posColumna) {
        model.seleccionarCasilla(posFila, posColumna, marcar, desmarcar, score);

        if (model.getBoard().getCelda(posFila, posColumna).tieneMina()) {
            String[] posMinas = model.getposMinas();
            eventoPartidaPedida(posMinas);
        } else {
            JButton[][] botones = view.getBotones();
            for (int x = 0; x < botones.length; x++) {
                for (int y = 0; y < botones[x].length; y++) {
                    if (model.getBoard().getCelda(x, y).esVisible()) {
                        int minaCerca = model.getBoard().getCelda(x, y).getMinaCerca();
                        if (minaCerca > 0 && !model.getBoard().getCelda(x, y).tieneBandera()) {
                            botones[x][y].setText(minaCerca + "");
                            botones[x][y].setForeground(getColorNumero(minaCerca));
                        } else {
                            if (!model.getBoard().getCelda(x, y).tieneBandera()) {
                                botones[x][y].setText("");
                            }
                            botones[x][y].setBackground(new Color(240, 240, 240)); // Gris claro 
                        }
                    }
                }
            }
            view.revalidate();
            view.repaint();
        }

    }


    private Color getColorNumero(int numero) {
        switch (numero) {
            case 1:
                return new Color(0, 0, 255);     // Azul
            case 2:
                return new Color(0, 128, 0);     // Verde
            case 3:
                return new Color(255, 0, 0);     // Rojo
            case 4:
                return new Color(0, 0, 139);     // Azul oscuro
            case 5:
                return new Color(139, 69, 19);   // Marrón
            case 6:
                return new Color(46, 139, 87);   // Turquesa
            case 7:
                return new Color(0, 0, 0);       // Negro
            case 8:
                return new Color(128, 128, 128); // Gris oscuro
            default:
                return Color.BLACK;
        }
    }

    private void eventoPartidaPedida(String[] posMinas) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/mina.png"));
        Image resizedImage = originalIcon.getImage().getScaledInstance(view.getboton(0, 0).getWidth() + 15, view.getboton(0, 0).getHeight() + 15, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        for (String pos : posMinas) {
            String[] posiciones = pos.split(",");
            int x = Integer.parseInt(posiciones[0]);
            int y = Integer.parseInt(posiciones[1]);
            JButton btn = view.getboton(x, y);
            if (btn != null) {
                btn.setIcon(resizedIcon);

            }
        }
        view.revalidate();
        view.repaint();
    }
    private void descargarControles() {
        JButton[][] botones = view.getBotones();
        JButton bandera = view.getBandera();
        if (botones != null) {
            for (int i = 0; i < botones.length; i++) {
                for (int j = 0; j < botones[i].length; j++) {
                    if (botones[i][j] != null) {
                        view.getContentPane().remove(botones[i][j]);
                    }
                }
            }
        }
        if(bandera!=null){
            view.getContentPane().remove(bandera);
        }
        botones = null;
        bandera = null;
        view.getContentPane().removeAll();
        view.revalidate();
        view.repaint();
        
    }

}
