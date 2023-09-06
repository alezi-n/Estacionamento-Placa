import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Estacionamento {
    private List<Carro> carros;
    private Random random;

    public Estacionamento() {
        carros = new ArrayList<>();
        random = new Random();
    }

    public void adicionarCarro(Carro carro) {
        int indice = gerarIndiceAleatorio();
        carros.add(indice, carro);
    }

    private int gerarIndiceAleatorio() {
        int indice;
        do {
            indice = random.nextInt(carros.size() + 1);
        } while (carros.contains(indice));
        return indice;
    }

    public void exibirEstacionamento() {
        System.out.print("Estacionamento: [");
        for (int i = 0; i < carros.size(); i++) {
            if (carros.get(i) != null) {
                System.out.print("ocupado");
            } else {
                System.out.print("vazio");
            }
            if (i < carros.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]\n");
    }
    public String buscarCarroHoraEntrada(String placa) {
        for (Carro carro : carros) {
            if (carro != null && carro.getPlaca().equals(placa)) {
                return carro.getHoraEntrada();
            }
        }
        return null; // Retorna null se o carro não for encontrado
    }
    public String[] getEstadoEstacionamento() {
        String[] estado = new String[15];
        Arrays.fill(estado, "vazio");

        for (int i = 0; i < carros.toArray().length; i++) {
            if (carros.get(i) != null) {
                estado[i] = "ocupado";
            }
        }

        return estado;
    }
    public void excluirCarro(String placa) throws Exception {
        boolean carroEncontrado = false;

        for (int i = 0; i < carros.size(); i++) {
            if (carros.get(i) != null && carros.get(i).getPlaca().equals(placa)) {
                carros.set(i,null);
                carroEncontrado = true;
                break;
            }
        }

        if (!carroEncontrado) {
            throw new Exception("Carro com a placa " + placa + " não encontrado no estacionamento.");
        }
    }
}
