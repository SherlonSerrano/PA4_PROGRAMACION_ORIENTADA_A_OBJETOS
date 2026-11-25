package pe.edu.isil;

public class Accesorio extends Producto implements Descontable {

    private String tipoAccesorio;

    private static final double DESCUENTO_MAXIMO = 10.0;

    public Accesorio(String codigo, String nombre, double precio, int stock,
                     String tipoAccesorio) {

        super(codigo, nombre, precio, stock);
        this.tipoAccesorio = tipoAccesorio;
    }

    public String getTipoAccesorio() {
        return tipoAccesorio;
    }

    @Override
    public int calcularGarantiaMeses() {
        return 6;
    }

    @Override
    public String getTipo() {
        return "Accesorio";
    }

    @Override
    public void aplicarDescuento(double porcentaje) {

        if (porcentaje < 0 || porcentaje > DESCUENTO_MAXIMO) {
            throw new IllegalArgumentException(
                    "El descuento de un accesorio debe estar entre 0% y 10%."
            );
        }

        double nuevoPrecio = getPrecio() * (1 - porcentaje / 100);
        setPrecio(nuevoPrecio);
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Tipo de accesorio: " + tipoAccesorio;
    }
}