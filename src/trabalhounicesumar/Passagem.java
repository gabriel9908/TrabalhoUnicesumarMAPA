public class Passagem {
    private Voo voo;
    private String nomePassageiro;
    private double preco;

    public Passagem(Voo voo, String nomePassageiro, double preco) {
        this.voo = voo;
        this.nomePassageiro = nomePassageiro;
        this.preco = preco;
    }

    public double calcularPrecoTotal() {
        return preco + 50; // Supondo uma taxa fixa de 50
    }

    public String getNomePassageiro() {
        return nomePassageiro;
    }

    void setPreco(double novoPreco) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setNomePassageiro(String novoNome) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
