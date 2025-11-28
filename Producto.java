public class Producto {
    private String nombre;
    private double precioBase; 

    public Producto(String nombre, double precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    public String getNombre() { return nombre; }
    public double getPrecioBase() { return precioBase; }
}