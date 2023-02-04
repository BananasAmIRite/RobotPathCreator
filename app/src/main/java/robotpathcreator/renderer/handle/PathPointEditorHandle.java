package robotpathcreator.renderer.handle;

import org.bananasamirite.robotmotionprofile.Waypoint;
import robotpathcreator.data.PathPoint;
import robotpathcreator.RobotPathCreator;
import robotpathcreator.dialog.ConstraintsDialog;
import robotpathcreator.renderer.PathPointsEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PathPointEditorHandle<G extends Waypoint, T extends PathPoint<G>> {

    private final JTextField nameField = new JTextField();
    private final JTextField posXField = new JTextField();
    private final JTextField posYField = new JTextField();
    private final JTextField timeField = new JTextField();
    private final JTextField weightField = new JTextField();
    private final JTextField angleField = new JTextField();
    private final JButton constraintEditBtn = new JButton("Edit");

    private PathPoint<G> waypoint;


    public void display(PathPointsEditor editor) {
        update();
        addFeatures(editor);
    }

    protected void addFeatures(PathPointsEditor editor) {
        editor.removeAll(); 
        editor.setLayout(new GridLayout(0, 2));
        editor.add(new JLabel("Name: "));
        editor.add(this.nameField);

        editor.add(new JLabel("Time: "));
        editor.add(this.timeField);

        editor.add(new JLabel("Position X: "));
        editor.add(this.posXField);

        editor.add(new JLabel("Position Y: "));
        editor.add(this.posYField);

        editor.add(new JLabel("Weight: "));
        editor.add(this.weightField);

        editor.add(new JLabel("Angle: "));
        editor.add(this.angleField);

        editor.add(new JLabel("Robot Constraints: "));
        editor.add(constraintEditBtn);
    }

    public void update() {
        this.nameField.setText(waypoint.getName());
        this.posXField.setText(Double.toString(waypoint.getX()));
        this.posYField.setText(Double.toString(waypoint.getY()));
        this.timeField.setText(Double.toString(waypoint.getTravelTime()));
        this.weightField.setText(Double.toString(waypoint.getWeight()));
        this.angleField.setText(Double.toString(Math.toDegrees(waypoint.getAngle()))); // IMPORTANT: displayed is degrees, stored is radians
    }

    protected void configureFocus() {
        this.nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                waypoint.setName(nameField.getText());
            }
        });

        this.posXField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                waypoint.setX(Double.parseDouble(posXField.getText()));
            }
        });

        this.posYField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                waypoint.setY(Double.parseDouble(posYField.getText()));
            }
        });

        this.timeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                waypoint.setTravelTime(Double.parseDouble(timeField.getText()));
            }
        });

        this.weightField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                waypoint.setWeight(Double.parseDouble(weightField.getText()));
            }
        });

        this.angleField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                waypoint.setAngle(Math.toRadians(Double.parseDouble(angleField.getText())));
            }
        });

        this.constraintEditBtn.addActionListener(e -> new ConstraintsDialog(RobotPathCreator.getInstance(), waypoint.getMaxVelocity(), waypoint.getMaxAcceleration(), a -> waypoint.setMaxVelocity(a), a -> waypoint.setMaxAcceleration(a)).setVisible(true));
    }

    public void configureHandleOwner(PathPoint<G> p) {
        this.waypoint = p;
        configureFocus();  
    }

    public T getWaypoint() {
        return (T) waypoint;
    }
}