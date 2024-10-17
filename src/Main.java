import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private CompanhiaAerea companhia;
    private Voo voo1;
    private List<Passagem> passagens;
    private DefaultListModel<String> listModel;
    private JList<String> passagemList;

    public Main() {
        companhia = new CompanhiaAerea("GabrielTUR");
        voo1 = new Voo("Latam Viagens Cuiaba MT", 100);
        passagens = new ArrayList<>();

        configurarInterface();
        carregarDados();
    }

    private void configurarInterface() {
        setTitle("Sistema de Reserva de Passagens - GabrielTUR");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Título
        JLabel titleLabel = new JLabel("Sistema de Reserva de Passagens Aéreas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel, BorderLayout.NORTH);

        // Painel de informações
        JPanel infoPanel = new JPanel(new GridLayout(3, 2));
        infoPanel.setBackground(Color.LIGHT_GRAY);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        infoPanel.add(new JLabel("Nome da Companhia: ", SwingConstants.RIGHT));
        infoPanel.add(new JLabel(companhia.getNome(), SwingConstants.LEFT));

        infoPanel.add(new JLabel("Código do Voo: ", SwingConstants.RIGHT));
        infoPanel.add(new JLabel(voo1.getCodigo(), SwingConstants.LEFT));

        infoPanel.add(new JLabel("Assentos Disponíveis: ", SwingConstants.RIGHT));
        infoPanel.add(new JLabel(String.valueOf(voo1.getAssentosDisponiveis()), SwingConstants.LEFT));

        add(infoPanel, BorderLayout.CENTER);

        // Lista de passagens
        listModel = new DefaultListModel<>();
        passagemList = new JList<>(listModel);
        passagemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(passagemList), BorderLayout.EAST);

        // Botões
        JPanel buttonPanel = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Passagem");
        btnAdicionar.addActionListener(this::adicionarPassagem);
        buttonPanel.add(btnAdicionar);

        JButton btnAtualizar = new JButton("Atualizar Passagem");
        btnAtualizar.addActionListener(this::atualizarPassagem);
        buttonPanel.add(btnAtualizar);

        JButton btnDeletar = new JButton("Deletar Passagem");
        btnDeletar.addActionListener(this::deletarPassagem);
        buttonPanel.add(btnDeletar);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void adicionarPassagem(ActionEvent e) {
        String nome = JOptionPane.showInputDialog(this, "Nome do Passageiro:");
        if (nome != null && !nome.trim().isEmpty()) {
            String precoStr = JOptionPane.showInputDialog(this, "Preço da Passagem:");
            if (precoStr != null) {
                try {
                    double preco = Double.parseDouble(precoStr);
                    Passagem passagem = new Passagem(voo1, nome, preco);
                    passagens.add(passagem);
                    listModel.addElement(passagem.getNomePassageiro() + " - R$ " + passagem.calcularPrecoTotal());
                    salvarDados(); // Salvar dados após adicionar
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Preço inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void atualizarPassagem(ActionEvent e) {
        int selectedIndex = passagemList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Passagem passagem = passagens.get(selectedIndex);
            String novoNome = JOptionPane.showInputDialog(this, "Novo Nome do Passageiro:", passagem.getNomePassageiro());
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                String novoPrecoStr = JOptionPane.showInputDialog(this, "Novo Preço da Passagem:", passagem.calcularPrecoTotal());
                if (novoPrecoStr != null) {
                    try {
                        double novoPreco = Double.parseDouble(novoPrecoStr);
                        passagem.setNomePassageiro(novoNome);
                        listModel.set(selectedIndex, passagem.getNomePassageiro() + " - R$ " + passagem.calcularPrecoTotal());
                        salvarDados(); // Salvar dados após atualizar
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Preço inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma passagem para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deletarPassagem(ActionEvent e) {
        int selectedIndex = passagemList.getSelectedIndex();
        if (selectedIndex >= 0) {
            passagens.remove(selectedIndex);
            listModel.remove(selectedIndex);
            salvarDados(); // Salvar dados após deletar
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma passagem para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void salvarDados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dados_reserva.txt"))) {
            for (Passagem passagem : passagens) {
                writer.write("Passagem: " + passagem.getNomePassageiro() + " - R$ " + passagem.calcularPrecoTotal() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!", "Salvar Dados", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarDados() {
        try (BufferedReader reader = new BufferedReader(new FileReader("dados_reserva.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Passagem: ")) {
                    String[] parts = line.substring(10).split(" - R$ ");
                    String nomePassageiro = parts[0];
                    double preco = Double.parseDouble(parts[1].replace(",", "."));
                    Passagem passagem = new Passagem(voo1, nomePassageiro, preco);
                    passagens.add(passagem);
                    listModel.addElement(passagem.getNomePassageiro() + " - R$ " + passagem.calcularPrecoTotal());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Iniciando com dados padrão.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
