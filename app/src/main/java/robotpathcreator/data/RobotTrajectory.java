package robotpathcreator.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bananasamirite.robotmotionprofile.TankMotionProfile;
import org.bananasamirite.robotmotionprofile.Waypoint;
import org.bananasamirite.robotmotionprofile.data.RobotConfiguration;
import org.bananasamirite.robotmotionprofile.data.Trajectory;
import org.bananasamirite.robotmotionprofile.data.waypoint.CommandWaypoint;
import org.bananasamirite.robotmotionprofile.data.waypoint.SplineWaypoint;
import robotpathcreator.RobotPathCreator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RobotTrajectory {
    private final DefaultListModel<PathPoint<?>> points = new DefaultListModel<>();
    private RobotConfiguration config = new RobotConfiguration(1, 1, new TankMotionProfile.TankMotionProfileConstraints(1, 0.5));

    private final RobotPathCreator pathCreator;


    public RobotTrajectory(RobotPathCreator pathCreator) {
        this.pathCreator = pathCreator;
        addSplinePoint("Untitled Point", 0,0, 0, 1, 1, false, new TankMotionProfile.TankMotionProfileConstraints(0, 0));
    }

    private RobotTrajectory(RobotPathCreator pathCreator, List<PathPoint<?>> points, RobotConfiguration config) {
        this.pathCreator = pathCreator;
        this.config = config;
        for (PathPoint<?> p : points) this.addPoint(p);
    }

    public void addPoint(PathPoint<?> p) {
        this.points.addElement(p);
        if (this.pathCreator.getDisplay() != null) pathCreator.getDisplay().update();
    }

    public void addSplinePoint(String name, double x, double y, double angle, double weight, double runTime, boolean reversed, TankMotionProfile.TankMotionProfileConstraints constraints) {
        SplineWaypoint w = new SplineWaypoint(x, y, angle, weight, runTime, constraints, reversed);
        w.setName(name);
        this.addPoint(new SplinePathPoint(w));
    }

    public void addCommandPoint(String name, double x, double y, double angle, double weight, double runTime, TankMotionProfile.TankMotionProfileConstraints constraints, String commandName) {
        CommandWaypoint w = new CommandWaypoint(name, x, y, angle, weight, runTime, constraints, commandName);
        this.addPoint(new CommandPathPoint(w));
    }

    // public void addPoint(String name, double x, double y, double angle, double velocity, double travelTime) {
    //     this.points.addElement(new SplinePathPoint(name, x, y, angle, velocity, travelTime));
    // }
    public DefaultListModel<PathPoint<?>> getPoints() {
        return points;
    }

    public RobotConfiguration getConfig() {
        return config;
    }

    public void setConfig(RobotConfiguration config) {
        this.config = config;
        pathCreator.getDisplay().update();
    }

    public static RobotTrajectory importTrajectory(RobotPathCreator pathCreator, File file) throws IOException {
        Trajectory t = Trajectory.fromFile(file);
        return new RobotTrajectory(pathCreator, t.getWaypoints().stream().map(e -> {
            if (e instanceof CommandWaypoint) return new CommandPathPoint((CommandWaypoint) e);
            else return new SplinePathPoint((SplineWaypoint) e);
        }).collect(Collectors.toList()), t.getConfig());
    }

    public Trajectory toTrajectory() {
        List<Waypoint> w = new ArrayList<>();
        for (int i = 0; i < this.getPoints().size(); i++) {
            PathPoint<?> p = this.getPoints().elementAt(i);
            w.add(p.getWaypoint());
        }
        return Trajectory.fromWaypoint(w, this.config);
    }

    public String toJsonString() throws JsonProcessingException {
        return toTrajectory().toJsonString();
    }

    public void toJsonFile(File f) throws IOException {
        toTrajectory().toJsonFile(f);
    }
}