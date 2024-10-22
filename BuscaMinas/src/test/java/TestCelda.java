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
import rodrocompany.model.celda;

/**
 *
 * @author win
 */
public class TestCelda {
    celda celda;
    
    public TestCelda() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
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
    public void testIniciarCelda() {
        assertFalse(celda.esVisible()); //La celda debería estar oculta al inicializarse
        assertFalse(celda.tieneMina()); //La celda no debería tener una mina al inicializarse
        assertFalse(celda.tieneBandera()); //La celda no debería estar marcada con bandera al inicializarse
    }
    

    @Test
    public void testCeldaRevelar() {
        celda.revelar();
        assertTrue(celda.esVisible()); //La celda debería estar revelada después de llamar a reveal()
    }

    @Test
    public void testMarcas() {
        celda.Marcar();
        assertTrue(celda.tieneBandera()); //La celda debería estar marcada con bandera después de llamar a marcar()
        celda.Desmarcar();
        assertFalse(celda.tieneBandera()); //La bandera debería ser removida al llamar nuevamente a desmarcar()
    }

    @Test
    public void testMina() {
        celda.setMina(true);
        assertTrue(celda.tieneMina()); //La celda debería tener una mina después de llamar a setMine(true)
    }
}
