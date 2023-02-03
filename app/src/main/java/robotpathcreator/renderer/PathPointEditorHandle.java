import robotpathcreator.PathPoint;

public class PathPointEditorHandle {

    private final JTextField nameField = new JTextField();
    private final JTextField posXField = new JTextField();
    private final JTextField posYField = new JTextField();
    private final JTextField timeField = new JTextField();
    private final JTextField velocityField = new JTextField();
    private final JTextField angleField = new JTextField();

    private final PathPoint waypoint; 

    public PathPointEditorHandle(PathPoint waypoint) {
        this.waypoint = waypoint; 
    }

    public void display(PathPointsEditor editor) {
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

        editor.add(new JLabel("Velocity: "));
        editor.add(this.velocityField);

        editor.add(new JLabel("Angle: "));
        editor.add(this.angleField);
    }

    public void update() {
        this.nameField.setText(currentPoint.getName());
        this.posXField.setText(Double.toString(currentPoint.getX()));
        this.posYField.setText(Double.toString(currentPoint.getY()));
        this.timeField.setText(Double.toString(currentPoint.getTravelTime()));
        this.velocityField.setText(Double.toString(currentPoint.getVelocity()));
        this.angleField.setText(Double.toString(Math.toDegrees(currentPoint.getAngle()))); // IMPORTANT: displayed is degrees, stored is radians
    }

    protected void configureFocus() {
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

    public void configureHandleOwner(PathPoint p) {
        this.waypoint = p;
        configureFocus();  
    }
}