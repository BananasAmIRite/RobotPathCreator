package robotpathcreator.renderer;

import org.bananasamirite.robotmotionprofile.TankMotionProfile;
import robotpathcreator.*;
import robotpathcreator.data.PathPoint;
import robotpathcreator.data.RobotTrajectory;

import javax.swing.*;

public class PathPointsList extends JList<PathPoint<?>> {
    private RobotTrajectory trajectory;

    public PathPointsList(RobotPathCreator pathCreator) {
        this.trajectory = new RobotTrajectory(pathCreator);
        this.setModel(getPoints());
    }

    public void addSplinePoint(String name, double x, double y, double angle, double weight, double runTime, TankMotionProfile.TankMotionProfileConstraints constraints) {
        this.trajectory.addSplinePoint(name, x, y, angle, weight, runTime, constraints);
    }

    public void addCommandPoint(String name, double x, double y, double angle, double weight, double runTime, TankMotionProfile.TankMotionProfileConstraints constraints, String commandName) {
        this.trajectory.addCommandPoint(name, x, y, angle, weight, runTime, constraints, commandName);
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