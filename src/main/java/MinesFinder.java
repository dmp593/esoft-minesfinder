import javax.swing.*;
import java.awt.event.ActionEvent;

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

        recordesFacil.addTabelaRecordesListener(this::recordesFacilAtualizados);
        recordesMedio.addTabelaRecordesListener(this::recordesMedioAtualizados);
        recordesDificil.addTabelaRecordesListener(this::recordesDificilAtualizados);

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

    private void recordesFacilAtualizados(TabelaRecordes recorde) {
        this.lblJogadorFacil.setText(recorde.getNomeJogador());
        this.lblPontuacaoFacil.setText(String.valueOf(recorde.getDuracaoEmSegundos()));
    }

    private void recordesMedioAtualizados(TabelaRecordes recorde) {
        this.lblJogadorMedio.setText(recorde.getNomeJogador());
        this.lblPontuacaoMedio.setText(String.valueOf(recorde.getDuracaoEmSegundos()));
    }

    private void recordesDificilAtualizados(TabelaRecordes recorde) {
        this.lblJogadorDificil.setText(recorde.getNomeJogador());
        this.lblPontuacaoDificil.setText(String.valueOf(recorde.getDuracaoEmSegundos()));
    }

    public static void main(String[] args) {
        new MinesFinder("Mines Finder").setVisible(true);
    }

}
