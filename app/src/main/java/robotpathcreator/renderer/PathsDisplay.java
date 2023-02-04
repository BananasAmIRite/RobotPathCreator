package robotpathcreator.renderer;

import org.bananasamirite.robotmotionprofile.ParametricSpline;
import org.bananasamirite.robotmotionprofile.Waypoint;
import org.bananasamirite.robotmotionprofile.data.RobotConfiguration;
import org.bananasamirite.robotmotionprofile.data.Trajectory;
import org.bananasamirite.robotmotionprofile.data.task.CommandTask;
import org.bananasamirite.robotmotionprofile.data.task.TrajectoryTask;
import org.bananasamirite.robotmotionprofile.data.task.WaypointTask;
import robotpathcreator.*;
import robotpathcreator.Canvas;

import java.awt.*;
import java.util.ArrayList;
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
            Coordinate<Integer> renderStart = toCanvasCoords(this.fieldImage.getRenderStartCoordinates());
            Dimension dimensions = this.fieldImage.getImageDimensions(this.zoom);
            System.out.println(this.fieldImage.getRenderStartCoordinates().getX());

            g.drawImage(this.fieldImage.getImage(),
                    renderStart.getX(),
                    renderStart.getY(),
//                    getImageHeight() / 2 + renderStart.getY() + toCanvasCoords(getFocalPoint()).getY(),
                    (int) dimensions.getWidth(), (int) dimensions.getHeight(),
                    null);
        }

        if (this.paths == null) return;

//        List<Path> trajectory = this.paths.getTrajectory().calculateTrajectory();
        List<Waypoint> w = new ArrayList<>();
        for (int i = 0; i < this.paths.getPoints().size(); i++) {
            PathPoint<?> p = this.paths.getPoints().elementAt(i);
            w.add(p.getWaypoint());
        }
        Trajectory t = Trajectory.fromWaypoint(w, new RobotConfiguration());
        Coordinate<Double> lastCoords = null;
        g.setColor(Color.BLACK);
        for (TrajectoryTask task : t.getTasks()) {
            if (task instanceof WaypointTask) {
                ParametricSpline spline = ParametricSpline.fromWaypoints(((WaypointTask) task).getWaypoints());
                for (double i = 0; i < spline.getTotalTime(); i += spline.getTotalTime() / 100) {
                    if (lastCoords == null) {
                        lastCoords = new Coordinate<>(spline.getXAtTime(i), spline.getYAtTime(i));
                    } else {
                        Coordinate<Double> curCoords = new Coordinate<>(spline.getXAtTime(i), spline.getYAtTime(i));

                        Coordinate<Integer> curCanvasCoords = toCanvasCoords(curCoords);
                        Coordinate<Integer> lastCanvasCoords = toCanvasCoords(lastCoords);

                        lastCoords = curCoords;

                        g.drawLine(lastCanvasCoords.getX(), lastCanvasCoords.getY(), curCanvasCoords.getX(), curCanvasCoords.getY());
                    }
                }
            } else {
                System.out.println(((CommandTask) task).getWaypoint().getCommandName());
                if (lastCoords == null) {
                    lastCoords = new Coordinate<>(((CommandTask) task).getWaypoint().getX(), ((CommandTask) task).getWaypoint().getY());
                } else {
                    Coordinate<Double> curCoords = new Coordinate<>(((CommandTask) task).getWaypoint().getX(), ((CommandTask) task).getWaypoint().getY());
                    Coordinate<Integer> curCanvasCoords = toCanvasCoords(curCoords);
                    Coordinate<Integer> lastCanvasCoords = toCanvasCoords(lastCoords);
                    lastCoords = curCoords;
                    g.drawLine(lastCanvasCoords.getX(), lastCanvasCoords.getY(), curCanvasCoords.getX(), curCanvasCoords.getY());
                }
            }
        }

        for (int i = 0; i < this.paths.getPoints().size(); i++) {
            PathPoint<?> p = this.paths.getPoints().elementAt(i);

            Coordinate<Integer> c = toCanvasCoords(p.getPosition());


            if (this.getPathsList().getSelectedValue() == p) {
                g.setColor(Color.GRAY);
                Coordinate<Integer> velocityCnvPoint = toCanvasCoords(p.getWeightCoordinates());
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
