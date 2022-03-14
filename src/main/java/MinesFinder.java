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

    public MinesFinder(String title) {
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelPrincipal);

        btnJogoFacil.addActionListener(this::btnJogoFacilActionPerformed);
        btnJogoMedio.addActionListener(this::btnJogoMedioActionPerformed);
        btnJogoDificil.addActionListener(this::btnJogoDificilActionPerformed);

        btnSair.addActionListener(this::btnSairActionPerformed);

        pack();
    }

    private void btnJogoFacilActionPerformed(ActionEvent e) {
        new JanelaDeJogo(new CampoMinado(9, 9, 10));
    }

    private void btnJogoMedioActionPerformed(ActionEvent e) {
        new JanelaDeJogo(new CampoMinado(16, 16, 40));
    }

    private void btnJogoDificilActionPerformed(ActionEvent e) {
        new JanelaDeJogo(new CampoMinado(16, 30, 90));
    }

    private void btnSairActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    public static void main(String[] args) {
        new MinesFinder("Mines Finder").setVisible(true);
    }

}
