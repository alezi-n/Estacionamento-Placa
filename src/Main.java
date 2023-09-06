import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaEstacionamento();
            }
        });

        int placas = 26*26*26*10*26*10*10;
        System.out.println("Quantidade de placas possiveis: " + placas);
    }
}
