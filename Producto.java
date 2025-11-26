public class Producto {
    private String nombre;
    private double precioBase; 

    public Producto(String nombre, double precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

   
    }


public class Moneda {
    private String codigo; 


    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return codigo;
    }
}