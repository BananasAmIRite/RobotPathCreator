package robotpathcreator;

import robotpathcreator.renderer.PathPointsEditor;
import robotpathcreator.renderer.CommandPointEditorHandle;

public class CommandPathPoint extends PathPoint {
    // CommandPathPoint(CommandWaypoint); 
    public CommandPathPoint(String name, double x, double y, double angle, double velocity, double travelTime) {
        super(name, x, y, angle, velocity, new CommandPointEditorHandle()); 
    }
}