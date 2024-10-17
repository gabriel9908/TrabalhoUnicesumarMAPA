public class Voo {
    private String codigo;
    private int totalAssentos;
    private int assentosOcupados;

    public Voo(String codigo, int totalAssentos) {
        this.codigo = codigo;
        this.totalAssentos = totalAssentos;
        this.assentosOcupados = 0;
    }

    public void reservarAssento() {
        if (assentosOcupados < totalAssentos) {
            assentosOcupados++;
        } else {
            System.out.println("Não há assentos disponíveis.");
        }
    }

    public int getAssentosDisponiveis() {
        return totalAssentos - assentosOcupados;
    }

    public String getCodigo() {
        return codigo;
    }

    void setCodigo(String novoCodigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
