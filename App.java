package campominado.gui;

import campominado12.CampoMinado;
import campominado12.CampoMinadoMedio;
import campominado12.CampoMinadoFacil;
import campominado12.CampoMinadoMaluco;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Menu Campo Minado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel titleLabel = new JLabel("Bem-vindo ao Campo Minado");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        // Botões de jogo
        JButton playButton = createButton("Difícil");
        JButton playButton2 = createButton("Médio");
        JButton playButton3 = createButton("Fácil");
        JButton playCrazyButton = createButton("Maluco");

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(playButton, gbc);

        gbc.gridx = 1;
        frame.add(playButton2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(playButton3, gbc);

        gbc.gridx = 1;
        frame.add(playCrazyButton, gbc);

        // Botão Sair
        JButton exitButton = createButton("Sair");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(exitButton, gbc);

        // Ação dos botões
        playButton.addActionListener(e -> startGame("Difícil"));
        playButton2.addActionListener(e -> startGame("Médio"));
        playButton3.addActionListener(e -> startGame("Fácil"));
        playCrazyButton.addActionListener(e -> startGame("Maluco"));
        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    private static void startGame(String difficulty) {
        // Aqui você pode adicionar a lógica para iniciar o jogo com a dificuldade escolhida
        // Por exemplo, iniciar as classes CampoMinado, CampoMinadoMedio, CampoMinadoFacil ou CampoMinadoMaluco
        if (difficulty.equals("Difícil")) {
            new CampoMinado();
        } else if (difficulty.equals("Médio")) {
            new CampoMinadoMedio();
        } else if (difficulty.equals("Fácil")) {
            new CampoMinadoFacil();
        } else if (difficulty.equals("Maluco")) {
            
            int linhas = 20;
            int colunas = 20;
            int bombas = 20;
            new CampoMinadoMaluco(linhas, colunas, bombas);
        }
    }
}
