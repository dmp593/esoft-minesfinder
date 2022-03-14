import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesFinder extends JFrame {

    private JPanel painelPrincipal;
    private JButton btnJogoMedio;
    private JButton btnSair;
    private JButton btnJogoFacil;
    private JButton btnJogoDificil;
    private JLabel lblJogadorFacil;
    private JLabel lblPontuacaoFacil;
    private JLabel lblJogadorMedio;
    private JLabel lblPontuacaoMedio;
    private JLabel lblJogadorDificil;
    private JLabel lblPontuacaoDificil;

    private TabelaRecordes recordesFacil;
    private TabelaRecordes recordesMedio;
    private TabelaRecordes recordesDificil;

    public MinesFinder(String title) {
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelPrincipal);

        recordesFacil = new TabelaRecordes();
        recordesMedio = new TabelaRecordes();
        recordesDificil = new TabelaRecordes();

        if (lerRecordes()) {
            atualizarRecordeFacil(recordesFacil);
            atualizarRecordeMedio(recordesMedio);
            atualizarRecordeDificil(recordesDificil);
        }

        recordesFacil.addTabelaRecordesListener(this::recordeFacilAtualizado);
        recordesMedio.addTabelaRecordesListener(this::recordeMedioAtualizado);
        recordesDificil.addTabelaRecordesListener(this::recordeDificilAtualizado);

        btnJogoFacil.addActionListener(this::btnJogoFacilActionPerformed);
        btnJogoMedio.addActionListener(this::btnJogoMedioActionPerformed);
        btnJogoDificil.addActionListener(this::btnJogoDificilActionPerformed);

        btnSair.addActionListener(this::btnSairActionPerformed);

        pack();
    }

    private void btnJogoFacilActionPerformed(ActionEvent e) {
        new JanelaDeJogo(new CampoMinado(9, 9, 3), recordesFacil);
    }

    private void btnJogoMedioActionPerformed(ActionEvent e) {
        new JanelaDeJogo(new CampoMinado(16, 16, 40), recordesMedio);
    }

    private void btnJogoDificilActionPerformed(ActionEvent e) {
        new JanelaDeJogo(new CampoMinado(16, 30, 90), recordesDificil);
    }

    private void btnSairActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void atualizarRecordeFacil(TabelaRecordes recorde) {
        this.lblJogadorFacil.setText(recorde.getNomeJogador());
        this.lblPontuacaoFacil.setText(String.valueOf(recorde.getDuracaoEmSegundos()));
    }

    private void atualizarRecordeMedio(TabelaRecordes recorde) {
        this.lblJogadorMedio.setText(recorde.getNomeJogador());
        this.lblPontuacaoMedio.setText(String.valueOf(recorde.getDuracaoEmSegundos()));
    }

    private void atualizarRecordeDificil(TabelaRecordes recorde) {
        this.lblJogadorDificil.setText(recorde.getNomeJogador());
        this.lblPontuacaoDificil.setText(String.valueOf(recorde.getDuracaoEmSegundos()));
    }

    private void recordeFacilAtualizado(TabelaRecordes recorde) {
        atualizarRecordeFacil(recorde);
        guardarRecordes();
    }

    private void recordeMedioAtualizado(TabelaRecordes recorde) {
        atualizarRecordeMedio(recorde);
        guardarRecordes();
    }

    private void recordeDificilAtualizado(TabelaRecordes recorde) {
        atualizarRecordeDificil(recorde);
        guardarRecordes();
    }

    private void guardarRecordes() {
        try {
            var f = new File(System.getProperty("user.home") + File.separator + "minesfinder.recordes");
            var oos = new ObjectOutputStream(new FileOutputStream(f));

            oos.writeObject(recordesFacil);
            oos.writeObject(recordesMedio);
            oos.writeObject(recordesDificil);

            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean lerRecordes() {
        var f = new File(System.getProperty("user.home") + File.separator + "minesfinder.recordes");
        if (!f.canRead()) return false;

        try {
            var ois = new ObjectInputStream(new FileInputStream(f));

            recordesFacil = (TabelaRecordes) ois.readObject();
            recordesMedio = (TabelaRecordes) ois.readObject();
            recordesDificil = (TabelaRecordes) ois.readObject();

            ois.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static void main(String[] args) {
        new MinesFinder("Mines Finder").setVisible(true);
    }

}
