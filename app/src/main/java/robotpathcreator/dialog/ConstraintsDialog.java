package robotpathcreator.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ConstraintsDialog extends JDialog {
    private final JTextField maxVelField = new JTextField();
    private final JTextField maxAccelField = new JTextField();

    public ConstraintsDialog(Frame owner, double maxVel, double maxAccel, Consumer<Double> setMaxVel, Consumer<Double> setMaxAccel) {
        super(owner, "Constraints", true);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel mV = new JLabel("Max Velocity: ");
        JLabel mA = new JLabel("Max Acceleration: ");

        JButton saveButton = new JButton("Save");
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup().addComponent(mV).addComponent(mA))
                        .addGroup(layout.createParallelGroup().addComponent(maxVelField).addComponent(maxAccelField).addComponent(saveButton))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(mV).addComponent(maxVelField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(mA).addComponent(maxAccelField))
                        .addComponent(saveButton)
        );

        maxVelField.setText(Double.toString(maxVel));
        maxAccelField.setText(Double.toString(maxAccel));
        saveButton.addActionListener(e -> {
            setMaxVel.accept(Double.valueOf(maxVelField.getText()));
            setMaxAccel.accept(Double.valueOf(maxAccelField.getText()));
            setVisible(false);
        });

        setSize(250, 125);
        setResizable(false);
        pack();
        setLocationRelativeTo(owner);
    }

}
