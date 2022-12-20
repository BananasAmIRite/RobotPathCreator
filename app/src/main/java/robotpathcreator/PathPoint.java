package robotpathcreator; 

public class PathPoint {
    private String name; 
    private double x; 
    private double y; 
    private double angle; 
    private double velocity; 
    private double travelTime; // time to travel from this point to the next

    // public PathPoint(String name, double x, double y, double angle, double velocity) {
    //     this(name, x, y, angle, velocity, -1); 
    // }

    public PathPoint(String name, double x, double y, double angle, double velocity, double travelTime) {
        this.name = name; 
        this.x = x; 
        this.y = y; 
        this.angle = angle; 
        this.velocity = velocity; 
        this.travelTime = travelTime; 
    }

    public String getName() {
        return this.name; 
    }

    public void setName(String name) {
        this.name = name; 
    }

    public double getTravelTime() {
        return this.travelTime; 
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime; 
    } 

    public double getX() {
        return x; 
    }

    public void setX(double x) {
        this.x = x; 
    }

    public double getY() {
        return y; 
    }

    public void setY(double y) {
        this.y = y; 
    } 

    public double getAngle() {
        return angle; 
    }

    public void setAngle(double angle) {
        this.angle = angle; 
    }

    public double getVelocity() {
        return velocity; 
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity; 
    }
}