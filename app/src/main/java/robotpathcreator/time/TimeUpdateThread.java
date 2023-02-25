package robotpathcreator.time;

import org.bananasamirite.robotmotionprofile.data.Trajectory;
import org.bananasamirite.robotmotionprofile.data.task.TrajectoryTask;
import org.bananasamirite.robotmotionprofile.data.task.WaypointTask;
import robotpathcreator.RobotPathCreator;

public class TimeUpdateThread extends Thread {

    private RobotPathCreator pathCreator;
    private long wait;
    private boolean finished = false;

    public TimeUpdateThread(RobotPathCreator creator, long wait) {
        this.pathCreator = creator;
        this.wait = wait;
    }

    @Override
    public void run() {
        while (!finished) {
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Trajectory t = Trajectory.fromWaypoint(pathCreator.getList().getWaypoints(), pathCreator.getList().getTrajectory().getConfig());
            double totalTime = 0;
            for (int j = 0; j < t.getTasks().size(); j++) {
                TrajectoryTask task = t.getTasks().get(j);
                if (task instanceof WaypointTask && ((WaypointTask) task).getWaypoints().size() > 1) {
                    totalTime += ((WaypointTask) task).createProfile().getTotalTime();
                }
            }

            pathCreator.setFooter("Total Time: " + totalTime );
        }
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
