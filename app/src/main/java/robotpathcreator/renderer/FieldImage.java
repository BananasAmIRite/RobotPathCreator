package robotpathcreator.renderer;

import robotpathcreator.Coordinate;

import java.awt.*;

public class FieldImage {
    private final Image img;

    private final double pixelsPerMeter;

    private final double sizeX;
    private final double sizeY;
    
    private final double originX;
    private final double originY;
    public FieldImage(Image img, double pixelsPerMeter, double sizeX, double sizeY, double originX, double originY) {
        this.img = img; 
        this.pixelsPerMeter = pixelsPerMeter; 
        this.sizeX = sizeX; 
        this.sizeY = sizeY; 
        this.originX = originX; 
        this.originY = originY; 
    }

    public Image getImage() {
        return img; 
    }

    public Coordinate<Double> getRenderStartCoordinates() {
        double startX = -originX / pixelsPerMeter;
        double startY = originY / pixelsPerMeter;

        return new Coordinate<>(startX, startY);
    }

    public Dimension getImageDimensions(double scale) {
        return new Dimension((int) (sizeX / pixelsPerMeter * scale), (int) (sizeY / pixelsPerMeter * scale));
    }
}