package umg.dem1.BaseIntegrales;

import java.util.function.BiFunction;

public class BaseLogica {

    // Método para integrar una función en un intervalo dado [a, b] y [c, d]
    public double integrar(BiFunction<Double, Double, Double> funcion, double a, double b, double c, double d) {
        int n = 1000; // Número de subdivisiones
        double hx = (b - a) / n;
        double hy = (d - c) / n;
        double suma = 0.0;

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                double x = a + i * hx;
                double y = c + j * hy;
                suma += funcion.apply(x, y);
            }
        }
        return suma * hx * hy;
    }
}