import javax.swing.JFrame;

public class jogo{



    public static void main(String[] args) {
        JFrame janela =new JFrame();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.setTitle("Jogo da vida");

        PainelJogo paineljogo = new PainelJogo();
        janela.add(paineljogo);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
        paineljogo.startGameThread();


    }
}