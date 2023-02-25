package robotpathcreator;

import robotpathcreator.data.RobotTrajectory;
import robotpathcreator.dialog.RobotConfigDialog;
import robotpathcreator.renderer.*;
import robotpathcreator.time.TimeUpdateThread;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RobotPathCreator extends JFrame {

    private final PathPointsEditor editor = new PathPointsEditor(this);
    private final PathPointsList list = new PathPointsList(this);
    private final PathsDisplay display = new PathsDisplay(this.list);

    private static RobotPathCreator instance;

    private final JLabel footer;

    public RobotPathCreator() throws IOException {
        RobotPathCreator.instance = this;
        this.footer = new JLabel();

        BufferedImage image = ImageIO.read(FieldImage.class.getResourceAsStream("/field_image.png"));

        FieldImage fImage = new FieldImage(image,
                63,
                image.getWidth(), image.getHeight(),
                46, 36
//                 521, 252
        );
        display.setFieldImage(fImage); 

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
                if (a.getActionCommand().equals("ApproveSelection")) {
                    try {
                        this.list.setTrajectory(RobotTrajectory.importTrajectory(this, fc.getSelectedFile()));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            fc.showOpenDialog(this);
        });
        JMenuItem exp = new JMenuItem("Export");
        exp.addActionListener(e -> {
            // export files
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
                if (a.getActionCommand().equals("ApproveSelection")) {
                    try {
                        this.list.getTrajectory().toJsonFile(fc.getSelectedFile());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            fc.showSaveDialog(this);
        });
        file.add(imp);
        file.add(exp);
        t.add(file);
        JMenuItem addNew = new JMenuItem("New");
        addNew.addActionListener(e -> this.list.setTrajectory(new RobotTrajectory(this)));
        JMenu project = new JMenu("Project");
        JMenuItem configItem = new JMenuItem("Configuration");
        configItem.addActionListener(e -> {
            RobotConfigDialog d = new RobotConfigDialog(this, this.list.getTrajectory().getConfig(), config -> this.list.getTrajectory().setConfig(config));
            d.setVisible(true);
        });
        project.add(addNew);
        project.add(configItem);
        t.add(project);
        JMenu options = new JMenu("Options");
        JCheckBoxMenuItem showOutline = new JCheckBoxMenuItem("Show Outline");
        showOutline.addActionListener(a -> this.display.setShowOutline(showOutline.isSelected()));
        showOutline.setSelected(this.display.getShowOutline());
        options.add(showOutline);
        t.add(options);

        setJMenuBar(t);
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(1, 2));
        controls.add(new PathPointsListEditor(list));
        controls.add(editor);

        new TimeUpdateThread(this, 1000).start();

        JSplitPane ui = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.display, controls);
        getContentPane().add(ui, BorderLayout.CENTER);
        getContentPane().add(footer, BorderLayout.PAGE_END);
        ui.setResizeWeight(0.75);
        pack();
    }

    public PathPointsEditor getEditor() {
        return editor;
    }

    public PathsDisplay getDisplay() {
        return display;
    }

    public PathPointsList getList() {
        return list;
    }

    public void setFooter(String t) {
        this.footer.setText(t);
    }

    public static RobotPathCreator getInstance() {
        return instance;
    }
}
