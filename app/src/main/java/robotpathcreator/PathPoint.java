package robotpathcreator;

import org.bananasamirite.robotmotionprofile.Waypoint;
import robotpathcreator.renderer.PathPointEditorHandle;

// this a terrible idea; dont use generics
public abstract class PathPoint<T extends Waypoint> {

    private final PathPointEditorHandle<T, ? extends PathPoint<T>> editorHandle;

    private final T waypoint;

    // PathPoint(Waypoint, PathPointEditorHandle)
    public PathPoint(T waypoint, PathPointEditorHandle<T, ? extends PathPoint<T>> handle) {
        this.waypoint = waypoint;
        this.editorHandle = handle; // TODO: move to two new classes
        this.editorHandle.configureHandleOwner(this);
    }

    public T getWaypoint() {
        return waypoint;
    }

    public PathPointEditorHandle<T, ? extends PathPoint<T>> getEditorHandle() {
        return editorHandle;
    }

    public double getTravelTime() {
        return this.waypoint.getRunTime();
    }

    public double getX() {
        return this.waypoint.getX();
    }

    public double getY() {
        return this.waypoint.getY();
    }

    public double getWeight() {
        return this.waypoint.getWeight();
    }

    public String getName() {
        return this.waypoint.getName();
    }

    public double getAngle() {
        return this.waypoint.getAngle();
    }

    public double getWeightTranslationX() {
        return this.waypoint.getWeight() * Math.cos(this.waypoint.getAngle());
    }

    public double getWeightTranslationY() {
        return this.waypoint.getWeight() * Math.sin(this.waypoint.getAngle());
    }

    public Coordinate<Double> getPosition() {
        return new Coordinate<>(this.getX(), this.getY());
    }

    public Coordinate<Double> getWeightCoordinates() {
        return new Coordinate<>(getX() + getWeightTranslationX(), getY() + getWeightTranslationY());
    }

    public void setX(double x) {
        this.waypoint.setX(x);
    }

    public void setY(double y) {
        this.waypoint.setY(y);
    }

    public void setAngle(double angle) {
        this.waypoint.setAngle(angle);
    }

    public void setName(String name) {
        this.waypoint.setName(name);
    }

    public void setTravelTime(double t) {
        this.waypoint.setRunTime(t);
    }

    public void setWeight(double w) {
        this.waypoint.setWeight(w);
    }

    @Override
    public String toString() {
        return getName();
    }

    public double getMaxVelocity() {
        return this.waypoint.getConstraints().getMaxVelocity();
    }

    public double getMaxAcceleration() {
        return this.waypoint.getConstraints().getMaxAcceleration();
    }

    public void setMaxVelocity(double velocity) {
        this.waypoint.getConstraints().setMaxVelocity(velocity);
    }

    public void setMaxAcceleration(double acceleration) {
        this.waypoint.getConstraints().setMaxAcceleration(acceleration);
    }
}