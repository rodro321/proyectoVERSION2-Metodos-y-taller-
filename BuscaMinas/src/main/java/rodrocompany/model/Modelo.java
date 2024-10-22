/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rodrocompany.model;

import java.util.Scanner;

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
    Tablero board;
    int cantMinas;
    long time;
    int marcadores;

    public Modelo() {
        selectNivel();
    }

    public void Modelo(int x, int y, int cantMinas) {
        tablero = new char[x][y];
        this.cantMinas = marcadores = cantMinas;
        board = new Tablero(x,y,cantMinas);
        vacio();
    }
    
    private void  vacio(){
        for(int i = 0 ; i < tablero.length; i++){
            for(int j = 0 ; j < tablero[i].length; j++ ){
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
            if(j > 9){
                System.out.print(" " + j + " ");
            }else{
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
            if((board.validar(i, j))){
                aux = board.getCelda(i, j);
                if(!aux.tieneMina()){
                    aux.setMina(true);
                    tablero[i][j] = 'B';
                    Minas--;
                }
            }
            
        }
    }
    
    public void MinasCercas(){
        board.minasAlrededor();
        for(int i = 0 ; i< tablero.length;i++){
            for(int j = 0 ; j < tablero[i].length;j++){
                celda aux = new celda();
                aux = board.getCelda(i, j);
                if(aux.getMinaCerca() != -1 && aux.getMinaCerca()!= 0){
                    char auxChar = (char)(aux.getMinaCerca()+'0');
                    tablero[i][j] = auxChar;
                }
            }
        }
    }

    
}
