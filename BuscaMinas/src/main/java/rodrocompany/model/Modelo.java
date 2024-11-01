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

    public Modelo() {
        //selectNivel();
        this.cantMinas = marcadores = 0;
        end = false;
    }

    public void Modelo(int x, int y, int cantMinas) {
        tablero = new char[x][y];
        this.cantMinas = marcadores = cantMinas;
        board = new Tablero(x, y, cantMinas);
        iniciarTablero();
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

                Modelo(10, 8, 10);
                break;
            case 2:
                Modelo(18, 14, 40);
                break;
            case 3:
                Modelo(24, 20, 99);
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

    public void generarMinas() {
        generarMinas(cantMinas);
    }

    private void generarMinas(int Minas) {
        celda aux = new celda();
        while (Minas > 0) {
            //genera numero aleatorios que no sobre el limite del tablero
            int i = (int) (Math.random() * tablero.length);
            int j = (int) (Math.random() * tablero[0].length);

            //valida que las posiciones generadas sean validas
            if ((board.validar(i, j))) {
                aux = board.getCelda(i, j);
                if (!aux.tieneMina()) {
                    aux.setMina(true);
                    //tablero[i][j] = 'B';era para controlar que se coloque bomba
                    Minas--;
                }
            }

        }
    }

    public void MinasCercas() {
        board.minasAlrededor();
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                celda aux = new celda();
                aux = board.getCelda(i, j);
                //if (aux.getMinaCerca() != -1 && aux.getMinaCerca() != 0) {
                //  char auxChar = (char) (aux.getMinaCerca() + '0');
                //tablero[i][j] = auxChar;
                //}
            }
        }
    }

    public void revelar(int i, int j) {
        board.revelar(i, j, board);
        for (int x = 0; x < tablero.length; x++) {
            for (int y = 0; y < tablero[x].length; y++) {
                if (board.getCelda(x, y).esVisible()) {
                    int minaCerca = board.getCelda(x, y).getMinaCerca();
                    if (minaCerca > 0) {
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
        mostrar();
        long ini = System.currentTimeMillis() / 1000;
        recogerDatos(ini);
        generarMinas();
        MinasCercas();
        while (!end) {
            mostrar();
            recogerDatos(ini);

        }
    }

    private void recogerDatos(long ini) {
        System.out.println("Revelar:R | marcar: M | Desmarcar: D");
        String sele = scan.nextLine();
        if (sele.equalsIgnoreCase("R")) {
            System.out.println("selecion posicion en i: ");
            int i = scan.nextInt();
            System.out.println("selecion posicion en j: ");
            int j = scan.nextInt();
            revelar(i, j);
            end = terminarJuego(i, j, ini);
        }
        if (sele.equalsIgnoreCase("M")) {
            System.out.println("selecion posicion en i: ");
            int i = scan.nextInt();
            System.out.println("selecion posicion en j: ");
            int j = scan.nextInt();
            tablero[i][j] ='M';
            board.getCelda(i, j).Marcar();
            end = terminarJuego(i, j, ini);
        }

        if (sele.equalsIgnoreCase("D")) {
            System.out.println("selecion posicion en i: ");
            int i = scan.nextInt();
            System.out.println("selecion posicion en j: ");
            int j = scan.nextInt();
            tablero[i][j] ='*';
            board.getCelda(i, j).Desmarcar();
        }

    }

    private boolean terminarJuego(int i, int j, long ini) {

        if (board.getCelda(i, j).tieneMina() || verificarMarcadores()) {
            end = true;
            long fin = System.currentTimeMillis() / 1000;
            time = fin - ini;
            System.out.println("GAME OVER");
            JOptionPane.showMessageDialog(null, "Game Over \nScore: " + time);
        }
        return end;
    }

    public boolean verificarMarcadores() {
        boolean termino = false;
        if (marcadores == 0) {
            for (int x = 0; x < tablero.length; x++) {
                for (int y = 0; y < tablero[x].length; y++) {
                    celda aux = board.getCelda(x, y);
                    if (tablero[x][y] == 'M') {
                        if (aux.tieneMina()) {
                            termino = true;
                        }
                    }
                }
            }
        }

        return termino;
    }

}
