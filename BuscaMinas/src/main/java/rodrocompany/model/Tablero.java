/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rodrocompany.model;

/**
 *
 * @author win
 */
public class Tablero {

    celda tablero[][];
    int cantMinas;
    long time;

    //constructor que se inicia antes de
    //pasar el tamaño del tablero y cantMinas
    public Tablero() {

    }

    //constructo donde construimos el tablero
    //con el tamaño  y minas elegidas por el usuario

    public Tablero(int i, int j, int minas) {
        tablero = new celda[i][j];
        cantMinas = minas;
        for(int x = 0 ; x < tablero.length;x++){
            for (int y =0 ; y < tablero[x].length;y++){
                tablero[x][y] = new celda();
            }
            
        }
    }

    public int getNroMinas() {
        return cantMinas;
    }

    public celda getCelda(int i, int j) {
        return tablero[i][j].getCelda();
    }

    public void revelar(int i, int j) {
        celda aux = tablero[i][j].getCelda();
        aux.revelar();
    }


    /**
    private int MinasAlrededor(int i , int j){
        int res = -1;
        celda aux;  
        aux = tablero[i][j].getCelda();
        //es esquina superior izquirda
        if(i==0 && j ==0){
            res = CalMinasAlredor(i, j+1) + CalMinasAlredor(i+1, j)+CalMinasAlredor(i+1, j+1);
        }else{
            //es esquina inferior derecha
            if(i == tablero[j].length && j == tablero.length){
                res = CalMinasAlredor(i-1, j) + CalMinasAlredor(i, j-1)+CalMinasAlredor(i-1, j-1);
            }else{
                //es esquina superior izquirda
                if(i == 0 && j == tablero.length){
                    res = CalMinasAlredor(i, j-1) + CalMinasAlredor(i+1, j)+CalMinasAlredor(i+1, j-1);
                }else{
                    //es esquina inferior izquierda
                    if(j == 0 && i == tablero[j].length){
                        res = CalMinasAlredor(i-1, j-1) + CalMinasAlredor(i+1, j)+CalMinasAlredor(i+1, j-1);
                    }else{
                        res = res = CalMinasAlredor(i, j-1) + CalMinasAlredor(i+1, j)+CalMinasAlredor(i+1, j-1);
                        //revisar se puede usar backtracking
                    }
                }
            }
        }
        return res;
    }
    */
 
    //calcula las minas cercas en todas posiciones
    public void minasAlrededor(){
        for(int i = 0 ; i < tablero.length;i++){
            for(int j = 0; j < tablero[i].length; j++){
                if(!getCelda(i, j).tieneMina()){
                    calcularMinasCerca(i,j);
                }
            }
    
        }
    }
    
    //recorre el alrededor de una celda para ver si tiene alguna mina cerca
    //para anotarlo
    private void calcularMinasCerca(int i, int j){
        int res = 0;
        // Verificar la celda superior izquierda
        if(validar(i-1, j-1)){
            if(getCelda(i-1, j-1).tieneMina()){
                res++;
            }
        }
        // Verificar la celda superior
        if(validar(i-1, j)){
            if(getCelda(i-1, j).tieneMina()){
                res++;
            }
        }
        // Verificar la celda superior derecha
        if(validar(i-1, j+1)){
            if(getCelda(i-1, j+1).tieneMina()){
                res++;
            }
        }
        // Verificar la celda izquierda
        if(validar(i, j-1)){
            if(getCelda(i, j-1).tieneMina()){
                res++;
            }
        }
        // Verificar la celda derecha
        if(validar(i, j+1)){
            if(getCelda(i, j+1).tieneMina()){
                res++;
            }
        }
        // Verificar la celda inferior izquierda
        if(validar(i+1,j-1)){
            if(getCelda(i+1, j-1).tieneMina()){
                res++;
            }
        }
        // Verificar la celda inferior
        if(validar(i+1,j)){
            if(getCelda(i+1, j).tieneMina()){
                res++;
            }
        }
        // Verificar la celda inferior derecha
        if(validar(i+1,j+1)){
            if(getCelda(i+1, j+1).tieneMina()){
                res++;
            }
        }
        //caso que no tenga minas ninguno no hacemos el set
        if(res != 0){
            getCelda(i, j).setMinaCercas(res);
        }
        
    }
        
    
    //verifica que la posicion es es valida
    // no sea una posicion que no tenga el tablero
    public boolean validar(int i, int j){
        boolean res = true;
        if(i < 0 || j < 0 || i > tablero.length - 1 || j > tablero[i].length-1){
            res = false;
        }
        return res;
    }
}

