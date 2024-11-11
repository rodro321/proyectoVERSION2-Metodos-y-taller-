package rodrocompany.model;

import controller.controller;
import view.FrameTablero;


public class BuscaMinas {

    /*3 tamaños 10X8 10 minas  
                18x14  40 minas
                24x20 99 minas 
     */

    public static void main(String[] args) {
        System.out.println("BUSCA MINAS");

    Modelo model = new Modelo();
    FrameTablero view= new FrameTablero(8, 10);
    controller control = new controller(view,model);
    view.setVisible(true);
    view.setLocationRelativeTo(null);
    }

}
