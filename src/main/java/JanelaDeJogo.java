import javax.swing.*;

public class JanelaDeJogo extends JFrame {
    private JPanel painelJogo;

    public JanelaDeJogo() {
        setContentPane(painelJogo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();

        setVisible(true);
    }
}
