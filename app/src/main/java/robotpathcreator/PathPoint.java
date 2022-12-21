package robotpathcreator;

import robotpathcreator.renderer.PathPointsEditor;

public class PathPoint {
    private String name; 
    private double x; 
    private double y; 
    private double angle; 
    private double velocity; 
    private double travelTime; // time to travel from this point to the next

    private final RobotPathCreator pathCreator;

    // public PathPoint(String name, double x, double y, double angle, double velocity) {
    //     this(name, x, y, angle, velocity, -1); 
    // }

    public PathPoint(RobotPathCreator pathCreator, String name, double x, double y, double angle, double velocity, double travelTime) {
        this.name = name; 
        this.x = x; 
        this.y = y; 
        this.angle = angle; 
        this.velocity = velocity; 
        this.travelTime = travelTime;
        this.pathCreator = pathCreator;
    }

    public String getName() {
        return this.name; 
    }

    public void setName(String name) {
        this.name = name;
        if (pathCreator.getEditor().getCurrentPoint() == this) pathCreator.getEditor().update();
    }

    public double getTravelTime() {
        return this.travelTime; 
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
        if (pathCreator.getEditor().getCurrentPoint() == this) pathCreator.getEditor().update();
    } 

    public double getX() {
        return x; 
    }

    public void setX(double x) {
        this.x = x;
        if (pathCreator.getEditor().getCurrentPoint() == this) pathCreator.getEditor().update();
    }

    public double getY() {
        return y; 
    }

    public void setY(double y) {
        this.y = y;
        if (pathCreator.getEditor().getCurrentPoint() == this) pathCreator.getEditor().update();
    } 

    public double getAngle() {
        return angle; 
    }

    public void setAngle(double angle) {
        this.angle = angle;
        if (pathCreator.getEditor().getCurrentPoint() == this) pathCreator.getEditor().update();
    }

    public double getVelocity() {
        return velocity; 
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
        if (pathCreator.getEditor().getCurrentPoint() == this) pathCreator.getEditor().update();
    }

    @Override
    public String toString() {
        return this.name;
    }
}