package robotpathcreator;

import org.bananasamirite.robotmotionprofile.data.waypoint.SplineWaypoint;
import robotpathcreator.renderer.WaypointEditorHandle;

public class SplinePathPoint extends PathPoint<SplineWaypoint> {
    // SplinePathPoint(SplineWaypoint); 
    public SplinePathPoint(SplineWaypoint waypoint) {
        super(waypoint, new WaypointEditorHandle());
    }
}