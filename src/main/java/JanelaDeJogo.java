import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaDeJogo extends JFrame {
    private CampoMinado campoMinado;
    private Recorde recorde;

    private JPanel painelJogo;
    private BotaoCampoMinado[][] botoes;

    public JanelaDeJogo(CampoMinado campoMinado, Recorde recorde) {
        this.campoMinado = campoMinado;
        this.recorde = recorde;

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

        var keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                var botao = (BotaoCampoMinado) e.getSource();

                var linha = botao.getLinha();
                var coluna = botao.getColuna();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> botoes[--linha < 0 ? altura - 1 : linha][coluna].requestFocus();
                    case KeyEvent.VK_DOWN -> botoes[(linha + 1) % altura][coluna].requestFocus();
                    case KeyEvent.VK_LEFT -> botoes[linha][--coluna < 0 ? largura - 1 : coluna].requestFocus();
                    case KeyEvent.VK_RIGHT -> botoes[linha][(coluna + 1) % largura].requestFocus();
                    case KeyEvent.VK_M -> {
                        switch (campoMinado.getEstadoQuadricula(linha, coluna)) {
                            case CampoMinado.TAPADO -> campoMinado.marcarComoTendoMina(linha, coluna);
                            case CampoMinado.MARCADO -> campoMinado.marcarComoSuspeita(linha, coluna);
                            case CampoMinado.DUVIDA -> campoMinado.desmarcarQuadricula(linha, coluna);
                        }

                        actualizarEstadoBotoes();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };

        for (var linha = 0; linha < altura; ++linha) {
            for (var coluna = 0; coluna < largura; ++coluna) {
                botoes[linha][coluna] = new BotaoCampoMinado(linha, coluna);
                botoes[linha][coluna].addMouseListener(mouseListener);
                botoes[linha][coluna].addKeyListener(keyListener);
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
                    "Oh :( rebentou uma mina",
                    "Perdeu...",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            var duracaoJogoEmSegs = campoMinado.getDuracaoJogoEmMilisegundos() / 1000;

            JOptionPane.showMessageDialog(
                    null,
                    "Parabéns :) Descobriu as minas em " + duracaoJogoEmSegs + " segundos",
                    "Vitória!",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (duracaoJogoEmSegs < recorde.getDuracaoEmSegundos()) {
                String nome=JOptionPane.showInputDialog("Introduza o seu nome");
                recorde.setRecorde(nome, duracaoJogoEmSegs);
            }
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
