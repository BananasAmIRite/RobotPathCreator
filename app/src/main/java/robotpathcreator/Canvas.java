package robotpathcreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public abstract class Canvas extends JPanel {
    protected Image image;
    private int width;
    private int height;

    public Canvas() {
        this(256, 256);
    }

    public Canvas(int sizeX, int sizeY) {
        super();
        setSize(sizeX, sizeY);
        this.width = sizeX;
        this.height = sizeY;
    }

    /*
    * Alias for repainting the component
    * */
    public void update() {
        this.invalidate();
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize((int) this.getSize().getWidth(), (int) this.getSize().getHeight());
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.draw(graphics);
        g.drawImage(image, 0, 0, null);
    }

    public abstract void draw(Graphics2D g);

    @Override
    public void setSize(Dimension d) {
        setSize((int) d.getWidth(), (int) d.getHeight());
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.width = width;
        this.height = height;
        image = createCanvasImage(width, height);
//                image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    public Graphics getContext() {
        return image.getGraphics();
    }

    private Image createCanvasImage(int w, int h) {
        return new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    }

    public Dimension getImgSize() {
        return new Dimension(image.getWidth(null), image.getHeight(null));
    }

    public int getImageWidth() {
        return width;
    }

    public int getImageHeight() {
        return height;
    }
}
