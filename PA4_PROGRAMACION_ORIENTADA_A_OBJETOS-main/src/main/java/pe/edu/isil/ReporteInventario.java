package pe.edu.isil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteInventario {

    public void listarPorPrecioAsc(List<Producto> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        List<Producto> copia = new ArrayList<>(lista);
        Collections.sort(copia);
        System.out.println("\n--- PRODUCTOS (MENOR A MAYOR PRECIO) ---");
        for (Producto p : copia) {
            System.out.println(p);
        }
    }

    public void listarPorPrecioDesc(List<Producto> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        List<Producto> copia = new ArrayList<>(lista);
        copia.sort(Collections.reverseOrder());
        System.out.println("\n--- PRODUCTOS (MAYOR A MENOR PRECIO) ---");
        for (Producto p : copia) {
            System.out.println(p);
        }
    }

    public void mostrarExtremos(List<Producto> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay productos para analizar.");
            return;
        }
        System.out.println("\n--- EXTREMOS DE PRECIO ---");
        System.out.println("Más caro: " + Collections.max(lista));
        System.out.println("Más barato: " + Collections.min(lista));
    }

    public void listarPorNombreAZ(List<Producto> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        List<Producto> copia = new ArrayList<>(lista);
        copia.sort(Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER));
        System.out.println("\n--- PRODUCTOS (A - Z) ---");
        for (Producto p : copia) {
            System.out.println(p);
        }
    }

    public void contarPorTipo(List<Producto> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.println("\n--- CANTIDAD POR TIPO ---");
        Map<String, Long> conteo = lista.stream()
                .collect(Collectors.groupingBy(Producto::getTipo, Collectors.counting()));

        conteo.forEach((tipo, cantidad) ->
                System.out.println("Tipo: " + tipo + " | Cantidad: " + cantidad)
        );
    }

    public void calcularValorTotal(List<Producto> lista) {
        double total = 0;
        for (Producto p : lista) {
            total += p.getPrecio() * p.getStock();
        }
        System.out.println("\n--- VALOR TOTAL EN ALMACÉN ---");
        System.out.println("Total: S/ " + String.format("%.2f", total));
    }
}
