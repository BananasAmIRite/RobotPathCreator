public class PathPointsEditor extends JPanel {
    private PathPoint currentPoint; 

    private JTextField nameField = new JTextField(); 
    private JTextField posXField = new JTextField(); 
    private JTextField posYField = new JTextField(); 
    private JTextField timeField = new JTextField(); 
    private JTextField velocityField = new JTextField();
    private JTextField angleField = new JTextField(); 

    public PathPointsEditor() {

    }

    public void render() {

        if (currentPoint == null) return; 


    }

    public void setCurrentPoint(PathPoint point) {
        this.currentPoint = point; 

        this.nameField.setText(point.getName()); 
        this.nameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                point.setName(nameField.getText()); 
            }

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }
        })
    }
}