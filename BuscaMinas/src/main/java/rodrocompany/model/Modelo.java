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
    public char[][] tablero;
    public Tablero board;
    public int cantMinas;
    public long time;
    public int marcadores;
    public boolean end;
    public String[] posMinas;
    private boolean primeraJugada = true;

    public Modelo() {
        cantMinas = marcadores = 0;
        end = false;
    }

    public void inicializar(int x, int y, int cantMinas) {
        tablero = new char[x][y];
        iniciarTablero();
        this.cantMinas = marcadores = cantMinas;
        board = new Tablero(x, y, cantMinas);
        posMinas = new String[cantMinas];
        end = false;
    }

    private void iniciarTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = '*';
            }
        }
    }

    public void selectNivel() {
        System.out.println("Selecionar dificultad: ");
        System.out.println("1. FACIL \n2. MEDIO \n3. DIFICIL");
        int dificultad = scan.nextInt();

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
                    //tablero[i][j] = 'B'; //era para controlar que se coloque bomba
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
        for (int x = 0; x < tablero.length; x++) {
            for (int y = 0; y < tablero[x].length; y++) {
                if (board.getCelda(x, y).esVisible()) {
                    int minaCerca = board.getCelda(x, y).getMinaCerca();
                    if (minaCerca > 0 && tablero[x][y] != 'M') {
                        char auxChar = (char) (minaCerca + '0');
                        tablero[x][y] = auxChar;
                    } else {
                        if (board.getCelda(x, y).esVisible() && tablero[x][y] != 'M') {
                            tablero[x][y] = ' ';
                        }
                    }

                }

            }
        }
    }

    public void iniciarJuego() {
        selectNivel();
        long ini = System.currentTimeMillis() / 1000;
        while (!end) {
            mostrar();
            recogerDatos(ini);

        }
    }

    private void recogerDatos(long ini) {
        int i = -1;
        int j = -1;
        if (primeraJugada) {
            System.out.println("Coloque su primera Jugada");
            System.out.println("Selecciona posición en i: ");
             i = scan.nextInt();
            System.out.println("Selecciona posición en j: ");
             j = scan.nextInt();
            generarMinas(i, j);
            primeraJugada = false;
            MinasCercas();
            end = terminarJuego(i, j, ini);
        }else{
            System.out.println("Revelar:R | marcar: M | Desmarcar: D");
            String sele = scan.nextLine();
            if (sele.equalsIgnoreCase("R")) {
                System.out.println("selecion posicion en i: ");
                i = scan.nextInt();
                System.out.println("selecion posicion en j: ");
                j = scan.nextInt();
                //revelar(i, j);
                end = terminarJuego(i, j, ini);

            }else{
                if (sele.equalsIgnoreCase("M")) {
                    System.out.println("selecion posicion en i: ");
                    i = scan.nextInt();
                    System.out.println("selecion posicion en j: ");
                    j = scan.nextInt();
                    if (marcadores != 0) {
                        tablero[i][j] = 'M';
                        marcadores--;
                        board.getCelda(i, j).Marcar();
                    }
                    if (marcadores == 0) {
                        end = terminarJuego(i, j, ini);
                    }

                }else{
                    if (sele.equalsIgnoreCase("D")) {
                        System.out.println("selecion posicion en i: ");
                        i = scan.nextInt();
                        System.out.println("selecion posicion en j: ");
                        j = scan.nextInt();
                        if (tablero[i][j] == 'M') {
                            tablero[i][j] = '*';
                            marcadores = marcadores + 1;
                            board.getCelda(i, j).Desmarcar();
                        }
                    }
                }
            }
        }

        if (i != -1 && j != -1) {
            revelar(i, j);
        }

    }

    private boolean terminarJuego(int i, int j, long ini) {
        if (marcadores == 0) {
            if (VerfificarMarcadores()) {
                end = true;
                long fin = System.currentTimeMillis() / 1000;
                time = fin - ini;
                System.out.println("WIN");
                JOptionPane.showMessageDialog(null, "Game Over \nScore: " + time);

            }
        } else {
            if (board.getCelda(i, j).tieneMina()) {
                end = true;
                long fin = System.currentTimeMillis() / 1000;
                time = fin - ini;
                marcarMinas(posMinas);
                System.out.println("GAME OVER");
                JOptionPane.showMessageDialog(null, "Game Over \nScore: " + time);
            }
        }

        return end;
    }

    private void marcarMinas(String[] posMina) {
        for (int i = 0; i < posMina.length; i++) {
            String[] posiciones = posMina[i].split(",");
            int x = Integer.parseInt(posiciones[0]);
            int y = Integer.parseInt(posiciones[1]);
            board.getCelda(x, y).revelar();
            tablero[x][y] = 'B';
            mostrar();
        }
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
            if (tablero[x][y] != 'M' || !board.getCelda(x, y).tieneMina()) {
                return false;  // Falta una mina sin marcar o hay un error en una posición marcada
            }
        }

        return termino;
    }
}
