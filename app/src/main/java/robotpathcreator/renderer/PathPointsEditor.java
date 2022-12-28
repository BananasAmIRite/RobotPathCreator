package robotpathcreator.renderer;

import robotpathcreator.PathPoint;
import robotpathcreator.RobotPathCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PathPointsEditor extends JPanel {
    private PathPoint currentPoint;

    private final JTextField nameField = new JTextField();
    private final JTextField posXField = new JTextField();
    private final JTextField posYField = new JTextField();
    private final JTextField timeField = new JTextField();
    private final JTextField velocityField = new JTextField();
    private final JTextField angleField = new JTextField();

    private final RobotPathCreator pathCreator;

    public PathPointsEditor(RobotPathCreator pathCreator) {
        this.pathCreator = pathCreator;
        setLayout(new GridLayout(0, 2));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                grabFocus();
            }
        });
    }

    public void rerender() {
        this.removeAll();

        if (currentPoint == null) return;

        update();

        this.add(new JLabel("Name: "));
        this.add(this.nameField);

        this.add(new JLabel("Time: "));
        this.add(this.timeField);

        this.add(new JLabel("Position X: "));
        this.add(this.posXField);

        this.add(new JLabel("Position Y: "));
        this.add(this.posYField);

        this.add(new JLabel("Velocity: "));
        this.add(this.velocityField);

        this.add(new JLabel("Angle: "));
        this.add(this.angleField);

        this.revalidate();
        this.repaint();
    }

    public void update() {
        if (currentPoint == null) return;

        this.nameField.setText(currentPoint.getName());
        this.posXField.setText(Double.toString(currentPoint.getX()));
        this.posYField.setText(Double.toString(currentPoint.getY()));
        this.timeField.setText(Double.toString(currentPoint.getTravelTime()));
        this.velocityField.setText(Double.toString(currentPoint.getVelocity()));
        this.angleField.setText(Double.toString(Math.toDegrees(currentPoint.getAngle()))); // IMPORTANT: displayed is degrees, stored is radians

        this.pathCreator.getDisplay().update();

    }

    public void setCurrentPoint(PathPoint point) {
        this.currentPoint = point;

        this.rerender();
        this.nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                point.setName(nameField.getText());
            }
        });

        this.posXField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                point.setX(Double.parseDouble(posXField.getText()));
            }
        });

        this.posYField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                point.setY(Double.parseDouble(posYField.getText()));
            }
        });

        this.timeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                point.setTravelTime(Double.parseDouble(timeField.getText()));
            }
        });

        this.velocityField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                point.setVelocity(Double.parseDouble(velocityField.getText()));
            }
        });

        this.angleField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                point.setAngle(Math.toRadians(Double.parseDouble(angleField.getText())));
            }
        });
    }

    public PathPoint getCurrentPoint() {
        return this.currentPoint;
    }
}