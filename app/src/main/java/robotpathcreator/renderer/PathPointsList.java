package robotpathcreator.renderer;

import robotpathcreator.PathPoint;
import robotpathcreator.RobotPathCreator;
import robotpathcreator.RobotTrajectory;

import javax.swing.*;

public class PathPointsList extends JList<PathPoint> {
    private RobotTrajectory trajectory;

    private final RobotPathCreator pathCreator;

    public PathPointsList(RobotPathCreator pathCreator) {
        this.pathCreator = pathCreator;
        this.trajectory = new RobotTrajectory(pathCreator);
        this.setModel(getPoints());
    }

    public void addPoint(String name, double x, double y, double angle, double velocity, double travelTime) {
        this.trajectory.addPoint(name, x, y, angle, velocity, travelTime);
    }

    public DefaultListModel<PathPoint> getPoints() {
        return trajectory.getPoints();
    }

    public void setTrajectory(RobotTrajectory trajectory) {
        this.trajectory = trajectory;
        this.setModel(getPoints());
    }
}