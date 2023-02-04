package robotpathcreator.utils;

public class NumberUtils {
    public static boolean isNumeric(String s) {
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    public static Number toNumber(String s) {
        return Double.parseDouble(s);
    }
}
