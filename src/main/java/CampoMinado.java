import java.util.Random;

public class CampoMinado {

    public static final int VAZIO = 0;
    /* de 1 a 8 são o número de minas à volta */
    public static final int TAPADO = 9;
    public static final int DUVIDA = 10;
    public static final int MARCADO = 11;
    public static final int REBENTADO = 12;

    private boolean[][] minas;
    private int[][] estado;

    private int largura;
    private int altura;
    private int nrMinas;

    private boolean primeiraJogada;
    private boolean jogoTerminado;
    private boolean jogoDerrotado;

    private long instanteInicioJogo;
    private long duracaoJogo;

    public CampoMinado(int largura, int altura, int nrMinas) {
        this.largura = largura;
        this.altura = altura;
        this.nrMinas = nrMinas;

        this.minas = new boolean[altura][largura];
        this.estado = new int[altura][largura];

        this.primeiraJogada = true;
        this.jogoTerminado = false;
        this.jogoDerrotado = false;

        for (var x = 0; x < altura; ++x) {
            for (var y = 0; y < largura; ++y) {
                estado[x][y] = TAPADO;
            }
        }
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public int getEstadoQuadricula(int x, int y) {
        return estado[x][y];
    }

    public boolean hasMina(int x, int y) {
        return minas[x][y];
    }

    public boolean isJogoTerminado() {
        return jogoTerminado;
    }

    public boolean isJogoDerrotado() {
        return jogoDerrotado;
    }

    public long getDuracaoJogo() {
        return jogoTerminado ? duracaoJogo : System.currentTimeMillis() - instanteInicioJogo;
    }

    public void revelarQuadricula(int x, int y) {
        if (jogoTerminado || estado[x][y] < TAPADO) {
            return;
        }

        if (primeiraJogada) {
            colocarMinas(x, y);
            primeiraJogada = false;

            instanteInicioJogo = System.currentTimeMillis();
        }

        if (hasMina(x, y)) {
            estado[x][y] = REBENTADO;
            jogoTerminado = true;
            jogoDerrotado = true;

            duracaoJogo = System.currentTimeMillis() - instanteInicioJogo;
            return;
        }

        estado[x][y] = contarMinasVizinhas(x, y);
        if (estado[x][y] == VAZIO) {
            revelarQuadriculasVizinhas(x, y);
        }

        if (isVitoria()) {
            jogoTerminado = true;
            jogoDerrotado = false;

            duracaoJogo = System.currentTimeMillis() - instanteInicioJogo;
        }
    }

    private void colocarMinas(int exceptoX, int exceptoY) {
        var aleatorio = new Random();
        var x = 0;
        var y = 0;

        for (var i = 0; i < nrMinas; ++i) {
            do {
                x = aleatorio.nextInt(altura);
                y = aleatorio.nextInt(largura);
            } while (minas[x][y] || (x == exceptoX && y == exceptoY));
            minas[x][y] = true;
        }
    }

    private int contarMinasVizinhas(int x, int y) {
        var nrMinasVizinhas = 0;

        for (var i = Math.max(0, x - 1); i < Math.min(altura, x + 2); ++i) {
            for (var j = Math.max(0, y - 1); j < Math.min(largura, y + 2); ++j) {
                if (hasMina(i, j)) {
                    ++nrMinasVizinhas;
                }
            }
        }

        return nrMinasVizinhas;
    }

    private void revelarQuadriculasVizinhas(int x, int y) {
        for (var i = Math.max(0, x - 1); i < Math.min(altura, x + 2); ++i) {
            for (var j = Math.max(0, y - 1); j < Math.min(largura, y + 2); ++j) {
                if (! hasMina(i, j)) {
                    revelarQuadricula(i, j);
                }
            }
        }
    }

    private boolean isVitoria() {
        for (int i = 0; i < altura; ++i) {
            for (var j = 0 ; j < largura; ++j) {
                if (!minas[i][j] && estado[i][j] >= TAPADO) {
                    return false;
                }
            }
        }
        return true;
    }

    public void marcarComoTendoMina(int x, int y) {
        if (this.estado[x][y] == TAPADO || this.estado[x][y] == DUVIDA) {
            this.estado[x][y] = MARCADO;
        }
    }

    public void marcarComoSuspeita(int x, int y) {
        if (this.estado[x][y] == TAPADO || this.estado[x][y] == MARCADO) {
            this.estado[x][y] = DUVIDA;
        }
    }

    public void desmarcarQuadricula(int x, int y) {
        if (this.estado[x][y] == DUVIDA || this.estado[x][y] == MARCADO) {
            this.estado[x][y] = TAPADO;
        }
    }
}
