import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class TelaEstacionamento extends JFrame {
    private JTextField placaField;
    private JTextField placaField2;
    private JTextField entradaField;
    private JTextField saidaField;
    public Estacionamento est = new Estacionamento();
    public TelaEstacionamento() {
        // Configurações da janela
        setTitle("Estacionamento");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(12, 2));

        // Rótulos
        JLabel placaLabel = new JLabel("Placa do Veículo:");
        JLabel entradaLabel = new JLabel("Entrada do Veículo:");
        JLabel saidaLabel = new JLabel("Saída do Veículo:");

        // Campos de entrada de texto
        placaField = new JTextField();
        placaField2 = new JTextField();
        entradaField = new JTextField();
        saidaField = new JTextField();

        // Botão para processar as informações
        JButton visu = new JButton("Visualizar");
        visu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExibirEstacionamentoJanela exibirEstacionamentoJanela = new ExibirEstacionamentoJanela(est);
                exibirEstacionamentoJanela.setVisible(true);
            }
        });

        JButton add = new JButton("Adicionar");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String placa = placaField.getText();
                    String hrE = entradaField.getText();

                    if (!placa.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}")) {
                        throw new IllegalArgumentException("Formato de placa inválido. Utilize o formato LLLNLNN, onde L é letra maiúscula e N é número.");
                    }

                    if (placa.isEmpty() || hrE.isEmpty()) {
                        throw new IllegalArgumentException("Placa ou hora de saída não podem estar vazios.");
                    }

                    Carro carro = new Carro(placa, hrE);
                    est.adicionarCarro(carro);
                }catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formato da data inválido. Utilize o formato dd/MM/yyyy HH:mm.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao processar as informações: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton processarButton = new JButton("Processar");
        processarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String placa = placaField2.getText();
                    String entradaStr = est.buscarCarroHoraEntrada(placa);
                    System.out.println(entradaStr);
                    String saidaStr = saidaField.getText();

                    if (placa.isEmpty() || saidaStr.isEmpty()) {
                        throw new IllegalArgumentException("Placa ou hora de saída não podem estar vazios.");
                    }

                    // Converter as strings de entrada e saída para LocalDateTime
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime entrada = LocalDateTime.parse(entradaStr, formatter);
                    LocalDateTime saida = LocalDateTime.parse(saidaStr, formatter);

                    if (saida.isBefore(entrada)) {
                        throw new IllegalArgumentException("Hora de saída menor que a de entrada!");
                    }
                    // Calcular a duração em horas
                    Duration duracao = Duration.between(entrada, saida);

                    long duracaoMin = ChronoUnit.MINUTES.between(entrada, saida);
                    System.out.println(duracaoMin);
                    long duracaoHoras = ChronoUnit.HOURS.between(entrada, saida);

                    // Exibir a duração no console
                    //System.out.println("Placa: " + placa);
                    //System.out.println("Entrada: " + entradaStr);
                    //System.out.println("Saída: " + saidaStr);
                    //System.out.println("Duração: " + duracaoHoras + " horas");
                    // Exibir a nova janela com os dados do veículo e a duração
                    ExibirDadosVeiculoJanela exibirDadosJanela = new ExibirDadosVeiculoJanela(placa, duracaoHoras, duracaoMin);
                    exibirDadosJanela.setVisible(true);

                    //Excluir carro do estacionamento
                    est.excluirCarro(placa);

                }catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formato da data inválido. Utilize o formato dd/MM/yyyy HH:mm.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao processar as informações: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionando os componentes à janela
        add(new JLabel()); // Espaço vazio
        add(new JLabel("Colocar carro no estacionamento: "));

        add(placaLabel);
        add(placaField);

        add(entradaLabel);
        add(entradaField);

        add(new JLabel()); // Espaço vazio
        add(add); // Espaço vazio

        add(new JLabel()); // Espaço vazio
        add(new JLabel("Tirar carro do estacionamento: "));

        add(new JLabel("Placa: "));
        add(placaField2);

        add(saidaLabel);
        add(saidaField);

        add(new JLabel()); // Espaço vazio
        add(processarButton);

        add(new JLabel()); // Espaço vazio
        add(new JLabel()); // Espaço vazio

        add(new JLabel("Visualizar Estacionamento:"));
        add(visu);

        // Exibindo a janela
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private class ExibirDadosVeiculoJanela extends JFrame {
        private double calcularTeto(long duracaoTotalMinutos,double juros) {
            int incrementoMinutos = 15;
            double duracaoTeto = Math.ceil((double)duracaoTotalMinutos / incrementoMinutos) * juros;
            return duracaoTeto;
        }

        public ExibirDadosVeiculoJanela(String placa, long duracaoHoras,long duracaoMin) {
            setTitle("Dados do Veículo");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(4, 1));

            // Rótulos para os dados
            JLabel placaLabel = new JLabel("Placa do Veículo: " + placa);
            double Min = duracaoMin - (duracaoHoras * 60);
            JLabel duracaoLabel = new JLabel("Duração: " + duracaoHoras + " horas "+ Min + " minutos");

            //Calcular tarifa
            double valor = 0;
            double juros = 1.50;

            if(duracaoMin <= 15){
                valor = 0;
            }else if(duracaoMin > 15 && duracaoMin <= 180){
                valor = 5;
            } else if (duracaoMin > 180) {
                long minutosExtras = duracaoMin - 180;
                double valorExtras = calcularTeto(minutosExtras,juros);
                //double valorExtras = Math.ceil((double) minutosExtras / 15) * juros;
                valor = 5 + valorExtras;
            }

            // Informações da tela
            String val_Format = String.format("%.2f", valor);
            JLabel informacao1Label = new JLabel("Valor: R$ " + val_Format);
            String p = VerificarPlaca(placa);
            JLabel informacao2Label = new JLabel(p);

            add(placaLabel);
            add(duracaoLabel);
            add(informacao1Label);
            add(informacao2Label);
        }
    }
    public String VerificarPlaca(String pl){
        String placa = pl.substring(0,3);
        System.out.println(placa);
        String estado = "";

        //Espirito Santo/  MOX a MTZ || OCV a ODT || OVE a OVF || OVH a OVL || OYD a OYK || PPA a PPZ || QRB a QRM || RBA a RBJ || RQM a RQV
        //Rio de Janeiro/ RIO a RIO || RIP a RKV
        //São Paulo/  BFA a GKI || QSN a QSZ
        //RIM5C34

        if (placa.compareTo("MOX") >= 0 && placa.compareTo("MTZ") <= 0||placa.compareTo("OCV") >= 0 && placa.compareTo("ODT") <= 0||placa.compareTo("OVE") >= 0 && placa.compareTo("OVF") <= 0||placa.compareTo("OVH") >= 0 && placa.compareTo("OVL") <= 0||placa.compareTo("OYD") >= 0 && placa.compareTo("OYK") <= 0||placa.compareTo("PPA") >= 0 && placa.compareTo("PPZ") <= 0||placa.compareTo("QRB") >= 0 && placa.compareTo("QRM") <= 0||placa.compareTo("RBA") >= 0 && placa.compareTo("RBJ") <= 0||placa.compareTo("RQM") >= 0 && placa.compareTo("RQV") <= 0) {
            estado = "A placa é do Espírito Santo";
        } else if (placa.compareTo("RIO") >= 0 && placa.compareTo("RKV") <= 0) {
            estado = "A placa é do Rio de Janeiro";
        } else if (placa.compareTo("BFA") >= 0 && placa.compareTo("GKI") <= 0||placa.compareTo("QSN") >= 0 && placa.compareTo("QSZ") <= 0) {
            estado = "A placa é de São Paulo";
        } else {
            estado = "A placa não pertence ao Sudeste";
        }

        return estado;
    }
}
