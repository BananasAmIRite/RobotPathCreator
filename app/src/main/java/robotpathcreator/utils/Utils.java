package robotpathcreator.utils;

import java.util.function.Function;

public final class Utils {
    public static double integrate(Function<Double, Double> func, double a, double b, double precision) {
        double sum = 0;
        for (double i = a; i < b; i += precision) {
            sum += func.apply(i) * precision;
        }
        return sum;
    }
}
