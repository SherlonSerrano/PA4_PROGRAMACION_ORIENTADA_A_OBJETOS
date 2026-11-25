package pe.edu.isil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventario inventario = new Inventario();
        ReporteInventario reporte = new ReporteInventario();

        // Carga inicial
        inventario.cargarDatosIniciales();

        int opcion;

        do {
            System.out.println("\n--- SISTEMA DE GESTIÓN COMPUTEC ---");
            System.out.println("1. Registrar Producto (Laptop / Procesador / Accesorio)");
            System.out.println("2. Listar Todos los Productos");
            System.out.println("3. Listar Productos por Tipo");
            System.out.println("4. Buscar Producto por Código");
            System.out.println("5. Eliminar Producto por Código");
            System.out.println("6. Eliminar Productos sin Stock");
            System.out.println("7. Aplicar Descuento General");
            System.out.println("8. Generar Reportes");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.println("\n[REGISTRAR PRODUCTO]");
                    System.out.println("Tipo: 1. Laptop | 2. Procesador | 3. Accesorio");
                    System.out.print("Elija el tipo: ");
                    int tipoProd = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer

                    System.out.print("Código: ");
                    String codigo = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Precio: ");
                    double precio = scanner.nextDouble();
                    System.out.print("Stock: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer

                    Producto nuevo = null;

                    if (tipoProd == 1) {
                        System.out.print("RAM (GB): ");
                        int ramGB = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Procesador: ");
                        String proc = scanner.nextLine();
                        nuevo = new Laptop(codigo, nombre, precio, stock, ramGB, proc);
                    } else if (tipoProd == 2) {
                        System.out.print("Núcleos: ");
                        int nucleos = scanner.nextInt();
                        System.out.print("Frecuencia (GHz): ");
                        double freq = scanner.nextDouble();
                        scanner.nextLine();
                        nuevo = new Procesador(codigo, nombre, precio, stock, nucleos, freq);
                    } else if (tipoProd == 3) {
                        System.out.print("Tipo de accesorio: ");
                        String tipoAcc = scanner.nextLine();
                        nuevo = new Accesorio(codigo, nombre, precio, stock, tipoAcc);
                    } else {
                        System.out.println("Tipo de producto no válido.");
                        break;
                    }

                    // Agregar producto
                    if (inventario.agregar(nuevo)) {
                        System.out.println("¡Producto registrado con éxito!");
                    } else {
                        System.out.println("Error: Ya existe un producto con ese código.");
                    }
                    break;

                case 2:
                    System.out.println("\n[LISTADO GENERAL]");
                    inventario.listar();
                    break;

                case 3:
                    System.out.println("\n[LISTAR POR TIPO]");
                    System.out.print("Ingrese el tipo (Laptop / Procesador / Accesorio): ");
                    String tipoBuscar = scanner.nextLine();
                    inventario.listarPorTipo(tipoBuscar);
                    break;

                case 4:
                    System.out.println("\n[BUSCAR PRODUCTO]");
                    System.out.print("Ingrese código a buscar: ");
                    String codBuscar = scanner.nextLine();
                    Producto encontrado = inventario.buscarPorCodigo(codBuscar);
                    if (encontrado != null) {
                        System.out.println("Encontrado: " + encontrado);
                    } else {
                        System.out.println("No se encontró ningún producto con ese código.");
                    }
                    break;

                case 5:
                    System.out.println("\n[ELIMINAR PRODUCTO]");
                    System.out.print("Ingrese código a eliminar: ");
                    String codEliminar = scanner.nextLine();
                    if (inventario.eliminarPorCodigo(codEliminar)) {
                        System.out.println("¡Producto eliminado con éxito!");
                    } else {
                        System.out.println("No se encontró ningún producto con ese código.");
                    }
                    break;

                case 6:
                    System.out.println("\n[ELIMINAR SIN STOCK]");
                    int eliminados = inventario.eliminarSinStock();
                    System.out.println("Productos eliminados con stock 0: " + eliminados);
                    break;

                case 7:
                    System.out.println("\n[APLICAR DESCUENTO GENERAL]");
                    System.out.print("Ingrese el porcentaje de descuento (%): ");
                    double porcentaje = scanner.nextDouble();
                    scanner.nextLine(); // Limpiar buffer
                    int aplicados = inventario.aplicarDescuentoGeneral(porcentaje);
                    System.out.println("Descuento aplicado a " + aplicados + " productos.");
                    break;

                case 8:
                    int opReporte;
                    do {
                        System.out.println("\n--- SUBMENÚ DE REPORTES ---");
                        System.out.println("1. Ordenar por precio (Menor a Mayor)");
                        System.out.println("2. Ordenar por precio (Mayor a Menor)");
                        System.out.println("3. Mostrar producto más caro y más barato");
                        System.out.println("4. Ordenar por nombre (A - Z)");
                        System.out.println("5. Contar productos por tipo");
                        System.out.println("6. Calcular valor total del inventario");
                        System.out.println("7. Volver al menú principal");
                        System.out.print("Seleccione una opción: ");
                        opReporte = scanner.nextInt();
                        scanner.nextLine(); // Limpiar buffer

                        switch (opReporte) {
                            case 1:
                                reporte.listarPorPrecioAsc(inventario.getProductos());
                                break;
                            case 2:
                                reporte.listarPorPrecioDesc(inventario.getProductos());
                                break;
                            case 3:
                                reporte.mostrarExtremos(inventario.getProductos());
                                break;
                            case 4:
                                reporte.listarPorNombreAZ(inventario.getProductos());
                                break;
                            case 5:
                                reporte.contarPorTipo(inventario.getProductos());
                                break;
                            case 6:
                                reporte.calcularValorTotal(inventario.getProductos());
                                break;
                            case 7:
                                System.out.println("Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("Opción no válida.");
                        }
                    } while (opReporte != 7);
                    break;

                case 9:
                    System.out.println("Saliendo del sistema Computec... ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 9);

        scanner.close();
    }
}
