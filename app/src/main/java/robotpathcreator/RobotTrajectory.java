package robotpathcreator;

import org.bananasamirite.robotmotionprofile.TankMotionProfile;
import org.bananasamirite.robotmotionprofile.data.waypoint.CommandWaypoint;
import org.bananasamirite.robotmotionprofile.data.waypoint.SplineWaypoint;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RobotTrajectory {
    private final DefaultListModel<PathPoint<?>> points = new DefaultListModel<>();

    private final RobotPathCreator pathCreator;

    public RobotTrajectory(RobotPathCreator pathCreator) {
        this.pathCreator = pathCreator;
        SplineWaypoint w = new SplineWaypoint(0,0, 0, 1, 5, new TankMotionProfile.TankMotionProfileConstraints(0, 0));
        w.setName("Point 1");
        this.addPoint(new SplinePathPoint(w));
        this.addPoint(new CommandPathPoint(new CommandWaypoint("Point 2", 0, 4, 0, 2, 1, new TankMotionProfile.TankMotionProfileConstraints(0, 0), "CoolCommand")));
    }

    private RobotTrajectory(RobotPathCreator pathCreator, List<PathPoint<?>> points) {
        this.pathCreator = pathCreator;
        for (PathPoint<?> p : points) this.addPoint(p);
    }

    public void addPoint(PathPoint<?> p) {
        this.points.addElement(p);
        if (this.pathCreator.getDisplay() != null) pathCreator.getDisplay().update();
    }

    // public void addPoint(String name, double x, double y, double angle, double velocity, double travelTime) {
    //     this.points.addElement(new SplinePathPoint(name, x, y, angle, velocity, travelTime));
    // }
    public DefaultListModel<PathPoint<?>> getPoints() {
        return points;
    }

    public static RobotTrajectory importTrajectory(RobotPathCreator pathCreator, File file) {
        // TODO: finish
        return new RobotTrajectory(pathCreator, new ArrayList<>());
    }
}