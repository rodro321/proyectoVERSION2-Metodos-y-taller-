
package rodrocompany.model;

public class celda {

    private boolean mina;
    private boolean visible;
    private boolean marcado;
    private int minaCerca;

    public celda() {
        mina = false;
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

    public void revelar() {
        if (!esVisible()) {
            visible = true;
        }
    }

    public void Desmarcar() {
        if (marcado) {
            marcado = false;
        }
    }

    public void Marcar() {
        if (!marcado) {
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
