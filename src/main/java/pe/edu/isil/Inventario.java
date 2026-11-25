package pe.edu.isil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

// Gestiona la colección de productos del inventario.
// - ArrayList: mantiene los productos en orden y permite ordenarlos.
// - HashSet: detecta códigos duplicados rápidamente (usa equals/hashCode de Producto).
public class Inventario {

    private List<Producto> productos;
    private Set<Producto> registrados;

    // Constructor: crea las colecciones vacías
    public Inventario() {
        this.productos = new ArrayList<>();
        this.registrados = new HashSet<>();
    }

    // Carga una lista inicial de 20 productos de ejemplo.
    // Memorias, almacenamiento y tarjetas de video se registran como Accesorio
    // porque el proyecto solo tiene las clases Laptop, Procesador y Accesorio.
    public void cargarDatosIniciales() {
        // Laptop(codigo, nombre, precio, stock, ramGB, procesador)
        agregar(new Laptop("P001", "Laptop Lenovo IdeaPad", 2499.90, 10, 16, "Intel Core i5"));
        agregar(new Laptop("P002", "Laptop Lenovo V15", 2299.90, 2, 8, "AMD Ryzen 5"));
        agregar(new Laptop("P003", "Laptop Lenovo ThinkBook 14", 3599.90, 5, 16, "Intel Core i7"));
        agregar(new Laptop("P004", "Laptop HP EliteBook", 3499.90, 9, 16, "Intel Core i7"));
        agregar(new Laptop("P005", "Laptop Asus ExpertBook", 3349.90, 9, 16, "Intel Core i5"));

        // Procesador(codigo, nombre, precio, stock, nucleos, frecuenciaGHz)
        agregar(new Procesador("P006", "Ryzen 5 5600", 549.00, 20, 6, 4.4));
        agregar(new Procesador("P007", "Ryzen 7 5700", 799.00, 20, 8, 4.6));
        agregar(new Procesador("P008", "Intel core i7-1200F", 680.00, 5, 12, 4.9));
        agregar(new Procesador("P009", "Intel core i9-12900KF", 1797.00, 15, 16, 5.2));
        agregar(new Procesador("P010", "Intel core i7-14700K", 1727.50, 1, 20, 5.6));

        // Accesorio(codigo, nombre, precio, stock, tipoAccesorio)
        agregar(new Accesorio("P011", "RAM 16GB DDR4", 523.00, 35, "Memoria RAM"));
        agregar(new Accesorio("P012", "RAM 16GB DDR5", 1309.50, 35, "Memoria RAM"));
        agregar(new Accesorio("P013", "RAM 32GB DDR5", 2537.50, 35, "Memoria RAM"));
        agregar(new Accesorio("P014", "SSD NVMe 512GB", 410.00, 25, "Almacenamiento"));
        agregar(new Accesorio("P015", "SSD NVMe 1TB", 678.00, 25, "Almacenamiento"));
        agregar(new Accesorio("P016", "SSD NVMe 2TB", 1169.00, 25, "Almacenamiento"));
        agregar(new Accesorio("P017", "RTX 3050", 994.50, 8, "Tarjeta de video"));
        agregar(new Accesorio("P018", "RTX 4060", 1499.00, 8, "Tarjeta de video"));
        agregar(new Accesorio("P019", "RTX 4070", 2699.00, 8, "Tarjeta de video"));
        agregar(new Accesorio("P020", "RTX 4090", 7299.00, 8, "Tarjeta de video"));
    }

    // Agrega un producto. Devuelve false si es null o si ya existe un producto con ese código.
    public boolean agregar(Producto producto) {
        if (producto == null) {
            return false;
        }
        // HashSet.add devuelve false si ya contiene un elemento igual (mismo código)
        if (!registrados.add(producto)) {
            return false;
        }
        productos.add(producto);
        return true;
    }

    // Busca un producto por su código (sin distinguir mayúsculas). Devuelve null si no existe.
    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    // Elimina un producto por código usando Iterator.
    // Con Iterator se puede borrar mientras se recorre; con un for each daría error.
    public boolean eliminarPorCodigo(String codigo) {
        Iterator<Producto> it = productos.iterator();
        while (it.hasNext()) {
            Producto p = it.next();
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                it.remove();
                registrados.remove(p);
                return true;
            }
        }
        return false;
    }

    // Elimina todos los productos con stock 0 usando removeIf.
    // Devuelve la cantidad de productos eliminados.
    public int eliminarSinStock() {
        int cantidadAntes = productos.size();
        productos.removeIf(p -> p.getStock() == 0);
        registrados.removeIf(p -> p.getStock() == 0);
        return cantidadAntes - productos.size();
    }

    // Muestra todos los productos en consola
    public void listar() {
        if (productos.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    // Ordena de menor a mayor precio usando el orden natural (Comparable) de Producto
    public void ordenarPorPrecio() {
        Collections.sort(productos);
    }

    // Muestra solo los productos del tipo indicado: "Laptop", "Procesador" o "Accesorio"
    public void listarPorTipo(String tipo) {
        boolean encontrado = false;
        for (Producto p : productos) {
            if (p.getTipo().equalsIgnoreCase(tipo)) {
                System.out.println(p);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay productos del tipo: " + tipo);
        }
    }

    // Aplica el descuento solo a los productos que implementan Descontable (instanceof).
    // Laptop y Accesorio lanzan IllegalArgumentException si el porcentaje supera su máximo;
    // en ese caso se omite ese producto y se continúa con los demás.
    // Devuelve la cantidad de productos a los que sí se les aplicó el descuento.
    public int aplicarDescuentoGeneral(double porcentaje) {
        int aplicados = 0;
        for (Producto p : productos) {
            if (p instanceof Descontable) {
                try {
                    ((Descontable) p).aplicarDescuento(porcentaje);
                    aplicados++;
                } catch (IllegalArgumentException e) {
                    System.out.println("No se aplicó a " + p.getCodigo() + ": " + e.getMessage());
                }
            }
        }
        return aplicados;
    }

    // Devuelve una copia de la lista: así otras clases (ReporteInventario) no alteran el inventario
    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }
}
