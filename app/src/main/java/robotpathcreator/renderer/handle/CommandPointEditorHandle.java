package robotpathcreator.renderer.handle;

import org.bananasamirite.robotmotionprofile.data.waypoint.CommandWaypoint;
import robotpathcreator.data.CommandPathPoint;
import robotpathcreator.renderer.PathPointsEditor;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CommandPointEditorHandle extends PathPointEditorHandle<CommandWaypoint, CommandPathPoint> {

    private final JTextField commandNameField = new JTextField();
    private final JTextField commandArgsField = new JTextField();
    @Override
    protected void addFeatures(PathPointsEditor editor) {
        super.addFeatures(editor);

        editor.add(new JLabel("Command Name"));
        editor.add(commandNameField);
        editor.add(new JLabel("Command Arguments"));
        editor.add(commandArgsField);
    }

    @Override
    public void update() {
        super.update();

        commandNameField.setText(getWaypoint().getCommandName());
        commandArgsField.setText(getWaypoint().getParameterString());
    }

    @Override
    protected void configureFocus() {
        super.configureFocus();

        commandNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                getWaypoint().setCommandName(commandNameField.getText());
            }
        });

        commandArgsField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                getWaypoint().setParameterString(commandArgsField.getText());
            }
        });
    }
}