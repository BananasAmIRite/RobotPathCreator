package robotpathcreator.renderer;

import org.bananasamirite.robotmotionprofile.TankMotionProfile;
import org.bananasamirite.robotmotionprofile.Waypoint;
import robotpathcreator.*;
import robotpathcreator.data.PathPoint;
import robotpathcreator.data.RobotTrajectory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PathPointsList extends JList<PathPoint<?>> {
    private RobotTrajectory trajectory;

    public PathPointsList(RobotPathCreator pathCreator) {
        this.trajectory = new RobotTrajectory(pathCreator);
        this.setModel(getPoints());
    }

    public void addSplinePoint(String name, double x, double y, double angle, double weight, double runTime, boolean reversed, TankMotionProfile.TankMotionProfileConstraints constraints) {
        this.trajectory.addSplinePoint(name, x, y, angle, weight, runTime, reversed, constraints);
    }

    public void addCommandPoint(String name, double x, double y, double angle, double weight, double runTime, TankMotionProfile.TankMotionProfileConstraints constraints, String commandName) {
        this.trajectory.addCommandPoint(name, x, y, angle, weight, runTime, constraints, commandName);
    }

    public DefaultListModel<PathPoint<?>> getPoints() {
        return trajectory.getPoints();
    }

    public List<Waypoint> getWaypoints() {
        List<Waypoint> w = new ArrayList<>();
        for (int i = 0; i < this.getPoints().size(); i++) {
            PathPoint<?> p = this.getPoints().elementAt(i);
            w.add(p.getWaypoint());
        }
        return w;
    }

    public void setTrajectory(RobotTrajectory trajectory) {
        this.trajectory = trajectory;
        this.setModel(getPoints());
    }

    public RobotTrajectory getTrajectory() {
        return trajectory;
    }
}