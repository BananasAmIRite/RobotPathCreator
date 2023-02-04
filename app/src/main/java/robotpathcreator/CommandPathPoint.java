package robotpathcreator;

import org.bananasamirite.robotmotionprofile.data.waypoint.CommandWaypoint;
import robotpathcreator.renderer.CommandPointEditorHandle;

public class CommandPathPoint extends PathPoint<CommandWaypoint> {
    // CommandPathPoint(CommandWaypoint); 
    public CommandPathPoint(CommandWaypoint waypoint) {
        super(waypoint, new CommandPointEditorHandle());
    }

    public String getCommandName() {
        return getWaypoint().getCommandName();
    }

    public void setCommandName(String name) {
        getWaypoint().setCommandName(name);
    }
}