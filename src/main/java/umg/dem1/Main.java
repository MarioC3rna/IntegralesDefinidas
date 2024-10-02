package umg.dem1;

import umg.dem1.BaseIntegrales.BaseLogica;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar la función al usuario
        System.out.println("Introduce la función a integrar X o Y:");
        String funcionStr = scanner.nextLine();

        // Solicitar el eje de integración
        System.out.println("¿En qué eje deseas trabajar? (x/y):");
        String eje = scanner.nextLine();

        // Solicitar los límites de integración
        System.out.println("Introduce el límite inferior de integración para " + eje + ":");
        double a = scanner.nextDouble();

        System.out.println("Introduce el límite superior de integración para " + eje + ":");
        double b = scanner.nextDouble();

        // Convertir la cadena de la función a una BiFunction<Double, Double, Double>
        BiFunction<Double, Double, Double> funcion = (x, y) -> {
            Expression e = new ExpressionBuilder(funcionStr)
                    .variables("x", "y")
                    .build()
                    .setVariable("x", x)
                    .setVariable("y", y);
            return e.evaluate();
        };

        BaseLogica calculadora = new BaseLogica();
        double resultado;
        if (eje.equalsIgnoreCase("x")) {
            resultado = calculadora.integrar((x, y) -> funcion.apply(x, 0.0), a, b, 0, 0);
        } else {
            resultado = calculadora.integrar((x, y) -> funcion.apply(0.0, y), 0, 0, a, b);
        }

        // Redondear el resultado a 3 dígitos decimales
        System.out.printf("Resultado de la integral: %.3f%n", resultado);

        // Crear la serie de datos para la función
        XYSeries series = new XYSeries("Función");
        if (eje.equalsIgnoreCase("x")) {
            for (double x = a; x <= b; x += (b - a) / 100) {
                series.add(x, funcion.apply(x, 0.0));
            }
        } else {
            for (double y = a; y <= b; y += (b - a) / 100) {
                series.add(y, funcion.apply(0.0, y));
            }
        }

        // Crear el conjunto de datos y añadir la serie
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gráfico de la Función",
                eje.toUpperCase(),
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Mostrar el gráfico en una ventana
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gráfico");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChartPanel(chart));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}