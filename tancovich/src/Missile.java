public class Missile extends Sprite implements Entity {

    private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
    private final int MISSILE_SPEED = 6;
    private int bounce = 0;

    public Missile(int x, int y, int r) {
        super(x, y, r);
        init();
    }

    public void init() {

        loadImage("Resources/bulletDark2.png", 180+r);
    }

    public void update() {

    	x = x + (int)(Math.sin(Math.toRadians(-r)) * MISSILE_SPEED);
        y = y + (int)(Math.cos(Math.toRadians(-r)) * MISSILE_SPEED);

        if (x < 0 || x > BOARD_WIDTH)
        {
        	r = -r;
        	bounce++;
        }
        
        if (y < 0 || y > BOARD_HEIGHT)
        {
        	r = 180-r;
        	bounce++;
        }
        
        if(bounce >= 3)
        {
        	this.visible = false;
        }
        
        loadImage("Resources/bulletDark2.png", 180+r);
    }
}