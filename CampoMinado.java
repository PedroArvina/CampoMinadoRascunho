package campominado.gui;

import campominado12.CampoMinado;
import campominado12.CampoMinadoMedio;
import campominado12.CampoMinadoFacil;
import campominado12.CampoMinadoMaluco;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {
    private static JFrame frame; // Referência estática para a janela do menu principal

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI() {
        if (frame == null) {
            frame = new JFrame("Menu Campo Minado");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel titleLabel = new JLabel("Bem-vindo ao Campo Minado");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            frame.add(titleLabel, gbc);

            JButton playButton = createButton("Difícil");
            JButton playButton2 = createButton("Médio");
            JButton playButton3 = createButton("Fácil");
            JButton playCrazyButton = createButton("Maluco");
            JButton scoreButton = createButton("Placar"); // Botão Placar adicionado

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

            gbc.gridx = 0;
            gbc.gridy = 3;
            frame.add(scoreButton, gbc); // Posicionando o botão Placar

            JButton exitButton = createButton("Sair");
            gbc.gridx = 1;
            frame.add(exitButton, gbc);

            playButton.addActionListener(e -> startGame("Difícil"));
            playButton2.addActionListener(e -> startGame("Médio"));
            playButton3.addActionListener(e -> startGame("Fácil"));
            playCrazyButton.addActionListener(e -> startGame("Maluco"));
            scoreButton.addActionListener(e -> placar()); // Adicionando ação ao botão Placar
            exitButton.addActionListener(e -> System.exit(0));
        }

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

    // Função Placar (esqueleto básico)
    private static void placar() {
        // Implemente a lógica de exibição do placar aqui
        JOptionPane.showMessageDialog(frame, "Placar ainda não implementado.");
    }
}
