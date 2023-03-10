package robotpathcreator;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        RobotPathCreator creator = new RobotPathCreator();
        creator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String err = sw.toString();
            JOptionPane.showMessageDialog(creator,
                err,
                "Error", JOptionPane.ERROR_MESSAGE);
        });
        creator.setSize(800, 500);
        creator.setVisible(true);
    }
}
