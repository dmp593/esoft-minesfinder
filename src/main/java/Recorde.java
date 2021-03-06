import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Recorde implements Serializable {
    private String nomeJogador;
    private long duracaoEmSegundos;

    private transient List<RecordeListener> listeners;

    public Recorde() {
        this.nomeJogador = "<jogador>";
        this.duracaoEmSegundos = Long.MAX_VALUE;

        this.listeners = new ArrayList<>();
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public long getDuracaoEmSegundos() {
        return duracaoEmSegundos;
    }

    public void adicionarRecordeListener(RecordeListener listener) {
        if (listeners == null) {
            listeners = new LinkedList<>();
        }

        if (! listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    public void removerRecordeListener(RecordeListener listener) {
        if (listeners == null) return;
        this.listeners.remove(listener);
    }

    public void setRecorde(String nomeJogador, long duracaoEmSegundos) {
        if (duracaoEmSegundos < this.duracaoEmSegundos) {
            this.nomeJogador = nomeJogador;
            this.duracaoEmSegundos = duracaoEmSegundos;

            notificarNovoRecorde();
        }
    }

    private void notificarNovoRecorde() {
        if (listeners == null) return;
        listeners.forEach(l -> l.novoRecorde(this));
    }
}
