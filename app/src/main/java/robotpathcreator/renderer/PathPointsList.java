package robotpathcreator.renderer;

import org.bananasamirite.robotmotionprofile.TankMotionProfile;
import org.bananasamirite.robotmotionprofile.data.waypoint.CommandWaypoint;
import org.bananasamirite.robotmotionprofile.data.waypoint.SplineWaypoint;
import robotpathcreator.*;

import javax.swing.*;

public class PathPointsList extends JList<PathPoint<?>> {
    private RobotTrajectory trajectory;

    private final RobotPathCreator pathCreator;

    public PathPointsList(RobotPathCreator pathCreator) {
        this.pathCreator = pathCreator;
        this.trajectory = new RobotTrajectory(pathCreator);
        this.setModel(getPoints());
    }

    public void addSplinePoint(String name, double x, double y, double angle, double weight, double runTime, TankMotionProfile.TankMotionProfileConstraints constraints) {
        SplineWaypoint w = new SplineWaypoint(x, y, angle, weight, runTime, constraints);
        w.setName(name);
        this.trajectory.addPoint(new SplinePathPoint(w));
    }

    public void addCommandPoint(String name, double x, double y, double angle, double weight, double runTime, TankMotionProfile.TankMotionProfileConstraints constraints, String commandName) {
        CommandWaypoint w = new CommandWaypoint(name, x, y, angle, weight, runTime, constraints, "");
        this.trajectory.addPoint(new CommandPathPoint(w));
    }

    public DefaultListModel<PathPoint<?>> getPoints() {
        return trajectory.getPoints();
    }

    public void setTrajectory(RobotTrajectory trajectory) {
        this.trajectory = trajectory;
        this.setModel(getPoints());
    }

    public RobotTrajectory getTrajectory() {
        return trajectory;
    }
}