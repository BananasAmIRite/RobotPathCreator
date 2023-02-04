package robotpathcreator.dialog;

import org.bananasamirite.robotmotionprofile.TankMotionProfile;
import org.bananasamirite.robotmotionprofile.data.RobotConfiguration;
import robotpathcreator.RobotPathCreator;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class RobotConfigDialog extends JDialog {

    private final JTextField dimensionXField = new JTextField();
    private final JTextField dimensionYField = new JTextField();

    private final TankMotionProfile.TankMotionProfileConstraints constraints;

    public RobotConfigDialog(Frame owner, RobotConfiguration initial, Consumer<RobotConfiguration> configurationSupplier) {
        super(owner, "Configuration", true);


        GroupLayout layout = new GroupLayout(this.getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel mX = new JLabel("Length: ");
        JLabel mY = new JLabel("Width: ");
        JLabel editConstraints = new JLabel("Constraints");

        JButton saveButton = new JButton("Save");
        JButton constraintBtn = new JButton("Edit");
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup().addComponent(mX).addComponent(mY).addComponent(editConstraints))
                        .addGroup(layout.createParallelGroup().addComponent(dimensionXField).addComponent(dimensionYField).addComponent(constraintBtn).addComponent(saveButton))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(mX).addComponent(dimensionXField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(mY).addComponent(dimensionYField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(editConstraints).addComponent(constraintBtn))
                        .addComponent(saveButton)
        );

        dimensionXField.setText(Double.toString(initial.getDimensionX()));
        dimensionYField.setText(Double.toString(initial.getDimensionY()));
        this.constraints = initial.getConstraints();

        constraintBtn.addActionListener(e ->
                new ConstraintsDialog(RobotPathCreator.getInstance(), constraints.getMaxVelocity(), constraints.getMaxAcceleration(),
                    a -> constraints.setMaxVelocity(a), a -> constraints.setMaxAcceleration(a)).setVisible(true));
        saveButton.addActionListener(e -> {
            configurationSupplier.accept(new RobotConfiguration(
                    Double.parseDouble(this.dimensionXField.getText()),
                    Double.parseDouble(this.dimensionYField.getText()),
                    this.constraints));
            setVisible(false);
        });

        setSize(250, 170);
        setResizable(false);
        pack();
        setLocationRelativeTo(owner);
    }
}
