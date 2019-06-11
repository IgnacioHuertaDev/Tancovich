public class Missile extends Sprite {

    private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
    private final int MISSILE_SPEED = 5;

    public Missile(int x, int y, int r) {
        super(x, y, r);
        initMissile();
    }

    private void initMissile() {

        loadImage("Resources/bulletDark2.png");
        getImageDimensions();
        rotateImageByDegrees(180+r);
    }

    public void move() {

    	x = (int)(x + (Math.sin(Math.toRadians(-r)) * MISSILE_SPEED));
        y = (int)(y + (Math.cos(Math.toRadians(-r)) * MISSILE_SPEED));

        if (x < 0 || x > BOARD_WIDTH)
            visible = false;
        
        if (y < 0 || y > BOARD_HEIGHT)
            visible = false;
    }
}