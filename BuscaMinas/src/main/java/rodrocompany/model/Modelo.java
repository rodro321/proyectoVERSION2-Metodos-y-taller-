/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rodrocompany.model;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author win
 */
public class Modelo {

    /*3 tamaños 10X8 10 minas  
                18x14  40 minas
                20x24 99 minas 
     */
    Scanner scan = new Scanner(System.in);
    private char[][] tablero;
    private Tablero board;
    private int cantMinas;
    private long time;
    private int marcadores;
    private boolean end;
    private String[] posMinas;
    private boolean primeraJugada = true;

    public Modelo() {
        cantMinas = marcadores = 0;
        end = false;
        //iniciarJuego();
    }
    
    public void reset(int nivel){
        tablero =  null;
        board = null;
        cantMinas = 0;
        time = 0;
        marcadores = 0;
        end = false;
        posMinas = null;
        primeraJugada = true;

    }

    public void inicializar(int x, int y, int cantMinas) {
        tablero = new char[x][y];
        iniciarTablero();
        this.cantMinas = marcadores = cantMinas;
        board = new Tablero(x, y, cantMinas);
        posMinas = new String[cantMinas];
        end = false;
    }
    
    public String [] getposMinas(){
        return posMinas;
    }
    
    public Tablero getBoard(){
        return board;
    }
    
    public boolean getEnd(){
        return end;
    }
    private void iniciarTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = '*';
            }
        }
    }

    public void selectNivel(int dificultad) {


        switch (dificultad) {
            case 1:

                inicializar(8, 10, 10);
                break;
            case 2:
                inicializar(14, 18, 40);
                break;
            case 3:
                inicializar(20, 24, 99);
                break;
            default:
                System.out.println("Valor no valido");

        }
    }

    public void mostrar() {
        mostrar(tablero);
    }

    private void mostrar(char[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print("" + tablero[i][j] + " | ");
            }
            System.out.println("    " + i + "\n");
        }
        System.out.print("\n ");
        for (int j = 0; j < tablero[0].length; j++) {
            if (j > 9) {
                System.out.print(" " + j + " ");
            } else {
                System.out.print(" " + j + "  ");
            }
        }
        System.out.println("\n \n");
    }

    public void generarMinas(int x, int y) {
        generarMinas(cantMinas, x, y);

    }

    private void generarMinas(int Minas, int x, int y) {
        celda aux = new celda();
        while (Minas > 0) {
            //genera numero aleatorios que no sobre el limite del tablero
            int i = (int) (Math.random() * tablero.length);
            int j = (int) (Math.random() * tablero[0].length);

            //valida que las posiciones generadas sean validas
            if ((board.validar(i, j))) {
                aux = board.getCelda(i, j);
                if (!aux.tieneMina() && (x != i && y != j)) {
                    aux.setMina(true);
                    posMinas[Minas - 1] = (i + "," + j);
                    tablero[i][j] = 'B'; //era para controlar que se coloque bomba
                    Minas--;
                }
            }

        }
    }

    public void MinasCercas() {
        board.minasAlrededor();

    }

    public void revelar(int i, int j) {
        board.revelar(i, j, board);
        
    }



    public void seleccionarCasilla(int i,int j, boolean marcar, boolean desmarcar, long ini) {
        if (!end) {
            if (primeraJugada) {
                generarMinas(i, j);
                primeraJugada = false;
                MinasCercas();
                end = terminarJuego(i, j, ini);
            } else {
                if (marcar == false && desmarcar == false && !board.getCelda(i, j).tieneBandera()) {
                    //revelar(i,j);
                    end = terminarJuego(i, j, ini);

                } else {
                    if (marcar && !board.getCelda(i, j).tieneBandera()) {

                        if (marcadores != 0) {
                            marcadores--;
                            board.getCelda(i, j).Marcar();
                            tablero[i][j]='M';
                        }
                        if (marcadores == 0) {
                            end = terminarJuego(i, j, ini);
                        }

                    } else {

                        if (board.getCelda(i, j).tieneBandera()) {
                            board.getCelda(i, j).Desmarcar();
                            marcadores = marcadores + 1;
                            tablero[i][j]='*';
                        }

                    }
                }
            }

            if (i != -1 && j != -1) {
                revelar(i, j);
                mostrar();
            }
        }
        

    }

    private boolean terminarJuego(int i, int j, long ini) {
        if (marcadores == 0) {
            if (VerfificarMarcadores()) {
                end = true;
                long fin = System.currentTimeMillis() / 1000;
                time = fin - ini;
                System.out.println("WIN");
                JOptionPane.showMessageDialog(null, "Win \nTime: " + time);

            }
        } else {

            if (board.getCelda(i, j).tieneMina()) {
                end = true;
                long fin = System.currentTimeMillis() / 1000;
                time = fin - ini;
                //marcarMinas(posMinas);
                System.out.println("GAME OVER");
                JOptionPane.showMessageDialog(null, "Game Over \nTime: " + time);
            } else {
                if (partidaGanada()) {
                    end = true;
                    long fin = System.currentTimeMillis() / 1000;
                    time = fin - ini;
                    System.out.println("WIN");
                    JOptionPane.showMessageDialog(null, "Win \nTime: " + time);
                }
            }

        }

        return end;
    }

    public boolean partidaGanada() {
    int casillasAbiertas = 0;
    boolean ganada = false;
    for (int i = 0; i < board.getSizeX(); i++) {
        for (int j = 0; j < board.getSizey(); j++) {
            if (board.getCelda(i, j).esVisible() && !board.getCelda(i, j).tieneMina()) {
                casillasAbiertas++;
            }
        }
    }
    if (casillasAbiertas >= (board.getSizeX() * board.getSizey()) - cantMinas) {
        ganada = true;
    }
    return ganada;
}


   

    public boolean VerfificarMarcadores() {
        return verificarMarcadores(posMinas);
    }

    private boolean verificarMarcadores(String[] posMina) {
        boolean termino = true;
        for (int i = 0; i < posMina.length; i++) {
            String[] posiciones = posMina[i].split(",");
            int x = Integer.parseInt(posiciones[0]);
            int y = Integer.parseInt(posiciones[1]);
            if (!board.getCelda(i, y).tieneBandera() || !board.getCelda(x, y).tieneMina()) {
                return false;  // Falta una mina sin marcar o hay un error en una posición marcada
            }
        }

        return termino;
    }
}
