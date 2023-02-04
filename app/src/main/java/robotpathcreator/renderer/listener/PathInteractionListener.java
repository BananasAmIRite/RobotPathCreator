package robotpathcreator.renderer.listener;

import robotpathcreator.data.Coordinate;
import robotpathcreator.data.PathPoint;
import robotpathcreator.renderer.PathsDisplay;

import java.awt.*;
import java.awt.event.*;

public class PathInteractionListener  extends MouseDragListener implements MouseListener, KeyListener, MouseWheelListener {
    private ControllingElement controllingElement;

    private final PathsDisplay display;

    private Point lastMousePoint;

    public PathInteractionListener(PathsDisplay display) {
        this.display = display;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.display.getPathsList().getSelectedValue() != null) {
            Coordinate<Integer> cnvPos = display.toCanvasCoords(this.display.getPathsList().getSelectedValue().getWeightCoordinates());
            if (isInRange(e.getX(), e.getY(), cnvPos.getX(), cnvPos.getY(), display.getNodeRadius())) {
                this.controllingElement = new ControllingElement(ElementType.VELOCITY, this.display.getPathsList().getSelectedValue());
                display.update();
                return;
            }
            Coordinate<Integer> canvasPosition = display.toCanvasCoords(this.display.getPathsList().getSelectedValue().getPosition());
            if (isInRange(e.getX(), e.getY(), canvasPosition.getX(), canvasPosition.getY(), display.getNodeRadius())) {
                this.controllingElement = new ControllingElement(ElementType.POINT, this.display.getPathsList().getSelectedValue());
                display.update();
                return;
            }
        }

        for (int i = 0; i < this.display.getPathsList().getPoints().size(); i++) {
            PathPoint<?> point = this.display.getPathsList().getPoints().elementAt(i);
            Coordinate<Integer> canvasPosition = display.toCanvasCoords(point.getPosition());
            if (isInRange(e.getX(), e.getY(), canvasPosition.getX(), canvasPosition.getY(), display.getNodeRadius())) {
                this.controllingElement = new ControllingElement(ElementType.POINT, point);
                this.display.getPathsList().setSelectedValue(point, true);
                display.update();
                return;
            }
        }

        this.display.getPathsList().clearSelection();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        controllingElement = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private boolean isInRange(double x, double y, double xD, double yD, double range) {
        return Math.sqrt(Math.pow(xD-x, 2) + Math.pow(yD-y, 2)) <= range;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    void onMouseDrag(Point lastMousePos, Point newMousePos) {
        if (controllingElement == null) {
            double dx = newMousePos.getX() - lastMousePos.getX();
            double dy = newMousePos.getY() - lastMousePos.getY();

            double dxTraj = display.fromCanvasValueX(dx);
            double dyTraj = display.fromCanvasValueY(dy);

            Coordinate<Double> fcp = display.getFocalPoint();

            display.setFocalPoint(new Coordinate<>(fcp.getX() - dxTraj, fcp.getY() - dyTraj));
            display.update();

            return;
        }

        if (controllingElement.getType() == ElementType.POINT) {
            Coordinate<Double> trajCoords = display.fromCanvasCoords(new Coordinate<>((int) newMousePos.getX(), (int) newMousePos.getY()));
            controllingElement.getPoint().setX(trajCoords.getX());
            controllingElement.getPoint().setY(trajCoords.getY());
        } else if (controllingElement.getType() == ElementType.VELOCITY) {
            Coordinate<Double> trajCoords = display.fromCanvasCoords(new Coordinate<>((int) newMousePos.getX(), (int) newMousePos.getY()));
            double deltaX = trajCoords.getX() - controllingElement.getPoint().getX();
            double deltaY = trajCoords.getY() - controllingElement.getPoint().getY();
            controllingElement.getPoint().setAngle(Math.atan2(deltaY, deltaX));
            controllingElement.getPoint().setWeight(Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
        }
        controllingElement.point.getEditorHandle().update();
        this.display.update();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scrollAmt = e.getWheelRotation();
        this.display.setZoom(this.display.getZoom() - scrollAmt);
        this.display.update();
    }

    private enum ElementType {
        POINT,
        VELOCITY
    }

    private static class ControllingElement {
        private final ElementType type;
        private final PathPoint<?> point;

        public ControllingElement(ElementType t, PathPoint<?> p) {
            this.type = t;
            this.point = p;
        }

        public ElementType getType() {
            return type;
        }

        public PathPoint<?> getPoint() {
            return point;
        }
    }
}
