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

    public CampoMinado(int largura, int altura, int nrMinas) {
        this.largura = largura;
        this.altura = altura;
        this.nrMinas = nrMinas;

        this.minas = new boolean[largura][altura];
        this.estado = new int[largura][altura];
    }

    public int getEstadoQuadricula(int x, int y) {
        return estado[x][y];
    }

    public boolean hasMina(int x, int y) {
        return minas[x][y];
    }
}
