import javax.swing.*;
import java.awt.*;

public class JanelaDeJogo extends JFrame {
    private CampoMinado campoMinado;
    private BotaoCampoMinado[][] botoes;

    private JPanel painelJogo;

    public JanelaDeJogo(CampoMinado campoMinado) {
        this.campoMinado = campoMinado;

        var largura = campoMinado.getLargura();
        var altura = campoMinado.getAltura();

        botoes = new BotaoCampoMinado[largura][altura];
        painelJogo.setLayout(new GridLayout(altura, largura));

        for (var linha = 0; linha < largura; ++linha) {
            for (var coluna = 0; coluna < altura; ++coluna) {
                botoes[coluna][linha] = new BotaoCampoMinado();
                painelJogo.add(botoes[coluna][linha]);
            }
        }

        setContentPane(painelJogo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();

        setVisible(true);
    }
}
