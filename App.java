package campominado.gui;
import campominado12.CampoMinado;
import campominado12.CampoMinadoMaluco;
import javax.swing.*;
import java.awt.event.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Campo Minado");
        JButton playButton = new JButton("Jogar");
        JButton playCrazyButton = new JButton("Jogar Campo Minado Maluco");
        JButton exitButton = new JButton("Sair");

        playButton.addActionListener(e -> {
            frame.dispose();
            new CampoMinado();
        });

        playCrazyButton.addActionListener(e -> {
            frame.dispose();
            new CampoMinadoMaluco();
        });

        exitButton.addActionListener(e -> System.exit(0));

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(playButton);
        frame.add(playCrazyButton);
        frame.add(exitButton);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
