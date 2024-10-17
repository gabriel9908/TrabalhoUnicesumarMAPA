import java.util.ArrayList;
import java.util.List;

public class CompanhiaAerea {
    private String nome;
    private ArrayList<Voo> voos;

    public CompanhiaAerea(String nome) {
        this.nome = nome;
        this.voos = new ArrayList<>();
    }

    public void adicionarVoo(Voo voo) {
        voos.add(voo);
    }

    public String getNome() {
        return nome;
    }

    public List<Voo> getVoos() {
        return voos;
    }
}
