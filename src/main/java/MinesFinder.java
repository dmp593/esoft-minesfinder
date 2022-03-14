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

    private Recorde recordeFacil;
    private Recorde recordeMedio;
    private Recorde recordeDificil;

    public MinesFinder(String title) {
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelPrincipal);

        recordeFacil = new Recorde();
        recordeMedio = new Recorde();
        recordeDificil = new Recorde();

        if (lerRecordes()) {
            atualizarRecordeFacil(recordeFacil);
            atualizarRecordeMedio(recordeMedio);
            atualizarRecordeDificil(recordeDificil);
        }

        recordeFacil.adicionarRecordeListener(this::recordeFacilAtualizado);
        recordeMedio.adicionarRecordeListener(this::recordeMedioAtualizado);
        recordeDificil.adicionarRecordeListener(this::recordeDificilAtualizado);

        btnJogoFacil.addActionListener(this::btnJogoFacilActionPerformed);
        btnJogoMedio.addActionListener(this::btnJogoMedioActionPerformed);
        btnJogoDificil.addActionListener(this::btnJogoDificilActionPerformed);

        btnSair.addActionListener(this::btnSairActionPerformed);

        pack();
    }

    private void btnJogoFacilActionPerformed(ActionEvent e) {
        new JanelaDeJogo(new CampoMinado(9, 9, 10), recordeFacil);
    }

    private void btnJogoMedioActionPerformed(ActionEvent e) {
        new JanelaDeJogo(new CampoMinado(16, 16, 40), recordeMedio);
    }

    private void btnJogoDificilActionPerformed(ActionEvent e) {
        new JanelaDeJogo(new CampoMinado(16, 30, 90), recordeDificil);
    }

    private void btnSairActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void atualizarRecordeFacil(Recorde recorde) {
        this.lblJogadorFacil.setText(recorde.getNomeJogador());
        this.lblPontuacaoFacil.setText(String.valueOf(recorde.getDuracaoEmSegundos()));
    }

    private void atualizarRecordeMedio(Recorde recorde) {
        this.lblJogadorMedio.setText(recorde.getNomeJogador());
        this.lblPontuacaoMedio.setText(String.valueOf(recorde.getDuracaoEmSegundos()));
    }

    private void atualizarRecordeDificil(Recorde recorde) {
        this.lblJogadorDificil.setText(recorde.getNomeJogador());
        this.lblPontuacaoDificil.setText(String.valueOf(recorde.getDuracaoEmSegundos()));
    }

    private void recordeFacilAtualizado(Recorde recorde) {
        atualizarRecordeFacil(recorde);
        guardarRecordes();
    }

    private void recordeMedioAtualizado(Recorde recorde) {
        atualizarRecordeMedio(recorde);
        guardarRecordes();
    }

    private void recordeDificilAtualizado(Recorde recorde) {
        atualizarRecordeDificil(recorde);
        guardarRecordes();
    }

    private void guardarRecordes() {
        try {
            var f = new File(System.getProperty("user.home") + File.separator + "minesfinder.recordes");
            var oos = new ObjectOutputStream(new FileOutputStream(f));

            oos.writeObject(recordeFacil);
            oos.writeObject(recordeMedio);
            oos.writeObject(recordeDificil);

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

            recordeFacil = (Recorde) ois.readObject();
            recordeMedio = (Recorde) ois.readObject();
            recordeDificil = (Recorde) ois.readObject();

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
