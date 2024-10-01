package umg.dem1.BaseIntegrales;

import java.util.function.Function;

public class BaseLogica {


    // Método para integrar una función en un intervalo dado [a, b]
    public double integrar(Function<Double, Double> funcion, double a, double b){

        double h = (b - a) / 1000;
        double suma = 0.5 * (funcion.apply(a) + funcion.apply(b));
        for (int i = 1; i < 1000; i++) {
            double x = a + i * h;
            suma += funcion.apply(x);
        }
        return suma * h;
    }
}