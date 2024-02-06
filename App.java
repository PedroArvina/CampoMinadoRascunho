package campominado.gui;
import campominado12.CampoMinado;
import campominado12.CampoMinadoMedio;
import campominado12.CampoMinadoFacil;
import campominado12.CampoMinadoMaluco;
import javax.swing.*;
import java.awt.event.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Campo Minado");
        JButton playButton = new JButton("Jogar Difícil");
        JButton playButton2 = new JButton("Jogar Médio");
        JButton playButton3 = new JButton("Jogar Fácil");
        JButton playCrazyButton = new JButton("Jogar Campo Minado Maluco");
        JButton exitButton = new JButton("Sair");

        playButton.addActionListener(e -> {
            frame.dispose();
            new CampoMinado();
        });
        
        playButton2.addActionListener(e -> {
            frame.dispose();
            new CampoMinadoMedio();
        });
        
        playButton3.addActionListener(e -> {
            frame.dispose();
            new CampoMinadoFacil();
        });
        
        playCrazyButton.addActionListener(e -> {
            frame.dispose();
            new CampoMinadoMaluco();
        });

        exitButton.addActionListener(e -> System.exit(0));

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(playButton);
        frame.add(playButton2);
        frame.add(playButton3);
        frame.add(playCrazyButton);
        frame.add(exitButton);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
