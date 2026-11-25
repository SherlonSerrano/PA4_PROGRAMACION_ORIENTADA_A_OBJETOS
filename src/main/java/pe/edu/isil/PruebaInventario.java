package pe.edu.isil;

import java.util.List;

public class PruebaInventario {

    private static int correctas = 0;
    private static int fallidas = 0;

    // Compara el resultado con lo esperado e imprime [OK] o [ERROR]
    private static void verificar(String descripcion, boolean condicion) {
        if (condicion) {
            correctas++;
            System.out.println("[OK]    " + descripcion);
        } else {
            fallidas++;
            System.out.println("[ERROR] " + descripcion);
        }
    }

    public static void main(String[] args) {

        Inventario inv = new Inventario();
        inv.cargarDatosIniciales();

        // ---------- 1. CARGA INICIAL ----------
        System.out.println("===== 1. CARGA INICIAL =====");
        inv.listar();
        verificar("Se cargaron 20 productos", inv.getProductos().size() == 20);

        // ---------- 2. AGREGAR ----------
        System.out.println("\n===== 2. AGREGAR =====");
        verificar("Agregar P021 nuevo devuelve true",
                inv.agregar(new Accesorio("P021", "Mouse Logitech", 90, 12, "Mouse")));
        verificar("Agregar p021 (código repetido) devuelve false",
                !inv.agregar(new Accesorio("p021", "Otro mouse", 80, 3, "Mouse")));
        verificar("Agregar P001 (ya existe) devuelve false",
                !inv.agregar(new Laptop("P001", "Laptop repetida", 1000, 1, 8, "Intel Core i3")));
        verificar("Agregar null devuelve false", !inv.agregar(null));
        verificar("El total es 21", inv.getProductos().size() == 21);

        // ---------- 3. BUSCAR ----------
        System.out.println("\n===== 3. BUSCAR POR CÓDIGO =====");
        Producto p = inv.buscarPorCodigo("P010");
        System.out.println(p);
        verificar("Buscar P010 encuentra el producto", p != null && p.getCodigo().equals("P010"));
        verificar("Buscar p010 (minúscula) lo encuentra", inv.buscarPorCodigo("p010") != null);
        verificar("Buscar X999 devuelve null", inv.buscarPorCodigo("X999") == null);

        // ---------- 4. ELIMINAR POR CÓDIGO (Iterator) ----------
        System.out.println("\n===== 4. ELIMINAR POR CÓDIGO (Iterator) =====");
        verificar("Eliminar P021 devuelve true", inv.eliminarPorCodigo("P021"));
        verificar("Eliminar P021 otra vez devuelve false", !inv.eliminarPorCodigo("P021"));
        verificar("P021 ya no se encuentra", inv.buscarPorCodigo("P021") == null);
        verificar("El total vuelve a 20", inv.getProductos().size() == 20);
        verificar("P021 se puede volver a agregar (el HashSet se actualizó)",
                inv.agregar(new Accesorio("P021", "Mouse Logitech", 90, 12, "Mouse")));
        inv.eliminarPorCodigo("P021");

        // ---------- 5. ELIMINAR SIN STOCK (removeIf) ----------
        System.out.println("\n===== 5. ELIMINAR SIN STOCK (removeIf) =====");
        verificar("Con los datos iniciales no hay productos sin stock (devuelve 0)",
                inv.eliminarSinStock() == 0);
        // Dejamos P002 y P010 sin stock para probar el método
        inv.buscarPorCodigo("P002").setStock(0);
        inv.buscarPorCodigo("P010").setStock(0);
        verificar("Tras poner P002 y P010 en 0, eliminarSinStock devuelve 2",
                inv.eliminarSinStock() == 2);
        verificar("P002 y P010 ya no están",
                inv.buscarPorCodigo("P002") == null && inv.buscarPorCodigo("P010") == null);
        verificar("El total es 18", inv.getProductos().size() == 18);

        // ---------- 6. ORDENAR POR PRECIO ----------
        System.out.println("\n===== 6. ORDENAR POR PRECIO =====");
        inv.ordenarPorPrecio();
        inv.listar();
        List<Producto> lista = inv.getProductos();
        boolean ordenado = true;
        for (int i = 1; i < lista.size(); i++) {
            if (lista.get(i - 1).getPrecio() > lista.get(i).getPrecio()) {
                ordenado = false;
            }
        }
        verificar("Los precios están de menor a mayor", ordenado);
        verificar("El más barato es P014 (S/ 410.00)", lista.get(0).getCodigo().equals("P014"));
        verificar("El más caro es P020 (S/ 7299.00)",
                lista.get(lista.size() - 1).getCodigo().equals("P020"));

        // ---------- 7. LISTAR POR TIPO ----------
        System.out.println("\n===== 7. LISTAR POR TIPO =====");
        System.out.println("-- Laptop (esperado: 4) --");
        inv.listarPorTipo("Laptop");
        System.out.println("-- Procesador (esperado: 4) --");
        inv.listarPorTipo("Procesador");
        System.out.println("-- accesorio, en minúscula (esperado: 10) --");
        inv.listarPorTipo("accesorio");
        System.out.println("-- Monitor (no existe) --");
        inv.listarPorTipo("Monitor");

        // ---------- 8. DESCUENTO GENERAL (instanceof Descontable) ----------
        System.out.println("\n===== 8. DESCUENTO GENERAL =====");
        double precioLaptop = inv.buscarPorCodigo("P001").getPrecio();
        double precioProcesador = inv.buscarPorCodigo("P006").getPrecio();
        double precioAccesorio = inv.buscarPorCodigo("P011").getPrecio();

        System.out.println("-- Descuento de 25% (supera el máximo de todos) --");
        verificar("Con 25% no se aplica a ningún producto (devuelve 0)",
                inv.aplicarDescuentoGeneral(25) == 0);

        System.out.println("-- Descuento de 15% (solo laptops) --");
        verificar("Con 15% se aplica solo a las 4 laptops",
                inv.aplicarDescuentoGeneral(15) == 4);
        verificar("El precio de P001 bajó 15%",
                Math.abs(inv.buscarPorCodigo("P001").getPrecio() - precioLaptop * 0.85) < 0.001);
        verificar("El precio de P011 (accesorio) no cambió",
                inv.buscarPorCodigo("P011").getPrecio() == precioAccesorio);

        System.out.println("-- Descuento de 10% (laptops y accesorios) --");
        verificar("Con 10% se aplica a 4 laptops + 10 accesorios = 14",
                inv.aplicarDescuentoGeneral(10) == 14);
        verificar("El precio de P006 (Procesador, no Descontable) no cambió",
                inv.buscarPorCodigo("P006").getPrecio() == precioProcesador);
        verificar("El precio de P011 bajó 10%",
                Math.abs(inv.buscarPorCodigo("P011").getPrecio() - precioAccesorio * 0.90) < 0.001);

        // ---------- 9. getProductos() DEVUELVE UNA COPIA ----------
        System.out.println("\n===== 9. getProductos() DEVUELVE UNA COPIA =====");
        inv.getProductos().clear();
        verificar("Vaciar la copia no afecta al inventario (siguen 18)",
                inv.getProductos().size() == 18);

        // ---------- RESUMEN ----------
        System.out.println("\n===== RESUMEN =====");
        System.out.println("Pruebas correctas: " + correctas);
        System.out.println("Pruebas fallidas:  " + fallidas);
        if (fallidas == 0) {
            System.out.println("Todo funciona correctamente.");
        }
    }
}
