package robotpathcreator;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Path {

    private final double[] xCoeffs;
    private final double[] yCoeffs;

    private final PathPoint startPoint;
    private final PathPoint endPoint;

    public Path(PathPoint a, PathPoint b) {
        this.startPoint = a;
        this.endPoint = b;
        this.xCoeffs = calculateCoeffs(
            a.getTravelTime(), 
            a.getX(), 
            b.getX(), 
            a.getVelocity() * Math.cos(a.getAngle()), 
            b.getVelocity() * Math.cos(b.getAngle())
        ); 

        this.yCoeffs = calculateCoeffs(
            a.getTravelTime(), 
            a.getY(), 
            b.getY(), 
            a.getVelocity() * Math.sin(a.getAngle()), 
            b.getVelocity() * Math.sin(b.getAngle())
        ); 
    }

    private double[] calculateCoeffs(double time, double p0, double p1, double v0, double v1) {
        /*
        // for each x and y: 
        f(x)=ax^5+bx^4+cx^3+dx^2+ex+f
        f'(x)=5ax^4+4bx^3+3cx^2+2dx+e
        f''(x)=20ax^3+12bx^2+6cx+2d
        f'''(x)=60ax^2+24bx+6c
        [
            0, 0, 0, 0, 0, 1 -> x0                      // f(t0) = x0
            t^5, t^4, t^3, t^2, t, 1 -> x1              // f(t1) = x1
            0, 0, 0, 0, 1, 0 -> v0costheta              // f'(t0) = v0costheta
            5t^4, 4t^3, 3t^2, 2t, 1, 0 -> v1costheta    // f'(t1) = v1costheta
            0, 0, 6, 0, 0, 0 -> 0                       // f'''(t0) = 0
            60t^2, 24t, 6, 0, 0, 0 -> 0                 // f'''(t1) = 0
        ]
         */

         SimpleMatrix coeffsMat = new SimpleMatrix(new double[][] {
            new double[] {0, 0, 0, 0, 0, 1}, 
            new double[] {Math.pow(time, 5), Math.pow(time, 4), Math.pow(time, 3), Math.pow(time, 2), time, 1}, 
            new double[] {0, 0, 0, 0, 1, 0}, 
            new double[] {5*Math.pow(time, 4), 4*Math.pow(time, 3), 3*Math.pow(time, 2), 2*time, 1, 0}, 
            new double[] {0, 0, 6, 0, 0, 0}, 
            new double[] {60*Math.pow(time, 2), 24 * time, 6, 0, 0, 0} 
         }); 

         SimpleMatrix answMat = new SimpleMatrix(new double[][] {
            new double[] {p0}, 
            new double[] {p1}, 
            new double[] {v0}, 
            new double[] {v1}, 
            new double[] {0}, 
            new double[] {0} 
         }); 

         SimpleMatrix solutions = coeffsMat.invert().mult(answMat); 
         return new double[] {
            solutions.get(0, 0), 
            solutions.get(1, 0), 
            solutions.get(2, 0), 
            solutions.get(3, 0), 
            solutions.get(4, 0), 
            solutions.get(5, 0)
        }; 
    }

    public PathPoint getStartPoint() {
        return startPoint;
    }

    public PathPoint getEndPoint() {
        return endPoint;
    }


    public double getTravelTime() {
        return startPoint.getTravelTime();
    }

    public Coordinate<Double> getPointAt(double time) {
        return new Coordinate<>(
                IntStream.range(0, xCoeffs.length).asDoubleStream().reduce(0, (a, i) -> a + xCoeffs[(int) i] * Math.pow(time, xCoeffs.length - i - 1)),
                IntStream.range(0, yCoeffs.length).asDoubleStream().reduce(0, (a, i) -> a + yCoeffs[(int) i] * Math.pow(time, yCoeffs.length - i - 1))
        );
    }
}