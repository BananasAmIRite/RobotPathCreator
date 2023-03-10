package robotpathcreator.renderer;

import org.bananasamirite.robotmotionprofile.TankMotionProfile;
import robotpathcreator.data.PathPoint;
import robotpathcreator.data.SplinePathPoint;

import javax.swing.*;

public class PathPointsListEditor extends JPanel {
    private final PathPointsList list;

    private final JButton addSplinePoint = new JButton("Add Spline");

    private final JButton addCommandPoint = new JButton("Add Command");
    private final JButton removePoint = new JButton("Delete");
    private final JButton upPoint = new JButton("Up");
    private final JButton downPoint = new JButton("Down");

    public PathPointsListEditor(PathPointsList list) {
        this.list = list;
        setupLayout();
    }

    private void setupLayout() {

        JScrollPane scrollableList = new JScrollPane(list);
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(addSplinePoint).addComponent(addCommandPoint).addComponent(removePoint)
                                        .addComponent(upPoint).addComponent(downPoint)
                        )
                        .addComponent(scrollableList)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(addSplinePoint).addComponent(addCommandPoint).addComponent(removePoint)
                                        .addComponent(upPoint).addComponent(downPoint)
                        )
                        .addComponent(scrollableList)
        );

        addSplinePoint.addActionListener(e -> {
            PathPoint<?> lastPoint = this.list.getTrajectory().getPoints().lastElement();
            this.list.addSplinePoint("Untitled Waypoint",
                    (lastPoint.getX() + lastPoint.getWeightTranslationX() * 1.5 * (lastPoint instanceof SplinePathPoint && ((SplinePathPoint) lastPoint).isReversed() ? -1 : 1)),
                    (lastPoint.getY() + lastPoint.getWeightTranslationY() * 1.5 * (lastPoint instanceof SplinePathPoint && ((SplinePathPoint) lastPoint).isReversed() ? -1 : 1)),
                    lastPoint.getAngle(), lastPoint.getWeightTranslationX(), 1, lastPoint instanceof SplinePathPoint && ((SplinePathPoint) lastPoint).isReversed(), new TankMotionProfile.TankMotionProfileConstraints(0, 0));
        });

        addCommandPoint.addActionListener(e -> {
            PathPoint<?> lastPoint = this.list.getTrajectory().getPoints().lastElement();
            this.list.addCommandPoint("Untitled Command",
                    (lastPoint.getX() + lastPoint.getWeightTranslationX() * 1.5 * (lastPoint instanceof SplinePathPoint && ((SplinePathPoint) lastPoint).isReversed() ? -1 : 1)),
                    (lastPoint.getY() + lastPoint.getWeightTranslationY() * 1.5 * (lastPoint instanceof SplinePathPoint && ((SplinePathPoint) lastPoint).isReversed() ? -1 : 1)),
                    lastPoint.getAngle(), lastPoint.getWeightTranslationX(), 1.0, new TankMotionProfile.TankMotionProfileConstraints(0, 0), "");
        });

        removePoint.addActionListener(e -> {
            if (this.list.getTrajectory().getPoints().size() <= 1 || this.list.getSelectedIndex() == -1) return;
            this.list.getTrajectory().getPoints().remove(this.list.getSelectedIndex());
        });

        upPoint.addActionListener(e -> {
            int index = this.list.getSelectedIndex();
            if (index <= 0) return;
            PathPoint<?> elem = this.list.getTrajectory().getPoints().get(index);
            this.list.getTrajectory().getPoints().remove(index);
            this.list.getTrajectory().getPoints().add(index-1, elem);
            this.list.setSelectedIndex(index-1);
        });

        downPoint.addActionListener(e -> {
            int index = this.list.getSelectedIndex();
            if (index == -1 || index >= this.list.getTrajectory().getPoints().size()-1) return;
            PathPoint<?> elem = this.list.getTrajectory().getPoints().get(index);
            this.list.getTrajectory().getPoints().remove(index);
            this.list.getTrajectory().getPoints().add(index+1, elem);
            this.list.setSelectedIndex(index+1);
        });
    }
}
