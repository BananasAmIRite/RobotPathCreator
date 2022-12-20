package robotpathcreator; 

public class RobotPath {
    private List<PathPoint> points = new List<>(); 

    public RobotPath() {}

    public void addPoint(PathPoint path) {
        this.points.add(path); 
    }

    public List<Path> calculatePath() {
        List<Path> paths = new ArrayList<>(); 
        for (int i = 0; i < points.size() - 1; i++) {
            paths.add(new Path(points.get(i), points.get(i+1))); 
        }
        return paths; 
    }
}