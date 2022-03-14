import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

        var mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) {
                    return;
                }
                var botao = (BotaoCampoMinado) e.getSource();

                var linha = botao.getLinha();
                var coluna = botao.getColuna();

                var estadoQuadricula = campoMinado.getEstadoQuadricula(linha, coluna);

                if (estadoQuadricula == CampoMinado.TAPADO) {
                    campoMinado.marcarComoTendoMina(linha, coluna);
                } else if (estadoQuadricula == CampoMinado.MARCADO) {
                    campoMinado.marcarComoSuspeita(linha, coluna);
                } else if (estadoQuadricula == CampoMinado.DUVIDA) {
                    campoMinado.desmarcarQuadricula(linha, coluna);
                }

                actualizarEstadoBotoes();
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };

        for (var linha = 0; linha < altura; ++linha) {
            for (var coluna = 0; coluna < largura; ++coluna) {
                botoes[linha][coluna] = new BotaoCampoMinado(linha, coluna);
                botoes[linha][coluna].setFont(new Font("Serif", Font.PLAIN, 16));

                botoes[linha][coluna].addMouseListener(mouseListener);
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
