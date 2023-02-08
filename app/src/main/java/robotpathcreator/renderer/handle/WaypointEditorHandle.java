package robotpathcreator.renderer.handle;

import org.bananasamirite.robotmotionprofile.data.waypoint.SplineWaypoint;
import robotpathcreator.data.SplinePathPoint;
import robotpathcreator.renderer.PathPointsEditor;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class WaypointEditorHandle extends PathPointEditorHandle<SplineWaypoint, SplinePathPoint> {
    private final JCheckBox reversedCheckbox = new JCheckBox();

    @Override
    protected void addFeatures(PathPointsEditor editor) {
        super.addFeatures(editor);
        editor.add(new JLabel("Reversed"));
        editor.add(reversedCheckbox);
    }

    @Override
    public void update() {
        super.update();
        reversedCheckbox.setSelected(getWaypoint().isReversed());
    }

    @Override
    protected void configureFocus() {
        super.configureFocus();
        reversedCheckbox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                getWaypoint().setReversed(reversedCheckbox.isSelected());
            }
        });
        reversedCheckbox.addChangeListener(e -> getWaypoint().setReversed(reversedCheckbox.isSelected()));
    }
}