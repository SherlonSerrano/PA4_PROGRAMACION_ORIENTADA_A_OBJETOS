package pe.edu.isil;

public class PruebaProducto {

    public static void main(String[] args) {

        // Crear una laptop
        Laptop laptop = new Laptop(
                "L001", "ASUS TUF Gaming", 3500, 5,
                16, "Intel Core i7"
        );

        // Crear un procesador
        Procesador procesador = new Procesador(
                "P001", "Intel Core i9", 2200, 10,
                16, 5.8
        );

        // Crear un accesorio
        Accesorio accesorio = new Accesorio(
                "A001", "Teclado Logitech", 250, 8,
                "Teclado mecánico"
        );

        // Mostrar productos
        System.out.println("===== PRODUCTOS =====");

        System.out.println(laptop);
        System.out.println(procesador);
        System.out.println(accesorio);

        // Aplicar descuentos
        laptop.aplicarDescuento(20);
        accesorio.aplicarDescuento(10);

        // Mostrar precios actualizados
        System.out.println("\n===== PRECIOS CON DESCUENTO =====");

        System.out.println("Laptop: S/ " + laptop.getPrecio());
        System.out.println("Procesador: S/ " + procesador.getPrecio());
        System.out.println("Accesorio: S/ " + accesorio.getPrecio());
        // Prueba 1: descuento superior al permitido para Laptop
        System.out.println("\n===== PRUEBA DE LIMITES =====");

        try {
            laptop.aplicarDescuento(25);
            System.out.println("Error: se aceptó un descuento no permitido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Laptop: " + e.getMessage());
        }

        // Prueba 2: descuento superior al permitido para Accesorio
        try {
            accesorio.aplicarDescuento(15);
            System.out.println("Error: se aceptó un descuento no permitido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Accesorio: " + e.getMessage());
        }
    }
}
