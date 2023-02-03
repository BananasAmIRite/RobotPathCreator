package robotpathcreator;

import robotpathcreator.renderer.PathPointEditorHandle;
import robotpathcreator.renderer.PathPointsEditor;

public abstract class PathPoint {
    private String name; 
    private Coordinate<Double> position;
    private double angle; 
    private double velocity; 
    private double travelTime; // time to travel from this point to the next

    private final PathPointEditorHandle editorHandle; 

    public PathPoint(String name, double x, double y, double angle, double velocity, double travelTime) {
        this(name, x, y, angle, velocity, travelTime, new PathPointEditorHandle()); 
    }

    // PathPoint(Waypoint, PathPointEditorHandle)
    public PathPoint(String name, double x, double y, double angle, double velocity, double travelTime, PathPointEditorHandle handle) {
        this.name = name; 
        this.position = new Coordinate<>(x, y);
        this.angle = angle; 
        this.velocity = velocity; 
        this.travelTime = travelTime;
        this.editorHandle = handle; // TODO: move to two new classes
        this.editorHandle.configureHandleOwner(this);
    }

    public String getName() {
        return this.name; 
    }

    public void setName(String name) {
        this.name = name;
        this.editorHandle.update();
    }

    public double getTravelTime() {
        return this.travelTime; 
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
        this.editorHandle.update();
    } 

    public double getX() {
        return this.position.getX();
    }

    public void setX(double x) {
        this.position = new Coordinate<>(x, this.position.getY());
        this.editorHandle.update();
    }

    public double getY() {
        return this.position.getY();
    }

    public void setY(double y) {
        this.position = new Coordinate<>(this.position.getX(), y);
        this.editorHandle.update();
    } 

    public double getAngle() {
        return angle; 
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.editorHandle.update();
    }

    public double getVelocity() {
        return velocity; 
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
        this.editorHandle.update();
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

    public PathPointEditorHandle getEditorHandle() {
        return this.editorHandle; 
    } 

    @Override
    public String toString() {
        return this.name;
    }
}