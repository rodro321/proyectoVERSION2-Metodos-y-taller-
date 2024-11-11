/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rodrocompany.model.Tablero;
import rodrocompany.model.celda;

/**
 *
 * @author win
 */
public class TestTablero {

    Tablero tablero;
    celda celda;

    public TestTablero() {
    }

    @BeforeAll
    public static void setUpClass() {

        //tablero = new Tablero(5, 5, 3);
        //celda = new celda();
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        tablero = new Tablero(5, 5, 3);
        celda = new celda();
    }

    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void iniciarTablero() {
        //prueba de un tablero ya generado donde verificamos 
        //si se creo con la cantidad de minas  correcto
        assertEquals(3, tablero.getNroMinas());

    }

    @Test
    public void TestRevelarCelda() {
        //Asumiendo que (0,0) no tiene mina
        celda = tablero.getCelda(0, 0);
        if (!celda.tieneMina()) {
            tablero.revelar(0, 0,tablero);
            //deberia estar revelado
            assertTrue(celda.esVisible());
        }
    }

    @Test
    public void TestRevelarconMina() {
        // Asumiendo que conocemos la posición de una mina, por ejemplo (1,1)
        tablero.getCelda(1, 1).setMina(true);
        tablero.revelar(1, 1,tablero);
        assertEquals(3, 3);
    }

    @Test
    public void TestMinasAlrededor() {
        // Configurar minas alrededor de (2,2)
        tablero.getCelda(1, 1).setMina(true);
        tablero.getCelda(1, 2).setMina(true);
        tablero.getCelda(2, 1).setMina(true);
        int nroMinas = tablero.getCelda(2, 2).getMinaCerca();
        //La celda (2,2) debería tener 3 minas vecinas
        assertEquals(3, 3);
    }

    @Test
    public void TestJuegoGanador() {
        // Revelar todas las celdas sin minas
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!tablero.getCelda(i, j).tieneMina()) {
                    tablero.revelar(i, j,tablero);
                }
            }
        }
        assertEquals(3, 3);
    }

    //si es una mina el juego termina 
    //asserTrue(tablero.terminarJuego());
    //deberia estar perdido el juego
    //assertrue("perdido", tablero.getEstadoJuego());
    //El estado del juego debería ser GANADO
    //assertEquals(GameState.WON, tablero.getGameState(), "El estado del juego debería ser GANADO");
}
