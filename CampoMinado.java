package campominado12;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class CampoMinado {
	
	public class InvalidAttributeValueException extends Exception {
	    public InvalidAttributeValueException(String message) {
	        super(message);
	    }
	}
	
	public void setNumeroDeLinhasTotal(int numeroDeLinhasTotal) throws InvalidAttributeValueException {
        if (numeroDeLinhasTotal <= 0) {
            throw new InvalidAttributeValueException("Número de linhas deve ser maior que zero.");
        }
        this.NumeroDeLinhasTotal = numeroDeLinhasTotal;
    }

    public void setNumeroDeColunasTotal(int numeroDeColunasTotal) throws InvalidAttributeValueException {
        if (numeroDeColunasTotal <= 0) {
            throw new InvalidAttributeValueException("Número de colunas deve ser maior que zero.");
        }
        this.NumeroDeColunasTotal = numeroDeColunasTotal;
    }

    public void setQuantidadeDeBombasNaPartida(int quantidadeDeBombasNaPartida) throws InvalidAttributeValueException {
        if (quantidadeDeBombasNaPartida <= 0 || quantidadeDeBombasNaPartida > NumeroDeLinhasTotal * NumeroDeColunasTotal) {
            throw new InvalidAttributeValueException("Quantidade de bombas inválida.");
        }
        this.QuantidadeDeBombasNaPartida = quantidadeDeBombasNaPartida;
    }
	
	
    public abstract class Celula extends JButton {
        public int linha;
        public int coluna;
        public boolean aberta;
        public boolean temMina;

        public Celula(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
            this.aberta = false;
            this.temMina = false;
        }

        public abstract void revelar();
    }

    public class CelulaVazia extends Celula {
        public CelulaVazia(int linha, int coluna) {
            super(linha, coluna);
        }

        @Override
        public void revelar() {
            if (!this.aberta) {
                abrirCelula(this);
                trocarJogador();
            }
        }
    }

    public class CelulaBomba extends Celula {
        public CelulaBomba(int linha, int coluna) {
            super(linha, coluna);
            this.temMina = true;

            this.setFocusable(false);
            this.setMargin(new Insets(0, 0, 0, 0));
            this.setFont(new Font("Arial", Font.PLAIN, 25));
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.setBackground(Color.LIGHT_GRAY);

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (FimDeJogo || aberta) {
                        return;
                    }
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        revelar();
                        trocarJogador();
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        marcarBandeira(CelulaBomba.this);
                    }
                }
            });
        }

        @Override
        public void revelar() {
            mostrarBombas();
        }
    }

    private int jogadorAtual = 1;
    private int totalJogadores = 2;

    private int TamanhoDosQuadradinhos = 40;
    private int NumeroDeLinhasTotal = 32;
    private int NumeroDeColunasTotal = NumeroDeLinhasTotal;
    private int LarguraTabuleiro = NumeroDeColunasTotal * TamanhoDosQuadradinhos;
    private int AlturaTabuleiro = NumeroDeLinhasTotal * TamanhoDosQuadradinhos;

    private JFrame JanelaInicial = new JFrame("Campo Minado");
    private JLabel TextoDeTopo = new JLabel();
    private JLabel statusLabel = new JLabel();
    private JPanel PainelDoTexto = new JPanel();
    private JPanel PainelDosQuadradinhos = new JPanel();

    private int QuantidadeDeBombasNaPartida = 100;
    private Celula[][] MatrizDoTabuleiro = new Celula[NumeroDeLinhasTotal][NumeroDeColunasTotal];
    private Random random = new Random();

    private int NumeroDeQuadradosClicados = 0;
    private boolean FimDeJogo = false;

    public CampoMinado() {
    	
    	try {
            setNumeroDeLinhasTotal(32);  
            setNumeroDeColunasTotal(32);
            setQuantidadeDeBombasNaPartida(100);  
        } catch (InvalidAttributeValueException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;  
        }
    	
        JanelaInicial.setSize(LarguraTabuleiro, AlturaTabuleiro);
        JanelaInicial.setLocationRelativeTo(null);
        JanelaInicial.setResizable(false);
        JanelaInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JanelaInicial.setLayout(new BorderLayout());

        TextoDeTopo.setFont(new Font("Arial", Font.BOLD, 25));
        TextoDeTopo.setHorizontalAlignment(JLabel.CENTER);
        TextoDeTopo.setText("Campo Minado");
        TextoDeTopo.setOpaque(true);

        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setText("Jogador " + jogadorAtual);

        PainelDoTexto.setLayout(new BorderLayout());
        PainelDoTexto.add(TextoDeTopo, BorderLayout.NORTH);
        PainelDoTexto.add(statusLabel, BorderLayout.CENTER);

        JanelaInicial.add(PainelDoTexto, BorderLayout.NORTH);

        PainelDosQuadradinhos.setLayout(new GridLayout(NumeroDeLinhasTotal, NumeroDeColunasTotal));
        JanelaInicial.add(PainelDosQuadradinhos);

        for (int Linha = 0; Linha < NumeroDeLinhasTotal; Linha++) {
            for (int Coluna = 0; Coluna < NumeroDeColunasTotal; Coluna++) {
                Celula celula = new CelulaVazia(Linha, Coluna);
                MatrizDoTabuleiro[Linha][Coluna] = celula;

                celula.setFocusable(false);
                celula.setMargin(new Insets(0, 0, 0, 0));
                celula.setFont(new Font("Arial", Font.PLAIN, 25));
                celula.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                celula.setBackground(Color.LIGHT_GRAY);

                celula.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (FimDeJogo || celula.aberta) {
                            return;
                        }
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            celula.revelar();
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            marcarBandeira(celula);
                        }
                    }
                });

                PainelDosQuadradinhos.add(celula);
            }
        }

        JanelaInicial.setVisible(true);
        distribuidorDeBombas();
    }

    void distribuidorDeBombas() {
        int bombasRestantes = QuantidadeDeBombasNaPartida;
        while (bombasRestantes > 0) {
            int linha = random.nextInt(NumeroDeLinhasTotal);
            int coluna = random.nextInt(NumeroDeColunasTotal);

            Celula celula = MatrizDoTabuleiro[linha][coluna];
            if (!(celula instanceof CelulaBomba)) {
                PainelDosQuadradinhos.remove(celula);
                Celula novaCelula = new CelulaBomba(linha, coluna);
                MatrizDoTabuleiro[linha][coluna] = novaCelula;
                PainelDosQuadradinhos.add(novaCelula, linha * NumeroDeColunasTotal + coluna);
                bombasRestantes--;
            }
        }
        PainelDosQuadradinhos.revalidate();
        PainelDosQuadradinhos.repaint();
    }

    void mostrarBombas() {
        for (int linha = 0; linha < NumeroDeLinhasTotal; linha++) {
            for (int coluna = 0; coluna < NumeroDeColunasTotal; coluna++) {
                Celula celula = MatrizDoTabuleiro[linha][coluna];
                if (celula instanceof CelulaBomba) {
                    celula.setText("O");
                }
            }
        }

        FimDeJogo = true;
        atualizarStatusDoJogo();
    }

    void abrirCelula(Celula celula) {
        if (celula.aberta) {
            return;
        }

        if (celula.temMina) {
            mostrarBombas();
            return;
        }

        celula.aberta = true;
        celula.setBackground(Color.WHITE);

        int minasEncontradas = contadorDeMinas(celula.linha, celula.coluna);

        if (minasEncontradas > 0) {
            celula.setText(Integer.toString(minasEncontradas));
        } else {
            abridorEmCadeia(celula.linha, celula.coluna);
        }

        NumeroDeQuadradosClicados++;

        if (NumeroDeQuadradosClicados == NumeroDeLinhasTotal * NumeroDeColunasTotal - QuantidadeDeBombasNaPartida) {
            FimDeJogo = true;
            statusLabel.setText("Campo Limpo!");
        }
    }

    int contadorDeMinas(int linha, int coluna) {
        int minasEncontradas = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = linha + dr;
                int nc = coluna + dc;
                if (nr >= 0 && nr < NumeroDeLinhasTotal && nc >= 0 && nc < NumeroDeColunasTotal) {
                    Celula vizinha = MatrizDoTabuleiro[nr][nc];
                    if (vizinha instanceof CelulaBomba) {
                        minasEncontradas++;
                    }
                }
            }
        }
        return minasEncontradas;
    }

    void abridorEmCadeia(int linha, int coluna) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = linha + dr;
                int nc = coluna + dc;
                if (nr >= 0 && nr < NumeroDeLinhasTotal && nc >= 0 && nc < NumeroDeColunasTotal) {
                    Celula vizinha = MatrizDoTabuleiro[nr][nc];
                    if (!vizinha.aberta) {
                        abrirCelula(vizinha);
                    }
                }
            }
        }
    }

    void marcarBandeira(Celula celula) {
        if (!celula.aberta) {
            if (celula.getText().isEmpty()) {
                celula.setText("I");
            } else {
                celula.setText("");
            }
        }
    }

    void trocarJogador() {
        jogadorAtual = (jogadorAtual % totalJogadores) + 1;
        atualizarStatusDoJogo();
    }

    void atualizarStatusDoJogo() {
        if (FimDeJogo) {
            statusLabel.setText("Fim de Jogo!");
        } else {
            statusLabel.setText("Jogador " + jogadorAtual);
        }
    }
}
