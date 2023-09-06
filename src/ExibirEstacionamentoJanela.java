import javax.swing.JFrame;
import javax.swing.JLabel;

public class ExibirEstacionamentoJanela extends JFrame {
    private Estacionamento estacionamento;

    public ExibirEstacionamentoJanela(Estacionamento estacionamento) {
        this.estacionamento = estacionamento;

        setTitle("Estacionamento");
        setSize(1000, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        exibirEstacionamento();
    }

    private void exibirEstacionamento() {
        String[] estadoEstacionamento = estacionamento.getEstadoEstacionamento();

        StringBuilder sb = new StringBuilder();
        sb.append("Estacionamento: [");
        for (int i = 0; i < estadoEstacionamento.length; i++) {
            sb.append(estadoEstacionamento[i]);
            if (i < estadoEstacionamento.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        JLabel estadoLabel = new JLabel(sb.toString());
        add(estadoLabel);
    }
}
