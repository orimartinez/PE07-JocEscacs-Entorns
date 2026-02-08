import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class EscacsTest {

    @Test
    void testCrearTaulerBuit() {
        char[][] tauler = Escacs.crearTauler();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals('·', tauler[i][j]);
            }
        }
    }

    @Test
    void testInicialitzarPeces() {
        char[][] tauler = Escacs.crearTauler();
        Escacs.inicialitzarPeces(tauler);

        assertEquals('P', tauler[6][0]);
        assertEquals('p', tauler[1][0]);
        assertEquals('K', tauler[7][4]);
        assertEquals('k', tauler[0][4]);
    }

    @Test
    void testConvertirMoviment() {
        int[] c = Escacs.convertirMoviment("a2 a4");

        assertEquals(6, c[0]); // fila origen
        assertEquals(0, c[1]); // columna origen
        assertEquals(4, c[2]); // fila destí
        assertEquals(0, c[3]); // columna destí
    }

    @Test
    void testPecaDelTorn() {
        assertTrue(Escacs.pecaDelTorn('P', true));
        assertFalse(Escacs.pecaDelTorn('p', true));
        assertTrue(Escacs.pecaDelTorn('p', false));
        assertFalse(Escacs.pecaDelTorn('·', true));
    }

    @Test
    void testMourePeca() {
        char[][] tauler = Escacs.crearTauler();
        tauler[6][0] = 'P';

        int[] c = {6, 0, 5, 0};
        Escacs.mourePeca(tauler, c);

        assertEquals('P', tauler[5][0]);
        assertEquals('·', tauler[6][0]);
    }

    @Test
    void testMovimentPeoValid() {
        char[][] tauler = Escacs.crearTauler();
        Escacs.inicialitzarPeces(tauler);

        int[] moviment = {6, 0, 5, 0}; // a2 a3
        assertTrue(Escacs.movimentPeoValid(tauler, moviment, true));
    }

    @Test
    void testMovimentTorreValid() {
        char[][] tauler = Escacs.crearTauler();
        tauler[4][4] = 'T';

        int[] moviment = {4, 4, 4, 7};
        assertTrue(Escacs.movimentTorreValid(tauler, moviment, true));
    }

    @Test
    void testMovimentCavallValid() {
        char[][] tauler = Escacs.crearTauler();
        tauler[4][4] = 'C';

        int[] moviment = {4, 4, 6, 5};
        assertTrue(Escacs.movimentCavallValid(tauler, moviment, true));
    }

    @Test
    void testMovimentAlfilValid() {
        char[][] tauler = Escacs.crearTauler();
        tauler[4][4] = 'A';

        int[] moviment = {4, 4, 6, 6};
        assertTrue(Escacs.movimentAlfilValid(tauler, moviment, true));
    }

    @Test
    void testMovimentReinaValid() {
        char[][] tauler = Escacs.crearTauler();
        tauler[4][4] = 'Q';

        int[] moviment = {4, 4, 4, 6};
        assertTrue(Escacs.movimentValidReina(tauler, moviment, true));
    }

    @Test
    void testMovimentReiValid() {
        char[][] tauler = Escacs.crearTauler();
        tauler[4][4] = 'K';

        int[] moviment = {4, 4, 5, 5};
        assertTrue(Escacs.movimentValidRei(tauler, moviment, true));
    }
}


