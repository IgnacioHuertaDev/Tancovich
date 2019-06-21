import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Sprite {

    protected int x;
    protected int y;
    protected int r;
    protected int width;
    protected int height;
    protected boolean visible;
    protected BufferedImage image;

    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        visible = true;
    }
    
    public Sprite(int x, int y, int r) {

        this.x = x;
        this.y = y;
        this.r = r;
        visible = true;
    }
    
    protected void loadImage(String imageName) {
    	try {        	
        	image = ImageIO.read(getClass().getResourceAsStream(imageName));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    	getImageDimensions();
    }

    protected void loadImage(String imageName, int angle) {
    	try {        	
        	image = ImageIO.read(getClass().getResourceAsStream(imageName));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    	if(angle != 0) image = rotate(image, angle);
    	getImageDimensions();
    }
    
    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    
    public BufferedImage rotate(BufferedImage img, double angle) {
    	double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));        
        int w = img.getWidth(), h = img.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
        
        BufferedImage result = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.translate((neww-w)/2, (newh-h)/2);
        g.rotate(rads, w/2, h/2);
        g.drawRenderedImage(img, null);
        g.dispose();
        return result;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getR() {
        return r;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Shape getShape() {
    	Shape bounds = new Rectangle(x, y, width, height);
        return bounds;
    }
    
    public static boolean testIntersection(Shape shapeA, Shape shapeB) {
    	Area areaA = new Area(shapeA);
    	areaA.intersect(new Area(shapeB));
    	return !areaA.isEmpty();
    }
}