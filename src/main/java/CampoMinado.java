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
    private long duracaoJogoEmMilisegundos;

    public CampoMinado(int altura, int largura, int nrMinas) {
        this.altura = altura;
        this.largura = largura;
        this.nrMinas = nrMinas;

        this.minas = new boolean[altura][largura];
        this.estado = new int[altura][largura];

        this.primeiraJogada = true;
        this.jogoTerminado = false;
        this.jogoDerrotado = false;

        for (var linha = 0; linha < altura; ++linha) {
            for (var coluna = 0; coluna < largura; ++coluna) {
                estado[linha][coluna] = TAPADO;
            }
        }
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public int getEstadoQuadricula(int linha, int coluna) {
        return estado[linha][coluna];
    }

    public boolean hasMina(int linha, int coluna) {
        return minas[linha][coluna];
    }

    public boolean isJogoTerminado() {
        return jogoTerminado;
    }

    public boolean isJogoDerrotado() {
        return jogoDerrotado;
    }

    public long getDuracaoJogoEmMilisegundos() {
        return jogoTerminado ? duracaoJogoEmMilisegundos : System.currentTimeMillis() - instanteInicioJogo;
    }

    private void iniciarJogo(int linha, int coluna) {
        colocarMinas(linha, coluna);
        primeiraJogada = false;
        instanteInicioJogo = System.currentTimeMillis();
    }

    private void pararJogo(boolean vitoria) {
        jogoTerminado = true;
        jogoDerrotado = !vitoria;
        guardarDuracaoJogo();
    }

    private void guardarDuracaoJogo() {
        duracaoJogoEmMilisegundos = System.currentTimeMillis() - instanteInicioJogo;
    }

    public void revelarQuadricula(int linha, int coluna) {
        if (jogoTerminado || estado[linha][coluna] < TAPADO) {
            return;
        }

        if (primeiraJogada) {
            iniciarJogo(linha, coluna);
        }

        if (hasMina(linha, coluna)) {
            estado[linha][coluna] = REBENTADO;
            pararJogo(false);
            return;
        }

        estado[linha][coluna] = contarMinasVizinhas(linha, coluna);
        if (estado[linha][coluna] == VAZIO) {
            revelarQuadriculasVizinhas(linha, coluna);
        }

        if (isVitoria()) {
            pararJogo(true);
        }
    }

    private void colocarMinas(int exceptoLinha, int exceptoColuna) {
        var aleatorio = new Random();

        var linha = -1;
        var coluna = -1;

        for (var i = 0; i < nrMinas; ++i) {
            do {
                linha = aleatorio.nextInt(altura);
                coluna = aleatorio.nextInt(largura);
            } while (minas[linha][coluna] || (linha == exceptoLinha && coluna == exceptoColuna));
            minas[linha][coluna] = true;
        }
    }

    private int contarMinasVizinhas(int linha, int coluna) {
        var nrMinasVizinhas = 0;

        for (var linha0 = Math.max(0, linha - 1); linha0 < Math.min(altura, linha + 2); ++linha0) {
            for (var coluna0 = Math.max(0, coluna - 1); coluna0 < Math.min(largura, coluna + 2); ++coluna0) {
                if (hasMina(linha0, coluna0)) {
                    ++nrMinasVizinhas;
                }
            }
        }

        return nrMinasVizinhas;
    }

    private void revelarQuadriculasVizinhas(int linha, int coluna) {
        for (var linha0 = Math.max(0, linha - 1); linha0 < Math.min(altura, linha + 2); ++linha0) {
            for (var coluna0 = Math.max(0, coluna - 1); coluna0 < Math.min(largura, coluna + 2); ++coluna0) {
                if (! hasMina(linha0, coluna0)) {
                    revelarQuadricula(linha0, coluna0);
                }
            }
        }
    }

    private boolean isVitoria() {
        for (int linha = 0; linha < altura; ++linha) {
            for (var coluna = 0 ; coluna < largura; ++coluna) {
                if (!minas[linha][coluna] && estado[linha][coluna] >= TAPADO) {
                    return false;
                }
            }
        }
        return true;
    }

    public void marcarComoTendoMina(int linha, int coluna) {
        if (this.estado[linha][coluna] == TAPADO || this.estado[linha][coluna] == DUVIDA) {
            this.estado[linha][coluna] = MARCADO;
        }
    }

    public void marcarComoSuspeita(int linha, int coluna) {
        if (this.estado[linha][coluna] == TAPADO || this.estado[linha][coluna] == MARCADO) {
            this.estado[linha][coluna] = DUVIDA;
        }
    }

    public void desmarcarQuadricula(int linha, int coluna) {
        if (this.estado[linha][coluna] == DUVIDA || this.estado[linha][coluna] == MARCADO) {
            this.estado[linha][coluna] = TAPADO;
        }
    }
}
