package robotpathcreator.renderer;

import robotpathcreator.PathPoint;
import robotpathcreator.RobotPathCreator;

import javax.swing.*;
import java.awt.event.*;

public class PathPointsEditor extends JPanel {
    private PathPoint<?> currentPoint;

    private final RobotPathCreator pathCreator;

    public PathPointsEditor(RobotPathCreator pathCreator) {
        this.pathCreator = pathCreator;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                grabFocus();
                pathCreator.getDisplay().update();
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                pathCreator.getDisplay().update();
            }
        });
    }

    public void setCurrentPoint(PathPoint<?> point) {
        this.removeAll(); 
        this.currentPoint = point;
        if (point == null) return; 

        point.getEditorHandle().display(this);


        this.revalidate();
        this.repaint();
    }

    public PathPoint<?> getCurrentPoint() {
        return this.currentPoint;
    }
}