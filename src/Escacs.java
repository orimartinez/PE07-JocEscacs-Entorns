import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Escacs {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        menuInicial(sc);
    }

    static boolean menuInicial(Scanner sc) {
        boolean sortir = false;

        while (!sortir) {
            System.out.println("1. Iniciar Partida");
            System.out.println("2. Sortir del programa");

            int opcioMenu = sc.nextInt();

            switch (opcioMenu) {
                case 1:
                    metodeJoc(sc);
                    break;

                case 2:
                    System.out.println("Sortint del programa");
                    sortir = true;
                    break;

                default:
                    System.out.println("Error, torna a seleccionar una opció");
            }
        }
        return sortir;
    }

    static void metodeJoc(Scanner sc) {

        introdueixJugadors(sc);

        char[][] tauler = crearTauler();
        inicialitzarPeces(tauler);

        // Gestió dels torns
        boolean tornBlancs = true;
        boolean partidaEnCurs = true;

        // Historial de moviments
        ArrayList<String> historialMoviments = new ArrayList<>();

        while (partidaEnCurs) {

            if (tornBlancs) {
                System.out.println("Torn de les BLANQUES");
            } else {
                System.out.println("Torn de les NEGRES");
            }

            sc.nextLine();
            mostrarTauler(tauler);

            System.out.println("Introdueix el moviment (ex: a2 a4): ");
            String moviment = sc.nextLine();

            int[] c = convertirMoviment(moviment);
            char peca = tauler[c[0]][c[1]];

            if (!pecaDelTorn(peca, tornBlancs)) {
                System.out.println("No pots moure aquesta peça!");
                System.out.println("Prem ENTER per continuar");
                sc.nextLine();
                continue;
            }

            boolean movimentValid = false;

            switch (Character.toUpperCase(peca)) {
                case 'P':
                    movimentValid = movimentPeoValid(tauler, c, tornBlancs);
                    break;
                case 'T':
                    movimentValid = movimentTorreValid(tauler, c, tornBlancs);
                    break;
                case 'C':
                    movimentValid = movimentCavallValid(tauler, c, tornBlancs);
                    break;
                case 'A':
                    movimentValid = movimentAlfilValid(tauler, c, tornBlancs);
                    break;
                case 'Q':
                    movimentValid = movimentValidReina(tauler, c, tornBlancs);
                    break;
                case 'K':
                    movimentValid = movimentValidRei(tauler, c, tornBlancs);
                    break;
                default:
                    System.out.println("Aquesta peça encara no existeix");
                    sc.nextLine();
                    continue;
            }

            if (!movimentValid) {
                System.out.println("Aquest moviment no està permès!");
                sc.nextLine();
                continue;
            }

            String jugador = tornBlancs ? "Blanques" : "Negres";
            historialMoviments.add(jugador + ": " + moviment);

            mourePeca(tauler, c);

            partidaEnCurs = !acabarPartida(sc);
            tornBlancs = !tornBlancs;

            mostrarHistorial(historialMoviments);
        }
    }

    static void introdueixJugadors(Scanner sc) {
        sc.nextLine();

        System.out.println("Introdueix el nom del jugador 1 (blanques)");
        String jugador1 = sc.nextLine();

        System.out.println("Introdueix el nom del jugador 2 (negres)");
        String jugador2 = sc.nextLine();

        System.out.println(
                "Les blanques serà el jugador " + jugador1 +
                        " i les negres " + jugador2);
    }

    // Crear el taulell de joc
    static char[][] crearTauler() {
        char[][] tauler = new char[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tauler[i][j] = '·';
            }
        }
        return tauler;
    }

    static void inicialitzarPeces(char[][] tauler) {

        for (int i = 0; i < 8; i++) {
            tauler[1][i] = 'p';
            tauler[6][i] = 'P';
        }

        tauler[0][0] = 't';
        tauler[0][7] = 't';
        tauler[7][0] = 'T';
        tauler[7][7] = 'T';

        tauler[0][1] = 'c';
        tauler[0][6] = 'c';
        tauler[7][1] = 'C';
        tauler[7][6] = 'C';

        tauler[0][2] = 'a';
        tauler[0][5] = 'a';
        tauler[7][2] = 'A';
        tauler[7][5] = 'A';

        tauler[0][3] = 'q';
        tauler[7][3] = 'Q';

        tauler[0][4] = 'k';
        tauler[7][4] = 'K';
    }

    static void mostrarTauler(char[][] tauler) {
        System.out.println("= = a b c d e f g h = =");
        System.out.println("-----------------------");

        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " - ");
            for (int j = 0; j < 8; j++) {
                System.out.print(tauler[i][j] + " ");
            }
            System.out.println("- " + (8 - i));
        }

        System.out.println("-----------------------");
        System.out.println("= = a b c d e f g h = =");
    }

    static int[] convertirMoviment(String moviment) {
        int[] c = new int[4];

        char columOrigen = moviment.charAt(0);
        char filaOrigen = moviment.charAt(1);
        char columDesti = moviment.charAt(3);
        char filaDesti = moviment.charAt(4);

        c[0] = 8 - (filaOrigen - '0');
        c[1] = columOrigen - 'a';
        c[2] = 8 - (filaDesti - '0');
        c[3] = columDesti - 'a';

        return c;
    }

    static void mourePeca(char[][] tauler, int[] c) {
        tauler[c[2]][c[3]] = tauler[c[0]][c[1]];
        tauler[c[0]][c[1]] = '·';
    }

    static boolean pecaDelTorn(char peca, boolean tornBlancs) {
        if (peca == '·') {
            return false;
        }

        if (tornBlancs) {
            return Character.isUpperCase(peca);
        }
        return Character.isLowerCase(peca);
    }

    // Majúscules --> Blanques
    // Minúscules --> Negres

    // === Taula de peces:
    // · P/p --> Peó
    // · C/c --> Cavall
    // · A/a --> Alfil
    // · T/t --> Torre
    // · K/k --> Rei
    // · Q/q --> Reina

    // Mètode per verificar els moviments del Peó

    static boolean movimentPeoValid(char[][] tauler, int[] c, boolean tornBlancs) {

        int filaO = c[0];
        int colO = c[1];
        int filaD = c[2];
        int colD = c[3];

        // Per verificar que les blanques únicamnet puguin pujar i les negres baixar
        int direccio;

        // Com que l'array està expressat diferent del tauler, la fila de dalt de tot és
        // la primera, per tant, si les blanques volen anar cap a dalt, estan restant i
        // per això és negativa la direcció

        if (tornBlancs) {
            direccio = -1; // blanques
        } else {
            direccio = 1; // negres
        }

        // Verificar si avança 1 casella
        if (colO == colD && filaD == filaO + direccio && tauler[filaD][colD] == '·') {
            return true;
        }

        // Verificar si avança 2 caselles (únicament al primer moviment)
        if (colO == colD) {
            if (tornBlancs && filaO == 6 && filaD == 4 &&
                    tauler[5][colO] == '·' && tauler[4][colO] == '·') {
                return true;
            }

            if (!tornBlancs && filaO == 1 && filaD == 3 &&
                    tauler[2][colO] == '·' && tauler[3][colO] == '·') {
                return true;
            }
        }

        // Verificar per matar una peca en diagonal
        if (Math.abs(colD - colO) == 1 && filaD == filaO + direccio) {
            char desti = tauler[filaD][colD];
            if (desti != '·' && Character.isUpperCase(desti) != tornBlancs) {
                return true;
            }
        }

        return false;
    }

    // Mètode per verificar els moviments de les torres
    static boolean movimentTorreValid(char[][] tauler, int[] c, boolean tornBlancs) {

        int filaO = c[0];
        int colO = c[1];
        int filaD = c[2];
        int colD = c[3];

        // Verificar que es mogui en línia recta
        if (filaO != filaD && colO != colD) {
            return false;
        }

        // Verificar que es mogui horitzontal
        if (filaO == filaD) {

            int pas; // Variable per saber la direcció a la que es mou la torre (positiu o negatiu)

            if (colD > colO) {
                pas = 1;
            } else {
                pas = -1;
            }

            for (int col = colO + pas; col != colD; col += pas) {
                if (tauler[filaO][col] != '·') {
                    return false;
                }
            }
        }

        // Verificar que es mogui vertical
        if (colO == colD) {
            int pas;
            // La diferència entre la variable "direcció" i "pas" és que la direcció és el
            // que ens determina si un peó pot pujar o baixar en funció del color,
            // ja que, els peons negres no poden pujar i els blancs no poden baixar
            // en canvi, la variable pas ens diu cap a quina direcció es mou la torre
            // (ja que aquestes poden pujar i baixar on vulguin)

            if (filaD > filaO) {
                pas = 1;
            } else {
                pas = -1;
            }
            // Comprovar que durant el camí que fa la torre, no hi hagi cap peça (alida o
            // enemiga)
            for (int fila = filaO + pas; fila != filaD; fila += pas) {
                if (tauler[fila][colO] != '·') {
                    return false;
                }
            }
        }

        // Comprovar casella on anirà (buit o peça contrària)
        char desti = tauler[filaD][colD];
        if (desti == '·') {
            return true;
        }

        return Character.isUpperCase(desti) != tornBlancs;
    }

    static boolean movimentCavallValid(char[][] tauler, int[] c, boolean tornBlancs) {

        int filaO = c[0];
        int colO = c[1];
        int filaD = c[2];
        int colD = c[3];

        // Per verificar si el moviment del cavall és correcte, ho farem
        // mitjançant el càlcul de la diferència de files i columnes.

        // Com que el cavall sempre es mou dues caselles i després una
        // perpendicular, al calcular la diferència de files i
        // columnes hauria de ser sempre 2 i 1.

        int difFila = Math.abs(filaD - filaO);
        int difCol = Math.abs(colD - colO);

        // Verifiquem que la diferència sigui correcte (2 files i 1 columna o 2 columnes
        // i 1 fila)
        if ((difFila == 2 && difCol == 1) || (difFila == 1 && difCol == 2)) {

            char desti = tauler[filaD][colD];

            // A part, perquè sigui un moviment vàlid, el cavall només pot anar a
            // parar a una casella buida o a una peça enemiga, per això mirem:

            // Mirem si la casella està buidsa
            if (desti == '·') {
                return true;
            }

            // Mirem si fa una captura enemiga
            return Character.isUpperCase(desti) != tornBlancs;

            // Això el que mira és que si tornBlancs és true, vol dir que li toca
            // a les blanques i, per tant, el cavall que es mou és majúscula.
            // Això vol dir que només pot menjar-se a peces minúscules, verificant així
            // que capturi una peça de l'enemic.
        }

        return false;
    }

    static boolean movimentAlfilValid(char[][] tauler, int[] c, boolean tornBlancs) {

        int filaO = c[0];
        int colO = c[1];
        int filaD = c[2];
        int colD = c[3];

        // Verifiquem que sigui diagonal
        if (Math.abs(filaD - filaO) != Math.abs(colD - colO)) {
            return false;
        }

        int pasFila;
        int pasCol;

        // Per saber la direcció que porta l'alfil (fila)
        if (filaD > filaO) {
            pasFila = 1;
        } else {
            pasFila = -1;
        }

        // Per saber la direcció que porta l'alfil (columna)
        if (colD > colO) {
            pasCol = 1;
        } else {
            pasCol = -1;
        }

        int fila = filaO + pasFila;
        int col = colO + pasCol;

        // Verificar que durant el camí, no passa per sobre cap peça
        while (fila != filaD && col != colD) {
            if (tauler[fila][col] != '·') {
                return false;
            }
            fila += pasFila;
            col += pasCol;
        }

        char desti = tauler[filaD][colD];

        // Verificar que sigui una casella buida
        if (desti == '·') {
            return true;
        }

        // Per verificar que no es capturi una peça del teu propi color
        if (Character.isUpperCase(desti) != tornBlancs) {
            return true;
        }

        return false;
    }

    static boolean movimentValidReina(char[][] tauler, int[] c, boolean tornBlancs) {

        // En aquest cas, com que el moviment de la reina és una fusió entre la torre i
        // l'alfil, podem aprofitar el codi

        if (movimentTorreValid(tauler, c, tornBlancs)) {
            return true;
        }

        if (movimentAlfilValid(tauler, c, tornBlancs)) {
            return true;
        }

        return false;
    }

    static boolean movimentValidRei(char[][] tauler, int[] c, boolean tornBlancs) {

        int filaO = c[0];
        int colO = c[1];
        int filaD = c[2];
        int colD = c[3];

        // Calculem la diferència de files i de columnes
        int difFila = Math.abs(filaD - filaO);
        int difCol = Math.abs(colD - colO);

        // Verifiquem que el rei únicament es pugui moure una casella
        if (difFila > 1 || difCol > 1) {
            return false;
        }

        // Verifiquem que el rei no es quedi al mateix lloc
        if (difFila == 0 && difCol == 0) {
            return false;
        }

        // Casella del destí
        char desti = tauler[filaD][colD];

        if (desti == '·') {
            return true;
        }

        // Verificar que es pugui dur a terme la captura sempre i quant sigui una peça
        // enemiga
        return Character.isUpperCase(desti) != tornBlancs;
    }

    static boolean acabarPartida(Scanner sc) {
        boolean acabarPartida = false;
        boolean entradaCorrecta = false;

        while (!entradaCorrecta) {
            System.out.println("Vols acabar la partida? (s/n)");
            // Guardem el char, el passem a minuscula i seleccionem unicament el de la
            // posició 0 (la p)
            char resposta = sc.next().toLowerCase().charAt(0);

            if (resposta == 's') {
                acabarPartida = true;
                entradaCorrecta = true;
            } else if (resposta == 'n') {
                acabarPartida = false;
                entradaCorrecta = true;
            } else {
                System.out.println("Resposta incorrecta. Escriu 's' o 'n'.");
            }
        }

        return acabarPartida;
    }

    // Mètode per mostrar l'historial de moviments al final de la partida
    static void mostrarHistorial(ArrayList<String> historial) {
        System.out.println("===== HISTORIAL DE MOVIMENTS =====");
        for (int i = 0; i < historial.size(); i++) {
            System.out.println((i + 1) + ". " + historial.get(i));
        }
        System.out.println("=================================");
    }

}
