package pe.edu.isil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class PruebaProducto {

    // Subclases temporales solo para probar la clase abstracta (no se suben al repo)
    static class ProductoPrueba extends Producto {
        ProductoPrueba(String codigo, String nombre, double precio, int stock) {
            super(codigo, nombre, precio, stock);
        }

        @Override
        public int calcularGarantiaMeses() {
            return 12;
        }

        @Override
        public String getTipo() {
            return "Prueba";
        }
    }

    static class ProductoDescontablePrueba extends ProductoPrueba implements Descontable {
        ProductoDescontablePrueba(String codigo, String nombre, double precio, int stock) {
            super(codigo, nombre, precio, stock);
        }

        @Override
        public void aplicarDescuento(double porcentaje) {
            precio = precio - (precio * porcentaje / 100);
        }
    }

    public static void main(String[] args) {
        Producto a = new ProductoPrueba("P001", "Mouse", 59.90, 30);
        Producto b = new ProductoPrueba("p001", "Otro nombre", 100.00, 1);
        Producto c = new ProductoPrueba("P002", "Teclado", 189.90, 20);

        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("a.equals(c): " + a.equals(c));
        System.out.println("mismo hashCode a y b: " + (a.hashCode() == b.hashCode()));

        HashSet<Producto> registro = new HashSet<>();
        System.out.println("add a: " + registro.add(a));
        System.out.println("add b (código repetido): " + registro.add(b));
        System.out.println("add c: " + registro.add(c));
        System.out.println("tamaño del HashSet: " + registro.size());

        ArrayList<Producto> lista = new ArrayList<>();
        lista.add(c);
        lista.add(a);
        Collections.sort(lista);
        System.out.println("Primero tras ordenar (el más barato): " + lista.get(0).getNombre());

        Producto d = new ProductoDescontablePrueba("P003", "Monitor", 500.00, 5);
        System.out.println("d es Descontable: " + (d instanceof Descontable));
        System.out.println("a es Descontable: " + (a instanceof Descontable));
        ((Descontable) d).aplicarDescuento(10);
        System.out.println("Precio con 10% de descuento: " + d.getPrecio());

        System.out.println(a);
    }
}
