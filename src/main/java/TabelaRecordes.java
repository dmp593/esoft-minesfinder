import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TabelaRecordes implements Serializable {
    private String nomeJogador;
    private long duracaoEmSegundos;

    private transient List<TabelaRecordesListener> listeners;

    public TabelaRecordes() {
        this.nomeJogador = "Anónimo";
        this.duracaoEmSegundos = Long.MAX_VALUE;

        this.listeners = new ArrayList<>();
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public long getDuracaoEmSegundos() {
        return duracaoEmSegundos;
    }

    public void addTabelaRecordesListener(TabelaRecordesListener listener) {
        if (listeners == null) {
            listeners = new LinkedList<>();
        }

        if (! listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    public void removeTabelaRecordesListener(TabelaRecordesListener listener) {
        if (listeners == null) return;
        this.listeners.remove(listener);
    }

    public void setRecorde(String nomeJogador, long duracaoEmSegundos) {
        if (duracaoEmSegundos < this.duracaoEmSegundos) {
            this.nomeJogador = nomeJogador;
            this.duracaoEmSegundos = duracaoEmSegundos;

            notifyRecordesAtualizados();
        }
    }

    private void notifyRecordesAtualizados() {
        if (listeners == null) return;

        for (TabelaRecordesListener listener : listeners) {
            listener.recordesAtualizados(this);
        }
    }
}
