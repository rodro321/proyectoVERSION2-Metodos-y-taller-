
package rodrocompany.model;


public class Tablero {

    private celda tablero[][];
    private int cantMinas;


    //constructo donde construimos el tablero
    //con el tamaño  y minas elegidas por el usuario
    public Tablero(int i, int j, int minas) {
        tablero = new celda[i][j];
        cantMinas = minas;
        for (int x = 0; x < tablero.length; x++) {
            for (int y = 0; y < tablero[x].length; y++) {
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

    public boolean revelar(int i, int j, Tablero tablero) {
        boolean revela;
        // Verificar si la celda está dentro de los límites y no tiene mina
        if (validar(i, j) && !tablero.getCelda(i, j).tieneMina()) {
            celda aux = tablero.getCelda(i, j);
            if (aux.esVisible()) {
                revela = false;
            } else {
                //revela celda actual
                aux.revelar();
                if (aux.getMinaCerca() == 0) {
                    //revela en las posiciones
                    revelar(i - 1, j, tablero); // norte
                    revelar(i + 1, j, tablero); // sur
                    revelar(i, j - 1, tablero); // oeste
                    revelar(i, j + 1, tablero); // este
                    revelar(i - 1, j - 1, tablero); // noroeste
                    revelar(i - 1, j + 1, tablero); // noreste
                    revelar(i + 1, j - 1, tablero); // suroeste
                    revelar(i + 1, j + 1, tablero); // sureste
                }
                revela = true;
            }
        } else {

            revela = false;
        }
        return revela;
    }

    //calcula las minas cercas en todas posiciones
    public void minasAlrededor() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (!getCelda(i, j).tieneMina()) {
                    calcularMinasCerca(i, j);
                }
            }

        }
    }

    //recorre el alrededor de una celda para ver si tiene alguna mina cerca
    //para anotarlo
    private void calcularMinasCerca(int i, int j) {
        int res = 0;

        if (validar(i - 1, j - 1)) {
            if (getCelda(i - 1, j - 1).tieneMina()) {
                res++;
            }
        }
        if (validar(i - 1, j)) {
            if (getCelda(i - 1, j).tieneMina()) {
                res++;
            }
        }
        if (validar(i - 1, j + 1)) {
            if (getCelda(i - 1, j + 1).tieneMina()) {
                res++;
            }
        }
        if (validar(i, j - 1)) {
            if (getCelda(i, j - 1).tieneMina()) {
                res++;
            }
        }
        if (validar(i, j + 1)) {
            if (getCelda(i, j + 1).tieneMina()) {
                res++;
            }
        }
        if (validar(i + 1, j - 1)) {
            if (getCelda(i + 1, j - 1).tieneMina()) {
                res++;
            }
        }
        if (validar(i + 1, j)) {
            if (getCelda(i + 1, j).tieneMina()) {
                res++;
            }
        }
        if (validar(i + 1, j + 1)) {
            if (getCelda(i + 1, j + 1).tieneMina()) {
                res++;
            }
        }
        if (res != 0) {
            getCelda(i, j).setMinaCercas(res);
        }

    }

    //verifica que la posicion es es valida
    // no sea una posicion que no tenga el tablero
    public boolean validar(int i, int j) {
        boolean res = true;
        if (i < 0 || j < 0 || i > tablero.length - 1 || j > tablero[i].length - 1) {
            res = false;
        }
        return res;
    }
    public int getSizeX(){
        return tablero.length;
    }
    
    public int getSizey(){
        return tablero[0].length;
    }
}
