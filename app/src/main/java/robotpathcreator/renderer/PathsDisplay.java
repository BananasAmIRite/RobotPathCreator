package robotpathcreator.renderer;

import robotpathcreator.Canvas;
import robotpathcreator.Coordinate;
import robotpathcreator.Path;
import robotpathcreator.PathPoint;

import java.awt.*;
import java.util.List;

public class PathsDisplay extends Canvas {
    private Coordinate<Double> focalPoint = new Coordinate<>(0d, 0d); // in trajectory units
    private double zoom = 10;
    private final int nodeRadius = 5;

    private FieldImage fieldImage; 

    private PathPointsList paths;

    private final PathInteractionListener listener = new PathInteractionListener(this);

    public PathsDisplay(PathPointsList paths) {
        super();
        this.paths = paths;
        this.addMouseListener(listener);
        this.addKeyListener(listener);
        this.addMouseMotionListener(listener);
        this.addMouseWheelListener(listener);
    }

    public void setFocalPoint(Coordinate<Double> focalPoint) {
        this.focalPoint = focalPoint;
    }

    public Coordinate<Double> getFocalPoint() {
        return focalPoint;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void setPaths(PathPointsList paths) {
        this.paths = paths;
    }

    public int getNodeRadius() {
        return nodeRadius;
    }

    public PathPointsList getPathsList() {
        return paths;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getImageWidth(), getImageHeight());

        if (this.fieldImage != null) {
            Coordinate<Integer> renderStart = this.fieldImage.getRenderStartCoordinates(this.zoom);
            Dimension dimensions = this.fieldImage.getImageDimensions(this.zoom);

            g.drawImage(renderStart.getX(), renderStart.getY(), dimensions.getWidth(), dimensions.getHeight());  
        }

        if (this.paths == null) return;

        List<Path> trajectory = this.paths.getTrajectory().calculateTrajectory();
        g.setColor(Color.BLACK);
        for (Path p : trajectory) {
            Coordinate<Double> lastCoords = null;

            for (double j = 0; j < p.getTravelTime(); j += (p.getTravelTime() / 100)) {
                if (lastCoords == null) {
                    lastCoords = p.getPointAt(j);
                } else {
                    Coordinate<Double> curCoords = p.getPointAt(j);

                    Coordinate<Integer> curCanvasCoords = toCanvasCoords(curCoords);
                    Coordinate<Integer> lastCanvasCoords = toCanvasCoords(lastCoords);

                    lastCoords = curCoords;

                    g.drawLine(lastCanvasCoords.getX(), lastCanvasCoords.getY(), curCanvasCoords.getX(), curCanvasCoords.getY());
                }
            }
        }

        for (int i = 0; i < this.paths.getPoints().size(); i++) {
            PathPoint p = this.paths.getPoints().elementAt(i);

            Coordinate<Integer> c = toCanvasCoords(p.getPosition());


            if (this.getPathsList().getSelectedValue() == p) {
                g.setColor(Color.GRAY);
                Coordinate<Integer> velocityCnvPoint = toCanvasCoords(p.getVelocityCoordinates());
                g.drawLine(c.getX(), c.getY(), velocityCnvPoint.getX(), velocityCnvPoint.getY());
                g.fillOval(velocityCnvPoint.getX() - this.nodeRadius, velocityCnvPoint.getY() - this.nodeRadius, this.nodeRadius * 2, this.nodeRadius * 2);
            }

            g.setColor(this.getPathsList().getSelectedValue() == p ? Color.BLUE : Color.BLACK);
            g.fillOval(c.getX() - this.nodeRadius, c.getY() - this.nodeRadius, this.nodeRadius*2, this.nodeRadius*2);

            g.setColor(Color.BLACK);
        }
    }

    public Coordinate<Integer> toCanvasCoords(Coordinate<Double> coords) {
        // transform the current coords
        double worldX = this.zoom * (coords.getX() - this.focalPoint.getX());
        double worldY = this.zoom * (coords.getY() - this.focalPoint.getY());

        return new Coordinate<>((int) (worldX + this.getImageWidth() / 2), (int) (-worldY + this.getImageHeight() / 2));
    }

    public Coordinate<Double> fromCanvasCoords(Coordinate<Integer> coords) {
        double worldX = coords.getX() - (double) this.getImageWidth() / 2;
        double worldY = -coords.getY() + (double) this.getImageHeight() / 2;

        return new Coordinate<>(
                worldX / this.zoom + this.focalPoint.getX(),
                worldY / this.zoom + this.focalPoint.getY()
        );
    }

    public double fromCanvasValueX(double val) {
        return val / this.zoom;
    }

    public double fromCanvasValueY(double val) {
        return -val / this.zoom;
    }

    public void setFieldImage(FieldImage image) {
        this.fieldImage = image; 
    }
}
