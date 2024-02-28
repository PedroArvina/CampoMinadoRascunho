package campominado.gui;

import campominado12.CampoMinado;
import campominado12.CampoMinadoMedio;
import campominado12.CampoMinadoFacil;
import campominado12.CampoMinadoMaluco;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;





public class App {
	
	
    private static JFrame frame;

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
            JButton scoreButton = createButton("Placar");

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
            frame.add(scoreButton, gbc);

            JButton exitButton = createButton("Sair");
            gbc.gridx = 1;
            frame.add(exitButton, gbc);

            playButton.addActionListener(e -> startGame("Difícil"));
            playButton2.addActionListener(e -> startGame("Médio"));
            playButton3.addActionListener(e -> startGame("Fácil"));
            playCrazyButton.addActionListener(e -> startGame("Maluco"));
            scoreButton.addActionListener(e -> placar());
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
            
            new CampoMinadoMaluco();
        }
    }
    
    public static void atualizarTop10Pontuacoes() {
        String arquivoDePontuacoes = "pontuacoes.txt";
        List<String[]> scores = new ArrayList<>();

        
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoDePontuacoes))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] parts = linha.split(",");
                
                if (parts.length == 2) {
                    scores.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de pontuações: " + e.getMessage());
            return;
        }

        
        scores.sort((a, b) -> Integer.compare(Integer.parseInt(b[1].trim()), Integer.parseInt(a[1].trim())));

        
        List<String[]> top10Scores = scores.subList(0, Math.min(10, scores.size()));

        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoDePontuacoes, false))) {
            for (String[] score : top10Scores) {
                bw.write(score[0] + "," + score[1]);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo de pontuações: " + e.getMessage());
        }
    }


    private static void placar() {
    	
        List<String[]> scores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("pontuacoes.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] parts = linha.split(",");
                scores.add(parts);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de pontuações: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Não foi possível carregar o placar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        scores.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

        
        String[] columnNames = {"Nome do Jogador", "Pontuação"};
        String[][] data = new String[scores.size()][2];
        for (int i = 0; i < scores.size(); i++) {
            data[i] = scores.get(i);
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        
        JOptionPane.showMessageDialog(frame, scrollPane, "Placar", JOptionPane.INFORMATION_MESSAGE);
    }

}
