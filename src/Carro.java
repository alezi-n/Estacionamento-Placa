import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Carro {
    private String placa;
    private String horaEntrada;

    public Carro(String placa, String horaEntradaStr) {
        this.placa = placa;
        this.horaEntrada = horaEntradaStr;
    }

    public String getPlaca() {
        return placa;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }
}