package robotpathcreator.data;

import org.bananasamirite.robotmotionprofile.data.waypoint.CommandWaypoint;
import robotpathcreator.renderer.handle.CommandPointEditorHandle;
import robotpathcreator.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void setParameterString(String s) {
        List<Object> os = new ArrayList<>();
        for (String p : s.split(" ")) os.add(toArbitraryObject(p));
        getWaypoint().setParameters(os);
    }

    public String getParameterString() {
        return getWaypoint().getParameters().stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    private static Object toArbitraryObject(String s) {
        if (NumberUtils.isNumeric(s)) return NumberUtils.toNumber(s);
        return s;
    }
}