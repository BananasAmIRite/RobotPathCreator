package robotpathcreator.data;

import org.bananasamirite.robotmotionprofile.data.waypoint.SplineWaypoint;
import robotpathcreator.renderer.handle.WaypointEditorHandle;

public class SplinePathPoint extends PathPoint<SplineWaypoint> {
    // SplinePathPoint(SplineWaypoint); 
    public SplinePathPoint(SplineWaypoint waypoint) {
        super(waypoint, new WaypointEditorHandle());
    }
}