package robotpathcreator.data;

public class Coordinate<T extends Number> {
    private final T x;
    private final T y;

    public Coordinate(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinate{x: " + x + ", y: " + y + "}";
    }
}
