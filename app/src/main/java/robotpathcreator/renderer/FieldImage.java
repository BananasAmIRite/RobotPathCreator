public class FieldImage {
    private Image img; 

    private double pixelsPerMeter; 

    private double sizeX; 
    private double sizeY; 
    
    private double originX; 
    private double originY; 
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

    public Coordinate<Integer> getRenderStartCoordinates(double scale) {
        int startX = (int) (-originX / pixelsPerMeter * scale); 
        int startY = (int) (-originY / pixelsPerMeter * scale); 

        return new Coordinate<Integer>(startX, startY); 
    }

    public Dimension getImageDimensions(double scale) {
        return new Dimension(sizeX / pixelsPerMeter * scale, sizeY / pixelsPerMeter * scale); 
    }
}