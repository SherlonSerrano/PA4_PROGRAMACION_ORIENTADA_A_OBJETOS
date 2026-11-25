package pe.edu.isil;

// Clase abstracta: es la base de la jerarquía y no se puede instanciar (no existe un "Producto" genérico).
// Implementa Comparable para ordenar los productos por precio con Collections.sort.
public abstract class Producto implements Comparable<Producto> {

    // protected: visibles para la propia clase, el paquete y las subclases
    protected String codigo;
    protected String nombre;
    protected double precio;
    protected int stock;

    // Constructor: las subclases lo invocan con super(...)
    public Producto(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Métodos que devuelven valor (getters)
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    // Métodos que no devuelven valor.
    // No hay setCodigo: el código identifica al producto dentro del HashSet y no debe cambiar.
    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Métodos abstractos: se declaran aquí, pero cada subclase está obligada a programarlos
    public abstract int calcularGarantiaMeses();

    public abstract String getTipo();

    // Texto base del producto; las subclases lo sobrescriben y reutilizan con super.toString()
    @Override
    public String toString() {
        return getTipo() + " | " + codigo + " | " + nombre
                + " | S/ " + String.format("%.2f", precio)
                + " | Stock: " + stock
                + " | Garantía: " + calcularGarantiaMeses() + " meses";
    }

    // Dos productos son iguales si tienen el mismo código (sin distinguir mayúsculas).
    // HashSet necesita equals() y hashCode() para detectar duplicados.
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Producto) {
            Producto otro = (Producto) obj;
            return this.codigo.equalsIgnoreCase(otro.codigo);
        } else {
            return false;
        }
    }

    // Debe ser coherente con equals: productos iguales deben dar el mismo hashCode
    @Override
    public int hashCode() {
        return this.codigo.toUpperCase().hashCode();
    }

    // Orden natural: de menor a mayor precio
    @Override
    public int compareTo(Producto otro) {
        if (this.precio < otro.precio) {
            return -1;
        } else if (this.precio > otro.precio) {
            return 1;
        } else {
            return 0;
        }
    }
}
