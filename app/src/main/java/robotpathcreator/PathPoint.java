package robotpathcreator;

import robotpathcreator.renderer.PathPointsEditor;

public class PathPoint {
    private String name; 
    private Coordinate<Double> position;
    private double angle; 
    private double velocity; 
    private double travelTime; // time to travel from this point to the next

    private final RobotPathCreator pathCreator;

    // public PathPoint(String name, double x, double y, double angle, double velocity) {
    //     this(name, x, y, angle, velocity, -1); 
    // }

    public PathPoint(RobotPathCreator pathCreator, String name, double x, double y, double angle, double velocity, double travelTime) {
        this.name = name; 
        this.position = new Coordinate<>(x, y);
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
        return this.position.getX();
    }

    public void setX(double x) {
        this.position = new Coordinate<>(x, this.position.getY());
        if (pathCreator.getEditor().getCurrentPoint() == this) pathCreator.getEditor().update();
    }

    public double getY() {
        return this.position.getY();
    }

    public void setY(double y) {
        this.position = new Coordinate<>(this.position.getX(), y);
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

    public double getVelocityTranslationX() {
        return this.velocity * Math.cos(this.angle);
    }

    public double getVelocityTranslationY() {
        return this.velocity * Math.sin(this.angle);
    }

    public Coordinate<Double> getVelocityCoordinates() {
        return new Coordinate<>(getX() + getVelocityTranslationX(), getY() + getVelocityTranslationY());
    }

    public Coordinate<Double> getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return this.name;
    }
}