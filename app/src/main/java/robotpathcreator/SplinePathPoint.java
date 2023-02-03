package robotpathcreator;

import robotpathcreator.renderer.PathPointsEditor;

public class SplinePathPoint extends PathPoint {
    // SplinePathPoint(SplineWaypoint); 
    public SplinePathPoint(String name, double x, double y, double angle, double velocity, double travelTime) {
        super(name, x, y, angle, velocity, new WaypointEditorHandle()); 
    }
}