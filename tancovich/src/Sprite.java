import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
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
    protected BufferedImage image;
    protected boolean visible;
    protected boolean alive;
    protected int explosionCounter;

    public Sprite(int x, int y) {

    	setX(x);
    	setY(y);
    	setR(0);
    	setVisible(true);
    	setAlive(true);
    }
    
    public Sprite(int x, int y, int r) {

    	setX(x);
    	setY(y);
        setR(r);
        setVisible(true);
    	setAlive(true);
    }
    
    public Sprite(int x, int y, int width, int height) {

    	setX(x);
    	setY(y);
    	setR(0);
    	setWidth(width);
    	setHeight(height);
    	setVisible(true);
    	setAlive(true);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }
    
    public void setX(int x)
    {
    	this.x = x;
    }

    public int getY() {
        return y;
    }
    
    public void setY(int y)
    {
    	this.y = y;
    }
    
    public int getR() {
        return r;
    }
    
    public void setR(int r)
    {
    	int normalized = normalizeAngle(r);
    	this.r = normalized;
    }
    
    private int normalizeAngle(int angle)
    {
        int newAngle = angle % 360;
        newAngle = (newAngle + 360) % 360;  
        if (newAngle > 180) newAngle -= 360;  
        return newAngle;
    }

    public int getWidth() {
		return width;
	}
    
    public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}	

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Shape getShape() {
    	Shape bounds = new Rectangle(x, y, width, height);
        return bounds;
    }
	
	protected void loadImage(String imageName) {
    	try {        	
        	image = ImageIO.read(getClass().getResourceAsStream(imageName));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    	if(r != 0) image = rotate(image, r);
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
    
    public BufferedImage rotate(BufferedImage img, int angle) {
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
	
    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    
    //Carga la imagen correspondiente al momento de la explosion. 
    //Funciona mejor con explosionFinish multiplos de 5 porque hay 5 imagenes de la explosion.
    public void explodeSprite(int explosionFinish)
    {
    	if(explosionCounter < explosionFinish)
    	{
    		if(isVisible() && !isAlive())
            {
    			boolean found = false;
    			for(int i = 0; i < 5 && !found; i++)
    			{
    				if(explosionCounter <= (i+1)*(explosionFinish/5))
    				{
    					loadImage("Resources/explosion"+(i+1)+".png");
    					found = true;
    				}
    			}
            }
    		//Fix explosiones fuera de imagen
    		if(x > Board.BOARD_WIDTH - getWidth()) x -= 1;
    		if(y > Board.BOARD_HEIGHT - getHeight()) y -= 1;
    		explosionCounter++;
    	}
    	else
    	{
    		this.setVisible(false);
    		explosionCounter = 0;
    	}
    }
}