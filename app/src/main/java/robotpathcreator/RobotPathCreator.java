package robotpathcreator;

import robotpathcreator.renderer.PathPointsEditor;
import robotpathcreator.renderer.PathPointsList;
import robotpathcreator.renderer.PathsDisplay;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class RobotPathCreator extends JFrame {

    private final PathPointsEditor editor = new PathPointsEditor(this);
    private final PathPointsList list = new PathPointsList(this);
    private final PathsDisplay display = new PathsDisplay(this.list);

    public RobotPathCreator() {
        list.addListSelectionListener(e -> {
            this.editor.setCurrentPoint(list.getSelectedValue());
            this.getContentPane().repaint();
        });

        JMenuBar t = new JMenuBar();
        JMenu file = new JMenu("Files");
        JMenuItem imp = new JMenuItem("Import");
        imp.addActionListener(e -> {
            // import files
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".json");
                }

                @Override
                public String getDescription() {
                    return "JSON (.json)";
                }
            });
            fc.setMultiSelectionEnabled(false);
            fc.addActionListener(a -> {
                if (a.getActionCommand().equals("ApproveSelection")) this.list.setTrajectory(RobotTrajectory.importTrajectory(this, fc.getSelectedFile()));
            });
            fc.showOpenDialog(this);
        });
        JMenuItem exp = new JMenuItem("Export");
        exp.addActionListener(e -> {
            // export files
        });
        file.add(imp);
        file.add(exp);
        t.add(file);
        JMenuItem add = new JMenuItem("Add Point");
        add.addActionListener(e -> {
            // add point
        });
        t.add(add);

        setJMenuBar(t);
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(1, 2));
        controls.add(list);
        controls.add(editor);


        JSplitPane ui = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.display, controls);
        setContentPane(ui);
        ui.setResizeWeight(0.75);
        pack();
    }

    public PathPointsEditor getEditor() {
        return editor;
    }

    public PathsDisplay getDisplay() {
        return display;
    }
}
