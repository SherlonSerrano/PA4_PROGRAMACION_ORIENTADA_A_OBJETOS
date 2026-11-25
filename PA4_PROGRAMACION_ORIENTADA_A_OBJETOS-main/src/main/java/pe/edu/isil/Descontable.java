package pe.edu.isil;

// Interface: define un comportamiento que pueden adoptar clases no necesariamente relacionadas.
// Solo declara el método; cada clase que la implemente decide cómo aplicarlo.
public interface Descontable {

    // Aplica un porcentaje de descuento al precio del producto
    void aplicarDescuento(double porcentaje);
}
