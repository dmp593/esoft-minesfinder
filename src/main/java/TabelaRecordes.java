public class TabelaRecordes {
    private String nomeJogador;
    private long duracaoEmSegundos;

    public TabelaRecordes() {
        setRecorde("Anónimo", Long.MAX_VALUE);
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public long getDuracaoEmSegundos() {
        return duracaoEmSegundos;
    }

    public void setRecorde(String nomeJogador, long duracaoEmSegundos) {
        if (duracaoEmSegundos < this.duracaoEmSegundos) {
            this.nomeJogador = nomeJogador;
            this.duracaoEmSegundos = duracaoEmSegundos;
        }
    }
}
