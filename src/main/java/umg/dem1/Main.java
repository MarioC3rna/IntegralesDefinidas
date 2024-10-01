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
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar la función al usuario
        System.out.println("Introduce la función a integrar (por ejemplo, 'sin(x)'):");
        String funcionStr = scanner.nextLine();

        // Solicitar los límites de integración
        System.out.println("Introduce el límite inferior de integración:");
        double a = scanner.nextDouble();

        System.out.println("Introduce el límite superior de integración:");
        double b = scanner.nextDouble();

        // Convertir la cadena de la función a una Function<Double, Double>
        Function<Double, Double> funcion = x -> {
            Expression e = new ExpressionBuilder(funcionStr)
                    .variable("x")
                    .build()
                    .setVariable("x", x);
            return e.evaluate();
        };

        BaseLogica calculadora = new BaseLogica();
        double resultado = calculadora.integrar(funcion, a, b);

        // Redondear el resultado a 3 dígitos decimales
        System.out.printf("Resultado de la integral: %.3f%n", resultado);

        // Crear la serie de datos para la función
        XYSeries series = new XYSeries("Función");
        for (double x = a; x <= b; x += (b - a) / 1000) {
            series.add(x, funcion.apply(x));
        }

        // Crear el conjunto de datos y añadir la serie
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gráfico de la Función",
                "X",
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