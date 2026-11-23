package pe.edu.isil;

public class Laptop extends Producto implements Descontable {

    private int ramGB;
    private String procesador;

    private static final double DESCUENTO_MAXIMO = 20.0;

    public Laptop(String codigo, String nombre, double precio, int stock,
                  int ramGB, String procesador) {

        super(codigo, nombre, precio, stock);
        this.ramGB = ramGB;
        this.procesador = procesador;
    }

    public int getRamGB() {
        return ramGB;
    }

    public String getProcesador() {
        return procesador;
    }

    @Override
    public int calcularGarantiaMeses() {
        return 24;
    }

    @Override
    public String getTipo() {
        return "Laptop";
    }

    @Override
    public void aplicarDescuento(double porcentaje) {

        if (porcentaje < 0 || porcentaje > DESCUENTO_MAXIMO) {
            throw new IllegalArgumentException(
                    "El descuento de una laptop debe estar entre 0% y 20%."
            );
        }

        double nuevoPrecio = getPrecio() * (1 - porcentaje / 100);
        setPrecio(nuevoPrecio);
    }

    @Override
    public String toString() {
        return super.toString()
                + " | RAM: " + ramGB + " GB"
                + " | Procesador: " + procesador;
    }
}