package robotpathcreator.renderer;

import org.bananasamirite.robotmotionprofile.ParametricSpline;
import org.bananasamirite.robotmotionprofile.Waypoint;
import org.bananasamirite.robotmotionprofile.data.RobotConfiguration;
import org.bananasamirite.robotmotionprofile.data.Trajectory;
import org.bananasamirite.robotmotionprofile.data.task.CommandTask;
import org.bananasamirite.robotmotionprofile.data.task.TrajectoryTask;
import org.bananasamirite.robotmotionprofile.data.task.WaypointTask;
import robotpathcreator.Canvas;
import robotpathcreator.data.Coordinate;
import robotpathcreator.data.PathPoint;
import robotpathcreator.renderer.listener.PathInteractionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PathsDisplay extends Canvas {
    private Coordinate<Double> focalPoint = new Coordinate<>(0d, 0d); // in trajectory units
    private double zoom = 50;
    private final int nodeRadius = 5;

    private FieldImage fieldImage; 

    private PathPointsList paths;

    public PathsDisplay(PathPointsList paths) {
        super();
        this.paths = paths;
        PathInteractionListener listener = new PathInteractionListener(this);
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

            g.drawImage(this.fieldImage.getImage(),
                    renderStart.getX(),
                    renderStart.getY(),
//                    getImageHeight() / 2 + renderStart.getY() + toCanvasCoords(getFocalPoint()).getY(),
                    (int) dimensions.getWidth(), (int) dimensions.getHeight(),
                    null);
        }

        if (this.paths == null) return;

        List<Waypoint> w = new ArrayList<>();
        for (int i = 0; i < this.paths.getPoints().size(); i++) {
            PathPoint<?> p = this.paths.getPoints().elementAt(i);
            w.add(p.getWaypoint());
        }
        Trajectory t = Trajectory.fromWaypoint(w, new RobotConfiguration());
        for (int j = 0; j < t.getTasks().size(); j++) {
            TrajectoryTask task = t.getTasks().get(j);
            if (task instanceof WaypointTask) {
                ParametricSpline spline = ParametricSpline.fromWaypoints(((WaypointTask) task).getWaypoints());
                renderSpline(spline, g);
            }
        }

        for (int j = 0; j < t.getTasks().size() - 1; j++) {
            TrajectoryTask task = t.getTasks().get(j);
            TrajectoryTask nextTask = t.getTasks().get(j+1);
            if (task instanceof CommandTask) {
                g.setColor(Color.BLACK);
                Coordinate<Double> curCoords = new Coordinate<>(((CommandTask) task).getWaypoint().getX(), ((CommandTask) task).getWaypoint().getY());
                Coordinate<Integer> curCanvasCoords = toCanvasCoords(curCoords);
                Coordinate<Integer> nextCanvasCoords = toCanvasCoords(
                        nextTask instanceof CommandTask ?
                                new Coordinate<>(((CommandTask) nextTask).getWaypoint().getX(), ((CommandTask) nextTask).getWaypoint().getY()) :
                                new Coordinate<>(((WaypointTask) nextTask).getWaypoints().get(0).getX(), ((WaypointTask) nextTask).getWaypoints().get(0).getY()));
//                System.out.println(curCanvasCoords + " " + nextCanvasCoords);
                g.drawLine(curCanvasCoords.getX(), curCanvasCoords.getY(), nextCanvasCoords.getX(), nextCanvasCoords.getY());
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

    private void renderSpline(ParametricSpline spline, Graphics2D g) {
        for (double i = 0; i < spline.getTotalTime() * 99/100; i += spline.getTotalTime() / 100) {
            double iNext = i+spline.getTotalTime() / 100;

            Coordinate<Integer> lastCanvasCoords = toCanvasCoords(new Coordinate<>(spline.getXAtTime(i), spline.getYAtTime(i)));
            Coordinate<Integer> curCanvasCoords = toCanvasCoords(new Coordinate<>(spline.getXAtTime(iNext), spline.getYAtTime(iNext)));

            double derivAngleCur = Math.atan2(spline.getDyAtTime(i), spline.getDxAtTime(i));
            double derivAngleNext = Math.atan2(spline.getDyAtTime(iNext), spline.getDxAtTime(iNext));

            Coordinate<Integer> curBoundaryPoint1 = toCanvasCoords(new Coordinate<>(
                    spline.getXAtTime(i) + paths.getTrajectory().getConfig().getDimensionY() / 2 * Math.cos(derivAngleCur + Math.PI/2),
                    spline.getYAtTime(i) + paths.getTrajectory().getConfig().getDimensionY() / 2 * Math.sin(derivAngleCur + Math.PI/2)
            ));
            Coordinate<Integer> curBoundaryPoint2 = toCanvasCoords(new Coordinate<>(
                    spline.getXAtTime(i) + paths.getTrajectory().getConfig().getDimensionY() / 2 * Math.cos(derivAngleCur - Math.PI/2),
                    spline.getYAtTime(i) + paths.getTrajectory().getConfig().getDimensionY() / 2 * Math.sin(derivAngleCur - Math.PI/2)
            ));

            Coordinate<Integer> nextBoundaryPoint1 = toCanvasCoords(new Coordinate<>(
                    spline.getXAtTime(iNext) + paths.getTrajectory().getConfig().getDimensionY() / 2 * Math.cos(derivAngleNext + Math.PI/2),
                    spline.getYAtTime(iNext) + paths.getTrajectory().getConfig().getDimensionY() / 2 * Math.sin(derivAngleNext + Math.PI/2)
            ));

            Coordinate<Integer> nextBoundaryPoint2 = toCanvasCoords(new Coordinate<>(
                    spline.getXAtTime(iNext) + paths.getTrajectory().getConfig().getDimensionY() / 2 * Math.cos(derivAngleNext - Math.PI/2),
                    spline.getYAtTime(iNext) + paths.getTrajectory().getConfig().getDimensionY() / 2 * Math.sin(derivAngleNext - Math.PI/2)
            ));

            g.setColor(Color.BLACK);
            g.drawLine(lastCanvasCoords.getX(), lastCanvasCoords.getY(), curCanvasCoords.getX(), curCanvasCoords.getY());
            g.setColor(Color.BLUE);
            g.drawLine(curBoundaryPoint1.getX(), curBoundaryPoint1.getY(), nextBoundaryPoint1.getX(), nextBoundaryPoint1.getY());
            g.drawLine(curBoundaryPoint2.getX(), curBoundaryPoint2.getY(), nextBoundaryPoint2.getX(), nextBoundaryPoint2.getY());
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
