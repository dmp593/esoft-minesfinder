import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaDeJogo extends JFrame {
    private CampoMinado campoMinado;
    private BotaoCampoMinado[][] botoes;

    private JPanel painelJogo;

    public JanelaDeJogo(CampoMinado campoMinado) {
        this.campoMinado = campoMinado;

        var altura = campoMinado.getAltura();
        var largura = campoMinado.getLargura();

        botoes = new BotaoCampoMinado[altura][largura];
        painelJogo.setLayout(new GridLayout(altura, largura));

        for (var linha = 0; linha < altura; ++linha) {
            for (var coluna = 0; coluna < largura; ++coluna) {
                botoes[linha][coluna] = new BotaoCampoMinado(linha, coluna);
                botoes[linha][coluna].setFont(new Font("Serif", Font.PLAIN, 16));
                botoes[linha][coluna].addActionListener(this::btnCampoMinadoActionPerformed);

                painelJogo.add(botoes[linha][coluna]);
            }
        }

        setContentPane(painelJogo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();

        setVisible(true);
    }

    public void btnCampoMinadoActionPerformed(ActionEvent e) {
        var botao = (BotaoCampoMinado) e.getSource();

        int x = botao.getLinha();
        int y = botao.getColuna();

        campoMinado.revelarQuadricula(x, y);

        actualizarEstadoBotoes();

        if (! campoMinado.isJogoTerminado()) {
            return;
        }

        if (campoMinado.isJogoDerrotado()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Oh, rebentou uma mina",
                    "Perdeu!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            var duracaoJogoEmSegs = campoMinado.getDuracaoJogo() / 1000;

            JOptionPane.showMessageDialog(
                    null,
                    "Parabéns. Conseguiu descobrir todas as minas em "+ duracaoJogoEmSegs + " segundos",
                    "Vitória",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        setVisible(false);
    }

    private void actualizarEstadoBotoes() {
        for (int x = 0; x < campoMinado.getAltura(); x++) {
            for (int y = 0; y < campoMinado.getLargura(); y++) {
                botoes[x][y].setEstado(campoMinado.getEstadoQuadricula(x, y));
            }
        }
    }
}
