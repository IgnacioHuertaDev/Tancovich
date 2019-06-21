import java.awt.Rectangle;
import java.awt.Shape;

public class BoxLevel {

	private int x;
	private int y;
	private int width;
	private int height;
	
	public BoxLevel() {
		
	}
	
	public BoxLevel(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
	
	public Shape getShape() {
    	Shape bounds = new Rectangle(x, y, width, height);
        return bounds;
    }

}
