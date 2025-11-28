public class Moneda {
    private String codigo; 

    public Moneda(String codigo) {
        this.codigo = codigo.toUpperCase();
    }
    public String getCodigo() { return codigo; }
}