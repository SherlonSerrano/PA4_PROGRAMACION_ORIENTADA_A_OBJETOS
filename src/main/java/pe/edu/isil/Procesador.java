package pe.edu.isil;

public class Procesador extends Producto {

    private int nucleos;
    private double frecuenciaGHz;

    public Procesador(String codigo, String nombre, double precio, int stock,
                      int nucleos, double frecuenciaGHz) {

        super(codigo, nombre, precio, stock);
        this.nucleos = nucleos;
        this.frecuenciaGHz = frecuenciaGHz;
    }

    public int getNucleos() {
        return nucleos;
    }

    public double getFrecuenciaGHz() {
        return frecuenciaGHz;
    }

    @Override
    public int calcularGarantiaMeses() {
        return 12;
    }

    @Override
    public String getTipo() {
        return "Procesador";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Núcleos: " + nucleos
                + " | Frecuencia: " + frecuenciaGHz + " GHz";
    }
}