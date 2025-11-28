import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Main {

    static class Producto {
        String nombre;
        double precioUSD;

        public Producto(String nombre, double precioUSD) {
            this.nombre = nombre;
            this.precioUSD = precioUSD;
        }

        @Override
        public String toString() {
            return nombre + " ($" + precioUSD + " USD)";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Producto> inventario = new ArrayList<>();

        inventario.add(new Producto("PlayStation 5", 499.99));
        inventario.add(new Producto("iPhone 15", 799.00));
        inventario.add(new Producto("Monitor Samsung", 250.00));
        inventario.add(new Producto("MacBook Air M2", 999.00));
        inventario.add(new Producto("Nintendo Switch OLED", 349.99));
        inventario.add(new Producto("Sony Auriculares XM5", 348.00));
        inventario.add(new Producto("iPad Air 5ta Gen", 599.00));
        inventario.add(new Producto("Xbox Series X", 499.00));
        inventario.add(new Producto("GoPro Hero 12", 399.99));
        inventario.add(new Producto("Samsung Galaxy Watch 6", 299.50));
        inventario.add(new Producto("Drone DJI Mini 3", 559.00));
        inventario.add(new Producto("Tarjeta Grafica RTX 4060", 299.99));
        inventario.add(new Producto("Amazon Echo Dot", 49.99));

        System.out.println("TIENDA IMPORTADORA COLOMBIA");
        System.out.println("Lista de Precios en Dolares:");

        for (int i = 0; i < inventario.size(); i++) {
            System.out.println((i + 1) + ". " + inventario.get(i));
        }

        System.out.print("Selecciona el numero del producto (1-" + inventario.size() + "): ");
        int opcion = scanner.nextInt();

        if (opcion < 1 || opcion > inventario.size()) {
            System.out.println("Opcion no valida.");
            return;
        }

        Producto producto = inventario.get(opcion - 1);

        System.out.println("Conectando con servidor financiero...");

        double tasaCOP = obtenerTasaCOP();

        if (tasaCOP > 0) {
            BiFunction<Double, Double, Double> calcular = (precio, tasa) -> precio * tasa;
            double precioFinal = calcular.apply(producto.precioUSD, tasaCOP);

            System.out.println("CONEXION EXITOSA");
            System.out.println("Producto:  " + producto.nombre);
            System.out.println("Precio:    $" + producto.precioUSD + " USD");
            
            System.out.printf("Tasa Hoy: COP", tasaCOP); 
            
          
            System.out.printf("TOTAL A PAGAR: COP", precioFinal);
        } else {
            System.out.println("ERROR CRITICO: No se pudo obtener la tasa.");
            System.out.println("Revisa tu conexion a internet.");
        }
        scanner.close();
    }

    public static double obtenerTasaCOP() {
        try {
            URL url = new URL("https://open.er-api.com/v6/latest/USD");

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            conexion.setRequestProperty("User-Agent", "Mozilla/5.0");
            conexion.setConnectTimeout(5000);

            int codigo = conexion.getResponseCode();

            if (codigo == 200) {
                Scanner scan = new Scanner(url.openStream());
                String json = scan.useDelimiter("\\A").next();
                scan.close();

                return extraerValorManual(json);
            } else {
                System.out.println("Error del servidor: Codigo " + codigo);
            }
        } catch (Exception e) {
            System.out.println("Detalle del error: " + e.toString());
        }
        return 0.0;
    }

    private static double extraerValorManual(String json) {
        try {
            String clave = "\"COP\":";
            int inicio = json.indexOf(clave);

            if (inicio != -1) {
                inicio += clave.length();
                int fin = json.indexOf(",", inicio);
                if (fin == -1) fin = json.indexOf("}", inicio);

                String numeroStr = json.substring(inicio, fin);
                return Double.parseDouble(numeroStr);
            }
        } catch (Exception e) {
            System.out.println("Error leyendo el numero del JSON.");
        }
        return 0.0;
    }
}