/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teszt;

import gyak2.PTEsResztvevo;
import gyak2.Rendezveny;
import gyak2.Resztvevo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hallgato
 */
public class JubileumTeszt {

    private final String CIM = "cim";
    private final String IDOPONT = "dátum";
    private final int JEGY_AR = 1000;
    private final double KEDVEZMENY_SZAZALEK = 10;
    
    public JubileumTeszt() {
    }
    
    private Rendezveny rendezveny;
    @Before
    public void setUp() {
        rendezveny = new Rendezveny(CIM, IDOPONT, JEGY_AR);
        PTEsResztvevo.setKedvezmenySzazalek(KEDVEZMENY_SZAZALEK);
    }

    @Test
    public void rendezvenyTeszt() {
        //feltételezés: nincs senki, nincs bevétel
        assertTrue(rendezveny.getResztvevokSzama() == 0);
        assertEquals(rendezveny.getBevetel(), 0);
        
        Resztvevo resztvevo = new Resztvevo("nev");
        resztvevo.penztKap(1500);
        
        rendezveny.resztVesz(resztvevo);
        
        // feltételezés: nem igaz, hogy nincs bevétel
        assertFalse(rendezveny.getBevetel() == 0);
        
        // feltételezés: részt tud venni
        assertEquals(rendezveny.getResztvevokSzama(), 1);
        assertEquals(rendezveny.getBevetel(), JEGY_AR);
       
        rendezveny.resztVesz(resztvevo);
        // feltételezés: nem tud részt venni
        
        assertFalse(rendezveny.getResztvevokSzama() == 2);
        
        resztvevo = new PTEsResztvevo("nev", "azonosito");
        resztvevo.penztKap(900);
        
        //feltételezés: be tud menni
        rendezveny.resztVesz(resztvevo);
        assertTrue(rendezveny.getResztvevokSzama() == 2);
        assertEquals(rendezveny.getBevetel(), 1900);
    }
    
    @Test
    public void ResztvevoTeszt() {
        Resztvevo resztvevo = new Resztvevo("nev");
        resztvevo.penztKap(1500);
        Rendezveny rendezveny1 = new Rendezveny("cim", "datum", JEGY_AR);
        Rendezveny rendezveny2 = new Rendezveny("cim", "datum", JEGY_AR);
        
        // feltételezés: részt vesz az elsőn
        rendezveny1.resztVesz(resztvevo);
        
        assertTrue(rendezveny1.getResztvevokSzama() == 1);
        assertEquals(resztvevo.getRendezvenyek().size(), 1);
        
        resztvevo.penztKap(JEGY_AR);
        // feltételezés: be tud lépni a másodikra
        assertTrue(resztvevo.belephet(rendezveny2));
        rendezveny2.resztVesz(resztvevo);
        
        assertTrue(resztvevo.getRendezvenyek().size() == 2);
        
        assertEquals(rendezveny2.getResztvevokSzama(), 1);
    }
}
