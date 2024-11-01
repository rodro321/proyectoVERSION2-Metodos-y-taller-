/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rodrocompany.model;

/**
 *
 * @author win
 */
public class celda {

    private boolean mina;
    //boolean libre;
    private boolean visible;
    private boolean marcado;
    private int minaCerca;

    public celda() {
        mina = false;
        //libre = true;
        visible = false;
        marcado = false;
        minaCerca = 0;
    }

    public boolean esVisible() {
        return visible;
    }

    public boolean tieneMina() {
        return mina;
    }

    public boolean tieneBandera() {
        return marcado;
    }

    /**
    public int getMinaCercana() {
        int res;
        if (mina == false && visible == false && marcado == false) {
            res = minaCerca;
        } else {
            res = -1;
        }
        return res;
    }*/
    
    //la celda se vuelve visible
    public void revelar() {
        if (visible == false) {
            visible = true;
        }
    }

    public void Desmarcar() {
        if (marcado == true) {
            marcado = false;
        }
    }

    public void Marcar() {
        if (marcado == false) {
            marcado = true;
        }
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }
    
    public celda getCelda(){
        return this;
    }

    public void setMinaCercas(int nroMinas) {
        minaCerca = nroMinas;
    }
    
    public int getMinaCerca(){
        return minaCerca;
    }

}
