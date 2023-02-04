package robotpathcreator.renderer.listener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class MouseDragListener implements MouseMotionListener, MouseListener {

    private Point lastMousePos;

    @Override
    public void mouseReleased(MouseEvent e) {
        lastMousePos = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastMousePos == null) {
            lastMousePos = e.getPoint();
            return;
        }

        onMouseDrag(lastMousePos, e.getPoint());
        this.lastMousePos = e.getPoint();
    }

    abstract void onMouseDrag(Point lastMousePos, Point newMousePos);
}
