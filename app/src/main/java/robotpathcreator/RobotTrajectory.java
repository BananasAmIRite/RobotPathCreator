package robotpathcreator;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RobotTrajectory {
    private final DefaultListModel<PathPoint> points = new DefaultListModel<>();

    private final RobotPathCreator pathCreator;

    public RobotTrajectory(RobotPathCreator pathCreator) {
        this.pathCreator = pathCreator;
        this.addPoint(new SplinePathPoint("Point 1", 0,0, 0, 10, 5));
        this.addPoint(new CommandPathPoint("Point 2", 5, 4, 0, 10, 1, "CoolCommand"));
    }

    private RobotTrajectory(RobotPathCreator pathCreator, List<PathPoint> points) {
        this.pathCreator = pathCreator;
        for (PathPoint p : points) this.addPoint(p);
    }

    public void addPoint(PathPoint p) {
        this.points.addElement(p);
    }

    // public void addPoint(String name, double x, double y, double angle, double velocity, double travelTime) {
    //     this.points.addElement(new SplinePathPoint(name, x, y, angle, velocity, travelTime));
    // }

    public List<Path> calculateTrajectory() {
        List<Path> paths = new ArrayList<>(); 
        for (int i = 0; i < points.size() - 1; i++) {
            paths.add(new Path(points.get(i), points.get(i+1))); 
        }
        return paths; 
    }

    public DefaultListModel<PathPoint> getPoints() {
        return points;
    }

    public static RobotTrajectory importTrajectory(RobotPathCreator pathCreator, File file) {
        // TODO: finish
        return new RobotTrajectory(pathCreator, new ArrayList<>());
    }
}